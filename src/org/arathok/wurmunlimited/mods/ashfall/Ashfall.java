package org.arathok.wurmunlimited.mods.ashfall; // HELLO GITHUB!

import com.wurmonline.server.MessageServer;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.economy.Shop;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.shared.constants.CreatureTypes;
import javassist.*;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.arathok.wurmunlimited.mods.ashfall.artifacts.*;
import org.arathok.wurmunlimited.mods.ashfall.creatures.Sandworm;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;

import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.FishBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.WaterRitualHook;
import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.WaterballoonBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.WaterballoonBehaviourCreature;
import org.arathok.wurmunlimited.mods.ashfall.gameTweaks.FenceConstruction.FenceConstructionHook;
import org.arathok.wurmunlimited.mods.ashfall.gameTweaks.GTRepair.GuardTowerRepairBehaviour;

import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.*;

import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;
import org.arathok.wurmunlimited.mods.ashfall.senet.DiceBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.senet.RuleBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.senet.SenetItems;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreatures;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviours;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Ashfall implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener{
    public static final Logger logger = Logger.getLogger("Ashfall");
    private boolean skillGainForBred = true;
    private boolean outOfThinAir = false;
    private boolean alliedCritterNoBounties = false;
    private boolean increasedBounties = true;
    private boolean payBountyToBank = true;
    private float bountyMultiplier = 1.0f;
    private float bredMultiplier = 0.1f;
    private static boolean bDebug = false;
    private long defaultBounty = 100;
    public static Map<Integer, Long> creatureBounties;
    public static Map<Byte, Float> typeModifiers;


    @Override
    public void configure (Properties properties) {

        Config.waterSickness = Boolean.parseBoolean(properties.getProperty("waterSickness", "true"));
        skillGainForBred = Boolean.parseBoolean(properties.getProperty("skillGainForBred", Boolean.toString(skillGainForBred)));
        increasedBounties = Boolean.parseBoolean(properties.getProperty("increasedBounties", Boolean.toString(increasedBounties)));
        outOfThinAir = Boolean.parseBoolean(properties.getProperty("outOfThinAir", Boolean.toString(outOfThinAir)));
        alliedCritterNoBounties = Boolean.parseBoolean(properties.getProperty("alliedCritterNoBounties", Boolean.toString(alliedCritterNoBounties)));
        payBountyToBank = Boolean.parseBoolean(properties.getProperty("payBountyToBank", Boolean.toString(payBountyToBank)));
        bountyMultiplier = Float.parseFloat(properties.getProperty("bountyMultiplier", Float.toString(bountyMultiplier)));
        bredMultiplier = Float.parseFloat(properties.getProperty("bredMultiplier", Float.toString(bredMultiplier)));
        defaultBounty = Long.parseLong(properties.getProperty("defaultBounty", Long.toString(defaultBounty)));
        bDebug = Boolean.parseBoolean(properties.getProperty("debug", Boolean.toString(bDebug)));
        try {
            final String logsPath = Paths.get("mods") + "/logs/";
            final File newDirectory = new File(logsPath);
            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }
            final FileHandler fh = new FileHandler(logsPath + this.getClass().getSimpleName() + ".log", 10240000, 200, true);
            if (bDebug) {
                fh.setLevel(Level.INFO);
            }
            else {
                fh.setLevel(Level.WARNING);
            }
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        }
        catch (IOException ie) {
            System.err.println(this.getClass().getName() + ": Unable to add file handler to logger");
            logger.log(Level.WARNING, this.getClass().getName() + ": Unable to add file handler to logger");
        }

        Debug("Debugging messages are enabled.");
        logger.log(Level.INFO, "skillGainForBred: " + skillGainForBred);
        logger.log(Level.INFO, "increasedBounties: " + increasedBounties);
        logger.log(Level.INFO, "outOfThinAir: " + outOfThinAir);
        logger.log(Level.INFO, "critterNoBounties: " + alliedCritterNoBounties);
        logger.log(Level.INFO, "payBountyToBank: " + payBountyToBank);
        logger.log(Level.INFO, "bountyMultiplier: " + bountyMultiplier);
        logger.log(Level.INFO, "bredMultiplier: " + bredMultiplier);
        //Now for the individual bounties
        //Bounties are in iron coins.  So 100 = 1 copper, 10000 = 1 silver, etc
        creatureBounties = new HashMap<Integer, Long>();
        typeModifiers = new HashMap<Byte, Float>();

        //Cow, 1 copper
        loadBounty(properties, "Cow_Brown", CreatureTemplateIds.COW_BROWN_CID, 100);
        //Black wolf, 3 copper
        loadBounty(properties, "Wolf_Black", CreatureTemplateIds.WOLF_BLACK_CID, 300);
        //Troll, 10 copper
        loadBounty(properties, "Troll", CreatureTemplateIds.TROLL_CID, 1000);
        //Brown Bear, 6 copper (cause it can swim after you)
        loadBounty(properties, "Bear_Brown", CreatureTemplateIds.BEAR_BROWN_CID, 600);
        //Large Rat, 2 copper
        loadBounty(properties, "Rat_Large", CreatureTemplateIds.RAT_LARGE_CID, 200);
        //Mountain Lion, 3 copper
        loadBounty(properties, "Lion_Mountain", CreatureTemplateIds.LION_MOUNTAIN_CID, 300);
        //Wild Cat, 2 copper
        loadBounty(properties, "Cat_Wild", CreatureTemplateIds.CAT_WILD_CID, 200);
        //Red Dragon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Dragon_Red", CreatureTemplateIds.DRAGON_RED_CID, 10000);
        //Green Drake, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drake_Green", CreatureTemplateIds.DRAKE_GREEN_CID, 10000);
        //Black Drake, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drake_Black", CreatureTemplateIds.DRAKE_BLACK_CID, 10000);
        //White Drake, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drake_White", CreatureTemplateIds.DRAKE_WHITE_CID, 10000);
        //Forest Giant, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Forest_Giant", CreatureTemplateIds.FOREST_GIANT_CID, 10000);
        //Unicorn, 3 copper
        loadBounty(properties, "Unicorn", CreatureTemplateIds.UNICORN_CID, 300);
        //Cyclops, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Cyclops", CreatureTemplateIds.CYCLOPS_CID, 10000);
        //Goblin, 3 copper
        loadBounty(properties, "Goblin", CreatureTemplateIds.GOBLIN_CID, 300);
        //Spider Mother, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Spider_Mother", CreatureTemplateIds.SPIDER_MOTHER_CID, 10000);
        //Huge Spider, 5 copper
        loadBounty(properties, "Spider", CreatureTemplateIds.SPIDER_CID, 500);
        //Goblin Leader, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Goblin_Leader", CreatureTemplateIds.GOBLIN_LEADER_CID, 10000);
        //Troll King, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Troll_King", CreatureTemplateIds.TROLL_KING_CID, 10000);
        //Wild Boar, 7 copper
        loadBounty(properties, "Boar_Fo", CreatureTemplateIds.BOAR_FO_CID, 700);
        //Anaconda, 10 copper
        loadBounty(properties, "Anaconda", CreatureTemplateIds.ANACONDA_CID, 1000);
        //Mountain Gorilla, 7 copper
        loadBounty(properties, "Gorilla_Magranon", CreatureTemplateIds.GORILLA_MAGRANON_CID, 700);
        //Rabid Hyena, 7 copper
        loadBounty(properties, "Hyena_Libila", CreatureTemplateIds.HYENA_LIBILA_CID, 700);
        //Black bear, 5 copper
        loadBounty(properties, "Bear_Black", CreatureTemplateIds.BEAR_BLACK_CID, 500);
        //Cave bug, 2 copper 50 iron
        loadBounty(properties, "Cave_Bug", CreatureTemplateIds.CAVE_BUG_CID, 250);
        //Pig, 1 copper
        loadBounty(properties, "Pig", CreatureTemplateIds.PIG_CID, 100);
        //Hen, 50 iron
        loadBounty(properties, "Hen", CreatureTemplateIds.HEN_CID, 50);
        //Chicken, 50 iron
        loadBounty(properties, "Chicken", CreatureTemplateIds.CHICKEN_CID, 50);
        //Bull, 1 copper 50 iron
        loadBounty(properties, "Bull", CreatureTemplateIds.BULL_CID, 150);
        //Calf, 50 iron
        loadBounty(properties, "Calf", CreatureTemplateIds.CALF_CID, 50);
        //Bull, 1 copper 50 iron
        loadBounty(properties, "Dog", CreatureTemplateIds.DOG_CID, 150);
        //Rooster, 50 iron
        loadBounty(properties, "Rooster", CreatureTemplateIds.ROOSTER_CID, 50);
        //Deer, 1 copper
        loadBounty(properties, "Deer", CreatureTemplateIds.DEER_CID, 100);
        //Pheasant, 50 iron
        loadBounty(properties, "Pheasant", CreatureTemplateIds.PHEASANT_CID, 50);
        //Lava Spider, 8 copper
        loadBounty(properties, "Lava_Spider", CreatureTemplateIds.LAVA_SPIDER_CID, 800);
        //Lava Fiend, 8 copper
        loadBounty(properties, "Lava_Creature", CreatureTemplateIds.LAVA_CREATURE_CID, 800);
        //Crocodile, 8 copper
        loadBounty(properties, "Crocodile", CreatureTemplateIds.CROCODILE_CID, 800);
        //Scorpion, 6 copper
        loadBounty(properties, "Scorpion", CreatureTemplateIds.SCORPION_CID, 600);
        //Horse, 1 copper 50 iron
        loadBounty(properties, "Horse", CreatureTemplateIds.HORSE_CID, 150);
        //Foal, 50 iron
        loadBounty(properties, "Foal", CreatureTemplateIds.FOAL_CID, 50);
        //Sea Serpent, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Sea_Serpent", CreatureTemplateIds.SEA_SERPENT_CID, 10000);
        //Shark, 5 copper
        loadBounty(properties, "Shark_Huge", CreatureTemplateIds.SHARK_HUGE_CID, 500);
        //Bison, 3 copper
        loadBounty(properties, "Bison", CreatureTemplateIds.BISON_CID, 300);
        //Hell Horse, 2 silver 50 copper
        loadBounty(properties, "Hell_Horse", CreatureTemplateIds.HELL_HORSE_CID, 250);
        //Hell Hound, 8 copper
        loadBounty(properties, "Hell_Hound", CreatureTemplateIds.HELL_HOUND_CID, 800);
        //Hell Scorpious, 8 copper
        loadBounty(properties, "Hell_Scorpion", CreatureTemplateIds.HELL_SCORPION_CID, 800);
        //Black Dragon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Dragon_Black", CreatureTemplateIds.DRAGON_BLACK_CID, 10000);
        //Green Dragon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Dragon_Green", CreatureTemplateIds.DRAGON_GREEN_CID, 10000);
        //Blue Dragon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Dragon_Blue", CreatureTemplateIds.DRAGON_BLUE_CID, 10000);
        //White Dragon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Dragon_White", CreatureTemplateIds.DRAGON_WHITE_CID, 10000);
        //Seal, 4 copper
        loadBounty(properties, "Seal", CreatureTemplateIds.SEAL_CID, 400);
        //Tortoise, 4 copper
        loadBounty(properties, "Tortoise", CreatureTemplateIds.TORTOISE_CID, 400);
        //Crab, 4 copper
        loadBounty(properties, "Crab", CreatureTemplateIds.CRAB_CID, 400);
        //Sheep, 1 copper
        loadBounty(properties, "Sheep", CreatureTemplateIds.SHEEP_CID, 100);
        //Blue Whale, 8 copper
        loadBounty(properties, "Blue_Whale", CreatureTemplateIds.BLUE_WHALE_CID, 800);
        //Seal cub, 2 copper
        loadBounty(properties, "Seal_Cub", CreatureTemplateIds.SEAL_CUB_CID, 200);
        //Dolphin, 2 copper
        loadBounty(properties, "Dolphin", CreatureTemplateIds.DOLPHIN_CID, 200);
        //Octopus, 2 copper
        loadBounty(properties, "Octopus", CreatureTemplateIds.OCTOPUS_CID, 200);
        //Lamb, 50 iron
        loadBounty(properties, "Lamb", CreatureTemplateIds.LAMB_CID, 50);
        //Ram, 1 copper
        loadBounty(properties, "Ram", CreatureTemplateIds.RAM_CID, 100);
        //Red Drake, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drake_Red", CreatureTemplateIds.DRAKE_RED_CID, 10000);
        //Blue Drake, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drake_Blue", CreatureTemplateIds.DRAKE_BLUE_CID, 10000);
        //Fog Spider, 8 copper
        loadBounty(properties, "Spider_Fog", CreatureTemplateIds.SPIDER_FOG_CID, 800);

        //Don't even know what these are yet so not yet putting them in the config file, but just in case...
        //Rift Jackal One, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Rift_Jackal_One", CreatureTemplateIds.RIFT_JACKAL_ONE_CID, 10000);
        //Rift Jackal Two, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Rift_Jackal_Two", CreatureTemplateIds.RIFT_JACKAL_TWO_CID, 10000);
        //Rift Jackal Three, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Rift_Jackal_Three", CreatureTemplateIds.RIFT_JACKAL_THREE_CID, 10000);
        //Rift Jackal Four, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Rift_Jackal_Four", CreatureTemplateIds.RIFT_JACKAL_FOUR_CID, 10000);


        //Valrei Critters, that I thought I wouldn't have to handle.  WRONG!
        //Sol Demon, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Demon_Sol", CreatureTemplateIds.DEMON_SOL_CID, 10000);
        //Deathcrawler Minion, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Deathcrawler_Minion", CreatureTemplateIds.DEATHCRAWLER_MINION_CID, 10000);
        //Spawn of Uttacha, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Spawn_Uttacha", CreatureTemplateIds.SPAWN_UTTACHA_CID, 10000);
        //Son Of Nogump, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Son_Of_Nogump", CreatureTemplateIds.SON_OF_NOGUMP_CID, 10000);
        //Drakespirit, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Drakespirit", CreatureTemplateIds.DRAKESPIRIT_CID, 10000);
        //Eaglespirit, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Eaglespirit", CreatureTemplateIds.EAGLESPIRIT_CID, 10000);
        //Vynora Avatar, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Avatar_Vynora", CreatureTemplateIds.EPIPHANY_VYNORA_CID, 10000);
        //Magranon Avatar, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Avatar_Magranon", CreatureTemplateIds.MAGRANON_JUGGERNAUT_CID, 10000);
        //Fo Avatar, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Avatar_Fo", CreatureTemplateIds.MANIFESTATION_FO_CID, 10000);
        //Libila Avatar, 1 silver (Remember this is per slayer)
        loadBounty(properties, "Avatar_Libila", CreatureTemplateIds.INCARNATION_LIBILA_CID, 10000);


        //Now for creature statuses and their multipliers
        //
        //Fierce, 1.5X
        loadModifier(properties, "Fierce", CreatureTypes.C_MOD_FIERCE, 1.5f);
        //Angry, 1.4X
        loadModifier(properties, "Angry", CreatureTypes.C_MOD_ANGRY, 1.4f);
        //Raging, 1.6X
        loadModifier(properties, "Raging", CreatureTypes.C_MOD_RAGING, 1.6f);
        //Slow, 0.9X
        loadModifier(properties, "Slow", CreatureTypes.C_MOD_SLOW, 0.95f);
        //Alert, 1.2X
        loadModifier(properties, "Alert", CreatureTypes.C_MOD_ALERT, 1.2f);
        //Greenish, 1.7X
        loadModifier(properties, "Greenish", CreatureTypes.C_MOD_GREENISH, 1.7f);
        //Lurking, 1.1X
        loadModifier(properties, "Lurking", CreatureTypes.C_MOD_LURKING, 1.1f);
        //Sly, 0.6X
        loadModifier(properties, "Sly", CreatureTypes.C_MOD_SLY, 0.8f);
        //Hardened, 1.3X
        loadModifier(properties, "Hardened", CreatureTypes.C_MOD_HARDENED, 1.3f);
        //Scared, 0.7X
        loadModifier(properties, "Scared", CreatureTypes.C_MOD_SCARED, 0.85f);
        //Diseased, 0.8X
        loadModifier(properties, "Diseased", CreatureTypes.C_MOD_DISEASED, 0.9f);
        //Champion, 1.7X
        loadModifier(properties, "Champion", CreatureTypes.C_MOD_CHAMPION, 1.7f);

    }
    public void loadBounty(Properties properties, String creatureName, int templateID, long defaultCritterBounty) {
        String bountyName = creatureName + "_Bounty";
        long bounty = Long.parseLong(properties.getProperty(bountyName, Long.toString(defaultCritterBounty)));
        bounty = (long)(bounty * bountyMultiplier);
        creatureBounties.put(templateID, bounty);
        logger.log(Level.INFO, bountyName + ": " + bounty);
    }

    public void loadModifier(Properties properties, String modifierName, byte modifierID, float defaultMultiplier) {
        String statusName = modifierName + "_StatusMultiplier";
        float multiplier = Float.parseFloat(properties.getProperty(statusName, Float.toString(defaultMultiplier)));
        typeModifiers.put(modifierID, multiplier);
        logger.log(Level.INFO, statusName + ": " + multiplier);
    }

    @Override
    public void preInit() {
        // EVERYBODY GETS TO DO MISSIONS
        Debug("Running preInit");
        ClassPool classpool= HookManager.getInstance().getClassPool();
        try {
            CtClass ctDeity =classpool.getCtClass("com.wurmonline.server.deities.Deities");
            ctDeity.getMethod("getFavoredKingdom","(I)B")
                    .insertBefore("deityNum=4;");

            ///////////////////////////////////////////



        } catch (NotFoundException e) {
            logger.log(Level.SEVERE,"class Deities not found",e);
            e.printStackTrace();
        } catch (CannotCompileException e) {
            logger.log(Level.SEVERE,"could not compile",e);
            e.printStackTrace();
        }


        // WATER TOKEN EVENT
        WaterRitualHook.insertStuff();
        // Fence changes
        FenceConstructionHook.insertStuff();


        if (skillGainForBred || increasedBounties) {
            try {
                CtClass ctCreature = ClassPool.getDefault().get("com.wurmonline.server.creatures.Creature");
                if(increasedBounties){
                    Debug("Injecting increased bounty code...");
                    ClassPool myPool = new ClassPool(ClassPool.getDefault());
                    myPool.appendClassPath("./mods/bountymod/bountymod.jar");
                    CtClass ctTHIS = myPool.get(this.getClass().getName());
//					ctCreature.addField(CtField.make("private static final boolean bDebug = " + Boolean.toString(bDebug) + ";", ctCreature));
//					ctCreature.addMethod(CtNewMethod.copy(ctTHIS.getDeclaredMethod("DebugLog"), ctCreature, null));
                    ctCreature.addMethod(CtNewMethod.copy(ctTHIS.getDeclaredMethod("checkCoinBounty"), ctCreature, null));
                    ctCreature.getDeclaredMethod("modifyFightSkill").instrument(new ExprEditor() {
                        public void edit(MethodCall m)
                                throws CannotCompileException {
                            if (m.getClassName().equals("com.wurmonline.server.players.Player")
                                    && m.getMethodName().equals("checkCoinAward")) {
                                String debugString = "";
                                if (bDebug)
                                    debugString = "java.util.logging.Logger.getLogger(\"org.gotti.wurmunlimited.mods.bountymod.BountyMod"
                                            + "\").log(java.util.logging.Level.INFO, \"Overriding checkCoinAward to checkCoinBounty\");\n";
                                m.replace(debugString + "$_ = checkCoinBounty(player);");
                            }
                        }
                    });
                }
                if(skillGainForBred) {
                    Debug("Injecting skillgain for bred code...");
                    ctCreature.getDeclaredMethod("isNoSkillgain").instrument(new ExprEditor() {
                        public void edit(MethodCall m)
                                throws CannotCompileException {
                            if (m.getClassName().equals("com.wurmonline.server.creatures.Creature")
                                    && m.getMethodName().equals("isBred")) {
                                String debugString = "";
                                if(bDebug) debugString = "System.out.println(\"isBred suppressed to false\");\n" +
                                        "System.out.flush();\n";
                                m.replace(debugString + "$_ = false;");
                            }
                        }
                    });
                }
            }
            catch (NotFoundException | CannotCompileException e) {
                logger.log(Level.SEVERE, "Error editing functions:", e);
                e.printStackTrace();
            }
        }

        /* BDEW MODDED CREATURES
        try {
            ClassPool classPool = HookManager.getInstance().getClassPool();
            if (classPool == null)
                logger.log(Level.SEVERE, "nu-uh!");
            CtClass ctSpawnTable = null;

            ctSpawnTable = classPool.getCtClass("com.wurmonline.server.zones.SpawnTable");


            ctSpawnTable.getMethod("addTileType", "(Lcom/wurmonline/server/zones/EncounterType;)V")
                    .insertBefore("org.arathok.wurmunlimited.mods.ashfall.SpawnOverride.spawnOverrideHook($1);");

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }*/
        ModVehicleBehaviours.init();
    }

    //This is here to be spliced into Creature so it can then be hooked.
    public boolean checkCoinBounty(Player thisPlayer) {
        return false;
    }

