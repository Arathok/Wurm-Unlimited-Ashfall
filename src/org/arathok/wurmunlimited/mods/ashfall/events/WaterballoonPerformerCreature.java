package org.arathok.wurmunlimited.mods.ashfall.events;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;

public class WaterballoonPerformerCreature implements ActionPerformer {


    public ActionEntry actionEntryWaterballoon;

    public WaterballoonPerformerCreature() {


        actionEntryWaterballoon = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Throw Waterballoon at Person", "throwing", new int[]{
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                36 /* USE SOURCE AND TARGET */,

        }).range(4).build();


        ModActions.registerAction(actionEntryWaterballoon);
    }

    @Override
    public short getActionId() {
        return actionEntryWaterballoon.getNumber();
    }

    public static boolean canUse(Creature performer, Item source) {
        return performer.isPlayer() && source.getOwnerId() == performer.getWurmId() && !source.isTraded();
    }


    @Override
    public boolean action(Action action, Creature performer, Item source, Creature target, short num, float counter) {
        if (source.getTemplateId() != EventItems.waterballoonId)
            return propagate(action, ActionPropagation.SERVER_PROPAGATION, ActionPropagation.ACTION_PERFORMER_PROPAGATION);

        if (!canUse(performer, source)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        if (target.isPlayer()&&!WaterRitualHandler.waterRitualPlayers.containsKey(performer.getWurmId())) {
            performer.getCommunicator().sendSafeServerMessage("You throw the waterballoon at " + target.getName() + "thus recreating the water ritual. You feel like your pocket got heavier.");
            Items.destroyItem(source.getWurmId());
            Item waterToken = null;
            try {
                waterToken = ItemFactory.createItem(EventItems.waterTokenId, 99.0F, null);
            } catch (FailedException | NoSuchTemplateException e) {
                Ashfall.logger.log(Level.SEVERE, "no item template id found", e);
                e.printStackTrace();
            }
            if (waterToken != null)
                performer.getInventory().insertItem(waterToken);

        }
        else
        {
            performer.getCommunicator().sendAlertServerMessage("you already did the water Ritual for today. "
                    + "You should wait another "
                    + ((((WaterRitualHandler.waterRitualPlayers.get(performer.getWurmId()) + 86400000 - System.currentTimeMillis())) / 3600000) + 1)
                    + " hours");

        }
        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }



}

