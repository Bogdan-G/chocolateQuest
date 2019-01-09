package com.chocolate.chocolateQuest.entity.npc;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFollowOwner;
import com.chocolate.chocolateQuest.entity.ai.AIControlledFormation;
import com.chocolate.chocolateQuest.entity.ai.AIControlledPath;
import com.chocolate.chocolateQuest.entity.ai.AIControlledSit;
import com.chocolate.chocolateQuest.entity.ai.AIControlledWardPosition;
import com.chocolate.chocolateQuest.entity.ai.AIHumanGoToPoint;
import com.chocolate.chocolateQuest.entity.ai.AIHumanMount;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import com.chocolate.chocolateQuest.entity.ai.HumanSelector;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanDummyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityHumanDummy extends EntityHumanBase {

   public EntityHumanDummy(World world) {
      super(world);
      super.shouldDespawn = false;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
   }

   public boolean isSuitableMount(Entity entity) {
      return entity instanceof EntityHumanBase?entity instanceof EntityGolemMecha:!(entity instanceof EntityPlayer);
   }

   protected boolean isAIEnabled() {
      return true;
   }

   public boolean isAiming() {
      return this.getAnimFlag(5);
   }

   public void setAiming(boolean aiming) {
      this.setAnimFlag(5, aiming);
   }

   public boolean isEating() {
      return false;
   }

   public void setEating(boolean flag) {}

   public boolean canSee(EntityLivingBase entity) {
      if(super.canSee(entity)) {
         this.setAiming(true);
      } else {
         this.setAiming(false);
      }

      return false;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public void addPotionEffect(PotionEffect potion) {
      super.addPotionEffect(new PotionEffect(potion.getPotionID(), Integer.MAX_VALUE, potion.getAmplifier()));
   }

   protected void addAITasks() {
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(2, new AIHumanGoToPoint(this));
      super.tasks.addTask(1, new AIHumanMount(this, 1.0F, false));
      super.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, new HumanSelector(this)));
      this.setAIForCurrentMode();
   }

   public void setAIForCurrentMode() {
      if(super.controlledAI != null) {
         super.tasks.removeTask(super.controlledAI);
      }

      byte priority = 4;
      if(super.AIMode == EnumAiState.FOLLOW.ordinal()) {
         super.controlledAI = new AIControlledFollowOwner(this, 8.0F, 50.0F);
      } else if(super.AIMode == EnumAiState.FORMATION.ordinal()) {
         super.controlledAI = new AIControlledFormation(this);
      } else if(super.AIMode == EnumAiState.PATH.ordinal()) {
         super.controlledAI = new AIControlledPath(this);
      } else if(super.AIMode == EnumAiState.WARD.ordinal()) {
         super.controlledAI = new AIControlledWardPosition(this);
      } else if(super.AIMode == EnumAiState.SIT.ordinal()) {
         super.controlledAI = new AIControlledSit(this);
         priority = 2;
      }

      if(super.controlledAI != null) {
         super.tasks.addTask(priority, super.controlledAI);
      }

   }

   protected boolean interact(EntityPlayer player) {
      ItemStack is = player.getCurrentEquippedItem();
      if(is != null && !super.worldObj.isRemote && is.getItem() == Items.potionitem) {
         List list = PotionHelper.getPotionEffects(is.getItemDamage(), true);
         if(list != null) {
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
               PotionEffect potion = (PotionEffect)i$.next();
               this.addPotionEffect(potion);
            }
         }

         return true;
      } else {
         super.addedToParty = true;
         return super.interact(player);
      }
   }

   public IMessage getEntityGUIUpdatePacket(EntityPlayer player) {
      return new PacketUpdateHumanDummyData(this);
   }

   public ItemStack getHeldItem() {
      return super.rightHand != null?super.rightHand.getItem():super.getHeldItem();
   }

   public boolean attackEntityFrom(DamageSource damagesource, float damage) {
      if(damagesource.damageType == DamageSource.inWall.damageType) {
         damage = 0.1F;
      }

      return super.attackEntityFrom(damagesource, damage);
   }

   protected void fall(float par1) {}
}
