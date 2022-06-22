package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlaskOfVynoraBehaviour implements BehaviourProvider {
    private final List<ActionEntry> embark = Collections.singletonList(new ActionEntry(Actions.EMBARK_DRIVER, "Ride", ""));
    private final List<ActionEntry> disembark = Collections.singletonList(new ActionEntry(Actions.DISEMBARK, "Disembark", ""));
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
        if (target.getTemplateId() == FlaskOfVynora.flaskOfVynoraId) {
            if (MountFlaskOfVynoraPerformer.canUse(performer, target))
                return new ArrayList<>(embark);
            else if (DismountFlaskOfVynoraPerformer.canUse(performer, target))
                return new ArrayList<>(disembark);
        }
        return null;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        return getBehavioursFor(performer, target);
    }
}
