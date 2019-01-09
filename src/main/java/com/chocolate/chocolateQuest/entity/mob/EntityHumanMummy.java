package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityHumanMummy extends EntityHumanZombie {

   public EntityHumanMummy(World world) {
      super(world);
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.mummy;
   }

   public int getInteligence() {
      return 3;
   }

   public String getCommandSenderName() {
      return this.isBoss()?StatCollector.translateToLocal(this.getEntityName()):super.getCommandSenderName();
   }

   public String getEntityName() {
      return this.isBoss()?"entity.chocolateQuest.mummyBoss.name":super.getEntityName();
   }
}
