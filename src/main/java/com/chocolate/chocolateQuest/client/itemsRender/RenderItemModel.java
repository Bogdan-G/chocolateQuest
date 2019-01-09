package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderItemModel extends RenderItemBase {

   public final ResourceLocation texture;
   ModelArmor model;
   int armorType;
   final float div1_16 = 0.0625F;


   public RenderItemModel(ItemArmor item) {
      ItemStack is = new ItemStack(item);
      this.armorType = item.armorType;
      this.texture = new ResourceLocation(item.getArmorTexture(is, (Entity)null, this.armorType, ""));
      if(item.getArmorModel((EntityLivingBase)null, is, this.armorType) instanceof ModelArmor) {
         this.model = (ModelArmor)item.getArmorModel((EntityLivingBase)null, is, this.armorType);
      } else {
         this.model = ClientProxy.defaultArmor;
      }

   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      item.getItem().getArmorModel((EntityLivingBase)null, item, this.armorType);
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
      EntityLivingBase p;
      if(type == ItemRenderType.EQUIPPED) {
         p = (EntityLivingBase)data[1];
         this.renderEquipped(p, item);
      }

      if(type == ItemRenderType.ENTITY) {
         Entity p1 = (Entity)data[1];
         this.render(p1, item);
      }

      if(type == ItemRenderType.INVENTORY) {
         this.renderInventory(item);
      }

      if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         p = (EntityLivingBase)data[1];
         this.renderFP(p, item);
      }

   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glDisable(2884);
      GL11.glEnable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      int armorOffset = this.armorType * 6;
      if(this.armorType == 2) {
         armorOffset += 2;
      }

      GL11.glTranslatef(8.0F, (float)(10 - armorOffset), 0.0F);
      GL11.glScalef(15.0F, 15.0F, 15.0F);
      GL11.glRotatef(-25.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(220.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
      this.model.renderArmor(itemstack, 0.0625F);
      GL11.glEnable(2884);
   }

   protected void render(Entity entity, ItemStack itemstack) {
      GL11.glDisable(2884);
      float armorOffset = (float)this.armorType * 1.2F;
      if(this.armorType == 3) {
         armorOffset = 3.2F;
      }

      GL11.glTranslatef(0.0F, armorOffset, 0.0F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      if(entity instanceof EntityItem) {
         this.model.renderArmor(((EntityItem)entity).getEntityItem(), 0.0625F);
      } else {
         this.model.render(entity, (float)this.armorType, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      }

      GL11.glEnable(2884);
   }

   protected void renderEquipped(EntityLivingBase entity, ItemStack itemstack) {
      float armorOffset = 0.4F - (float)this.armorType * 0.6F;
      if(this.armorType == 2) {
         armorOffset += 0.2F;
      }

      GL11.glTranslatef(0.5F, armorOffset * 0.5F, 0.4F + armorOffset);
      GL11.glRotatef(-6.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(16.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(24.0F, 0.0F, 1.0F, 1.0F);
      this.model.renderArmor(itemstack, 0.0625F);
   }

   protected void renderFP(EntityLivingBase entity, ItemStack itemstack) {}

   protected void renderModel(EntityLivingBase entity, ItemStack itemstack) {
      this.model.render(entity, (float)this.armorType, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
   }
}
