package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelDragonChineseHead extends ModelBase {

   ModelRenderer head = new ModelRenderer(this, 0, 16);
   ModelRenderer mouthUp;
   ModelRenderer mouthDown;
   ModelRenderer nose;
   ModelRenderer hornLeft;
   ModelRenderer hornRight;


   public ModelDragonChineseHead() {
      this.head.addBox(-2.5F, -2.0F, -4.0F, 5, 5, 8);
      this.mouthUp = new ModelRenderer(this, 32, 0);
      this.mouthUp.addBox(-2.0F, -2.0F, -11.0F, 4, 3, 7);
      this.mouthDown = new ModelRenderer(this, 32, 10);
      this.mouthDown.addBox(-2.0F, 1.0F, -10.0F, 4, 1, 6);
      this.nose = new ModelRenderer(this, 27, 0);
      this.nose.addBox(-2.0F, -3.0F, -11.0F, 4, 1, 1);
      this.hornLeft = new ModelRenderer(this, 27, 18);
      this.hornLeft.addBox(1.5F, -2.0F, 3.0F, 1, 1, 8);
      this.hornLeft.rotateAngleX = 0.5F;
      this.hornLeft.rotateAngleY = 0.098F;
      this.hornRight = new ModelRenderer(this, 27, 18);
      this.hornRight.addBox(-2.5F, -2.0F, 3.0F, 1, 1, 8);
      this.hornRight.rotateAngleX = 0.5F;
      this.hornRight.rotateAngleY = -0.298F;
      this.head.addChild(this.mouthUp);
      this.head.addChild(this.mouthDown);
      this.head.addChild(this.nose);
      this.head.addChild(this.hornLeft);
      this.head.addChild(this.hornRight);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glPushMatrix();
      GL11.glScalef(3.0F, 3.0F, 3.0F);
      this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.head.render(f5);
      GL11.glPopMatrix();
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   }
}
