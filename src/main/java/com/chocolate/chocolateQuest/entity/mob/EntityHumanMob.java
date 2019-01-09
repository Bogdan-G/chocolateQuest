package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.gui.InventoryMedal;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityHumanMob extends EntityHumanBase implements IMob {

   DungeonMonstersBase monsterType;
   public boolean saveToSpawner = true;
   int leadership = 0;


   public EntityHumanMob(World world) {
      super(world);
      this.updateEntityAttributes();
      super.potionCount = super.rand.nextInt(3);
      super.maxStamina = 20 + super.worldObj.difficultySetting.getDifficultyId() * 20;
   }

   public DungeonMonstersBase getMonsterType() {
      return null;
   }

   protected void updateEntityAttributes() {
      this.monsterType = this.getMonsterType();
      if(!this.isBoss()) {
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(this.monsterType.getAttack());
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.monsterType.getHealth());
         this.setHealth((float)this.monsterType.getHealth());
         this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.monsterType.getRange());
      }

      super.entityTeam = this.monsterType.getTeamName();
      super.shieldID = this.monsterType.getFlagId();
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
      nbttagcompound.setBoolean("isBoss", this.isBoss());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
      if(nbttagcompound.getBoolean("isBoss")) {
         this.setBoss();
      }

   }

   public void readEntityFromSpawnerNBT(NBTTagCompound nbttagcompound, int spawnerX, int spawnerY, int spawnerZ) {
      super.readEntityFromSpawnerNBT(nbttagcompound, spawnerX, spawnerY, spawnerZ);
      this.setHomeArea(spawnerX, spawnerY, spawnerZ, 30);
      ItemStack rightHandItem = this.getEquipmentInSlot(0);
      if(rightHandItem != null && rightHandItem.getItem() == ChocolateQuest.banner) {
         rightHandItem.setItemDamage(this.getTeamID());
      }

      if(super.leftHandItem != null && super.leftHandItem.getItem() == ChocolateQuest.shield) {
         super.leftHandItem.setItemDamage(this.getTeamID());
      }

   }

   protected boolean interact(EntityPlayer player) {
      return this.isOnSameTeam(player)?super.interact(player):false;
   }

   public void onLivingUpdate() {
      if(!super.worldObj.isRemote && super.party != null && super.party.getLeader().equals(this) && !super.shouldDespawn && this.saveToSpawner) {
         EntityPlayer p = super.worldObj.getClosestPlayer(super.posX, super.posY, super.posZ, (double)ChocolateQuest.config.distToDespawn);
         if(p == null) {
            int x;
            int y;
            int z;
            if(this.hasHome()) {
               ChunkCoordinates i = this.getHomePosition();
               x = i.posX;
               y = i.posY;
               z = i.posZ;
            } else {
               x = MathHelper.floor_double(super.posX);
               y = MathHelper.floor_double(super.posY);
               z = MathHelper.floor_double(super.posZ);
            }

            if(ItemMobToSpawner.saveToSpawner(x, y, z, this)) {
               if(super.party != null) {
                  for(int var7 = 0; var7 < super.party.getMembersLength(); ++var7) {
                     EntityHumanBase e = super.party.getMember(var7);
                     if(e != null) {
                        if(e.ridingEntity != null) {
                           e.ridingEntity.setDead();
                        }

                        e.setDead();
                     }
                  }
               }

               if(super.ridingEntity != null) {
                  super.ridingEntity.setDead();
               }

               this.setDead();
            }
         }
      }

      super.onLivingUpdate();
   }

   public void onSpawn() {
      super.onSpawn();
      if(super.party == null) {
         double targetDistance = 10.0D;
         List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(targetDistance, 1.2D, targetDistance));
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if(entity instanceof EntityHumanBase) {
               EntityHumanBase human = (EntityHumanBase)entity;
               if(this.isOnSameTeam(human)) {
                  if(human.party == null) {
                     human.tryPutIntoPArty(human);
                  }

                  if(human.party.tryToAddNewMember(this, false)) {
                     return;
                  }
               }
            }
         }
      }

   }

   public boolean canSprint() {
      return super.worldObj.difficultySetting.getDifficultyId() >= EnumDifficulty.NORMAL.getDifficultyId();
   }

   public int getExhaustionOnStopSprint() {
      return 130 - super.worldObj.difficultySetting.getDifficultyId() * 30;
   }

   public void onDeath(DamageSource damageSource) {
      super.experienceValue = (int)BDHelper.getEntityValue(this);
      if(this.isBoss()) {
         super.experienceValue *= 2;
      }

      super.onDeath(damageSource);
   }

   protected void dropFewItems(boolean killedByPlayer, int looting) {
      super.dropFewItems(killedByPlayer, looting);
      int j = super.rand.nextInt(3);
      if(looting > 0) {
         j += super.rand.nextInt(looting + 1);
      }

      ItemStack mainDrop = this.getMainDrop();
      if(mainDrop != null) {
         for(int flag = 0; flag < j; ++flag) {
            this.entityDropItem(mainDrop, 0.0F);
         }
      }

      if(killedByPlayer) {
         boolean var9 = false;

         int entityValue;
         for(entityValue = 0; entityValue < 4; ++entityValue) {
            if(this.getEquipmentInSlot(entityValue + 1) != null) {
               var9 = true;
               break;
            }
         }

         entityValue = (int)BDHelper.getEntityValue(this);
         if(var9) {
            for(ItemStack secondDrop = this.getSecondaryDrop(); entityValue > 0; entityValue -= 3) {
               int k;
               if(super.rand.nextInt(10) == 0) {
                  for(k = 0; k < j; ++k) {
                     this.entityDropItem(new ItemStack(Items.emerald), 0.0F);
                  }
               } else if(secondDrop != null && super.rand.nextInt(10) == 0) {
                  for(k = 0; k < j; ++k) {
                     this.entityDropItem(secondDrop, 0.0F);
                  }
               }
            }
         }
      }

   }

   public ItemStack getBossMedal() {
      ItemStack medal = new ItemStack(ChocolateQuest.medal);
      medal.stackTagCompound = new NBTTagCompound();
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tagDisplay.setString("mob", this.getEntityName());
      tagDisplay.setInteger("color", this.getMonsterType().getColor());
      medal.stackTagCompound.setTag("display", tagDisplay);
      return medal;
   }

   protected ItemStack getMainDrop() {
      return null;
   }

   public ItemStack getSecondaryDrop() {
      return super.rand.nextBoolean()?new ItemStack(Items.dye, 1, 4):new ItemStack(Items.gold_nugget);
   }

   public boolean getCanSpawnHere() {
      return super.getCanSpawnHere() && super.worldObj.difficultySetting.getDifficultyId() > EnumDifficulty.PEACEFUL.getDifficultyId();
   }

   public int getLeadershipValue() {
      return this.leadership;
   }

   public EntityHumanBase setBoss() {
      this.leadership = 1000;
      super.shouldDespawn = false;
      super.dataWatcher.updateObject(20, Byte.valueOf((byte)1));
      InventoryMedal bossDrop = new InventoryMedal(this);
      bossDrop.setInventorySlotContents(0, this.getBossMedal());
      bossDrop.closeInventory();
      return this;
   }

   public boolean isBoss() {
      return super.dataWatcher.getWatchableObjectByte(20) == 1;
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(20, Byte.valueOf((byte)0));
   }

   public String getEntityName() {
      String s = EntityList.getEntityString(this);
      if(s == null) {
         s = "generic";
      }

      return "entity." + s + ".name";
   }
}
