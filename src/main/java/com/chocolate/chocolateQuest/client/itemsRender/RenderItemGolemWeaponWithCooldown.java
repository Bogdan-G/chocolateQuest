package com.chocolate.chocolateQuest.client.itemsRender;

import com.chocolate.chocolateQuest.client.itemsRender.RenderItemGolemWeapon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGolemWeapon;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderItemGolemWeaponWithCooldown extends RenderItemGolemWeapon {

   public RenderItemGolemWeaponWithCooldown(ModelGolemWeapon model) {
      super(model);
      super.model = model;
   }

   protected void renderInventory(ItemStack itemstack) {
      super.renderInventory(itemstack);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 14.0F, 50.0F);
      GL11.glScalef(0.2F, 0.2F, 0.2F);
      renderCoolDown(itemstack, ((ItemGun)itemstack.getItem()).getCooldown(itemstack));
      GL11.glPopMatrix();
   }

   public static void renderCoolDown(ItemStack itemstack, int maxCooldown) {
      long lastShootTick = itemstack.stackTagCompound == null?0L:itemstack.stackTagCompound.getLong("ticks");
      long currentTick = Minecraft.getMinecraft().theWorld.getWorldTime();
      float min = (float)(currentTick - lastShootTick);
      float max = (float)maxCooldown;
      if((float)lastShootTick + max >= (float)currentTick && currentTick >= lastShootTick) {
         int width = (int)(min / max * 80.0F);
         byte posX = 0;
         byte posY = 5;
         Gui.drawRect(posX, posY, posX + width, posY + 5, -16746497);
      }

   }
}
