package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMage extends ModelBase {

   public ModelRenderer field_40340_a;
   public ModelRenderer field_40338_b;
   public ModelRenderer field_40339_c;
   public ModelRenderer field_40336_d;
   public ModelRenderer field_40337_e;
   public int field_40334_f;
   public int field_40335_g;
   public boolean field_40341_n;
   public boolean field_40342_o;


   public ModelMage() {
      this(0.0F);
   }

   public ModelMage(float f) {
      this(f, 0.0F);
   }

   public ModelMage(float f, float f1) {
      this.field_40334_f = 0;
      this.field_40335_g = 0;
      this.field_40341_n = false;
      this.field_40342_o = false;
      byte byte0 = 64;
      byte byte1 = 64;
      this.field_40340_a = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
      this.field_40340_a.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      this.field_40340_a.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, f);
      this.field_40340_a.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, f);
      this.field_40338_b = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
      this.field_40338_b.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
      this.field_40338_b.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, f);
      this.field_40338_b.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, f + 0.5F);
      this.field_40339_c = (new ModelRenderer(this)).setTextureSize(byte0, byte1);
      this.field_40339_c.setRotationPoint(0.0F, 0.0F + f1 + 2.0F, 0.0F);
      this.field_40339_c.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, f);
      this.field_40339_c.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, f);
      this.field_40339_c.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, f);
      this.field_40336_d = (new ModelRenderer(this, 0, 22)).setTextureSize(byte0, byte1);
      this.field_40336_d.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
      this.field_40336_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
      this.field_40337_e = (new ModelRenderer(this, 0, 22)).setTextureSize(byte0, byte1);
      this.field_40337_e.mirror = true;
      this.field_40337_e.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
      this.field_40337_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      this.field_40340_a.render(f5);
      this.field_40338_b.render(f5);
      this.field_40336_d.render(f5);
      this.field_40337_e.render(f5);
      this.field_40339_c.render(f5);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      this.field_40340_a.rotateAngleY = f3 / 57.295776F;
      this.field_40340_a.rotateAngleX = f4 / 57.295776F;
      this.field_40339_c.rotationPointY = 3.0F;
      this.field_40339_c.rotationPointZ = -1.0F;
      this.field_40339_c.rotateAngleX = -0.75F;
      this.field_40336_d.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 * 0.5F;
      this.field_40337_e.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1 * 0.5F;
      this.field_40336_d.rotateAngleY = 0.0F;
      this.field_40337_e.rotateAngleY = 0.0F;
   }
}
