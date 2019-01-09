package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMobSpawnerTileEntity extends TileEntity {

   public int delay = -1;
   public int mob;
   public int metadata;
   public double yaw;
   public double yaw2 = 0.0D;
   public NBTTagCompound mobNBT = null;


   public BlockMobSpawnerTileEntity() {
      this.delay = 20;
   }

   public boolean anyPlayerInRange() {
      EntityPlayer player = super.worldObj.getClosestPlayer((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, 16.0D);
      return player != null && !player.capabilities.isCreativeMode;
   }

   public void updateEntity() {
      if(this.anyPlayerInRange()) {
         if(!super.worldObj.isRemote) {
            if(this.delay == -1) {
               this.updateDelay();
            }

            if(this.delay > 0) {
               --this.delay;
               return;
            }

            this.spawnEntity();
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
         }

         super.updateEntity();
      }
   }

   public void spawnEntity() {
      int tmob = this.mob;
      Object entity = getEntity(this.metadata, tmob, super.worldObj, super.xCoord, super.yCoord, super.zCoord);
      if(entity == null) {
         entity = new EntityZombie(super.worldObj);
      }

      if(this.mobNBT != null) {
         this.editEntityStats(this.mobNBT);
         Entity e = EntityList.createEntityFromNBT(this.mobNBT, super.worldObj);
         if(e != null) {
            entity = e;
         }

         if(entity instanceof EntityHumanBase) {
            EntityHumanBase human = (EntityHumanBase)entity;
            human.readEntityFromSpawnerNBT(this.mobNBT, super.xCoord, super.yCoord, super.zCoord);
            if(this.mobNBT.getTag("Riding") != null) {
               NBTTagCompound ridingNBT = (NBTTagCompound)this.mobNBT.getTag("Riding");
               Entity riding = EntityList.createEntityFromNBT(ridingNBT, super.worldObj);
               if(riding != null) {
                  riding.setPosition((double)super.xCoord + 0.5D, (double)super.yCoord, (double)super.zCoord + 0.5D);
                  super.worldObj.spawnEntityInWorld(riding);
                  human.mountEntity(riding);
                  if(riding instanceof EntityHumanBase) {
                     ((EntityHumanBase)riding).entityTeam = human.entityTeam;
                  }
               }
            }
         }
      }

      ((Entity)entity).setLocationAndAngles((double)super.xCoord + 0.5D, (double)super.yCoord, (double)super.zCoord + 0.5D, super.worldObj.rand.nextFloat() * 360.0F, 0.0F);
      super.worldObj.spawnEntityInWorld((Entity)entity);
      super.worldObj.playAuxSFX(2004, super.xCoord, super.yCoord, super.zCoord, 0);
      if(super.worldObj.isRemote && entity instanceof EntityLiving) {
         ((EntityLiving)entity).spawnExplosionParticle();
      }

   }

   private void updateDelay() {
      this.delay = 200 + super.worldObj.rand.nextInt(600);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.mob = par1NBTTagCompound.getInteger("mob");
      this.metadata = par1NBTTagCompound.getInteger("typeMob");
      this.mobNBT = (NBTTagCompound)par1NBTTagCompound.getTag("mobData");
      if(this.mobNBT != null && this.mobNBT.hasNoTags()) {
         this.mobNBT = null;
      }

   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("mob", this.mob);
      par1NBTTagCompound.setInteger("typeMob", this.metadata);
      if(this.mobNBT != null) {
         par1NBTTagCompound.setTag("mobData", this.mobNBT);
      }

   }

   public void editEntityStats(NBTTagCompound eTag) {
      DungeonMonstersBase mobType = null;
      if(this.mob >= 0) {
         mobType = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(this.mob);
      }

      if(mobType != null && (eTag.getString("id").equals("chocolateQuest.dummy") || eTag.getString("id").equals(""))) {
         NBTTagList list = eTag.getTagList("Party", eTag.getId());

         for(int attributes = 0; attributes < list.tagCount(); ++attributes) {
            this.editEntityStats(list.getCompoundTagAt(attributes));
         }

         eTag.setString("id", mobType.getRegisteredEntityName());
         eTag.setString("team", mobType.getTeamName());
         NBTTagList var10 = eTag.getTagList("Attributes", eTag.getId());

         for(int a = 0; a < var10.tagCount(); ++a) {
            NBTTagCompound tag = var10.getCompoundTagAt(a);
            String name = tag.getString("Name");
            double base;
            if(name.equals("generic.maxHealth")) {
               base = tag.getDouble("Base");
               tag.setDouble("Base", base * mobType.getHealth());
               eTag.setShort("Health", (short)((int)(base * mobType.getHealth())));
               eTag.setFloat("HealF", (float)(base * mobType.getHealth()));
            }

            if(name.equals("generic.attackDamage")) {
               base = tag.getDouble("Base");
               tag.setDouble("generic.attackDamage", mobType.getAttack());
            }

            if(name.equals("generic.followRange")) {
               base = tag.getDouble("Base");
               tag.setDouble("generic.followRange", mobType.getRange());
            }
         }
      }

   }

   public static Entity getEntity(int metadata, int mob, World world, int x, int y, int z) {
      Object e = null;
      DungeonMonstersBase mobType = null;
      if(mob >= 0) {
         mobType = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(mob);
      }

      if(mobType != null) {
         if(metadata <= 14) {
            e = mobType.getEntity(world, x, y, z);
         } else {
            e = mobType.getBoss(world, x, y, z);
         }

         if(e instanceof EntityHumanBase) {
            EquipementHelper.equipEntity((EntityHumanBase)e, metadata % 5);
         }
      } else if(metadata < 5) {
         e = new EntityZombie(world);
      } else if(metadata <= 9) {
         e = new EntitySkeleton(world);
      } else {
         e = new EntitySpider(world);
      }

      return (Entity)e;
   }
}
