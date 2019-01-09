package com.chocolate.chocolateQuest.builder;

import java.util.Random;

public class Perlin3D {

   private long seed;
   private Random rand;
   private int frequency;


   public Perlin3D(long seed, int octave, Random random) {
      this.seed = seed;
      this.frequency = octave;
      this.rand = new Random();
   }

   public double getNoiseAt(int x, int y, int z) {
      int ymin = (int)Math.floor((double)y / (double)this.frequency);
      int ymax = ymin + 1;
      return (double)this.cosineInterpolate((float)this.getNoiseLevelAtPosition(x, ymin, z), (float)this.getNoiseLevelAtPosition(x, ymax, z), ((float)y - (float)ymin * (float)this.frequency) / (float)this.frequency);
   }

   private double getNoiseLevelAtPosition(int x, int y, int z) {
      int xmin = (int)Math.floor((double)x / (double)this.frequency);
      int xmax = xmin + 1;
      int zmin = (int)Math.floor((double)z / (double)this.frequency);
      int zmax = zmin + 1;
      return (double)this.cosineInterpolate(this.cosineInterpolate((float)this.getRandomAtPosition(xmin, y, zmin), (float)this.getRandomAtPosition(xmax, y, zmin), (float)(x - xmin * this.frequency) / (float)this.frequency), this.cosineInterpolate((float)this.getRandomAtPosition(xmin, y, zmax), (float)this.getRandomAtPosition(xmax, y, zmax), (float)(x - xmin * this.frequency) / (float)this.frequency), ((float)z - (float)zmin * (float)this.frequency) / (float)this.frequency);
   }

   private float cosineInterpolate(float a, float b, float x) {
      float f = (float)((1.0D - Math.cos((double)x * 3.141592653589793D)) * 0.5D);
      return a * (1.0F - f) + b * f;
   }

   private float linearInterpolate(float a, float b, float x) {
      return a * (1.0F - x) + b * x;
   }

   private double getRandomAtPosition(int x, int y, int z) {
      this.rand.setSeed((long)(10000.0D * (Math.sin((double)x) + Math.cos((double)z) + Math.cos((double)y) + Math.tan((double)this.seed))));
      return this.rand.nextDouble();
   }
}
