package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelArmorSlime extends ModelArmor {

   ModelRenderer bipedBodyPants;
   ModelRenderer bipedRightLegPants;
   ModelRenderer bipedLeftLegPants;


   public ModelArmorSlime(int type) {
      this(1.0F, type);
   }

   public ModelArmorSlime(float f, int type) {
      super(f, type);
      super.type = type;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      this.onRenderPass(super.type);
      if(this.getRenderPass(super.type) <= 1) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         super.render(par1Entity, par2, par3, par4, par5, par6, par7);
         GL11.glDisable(3042);
      } else {
         super.render(par1Entity, par2, par3, par4, par5, par6, par7);
      }

   }
}
