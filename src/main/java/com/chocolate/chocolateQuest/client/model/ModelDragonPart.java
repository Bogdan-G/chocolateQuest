package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelDragonPart extends ModelBase {

   ModelRenderer leg;
   ModelRenderer Head;


   public ModelDragonPart() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.leg = new ModelRenderer(this, 0, 24);
      this.leg.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4);
      this.leg.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.leg.setTextureSize(64, 32);
      this.leg.mirror = true;
      this.setRotation(this.leg, 0.0F, 0.0F, 0.0F);
      this.Head = new ModelRenderer(this, 0, 0);
      this.Head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      this.Head.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.Head.setTextureSize(64, 32);
      this.Head.mirror = true;
      this.setRotation(this.Head, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.2F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      this.Head.render(f5);
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
