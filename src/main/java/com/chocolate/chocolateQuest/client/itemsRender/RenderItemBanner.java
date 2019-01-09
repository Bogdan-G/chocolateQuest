package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemBanner extends RenderItemBase {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      super.renderItem(type, item, data);
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
      int spriteIndex = itemstack.getItemDamage();
      renderBanner(spriteIndex, 16.0F);
      GL11.glDisable(3042);
   }

   protected void renderEquipped(EntityLivingBase entity, ItemStack itemstack) {
      GL11.glTranslatef(-0.1F, 0.12F, 0.0F);
      if(entity == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
         GL11.glTranslatef(0.0F, -0.5F, 0.3F);
         GL11.glScalef(2.0F, 4.0F, 2.0F);
      } else {
         GL11.glTranslatef(-0.2F, 0.0F, 0.0F);
         GL11.glScalef(2.0F, 4.0F, 2.0F);
      }

      this.renderItem(itemstack);
   }

   protected void renderItem(ItemStack is) {
      doRenderItem(is);
      GL11.glEnable('\u803a');
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDisable(2884);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
      int spriteIndex = is.getItemDamage();
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float i3 = (float)(32 + spriteIndex / 16 * 32) / 256.0F;
      float i4 = (float)(64 + spriteIndex / 16 * 32) / 256.0F;
      float f5 = 1.0F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      double desp = 0.24D;
      double timeDiff = 1000.0D;
      double inclinationX = 0.8D;
      double d1 = desp + Math.cos((double)System.currentTimeMillis() / timeDiff) * desp + inclinationX;
      double d2 = desp + Math.cos(((double)System.currentTimeMillis() + timeDiff) / timeDiff) * desp + inclinationX;
      double extraY = -0.1D;
      tessellator.addVertexWithUV(0.0D + inclinationX, extraY, d1, (double)i1, (double)i4);
      tessellator.addVertexWithUV((double)f5 + inclinationX, 0.0D, d2, (double)i2, (double)i4);
      tessellator.addVertexWithUV((double)f5, (double)f5, 0.0D, (double)i2, (double)i3);
      tessellator.addVertexWithUV(0.0D, (double)f5, 0.0D, (double)i1, (double)i3);
      tessellator.draw();
      GL11.glEnable(2884);
      GL11.glDisable('\u803a');
      GL11.glDisable(3042);
   }

   public static void renderBanner(int spriteIndex, float size) {
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float ty0 = (float)(32 + spriteIndex / 16 * 32) / 256.0F;
      float ty1 = (float)(64 + spriteIndex / 16 * 32) / 256.0F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(0.0D, (double)size, 0.0D, (double)i1, (double)ty1);
      tessellator.addVertexWithUV((double)size, (double)size, 0.0D, (double)i2, (double)ty1);
      tessellator.addVertexWithUV((double)size, 0.0D, 0.0D, (double)i2, (double)ty0);
      tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)i1, (double)ty0);
      tessellator.draw();
   }
}
