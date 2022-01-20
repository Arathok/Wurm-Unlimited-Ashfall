package org.arathok.wurmunlimited.mods.ashfall;

import com.wurmonline.server.combat.ArmourTemplate;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.skills.Skills;
import com.wurmonline.server.skills.SkillsFactory;

import java.io.IOException;
import java.util.logging.Level;

public class AshfallCreatures
{

    public static void createCreatureTemplates() {
        Ashfall.logger.info("Starting to create Creature Templates");
        long start = System.currentTimeMillis();
            createCreatureTemplate(36001,"Sandworm","Sandworms","A sandworm of Ashfall. These creatures appeared shortly after the Volcano erupted. Now they are commonly found in the deserts.");
            //createCreatureTemplate(114, "NPC Wagoner", "NPC Wagoners", "A relatively normal person stands here waiting to help transport bulk goods.");
            //createCreatureTemplate(115, "Wagon Creature", "Wagon Creatures", "The wagon creature is only used for hauling a wagoner's wagon.");

        long end = System.currentTimeMillis();
       Ashfall.logger.info("Creating Creature Templates took " + (end - start) + " ms");
    }

    public static void createCreatureTemplate(int id, String name, String plural, String longDesc) {
        Skills skills = SkillsFactory.createSkills(name);
        try {
            skills.learnTemp(102, 20.0F);
            skills.learnTemp(104, 20.0F);
            skills.learnTemp(103, 20.0F);
            skills.learnTemp(100, 20.0F);
            skills.learnTemp(101, 20.0F);
            skills.learnTemp(105, 20.0F);
            skills.learnTemp(106, 20.0F);
            if (id == 36001) {
                createSandwormTemplate(id, name, plural, longDesc, skills);

            } else if (Ashfall.logger.isLoggable(Level.FINE)) {
                Ashfall.logger.fine("Using standard creature skills and characteristics for template id: " + id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void createSandwormTemplate(int id, String name, String plural, String longDesc, Skills skills) throws IOException {
        skills.learnTemp(102, 15.0F);
        skills.learnTemp(104, 15.0F);
        skills.learnTemp(103, 50.0F);
        skills.learnTemp(100, 3.0F);
        skills.learnTemp(101, 10.0F);
        skills.learnTemp(105, 4.0F);
        skills.learnTemp(106, 2.0F);
        skills.learnTemp(10052, 30.0F);
        int[] types = { 7, 41, 25, 13, 3, 29, 36, 39 };
        int biteDamage = 15;
        CreatureTemplate temp = CreatureTemplateFactory.getInstance().createCreatureTemplate(36001,"Sandworm", "Sandworms", "A sandworm of Ashfall. These creatures appeared shortly after the Volcano erupted. Now they are commonly found in the deserts.", "model.creature.spawn.uttacha", types, (byte)9, skills, (short)5, (byte)1, (short)40, (short)40, (short)40, "sound.death.uttacha.spawn", "sound.death.uttacha.spawn", "sound.combat.hit.deathcrawler", "sound.combat.hit.deathcrawler", 0.7F, 7.0F, 0.0F, 15.0F, 0.0F, 0.0F, 0.5F, 1500, new int[] { 153, 92,298 }, 10, 34, (byte)81);
        temp.setAlignment(-10.0F);
        temp.setMaxAge(100);
        temp.setArmourType(ArmourTemplate.ARMOUR_TYPE_SCALE);
        temp.setBaseCombatRating(12.0F);
        temp.combatDamageType = 10;
        temp.setMaxGroupAttackSize(8);
        temp.setHandDamString("bite");
        temp.setMaxPercentOfCreatures(0.01F);
    }



}
