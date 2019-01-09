package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemGolemWeaponWithCooldown;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderItemPistol extends RenderItemBase {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   protected void renderEquipped(EntityLivingBase par1EntityLiving, ItemStack itemstack) {
      float par1 = 0.0F;
      float f1 = 0.0F;
      GL11.glTranslatef(0.4F, 0.2F, -0.4F);
      GL11.glRotatef(-75.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
      Minecraft mc = Minecraft.getMinecraft();
      doRenderItem(itemstack);
      if(Awakements.isAwakened(itemstack)) {
         renderVanillaEffect(itemstack, 11176072);
      }

   }

   protected void renderInventory(ItemStack itemstack) {
      GL11.glPushMatrix();
      GL11.glScalef(16.0F, 16.0F, 0.0F);
      GL11.glEnable(3008);
      Tessellator tessellator = Tessellator.instance;
      IIcon icon = itemstack.getIconIndex();
      ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMaxV(), icon.getMaxU(), icon.getMinV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
      if(Awakements.isAwakened(itemstack)) {
         renderVanillaEffect(itemstack, 11176072);
      }

      GL11.glDisable(3008);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 14.0F, 150.0F);
      GL11.glScalef(0.2F, 0.2F, 0.2F);
      RenderItemGolemWeaponWithCooldown.renderCoolDown(itemstack, ((ItemGun)itemstack.getItem()).getCooldown(itemstack));
      GL11.glPopMatrix();
   }

   protected void renderFirstPerson(EntityLivingBase player, ItemStack itemstack) {
      doRenderItem(itemstack);
      if(Awakements.isAwakened(itemstack)) {
         renderVanillaEffect(itemstack, 11176072);
      }

   }
}
