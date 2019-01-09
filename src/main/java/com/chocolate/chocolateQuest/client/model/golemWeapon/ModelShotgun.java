package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class ModelShotgun extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer cannon;
   public ModelRenderer handle;
   public ModelRenderer V2gun0;
   public ModelRenderer V2gun1;
   public ModelRenderer V2gun2;
   public ModelRenderer V2gun3;


   public ModelShotgun() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 0);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.cannon = new ModelRenderer(this, 20, 19);
      this.cannon.addBox(-0.5F, -0.5F, 6.0F, 5, 5, 1, f);
      this.handle = new ModelRenderer(this, 0, 0);
      this.handle.addBox(1.5F, 0.0F, 1.0F, 1, 2, 1, f);
      this.V2gun0 = new ModelRenderer(this, 0, 8);
      this.V2gun0.addBox(1.0F, -2.0F, 0.0F, 2, 2, 4, f);
      this.V2gun1 = new ModelRenderer(this, 18, 0);
      this.V2gun1.addBox(0.5F, -2.5F, 4.0F, 3, 3, 3, f);
      this.V2gun2 = new ModelRenderer(this, 30, 0);
      this.V2gun2.addBox(0.0F, -3.0F, 7.0F, 4, 4, 2, f);
      this.V2gun3 = new ModelRenderer(this, 12, 8);
      this.V2gun3.addBox(-0.5F, -3.5F, 9.0F, 5, 5, 1, f);
   }

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
      this.cannon.render(f5);
   }
}
