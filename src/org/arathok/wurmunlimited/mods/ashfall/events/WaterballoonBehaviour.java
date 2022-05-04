package org.arathok.wurmunlimited.mods.ashfall.events;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaterballoonBehaviour implements BehaviourProvider {
    private final List<ActionEntry> waterballoon;
    private final WaterballoonPerformerDoll waterballoonPerformerDoll;

    public WaterballoonBehaviour() {
        this.waterballoonPerformerDoll = new WaterballoonPerformerDoll();
        this.waterballoon = Collections.singletonList(waterballoonPerformerDoll.actionEntryWaterballoon);
        ModActions.registerActionPerformer(waterballoonPerformerDoll);



    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == EventItems.waterballoonId) {
            if (WaterballoonPerformerDoll.canUse(performer, target))
                return new ArrayList<>(waterballoon);

        }
        return null;
    }


}
