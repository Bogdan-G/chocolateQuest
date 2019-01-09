package com.chocolate.chocolateQuest.entity.npc;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGolemMechaHeavy extends EntityGolemMecha {

   int gunCD = 0;


   public EntityGolemMechaHeavy(World world) {
      super(world);
      this.setSize(0.8F, 2.9F);
   }

   public EntityGolemMechaHeavy(World world, EntityLivingBase owner) {
      super(world, owner);
      this.setSize(0.8F, 2.9F);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.19D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250.0D);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public double getMountedYOffset() {
      return super.riddenByEntity instanceof EntityPlayer?1.55D:1.0D;
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
}
