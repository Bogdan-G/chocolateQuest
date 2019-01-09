package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectLiquidDrip extends EffectBase {

   public EffectLiquidDrip(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 30;
      super.worldObj = par1World;
      super.particleTextureIndexX = 5;
      super.particleTextureIndexY = 4;
      super.motionX = super.motionZ = super.motionY = 0.0D;
      super.particleRed = (float)par8;
      super.particleGreen = (float)par10;
      super.particleBlue = (float)par12;
   }

   public void onUpdate() {
      super.motionY = -0.6D;
      ++super.ticksExisted;
      if(super.ticksExisted > 1 && super.fallDistance == 0.0F && super.particleTextureIndexX < 8 && super.ticksExisted % 2 == 0) {
         ++super.particleTextureIndexX;
      }

      super.onUpdate();
   }
}
