package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBulletPistol;

public class ProjectileBulletGolem extends ProjectileBulletPistol {

   public ProjectileBulletGolem(EntityBaseBall entity) {
      super(entity);
   }

   protected int getBulletBaseDamage() {
      return 8;
   }

   public float getSize() {
      return super.entity.getlvl() >= 4?0.6F:0.3F;
   }

   public boolean canBounce() {
      return false;
   }

   public float getBulletPitch() {
      return 0.8F;
   }
}
