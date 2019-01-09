package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemSpear extends RenderItemBase {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   protected void renderEquipped(EntityLivingBase player, ItemStack itemstack) {
      GL11.glTranslatef(-0.6F, -0.2F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      if(player instanceof EntityPlayer) {
         if(((EntityPlayer)player).getItemInUse() == itemstack) {
            GL11.glTranslatef(0.7F, 1.0F, 0.2F);
            GL11.glRotatef(-104.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
         }
      } else if(player instanceof EntityHumanBase && ((EntityHumanBase)player).isAiming()) {
         GL11.glTranslatef(0.7F, 1.0F, 0.3F);
         GL11.glRotatef(-104.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
      }

      this.doRender(player, itemstack);
      this.renderEffect(itemstack, RenderItemBase.spearOverlay);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      GL11.glScalef(2.5F, 2.5F, 2.5F);
      GL11.glTranslatef(0.12F, 0.05F, 0.8F);
      GL11.glRotatef(-48.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(30.0F, 1.0F, 1.0F, 0.0F);
      if(player instanceof EntityPlayer && (((EntityPlayer)player).getItemInUse() == itemstack || player.isSwingInProgress)) {
         GL11.glTranslatef(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-75.0F, 0.0F, 0.0F, 1.0F);
      }

      this.doRender(player, itemstack);
      this.renderEffect(itemstack, RenderItemBase.spearOverlay);
   }

   public void doRender(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      doRenderItem(itemstack, 0);
   }

   protected void renderAsEntity(ItemStack is) {
      super.renderAsEntity(is);
      this.renderEffect(is, RenderItemBase.spearOverlay);
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glDepthMask(false);
      super.renderInventory(itemstack);
      GL11.glTranslatef(1.0F, 1.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 0.0F);
      this.renderEffect(itemstack, RenderItemBase.spearOverlay);
      GL11.glDepthMask(true);
   }
}
