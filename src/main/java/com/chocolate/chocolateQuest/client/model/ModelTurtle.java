package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtlePart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTurtle extends ModelBase {

   ModelRenderer Body;
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;
   ModelRenderer leg;
   ModelRenderer head;


   public ModelTurtle() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      float y = 23.0F;
      this.Body = new ModelRenderer(this, 0, 0);
      this.Body.addBox(-8.0F, -6.0F + y, -8.0F, 16, 7, 16);
      this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Body.setTextureSize(64, 32);
      this.Body.mirror = true;
      this.Shape1 = new ModelRenderer(this, 18, 2);
      this.Shape1.addBox(1.0F, -8.0F + y, 0.0F, 5, 1, 6);
      this.Shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape1.setTextureSize(64, 32);
      this.Shape1.mirror = true;
      this.Shape2 = new ModelRenderer(this, 18, 9);
      this.Shape2.addBox(0.0F, -8.0F + y, -6.0F, 6, 1, 5);
      this.Shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape2.setTextureSize(64, 32);
      this.Shape2.mirror = true;
      this.Shape3 = new ModelRenderer(this, 13, 2);
      this.Shape3.addBox(-6.0F, -8.0F + y, 1.0F, 6, 1, 5);
      this.Shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape3.setTextureSize(64, 32);
      this.Shape3.mirror = true;
      this.Shape4 = new ModelRenderer(this, 12, 7);
      this.Shape4.addBox(-6.0F, -8.0F + y, -6.0F, 5, 1, 6);
      this.Shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape4.setTextureSize(64, 32);
      this.Shape4.mirror = true;
      this.Shape5 = new ModelRenderer(this, 2, 1);
      this.Shape5.addBox(-7.0F, -7.0F + y, -7.0F, 14, 1, 14);
      this.Shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape5.setTextureSize(64, 32);
      this.Shape5.mirror = true;
      this.leg = new ModelRenderer(this, 0, 24);
      this.leg.addBox(-2.0F, 1.0F + y, -2.0F, 4, 4, 4);
      this.leg.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.leg.setTextureSize(64, 32);
      this.leg.mirror = true;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-2.0F, 0.0F + y, -2.0F, 4, 4, 4);
      this.head.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.head.setTextureSize(64, 32);
      this.head.mirror = true;
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Body.render(f5);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
      EntityTurtle turtle = (EntityTurtle)entity;
      EntityTurtlePart[] parts = ((EntityTurtle)entity).getBossParts();
      float scale = turtle.getScaleSize();
      if(!turtle.isAttacking() && !turtle.isHealing()) {
         EntityTurtlePart[] arr$ = parts;
         int len$ = parts.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            EntityTurtlePart part = arr$[i$];
            if(part != null) {
               if(part.isHead()) {
                  this.head.offsetZ = -part.distanceToMainBody / scale;
                  this.head.rotateAngleY = -(turtle.rotationYaw - turtle.prevRotationYaw) * 3.141592F / 18.0F * f5;
                  this.head.render(f5);
               } else {
                  float hx = -MathHelper.sin(part.rotationYawOffset * 3.141592F / 180.0F) * part.distanceToMainBody / scale;
                  float hz = -MathHelper.cos(part.rotationYawOffset * 3.141592F / 180.0F) * part.distanceToMainBody / scale;
                  this.leg.offsetX = hx;
                  this.leg.offsetZ = hz;
                  this.leg.render(f5);
               }
            }
         }
      }

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
