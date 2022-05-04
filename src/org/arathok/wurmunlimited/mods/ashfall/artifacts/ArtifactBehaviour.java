package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtifactBehaviour implements BehaviourProvider {

        private final List<ActionEntry> soulSteal;
        private final List<ActionEntry> drawSource;
        private final WaningCrescentPerformer waningCrescentPerformer;
        private final ThornFoPerformer thornFoPerformer;

        public ArtifactBehaviour() {
            this.waningCrescentPerformer = new WaningCrescentPerformer();
            this.soulSteal = Collections.singletonList(waningCrescentPerformer.actionEntryWaningCrescent);
            ModActions.registerActionPerformer(waningCrescentPerformer);

            this.thornFoPerformer = new ThornFoPerformer();
            this.drawSource = Collections.singletonList(thornFoPerformer.actionEntryThornFo);
            ModActions.registerActionPerformer(thornFoPerformer);

        }

        //, , , , ,
        //, , , , ;

        @Override
        public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
            if(source.getTemplateId()== AshfallItems.waningCrescentId &&target.isCorpse())
                return new ArrayList<>(soulSteal);
            else
            if(source.getTemplateId()== AshfallItems.thornOfFoId && ((target.isFlower()||target.isHerb())&&target.isFresh())) // NOCH PRO TAG 10 MAL LIMITEN
                return new ArrayList<>(drawSource);
            else
                return null;


        }

    }


