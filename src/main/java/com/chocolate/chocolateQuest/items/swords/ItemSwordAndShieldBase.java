package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemSwordAndShieldBase extends ItemCQBlade {

   String texture;
   protected final AttributeModifier knockBackShield;
   int shiedID;


   public ItemSwordAndShieldBase() {
      this(ToolMaterial.IRON, "ChocolateQuest:swordDefensive");
   }

   public ItemSwordAndShieldBase(ToolMaterial mat, String texture) {
      super(mat);
      this.texture = "ChocolateQuest:swordDefensive";
      this.knockBackShield = (new AttributeModifier(Item.field_111210_e, "Weapon modifier", 1.0D, 0)).setSaved(false);
      this.shiedID = 0;
      this.texture = texture;
   }

   public ItemSwordAndShieldBase(ToolMaterial mat, String texture, int baseDamage, float elementModifier) {
      super(mat, (float)baseDamage);
      this.texture = "ChocolateQuest:swordDefensive";
      this.knockBackShield = (new AttributeModifier(Item.field_111210_e, "Weapon modifier", 1.0D, 0)).setSaved(false);
      this.shiedID = 0;
      this.texture = texture;
      super.elementModifier = elementModifier;
   }

   public ItemSwordAndShieldBase setShieldId(int id) {
      this.shiedID = id;
      return this;
   }

   public int getShieldID(ItemStack is) {
      return this.shiedID;
   }

   public void onBlock(EntityLivingBase blockingEntity, EntityLivingBase attackerEntity, DamageSource ds) {}

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
   }

   public int getMaxDamage() {
      return 2048;
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         if(ep.getCurrentEquippedItem() == itemStack) {
            ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(this.knockBackShield);
            if(ep.isUsingItem()) {
               ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(this.knockBackShield);
            }
         } else if(ep.getCurrentEquippedItem() == null || !(ep.getCurrentEquippedItem().getItem() instanceof ItemSwordAndShieldBase)) {
            ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(this.knockBackShield);
         }
      }

      super.onUpdate(itemStack, world, entity, par4, par5);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public static void projectileDeflection(Entity source, double dist) {
      AxisAlignedBB aabb = source.boundingBox.expand(dist - 1.0D, 3.0D, dist - 1.0D);
      List list = source.worldObj.getEntitiesWithinAABB(Entity.class, aabb);
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e != source) {
            Vec3 d = Vec3.createVectorHelper(source.posX - e.posX, source.posY - e.posY - 1.0D, source.posZ - e.posZ);
            double distToEntity = Math.max(40.0D, (d.xCoord * d.xCoord + d.zCoord * d.zCoord) * 10.0D);
            d.normalize();
            double var10000 = d.xCoord / distToEntity;
            double y = d.yCoord / Math.max(15.0D, d.yCoord * d.yCoord * 10.0D) / distToEntity;
            var10000 = d.zCoord / distToEntity;
            double speedModifier = 1.0D;
            double dist3D = source.getDistanceSqToEntity(e) - 2.0D;
            if(dist3D < 10.0D && !(e instanceof EntityItem) && !(e instanceof EntityLivingBase) && e != source.ridingEntity && e != source.riddenByEntity) {
               if(e instanceof EntityFireball) {
                  e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
               } else if(e instanceof IThrowableEntity) {
                  if(((IThrowableEntity)e).getThrower() != source) {
                     e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
                  }
               } else if(e instanceof EntityArrow) {
                  dist3D = 4.0D;
                  e.addVelocity(-d.xCoord / dist3D, -d.yCoord / dist3D, -d.zCoord / dist3D);
               } else {
                  e.addVelocity(-d.xCoord / dist3D * 4.0D, -d.yCoord / dist3D * 4.0D, -d.zCoord / dist3D * 4.0D);
               }

               if(source.worldObj.isRemote) {
                  source.worldObj.spawnParticle("enchantmenttable", e.posX + (double)Item.itemRand.nextFloat() - 0.5D, e.posY + (double)Item.itemRand.nextFloat() - 0.5D, e.posZ + (double)Item.itemRand.nextFloat() - 0.5D, d.xCoord, d.yCoord, d.zCoord);
               }
            }
         }
      }

   }
}
