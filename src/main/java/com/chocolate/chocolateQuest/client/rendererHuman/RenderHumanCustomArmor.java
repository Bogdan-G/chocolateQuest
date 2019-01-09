package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelHumanCustomArmor;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

public class RenderHumanCustomArmor extends RenderHuman {

   public RenderHumanCustomArmor(ModelBiped modelbase, float f, ResourceLocation r) {
      super(modelbase, f, r);
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelHumanCustomArmor(1.0F, false);
      super.field_82425_h = new ModelHumanCustomArmor(0.5F, false);
   }
}
