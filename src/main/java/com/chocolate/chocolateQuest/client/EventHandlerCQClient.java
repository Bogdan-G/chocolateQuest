package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.model.ModelArmor;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import org.lwjgl.opengl.GL11;

public class EventHandlerCQClient {

   boolean test = false;


   @SubscribeEvent
   public void RenderLiving(Post event) {
      ModelArmor.renderPass0 = 0;
      ModelArmor.renderPass1 = 0;
      ModelArmor.renderPass2 = 0;
      ModelArmor.renderPass3 = 0;
   }

   @SubscribeEvent
   public void RenderPlayerEvent(net.minecraftforge.client.event.RenderPlayerEvent.Post event) {
      ModelBiped mainModel = event.renderer.modelBipedMain;
      RenderManager renderManager = RenderManager.instance;
      EntityPlayer entityLiving = event.entityPlayer;
      ItemStack itemstack = entityLiving.getHeldItem();
      if(itemstack != null) {
         if(itemstack.getItem() instanceof ItemSwordAndShieldBase) {
            itemstack = new ItemStack(ChocolateQuest.shield, 1, ((ItemSwordAndShieldBase)itemstack.getItem()).getShieldID(itemstack));
            GL11.glPushMatrix();
            float f5 = 0.0625F;
            GL11.glDisable(2884);
            GL11.glRotatef(180.0F - entityLiving.renderYawOffset, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            GL11.glTranslatef(0.0F, 1.42F * f5 - 0.0078125F, 0.0F);
            if(itemstack != null) {
               GL11.glPushMatrix();
               mainModel.bipedLeftArm.postRender(0.0625F);
               GL11.glTranslatef(0.0325F, 0.4375F, 0.0625F);
               IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
               boolean var10000;
               if(customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack, ItemRendererHelper.BLOCK_3D)) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               if(itemstack.getItem() == ChocolateQuest.shield) {
                  GL11.glTranslatef(-0.02F, 0.15F, 0.0F);
                  GL11.glRotatef(8.0F, 0.0F, 1.0F, 0.0F);
               }

               float var6;
               if(itemstack.getItem() == ChocolateQuest.shield) {
                  var6 = 1.2F;
                  GL11.glTranslatef(0.22F, 0.35F, 0.0F);
                  if(entityLiving.isBlocking()) {
                     GL11.glRotatef(86.0F, 0.0F, 1.0F, 0.0F);
                     GL11.glRotatef(-22.0F, 1.0F, 0.0F, 0.0F);
                     GL11.glRotatef(-18.0F, 0.0F, 1.0F, 0.0F);
                     GL11.glTranslatef(0.28F, 0.0F, -0.46F);
                  }

                  GL11.glRotatef(169.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glRotatef(22.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(8.0F, 0.0F, 1.0F, 0.0F);
               }

               var6 = 0.375F;
               GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
               GL11.glScalef(var6, var6, var6);
               GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
               renderManager.itemRenderer.renderItem(entityLiving, itemstack, 0);
               if(itemstack.getItem().requiresMultipleRenderPasses()) {
                  for(int x = 1; x < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++x) {
                     renderManager.itemRenderer.renderItem(entityLiving, itemstack, x);
                  }
               }

               GL11.glPopMatrix();
               GL11.glEnable(2884);
            }

            GL11.glPopMatrix();
         }
      }
   }
}
