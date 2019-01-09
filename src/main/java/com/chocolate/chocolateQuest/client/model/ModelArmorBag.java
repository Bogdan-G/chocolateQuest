package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelArmorBag extends ModelArmor {

   ModelRenderer mainBag;
   ModelRenderer topBag;
   ModelRenderer leftBag;
   ModelRenderer rightBag;
   ModelRenderer backBag;


   public ModelArmorBag() {
      this(1.0F);
   }

   public ModelArmorBag(float size) {
      super(1);
      boolean textureSizeX = true;
      boolean textureSizeY = true;
      size = 0.5F;
      super.bipedBody = new ModelRenderer(this, 32, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 1.0F);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mainBag = new ModelRenderer(this, 0, 0);
      this.mainBag.addBox(0.0F, 0.0F, 0.0F, 10, 12, 8, size);
      this.mainBag.setRotationPoint(-4.0F, 0.0F, 3.0F);
      this.topBag = new ModelRenderer(this, 36, 0);
      this.topBag.addBox(0.0F, -8.0F, 0.0F, 8, 8, 5, size);
      this.topBag.setRotationPoint(-3.0F, 0.0F, 4.0F);
      this.leftBag = new ModelRenderer(this, 36, 0);
      this.leftBag.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 5, 0.0F);
      this.leftBag.setRotationPoint(5.0F, 5.0F, 8.0F);
      this.leftBag.rotateAngleY = (float)Math.toRadians(90.0D);
      this.rightBag = new ModelRenderer(this, 36, 0);
      this.rightBag.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 5, 0.0F);
      this.rightBag.setRotationPoint(-3.0F, 5.0F, 6.0F);
      this.rightBag.rotateAngleY = (float)Math.toRadians(-90.0D);
      this.backBag = new ModelRenderer(this, 36, 0);
      this.backBag.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 5, 0.0F);
      this.backBag.setRotationPoint(0.0F, 6.0F, 9.0F);
      super.bipedBody.addChild(this.mainBag);
      super.bipedBody.addChild(this.topBag);
      super.bipedBody.addChild(this.leftBag);
      super.bipedBody.addChild(this.rightBag);
      super.bipedBody.addChild(this.backBag);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.renderArmor(this.getCachedItem((EntityLivingBase)entity, super.type), f5);
   }

   public void renderArmor(ItemStack is, float f5) {
      this.topBag.isHidden = true;
      this.leftBag.isHidden = true;
      this.rightBag.isHidden = true;
      this.backBag.isHidden = true;
      if(is.stackTagCompound != null) {
         int tier = is.stackTagCompound.getInteger("tier");
         if(tier > 0) {
            this.topBag.isHidden = false;
         }

         if(tier > 1) {
            this.leftBag.isHidden = false;
            this.rightBag.isHidden = false;
         }

         if(tier > 2) {
            this.backBag.isHidden = false;
         }
      }

      super.bipedBody.render(f5);
      this.renderFront(is);
   }
}
