package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class ModelBubbleCannon extends ModelGolemWeapon {

   public ModelRenderer bipedGun;
   public ModelRenderer rifle;


   public ModelBubbleCannon() {
      float f = 0.0F;
      this.bipedGun = new ModelRenderer(this, 0, 0);
      this.bipedGun.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.rifle = new ModelRenderer(this, 25, 10);
      this.rifle.addBox(0.5F, 0.5F, 6.0F, 3, 3, 6, f);
   }

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.bipedGun.render(f5);
      this.rifle.render(f5);
   }
}
