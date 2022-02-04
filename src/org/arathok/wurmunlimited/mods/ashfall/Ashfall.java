package org.arathok.wurmunlimited.mods.ashfall; // HELLO GITHUB!

import com.wurmonline.server.creatures.Communicator;

import org.arathok.wurmunlimited.mods.ashfall.artifacts.ArtifactBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.creatures.Sandworm;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;
import org.arathok.wurmunlimited.mods.ashfall.senet.DiceBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.senet.RuleBehaviour;
import org.arathok.wurmunlimited.mods.ashfall.senet.SenetItems;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreatures;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ashfall implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener{
    public static final Logger logger = Logger.getLogger("Ashfall");



    @Override
    public void configure (Properties properties) {

        Config.waterSickness = Boolean.parseBoolean(properties.getProperty("waterSickness", "true"));
    }


    @Override
    public void preInit() {
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
    }
        @Override
    public boolean onPlayerMessage(Communicator arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onItemTemplatesCreated() {



        try{

            AshfallItems.register();
            SenetItems.register();
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
        logger.log(Level.INFO, "Done with Actions");
    }



    @Override
    public void init() {
        // TODO Auto-generated method stub
        ModCreatures.init();
        ModCreatures.addCreature(new Sandworm());
        logger.log(Level.INFO, "Done with creatures");
    }

    @Override
    public void onServerPoll() {


    }


}
