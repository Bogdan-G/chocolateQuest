package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ICooldownTracker;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.CoolDownTracker;
import com.chocolate.chocolateQuest.items.ItemArmorMage;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemStaffBase extends Item implements ILoadableGun, IRangedWeapon, ICooldownTracker {

   int cooldown;
   Elements element;
   public static final String TAG_NO_RENDER = "noRender";
   public static final String TAG_SPELL_AMMOUNT = "spells";
   public static final String TAG_WAND = "wand";
   public static final String TAG_COOLDOWN = "cooldown";
   public static final String TAG_MANA_COST = "cost";
   public static final String TAG_ROSARY = "rosary";
   public static final String TAG_DAMAGE = "damage";
   public static final String TAG_LOCKED = "locked";
   public static final String TAG_MELEE = "melee";
   @SideOnly(Side.CLIENT)
   public IIcon rosaryIcon;
   @SideOnly(Side.CLIENT)
   public IIcon wandIcon;
   CoolDownTracker cachedTracker;


   public ItemStaffBase() {
      this.cooldown = 10;
      this.setMaxStackSize(1);
   }

   public ItemStaffBase(Elements element) {
      this();
      this.element = element;
   }

   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List list) {
      ItemStack is = new ItemStack(item);
      list.add(is);
      is = new ItemStack(item);
      is.stackTagCompound = new NBTTagCompound();
      is.stackTagCompound.setFloat("damage", 3.0F);
      is.stackTagCompound.setInteger("cost", 10);
      is.stackTagCompound.setBoolean("wand", true);
      list.add(is);
      is = new ItemStack(item);
      is.stackTagCompound = new NBTTagCompound();
      is.stackTagCompound.setFloat("damage", 3.0F);
      is.stackTagCompound.setFloat("cooldown", 10.0F);
      is.stackTagCompound.setBoolean("rosary", true);
      list.add(is);
   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      ItemStack is = original.theItemId;
      is.stackTagCompound = new NBTTagCompound();
      float damageBase = 2.0F;
      byte damageValue = 4;
      byte cooldownValue = 25;
      byte manaValue = 25;
      int type = rnd.nextInt(3);
      if(type == 0) {
         damageValue = 8;
         damageBase = 3.0F;
      }

      if(type == 1) {
         cooldownValue = 50;
         is.stackTagCompound.setBoolean("rosary", true);
      }

      if(type == 2) {
         manaValue = 50;
         is.stackTagCompound.setBoolean("wand", true);
      }

      is.stackTagCompound.setFloat("damage", damageBase + (float)BDHelper.getRandomValue(rnd) * (float)damageValue);
      if(type == 1 || rnd.nextBoolean()) {
         is.stackTagCompound.setInteger("cooldown", (int)(BDHelper.getRandomValue(rnd) * (double)cooldownValue));
      }

      if(type == 2 || rnd.nextBoolean()) {
         is.stackTagCompound.setInteger("cost", (int)(BDHelper.getRandomValue(rnd) * (double)manaValue));
      }

      is.stackTagCompound.setInteger("spells", 1 + (int)(BDHelper.getRandomValue(rnd) * 7.0D));
      return original;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
      this.rosaryIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.staff", "rosary"));
      this.wandIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.staff", "wand"));
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconIndex(ItemStack stack) {
      return stack.stackTagCompound != null && stack.stackTagCompound.hasKey("rosary")?this.rosaryIcon:(stack.stackTagCompound != null && stack.stackTagCompound.hasKey("wand")?this.wandIcon:super.itemIcon);
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public int getRenderPasses(int metadata) {
      return 1;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      if(is.stackTagCompound != null) {
         if(is.stackTagCompound.hasKey("noRender")) {
            list.add("noRender:true");
         }

         if(is.stackTagCompound.hasKey("locked")) {
            list.add(EnumChatFormatting.OBFUSCATED + "Locked");
         }

         if(is.stackTagCompound.hasKey("spells")) {
            list.add(StatCollector.translateToLocal("strings.spells").trim() + ": " + this.getAmmoLoaderAmmount(is));
         }

         ItemStack[] ammount = InventoryBag.getCargo(is);
         ItemStack[] ammount1 = ammount;
         int len$ = ammount.length;

         int i$;
         for(i$ = 0; i$ < len$; ++i$) {
            ItemStack a = ammount1[i$];
            if(a != null) {
               list.add(" " + EnumChatFormatting.DARK_GRAY + a.getItem().getItemStackDisplayName(a));
            }
         }

         Awakements[] var12 = Awakements.awekements;
         len$ = var12.length;

         for(i$ = 0; i$ < len$; ++i$) {
            Awakements var13 = var12[i$];
            if(Awakements.hasEnchant(is, var13)) {
               list.add(var13.getDescription(is));
            }
         }

         int var11;
         if(is.stackTagCompound.hasKey("cooldown")) {
            var11 = is.stackTagCompound.getInteger("cooldown");
            list.add(BDHelper.formatNumberToDisplay(-var11, true) + "% " + StatCollector.translateToLocal("armorbonus.cast_speed.name").trim());
         }

         if(is.stackTagCompound.hasKey("cost")) {
            var11 = is.stackTagCompound.getInteger("cost");
            list.add(BDHelper.formatNumberToDisplay(-var11, true) + "% " + StatCollector.translateToLocal("armorbonus.spell_cost.name").trim());
         }
      }

      float var10 = getMagicDamage(is);
      list.add(BDHelper.formatNumberToDisplay(var10) + " " + StatCollector.translateToLocal("weaponbonus.spell_damage.name").trim() + " " + BDHelper.StringColor(this.element.stringColor) + "(" + this.element.getTranslatedName() + ")");
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      String name = this.getUnlocalizedNameInefficiently(itemstack);
      if(itemstack.stackTagCompound != null) {
         if(itemstack.stackTagCompound.hasKey("rosary")) {
            name = name.replace("staff", "rosary");
         }

         if(itemstack.stackTagCompound.hasKey("wand")) {
            name = name.replace("staff", "wand");
         }
      }

      name = ("" + StatCollector.translateToLocal(name + ".name")).trim();
      return name;
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         entityPlayer.openGui(ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
         return itemstack;
      } else {
         ItemStack[] cargo = InventoryBag.getCargo(itemstack);
         if(cargo[0] != null && cargo[0].stackTagCompound == null) {
            SpellBase spell = this.getSpell(itemstack);
            spell.onCastStart(entityPlayer, this.element, itemstack);
            entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
         }

         return super.onItemRightClick(itemstack, world, entityPlayer);
      }
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {
      useTime = this.getMaxItemUseDuration(itemstack) - useTime;
      useTime = Math.min(useTime + 1, 60);
      SpellBase spell = this.getSpell(itemstack);
      if(spell != null && spell.isProjectile()) {
         spell.onShoot(entityPlayer, this.getElement(itemstack), itemstack, useTime);
         ItemStack[] spellsStack = InventoryBag.getCargo(itemstack);
         if(spellsStack[0].stackTagCompound == null) {
            spellsStack[0].stackTagCompound = new NBTTagCompound();
         }

         float castSpeed = getEntityCoolDownReduction(entityPlayer);
         spellsStack[0].stackTagCompound.setInteger("cd", (int)((float)spell.getCoolDown() * castSpeed));
         InventoryBag.saveCargo(itemstack, spellsStack);
         float manaCost = getEntityManaCost(entityPlayer);
         PlayerManager.useStamina(entityPlayer, spell.getCost(itemstack) * manaCost, true);
      }

   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(entity instanceof EntityPlayer) {
         if(Awakements.hasEnchant(itemStack, Awakements.property)) {
            Awakements.property.onUpdate(entity, itemStack);
         }

         if(Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
            Awakements.autoRepair.onUpdate(entity, itemStack);
         }

         EntityPlayer player = (EntityPlayer)entity;
         if(player.isUsingItem() && player.getEquipmentInSlot(0) == itemStack) {
            SpellBase var11 = this.getSpell(itemStack);
            if(var11 != null && var11.shouldUpdate()) {
               var11.onUpdate((EntityLivingBase)entity, this.getElement(itemStack), itemStack, 30);
               if(entity instanceof EntityPlayer) {
                  float var12 = getEntityManaCost((EntityLivingBase)entity);
                  PlayerManager.useStamina((EntityPlayer)entity, var11.getCost(itemStack) * var12, true);
               }
            }
         } else {
            NBTTagCompound tag = itemStack.stackTagCompound;
            if(tag != null) {
               ItemStack[] cargo = InventoryBag.getCargo(itemStack);

               int cd;
               for(cd = 0; cd < cargo.length; ++cd) {
                  if(cargo[cd] != null && cargo[cd].stackTagCompound != null) {
                     int cd1 = cargo[cd].stackTagCompound.getInteger("cd");
                     if(cd1 > 0) {
                        cargo[cd].stackTagCompound.setInteger("cd", cd1 - 1);
                     } else {
                        cargo[cd].stackTagCompound = null;
                     }
                  }
               }

               InventoryBag.saveCargo(itemStack, cargo);
               if(cargo[0] != null) {
                  if(cargo[0].stackTagCompound != null) {
                     cd = cargo[0].stackTagCompound.getInteger("cd");
                     if(cd > 0) {
                        itemStack.setItemDamage(cd);
                     } else {
                        itemStack.setItemDamage(-1);
                     }
                  } else {
                     itemStack.setItemDamage(-1);
                  }
               }
            }
         }
      } else {
         this.onUpdateEntity(itemStack, world, entity, par4, par5);
      }

      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      Awakements.property.onEntityItemUpdate(entityItem);
      return super.onEntityItemUpdate(entityItem);
   }

   public static float getEntityManaCost(EntityLivingBase entity) {
      float manaCost = 1.0F;

      for(int equipement = 1; equipement < 5; ++equipement) {
         ItemStack equipement1 = entity.getEquipmentInSlot(equipement);
         if(equipement1 != null && equipement1.getItem() instanceof ItemArmorMage) {
            manaCost = (float)((double)manaCost - 0.01D * (double)((ItemArmorMage)equipement1.getItem()).getCostReduction(equipement1));
         }
      }

      ItemStack var4 = entity.getEquipmentInSlot(0);
      if(var4 != null) {
         manaCost *= getStaffCastCost(var4);
      }

      return manaCost;
   }

   public static float getEntityCoolDownReduction(EntityLivingBase entity) {
      float castSpeed = 1.0F;

      for(int equipement = 1; equipement < 5; ++equipement) {
         ItemStack equipement1 = entity.getEquipmentInSlot(equipement);
         if(equipement1 != null && equipement1.getItem() instanceof ItemArmorMage) {
            castSpeed = (float)((double)castSpeed - 0.01D * (double)((ItemArmorMage)equipement1.getItem()).getCooldownReduction(equipement1));
         }
      }

      ItemStack var4 = entity.getEquipmentInSlot(0);
      if(var4 != null) {
         castSpeed *= getStaffCastSpeed(var4);
      }

      return castSpeed;
   }

   public static float getStaffCastSpeed(ItemStack is) {
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("cooldown")) {
         int ammount = is.stackTagCompound.getInteger("cooldown");
         return (float)Math.max(0, 100 - ammount) / 100.0F;
      } else {
         return 1.0F;
      }
   }

   public static float getStaffCastCost(ItemStack is) {
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("cost")) {
         int ammount = is.stackTagCompound.getInteger("cost");
         return (float)(100 - ammount) / 100.0F;
      } else {
         return 1.0F;
      }
   }

   public static float getMagicDamage(ItemStack is) {
      float ammount = ((ItemStaffBase)is.getItem()).getBaseMagicDamage();
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("damage")) {
         ammount = is.stackTagCompound.getFloat("damage");
      }

      return ammount + (float)Awakements.getEnchantLevel(is, Awakements.spellPower);
   }

   public float getBaseMagicDamage() {
      return 4.0F;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public boolean isDamageable() {
      return true;
   }

   public int getMaxDamage() {
      return 100;
   }

   public int getDisplayDamage(ItemStack stack) {
      ItemStack[] cargo = InventoryBag.getCargo(stack);
      if(cargo[0] != null) {
         if(cargo[0].stackTagCompound != null) {
            SpellBase spell = this.getSpell(stack);
            int damage = cargo[0].stackTagCompound.getInteger("cd");
            int maxCD = spell.getCoolDown();
            return damage * 100 / maxCD;
         } else {
            return 100;
         }
      } else {
         return 0;
      }
   }

   public Entity getTarget(EntityPlayer ep, World world) {
      return HelperPlayer.getTarget(ep, world, 30.0D);
   }

   public boolean isFull3D() {
      return true;
   }

   public CreativeTabs getCreativeTab() {
      return ChocolateQuest.tabItems;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public SpellBase getSpell(ItemStack is) {
      ItemStack[] ammo = InventoryBag.getCargo(is);
      return ammo[0] == null?null:SpellBase.getSpellByID(ammo[0].getItemDamage());
   }

   public Elements getElement(ItemStack is) {
      return this.element != null?this.element:Elements.light;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 1;
   }

   public int getAmmoLoaderAmmount(ItemStack is) {
      return is.stackTagCompound != null && is.stackTagCompound.hasKey("spells")?Math.max(1, is.stackTagCompound.getInteger("spells")):4;
   }

   public boolean isValidAmmo(ItemStack is) {
      return is.getItem() == ChocolateQuest.spell;
   }

   public int getStackIcon(ItemStack is) {
      return 70;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      int maxRange = 0;
      if(this.cachedTracker != null && this.cachedTracker.castingSpell != null) {
         maxRange = this.cachedTracker.castingSpell.getRange(is);
      } else {
         ItemStack[] spells = InventoryBag.getCargo(is);

         for(int i = 0; i < spells.length; ++i) {
            if(spells[i] != null) {
               maxRange = Math.max(maxRange, SpellBase.getSpellByID(spells[i].getItemDamage()).getRange(is));
            }
         }
      }

      return (float)(maxRange * maxRange);
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return 10;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      if(this.cachedTracker.castingSpell != null) {
         shooter.rotationYaw = shooter.rotationYawHead;
         this.cachedTracker.castingSpell.onShoot(shooter, this.getElement(is), is, 5);
      }

      this.cachedTracker.castingSpell = null;
   }

   public boolean canBeUsedByEntity(Entity entity) {
      return true;
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return is.stackTagCompound == null?false:is.stackTagCompound.getBoolean("melee");
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return true;
   }

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      for(int i = 0; i < this.cachedTracker.spells.length; ++i) {
         if(this.cachedTracker.cooldowns[i] == 0) {
            SpellBase spell = this.cachedTracker.spells[i];
            if(spell != null && spell.shouldStartCasting(is, shooter, target)) {
               if(target != null) {
                  double dist = shooter.getDistanceSqToEntity(target);
                  if(dist < (double)(spell.getRange(is) * spell.getRange(is))) {
                     return this.mobCastSpell(spell, i, shooter, is);
                  }
               } else if(spell.isSupportSpell()) {
                  return this.mobCastSpell(spell, i, shooter, is);
               }
            }
         }
      }

      return -1;
   }

   private void onUpdateEntity(ItemStack itemStack, World world, Entity entity, int par4, boolean isAiming) {
      if(this.cachedTracker != null) {
         SpellBase spell = this.cachedTracker.castingSpell;
         if(spell != null && spell.shouldUpdate()) {
            spell.onUpdate((EntityLivingBase)entity, this.getElement(itemStack), itemStack, par4);
         }
      }

   }

   public Object getCooldownTracker(ItemStack is, Entity entity) {
      return new CoolDownTracker(is);
   }

   public boolean shouldStartCasting(ItemStack is, EntityLivingBase entity, boolean isAiming) {
      this.cachedTracker.onUpdate();
      if(this.cachedTracker.castingSpell == null) {
         for(int i = 0; i < this.cachedTracker.spells.length; ++i) {
            if(this.cachedTracker.cooldowns[i] == 0) {
               SpellBase spell = this.cachedTracker.spells[i];
               if(spell != null && spell.isSupportSpell() && spell.shouldStartCasting(is, entity, (Entity)null)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void startTick(Object tracker) {
      this.cachedTracker = (CoolDownTracker)tracker;
   }

   public int mobCastSpell(SpellBase spell, int i, EntityLivingBase entity, ItemStack is) {
      this.cachedTracker.castingSpell = spell;
      this.cachedTracker.cooldowns[i] = (int)((float)this.cachedTracker.cooldowns[i] + (float)spell.getCoolDown() * getEntityCoolDownReduction(entity) * 2.0F + 10.0F);
      spell.onCastStart(entity, this.getElement(is), is);
      int castTime = spell.getCastingTime();
      if(!spell.shouldUpdate()) {
         castTime = 5 + (int)((float)castTime * getEntityCoolDownReduction(entity));
      }

      return castTime;
   }
}
