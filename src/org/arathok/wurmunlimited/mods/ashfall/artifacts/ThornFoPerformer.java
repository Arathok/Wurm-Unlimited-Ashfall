package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.behaviours.ActionEntry;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class ThornFoPerformer implements ActionPerformer {


    public ActionEntry actionEntryThornFo;

    public ThornFoPerformer() {


        actionEntryThornFo = new ActionEntryBuilder((short) ModActions.getNextActionId(), "draw source", "drawing source", new int[]{
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                36 /* USE SOURCE AND TARGET */,

        }).range(4).build();





        ModActions.registerAction(actionEntryThornFo);
    }

    @Override
    public short getActionId() {
        return actionEntryThornFo.getNumber();
    }
}

