package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityHumanGremlin extends EntityHumanMob {

   public EntityHumanGremlin(World world) {
      super(world);
      this.setSize(0.6F, 1.2F);
      super.isImmuneToFire = true;
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.gremlin;
   }

   protected String getLivingSound() {
      return EnumVoice.GOBLIN.say;
   }

   protected String getHurtSound() {
      return EnumVoice.GOBLIN.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.GOBLIN.death;
   }

   public String getCommandSenderName() {
      return this.isBoss()?StatCollector.translateToLocal(this.getEntityName()):super.getCommandSenderName();
   }

   public String getEntityName() {
      return this.isBoss()?"entity.chocolateQuest.gremlinBoss.name":super.getEntityName();
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.coal);
   }

   public boolean canTakeAsPet(EntityCreature f) {
      return f instanceof EntityWolf || f instanceof EntitySpider;
   }
}
