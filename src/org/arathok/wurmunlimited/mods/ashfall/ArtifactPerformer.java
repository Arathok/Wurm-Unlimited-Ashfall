package org.arathok.wurmunlimited.mods.ashfall;

import com.wurmonline.server.behaviours.ActionEntry;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class ArtifactPerformer implements ActionPerformer {

    public ActionEntry actionEntryWaningCrescent;

    public ArtifactPerformer() {


        actionEntryWaningCrescent = new ActionEntryBuilder((short) ModActions.getNextActionId(), "create soul gem", "soul stealing", new int[]{
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                36 /* USE SOURCE AND TARGET */,

        }).range(4).build();

        ModActions.registerAction(actionEntryWaningCrescent);
    }

    @Override
    public short getActionId() {
        return actionEntryWaningCrescent.getNumber();
    }
}

