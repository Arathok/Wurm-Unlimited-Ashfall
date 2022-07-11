package org.arathok.wurmunlimited.mods.ashfall.gameTweaks.FenceConstruction;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.logging.Level;



public class FenceConstructionHook {
    public static ClassPool classPool = HookManager.getInstance().getClassPool();
    public static CtClass ctFence;
    public static boolean waterRitualRunning = false;

    public static void insertStuff() {
        Ashfall.logger.log(Level.INFO, "Hooking New Fence Making into base Game");
        try {
            CtClass ctWallBehaviour = HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.WallBehaviour");

            CtMethod ctAction = ctWallBehaviour.getDeclaredMethod("action");

            ctAction.instrument(new ExprEditor() {
                public void edit(MethodCall methodCall) throws CannotCompileException {
                    if (methodCall.getClassName().equals("Java/Lang/Math") && methodCall.getMethodName().equals("abs")) {
                        methodCall.replace("$_ = 7f;");//$_ is the return value of the method

                    }
                }
            }



            );
            ctWallBehaviour = HookManager.getInstance().getClassPool().getCtClass("com.wurmonline.server.behaviours.WallBehaviour");
            //public boolean action(Action act, Creature performer, Wall wall, short action, float counter) {
            ctWallBehaviour.getMethod("action","(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/structures/Wall;SF)Z")
                    .insertAt(1552,"return false;");
        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.SEVERE,"Class not found!",e);
            throw new HookException(e);

        } catch (CannotCompileException e) {
            Ashfall.logger.log(Level.SEVERE,"Could not compile!",e);
            throw new HookException(e);

        }

    }
}
