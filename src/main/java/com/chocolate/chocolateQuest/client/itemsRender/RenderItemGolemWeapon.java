package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderItemGolemWeapon extends RenderItemBase {

   public static final ResourceLocation texture = new ResourceLocation("chocolateQuest:textures/entity/golemWeapon.png");
   ModelGolemWeapon model;


   public RenderItemGolemWeapon(ModelGolemWeapon model) {
      this.model = model;
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      Minecraft.getMinecraft().renderEngine.bindTexture(texture);
      EntityLivingBase p;
      if(type == ItemRenderType.EQUIPPED) {
         p = (EntityLivingBase)data[1];
         this.renderEquipped(p, item);
      }

      if(type == ItemRenderType.ENTITY) {
         Entity p1 = (Entity)data[1];
         GL11.glTranslatef(-0.4F, 0.0F, 0.0F);
         this.render(p1, item, 0);
      }

      if(type == ItemRenderType.INVENTORY) {
         this.renderInventory(item);
      }

      if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         p = (EntityLivingBase)data[1];
         this.renderFP(p, item, 0);
      }

   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef(2.0F, 0.0F, 0.0F);
      GL11.glRotatef(190.0F, 0.0F, 0.0F, 1.0F);
      GL11.glScalef(30.0F, 30.0F, 30.0F);
      GL11.glRotatef(25.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-25.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      this.renderModel(itemstack);
      GL11.glPopMatrix();
   }

   protected void render(Entity entity, ItemStack itemstack, int par3) {
      GL11.glScalef(3.0F, 3.0F, 3.0F);
      this.renderModel(itemstack);
   }

   protected void renderEquipped(EntityLivingBase entity, ItemStack itemstack) {
      GL11.glRotatef(190.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.6F, -0.2F, -0.4F);
      if(entity instanceof EntityGolemMecha) {
         GL11.glTranslatef(0.4F, -0.08F, -0.1F);
         GL11.glScalef(1.1F, 1.1F, 1.1F);
      }

      GL11.glRotatef(-14.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(2.2F, 2.2F, 2.2F);
      this.renderModel(itemstack);
   }

   protected void renderFP(EntityLivingBase entity, ItemStack itemstack, int par3) {
      GL11.glTranslatef(0.54F, 0.32F, 0.31F);
      GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(80.0F, 0.0F, 1.0F, 0.0F);
      this.renderEquipped(entity, itemstack);
   }

   public void renderModel(ItemStack is) {
      this.model.render(is);
      if(Awakements.isAwakened(is)) {
         Minecraft mc = Minecraft.getMinecraft();
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glDepthFunc(514);
         GL11.glBlendFunc(768, 1);
         GL11.glDisable(3008);
         mc.renderEngine.bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
         float red = 0.4F;
         float green = 0.4F;
         float blue = 0.8F;
         GL11.glMatrixMode(5890);
         GL11.glPushMatrix();
         GL11.glColor4f(red, green, blue, 0.8F);
         float f8 = 0.125F;
         GL11.glScalef(f8, f8, f8);
         float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
         GL11.glTranslatef(f9, 0.0F, 0.0F);
         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
         this.model.render(is);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glDepthFunc(515);
         GL11.glDisable(3042);
      }

   }

}
