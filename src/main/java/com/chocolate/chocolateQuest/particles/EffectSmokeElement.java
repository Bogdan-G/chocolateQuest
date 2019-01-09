package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectSmoke;
import net.minecraft.world.World;

public class EffectSmokeElement extends EffectSmoke {

   public EffectSmokeElement(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12, Elements element) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleRed = element.getColorX();
      super.particleGreen = element.getColorY();
      super.particleBlue = element.getColorZ();
      super.particleMaxAge *= 3;
      super.animationTicks = 3;
   }
}
