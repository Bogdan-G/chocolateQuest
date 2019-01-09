package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ICooldownTracker;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandBigSword;
import com.chocolate.chocolateQuest.entity.handHelper.HandBow;
import com.chocolate.chocolateQuest.entity.handHelper.HandCQBlade;
import com.chocolate.chocolateQuest.entity.handHelper.HandDagger;
import com.chocolate.chocolateQuest.entity.handHelper.HandEmpty;
import com.chocolate.chocolateQuest.entity.handHelper.HandFire;
import com.chocolate.chocolateQuest.entity.handHelper.HandFireChange;
import com.chocolate.chocolateQuest.entity.handHelper.HandHealer;
import com.chocolate.chocolateQuest.entity.handHelper.HandHook;
import com.chocolate.chocolateQuest.entity.handHelper.HandLead;
import com.chocolate.chocolateQuest.entity.handHelper.HandMagicCaster;
import com.chocolate.chocolateQuest.entity.handHelper.HandPotion;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import com.chocolate.chocolateQuest.entity.handHelper.HandShield;
import com.chocolate.chocolateQuest.entity.handHelper.HandSnowBall;
import com.chocolate.chocolateQuest.entity.handHelper.HandSpear;
import com.chocolate.chocolateQuest.entity.handHelper.HandSupport;
import com.chocolate.chocolateQuest.entity.handHelper.HandTNT;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class HandHelper {

   private boolean twoHanded = false;
   ItemStack currentItem;
   EntityHumanBase owner;
   public int attackTime = 0;


   public static HandHelper getHandHelperForItem(EntityHumanBase owner, ItemStack itemStack) {
      if(itemStack != null) {
         Item item = itemStack.getItem();
         return (HandHelper)(item instanceof ItemStaffHeal?new HandHealer(owner, itemStack):(item instanceof IRangedWeapon && item instanceof ICooldownTracker?new HandMagicCaster(owner, itemStack):(item instanceof IRangedWeapon?new HandRanged(owner, itemStack):(item == ChocolateQuest.banner?new HandSupport(owner, itemStack):(item == ChocolateQuest.shield?new HandShield(owner, itemStack):(!(item instanceof ItemHookShoot) && item != ChocolateQuest.hookSword?(item instanceof ItemBaseDagger?new HandDagger(owner, itemStack):(item instanceof ItemBaseBroadSword?new HandBigSword(owner, itemStack):(item instanceof ItemBaseSpear?new HandSpear(owner, itemStack):(item instanceof ItemCQBlade?new HandCQBlade(owner, itemStack):(item == Items.bow?new HandBow(owner, itemStack):(item == Items.lead?new HandLead(owner, itemStack):(item == Items.snowball?new HandSnowBall(owner, itemStack):(item == Items.fire_charge?new HandFireChange(owner, itemStack):(item == Items.flint_and_steel?new HandFire(owner, itemStack):(item instanceof ItemPotion?new HandPotion(owner, itemStack):(item instanceof ItemBlock && Block.getBlockFromItem(item) == Blocks.tnt?new HandTNT(owner, itemStack):new HandHelper(owner, itemStack)))))))))))):new HandHook(owner, itemStack)))))));
      } else {
         return new HandEmpty(owner, itemStack);
      }
   }

   public HandHelper(EntityHumanBase owner, ItemStack itemStack) {
      this.owner = owner;
      this.currentItem = itemStack;
      if(itemStack != null) {
         Item item = itemStack.getItem();
         if(item instanceof ITwoHandedItem || item == Items.bow) {
            this.twoHanded = true;
         }
      }

   }

   public void attackEntity(Entity entity) {
      if(this.attackTime <= 0) {
         this.attackTime = this.owner.getAttackSpeed();
         this.owner.swingHand(this);
         this.owner.attackEntityAsMob(entity, this.currentItem);
      }

   }

   public boolean attackWithRange(Entity target, float f) {
      return false;
   }

   public void setAiming(boolean aim) {}

   public boolean isAiming() {
      return false;
   }

   public void onUpdate() {
      if(this.attackTime > 0) {
         --this.attackTime;
      }

   }

   public boolean isTwoHanded() {
      return this.twoHanded;
   }

   public boolean isRanged() {
      return false;
   }

   public boolean canBlock() {
      return false;
   }

   public boolean isHealer() {
      return false;
   }

   public double getAttackRangeBonus() {
      return 0.0D;
   }

   public double getMaxRangeForAttack() {
      return (double)(this.owner.width * this.owner.width) + this.getAttackRangeBonus();
   }

   public double getDistanceToStopAdvancing() {
      return this.getAttackRangeBonus();
   }

   public ItemStack getItem() {
      return this.currentItem;
   }
}
