package org.arathok.wurmunlimited.mods.ashfall.gameTweaks.GTRepair;

import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.SkillList;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class GuardTowerRepairPerformer implements ActionPerformer {


    public ActionEntry actionEntryRepairGT;

    public GuardTowerRepairPerformer() {


        actionEntryRepairGT = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Ashfall GT Repair", "repairing", new int[]{
                4,
                25,
                43,
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                37 /* USE SOURCE ONLY */,

        })
                .range(4)
                .build();


        ModActions.registerAction(actionEntryRepairGT);
    }

    @Override
    public short getActionId() {
        return actionEntryRepairGT.getNumber();
    }

    public static boolean canUse(Creature performer, Item target) {
        return performer.isPlayer() && target.isKingdomMarker();
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
        return action(action, performer, target, num, counter);
    } // NEEDED OR THE ITEM WILL ONLY ACTIVATE IF YOU HAVE NO ITEM ACTIVE

    @Override
    public boolean action(Action action, Creature performer, Item target, short num, float counter) {
        int time = 0;
        if (!canUse(performer, target)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        if (counter ==1) {
            try {

                if (performer.getSkills().getSkill(SkillList.REPAIR).getKnowledge() <= 20.0D) {
                    performer.getCommunicator().sendSafeServerMessage("You can not work out how one would repair the intricatly stacked bricks.");
                    return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                }

                if (performer.getSkills().getSkill(SkillList.MASONRY).getKnowledge() <= 20.0D) {
                    performer.getCommunicator().sendSafeServerMessage("You don't even know how these bricks are stacked to begin with. It appears to you that you lack basic knowledge in Masonry");
                    return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                }
                if (performer.getSkills().getSkill(SkillList.REPAIR).getKnowledge() > 20.0D
                        && performer.getSkills().getSkill(SkillList.MASONRY).getKnowledge() > 20.0D) {
                    time = target.getRepairTime(performer);
                    action.setPower(target.getDamage());

                    performer.getCommunicator().sendNormalServerMessage("You start repairing the " + target
                            .getName() + ".");
                    Server.getInstance().broadCastAction(performer
                            .getName() + " starts repairing " + target.getNameWithGenus() + ".", performer, 5);
                    performer.sendActionControl(Actions.actionEntrys[162].getVerbString(), true, time);
                    action.setTimeLeft(time);
                    target.repair(performer, (short) action.getTimeLeft(),target.getDamage());
                }
            } catch (NoSuchSkillException e) {
                e.printStackTrace();
            }
        }
        else if(counter > 1) {
            /*time = target.getRepairTime(performer);
            action.setTimeLeft(time);*/
            action.setPower(target.getDamage());
            target.repair(performer, (short) action.getTimeLeft(), target.getDamage());
            if (target.getDamage() < 1) {
                target.setDamage(0.0f);
                return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
            }
            return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

        }
        return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}






