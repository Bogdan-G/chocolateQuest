package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectHate extends EffectBase {

   public EffectHate(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 12;
      super.worldObj = par1World;
      super.particleTextureIndexX = 9;
      super.particleTextureIndexY = 4;
      super.motionX = par8 / (double)super.particleMaxAge;
      super.motionY = par10 / (double)super.particleMaxAge;
      super.motionZ = par12 / (double)super.particleMaxAge;
   }

   public void onUpdate() {
      ++super.ticksExisted;
      if(super.ticksExisted > 1 && super.particleTextureIndexX < 11 && super.ticksExisted % 4 == 0) {
         ++super.particleTextureIndexX;
      }

      super.onUpdate();
   }
}
