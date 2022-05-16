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




    public WaterballoonBehaviour() {
        this.waterballoonPerformerDoll = new WaterballoonPerformerDoll();
        this.waterballoondoll = Collections.singletonList(waterballoonPerformerDoll.actionEntryWaterballoon);
        ModActions.registerActionPerformer(waterballoonPerformerDoll);




    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source.getTemplateId() == EventItems.waterballoonId&&target.getTemplateId()== ItemList.practiceDoll&& WaterRitualHook.waterRitualRunning) {
            if (WaterballoonPerformerDoll.canUse(performer, source))
                return new ArrayList<>(waterballoondoll);

        }
        return null;
    }


}
