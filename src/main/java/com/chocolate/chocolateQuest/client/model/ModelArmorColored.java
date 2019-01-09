package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelArmorColored extends ModelArmor {

   public ModelArmorColored(int type) {
      super(type);
   }

   public ModelArmorColored(float f, int type) {
      super(f, type);
   }

   public void render(Entity e, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, e);
      if(e != null) {
         ItemStack cachedItem = this.getCachedItem((EntityLivingBase)e, super.type);
         if(this.getRenderPass(super.type) == 0 && cachedItem != null) {
            this.setArmorColor(cachedItem);
            if(super.type == 2) {
               Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmor.layer2);
            } else {
               Minecraft.getMinecraft().renderEngine.bindTexture(ModelArmor.layer1);
            }

            super.render(e, par2, par3, par4, par5, par6, par7);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(cachedItem.getItem().getArmorTexture(cachedItem, e, super.type, "")));
         }
      }

      super.render(e, par2, par3, par4, par5, par6, par7);
   }
}
