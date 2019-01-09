package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemTwoHandedSword extends RenderItemBase implements IItemRenderer {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      GL11.glTranslatef(-0.9F, -0.08F, 0.14F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.bigSwordOverlay);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      GL11.glTranslatef(0.0F, -0.3F, 0.6F);
      GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      doRenderItem(itemstack);
      this.renderEffect(itemstack, RenderItemBase.bigSwordOverlay);
   }

   protected void renderAsEntity(ItemStack is) {
      super.renderAsEntity(is);
      this.renderEffect(is, RenderItemBase.bigSwordOverlay);
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glDepthMask(false);
      super.renderInventory(itemstack);
      GL11.glTranslatef(1.0F, 1.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 0.0F);
      this.renderEffect(itemstack, RenderItemBase.bigSwordOverlay);
      GL11.glDepthMask(true);
   }
}
