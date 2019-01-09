package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class ModelRifle extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer rifle;
   public ModelRenderer handle;
   public ModelRenderer V2Gun;
   public ModelRenderer V2Gun2;
   public ModelRenderer V2Cannon;
   public ModelRenderer V2Mark1;
   public ModelRenderer V2Mark2;


   public ModelRifle() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 0);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.rifle = new ModelRenderer(this, 0, 24);
      this.rifle.addBox(0.5F, 0.5F, 6.0F, 3, 3, 5, f);
      this.handle = new ModelRenderer(this, 0, 0);
      this.handle.addBox(1.5F, 0.0F, 1.0F, 1, 2, 1, f);
      this.V2Gun = new ModelRenderer(this, 0, 18);
      this.V2Gun.addBox(0.5F, -2.0F, 0.0F, 3, 2, 5, f);
      this.V2Gun2 = new ModelRenderer(this, 11, 18);
      this.V2Gun2.addBox(1.0F, -2.0F, 5.0F, 2, 2, 3, f);
      this.V2Cannon = new ModelRenderer(this, 9, 24);
      this.V2Cannon.addBox(1.5F, -2.0F, 8.0F, 1, 1, 7, f);
      this.V2Mark1 = new ModelRenderer(this, 0, 3);
      this.V2Mark1.addBox(0.5F, -3.0F, 4.0F, 1, 1, 1, f);
      this.V2Mark2 = new ModelRenderer(this, 0, 3);
      this.V2Mark2.addBox(2.5F, -3.0F, 4.0F, 1, 1, 1, f);
   }

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
      this.rifle.render(f5);
   }
}
