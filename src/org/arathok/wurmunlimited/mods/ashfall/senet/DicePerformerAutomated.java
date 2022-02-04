package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class DicePerformerAutomated implements ActionPerformer {

    public ActionEntry actionEntry;

    public DicePerformerAutomated() {


        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "start automated game", "rolling dice", new int[]{
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                35 /* DONT CARE SOURCE AND TARGET */,

        }).range(4).build();

        ModActions.registerAction(actionEntry);
    }


    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
        return action(action, performer, target, num, counter);
    } // NEEDED OR THE ITEM WILL ONLY ACTIVATE IF YOU HAVE NO ITEM ACTIVE

    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item target) {
        return performer.isPlayer() && target.getOwnerId() == performer.getWurmId() && !target.isTraded();
    }

    @Override
    public boolean action(Action action, Creature performer, Item target, short num, float counter) {

        //Alchemy.logger.log(Level.INFO, "BLAH BLAH HE PERFORMS");


        if (!canUse(performer, target)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action,
                    ActionPropagation.FINISH_ACTION,
                    ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);


        }
        performer.getCommunicator().sendAlertServerMessage("You read the rules of Senet");
        performer.getCommunicator().sendBml(800,800,true,true,"To play an unautomated game right click your dice instrument and select, --just roll for fun--." +
                "Otherwise the game will respect the rules of the game and move your figurines automatically. To setup an automated game, place the game board, align it with the world and place the figurines on the according starting fields. then decide who rolls first (for example with two --just for fun rolls). Then have the other player stand on the same tile as you, with the starting player selecting -- roll dice--  "
                ,250,250,250,"Setup instructions:");

        performer.getCommunicator().sendBml(800,800,true,true,"To play an unautomated game right click your dice instrument and select, --just roll for fun--." +
                        "Otherwise the game will respect the rules of the game and move your figurines automatically. To setup an automated game, place the game board, align it with the world and place the figurines on the according starting fields. then decide who rolls first (for example with two --just for fun rolls). Then have the other player stand on the same tile as you, with the starting player selecting -- roll dice--  "
                ,250,250,250,"Setup instructions:");

        return propagate(action,
                ActionPropagation.FINISH_ACTION,
                ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
