package org.arathok.wurmunlimited.mods.ashfall.events;

import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import java.io.IOException;

public class EventItems {

    private static ItemTemplate waterballoon, waterToken;
    public static int waterballoonId , waterTokenId;


    private static void registerWaterballoon() throws IOException {
        waterballoon = new ItemTemplateBuilder("arathok.ashfall.waterballoon").name("water balloon", "waterballoons",
                        "A round shaped object with a thin membrane filled with water")
                .modelName("model.item.clay.")
                .imageNumber((short) IconConstants.ICON_CLAY_PILE)
                .itemTypes(new short[]{


                        ItemTypes.ITEM_TYPE_NODROP,
                        ItemTypes.ITEM_TYPE_NODISCARD,

                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 5, 1)
                .weightGrams(1000).material(Materials.MATERIAL_LEATHER)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.LEATHERWORKING)
                .difficulty(10) // no hard lock
                .build();

        waterballoonId = waterballoon.getTemplateId();
        CreationEntryCreator.createSimpleEntry(SkillList.LEATHERWORKING, ItemList.leather, ItemList.water,
                waterballoonId, true, true, 0.0f, false, false, CreationCategories.TOYS);
        CreationEntryCreator.createSimpleEntry(SkillList.GROUP_SMITHING_WEAPONSMITHING, ItemList.bladder, ItemList.water,
                waterballoonId, true, true, 0.0f, false, false, CreationCategories.TOYS);
    }

    private static void registerWaterToken() throws IOException {
        waterToken = new ItemTemplateBuilder("arathok.ashfall.waterToken").name("water token", "water tokens",
                        "A coin like token. It has tentacles imprinted on it.")
                .modelName("model.item.coinsilver.")
                .imageNumber((short) IconConstants.ICON_COIN_SILVER)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_NODROP,
                        ItemTypes.ITEM_TYPE_NODISCARD,

                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET,
                        ItemTypes.ITEM_TYPE_NOTRADE,
                        ItemTypes.ITEM_TYPE_NOSELLBACK,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(1, 1, 1)
                .weightGrams(1).material(Materials.MATERIAL_SILVER)
                .behaviourType((short) 1) // Item
                .primarySkill(SkillList.LEATHERWORKING)
                .difficulty(10) // no hard lock
                .build();

        waterTokenId = waterToken.getTemplateId();

    }

    public static void register() throws IOException {

        registerWaterballoon();
        registerWaterToken();


    }
}
