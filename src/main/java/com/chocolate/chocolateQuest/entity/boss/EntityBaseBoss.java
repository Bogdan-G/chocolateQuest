package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntityBaseBoss extends EntityCreature implements IBossDisplayData, IMob, IEntityAdditionalSpawnData, IElementWeak {

   private boolean firstTick = true;
   float lvl = 1.0F;
   float size = 1.0F;
   protected boolean ridableBB = true;
   float rotSpeed = 3.0F;
   boolean limitRotation = false;
   int rage = 0;
   float xpRatio = 1.0F;
   protected int physicDefense = 0;
   protected int magicDefense = 0;
   protected int blastDefense = 0;
   protected int fireDefense = 0;
   protected int projectileDefense = 0;


   public EntityBaseBoss(World par1World) {
      super(par1World);
      this.addAITasks();
      this.setMonsterScale(this.lvl);
      this.heal(this.getMaxHealth());
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(17, Byte.valueOf((byte)0));
      super.experienceValue = 30 + (int)(this.getMonsterDificulty() * 10.0F * this.xpRatio);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
   }

   protected void scaleAttributes() {}

   protected boolean isAIEnabled() {
      return true;
   }

   public void initBody() {
      this.firstTick = false;
   }

   public void setPart(EntityPart entityPart, int partID) {
      this.firstTick = false;
   }

   public void setMonsterScale(float size) {
      if(size < 0.0F) {
         size = 1.0F;
      }

      this.lvl = size;
      this.size = size * this.getSizeVariation() + this.getMinSize();
      this.resize();
      this.scaleAttributes();
   }

   public float getMinSize() {
      return 1.0F;
   }

   public float getSizeVariation() {
      return 1.0F;
   }

   public float getMonsterDificulty() {
      return this.lvl;
   }

   public void onLivingUpdate() {
      if(this.firstTick) {
         this.initBody();
      }

      super.onLivingUpdate();
      if(this.rage > 0) {
         --this.rage;
      }

      this.updateArmSwingProgress();
      if(super.ticksExisted % 50 == 0 && this.getHealth() < this.getMaxHealth()) {
         if(this.getAttackTarget() != null) {
            if(super.ticksExisted % 500 == 0) {
               this.heal(1.0F);
            }
         } else {
            this.heal(1.0F);
         }
      }

      super.rotationYawHead = super.rotationYaw;
   }

   public void setRotationYawHead(float par1) {
      if(!this.limitRotation()) {
         super.setRotationYawHead(par1);
      }

   }

   public void moveEntityWithHeading(float par1, float par2) {
      if(this.limitRotation() && !super.worldObj.isRemote) {
         if(super.rotationYaw - super.prevRotationYaw > this.rotSpeed) {
            super.rotationYaw = super.prevRotationYaw + this.rotSpeed;
         } else if(super.rotationYaw - super.prevRotationYaw < -this.rotSpeed) {
            super.rotationYaw = super.prevRotationYaw - this.rotSpeed;
         }
      }

      super.moveEntityWithHeading(par1, par2);
   }

   protected boolean limitRotation() {
      return true;
   }

   public void moveAsBoss() {}

   public void attackEntity(Entity par1Entity, float par2) {
      super.attackEntity(par1Entity, par2);
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return this.attackEntityAsMob(par1Entity, 1.0F);
   }

   public boolean attackEntityAsMob(Entity par1Entity, float damageScale) {
      float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      EntityDamageSource ds = new EntityDamageSource("mob", this);
      if(par1Entity instanceof EntityCreeper) {
         damage = 40.0F;
      }

      return par1Entity.attackEntityFrom(ds, damage * damageScale);
   }

   public void addAITasks() {}

   protected boolean canDespawn() {
      return false;
   }

   public boolean isAttacking() {
      return this.getAnimFlag(0);
   }

   public void setAttacking(boolean attacking) {
      this.setAnimFlag(0, attacking);
   }

   protected boolean getAnimFlag(int index) {
      return (super.dataWatcher.getWatchableObjectByte(17) & 1 << index) != 0;
   }

   protected void setAnimFlag(int index, boolean result) {
      byte b = super.dataWatcher.getWatchableObjectByte(17);
      if(result) {
         super.dataWatcher.updateObject(17, Byte.valueOf((byte)(b | 1 << index)));
      } else {
         super.dataWatcher.updateObject(17, Byte.valueOf((byte)(b & ~(1 << index))));
      }

   }

   public boolean attackEntityFrom(DamageSource ds, float damage) {
      boolean ret = super.attackEntityFrom(ds, damage);
      this.rage = (int)((float)this.rage + damage);
      if(ds.getSourceOfDamage() != null) {
         if(!this.canAttackClass(ds.getSourceOfDamage().getClass())) {
            return true;
         }

         if(ret && !super.worldObj.isRemote && ds.getSourceOfDamage() instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase)ds.getSourceOfDamage());
         }
      }

      return ret;
   }

   public boolean attackFromPart(DamageSource par1DamageSource, float par2, EntityPart part) {
      return this.attackEntityFrom(par1DamageSource, par2);
   }

   public void swingItem() {
      if(!super.isSwingInProgress || super.swingProgressInt < 0) {
         super.swingProgressInt = -1;
         super.isSwingInProgress = true;
         if(!super.worldObj.isRemote) {
            PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)0);
            ChocolateQuest.channel.sendToAllAround(this, packet, 64);
         }
      }

   }

   public float getScaleSize() {
      return this.size;
   }

   public void readSpawnData(ByteBuf additionalData) {
      this.lvl = additionalData.readFloat();
      this.setMonsterScale(this.lvl);
      this.onSpawn();
   }

   public void writeSpawnData(ByteBuf buffer) {
      buffer.writeFloat(this.lvl);
      this.onSpawn();
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
      super.readEntityFromNBT(nbt);
      this.lvl = nbt.getFloat("size");
      this.setMonsterScale(this.lvl);
      this.resize();
   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
      super.writeEntityToNBT(nbt);
      nbt.setFloat("size", this.lvl);
   }

   public boolean shouldMoveToEntity(double d1, Entity target) {
      float sizeBB = super.width + target.width + this.size / 2.0F;
      return d1 > (double)sizeBB && !this.attackInProgress();
   }

   protected void resize() {
      this.setSize(this.size, this.size);
   }

   public void knockBack(Entity entity, float par2, double par3, double par5) {}

   public AxisAlignedBB getBoundingBox() {
      return !this.ridableBB?null:super.boundingBox;
   }

   public AxisAlignedBB getCollisionBox(Entity entity) {
      return !entity.isEntityEqual(this) && this.ridableBB?entity.boundingBox:null;
   }

   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {}

   public boolean canBePushed() {
      return true;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public void applyEntityCollision(Entity par1Entity) {
      par1Entity.motionX += (par1Entity.posX - super.posX) / (double)super.width;
      par1Entity.motionZ += (par1Entity.posZ - super.posZ) / (double)super.width;
   }

   public void onSpawn() {}

   public void animationBoss(byte animType) {}

   public void attackToXYZ(byte arm, double x, double y, double z) {}

   public boolean attackInProgress() {
      return false;
   }

   public boolean isRaging() {
      return this.getHealth() < this.getMaxHealth() / 5.0F;
   }

   public int getPhysicDefense() {
      return this.physicDefense;
   }

   public int getMagicDefense() {
      return this.magicDefense;
   }

   public int getBlastDefense() {
      return this.blastDefense;
   }

   public int getFireDefense() {
      return this.fireDefense;
   }

   public int getProjectileDefense() {
      return this.projectileDefense;
   }

   public boolean canAttackClass(Class par1Class) {
      return super.canAttackClass(par1Class) && par1Class != this.getClass();
   }

   protected void dropFewItems(boolean flag, int i) {
      int ammount = 2 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty()) / 2;
      this.entityDropItem(new ItemStack(ChocolateQuest.material, ammount, this.getDropMaterial()), 0.2F);
      this.entityDropItem(this.getBossMedal(), 0.2F);
   }

   public String getEntityName() {
      String s = EntityList.getEntityString(this);
      if(s == null) {
         s = "generic";
      }

      return "entity." + s + ".name";
   }

   protected int getDropMaterial() {
      return 5;
   }

   public ItemStack getBossMedal() {
      ItemStack medal = new ItemStack(ChocolateQuest.medal);
      medal.stackTagCompound = new NBTTagCompound();
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tagDisplay.setString("mob", this.getEntityName());
      tagDisplay.setFloat("size", this.getMonsterDificulty());
      medal.stackTagCompound.setTag("display", tagDisplay);
      return medal;
   }

   protected int getMedalColor() {
      return 255;
   }
}
