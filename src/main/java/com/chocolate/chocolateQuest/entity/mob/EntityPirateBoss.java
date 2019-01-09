package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.ai.AIFirefighter;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPirateBoss extends EntityHumanPirate {

   int invisibleCD = 10;


   public EntityPirateBoss(World world) {
      super(world);
      super.AICombatMode = EnumAiCombat.BACKSTAB.ordinal();
      super.potionCount = 3;
      ItemStack is = this.getEquipedWeapon();
      this.setCurrentItemOrArmor(0, is);
      this.setAIForCurrentMode();
      this.setBoss();
   }

   protected void updateEntityAttributes() {
      super.updateEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
      this.setHealth(400.0F);
   }

   public ItemStack getEquipedWeapon() {
      return super.rand.nextBoolean()?new ItemStack(ChocolateQuest.ninjaDagger):new ItemStack(ChocolateQuest.tricksterDagger);
   }

   public void updateHands() {
      super.updateHands();
      if(this.getEquipmentInSlot(0) != null && (this.getEquipmentInSlot(0).getItem() == ChocolateQuest.ninjaDagger || this.getEquipmentInSlot(0).getItem() == ChocolateQuest.tricksterDagger)) {
         super.rightHand = new HandHelper(this, this.getEquipmentInSlot(0));
      }

   }

   public int getInteligence() {
      return 0;
   }

   protected void addAITasks() {
      super.tasks.addTask(4, new AIFirefighter(this, 1.0F, false));
      super.addAITasks();
   }

   public void onUpdate() {
      if(this.getAttackTarget() != null) {
         if(this.getHealth() < 450.0F && !this.isInvisible() && this.invisibleCD < 0) {
            this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200, 0));
            this.setInvisible(true);
            if(super.worldObj.isRemote) {
               for(int e = 0; e < 5; ++e) {
                  super.worldObj.spawnParticle("mobSpell", super.posX + (double)super.rand.nextFloat() - 0.5D, super.posY + 1.0D, super.posZ + (double)super.rand.nextFloat() - 0.5D, 1.0D, 1.0D, 1.0D);
               }
            } else {
               EntityBaiter var2 = new EntityBaiter(super.worldObj, this);
               var2.setPosition(super.posX, super.posY, super.posZ);
               super.worldObj.spawnEntityInWorld(var2);
            }

            this.castTeleport(this.getAttackTarget());
            this.invisibleCD = 200;
         }

         --this.invisibleCD;
      }

      if(this.isInvisible() && !super.worldObj.isRemote && this.getAttackTarget() == null) {
         this.setInvisible(false);
      }

      super.onUpdate();
   }

   public boolean attackEntityAsMob(Entity entity) {
      if(super.rand.nextInt(15) == 1) {
         if(entity instanceof EntityLiving) {
            ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 3));
         }
      } else if(super.rand.nextInt(15) == 1 && entity instanceof EntityLiving) {
         ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 100, 5));
      }

      return super.attackEntityAsMob(entity);
   }

   public boolean attackEntityFrom(DamageSource damagesource, float i) {
      if(this.isInvisible()) {
         this.setInvisible(false);
         this.invisibleCD = 60 - super.worldObj.difficultySetting.ordinal() * 10;
      }

      return this.isDefending() && (damagesource.isProjectile() || super.rand.nextInt(5) == 0)?false:super.attackEntityFrom(damagesource, i);
   }

   protected boolean castTeleport(Entity entity) {
      double d = entity.posX + (super.rand.nextDouble() - 0.5D) * 8.0D;
      double d1 = entity.posY;
      double d2 = entity.posZ + (super.rand.nextDouble() - 0.5D) * 8.0D;
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

   protected void dropFewItems(boolean flag, int i) {
      super.dropFewItems(flag, i);
      if(this.getEquipmentInSlot(0) != null) {
         this.dropItem(this.getEquipmentInSlot(0).getItem(), 1);
      }

      if(flag && (super.rand.nextInt(5) == 0 || super.rand.nextInt(1 + i) > 0)) {
         this.dropItem(Items.diamond, 2);
      }

   }

   protected boolean canDespawn() {
      return false;
   }

   protected String getLivingSound() {
      return "mob.villager.default";
   }

   protected String getHurtSound() {
      return "mob.villager.defaulthurt";
   }

   protected String getDeathSound() {
      return "mob.villager.defaultdeath";
   }

   public boolean isInvisible() {
      return super.dataWatcher.getWatchableObjectByte(30) == 1;
   }

   public void setInvisible(boolean invisible) {
      super.dataWatcher.updateObject(30, Byte.valueOf((byte)(invisible?1:0)));
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(30, Byte.valueOf((byte)0));
   }

   public boolean shouldRenderCape() {
      return true;
   }
}
