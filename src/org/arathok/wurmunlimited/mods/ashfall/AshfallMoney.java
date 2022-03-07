package org.arathok.wurmunlimited.mods.ashfall;

import com.wurmonline.server.Players;
import com.wurmonline.server.players.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class AshfallMoney {
    static HashMap<Player,Long> nextSalary = new HashMap<>();
    static long nextPoll;
    int salaryCopper=5;
    static long time=0;
    public static void payout() {

        time = System.currentTimeMillis();
        if (time > nextPoll) {
            Player[] players = Players.getInstance().getPlayers();
            if (players != null || players.length > 0) {

                for (Player onePlayer : players) {
                    if (!nextSalary.containsKey(onePlayer)) {
                        nextSalary.put(onePlayer, time + 120000);
                        Ashfall.logger.log(Level.INFO,"Added Player "+onePlayer.getName()+" to the list!");
                    }
                    else

                    if (!onePlayer.isLoggedOut() && onePlayer.isActiveInChat() &&  nextSalary.get(onePlayer) < time) {


                        try {
                            onePlayer.addMoney(500);
                            onePlayer.getCommunicator().sendSafeServerMessage("Thank you for Playing on Ashfall! The Wesirs send you their regards in hoping you will restore this world to its former glory. You are bestowed a salary of 5 copper coins!");
                            Ashfall.logger.log(Level.INFO,"Payed out player "+onePlayer.getName()+ "their daily salary");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        nextSalary.replace(onePlayer, time + 86400000);
                    }


                }


            }
            nextPoll = time + 120000;
        }
    }

}
