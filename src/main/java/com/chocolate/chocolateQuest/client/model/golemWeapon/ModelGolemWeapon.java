package com.chocolate.chocolateQuest.client.model.golemWeapon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ModelGolemWeapon extends ModelBase {

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {}

   public void render(ItemStack is) {
      float f5 = 0.0625F;
   }

   public void renderEffect(ItemStack is) {
      this.render(is);
   }
}
