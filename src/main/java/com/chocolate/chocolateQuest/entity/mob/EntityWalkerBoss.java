package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWalkerBoss extends EntityHumanWalker {

   int countDown = 0;
   boolean bolt;
   double lightX;
   double lightY;
   double lightZ;


   public EntityWalkerBoss(World world) {
      super(world);
      super.blockRate = 80;
      super.parryRate = 80;
      this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.endSword));
      this.setLeftHandItem(new ItemStack(ChocolateQuest.shield, 1, 10));
      this.updateHands();
      this.setCurrentItemOrArmor(4, this.getDiamondArmorForSlot(4));
      ItemStack plate = new ItemStack(ChocolateQuest.kingArmor);
      BDHelper.colorArmor(plate, 8339378);
      plate.stackTagCompound.setInteger("cape", 10);
      plate.stackTagCompound.setInteger("apron", 10);
      this.setCurrentItemOrArmor(3, plate);
      this.setCurrentItemOrArmor(2, this.getDiamondArmorForSlot(2));
      this.setCurrentItemOrArmor(1, this.getDiamondArmorForSlot(1));
      this.setBoss();
   }

   protected void updateEntityAttributes() {
      super.updateEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
      this.setHealth(500.0F);
   }

   public int getInteligence() {
      return 0;
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean shouldRenderCape() {
      return false;
   }

   public boolean attackEntityFrom(DamageSource damagesource, float i) {
      return damagesource.getDamageType() == "fall"?false:super.attackEntityFrom(damagesource, i);
   }

   public void onLivingUpdate() {
      if(this.getAttackTarget() != null) {
         float dist = this.getDistanceToEntity(this.getAttackTarget());
         boolean isTargetRanged = false;
         ItemStack is = this.getAttackTarget().getEquipmentInSlot(0);
         if(is != null && dist > 5.0F && (is.getItem() instanceof ItemBow || is.getItem() instanceof IRangedWeapon)) {
            isTargetRanged = true;
         }

         if((dist > 15.0F && dist < 40.0F || isTargetRanged) && !super.worldObj.isRemote) {
            this.castTeleport(this.getAttackTarget());
         }

         if((dist > 5.0F && dist < 10.0F && super.rand.nextInt(30) == 0 || super.rand.nextInt(300) == 0) && super.ridingEntity == null && !super.worldObj.isRemote) {
            this.swingLeftHand();
            Elements element = Elements.darkness;
            if(this.getAttackTarget().getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
               element = Elements.light;
            }

            EntityBaseBall ball = new EntityBaseBall(super.worldObj, this, 10, 1, element);
            ball.motionY = this.getAttackTarget().posY - super.posY;
            super.worldObj.spawnEntityInWorld(ball);
         }
      }

      if(!super.worldObj.isRemote && this.getAttackTarget() == null && this.isDefending()) {
         this.setDefending(false);
      }

      super.onLivingUpdate();
   }

   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
      this.heal(50.0F);

      for(int i = 0; i < 3; ++i) {
         super.worldObj.spawnParticle("heart", super.posX + (double)super.rand.nextFloat() - 0.5D, super.posY + (double)super.rand.nextFloat() - 0.5D, super.posZ + (double)super.rand.nextFloat() - 0.5D, 0.0D, 1.0D, 0.0D);
      }

   }

   public void heal(float par1) {
      super.heal(par1);
      if(this.getHealth() > this.getMaxHealth()) {
         this.setHealth(this.getMaxHealth());
      }

   }

   public void onDeath(DamageSource damagesource) {
      if(!super.worldObj.isRemote) {
         if(super.rand.nextInt(2) == 0) {
            this.dropItem(ChocolateQuest.endSword, 1);
         }

         if(super.rand.nextInt(4) == 0) {
            this.dropItem(ChocolateQuest.kingArmor, 1);
         }
      }

      super.onDeath(damagesource);
   }

   protected boolean castTeleport(Entity entity) {
      if(super.ridingEntity != null) {
         return false;
      } else {
         double d = entity.posX + (super.rand.nextDouble() - 0.5D) * 4.0D;
         double d1 = entity.posY;
         double d2 = entity.posZ + (super.rand.nextDouble() - 0.5D) * 4.0D;
         double d3 = super.posX;
         double d4 = super.posY;
         double d5 = super.posZ;
         super.posX = d;
         super.posY = d1;
         super.posZ = d2;
         boolean flag = false;
         int i = MathHelper.floor_double(super.posX);
         int j = MathHelper.floor_double(super.posY);
         int k = MathHelper.floor_double(super.posZ);
         int var31;
         if(super.worldObj.blockExists(i, j, k)) {
            boolean l = false;

            while(!l && j > 0) {
               Block j1 = super.worldObj.getBlock(i, j - 1, k);
               if(j1 != Blocks.air && j1.getMaterial().isSolid()) {
                  l = true;
               } else {
                  --super.posY;
                  --j;
               }
            }

            if(l) {
               for(var31 = 0; var31 < 10; ++var31) {
                  super.worldObj.spawnParticle("largesmoke", d3 + (double)super.rand.nextFloat() - 0.5D, d4, d5 + (double)super.rand.nextFloat() - 0.5D, 1.0D, 1.0D, 1.0D);
               }

               this.setPosition(super.posX, super.posY, super.posZ);
               if(super.worldObj.getCollidingBoundingBoxes(this, super.boundingBox).size() == 0 && !super.worldObj.isAnyLiquid(super.boundingBox)) {
                  flag = true;
               }
            }
         }

         if(!flag) {
            this.setPosition(d3, d4, d5);
            return false;
         } else {
            short var32 = 128;

            for(var31 = 0; var31 < var32; ++var31) {
               double d6 = (double)var31 / ((double)var32 - 1.0D);
               float f = (super.rand.nextFloat() - 0.5F) * 0.2F;
               float f1 = (super.rand.nextFloat() - 0.5F) * 0.2F;
               float f2 = (super.rand.nextFloat() - 0.5F) * 0.2F;
               double d7 = d3 + (super.posX - d3) * d6 + (super.rand.nextDouble() - 0.5D) * (double)super.width * 2.0D;
               double var10000 = d4 + (super.posY - d4) * d6 + super.rand.nextDouble() * (double)super.height;
               double d9 = d5 + (super.posZ - d5) * d6 + (super.rand.nextDouble() - 0.5D) * (double)super.width * 2.0D;
            }

            super.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            super.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
            return true;
         }
      }
   }

   public boolean isSuitableMount(Entity entity) {
      return entity instanceof EntityWyvern || super.isSuitableMount(entity);
   }

   protected boolean canDespawn() {
      return false;
   }
}
