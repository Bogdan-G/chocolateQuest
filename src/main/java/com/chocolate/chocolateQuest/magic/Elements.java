package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceBlast;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceDark;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceFire;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceLight;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceMagic;
import com.chocolate.chocolateQuest.magic.ElementDamageSourceNature;
import com.chocolate.chocolateQuest.magic.IElementHolder;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public enum Elements {

   physic("physic", 0, "physic", new ElementDamageSourceNature(), 1.0D, "2", "physic", 8956552, "random.bow"),
   magic("magic", 1, "magic", new ElementDamageSourceMagic(), 0.75D, "3", "magic", 3394815, "mob.wither.shoot"),
   blast("blast", 2, "blast", new ElementDamageSourceBlast(), 0.5D, "5", "blast", 10066380, "random.fizz"),
   fire("fire", 3, "fire", new ElementDamageSourceFire(), 1.0D, "6", "fire", 16750848, "mob.ghast.fireball"),
   light("light", 4, "light", new ElementDamageSourceLight(), 1.0D, "e", "light", 16777113, "mob.zombie.unfect"),
   darkness("darkness", 5, "darkness", new ElementDamageSourceDark(), 1.0D, "8", "dark", 4456516, "mob.zombie.infect"),
   water("water", 6, "water", new ElementDamageSourceNature(), 1.0D, "8", "????", 11184895, "");
   String name;
   public ElementDamageSource damageSource;
   public double ammountMultiplier;
   public String stringColor;
   public String particle;
   int color;
   public String sound;
   // $FF: synthetic field
   private static final Elements[] $VALUES = new Elements[]{physic, magic, blast, fire, light, darkness, water};


   private Elements(String var1, int var2, String s, ElementDamageSource ds, double dmgMultiplier, String stringColor, String particle, int color, String sound) {
      this.name = s;
      this.damageSource = ds;
      this.ammountMultiplier = dmgMultiplier;
      this.stringColor = stringColor;
      this.particle = particle;
      this.color = color;
      this.sound = sound;
   }

   public boolean attackWithElement(EntityLivingBase target, EntityLivingBase shooter, float damage) {
      damage = this.onHitEntity(shooter, target, damage);
      DamageSource ds = this.getDamageSource(shooter);
      return target.attackEntityFrom(ds, damage);
   }

   public boolean attackWithElementProjectile(EntityLivingBase target, EntityLivingBase shooter, Entity projectile, float damage) {
      return this.attackWithElementProjectile(target, shooter, projectile, damage, true);
   }

   public boolean attackWithElementProjectile(EntityLivingBase target, EntityLivingBase shooter, Entity projectile, float damage, boolean knockBack) {
      damage = this.onHitEntity(shooter, target, damage);
      DamageSource ds = this.getDamageSourceIndirect(shooter, projectile);
      return knockBack?target.attackEntityFrom(ds, damage):HelperDamageSource.attackEntityWithoutKnockBack(target, ds, damage);
   }

   public DamageSource getDamageSource(Entity shooter) {
      return this.damageSource.getDamageSource(shooter, this.name);
   }

   public DamageSource getDamageSourceIndirect(Entity shooter, Entity projectile) {
      return this.damageSource.getIndirectDamage(projectile, shooter, this.name);
   }

   public DamageSource getDamageSource() {
      return this.damageSource.getDamageSource(this.name);
   }

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      return this.damageSource.onHitEntity(source, entityHit, damage);
   }

   public String getTranslatedName() {
      return StatCollector.translateToLocal("element." + this.name + ".name");
   }

   public String getName() {
      return this.name;
   }

   public String getStringColor() {
      return this.stringColor;
   }

   public String getSound() {
      return this.sound;
   }

   public String getParticle() {
      return this.particle;
   }

   public float getColorX() {
      return (float)(this.color >> 16 & 255) / 256.0F;
   }

   public float getColorY() {
      return (float)(this.color >> 8 & 255) / 256.0F;
   }

   public float getColorZ() {
      return (float)(this.color >> 0 & 255) / 256.0F;
   }

   public static int getElementValue(ItemStack is, Elements element) {
      if(!hasElements(is)) {
         return 0;
      } else {
         float damage = 0.0F;
         NBTBase tag = is.stackTagCompound.getTag("elements");
         return tag instanceof NBTTagCompound?((NBTTagCompound)tag).getByte(element.getName()):0;
      }
   }

   public static float getElementDamage(ItemStack is, Elements element) {
      float damage = (float)getElementValue(is, element);
      if(is.getItem() instanceof IElementHolder) {
         damage *= ((IElementHolder)is.getItem()).getElementModifier(is, element);
      }

      return damage;
   }

   public static void setElementValue(ItemStack is, Elements element, int value) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      if(is.getItem() instanceof ItemArmor) {
         value = Math.min(4, value);
      }

      NBTTagCompound tag = new NBTTagCompound();
      tag.setByte(element.getName(), (byte)value);
      is.stackTagCompound.setTag("elements", tag);
   }

   public static boolean hasElements(ItemStack is) {
      return is.stackTagCompound == null?false:is.stackTagCompound.hasKey("elements");
   }

}
