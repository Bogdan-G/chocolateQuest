package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.particles.EffectBase;
import net.minecraft.world.World;

public class EffectElement extends EffectBase {

   public static final int FIRE = 0;
   public static final int PHYSIC = 1;
   public static final int BLAST = 2;
   public static final int MAGIC = 3;
   double mx;
   double my;
   double mz;


   public EffectElement(World par1World, double posX, double posY, double posZ, double par8, double par10, double par12, int element) {
      super(par1World, posX, posY, posZ, par8, par10, par12);
      super.particleMaxAge = 10;
      super.worldObj = par1World;
      super.particleTextureIndexX = element;
      super.particleTextureIndexY = 5;
      super.motionX = this.mx = par8;
      super.motionY = this.my = par10;
      super.motionZ = this.mz = par12;
   }

   public void onUpdate() {
      ++super.ticksExisted;
      if(super.particleAge == 3) {
         ++super.particleTextureIndexY;
      }

      if(super.particleAge == 7) {
         ++super.particleTextureIndexY;
      }

      super.onUpdate();
   }
}
