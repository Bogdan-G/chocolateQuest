package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBubble;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBulletGolem;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileBulletPistol;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileDummy;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileEarthQuake;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileEarthQuakeArea;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileFireBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileFireFalling;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileGrenade;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileHealBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagic;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicAimed;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicArea;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicArrow;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicExplosive;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicHaze;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicMeteor;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicPrison;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicShield;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicStorm;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicStormProjectile;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicTornado;
import com.chocolate.chocolateQuest.entity.projectile.ProjectilePoisonBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileRocket;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileVapiricBall;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;

public abstract class ProjectileBase {

   EntityBaseBall entity;
   public static final byte POISONBALL = 0;
   public static final byte BULLETPISTOL = 1;
   public static final byte BULLETPISTOLGOLEM = 2;
   public static final byte QUAKE = 3;
   public static final byte QUAKEAREA = 4;
   public static final byte FIREBALL = 5;
   public static final byte FIREFALLING = 6;
   public static final byte BUBBLE = 7;
   public static final byte VAMPIRIC = 8;
   public static final byte HEALBALL = 9;
   public static final byte TORNADO = 10;
   public static final byte ROCKET = 11;
   public static final byte GRENADE = 12;
   public static final byte MAGIC = 100;
   public static final byte MAGIC_AIM = 101;
   public static final byte MAGIC_AREA = 102;
   public static final byte MAGIC_SHIELD = 103;
   public static final byte MAGIC_STORM = 104;
   public static final byte MAGIC_STORM_PROJECTILE = 105;
   public static final byte MAGIC_EXPLOSIVE = 106;
   public static final byte MAGIC_METEOR = 107;
   public static final byte MAGIC_PRISON = 108;
   public static final byte MAGIC_ARROW = 109;
   public static final byte MAGIC_HAZE = 110;


   public ProjectileBase(EntityBaseBall entity) {
      this.entity = entity;
   }

   public void onUpdateInAir() {}

   public abstract int getTextureIndex();

   public abstract void onImpact(MovingObjectPosition var1);

   public boolean canBounce() {
      return false;
   }

   public float getSize() {
      return 1.0F;
   }

   public float getSizeBB() {
      return this.getSize();
   }

   public void onSpawn() {}

   public void onDead() {}

   public float getGravityVelocity() {
      return 0.0F;
   }

   public int getMaxLifeTime() {
      return 200;
   }

   public int getRopeColor() {
      return -1;
   }

   public void attackFrom(DamageSource d, float damage) {}

   public double getYOffset() {
      return 0.0D;
   }

   public boolean longRange() {
      return true;
   }

   public boolean renderAsArrow() {
      return false;
   }

   public float getZOffset() {
      return 0.0F;
   }

   public static ProjectileBase getBallData(EntityBaseBall entity) {
      byte type = entity.getType();
      switch(type) {
      case 0:
         return new ProjectilePoisonBall(entity);
      case 1:
         return new ProjectileBulletPistol(entity);
      case 2:
         return new ProjectileBulletGolem(entity);
      case 3:
         return new ProjectileEarthQuake(entity);
      case 4:
         return new ProjectileEarthQuakeArea(entity);
      case 5:
         return new ProjectileFireBall(entity);
      case 6:
         return new ProjectileFireFalling(entity);
      case 7:
         return new ProjectileBubble(entity);
      case 8:
         return new ProjectileVapiricBall(entity);
      case 9:
         return new ProjectileHealBall(entity);
      case 10:
         return new ProjectileMagicTornado(entity);
      case 11:
         return new ProjectileRocket(entity);
      case 12:
         return new ProjectileGrenade(entity);
      case 100:
         return new ProjectileMagic(entity);
      case 101:
         return new ProjectileMagicAimed(entity);
      case 102:
         return new ProjectileMagicArea(entity);
      case 103:
         return new ProjectileMagicShield(entity);
      case 104:
         return new ProjectileMagicStorm(entity);
      case 105:
         return new ProjectileMagicStormProjectile(entity);
      case 106:
         return new ProjectileMagicExplosive(entity);
      case 107:
         return new ProjectileMagicMeteor(entity);
      case 108:
         return new ProjectileMagicPrison(entity);
      case 109:
         return new ProjectileMagicArrow(entity);
      case 110:
         return new ProjectileMagicHaze(entity);
      default:
         return new ProjectileDummy(entity);
      }
   }
}
