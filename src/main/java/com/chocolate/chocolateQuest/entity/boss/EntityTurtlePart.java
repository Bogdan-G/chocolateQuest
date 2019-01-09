package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTurtlePart extends EntityPart {

   int maxHealth;
   private final int LIFEWATCHER;


   public EntityTurtlePart(World par1World) {
      super(par1World);
      this.maxHealth = 100;
      this.LIFEWATCHER = 10;
      super.isImmuneToFire = true;
      this.setHeartsLife(100);
      super.noClip = true;
   }

   public EntityTurtlePart(World world, int partID, float rotationYawOffset, float distanceToMainBody) {
      this(world);
      super.rotationYawOffset = rotationYawOffset;
      super.distanceToMainBody = distanceToMainBody;
      super.partID = partID;
   }

   public EntityTurtlePart(World world, int partID, float rotationYawOffset, float distanceToMainBody, float heightOffset) {
      this(world, partID, rotationYawOffset, distanceToMainBody);
      super.heightOffset = heightOffset;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(par1DamageSource.damageType.equals(DamageSource.inWall.damageType)) {
         return false;
      } else if(par1DamageSource.getEntity() == super.mainBody) {
         return false;
      } else {
         boolean b = super.attackEntityFrom(par1DamageSource, par2);
         if(b) {
            this.setHeartsLife((int)((float)this.getHeartsLife() - par2));
            if(this.getHeartsLife() <= 0 && super.worldObj.isRemote) {
               this.setDead();
            }
         }

         return b;
      }
   }

   public void onUpdate() {
      if(this.getHeartsLife() < 0) {
         this.setDead();
      }

      super.onUpdate();
   }

   public void setHead() {
      super.partID = 0;
      this.setHeartsLife(300);
   }

   public boolean isHead() {
      return super.partID == 0;
   }

   public int getHeartsLife() {
      return super.dataWatcher.getWatchableObjectShort(10);
   }

   public void setHeartsLife(int heartsLife) {
      super.dataWatcher.updateObject(10, Short.valueOf((short)heartsLife));
   }

   protected void entityInit() {
      super.dataWatcher.addObject(10, Short.valueOf((short)100));
   }

   public void readSpawnData(ByteBuf additionalData) {
      super.readSpawnData(additionalData);
   }

   public void writeSpawnData(ByteBuf buffer) {
      super.writeSpawnData(buffer);
   }

   public AxisAlignedBB getBoundingBox() {
      return null;
   }

   public AxisAlignedBB getCollisionBox(Entity entity) {
      return entity.isEntityEqual(this)?null:entity.boundingBox;
   }
}
