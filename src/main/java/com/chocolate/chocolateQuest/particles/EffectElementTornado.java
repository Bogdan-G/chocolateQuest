package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectElement;
import net.minecraft.world.World;

public class EffectElementTornado extends EffectElement {

   public EffectElementTornado(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12, int element) {
      super(par1World, posX, posY, posZ, par8, par10, par12, element);
      int extraAge = super.rand.nextInt(10 + (int)par8);
      super.ticksExisted = extraAge;
      super.particleMaxAge = (int)(15.0D + par8) + extraAge;
      super.motionY = (10.0D + par8) / 200.0D;
   }

   public void onUpdate() {
      super.motionX = Math.sin((double)(super.ticksExisted / 2)) / 2.0D * (double)super.ticksExisted / 20.0D;
      super.motionZ = Math.cos((double)(super.ticksExisted / 2)) / 2.0D * (double)super.ticksExisted / 20.0D;
      super.onUpdate();
   }
}
