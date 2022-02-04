package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.apache.commons.codec.language.bm.Rule;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleBehaviour implements BehaviourProvider {

    private final List<ActionEntry> read;
    private final List<ActionEntry> howto;
    private final RulePerformer rulePerformer;
    private final RulePerformerHowTo rulePerformerHowTo;

    public RuleBehaviour() {
        this.rulePerformer = new RulePerformer();
        this.rulePerformerHowTo = new RulePerformerHowTo();
        this.read = Collections.singletonList(rulePerformer.actionEntry);
        ModActions.registerActionPerformer(rulePerformer);

        this.howto = Collections.singletonList(rulePerformerHowTo.actionEntry);
        ModActions.registerActionPerformer(rulePerformerHowTo);

    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {

        if (target.getTemplateId() == SenetItems.senetRulesId) {
            if (rulePerformer.canUse(performer, target))
                return new ArrayList<>(read);
        }
        if (target.getTemplateId() == SenetItems.senetRulesId) {
            if (rulePerformer.canUse(performer, target))
                return new ArrayList<>(howto);
        }
        return null;
    }
}
