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
                    if (methodCall.getClassName().equals("Java.Lang.Math") && methodCall.getMethodName().equals("abs")) {
                        StringBuffer code = new StringBuffer();
                        code.append("if ($1 < 0.0F) \n");
                        code.append("	$1 = $1*-1;\n");
                        code.append("return ($1/2.0F);\n");
                        code.append("}\n");
                        methodCall.replace(code.toString());
                    }
                }
            });
        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.SEVERE,"Class not found!",e);
            throw new HookException(e);

        } catch (CannotCompileException e) {
            Ashfall.logger.log(Level.SEVERE,"Could not compile!",e);
            throw new HookException(e);

        }
        float a=-2.0F;
        Math.abs(a);
    }
}
