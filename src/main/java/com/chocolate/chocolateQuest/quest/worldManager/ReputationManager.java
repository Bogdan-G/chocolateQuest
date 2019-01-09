package com.chocolate.chocolateQuest.quest.worldManager;

import com.chocolate.chocolateQuest.quest.worldManager.KillCounter;
import com.chocolate.chocolateQuest.quest.worldManager.Reputation;
import com.chocolate.chocolateQuest.quest.worldManager.WorldManagerBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class ReputationManager extends WorldManagerBase {

   public static ReputationManager instance;
   Map reputation = new HashMap();
   Map globals = new HashMap();
   List killCounters = new ArrayList();


   public int getGlobal(String varName) {
      return !this.globals.containsKey(varName)?0:((Integer)this.globals.get(varName)).intValue();
   }

   public void setGlobal(String varName, int reputation) {
      this.globals.put(varName, Integer.valueOf(reputation));
   }

   public void onEntityKilled(EntityLivingBase entity, EntityPlayer killer) {
      for(int i = 0; i < this.killCounters.size(); ++i) {
         KillCounter counter = (KillCounter)this.killCounters.get(i);
         if(counter.playerName.equals(killer.getCommandSenderName()) && counter.entityMatchesCounter(entity)) {
            ++counter.killAmmount;
         }
      }

   }

   public void addKillCounter(String name, String player, String entity, String tags) {
      if(this.getKillCounter(name, player) == null) {
         KillCounter newCounter = new KillCounter(player, name, entity, tags);
         this.killCounters.add(newCounter);
      }

   }

   public void removeKillCounter(String name, String player) {
      KillCounter counter = this.getKillCounter(name, player);
      if(counter != null) {
         this.killCounters.remove(counter);
      }

   }

   public KillCounter getKillCounter(String name, String player) {
      Iterator i$ = this.killCounters.iterator();

      KillCounter killCounter;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         killCounter = (KillCounter)i$.next();
      } while(!killCounter.name.equals(name) || !killCounter.playerName.equals(player));

      return killCounter;
   }

   public void getCounterNames(String player, List list) {
      Iterator i$ = this.killCounters.iterator();

      while(i$.hasNext()) {
         KillCounter killCounter = (KillCounter)i$.next();
         if(killCounter.playerName.equals(player)) {
            list.add(killCounter.name);
         }
      }

   }

   public int getKillAmmount(String name, String player) {
      Iterator i$ = this.killCounters.iterator();

      KillCounter killCounter;
      do {
         if(!i$.hasNext()) {
            return 0;
         }

         killCounter = (KillCounter)i$.next();
      } while(!killCounter.name.equals(name) || !killCounter.playerName.equals(player));

      return killCounter.killAmmount;
   }

   public int getPlayerReputation(String player, String team) {
      return this.getReputation(player, team).reputation;
   }

   public void setReputation(String player, String team, int reputation) {
      Reputation rep = this.getReputation(player, team);
      rep.reputation = reputation;
   }

   public void addReputation(String player, String team, int reputationAddition) {
      Reputation rep = this.getReputation(player, team);
      rep.reputation += reputationAddition;
   }

   public void addReputation(EntityPlayer player, String team, int reputationAddition) {
      this.addReputation(player.getCommandSenderName(), team, reputationAddition);
      int total = instance.getPlayerReputation(player.getCommandSenderName(), team);
      String repAdditionString = this.colorNumber(reputationAddition);
      String repTotalString = this.colorNumber(total);
      String message = StatCollector.translateToLocal("strings.reputation");
      message = String.format(team + " " + message, new Object[]{repAdditionString, repTotalString});
      player.addChatMessage(new ChatComponentText(message));
   }

   public Reputation getReputation(String player, String team) {
      Map factionsMap = this.getTeamMap(player, team);
      Object object = factionsMap.get(team);
      if(object == null) {
         object = new Reputation(player, team);
         factionsMap.put(team, object);
      }

      Reputation reputation = (Reputation)object;
      return reputation;
   }

   public String colorNumber(int number) {
      String color = "+" + BDHelper.StringColor("2");
      if(number < 0) {
         color = BDHelper.StringColor("4");
      }

      color = color.concat(number + BDHelper.StringColor("r"));
      return color;
   }

   public Map getTeamMap(String player, String team) {
      Object factionsMap = (Map)this.reputation.get(player);
      if(factionsMap == null) {
         factionsMap = new HashMap();
         this.reputation.put(player, factionsMap);
      }

      return (Map)factionsMap;
   }

   protected void readFromNBT(NBTTagCompound tag) {
      NBTTagList list = (NBTTagList)tag.getTag("Reputation");
      int i;
      NBTTagCompound entry;
      if(list != null) {
         for(i = 0; i < list.tagCount(); ++i) {
            entry = list.getCompoundTagAt(i);
            this.setReputation(entry.getString("Player"), entry.getString("Team"), entry.getInteger("Rep"));
         }
      }

      list = (NBTTagList)tag.getTag("Globals");
      if(list != null) {
         for(i = 0; i < list.tagCount(); ++i) {
            entry = list.getCompoundTagAt(i);
            String counter = entry.getString("name");
            int value = entry.getInteger("value");
            this.setGlobal(counter, value);
         }
      }

      list = (NBTTagList)tag.getTag("Counters");
      if(list != null) {
         for(i = 0; i < list.tagCount(); ++i) {
            entry = list.getCompoundTagAt(i);
            KillCounter var7 = new KillCounter();
            var7.readFromNBT(entry);
            this.killCounters.add(var7);
         }
      }

   }

   protected void writeToNBT(NBTTagCompound tag) {
      NBTTagList list = new NBTTagList();
      Set playersMap = this.reputation.entrySet();
      Iterator listGlobal = playersMap.iterator();

      while(listGlobal.hasNext()) {
         Entry globalMap = (Entry)listGlobal.next();
         String listCounters = (String)globalMap.getKey();
         Set i$ = ((Map)globalMap.getValue()).entrySet();
         Iterator counter = i$.iterator();

         while(counter.hasNext()) {
            Entry counterTag = (Entry)counter.next();
            String teamName = (String)counterTag.getKey();
            NBTTagCompound tagRep = new NBTTagCompound();
            tagRep.setInteger("Rep", ((Reputation)counterTag.getValue()).reputation);
            tagRep.setString("Team", teamName);
            tagRep.setString("Player", listCounters);
            list.appendTag(tagRep);
         }
      }

      tag.setTag("Reputation", list);
      NBTTagList listGlobal1 = new NBTTagList();
      Set globalMap1 = this.globals.entrySet();
      Iterator listCounters2 = globalMap1.iterator();

      while(listCounters2.hasNext()) {
         Entry i$1 = (Entry)listCounters2.next();
         NBTTagCompound counter2 = new NBTTagCompound();
         counter2.setString("name", (String)i$1.getKey());
         counter2.setInteger("value", ((Integer)i$1.getValue()).intValue());
         listGlobal1.appendTag(counter2);
      }

      tag.setTag("Globals", listGlobal1);
      NBTTagList listCounters1 = new NBTTagList();
      Iterator i$2 = this.killCounters.iterator();

      while(i$2.hasNext()) {
         KillCounter counter1 = (KillCounter)i$2.next();
         NBTTagCompound counterTag1 = new NBTTagCompound();
         counter1.writeToNBT(counterTag1);
         listCounters1.appendTag(counterTag1);
      }

      tag.setTag("Counters", listCounters1);
   }
}
