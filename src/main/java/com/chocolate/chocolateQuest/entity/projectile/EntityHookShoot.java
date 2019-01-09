package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.IHookLauncher;
import com.chocolate.chocolateQuest.items.ItemArmorHeavy;
import com.chocolate.chocolateQuest.packets.PacketHookImpact;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityHookShoot extends EntityThrowable implements IThrowableEntity {

   public ItemStack item;
   int lifeTime;
   EntityLivingBase shootingEntity;
   public boolean returning;
   public boolean reeling;
   public static final byte hookManual = 2;
   public static final byte hookSpider = 3;
   public static final byte hookWeapon = 4;
   public static final byte hookSpiderBoss = 5;
   public Entity hookedEntity;
   public double hookedAtHeight;
   public double hookedAtDistance;
   public double hookedAtAngle;
   public int blockX;
   public int blockY;
   public int blockZ;
   int reelSpeed;


   public EntityHookShoot(World par1World) {
      super(par1World);
      this.item = null;
      this.lifeTime = 0;
      this.returning = false;
      this.reeling = false;
      this.reelSpeed = 0;
   }

   public EntityHookShoot(World world, EntityLivingBase entityliving, int type) {
      super(world, entityliving);
      this.item = null;
      this.lifeTime = 0;
      this.returning = false;
      this.reeling = false;
      this.reelSpeed = 0;
      this.shootingEntity = entityliving;
      this.setHookType(type);
      float s = 0.6F;
      this.setSize(s, s);
      super.worldObj.playSoundEffect((double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), "random.bow", 4.0F, (1.0F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
   }

   public EntityHookShoot(World world, EntityLivingBase entityliving, int type, ItemStack item) {
      this(world, entityliving, type);
      this.item = item;
   }

   protected void entityInit() {
      super.dataWatcher.addObject(10, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(11, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(12, Float.valueOf(0.0F));
   }

   public void setReeling(boolean b) {
      super.dataWatcher.updateObject(10, Byte.valueOf((byte)(b?1:0)));
   }

   public boolean isReeling() {
      return super.dataWatcher.getWatchableObjectByte(10) == 1;
   }

   public void setHookType(int par) {
      super.dataWatcher.updateObject(11, Byte.valueOf((byte)par));
   }

   public byte getHookType() {
      return super.dataWatcher.getWatchableObjectByte(11);
   }

   public void setRadio(float par) {
      super.dataWatcher.updateObject(12, Float.valueOf(par));
   }

   public float getRadio() {
      return super.dataWatcher.getWatchableObjectFloat(12);
   }

   public void reel(int i) {
      if(this.reelSpeed == i) {
         this.reelSpeed = 0;
      } else {
         this.reelSpeed = i;
      }

   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double par1) {
      double d1 = super.boundingBox.getAverageEdgeLength() * 4.0D;
      d1 *= 64.0D;
      return par1 < d1 * d1;
   }

   protected void onImpact(MovingObjectPosition mop) {
      if(!this.isReeling()) {
         PacketHookImpact packet;
         if(mop.typeOfHit == MovingObjectType.BLOCK && !super.worldObj.isRemote) {
            this.blockX = mop.blockX;
            this.blockY = mop.blockY;
            this.blockZ = mop.blockZ;
            packet = new PacketHookImpact(this.getEntityId(), mop.blockX, mop.blockY, mop.blockZ, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
            this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            super.onGround = true;
         }

         if(mop.entityHit instanceof Entity && !super.worldObj.isRemote) {
            if(mop.entityHit instanceof EntityThrowable) {
               return;
            }

            if(!mop.entityHit.isEntityEqual(this.getThrower())) {
               this.hookedEntity = mop.entityHit;
               this.hookedAtHeight = Math.min((double)this.hookedEntity.height, super.posY - this.hookedEntity.posY);
               this.hookedAtAngle = Math.atan2(this.hookedEntity.posZ - super.posZ, this.hookedEntity.posX - super.posX) * 180.0D / 3.141592653589793D + 90.0D;
               this.hookedAtAngle -= (double)this.hookedEntity.rotationYaw;
               this.hookedAtDistance = Math.min((double)this.hookedEntity.width, Math.sqrt(Math.abs(this.hookedEntity.posX - super.posX + this.hookedEntity.posZ - super.posZ)));
               packet = new PacketHookImpact(this.getEntityId(), this.hookedEntity.getEntityId(), this.hookedAtAngle, this.hookedAtDistance, this.hookedAtHeight);
               ChocolateQuest.channel.sendToAllAround((Entity)this, (IMessage)packet);
               this.returning = true;
               super.onGround = true;
            }
         }

         if(!super.worldObj.isRemote && super.onGround) {
            this.setReeling(true);
            this.onImpact();
         }
      }

   }

   public void onImpact() {}

   public void onUpdate() {
      if((this.getThrower() == null || !this.getThrower().isDead) && super.worldObj.blockExists((int)super.posX, (int)super.posY, (int)super.posZ)) {
         super.onUpdate();
         if(this.hookedEntity != null) {
            float shooting = (float)((this.hookedAtAngle + (double)this.hookedEntity.rotationYaw) * 3.1415998935699463D / 180.0D);
            super.posX = this.hookedEntity.posX - (double)MathHelper.sin(shooting) * this.hookedAtDistance;
            super.posY = this.hookedEntity.posY + this.hookedAtHeight;
            super.posZ = this.hookedEntity.posZ + (double)MathHelper.cos(shooting) * this.hookedAtDistance;
            if(!this.hookedEntity.isEntityAlive()) {
               this.hookedEntity = null;
               this.setReeling(false);
            }
         }

         if(this.isReeling()) {
            super.motionX = 0.0D;
            super.motionY = 0.0D;
            super.motionZ = 0.0D;
         }

         ++this.lifeTime;
         if(this.getThrower() != null) {
            EntityLivingBase var11 = this.getThrower();
            double dist = (double)this.getDistanceToEntity(this.getThrower());
            float radio = this.getRadio();
            if(dist >= (double)radio) {
               this.getThrower().fallDistance = 0.0F;
               if(this.hookedEntity != null) {
                  this.hookedEntity.fallDistance = 0.0F;
               }
            }

            boolean fc;
            if(!super.worldObj.isRemote && var11 instanceof EntityPlayer) {
               fc = false;
               EntityPlayer s = (EntityPlayer)var11;
               ItemStack pullSpeed = this.getHookShoot(s);
               if(pullSpeed != null) {
                  for(int flag = 0; flag < 10; ++flag) {
                     if(s.inventory.getStackInSlot(flag) == pullSpeed) {
                        fc = true;
                     }
                  }
               }

               if(!fc) {
                  this.setDead();
               }
            }

            if(this.isReeling()) {
               if(!(var11 instanceof EntityPlayer)) {
                  this.setRadio(radio - 1.2F);
               } else if(this.reelSpeed != 0) {
                  float var12 = (float)this.reelSpeed;
                  if(this.isSpiderHook()) {
                     var12 = Math.min(0.33F, var12);
                  }

                  this.setRadio(Math.max(0.0F, Math.min((float)this.getMaxRadius(), this.getRadio() + (this.isSpiderHook()?1.5F:0.5F) * var12)));
               }

               if(!super.worldObj.isRemote) {
                  label147: {
                     if(this.hookedEntity == null) {
                        if(!super.worldObj.isAirBlock(this.blockX, this.blockY, this.blockZ)) {
                           break label147;
                        }
                     } else if(!this.hookedEntity.isDead) {
                        break label147;
                     }

                     this.setReeling(false);
                  }
               }

               fc = false;
               Entity var14 = this.hookedEntity;
               if(var14 != null) {
                  if(var14 instanceof EntityPlayer && var14.isSneaking()) {
                     this.setDead();
                  }

                  float var15 = var14.width * 2.0F + var14.height;
                  float var18 = var11.width * 2.0F + var11.height + 0.2F;

                  for(int fc1 = 1; fc1 <= 4; ++fc1) {
                     ItemStack boots = var11.getEquipmentInSlot(fc1);
                     if(boots != null && boots.getItem() != null && boots.getItem() instanceof ItemArmorHeavy) {
                        var18 += 5.0F;
                     }
                  }

                  if(var18 > var15 && this.hookedEntity.canBePushed()) {
                     fc = true;
                  }
               }

               double var17;
               if(this.manualPull()) {
                  var17 = Math.sqrt(var11.motionX * var11.motionX + var11.motionZ * var11.motionZ + var11.motionY * var11.motionY);
                  var17 *= Math.max(1.0D, this.getDistanceSqToEntity(var11) / (double)(radio * radio));
                  var17 /= 10.0D;
                  if(var17 > (double)var11.jumpMovementFactor && !fc) {
                     var11.jumpMovementFactor = (float)Math.min(var17, 0.14D);
                  }
               } else if(radio > 0.0F) {
                  this.setRadio(Math.max(0.0F, radio - 0.8F));
               }

               var17 = 0.0D;
               var17 = dist - (double)radio;
               if(var17 < 0.0D) {
                  var17 = 0.0D;
               }

               if(var17 > 2.0D) {
                  var17 = 2.0D;
               }

               var17 *= 0.03D;
               Vec3 var20 = Vec3.createVectorHelper(var11.posX - super.posX, var11.posY - super.posY, var11.posZ - super.posZ);
               var20.normalize();
               var20.xCoord *= var17;
               var20.zCoord *= var17;
               var20.yCoord *= var17;
               if(fc) {
                  if(dist > (double)radio || radio < 1.0F) {
                     var14.addVelocity(var20.xCoord, var20.yCoord, var20.zCoord);
                     super.motionX = var20.xCoord;
                     super.motionY = var20.yCoord;
                     super.motionZ = var20.zCoord;
                  }
               } else {
                  var11.addVelocity(-var20.xCoord, -var20.yCoord, -var20.zCoord);
               }
            } else {
               if(dist > (double)this.getMaxRadius()) {
                  this.returning = true;
               }

               if(this.returning) {
                  this.setRadio((float)dist);
                  Vec3 var16 = Vec3.createVectorHelper(var11.posX - super.posX, var11.posY + 1.4D - super.posY, var11.posZ - super.posZ);
                  var16.normalize();
                  double var13 = 0.2D;
                  if(this.getHookType() == 5) {
                     var13 = 0.05D;
                  }

                  boolean var19 = true;
                  super.motionX = var16.xCoord * var13;
                  super.motionY = var16.yCoord * var13;
                  super.motionZ = var16.zCoord * var13;
               } else {
                  this.setRadio((float)(dist < (double)this.getMaxRadius()?dist:(double)this.getMaxRadius()));
               }
            }

            if(this.returning && !this.isReeling() && dist <= (double)(var11.width + var11.height)) {
               this.setDead();
            }
         }
      } else {
         this.setDead();
      }

   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.setDead();
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public ItemStack getHookShoot(EntityPlayer ep) {
      if(ep != null) {
         for(int i = 0; i < ep.inventory.getSizeInventory(); ++i) {
            ItemStack is = ep.inventory.getStackInSlot(i);
            if(is != null) {
               int id = this.getEntityId();
               if(is.getItem() instanceof IHookLauncher && ((IHookLauncher)is.getItem()).getHookID(is) == id) {
                  return is;
               }
            }
         }
      }

      return null;
   }

   public void setDead() {
      if(this.getThrower() instanceof EntityPlayer) {
         ItemStack is = this.getHookShoot((EntityPlayer)this.getThrower());
         if(is != null) {
            ((IHookLauncher)is.getItem()).setHookID(is, 0);
         }
      }

      super.setDead();
   }

   public int getRopeColor() {
      byte type = this.getHookType();
      return type != 3 && type != 5?4473924:16777215;
   }

   public int getMaxRadius() {
      return this.getHookType() == 3?40:(this.getHookType() == 2?30:(this.getHookType() == 1?25:(this.getHookType() == 0?15:40)));
   }

   public EntityLivingBase getThrower() {
      return this.shootingEntity;
   }

   public void setThrower(Entity entity) {
      if(entity instanceof EntityLivingBase) {
         this.shootingEntity = (EntityLivingBase)entity;
      }

   }

   protected boolean isSpiderHook() {
      byte hookType = this.getHookType();
      return hookType == 3 || hookType == 4;
   }

   private boolean manualPull() {
      byte hookType = this.getHookType();
      return hookType == 2 || hookType == 3 || hookType == 4;
   }
}
