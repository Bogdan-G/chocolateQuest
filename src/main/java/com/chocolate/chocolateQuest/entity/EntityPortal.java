package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityPortal extends Entity implements IEntityAdditionalSpawnData {

   public static final int STANDING = 0;
   public static final int ON_FLOOR = 1;
   public static final int INVISIBLE = 2;
   public int type;
   public ChunkCoordinates coords;
   public String name;


   public EntityPortal(World world) {
      super(world);
   }

   public void onUpdate() {
      if(super.worldObj.isRemote && super.ticksExisted % 16 == 0) {
         super.worldObj.spawnParticle("portal", super.posX + ((double)super.rand.nextFloat() - 0.5D), super.posY + (double)(super.height / 2.0F) + (double)(super.rand.nextFloat() - 0.5F), super.posZ + ((double)super.rand.nextFloat() - 0.5D), ((double)super.rand.nextFloat() - 0.5D) / 2.0D, ((double)super.rand.nextFloat() - 0.5D) / 2.0D, ((double)super.rand.nextFloat() - 0.5D) / 2.0D);
      }

      super.onUpdate();
   }

   public boolean attackEntityFrom(DamageSource ds, float damage) {
      if(ds.getEntity() instanceof EntityLivingBase) {
         ItemStack is = ((EntityLivingBase)ds.getEntity()).getEquipmentInSlot(0);
         if(is != null && is.getItem() == ChocolateQuest.magicPickaxe) {
            this.setDead();
            return true;
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int p_70056_9_) {
      this.setPosition(x, y, z);
      this.setRotation(yaw, pitch);
   }

   protected void readEntityFromNBT(NBTTagCompound tag) {
      this.type = tag.getInteger("type");
      if(tag.hasKey("coords")) {
         NBTTagCompound tempTag = tag.getCompoundTag("coords");
         int x = tempTag.getInteger("x");
         int y = tempTag.getInteger("y");
         int z = tempTag.getInteger("z");
         this.coords = new ChunkCoordinates(x, y, z);
      }

      if(tag.hasKey("name")) {
         this.name = tag.getString("name");
      }

   }

   protected void writeEntityToNBT(NBTTagCompound tag) {
      tag.setInteger("type", this.type);
      if(this.coords != null) {
         NBTTagCompound tempTag = new NBTTagCompound();
         tempTag.setInteger("x", this.coords.posX);
         tempTag.setInteger("y", this.coords.posY);
         tempTag.setInteger("z", this.coords.posZ);
         tag.setTag("coords", tempTag);
      }

      if(this.name != null) {
         tag.setString("name", this.name);
      }

   }

   public void readSpawnData(ByteBuf buff) {
      this.type = buff.readInt();
      super.rotationYaw = (float)buff.readInt();
      boolean hasName = buff.readBoolean();
      if(hasName) {
         int lenght = buff.readInt();
         byte[] nameBytes = new byte[lenght];
         this.name = "";

         for(int i = 0; i < lenght; ++i) {
            this.name = this.name + buff.readChar();
         }
      }

      this.resize();
   }

   public void writeSpawnData(ByteBuf buff) {
      buff.writeInt(this.type);
      buff.writeInt((int)super.rotationYaw);
      buff.writeBoolean(this.name != null);
      if(this.name != null) {
         buff.writeInt(this.name.length());

         for(int i = 0; i < this.name.length(); ++i) {
            buff.writeChar(this.name.charAt(i));
         }
      }

      this.resize();
   }

   private void resize() {
      if(this.type == 1) {
         this.setSize(0.4F, 0.2F);
      } else {
         this.setSize(0.4F, 1.8F);
      }

   }

   protected void entityInit() {}

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canBePushed() {
      return true;
   }

   public void applyEntityCollision(Entity e) {
      ChunkCoordinates coords = this.getDestCoordinates();
      if(coords != null && e.timeUntilPortal == 0) {
         e.timeUntilPortal = 40;
         Vec3 v = e.getLookVec();
         if(this.type == 1 || this.getLookVec().dotProduct(v) > 0.0D) {
            if(e instanceof EntityPlayerMP) {
               ((EntityPlayerMP)e).playerNetServerHandler.setPlayerLocation((double)coords.posX + 0.5D, (double)coords.posY, (double)coords.posZ + 0.5D, ((EntityPlayerMP)e).rotationYawHead, e.rotationPitch);
            } else {
               e.setPosition((double)coords.posX + 0.5D, (double)coords.posY, (double)coords.posZ + 0.5D);
            }
         }
      }

   }

   public Vec3 getLookVec() {
      float rot = super.rotationYaw - 90.0F;
      double X = (double)MathHelper.cos(rot / 180.0F * 3.1415927F);
      double Y = 0.0D;
      double Z = (double)MathHelper.sin(rot / 180.0F * 3.1415927F);
      return Vec3.createVectorHelper(X, Y, Z);
   }

   protected boolean shouldSetPosAfterLoading() {
      return false;
   }

   public void setDestCoordinates(ChunkCoordinates coorsd) {
      this.coords = coorsd;
   }

   public ChunkCoordinates getDestCoordinates() {
      return this.name != null?TerrainManager.getInstance().getTeleportPoint(this.name):this.coords;
   }

   public void onSaveToSchematic(int x, int y, int z) {
      this.coords.posX -= x;
      this.coords.posY -= y;
      this.coords.posZ -= z;
   }

   public void onFinishSaving(int x, int y, int z) {
      this.coords.posX += x;
      this.coords.posY += y;
      this.coords.posZ += z;
   }

   public void onLoadFromSchematic(int x, int y, int z) {
      this.onFinishSaving(x, y, z);
   }
}
