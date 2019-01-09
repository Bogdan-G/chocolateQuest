package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemBaseAxe extends ItemCQBlade {

   String texture;


   public ItemBaseAxe(ToolMaterial mat, int baseDamage) {
      super(mat, (float)baseDamage);
   }

   public ItemBaseAxe(ToolMaterial mat, String texture) {
      this(mat, 2);
      this.texture = texture;
   }

   public ItemBaseAxe(ToolMaterial mat, String texture, int baseDamage, float elementModifier) {
      this(mat, baseDamage);
      this.texture = texture;
      super.elementModifier = elementModifier;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
   }

   public EnumAction getItemUseAction(ItemStack p_77661_1_) {
      return EnumAction.none;
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.onGround && !entityPlayer.isSwingInProgress) {
         float exhaustion = 16.0F;
         int staminaSaving = Awakements.getEnchantLevel(itemStack, Awakements.dodgeStamina);
         if(staminaSaving > 0) {
            exhaustion /= (float)staminaSaving;
         }

         if(PlayerManager.useStamina(entityPlayer, exhaustion) && !world.isRemote) {
            world.spawnEntityInWorld(new EntityBaseBall(world, entityPlayer, 3, 0));
         }
      }

      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
   }

   public float getDigSpeed(ItemStack stack, Block block, int meta) {
      return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine?super.getDigSpeed(stack, block, meta):7.0F;
   }

   public boolean hasSkill() {
      return true;
   }

   public int doSkill(EntityHumanBase human) {
      human.worldObj.spawnEntityInWorld(new EntityBaseBall(human.worldObj, human, 3, 0));
      return 80;
   }
}
