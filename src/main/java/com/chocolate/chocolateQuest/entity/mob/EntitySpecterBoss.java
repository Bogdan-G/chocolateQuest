package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySpecterBoss extends EntityHumanSpecter {

   int invisibleCD = 10;


   public EntitySpecterBoss(World world) {
      super(world);
      this.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.swordMoonLight));
      this.setBoss();
   }

   protected void updateEntityAttributes() {
      super.updateEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
      this.setHealth(200.0F);
   }

   public void onUpdate() {
      this.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 0));
      super.onUpdate();
   }

   public boolean attackEntityAsMob(Entity entity) {
      return super.attackEntityAsMob(entity);
   }

   public boolean canBeCollidedWith() {
      return this.isMaterialized();
   }

   public boolean canBePushed() {
      return this.canBeCollidedWith();
   }

   public boolean attackEntityFrom(DamageSource damagesource, float i) {
      boolean ret = false;
      if(super.isSwingInProgress || this.getAttackTarget() == null || damagesource.isUnblockable() || damagesource.isFireDamage() || damagesource.isExplosion()) {
         ret = super.attackEntityFrom(damagesource, i);
      }

      return ret;
   }

   public boolean isMaterialized() {
      return super.isSwingInProgress || super.leftHandSwing > 0 || this.getAttackTarget() == null;
   }

   protected void dropFewItems(boolean flag, int i) {
      super.dropFewItems(flag, i);
      if(flag && (super.rand.nextInt(5) == 0 || super.rand.nextInt(1 + i) > 0)) {
         this.dropItem(Items.diamond, 2);
      }

      if(super.rand.nextInt(3) == 0) {
         this.dropItem(ChocolateQuest.swordMoonLight, 1);
      }

   }

   public boolean shouldRenderCape() {
      return true;
   }

   protected boolean canDespawn() {
      return false;
   }
}
