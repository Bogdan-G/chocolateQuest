package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectFog;
import net.minecraft.world.World;

public class EffectCloud extends EffectFog {

   public EffectCloud(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 16;
      super.particleScale = 32.0F + super.rand.nextFloat() * 16.0F;
   }
}
