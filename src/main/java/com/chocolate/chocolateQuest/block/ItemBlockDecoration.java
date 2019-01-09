package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.block.BlockDecoration;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDecoration extends ItemBlock {

   public ItemBlockDecoration(Block block) {
      super(block);
      this.setHasSubtypes(true);
   }

   public int getMetadata(int damageValue) {
      return damageValue;
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return ((BlockDecoration)super.field_150939_a).getBlockName(itemstack.getItemDamage());
   }
}
