package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import com.chocolate.chocolateQuest.utils.Vec4I;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemController extends Item {

   public final int CONTROLLER = 0;
   public final int GOLEM_CONTROLLER = 1;
   public static final int MOVE = 0;
   public static final int STANDING_POS = 1;
   public static final int TEAM_EDITOR = 2;
   public static final int CLEAR = 3;
   ArrayList cursors = new ArrayList();
   ItemStack currentItem;


   public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityPlayer, EntityLivingBase entity) {
      EntityLivingBase[] entities = this.getEntity(itemstack, entityPlayer.worldObj);
      EntityLivingBase[] arr$ = entities;
      int len$ = entities.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         EntityLivingBase e = arr$[i$];
         if(e != null) {
            MovingObjectPosition mop = new MovingObjectPosition(entity);
            this.doStuff(itemstack, entityPlayer.worldObj, entityPlayer, mop, e);
            return true;
         }
      }

      return false;
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         entityPlayer.openGui(ChocolateQuest.instance, 8, entityPlayer.worldObj, 0, 0, 0);
         return itemstack;
      } else {
         MovingObjectPosition mop = HelperPlayer.getMovingObjectPositionFromPlayer(entityPlayer, world, 80.0D);
         EntityLivingBase[] entities = this.getEntity(itemstack, world);
         if(entities != null) {
            EntityLivingBase[] arr$ = entities;
            int len$ = entities.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               EntityLivingBase e = arr$[i$];
               if(e != null) {
                  this.doStuff(itemstack, world, entityPlayer, mop, e);
               }
            }
         }

         return super.onItemRightClick(itemstack, world, entityPlayer);
      }
   }

   public void doStuff(ItemStack itemstack, World world, EntityPlayer entityPlayer, MovingObjectPosition mop, Entity assignedEntity) {
      if(mop != null) {
         byte mode = this.getMode(itemstack);
         if(mop.entityHit == null) {
            if(assignedEntity instanceof EntityHumanBase) {
               EntityHumanBase entityHit = (EntityHumanBase)assignedEntity;
               if(world.isRemote) {
                  world.spawnEntityInWorld(new EntityCursor(world, (double)mop.blockX + 0.5D, (double)(mop.blockY + 1), (double)mop.blockZ + 0.5D, entityPlayer.rotationYaw));
               }

               if(mode == 0) {
                  entityHit.currentPos = new Vec4I(mop.blockX, mop.blockY, mop.blockZ, (int)entityPlayer.rotationYaw);
               } else if(mode == 1) {
                  entityHit.AIPosition = new Vec4I(mop.blockX, mop.blockY, mop.blockZ, (int)entityPlayer.rotationYaw);
               }
            }
         } else if(mop.entityHit instanceof EntityLiving && mop.entityHit != assignedEntity) {
            EntityLivingBase entityHit1 = (EntityLivingBase)mop.entityHit;
            if(assignedEntity instanceof EntityHumanBase) {
               EntityHumanBase human = (EntityHumanBase)assignedEntity;
               if(world.isRemote) {
                  world.spawnEntityInWorld(new EntityCursor(world, mop.entityHit, itemstack));
               }

               boolean isMount = human.isSuitableMount(entityHit1);
               if(isMount && entityHit1.riddenByEntity != null && entityHit1 instanceof EntityLivingBase) {
                  entityHit1 = (EntityLivingBase)entityHit1.riddenByEntity;
                  isMount = false;
               }

               if(isMount) {
                  if(human.ridingEntity == null && mode == 0 || mode == 2) {
                     if(human.isSuitableMount(entityHit1)) {
                        entityPlayer.addChatMessage(new ChatComponentText(BDHelper.StringColor("2") + human.getCommandSenderName() + BDHelper.StringColor("f") + " mounting " + BDHelper.StringColor("2") + entityHit1.getCommandSenderName() + BDHelper.StringColor("f")));
                     }

                     human.setAttackTarget(entityHit1);
                  }
               } else if(!human.isSuitableTargetAlly(entityHit1)) {
                  if(mode == 0) {
                     human.setAttackTarget(entityHit1);
                  }
               } else {
                  if(mode == 2) {
                     boolean added = true;
                     if(entityHit1 instanceof EntityHumanBase) {
                        if(!((EntityHumanBase)entityHit1).tryPutIntoPArty(human)) {
                           added = false;
                        }
                     } else {
                        human.setOwner(entityHit1);
                     }

                     if(added) {
                        entityPlayer.addChatMessage(new ChatComponentText(BDHelper.StringColor("2") + human.getCommandSenderName() + BDHelper.StringColor("f") + " now following " + BDHelper.StringColor("2") + entityHit1.getCommandSenderName() + BDHelper.StringColor("f") + " orders"));
                     }
                  }

                  if(mode == 0) {
                     human.currentPos = new Vec4I(MathHelper.floor_double(entityHit1.posX), MathHelper.floor_double(entityHit1.posY), MathHelper.floor_double(entityHit1.posZ), (int)entityPlayer.rotationYaw);
                  }
               }
            } else if(assignedEntity instanceof EntityLiving && mode == 0) {
               ((EntityLiving)assignedEntity).setAttackTarget(entityHit1);
               if(world.isRemote) {
                  world.spawnEntityInWorld(new EntityCursor(world, mop.entityHit, itemstack));
               }
            }
         }
      }

   }

   public boolean tryMount(Entity mount, EntityHumanBase rider) {
      return false;
   }

   public byte getMode(ItemStack itemstack) {
      return itemstack.stackTagCompound == null?0:itemstack.stackTagCompound.getByte("Mode");
   }

   public void setMode(ItemStack itemstack, byte mode) {
      if(itemstack.stackTagCompound == null) {
         itemstack.stackTagCompound = new NBTTagCompound();
      }

      itemstack.stackTagCompound.setByte("Mode", mode);
   }

   public void addEntity(ItemStack itemstack, EntityLivingBase entity) {
      if(itemstack.stackTagCompound == null) {
         itemstack.stackTagCompound = new NBTTagCompound();
      }

      NBTTagList list = (NBTTagList)itemstack.stackTagCompound.getTag("Entities");
      if(list == null) {
         list = new NBTTagList();
      }

      NBTTagCompound tag = new NBTTagCompound();
      tag.setInteger("EntityID", entity.getEntityId());
      list.appendTag(tag);
      itemstack.stackTagCompound.setTag("Entities", list);
   }

   public EntityLivingBase[] getEntity(ItemStack itemstack, World world) {
      if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("Entities")) {
         NBTTagList list = (NBTTagList)itemstack.stackTagCompound.getTag("Entities");
         if(list != null) {
            EntityLivingBase[] entities = new EntityLivingBase[list.tagCount()];

            int i;
            for(i = 0; i < list.tagCount(); ++i) {
               NBTTagCompound tag = list.getCompoundTagAt(i);
               int id = tag.getInteger("EntityID");
               if(id != 0) {
                  Entity e = world.getEntityByID(id);
                  if(e instanceof EntityLivingBase) {
                     entities[i] = (EntityLivingBase)e;
                  }
               }
            }

            for(i = entities.length - 1; i > 0; --i) {
               if(entities[i] == null || entities[i].isDead) {
                  list.removeTag(i);
               }
            }

            return entities;
         }
      }

      return null;
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.stackTagCompound != null;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(this.isSuitableTarget(entity, stack) && entity instanceof EntityLivingBase && (entity instanceof EntityHumanBase && ((EntityHumanBase)entity).isOnSameTeam(player) || player.capabilities.isCreativeMode)) {
         this.addEntity(stack, (EntityLivingBase)entity);
         stack.setItemDamage(Item.itemRand.nextInt());
         String name = entity.getCommandSenderName();
         if(player.worldObj.isRemote) {
            player.addChatMessage(new ChatComponentText("Assigned " + BDHelper.StringColor("2") + name + BDHelper.StringColor("f") + " to this item"));
         }

         this.currentItem = null;
         return true;
      } else {
         return true;
      }
   }

   public boolean isSuitableTarget(Entity e, ItemStack is) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity par3Entity, int par4, boolean par5) {
      if(world.isRemote && par3Entity instanceof EntityPlayer) {
         boolean itemChanged = false;
         EntityPlayer player = (EntityPlayer)par3Entity;
         ItemStack playerCurrentItem = player.inventory.getCurrentItem();
         if(player.inventory.getCurrentItem() != null) {
            if(playerCurrentItem.getItem() == this) {
               if(this.currentItem == null || !playerCurrentItem.isItemEqual(this.currentItem)) {
                  this.currentItem = playerCurrentItem;
                  itemChanged = true;
               }
            } else {
               itemChanged = true;
               this.currentItem = null;
            }
         } else {
            itemChanged = true;
            this.currentItem = null;
         }

         if(itemChanged && playerCurrentItem != null) {
            this.spawnCursors(world, playerCurrentItem);
         }
      }

   }

   public void spawnCursors(World world, ItemStack itemstack) {
      Iterator entities = this.cursors.iterator();

      while(entities.hasNext()) {
         EntityCursor arr$ = (EntityCursor)entities.next();
         arr$.setDead();
      }

      this.cursors.clear();
      EntityLivingBase[] var9 = this.getEntity(itemstack, world);
      if(var9 != null) {
         EntityLivingBase[] var10 = var9;
         int len$ = var9.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            EntityLivingBase e = var10[i$];
            if(e != null) {
               EntityCursor c = new EntityCursor(world, e, itemstack, 1);
               world.spawnEntityInWorld(c);
               this.cursors.add(c);
            }
         }
      }

   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      byte mode = this.getMode(itemstack);
      String surName;
      switch(mode) {
      case 0:
         surName = StatCollector.translateToLocal("item.move.name");
         break;
      case 1:
         surName = StatCollector.translateToLocal("item.ward.name");
         break;
      case 2:
         surName = StatCollector.translateToLocal("item.team.name");
         break;
      default:
         surName = "????";
      }

      return super.getItemStackDisplayName(itemstack) + (" " + surName).trim();
   }
}
