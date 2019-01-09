package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderItemBase implements IItemRenderer {

   private static final ResourceLocation itemGlint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   public static IIcon swordOverlay;
   public static IIcon bigSwordOverlay;
   public static IIcon spearOverlay;
   public static IIcon daggerOverlay;
   public static IIcon staffOverlay;
   public static IIcon rosaryOverlay;
   public static IIcon wandOverlay;
   public static IIcon axeOverlay;


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      EntityLivingBase e;
      if(type == ItemRenderType.EQUIPPED) {
         e = (EntityLivingBase)data[1];
         this.renderEquipped(e, item);
      }

      if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         e = (EntityLivingBase)data[1];
         this.renderFirstPerson(e, item);
      }

      if(type == ItemRenderType.INVENTORY) {
         this.renderInventory(item);
      }

      if(type == ItemRenderType.ENTITY) {
         Entity e1 = (Entity)data[1];
         GL11.glRotatef((float)(e1.ticksExisted % 360), 0.0F, 1.0F, 0.0F);
         this.renderAsEntity(item);
      }

   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      this.renderItem(itemstack);
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glScalef(16.0F, 16.0F, 0.0F);
      Tessellator tessellator = Tessellator.instance;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(3008);
      IIcon icon = itemstack.getIconIndex();
      ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
      GL11.glDepthMask(true);
      if(!EnchantmentHelper.getEnchantments(itemstack).isEmpty()) {
         renderVanillaEffect(itemstack, 11176072);
      }

   }

   protected void renderAsEntity(ItemStack is) {
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.1F, 0.0F);
      doRenderItem(is);
      if(!EnchantmentHelper.getEnchantments(is).isEmpty()) {
         renderVanillaEffect(is, 11176072);
      }

   }

   protected void renderEquipped(EntityLivingBase player, ItemStack itemstack) {
      this.renderItem(itemstack);
   }

   protected void renderItem(ItemStack is) {
      doRenderItem(is);
   }

   public static void doRenderItem(ItemStack itemstack) {
      doRenderItem(itemstack, 11176072, false);
   }

   public static void doRenderItem(ItemStack itemstack, int color) {
      doRenderItem(itemstack, color, false);
   }

   public static void doRenderItem(ItemStack itemstack, int color, boolean effect) {
      IIcon icon = itemstack.getIconIndex();
      doRenderItem(icon, itemstack, color, false);
   }

   public static void doRenderItem(IIcon icon, ItemStack itemstack, int effectColor, boolean effect) {
      if(icon == null) {
         GL11.glPopMatrix();
      } else {
         Minecraft mc = Minecraft.getMinecraft();
         ResourceLocation resourcelocation = mc.renderEngine.getResourceLocation(itemstack.getItemSpriteNumber());
         mc.renderEngine.bindTexture(resourcelocation);
         GL11.glPushMatrix();
         Tessellator tessellator = Tessellator.instance;
         ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
         if(itemstack != null && (itemstack.hasEffect() || effect)) {
            renderVanillaEffect(itemstack, effectColor);
         }

         GL11.glPopMatrix();
      }
   }

   public static void registerIcons(IIconRegister iconRegister) {
      swordOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlaySword");
      bigSwordOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayBigSword");
      daggerOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayDagger");
      spearOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlaySpear");
      staffOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayStaff");
      rosaryOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayRosary");
      wandOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayWand");
      axeOverlay = iconRegister.registerIcon("chocolatequest:enchantOverlayAxe");
   }

   public static void renderVanillaEffect(ItemStack itemstack, int effectColor) {
      float red = (float)((double)((effectColor & 16711680) >> 16) / 255.0D);
      float green = (float)((double)((effectColor & '\uff00') >> 8) / 255.0D);
      float blue = (float)((double)(effectColor & 255) / 255.0D);
      GL11.glDepthFunc(514);
      GL11.glDisable(2896);
      Minecraft.getMinecraft().renderEngine.bindTexture(itemGlint);
      GL11.glEnable(3042);
      GL11.glBlendFunc(768, 1);
      GL11.glColor4f(red, green, blue, 0.5F);
      GL11.glMatrixMode(5890);
      GL11.glPushMatrix();
      float f8 = 0.125F;
      GL11.glScalef(f8, f8, f8);
      float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
      GL11.glTranslatef(f9, 0.0F, 0.0F);
      GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
      Tessellator tessellator = Tessellator.instance;
      ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glDepthFunc(515);
   }

   public void renderEffect(ItemStack itemstack, IIcon iconOverlay) {
      if(itemstack != null && itemstack.stackTagCompound != null && (Awakements.isAwakened(itemstack) || Elements.hasElements(itemstack) || itemstack.stackTagCompound.hasKey("CustomPotionEffects"))) {
         GL11.glPushMatrix();
         Minecraft mc = Minecraft.getMinecraft();
         ResourceLocation resourcelocation = mc.renderEngine.getResourceLocation(itemstack.getItemSpriteNumber());
         mc.renderEngine.bindTexture(resourcelocation);
         GL11.glScalef(1.01F, 1.01F, 1.04F);
         GL11.glTranslatef(-0.005F, -0.005F, 0.002F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2896);
         float red = 1.0F;
         float green = 1.0F;
         float blue = 1.0F;
         GL11.glColor4f(red, green, blue, 0.3F);
         Tessellator tessellator = Tessellator.instance;
         ItemRenderer.renderItemIn2D(tessellator, iconOverlay.getMaxU(), iconOverlay.getMinV(), iconOverlay.getMinU(), iconOverlay.getMaxV(), iconOverlay.getIconWidth(), swordOverlay.getIconHeight(), 0.0625F);
         if(Awakements.isAwakened(itemstack)) {
            GL11.glDepthFunc(514);
            GL11.glBlendFunc(768, 1);
            mc.renderEngine.bindTexture(itemGlint);
            red = 0.4F;
            green = 0.4F;
            blue = 0.8F;
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glColor4f(red, green, blue, 0.8F);
            float list = 0.125F;
            GL11.glScalef(list, list, list);
            float arr$ = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(arr$, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDepthFunc(515);
         }

         List var15 = ItemStaffHeal.getPotionEffects(itemstack);
         if(var15 != null && !var15.isEmpty()) {
            this.renderElementOverlay(tessellator, 6);
         }

         if(Elements.hasElements(itemstack)) {
            Elements[] var16 = Elements.values();
            int len$ = var16.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Elements element = var16[i$];
               int damage = Elements.getElementValue(itemstack, element);
               if(damage > 0) {
                  this.renderElementOverlay(tessellator, element.ordinal());
               }
            }
         }

         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glPopMatrix();
      }

   }

   public void renderElementOverlay(Tessellator tessellator, int element) {
      GL11.glDepthFunc(514);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("chocolateQuest:textures/entity/shine.png"));
      GL11.glMatrixMode(5890);
      GL11.glPushMatrix();
      float f9 = (float)Minecraft.getSystemTime() / 10000.0F * 3.8F;
      GL11.glTranslatef(0.0F, f9, 0.0F);
      float spriteWidth = 0.125F;
      float spriteOffset = spriteWidth * (float)element;
      ItemRenderer.renderItemIn2D(tessellator, spriteOffset, spriteOffset, spriteOffset + spriteWidth, spriteOffset + spriteWidth, 256, 128, 0.0625F);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glDepthFunc(515);
   }

}
