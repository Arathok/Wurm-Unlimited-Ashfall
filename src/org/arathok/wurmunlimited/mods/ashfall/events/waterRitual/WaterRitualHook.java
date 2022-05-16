package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.logging.Level;

public class WaterRitualHook {
  public static ClassPool classPool = HookManager.getInstance().getClassPool();
  public static CtClass ctFish;
  public static boolean waterRitualRunning = false;

   public static void insertStuff()
    {
        Ashfall.logger.log(Level.INFO,"Hooking WaterRitualFishcode into base Game");
        try {
            ctFish = classPool.getCtClass("com.wurmonline.server.behaviours.Fish");
            //static boolean fish(Creature performer, Item source, int tilex, int tiley, int tile, float counter, Action act) {
            ctFish.getMethod("getFishFor","(Lcom/wurmonline/server/creatures/Creature;SSIILcom/wurmonline/server/items/Item;)S")
                    .insertAfter("org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.WaterRitualHook.waterTokenInsert(performer);");

        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.WARNING,"No such class",e);
            e.printStackTrace();
        } catch (CannotCompileException e) {
            Ashfall.logger.log(Level.SEVERE,"Could not Compile the injection code",e);
            e.printStackTrace();
        }
    }
    public static void waterTokenInsert(Creature performer) {
        if (waterRitualRunning) {
            Item waterToken;
            try {
                waterToken = ItemFactory.createItem(EventItems.waterTokenId, 99, "Vynora");
                Ashfall.logger.log(Level.INFO,"Checking if Player will get a Token...");
                System.console().printf("Ashfall: Checking if Player will get a Token from fishing...");
                if (Server.rand.nextInt(10) <= 11 ) {
                    if (!WaterRitualHandler.waterRitualPlayers.containsKey(performer.getWurmId()))
                    {
                    performer.getInventory().insertItem(waterToken);
                    System.console().printf("Ashfall: Player "+performer.getName()+ "got A token through Fishing");
                    WaterRitualHandler.waterRitualPlayers.put(performer.getWurmId(),System.currentTimeMillis());
                    performer.getCommunicator().sendSafeServerMessage("You find a shiny token has been attached to your Fishing Rod!");
                    }
                    else
                        performer.getCommunicator().sendSafeServerMessage("You feel like you should have fished a WaterToken but maybe you run out of Luck for today.");

            }

            } catch (FailedException e) {
                Ashfall.logger.log(Level.WARNING, "No such item template ID", e);
                e.printStackTrace();
            } catch (NoSuchTemplateException e) {
                e.printStackTrace();
            }

        }
    }
}
