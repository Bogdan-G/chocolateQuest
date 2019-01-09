package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class Modelbee extends ModelBase {

   ModelRenderer mainBody = new ModelRenderer(this, 0, 0);
   ModelRenderer leftWing;
   ModelRenderer head;
   ModelRenderer tail;
   ModelRenderer leftWing1;
   ModelRenderer sendaryBody;


   public Modelbee() {
      this.mainBody.addBox(0.0F, 0.0F, 0.0F, 6, 5, 8);
      this.mainBody.setRotationPoint(-2.0F, 0.0F, 0.0F);
      this.mainBody.rotateAngleX = 0.0F;
      this.mainBody.rotateAngleY = 0.0F;
      this.mainBody.rotateAngleZ = 0.0F;
      this.mainBody.mirror = false;
      this.leftWing = new ModelRenderer(this, 0, 21);
      this.leftWing.addBox(0.0F, 0.0F, 0.0F, 7, 0, 11);
      this.leftWing.setRotationPoint(0.0F, 1.0F, 7.0F);
      this.leftWing.rotateAngleX = 0.00227F;
      this.leftWing.rotateAngleY = 1.041F;
      this.leftWing.rotateAngleZ = 0.0F;
      this.leftWing.mirror = false;
      this.head = new ModelRenderer(this, 37, 13);
      this.head.addBox(0.0F, 1.0F, 0.0F, 4, 4, 5);
      this.head.setRotationPoint(-1.0F, 0.0F, -4.0F);
      this.head.rotateAngleX = 0.0F;
      this.head.rotateAngleY = 0.0F;
      this.head.rotateAngleZ = 0.0F;
      this.head.mirror = false;
      this.tail = new ModelRenderer(this, 56, 0);
      this.tail.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2);
      this.tail.setRotationPoint(0.0F, 7.0F, 8.0F);
      this.tail.rotateAngleX = -0.88392F;
      this.tail.rotateAngleY = 0.0F;
      this.tail.rotateAngleZ = 0.0F;
      this.tail.mirror = false;
      this.leftWing1 = new ModelRenderer(this, 0, 21);
      this.leftWing1.addBox(0.0F, 0.0F, 0.0F, 7, 0, 11);
      this.leftWing1.setRotationPoint(-2.0F, 1.0F, 1.0F);
      this.leftWing1.rotateAngleX = 0.00227F;
      this.leftWing1.rotateAngleY = -1.02974F;
      this.leftWing1.rotateAngleZ = 0.0F;
      this.leftWing1.mirror = false;
      this.sendaryBody = new ModelRenderer(this, 29, 1);
      this.sendaryBody.addBox(0.0F, 0.0F, 0.0F, 4, 3, 7);
      this.sendaryBody.setRotationPoint(-1.0F, 1.0F, 8.0F);
      this.sendaryBody.rotateAngleX = -1.07818F;
      this.sendaryBody.rotateAngleY = 0.0F;
      this.sendaryBody.rotateAngleZ = 0.0F;
      this.sendaryBody.mirror = false;
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.mainBody.render(f5);
      this.leftWing.render(f5);
      this.head.render(f5);
      this.tail.render(f5);
      this.leftWing1.render(f5);
      this.sendaryBody.render(f5);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.leftWing.rotateAngleX = MathHelper.cos((float)((double)(f / 1.9191077F) * 1.5707963267949D * (double)f1 + 0.00227202624519969D));
      this.leftWing1.rotateAngleX = MathHelper.cos((float)((double)(f / 1.9191077F) * 1.5707963267949D * (double)f1 + 0.00227202624519969D));
      this.tail.rotateAngleX = MathHelper.cos((float)((double)(f / 1.9191077F) * 0.523598775598299D * (double)f1 + -0.883921483302927D));
   }
}
