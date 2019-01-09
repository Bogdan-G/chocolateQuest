package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntityPartRidable;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.utils.MobTeam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityBaseBoss {

   public AttackKick kickHelper;
   public EntityPart head;
   MobTeam team = new MobTeam("mob_undead");


   public EntityGiantZombie(World world) {
      super(world);
      this.resize();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D + (double)(super.size / 70.0F));
      this.kickHelper = new AttackKick(this);
      super.xpRatio = 2.0F;
      super.projectileDefense = 40;
      super.blastDefense = 25;
      super.magicDefense = -20;
      super.fireDefense = 0;
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D + (double)super.lvl * 0.02D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D + (double)super.lvl * 0.4D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D + (double)super.lvl * 200.0D);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void addAITasks() {
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new AIBossAttack(this, 1.0F, false));
      super.targetTasks.addTask(1, new AITargetHurtBy(this, true));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
   }

   protected boolean isAIEnabled() {
      return true;
   }

   public void initBody() {
      this.head = new EntityPartRidable(super.worldObj, this, 0, 0.0F, super.size / 20.0F, super.size * 1.2F);
      this.head.setSize(super.size / 3.0F, super.size / 3.0F);
      if(!super.worldObj.isRemote) {
         super.worldObj.spawnEntityInWorld(this.head);
      }

      super.initBody();
   }

   public void setPart(EntityPart entityPart, int partID) {
      super.setPart(entityPart, partID);
      entityPart.setSize(super.size / 3.0F, super.size / 3.0F);
   }

   public boolean attackFromPart(DamageSource par1DamageSource, float par2, EntityPart part) {
      if(part == this.head) {
         if(par1DamageSource.isProjectile()) {
            par2 *= 2.0F;
         } else {
            par2 *= super.lvl;
         }
      }

      return super.attackFromPart(par1DamageSource, par2, part);
   }

   public void onUpdate() {
      if(!super.isDead) {
         this.kickHelper.onUpdate();
      }

      super.onUpdate();
   }

   public void animationBoss(byte animType) {
      if(!super.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), animType);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      switch(animType) {
      default:
         this.kickHelper.kick(animType);
      }
   }

   public void attackEntity(Entity target, float dist) {
      if(super.ticksExisted % 10 == 0 && !super.worldObj.isRemote && dist < (super.width + 3.0F) * (super.width + 3.0F)) {
         this.kickHelper.attackTarget(target);
      }

   }

   protected void resize() {
      this.setSize(super.size / 3.0F, super.size * 1.2F);
   }

   public float getMinSize() {
      return 1.1F;
   }

   public float getSizeVariation() {
      return 1.4F;
   }

   public boolean canAttackClass(Class par1Class) {
      return EntityGhast.class != par1Class;
   }

   protected void fall(float par1) {}

   public boolean isEntityEqual(Entity par1Entity) {
      return this == par1Entity || par1Entity == this.head;
   }

   public boolean attackInProgress() {
      return this.kickHelper.isAttackInProgress();
   }

   protected void dropFewItems(boolean flag, int i) {
      int ammount = 4 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty());
      this.entityDropItem(new ItemStack(Items.emerald, ammount + this.getRNG().nextInt(5), 0), super.size);
      this.entityDropItem(new ItemStack(Items.rotten_flesh, ammount / 2 + this.getRNG().nextInt(5), 0), super.size);
   }

   public Team getTeam() {
      return this.team;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return EnumVoice.ZOMBIE.say;
   }

   protected String getHurtSound() {
      return EnumVoice.ZOMBIE.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.ZOMBIE.death;
   }
}
