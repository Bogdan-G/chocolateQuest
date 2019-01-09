package com.chocolate.chocolateQuest.entity.npc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIBuildSchematic;
import com.chocolate.chocolateQuest.entity.ai.AIControlledPath;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGoToPosition;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGuard;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcSit;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcSleep;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcWander;
import com.chocolate.chocolateQuest.entity.ai.npcai.EnumNpcAI;
import com.chocolate.chocolateQuest.entity.ai.npcai.NpcAI;
import com.chocolate.chocolateQuest.entity.npc.TimeCounter;
import com.chocolate.chocolateQuest.entity.npc.npcLogic;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import com.chocolate.chocolateQuest.misc.EnumRace;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import com.chocolate.chocolateQuest.packets.PacketBase;
import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.packets.PacketStartConversation;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import com.chocolate.chocolateQuest.packets.PacketUpdateShopRecipe;
import com.chocolate.chocolateQuest.quest.DialogOption;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.utils.AIPosition;
import com.chocolate.chocolateQuest.utils.Vec4I;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.ICommandManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityHumanNPC extends EntityHumanBase implements INpc {

   ShopRecipe[] trades;
   public String texture;
   public boolean hasPlayerTexture;
   public ResourceLocation textureLocationPlayer;
   public int modelType;
   public String name;
   public String displayName;
   public float size;
   public int color;
   public boolean isInvincible;
   public int voice;
   public NBTTagCompound npcVariables;
   public DialogOption conversation;
   DialogOption currentDialog;
   public boolean canTeleport;
   public int repToAttack;
   public int repOnDeath;
   public boolean targetMobs;
   EntityAINearestAttackableTarget aiTargetMonster;
   public int followTime;
   List timers;
   public List AIPositions;
   public NpcAI npcAI;
   private NpcAI currentAI;
   protected EntityAIBase npcAITask;
   protected AIBuildSchematic buildTask;


   public EntityHumanNPC(World world) {
      super(world);
      this.trades = new ShopRecipe[]{new ShopRecipe(new ItemStack(Items.emerald), new ItemStack[]{new ItemStack(Items.apple)})};
      this.texture = "pirate.png";
      this.hasPlayerTexture = false;
      this.textureLocationPlayer = null;
      this.name = "unnamed";
      this.displayName = "Unknown";
      this.size = 0.5F;
      this.color = 16777215;
      this.isInvincible = false;
      this.conversation = new DialogOption((DialogOption[])null, "helloWorld");
      this.canTeleport = false;
      this.repToAttack = 0;
      this.repOnDeath = 10;
      this.targetMobs = true;
      this.followTime = 0;
      this.timers = null;
      this.npcAI = null;
      this.currentAI = null;
      if(world != null && !world.isRemote) {
         this.npcVariables = new NBTTagCompound();
      }

      super.shouldDespawn = false;
      super.AIMode = EnumAiState.FOLLOW.ordinal();
      super.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
   }

   public void setAIForCurrentMode() {
      super.setAIForCurrentMode();
      this.setTargetsMobs();
   }

   public void setTargetsMobs() {
      if(this.targetMobs && this.aiTargetMonster == null) {
         this.aiTargetMonster = new EntityAINearestAttackableTarget(this, IMob.class, 0, true, false, new HumanSelector(this));
         super.targetTasks.addTask(4, this.aiTargetMonster);
      } else if(!this.targetMobs && this.aiTargetMonster != null) {
         super.targetTasks.removeTask(this.aiTargetMonster);
         this.aiTargetMonster = null;
      }

   }

   public void onLivingUpdate() {
      if(this.followTime > 0) {
         if(this.followTime == 1) {
            this.setOwner((EntityLivingBase)null);
         }

         --this.followTime;
      }

      if(super.ticksExisted % 2000 == 0) {
         this.heal(1.0F);
      }

      if(this.timers != null) {
         for(int nextAI = 0; nextAI < this.timers.size(); ++nextAI) {
            ((TimeCounter)this.timers.get(nextAI)).update();
         }
      }

      if(this.npcAI != null && !super.worldObj.isRemote) {
         NpcAI var3 = null;
         if(this.currentAI != null) {
            var3 = this.currentAI.nextAI;
         } else {
            this.currentAI = this.npcAI;
            var3 = this.npcAI.nextAI;
         }

         if(var3 == null) {
            var3 = this.npcAI;
         }

         int time = (int)super.worldObj.getWorldTime();
         if(this.npcAITask == null || var3.hour > this.currentAI.hour && time > var3.hour || time < this.currentAI.hour && (time > this.npcAI.hour || var3.hour > this.currentAI.hour)) {
            if(this.npcAITask != null) {
               super.tasks.removeTask(this.npcAITask);
            }

            this.setSitting(false);
            this.setSleeping(false);
            if(var3.type == EnumNpcAI.STAY.ordinal()) {
               this.npcAITask = new AINpcGoToPosition(this, this.getPositionByName(var3.position));
            } else if(var3.type == EnumNpcAI.SLEEP.ordinal()) {
               this.npcAITask = new AINpcSleep(this, this.getPositionByName(var3.position));
            } else if(var3.type == EnumNpcAI.WANDER.ordinal()) {
               this.npcAITask = new AINpcWander(this, this.getPositionByName(var3.position));
            } else if(var3.type == EnumNpcAI.SIT.ordinal()) {
               this.npcAITask = new AINpcSit(this, this.getPositionByName(var3.position));
            } else if(var3.type == EnumNpcAI.PATH.ordinal()) {
               this.npcAITask = new AIControlledPath(this);
            } else {
               this.npcAITask = new AINpcGuard(this, this.getPositionByName(var3.position));
            }

            if(this.npcAITask != null) {
               super.tasks.addTask(7, this.npcAITask);
            }

            this.currentAI = var3;
         }
      }

      if(this.buildTask != null && !this.buildTask.hasBuildingPlans()) {
         super.tasks.removeTask(this.buildTask);
         this.buildTask = null;
      }

      super.onLivingUpdate();
   }

   public Vec4I getPositionByName(String name) {
      Vec4I v = null;
      if(this.AIPositions != null) {
         Iterator c = this.AIPositions.iterator();

         while(c.hasNext()) {
            AIPosition p = (AIPosition)c.next();
            if(p.name.equals(name)) {
               v = new Vec4I(p.xCoord, p.yCoord, p.zCoord, p.rot);
               break;
            }
         }
      }

      if(v == null) {
         ChunkCoordinates c1 = this.getHomePosition();
         v = new Vec4I(c1.posX, c1.posY, c1.posZ, 0);
      }

      return v;
   }

   public boolean attackEntityFrom(DamageSource damagesource, float damage) {
      return this.isInvincible?false:super.attackEntityFrom(damagesource, damage);
   }

   protected boolean interact(EntityPlayer player) {
      if(super.playerSpeakingTo == null) {
         if(super.entityTeam.startsWith("mob") && !player.capabilities.isCreativeMode) {
            return false;
         }

         if(!super.worldObj.isRemote) {
            String team = super.entityTeam.replace("npc_", "");
            boolean allied = team.equals("npc") || ReputationManager.instance.getPlayerReputation(player.getCommandSenderName(), team) >= this.repToAttack;
            if(!allied && player.getTeam() != null) {
               allied = this.isOnAlliedTeam(player.getTeam());
            }

            if(player.capabilities.isCreativeMode || allied) {
               this.setAttackTarget((EntityLivingBase)null);
               super.playerSpeakingTo = player;
               this.openConversation(player);
            }
         }
      }

      return true;
   }

   public IMessage getEntityGUIUpdatePacket(EntityPlayer player) {
      return (IMessage)(player.capabilities.isCreativeMode?new PacketUpdateHumanDummyData(this):new PacketUpdateHumanData(this));
   }

   public void openShop(EntityPlayer player) {
      if(super.worldObj instanceof WorldServer) {
         PacketUpdateShopRecipe packet = new PacketUpdateShopRecipe(this, -1);
         ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
         player.openGui(ChocolateQuest.instance, 5, super.worldObj, this.getEntityId(), 0, 0);
      }

   }

   public void openEnchantment(EntityPlayer player, int type, int level) {
      player.openGui(ChocolateQuest.instance, 7, super.worldObj, this.getEntityId(), type, level);
   }

   public void editNPC(EntityPlayer player) {
      if(player.capabilities.isCreativeMode) {
         PacketEditNPC packet = new PacketEditNPC(this, (byte)1);
         ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
      }

   }

   public void editAI(EntityPlayer player) {
      if(player.capabilities.isCreativeMode) {
         NBTTagCompound tag = new NBTTagCompound();
         this.writeAIToNBT(tag);
         PacketEditNPC packet = new PacketEditNPC(this, tag, (byte)2);
         ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
      }

   }

   public void openConversation(EntityPlayer player) {
      this.conversation.setID(0);
      PacketStartConversation packet = new PacketStartConversation(this, new int[0], player);
      ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
   }

   public void editConversation(EntityPlayer player) {
      if(player.capabilities.isCreativeMode) {
         PacketEditConversation packet = new PacketEditConversation(this, new int[0]);
         ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
      }

   }

   public void updateConversation(EntityPlayer player, int[] selectedStep) {
      DialogOption option = this.getDialogOption(selectedStep);
      option.execute(player, this);
      PacketStartConversation packet = new PacketStartConversation(this, selectedStep, player);
      ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
   }

   public void endConversation() {
      super.playerSpeakingTo = null;
   }

   public DialogOption getDialogOption(int[] selectedStep) {
      DialogOption option = this.conversation;

      for(int i = 0; i < selectedStep.length; ++i) {
         option = option.options[selectedStep[i]];
      }

      return option;
   }

   public int executeCommand(String command) {
      if(super.playerSpeakingTo != null) {
         command = command.replace("@sp", super.playerSpeakingTo.getCommandSenderName());
      }

      int returnValue = 0;
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if(minecraftserver != null && minecraftserver.isCommandBlockEnabled()) {
         ICommandManager icommandmanager = minecraftserver.getCommandManager();
         returnValue = icommandmanager.executeCommand(new npcLogic(this), command);
      }

      return returnValue;
   }

   public ShopRecipe[] getRecipes() {
      return this.trades;
   }

   public void setRecipes(int i, ShopRecipe recipe) {
      if(this.trades == null) {
         this.trades = new ShopRecipe[1];
      }

      if(i >= this.trades.length) {
         i = this.trades.length;
         ShopRecipe[] newTrades = new ShopRecipe[i + 1];

         for(int t = 0; t < newTrades.length - 1; ++t) {
            newTrades[t] = this.trades[t];
         }

         this.trades = newTrades;
      }

      this.trades[i] = recipe;
   }

   public void setRecipes(ShopRecipe[] recipes) {
      this.trades = recipes;
   }

   public String getCommandSenderName() {
      return this.displayName;
   }

   public void startBuildingSchematic(String schematicName, String position, int speed) {
      if(this.buildTask == null) {
         this.buildTask = new AIBuildSchematic(this);
         this.addBuildTask();
      }

      this.buildTask.addBuildingPlans(schematicName, position, speed);
   }

   private void addBuildTask() {
      super.tasks.addTask(3, this.buildTask);
   }

   public void loadFromTag(NBTTagCompound tag) {
      double x = super.posX;
      double y = super.posY;
      double z = super.posZ;
      NBTTagCompound tagOriginal = new NBTTagCompound();
      this.writeEntityToNBT(tagOriginal);
      Iterator iterator = tag.func_150296_c().iterator();

      while(iterator.hasNext()) {
         String s = (String)iterator.next();
         NBTBase nbtbase = tag.getTag(s);
         tagOriginal.setTag(s, nbtbase);
      }

      this.readFromNBT(tagOriginal);
      this.setPosition(x, y, z);
   }

   public void updateStats(NBTTagCompound tag) {
      if(tag.hasKey("CustomNameVisible")) {
         this.setAlwaysRenderNameTag(tag.getBoolean("CustomNameVisible"));
      }

      if(tag.hasKey("CanPickUpLoot")) {
         this.setCanPickUpLoot(tag.getBoolean("CanPickUpLoot"));
      }

      if(tag.hasKey("nameID")) {
         this.name = tag.getString("nameID");
      }

      if(tag.hasKey("displayName")) {
         this.displayName = tag.getString("displayName");
      }

      if(tag.hasKey("texture")) {
         this.texture = tag.getString("texture");
      }

      if(tag.hasKey("model")) {
         this.modelType = tag.getInteger("model");
      }

      if(tag.hasKey("sizeModifier")) {
         this.size = tag.getFloat("sizeModifier");
         this.resize();
      }

      if(tag.hasKey("colorMod")) {
         this.color = tag.getInteger("colorMod");
      }

      if(tag.hasKey("playerTexture")) {
         this.hasPlayerTexture = tag.getBoolean("playerTexture");
      }

      if(tag.hasKey("gender")) {
         super.isMale = tag.getBoolean("gender");
      }

      if(tag.hasKey("team")) {
         super.entityTeam = tag.getString("team");
      }

      if(tag.hasKey("isInvincible")) {
         this.isInvincible = tag.getBoolean("isInvincible");
      }

      if(tag.hasKey("targetMobs")) {
         this.targetMobs = tag.getBoolean("targetMobs");
      }

      if(tag.hasKey("teleport")) {
         this.canTeleport = tag.getBoolean("teleport");
      }

      if(tag.hasKey("voice")) {
         this.voice = tag.getInteger("voice");
      }

      if(tag.hasKey("repOnKill")) {
         this.repOnDeath = tag.getInteger("repOnKill");
      }

      if(tag.hasKey("repFriendly")) {
         this.repToAttack = tag.getInteger("repFriendly");
      }

      if(tag.hasKey("health")) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)tag.getInteger("health"));
      }

      if(tag.hasKey("speed")) {
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double)tag.getInteger("speed") * 0.001D);
      }

      int dist;
      if(tag.hasKey("homeX") && tag.hasKey("homeY") && tag.hasKey("homeZ")) {
         dist = tag.getInteger("homeX");
         int y = tag.getInteger("homeY");
         int z = tag.getInteger("homeZ");
         this.setHomeArea(dist, y, z, -1);
      }

      if(tag.hasKey("homeDist")) {
         dist = tag.getInteger("homeDist");
         this.setHomeArea(this.getHomePosition().posX, this.getHomePosition().posY, this.getHomePosition().posZ, dist);
      }

   }

   public void writeStats(NBTTagCompound tag, boolean writeAll) {
      tag.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
      tag.setBoolean("CanPickUpLoot", this.canPickUpLoot());
      tag.setString("nameID", this.name);
      tag.setString("displayName", this.displayName);
      tag.setBoolean("gender", super.isMale);
      tag.setBoolean("playerTexture", this.hasPlayerTexture);
      tag.setInteger("model", this.modelType);
      tag.setString("texture", this.texture);
      tag.setFloat("sizeModifier", this.size);
      tag.setInteger("colorMod", this.color);
      tag.setBoolean("isInvincible", this.isInvincible);
      tag.setBoolean("targetMobs", this.targetMobs);
      tag.setBoolean("teleport", this.canTeleport);
      tag.setInteger("voice", this.voice);
      tag.setInteger("repFriendly", this.repToAttack);
      tag.setInteger("repOnKill", this.repOnDeath);
      if(writeAll) {
         tag.setInteger("homeX", this.getHomePosition().posX);
         tag.setInteger("homeY", this.getHomePosition().posY);
         tag.setInteger("homeZ", this.getHomePosition().posZ);
         tag.setInteger("homeDist", (int)this.getHomeDistance());
         tag.setString("team", super.entityTeam);
         tag.setInteger("health", (int)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue());
         tag.setInteger("speed", (int)(this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 1000.0D));
      }

   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
      this.writeShop(nbttagcompound, false);
      NBTTagCompound tag;
      if(this.conversation != null) {
         tag = new NBTTagCompound();
         this.conversation.writeToNBT(tag);
         nbttagcompound.setTag("Conversation", tag);
      }

      this.writeStats(nbttagcompound, false);
      if(this.npcVariables != null) {
         nbttagcompound.setTag("Variables", this.npcVariables);
      }

      this.setTargetsMobs();
      nbttagcompound.setBoolean("inventoryLocked", super.inventoryLocked);
      if(this.timers != null) {
         NBTTagList tag2 = new NBTTagList();
         Iterator i$ = this.timers.iterator();

         while(i$.hasNext()) {
            TimeCounter counter = (TimeCounter)i$.next();
            NBTTagCompound tag1 = new NBTTagCompound();
            counter.saveToNBT(tag1);
            tag2.appendTag(tag1);
         }

         nbttagcompound.setTag("npc_timers", tag2);
      }

      this.writeAIToNBT(nbttagcompound);
      if(this.buildTask != null) {
         tag = new NBTTagCompound();
         this.buildTask.writeToNBT(tag);
         nbttagcompound.setTag("buildAI", tag);
      }

   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
      this.readShop(nbttagcompound, false);
      if(nbttagcompound.hasKey("Conversation")) {
         this.conversation = new DialogOption();
         this.conversation.readFromNBT((NBTTagCompound)nbttagcompound.getTag("Conversation"));
      }

      this.updateStats(nbttagcompound);
      if(nbttagcompound.hasKey("Variables")) {
         this.npcVariables = (NBTTagCompound)nbttagcompound.getTag("Variables");
      }

      super.inventoryLocked = nbttagcompound.getBoolean("inventoryLocked");
      if(nbttagcompound.hasKey("npc_timers")) {
         NBTTagList list = (NBTTagList)nbttagcompound.getTag("npc_timers");
         int tagCount = list.tagCount();
         this.timers = new ArrayList();

         for(int i = 0; i < tagCount; ++i) {
            this.timers.add(new TimeCounter(list.getCompoundTagAt(i)));
         }
      }

      this.readAIfromNBT(nbttagcompound);
      if(nbttagcompound.hasKey("buildAI")) {
         this.buildTask = new AIBuildSchematic(this);
         this.buildTask.readFromNBT(nbttagcompound.getCompoundTag("buildAI"));
         this.addBuildTask();
      }

   }

   public void writeAIToNBT(NBTTagCompound nbttagcompound) {
      NBTTagList list;
      if(this.AIPositions != null) {
         list = new NBTTagList();
         Iterator i$ = this.AIPositions.iterator();

         while(i$.hasNext()) {
            AIPosition pos = (AIPosition)i$.next();
            NBTTagCompound tag = new NBTTagCompound();
            pos.saveToNBT(tag);
            list.appendTag(tag);
         }

         nbttagcompound.setTag("AI_positions", list);
      }

      if(this.npcAI != null) {
         list = new NBTTagList();
         this.npcAI.writeToNBT(list);
         nbttagcompound.setTag("NpcAI", list);
      }

   }

   public void readAIfromNBT(NBTTagCompound nbttagcompound) {
      NBTBase tag;
      if(nbttagcompound.hasKey("AI_positions")) {
         tag = nbttagcompound.getTag("AI_positions");
         if(tag instanceof NBTTagList) {
            NBTTagList posList = (NBTTagList)tag;
            int tagCount = posList.tagCount();
            this.AIPositions = new ArrayList();

            for(int i = 0; i < tagCount; ++i) {
               this.AIPositions.add(new AIPosition(posList.getCompoundTagAt(i)));
            }
         }
      }

      if(nbttagcompound.hasKey("NpcAI")) {
         tag = nbttagcompound.getTag("NpcAI");
         if(tag instanceof NBTTagList) {
            this.npcAI = new NpcAI();
            this.npcAI.readFromNBT((NBTTagList)tag);
         } else {
            this.npcAI = null;
         }
      }

      if(this.npcAITask != null) {
         super.tasks.removeTask(this.npcAITask);
         this.npcAITask = null;
      }

   }

   public void writeEntityToSpawnerNBT(NBTTagCompound nbttagcompound, int spawnerX, int spawnerY, int spawnerZ) {
      Iterator i$;
      AIPosition pos;
      if(this.AIPositions != null) {
         for(i$ = this.AIPositions.iterator(); i$.hasNext(); pos.zCoord -= spawnerZ) {
            pos = (AIPosition)i$.next();
            pos.xCoord -= spawnerX;
            pos.yCoord -= spawnerY;
         }
      }

      super.writeEntityToSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
      this.writeShop(nbttagcompound, true);
      if(this.AIPositions != null) {
         for(i$ = this.AIPositions.iterator(); i$.hasNext(); pos.zCoord += spawnerZ) {
            pos = (AIPosition)i$.next();
            pos.xCoord += spawnerX;
            pos.yCoord += spawnerY;
         }
      }

   }

   public void readEntityFromSpawnerNBT(NBTTagCompound nbttagcompound, int spawnerX, int spawnerY, int spawnerZ) {
      super.readEntityFromSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
      this.readShop(nbttagcompound, true);
      AIPosition pos;
      if(this.AIPositions != null) {
         for(Iterator i$ = this.AIPositions.iterator(); i$.hasNext(); pos.zCoord += spawnerZ) {
            pos = (AIPosition)i$.next();
            pos.xCoord += spawnerX;
            pos.yCoord += spawnerY;
         }
      }

   }

   public void writeShop(NBTTagCompound nbttagcompound, boolean addMappings) {
      if(this.trades != null) {
         NBTTagList list = new NBTTagList();

         for(int i = 0; i < this.trades.length; ++i) {
            if(this.trades[i] != null) {
               NBTTagCompound tag = new NBTTagCompound();
               if(addMappings) {
                  this.trades[i].writeToNBTWithMapping(tag);
               } else {
                  this.trades[i].writeToNBT(tag);
               }

               list.appendTag(tag);
            }
         }

         nbttagcompound.setTag("trades", list);
      }

   }

   public void readShop(NBTTagCompound tag, boolean addMapings) {
      if(tag.hasKey("trades")) {
         NBTTagList list = (NBTTagList)tag.getTag("trades");
         this.trades = new ShopRecipe[list.tagCount()];

         for(int i = 0; i < list.tagCount(); ++i) {
            ShopRecipe recipe = new ShopRecipe(list.getCompoundTagAt(i), addMapings);
            this.trades[i] = recipe;
         }
      }

   }

   public void writeSpawnData(ByteBuf buffer) {
      super.writeSpawnData(buffer);
      buffer.writeInt(this.modelType);
      buffer.writeFloat(this.size);
      buffer.writeInt(this.color);
      buffer.writeBoolean(super.isMale);
      buffer.writeBoolean(this.hasPlayerTexture);
      buffer.writeInt(this.texture.length());

      for(int i = 0; i < this.texture.length(); ++i) {
         buffer.writeChar(this.texture.charAt(i));
      }

      PacketBase.writeString(buffer, this.displayName);
      PacketBase.writeString(buffer, super.entityTeam);
   }

   public void readSpawnData(ByteBuf additionalData) {
      super.readSpawnData(additionalData);
      this.modelType = additionalData.readInt();
      this.size = additionalData.readFloat();
      this.color = additionalData.readInt();
      super.isMale = additionalData.readBoolean();
      this.hasPlayerTexture = additionalData.readBoolean();
      int texLength = additionalData.readInt();
      this.texture = "";

      for(int i = 0; i < texLength; ++i) {
         this.texture = this.texture + additionalData.readChar();
      }

      this.displayName = PacketBase.readString(additionalData);
      super.entityTeam = PacketBase.readString(additionalData);
      this.resize();
   }

   public void resize() {
      float sizeBase = 1.8F;
      if(this.modelType == EnumRace.DWARF.ordinal()) {
         sizeBase = 1.4F;
      }

      super.height = sizeBase * (0.5F + this.size);
      super.width = 0.6F * (0.5F + this.size);
   }

   public boolean isSuitableTargetAlly(EntityLivingBase entity) {
      if(entity instanceof EntityPlayer) {
         if(super.entityTeam.equals("npc")) {
            return true;
         } else if(super.entityTeam.startsWith("mob_")) {
            return false;
         } else if(entity.getTeam() != null && !this.isOnAlliedTeam(entity.getTeam())) {
            return false;
         } else {
            String teamName = super.entityTeam.replace("npc_", "");
            return ReputationManager.instance.getPlayerReputation(entity.getCommandSenderName(), teamName) >= this.repToAttack;
         }
      } else {
         return super.isSuitableTargetAlly(entity);
      }
   }

   public void onDeath(DamageSource damageSource) {
      if(this.repOnDeath > 0 && damageSource.getSourceOfDamage() instanceof EntityPlayer && !super.entityTeam.equals("npc") && !super.entityTeam.startsWith("mob")) {
         EntityPlayer player = (EntityPlayer)damageSource.getSourceOfDamage();
         if(!player.capabilities.isCreativeMode) {
            String team = super.entityTeam.replace("npc_", "");
            ReputationManager.instance.addReputation(player, team, -this.repOnDeath);
         }
      }

      super.onDeath(damageSource);
   }

   public boolean canBePushed() {
      return !this.isInvincible;
   }

   public float getSizeModifier() {
      return 1.0F;
   }

   public boolean canTeleport() {
      return this.canTeleport;
   }

   public int getInteligence() {
      return 2;
   }

   protected String getLivingSound() {
      return EnumVoice.getVoice(this.voice).say;
   }

   protected String getHurtSound() {
      return EnumVoice.getVoice(this.voice).hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.getVoice(this.voice).death;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return this.modelType != EnumRace.SKELETON.ordinal() && this.modelType != EnumRace.SPECTER.ordinal()?EnumCreatureAttribute.UNDEFINED:EnumCreatureAttribute.UNDEAD;
   }

   public int getTimeCounter(String name) {
      if(this.timers == null) {
         return 0;
      } else {
         Iterator i$ = this.timers.iterator();

         TimeCounter counter;
         do {
            if(!i$.hasNext()) {
               return 0;
            }

            counter = (TimeCounter)i$.next();
         } while(!counter.name.equals(name));

         return counter.time;
      }
   }

   public void setTimeCounter(String name, int value) {
      if(this.timers == null) {
         this.timers = new ArrayList();
      }

      Iterator i$ = this.timers.iterator();

      TimeCounter counter;
      do {
         if(!i$.hasNext()) {
            this.timers.add(new TimeCounter(name, value));
            return;
         }

         counter = (TimeCounter)i$.next();
      } while(!counter.name.equals(name));

      counter.time = value;
   }
}
