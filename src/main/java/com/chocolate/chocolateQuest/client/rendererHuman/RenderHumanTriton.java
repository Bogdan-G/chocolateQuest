package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.client.model.ModelNaga;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderHumanTriton extends RenderHuman {

   public RenderHumanTriton(float f, ResourceLocation r) {
      super(new ModelNaga(), f, r);
      super.featherY = -0.3F;
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelHuman(1.0F, false);
      super.field_82425_h = new ModelHuman(0.5F, false);
   }

   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return par2 != 2 && par2 != 3?super.shouldRenderPass(par1EntityLivingBase, par2, par3):0;
   }
}
