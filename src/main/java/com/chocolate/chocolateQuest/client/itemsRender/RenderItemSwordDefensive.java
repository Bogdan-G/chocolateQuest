package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemShield;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemSwordDefensive extends RenderItemBase implements IItemRenderer {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glPushMatrix();
      GL11.glScalef(16.0F, 16.0F, 0.0F);
      Tessellator tessellator = Tessellator.instance;
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      IIcon icon = ChocolateQuest.shield.getIconFromDamage(((ItemSwordAndShieldBase)itemstack.getItem()).getShieldID(itemstack));
      ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
      GL11.glPopMatrix();
      super.renderInventory(itemstack);
      GL11.glTranslatef(1.0F, 1.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 0.0F);
      this.renderEffect(itemstack, RenderItemBase.swordOverlay);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.swordOverlay);
      if(((EntityPlayer)player).isBlocking()) {
         GL11.glLoadIdentity();
         GL11.glTranslatef(-1.5F, -1.0F, -1.0F);
         ItemStack shield = new ItemStack(ChocolateQuest.shield, 1, ((ItemSwordAndShieldBase)itemstack.getItem()).getShieldID(itemstack));
         doRenderItem(shield);
         Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
         int spriteIndex = shield.getItemDamage();
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
      }

   }

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.swordOverlay);
   }

   protected void renderAsEntity(ItemStack is) {
      super.renderAsEntity(is);
      this.renderEffect(is, RenderItemBase.swordOverlay);
      int shieldID = ((ItemSwordAndShieldBase)is.getItem()).getShieldID(is);
      GL11.glTranslatef(0.0F, 0.0F, 0.07F);
      RenderItemShield.doRenderShield(new ItemStack(ChocolateQuest.shield, 1, shieldID));
   }
}
