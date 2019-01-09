package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelMachineGun extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer rifle;
   public ModelRenderer cannon0;
   public ModelRenderer cannon1;
   public ModelRenderer cannon2;
   public ModelRenderer cannon3;


   public ModelMachineGun() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 10);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, f);
      this.rifle = new ModelRenderer(this, 11, 25);
      this.rifle.addBox(-1.5F, -1.5F, 9.4F, 3, 3, 1, f);
      this.rifle.setRotationPoint(2.0F, 2.0F, 0.0F);
      f = -0.1F;
      this.cannon0 = new ModelRenderer(this, 9, 11);
      this.cannon0.addBox(-1.7F, -1.7F, 3.8F, 1, 1, 7, f);
      this.cannon0.setRotationPoint(2.0F, 2.0F, 0.0F);
      this.cannon1 = new ModelRenderer(this, 9, 11);
      this.cannon1.addBox(-1.7F, 0.7F, 3.8F, 1, 1, 7, f);
      this.cannon1.setRotationPoint(2.0F, 2.0F, 0.0F);
      this.cannon2 = new ModelRenderer(this, 9, 11);
      this.cannon2.addBox(0.7F, -1.7F, 3.8F, 1, 1, 7, f);
      this.cannon2.setRotationPoint(2.0F, 2.0F, 0.0F);
      this.cannon3 = new ModelRenderer(this, 9, 11);
      this.cannon3.addBox(0.7F, 0.7F, 3.8F, 1, 1, 7, f);
      this.cannon3.setRotationPoint(2.0F, 2.0F, 0.0F);
      float rotFactor = 0.04F;
      this.cannon0.rotateAngleX = -rotFactor;
      this.cannon0.rotateAngleY = rotFactor;
      this.cannon1.rotateAngleX = rotFactor;
      this.cannon1.rotateAngleY = rotFactor;
      this.cannon2.rotateAngleX = -rotFactor;
      this.cannon2.rotateAngleY = -rotFactor;
      this.cannon3.rotateAngleX = rotFactor;
      this.cannon3.rotateAngleY = -rotFactor;
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {}

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
      this.rifle.render(f5);
      this.cannon0.render(f5);
      this.cannon1.render(f5);
      this.cannon2.render(f5);
      this.cannon3.render(f5);
   }
}
