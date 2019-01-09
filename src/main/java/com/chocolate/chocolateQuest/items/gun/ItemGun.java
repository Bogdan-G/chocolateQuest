package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemGun extends Item implements IRangedWeapon, ILoadableGun {

   static final int NONE = -1;
   int cooldownBase;
   private final float accuracy;
   private final float range;
   final float projectileDamage;
   protected boolean canPickAmmoFromLoader;
   protected boolean canPickAmmoFromInventory;
   protected boolean usesStats;
   public static final String TAG_POWER = "power";
   public static final String TAG_ACCURACY = "accuracy";
   public static final String TAG_COOLDOWN = "cooldown";
   public int fireRounds;


   public ItemGun() {
      this(30, 100.0F, 50.0F);
   }

   public ItemGun(int cooldown, float range, float accuracy) {
      this(cooldown, range, accuracy, 1.0F);
   }

   public ItemGun(int cooldown, float range, float accuracy, float damage) {
      this.cooldownBase = 20;
      this.canPickAmmoFromLoader = false;
      this.canPickAmmoFromInventory = false;
      this.usesStats = true;
      this.fireRounds = 1;
      this.cooldownBase = cooldown;
      this.range = range;
      this.accuracy = accuracy;
      this.projectileDamage = damage;
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {}

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      if(this.usesStats) {
         ItemStack is = original.theItemId;
         is.stackTagCompound = new NBTTagCompound();
         float damage = this.projectileDamage * 1.75F;
         float damageBase = this.projectileDamage * 0.5F;
         int accuracy = (int)this.accuracy * 2;
         int cooldown = this.cooldownBase * 2;
         int type = rnd.nextInt(3);
         if(type == 0) {
            damage += this.projectileDamage * 0.75F;
         }

         if(type == 1) {
            accuracy = (int)((float)accuracy - this.accuracy / 2.0F);
         }

         if(type == 2) {
            cooldown -= this.cooldownBase / 2;
         }

         is.stackTagCompound.setFloat("power", damageBase + (float)BDHelper.getRandomValue(rnd) * damage);
         is.stackTagCompound.setInteger("cooldown", cooldown - (int)(BDHelper.getRandomValue(rnd) * (double)cooldown));
         is.stackTagCompound.setInteger("accuracy", accuracy - (int)(BDHelper.getRandomValue(rnd) * (double)accuracy));
         return original;
      } else {
         return super.getChestGenBase(chest, rnd, original);
      }
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      if(this.usesStats) {
         Awakements[] damage = Awakements.awekements;
         int rounds = damage.length;

         for(int cooldown = 0; cooldown < rounds; ++cooldown) {
            Awakements accuracy = damage[cooldown];
            if(Awakements.hasEnchant(is, accuracy)) {
               list.add(accuracy.getDescription(is));
            }
         }

         float var9 = 6.0F * this.getPower(is) + (float)this.getExtraBulletDamage(0);
         String var10 = "";
         if(this.getFireRounds() > 1) {
            var10 = "x" + this.getFireRounds();
         }

         list.add(BDHelper.formatNumberToDisplay(var9) + var10 + " " + StatCollector.translateToLocal("weaponbonus.bullet_damage.name").trim());
         float var11 = (float)this.getCooldown(is);
         list.add(BDHelper.formatNumberToDisplay(-var11) + " " + StatCollector.translateToLocal("weaponbonus.fire_rate.name").trim());
         float var12 = this.getAccuracy(is);
         list.add(BDHelper.formatNumberToDisplay(-var12) + "% " + StatCollector.translateToLocal("weaponbonus.accuracy.name").trim());
         if(player.capabilities.isCreativeMode) {
            list.add("Tags: power(float), ");
            list.add("cooldown(Integer), accuracy(Integer)");
         }
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {
      if(entityPlayer.isSneaking() && !this.freeAmmo()) {
         entityPlayer.openGui(ChocolateQuest.instance, 3, entityPlayer.worldObj, 0, 0, 0);
      } else {
         long lastShootTick = itemstack.stackTagCompound == null?0L:itemstack.stackTagCompound.getLong("ticks");
         long currentTick = world.getWorldTime();
         if(lastShootTick + (long)this.getCooldown(itemstack) >= currentTick && currentTick >= lastShootTick) {
            entityPlayer.worldObj.playSoundAtEntity(entityPlayer, "random.click", 1.0F, 1.0F);
         } else if(this.shoot(itemstack, world, entityPlayer)) {
            if(itemstack.stackTagCompound == null) {
               itemstack.stackTagCompound = new NBTTagCompound();
            }

            itemstack.stackTagCompound.setLong("ticks", currentTick);
         }

      }
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public boolean shoot(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      int bulletType = getAmmo(itemstack, !entityPlayer.capabilities.isCreativeMode);
      if(this.freeAmmo()) {
         bulletType = this.getDefaultAmmo();
      }

      if(entityPlayer.capabilities.isCreativeMode && bulletType == -1) {
         bulletType = this.getDefaultAmmo();
      }

      if(bulletType == -1 && this.canPickAmmoFromLoader) {
         bulletType = getAmmoFromAmmoLoader(itemstack, entityPlayer);
      }

      if(bulletType == -1 && this.canPickAmmoFromInventory) {
         bulletType = getAmmoFromInventory(itemstack, entityPlayer);
      }

      if(bulletType == -1) {
         return false;
      } else {
         if(!world.isRemote) {
            for(int i = 0; i < this.getFireRounds(); ++i) {
               EntityBaseBall ball = new EntityBaseBall(world, entityPlayer, 1, bulletType);
               float accuracy = this.getAccuracy(itemstack) / 10.0F;
               ball.setThrowableHeading(entityPlayer.getLookVec().xCoord, entityPlayer.getLookVec().yCoord, entityPlayer.getLookVec().zCoord, 2.0F, accuracy);
               ball.setDamageMultiplier(this.getPower(itemstack));
               world.spawnEntityInWorld(ball);
               double rPitch = Math.toRadians((double)entityPlayer.rotationPitch);
               double rYaw = Math.toRadians((double)(entityPlayer.rotationYawHead + 35.0F));
               float dist = 1.4F;
               double x = -Math.sin(rYaw) * Math.cos(rPitch) * (double)dist;
               double z = Math.cos(rYaw) * Math.cos(rPitch) * (double)dist;
               double y = -Math.sin(rPitch) * (double)dist;
               if(i == 0) {
                  x += entityPlayer.posX;
                  y += entityPlayer.posY;
                  z += entityPlayer.posZ;
                  PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)7, x, y + 1.0D, z);
                  ChocolateQuest.channel.sendToAllAround(entityPlayer, packet, 64);
               }
            }
         }

         return true;
      }
   }

   public int getDefaultAmmo() {
      return 1;
   }

   public boolean freeAmmo() {
      return false;
   }

   public static int getAmmo(ItemStack itemstack, boolean useBullet) {
      int bulletType = -1;
      ItemStack[] ammo = InventoryBag.getCargo(itemstack);

      for(int i = 0; i < ammo.length; ++i) {
         if(ammo[i] != null && ammo[i].getItem() == ChocolateQuest.bullet) {
            bulletType = ammo[i].getItemDamage();
            if(useBullet) {
               int ammoSaver = Awakements.getEnchantLevel(itemstack, Awakements.ammoSaver);
               if(ammoSaver == 0 || Item.itemRand.nextInt(2 + ammoSaver) >= 2) {
                  --ammo[i].stackSize;
                  if(ammo[i].stackSize <= 0) {
                     ammo[i] = null;
                  }

                  InventoryBag.saveCargo(itemstack, ammo);
               }
            }
            break;
         }
      }

      return bulletType;
   }

   public static int getAmmoFromAmmoLoader(ItemStack is, EntityPlayer entityPlayer) {
      int bulletType = -1;

      for(int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index) {
         ItemStack currentIs = entityPlayer.inventory.getStackInSlot(index);
         if(currentIs != null && currentIs.stackTagCompound != null && currentIs.getItem() == ChocolateQuest.ammoLoader) {
            bulletType = getAmmo(currentIs, !entityPlayer.capabilities.isCreativeMode);
            if(bulletType != -1) {
               return bulletType;
            }
         }
      }

      return bulletType;
   }

   public static int getAmmoFromInventory(ItemStack is, EntityPlayer entityPlayer) {
      int bulletType = -1;

      for(int index = 0; index < entityPlayer.inventory.getSizeInventory(); ++index) {
         ItemStack currentIs = entityPlayer.inventory.getStackInSlot(index);
         if(currentIs != null && currentIs.getItem() == ChocolateQuest.bullet) {
            bulletType = currentIs.getItemDamage();
            int ammoSaver = Awakements.getEnchantLevel(currentIs, Awakements.ammoSaver);
            if(ammoSaver == 0 || Item.itemRand.nextInt(2 + ammoSaver) >= 2) {
               --currentIs.stackSize;
               if(currentIs.stackSize <= 0) {
                  currentIs = null;
               }
            }

            if(bulletType != -1) {
               return bulletType;
            }
         }
      }

      return bulletType;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return 8;
   }

   public int getAmmoLoaderAmmount(ItemStack is) {
      int loaders = Awakements.getEnchantLevel(is, Awakements.ammoCapacity);
      return 1 + loaders;
   }

   public boolean isValidAmmo(ItemStack is) {
      return is == null?false:is.getItem() == ChocolateQuest.bullet && is.getItemDamage() != 4;
   }

   public int getStackIcon(ItemStack is) {
      return 85;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public boolean isDamageable() {
      return true;
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(itemStack.getItemDamage() > 0) {
         itemStack.setItemDamage(itemStack.getItemDamage() - 1);
      }

      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   public int getMaxDamage() {
      return this.cooldownBase;
   }

   protected int getExtraBulletDamage(int bullet) {
      return 0;
   }

   public boolean shouldRotateAroundWhenRendering() {
      return false;
   }

   public boolean isFull3D() {
      return false;
   }

   public int getCooldown(ItemStack is) {
      float cooldown = (float)this.getCooldownBase(is);
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("cooldown")) {
         cooldown = is.stackTagCompound.getFloat("cooldown");
      }

      return (int)cooldown;
   }

   public int getCooldownBase(ItemStack is) {
      return this.cooldownBase;
   }

   public float getAccuracy(ItemStack is) {
      float accuracy = this.accuracy;
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("accuracy")) {
         accuracy = is.stackTagCompound.getFloat("accuracy");
      }

      return accuracy;
   }

   public float getPower(ItemStack is) {
      float basePower = this.getBasePower();
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("power")) {
         basePower = is.stackTagCompound.getFloat("power");
      }

      int power = Awakements.getEnchantLevel(is, Awakements.power);
      return basePower * (1.0F + (float)power * 0.5F);
   }

   public float getBasePower() {
      return this.projectileDamage;
   }

   public int getFireRounds() {
      return this.fireRounds;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return this.range;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return this.getCooldown(is);
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      if(!shooter.worldObj.isRemote) {
         double armDist = (double)(shooter.width * 2.0F);
         double offsetY = (double)shooter.height;
         if(shooter instanceof EntityGolemMecha) {
            offsetY = 1.6D;
         }

         double posX = shooter.posX - Math.sin(Math.toRadians((double)(shooter.rotationYaw + (float)angle))) * armDist;
         double posY = shooter.posY + offsetY;
         double posZ = shooter.posZ + Math.cos(Math.toRadians((double)(shooter.rotationYaw + (float)angle))) * armDist;

         double x1;
         double z;
         for(int rPitch = 0; rPitch < this.getFireRounds(); ++rPitch) {
            EntityBaseBall ball;
            if(target != null) {
               ball = this.getBall(shooter.worldObj, shooter, is, target.posX - posX, target.posY + (double)target.height - posY, target.posZ - posZ);
            } else {
               double ry = Math.toRadians((double)(shooter.rotationYaw - 180.0F));
               double x = Math.sin(ry);
               x1 = -Math.cos(ry);
               z = -Math.sin(Math.toRadians((double)(shooter.rotationPitch * 2.0F - 1.0F)));
               ball = this.getBall(shooter.worldObj, shooter, is, x, z, x1);
               ball.posY -= (double)(shooter.height / 2.0F);
            }

            ball.setPosition(posX, posY, posZ);
            shooter.worldObj.spawnEntityInWorld(ball);
         }

         double var28 = Math.toRadians((double)shooter.rotationPitch);
         double rYaw = Math.toRadians((double)shooter.rotationYawHead + (double)angle * 0.5D);
         float dist = 1.4F;
         x1 = -Math.sin(rYaw) * Math.cos(var28) * (double)dist;
         z = Math.cos(rYaw) * Math.cos(var28) * (double)dist;
         double y = -Math.sin(var28) * (double)dist;
         x1 += shooter.posX;
         y += shooter.posY;
         z += shooter.posZ;
         PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)7, x1, y + 1.0D, z);
         ChocolateQuest.channel.sendToAllAround(shooter, packet, 64);
      }

   }

   public EntityBaseBall getBall(World world, EntityLivingBase shooter, ItemStack is, double x, double y, double z) {
      int ammoType = getAmmo(is, false);
      if(ammoType == -1) {
         ammoType = 0;
      }

      float accuracy = this.getAccuracy(is) / 10.0F;
      byte bulletType = 1;
      if(shooter instanceof EntityHumanBase) {
         accuracy += ((EntityHumanBase)shooter).accuracy;
         if(shooter instanceof EntityGolemMecha) {
            bulletType = 2;
         }
      }

      EntityBaseBall ball = new EntityBaseBall(shooter.worldObj, shooter, x, y, z, bulletType, ammoType, accuracy, 2.0F);
      ball.setDamageMultiplier(this.getPower(is));
      return ball;
   }

   public boolean canBeUsedByEntity(Entity entity) {
      return true;
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return false;
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return false;
   }

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      return this.cooldownBase + 15;
   }
}
