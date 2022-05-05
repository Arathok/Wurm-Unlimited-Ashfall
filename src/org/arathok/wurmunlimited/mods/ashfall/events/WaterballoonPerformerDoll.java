package org.arathok.wurmunlimited.mods.ashfall.events;

import com.wurmonline.server.*;
import com.wurmonline.server.behaviours.*;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.creatures.MountAction;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.artifacts.FlaskOfVynora;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.logging.Level;

public class WaterballoonPerformerDoll implements ActionPerformer {


    public ActionEntry actionEntryWaterballoon;

    public WaterballoonPerformerDoll() {


        actionEntryWaterballoon = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Throw Waterballoon", "throwing", new int[]{
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
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
        if (source.getTemplateId() != EventItems.waterballoonId)
            return propagate(action, ActionPropagation.SERVER_PROPAGATION, ActionPropagation.ACTION_PERFORMER_PROPAGATION);

        if (!canUse(performer, target)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        if (target.getTemplateId() == ItemList.practiceDoll&&!WaterRitualHandler.waterRitualPlayers.containsKey(performer.getWurmId())) {
            boolean playerfound = false;
            VolaTile[] radius = Zones.getTilesSurrounding(performer.getTileX(), performer.getTileY(), true, 79);
            for (VolaTile oneTile : radius) {
                Creature[] potentialPlayer = oneTile.getCreatures();
                for (Creature oneCreature : potentialPlayer)
                    if (oneCreature.isPlayer())
                        playerfound = true;

            }

            if (!playerfound)
                performer.getCommunicator().sendSafeServerMessage("You throw the waterballoon at " + target.getName() + "thus recreating the water ritual. You feel like your pocket got heavier.");
            Item waterToken = null;
            try {
                waterToken = ItemFactory.createItem(EventItems.waterTokenId, 99.0F, null);
            } catch (FailedException | NoSuchTemplateException e) {
                Ashfall.logger.log(Level.SEVERE, "no item template id found", e);
                e.printStackTrace();
            }
            if (waterToken != null)
                performer.getInventory().insertItem(waterToken);
            else {
                performer.getCommunicator().sendAlertServerMessage("You are not alone! There are other players around! Go find them to the ritual");
                WaterRitualHandler.waterRitualPlayers.put(performer.getWurmId(), System.currentTimeMillis());
            }
        }
        else
        {
            performer.getCommunicator().sendAlertServerMessage("you already did the water Ritual for today. "
                    +"You should wait another"
                    +((WaterRitualHandler.waterRitualPlayers.get(performer.getWurmId())+86400000-System.currentTimeMillis())/86400000)+1
                    +"hours");
        }
        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
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
                    +"You should wait another"
                    +((WaterRitualHandler.waterRitualPlayers.get(performer.getWurmId())+86400000-System.currentTimeMillis())/86400000)+1
                    +"hours");

        }
        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION, ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }

}

