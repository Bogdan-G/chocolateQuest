package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.misc.PotionCQ;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDungeonCrystal extends Entity {

   boolean hasBounds = false;
   int[] bounds = new int[6];
   public NBTTagList crystalTag;
   public String excludeTeam;


   public EntityDungeonCrystal(World world) {
      super(world);
   }

   public void onUpdate() {
      super.onUpdate();
      if(super.ticksExisted % 100 == 0) {
         if(this.hasBounds) {
            AxisAlignedBB radio = AxisAlignedBB.getBoundingBox((double)this.bounds[0], (double)this.bounds[1], (double)this.bounds[2], (double)this.bounds[3], (double)this.bounds[4], (double)this.bounds[5]);
            List list = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, radio);
            Iterator listCreepers = list.iterator();

            while(listCreepers.hasNext()) {
               EntityPlayer i$ = (EntityPlayer)listCreepers.next();
               if(this.excludeTeam == null || i$.getTeam() == null || !i$.getTeam().getRegisteredName().equals(this.excludeTeam)) {
                  i$.addPotionEffect(new PotionEffect(PotionCQ.minePrevention.id, 200, 0, true));
                  if(this.crystalTag != null) {
                     ItemStaffHeal.applyPotionEffects(this.crystalTag, i$);
                  }
               }
            }

            if(super.ticksExisted % 400 == 0) {
               List listCreepers1 = super.worldObj.getEntitiesWithinAABB(EntityCreeper.class, radio);
               Iterator i$1 = listCreepers1.iterator();

               while(i$1.hasNext()) {
                  EntityCreeper c = (EntityCreeper)i$1.next();
                  c.setDead();
               }
            }
         } else {
            byte radio1 = 20;
            this.setBounds((int)super.posX - radio1, (int)super.posY - radio1, (int)super.posZ - radio1, (int)super.posX + radio1, (int)super.posY + radio1, (int)super.posZ + radio1);
         }
      }

   }

   public void setBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      this.bounds[0] = minX;
      this.bounds[1] = minY;
      this.bounds[2] = minZ;
      this.bounds[3] = maxX;
      this.bounds[4] = maxY;
      this.bounds[5] = maxZ;
      this.hasBounds = true;
   }

   public boolean attackEntityFrom(DamageSource ds, float damage) {
      if(!ds.isExplosion()) {
         this.setDead();
      }

      return super.attackEntityFrom(ds, damage);
   }

   protected void readEntityFromNBT(NBTTagCompound tag) {
      this.hasBounds = tag.getBoolean("hasBounds");
      this.bounds = tag.getIntArray("bounds");
      if(tag.hasKey("crystalTag")) {
         NBTBase crystalTag = tag.getTag("crystalTag");
         if(crystalTag instanceof NBTTagList) {
            this.crystalTag = (NBTTagList)crystalTag;
         }
      }

   }

   protected void writeEntityToNBT(NBTTagCompound tag) {
      tag.setBoolean("hasBounds", this.hasBounds);
      tag.setIntArray("bounds", this.bounds);
      if(this.crystalTag != null) {
         tag.setTag("crystalTag", this.crystalTag);
      }

   }

   protected void entityInit() {}

   public boolean canBeCollidedWith() {
      return true;
   }
}
