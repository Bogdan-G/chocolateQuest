package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EffectBase extends EntityFX {

   public static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
   protected float iconsAmmount = 16.0F;
   protected float iconScale = 8.0F;


   public EffectBase(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      int iconX = super.particleTextureIndexX;
      int iconY = super.particleTextureIndexY;
      Tessellator tessellator1 = new Tessellator();
      tessellator1.startDrawingQuads();
      tessellator1.setBrightness(this.getBrightnessForRender(par2));
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getParticleTexture());
      float iconWidth = this.iconScale / 128.0F;
      float minX = (float)iconX / this.iconsAmmount;
      float maxX = minX + iconWidth;
      float minY = (float)iconY / this.iconsAmmount;
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
      Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
   }

}
