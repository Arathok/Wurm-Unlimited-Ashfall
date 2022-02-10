package org.arathok.wurmunlimited.mods.ashfall.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.gotti.wurmunlimited.modsupport.items.ModItems;

import java.io.IOException;

public class AshfallItems {

    public static ItemTemplate waningCrescent, thornOfFo, heartOfUttacha, eyeOfValrei,  spice, essenceOfSea, ashPestPustule, ashPestPus;
    public static int waningCrescentId, thornOfFoId, heartOfUttachaId, eyeOfValreiId,  spiceId, essenceOfSeaId, ashPestPustuleId, ashPestPusId;

    private static void registerSpice() throws IOException {
        spice = new ItemTemplateBuilder("arathok.ashfall.spice").name("Spice", "Spice",
                        "A substance created by the Sandworms. It is said to heighten the Senses. Traders will probably exchange nice things for it.")
                .modelName("model.spice.nutmeg.")
                .imageNumber((short) IconConstants.ICON_SPICE_NUTMEG)
                .itemTypes(new short[]{


                })

                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 5, 5)
                .weightGrams(1000)
                .behaviourType((short) 1) // ITEM
                .build();

        spiceId = spice.getTemplateId();
    }

    private static void registerEssenceOfSea() throws IOException {
        essenceOfSea = new ItemTemplateBuilder("arathok.ashfall.essenceSea").name("Essence of the Sea", "Essence of the Sea",
                        "Essence created by Vynora to give power to all sea creatures.")
                .modelName("model.liquid.water.")
                .imageNumber((short) IconConstants.ICON_LIQUID_WATER)
                .itemTypes(new short[]{
                        ItemTypes.ITEM_TYPE_LIQUID

                })

                .decayTime(Long.MAX_VALUE)
                .dimensions(1, 1, 1)
                .weightGrams(10)
                .behaviourType((short) 1) // ITEM
                .build();

        essenceOfSeaId = essenceOfSea.getTemplateId();
    }

    private static void registerWaningCrescent() throws IOException {
        waningCrescent = new ItemTemplateBuilder("arathok.ashfall.libArtifact").name("Waning Crescent", "Waning Crescents",
                        "A scythe from the gods. Libila used to carry it but she lost it when the Volcano erupted.")
                .modelName("model.weapon.scythe.")
                .imageNumber((short) IconConstants.ICON_WEAPON_SCYTHE)
                .itemTypes(new short[]{

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

    private static void registerThornOfFo() throws IOException {
        thornOfFo = new ItemTemplateBuilder("arathok.ashfall.foArtifact").name("Thorn Of Fo", "Thorn of Fo",
                        "A needle from the gods. Fo used it to sever the ground on Ashfall.")
                .modelName("model.tool.needle.")
                .imageNumber((short) IconConstants.ICON_TOOL_NEEDLE)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_TOOL,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,
                        ItemTypes.ITEM_TYPE_ARTIFACT,

                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 5, 1)
                .weightGrams(10).material(Materials.MATERIAL_SERYLL)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.CLOTHTAILORING)
                .difficulty(90) // no hard lock
                .build();

        thornOfFoId = thornOfFo.getTemplateId();

    }

    private static void registerHeartOfUttacha() throws IOException {
        heartOfUttacha = new ItemTemplateBuilder("arathok.ashfall.uttachaArtifact").name("Heart of Uttacha", "Heart of Uttacha",
                        "The heart of the worm god. Its still beating and is said to give superior regeneration to its user.")
                .modelName("model.item.heart.")
                .imageNumber((short) IconConstants.ICON_HEART)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_TOOL,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,
                        ItemTypes.ITEM_TYPE_ARTIFACT,

                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 5, 1)
                .weightGrams(10).material(Materials.MATERIAL_FLESH)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.GROUP_HEALING)
                .difficulty(90) // no hard lock
                .build();

        heartOfUttachaId = heartOfUttacha.getTemplateId();

    }

    private static void registerEyeOfValrei() throws IOException {
        eyeOfValrei = new ItemTemplateBuilder("arathok.ashfall.valreiArtifact").name("Eye of Valrei", "Eye of Valrei",
                        "The Ever watchfull eye of Valrei. Its the window to the soul and is said to give superior magical energies to its user.")
                .modelName("model.item.valrei.")
                .imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_TOOL,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,
                        ItemTypes.ITEM_TYPE_ARTIFACT,

                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 5, 1)
                .weightGrams(10).material(Materials.MATERIAL_ADAMANTINE)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.GROUP_RELIGION)
                .difficulty(90) // no hard lock
                .build();

        eyeOfValreiId = eyeOfValrei.getTemplateId();

    }



    public static void register() throws IOException {

        registerSpice();
        registerEssenceOfSea();
        registerEyeOfValrei();
        registerHeartOfUttacha();
        registerThornOfFo();
        registerWaningCrescent();


    }

}
