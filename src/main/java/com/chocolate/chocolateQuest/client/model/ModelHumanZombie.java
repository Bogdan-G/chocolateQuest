package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;

public class ModelHumanZombie extends ModelHuman {

   public ModelHumanZombie() {
      this(0.0F, false);
   }

   protected ModelHumanZombie(float par1, float par2, int par3, int par4) {
      super(par1, par2, par3, par4);
   }

   public ModelHumanZombie(float par1, boolean par2) {
      super(par1, 0.0F, 64, par2?32:64);
   }

   public void setHumanRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityHumanBase e) {
      if(((EntityHumanZombie)e).isDwarf()) {
         super.isChild = true;
      } else {
         super.isChild = false;
      }

      super.setHumanRotationAngles(f, f1, f2, f3, f4, f5, e);
   }
}
