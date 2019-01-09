package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBeam;
import com.chocolate.chocolateQuest.magic.SpellBubbleShield;
import com.chocolate.chocolateQuest.magic.SpellCharged;
import com.chocolate.chocolateQuest.magic.SpellHeal;
import com.chocolate.chocolateQuest.magic.SpellLeap;
import com.chocolate.chocolateQuest.magic.SpellMagicPrison;
import com.chocolate.chocolateQuest.magic.SpellMeteor;
import com.chocolate.chocolateQuest.magic.SpellMiner;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import com.chocolate.chocolateQuest.magic.SpellProjectileAimed;
import com.chocolate.chocolateQuest.magic.SpellProjectileArea;
import com.chocolate.chocolateQuest.magic.SpellProjectileBoomerang;
import com.chocolate.chocolateQuest.magic.SpellProjectileExplosive;
import com.chocolate.chocolateQuest.magic.SpellProjectileShield;
import com.chocolate.chocolateQuest.magic.SpellRepel;
import com.chocolate.chocolateQuest.magic.SpellSpray;
import com.chocolate.chocolateQuest.magic.SpellStorm;
import com.chocolate.chocolateQuest.magic.SpellSummonElemental;
import com.chocolate.chocolateQuest.magic.SpellTeleport;
import com.chocolate.chocolateQuest.magic.SpellTornadoMini;
import com.chocolate.chocolateQuest.magic.SpellTunneler;
import com.chocolate.chocolateQuest.magic.SpellVampiric;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class SpellBase {

   public String name;
   public static SpellBase[] spells = new SpellBase[]{(new SpellProjectile()).setName("spell_projectile"), (new SpellProjectileAimed()).setName("spell_tracker"), (new SpellProjectileBoomerang()).setName("spell_boomerang"), (new SpellProjectileArea()).setName("spell_area"), (new SpellTeleport()).setName("spell_teleport"), (new SpellSpray()).setName("spell_spray"), (new SpellTornadoMini()).setName("spell_tornado"), (new SpellVampiric()).setName("spell_vampiric"), (new SpellProjectileShield()).setName("spell_shield"), (new SpellStorm()).setName("spell_storm"), (new SpellBeam()).setName("spell_beam"), (new SpellCharged()).setName("spell_charged"), (new SpellProjectileExplosive()).setName("spell_explosive"), (new SpellMeteor()).setName("spell_meteor"), (new SpellMagicPrison()).setName("spell_magic_prison"), (new SpellRepel()).setName("spell_repel"), (new SpellLeap()).setName("spell_leap"), (new SpellHeal()).setName("spell_heal"), (new SpellBubbleShield()).setName("spell_bubble_shield"), (new SpellMiner()).setName("spell_miner"), (new SpellTunneler()).setName("spell_tunneler"), (new SpellSummonElemental((byte)0, 50)).setName("spell_elemental"), (new SpellSummonElemental((byte)1, 100)).setName("spell_elemental_golem"), (new SpellSummonElemental((byte)2, 100)).setName("spell_elemental_hound")};


   public void onUpdate(EntityLivingBase shooter, Elements element, ItemStack is, int angle) {}

   public boolean shouldUpdate() {
      return false;
   }

   public void onCastStart(EntityLivingBase shooter, Elements element, ItemStack is) {}

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {}

   public boolean isProjectile() {
      return false;
   }

   public void onEntityHit() {}

   public int getCastingTime() {
      return 25;
   }

   public int getCoolDown() {
      return 12;
   }

   public int getRange(ItemStack itemstack) {
      return 16;
   }

   public float getCost(ItemStack itemstack) {
      return (float)(this.getCoolDown() / 4);
   }

   public boolean shouldStartCasting(ItemStack is, EntityLivingBase shooter, Entity target) {
      return true;
   }

   public boolean isSupportSpell() {
      return false;
   }

   public static SpellBase getSpellByID(int id) {
      return id < spells.length?spells[id]:spells[0];
   }

   public static int getSpellID(String name) {
      for(int i = 0; i < spells.length; ++i) {
         if(spells[i].name.equals(name)) {
            return i;
         }
      }

      return 0;
   }

   public static String[] getNames() {
      String[] names = new String[spells.length];

      for(int i = 0; i < names.length; ++i) {
         names[i] = spells[i].name;
      }

      return names;
   }

   public SpellBase setName(String s) {
      this.name = s;
      return this;
   }

   public int getExpansion(ItemStack itemStack) {
      return Awakements.getEnchantLevel(itemStack, Awakements.spellExpansion);
   }

   public float getDamage(ItemStack itemStack) {
      return ItemStaffBase.getMagicDamage(itemStack);
   }

}
