package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.ChocolateQuest;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDecoration extends Entity implements IEntityAdditionalSpawnData {

   public static final int TYPE_CUBE = 1000;
   public static final int TYPE_CUBE_STATIC = 1001;
   public float size = 1.0F;
   public int type;
   public float field_70129_M = 0.0F;


   public EntityDecoration(World world) {
      super(world);
   }

   public void onUpdate() {
      super.onUpdate();
      super.motionY -= 0.03999999910593033D;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.9D;
      super.motionZ *= 0.9D;
      if(super.worldObj.isRemote && super.ticksExisted % 100 == 0) {
         Block block = super.worldObj.getBlock(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY) - 1, MathHelper.floor_double(super.posZ));
         if(block instanceof BlockFence) {
            this.field_70129_M = 0.4F;
         }
      }

   }

   public boolean attackEntityFrom(DamageSource ds, float damage) {
      ItemStack itemstack;
      if(this.type != 1000 && this.type != 1001) {
         this.setDead();
         if(!super.worldObj.isRemote) {
            itemstack = new ItemStack(ChocolateQuest.banner, 1, this.type);
            itemstack.stackTagCompound = new NBTTagCompound();
            itemstack.stackTagCompound.setFloat("size", this.size);
            this.entityDropItem(itemstack, 0.0F);
         }
      } else {
         if(this.type == 1001) {
            super.motionY = 0.1D;
            this.type = 1000;
         } else {
            this.type = 1001;
         }

         if(super.worldObj.isRemote) {
            super.worldObj.spawnParticle("heart", super.posX, super.posY + (double)this.size, super.posZ, 0.0D, 1.0D, 0.0D);
         }

         if(ds.getEntity() instanceof EntityPlayer) {
            itemstack = ((EntityLivingBase)ds.getEntity()).getEquipmentInSlot(0);
            if(itemstack != null && itemstack.getItem() == ChocolateQuest.magicPickaxe) {
               this.setDead();
            }
         }
      }

      return true;
   }

   protected void readEntityFromNBT(NBTTagCompound tag) {
      this.size = tag.getFloat("size");
      this.type = tag.getInteger("type");
   }

   protected void writeEntityToNBT(NBTTagCompound tag) {
      tag.setFloat("size", this.size);
      tag.setInteger("type", this.type);
   }

   public void readSpawnData(ByteBuf buff) {
      this.type = buff.readInt();
      this.size = buff.readFloat();
      super.rotationYaw = (float)buff.readInt();
      this.resize();
      super.renderDistanceWeight = (double)this.size;
   }

   public void writeSpawnData(ByteBuf buff) {
      buff.writeInt(this.type);
      buff.writeFloat(this.size);
      buff.writeInt((int)super.rotationYaw);
      this.resize();
   }

   private void resize() {
      if(this.type != 1000 && this.type != 1001) {
         this.setSize(0.5F, 1.8F * this.size);
      } else {
         this.setSize(1.0F * this.size, 1.0F * this.size);
      }

   }

   protected void entityInit() {}

   public boolean interactFirst(EntityPlayer p) {
      return super.interactFirst(p);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canBePushed() {
      return this.type == 1000;
   }

   public AxisAlignedBB getBoundingBox() {
      return !this.canBePushed() && this.type != 1001?null:super.boundingBox;
   }

   public AxisAlignedBB getCollisionBox(Entity entity) {
      return null;
   }

   public void applyEntityCollision(Entity e) {
      super.applyEntityCollision(e);
   }
}
