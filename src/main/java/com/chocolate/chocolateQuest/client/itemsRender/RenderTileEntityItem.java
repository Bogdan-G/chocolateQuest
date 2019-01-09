package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderTileEntityItem extends RenderItemBase {

   TileEntitySpecialRenderer render;
   TileEntity entity;


   public RenderTileEntityItem(TileEntity e, TileEntitySpecialRenderer render) {
      this.entity = e;
      this.render = render;
      render.func_147497_a(TileEntityRendererDispatcher.instance);
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      EntityLivingBase p;
      if(type == ItemRenderType.EQUIPPED) {
         p = (EntityLivingBase)data[1];
         this.renderEquipped(p, item);
      }

      if(type == ItemRenderType.ENTITY) {
         Entity p1 = (Entity)data[1];
         this.render(p1, item, 0);
      }

      if(type == ItemRenderType.INVENTORY) {
         this.renderInventory(item);
      }

      if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         p = (EntityLivingBase)data[1];
         this.renderEquipped(p, item);
      }

   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glScalef(10.0F, -10.0F, 10.0F);
      GL11.glTranslatef(0.5F, -1.4F, 0.0F);
      GL11.glRotatef(-30.0F, -0.6F, 1.0F, 0.0F);
      this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F);
   }

   protected void render(Entity entity, ItemStack itemstack, int par3) {
      GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
      this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F);
   }

   protected void renderEquipped(EntityLivingBase entity, ItemStack itemstack) {
      GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
      this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F);
   }
}
