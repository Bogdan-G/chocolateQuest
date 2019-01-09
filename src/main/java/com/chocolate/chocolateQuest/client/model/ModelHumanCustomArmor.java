package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.entity.Entity;

public class ModelHumanCustomArmor extends ModelHuman {

   public ModelHumanCustomArmor(float f) {
      super(f);
   }

   public ModelHumanCustomArmor(float f, boolean renderTinyArms) {
      super(f);
      super.renderTinyArms = renderTinyArms;
   }

   public void render(Entity z, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, z);
      super.bipedHead.render(f5);
      super.bipedBody.render(f5);
      super.bipedRightArm.render(f5);
      super.bipedLeftArm.render(f5);
      super.bipedHeadwear.render(f5);
   }

   public boolean useTinyArms() {
      return false;
   }
}
