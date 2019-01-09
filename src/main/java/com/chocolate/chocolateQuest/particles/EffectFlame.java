package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectFlame extends EffectBase {

   double mx;
   double my;
   double mz;


   public EffectFlame(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 10;
      super.worldObj = par1World;
      super.particleTextureIndexX = 3;
      super.particleTextureIndexY = 5;
      super.particleScale = 1.5F;
      super.motionX = this.mx = par8;
      super.motionY = this.my = par10;
      super.motionZ = this.mz = par12;
   }

   public void onUpdate() {
      super.motionX = this.mx;
      super.motionZ = this.mz;
      if(super.particleAge == 2) {
         ++super.particleTextureIndexY;
      }

      if(super.particleAge == 7) {
         ++super.particleTextureIndexY;
      }

      super.onUpdate();
   }
}
