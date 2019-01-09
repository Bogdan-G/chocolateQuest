package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class ModelGun extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer cannon;
   public ModelRenderer handle;
   public ModelRenderer bipedGunV2;
   public ModelRenderer cannonV2;


   public ModelGun() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 0);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.cannon = new ModelRenderer(this, 12, 21);
      this.cannon.addBox(0.5F, 0.5F, 5.5F, 3, 3, 1, f);
      this.handle = new ModelRenderer(this, 0, 0);
      this.handle.addBox(1.5F, 0.0F, 1.0F, 1, 2, 1, f);
      this.bipedGunV2 = new ModelRenderer(this, 0, 0);
      this.bipedGunV2.addBox(1.0F, -2.0F, 0.0F, 2, 2, 6, f);
      this.cannonV2 = new ModelRenderer(this, 11, 0);
      this.cannonV2.addBox(0.5F, -2.5F, 5.5F, 3, 3, 1, f);
   }

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
   }
}
