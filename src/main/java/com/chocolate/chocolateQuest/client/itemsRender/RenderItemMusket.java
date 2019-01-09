package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemPistol;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemMusket extends RenderItemPistol {

   ItemStack ironDagger;
   ItemStack diamondDagger;
   ItemStack tricksterDagger;
   ItemStack monkingDagger;


   public RenderItemMusket() {
      this.ironDagger = new ItemStack(ChocolateQuest.ironDagger);
      this.diamondDagger = new ItemStack(ChocolateQuest.diamondDagger);
      this.tricksterDagger = new ItemStack(ChocolateQuest.tricksterDagger);
      this.monkingDagger = new ItemStack(ChocolateQuest.monkingDagger);
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type != ItemRenderType.EQUIPPED_FIRST_PERSON && type != ItemRenderType.ENTITY?super.handleRenderType(item, type):true;
   }

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      boolean isAiming = false;
      if(par1EntityLiving instanceof EntityPlayer) {
         if(((EntityPlayer)par1EntityLiving).getItemInUse() == itemstack) {
            isAiming = true;
         }
      } else if(par1EntityLiving instanceof EntityHumanBase && ((EntityHumanBase)par1EntityLiving).isAiming()) {
         isAiming = true;
      }

      if(!isAiming) {
         GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
      }

      float scale = 2.0F;
      GL11.glTranslatef(-0.5F, -0.2F, 0.4F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      super.renderEquipped(par1EntityLiving, itemstack);
      this.renderBayonet(itemstack);
   }

   protected void renderInventory(ItemStack itemstack) {
      ItemStack bayonet = this.getBayonet(itemstack);
      if(bayonet != null) {
         GL11.glPushMatrix();
         GL11.glEnable(3008);
         GL11.glScalef(8.0F, 8.0F, 0.0F);
         GL11.glTranslatef(-0.2F, 1.0F, 0.0F);
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
         Tessellator tessellator = Tessellator.instance;
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         IIcon icon = bayonet.getIconIndex();
         ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
         GL11.glPopMatrix();
      }

      super.renderInventory(itemstack);
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      GL11.glTranslatef(0.0F, -0.3F, 0.0F);
      GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      doRenderItem(itemstack);
      if(Awakements.isAwakened(itemstack)) {
         renderVanillaEffect(itemstack, 11176072);
      }

      this.renderBayonet(itemstack);
   }

   public ItemStack getBayonet(ItemStack itemstack) {
      int i = ChocolateQuest.musket.getBayonet(itemstack);
      switch(i) {
      case 1:
         return this.ironDagger;
      case 2:
         return this.diamondDagger;
      case 3:
         return this.tricksterDagger;
      case 4:
         return this.monkingDagger;
      default:
         return null;
      }
   }

   protected void renderBayonet(ItemStack itemstack) {
      ItemStack bayonet = this.getBayonet(itemstack);
      if(bayonet != null) {
         float scale = 0.5F;
         GL11.glScalef(scale, scale, scale);
         GL11.glTranslatef(1.2F, 2.0F, 0.0F);
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
         doRenderItem(bayonet, 11176072, false);
         this.renderEffect(itemstack, RenderItemBase.daggerOverlay);
      }

   }

   protected void renderAsEntity(ItemStack is) {
      super.renderAsEntity(is);
      this.renderBayonet(is);
   }
}
