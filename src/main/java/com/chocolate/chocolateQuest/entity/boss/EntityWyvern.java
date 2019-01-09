package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AIFly;
import com.chocolate.chocolateQuest.entity.ai.AIFlyRoam;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.ai.AITargetNearestAttackableForDragon;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWyvern extends EntityBaseBoss {

   public int openMouthTime = 0;
   public final int totalOpenMouthTime = 10;
   int speedBoost = 0;
   private int flameThrowerCD = 0;
   protected int flameThrowerMaxCD = 10;
   protected final byte OPEN_MOUTH = 1;


   public EntityWyvern(World par1World) {
      super(par1World);
      this.addAITasks();
      this.setSize(1.8F, 2.5F);
      super.isImmuneToFire = true;
      super.size = 1.0F;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D + (double)(super.size / 8.0F));
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(80.0D + (double)(super.size * 10.0F));
   }

   public void addAITasks() {
      super.tasks.addTask(0, new AIFly(this));
      super.tasks.addTask(0, new AIFlyRoam(this, 88));
      super.tasks.addTask(2, new AIBossAttack(this, 1.0F, false));
      super.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
      super.targetTasks.addTask(1, new AITargetHurtBy(this, false));
      super.targetTasks.addTask(2, new AITargetNearestAttackableForDragon(this, IMob.class, 0, true));
      super.targetTasks.addTask(2, new AITargetNearestAttackableForDragon(this, EntityPlayer.class, 0, true));
   }

   public void onLivingUpdate() {
      if(this.openMouthTime > 0) {
         --this.openMouthTime;
      }

      if(!this.hasHome()) {
         this.setHomeArea(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ), 200);
      }

      if(!super.onGround && this.getAttackTarget() != null) {
         super.motionY = 0.0D;
      }

      if(super.riddenByEntity instanceof EntityPlayer) {
         if(this.flameThrowerCD > 0) {
            --this.flameThrowerCD;
         }

         if(((EntityPlayer)super.riddenByEntity).isSwingInProgress && this.flameThrowerCD == 0 && !super.worldObj.isRemote) {
            double ry = Math.toRadians((double)(super.rotationYaw - 180.0F));
            double x = Math.sin(ry);
            double z = -Math.cos(ry);
            double y = -Math.sin(Math.toRadians((double)super.riddenByEntity.rotationPitch));
            this.shootFireball(x, y, z);
            this.flameThrowerCD = this.flameThrowerMaxCD;
         }
      }

      super.onLivingUpdate();
   }

   protected boolean isAIEnabled() {
      return true;
   }

   public boolean canAttackClass(Class par1Class) {
      return true;
   }

   protected boolean limitRotation() {
      return true;
   }

   protected void updateFallState(double par1, boolean par3) {}

   public void openMouth() {
      if(this.openMouthTime <= 0) {
         this.openMouthTime = 10;
         if(!super.worldObj.isRemote) {
            PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)1);
            ChocolateQuest.channel.sendToAllAround(this, packet, 64);
         }
      }

   }

   public void animationBoss(byte animType) {
      switch(animType) {
      case 1:
         this.openMouth();
      default:
      }
   }

   protected void dropFewItems(boolean flag, int i) {
      for(int a = super.rand.nextInt(5) + 1; a > 0; --a) {
         this.dropItem(Items.diamond, 2);
      }

      this.dropItem(ChocolateQuest.dragonHelmet, 1);
   }

   public void moveEntityWithHeading(float par1, float par2) {
      if(super.riddenByEntity instanceof EntityPlayer) {
         super.prevRotationYaw = super.rotationYaw = super.riddenByEntity.rotationYaw;
         super.rotationPitch = super.riddenByEntity.rotationPitch * 0.5F;
         this.setRotation(super.rotationYaw, super.rotationPitch);
         super.rotationYawHead = super.renderYawOffset = super.rotationYaw;
         par1 = ((EntityLivingBase)super.riddenByEntity).moveStrafing * 0.5F;
         par2 = ((EntityLivingBase)super.riddenByEntity).moveForward;
         super.stepHeight = 1.0F;
         super.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
         super.prevLimbSwingAmount = super.limbSwingAmount;
         double d0 = super.posX - super.prevPosX;
         double d1 = super.posZ - super.prevPosZ;
         float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
         if(f4 > 1.0F) {
            f4 = 1.0F;
         }

         super.limbSwingAmount += (f4 - super.limbSwingAmount) * 0.4F;
         super.limbSwing += super.limbSwingAmount;
         double ry = Math.toRadians((double)(super.rotationYaw - 180.0F));
         float moveSpeed = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * par2;
         super.motionX = Math.sin(ry) * (double)moveSpeed;
         super.motionZ = -Math.cos(ry) * (double)moveSpeed;
         super.motionY = -Math.sin(Math.toRadians((double)super.rotationPitch)) * (double)moveSpeed;
         super.moveEntityWithHeading(par1, par2);
      } else {
         super.moveEntityWithHeading(par1, par2);
      }

   }

   public boolean interact(EntityPlayer entityPlayer) {
      if(entityPlayer.getCommandSenderName().equals("ArrgChocolate")) {
         this.setAttackTarget((EntityLivingBase)null);
         entityPlayer.mountEntity(this);
      }

      return false;
   }

   public void fireBreath(double x, double y, double z) {
      this.openMouth();
      World world = super.worldObj;
      if(!world.isRemote) {
         world.spawnEntityInWorld(new EntityBaseBall(world, this, x + (double)(this.getRNG().nextFloat() - 0.5F), y + (double)(this.getRNG().nextFloat() - 0.25F), z + (double)(this.getRNG().nextFloat() - 0.5F), 6, 0));
         world.playSoundEffect((double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), "fire.fire", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
      }

   }

   public void shootFireball(double x, double y, double z) {
      this.openMouth();
      World world = super.worldObj;

      for(int i = 0; i < 4; ++i) {
         EntityBaseBall entitylargefireball = new EntityBaseBall(world, this, x + (double)((this.getRNG().nextFloat() - 0.5F) / 4.0F), y + (double)((this.getRNG().nextFloat() - 0.5F) / 4.0F), z + (double)((this.getRNG().nextFloat() - 0.5F) / 4.0F), 5, 2);
         world.spawnEntityInWorld(entitylargefireball);
      }

      world.playSoundEffect((double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), "mob.ghast.fireball", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
   }

   protected void resize() {
      this.setSize(super.size * 1.8F, super.size * 2.0F);
   }

   protected String getLivingSound() {
      return "chocolatequest:dragon_speak";
   }

   protected String getHurtSound() {
      return "chocolatequest:dragon_hurt";
   }

   protected String getDeathSound() {
      return "chocolatequest:dragon_death";
   }
}
