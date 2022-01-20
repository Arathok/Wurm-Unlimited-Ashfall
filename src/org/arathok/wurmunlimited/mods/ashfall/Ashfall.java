package org.arathok.wurmunlimited.mods.ashfall; // HELLO GITHUB!

import com.wurmonline.server.creatures.Communicator;

import javassist.CannotCompileException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.zones.SpawnTable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ashfall implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener{
    public static final Logger logger = Logger.getLogger("Ashfall");



    @Override
    public void configure (Properties properties) {


    }


    @Override
    public void preInit() {
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
        }
    }
        @Override
    public boolean onPlayerMessage(Communicator arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onItemTemplatesCreated() {






        // TODO Auto-generated method stub

    }

    @Override
    public void onServerStarted() {
        // TODO Auto-generated method stub
        AshfallCreatures.createCreatureTemplates();

    }



    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onServerPoll() {


    }


}
