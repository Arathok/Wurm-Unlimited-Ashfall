package org.arathok.wurmunlimited.mods.ashfall.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import java.io.IOException;

public class AshfallItems {

    public static ItemTemplate waningCrescent,thornOfFo, heartOfUttacha, eyeOfValrei, flaskOfVynora;
    public static int waningCrescentId,thornOfFoId, heartOfUttachaId, eyeOfValreiId, flaskOfVynoraId;

    private static void registerWaningCrescent() throws IOException {
        waningCrescent = new ItemTemplateBuilder("arathok.ashfall.libArtifact").name("Waning Crescent", "Waning Crescents",
                        "A scythe from the gods. Libila used to carry it but she lost it when the Volcano erupted.")
                .modelName("model.weapon.scythe.")
                .imageNumber((short) IconConstants.ICON_WEAPON_SCYTHE)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_WEAPON,
                        ItemTypes.ITEM_TYPE_WEAPON_SLASH,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,
                        ItemTypes.ITEM_TYPE_ARTIFACT,

                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(2400).material(Materials.MATERIAL_SERYLL)
                .behaviourType((short) 35) // WEAPON
                .combatDamage(40) // HAMMER OF MAG
                .bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY)
                .primarySkill(SkillList.SCYTHE)
                .difficulty(90) // no hard lock
                .build();

        waningCrescentId = waningCrescent.getTemplateId();

    }
}
