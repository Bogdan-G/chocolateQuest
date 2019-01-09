package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelArmorSpider extends ModelArmor {

   ModelRenderer protectorL;
   ModelRenderer protectorR;
   ModelRenderer protectorF;
   ModelRenderer protectorShoulderL;
   ModelRenderer protectorShoulderR;


   public ModelArmorSpider() {
      this(0.0F, 1);
   }

   public ModelArmorSpider(float f, int type) {
      super(f, type);
      this.protectorR = this.getProtector(f, 0);
      this.protectorR.rotateAngleZ = 0.5F;
      this.protectorR.setRotationPoint(-5.1F, 12.0F, 0.0F);
      super.bipedBody.addChild(this.protectorR);
      this.protectorL = this.getProtector(f, 0);
      this.protectorL.setRotationPoint(5.1F, 12.0F, 0.0F);
      this.protectorL.rotateAngleY = 3.1415927F;
      this.protectorL.rotateAngleZ = -0.5F;
      super.bipedBody.addChild(this.protectorL);
      this.protectorF = this.getProtectorF(f);
      this.protectorF.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.protectorF.rotateAngleX = -0.5F;
      super.bipedBody.addChild(this.protectorF);
      this.protectorShoulderR = this.getProtector(f, 36);
      this.protectorShoulderR.setRotationPoint(-2.1F, 0.0F, 0.0F);
      this.protectorShoulderR.rotateAngleZ = 1.1F;
      super.bipedRightArm.addChild(this.protectorShoulderR);
      this.protectorShoulderL = this.getProtector(f, 36);
      this.protectorShoulderL.setRotationPoint(3.1F, 0.0F, 0.0F);
      this.protectorShoulderL.rotateAngleY = 3.1415927F;
      this.protectorShoulderL.rotateAngleZ = -1.1F;
      super.bipedLeftArm.addChild(this.protectorShoulderL);
   }

   public ModelRenderer getProtector(float f, int textureIndexX) {
      ModelRenderer protector = new ModelRenderer(this, textureIndexX, 0);
      protector.addBox(0.0F, -1.0F, -2.0F, 1, 4, 4, f);
      ModelRenderer protector1 = new ModelRenderer(this, textureIndexX, 0);
      protector1.addBox(-1.0F, -3.0F, -2.0F, 1, 4, 4, f);
      protector.addChild(protector1);
      return protector;
   }

   public ModelRenderer getProtectorF(float f) {
      ModelRenderer protector = new ModelRenderer(this, 19, 0);
      protector.addBox(-4.0F, -4.0F, 0.0F, 8, 4, 1, f);
      ModelRenderer protector1 = new ModelRenderer(this, 19, 0);
      protector1.addBox(-4.0F, -6.0F, -1.0F, 8, 4, 1, f);
      protector.addChild(protector1);
      return protector;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      if(super.type == 0) {
         super.bipedHead.render(par7);
      }

      if(super.type == 1) {
         this.protectorL.showModel = false;
         this.protectorR.showModel = false;
         this.protectorF.showModel = false;
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         super.bipedBody.render(par7);
         if(par1Entity != null) {
            ItemStack cachedItem = ((EntityLivingBase)par1Entity).getEquipmentInSlot(3);
            if(cachedItem != null) {
               if(par1Entity.hurtResistantTime > 0) {
                  GL11.glEnable(3553);
               }

               this.renderCape(cachedItem);
               this.renderFront(cachedItem);
               if(this.getRenderPass(super.type) <= 1 && ((ItemArmor)cachedItem.getItem()).hasColor(cachedItem)) {
                  this.setArmorColor(cachedItem);
                  Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmor.layer1);
                  super.bipedBody.render(par7);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(cachedItem.getItem().getArmorTexture(cachedItem, par1Entity, super.type, "")));
               }
            }
         }
      }

      if(super.type == 2) {
         this.protectorL.showModel = true;
         this.protectorR.showModel = true;
         super.bipedBody.render(par7);
         super.bipedLeftLeg.render(par7);
         super.bipedRightLeg.render(par7);
      }

   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
