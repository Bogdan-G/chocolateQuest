package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBaseDagger extends ItemCQBlade {

   public static final String REAR_DAMAGE = "rear_damage";
   String texture;
   double speedModifier;


   public ItemBaseDagger(ToolMaterial mat, int baseDamage) {
      super(mat, (float)baseDamage);
      this.speedModifier = 0.05D;
   }

   public ItemBaseDagger(ToolMaterial mat, String texture) {
      this(mat, 2);
      this.texture = texture;
   }

   public ItemBaseDagger(ToolMaterial mat, String texture, int baseDamage, float elementModifier) {
      this(mat, baseDamage);
      this.texture = texture;
      super.elementModifier = elementModifier;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      float ammount = this.getBackStabModifier(is);
      list.add(BDHelper.formatNumberToDisplay((int)(ammount * 100.0F)) + "% " + StatCollector.translateToLocal("weaponbonus.rear_damage.name").trim());
      super.addInformation(is, player, list, par4);
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.onGround && !entityPlayer.isSwingInProgress) {
         float exhaustion = 8.0F;
         int staminaSaving = Awakements.getEnchantLevel(itemStack, Awakements.dodgeStamina);
         if(staminaSaving > 0) {
            exhaustion /= (float)staminaSaving;
         }

         if(PlayerManager.useStamina(entityPlayer, exhaustion)) {
            float rot = entityPlayer.rotationYawHead * 3.1416F / 180.0F;
            double mx = -Math.sin((double)rot);
            double mz = Math.cos((double)rot);
            entityPlayer.motionX += mx * (double)entityPlayer.moveForward;
            entityPlayer.motionZ += mz * (double)entityPlayer.moveForward;
            rot = (float)((double)rot - 1.57D);
            mx = -Math.sin((double)rot);
            mz = Math.cos((double)rot);
            entityPlayer.motionX += mx * (double)entityPlayer.moveStrafing;
            entityPlayer.motionZ += mz * (double)entityPlayer.moveStrafing;
            entityPlayer.motionY = 0.2D;
         }
      }

      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
   }

   public Multimap getItemAttributeModifiers() {
      Multimap multimap = super.getItemAttributeModifiers();
      multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Weapon modifier", this.speedModifier, 2));
      return multimap;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase target, EntityLivingBase entity) {
      double angle;
      for(angle = (double)(entity.rotationYaw - target.rotationYaw); angle > 360.0D; angle -= 360.0D) {
         ;
      }

      while(angle < 0.0D) {
         angle += 360.0D;
      }

      angle = Math.abs(angle - 180.0D);
      if(angle > 130.0D) {
         if(!entity.worldObj.isRemote) {
            PacketSpawnParticlesAround damage = new PacketSpawnParticlesAround((byte)1, target.posX, target.posY + 1.0D + Item.itemRand.nextDouble(), target.posZ);
            ChocolateQuest.channel.sendToAllAround(entity, damage, 64);
         }

         if(entity.worldObj.isRemote) {
            for(int var9 = 0; var9 < 5; ++var9) {
               entity.worldObj.spawnParticle("crit", target.posX, target.posY + 1.0D + Item.itemRand.nextDouble(), target.posZ, Item.itemRand.nextDouble() - 0.5D, -0.5D + Item.itemRand.nextDouble(), Item.itemRand.nextDouble() - 0.5D);
            }
         }

         float var10 = (float)entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         int awkModifier = Awakements.getEnchantLevel(par1ItemStack, Awakements.backStab);
         float backstabModifier = this.getBackStabModifier(par1ItemStack);
         target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), var10 * backstabModifier * (entity.fallDistance > 0.0F?1.5F:1.0F));
      }

      return super.hitEntity(par1ItemStack, target, entity);
   }

   public float getBackStabModifier(ItemStack is) {
      float damageModifier = 2.0F;
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("rear_damage")) {
         damageModifier = (float)is.stackTagCompound.getInteger("rear_damage") / 100.0F;
      }

      int awkModifier = Awakements.getEnchantLevel(is, Awakements.backStab);
      damageModifier += 0.5F * (float)awkModifier;
      return damageModifier;
   }
}
