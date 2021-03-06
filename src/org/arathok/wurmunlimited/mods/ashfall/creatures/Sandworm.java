package org.arathok.wurmunlimited.mods.ashfall.creatures;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.bodys.BodyTemplate;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.combat.ArmourTypes;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.CreatureTemplateIds;
import com.wurmonline.server.creatures.NoSuchCreatureTemplateException;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.Materials;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.CreatureTypes;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;
import org.gotti.wurmunlimited.modsupport.CreatureTemplateBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.EncounterBuilder;
import org.gotti.wurmunlimited.modsupport.creatures.ModCreature;

public class Sandworm implements ModCreature {


    public static int templateId;

    @Override
    public CreatureTemplateBuilder createCreateTemplateBuilder() {
        // {C_TYPE_MOVE_LOCAL, C_TYPE_VEHICLE, C_TYPE_ANIMAL, C_TYPE_LEADABLE, C_TYPE_GRAZER, C_TYPE_OMNIVORE, C_TYPE_DOMINATABLE, C_TYPE_AGG_HUMAN, C_TYPE_NON_NEWBIE, C_TYPE_BURNING}; - Hell Horse
        // int[] types = new int[]{7, 6, 13, 3, 29, 39, 60, 61}; - Spider
        int[] types = {
                CreatureTypes.C_TYPE_MOVE_LOCAL,
                CreatureTypes.C_TYPE_AGG_HUMAN,
                CreatureTypes.C_TYPE_HUNTING,
                CreatureTypes.C_TYPE_ANIMAL,
                CreatureTypes.C_TYPE_CARNIVORE,
                CreatureTypes.C_TYPE_NON_NEWBIE

        };

        //public CreatureTemplateBuilder(final String identifier, final String name, final String description,
        //       final String modelName, final int[] types, final byte bodyType, final short vision, final byte sex, final short centimetersHigh, final short centimetersLong, final short centimetersWide,
        //       final String deathSndMale, final String deathSndFemale, final String hitSndMale, final String hitSndFemale,
        //       final float naturalArmour, final float handDam, final float kickDam, final float biteDam, final float headDam, final float breathDam, final float speed, final int moveRate,
        //       final int[] itemsButchered, final int maxHuntDist, final int aggress) {
        CreatureTemplateBuilder builder = new CreatureTemplateBuilder("arathok.creature.Sandworm", "Sandworm", "A large Sandworm. It came into existence after the eruption of the Volcano and is a mess of magical and occult energy.",
                "model.creature.spawn.uttacha", types, BodyTemplate.TYPE_BEAR, (short) 5, (byte) 0, (short) 85, (short) 50, (short) 85,
                "sound.death.uttacha.spawn", "sound.death.uttacha.spawn", "sound.combat.hit.deathcrawler", "sound.combat.hit.deathcrawler",
                0.5f, 8f, 0f, 10.0f, 14.0f, 0.0f, 1.2f, 500,
                new int[]{ItemList.meat, ItemList.heart, AshfallItems.spiceId}, 10, 74, Materials.MATERIAL_MEAT_SNAKE);

        builder.skill(SkillList.BODY_STRENGTH, 30.0f);
        builder.skill(SkillList.BODY_STAMINA, 35.0f);
        builder.skill(SkillList.BODY_CONTROL, 45.0f);
        builder.skill(SkillList.MIND_LOGICAL, 5.0f);
        builder.skill(SkillList.MIND_SPEED, 10.0f);
        builder.skill(SkillList.SOUL_STRENGTH, 34.0f);
        builder.skill(SkillList.SOUL_DEPTH, 3.0f);
        builder.skill(SkillList.WEAPONLESS_FIGHTING, 40.0f);
        builder.skill(SkillList.GROUP_FIGHTING, 30.0f);

        builder.boundsValues(-0.5f, -1.0f, 0.5f, 1.42f);
        builder.handDamString("slam");
        builder.maxAge(100);
        builder.armourType(ArmourTypes.ARMOUR_CLOTH);
        builder.baseCombatRating(18.0f);
        builder.combatDamageType(Wound.TYPE_CRUSH);
        builder.headbuttDamString("bite");

        builder.maxPercentOfCreatures(0.01f);
        builder.maxGroupAttackSize(100);

        templateId = builder.getTemplateId();
        return builder;
    }

    @Override
    public void addEncounters() {
        if (templateId == 0)
            return;

        new EncounterBuilder(Tiles.Tile.TILE_SAND.id)
                .addCreatures(templateId, 1)
                .build(2);


    }
}


