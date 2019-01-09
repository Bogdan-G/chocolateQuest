package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import net.minecraft.util.MovingObjectPosition;

public class ProjectileDummy extends ProjectileBase {

   EntityBaseBall entity;


   public ProjectileDummy(EntityBaseBall entity) {
      super(entity);
   }

   public int getTextureIndex() {
      return 0;
   }

   public void onImpact(MovingObjectPosition par1MovingObjectPosition) {}
}
