package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FishBehaviour implements BehaviourProvider {


    private final List<ActionEntry> releaseFish;
    private final WaterballoonPerformerFish waterballoonPerformerFish;


    public FishBehaviour() {

        this.waterballoonPerformerFish = new WaterballoonPerformerFish();
        this.releaseFish = Collections.singletonList(waterballoonPerformerFish.actionEntryWaterballoonFish);
        ModActions.registerActionPerformer(waterballoonPerformerFish);



    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target ) {
        if (target.isFish() &&WaterRitualHook.waterRitualRunning&&performer.getPositionZ()<0.15) {
            if (WaterballoonPerformerFish.canUse(performer, target))
                return new ArrayList<>(releaseFish);

        }
        return null;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        return getBehavioursFor(performer, target);
    }
}
