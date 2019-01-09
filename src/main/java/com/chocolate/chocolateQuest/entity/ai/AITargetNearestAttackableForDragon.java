package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.ai.NearestAttackableTargetSorter;
import com.chocolate.chocolateQuest.entity.ai.SelectorLiving;
import java.util.Collections;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetNearestAttackableForDragon extends EntityAITarget {

   private final Class targetClass;
   private final int targetChance;
   private final NearestAttackableTargetSorter theNearestAttackableTargetSorter;
   private final IEntitySelector targetEntitySelector;
   private EntityLivingBase targetEntity;


   public AITargetNearestAttackableForDragon(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4) {
      this(par1EntityCreature, par2Class, par3, par4, false);
   }

   public AITargetNearestAttackableForDragon(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5) {
      this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
   }

   public AITargetNearestAttackableForDragon(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector) {
      super(par1EntityCreature, par4, par5);
      this.targetClass = par2Class;
      this.targetChance = par3;
      this.theNearestAttackableTargetSorter = new NearestAttackableTargetSorter(par1EntityCreature);
      this.setMutexBits(1);
      this.targetEntitySelector = new SelectorLiving(par1EntityCreature, this, par6IEntitySelector);
   }

   public boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2) {
      return super.isSuitableTarget(par1EntityLivingBase, par2);
   }

   public boolean shouldExecute() {
      if(this.targetChance > 0 && super.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         double d0 = this.getTargetDistance();
         List list = super.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, super.taskOwner.boundingBox.expand(d0, 10.0D, d0), this.targetEntitySelector);
         Collections.sort(list, this.theNearestAttackableTargetSorter);
         if(list.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)list.get(0);
            return true;
         }
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.targetEntity);
      super.startExecuting();
   }
}
