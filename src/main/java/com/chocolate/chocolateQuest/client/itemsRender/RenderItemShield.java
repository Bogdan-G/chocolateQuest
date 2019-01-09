package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderItemShield extends RenderItemBase {

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      if(par1EntityLiving == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
         GL11.glTranslatef(0.0F, -0.3F, 0.3F);
      } else {
         GL11.glRotatef(-75.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(1.0F, -0.6F, -0.55F);
      }

      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(1.4F, 1.4F, 1.4F);
      doRenderShield(itemstack);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      if(((EntityPlayer)player).isBlocking()) {
         GL11.glLoadIdentity();
         GL11.glTranslatef(-1.5F, -1.0F, -1.0F);
      }

      doRenderShield(itemstack);
   }

   public static void doRenderShield(ItemStack itemstack) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      doRenderItem(itemstack);
      GL11.glEnable('\u803a');
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
      int spriteIndex = itemstack.getItemDamage();
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float i3 = (float)(spriteIndex / 16 * 16) / 256.0F;
      float i4 = i3 + 0.0625F;
      float f5 = 1.0F;
      float posZ = 0.0F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(0.0D, 0.0D, (double)posZ, (double)i1, (double)i4);
      tessellator.addVertexWithUV((double)f5, 0.0D, (double)posZ, (double)i2, (double)i4);
      tessellator.addVertexWithUV((double)f5, 1.0D, (double)posZ, (double)i2, (double)i3);
      tessellator.addVertexWithUV(0.0D, 1.0D, (double)posZ, (double)i1, (double)i3);
      tessellator.draw();
      GL11.glDisable('\u803a');
   }
}
