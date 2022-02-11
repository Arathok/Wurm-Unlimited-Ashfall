package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.MessageServer;
import com.wurmonline.server.Players;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.bodys.Wounds;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import org.arathok.wurmunlimited.mods.ashfall.Ashfall;
import org.arathok.wurmunlimited.mods.ashfall.items.AshfallItems;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class ArtifactPoller {

    public static List<Artifact> artifacts = new LinkedList<>();
    static Artifact waningCrescent;
    static Artifact thornOfFo;
    static Artifact eyeOfValrei;
    static Artifact flaskOfVynora;
    static Artifact heartOfUttacha;
    static boolean artifactsGood=false;
  public static void ArtifactCheckAndBuild()
    {
        int[] newArtifactToCreate ={AshfallItems.waningCrescentId,AshfallItems.thornOfFoId,AshfallItems.heartOfUttachaId,AshfallItems.heartOfUttachaId,FlaskOfVynora.flaskOfVynoraId,};

        for (int i =0; i< 5; i++) {
            Random x = new Random();
            int lowx = 100;
            int highx = 3800;
            int resultx = x.nextInt(highx) + lowx;

            Random y = new Random();
            int lowy = 100;
            int highy = 3800;
            int resulty = y.nextInt(highy) + lowy;
            Item newArtifact = null;
            try {

                newArtifact = ItemFactory.createItem(newArtifactToCreate[i], (float) 50, (float) resultx, (float) resulty, 10F, true, (byte) 21, (byte) 3, 0, "gods");
            } catch (NoSuchTemplateException e) {
                e.printStackTrace();
                Ashfall.logger.log(Level.FINE, "Artifact has no ItemTemplate", e);
            } catch (FailedException e) {
                e.printStackTrace();
            }
        }

        long time = System.currentTimeMillis();
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

    public static void ArtifactCallOut()
    {
        long time = System.currentTimeMillis();
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
    public static void ArtifactOwnerPoller()
    {
        boolean messageMerker=false;
       long time = System.currentTimeMillis();
        Iterator<Artifact> artifactsPoller = artifacts.iterator();
        while (artifactsPoller.hasNext()) {
            Artifact artifactInQuestion = artifactsPoller.next();
            int index=artifacts.indexOf(artifactInQuestion);
            if (artifactInQuestion.item.getOwnerId()!=artifactInQuestion.ownerId) {
                artifactInQuestion.ownershipBeginDelayed = time + 900000;
                artifactInQuestion.ownershipBegin=time;
                artifactInQuestion.previousOwnerId=artifactInQuestion.ownerId;
                artifactInQuestion.ownerId=artifactInQuestion.item.getOwnerId();
                artifactInQuestion.owner=Players.getInstance().getPlayerOrNull(artifactInQuestion.item.getOwnerId());
                artifacts.set(index,artifactInQuestion);
                messageMerker = true;

            }
                if (artifactInQuestion.ownershipBeginDelayed<time&& messageMerker) {


                long holdtime = time-artifactInQuestion.ownershipBegin-900000L;
                holdtime = holdtime / 1000;
                long holdtimeDays =  holdtime/86400;
                long holdtimeHours= (holdtime%86400)/3600;
                long holdtimeMinutes = (holdtime%3600)/60;
                long holdtimeSeconds = holdtime%60;

                MessageServer.broadCastSafe(artifactInQuestion.item.getName()+" has a new owner!("+Players.getInstance().getPlayerOrNull(artifactInQuestion.item.getOwnerId()).getName()+")" ,(byte) 1);
                MessageServer.broadCastSafe("the previous ownder held it for "+holdtimeDays + "d, " + holdtimeHours+"h, "+holdtimeMinutes+ "m, "+holdtimeSeconds+ "s.",(byte)1);
                Ashfall.logger.log(Level.FINE,"new Owner: "+ Players.getInstance().getPlayerOrNull(artifactInQuestion.ownerId));

            }

        }

    }

        public static void ArtifactEffectPoller() {
        long time = System.currentTimeMillis();
        Player playerinQuestion = null;
        Iterator<Artifact> ownerfinder = artifacts.iterator();
        while (ownerfinder.hasNext()) {
            Artifact aArtifact = ownerfinder.next();
            int index = artifacts.indexOf(aArtifact);
            if (aArtifact.item.getTemplate().getName().contains("Uttacha") && Players.getInstance().getPlayerOrNull(aArtifact.ownerId) != null) {
                playerinQuestion = aArtifact.owner;
                if (playerinQuestion != null)
                    if (!playerinQuestion.isOffline()) {
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
                            artifacts.set(index, aArtifact);
                        }
                    }
            }

            if (aArtifact.item.getTemplate().getName().contains("Valrei") && Players.getInstance().getPlayerOrNull(aArtifact.ownerId) != null) {
                playerinQuestion = aArtifact.owner;
                if (playerinQuestion != null) {
                    if (aArtifact.nextEyeOpen > time) {
                        try {
                            playerinQuestion.setFavor((playerinQuestion.getFavor() + 0.1F));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        aArtifact.nextEyeOpen = time + 10000;
                        artifacts.set(index, aArtifact);
                    }
                }
            }

            if (aArtifact.item.getTemplate().getName().contains("Valrei") && Players.getInstance().getPlayerOrNull(aArtifact.ownerId) != null) {
                playerinQuestion = aArtifact.owner;
                if (playerinQuestion != null) {
                    if (aArtifact.nextEyeOpen < time) {
                        try {
                            playerinQuestion.setFavor((playerinQuestion.getFavor() + 0.1F));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        aArtifact.nextEyeOpen = time + 10000;
                        artifacts.set(index, aArtifact);
                    }
                }
            }

            if (aArtifact.item.getTemplate().getName().contains("Vynora") && Players.getInstance().getPlayerOrNull(aArtifact.ownerId) != null) {
                playerinQuestion = aArtifact.owner; // get owner
                if (playerinQuestion != null) {     // if there is owner
                    Vehicle v = Vehicles.getVehicleForId(aArtifact.item.getWurmId()); // get vehicle
                    if (aArtifact.nextEssence > time && v.getPosZ()> 20) {              // if above water level and next essence time is around
                        Set<Item>essences = aArtifact.item.getItems();                  // get num of essences
                        Item essence=null;
                        if (essences.size()<100) {                                      // if less than 100 refill
                            try {
                                essence = ItemFactory.createItem(AshfallItems.essenceOfSeaId, 99.0F, (byte) 3, "Vynora");
                            } catch (FailedException e) {
                                e.printStackTrace();
                            } catch (NoSuchTemplateException e) {
                                e.printStackTrace();
                                Ashfall.logger.log(Level.SEVERE, "NO ESSENCE TEMPLATE! NUUUUUUUUUUUUU! >:C ", e);
                            }
                            if (essence != null)
                                aArtifact.item.insertItem(essence, true);
                            aArtifact.nextEssence = time + 18000;
                            artifacts.set(index, aArtifact);
                        }
                    }
                    else
                    {
                        if (v.getPosZ()<4)
                        {

                                Set <Item> essences=aArtifact.item.getItems();
                                if (aArtifact.nextEssenceLost>time&& !essences.isEmpty()&&aArtifact.item.getItems()!=null)
                                {
                                    Item essence = aArtifact.item.getFirstContainedItem();
                                    aArtifact.item.getItems().remove(essence);
                                    aArtifact.nextEssenceLost=time+3;
                                    artifacts.set(index,aArtifact);
                                }


                        }

                    }
                }
            }

        }
    }


    public static void ArtifactDisappear()
    {
        long time = System.currentTimeMillis();
        Iterator<Artifact> artifactsWirbler = artifacts.iterator();
        while (artifactsWirbler.hasNext()) {
            Artifact artifactInQuestion = artifactsWirbler.next();
            int index=artifacts.indexOf(artifactInQuestion);
            if (time > artifactInQuestion.ownershipBegin+2592000000L&&!Players.getInstance().getPlayerOrNull(artifactInQuestion.item.getOwnerId()).isFighting()) {

                MessageServer.broadCastSafe(artifactInQuestion.item.getName()+" Has disappeared! Where in the world can it be now?",(byte)1);
                Ashfall.logger.log(Level.FINE, "Artifact disappeared");
                artifactInQuestion.previousOwnerId=artifactInQuestion.item.getOwnerId();
                int newArtifactToCreate = artifactInQuestion.item.getTemplateId();

                Random qualityBetter = new Random();
                int resultBetter = qualityBetter.nextInt(2);
                int resultquality=0;
                if (resultBetter==1) {

                    Random quality = new Random();
                    int low = (int)artifactInQuestion.item.getQualityLevel();
                    int high = 100;
                    resultquality = quality.nextInt(high);
                }
                else {
                    Random quality = new Random();
                    int low = 1;
                    int high = (int) artifactInQuestion.item.getQualityLevel();
                    resultquality = quality.nextInt(high);
                }

                Random x = new Random();
                int lowx = 100;
                int highx = 3800;
                int resultx = x.nextInt(highx) + lowx;

                Random y = new Random();
                int lowy = 100;
                int highy= 3800;
                int resulty = y.nextInt(highy) + lowy;
                Item newArtifact = null;
                try {
                     newArtifact=ItemFactory.createItem(newArtifactToCreate,(float)resultquality,(float) resultx,(float)resulty,10F,true,(byte)21,(byte)3,0,"gods");
                } catch (NoSuchTemplateException e) {
                    e.printStackTrace();
                    Ashfall.logger.log(Level.FINE, "Artifact has no ItemTemplate",e);
                } catch (FailedException e) {
                    e.printStackTrace();
                }
                if (artifactInQuestion.item != null) {
                    Items.destroyItem(artifactInQuestion.item.getWurmId());
                }
                artifactInQuestion.item=newArtifact;
                artifactInQuestion.ownerId=-10L;


                artifacts.set(index,artifactInQuestion);
            }


        }

    }
}
