package com.chocolate.chocolateQuest.particles;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.ColoredDust;
import com.chocolate.chocolateQuest.particles.EffectBubble;
import com.chocolate.chocolateQuest.particles.EffectCloud;
import com.chocolate.chocolateQuest.particles.EffectElement;
import com.chocolate.chocolateQuest.particles.EffectElementTornado;
import com.chocolate.chocolateQuest.particles.EffectFlame;
import com.chocolate.chocolateQuest.particles.EffectFog;
import com.chocolate.chocolateQuest.particles.EffectHate;
import com.chocolate.chocolateQuest.particles.EffectLiquidDrip;
import com.chocolate.chocolateQuest.particles.EffectSmoke;
import com.chocolate.chocolateQuest.particles.EffectSmokeElement;
import com.chocolate.chocolateQuest.particles.EffectSmokeElementBig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EffectManager {

   public static final int slimeFog = 0;
   public static final int bubble = 1;
   public static final int liquid_Drop = 2;
   public static final int flame = 3;
   public static final int dust = 4;
   public static final int cloud = 5;
   public static final int vanilla_cloud = 6;
   public static final int dust_walker = 7;
   public static final int hate = 8;
   public static final int element = 0;
   public static final int element_tornado = 1;
   public static final int element_smoke = 2;
   public static final int element_smoke_big = 3;


   public static void spawnParticle(int particle, World worldObj, double x, double y, double z) {
      spawnParticle(particle, worldObj, x, y, z, 0.0D, 0.0D, 0.0D);
   }

   public static void spawnParticle(int particle, World worldObj, double x, double y, double z, double mx, double my, double mz) {
      switch(particle) {
      case 0:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectFog(worldObj, x, y, z, mx, my, mz));
         return;
      case 1:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectBubble(worldObj, x, y, z, mx, my, mz));
         return;
      case 2:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectLiquidDrip(worldObj, x, y, z, mx, my, mz));
         return;
      case 3:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectFlame(worldObj, x, y, z, mx, my, mz));
         return;
      case 4:
         Minecraft.getMinecraft().effectRenderer.addEffect(new ColoredDust(worldObj, x, y, z, mx, my, mz));
         return;
      case 5:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectCloud(worldObj, x, y, z, mx, my, mz));
         return;
      case 6:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectSmoke(worldObj, x, y, z, mx, my, mz));
         return;
      case 7:
         if(shouldSpawnParticle()) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new ColoredDust(worldObj, x, y, z, mx, my, mz));
         }

         return;
      case 8:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectHate(worldObj, x, y, z, mx, my, mz));
         return;
      default:
      }
   }

   public static void spawnElementParticle(int particle, World worldObj, double x, double y, double z, double mx, double my, double mz, Elements element) {
      int elementType = element.ordinal();
      switch(particle) {
      case 1:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectElementTornado(worldObj, x, y, z, mx, my, mz, elementType));
         return;
      case 2:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectSmokeElement(worldObj, x, y, z, mx, my, mz, element));
         return;
      case 3:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectSmokeElementBig(worldObj, x, y, z, mx, my, mz, element));
         return;
      default:
         Minecraft.getMinecraft().effectRenderer.addEffect(new EffectElement(worldObj, x, y, z, mx, my, mz, elementType));
      }
   }

   public static boolean shouldSpawnParticle() {
      return Minecraft.getMinecraft().gameSettings.particleSetting == 0;
   }
}
