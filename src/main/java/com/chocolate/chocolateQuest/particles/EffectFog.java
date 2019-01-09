package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectFog extends EffectBase {

   public EffectFog(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 16;
      super.worldObj = par1World;
      super.particleTextureIndexY = 0;
      super.motionX = super.motionZ = super.motionY = 0.0D;
      super.particleScale = 6.0F + super.rand.nextFloat() * 6.0F;
      super.particleRed = (float)par8;
      super.particleGreen = (float)par10;
      super.particleBlue = (float)par12;
      super.iconsAmmount = 8.0F;
      super.iconScale = 16.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      ++super.particleTextureIndexX;
      if(super.particleTextureIndexX == 8) {
         ++super.particleTextureIndexY;
         super.particleTextureIndexX = 0;
      }

   }
}
