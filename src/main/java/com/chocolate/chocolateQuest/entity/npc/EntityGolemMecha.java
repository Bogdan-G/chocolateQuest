package com.chocolate.chocolateQuest.entity.npc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.handHelper.HandEmpty;
import com.chocolate.chocolateQuest.entity.handHelper.HandGolem;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileRocket;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBubbleShield;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGolemMecha extends EntityHumanBase implements INpc {

   public static final byte SHIELD_WATCHER = 20;
   int gunCD;
   private int CDshield;


   public EntityGolemMecha(World world) {
      super(world);
      this.gunCD = 0;
      super.fireDefense = 20;
      super.blastDefense = 20;
      super.magicDefense = -10;
      super.shouldDespawn = false;
      super.AIMode = EnumAiState.FOLLOW.ordinal();
      super.AICombatMode = EnumAiCombat.DEFENSIVE.ordinal();
      this.setAIForCurrentMode();
      this.setSize(0.8F, 2.2F);
   }

   public EntityGolemMecha(World world, EntityLivingBase owner) {
      this(world);
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(20, Integer.valueOf(0));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
   }

   protected void addAITasks() {
      super.tasks.addTask(1, new AIHumanGoToPoint(this));
      super.tasks.addTask(8, new EntityAILookIdle(this));
      this.setAIForCurrentMode();
      super.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
      super.targetTasks.addTask(3, new AITargetOwner(this));
      super.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, new HumanSelector(this)));
      super.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityHumanBase.class, 0, true, false, new HumanSelector(this)));
      super.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, IMob.class, 0, true, false, (IEntitySelector)null));
   }

   public void onLivingUpdate() {
      if(super.riddenByEntity != null) {
         if(super.riddenByEntity instanceof EntityPlayer) {
            EntityPlayer rocketLevel = (EntityPlayer)super.riddenByEntity;
            super.moveForwardHuman = 0.0F;
            if(this.gunCD > 0) {
               --this.gunCD;
            }
         }

         if(super.riddenByEntity instanceof EntityLiving) {
            this.setAttackTarget(((EntityLiving)super.riddenByEntity).getAttackTarget());
         }
      }

      if(!super.worldObj.isRemote) {
         if(this.hasElectricField() && super.ticksExisted % 18 - this.getUpgradeLevel(1) * 3 == 0) {
            double var8 = 1.5D;
            List regeneration = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(var8, 1.0D, var8));

            for(int list = 0; list < regeneration.size(); ++list) {
               Entity j = (Entity)regeneration.get(list);
               if(j instanceof EntityLivingBase && j.canBeCollidedWith() && !this.isEntityEqual(j) && j != super.riddenByEntity && !this.isOnSameTeam((EntityLivingBase)j) && Elements.blast.attackWithElement((EntityLivingBase)j, this, 2.0F)) {
                  break;
               }
            }
         }

         int var9 = this.getUpgradeLevel(3);
         if(var9 > 0 && super.ticksExisted % (100 - 20 * var9) == 0) {
            double shieldLevel = 25.0D;
            List var12 = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(shieldLevel, 1.0D, shieldLevel));

            for(int var13 = 0; var13 < var12.size(); ++var13) {
               Entity entity1 = (Entity)var12.get(var13);
               if(entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && !this.isEntityEqual(entity1) && entity1 != super.riddenByEntity && ((EntityLivingBase)entity1).getTeam() != null && !this.isOnSameTeam((EntityLivingBase)entity1)) {
                  if(!super.worldObj.isRemote) {
                     EntityBaseBall ball = new EntityBaseBall(super.worldObj, this, 11, 0);
                     ball.setBallData(new ProjectileRocket(ball, entity1, 10));
                     ++ball.posY;
                     ball.motionY = 1.0D;
                     ball.motionX = ball.motionZ = 0.0D;
                     super.worldObj.spawnEntityInWorld(ball);
                  }
                  break;
               }
            }
         }

         int var10 = this.getUpgradeLevel(2);
         int var11;
         if(this.hasElectricShield()) {
            var11 = SpellBubbleShield.applyShieldEffect(this, 1.5D);
            this.damageShield(var11);
         }

         if(!super.worldObj.isRemote) {
            if(this.CDshield > 0) {
               --this.CDshield;
               if(this.CDshield == 0) {
                  this.setShieldAmmount(this.getShieldAmmount() + var10);
               }
            }

            if(this.getShieldAmmount() < 50 + var10 * 50 && super.ticksExisted % 2 == 0) {
               this.setShieldAmmount(this.getShieldAmmount() + 1);
            }
         }

         var11 = this.getRegeneration();
         if(var11 > 0 && super.ticksExisted % ((5 - var11) * 20) == 0) {
            this.heal(1.0F);
         }
      }

      super.onLivingUpdate();
   }

   public void updateHands() {
      if(this.getEquipmentInSlot(0) != null && this.getEquipmentInSlot(0).getItem() instanceof ItemGolemWeapon) {
         super.rightHand = new HandGolem(this, this.getEquipmentInSlot(0));
      } else {
         super.rightHand = new HandHelper(this, this.getEquipmentInSlot(0));
      }

      if(this.getLeftHandItem() != null && this.getLeftHandItem().getItem() instanceof ItemGolemWeapon) {
         super.leftHand = new HandGolem(this, this.getLeftHandItem());
      } else {
         super.leftHand = new HandEmpty(this, this.getLeftHandItem());
      }

   }

   public boolean isSitting() {
      return false;
   }

   public double getMountedYOffset() {
      return super.riddenByEntity instanceof EntityPlayer?1.88D:1.1D;
   }

   public float getSizeModifier() {
      return 1.6F;
   }

   public boolean attackEntityFrom(DamageSource damagesource, float i) {
      Entity entity = damagesource.getEntity();
      if(super.riddenByEntity != null && damagesource.getEntity() instanceof EntityLivingBase) {
         EntityLivingBase e = (EntityLivingBase)entity;
         if(e == super.riddenByEntity) {
            return false;
         }

         if(!this.isSuitableTargetAlly(e)) {
            this.setAttackTarget((EntityLivingBase)entity);
         }
      }

      if(this.hasElectricShield()) {
         if(damagesource.isProjectile()) {
            this.damageShield(30);
            return false;
         }

         if(entity != null && this.getDistanceSqToEntity(entity) > 6.0D) {
            if(entity instanceof EntityPlayer && ((EntityPlayer)entity).swingProgress != 0.0F) {
               return false;
            }

            this.damageShield((int)(i * 3.0F));
            return true;
         }
      }

      return super.attackEntityFrom(damagesource, i);
   }

   protected boolean isAIEnabled() {
      return true;
   }

   public void moveEntityWithHeading(float par1, float par2) {
      if(par2 < 0.0F) {
         par2 /= 3.0F;
      }

      if(super.riddenByEntity instanceof EntityPlayer) {
         super.prevRotationYaw = super.rotationYaw = super.riddenByEntity.rotationYaw;
         super.rotationPitch = super.riddenByEntity.rotationPitch * 0.5F;
         this.setRotation(super.rotationYaw, super.rotationPitch);
         super.rotationYawHead = super.renderYawOffset = super.rotationYaw;
         par1 = ((EntityLivingBase)super.riddenByEntity).moveStrafing;
         par1 /= 2.0F;
         par2 = ((EntityLivingBase)super.riddenByEntity).moveForward;
         if(par2 < 0.0F) {
            par2 /= 2.0F;
         }

         float moveSpeed = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
         this.setAIMoveSpeed(moveSpeed / 2.0F);
         super.stepHeight = 1.0F;
         if(!super.worldObj.isRemote) {
            super.moveEntityWithHeading(par1, par2);
         }

         super.prevLimbSwingAmount = super.limbSwingAmount;
         double d0 = super.posX - super.prevPosX;
         double d1 = super.posZ - super.prevPosZ;
         float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
         if(f4 > 1.0F) {
            f4 = 1.0F;
         }

         super.limbSwingAmount += (f4 - super.limbSwingAmount) * 0.4F;
         super.limbSwing += super.limbSwingAmount;
      } else {
         super.moveEntityWithHeading(par1, par2);
      }

   }

   protected boolean interact(EntityPlayer entityPlayer) {
      if(super.riddenByEntity == null) {
         if(entityPlayer.isSneaking()) {
            return super.interact(entityPlayer);
         }

         this.setAttackTarget((EntityLivingBase)null);
         if(!super.worldObj.isRemote) {
            entityPlayer.mountEntity(this);
         }
      } else if(super.riddenByEntity instanceof EntityHumanBase) {
         if(entityPlayer.isSneaking()) {
            return super.interact(entityPlayer);
         }

         return ((EntityHumanBase)super.riddenByEntity).interactFirst(entityPlayer);
      }

      return false;
   }

   public void leftClick(boolean release) {
      HandGolem hand = (HandGolem)super.leftHand;
      hand.onClick();
   }

   public void rightClick(boolean release) {
      HandGolem hand = (HandGolem)super.rightHand;
      hand.onClick();
      this.setAiming(hand, true);
   }

   public void setOwner(EntityLivingBase entity) {
      super.setOwner(entity);
      if(entity instanceof EntityPlayer && entity.getTeam() != null) {
         super.entityTeam = entity.getTeam().getRegisteredName();
      }

   }

   protected String getLivingSound() {
      return "none";
   }

   protected String getHurtSound() {
      return "mob.irongolem.hit";
   }

   protected String getDeathSound() {
      return "mob.irongolem.death";
   }

   public boolean canRiderInteract() {
      return true;
   }

   public float getEyeHeight() {
      return super.getEyeHeight();
   }

   public boolean isTwoHanded() {
      return false;
   }

   public boolean canSprint() {
      return false;
   }

   protected boolean canDespawn() {
      return false;
   }

   public boolean canAimBeCanceled() {
      return false;
   }

   public boolean isInvisible() {
      return false;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public void setFire(int par1) {}

   public int getTotalArmorValue() {
      return this.getUpgradeLevel(0) * 5;
   }

   public boolean tryPutIntoPArty(EntityHumanBase newMember) {
      return false;
   }

   public int getShieldAmmount() {
      return super.dataWatcher.getWatchableObjectInt(20);
   }

   public void setShieldAmmount(int ammount) {
      super.dataWatcher.updateObject(20, Integer.valueOf(ammount));
   }

   public int getRegeneration() {
      return this.getUpgradeLevel(4);
   }

   public boolean hasElectricField() {
      return this.getUpgradeLevel(1) > 0;
   }

   public boolean hasElectricShield() {
      return this.CDshield == 0 && this.getUpgradeLevel(2) > 0;
   }

   public boolean shieldON() {
      return this.getShieldAmmount() > 0;
   }

   public void damageShield(int damage) {
      this.setShieldAmmount(Math.max(0, this.getShieldAmmount() - damage));
      if(this.getShieldAmmount() <= 0 && !super.worldObj.isRemote) {
         this.CDshield = 60;
      }

   }

   public int getUpgradeLevel(int upgrade) {
      int armor = 0;

      for(int i = 1; i <= 4; ++i) {
         ItemStack is = this.getEquipmentInSlot(i);
         if(is != null && is.getItem() == ChocolateQuest.golemUpgrade && is.getItemDamage() == upgrade) {
            ++armor;
         }
      }

      return armor;
   }
}
