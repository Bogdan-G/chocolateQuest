package com.chocolate.chocolateQuest.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ColoredDust extends EntityFX {

   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
   private int coolDown;


   public ColoredDust(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
      super(par1World, par2, par4, par6, par8, par10, par12);
      super.particleMaxAge = 12;
      super.worldObj = par1World;
      this.coolDown = 0;
      super.motionX *= 0.1D;
      super.motionZ *= 0.1D;
      super.motionY *= 0.2D;
      super.particleTextureIndexX = 0;
      super.particleTextureIndexY = 0;
      super.particleRed = (float)par8;
      super.particleGreen = (float)par10;
      super.particleBlue = (float)par12;
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      int iconX = super.particleTextureIndexX;
      int iconY = super.particleTextureIndexY;
      if(super.particleAge < 6) {
         iconX += super.particleAge;
      } else {
         iconX += 10 - super.particleAge;
      }

      Tessellator tessellator1 = new Tessellator();
      tessellator1.startDrawingQuads();
      tessellator1.setBrightness(this.getBrightnessForRender(par2));
      float minX = (float)(iconX % 16) / 16.0F;
      float maxX = minX + 0.0624375F;
      float minY = (float)(iconY % 16) / 16.0F;
      float maxY = minY + 0.0624375F;
      float scale = 0.1F * super.particleScale;
      float x = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)par2 - EntityFX.interpPosX);
      float y = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)par2 - EntityFX.interpPosY);
      float z = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)par2 - EntityFX.interpPosZ);
      float f8 = 1.0F;
      tessellator1.setColorOpaque_F(super.particleRed * f8, super.particleGreen * f8, super.particleBlue * f8);
      tessellator1.addVertexWithUV((double)(x - par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z - par5 * scale - par7 * scale), (double)maxX, (double)maxY);
      tessellator1.addVertexWithUV((double)(x - par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z - par5 * scale + par7 * scale), (double)maxX, (double)minY);
      tessellator1.addVertexWithUV((double)(x + par3 * scale + par6 * scale), (double)(y + par4 * scale), (double)(z + par5 * scale + par7 * scale), (double)minX, (double)minY);
      tessellator1.addVertexWithUV((double)(x + par3 * scale - par6 * scale), (double)(y - par4 * scale), (double)(z + par5 * scale - par7 * scale), (double)minX, (double)maxY);
      tessellator1.draw();
   }

   public void onUpdate() {
      super.onUpdate();
   }

}
