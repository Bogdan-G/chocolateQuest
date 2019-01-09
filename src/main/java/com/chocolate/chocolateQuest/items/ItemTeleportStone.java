package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.EntityPortal;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemTeleportStone extends Item implements ILoadBar {

   public IIcon[] icon;
   String textureName;
   static final String TAG_POINT_NAME = "position";
   static final String TAG_PLACE_POSITION = "placePosition";
   static final String TAG_PLACE_PORTAL = "placePortal";


   public boolean getHasSubtypes() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.icon = new IIcon[16];

      for(int i = 0; i < this.icon.length; ++i) {
         this.icon[i] = iconRegister.registerIcon("chocolatequest:d" + i);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return par1 < this.icon.length?this.icon[par1]:this.icon[0];
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List) {
      for(int is = 0; is < this.icon.length; ++is) {
         par3List.add(new ItemStack(this, 1, is));
      }

      ItemStack var5 = new ItemStack(this, 1);
      var5.stackTagCompound = new NBTTagCompound();
      var5.stackTagCompound.setBoolean("placePortal", true);
      par3List.add(var5);
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      ChunkCoordinates coords = this.getPoint(is);
      boolean addCoords = true;
      if(is.hasTagCompound()) {
         if(is.stackTagCompound.hasKey("position")) {
            addCoords = false;
            list.add(is.stackTagCompound.getString("position"));
            if(is.stackTagCompound.hasKey("placePosition")) {
               list.add("Position placer");
            }
         }

         if(is.stackTagCompound.hasKey("placePortal")) {
            list.add("Portal spawner");
         }
      }

      if(addCoords && coords != null) {
         list.add("x:" + coords.posX + ", y:" + coords.posY + ", z:" + coords.posZ);
      }

      if(player.capabilities.isCreativeMode) {
         list.add("Tags: placePortal(boolean), ");
         list.add("position(String), placePosition(boolean)");
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(this.getPoint(itemstack) != null) {
         entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      }

      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {
      useTime = this.getMaxItemUseDuration(itemstack) - useTime;
      if((useTime > this.getMaxCharge() || entityPlayer.capabilities.isCreativeMode) && entityPlayer instanceof EntityPlayerMP) {
         ChunkCoordinates coords = this.getPoint(itemstack);
         if(coords != null) {
            ((EntityPlayerMP)entityPlayer).playerNetServerHandler.setPlayerLocation((double)coords.posX + 0.5D, (double)coords.posY, (double)coords.posZ + 0.5D, entityPlayer.rotationYaw, entityPlayer.rotationPitch);
         }
      }

   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.drink;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return this.getMaxCharge() * 5;
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int f1, float fx, float fy, float fz) {
      if(!world.isRemote) {
         if(this.getPoint(stack) == null) {
            switch(f1) {
            case 0:
               y -= 2;
               break;
            case 1:
               ++y;
               break;
            case 2:
               --z;
               break;
            case 3:
               ++z;
               break;
            case 4:
               --x;
               break;
            case 5:
               ++x;
            }

            if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("position")) {
               if(stack.stackTagCompound.hasKey("placePosition")) {
                  String var14 = stack.stackTagCompound.getString("position");
                  TerrainManager.getInstance().addTeleportPoint(var14, new ChunkCoordinates(x, y, z));
                  player.addChatMessage(new ChatComponentText("Saved point " + x + ", " + y + ", " + z + ", as " + var14));
                  EntityPortal var15 = new EntityPortal(world);
                  var15.name = var14;
                  var15.coords = this.getPoint(stack);
                  var15.type = 2;
                  var15.setPosition((double)x + 0.5D, (double)y, (double)z + 0.5D);
                  world.spawnEntityInWorld(var15);
               }

               return true;
            }

            this.setPoint(stack, new ChunkCoordinates(x, y, z));
            return true;
         }

         if(this.canPlacePortal(stack)) {
            EntityPortal e = new EntityPortal(world);
            if(player.isSneaking()) {
               fz = 0.5F;
               fx = 0.5F;
               fy = 0.0F;
               if(f1 == 1) {
                  fy = 1.0F;
               }
            }

            byte type = 0;
            float rot = 0.0F;
            switch(f1) {
            case 1:
               type = 1;
               break;
            case 2:
               rot = 180.0F;
               fz = 0.0F;
               break;
            case 3:
               rot = 0.0F;
               fz = 1.0F;
               break;
            case 4:
               rot = 90.0F;
               fx = 0.0F;
               break;
            case 5:
               rot = 270.0F;
               fx = 1.0F;
               break;
            default:
               rot = (float)((int)player.rotationYawHead - 180);
            }

            e.rotationYaw = rot;
            e.setPosition((double)((float)x + fx), (double)((float)y + fy), (double)((float)z + fz));
            e.type = type;
            if(stack.stackTagCompound.hasKey("position")) {
               e.name = stack.stackTagCompound.getString("position");
            } else {
               e.coords = this.getPoint(stack);
            }

            world.spawnEntityInWorld(e);
            if(!player.capabilities.isCreativeMode) {
               player.inventory.decrStackSize(player.inventory.currentItem, 1);
            }
         }
      }

      return false;
   }

   public ChunkCoordinates setPoint(ItemStack is, ChunkCoordinates coords) {
      if(!is.hasTagCompound()) {
         is.stackTagCompound = new NBTTagCompound();
      }

      NBTTagCompound tag = new NBTTagCompound();
      tag.setInteger("x", coords.posX);
      tag.setInteger("y", coords.posY);
      tag.setInteger("z", coords.posZ);
      is.stackTagCompound.setTag("coords", tag);
      return null;
   }

   public ChunkCoordinates getPoint(ItemStack is) {
      if(is.hasTagCompound()) {
         if(is.stackTagCompound.hasKey("position")) {
            if(is.stackTagCompound.hasKey("placePosition")) {
               return null;
            }

            String tag1 = is.stackTagCompound.getString("position");
            return TerrainManager.getInstance().getTeleportPoint(tag1);
         }

         if(is.stackTagCompound.hasKey("coords")) {
            NBTTagCompound tag = is.stackTagCompound.getCompoundTag("coords");
            return new ChunkCoordinates(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
         }
      }

      return null;
   }

   public int getMaxCharge() {
      return 100;
   }

   public boolean shouldBarShine(EntityPlayer player, ItemStack is) {
      return player.getItemInUseDuration() > this.getMaxCharge();
   }

   public boolean canPlacePortal(ItemStack is) {
      return is.stackTagCompound == null?false:is.stackTagCompound.hasKey("placePortal");
   }
}
