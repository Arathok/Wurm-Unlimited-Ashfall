package org.arathok.wurmunlimited.mods.ashfall;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtifactBehaviour implements BehaviourProvider {

        private final List<ActionEntry> apply;
        private final ArtifactPerformer artifactPerformer;

        public ArtifactBehaviour() {
            this.artifactPerformer = new ArtifactPerformer();
            this.apply = Collections.singletonList(artifactPerformer.actionEntry);
            ModActions.registerActionPerformer(artifactPerformer);

        }

        //, , , , ,
        //, , , , ;

        @Override
        public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {


            return null;

        }
    }


