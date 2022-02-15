package org.arathok.wurmunlimited.mods.ashfall.ashpest;



import com.wurmonline.mesh.MeshIO;
import com.wurmonline.server.FailedException;
import com.wurmonline.server.Players;
import com.wurmonline.server.Server;
import com.wurmonline.server.WurmCalendar;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.Config;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class AshpestHandler {

    Ashpest sickPerson;
    public List<Ashpest> sickPeople;
    Item pus;
    Item pustule;

    {
        try {
            pustule = ItemFactory.createItem(AshfallItems.ashPestPustuleId,99.0F,"Ashpest");
            pus = ItemFactory.createItem(AshfallItems.ashPestPusId,99.0F,"Ashpest");
        } catch (FailedException e) {
            e.printStackTrace();
        } catch (NoSuchTemplateException e) {
            e.printStackTrace();
        }
    }







    public void contractThroughWater() {

        Long time = System.currentTimeMillis();
        Player[] players;
        if (Config.waterSickness) {
            players = Players.getInstance().getPlayers();
            for (Player wetPlayer : players)
                if (wetPlayer.isSwimming()) {
                    boolean playerFound = false;
                    Iterator<Ashpest> getSickPeople = sickPeople.iterator();
                    while (getSickPeople.hasNext()) {

                        sickPerson = getSickPeople.next();
                        if (sickPerson.p == wetPlayer) {
                            int index = sickPeople.indexOf(sickPerson);

                            sickPerson.timeToheal=time+172800000L;
                            sickPerson.numberOfPustules++;
                            playerFound = true;
                            wetPlayer.getCommunicator().sendSafeServerMessage("Your Ashpest got worse in the poisonous waters, your body spawns even more pustules, and your existing pustules get aggitated spewing out even more pus!");


                            wetPlayer.getInventory().insertItem(pustule,true);
                            wetPlayer.getInventory().insertItem(pus,true);
                            sickPeople.set(index,sickPerson);



                        }
                        if (!playerFound) {

                            Ashpest newSickplayer = new Ashpest();
                            newSickplayer.p = wetPlayer;
                            newSickplayer.timeOfInfection = time;
                            sickPerson.numberOfPustules=3;

                            wetPlayer.getCommunicator().sendSafeServerMessage("You have contracted Ashpest... you better take care of yourself for a while");

                            newSickplayer.timeToheal=time+172800000L;
                            wetPlayer.getInventory().insertItem(pustule,true);
                            wetPlayer.getInventory().insertItem(pustule,true);
                            wetPlayer.getInventory().insertItem(pustule,true);
                            sickPeople.add(newSickplayer);
                        }
                    }
                }

        }

    }

    public void contractThroughPlayer() {

        Long time = System.currentTimeMillis();
         Iterator<Ashpest> getSickPeople = sickPeople.iterator();
         while (getSickPeople.hasNext()) {
             sickPerson = getSickPeople.next();
             int index = sickPeople.indexOf(sickPerson);
             Creature[] creatures=sickPerson.p.getCurrentTile().getCreatures();
             int basex = sickPerson.p.getCurrentTile().tilex;
             int basey = sickPerson.p.getCurrentTile().tiley;


             Volatile[] Zone = Zones.getTilesSurrounding(basex,basey,true,1);





        }

    }
    public void PusGenerator()
    {
        Long time = System.currentTimeMillis();
        Iterator<Ashpest> getSickPeople = sickPeople.iterator();
        while (getSickPeople.hasNext())
        {
            sickPerson= getSickPeople.next();
            int index =sickPeople.indexOf(sickPerson);
            if(sickPerson.nextPustule<time)
            for (int i = 0; i<sickPerson.numberOfPustules;i++)
            sickPerson.p.getInventory().insertItem(pus,true);
            sickPeople.set(index,sickPerson);


        }

    }

}
