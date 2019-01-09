package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelCannon extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer cannon;
   public ModelRenderer cannonMouth;
   public ModelRenderer handle;
   public ModelRenderer bipedGunV2;
   public ModelRenderer cannonV2;


   public ModelCannon() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 0);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.cannon = new ModelRenderer(this, 0, 0);
      this.cannon.addBox(0.0F, 0.0F, 6.0F, 4, 4, 6, f);
      this.cannonMouth = new ModelRenderer(this, 16, 25);
      this.cannonMouth.addBox(1.0F, 1.0F, 7.0F, 2, 2, 5, f);
      this.handle = new ModelRenderer(this, 0, 0);
      this.handle.addBox(1.5F, 0.0F, 1.0F, 1, 2, 1, f);
      this.bipedGunV2 = new ModelRenderer(this, 25, 16);
      this.bipedGunV2.addBox(0.0F, -4.0F, -3.0F, 4, 4, 12, f);
      this.cannonV2 = new ModelRenderer(this, 12, 18);
      this.cannonV2.addBox(-0.5F, -4.5F, 9.0F, 5, 5, 1, f);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {}

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
      this.cannon.render(f5);
   }
}
