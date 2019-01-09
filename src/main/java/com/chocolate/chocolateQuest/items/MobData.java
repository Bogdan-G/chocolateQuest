package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import java.lang.reflect.Constructor;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

class MobData {

   private Class mobClass;
   String name;
   int color;
   DungeonMonstersBase monster;
   boolean isBoss;


   public MobData(Class mob, String name, int color) {
      this.mobClass = mob;
      this.name = name;
      this.color = color;
   }

   public MobData(DungeonMonstersBase monster, String name, int color, boolean isBoss) {
      this.monster = monster;
      this.name = name;
      this.color = color;
      this.isBoss = isBoss;
   }

   public Entity getEntity(World world) {
      Object e = null;
      if(this.monster != null) {
         if(this.isBoss) {
            e = this.monster.getBoss(world, 0, 0, 0);
         } else {
            e = this.monster.getEntity(world, 0, 0, 0);
         }
      }

      if(this.mobClass != null) {
         try {
            Constructor c = this.mobClass.getDeclaredConstructor(new Class[]{World.class});
            c.setAccessible(true);
            e = (Entity)c.newInstance(new Object[]{world});
         } catch (Exception var5) {
            e = new EntityHumanBase(world);
         }
      }

      return (Entity)e;
   }
}
