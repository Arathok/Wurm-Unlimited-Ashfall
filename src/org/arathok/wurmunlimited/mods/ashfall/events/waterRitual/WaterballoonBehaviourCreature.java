package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaterballoonBehaviourCreature implements BehaviourProvider {


    private final List<ActionEntry> waterballoonperson;
    private final WaterballoonPerformerCreature waterballoonPerformerCreature;


    public WaterballoonBehaviourCreature() {

        this.waterballoonPerformerCreature = new WaterballoonPerformerCreature();
        this.waterballoonperson = Collections.singletonList(waterballoonPerformerCreature.actionEntryWaterballoon);
        ModActions.registerActionPerformer(waterballoonPerformerCreature);



    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Creature target) {
        if (source.getTemplateId() == EventItems.waterballoonId&&target.isPlayer()) {
            if (WaterballoonPerformerDoll.canUse(performer, source))
                return new ArrayList<>(waterballoonperson);

        }
        return null;
    }


}
