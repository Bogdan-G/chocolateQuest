package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemDagger extends RenderItemBase implements IItemRenderer {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.daggerOverlay);
   }

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.daggerOverlay);
   }

   protected void renderAsEntity(ItemStack is) {
      super.renderAsEntity(is);
      this.renderEffect(is, RenderItemBase.daggerOverlay);
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glDepthMask(false);
      super.renderInventory(itemstack);
      GL11.glTranslatef(1.0F, 1.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 0.0F);
      this.renderEffect(itemstack, RenderItemBase.daggerOverlay);
      GL11.glDepthMask(true);
   }
}
