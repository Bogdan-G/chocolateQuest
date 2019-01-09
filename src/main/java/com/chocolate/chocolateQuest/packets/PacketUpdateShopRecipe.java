package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerShop;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class PacketUpdateShopRecipe implements IMessage {

   int entityID;
   int recipeIndex;
   ShopRecipe recipe;
   ShopRecipe[] trades;


   public PacketUpdateShopRecipe() {}

   public PacketUpdateShopRecipe(EntityHumanNPC npc, int recipeIndex) {
      this.entityID = npc.getEntityId();
      this.recipeIndex = recipeIndex;
      if(recipeIndex > -1) {
         this.recipe = npc.getRecipes()[recipeIndex];
      } else {
         this.trades = npc.getRecipes();
      }

   }

   public void execute(EntityPlayer player) {
      Entity entity = player.worldObj.getEntityByID(this.entityID);
      if(entity instanceof EntityHumanNPC) {
         if(this.recipeIndex > -1) {
            ((EntityHumanNPC)entity).setRecipes(this.recipeIndex, this.recipe);
         } else {
            ((EntityHumanNPC)entity).setRecipes(this.trades);
         }

         if(player.openContainer instanceof ContainerShop) {
            ((ContainerShop)player.openContainer).shopInventory.updateCargo();
         }
      }

   }

   public void fromBytes(ByteBuf bytes) {
      this.entityID = bytes.readInt();
      this.recipeIndex = bytes.readByte();
      if(this.recipeIndex > -1) {
         this.recipe = this.readRecipe(bytes);
      } else {
         byte tradesLength = bytes.readByte();
         this.trades = new ShopRecipe[tradesLength];

         for(int i = 0; i < tradesLength; ++i) {
            this.trades[i] = this.readRecipe(bytes);
         }
      }

   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeInt(this.entityID);
      bytes.writeByte(this.recipeIndex);
      if(this.recipeIndex > -1) {
         this.writeRecipe(bytes, this.recipe);
      } else if(this.trades == null) {
         bytes.writeByte(0);
      } else {
         bytes.writeByte(this.trades.length);

         for(int i = 0; i < this.trades.length; ++i) {
            this.writeRecipe(bytes, this.trades[i]);
         }
      }

   }

   public ShopRecipe readRecipe(ByteBuf bytes) {
      ItemStack recipeItem = this.readStackBytes(bytes);
      byte costItems = bytes.readByte();
      ItemStack[] costItemStacks = new ItemStack[costItems];

      for(int i = 0; i < costItemStacks.length; ++i) {
         costItemStacks[i] = this.readStackBytes(bytes);
      }

      return new ShopRecipe(recipeItem, costItemStacks);
   }

   public void writeRecipe(ByteBuf bytes, ShopRecipe recipe) {
      this.writeStackBytes(bytes, recipe.tradedItem);
      bytes.writeByte(recipe.costItems.length);
      ItemStack[] arr$ = recipe.costItems;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ItemStack is = arr$[i$];
         this.writeStackBytes(bytes, is);
      }

   }

   public ItemStack readStackBytes(ByteBuf bytes) {
      int length = bytes.readInt();
      if(length > 0) {
         NBTTagCompound data = null;
         byte[] bData = new byte[length];
         bytes.readBytes(bData);

         try {
            data = CompressedStreamTools.func_152457_a(bData, NBTSizeTracker.field_152451_a);
         } catch (IOException var6) {
            var6.printStackTrace();
         }

         ItemStack is = ItemStack.loadItemStackFromNBT(data);
         return is;
      } else {
         return null;
      }
   }

   public void writeStackBytes(ByteBuf bytes, ItemStack itemstack) {
      try {
         NBTTagCompound e = new NBTTagCompound();
         if(itemstack != null) {
            itemstack.writeToNBT(e);
         }

         byte[] bData = CompressedStreamTools.compress(e);
         bytes.writeInt(bData.length);
         bytes.writeBytes(bData);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
