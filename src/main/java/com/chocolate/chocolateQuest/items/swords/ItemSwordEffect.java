package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemSwordEffect extends ItemSwordAndShieldBase {

   int field_77785_bY;


   public ItemSwordEffect(ToolMaterial mat, String texture) {
      super(mat, texture);
      this.field_77785_bY = Potion.weakness.id;
   }

   public ItemSwordEffect(ToolMaterial mat, String texture, int effect) {
      this(mat, texture);
      this.field_77785_bY = effect;
   }

   public void addPotionEffectsOnBlockInformation(List list) {
      list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("strings.on_block"));
      PotionEffect effect = new PotionEffect(this.field_77785_bY, 100, 0);
      list.add(" " + ItemStaffHeal.getPotionName(effect));
   }

   public void onBlock(EntityLivingBase blockingEntity, EntityLivingBase attackerEntity, DamageSource ds) {
      PotionEffect effect = new PotionEffect(this.field_77785_bY, 100, 0);
      attackerEntity.addPotionEffect(effect);
   }
}
