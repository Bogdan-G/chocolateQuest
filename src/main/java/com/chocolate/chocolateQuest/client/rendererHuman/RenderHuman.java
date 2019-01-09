package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderHuman extends RenderBiped {

   float featherY;
   ItemStack item;
   protected ResourceLocation texture;


   public RenderHuman(ModelBiped modelbase, float f) {
      super(modelbase, f);
      this.featherY = -0.5F;
      this.item = new ItemStack(Items.feather);
      this.texture = new ResourceLocation("chocolatequest:textures/entity/biped/pirate.png");
   }

   public RenderHuman(ModelBiped modelbase, float f, ResourceLocation r) {
      this(modelbase, f);
      this.texture = r;
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelHuman(1.0F, false);
      super.field_82425_h = new ModelHuman(0.5F, false);
   }

   protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
      EntityHumanBase e = (EntityHumanBase)entityliving;
      if(e.isSitting()) {
         this.setSitOffset();
      } else if(e.isSleeping()) {
         GL11.glTranslatef(0.0F, 1.35F, 0.0F);
         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
      }

      super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
      this.renderLeftHandItem(entityliving, f1);
      if(!e.isInvisible()) {
         if(e.isCaptain()) {
            this.renderHelmetFeather(e);
         }

         if(e.shouldRenderCape()) {
            this.renderCape(e);
         }
      }

   }

   protected void renderLivingAt(EntityLivingBase entityliving, double d, double d1, double d2) {
      this.renderLivingLabel(entityliving, entityliving.getCommandSenderName(), d, d1, d2, 64);
      GL11.glTranslatef((float)d, (float)d1, (float)d2);
      EntityHumanBase human = (EntityHumanBase)entityliving;
      if(human.ridingEntity instanceof EntityHorse) {
         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
      }

      if(human.isSpeaking()) {
         this.renderSpeech(human);
      }

   }

   protected void renderHelmetFeather(EntityHumanBase e) {
      GL11.glPushMatrix();
      ((ModelBiped)super.mainModel).bipedHead.postRender(0.0625F);
      GL11.glTranslatef(-0.05F, this.featherY, 0.01F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
      GL11.glRotatef(125.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      super.renderManager.itemRenderer.renderItem(e, this.item, 0);
      GL11.glPopMatrix();
   }

   protected void renderCape(EntityHumanBase e) {
      GL11.glPushMatrix();
      GL11.glTranslatef(-0.5F, 0.0F, 0.2F);
      GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
      ((ModelBiped)super.mainModel).bipedBody.postRender(0.0625F);
      super.renderManager.renderEngine.bindTexture(BDHelper.getItemTexture());
      int spriteIndex = e.getTeamID();
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float i3 = 0.125F;
      float i4 = 0.25F;
      float f6 = 1.0F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)i1, (double)i3);
      tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)i2, (double)i3);
      tessellator.addVertexWithUV(1.0D, 1.2000000476837158D, 0.0D, (double)i2, (double)i4);
      tessellator.addVertexWithUV(0.0D, 1.2000000476837158D, 0.0D, (double)i1, (double)i4);
      tessellator.draw();
      GL11.glPopMatrix();
   }

   protected void renderSpeech(EntityHumanBase e) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, e.height * 1.6F, 0.0F);
      GL11.glRotatef(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(180.0F - super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      super.renderManager.renderEngine.bindTexture(BDHelper.getItemTexture());
      Tessellator tessellator = Tessellator.instance;
      int spriteIndex = (e.ticksExisted / 160 + e.getEntityId()) % 16;
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float i3 = 0.8125F;
      float i4 = 0.875F;
      float size = 0.6F;
      float x = -0.5F;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)x, 0.0D, 0.0D, (double)i1, (double)i3);
      tessellator.addVertexWithUV((double)(x + size), 0.0D, 0.0D, (double)i2, (double)i3);
      tessellator.addVertexWithUV((double)(x + size), (double)size, 0.0D, (double)i2, (double)i4);
      tessellator.addVertexWithUV((double)(x + 0.0F), (double)size, 0.0D, (double)i1, (double)i4);
      tessellator.draw();
      GL11.glPopMatrix();
   }

   protected void setSitOffset() {
      GL11.glTranslatef(0.0F, 0.6F, 0.0F);
   }

   protected void renderLeftHandItem(EntityLivingBase entityliving, float f) {
      ItemStack itemstack = ((EntityHumanBase)entityliving).getHeldItemLeft();
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
            GL11.glRotatef(172.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(18.0F, 0.0F, 1.0F, 0.0F);
         }

         if(itemstack.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockById(Item.getIdFromItem(itemstack.getItem())).getRenderType()))) {
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

            this.func_82422_c();
            GL11.glScalef(var6, -var6, var6);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if(itemstack.getItem() instanceof ItemGolemWeapon) {
            this.doLeftItemRotation();
            this.doLeftHandRotationForGolemWeapon();
         } else {
            this.doLeftItemRotation();
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

   public void doLeftItemRotation() {
      float var6 = 0.375F;
      GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
      GL11.glScalef(var6, var6, var6);
      GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
   }

   public void doLeftHandRotationForGolemWeapon() {
      GL11.glTranslatef(-0.02F, 0.04F, -0.2F);
   }

   protected void rotateCorpse(EntityLivingBase entityliving, float f, float f1, float f2) {
      GL11.glRotatef(180.0F - f1, 0.0F, 1.0F, 0.0F);
      if(entityliving.deathTime > 0) {
         float f3 = ((float)entityliving.deathTime + f2 - 1.0F) / 20.0F * 1.6F;
         f3 = MathHelper.sqrt_float(f3);
         if(f3 > 1.0F) {
            f3 = 1.0F;
         }

         GL11.glRotatef(f3 * this.getDeathMaxRotation(entityliving), 0.0F, 0.0F, 1.0F);
      }

   }

   public void renderCustomItem(EntityLivingBase entityliving, ItemStack itemstack, int i) {
      RenderItemBase.doRenderItem(itemstack, i);
   }

   protected int getColorMultiplier(EntityLivingBase entityliving, float f, float f1) {
      return 0;
   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      EntityHumanBase e = (EntityHumanBase)entityliving;
      float s = e.getSizeModifier();
      GL11.glScalef(s, s, s);
   }

   protected void renderLivingLabel(EntityLivingBase entityliving, String s, double d, double d1, double d2, int i) {
      EntityHumanBase human = (EntityHumanBase)entityliving;
      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
      if(entityliving.isOnSameTeam(player)) {
         float f1 = 1.6F;
         float f2 = 0.01666667F * f1;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d + 0.0F, (float)d1 + entityliving.height + entityliving.height * 0.4F, (float)d2);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-f2, -f2, f2);
         GL11.glDisable(2896);
         GL11.glDisable(3553);
         Tessellator tessellator = Tessellator.instance;
         float width = 20.0F;
         float height = 2.0F;
         tessellator.startDrawingQuads();
         tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
         tessellator.addVertex((double)(-width - 1.0F), -1.0D, 0.0D);
         tessellator.addVertex((double)(-width - 1.0F), (double)height, 0.0D);
         tessellator.addVertex((double)(width + 1.0F), (double)height, 0.0D);
         tessellator.addVertex((double)(width + 1.0F), -1.0D, 0.0D);
         tessellator.draw();
         float healthWidth = human.getHealth() * width * 2.0F / human.getMaxHealth();
         if(healthWidth > 0.0F) {
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0.0F, 1.0F, 0.0F, 1.0F);
            tessellator.addVertex((double)(-width - 1.0F), -1.0D, -0.01D);
            tessellator.addVertex((double)(-width - 1.0F), (double)height, -0.01D);
            tessellator.addVertex((double)(-width + healthWidth + 1.0F), (double)height, -0.01D);
            tessellator.addVertex((double)(-width + healthWidth + 1.0F), -1.0D, -0.01D);
            tessellator.draw();
         }

         if(human.getOwner() == player) {
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            boolean byte0 = false;
            byte byte01 = -10;
            tessellator.startDrawingQuads();
            width = (float)(fontrenderer.getStringWidth(s) / 2);
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((double)(-width - 1.0F), (double)(-1 + byte01), 0.0D);
            tessellator.addVertex((double)(-width - 1.0F), (double)(8 + byte01), 0.0D);
            tessellator.addVertex((double)(width + 1.0F), (double)(8 + byte01), 0.0D);
            tessellator.addVertex((double)(width + 1.0F), (double)(-1 + byte01), 0.0D);
            tessellator.draw();
            GL11.glEnable(3553);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte01, 553648127);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte01, -1);
         }

         GL11.glEnable(3553);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.texture;
   }
}
