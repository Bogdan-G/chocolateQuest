package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.WorldGeneratorNew;
import com.chocolate.chocolateQuest.API.DungeonBase;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.items.MobData;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemEggBD extends Item implements IBehaviorDispenseItem {

   final int unequipped = 0;
   final int leather = 1;
   final int chain = 2;
   final int gold = 3;
   final int iron = 4;
   final int diamond = 5;
   int OFF_BOSS_HUMAN = 700;
   int OFF_BOSS = 800;
   int OFF_DUNGEON = 900;
   public static final int MICROPHONE = 499;
   int cooldown = 0;
   String[] name;
   String[] bossName;
   IIcon[] bossIcon;
   MobData[] mobs;
   MobData[] humanBosses;
   MobData[] bosses;


   public ItemEggBD() {
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, this);
      this.setHasSubtypes(true);
   }

   public void init() {
      this.mobs = new MobData[]{new MobData(EntityHumanDummy.class, "Dummy", 0), new MobData(ChocolateQuest.skeleton, "Skeleton", ChocolateQuest.skeleton.getFlagId(), false), new MobData(ChocolateQuest.zombie, "Zombie", ChocolateQuest.zombie.getFlagId(), false), new MobData(ChocolateQuest.mummy, "Mummy", ChocolateQuest.mummy.getFlagId(), false), new MobData(ChocolateQuest.specter, "Specter", ChocolateQuest.specter.getFlagId(), false), new MobData(ChocolateQuest.pigZombie, "PigZombie", ChocolateQuest.pigZombie.getFlagId(), false), new MobData(ChocolateQuest.gremlin, "Gremlin", ChocolateQuest.gremlin.getFlagId(), false), new MobData(ChocolateQuest.pirate, "Pirate", ChocolateQuest.pirate.getFlagId(), false), new MobData(ChocolateQuest.walker, "Abyss Walker", ChocolateQuest.walker.getFlagId(), false), new MobData(ChocolateQuest.minotaur, "Minotaur", ChocolateQuest.minotaur.getFlagId(), false)};
      this.humanBosses = new MobData[]{new MobData(ChocolateQuest.skeleton, "Necromancer", 8, true), new MobData(ChocolateQuest.zombie, "Lich", 7, true), new MobData(ChocolateQuest.mummy, "Mummy Boss", ChocolateQuest.mummy.getFlagId(), true), new MobData(ChocolateQuest.specter, "Specter Boss", 12, true), new MobData(ChocolateQuest.pigZombie, "Pig Zombie Boss", 3, true), new MobData(ChocolateQuest.gremlin, "Gremlin Boss", 11, true), new MobData(ChocolateQuest.pirate, "Pirate Boss", 9, true), new MobData(ChocolateQuest.walker, "Abyss Walker Boss", 10, true), new MobData(ChocolateQuest.minotaur, "Minotaur", ChocolateQuest.minotaur.getFlagId(), true), new MobData(EntityHumanNPC.class, "NPC", 0)};
      this.bosses = new MobData[]{new MobData(EntityTurtle.class, "Turtle", 3), new MobData(EntityGiantBoxer.class, "Monking", 2), new MobData(EntityBull.class, "Bull", 4), new MobData(EntitySlimeBoss.class, "Slime", 1), new MobData(EntitySpiderBoss.class, "Spider", 0), new MobData(EntityGiantZombie.class, "Giant Zombie", 6), new MobData(EntityWyvern.class, "Dragon", 5)};
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.bossIcon = new IIcon[7];

      for(int i = 0; i < this.bossIcon.length; ++i) {
         this.bossIcon[i] = iconRegister.registerIcon("chocolatequest:b" + i);
      }

   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item itemId, CreativeTabs table, List list) {
      int i;
      for(i = 0; i < this.mobs.length; ++i) {
         for(int l = 0; l < 6; ++l) {
            list.add(new ItemStack(itemId, 1, i + 100 * l));
         }
      }

      for(i = 0; i < this.humanBosses.length; ++i) {
         list.add(new ItemStack(itemId, 1, i + this.OFF_BOSS_HUMAN));
      }

      for(i = 0; i < this.bosses.length; ++i) {
         list.add(new ItemStack(itemId, 1, i + this.OFF_BOSS));
      }

      if(DungeonRegister.dungeonList != null) {
         for(i = 0; i < DungeonRegister.dungeonList.size(); ++i) {
            list.add(new ItemStack(itemId, 1, this.OFF_DUNGEON + i));
         }
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      MovingObjectPosition movingobjectposition = this.playerPick(entityplayer, world);
      if(movingobjectposition != null) {
         int i;
         int j;
         int k;
         if(movingobjectposition.entityHit != null) {
            i = MathHelper.floor_double(movingobjectposition.entityHit.posX);
            j = MathHelper.floor_double(movingobjectposition.entityHit.posY) + 1;
            k = MathHelper.floor_double(movingobjectposition.entityHit.posZ);
         } else {
            i = movingobjectposition.blockX;
            j = movingobjectposition.blockY;
            k = movingobjectposition.blockZ;
            switch(movingobjectposition.sideHit) {
            case 0:
               --j;
               break;
            case 1:
               ++j;
               break;
            case 2:
               --k;
               break;
            case 3:
               ++k;
               break;
            case 4:
               --i;
               break;
            case 5:
               ++i;
            }
         }

         this.onItemUse(itemstack, entityplayer, world, i, j, k, 0);
      }

      return super.onItemRightClick(itemstack, world, entityplayer);
   }

   public MovingObjectPosition playerPick(EntityPlayer player, World world) {
      byte dist = 80;
      Vec3 vec3d = Vec3.createVectorHelper(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
      Vec3 vec3d1 = Vec3.createVectorHelper(player.posX + player.getLookVec().xCoord * (double)dist, player.posY + player.getLookVec().yCoord * (double)dist, player.posZ + player.getLookVec().zCoord * (double)dist);
      MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3d, vec3d1);
      List list = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(player.getLookVec().xCoord * 40.0D, player.getLookVec().yCoord * 40.0D, player.getLookVec().zCoord * 40.0D).expand(1.0D, 1.0D, 1.0D));
      double d = 0.0D;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1.canBeCollidedWith()) {
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f2, (double)f2, (double)f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if(movingobjectposition1 != null) {
               movingobjectposition1.entityHit = entity1;
               return movingobjectposition1;
            }
         }
      }

      return movingobjectposition;
   }

   public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
      int itemDamage = itemstack.getItemDamage();
      if(itemDamage >= this.OFF_DUNGEON && !world.isRemote) {
         if(this.cooldown <= 0) {
            DungeonBase e1 = (DungeonBase)DungeonRegister.dungeonList.get(itemDamage - this.OFF_DUNGEON);
            if(e1 != null) {
               File file = new File(e1.getPath());
               e1 = e1.readData(file);
               if(e1 != null) {
                  Random random = new Random();
                  int x = i / 16;
                  int z = k / 16;
                  i -= Math.abs(i % 16);
                  k -= Math.abs(k % 16);
                  random.setSeed(WorldGeneratorNew.getSeed(world, x, z));
                  BuilderHelper.builderHelper.initialize(3);
                  e1.getBuilder().generate(random, world, i, j, k, e1.getMobID());
                  BuilderHelper.builderHelper.flush(world);
                  this.cooldown = 100;
               }
            }
         }

         return true;
      } else if(!world.isRemote) {
         Entity e = this.getEntity(world, i, j, k, itemstack);
         if(e != null) {
            e.setPosition((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D);
            world.spawnEntityInWorld(e);
            if(e.ridingEntity != null) {
               e.ridingEntity.setPosition((double)i + 0.5D, (double)(j + 1), (double)k + 0.5D);
               world.spawnEntityInWorld(e.ridingEntity);
            }

            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public Entity getEntity(World world, int i, int j, int k, ItemStack itemStack) {
      int itemDamage = itemStack.getItemDamage();
      Entity e = null;
      Class currentClass = EntityHumanBase.class;
      MobData data = this.getDataFromDamage(itemDamage);
      e = data.getEntity(world);
      if(itemDamage < this.OFF_BOSS_HUMAN) {
         if(itemStack.stackTagCompound != null) {
            e.readFromNBT(itemStack.stackTagCompound);
         } else if(e instanceof EntityHumanBase) {
            EntityHumanBase entityLiving = (EntityHumanBase)e;
            int armorType = itemDamage / 100;
            if(armorType != 0) {
               EquipementHelper.equipHumanRandomly(entityLiving, armorType - 1, EquipementHelper.getRandomType(entityLiving, 5));
            }
         }
      }

      return e;
   }

   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
      if(this.cooldown > 0) {
         --this.cooldown;
      }

      super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      int i = itemstack.getItemDamage();
      if(i >= this.OFF_DUNGEON && DungeonRegister.dungeonList.size() > i - this.OFF_DUNGEON) {
         DungeonBase itemDamage = (DungeonBase)DungeonRegister.dungeonList.get(i - this.OFF_DUNGEON);
         if(itemDamage != null) {
            return itemDamage.getName();
         }
      }

      int itemDamage1 = itemstack.getItemDamage();
      String info = "";
      if(i < this.OFF_BOSS) {
         int data = itemDamage1 / 100;
         switch(data) {
         case 1:
            info = " Leather";
            break;
         case 2:
            info = " Chain";
            break;
         case 3:
            info = " Gold";
            break;
         case 4:
            info = " Iron";
            break;
         case 5:
            info = " Diamond";
         }
      }

      MobData data1 = this.getDataFromDamage(itemDamage1);
      return data1 != null?data1.name + info:"????";
   }

   public IIcon getIconFromDamage(int itemDamage) {
      if(itemDamage >= this.OFF_DUNGEON) {
         int data = itemDamage - this.OFF_DUNGEON;
         if(data < DungeonRegister.dungeonList.size()) {
            DungeonBase dungeon = (DungeonBase)DungeonRegister.dungeonList.get(data);
            if(dungeon != null) {
               return ChocolateQuest.teleportStone.getIconFromDamage(dungeon.getIcon());
            }
         }
      }

      MobData data1 = this.getDataFromDamage(itemDamage);
      return data1 != null?(itemDamage < this.OFF_BOSS?ChocolateQuest.shield.getIconFromDamage(data1.color):this.bossIcon[data1.color]):ChocolateQuest.teleportStone.getIconFromDamage(0);
   }

   public MobData getDataFromDamage(int itemDamage) {
      if(itemDamage >= this.OFF_BOSS) {
         itemDamage -= this.OFF_BOSS;
         if(itemDamage < this.bosses.length) {
            return this.bosses[itemDamage];
         }
      } else if(itemDamage >= this.OFF_BOSS_HUMAN) {
         itemDamage -= this.OFF_BOSS_HUMAN;
         if(itemDamage < this.humanBosses.length) {
            return this.humanBosses[itemDamage];
         }
      } else {
         itemDamage %= 100;
         if(itemDamage < this.mobs.length) {
            return this.mobs[itemDamage];
         }
      }

      return null;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(stack.getItemDamage() >= this.OFF_BOSS_HUMAN) {
         if(stack.stackTagCompound != null) {
            Entity mobData1 = player.worldObj.getEntityByID(stack.stackTagCompound.getInteger("EntityID"));
            if(mobData1 instanceof EntityLiving) {
               if(entity instanceof EntityLiving) {
                  ((EntityLiving)mobData1).setAttackTarget((EntityLiving)entity);
               }
            } else {
               if(!player.worldObj.isRemote) {
                  player.addChatMessage(new ChatComponentText("Assigned entity not found, left click to assign a new entity"));
               }

               stack.stackTagCompound = null;
            }

            return true;
         }

         if(entity instanceof EntityLiving) {
            stack.stackTagCompound = new NBTTagCompound();
            stack.stackTagCompound.setInteger("EntityID", entity.getEntityId());
            stack.stackTagCompound.setString("name", entity.getCommandSenderName());
            if(!player.worldObj.isRemote) {
               player.addChatMessage(new ChatComponentText("Assigned " + BDHelper.StringColor("2") + entity.getCommandSenderName() + BDHelper.StringColor("f") + " to this item"));
               player.addChatMessage(new ChatComponentText("Left click to another entity to start a fight!"));
            }

            return true;
         }
      }

      if(entity instanceof EntityHumanBase) {
         NBTTagCompound mobData = new NBTTagCompound();
         entity.writeToNBT(mobData);
         stack.stackTagCompound = mobData;
         mobData.removeTag("Age");
         mobData.removeTag("UUIDMost");
         mobData.removeTag("UUIDLeast");
         mobData.removeTag("Dimension");
         mobData.removeTag("Pos");
         mobData.removeTag("Attributes");
         player.addChatMessage(new ChatComponentText("Saved entity data to this item"));
         return true;
      } else {
         return false;
      }
   }

   public ItemStack dispense(IBlockSource iblocksource, ItemStack itemstack) {
      EnumFacing enumfacing = BlockDispenser.func_149937_b(iblocksource.getBlockMetadata());
      double d0 = iblocksource.getX() + (double)enumfacing.getFrontOffsetX();
      double d1 = (double)((float)iblocksource.getYInt() + 0.2F);
      double d2 = iblocksource.getZ() + (double)enumfacing.getFrontOffsetZ();
      Entity e = this.getEntity(iblocksource.getWorld(), (int)d0, (int)d1, (int)d2, itemstack);
      if(e != null) {
         e.setPosition(d0, d1, d2);
         if(!iblocksource.getWorld().isRemote) {
            iblocksource.getWorld().spawnEntityInWorld(e);
         }
      }

      --itemstack.stackSize;
      return itemstack;
   }
}
