package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class RulePerformerHowTo implements ActionPerformer{


        public ActionEntry actionEntry;

        public RulePerformerHowTo() {


            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "how to setup a senet game", "reading setup information for a game", new int[]{
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
            performer.getCommunicator().sendBml(800,800,true,true,"" +
                    "For any Senet game there should be the following items present: \n- A black dice instrument \n- a white dice instrument\n- a senet board\n- 3 white Senet figurines (each 1 r,g,b)\n- 3 black Senet figurines (each 1 r,g,b)\n" +
                    "\nTo start an unautomated game stand more than one field apart and right click your dices select --roll dice-- . Take your turns per rules" +
                    "\n\nTo start an automated game place the board, align it to the tiles, and place your figurines on the according colored tiles."+
                    "\n\nDecide whoever player begins possibly through some normal dice rolls as described above, then stand on the same field as the other player, and equip your dice instruments in your hands"+
                    "\nThen the player who is taking their turn first needs to right click their dice instrument and select --start automated game-- Then the players separate and the player who is going first does their first dice roll "+
                    "\nYou can then walk around while taking turns with your dice instrument"+
                    "\n\nAfter rolling a prompt in the Event window will tell you to pick a color. Put ''r'', ''g'' or ''b'' into local chat. Your according Figurine will be taking its turn."+
                    "\nIf the figurine does not land on a special field the next turn is the opposing players. They will get a prompt in their event window."+
                    "\n\n If there are no turns taken in 3 minutes the game will abort, and the player whos turn it was will lose."

                    ,250,250,250,"How to setup a Senet game:");
            return propagate(action,
                    ActionPropagation.FINISH_ACTION,
                    ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
    }


