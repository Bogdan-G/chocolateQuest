package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectBubble extends EffectBase {

   public EffectBubble(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 20;
      super.worldObj = par1World;
      super.particleTextureIndexX = 0;
      super.particleTextureIndexY = 4;
      super.motionX = super.motionZ = super.motionY = 0.0D;
      super.particleRed = (float)par8;
      super.particleGreen = (float)par10;
      super.particleBlue = (float)par12;
   }

   public void onUpdate() {
      if(super.ticksExisted > 12 && super.ticksExisted % 2 == 0) {
         ++super.particleTextureIndexX;
      } else {
         super.motionY += 0.01D;
      }

      ++super.ticksExisted;
      super.onUpdate();
   }
}
