package org.arathok.wurmunlimited.mods.ashfall.gameTweaks.FenceConstruction;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.structures.Door;
import com.wurmonline.server.structures.Structure;
import com.wurmonline.server.structures.Structures;
import com.wurmonline.server.structures.Wall;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.events.EventItems;
import org.arathok.wurmunlimited.mods.ashfall.events.waterRitual.WaterRitualHandler;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import com.wurmonline.server.behaviours.WallBehaviour;

import java.util.logging.Level;

public class FenceConstructionHook {
  public static ClassPool classPool = HookManager.getInstance().getClassPool();
  public static CtClass ctFence;
  public static boolean waterRitualRunning = false;

   public static void insertStuff()
    {
        Ashfall.logger.log(Level.INFO,"Hooking New Fence Making into base Game");
        try {
            ctFence = classPool.getCtClass("com.wurmonline.server.behaviours.WallBehaviour");
            //static boolean fish(Creature performer, Item source, int tilex, int tiley, int tile, float counter, Action act) {
            ctFence.getMethod("action","(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;Lcom/wurmonline/server/structures/Wall;SF)Z")
                    .setBody("    boolean done = true;\n" +
                            "    Structure structure = Structures.getStructureOrNull(wall.getStructureId());\n" +
                            "    Door door = wall.getDoor();\n" +
                            "    if (act.isBuildHouseWallAction() && performer.isFighting()) {\n" +
                            "      performer.getCommunicator().sendNormalServerMessage(\"You cannot do that while in combat.\");\n" +
                            "      performer.getCommunicator().sendActionResult(false);\n" +
                            "      return true;\n" +
                            "    } \n" +
                            "    if (action == 1) {\n" +
                            "      done = action(act, performer, wall, action, counter);\n" +
                            "    } else {\n" +
                            "      if (action == 647)\n" +
                            "        return modifyWall(performer, source, wall, act, counter); \n" +
                            "      if (action == 683)\n" +
                            "        return action(act, performer, wall, action, counter); \n" +
                            "      if (action == 847 && wall.isPlainStone() && (source\n" +
                            "        .getTemplateId() == 130 || (source.isWand() && performer.getPower() >= 4)))\n" +
                            "        return toggleRenderWall(performer, source, wall, act, counter); \n" +
                            "      if (action == 847 && wall.isPlastered() && (source\n" +
                            "        .getTemplateId() == 1115 || (source.isWand() && performer.getPower() >= 4)))\n" +
                            "        return toggleRenderWall(performer, source, wall, act, counter); \n" +
                            "      if (action == 848 && wall.isLRArch() && (source\n" +
                            "        .getTemplateId() == 1115 || (source.isWand() && performer.getPower() >= 4)))\n" +
                            "        return toggleLeftRightArch(performer, source, wall, act, counter); \n" +
                            "      if (act.isBuildHouseWallAction()) {\n" +
                            "        WallEnum targetWallType = WallEnum.getWallByActionId(action);\n" +
                            "        done = false;\n" +
                            "        String buildString = \"wall\";\n" +
                            "        if (act.isBuildWindowAction()) {\n" +
                            "          buildString = \"window\";\n" +
                            "        } else if (act.isBuildDoorAction()) {\n" +
                            "          buildString = \"door\";\n" +
                            "        } else if (act.isBuildDoubleDoorAction()) {\n" +
                            "          buildString = \"double door\";\n" +
                            "        } else if (act.isBuildArchedWallAction()) {\n" +
                            "          buildString = \"arched wall\";\n" +
                            "        } else if (act.isBuildPortcullisAction()) {\n" +
                            "          buildString = \"portcullis\";\n" +
                            "        } else if (act.isBuildBarredWall()) {\n" +
                            "          buildString = \"barred wall\";\n" +
                            "        } else if (act.isBuildBalcony()) {\n" +
                            "          buildString = \"balcony\";\n" +
                            "        } else if (act.isBuildJetty()) {\n" +
                            "          buildString = \"jetty\";\n" +
                            "        } else if (act.isBuildOriel()) {\n" +
                            "          buildString = \"oriel\";\n" +
                            "        } else if (act.isBuildCanopyDoor()) {\n" +
                            "          buildString = \"canopy\";\n" +
                            "        } \n" +
                            "        int tilex = wall.getStartX();\n" +
                            "        int tiley = wall.getStartY();\n" +
                            "        VolaTile wallTile = null;\n" +
                            "        Wall orig = null;\n" +
                            "        boolean usesRightItem = false;\n" +
                            "        if (MethodsStructure.isCorrectToolForBuilding(performer, source.getTemplateId()) && targetWallType\n" +
                            "          .isCorrectToolForType(source, performer))\n" +
                            "          usesRightItem = true; \n" +
                            "        if (!usesRightItem) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You can't use that.\");\n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        if (structure != null) {\n" +
                            "          boolean hasMarker = StructureBehaviour.hasMarker(tilex, tiley, wall.isOnSurface(), wall.getDir(), wall.getHeight());\n" +
                            "          if (hasMarker && targetWallType\n" +
                            "            .getType() != StructureTypeEnum.ARCHED_LEFT && targetWallType\n" +
                            "            .getType() != StructureTypeEnum.ARCHED_RIGHT && targetWallType\n" +
                            "            .getType() != StructureTypeEnum.ARCHED_T) {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You can't build those over a highway.\");\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          if (wall.getType() == StructureTypeEnum.PLAN && structure.needsDoor())\n" +
                            "            if (!act.isBuildDoorAction() && !act.isBuildDoubleDoorAction() && \n" +
                            "              !act.isBuildArchedWallAction() && !act.isBuildPortcullisAction() && !act.isBuildCanopyDoor()) {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"Houses need at least one door. Build a door first.\");\n" +
                            "              performer.getCommunicator().sendActionResult(false);\n" +
                            "              return true;\n" +
                            "            }  \n" +
                            "        } else {\n" +
                            "          logger.log(Level.WARNING, \"Structure with id \" + wall.getStructureId() + \" not found!\");\n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return done;\n" +
                            "        } \n" +
                            "        for (int xx = 1; xx >= -1; xx--) {\n" +
                            "          for (int yy = 1; yy >= -1; yy--) {\n" +
                            "            try {\n" +
                            "              Zone zone = Zones.getZone(tilex + xx, tiley + yy, performer.isOnSurface());\n" +
                            "              VolaTile tile = zone.getTileOrNull(tilex + xx, tiley + yy);\n" +
                            "              if (tile != null) {\n" +
                            "                Wall[] walls = tile.getWalls();\n" +
                            "                for (int s = 0; s < walls.length; s++) {\n" +
                            "                  if (walls[s].getId() == wall.getId()) {\n" +
                            "                    wallTile = tile;\n" +
                            "                    orig = walls[s];\n" +
                            "                    if (wallTile.getStructure() != null && !wallTile.getStructure().isFinalized()) {\n" +
                            "                      performer.getCommunicator().sendNormalServerMessage(\"You need to finalize the build plan before you start building.\");\n" +
                            "                      performer.getCommunicator().sendActionResult(false);\n" +
                            "                      return done;\n" +
                            "                    } \n" +
                            "                    break;\n" +
                            "                  } \n" +
                            "                } \n" +
                            "              } \n" +
                            "            } catch (NoSuchZoneException noSuchZoneException) {}\n" +
                            "          } \n" +
                            "        } \n" +
                            "        if (orig == null) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"No structure is planned there at the moment.\");\n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        if (orig.isFinished()) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You need to destroy the \" + orig\n" +
                            "              .getName() + \" before modifying it.\");\n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        if (!MethodsStructure.mayModifyStructure(performer, structure, wallTile, action)) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You need permission in order to make modifications to this structure.\");\n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        StructureMaterialEnum material = targetWallType.getMaterial();\n" +
                            "        StructureTypeEnum actionType = targetWallType.getType();\n" +
                            "        int primskillTemplate = targetWallType.getSkillNumber();\n" +
                            "        if (StructureStateEnum.INITIALIZED != wall.getState() && StructureStateEnum.FINISHED != wall.getState()) {\n" +
                            "          if (material != wall.getMaterial())\n" +
                            "            if (source.getTemplateId() == 176 && WurmPermissions.mayUseGMWand(performer)) {\n" +
                            "              material = wall.getMaterial();\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You use the power of your \" + source\n" +
                            "                  .getName() + \" to change the material of the wall!\");\n" +
                            "            } else {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You may not change the material of the wall now that you are building it.\");\n" +
                            "              performer.getCommunicator().sendActionResult(false);\n" +
                            "              return true;\n" +
                            "            }  \n" +
                            "          if (wall.getType() != actionType)\n" +
                            "            if (source.getTemplateId() == 176 && WurmPermissions.mayUseGMWand(performer)) {\n" +
                            "              wall.setType(actionType);\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You use the power of your \" + source\n" +
                            "                  .getName() + \" to change the structure of the wall!\");\n" +
                            "            } else {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You may not change the type of wall now that you are building it.\");\n" +
                            "              performer.getCommunicator().sendActionResult(false);\n" +
                            "              return true;\n" +
                            "            }  \n" +
                            "        } else if (StructureStateEnum.INITIALIZED == wall.getState()) {\n" +
                            "          wall.setMaterial(material);\n" +
                            "        } \n" +
                            "        Skill carpentry = null;\n" +
                            "        Skill hammer = null;\n" +
                            "        try {\n" +
                            "          carpentry = performer.getSkills().getSkill(primskillTemplate);\n" +
                            "          if (primskillTemplate == 1013)\n" +
                            "            if (carpentry.getKnowledge(0.0D) < 30.0D) {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You need at least 30 masonry to build stone house walls.\");\n" +
                            "              performer.getCommunicator().sendActionResult(false);\n" +
                            "              return true;\n" +
                            "            }  \n" +
                            "        } catch (NoSuchSkillException nss) {\n" +
                            "          if (primskillTemplate == 1013) {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You need at least 30 masonry to build stone house walls.\");\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          carpentry = performer.getSkills().learn(primskillTemplate, 1.0F);\n" +
                            "        } \n" +
                            "        if (FloorBehaviour.getRequiredBuildSkillForFloorLevel(wall.getFloorLevel(), false) > carpentry.getKnowledge(0.0D)) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"Construction of walls is reserved for craftsmen of higher rank than yours.\");\n" +
                            "          if (Servers.localServer.testServer)\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You have \" + carpentry\n" +
                            "                .getKnowledge(0.0D) + \" and need \" + \n" +
                            "                FloorBehaviour.getRequiredBuildSkillForFloorLevel(wall.getFloorLevel(), false)); \n" +
                            "          performer.getCommunicator().sendActionResult(false);\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        try {\n" +
                            "          hammer = performer.getSkills().getSkill(source.getPrimarySkill());\n" +
                            "        } catch (NoSuchSkillException nss) {\n" +
                            "          try {\n" +
                            "            hammer = performer.getSkills().learn(source.getPrimarySkill(), 1.0F);\n" +
                            "          } catch (NoSuchSkillException noSuchSkillException) {}\n" +
                            "        } \n" +
                            "        int time = 10;\n" +
                            "        double bonus = 0.0D;\n" +
                            "        StructureTypeEnum oldType = orig.getType();\n" +
                            "        boolean immediate = ((source.getTemplateId() == 176 && WurmPermissions.mayUseGMWand(performer)) || (source.getTemplateId() == 315 && performer.getPower() >= 2 && Servers.isThisATestServer()));\n" +
                            "        if (oldType == actionType)\n" +
                            "          if (orig.isFinished()) {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"The wall is finished already.\");\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          }  \n" +
                            "        if (counter == 1.0F && !immediate) {\n" +
                            "          time = Actions.getSlowActionTime(performer, carpentry, source, 0.0D);\n" +
                            "          if (checkWallItem2(performer, wall, buildString, time, act)) {\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          act.setTimeLeft(time);\n" +
                            "          if (oldType == actionType) {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You continue to build a \" + buildString + \".\");\n" +
                            "            Server.getInstance().broadCastAction(performer\n" +
                            "                .getName() + \" continues to build a \" + buildString + \".\", performer, 5);\n" +
                            "          } \n" +
                            "          performer.sendActionControl(\"Building \" + buildString, true, time);\n" +
                            "          performer.getStatus().modifyStamina(-1000.0F);\n" +
                            "          if (source.getTemplateId() == 63) {\n" +
                            "            source.setDamage(source.getDamage() + 0.0015F * source.getDamageModifier());\n" +
                            "          } else if (source.getTemplateId() == 62) {\n" +
                            "            source.setDamage(source.getDamage() + 3.0E-4F * source.getDamageModifier());\n" +
                            "          } else if (source.getTemplateId() == 493) {\n" +
                            "            source.setDamage(source.getDamage() + 5.0E-4F * source.getDamageModifier());\n" +
                            "          } \n" +
                            "        } else {\n" +
                            "          time = act.getTimeLeft();\n" +
                            "          if (Math.abs(performer.getPosX() - (wall.getEndX() << 2)) > 24.0F || Math.abs(performer.getPosX() - (wall.getStartX() << 2)) > 24.0F || \n" +
                            "            Math.abs(performer.getPosY() - (wall.getEndY() << 2)) > 24.0F || Math.abs(performer.getPosY() - (wall.getStartY() << 2)) > 24.0F) {\n" +
                            "            performer.getCommunicator().sendAlertServerMessage(\"You are too far from the end.\");\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          if (act.currentSecond() % 5 == 0) {\n" +
                            "            if (wall.isStone() || wall.isPlainStone() || wall.isSlate() || wall.isRoundedStone() || wall\n" +
                            "              .isPottery() || wall.isSandstone() || wall.isMarble()) {\n" +
                            "              SoundPlayer.playSound(\"sound.work.masonry\", tilex, tiley, performer.isOnSurface(), 1.6F);\n" +
                            "            } else {\n" +
                            "              SoundPlayer.playSound((Server.rand.nextInt(2) == 0) ? \"sound.work.carpentry.mallet1\" : \"sound.work.carpentry.mallet2\", tilex, tiley, performer\n" +
                            "                  .isOnSurface(), 1.6F);\n" +
                            "            } \n" +
                            "            performer.getStatus().modifyStamina(-10000.0F);\n" +
                            "            if (source.getTemplateId() == 63) {\n" +
                            "              source.setDamage(source.getDamage() + 0.0015F * source.getDamageModifier());\n" +
                            "            } else if (source.getTemplateId() == 62) {\n" +
                            "              source.setDamage(source.getDamage() + 3.0E-4F * source.getDamageModifier());\n" +
                            "            } else if (source.getTemplateId() == 493) {\n" +
                            "              source.setDamage(source.getDamage() + 5.0E-4F * source.getDamageModifier());\n" +
                            "            } \n" +
                            "          } \n" +
                            "        } \n" +
                            "        if (counter * 10.0F > time || immediate) {\n" +
                            "          if (!immediate && !depleteWallItems2(performer, wall, act)) {\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          if (hammer != null) {\n" +
                            "            hammer.skillCheck(10.0D, source, 0.0D, false, counter);\n" +
                            "            bonus = hammer.getKnowledge(source, 0.0D) / 10.0D;\n" +
                            "          } \n" +
                            "          carpentry.skillCheck(10.0D, source, bonus, false, counter);\n" +
                            "          done = true;\n" +
                            "          try {\n" +
                            "            float oldql = wall.getQualityLevel();\n" +
                            "            float qlevel = MethodsStructure.calculateNewQualityLevel(act.getPower(), carpentry.getKnowledge(0.0D), oldql, \n" +
                            "                (wall.getFinalState()).state);\n" +
                            "            qlevel = Math.max(1.0F, qlevel);\n" +
                            "            if (immediate)\n" +
                            "              qlevel = 50.0F; \n" +
                            "            boolean updateOrig = false;\n" +
                            "            if (oldType != actionType) {\n" +
                            "              orig.setType(actionType);\n" +
                            "              orig.setDamage(0.0F);\n" +
                            "              qlevel = MethodsStructure.calculateNewQualityLevel(act.getPower(), carpentry.getKnowledge(0.0D), 0.0F, \n" +
                            "                  (wall.getFinalState()).state);\n" +
                            "              orig.setState(StructureStateEnum.INITIALIZED);\n" +
                            "              updateOrig = true;\n" +
                            "            } \n" +
                            "            StructureStateEnum oldState = orig.getState();\n" +
                            "            StructureStateEnum state = oldState;\n" +
                            "            if (state.state < Byte.MAX_VALUE) {\n" +
                            "              state = StructureStateEnum.getStateByValue((byte)(state.state + 1));\n" +
                            "              if (WurmPermissions.mayUseGMWand(performer) && (source\n" +
                            "                .getTemplateId() == 315 || source.getTemplateId() == 176) && \n" +
                            "                Servers.isThisATestServer() == true) {\n" +
                            "                state = StructureStateEnum.FINISHED;\n" +
                            "                qlevel = 80.0F;\n" +
                            "              } else if (performer.getPower() >= 4 && source.getTemplateId() == 176) {\n" +
                            "                state = StructureStateEnum.FINISHED;\n" +
                            "                qlevel = 80.0F;\n" +
                            "              } \n" +
                            "            } \n" +
                            "            orig.setState(state);\n" +
                            "            orig.setQualityLevel(qlevel);\n" +
                            "            orig.setDamage(0.0F);\n" +
                            "            orig.setMaterial(material);\n" +
                            "            if (updateOrig || orig.isFinished()) {\n" +
                            "              wallTile.updateWall(orig);\n" +
                            "              if (performer.getDeity() != null && (performer.getDeity()).number == 3)\n" +
                            "                performer.maybeModifyAlignment(1.0F); \n" +
                            "              if (wall.isFinished() && (wall.isStone() || wall.isPlainStone()))\n" +
                            "                performer.achievement(525); \n" +
                            "              if (wall.isFinished() && wall.getFloorLevel() == 1)\n" +
                            "                performer.achievement(539); \n" +
                            "            } \n" +
                            "            if (orig.isFinished()) {\n" +
                            "              performer.getCommunicator().sendRemoveFromCreationWindow(orig.getId());\n" +
                            "            } else {\n" +
                            "              performer.getCommunicator().sendAddWallToCreationWindow(wall, orig.getId());\n" +
                            "            } \n" +
                            "            if (wall.isHalfArch() && oldState == StructureStateEnum.INITIALIZED) {\n" +
                            "              String beam = (wall.isWood() || wall.isTimberFramed()) ? \"a beam\" : \"an iron bar\";\n" +
                            "              Server.getInstance().broadCastAction(performer.getName() + \" add \" + beam + \" as reinforcement to the arch.\", performer, 5);\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You add \" + beam + \" as reinforcement to the arch.\");\n" +
                            "            } else if (wall.isWood()) {\n" +
                            "              Server.getInstance().broadCastAction(performer.getName() + \" nails a plank to the wall.\", performer, 5);\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You nail a plank to the wall.\");\n" +
                            "            } else if (wall.isTimberFramed()) {\n" +
                            "              if (state.state < 7) {\n" +
                            "                Server.getInstance().broadCastAction(performer.getName() + \" affixes a beam to the frame.\", performer, 5);\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"You affix a beam to the frame.\");\n" +
                            "              } else if (state.state < 17) {\n" +
                            "                Server.getInstance().broadCastAction(performer.getName() + \" adds some clay and mixed grass to the wall.\", performer, 5);\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"You add some clay and mixed grass to the wall.\");\n" +
                            "              } else {\n" +
                            "                Server.getInstance().broadCastAction(performer.getName() + \" reinforces the wall with more clay.\", performer, 5);\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"You reinforce the wall with more clay.\");\n" +
                            "              } \n" +
                            "            } else {\n" +
                            "              String brickType = wall.getBrickName();\n" +
                            "              Server.getInstance().broadCastAction(performer\n" +
                            "                  .getName() + \" adds a \" + brickType + \" and some mortar to the wall.\", performer, 5);\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You add a \" + brickType + \" and some mortar to the wall.\");\n" +
                            "            } \n" +
                            "            performer.getCommunicator().sendActionResult(true);\n" +
                            "            try {\n" +
                            "              orig.save();\n" +
                            "            } catch (IOException iox) {\n" +
                            "              logger.log(Level.WARNING, \"Failed to save wall with id \" + orig.getId());\n" +
                            "            } \n" +
                            "            if ((!structure.isFinished() || !structure.isFinalFinished()) && \n" +
                            "              structure.updateStructureFinishFlag()) {\n" +
                            "              performer.achievement(216);\n" +
                            "              if (!structure.isOnSurface())\n" +
                            "                performer.achievement(571); \n" +
                            "            } \n" +
                            "            if (oldType == StructureTypeEnum.DOOR || oldType == StructureTypeEnum.DOUBLE_DOOR || oldType == StructureTypeEnum.CANOPY_DOOR) {\n" +
                            "              Door[] doors = structure.getAllDoors();\n" +
                            "              for (int x = 0; x < doors.length; x++) {\n" +
                            "                if (doors[x].getWall() == wall) {\n" +
                            "                  structure.removeDoor(doors[x]);\n" +
                            "                  doors[x].removeFromTiles();\n" +
                            "                } \n" +
                            "              } \n" +
                            "            } \n" +
                            "            if ((act.isBuildDoorAction() || act.isBuildDoubleDoorAction() || act.isBuildPortcullisAction() || act\n" +
                            "              .isBuildCanopyDoor()) && orig.isFinished()) {\n" +
                            "              DbDoor dbDoor = new DbDoor(orig);\n" +
                            "              dbDoor.setStructureId(structure.getWurmId());\n" +
                            "              structure.addDoor((Door)dbDoor);\n" +
                            "              dbDoor.setIsManaged(true, (Player)performer);\n" +
                            "              dbDoor.save();\n" +
                            "              dbDoor.addToTiles();\n" +
                            "            } \n" +
                            "          } catch (Exception ex) {\n" +
                            "            logger.log(Level.WARNING, \"Error when building wall:\", ex);\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"An error occured on the server when building wall. Please tell the administrators.\");\n" +
                            "            performer.getCommunicator().sendActionResult(false);\n" +
                            "          } \n" +
                            "        } \n" +
                            "      } else if (action == 58) {\n" +
                            "        int tilex = wall.getTileX();\n" +
                            "        int tiley = wall.getTileY();\n" +
                            "        MethodsStructure.tryToFinalize(performer, tilex, tiley);\n" +
                            "      } else if (action == 57) {\n" +
                            "        if (canRemoveWallPlan(performer, wall)) {\n" +
                            "          wall.destroy();\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You remove a plan for a new wall.\");\n" +
                            "          Server.getInstance().broadCastAction(performer.getName() + \" removes a plan for a new wall.\", performer, 3);\n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"This would cause a section of the structure to crash down since it lacks support.\");\n" +
                            "        } \n" +
                            "      } else if (action == 209) {\n" +
                            "        done = true;\n" +
                            "        if (performer.getCitizenVillage() != null) {\n" +
                            "          if (wall.getTile() != null && \n" +
                            "            wall.getTile().getVillage() != null)\n" +
                            "            if (performer.getCitizenVillage().mayDeclareWarOn(wall.getTile().getVillage())) {\n" +
                            "              Methods.sendWarDeclarationQuestion(performer, wall\n" +
                            "                  .getTile().getVillage());\n" +
                            "            } else {\n" +
                            "              performer.getCommunicator().sendAlertServerMessage(wall\n" +
                            "                  .getTile().getVillage().getName() + \" is already at war with your village.\");\n" +
                            "            }  \n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendAlertServerMessage(\"You are no longer a citizen of a village.\");\n" +
                            "        } \n" +
                            "      } else if (action == 161 && source.isLock() && source.getTemplateId() == 167) {\n" +
                            "        if (source.isLocked()) {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"The \" + source.getName() + \" is already in use.\");\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        if (wall.getType() == StructureTypeEnum.DOOR || wall\n" +
                            "          .getType() == StructureTypeEnum.DOUBLE_DOOR || wall\n" +
                            "          .getType() == StructureTypeEnum.PORTCULLIS || wall\n" +
                            "          .getType() == StructureTypeEnum.CANOPY_DOOR) {\n" +
                            "          done = false;\n" +
                            "          Skill carpentry = null;\n" +
                            "          try {\n" +
                            "            carpentry = performer.getSkills().getSkill(1005);\n" +
                            "          } catch (NoSuchSkillException nss) {\n" +
                            "            carpentry = performer.getSkills().learn(1005, 1.0F);\n" +
                            "          } \n" +
                            "          int time = 10;\n" +
                            "          if (counter == 1.0F) {\n" +
                            "            if (structure != null) {\n" +
                            "              if (!structure.mayModify(performer))\n" +
                            "                return true; \n" +
                            "            } else {\n" +
                            "              logger.log(Level.WARNING, \"This wall has no structure: \" + wall.getId());\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"This wall has a problem with its data. Please report this.\");\n" +
                            "            } \n" +
                            "            time = (int)Math.max(100.0D, (100.0D - carpentry.getKnowledge(source, 0.0D)) * 3.0D);\n" +
                            "            try {\n" +
                            "              performer.getCurrentAction().setTimeLeft(time);\n" +
                            "            } catch (NoSuchActionException nsa) {\n" +
                            "              logger.log(Level.INFO, \"This action does not exist?\", (Throwable)nsa);\n" +
                            "            } \n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You start to attach the lock.\");\n" +
                            "            Server.getInstance().broadCastAction(performer.getName() + \" starts to attach a lock.\", performer, 5);\n" +
                            "            performer.sendActionControl(Actions.actionEntrys[161].getVerbString(), true, time);\n" +
                            "          } else {\n" +
                            "            try {\n" +
                            "              time = performer.getCurrentAction().getTimeLeft();\n" +
                            "            } catch (NoSuchActionException nsa) {\n" +
                            "              logger.log(Level.INFO, \"This action does not exist?\", (Throwable)nsa);\n" +
                            "            } \n" +
                            "            if (counter * 10.0F > time) {\n" +
                            "              carpentry.skillCheck((100.0F - source.getCurrentQualityLevel()), 0.0D, false, counter);\n" +
                            "              done = true;\n" +
                            "              if (structure != null) {\n" +
                            "                long parentId = source.getParentId();\n" +
                            "                if (parentId != -10L) {\n" +
                            "                  try {\n" +
                            "                    Items.getItem(parentId).dropItem(source.getWurmId(), false);\n" +
                            "                  } catch (NoSuchItemException nsi) {\n" +
                            "                    logger.log(Level.INFO, performer.getName() + \" tried to attach nonexistant lock or lock with no parent.\");\n" +
                            "                  } \n" +
                            "                } else {\n" +
                            "                  logger.log(Level.INFO, performer.getName() + \" tried to attach lock with no parent.\");\n" +
                            "                  performer.getCommunicator().sendNormalServerMessage(\"You may not use that lock.\");\n" +
                            "                } \n" +
                            "                source.addKey(structure.getWritId());\n" +
                            "                Door[] doors = structure.getAllDoors();\n" +
                            "                for (int x = 0; x < doors.length; x++) {\n" +
                            "                  try {\n" +
                            "                    if (doors[x].getWall() == wall) {\n" +
                            "                      if (!doors[x].isNotLockable()) {\n" +
                            "                        try {\n" +
                            "                          Item oldlock = doors[x].getLock();\n" +
                            "                          oldlock.removeKey(structure.getWritId());\n" +
                            "                          oldlock.unlock();\n" +
                            "                          performer.getInventory().insertItem(oldlock);\n" +
                            "                        } catch (NoSuchLockException noSuchLockException) {}\n" +
                            "                        doors[x].setLock(source.getWurmId());\n" +
                            "                        source.lock();\n" +
                            "                        PermissionsHistories.addHistoryEntry(doors[x].getWurmId(), System.currentTimeMillis(), performer\n" +
                            "                            .getWurmId(), performer.getName(), \"Attached lock to door\");\n" +
                            "                        Server.getInstance().broadCastAction(performer.getName() + \" attaches the lock.\", performer, 5);\n" +
                            "                        performer.getCommunicator().sendNormalServerMessage(\"You attach the lock and lock the door.\");\n" +
                            "                      } \n" +
                            "                      break;\n" +
                            "                    } \n" +
                            "                  } catch (NoSuchWallException nsw) {\n" +
                            "                    logger.log(Level.WARNING, \"No inner wall\");\n" +
                            "                  } \n" +
                            "                } \n" +
                            "              } else {\n" +
                            "                logger.log(Level.WARNING, \"This wall has no structure: \" + wall.getId());\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"This wall has a problem with its data. Please report this.\");\n" +
                            "              } \n" +
                            "            } \n" +
                            "          } \n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You can only attach locks to doors and fence gates.\");\n" +
                            "        } \n" +
                            "      } else if (action == 101) {\n" +
                            "        if (wall.isOnPvPServer() || Servers.isThisATestServer())\n" +
                            "          if (wall.getType() == StructureTypeEnum.DOOR || wall\n" +
                            "            .getType() == StructureTypeEnum.DOUBLE_DOOR || wall\n" +
                            "            .getType() == StructureTypeEnum.PORTCULLIS || wall\n" +
                            "            .getType() == StructureTypeEnum.CANOPY_DOOR)\n" +
                            "            if (wall.isFinished() && !wall.isNotLockpickable())\n" +
                            "              if (structure != null) {\n" +
                            "                Door[] doors = structure.getAllDoors();\n" +
                            "                for (int x = 0; x < doors.length; x++) {\n" +
                            "                  try {\n" +
                            "                    if (doors[x].getWall() == wall) {\n" +
                            "                      done = false;\n" +
                            "                      done = MethodsStructure.picklock(performer, source, doors[x], wall.getName(), counter, act);\n" +
                            "                      break;\n" +
                            "                    } \n" +
                            "                  } catch (NoSuchWallException nsw) {\n" +
                            "                    logger.log(Level.WARNING, \"No inner wall\");\n" +
                            "                  } \n" +
                            "                } \n" +
                            "              } else {\n" +
                            "                logger.log(Level.WARNING, \"This wall has no structure: \" + wall.getId());\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"This wall has a problem with its data. Please report this.\");\n" +
                            "              }    \n" +
                            "      } else if (action == 193) {\n" +
                            "        if ((!Servers.localServer.challengeServer || performer.getEnemyPresense() <= 0) && \n" +
                            "          !wall.isNoRepair()) {\n" +
                            "          done = MethodsStructure.repairWall(act, performer, source, wall, counter);\n" +
                            "        } else {\n" +
                            "          done = true;\n" +
                            "        } \n" +
                            "      } else if (action == 192) {\n" +
                            "        if (source == null || wall.isNoImprove()) {\n" +
                            "          done = true;\n" +
                            "        } else {\n" +
                            "          done = MethodsStructure.improveWall(act, performer, source, wall, counter);\n" +
                            "        } \n" +
                            "      } else if (action == 180) {\n" +
                            "        if (performer.getPower() >= 2) {\n" +
                            "          performer.getLogger().log(Level.INFO, performer\n" +
                            "              .getName() + \" destroyed a wall at \" + wall.getTileX() + \", \" + wall.getTileY());\n" +
                            "          wall.setDamage(100.0F);\n" +
                            "          done = true;\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You deal a lot of damage to the wall!\");\n" +
                            "        } \n" +
                            "      } else if (action == 174 && !wall.isIndestructible()) {\n" +
                            "        if (!wall.isRubble()) {\n" +
                            "          int tilex = wall.getStartX();\n" +
                            "          int tiley = wall.getStartY();\n" +
                            "          VolaTile wallTile = null;\n" +
                            "          for (int xx = 1; xx >= -1; xx--) {\n" +
                            "            for (int yy = 1; yy >= -1; yy--) {\n" +
                            "              try {\n" +
                            "                Zone zone = Zones.getZone(tilex + xx, tiley + yy, wall.isOnSurface());\n" +
                            "                VolaTile tile = zone.getTileOrNull(tilex + xx, tiley + yy);\n" +
                            "                if (tile != null) {\n" +
                            "                  Wall[] walls = tile.getWalls();\n" +
                            "                  for (int s = 0; s < walls.length; s++) {\n" +
                            "                    if (walls[s].getId() == wall.getId()) {\n" +
                            "                      wallTile = tile;\n" +
                            "                      break;\n" +
                            "                    } \n" +
                            "                  } \n" +
                            "                } \n" +
                            "              } catch (NoSuchZoneException noSuchZoneException) {}\n" +
                            "            } \n" +
                            "          } \n" +
                            "          if (wallTile == null) {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You fail to destroy the wall.\");\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          done = MethodsStructure.destroyWall(action, performer, source, wall, false, counter);\n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"The rubble will clear by itself soon.\");\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "      } else if (action == 231) {\n" +
                            "        if (wall.isFinished()) {\n" +
                            "          if (Methods.isActionAllowed(performer, action, wall.getTileX(), wall.getTileY())) {\n" +
                            "            if (wall.isNotPaintable()) {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"You are not allowed to paint this wall.\");\n" +
                            "              return true;\n" +
                            "            } \n" +
                            "            done = MethodsStructure.colorWall(performer, source, wall, act);\n" +
                            "          } else {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"You are not allowed to paint this wall.\");\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"Finish the wall first.\");\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "      } else if (action == 232) {\n" +
                            "        if (Methods.isActionAllowed(performer, action, wall.getTileX(), wall.getTileY())) {\n" +
                            "          done = MethodsStructure.removeColor(performer, source, wall, act);\n" +
                            "        } else {\n" +
                            "          performer.getCommunicator().sendNormalServerMessage(\"You are not allowed to remove the paint from this wall.\");\n" +
                            "          return true;\n" +
                            "        } \n" +
                            "      } else if (action == 82) {\n" +
                            "        DemolishCheckQuestion dcq = new DemolishCheckQuestion(performer, \"Demolish Building\", \"A word of warning!\", wall.getStructureId());\n" +
                            "        dcq.sendQuestion();\n" +
                            "      } else {\n" +
                            "        if (action == 662) {\n" +
                            "          if (performer.getPower() >= 2) {\n" +
                            "            wall.setIndoor(!wall.isIndoor());\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"Wall toggled and now is \" + (\n" +
                            "                wall.isIndoor() ? \"Inside\" : \"Outside\"));\n" +
                            "            if (structure != null) {\n" +
                            "              structure.updateStructureFinishFlag();\n" +
                            "            } else {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"The structural integrity of the building is at risk.\");\n" +
                            "              logger.log(Level.WARNING, \"Structure not found while trying to toggle a wall at [\" + wall.getStartX() + \",\" + wall\n" +
                            "                  .getStartY() + \"]\");\n" +
                            "            } \n" +
                            "          } \n" +
                            "          return true;\n" +
                            "        } \n" +
                            "        if (action == 78) {\n" +
                            "          if (performer.getPower() >= 2)\n" +
                            "            try {\n" +
                            "              Structure struct = Structures.getStructure(wall.getStructureId());\n" +
                            "              try {\n" +
                            "                Items.getItem(struct.getWritId());\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"Writ item exists for structure.\");\n" +
                            "              } catch (NoSuchItemException nss) {\n" +
                            "                performer.getCommunicator().sendNormalServerMessage(\"Writ item does not exist for structure. Replacing.\");\n" +
                            "                try {\n" +
                            "                  Item newWrit = ItemFactory.createItem(166, 80.0F + Server.rand\n" +
                            "                      .nextFloat() * 20.0F, performer.getName());\n" +
                            "                  newWrit.setDescription(struct.getName());\n" +
                            "                  performer.getInventory().insertItem(newWrit);\n" +
                            "                  struct.setWritid(newWrit.getWurmId(), true);\n" +
                            "                } catch (NoSuchTemplateException nst) {\n" +
                            "                  performer.getCommunicator().sendNormalServerMessage(\"Failed replace:\" + nst.getMessage());\n" +
                            "                } catch (FailedException enst) {\n" +
                            "                  performer.getCommunicator().sendNormalServerMessage(\"Failed replace:\" + enst.getMessage());\n" +
                            "                } \n" +
                            "              } \n" +
                            "            } catch (NoSuchStructureException nss) {\n" +
                            "              logger.log(Level.WARNING, nss.getMessage(), (Throwable)nss);\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"No such structure. Bug. Good luck.\");\n" +
                            "            }  \n" +
                            "        } else if (action == 472) {\n" +
                            "          done = true;\n" +
                            "          if (source.getTemplateId() == 676 && source.getOwnerId() == performer.getWurmId()) {\n" +
                            "            MissionManager m = new MissionManager(performer, \"Manage missions\", \"Select action\", wall.getId(), wall.getName(), source.getWurmId());\n" +
                            "            m.sendQuestion();\n" +
                            "          } \n" +
                            "        } else if (action == 90) {\n" +
                            "          if (performer.getPower() < 4) {\n" +
                            "            logger.log(Level.WARNING, \"Possible hack attempt by \" + performer.getName() + \" calling Actions.POLL on wall in WallBehaviour without enough power.\");\n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          int tilex = wall.getStartX();\n" +
                            "          int tiley = wall.getStartY();\n" +
                            "          VolaTile wallTile = Zones.getOrCreateTile(tilex, tiley, true);\n" +
                            "          if (wallTile != null) {\n" +
                            "            Structure struct = null;\n" +
                            "            try {\n" +
                            "              struct = Structures.getStructure(wall.getStructureId());\n" +
                            "            } catch (NoSuchStructureException e) {\n" +
                            "              logger.log(Level.WARNING, e.getMessage(), (Throwable)e);\n" +
                            "            } \n" +
                            "            if (struct == null) {\n" +
                            "              performer.getCommunicator().sendNormalServerMessage(\"Couldn't find structure for wall '\" + wall\n" +
                            "                  .getId() + \"'.\");\n" +
                            "              return true;\n" +
                            "            } \n" +
                            "            wall.poll(struct.getCreationDate() + 604800000L, wallTile, struct);\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"Poll performed for wall '\" + wall.getId() + \"'.\");\n" +
                            "          } else {\n" +
                            "            performer.getCommunicator().sendNormalServerMessage(\"Unexpectedly missing a tile for \" + tilex + \",\" + tiley + \".\");\n" +
                            "          } \n" +
                            "        } else if (action == 664) {\n" +
                            "          manageBuilding(performer, structure, wall);\n" +
                            "        } else if (action == 666) {\n" +
                            "          manageDoor(performer, structure, wall);\n" +
                            "        } else if (action == 673) {\n" +
                            "          manageAllDoors(performer, structure, wall);\n" +
                            "        } else if (action == 102 && mayLockDoor(performer, wall, door)) {\n" +
                            "          if (door != null && door.hasLock() && door.isLocked() && !door.isNotLockable()) {\n" +
                            "            door.unlock(true);\n" +
                            "            PermissionsHistories.addHistoryEntry(door.getWurmId(), System.currentTimeMillis(), performer\n" +
                            "                .getWurmId(), performer.getName(), \"Unlocked door\");\n" +
                            "          } \n" +
                            "        } else if (action == 28 && mayLockDoor(performer, wall, door)) {\n" +
                            "          if (door != null && door.hasLock() && !door.isLocked() && !door.isNotLockable()) {\n" +
                            "            door.lock(true);\n" +
                            "            PermissionsHistories.addHistoryEntry(door.getWurmId(), System.currentTimeMillis(), performer\n" +
                            "                .getWurmId(), performer.getName(), \"Locked door\");\n" +
                            "          } \n" +
                            "        } else {\n" +
                            "          if (action == 866) {\n" +
                            "            if (performer.getPower() >= 4)\n" +
                            "              Methods.sendGmBuildAllWallsQuestion(performer, structure); \n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          if (action == 684) {\n" +
                            "            if ((source.getTemplateId() == 315 || source.getTemplateId() == 176) && performer\n" +
                            "              .getPower() >= 2) {\n" +
                            "              Methods.sendItemRestrictionManagement(performer, (Permissions.IAllow)wall, wall.getId());\n" +
                            "            } else {\n" +
                            "              logger.log(Level.WARNING, performer.getName() + \" hacking the protocol by trying to set the restrictions of \" + wall + \", counter: \" + counter + '!');\n" +
                            "            } \n" +
                            "            return true;\n" +
                            "          } \n" +
                            "          if (source.isTrellis() && (action == 176 || action == 746 || action == 747))\n" +
                            "            done = Terraforming.plantTrellis(performer, source, wall.getMinX(), wall.getMinY(), wall.isOnSurface(), wall.getDir(), action, counter, act); \n" +
                            "        } \n" +
                            "      } \n" +
                            "    } \n" +
                            "    return done;\n" +
                            "  }" +
                            "");


        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.WARNING,"No such class",e);
            e.printStackTrace();
        } catch (CannotCompileException e) {
            Ashfall.logger.log(Level.SEVERE,"Could not Compile the injection code",e);
            e.printStackTrace();
        }
    }

}
