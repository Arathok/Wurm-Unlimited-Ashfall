package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.logging.Level;

public class Hook {
   ClassPool classPool = HookManager.getInstance().getClassPool();
   CtClass ctFish;

    {
        try {
            ctFish = classPool.getCtClass("com.wurmonline.server.behaviours.Fish");
            //static boolean fish(Creature performer, Item source, int tilex, int tiley, int tile, float counter, Action act) {
            ctFish.getMethod("fish","(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;IIIFcom.wurmonline.server.behaviours.Action;)B")
                    .insertAfter("");

        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.WARNING,"No such class",e);
            e.printStackTrace();
        }
    }

}
