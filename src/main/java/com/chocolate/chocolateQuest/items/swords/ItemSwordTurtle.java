package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public class ItemSwordTurtle extends ItemSwordAndShieldBase {

   public ItemSwordTurtle(ToolMaterial mat, String texture) {
      super(mat, texture);
   }

   public Multimap getItemAttributeModifiers() {
      Multimap multimap = super.getItemAttributeModifiers();
      multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Weapon modifier", 1.0D, 0));
      return multimap;
   }
}
