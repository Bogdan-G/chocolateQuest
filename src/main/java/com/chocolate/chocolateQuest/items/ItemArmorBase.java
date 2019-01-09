package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemArmorBase extends ItemArmor {

   String name;
   boolean isEpic;
   public static final ArmorMaterial DRAGON = EnumHelper.addArmorMaterial("DRAGON", 55, new int[]{4, 9, 7, 4}, 15);
   public static final ArmorMaterial TURTLE = EnumHelper.addArmorMaterial("TURTLE", 45, new int[]{3, 8, 6, 3}, 25);
   public static final ArmorMaterial MONSTER_FUR = EnumHelper.addArmorMaterial("MONSTER_FUR", 45, new int[]{2, 7, 5, 2}, 25);
   public static final ArmorMaterial MAGE_ROBE = EnumHelper.addArmorMaterial("MAGE_ROBE", 20, new int[]{2, 4, 3, 2}, 25);
   public static final String[] CLOTH_OVERLAY_NAMES = new String[]{"overlay_helmet", "overlay_plate", "overlay_pants", "overlay_boots"};
   public static final int HELMET = 0;
   public static final int PLATE = 1;
   public static final int LEGS = 2;
   public static final int BOOTS = 3;
   boolean isColoreable;
   int defaultColor;
   @SideOnly(Side.CLIENT)
   private IIcon overlayIcon;


   public ItemArmorBase(ArmorMaterial material, int renderIndex) {
      super(material, 0, renderIndex);
      this.isEpic = false;
      this.isColoreable = false;
      this.defaultColor = 16777215;
   }

   public ItemArmorBase(ArmorMaterial material, int renderIndex, String name) {
      this(material, renderIndex);
      this.name = name;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon(this.getIconString());
      if(this.isColoreable) {
         this.overlayIcon = iconRegister.registerIcon("chocolateQuest:" + CLOTH_OVERLAY_NAMES[super.armorType]);
      }

   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return slot == 2?"chocolatequest:textures/armor/" + this.name + "_2.png":"chocolatequest:textures/armor/" + this.name + ".png";
   }

   public boolean requiresMultipleRenderPasses() {
      return this.isColoreable;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack stack, int pass) {
      return pass == 1?this.overlayIcon:super.itemIcon;
   }

   public int getColorFromItemStack(ItemStack is, int i) {
      if(i == 1) {
         if(this.hasColor(is) && is.stackTagCompound != null && is.stackTagCompound.hasKey("display")) {
            NBTBase tagbase = is.stackTagCompound.getTag("display");
            if(tagbase instanceof NBTTagCompound) {
               NBTTagCompound tag = (NBTTagCompound)tagbase;
               if(tag.hasKey("color")) {
                  return tag.getInteger("color");
               }
            }
         }

         return this.defaultColor;
      } else {
         return 16777215;
      }
   }

   public boolean hasColor(ItemStack is) {
      return this.isColoreable && is.stackTagCompound != null && is.stackTagCompound.hasKey("display") || super.hasColor(is);
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      int i$;
      if(is.stackTagCompound != null) {
         if(is.stackTagCompound.hasKey("tier")) {
            list.add(StatCollector.translateToLocal("strings.tier") + ": " + this.getTier(is));
         }

         if(this.hasCape(is)) {
            list.add("Cape: " + this.getCape(is));
         }

         if(this.hasApron(is)) {
            list.add("Apron: " + this.getApron(is));
         }

         if(Awakements.hasEnchant(is, Awakements.elementProtection)) {
            Elements[] arr$ = Elements.values();
            String len$ = "";

            for(i$ = 0; i$ < 4; ++i$) {
               float a = -(5.0F + (float)Elements.getElementValue(is, arr$[i$]) * 3.75F);
               len$ = len$.concat(BDHelper.StringColor(arr$[i$].stringColor) + " " + a + "%");
            }

            list.add(len$);
         }
      }

      if(this.isEpic()) {
         Awakements[] var9 = Awakements.awekements;
         int var10 = var9.length;

         for(i$ = 0; i$ < var10; ++i$) {
            Awakements var11 = var9[i$];
            if(Awakements.hasEnchant(is, var11)) {
               list.add(var11.getDescription(is));
            }
         }
      }

      if(this.hasFullSetBonus()) {
         list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("armorbonus.full_set.name").trim());
         list.add(" " + EnumChatFormatting.BLUE + StatCollector.translateToLocal("armorbonus." + this.getFullSetBonus() + ".name").trim());
      }

   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      ItemStack is = original.theItemId;
      int armorType = ((ItemArmor)is.getItem()).armorType;
      double speed;
      if(rnd.nextInt(20) == 0) {
         speed = BDHelper.getRandomValue(rnd) * 10.0D;
         BDHelper.addAttribute(is, SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, armorType + "Health", speed, 0));
      }

      if(rnd.nextInt(20) == 0) {
         speed = BDHelper.getRandomValue(rnd) * 2.0D;
         BDHelper.addAttribute(is, SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, armorType + "Attack", speed, 0));
      }

      if(rnd.nextInt(20) == 0) {
         speed = BDHelper.getRandomValue(rnd) * 0.5D;
         BDHelper.addAttribute(is, SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, armorType + "KB", speed, 0));
      }

      if(rnd.nextInt(20) == 0) {
         speed = BDHelper.getRandomValue(rnd) * 0.2D;
         BDHelper.addAttribute(is, SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, armorType + "Speed", speed, 2));
      }

      return original;
   }

   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
      this.onUpdateEquiped(itemStack, world, player);
      if(this.isEpic()) {
         if(Awakements.hasEnchant(itemStack, Awakements.property)) {
            Awakements.property.onUpdate(player, itemStack);
         }

         if(Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
            Awakements.autoRepair.onUpdate(player, itemStack);
         }

         if(Awakements.hasEnchant(itemStack, Awakements.staminaUP)) {
            Awakements.staminaUP.onUpdate(player, itemStack);
         }
      }

   }

   public void onUpdateEquiped(ItemStack itemStack, World world, EntityLivingBase entity) {}

   public int getColor(ItemStack par1ItemStack) {
      return 16777215;
   }

   public ItemArmorBase setEpic() {
      this.isEpic = true;
      return this;
   }

   public boolean isEpic() {
      return this.isEpic;
   }

   public boolean hasFullSetBonus() {
      return false;
   }

   public String getFullSetBonus() {
      return "unspecified";
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(this.isEpic) {
         if(Awakements.hasEnchant(itemStack, Awakements.property)) {
            Awakements.property.onUpdate(entity, itemStack);
         }

         if(Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
            Awakements.autoRepair.onUpdate(entity, itemStack);
         }
      }

   }

   public void onHit(LivingHurtEvent event, ItemStack is, EntityLivingBase entity) {}

   public String getItemStackDisplayName(ItemStack itemstack) {
      return super.getItemStackDisplayName(itemstack);
   }

   public boolean isFullSet(EntityLivingBase e, ItemStack itemstack) {
      for(int i = 1; i < 5; ++i) {
         ItemStack is = e.getEquipmentInSlot(i);
         if(is == null) {
            return false;
         }

         if(is.getItem().getClass() != itemstack.getItem().getClass()) {
            return false;
         }
      }

      return true;
   }

   public Item setUnlocalizedName(String name) {
      this.setTextureName("chocolateQuest:" + name);
      return super.setUnlocalizedName(name);
   }

   public boolean hasApron(ItemStack is) {
      return is.getTagCompound() == null?false:is.getTagCompound().hasKey("apron");
   }

   public int getApron(ItemStack is) {
      return is.getTagCompound().getInteger("apron");
   }

   public boolean hasCape(ItemStack is) {
      return is.getTagCompound() == null?false:is.getTagCompound().hasKey("cape");
   }

   public int getCape(ItemStack is) {
      return is.getTagCompound().getInteger("cape");
   }

   public void setCape(ItemStack is, int capeID) {
      is.getTagCompound().setInteger("cape", capeID);
   }

   public int getTier(ItemStack is) {
      return is.getTagCompound() == null?0:is.getTagCompound().getInteger("tier");
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return armorSlot == 1?ClientProxy.defaultArmor:null;
   }

}
