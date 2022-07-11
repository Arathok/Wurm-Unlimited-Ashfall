package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import java.io.IOException;

public class SenetItems {


        public static ItemTemplate senetBoardSimple, senetBoardAdvanced, senetFigurineWR, senetFigurineWB, senetFigurineWG, senetFigurineBR, senetFigurineBG, senetFigurineBB, senetDiceBlack,senetDiceWhite,senetRules;
        public static int senetBoardSimpleId, senetBoardAdvancedId, senetFigurineWRId, senetFigurineWBId, senetFigurineWGId, senetFigurineBRId, senetFigurineBGId, senetFigurineBBId, senetDiceBlackId,senetDiceWhiteId,senetRulesId;

    private static void registerSenetRules() throws IOException {
        senetRules = new ItemTemplateBuilder("arathok.ashfall.senet.rules").name("Senet Rules", "Rules",
                        "Senet Rules")
                .modelName("model.ashfall.senet.rules")
                .imageNumber((short) IconConstants.ICON_RESO_FENCEBARS)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_WOOD,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(200).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                
                .build();

        senetRulesId = senetRules.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.SMITHING_BLACKSMITHING, ItemList.paperSheet, ItemList.dyeBlack, senetRulesId, true, true, 0f, false, false,0,5, CreationCategories.TOYS);


    }

    private static void registerSenetDiceBlack() throws IOException {
        senetDiceBlack = new ItemTemplateBuilder("arathok.ashfall.senet.dice.black").name("Senet black dice instrument", "Dice instruments",
                        "One of two Senet dice instruments. This one is colored black .")
                .modelName("model.ashfall.senet.dice.black")
                .imageNumber((short) IconConstants.ICON_RESO_FENCEBARS)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(2000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                .difficulty(90) // no hard lock
                .build();

        senetDiceBlackId = senetDiceBlack.getTemplateId();

        CreationEntryCreator.createAdvancedEntry(SkillList.SMITHING_BLACKSMITHING, ItemList.opal, ItemList.fenceBars, senetDiceBlackId, true, true, 0f, false, false,0,5, CreationCategories.TOYS)
        .addRequirement(new CreationRequirement(1, ItemList.dyeWhite, 2, true))
        .addRequirement(new CreationRequirement(2, ItemList.dyeBlack, 2, true));

    }

    private static void registerSenetDiceWhite() throws IOException {
        senetDiceWhite = new ItemTemplateBuilder("arathok.ashfall.senet.dice.white").name("Senet white dice instrument", "Dice instruments",
                        "One of two Senet dice instruments. This one is colored white .")
                .modelName("model.ashfall.senet.dice.white")
                .imageNumber((short) IconConstants.ICON_RESO_FENCEBARS)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(2000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                .difficulty(90) // no hard lock
                .build();

        senetDiceWhiteId = senetDiceWhite.getTemplateId();

        CreationEntryCreator.createAdvancedEntry(SkillList.SMITHING_BLACKSMITHING, ItemList.diamond, ItemList.fenceBars, senetDiceWhiteId, true, true, 0f, false, false,0,5, CreationCategories.TOYS)
                .addRequirement(new CreationRequirement(1, ItemList.dyeWhite, 2, true))
                .addRequirement(new CreationRequirement(2, ItemList.dyeBlack, 2, true));

    }
        
        private static void registerSenetBoardSimple() throws IOException {
            senetBoardSimple = new ItemTemplateBuilder("arathok.ashfall.senet.boardSimple").name("Simple Senet Board", "Simple Senet Boards",
                            "A simple Senet game board. It is Played with 3 black and 3 white stones plus a black and a white dice. Maybe you can get a Toymaker to write the rules down for you.")
                    .modelName("model.ashfall.senet.boardSimple.")
                    .imageNumber((short) IconConstants.ICON_SPICE_NUTMEG)
                    .itemTypes(new short[]{
                            ItemTypes.ITEM_TYPE_NAMED,
                            ItemTypes.ITEM_TYPE_REPAIRABLE,
                            ItemTypes.ITEM_TYPE_WOOD,


                    })

                    .decayTime(9072000L)
                    .dimensions(12000, 500, 100)
                    .weightGrams(20000)
                    .behaviourType((short) 1) // ITEM
                    .build();

            senetBoardSimpleId = senetBoardSimple.getTemplateId();
            CreationEntryCreator.createAdvancedEntry(SkillList.TOYMAKING, ItemList.shaft, ItemList.clothYard, senetBoardSimpleId, true, false, 0f, false, false,0,5, CreationCategories.TOYS)
                    .addRequirement(new CreationRequirement(1, ItemList.dyeRed, 2, true))
                    .addRequirement(new CreationRequirement(2, ItemList.dyeGreen, 2, true))
                    .addRequirement(new CreationRequirement(3, ItemList.dyeBlue, 2, true))
                    .addRequirement(new CreationRequirement(4, ItemList.dyeBlue, 2, true))
                    .addRequirement(new CreationRequirement(5, ItemList.dyeWhite, 12, true))
                    .addRequirement(new CreationRequirement(6, ItemList.clothYard,23, true))
                    .addRequirement(new CreationRequirement(7, ItemList.shaft, 29, true))
                    .addRequirement(new CreationRequirement(8, ItemList.nailsIronSmall, 15, true));
        }

        private static void registerSenetBoardAdvanced() throws IOException {
            senetBoardAdvanced = new ItemTemplateBuilder("arathok.ashfall.senet.boardAdvanced").name("Advanced Senet Board", "Advanced Senet Boards",
                            "An advanced Senet game board. Instead of 16 fields it has 30 fields each player must pass with enhanced rules. Maybe you can get a Toymaker to write the rules down for you.")
                    .modelName("model.ashfall.senet.boardAdvanced.")
                    .imageNumber((short) IconConstants.ICON_SPICE_NUTMEG)
                    .itemTypes(new short[]{
                            ItemTypes.ITEM_TYPE_NAMED,
                            ItemTypes.ITEM_TYPE_REPAIRABLE,
                            ItemTypes.ITEM_TYPE_WOOD,


                    })

                    .decayTime(9072000L)
                    .dimensions(12000, 500, 100)
                    .weightGrams(30000)
                    .behaviourType((short) 1) // ITEM
                    .build();

            senetBoardAdvancedId = senetBoardAdvanced.getTemplateId();

            CreationEntryCreator.createAdvancedEntry(SkillList.TOYMAKING, ItemList.shaft, ItemList.clothYard, senetBoardAdvancedId, true, false, 0f, false, false,0,5, CreationCategories.TOYS)
                    .addRequirement(new CreationRequirement(1, ItemList.dyeRed, 2, true))
                    .addRequirement(new CreationRequirement(2, ItemList.dyeGreen, 2, true))
                    .addRequirement(new CreationRequirement(3, ItemList.dyeBlue, 2, true))
                    .addRequirement(new CreationRequirement(4, ItemList.dyeBlue, 2, true))
                    .addRequirement(new CreationRequirement(5, ItemList.dyeWhite, 15, true))
                    .addRequirement(new CreationRequirement(6, ItemList.clothYard,29, true))
                    .addRequirement(new CreationRequirement(7, ItemList.shaft, 39, true))
                    .addRequirement(new CreationRequirement(8, ItemList.nailsIronSmall, 20, true));
        }

        private static void registerSenetFigurineWR() throws IOException {
            senetFigurineWR = new ItemTemplateBuilder("arathok.ashfall.senet.figWR").name("Senet white figurine with ruby head", "Figurines",
                            "One of six Senet game figurines. This one is colored white and has a Ruby head.")
                    .modelName("model.ashfall.senet.figurine.wr")
                    .imageNumber((short) IconConstants.ICON_DECO_MARBLE_BRAZIER_PILLAR)
                    .itemTypes(new short[]{

                            ItemTypes.ITEM_TYPE_NAMED,
                            ItemTypes.ITEM_TYPE_REPAIRABLE,
                            ItemTypes.ITEM_TYPE_STONE,


                    })
                    .decayTime(Long.MAX_VALUE)
                    .dimensions(5, 10, 80)
                    .weightGrams(10000).material(Materials.MATERIAL_MARBLE)
                    .behaviourType((short) 1) // ITEM



                    .difficulty(90) // no hard lock
                    .build();

            senetFigurineWRId = senetFigurineWR.getTemplateId();

            CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.ruby, ItemList.marbleBrick, senetFigurineWRId, true, true, 0f, false, false,0,5, CreationCategories.TOYS);

        }

    private static void registerSenetFigurineWG() throws IOException {
        senetFigurineWG = new ItemTemplateBuilder("arathok.ashfall.senet.figWG").name("Senet white figurine with emerald head", "Figurines",
                        "One of six Senet game figurines. This one is colored white and has an emerald head.")
                .modelName("model.ashfall.senet.figurine.wg")
                .imageNumber((short) IconConstants.ICON_DECO_MARBLE_BRAZIER_PILLAR)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(10000).material(Materials.MATERIAL_MARBLE)
                .behaviourType((short) 1) // ITEM


                .difficulty(90) // no hard lock
                .build();

        senetFigurineWGId = senetFigurineWG.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.emerald, ItemList.marbleBrick, senetFigurineWGId, true, true, 0f, false, false, 0, 5, CreationCategories.TOYS);

    }

    private static void registerSenetFigurineWB() throws IOException {
        senetFigurineWB = new ItemTemplateBuilder("arathok.ashfall.senet.figWB").name("Senet white figurine with sapphire head", "Figurines",
                        "One of six Senet game figurines. This one is colored white and has a sapphire head.")
                .modelName("model.ashfall.senet.figurine.wb")
                .imageNumber((short) IconConstants.ICON_DECO_MARBLE_BRAZIER_PILLAR)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(10000).material(Materials.MATERIAL_MARBLE)
                .behaviourType((short) 1) // ITEM


                .difficulty(90) // no hard lock
                .build();

        senetFigurineWBId = senetFigurineWB.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.sapphire, ItemList.marbleBrick, senetFigurineWBId, true, true, 0f, false, false, 0, 5, CreationCategories.TOYS);

    }

    private static void registerSenetFigurineBR() throws IOException {
        senetFigurineBR = new ItemTemplateBuilder("arathok.ashfall.senet.figBR").name("Senet black figurine with ruby head", "Figurines",
                        "One of six Senet game figurines. This one is colored black and has a ruby head.")
                .modelName("model.ashfall.senet.figurine.br.")
                .imageNumber((short) IconConstants.ICON_FRAGMENT_SLATE)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(10000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                .difficulty(90) // no hard lock
                .build();

        senetFigurineBRId = senetFigurineBR.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.ruby, ItemList.slateBrick, senetFigurineBRId, true, true, 0f, false, false,0,5, CreationCategories.TOYS);

    }

    private static void registerSenetFigurineBG() throws IOException {
        senetFigurineBG = new ItemTemplateBuilder("arathok.ashfall.senet.figBG").name("Senet black figurine with emerald head", "Figurines",
                        "One of six Senet game figurines. This one is colored black and has an emerald head.")
                .modelName("model.ashfall.senet.figurine.bg")
                .imageNumber((short) IconConstants.ICON_FRAGMENT_SLATE)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(10000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                .difficulty(90) // no hard lock
                .build();

        senetFigurineBGId = senetFigurineBG.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.emerald, ItemList.slateBrick, senetFigurineBGId, true, true, 0f, false, false,0,5, CreationCategories.TOYS);

    }

    private static void registerSenetFigurineBB() throws IOException {
        senetFigurineBB = new ItemTemplateBuilder("arathok.ashfall.senet.figBB").name("Senet black figurine with sapphire head", "Figurines",
                        "One of six Senet game figurines. This one is colored black and has a sapphire head.")
                .modelName("model.ashfall.senet.figurine.bb")
                .imageNumber((short) IconConstants.ICON_FRAGMENT_SLATE)
                .itemTypes(new short[]{

                        ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_REPAIRABLE,
                        ItemTypes.ITEM_TYPE_STONE,


                })
                .decayTime(Long.MAX_VALUE)
                .dimensions(5, 10, 80)
                .weightGrams(10000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1) // ITEM



                .difficulty(90) // no hard lock
                .build();

        senetFigurineBBId = senetFigurineBB.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.TOYMAKING, ItemList.sapphire, ItemList.slateBrick, senetFigurineBBId, true, true, 0f, false, false,0,5, CreationCategories.TOYS);

    }


        public static void register() throws IOException {

            registerSenetRules();
            registerSenetDiceBlack();
            registerSenetDiceWhite();
            registerSenetBoardSimple();
            registerSenetBoardAdvanced();
            registerSenetFigurineWR();
            registerSenetFigurineWG();
            registerSenetFigurineWB();
            registerSenetFigurineBR();
            registerSenetFigurineBG();
            registerSenetFigurineBB();
        }

    }


