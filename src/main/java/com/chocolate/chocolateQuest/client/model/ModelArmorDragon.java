package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelArmorDragon extends ModelBiped {

   ModelRenderer mouthUp;
   ModelRenderer mouthDown;
   ModelRenderer nose;
   ModelRenderer hornLeft;
   ModelRenderer hornRight;


   public ModelArmorDragon() {
      this(1.0F);
   }

   public ModelArmorDragon(float size) {
      short textureSizeX = 128;
      byte textureSizeY = 64;
      size = 0.0F;
      super.bipedHead = (new ModelRenderer(this, 74, 0)).setTextureSize(textureSizeX, textureSizeY);
      super.bipedHead.addBox(-2.5F, -3.0F, -4.0F, 5, 5, 8, size);
      this.mouthUp = (new ModelRenderer(this, 106, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.mouthUp.addBox(-2.0F, -2.0F, -11.0F, 4, 3, 7, size);
      this.mouthDown = (new ModelRenderer(this, 92, 0)).setTextureSize(textureSizeX, textureSizeY);
      this.mouthDown.addBox(-2.0F, 1.0F, -10.0F, 4, 1, 6, size);
      this.nose = (new ModelRenderer(this, 107, 11)).setTextureSize(textureSizeX, textureSizeY);
      this.nose.addBox(-2.0F, -3.0F, -11.0F, 4, 1, 1, size);
      this.hornLeft = (new ModelRenderer(this, 94, 8)).setTextureSize(textureSizeX, textureSizeY);
      this.hornLeft.addBox(1.5F, -2.0F, 3.0F, 1, 1, 8, size);
      this.hornLeft.rotateAngleX = 0.5F;
      this.hornLeft.rotateAngleY = 0.098F;
      this.hornRight = (new ModelRenderer(this, 94, 8)).setTextureSize(textureSizeX, textureSizeY);
      this.hornRight.addBox(-2.5F, -2.0F, 3.0F, 1, 1, 8, size);
      this.hornRight.rotateAngleX = 0.5F;
      this.hornRight.rotateAngleY = -0.298F;
      super.bipedHead.addChild(this.mouthUp);
      super.bipedHead.addChild(this.mouthDown);
      super.bipedHead.addChild(this.nose);
      super.bipedHead.addChild(this.hornLeft);
      super.bipedHead.addChild(this.hornRight);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glPushMatrix();
      float scale = 1.71F;
      GL11.glScalef(scale + 0.2F, scale, scale - 0.2F);
      super.bipedHead.setRotationPoint(0.0F, -2.0F, 0.0F);
      super.bipedHead.render(f5);
      GL11.glPopMatrix();
   }
}
