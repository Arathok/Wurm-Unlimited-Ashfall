package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaterballoonBehaviour implements BehaviourProvider {
    private final List<ActionEntry> waterballoondoll;
    private final WaterballoonPerformerDoll waterballoonPerformerDoll;

    private final List<ActionEntry> waterballoonperson;
    private final WaterballoonPerformerCreature waterballoonPerformerCreature;


    public WaterballoonBehaviour() {
        this.waterballoonPerformerDoll = new WaterballoonPerformerDoll();
        this.waterballoondoll = Collections.singletonList(waterballoonPerformerDoll.actionEntryWaterballoon);
        ModActions.registerActionPerformer(waterballoonPerformerDoll);
        this.waterballoonPerformerCreature = new WaterballoonPerformerCreature();
        this.waterballoonperson = Collections.singletonList(waterballoonPerformerCreature.actionEntryWaterballoon);
        ModActions.registerActionPerformer(waterballoonPerformerCreature);



    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == EventItems.waterballoonId&&target.getTemplateId()== ItemList.practiceDoll) {
            if (WaterballoonPerformerDoll.canUse(performer, source))
                return new ArrayList<>(waterballoondoll);

        }
        return null;
    }


}
