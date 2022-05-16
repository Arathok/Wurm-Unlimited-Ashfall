package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.logging.Level;

public class HookInjection {

    public static void inject() {
        Item waterToken = null;
        try {
            waterToken = ItemFactory.createItem(EventItems.waterTokenId, 99.0F, null);
        } catch (FailedException e) {
            e.printStackTrace();
        } catch (NoSuchTemplateException e) {
            e.printStackTrace();
        }
    }


    }


