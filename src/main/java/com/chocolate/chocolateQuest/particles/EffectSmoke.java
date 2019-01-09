package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EffectSmoke extends EffectBase {

   int animationTicks = 1;


   public EffectSmoke(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 8;
      super.worldObj = par1World;
      super.iconsAmmount = 128.0F;
      super.iconScale = 8.0F;
      super.particleTextureIndexX = 8;
      super.particleTextureIndexY = 0;
      super.motionX = super.motionZ = super.motionY = 0.0D;
      super.particleRed = (float)par8;
      super.particleGreen = (float)par10;
      super.particleBlue = (float)par12;
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      int iconX = super.particleTextureIndexX;
      int iconY = super.particleTextureIndexY;
      Tessellator tessellator1 = new Tessellator();
      tessellator1.startDrawingQuads();
      tessellator1.setBrightness(this.getBrightnessForRender(par2));
      float iconWidth = super.iconScale / super.iconsAmmount;
      float minX = (float)iconX / super.iconsAmmount * super.iconScale;
      float maxX = minX + iconWidth;
      float minY = (float)iconY / super.iconsAmmount * super.iconScale;
      float maxY = minY + iconWidth;
      float scale = 0.1F * super.particleScale;
      float x = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)par2 - EntityFX.interpPosX);
      float y = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)par2 - EntityFX.interpPosY);
      float z = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)par2 - EntityFX.interpPosZ);
      float f8 = 1.0F;
      tessellator1.setColorRGBA_F(super.particleRed * f8, super.particleGreen * f8, super.particleBlue * f8, 1.0F);
      tessellator1.addVertexWithUV((double)(x - par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z - par5 * scale - par7 * scale), (double)maxX, (double)maxY);
      tessellator1.addVertexWithUV((double)(x - par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z - par5 * scale + par7 * scale), (double)maxX, (double)minY);
      tessellator1.addVertexWithUV((double)(x + par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z + par5 * scale + par7 * scale), (double)minX, (double)minY);
      tessellator1.addVertexWithUV((double)(x + par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z + par5 * scale - par7 * scale), (double)minX, (double)maxY);
      tessellator1.draw();
   }

   public void onUpdate() {
      if(super.ticksExisted % this.animationTicks == 0) {
         --super.particleTextureIndexX;
      }

      super.motionY += 0.005D;
      ++super.ticksExisted;
      super.onUpdate();
   }
}
