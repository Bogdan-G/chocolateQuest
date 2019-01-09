package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelArmorMageTunic extends ModelArmor {

   ModelRenderer foot;
   ModelRenderer headTail;
   ModelRenderer headTail1;
   ModelRenderer face;
   ModelRenderer headTail2;


   public ModelArmorMageTunic(int type, float size) {
      super(size, type);
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.face = new ModelRenderer(this, 24, 0);
      this.face.addBox(-3.0F, -7.0F, -3.9F, 6, 7, 1, size);
      this.face.setTextureSize(64, 32);
      super.bipedHead.addChild(this.face);
      this.foot = new ModelRenderer(this, 56, 28);
      this.foot.addBox(-2.0F, 9.0F, -3.0F, 4, 3, 1, size);
      this.foot.setTextureSize(56, 28);
      super.bipedRightLeg.addChild(this.foot);
      super.bipedLeftLeg.addChild(this.foot);
      this.headTail = new ModelRenderer(this, 24, 0);
      this.headTail.addBox(-3.0F, -7.0F, 3.5F, 6, 7, 1, size);
      this.headTail.setTextureSize(64, 32);
      this.headTail1 = new ModelRenderer(this, 38, 0);
      this.headTail1.addBox(-2.0F, -5.0F, 4.0F, 4, 6, 1, size);
      this.headTail1.setTextureSize(64, 32);
      this.headTail2 = new ModelRenderer(this, 48, 0);
      this.headTail2.addBox(-1.0F, -3.0F, 4.5F, 2, 6, 1, size);
      this.headTail2.setTextureSize(64, 32);
      super.bipedHead.addChild(this.headTail);
      super.bipedHead.addChild(this.headTail1);
      super.bipedHead.addChild(this.headTail2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.renderArmor(this.getCachedItem((EntityLivingBase)entity, super.type), f5);
   }

   public void renderArmor(ItemStack is, float f5) {
      if(this.getRenderPass(super.type) == 1) {
         this.setArmorColor(is);
      }

      if(super.type == 0) {
         this.face.showModel = true;
         super.bipedHead.render(f5);
      } else if(super.type == 2) {
         this.renderSkirt();
      } else {
         super.renderArmor(is, f5);
      }

   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
