package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;

public class WaterballoonPerformerFish implements ActionPerformer {


    public ActionEntry actionEntryWaterballoonFish;

    public WaterballoonPerformerFish() {


        actionEntryWaterballoonFish = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Let Fish go", "letting go", new int[]{
                6 /* ACTION_TYPE_NOMOVE */,
                48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                37 /* USE SOURCE ONLY */,

        }).range(4).build();


        ModActions.registerAction(actionEntryWaterballoonFish);
    }

    @Override
    public short getActionId() {
        return actionEntryWaterballoonFish.getNumber();
    }

    public static boolean canUse(Creature performer, Item source) {
        return performer.isPlayer() && source.getOwnerId() == performer.getWurmId() && !source.isTraded();
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter)
    {
        return action(action, performer, target, num, counter);
    } // NEEDED OR THE ITEM WILL ONLY ACTIVATE IF YOU HAVE NO ITEM ACTIVE

    @Override
    public boolean action(Action action, Creature performer,  Item target, short num, float counter) {
        if (!target.isFish())
            return propagate(action, ActionPropagation.SERVER_PROPAGATION, ActionPropagation.ACTION_PERFORMER_PROPAGATION);

        if (!canUse(performer, target)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        if (target.isFish() &&!WaterRitualHandler.waterRitualPlayers.containsKey(performer.getWurmId())) {
            performer.getCommunicator().sendSafeServerMessage("you release the Fish into the Water. It feels like Vynora is thanking you. You notice something in your pocket.");
            Item waterToken = null;
            try {
                waterToken = ItemFactory.createItem(EventItems.waterTokenId, 99.0F, "Vynora");
            } catch (FailedException | NoSuchTemplateException e) {
                Ashfall.logger.log(Level.SEVERE, "no item template id found", e);
                e.printStackTrace();
            }
            if (waterToken != null)
                performer.getInventory().insertItem(waterToken);
            WaterRitualHandler.waterRitualPlayers.put(performer.getWurmId(),System.currentTimeMillis());
            Items.destroyItem(target.getWurmId());
            Ashfall.logger.log(Level.INFO,"Player "+performer.getName()+" Got rewarded with a token for releasing a fish");

            }
        else {
            performer.getCommunicator().sendSafeServerMessage("You feel the thankfulness of Vynroa, honoring her Creature, but it seems she wont give you another Token of her gratitude for Today.");
            Items.destroyItem(target.getWurmId());
        }


        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }



}

