package com.chocolate.chocolateQuest.client.model.golemWeapon;

import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class ModelHammer extends ModelGolemWeapon {

   public ModelRenderer hand;
   public ModelRenderer handEnd;
   public ModelRenderer hammerTop;
   public ModelRenderer hammerTop1;
   public ModelRenderer hammerBot;
   public ModelRenderer hammerBot1;


   public ModelHammer() {
      float f = 0.0F;
      this.hand = new ModelRenderer(this, 0, 0);
      this.hand.addBox(0.0F, 0.0F, 0.0F, 4, 4, 6, f);
      this.handEnd = new ModelRenderer(this, 20, 0);
      this.handEnd.addBox(0.0F, 0.0F, 6.0F, 4, 4, 6, f);
      this.hammerTop = new ModelRenderer(this, 34, 0);
      this.hammerTop.addBox(0.5F, -1.0F, 9.0F, 3, 1, 3, f);
      this.hammerTop1 = new ModelRenderer(this, 48, 0);
      this.hammerTop1.addBox(0.0F, -2.0F, 8.5F, 4, 1, 4, f);
      this.hammerBot = new ModelRenderer(this, 34, 0);
      this.hammerBot.addBox(0.5F, 4.0F, 9.0F, 3, 1, 3, f);
      this.hammerBot1 = new ModelRenderer(this, 48, 0);
      this.hammerBot1.addBox(0.0F, 5.0F, 8.5F, 4, 1, 4, f);
   }

   public void render(ItemStack is) {
      float f5 = 0.0625F;
      this.hand.render(f5);
      this.handEnd.render(f5);
      this.hammerTop.render(f5);
      this.hammerTop1.render(f5);
      this.hammerBot.render(f5);
      this.hammerBot1.render(f5);
   }

   public void renderEffect(ItemStack is) {
      float f5 = 0.0625F;
      this.hammerTop.render(f5);
      this.hammerTop1.render(f5);
      this.hammerBot.render(f5);
      this.hammerBot1.render(f5);
   }
}
