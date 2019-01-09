package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.world.World;

public class EntitySpaceWarrior extends EntityHumanMob {

   public EntitySpaceWarrior(World world) {
      super(world);
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.spaceWarrior;
   }
}
