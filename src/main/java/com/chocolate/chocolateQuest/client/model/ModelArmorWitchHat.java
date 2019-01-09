package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelArmorWitchHat extends ModelArmor {

   public boolean field_82900_g;


   public ModelArmorWitchHat(float par1) {
      super(0);
      float x = -5.0F;
      float y = -9.0F;
      float z = -5.0F;
      super.bipedHead = (new ModelRenderer(this)).setTextureSize(64, 128);
      super.bipedHead.setTextureOffset(0, 64).addBox(x, y, z, 10, 2, 10);
      ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer.setRotationPoint(1.75F, -3.8F, 2.0F);
      modelrenderer.setTextureOffset(0, 76).addBox(x, y, z, 7, 4, 7);
      modelrenderer.rotateAngleX = -0.05235988F;
      modelrenderer.rotateAngleZ = 0.02617994F;
      super.bipedHead.addChild(modelrenderer);
      ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer1.setRotationPoint(1.75F, -3.4F, 2.0F);
      modelrenderer1.setTextureOffset(0, 87).addBox(x, y, z, 4, 4, 4);
      modelrenderer1.rotateAngleX = -0.10471976F;
      modelrenderer1.rotateAngleZ = 0.05235988F;
      modelrenderer.addChild(modelrenderer1);
      ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(64, 128);
      modelrenderer2.setRotationPoint(1.0F, -1.0F, 0.5F);
      modelrenderer2.setTextureOffset(0, 95).addBox(x, y, z, 1, 2, 1, 0.25F);
      modelrenderer2.rotateAngleX = -0.20943952F;
      modelrenderer2.rotateAngleZ = 0.10471976F;
      modelrenderer1.addChild(modelrenderer2);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      super.bipedHead.render(par7);
   }
}
