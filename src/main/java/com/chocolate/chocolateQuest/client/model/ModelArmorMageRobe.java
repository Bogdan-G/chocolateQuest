package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelArmorMageRobe extends ModelArmor {

   int count;


   public ModelArmorMageRobe() {
      this(0.0F);
   }

   public ModelArmorMageRobe(float f) {
      super(f, 1);
      this.count = 0;
      super.bipedBody = new ModelRenderer(this);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedBody.setTextureOffset(0, 0).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.8F);
      super.bipedHead = new ModelRenderer(this);
      super.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHead.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 1.0F);
   }

   public void render(Entity e, float par2, float par3, float par4, float par5, float par6, float par7) {
      ItemStack cachedItem = null;
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, e);
      if(e instanceof EntityLivingBase) {
         cachedItem = this.getCachedItem((EntityLivingBase)e, super.type);
      }

      if(this.getRenderPass(super.type) == 1) {
         this.setArmorColor(cachedItem);
      }

      if(!(e instanceof EntityLivingBase) || ((EntityLivingBase)e).getEquipmentInSlot(4) == null) {
         super.bipedHead.render(par7);
      }

      super.bipedBody.render(par7);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
