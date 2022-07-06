package org.arathok.wurmunlimited.mods.ashfall.gameTweaks.FenceConstruction;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.logging.Level;



public class FenceConstructionHook {
    public static ClassPool classPool = HookManager.getInstance().getClassPool();
    public static CtClass ctFence;
    public static boolean waterRitualRunning = false;

    public static void insertStuff() {
        Ashfall.logger.log(Level.INFO, "Hooking New Fence Making into base Game");
        try {
            ctFence = classPool.getCtClass("com.wurmonline.server.behaviours.WallBehaviour");
            //static boolean fish(Creature $2, Item source, int tilex, int tiley, int tile, float counter, Action $1) {
            ctFence.getMethod("action", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;Lcom/wurmonline/server/structures/Wall;SF)Z")
                    .setBody("{ boolean done = true;" +
                            "    com.wurmonline.server.structures.Structure structure = com.wurmonline.server.structures.Structures.getStructureOrNull($4.getStructureId());" +
                            "    com.wurmonline.server.structures.Door door = $4.getDoor();" +
                            "    if ($1.isBuildHouseWallAction() && $2.isFighting()) {" +
                            "      $2.getCommunicator().sendNormalServerMessage(\"You cannot do that while in combat.\");" +
                            "      $2.getCommunicator().sendActionResult(false);" +
                            "      return true;" +
                            "    } " +
                            "    if ($5 == 1) {" +
                            "      done = action($1, $2, $4, $5, $6);" +
                            "    } else {" +
                            "      if ($5 == 647)" +
                            "        return modifyWall($2, $3, $4, $1, $6); " +
                            "      if ($5 == 683)" +
                            "        return $5($1, $2, $4, $5, $6); " +
                            "      if ($5 == 847 && $4.isPlainStone() && ($3" +
                            "        .getTemplateId() == 130 || ($3.isWand() && $2.getPower() >= 4)))" +
                            "        return toggleRenderWall($2, $3, $4, $1, $6); " +
                            "      if ($5 == 847 && $4.isPlastered() && ($3" +
                            "        .getTemplateId() == 1115 || ($3.isWand() && $2.getPower() >= 4)))" +
                            "        return toggleRenderWall($2, $3, $4, $1, $6); " +
                            "      if ($5 == 848 && $4.isLRArch() && ($3" +
                            "        .getTemplateId() == 1115 || ($3.isWand() && $2.getPower() >= 4)))" +
                            "        return toggleLeftRightArch($2, $3, $4, $1, $6); " +
                            "      if ($1.isBuildHouseWallAction()) {" +
                            "        com.wurmonline.server.structures.WallEnum targetWallType = WallEnum.getWallByActionId($5);" +
                            "        done = false;" +
                            "        String buildString = \"$4\";" +
                            "        if ($1.isBuildWindowAction()) {" +
                            "          buildString = \"window\";" +
                            "        } else if ($1.isBuildDoorAction()) {" +
                            "          buildString = \"door\";" +
                            "        } else if ($1.isBuildDoubleDoorAction()) {" +
                            "          buildString = \"double door\";" +
                            "        } else if ($1.isBuildArchedWallAction()) {" +
                            "          buildString = \"arched $4\";" +
                            "        } else if ($1.isBuildPortcullisAction()) {" +
                            "          buildString = \"portcullis\";" +
                            "        } else if ($1.isBuildBarredWall()) {" +
                            "          buildString = \"barred $4\";" +
                            "        } else if ($1.isBuildBalcony()) {" +
                            "          buildString = \"balcony\";" +
                            "        } else if ($1.isBuildJetty()) {" +
                            "          buildString = \"jetty\";" +
                            "        } else if ($1.isBuildOriel()) {" +
                            "          buildString = \"oriel\";" +
                            "        } else if ($1.isBuildCanopyDoor()) {" +
                            "          buildString = \"canopy\";" +
                            "        } " +
                            "        int tilex = $4.getStartX();" +
                            "        int tiley = $4.getStartY();" +
                            "        com.wurmonline.server.zones.VolaTile wallTile = null;" +
                            "        com.wurmonline.server.structures.Wall orig = null;" +
                            "        boolean usesRightItem = false;" +
                            "        if (MethodsStructure.isCorrectToolForBuilding($2, $3.getTemplateId()) && targetWallType" +
                            "          .isCorrectToolForType($3, $2))" +
                            "          usesRightItem = true; " +
                            "        if (!usesRightItem) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You can't use that.\");" +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return true;" +
                            "        } " +
                            "        if (structure != null) {" +
                            "          boolean hasMarker = StructureBehaviour.hasMarker(tilex, tiley, $4.isOnSurface(), $4.getDir(), $4.getHeight());" +
                            "          if (hasMarker && targetWallType" +
                            "            .getType() != StructureTypeEnum.ARCHED_LEFT && targetWallType" +
                            "            .getType() != StructureTypeEnum.ARCHED_RIGHT && targetWallType" +
                            "            .getType() != StructureTypeEnum.ARCHED_T) {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You can't build those over a highway.\");" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          } " +
                            "          if ($4.getType() == StructureTypeEnum.PLAN && structure.needsDoor())" +
                            "            if (!$1.isBuildDoorAction() && !$1.isBuildDoubleDoorAction() && " +
                            "              !$1.isBuildArchedWallAction() && !$1.isBuildPortcullisAction() && !$1.isBuildCanopyDoor()) {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"Houses need at least one door. Build a door first.\");" +
                            "              $2.getCommunicator().sendActionResult(false);" +
                            "              return true;" +
                            "            }  " +
                            "        } else {" +
                            "          logger.log(Level.WARNING, \"Structure with id \" + $4.getStructureId() + \" not found!\");" +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return done;" +
                            "        } " +
                            "        for (int xx = 1; xx >= -1; xx--) {" +
                            "          for (int yy = 1; yy >= -1; yy--) {" +
                            "            try {" +
                            "              com.wurmonline.server.zones.Zone zone = Zones.getZone(tilex + xx, tiley + yy, $2.isOnSurface());" +
                            "              com.wurmonline.server.zonesVolaTile tile = zone.getTileOrNull(tilex + xx, tiley + yy);" +
                            "              if (tile != null) {" +
                            "                com.wurmonline.server.structures.Wall[] walls = tile.getWalls();" +
                            "                for (int s = 0; s < walls.length; s++) {" +
                            "                  if (walls[s].getId() == $4.getId()) {" +
                            "                    wallTile = tile;" +
                            "                    orig = walls[s];" +
                            "                    if (wallTile.getStructure() != null && !wallTile.getStructure().isFinalized()) {" +
                            "                      $2.getCommunicator().sendNormalServerMessage(\"You need to finalize the build plan before you start building.\");" +
                            "                      $2.getCommunicator().sendActionResult(false);" +
                            "                      return done;" +
                            "                    } " +
                            "                    break;" +
                            "                  } " +
                            "                } " +
                            "              } " +
                            "            } catch (com.wurmonline.server.zones.NoSuchZoneException noSuchZoneException) {}" +
                            "          } " +
                            "        } " +
                            "        if (orig == null) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"No structure is planned there at the moment.\");" +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return true;" +
                            "        } " +
                            "        if (orig.isFinished()) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You need to destroy the \" + orig" +
                            "              .getName() + \" before modifying it.\");" +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return true;" +
                            "        } " +
                            "        if (!MethodsStructure.mayModifyStructure($2, structure, wallTile, $5)) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You need permission in order to make modifications to this structure.\");" +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return true;" +
                            "        } " +
                            "        com.wurmonline.shared.constants.StructureMaterialEnum material = targetWallType.getMaterial();" +
                            "        com.wurmonline.shared.constants.StructureTypeEnum actionType = targetWallType.getType();" +
                            "        int primskillTemplate = targetWallType.getSkillNumber();" +
                            "        if (com.wurmonline.shared.constants.StructureStateEnum.INITIALIZED != $4.getState() && com.wurmonline.shared.constants.StructureStateEnum.FINISHED != $4.getState()) {" +
                            "          if (material != $4.getMaterial())" +
                            "            if ($3.getTemplateId() == 176 && WurmPermissions.mayUseGMWand($2)) {" +
                            "              material = $4.getMaterial();" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You use the power of your \" + source" +
                            "                  .getName() + \" to change the material of the $4!\");" +
                            "            } else {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You may not change the material of the $4 now that you are building it.\");" +
                            "              $2.getCommunicator().sendActionResult(false);" +
                            "              return true;" +
                            "            }  " +
                            "          if ($4.getType() != actionType)" +
                            "            if ($3.getTemplateId() == 176 && WurmPermissions.mayUseGMWand($2)) {" +
                            "              $4.setType(actionType);" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You use the power of your \" + source" +
                            "                  .getName() + \" to change the structure of the $4!\");" +
                            "            } else {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You may not change the type of $4 now that you are building it.\");" +
                            "              $2.getCommunicator().sendActionResult(false);" +
                            "              return true;" +
                            "            }  " +
                            "        } else if (StructureStateEnum.INITIALIZED == $4.getState()) {" +
                            "          $4.setMaterial(material);" +
                            "        } " +
                            "        com.wurmonline.server.skills.Skill carpentry = null;" +
                            "        com.wurmonline.server.skills.Skill hammer = null;" +
                            "        try {" +
                            "          carpentry = $2.getSkills().getSkill(primskillTemplate);" +
                            "          if (primskillTemplate == 1013)" +
                            "            if (carpentry.getKnowledge(0.0D) < 30.0D) {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You need at least 30 masonry to build stone house walls.\");" +
                            "              $2.getCommunicator().sendActionResult(false);" +
                            "              return true;" +
                            "            }  " +
                            "        } catch (com.wurmonline.server.skills.NoSuchSkillException nss) {" +
                            "          if (primskillTemplate == 1013) {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You need at least 30 masonry to build stone house walls.\");" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          } " +
                            "          carpentry = $2.getSkills().learn(primskillTemplate, 1.0F);" +
                            "        } " +
                            "        if (com.wurmonline.server.behaviours.FloorBehaviour.getRequiredBuildSkillForFloorLevel($4.getFloorLevel(), false) > carpentry.getKnowledge(0.0D)) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"Construction of walls is reserved for craftsmen of higher rank than yours.\");" +
                            "          if (Servers.localServer.testServer)" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You have \" + carpentry" +
                            "                .getKnowledge(0.0D) + \" and need \" + " +
                            "                FloorBehaviour.getRequiredBuildSkillForFloorLevel($4.getFloorLevel(), false)); " +
                            "          $2.getCommunicator().sendActionResult(false);" +
                            "          return true;" +
                            "        } " +
                            "        try {" +
                            "          hammer = $2.getSkills().getSkill($3.getPrimarySkill());" +
                            "        } catch (com.wurmonline.server.skills.NoSuchSkillException nss) {" +
                            "          try {" +
                            "            hammer = $2.getSkills().learn($3.getPrimarySkill(), 1.0F);" +
                            "          } catch (com.wurmonline.server.skills.NoSuchSkillException noSuchSkillException) {}" +
                            "        } " +
                            "        int time = 10;" +
                            "        double bonus = 0.0D;" +
                            "        com.wurmonline.shared.constants.StructureTypeEnum oldType = orig.getType();" +
                            "        boolean immediate = (($3.getTemplateId() == 176 && com.wurmonline.server.behaviours.WurmPermissions.mayUseGMWand($2)) || ($3.getTemplateId() == 315 && $2.getPower() >= 2 && com.wurmonline.server.Servers.isThisATestServer()));" +
                            "        if (oldType == actionType)" +
                            "          if (orig.isFinished()) {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"The $4 is finished already.\");" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          }  " +
                            "        if ($6 == 1.0F && !immediate) {" +
                            "          time = com.wurmonline.server.behaviours.Actions.getSlowActionTime($2, carpentry, $3, 0.0D);" +
                            "          if (checkWallItem2($2, $4, buildString, time, $1)) {" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          } " +
                            "          $1.setTimeLeft(time);" +
                            "          if (oldType == actionType) {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You continue to build a \" + buildString + \".\");" +
                            "            Server.getInstance().broadCastAction($2" +
                            "                .getName() + \" continues to build a \" + buildString + \".\", $2, 5);" +
                            "          } " +
                            "          $2.sendActionControl(\"Building \" + buildString, true, time);" +
                            "          $2.getStatus().modifyStamina(-1000.0F);" +
                            "          if ($3.getTemplateId() == 63) {" +
                            "            $3.setDamage($3.getDamage() + 0.0015F * $3.getDamageModifier());" +
                            "          } else if ($3.getTemplateId() == 62) {" +
                            "            $3.setDamage($3.getDamage() + 3.0E-4F * $3.getDamageModifier());" +
                            "          } else if ($3.getTemplateId() == 493) {" +
                            "            $3.setDamage($3.getDamage() + 5.0E-4F * $3.getDamageModifier());" +
                            "          } " +
                            "        } else {" +
                            "          time = $1.getTimeLeft();" +
                            "          if (Math.abs($2.getPosX() - ($4.getEndX() << 2)) > 8.0F || Math.abs($2.getPosX() - ($4.getStartX() << 2)) > 8.0F || " +
                            "            Math.abs($2.getPosY() - ($4.getEndY() << 2)) > 8.0F || Math.abs($2.getPosY() - ($4.getStartY() << 2)) > 8.0F) {" +
                            "            $2.getCommunicator().sendAlertServerMessage(\"You are too far from the end.\");" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          } " +
                            "          if ($1.currentSecond() % 5 == 0) {" +
                            "            if ($4.isStone() || $4.isPlainStone() || $4.isSlate() || $4.isRoundedStone() || $4" +
                            "              .isPottery() || $4.isSandstone() || $4.isMarble()) {" +
                            "              SoundPlayer.playSound(\"sound.work.masonry\", tilex, tiley, $2.isOnSurface(), 1.6F);" +
                            "            } else {" +
                            "              SoundPlayer.playSound((Server.rand.nextInt(2) == 0) ? \"sound.work.carpentry.mallet1\" : \"sound.work.carpentry.mallet2\", tilex, tiley, $2" +
                            "                  .isOnSurface(), 1.6F);" +
                            "            } " +
                            "            $2.getStatus().modifyStamina(-10000.0F);" +
                            "            if ($3.getTemplateId() == 63) {" +
                            "              $3.setDamage($3.getDamage() + 0.0015F * $3.getDamageModifier());" +
                            "            } else if ($3.getTemplateId() == 62) {" +
                            "              $3.setDamage($3.getDamage() + 3.0E-4F * $3.getDamageModifier());" +
                            "            } else if ($3.getTemplateId() == 493) {" +
                            "              $3.setDamage($3.getDamage() + 5.0E-4F * $3.getDamageModifier());" +
                            "            } " +
                            "          } " +
                            "        } " +
                            "        if ($6 * 10.0F > time || immediate) {" +
                            "          if (!immediate && !depleteWallItems2($2, $4, $1)) {" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "            return true;" +
                            "          } " +
                            "          if (hammer != null) {" +
                            "            hammer.skillCheck(10.0D, $3, 0.0D, false, $6);" +
                            "            bonus = hammer.getKnowledge($3, 0.0D) / 10.0D;" +
                            "          } " +
                            "          carpentry.skillCheck(10.0D, $3, bonus, false, $6);" +
                            "          done = true;" +
                            "          try {" +
                            "            float oldql = $4.getQualityLevel();" +
                            "            float qlevel = MethodsStructure.calculateNewQualityLevel($1.getPower(), carpentry.getKnowledge(0.0D), oldql, " +
                            "                ($4.getFinalState()).state);" +
                            "            qlevel = Math.max(1.0F, qlevel);" +
                            "            if (immediate)" +
                            "              qlevel = 50.0F; " +
                            "            boolean updateOrig = false;" +
                            "            if (oldType != actionType) {" +
                            "              orig.setType(actionType);" +
                            "              orig.setDamage(0.0F);" +
                            "              qlevel = MethodsStructure.calculateNewQualityLevel($1.getPower(), carpentry.getKnowledge(0.0D), 0.0F, " +
                            "                  ($4.getFinalState()).state);" +
                            "              orig.setState(StructureStateEnum.INITIALIZED);" +
                            "              updateOrig = true;" +
                            "            } " +
                            "            StructureStateEnum oldState = orig.getState();" +
                            "            StructureStateEnum state = oldState;" +
                            "            if (state.state < Byte.MAX_VALUE) {" +
                            "              state = StructureStateEnum.getStateByValue((byte)(state.state + 1));" +
                            "              if (WurmPermissions.mayUseGMWand($2) && ($3" +
                            "                .getTemplateId() == 315 || $3.getTemplateId() == 176) && " +
                            "                Servers.isThisATestServer() == true) {" +
                            "                state = StructureStateEnum.FINISHED;" +
                            "                qlevel = 80.0F;" +
                            "              } else if ($2.getPower() >= 4 && $3.getTemplateId() == 176) {" +
                            "                state = StructureStateEnum.FINISHED;" +
                            "                qlevel = 80.0F;" +
                            "              } " +
                            "            } " +
                            "            orig.setState(state);" +
                            "            orig.setQualityLevel(qlevel);" +
                            "            orig.setDamage(0.0F);" +
                            "            orig.setMaterial(material);" +
                            "            if (updateOrig || orig.isFinished()) {" +
                            "              wallTile.updateWall(orig);" +
                            "              if ($2.getDeity() != null && ($2.getDeity()).number == 3)" +
                            "                $2.maybeModifyAlignment(1.0F); " +
                            "              if ($4.isFinished() && ($4.isStone() || $4.isPlainStone()))" +
                            "                $2.achievement(525); " +
                            "              if ($4.isFinished() && $4.getFloorLevel() == 1)" +
                            "                $2.achievement(539); " +
                            "            } " +
                            "            if (orig.isFinished()) {" +
                            "              $2.getCommunicator().sendRemoveFromCreationWindow(orig.getId());" +
                            "            } else {" +
                            "              $2.getCommunicator().sendAddWallToCreationWindow($4, orig.getId());" +
                            "            } " +
                            "            if ($4.isHalfArch() && oldState == StructureStateEnum.INITIALIZED) {" +
                            "              String beam = ($4.isWood() || $4.isTimberFramed()) ? \"a beam\" : \"an iron bar\";" +
                            "              Server.getInstance().broadCastAction($2.getName() + \" add \" + beam + \" as reinforcement to the arch.\", $2, 5);" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You add \" + beam + \" as reinforcement to the arch.\");" +
                            "            } else if ($4.isWood()) {" +
                            "              Server.getInstance().broadCastAction($2.getName() + \" nails a plank to the $4.\", $2, 5);" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You nail a plank to the $4.\");" +
                            "            } else if ($4.isTimberFramed()) {" +
                            "              if (state.state < 7) {" +
                            "                Server.getInstance().broadCastAction($2.getName() + \" affixes a beam to the frame.\", $2, 5);" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"You affix a beam to the frame.\");" +
                            "              } else if (state.state < 17) {" +
                            "                Server.getInstance().broadCastAction($2.getName() + \" adds some clay and mixed grass to the $4.\", $2, 5);" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"You add some clay and mixed grass to the $4.\");" +
                            "              } else {" +
                            "                Server.getInstance().broadCastAction($2.getName() + \" reinforces the $4 with more clay.\", $2, 5);" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"You reinforce the $4 with more clay.\");" +
                            "              } " +
                            "            } else {" +
                            "              String brickType = $4.getBrickName();" +
                            "              Server.getInstance().broadCastAction($2" +
                            "                  .getName() + \" adds a \" + brickType + \" and some mortar to the $4.\", $2, 5);" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You add a \" + brickType + \" and some mortar to the $4.\");" +
                            "            } " +
                            "            $2.getCommunicator().sendActionResult(true);" +
                            "            try {" +
                            "              orig.save();" +
                            "            } catch (IOException iox) {" +
                            "              logger.log(Level.WARNING, \"Failed to save $4 with id \" + orig.getId());" +
                            "            } " +
                            "            if ((!structure.isFinished() || !structure.isFinalFinished()) && " +
                            "              structure.updateStructureFinishFlag()) {" +
                            "              $2.achievement(216);" +
                            "              if (!structure.isOnSurface())" +
                            "                $2.achievement(571); " +
                            "            } " +
                            "            if (oldType == StructureTypeEnum.DOOR || oldType == StructureTypeEnum.DOUBLE_DOOR || oldType == StructureTypeEnum.CANOPY_DOOR) {" +
                            "              Door[] doors = structure.getAllDoors();" +
                            "              for (int x = 0; x < doors.length; x++) {" +
                            "                if (doors[x].getWall() == $4) {" +
                            "                  structure.removeDoor(doors[x]);" +
                            "                  doors[x].removeFromTiles();" +
                            "                } " +
                            "              } " +
                            "            } " +
                            "            if (($1.isBuildDoorAction() || $1.isBuildDoubleDoorAction() || $1.isBuildPortcullisAction() || $1" +
                            "              .isBuildCanopyDoor()) && orig.isFinished()) {" +
                            "              DbDoor dbDoor = new DbDoor(orig);" +
                            "              dbDoor.setStructureId(structure.getWurmId());" +
                            "              structure.addDoor((Door)dbDoor);" +
                            "              dbDoor.setIsManaged(true, (Player)$2);" +
                            "              dbDoor.save();" +
                            "              dbDoor.addToTiles();" +
                            "            } " +
                            "          } catch (Exception ex) {" +
                            "            logger.log(Level.WARNING, \"Error when building $4:\", ex);" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"An error occured on the server when building $4. Please tell the administrators.\");" +
                            "            $2.getCommunicator().sendActionResult(false);" +
                            "          } " +
                            "        } " +
                            "      } else if ($5 == 58) {" +
                            "        int tilex = $4.getTileX();" +
                            "        int tiley = $4.getTileY();" +
                            "        MethodsStructure.tryToFinalize($2, tilex, tiley);" +
                            "      } else if ($5 == 57) {" +
                            "        if (canRemoveWallPlan($2, $4)) {" +
                            "          $4.destroy();" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You remove a plan for a new $4.\");" +
                            "          Server.getInstance().broadCastAction($2.getName() + \" removes a plan for a new $4.\", $2, 3);" +
                            "        } else {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"This would cause a section of the structure to crash down since it lacks support.\");" +
                            "        } " +
                            "      } else if ($5 == 209) {" +
                            "        done = true;" +
                            "        if ($2.getCitizenVillage() != null) {" +
                            "          if ($4.getTile() != null && " +
                            "            $4.getTile().getVillage() != null)" +
                            "            if ($2.getCitizenVillage().mayDeclareWarOn($4.getTile().getVillage())) {" +
                            "              Methods.sendWarDeclarationQuestion($2, $4" +
                            "                  .getTile().getVillage());" +
                            "            } else {" +
                            "              $2.getCommunicator().sendAlertServerMessage($4" +
                            "                  .getTile().getVillage().getName() + \" is already at war with your village.\");" +
                            "            }  " +
                            "        } else {" +
                            "          $2.getCommunicator().sendAlertServerMessage(\"You are no longer a citizen of a village.\");" +
                            "        } " +
                            "      } else if ($5 == 161 && $3.isLock() && $3.getTemplateId() == 167) {" +
                            "        if ($3.isLocked()) {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"The \" + $3.getName() + \" is already in use.\");" +
                            "          return true;" +
                            "        } " +
                            "        if ($4.getType() == StructureTypeEnum.DOOR || $4" +
                            "          .getType() == StructureTypeEnum.DOUBLE_DOOR || $4" +
                            "          .getType() == StructureTypeEnum.PORTCULLIS || $4" +
                            "          .getType() == StructureTypeEnum.CANOPY_DOOR) {" +
                            "          done = false;" +
                            "          Skill carpentry = null;" +
                            "          try {" +
                            "            carpentry = $2.getSkills().getSkill(1005);" +
                            "          } catch (NoSuchSkillException nss) {" +
                            "            carpentry = $2.getSkills().learn(1005, 1.0F);" +
                            "          } " +
                            "          int time = 10;" +
                            "          if ($6 == 1.0F) {" +
                            "            if (structure != null) {" +
                            "              if (!structure.mayModify($2))" +
                            "                return true; " +
                            "            } else {" +
                            "              logger.log(Level.WARNING, \"This $4 has no structure: \" + $4.getId());" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"This $4 has a problem with its data. Please report this.\");" +
                            "            } " +
                            "            time = (int)Math.max(100.0D, (100.0D - carpentry.getKnowledge($3, 0.0D)) * 3.0D);" +
                            "            try {" +
                            "              $2.getCurrentAction().setTimeLeft(time);" +
                            "            } catch (NoSuchActionException nsa) {" +
                            "              logger.log(Level.INFO, \"This action does not exist?\", (Throwable)nsa);" +
                            "            } " +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You start to attach the lock.\");" +
                            "            Server.getInstance().broadCastAction($2.getName() + \" starts to attach a lock.\", $2, 5);" +
                            "            $2.sendActionControl(Actions.actionEntrys[161].getVerbString(), true, time);" +
                            "          } else {" +
                            "            try {" +
                            "              time = $2.getCurrentAction().getTimeLeft();" +
                            "            } catch (NoSuchActionException nsa) {" +
                            "              logger.log(Level.INFO, \"This action does not exist?\", (Throwable)nsa);" +
                            "            } " +
                            "            if ($6 * 10.0F > time) {" +
                            "              carpentry.skillCheck((100.0F - $3.getCurrentQualityLevel()), 0.0D, false, $6);" +
                            "              done = true;" +
                            "              if (structure != null) {" +
                            "                long parentId = $3.getParentId();" +
                            "                if (parentId != -10L) {" +
                            "                  try {" +
                            "                    Items.getItem(parentId).dropItem($3.getWurmId(), false);" +
                            "                  } catch (NoSuchItemException nsi) {" +
                            "                    logger.log(Level.INFO, $2.getName() + \" tried to attach nonexistant lock or lock with no parent.\");" +
                            "                  } " +
                            "                } else {" +
                            "                  logger.log(Level.INFO, $2.getName() + \" tried to attach lock with no parent.\");" +
                            "                  $2.getCommunicator().sendNormalServerMessage(\"You may not use that lock.\");" +
                            "                } " +
                            "                $3.addKey(structure.getWritId());" +
                            "                Door[] doors = structure.getAllDoors();" +
                            "                for (int x = 0; x < doors.length; x++) {" +
                            "                  try {" +
                            "                    if (doors[x].getWall() == $4) {" +
                            "                      if (!doors[x].isNotLockable()) {" +
                            "                        try {" +
                            "                          Item oldlock = doors[x].getLock();" +
                            "                          oldlock.removeKey(structure.getWritId());" +
                            "                          oldlock.unlock();" +
                            "                          $2.getInventory().insertItem(oldlock);" +
                            "                        } catch (NoSuchLockException noSuchLockException) {}" +
                            "                        doors[x].setLock($3.getWurmId());" +
                            "                        $3.lock();" +
                            "                        PermissionsHistories.addHistoryEntry(doors[x].getWurmId(), System.currentTimeMillis(), $2" +
                            "                            .getWurmId(), $2.getName(), \"Attached lock to door\");" +
                            "                        Server.getInstance().broadCastAction($2.getName() + \" attaches the lock.\", $2, 5);" +
                            "                        $2.getCommunicator().sendNormalServerMessage(\"You attach the lock and lock the door.\");" +
                            "                      } " +
                            "                      break;" +
                            "                    } " +
                            "                  } catch (NoSuchWallException nsw) {" +
                            "                    logger.log(Level.WARNING, \"No inner $4\");" +
                            "                  } " +
                            "                } " +
                            "              } else {" +
                            "                logger.log(Level.WARNING, \"This $4 has no structure: \" + $4.getId());" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"This $4 has a problem with its data. Please report this.\");" +
                            "              } " +
                            "            } " +
                            "          } " +
                            "        } else {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You can only attach locks to doors and fence gates.\");" +
                            "        } " +
                            "      } else if ($5 == 101) {" +
                            "        if ($4.isOnPvPServer() || Servers.isThisATestServer())" +
                            "          if ($4.getType() == StructureTypeEnum.DOOR || $4" +
                            "            .getType() == StructureTypeEnum.DOUBLE_DOOR || $4" +
                            "            .getType() == StructureTypeEnum.PORTCULLIS || $4" +
                            "            .getType() == StructureTypeEnum.CANOPY_DOOR)" +
                            "            if ($4.isFinished() && !$4.isNotLockpickable())" +
                            "              if (structure != null) {" +
                            "                Door[] doors = structure.getAllDoors();" +
                            "                for (int x = 0; x < doors.length; x++) {" +
                            "                  try {" +
                            "                    if (doors[x].getWall() == $4) {" +
                            "                      done = false;" +
                            "                      done = MethodsStructure.picklock($2, $3, doors[x], $4.getName(), $6, $1);" +
                            "                      break;" +
                            "                    } " +
                            "                  } catch (NoSuchWallException nsw) {" +
                            "                    logger.log(Level.WARNING, \"No inner $4\");" +
                            "                  } " +
                            "                } " +
                            "              } else {" +
                            "                logger.log(Level.WARNING, \"This $4 has no structure: \" + $4.getId());" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"This $4 has a problem with its data. Please report this.\");" +
                            "              }    " +
                            "      } else if ($5 == 193) {" +
                            "        if ((!Servers.localServer.challengeServer || $2.getEnemyPresense() <= 0) && " +
                            "          !$4.isNoRepair()) {" +
                            "          done = MethodsStructure.repairWall($1, $2, $3, $4, $6);" +
                            "        } else {" +
                            "          done = true;" +
                            "        } " +
                            "      } else if ($5 == 192) {" +
                            "        if ($3 == null || $4.isNoImprove()) {" +
                            "          done = true;" +
                            "        } else {" +
                            "          done = MethodsStructure.improveWall($1, $2, $3, $4, $6);" +
                            "        } " +
                            "      } else if ($5 == 180) {" +
                            "        if ($2.getPower() >= 2) {" +
                            "          $2.getLogger().log(Level.INFO, $2" +
                            "              .getName() + \" destroyed a $4 at \" + $4.getTileX() + \", \" + $4.getTileY());" +
                            "          $4.setDamage(100.0F);" +
                            "          done = true;" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You deal a lot of damage to the $4!\");" +
                            "        } " +
                            "      } else if ($5 == 174 && !$4.isIndestructible()) {" +
                            "        if (!$4.isRubble()) {" +
                            "          int tilex = $4.getStartX();" +
                            "          int tiley = $4.getStartY();" +
                            "          VolaTile wallTile = null;" +
                            "          for (int xx = 1; xx >= -1; xx--) {" +
                            "            for (int yy = 1; yy >= -1; yy--) {" +
                            "              try {" +
                            "                Zone zone = Zones.getZone(tilex + xx, tiley + yy, $4.isOnSurface());" +
                            "                VolaTile tile = zone.getTileOrNull(tilex + xx, tiley + yy);" +
                            "                if (tile != null) {" +
                            "                  Wall[] walls = tile.getWalls();" +
                            "                  for (int s = 0; s < walls.length; s++) {" +
                            "                    if (walls[s].getId() == $4.getId()) {" +
                            "                      wallTile = tile;" +
                            "                      break;" +
                            "                    } " +
                            "                  } " +
                            "                } " +
                            "              } catch (NoSuchZoneException noSuchZoneException) {}" +
                            "            } " +
                            "          } " +
                            "          if (wallTile == null) {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You fail to destroy the $4.\");" +
                            "            return true;" +
                            "          } " +
                            "          done = MethodsStructure.destroyWall($5, $2, $3, $4, false, $6);" +
                            "        } else {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"The rubble will clear by itself soon.\");" +
                            "          return true;" +
                            "        } " +
                            "      } else if ($5 == 231) {" +
                            "        if ($4.isFinished()) {" +
                            "          if (Methods.isActionAllowed($2, $5, $4.getTileX(), $4.getTileY())) {" +
                            "            if ($4.isNotPaintable()) {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"You are not allowed to paint this $4.\");" +
                            "              return true;" +
                            "            } " +
                            "            done = MethodsStructure.colorWall($2, $3, $4, $1);" +
                            "          } else {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"You are not allowed to paint this $4.\");" +
                            "            return true;" +
                            "          } " +
                            "        } else {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"Finish the $4 first.\");" +
                            "          return true;" +
                            "        } " +
                            "      } else if ($5 == 232) {" +
                            "        if (Methods.isActionAllowed($2, $5, $4.getTileX(), $4.getTileY())) {" +
                            "          done = MethodsStructure.removeColor($2, $3, $4, $1);" +
                            "        } else {" +
                            "          $2.getCommunicator().sendNormalServerMessage(\"You are not allowed to remove the paint from this $4.\");" +
                            "          return true;" +
                            "        } " +
                            "      } else if ($5 == 82) {" +
                            "        DemolishCheckQuestion dcq = new DemolishCheckQuestion($2, \"Demolish Building\", \"A word of warning!\", $4.getStructureId());" +
                            "        dcq.sendQuestion();" +
                            "      } else {" +
                            "        if ($5 == 662) {" +
                            "          if ($2.getPower() >= 2) {" +
                            "            $4.setIndoor(!$4.isIndoor());" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"Wall toggled and now is \" + (" +
                            "                $4.isIndoor() ? \"Inside\" : \"Outside\"));" +
                            "            if (structure != null) {" +
                            "              structure.updateStructureFinishFlag();" +
                            "            } else {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"The structural integrity of the building is at risk.\");" +
                            "              logger.log(Level.WARNING, \"Structure not found while trying to toggle a $4 at [\" + $4.getStartX() + \",\" + $4" +
                            "                  .getStartY() + \"]\");" +
                            "            } " +
                            "          } " +
                            "          return true;" +
                            "        } " +
                            "        if ($5 == 78) {" +
                            "          if ($2.getPower() >= 2)" +
                            "            try {" +
                            "              com.wurmonline.server.structures.Structure struct = com.wurmonline.server.structures.Structures.getStructure($4.getStructureId());" +
                            "              try {" +
                            "                Items.getItem(struct.getWritId());" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"Writ item exists for structure.\");" +
                            "              } catch (NoSuchItemException nss) {" +
                            "                $2.getCommunicator().sendNormalServerMessage(\"Writ item does not exist for structure. Replacing.\");" +
                            "                try {" +
                            "                  Item newWrit = ItemFactory.createItem(166, 80.0F + Server.rand" +
                            "                      .nextFloat() * 20.0F, $2.getName());" +
                            "                  newWrit.setDescription(struct.getName());" +
                            "                  $2.getInventory().insertItem(newWrit);" +
                            "                  struct.setWritid(newWrit.getWurmId(), true);" +
                            "                } catch (NoSuchTemplateException nst) {" +
                            "                  $2.getCommunicator().sendNormalServerMessage(\"Failed replace:\" + nst.getMessage());" +
                            "                } catch (FailedException enst) {" +
                            "                  $2.getCommunicator().sendNormalServerMessage(\"Failed replace:\" + enst.getMessage());" +
                            "                } " +
                            "              } " +
                            "            } catch (NoSuchStructureException nss) {" +
                            "              logger.log(Level.WARNING, nss.getMessage(), (Throwable)nss);" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"No such structure. Bug. Good luck.\");" +
                            "            }  " +
                            "        } else if ($5 == 472) {" +
                            "          done = true;" +
                            "          if ($3.getTemplateId() == 676 && $3.getOwnerId() == $2.getWurmId()) {" +
                            "            MissionManager m = new MissionManager($2, \"Manage missions\", \"Select action\", $4.getId(), $4.getName(), $3.getWurmId());" +
                            "            m.sendQuestion();" +
                            "          } " +
                            "        } else if ($5 == 90) {" +
                            "          if ($2.getPower() < 4) {" +
                            "            logger.log(Level.WARNING, \"Possible hack attempt by \" + $2.getName() + \" calling Actions.POLL on $4 in WallBehaviour without enough power.\");" +
                            "            return true;" +
                            "          } " +
                            "          int tilex = $4.getStartX();" +
                            "          int tiley = $4.getStartY();" +
                            "          VolaTile wallTile = Zones.getOrCreateTile(tilex, tiley, true);" +
                            "          if (wallTile != null) {" +
                            "            Structure struct = null;" +
                            "            try {" +
                            "              struct = com.wurmonline.server.structures.Structures.getStructure($4.getStructureId());" +
                            "            } catch (NoSuchStructureException e) {" +
                            "              logger.log(Level.WARNING, e.getMessage(), (Throwable)e);" +
                            "            } " +
                            "            if (struct == null) {" +
                            "              $2.getCommunicator().sendNormalServerMessage(\"Couldn't find structure for $4 '\" + $4" +
                            "                  .getId() + \"'.\");" +
                            "              return true;" +
                            "            } " +
                            "            $4.poll(struct.getCreationDate() + 604800000L, wallTile, struct);" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"Poll performed for $4 '\" + $4.getId() + \"'.\");" +
                            "          } else {" +
                            "            $2.getCommunicator().sendNormalServerMessage(\"Unexpectedly missing a tile for \" + tilex + \",\" + tiley + \".\");" +
                            "          } " +
                            "        } else if ($5 == 664) {" +
                            "          manageBuilding($2, structure, $4);" +
                            "        } else if ($5 == 666) {" +
                            "          manageDoor($2, structure, $4);" +
                            "        } else if ($5 == 673) {" +
                            "          manageAllDoors($2, structure, $4);" +
                            "        } else if ($5 == 102 && mayLockDoor($2, $4, door)) {" +
                            "          if (door != null && door.hasLock() && door.isLocked() && !door.isNotLockable()) {" +
                            "            door.unlock(true);" +
                            "            PermissionsHistories.addHistoryEntry(door.getWurmId(), System.currentTimeMillis(), $2" +
                            "                .getWurmId(), $2.getName(), \"Unlocked door\");" +
                            "          } " +
                            "        } else if ($5 == 28 && mayLockDoor($2, $4, door)) {" +
                            "          if (door != null && door.hasLock() && !door.isLocked() && !door.isNotLockable()) {" +
                            "            door.lock(true);" +
                            "            PermissionsHistories.addHistoryEntry(door.getWurmId(), System.currentTimeMillis(), $2" +
                            "                .getWurmId(), $2.getName(), \"Locked door\");" +
                            "          } " +
                            "        } else {" +
                            "          if ($5 == 866) {" +
                            "            if ($2.getPower() >= 4)" +
                            "              Methods.sendGmBuildAllWallsQuestion($2, structure); " +
                            "            return true;" +
                            "          } " +
                            "          if ($5 == 684) {" +
                            "            if (($3.getTemplateId() == 315 || $3.getTemplateId() == 176) && $2" +
                            "              .getPower() >= 2) {" +
                            "              Methods.sendItemRestrictionManagement($2, (Permissions.IAllow)$4, $4.getId());" +
                            "            } else {" +
                            "              logger.log(Level.WARNING, $2.getName() + \" hacking the protocol by trying to set the restrictions of \" + $4 + \", $6: \" + $6 + '!');" +
                            "            } " +
                            "            return true;" +
                            "          } " +
                            "          if ($3.isTrellis() && ($5 == 176 || $5 == 746 || $5 == 747))" +
                            "            done = Terraforming.plantTrellis($2, $3, $4.getMinX(), $4.getMinY(), $4.isOnSurface(), $4.getDir(), $5, $6, $1); " +
                            "        } " +
                            "      } " +
                            "    } " +
                            "    return done; }");
        } catch (NotFoundException e) {
            Ashfall.logger.log(Level.SEVERE,"class not found!",e);
            e.printStackTrace();

        } catch (CannotCompileException e) {
            Ashfall.logger.log(Level.SEVERE,"can not compile",e);
            e.printStackTrace();
        }
    }
}
