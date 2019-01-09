package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.Entity;

public class AttackBase {

   EntityBaseBoss owner;


   public AttackBase(EntityBaseBoss owner) {
      this.owner = owner;
   }

   public void onUpdate() {}

   public boolean isAttackInProgress() {
      return false;
   }

   public boolean attackTarget(Entity target) {
      return false;
   }
}
