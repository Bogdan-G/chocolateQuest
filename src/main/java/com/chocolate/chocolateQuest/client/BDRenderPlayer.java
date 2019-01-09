package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.BDRenderPlayerModel;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class BDRenderPlayer extends RenderPlayer {

   public BDRenderPlayer() {
      BDRenderPlayerModel model = new BDRenderPlayerModel();
      super.mainModel = model;
      ReflectionHelper.setPrivateValue(RenderPlayer.class, this, model, new String[]{"modelBipedMain", "f"});
   }

   protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
      super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
      EntityPlayer ep = (EntityPlayer)entityliving;
      if(ep.inventory.getCurrentItem() != null) {
         ItemStack is = ep.inventory.getCurrentItem();
         if(is.getItem() instanceof ItemSwordAndShieldBase) {
            this.renderLeftHandItem(entityliving, f1, is);
         }
      }

   }

   protected void renderLeftHandItem(EntityLivingBase entityliving, float f, ItemStack is) {
      ItemStack itemstack = new ItemStack(ChocolateQuest.shield, 1, ((ItemSwordAndShieldBase)is.getItem()).getShieldID(is));
      if(itemstack != null) {
         GL11.glPushMatrix();
         ((ModelBiped)super.mainModel).bipedLeftArm.postRender(0.0625F);
         GL11.glTranslatef(0.0325F, 0.4375F, 0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack, ItemRendererHelper.BLOCK_3D);
         float var6;
         if(itemstack.getItem() == ChocolateQuest.shield) {
            var6 = 1.2F;
            GL11.glTranslatef(0.22F, 0.35F, 0.0F);
            GL11.glRotatef(169.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(22.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(8.0F, 0.0F, 1.0F, 0.0F);
         }

         if(itemstack.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))) {
            var6 = 0.5F;
            GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
            var6 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-var6, -var6, var6);
         } else if(itemstack.getItem() == Items.bow) {
            var6 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(var6, -var6, var6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if(itemstack.getItem().isFull3D()) {
            var6 = 0.625F;
            if(itemstack.getItem().shouldRotateAroundWhenRendering()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.125F, 0.0F);
            }

            GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
            GL11.glScalef(var6, -var6, var6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            var6 = 0.375F;
            GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
            GL11.glScalef(var6, var6, var6);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
         }

         super.renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
         if(itemstack.getItem().requiresMultipleRenderPasses()) {
            for(int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++x) {
               super.renderManager.itemRenderer.renderItem(entityliving, itemstack, x);
            }
         }

         GL11.glPopMatrix();
      }

   }
}
