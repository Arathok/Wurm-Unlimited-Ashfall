package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.behaviours.ActionEntry;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class DiceBehaviour implements BehaviourProvider {

    private final List<ActionEntry> roll;
    private final DicePerformerForFun dicePerformerForFun;

    public DiceBehaviour() {
        this.dicePerformerForFun = new DicePerformerForFun();
        this.roll = Collections.singletonList(dicePerformerForFun.actionEntry);
        ModActions.registerActionPerformer(dicePerformerForFun);

    }
}
