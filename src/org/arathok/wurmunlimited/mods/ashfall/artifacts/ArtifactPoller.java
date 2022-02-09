package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.*;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.bodys.Wounds;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.SkillList;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class ArtifactPoller {

    public static List<Artifact> artifacts = new LinkedList<>();
    Artifact waningCrescent,thornOfFo,eyeOfValrei,flaskOfVynora,heartOfUttacha;
    boolean artifactsGood=false;
    void ArtifactCheckAndBuild()
    {
        Long time = System.currentTimeMillis();
        Item[] allItems = Items.getAllItems();

        for (Item oneItem : allItems) {
            if (oneItem.getTemplate().getName().contains("Crescent")) {
                waningCrescent.item = oneItem;
                waningCrescent.ownerId= oneItem.getOwnerId();
                waningCrescent.previousOwnerId = oneItem.getOwnerId();
                waningCrescent.ownershipBegin= time;
            }
            else if (oneItem.getTemplate().getName().contains("Fo")) {
                thornOfFo.item = oneItem;
                thornOfFo.ownerId = oneItem.getOwnerId();
                thornOfFo.previousOwnerId = oneItem.getOwnerId();
                thornOfFo.ownershipBegin=time;
            }
            else if (oneItem.getTemplate().getName().contains("Valrei")) {
                eyeOfValrei.item = oneItem;
                eyeOfValrei.ownerId = oneItem.getOwnerId();
                eyeOfValrei.previousOwnerId = oneItem.getOwnerId();
                eyeOfValrei.ownershipBegin=time;
            }
            else if (oneItem.getTemplate().getName().contains("Heart")) {
                heartOfUttacha.item = oneItem;
                heartOfUttacha.ownerId = oneItem.getOwnerId();
                heartOfUttacha.previousOwnerId = oneItem.getOwnerId();
                heartOfUttacha.ownershipBegin=time;
            }
            else if (oneItem.getTemplate().getName().contains("Flask")) {
                flaskOfVynora.item = oneItem;
                flaskOfVynora.ownerId = oneItem.getOwnerId();
                flaskOfVynora.previousOwnerId = oneItem.getOwnerId();
                flaskOfVynora.ownershipBegin=time;
                try {
                    flaskOfVynora.item.insertItem(ItemFactory.createItem(AshfallItems.essenceOfSeaId,99.0F,null));
                } catch (FailedException e) {
                    e.printStackTrace();
                } catch (NoSuchTemplateException e) {
                    Ashfall.logger.log(Level.SEVERE,"there was no template registered! for essence of the sea!");
                    e.printStackTrace();
                }

            }
        }
        if (waningCrescent.item == null||eyeOfValrei.item==null||thornOfFo.item==null||flaskOfVynora.item==null||heartOfUttacha.item==null)
        Ashfall.logger.log(Level.SEVERE,"One or more artifacts were not found!! Admin probably forgot to spawn them, dumb fuck.");
        else artifactsGood=true;
        if (artifactsGood) {
           artifacts.add(waningCrescent);
           artifacts.add(thornOfFo);
           artifacts.add(flaskOfVynora);
           artifacts.add(heartOfUttacha);
           artifacts.add(eyeOfValrei);
        }
    }

    public void ArtifactCallOut()
    {
        Long time = System.currentTimeMillis();
        Iterator<Artifact> artifactsCaller = artifacts.iterator();
        while (artifactsCaller.hasNext()) {
            Artifact artifactInQuestion = artifactsCaller.next();
            int index=artifacts.indexOf(artifactInQuestion);
            if (time > artifactInQuestion.calloutTimer&&artifactInQuestion.ownerId==-10) {

                MessageServer.broadCastSafe(artifactInQuestion.item.getName()+" Calls out to the Ashlanders. It is searching for a new bearer to find it.",(byte)1);
                Ashfall.logger.log(Level.FINE, "Artifacts called out");
            }
            else
            {
                artifactInQuestion.calloutTimer=time+14400000L;
                artifacts.set(index,artifactInQuestion);
            }

        }

    }
    public void ArtifactOwnerPoller()
    {
       Long time = System.currentTimeMillis();
        Iterator<Artifact> artifactsPoller = artifacts.iterator();
        while (artifactsPoller.hasNext()) {
            Artifact artifactInQuestion = artifactsPoller.next();
            int index=artifacts.indexOf(artifactInQuestion);
            if (artifactInQuestion.item.getOwnerId()!=artifactInQuestion.ownerId)
            {
                
                artifactInQuestion.previousOwnerId=artifactInQuestion.ownerId;
                artifactInQuestion.ownerId=artifactInQuestion.item.getOwnerId();
                artifactInQuestion.owner=Players.getInstance().getPlayerOrNull(artifactInQuestion.item.getOwnerId());
                Long holdtime = time-artifactInQuestion.ownershipBegin;
                holdtime = holdtime / 1000;
                Long holdtimeDays =  holdtime/86400;
                Long holdtimeHours= (holdtime%86400)/3600;
                Long holdtimeMinutes = (holdtime%3600)/60;
                Long holdtimeSeconds = holdtime%60;

                MessageServer.broadCastSafe(artifactInQuestion.item.getName()+" has a new owner!",(byte) 1);
                MessageServer.broadCastSafe("the previous ownder held it for "+holdtimeDays + "d, " + holdtimeHours+"h, "+holdtimeMinutes+ "m, "+holdtimeSeconds+ "s.",(byte)1);
                Ashfall.logger.log(Level.FINE,"new Owner: "+ Players.getInstance().getPlayerOrNull(artifactInQuestion.ownerId));
                artifacts.set(index,artifactInQuestion);
            }

        }
    }
    public void ArtifactEffectPoller()
    {
        long time = System.currentTimeMillis();
        Player playerinQuestion = null;
        Iterator <Artifact> ownerfinder = artifacts.iterator();
        while (ownerfinder.hasNext()) {
            Artifact aArtifact = ownerfinder.next();
            if (aArtifact.item.getTemplate().getName().contains("Uttacha") && Players.getInstance().getPlayerOrNull(aArtifact.ownerId) != null)
                playerinQuestion = aArtifact.owner;
            if (playerinQuestion != null)
            if (!playerinQuestion.isOffline())
            {
                if (aArtifact.nextHeartBeat > time) {
                    double healingPool = 1D;
                    Wounds tWounds = playerinQuestion.getBody().getWounds();
                    if (tWounds != null) {
                        for (Wound w : tWounds.getWounds()) {
                            if (w.getSeverity() <= healingPool) {
                                healingPool -= w.getSeverity();
                                w.heal();

                            }
                            if (w.getSeverity() > healingPool) {
                                w.modifySeverity((int) -healingPool);

                            }
                        }
                    }
                    aArtifact.nextHeartBeat = time + 60000;
                }
            }
            if (aArtifact.item.getTemplate().getName().contains("Vynora")&&Players.getInstance().getPlayerOrNull(aArtifact.ownerId)!=null)
                playerinQuestion = aArtifact.owner;
            if (playerinQuestion!=null) {
                double oldStrength = 0;
                double oldStamina = 0;
                try {
                    oldStrength = playerinQuestion.getSkills().getSkill(SkillList.BODY_STRENGTH).getKnowledge();
                    oldStamina = playerinQuestion.getSkills().getSkill(SkillList.BODY_STAMINA).getKnowledge();
                    
                } catch (NoSuchSkillException e) {
                    e.printStackTrace();
                }
                if (playerinQuestion.isSwimming()) {

                    if (aArtifact.nextEssenceLost < time && aArtifact.item.getItemsAsArray().length > 0) {
                        playerinQuestion.setSkill(SkillList.BODY_STRENGTH, 99.0F);
                        playerinQuestion.setSkill(SkillList.BODY_STAMINA, 99.0F);
                        Item[] essences;
                        essences = aArtifact.item.getItemsAsArray();
                        Items.destroyItem(essences[0].getWurmId());
                        aArtifact.nextEssenceLost = time + 1000;

                    } else {

                        playerinQuestion.setSkill(SkillList.BODY_STAMINA,(float)oldStamina);
                        playerinQuestion.setSkill(SkillList.BODY_STRENGTH,(float)oldStrength);

                    }
                }
            }
        }

    }

    public void EyeofValreiPoller()
    {

    }

    public void VynorasFlaskPoller()
    {

    }
}
