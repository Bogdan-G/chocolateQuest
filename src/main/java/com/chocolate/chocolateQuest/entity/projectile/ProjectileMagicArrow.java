package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;

public class ProjectileMagicArrow extends ProjectileMagic {

   public ProjectileMagicArrow(EntityBaseBall entity) {
      super(entity);
   }

   public boolean longRange() {
      return true;
   }

   public boolean spawnParticles() {
      return true;
   }

   public float getGravityVelocity() {
      return 0.01F;
   }

   public boolean renderAsArrow() {
      return true;
   }

   public int getTextureIndex() {
      return 194 + super.type * 2;
   }
}
