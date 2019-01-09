package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.DummyChocolate;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.entity.ai.AIAnimalMountedByEntity;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFollowOwner;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFormation;
import com.chocolate.chocolateQuest.entity.ai.AIControlledPath;
import com.chocolate.chocolateQuest.entity.ai.AIControlledSit;
import com.chocolate.chocolateQuest.entity.ai.AIControlledWardPosition;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressive;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressiveBackstab;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackDefensive;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackEvasive;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackHeal;
import com.chocolate.chocolateQuest.entity.ai.AIHumanFlee;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import com.chocolate.chocolateQuest.entity.ai.AIHumanIdleSit;
import com.chocolate.chocolateQuest.entity.ai.AIHumanIdleTalkClosest;
import com.chocolate.chocolateQuest.entity.ai.AIHumanMount;
import com.chocolate.chocolateQuest.entity.ai.AIHumanPanic;
import com.chocolate.chocolateQuest.entity.ai.AIHumanPotion;
import com.chocolate.chocolateQuest.entity.ai.AIHumanReturnHome;
import com.chocolate.chocolateQuest.entity.ai.AILavaSwim;
import com.chocolate.chocolateQuest.entity.ai.AISpeakToPlayer;
import com.chocolate.chocolateQuest.entity.ai.AITargetNearestHurtAlly;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.entity.ai.AITargetParty;
import com.chocolate.chocolateQuest.entity.ai.EntityParty;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.handHelper.HandEmpty;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.MobTeam;
import com.chocolate.chocolateQuest.utils.Vec4I;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityHumanBase extends EntityCreature implements IEntityAdditionalSpawnData, IEntityOwnable, IElementWeak {

   public static final byte ANIM = 16;
   public static final byte HUMAN = 0;
   public static final byte ORC = 1;
   public static final byte GOBLIN = 2;
   public static final byte DWARF = 3;
   public static final byte TRITON = 4;
   public int maxStamina = 60;
   protected float randomHeightVariation;
   public boolean isMale = true;
   public int leftHandSwing = 0;
   public float moveForwardHuman = 0.0F;
   protected int sprintTime;
   protected int exhaustion;
   public int potionCount = 1;
   public ItemStack leftHandItem;
   public float leftHandDropChances = 0.0F;
   public short parryRate = 0;
   public short blockRate = 0;
   protected final float shiedBlockDefense = 0.8F;
   public float accuracy;
   public String entityTeam = "npc";
   public int shieldID = 0;
   protected String ownerName;
   private EntityLivingBase owner;
   public EntityParty party;
   public int partyPositionAngle;
   public int partyDistanceToLeader = 2;
   public boolean partyPositionPersistance = false;
   public boolean addedToParty = false;
   protected EntityAIBase attackAI;
   protected EntityAIBase controlledAI;
   protected EntityAIBase supportAI;
   protected EntityAIBase supportAITarget;
   public int AIMode;
   public int AICombatMode;
   public Vec4I currentPos;
   public Vec4I AIPosition;
   public Vec4I[] path;
   public HandHelper leftHand;
   public HandHelper rightHand;
   private static final AttributeModifier slowDownModifier = (new AttributeModifier("Human speed mod", -0.2D, 1)).setSaved(false);
   public EntityPlayer playerSpeakingTo;
   public boolean updateOwner;
   protected boolean shouldDespawn;
   public boolean inventoryLocked;
   public EntityLivingBase entityToMount;
   public int alertTime;
   public int panic;
   protected int physicDefense;
   protected int magicDefense;
   protected int blastDefense;
   protected int fireDefense;
   protected int projectileDefense;


   public EntityHumanBase(World world) {
      super(world);
      this.AIMode = EnumAiState.FORMATION.ordinal();
      this.AICombatMode = EnumAiCombat.OFFENSIVE.ordinal();
      this.updateOwner = false;
      this.shouldDespawn = true;
      this.inventoryLocked = false;
      this.physicDefense = 0;
      this.magicDefense = 0;
      this.blastDefense = 0;
      this.fireDefense = 0;
      this.projectileDefense = 0;
      this.addAITasks();
      super.stepHeight = 1.0F;
      this.updateHands();

      for(int i = 0; i < super.equipmentDropChances.length; ++i) {
         super.equipmentDropChances[i] = 0.0F;
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
      this.setSize(0.6F, 1.8F);
      this.randomHeightVariation = 1.0F + (super.rand.nextFloat() - 0.6F) / 5.0F;
   }

   protected boolean isAIEnabled() {
      return true;
   }

   protected void addAITasks() {
      this.getNavigator().setEnterDoors(true);
      this.getNavigator().setBreakDoors(true);
      super.tasks.addTask(0, new AILavaSwim(this));
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new AISpeakToPlayer(this));
      super.tasks.addTask(1, new AIHumanPotion(this));
      super.tasks.addTask(1, new AIHumanGoToPoint(this));
      super.tasks.addTask(1, new AIHumanMount(this, 1.0F, false));
      super.tasks.addTask(2, new EntityAIOpenDoor(this, true));
      super.tasks.addTask(6, new AIHumanPanic(this, 1.0D));
      this.setAIForCurrentMode();
      super.tasks.addTask(10, new AIHumanIdleTalkClosest(this, EntityHumanBase.class, 8.0F));
      super.tasks.addTask(11, new AIHumanIdleSit(this));
      super.tasks.addTask(9, new AIHumanReturnHome(this));
      super.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      super.targetTasks.addTask(2, new AITargetParty(this));
      super.targetTasks.addTask(3, new AITargetOwner(this));
      super.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, new HumanSelector(this)));
      super.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityHumanBase.class, 0, true, false, new HumanSelector(this)));
   }

   public void updateHands() {
      this.rightHand = HandHelper.getHandHelperForItem(this, this.getEquipmentInSlot(0));
      this.leftHand = HandHelper.getHandHelperForItem(this, this.getLeftHandItem());
      if(this.rightHand instanceof HandEmpty) {
         this.rightHand = new HandHelper(this, (ItemStack)null);
      }

   }

   public void setAIForCurrentMode() {
      this.updateHands();
      if(this.supportAI != null) {
         super.tasks.removeTask(this.supportAI);
         super.targetTasks.removeTask(this.supportAITarget);
      }

      if(this.isHealer()) {
         this.supportAI = new AIHumanAttackHeal(this, 1.0F, false);
         super.tasks.addTask(3, this.supportAI);
         this.supportAITarget = new AITargetNearestHurtAlly(this, EntityLivingBase.class);
         super.targetTasks.addTask(0, this.supportAITarget);
      }

      if(this.controlledAI != null) {
         super.tasks.removeTask(this.controlledAI);
      }

      byte priority = 8;
      if(this.AIMode == EnumAiState.FOLLOW.ordinal()) {
         this.controlledAI = new AIControlledFollowOwner(this, 5.0F, 25.0F);
      } else if(this.AIMode == EnumAiState.FORMATION.ordinal()) {
         this.controlledAI = new AIControlledFormation(this);
      } else if(this.AIMode == EnumAiState.PATH.ordinal()) {
         this.controlledAI = new AIControlledPath(this);
      } else if(this.AIMode == EnumAiState.WARD.ordinal()) {
         this.controlledAI = new AIControlledWardPosition(this);
      } else if(this.AIMode == EnumAiState.SIT.ordinal()) {
         this.controlledAI = new AIControlledSit(this);
         priority = 2;
      } else if(this.AIMode == EnumAiState.WANDER.ordinal()) {
         this.controlledAI = new EntityAIWander(this, 1.0D);
      } else if(this.AIMode == EnumAiState.SIT.ordinal()) {
         this.controlledAI = new AIControlledSit(this, true);
         priority = 2;
      }

      if(this.controlledAI != null) {
         super.tasks.addTask(priority, this.controlledAI);
      }

      if(this.attackAI != null) {
         super.tasks.removeTask(this.attackAI);
      }

      if(this.AICombatMode == EnumAiCombat.OFFENSIVE.ordinal()) {
         this.attackAI = new AIHumanAttackAggressive(this, EntityLivingBase.class, 1.0F, false);
      } else if(this.AICombatMode == EnumAiCombat.DEFENSIVE.ordinal()) {
         this.attackAI = new AIHumanAttackDefensive(this, 1.0F);
      } else if(this.AICombatMode == EnumAiCombat.EVASIVE.ordinal()) {
         this.attackAI = new AIHumanAttackEvasive(this, 1.0F);
      } else if(this.AICombatMode == EnumAiCombat.FLEE.ordinal()) {
         this.attackAI = new AIHumanFlee(this, 1.0F);
      } else if(this.AICombatMode == EnumAiCombat.BACKSTAB.ordinal()) {
         this.attackAI = new AIHumanAttackAggressiveBackstab(this, 1.0F, false);
      }

      if(this.attackAI != null) {
         super.tasks.addTask(3, this.attackAI);
      }

   }

   public void onLivingUpdate() {
      if(this.party != null && this.party.getLeader() == this) {
         this.party.update();
      }

      if(this.haveShied()) {
         IAttributeInstance i = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
         i.removeModifier(slowDownModifier);
         if(this.isDefending()) {
            i.applyModifier(slowDownModifier);
         }
      }

      if(!super.worldObj.isRemote) {
         if(this.isSprinting()) {
            --this.sprintTime;
            if(this.sprintTime <= 0) {
               this.setSprinting(false);
            }

            this.exhaustion = this.getExhaustionOnStopSprint();
         } else if(this.exhaustion > 0) {
            --this.exhaustion;
         }

         if(this.alertTime > 0) {
            --this.alertTime;
         }
      }

      for(int var3 = 0; var3 < 4; ++var3) {
         ItemStack is = this.getEquipmentInSlot(var3 + 1);
         if(is != null && is.getItem() instanceof ItemArmorBase) {
            ((ItemArmorBase)is.getItem()).onUpdateEquiped(is, super.worldObj, this);
         }
      }

      this.rightHand.onUpdate();
      this.leftHand.onUpdate();
      this.updateArmSwingProgress();
      super.onLivingUpdate();
      if(this.isSitting() && super.ticksExisted % 100 == 0) {
         this.heal(1.0F);
      }

      if(this.isSleeping() && super.ticksExisted % 10 == 0) {
         this.heal(1.0F);
      }

   }

   protected void updateArmSwingProgress() {
      super.updateArmSwingProgress();
      if(this.leftHandSwing > 0) {
         --this.leftHandSwing;
      }

   }

   public void swingLeftHand() {
      if(this.leftHandSwing <= 0) {
         this.leftHandSwing = 10;
         if(super.worldObj instanceof WorldServer) {
            PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)1);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
         }
      }

   }

   public void swingHand(HandHelper hand) {
      if(hand == this.rightHand) {
         this.swingItem();
      } else {
         this.swingLeftHand();
      }

   }

   public boolean isSwingInProgress(HandHelper hand) {
      return hand == this.rightHand?super.isSwingInProgress:this.leftHandSwing > 0;
   }

   public void setAiming(HandHelper hand, boolean aiming) {
      if(hand.isAiming() != aiming) {
         if(hand == this.rightHand) {
            this.toogleAimRight();
         } else {
            this.toogleAimLeft();
         }
      }

   }

   public boolean isAiming() {
      return this.rightHand.isAiming() || this.leftHand.isAiming();
   }

   public void stopAiming() {
      if(this.rightHand.isAiming()) {
         this.toogleAimRight();
      }

      if(this.leftHand.isAiming()) {
         this.toogleAimLeft();
      }

   }

   public void toogleAimRight() {
      this.rightHand.setAiming(!this.rightHand.isAiming());
      if(super.worldObj instanceof WorldServer) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)2);
         ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
      }

   }

   public void toogleAimLeft() {
      this.leftHand.setAiming(!this.leftHand.isAiming());
      if(super.worldObj instanceof WorldServer) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)3);
         ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
      }

   }

   public boolean writeToNBTOptional(NBTTagCompound nbttagcompound) {
      return this.party != null && !this.writePartyToNBT(nbttagcompound)?false:super.writeToNBTOptional(nbttagcompound);
   }

   public boolean writePartyToNBT(NBTTagCompound nbttagcompound) {
      if(this.party != null && this.party.getLeader().isEntityEqual(this)) {
         this.party.saveToNBT(nbttagcompound);
         return true;
      } else {
         return false;
      }
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
      nbttagcompound.setInteger("PotionCount", this.potionCount);
      if(this.getLeftHandItem() != null) {
         NBTTagCompound coords = new NBTTagCompound();
         nbttagcompound.setTag("leftHand", this.getLeftHandItem().writeToNBT(coords));
      }

      nbttagcompound.setFloat("dropLeft", this.leftHandDropChances);
      if(this.owner instanceof EntityPlayer) {
         nbttagcompound.setString("ownerName", this.ownerName);
      } else if(this.ownerName != null) {
         nbttagcompound.setString("ownerName", this.ownerName);
      }

      nbttagcompound.setString("team", this.entityTeam);
      if(this.partyPositionPersistance) {
         nbttagcompound.setInteger("leaderDist", this.partyDistanceToLeader);
         nbttagcompound.setInteger("partyPos", this.partyPositionAngle);
      }

      nbttagcompound.setInteger("AIMode", this.AIMode);
      if(this.AIPosition != null) {
         nbttagcompound.setBoolean("standing", true);
         nbttagcompound.setInteger("standX", this.AIPosition.xCoord);
         nbttagcompound.setInteger("standY", this.AIPosition.yCoord);
         nbttagcompound.setInteger("standZ", this.AIPosition.zCoord);
         nbttagcompound.setInteger("standRotation", this.AIPosition.rot);
      }

      if(this.path != null) {
         nbttagcompound.setInteger("pathPoints", this.path.length);

         for(int var3 = 0; var3 < this.path.length; ++var3) {
            nbttagcompound.setInteger("pathX" + var3, this.path[var3].xCoord);
            nbttagcompound.setInteger("pathY" + var3, this.path[var3].yCoord);
            nbttagcompound.setInteger("pathZ" + var3, this.path[var3].zCoord);
         }
      }

      nbttagcompound.setInteger("AIMode", this.AIMode);
      nbttagcompound.setInteger("AICombat", this.AICombatMode);
      nbttagcompound.setBoolean("addedToParty", this.addedToParty);
      nbttagcompound.setBoolean("Despawn", this.shouldDespawn);
      ChunkCoordinates var4 = this.getHomePosition();
      nbttagcompound.setInteger("homeX", var4.posX);
      nbttagcompound.setInteger("homeY", var4.posY);
      nbttagcompound.setInteger("homeZ", var4.posZ);
      nbttagcompound.setInteger("homeDist", (int)this.getHomeDistance());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
      if(nbttagcompound.hasKey("PotionCount")) {
         this.potionCount = nbttagcompound.getInteger("PotionCount");
      }

      if(nbttagcompound.hasKey("dropLeft")) {
         this.leftHandDropChances = nbttagcompound.getFloat("dropLeft");
      }

      if(nbttagcompound.hasKey("leftHand")) {
         NBTTagCompound x = (NBTTagCompound)nbttagcompound.getTag("leftHand");
         if(x != null) {
            this.leftHandItem = ItemStack.loadItemStackFromNBT(x);
         } else {
            this.leftHandItem = null;
         }
      }

      if(nbttagcompound.hasKey("team")) {
         this.entityTeam = nbttagcompound.getString("team");
      }

      if(nbttagcompound.hasKey("ownerName")) {
         this.ownerName = nbttagcompound.getString("ownerName");
         if(this.ownerName == "") {
            this.ownerName = null;
         }

         if(this.ownerName != null) {
            this.setOwner(super.worldObj.getPlayerEntityByName(this.ownerName));
         }
      }

      if(nbttagcompound.hasKey("leaderDist")) {
         this.partyPositionPersistance = true;
         this.partyDistanceToLeader = nbttagcompound.getInteger("leaderDist");
      }

      if(nbttagcompound.hasKey("partyPos")) {
         this.partyPositionPersistance = true;
         this.partyPositionAngle = nbttagcompound.getInteger("partyPos");
      }

      int y;
      int z;
      int f;
      int var6;
      if(nbttagcompound.hasKey("standing") && nbttagcompound.getBoolean("standing")) {
         var6 = nbttagcompound.getInteger("standX");
         y = nbttagcompound.getInteger("standY");
         z = nbttagcompound.getInteger("standZ");
         f = nbttagcompound.getInteger("standRotation");
         this.AIPosition = new Vec4I(var6, y, z, f);
      }

      if(nbttagcompound.hasKey("pathPoints")) {
         var6 = nbttagcompound.getInteger("pathPoints");
         if(var6 > 0) {
            this.path = new Vec4I[var6];

            for(y = 0; y < var6; ++y) {
               this.path[y] = new Vec4I(nbttagcompound.getInteger("pathX" + y), nbttagcompound.getInteger("pathY" + y), nbttagcompound.getInteger("pathZ" + y), 0);
            }
         }
      }

      if(nbttagcompound.hasKey("AIMode")) {
         this.AIMode = nbttagcompound.getInteger("AIMode");
      }

      if(nbttagcompound.hasKey("AICombat")) {
         this.AICombatMode = nbttagcompound.getInteger("AICombat");
      }

      this.setAIForCurrentMode();
      if(nbttagcompound.hasKey("Party") && nbttagcompound.getTag("Party") != null && this.party == null) {
         this.party = new EntityParty();
         this.party.tryToAddNewMember(this);
         this.party.readFromNBT(nbttagcompound);
      }

      if(nbttagcompound.hasKey("addedToParty")) {
         this.addedToParty = nbttagcompound.getBoolean("addedToParty");
      }

      if(nbttagcompound.hasKey("Despawn")) {
         this.shouldDespawn = nbttagcompound.getBoolean("Despawn");
      }

      var6 = nbttagcompound.getInteger("homeX");
      y = nbttagcompound.getInteger("homeY");
      z = nbttagcompound.getInteger("homeZ");
      f = -1;
      if(nbttagcompound.hasKey("homeDist")) {
         f = nbttagcompound.getInteger("homeDist");
      }

      this.setHomeArea(var6, y, z, f);
   }

   public void writeEntityToSpawnerNBT(NBTTagCompound nbttagcompound, int spawnerX, int spawnerY, int spawnerZ) {
      this.writePartyToNBT(nbttagcompound);
      Vec4I currentStand = this.AIPosition;
      Vec4I[] currentPath = this.path;
      double tempPosX = super.posX;
      double tempPosY = super.posY;
      double tempPosZ = super.posZ;
      super.posX -= (double)spawnerX;
      super.posY -= (double)spawnerY;
      super.posZ -= (double)spawnerZ;
      if(this.AIPosition != null) {
         this.AIPosition = new Vec4I(currentStand.xCoord - spawnerX, currentStand.yCoord - spawnerY, currentStand.zCoord - spawnerZ, currentStand.rot);
      }

      if(this.path != null) {
         this.path = new Vec4I[this.path.length];

         for(int coords = 0; coords < this.path.length; ++coords) {
            this.path[coords] = new Vec4I(currentPath[coords].xCoord - spawnerX, currentPath[coords].yCoord - spawnerY, currentPath[coords].zCoord - spawnerZ, currentPath[coords].rot);
         }
      }

      ChunkCoordinates var14 = this.getHomePosition();
      this.setHomeArea(var14.posX - spawnerX, var14.posY - spawnerY, var14.posZ - spawnerZ, (int)this.getHomeDistance());
      this.writeEntityToNBT(nbttagcompound);
      if(super.ridingEntity != null) {
         this.writeMountToNBT(nbttagcompound);
      }

      this.setHomeArea(var14.posX, var14.posY, var14.posZ, (int)this.getHomeDistance());
      super.posX += (double)spawnerX;
      super.posY += (double)spawnerY;
      super.posZ += (double)spawnerZ;
      this.AIPosition = currentStand;
      this.path = currentPath;
      this.saveMappingsToNBT(nbttagcompound);
   }

   public void saveMappingsToNBT(NBTTagCompound nbttagcompound) {
      NBTTagList list = new NBTTagList();

      for(int s = 0; s < 5; ++s) {
         String s1 = "";
         if(this.getEquipmentInSlot(s) != null) {
            s1 = Item.itemRegistry.getNameForObject(this.getEquipmentInSlot(s).getItem());
         }

         list.appendTag(new NBTTagString(s1));
      }

      String var5 = "";
      if(this.getLeftHandItem() != null) {
         var5 = Item.itemRegistry.getNameForObject(this.getLeftHandItem().getItem());
      }

      list.appendTag(new NBTTagString(var5));
      nbttagcompound.setTag("EquipementMap", list);
   }

   public void readEntityFromSpawnerNBT(NBTTagCompound nbttagcompound, int spawnerX, int spawnerY, int spawnerZ) {
      NBTBase equipementMap = nbttagcompound.getTag("EquipementMap");
      int f;
      if(equipementMap != null) {
         NBTBase x = nbttagcompound.getTag("Equipment");
         if(x != null) {
            NBTTagList y = (NBTTagList)x;
            NBTTagList z = (NBTTagList)equipementMap;

            for(f = 0; f < y.tagCount(); ++f) {
               short itemNBT = y.getCompoundTagAt(f).getShort("id");
               if(itemNBT != 0) {
                  Item id = (Item)Item.itemRegistry.getObject(z.getStringTagAt(f));
                  if(id != null) {
                     short item = (short)Item.getIdFromItem(id);
                     y.getCompoundTagAt(f).setShort("id", item);
                  }
               }
            }

            boolean var19 = true;
            NBTTagCompound var21 = (NBTTagCompound)nbttagcompound.getTag("leftHand");
            if(var21 != null) {
               short var20 = var21.getShort("id");
               if(var20 != 0) {
                  Item var22 = (Item)Item.itemRegistry.getObject(z.getStringTagAt(5));
                  if(var22 != null) {
                     short newID = (short)Item.getIdFromItem(var22);
                     var21.setShort("id", newID);
                  }
               }
            }
         }
      }

      this.readFromNBT(nbttagcompound);
      if(this.AIPosition != null) {
         Vec4I var15 = this.AIPosition;
         this.AIPosition = new Vec4I(var15.xCoord + spawnerX, var15.yCoord + spawnerY, var15.zCoord + spawnerZ, var15.rot);
      }

      int var14;
      if(this.path != null) {
         for(var14 = 0; var14 < this.path.length; ++var14) {
            Vec4I var16 = this.path[var14];
            this.path[var14] = new Vec4I(var16.xCoord + spawnerX, var16.yCoord + spawnerY, var16.zCoord + spawnerZ, var16.rot);
         }
      }

      var14 = nbttagcompound.getInteger("homeX") + spawnerX;
      int var17 = nbttagcompound.getInteger("homeY") + spawnerY;
      int var18 = nbttagcompound.getInteger("homeZ") + spawnerZ;
      f = nbttagcompound.getInteger("homeDist");
      this.setHomeArea(var14, var17, var18, f);
      super.posX += (double)spawnerX;
      super.posY += (double)spawnerY;
      super.posZ += (double)spawnerZ;
      this.shouldDespawn = false;
   }

   public void setAttackTarget(EntityLivingBase par1EntityLivingBase) {
      if(par1EntityLivingBase != null) {
         if(!this.isOnSameTeam(par1EntityLivingBase)) {
            if(this.party != null) {
               this.party.addAggroToTarget(par1EntityLivingBase, 1);
            }
         } else if(!this.isHealer() && !this.isSuitableMount(par1EntityLivingBase)) {
            return;
         }
      } else if(this.getAITarget() != null && this.getAITarget() != this.getAttackTarget()) {
         this.setAttackTarget(this.getAITarget());
         return;
      }

      this.alert(100);
      super.setAttackTarget(par1EntityLivingBase);
   }

   protected void despawnEntity() {
      super.despawnEntity();
   }

   protected boolean canDespawn() {
      return this.shouldDespawn;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public double getMountedYOffset() {
      return super.ridingEntity instanceof EntityHorse?-0.4D:super.getMountedYOffset();
   }

   public ItemStack getLeftHandItem() {
      return this.leftHandItem;
   }

   public void setLeftHandItem(ItemStack is) {
      this.leftHandItem = is;
   }

   public void moveEntityWithHeading(float par1, float par2) {
      if(this.moveForwardHuman != 0.0F) {
         par2 = this.moveForwardHuman;
      }

      super.moveEntityWithHeading(par1, par2);
   }

   public float getSizeModifier() {
      return this.randomHeightVariation;
   }

   public boolean attackEntityFrom(DamageSource damagesource, float damage) {
      this.alert(60);
      boolean rangedAttack = damagesource.isProjectile();
      double entity;
      if(!rangedAttack && damagesource.getEntity() instanceof EntityLivingBase) {
         entity = this.getDistanceSqToEntity(damagesource.getEntity());
         if(entity > 36.0D) {
            rangedAttack = true;
         }
      }

      if(this.isDefending()) {
         entity = 0.0D;
         if(damagesource.getEntity() != null) {
            for(entity = (double)(damagesource.getEntity().rotationYaw - super.rotationYaw); entity > 360.0D; entity -= 360.0D) {
               ;
            }

            while(entity < 0.0D) {
               entity += 360.0D;
            }

            entity = Math.abs(entity - 180.0D);
         }

         if(entity < 120.0D && !damagesource.isUnblockable()) {
            if(super.hurtTime == 0) {
               super.worldObj.playSoundAtEntity(this, "mob.blaze.hit", 1.0F, 1.0F);
            }

            ItemStack weapon = this.getHeldItem();
            if(weapon != null && weapon.getItem() instanceof ItemSwordAndShieldBase && damagesource.getEntity() instanceof EntityLivingBase) {
               ((ItemSwordAndShieldBase)weapon.getItem()).onBlock(this, (EntityLivingBase)damagesource.getEntity(), damagesource);
            }

            if(rangedAttack) {
               return true;
            }

            int blockRate = this.blockRate + Awakements.getEnchantLevel(this.getHeldItem(), Awakements.blockStamina) * 20 + (damagesource.isProjectile()?50:0);
            int parryRate = this.blockRate + Awakements.getEnchantLevel(this.getHeldItem(), Awakements.parryDamage) * 20;
            if(super.rand.nextInt(100) < blockRate) {
               if(super.rand.nextInt(100) < parryRate && damagesource.getEntity() instanceof EntityLivingBase && !rangedAttack) {
                  this.attackEntityAsMob(damagesource.getEntity());
                  this.swingLeftHand();
               }

               super.hurtTime = 10;
               return false;
            }

            damage *= 0.8F;
         }
      } else if(this.haveShied()) {
         this.toogleBlocking();
      }

      if(damagesource.getEntity() instanceof EntityLivingBase) {
         EntityLivingBase entity1 = (EntityLivingBase)damagesource.getEntity();
         if(!super.worldObj.isRemote && !this.isOnSameTeam(entity1)) {
            if(this.getAttackTarget() != null) {
               if(this.getDistanceToEntity(this.getAttackTarget()) > this.getDistanceToEntity(entity1)) {
                  this.setAttackTarget(entity1);
               } else {
                  this.setRevengeTarget(entity1);
               }
            } else {
               this.setRevengeTarget(entity1);
            }

            if(this.party != null) {
               this.party.addAggroToTarget(entity1, (int)damage);
            }
         }

         if(this.isAiming() && this.canAimBeCanceled() && !damagesource.isProjectile()) {
            super.attackTime = this.getAttackSpeed();
            this.stopAiming();
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(slowDownModifier);
         }
      }

      return super.attackEntityFrom(damagesource, damage);
   }

   public void applyEntityCollision(Entity par1Entity) {
      if(!super.worldObj.isRemote && this.getLeader() != null && par1Entity instanceof EntityHumanBase && !((EntityHumanBase)par1Entity).isOnSameTeam(this)) {
         this.setAttackTarget((EntityLivingBase)par1Entity);
      }

      super.applyEntityCollision(par1Entity);
   }

   public boolean attackEntityWithRangedAttack(Entity entity, float f) {
      boolean flagRight = false;
      boolean flagLeft = false;
      if(this.leftHand.isRanged()) {
         flagLeft = this.leftHand.attackWithRange(entity, f);
      }

      if(this.rightHand.isRanged()) {
         flagRight = this.rightHand.attackWithRange(entity, f);
      }

      return flagLeft || flagRight;
   }

   public void attackEntity(EntityLivingBase entity) {
      if(entity.hurtTime <= 0) {
         if(this.rightHand.attackTime <= 0) {
            this.rightHand.attackEntity(entity);
         } else if(this.leftHand.attackTime <= 0) {
            this.leftHand.attackEntity(entity);
         }

         if(this.haveShied() && this.isDefending()) {
            this.setDefending(false);
         }
      }

   }

   public boolean attackEntityAsMob(Entity entity) {
      return this.attackEntityAsMob(entity, this.getEquipmentInSlot(0));
   }

   public boolean attackEntityAsMob(Entity entity, ItemStack weapon) {
      float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      if(weapon != this.getEquipmentInSlot(0)) {
         damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();
         damage = (float)((double)damage + BDHelper.getWeaponDamage(weapon));
      }

      if(entity instanceof EntityCreeper) {
         damage += 1000.0F;
      }

      float knockBackAmount = 0.0F;
      if(weapon != null) {
         if(weapon.getItem() instanceof ItemBaseDagger) {
            float flag;
            for(flag = super.rotationYaw - entity.rotationYaw; flag > 360.0F; flag -= 360.0F) {
               ;
            }

            while(flag < 0.0F) {
               flag += 360.0F;
            }

            flag = Math.abs(flag - 180.0F);
            if(Math.abs(flag) > 130.0F) {
               damage *= ((ItemBaseDagger)weapon.getItem()).getBackStabModifier(weapon);
               if(!super.worldObj.isRemote) {
                  PacketSpawnParticlesAround j = new PacketSpawnParticlesAround((byte)1, entity.posX, entity.posY + 1.0D, entity.posZ);
                  ChocolateQuest.channel.sendToAllAround(entity, j, 64);
               }
            }
         }

         if(weapon.getItem() instanceof ItemBaseSpear && super.rand.nextInt(3) == 0) {
            knockBackAmount = 1.3F;
            if(entity.ridingEntity != null && super.rand.nextInt(6) == 0) {
               entity.mountEntity((Entity)null);
            }
         }
      }

      boolean flag1 = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
      if(flag1 && weapon != null) {
         int j1 = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, weapon);
         if(j1 > 0) {
            entity.addVelocity((double)(-MathHelper.sin(super.rotationYaw * 3.1415927F / 180.0F) * (float)j1 * 0.5F), 0.1D, (double)(MathHelper.cos(super.rotationYaw * 3.1415927F / 180.0F) * (float)j1 * 0.5F));
            super.motionX *= 0.6D;
            super.motionZ *= 0.6D;
         }

         int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, weapon);
         if(k > 0) {
            entity.setFire(k * 4);
         }
      }

      if(knockBackAmount > 0.0F) {
         entity.addVelocity((double)(-MathHelper.sin(super.rotationYaw * 3.1415927F / 180.0F) * knockBackAmount * 0.5F), 0.1D, (double)(MathHelper.cos(super.rotationYaw * 3.1415927F / 180.0F) * knockBackAmount * 0.5F));
      }

      return flag1;
   }

   public boolean canAttackClass(Class par1Class) {
      return EntityGhast.class != par1Class && par1Class != EntityReferee.class;
   }

   public float getEyeHeight() {
      return this.isSitting()?super.getEyeHeight() - 0.5F:super.getEyeHeight();
   }

   public double getDistanceToAttack() {
      return Math.max(this.rightHand.getDistanceToStopAdvancing(), this.leftHand.getDistanceToStopAdvancing());
   }

   protected boolean interact(EntityPlayer player) {
      if(player.getCurrentEquippedItem() != null) {
         ItemStack is = player.getCurrentEquippedItem();
         if(is.getItem() == Items.name_tag || is.getItem() instanceof ItemController) {
            return super.interact(player);
         }
      }

      if(this.playerSpeakingTo == null) {
         this.playerSpeakingTo = player;
         this.openEquipement(player);
         return true;
      } else {
         return false;
      }
   }

   public void openEquipement(EntityPlayer player) {
      if(super.worldObj instanceof WorldServer) {
         this.updateOwner = this.getOwner() == this.playerSpeakingTo;
         IMessage packet = this.getEntityGUIUpdatePacket(player);
         ChannelHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)player);
      }

      if(!super.worldObj.isRemote) {
         player.openGui("chocolateQuest", 0, super.worldObj, this.getEntityId(), 0, 0);
      }

   }

   public IMessage getEntityGUIUpdatePacket(EntityPlayer player) {
      return new PacketUpdateHumanData(this);
   }

   public float getEquipmentDropChances(int i) {
      return super.equipmentDropChances[i];
   }

   public void setEquipmentDropChances(int i, float value) {
      super.equipmentDropChances[i] = value;
   }

   public boolean isWithinHomeDistance(int x, int y, int z) {
      if(this.ownerName != null) {
         return true;
      } else if(this.AIPosition != null) {
         float maximumHomeDistance = this.getHomeDistance();
         return maximumHomeDistance == -1.0F?true:this.getDistanceSq((double)this.AIPosition.xCoord, (double)this.AIPosition.yCoord, (double)this.AIPosition.zCoord) < (double)(maximumHomeDistance * maximumHomeDistance);
      } else {
         return super.isWithinHomeDistance(x, y, z);
      }
   }

   public float getHomeDistance() {
      return this.func_110174_bM();
   }

   public void onDeath(DamageSource damageSource) {
      super.onDeath(damageSource);
      if(this.party != null) {
         this.party.removeMember(this);
      }

      if(this.getLeftHandItem() != null && !super.worldObj.isRemote && this.leftHandDropChances > 0.0F && super.rand.nextFloat() <= this.leftHandDropChances) {
         ItemStack is = this.getLeftHandItem();
         EntityItem eItem = new EntityItem(super.worldObj, super.posX, super.posY, super.posZ, is);
         super.worldObj.spawnEntityInWorld(eItem);
      }

   }

   public void onSpawn() {
      this.updateHands();
   }

   public int getHandAngle(HandHelper hand) {
      return hand == this.leftHand?-30:30;
   }

   public boolean canSee(EntityLivingBase entity) {
      boolean flag = this.isAlarmed();
      if(!flag) {
         if(entity.ridingEntity == null && !entity.isSprinting()) {
            double rotDiff = BDHelper.getAngleBetweenEntities(this, entity);
            double entityRot = MathHelper.wrapAngleTo180_double((double)super.rotationYawHead);
            double rot = MathHelper.wrapAngleTo180_double(entityRot - rotDiff);
            rot = Math.abs(rot);
            float lightLevel = entity.worldObj.getLightBrightness(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
            double dist = entity.getDistanceSqToEntity(this) / 16.0D;
            double value = (rot + dist + (double)(entity.isSneaking()?40:0)) * (2.2D - (double)lightLevel);
            flag = value < 130.0D;
         } else {
            flag = true;
         }
      }

      return flag;
   }

   public boolean isSuitableTargetAlly(EntityLivingBase entity) {
      if(entity == null) {
         return false;
      } else if(this.isOnAlliedTeam(entity.getTeam())) {
         return true;
      } else {
         if(this.isSuitableMount(entity)) {
            if(entity.riddenByEntity == null) {
               return true;
            }

            if(entity.riddenByEntity instanceof EntityLivingBase) {
               return this.isOnSameTeam((EntityLivingBase)entity.riddenByEntity);
            }
         }

         return false;
      }
   }

   public boolean isAlarmed() {
      return this.alertTime > 0;
   }

   public void alert(int ammount) {
      this.alertTime += ammount;
   }

   public boolean canTeleport() {
      return false;
   }

   public int getInteligence() {
      return 3;
   }

   public void startSprinting() {
      if(!this.isSprinting() && this.canSprint() && this.exhaustion <= 0) {
         this.setSprinting(true);
         this.sprintTime = this.maxStamina;
      }

   }

   public boolean canSprint() {
      return true;
   }

   public int getExhaustionOnStopSprint() {
      return 20;
   }

   public boolean canBlock() {
      return this.haveShied();
   }

   public boolean haveShied() {
      return this.leftHand.canBlock();
   }

   public void toogleBlocking() {
      if(this.haveShied()) {
         this.setDefending(!this.isDefending());
      } else {
         this.setDefending(false);
      }

   }

   public boolean canAimBeCanceled() {
      return false;
   }

   public boolean isHealer() {
      return this.rightHand.isHealer() || this.leftHand.isHealer();
   }

   public boolean isRanged() {
      return this.rightHand.isRanged() || this.leftHand.isRanged();
   }

   public boolean isTwoHanded() {
      return this.rightHand.isTwoHanded();
   }

   public void setCaptain(boolean captain) {
      this.setAnimFlag(0, captain);
   }

   public boolean isCaptain() {
      return this.getAnimFlag(0);
   }

   public boolean isDefending() {
      return this.getAnimFlag(2);
   }

   public void setDefending(boolean flag) {
      if(this.haveShied()) {
         this.setAnimFlag(2, flag);
      }

   }

   public boolean isSitting() {
      return this.getAnimFlag(3);
   }

   public void setSitting(boolean flag) {
      this.setAnimFlag(3, flag);
   }

   public boolean isSpeaking() {
      return this.getAnimFlag(4);
   }

   public void setSpeaking(boolean flag) {
      this.setAnimFlag(4, flag);
   }

   public boolean isEating() {
      return this.getAnimFlag(5);
   }

   public void setEating(boolean flag) {
      this.setAnimFlag(5, flag);
   }

   public boolean isSleeping() {
      return this.getAnimFlag(6);
   }

   public void setSleeping(boolean flag) {
      this.setAnimFlag(6, flag);
   }

   protected boolean getAnimFlag(int index) {
      return (super.dataWatcher.getWatchableObjectByte(16) & 1 << index) != 0;
   }

   protected void setAnimFlag(int index, boolean result) {
      byte b = super.dataWatcher.getWatchableObjectByte(16);
      if(result) {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b | 1 << index)));
      } else {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b & ~(1 << index))));
      }

   }

   public double getAttackRangeBonus() {
      return this.rightHand.getAttackRangeBonus();
   }

   public double getMaxRangeForAttack() {
      return this.rightHand.getAttackRangeBonus();
   }

   public EntityLivingBase getOwner() {
      if(this.owner == null && this.ownerName != null) {
         this.setOwner(super.worldObj.getPlayerEntityByName(this.ownerName));
      }

      return this.owner;
   }

   public void setOwner(EntityLivingBase entity) {
      this.setOwner(entity, false);
   }

   public void setOwner(EntityLivingBase entity, boolean removePlayerOwnage) {
      if(entity instanceof EntityHumanBase) {
         EntityHumanBase human = (EntityHumanBase)entity;
         if(human.getOwner() == this) {
            return;
         }
      } else if(entity instanceof EntityPlayer) {
         this.ownerName = ((EntityPlayer)entity).getCommandSenderName();
      } else if(entity == null) {
         this.ownerName = null;
      }

      this.owner = entity;
   }

   public EntityLivingBase getLeader() {
      return (EntityLivingBase)(this.party != null && this.party.getLeader() != this?this.party.getLeader():this.owner);
   }

   public boolean tryPutIntoPArty(EntityHumanBase newMember) {
      if(this.party == null) {
         this.party = new EntityParty();
         this.party.tryToAddNewMember(this);
      }

      return this.party.tryToAddNewMember(newMember);
   }

   public void setInParty(EntityParty party, int angle, int dist) {
      this.party = party;
      if(!this.partyPositionPersistance) {
         this.partyPositionAngle = angle;
         this.partyDistanceToLeader = dist;
         if(this.isCaptain()) {
            this.AICombatMode = EnumAiCombat.OFFENSIVE.ordinal();
         }

         if(!this.isRanged() && !this.isHealer()) {
            if(this.haveShied() || angle < 45 && angle > -45) {
               this.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
            }
         } else {
            if(this.isRanged()) {
               this.partyDistanceToLeader += 2;
            }

            this.AICombatMode = EnumAiCombat.EVASIVE.ordinal();
         }

         this.setAIForCurrentMode();
      }

   }

   public void setOutOfParty() {
      this.party = null;
   }

   public Team getTeam() {
      EntityLivingBase entitylivingbase = this.getOwner();
      return (Team)(entitylivingbase instanceof EntityPlayer && entitylivingbase.getTeam() != null?entitylivingbase.getTeam():new MobTeam(this.entityTeam));
   }

   public boolean isOnAlliedTeam(Team team) {
      if(this.getTeam() != null && team != null) {
         if(this.getTeam().isSameTeam(team)) {
            return true;
         }

         String ownerTeamName = this.getTeam().getRegisteredName();
         String entityTeamName = team.getRegisteredName();
         if(entityTeamName.startsWith("mob") || ownerTeamName.startsWith("mob")) {
            return false;
         }

         if(entityTeamName.startsWith("npc") || ownerTeamName.startsWith("npc")) {
            return true;
         }
      }

      return false;
   }

   public int getAttackSpeed() {
      return 20;
   }

   public int getLeadershipValue() {
      return 0;
   }

   public int getTeamID() {
      return this.shieldID;
   }

   public ItemStack getRangedWeapon(int lvl) {
      return new ItemStack(Items.bow);
   }

   public ItemStack getRangedWeaponLeft(int lvl) {
      return null;
   }

   public ItemStack getDiamondArmorForSlot(int slot) {
      switch(slot) {
      case 1:
         return new ItemStack(Items.diamond_boots);
      case 2:
         return new ItemStack(Items.diamond_leggings);
      case 3:
         return new ItemStack(Items.diamond_chestplate);
      default:
         return new ItemStack(Items.diamond_helmet);
      }
   }

   public ItemStack getIronArmorForSlot(int slot) {
      switch(slot) {
      case 1:
         return new ItemStack(Items.iron_boots);
      case 2:
         return new ItemStack(Items.iron_leggings);
      case 3:
         return new ItemStack(Items.iron_chestplate);
      default:
         return new ItemStack(Items.iron_helmet);
      }
   }

   public boolean shouldRenderCape() {
      return false;
   }

   public ItemStack getHeldItem() {
      return this.isEating()?new ItemStack(ChocolateQuest.potion):(this.rightHand != null?this.rightHand.getItem():super.getHeldItem());
   }

   public ItemStack getHeldItemLeft() {
      return this.leftHand != null?this.leftHand.getItem():super.getHeldItem();
   }

   public void mountEntity(Entity e) {
      super.mountEntity(e);
      this.setMountAI();
   }

   public boolean isSuitableMount(Entity entity) {
      return entity instanceof EntityHorse || entity instanceof EntityGolemMecha && this.isOnSameTeam((EntityLivingBase)entity);
   }

   protected Entity getMount() {
      return new EntityHorse(super.worldObj);
   }

   public void setMountAI() {
      if(super.ridingEntity != null && this.isSuitableMount(super.ridingEntity) && super.ridingEntity instanceof EntityHorse) {
         EntityHorse horse = (EntityHorse)super.ridingEntity;
         if(!horse.isTame()) {
            horse.setTamedBy(new DummyChocolate(super.worldObj));
         }

         EntityCreature entityTarget = (EntityCreature)super.ridingEntity;
         boolean hasRiddenTask = false;
         Iterator i$ = entityTarget.tasks.taskEntries.iterator();

         while(i$.hasNext()) {
            Object task = i$.next();
            if(task instanceof AIAnimalMountedByEntity) {
               hasRiddenTask = true;
            }
         }

         if(!hasRiddenTask) {
            entityTarget.tasks.addTask(1, new AIAnimalMountedByEntity(entityTarget, 1.0F));
         }
      }

   }

   public boolean canTakeAsPet(EntityCreature f) {
      return f instanceof EntityWolf;
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(16, Byte.valueOf((byte)0));
   }

   public int getTalkInterval() {
      return 450;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInvisibleToPlayer(EntityPlayer player) {
      return this.isInvisible() && player != this.owner;
   }

   public void readSpawnData(ByteBuf additionalData) {
      NBTTagCompound data = null;

      try {
         this.AICombatMode = additionalData.readInt();
         this.AIMode = additionalData.readInt();
         this.partyPositionAngle = additionalData.readInt();
         this.partyDistanceToLeader = additionalData.readInt();
         int e = additionalData.readInt();
         if(e > 0) {
            byte[] bData = new byte[e];
            additionalData.readBytes(bData);
            data = CompressedStreamTools.func_152457_a(bData, NBTSizeTracker.field_152451_a);
            ItemStack is = ItemStack.loadItemStackFromNBT(data);
            if(is != null) {
               this.leftHandItem = is;
            }
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      this.updateHands();
   }

   public void writeSpawnData(ByteBuf buffer) {
      try {
         buffer.writeInt(this.AICombatMode);
         buffer.writeInt(this.AIMode);
         buffer.writeInt(this.partyPositionAngle);
         buffer.writeInt(this.partyDistanceToLeader);
         NBTTagCompound e = new NBTTagCompound();
         if(this.getLeftHandItem() != null) {
            this.getLeftHandItem().writeToNBT(e);
         }

         byte[] bData = CompressedStreamTools.compress(e);
         buffer.writeInt(bData.length);
         buffer.writeBytes(bData);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      this.onSpawn();
   }

   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
      if(par1 == 5) {
         this.leftHandItem = par2ItemStack;
         this.updateHands();
      } else {
         super.setCurrentItemOrArmor(par1, par2ItemStack);
         if(par1 == 0) {
            this.updateHands();
         }

      }
   }

   public String func_152113_b() {
      return this.ownerName;
   }

   public int getPhysicDefense() {
      return this.physicDefense;
   }

   public int getMagicDefense() {
      return this.magicDefense;
   }

   public int getBlastDefense() {
      return this.blastDefense;
   }

   public int getFireDefense() {
      return this.fireDefense;
   }

   public int getProjectileDefense() {
      return this.projectileDefense;
   }

}
