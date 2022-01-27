package org.arathok.wurmunlimited.mods.ashfall.ashpest;

import com.wurmonline.server.Players;
import com.wurmonline.server.players.Player;
import org.arathok.wurmunlimited.mods.ashfall.Config;

import java.util.Iterator;
import java.util.List;

public class AshpestHandler {

    Ashpest sickPerson;
    public List<Ashpest> sickPeople;

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
                            sickPerson.numberOfPustules+=1;
                            sickPeople.set(index,sickPerson);
                            playerFound = true;


                        }
                        if (!playerFound) {
                            Ashpest newSickplayer = new Ashpest();
                            newSickplayer.p = wetPlayer;
                            newSickplayer.timeOfInfection = time;
                            newSickplayer.numberOfPustules = 0;
                            sickPeople.add(newSickplayer);
                        }
                    }
                }

        }

    }
}
