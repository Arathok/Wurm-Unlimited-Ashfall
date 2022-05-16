package org.arathok.wurmunlimited.mods.ashfall.events.waterRitual;

import java.util.HashMap;
import java.util.Set;

public class WaterRitualHandler {

    public static HashMap<Long, Long> waterRitualPlayers=new HashMap<Long, Long>();
    public static long time;
    public static long timer=0;

    public static void emptyList() {
        time = System.currentTimeMillis();
        if (timer < time) {
            Set<Long> playersSet = waterRitualPlayers.keySet();
            for (Long oneId : playersSet) {
                if (waterRitualPlayers.containsKey(oneId)) {
                    if (time - 86400000 > waterRitualPlayers.get(oneId))
                        waterRitualPlayers.remove(oneId);
                }
            }
        }
        timer=time+1800000;
    }
}
