package org.arathok.wurmunlimited.mods.ashfall;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.zones.Encounter;
import com.wurmonline.server.zones.EncounterType;

public class SpawnOverride {
    private static Encounter mkEncounter(int template, int number) {
        Encounter e = new Encounter();
        e.addType(template, number);
        return e;
    }


    public static void spawnOverrideHook(EncounterType enc) {

        if (enc.getTiletype() == Tiles.Tile.TILE_SAND.id) {
            enc.addEncounter(mkEncounter(36001, 1), 1);

        }
    }
}
