package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.items.swords.ItemHookSword;
import net.minecraft.item.ItemStack;

public class RenderItemHookSword extends RenderItemBase {

   ItemStack hook;


   public RenderItemHookSword() {
      this.hook = new ItemStack(ChocolateQuest.material);
   }

   protected void renderItem(ItemStack is) {
      if(((ItemHookSword)is.getItem()).getHookID(is) == 0) {
         doRenderItem(is.getItem().getIconFromDamageForRenderPass(0, 1), is, 0, false);
      }

      doRenderItem(is);
      this.renderEffect(is, RenderItemBase.swordOverlay);
   }
}
