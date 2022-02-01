package org.arathok.wurmunlimited.mods.ashfall.senet;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.items.Materials;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;

import java.io.IOException;

public class SenetItems {


        public static ItemTemplate senetBoardSimple, senetBoardAdvanced, gameFigurineWR, gameFigurineWB, gameFigurineWG, gameFigurineBR, gameFigurineBG, gameFigurineBB, diceBlack,diceWhite,senetRules;
        public static int senetBoardSimpleId, senetBoardAdvancedId, gameFigurineWRId, gameFigurineWBId, gameFigurineWGId, gameFigurineBRId, gameFigurineBGId, gameFigurineBBId, diceBlackId,diceWhiteId,senetRulesId;

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
                    .dimensions(5, 5, 5)
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

        private static void registerFlaskOfVynora() throws IOException {
            flaskOfVynora = new ItemTemplateBuilder("arathok.ashfall.vynoraArtifact").name("Flask of Vynora", "Flask of Vynora",
                            "Fo stole it to create the volcano eruption creating Ashfall. It contains the essence of the Ocean which makes its user swim faster.")
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
                            ItemTypes.ITEM_TYPE_HOLLOW,

                    })
                    .decayTime(Long.MAX_VALUE)
                    .dimensions(10, 10, 10)
                    .weightGrams(10).material(Materials.MATERIAL_ADAMANTINE)
                    .behaviourType((short) 1) // Item
                    .primarySkill(SkillList.GROUP_RELIGION)
                    .difficulty(90) // no hard lock
                    .build();

            flaskOfVynoraId = flaskOfVynora.getTemplateId();

        }

        public static void register() throws IOException {

            registerSpice();
            registerEssenceOfSea();
            registerEyeOfValrei();
            registerHeartOfUttacha();
            registerThornOfFo();
            registerWaningCrescent();
            registerFlaskOfVynora();

        }

    }


