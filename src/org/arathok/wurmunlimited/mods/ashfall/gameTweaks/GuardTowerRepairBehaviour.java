package org.arathok.wurmunlimited.mods.ashfall.gameTweaks;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuardTowerRepairBehaviour implements BehaviourProvider {

    private final List<ActionEntry> repairGt;
    private final GuardTowerRepairPerformer repairGTPerformer;




    public GuardTowerRepairBehaviour() {
        this.repairGTPerformer = new GuardTowerRepairPerformer();
        this.repairGt = Collections.singletonList(repairGTPerformer.actionEntryRepairGT);
        ModActions.registerActionPerformer(repairGTPerformer);
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        return getBehavioursFor(performer, target);
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
        if (target.isKingdomMarker()) {
            if (GuardTowerRepairPerformer.canUse(performer, target))
                return new ArrayList<>(repairGt);

        }
        return null;
    }




}
