package org.arathok.wurmunlimited.mods.ashfall.artifacts;

import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;

public class Artifact {

   public Player owner;
   public Long ownerId;
   public Item item;
   public Long soulsHarvested;
   public Long nextHeartBeat;
   public Long nextEyeOpen;
   public Long nextEssence;
   public Long nextEssenceLost;
   public Long previousOwnerId;
   public Long ownershipBegin;
   public Long uses;
   public Long calloutTimer=3600000L;




}
