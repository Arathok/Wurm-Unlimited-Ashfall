package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;

public class Artifact {
   public Player owner;
   public Long ownerId;
   public Item item;
   public Long soulsHarvested;

   public Long nextHeartBeat=0L;
   public Long nextEyeOpen=0L;
   public Long nextEssence=0L;
   public Long nextEssenceLost;
   public Long previousOwnerId=-10L;
   public Long ownershipBegin = 0L;
   public Long ownershipBeginDelayed=0L;
   public boolean foundBySameUser;
   public Long uses;
   public Long calloutTimer;


   Artifact(){
     owner=null;
     ownerId=-10L;
     item=null;
     ownershipBegin=null;
     previousOwnerId=-10L;
     calloutTimer=0L;
   }






}
