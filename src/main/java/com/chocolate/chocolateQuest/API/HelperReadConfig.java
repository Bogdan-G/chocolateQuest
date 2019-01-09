package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.API.BuilderBlockData;
import com.chocolate.chocolateQuest.builder.BuilderBlockDataBiomeFiller;
import com.chocolate.chocolateQuest.builder.BuilderBlockDataBiomeTop;
import com.chocolate.chocolateQuest.builder.BuilderBlockOldStoneBrick;
import com.chocolate.chocolateQuest.builder.BuilderBlockStoneJungle;
import java.util.Properties;
import java.util.StringTokenizer;
import net.minecraft.block.Block;

public class HelperReadConfig {

   static BuilderBlockData biomeFiller = new BuilderBlockDataBiomeFiller();
   static BuilderBlockData biomeTop = new BuilderBlockDataBiomeTop();
   static BuilderBlockData crackedBrick = new BuilderBlockOldStoneBrick();
   static BuilderBlockData jungleBrick = new BuilderBlockStoneJungle();


   public static String getStringProperty(Properties prop, String name, String defaultValue) {
      String s = prop.getProperty(name);
      return s == null?defaultValue:s;
   }

   public static boolean getBooleanProperty(Properties prop, String name, boolean defaultValue) {
      String s = prop.getProperty(name);
      if(s == null) {
         return defaultValue;
      } else {
         s = s.trim();
         return s.equals("true");
      }
   }

   public static int getIntegerProperty(Properties prop, String name, int defaultValue) {
      String s = prop.getProperty(name);
      if(s == null) {
         return defaultValue;
      } else {
         try {
            s = s.trim();
            int ret = Integer.parseInt(s);
            return ret;
         } finally {
            ;
         }
      }
   }

   public static int getIntegerProperty(Properties prop, String name, int defaultValue, int minValue, int maxValue) {
      int i = getIntegerProperty(prop, name, defaultValue);
      i = Math.max(minValue, i);
      i = Math.min(maxValue, i);
      return i;
   }

   public static int[] getIntegerArray(Properties prop, String name, int defaultValue) {
      String s = prop.getProperty(name);
      int[] ids = null;
      if(s != null) {
         StringTokenizer stkn = new StringTokenizer(s, ",");
         ids = new int[stkn.countTokens()];

         for(int i = 0; i < ids.length; ++i) {
            String nextToken = stkn.nextToken().trim();
            ids[i] = Integer.parseInt(nextToken);
         }
      }

      if(ids == null) {
         ids = new int[]{defaultValue};
      }

      return ids;
   }

   public static BuilderBlockData getBlock(Properties prop, String name, BuilderBlockData defaultValue) {
      Block block = null;
      int metadata = 0;
      String s = prop.getProperty(name);
      if(s != null) {
         if(s.startsWith("@")) {
            if(s.equals("@biome_top")) {
               return biomeTop;
            }

            if(s.equals("@biome_filler")) {
               return biomeFiller;
            }

            if(s.equals("@brick_mossy")) {
               return jungleBrick;
            }

            if(s.equals("@brick_cracked")) {
               return crackedBrick;
            }
         }

         StringTokenizer b = new StringTokenizer(s, ",");
         block = Block.getBlockFromName(((String)b.nextElement()).trim());
         if(b.hasMoreElements()) {
            String md = (String)((String)b.nextElement());
            if(md != null) {
               metadata = Integer.parseInt(md.trim());
            }
         }
      }

      if(block == null) {
         return defaultValue;
      } else {
         BuilderBlockData b1 = new BuilderBlockData(block, metadata);
         return b1;
      }
   }

}
