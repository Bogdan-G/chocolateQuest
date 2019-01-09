package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AILavaSwim;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntityPartRidable;
import com.chocolate.chocolateQuest.packets.PacketAttackToXYZ;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGiantBoxer extends EntityBaseBoss {

   public AttackPunch rightHand;
   public AttackPunch leftHand;
   public AttackKick kickHelper;
   static final byte airSmash = 10;
   public boolean airSmashInProgress = false;
   public boolean airSmashFalling = false;
   public EntityPart head;
   public int attackSpeed = 15;


   public EntityGiantBoxer(World world) {
      super(world);
      this.resize();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D + (double)(super.size / 70.0F));
      this.kickHelper = new AttackKick(this);
      this.leftHand = new AttackPunch(this, (byte)5);
      this.leftHand.setAngle(-15, -2, 0.4F);
      this.rightHand = new AttackPunch(this, (byte)6);
      this.rightHand.setAngle(15, -2, 0.4F);
      super.xpRatio = 2.0F;
      super.projectileDefense = 40;
      super.blastDefense = 10;
      super.magicDefense = 10;
      super.fireDefense = -30;
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D + (double)super.lvl * 0.02D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D + (double)super.lvl * 0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D + (double)super.lvl * 360.0D);
      this.attackSpeed = (int)(6.0D + (double)super.lvl * 1.8D);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void addAITasks() {
      super.tasks.addTask(0, new AILavaSwim(this));
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new AIBossAttack(this, 1.0F, false));
      super.targetTasks.addTask(1, new AITargetHurtBy(this, true));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      super.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
   }

   protected boolean isAIEnabled() {
      return true;
   }

   public void initBody() {
      this.head = new EntityPartRidable(super.worldObj, this, 0, 0.0F, super.size / 6.0F, super.size);
      this.head.setSize(super.size / 3.0F, super.size / 3.0F);
      if(!super.worldObj.isRemote) {
         super.worldObj.spawnEntityInWorld(this.head);
      }

      super.initBody();
   }

   public void setPart(EntityPart entityPart, int partID) {
      super.setPart(entityPart, partID);
      entityPart.setSize(super.size / 3.0F, super.size / 3.0F);
   }

   public void onUpdate() {
      if(!super.isDead) {
         this.leftHand.onUpdate();
         this.rightHand.onUpdate();
         this.kickHelper.onUpdate();
         if(this.airSmashInProgress && super.fallDistance > 0.0F) {
            this.airSmashFalling = true;
         }

         if(this.airSmashInProgress && super.onGround && this.airSmashFalling) {
            this.airSmashInProgress = false;
            this.airSmashFalling = false;
            if(super.worldObj.isRemote) {
               super.worldObj.spawnParticle("hugeexplosion", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D);
            } else {
               List rot = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand((double)(1.0F + super.size / 10.0F), 1.0D, (double)(1.0F + super.size / 10.0F)));
               Iterator scale = rot.iterator();

               while(scale.hasNext()) {
                  Entity scale1 = (Entity)scale.next();
                  if(scale1 instanceof EntityLivingBase) {
                     this.attackEntityAsMob(scale1);
                  }
               }
            }
         }
      }

      if(super.worldObj.isRemote) {
         double mz;
         double posX;
         double posY;
         double posZ;
         if(this.getHealth() < this.getMaxHealth() / 3.0F && super.ticksExisted % 10 < 2) {
            String rot1 = "largesmoke";
            if(super.size < 5.0F) {
               rot1 = "smoke";
            }

            float scale2 = super.rotationYawHead * 3.1416F / 180.0F;
            double scale4 = (double)super.width * 1.4D;
            float desp = super.width * 0.05F;
            mz = (double)MathHelper.sin(scale2 + 0.2F);
            posX = (double)MathHelper.cos(scale2 + 0.2F);
            posY = super.posX - mz * scale4;
            posZ = super.posY + (double)super.height * 1.05D;
            double desp1 = super.posZ + posX * scale4;
            super.worldObj.spawnParticle(rot1, posY + super.rand.nextGaussian() * (double)desp, posZ, desp1 + super.rand.nextGaussian() * (double)desp, -mz / 20.0D, -(0.3D + (double)(super.rand.nextFloat() * super.size)) / 25.0D, posX / 20.0D);
            mz = (double)MathHelper.sin(scale2 - 0.2F);
            posX = (double)MathHelper.cos(scale2 - 0.2F);
            posY = super.posX - mz * scale4;
            posZ = super.posY + (double)super.height * 1.05D;
            desp1 = super.posZ + posX * scale4;
            super.worldObj.spawnParticle(rot1, posY + super.rand.nextGaussian() * (double)desp, posZ, desp1 + super.rand.nextGaussian() * (double)desp, -mz / 20.0D, -(0.3D + (double)(super.rand.nextFloat() * super.size)) / 25.0D, posX / 20.0D);
         }

         if(super.ticksExisted % 200 < 10) {
            float rot2 = super.rotationYaw * 3.1416F / 180.0F;
            double scale3 = (double)super.width / 1.6D;
            double mx = (double)MathHelper.sin(rot2) * scale3;
            mz = (double)MathHelper.cos(rot2) * scale3;
            posX = super.posX + mx;
            posY = super.posY + (double)super.height * 0.5D;
            posZ = super.posZ - mz;
            float desp2 = super.width * 0.05F;
            super.worldObj.spawnParticle("cloud", posX + super.rand.nextGaussian() * (double)desp2, posY, posZ + super.rand.nextGaussian() * (double)desp2, mx / 5.0D, -0.3D + (double)(super.rand.nextFloat() / 4.0F), -mz / 5.0D);
         }
      }

      super.onUpdate();
   }

   public void animationBoss(byte animType) {
      if(!super.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), animType);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      switch(animType) {
      case 10:
         this.airSmashInProgress = true;
         return;
      default:
         this.kickHelper.kick(animType);
      }
   }

   public void attackToXYZ(byte arm, double x, double y, double z) {
      if(!super.worldObj.isRemote) {
         PacketAttackToXYZ packet = new PacketAttackToXYZ(this.getEntityId(), arm, x, y, z);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      if(arm == 5) {
         this.leftHand.swingArmTo(x, y, z);
      } else {
         this.rightHand.swingArmTo(x, y, z);
      }

   }

   public boolean attackFromPart(DamageSource par1DamageSource, float par2, EntityPart part) {
      if(part == this.head) {
         if(par1DamageSource.isProjectile()) {
            par2 *= 2.0F;
         } else {
            par2 *= super.lvl;
         }
      }

      return super.attackFromPart(par1DamageSource, par2, part);
   }

   public void attackEntity(Entity target, float dist) {
      int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, target) - (double)super.rotationYaw);
      if(super.ticksExisted % this.attackSpeed == 0 && !super.worldObj.isRemote) {
         double var10000 = super.posY + (double)super.size;
         double targetY = target.posY + (double)target.height;
         double dx = super.posX - target.posX;
         double dy = super.posY + (double)super.size - targetY;
         double dz = super.posZ - target.posZ;
         double distToHead = dx * dx + dy * dy + dz * dz - (double)(super.width * super.width);
         if(distToHead < (this.getArmLength() + 1.0D) * (this.getArmLength() + 1.0D)) {
            boolean handFlag = super.rand.nextBoolean();
            boolean ARM_LEFT = false;
            boolean ARM_RIGHT = true;
            if(!handFlag) {
               if(this.leftHand.attackInProgress()) {
                  handFlag = true;
               }
            } else if(this.rightHand.attackInProgress()) {
               handFlag = false;
            }

            if(angle > -110 && angle < 110) {
               if(!handFlag && !this.leftHand.attackInProgress()) {
                  this.leftHand.attackTarget(target);
               } else if(!this.rightHand.attackInProgress()) {
                  this.rightHand.attackTarget(target);
               }
            }
         }
      }

      if(super.ticksExisted % 10 == 0 && !super.worldObj.isRemote && dist < (super.width + 3.0F) * (super.width + 3.0F)) {
         this.kickHelper.attackTarget(target);
      }

      if(super.ticksExisted % 10 == 0 && super.onGround && super.rand.nextInt(10) == 0 && (dist > (super.width + 3.0F) * (super.width + 3.0F) && angle > -60 && angle < 60 || angle < -110 || angle > 110)) {
         super.motionY = 1.5D + (double)(super.lvl / 10.0F);
         super.motionX = (target.posX - super.posX) / 5.0D;
         super.motionZ = (target.posZ - super.posZ) / 5.0D;
         this.animationBoss((byte)10);
      }

   }

   public double getArmLength() {
      return (double)super.size;
   }

   protected void resize() {
      this.setSize(super.size / 3.0F, super.size);
   }

   public float getMinSize() {
      return 1.1F;
   }

   public float getSizeVariation() {
      return 1.4F;
   }

   public boolean canAttackClass(Class par1Class) {
      return EntityGhast.class != par1Class && par1Class != EntityReferee.class;
   }

   protected void fall(float par1) {}

   public boolean isEntityEqual(Entity par1Entity) {
      return this == par1Entity || par1Entity == this.head;
   }

   public boolean attackInProgress() {
      return this.kickHelper.isAttackInProgress() || this.leftHand.isAttacking || this.rightHand.isAttacking;
   }

   protected String getLivingSound() {
      return "chocolatequest:monking_speak";
   }

   protected String getHurtSound() {
      return "chocolatequest:monking_hurt";
   }

   protected String getDeathSound() {
      return "chocolatequest:monking_death";
   }

   protected int getDropMaterial() {
      return 5;
   }
}
