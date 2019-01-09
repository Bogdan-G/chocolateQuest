package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelOgre;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import net.minecraft.util.ResourceLocation;

public class RenderHumanOrc extends RenderHuman {

   public RenderHumanOrc(float f, ResourceLocation r) {
      super(new ModelOgre(), f, r);
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelOgre(1.0F, false);
      super.field_82425_h = new ModelOgre(0.5F, false);
   }
}