//	private void DebugLog(String x) {
//		if (bDebug) {
//			System.out.println(this.getClass().getSimpleName() + ": " + x);
//			System.out.flush();
//			java.util.logging.Logger.getLogger("org.gotti.wurmunlimited.mods.bountymod.BountyMod").log(Level.INFO, x);
//		}
//	}

    private void Debug(String x) {
        if (bDebug) {
            System.out.println(this.getClass().getSimpleName() + ": " + x);
            System.out.flush();
            logger.log(Level.INFO, x);
        }
    }


    @Override
    public boolean onPlayerMessage(Communicator communicator, String message) {
        if (message != null&&message.startsWith("#Ashfall waterRitual on")&&communicator.getPlayer().getPower()>=4)
        {
            WaterRitualHook.waterRitualRunning=true;
            communicator.sendSafeServerMessage("The Water Ritual just begun!");
            MessageServer.broadCastSafe("The Water Ritual just begun!",(byte)2);

        }
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onItemTemplatesCreated() {



        try{

            AshfallItems.register();
            SenetItems.register();
            FlaskOfVynora.register();
            EventItems.register();
            logger.log(Level.INFO, "Done with item Register");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }


        // TODO Auto-generated method stub

    }

    @Override
    public void onServerStarted() {
        // TODO Auto-generated method stub
        AshfallCreatures.createCreatureTemplates();
        ModActions.registerBehaviourProvider(new ArtifactBehaviour());
        ModActions.registerBehaviourProvider(new DiceBehaviour());
        ModActions.registerBehaviourProvider(new RuleBehaviour());
        ModActions.registerBehaviourProvider(new FlaskOfVynoraBehaviour());
        ModActions.registerBehaviourProvider(new WaterballoonBehaviour());
        ModActions.registerBehaviourProvider(new WaterballoonBehaviourCreature());
        ModActions.registerBehaviourProvider(new FishBehaviour());
        ModActions.registerBehaviourProvider(new GuardTowerRepairBehaviour());
        ModActions.registerActionPerformer(new MountFlaskOfVynoraPerformer());
        ModActions.registerActionPerformer(new DismountFlaskOfVynoraPerformer());

        logger.log(Level.INFO, "Done with Actions");
    }



    @Override
    public void init() {
        // TODO Auto-generated method stub
        ModCreatures.init();
        ModCreatures.addCreature(new Sandworm());
        logger.log(Level.INFO, "Done with creatures");
        if(increasedBounties) {
            try {
                CtClass[] paramTypes = {
                        HookManager.getInstance().getClassPool().get("com.wurmonline.server.players.Player")
                };

                HookManager.getInstance().registerHook("com.wurmonline.server.creatures.Creature", "checkCoinBounty", Descriptor.ofMethod(CtPrimitiveType.booleanType, paramTypes),
                        new InvocationHandlerFactory() {

                            @Override
                            public InvocationHandler createInvocationHandler() {
                                return new InvocationHandler() {

                                    @Override
                                    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
                                        //Pulling the indentation back for readability here.
                                        Player thisPlayer = (Player) args[0];
                                        Creature thisCreature = (Creature) object;
                                        Debug("checkCoinBounty hook running...");
                                        try {
                                            /* Money are NOT awarded if:
                                             *  - CritterNoBounties is activated AND
                                             *  - The creature is peaceful AND
                                             *  - The creature and the player are from the same kingdom.
                                             *  Note that Gorillas, Hyenas and Boars are considered peaceful.
                                             */
                                            if (alliedCritterNoBounties && !thisCreature.isAggHuman() && (thisPlayer.getKingdomId() == thisCreature.getKingdomId())) {
                                                return true;
                                            } else {
                                                final Shop kingsMoney = Economy.getEconomy().getKingsShop();
                                                if (outOfThinAir || kingsMoney.getMoney() > 100000L) {
                                                    Debug("King Has enough money, checking Template ID...");
                                                    int templateid = thisCreature.getTemplate().getTemplateId();
                                                    Debug("Template id is: " + templateid + "\tChecking if creatureBounties has that key...");
                                                    long bounty = defaultBounty;
                                                    String coinMessage = "";
                                                    if (org.arathok.wurmunlimited.mods.ashfall.Ashfall.creatureBounties.containsKey(templateid)) {
                                                        Debug("creatureBounties does have that id!");
                                                        bounty = org.arathok.wurmunlimited.mods.ashfall.Ashfall.creatureBounties.get(templateid);
                                                    } else {
                                                        Debug("creatureBounties doesn't have that id.");
                                                        Logger.getLogger("org.gotti.wurmunlimited.mods.bountymod.BountyMod")
                                                                .warning("No bounty found for templateid: " + templateid + ", using Default.");
                                                        coinMessage += "The king has not heard of such a creature, and in surprise only awards a default bounty.  ";
                                                    }
                                                    Debug("Bounty is: " + bounty);
                                                    bounty = adjustBountyForStatus(thisCreature, bounty);
                                                    Debug("Bounty, adjusted for status, is: " + bounty);
                                                    if(thisCreature.isBred()) {
                                                        bounty = (long)(bounty * bredMultiplier);
                                                        Debug("Creature was bred, so bounty is reduced to: " + bounty);
                                                    }
                                                    for (long oneId : thisCreature.getLatestAttackers())
                                                    {
                                                        Creature maybeGuard=Creatures.getInstance().getCreatureOrNull(oneId);
                                                        if (maybeGuard!=null)
                                                        if (maybeGuard.getTemplate().getName().contains("guard")||maybeGuard.getTemplate().getName().contains("Guard"))
                                                            bounty=0;

                                                    }
                                                    if (bounty==0)
                                                    coinMessage+="A tower guard was needed to help you fight the mob! He kept all the bounty for himself.";
                                                    long splitMoney=thisCreature.getLatestAttackers().length;
                                                    if (splitMoney>1)
                                                    {
                                                        coinMessage+= "you split the bounty with "+(splitMoney-1)+" other players";
                                                        bounty = bounty / splitMoney;
                                                    }
                                                    if (outOfThinAir || kingsMoney.getMoney() > bounty + 100000L) {
                                                        Debug("King can cover all the bounty.");
                                                        coinMessage += "You receive a bounty of ";
                                                    } else {
                                                        bounty = kingsMoney.getMoney() - 100000L;
                                                        Debug("King can't cover all the bounty, reduced to: " + bounty);
                                                        coinMessage += "The kingdom coffers have run low, and so you only receive ";
                                                    }
                                                    if(payBountyToBank) {
                                                        Debug("Bounty goes to player's bank.  Adding to bank...");
                                                        thisPlayer.addMoney(bounty);
                                                        if (!outOfThinAir) {
                                                            kingsMoney.setMoney(kingsMoney.getMoney() - bounty);
                                                        }
                                                    } else {
                                                        Debug("Bounty goes to player inventory.  Getting coins...");
                                                        Item[] coins = Economy.getEconomy().getCoinsFor(bounty);
                                                        Debug("Giving coins to player...");
                                                        for (Iterator<Item> iterator = Arrays.asList(coins)
                                                                .iterator(); iterator.hasNext();) {
                                                            Item coin = iterator.next();
                                                            thisPlayer.getInventory().insertItem(coin, true);
                                                            if (!outOfThinAir) {
                                                                kingsMoney.setMoney(kingsMoney.getMoney() -
                                                                        Economy.getValueFor(coin.getTemplateId()));
                                                            }
                                                        }
                                                    }
                                                    Debug("Compiling message...");
                                                    coinMessage += Economy.getEconomy().getChangeFor(bounty).getChangeString();
                                                    if (payBountyToBank) {
                                                        coinMessage += " to your bank account.";
                                                    } else {
                                                        coinMessage += ".";
                                                    }
                                                    Debug("Sending message...");
                                                    thisPlayer.getCommunicator().sendSafeServerMessage(coinMessage);
                                                    Debug("CheckCoinBounty finished.");
                                                    return true;
                                                }
                                                else {
                                                    thisPlayer.getCommunicator().sendNormalServerMessage("There are apparently no coins in the coffers to pay out a bounty at the moment.");
                                                }
                                                return false;
                                            }
                                        }
                                        catch (Exception e) {
                                            java.util.logging.Logger.getLogger("org.gotti.wurmunlimited.mods.bountymod.BountyMod").log(Level.SEVERE, "checkCoinBounty encountered Exception:", e);
                                            return false;
                                        }
                                    }
                                };
                            }
                        });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onServerPoll() {
        AshfallMoney.payout();
        WaterRitualHandler.emptyList();
        ArtifactPoller.ArtifactCheckAndBuild();
        ArtifactPoller.ArtifactOwnerPoller();
        ArtifactPoller.ArtifactCallOut();
        ArtifactPoller.ArtifactDisappear();
        ArtifactPoller.ArtifactEffectPoller();

    }

    public static long adjustBountyForStatus (Creature victim, long bounty) {
        try {
            byte modtype = ReflectionUtil.getPrivateField(victim.getStatus(), ReflectionUtil.getField(com.wurmonline.server.creatures.CreatureStatus.class, "modtype"));
            if (modtype <= 0) {
                return bounty;
            }
            if (typeModifiers.containsKey(modtype)) {
                bounty = (long)(bounty * typeModifiers.get(modtype));
            }
        } catch (IllegalArgumentException | IllegalAccessException | ClassCastException | NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return bounty;
        }
        return bounty;
    }

}
