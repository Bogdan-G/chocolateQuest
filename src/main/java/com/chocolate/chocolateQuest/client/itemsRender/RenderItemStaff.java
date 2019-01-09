package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemStaff extends RenderItemBase {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glDepthMask(false);
      super.renderInventory(itemstack);
      GL11.glTranslatef(1.0F, 1.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 0.0F);
      if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("rosary")) {
         this.renderEffect(itemstack, RenderItemBase.rosaryOverlay);
      } else if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("wand")) {
         this.renderEffect(itemstack, RenderItemBase.wandOverlay);
      } else {
         this.renderEffect(itemstack, RenderItemBase.staffOverlay);
      }

      GL11.glDepthMask(true);
   }

   protected void renderEquipped(EntityLivingBase entity, ItemStack itemstack) {
      if(itemstack.stackTagCompound == null || !itemstack.stackTagCompound.hasKey("noRender")) {
         if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("rosary")) {
            float scale = 0.5F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(2.55F, 0.7F, -0.4F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(10.0F, 1.0F, 0.0F, 1.0F);
            if(this.isAiming(entity, itemstack)) {
               GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
               GL11.glTranslatef(0.0F, -0.6F, 0.8F);
            }

            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.rosaryOverlay);
         } else if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("wand")) {
            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.wandOverlay);
         } else {
            GL11.glTranslatef(0.2F, -0.32F, -0.0F);
            GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
            GL11.glScalef(1.8F, 1.8F, 1.8F);
            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.staffOverlay);
         }

      }
   }

   public boolean isAiming(EntityLivingBase entity, ItemStack itemstack) {
      return entity instanceof EntityPlayer?((EntityPlayer)entity).getItemInUse() == itemstack:(entity instanceof EntityHumanBase?((EntityHumanBase)entity).isAiming():false);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      if(itemstack.stackTagCompound == null || !itemstack.stackTagCompound.hasKey("noRender")) {
         if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("rosary")) {
            float scale = 0.5F;
            GL11.glScalef(scale, scale, scale);
            if(((EntityPlayer)player).getItemInUse() == itemstack) {
               GL11.glTranslatef(0.5F, -0.8F, 0.8F);
            }

            GL11.glTranslatef(0.55F, 0.7F, -0.4F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.rosaryOverlay);
         } else if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("wand")) {
            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.wandOverlay);
         } else {
            GL11.glTranslatef(0.0F, -0.5F, 0.4F);
            GL11.glScalef(1.5F, 2.5F, 1.5F);
            doRenderItem(itemstack);
            this.renderEffect(itemstack, RenderItemBase.staffOverlay);
         }

      }
   }
}
