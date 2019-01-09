package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockDungeonChestTileEntity;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.ContainerArmorStand;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.ContainerGolemInventory;
import com.chocolate.chocolateQuest.gui.ContainerGun;
import com.chocolate.chocolateQuest.gui.ContainerHumanInventory;
import com.chocolate.chocolateQuest.gui.GuiArmorStand;
import com.chocolate.chocolateQuest.gui.GuiChestBD;
import com.chocolate.chocolateQuest.gui.GuiDummy;
import com.chocolate.chocolateQuest.gui.GuiEditor;
import com.chocolate.chocolateQuest.gui.GuiGolem;
import com.chocolate.chocolateQuest.gui.GuiHuman;
import com.chocolate.chocolateQuest.gui.GuiInventoryEntity;
import com.chocolate.chocolateQuest.gui.GuiInventoryGun;
import com.chocolate.chocolateQuest.gui.GuiMobController;
import com.chocolate.chocolateQuest.gui.InventoryGun;
import com.chocolate.chocolateQuest.gui.InventoryHuman;
import com.chocolate.chocolateQuest.gui.InventoryMedal;
import com.chocolate.chocolateQuest.gui.guiParty.GuiParty;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.ContainerShop;
import com.chocolate.chocolateQuest.gui.guinpc.GuiAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditDialog;
import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiShop;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryAwakement;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {

   public ChannelHandler channel;


   public void register() {
      GameRegistry.registerTileEntity(BlockMobSpawnerTileEntity.class, "CQSpawner");
      this.registerTileEntities();
   }

   public void registerRenderInformation() {}

   public void postInit() {}

   public void registerTileEntities() {
      GameRegistry.registerTileEntity(BlockArmorStandTileEntity.class, "armorStand");
      GameRegistry.registerTileEntity(BlockBannerStandTileEntity.class, "bannerStand");
      GameRegistry.registerTileEntity(BlockAltarTileEntity.class, "table");
      GameRegistry.registerTileEntity(BlockEditorTileEntity.class, "exporter");
   }

   public void registerAudio() {}

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      Entity e;
      if(ID == 0) {
         e = world.getEntityByID(x);
         if(e instanceof EntityHumanNPC) {
            if(player.capabilities.isCreativeMode) {
               return new GuiDummy((EntityHumanBase)e, new InventoryHuman((EntityHumanBase)e), player);
            }

            return new GuiHuman((EntityHumanBase)e, new InventoryHuman((EntityHumanBase)e), player);
         }

         if(e instanceof EntityGolemMecha) {
            return new GuiGolem((EntityHumanBase)e, new InventoryHuman((EntityHumanBase)e), player);
         }

         if(e instanceof EntityHumanDummy) {
            return new GuiDummy((EntityHumanBase)e, new InventoryHuman((EntityHumanBase)e), player);
         }

         if(e instanceof EntityHumanBase) {
            return new GuiHuman((EntityHumanBase)e, new InventoryHuman((EntityHumanBase)e), player);
         }
      }

      if(ID == 1) {
         return new GuiEditor(world, x, y, z);
      } else {
         if(ID == 4) {
            TileEntity e1 = world.getTileEntity(x, y, z);
            if(e1 instanceof BlockDungeonChestTileEntity) {
               return new GuiChestBD(player.inventory, (BlockDungeonChestTileEntity)e1);
            }
         }

         if(ID == 3) {
            ItemStack e2 = player.getCurrentEquippedItem();
            return new GuiInventoryGun(e2, new InventoryGun(e2, player), player);
         } else if(ID == 2) {
            BlockArmorStandTileEntity e3 = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
            return new GuiArmorStand(e3, player, e3);
         } else {
            if(ID == 5) {
               e = world.getEntityByID(x);
               if(e instanceof EntityHumanNPC) {
                  return new GuiShop(new InventoryShop((EntityHumanNPC)e), player);
               }
            }

            if(ID == 7) {
               e = world.getEntityByID(x);
               if(e instanceof EntityHumanNPC) {
                  return new GuiAwakement(new InventoryAwakement((EntityHumanNPC)e), player, y, z);
               }
            }

            if(ID == 6) {
               e = world.getEntityByID(x);
               if(e instanceof EntityHumanNPC) {
                  EntityHumanNPC inventoryMedal1 = (EntityHumanNPC)e;
                  switch(y) {
                  case 1:
                     return new GuiEditDialog((GuiScreen)null, inventoryMedal1.conversation, inventoryMedal1);
                  default:
                     return new GuiNPC(inventoryMedal1, player);
                  }
               }
            }

            if(ID == 8) {
               return new GuiMobController();
            } else if(ID == 9) {
               return new GuiParty();
            } else {
               if(ID == 10) {
                  e = world.getEntityByID(x);
                  if(e != null) {
                     ItemStack is = player.getCurrentEquippedItem();
                     InventoryMedal inventoryMedal = new InventoryMedal(e);
                     return new GuiInventoryEntity(new ContainerBDChest(player.inventory, inventoryMedal), inventoryMedal, player);
                  }
               }

               return null;
            }
         }
      }
   }

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      Entity e;
      if(ID == 0) {
         e = world.getEntityByID(x);
         if(e instanceof EntityGolemMecha) {
            return new ContainerGolemInventory(player.inventory, new InventoryHuman((EntityHumanBase)e));
         }

         if(e instanceof EntityHumanBase) {
            return new ContainerHumanInventory(player.inventory, new InventoryHuman((EntityHumanBase)e));
         }
      }

      if(ID == 4) {
         TileEntity e1 = world.getTileEntity(x, y, z);
         if(e1 instanceof BlockDungeonChestTileEntity) {
            return new ContainerBDChest(player.inventory, (BlockDungeonChestTileEntity)e1);
         }
      }

      if(ID == 3) {
         ItemStack e3 = player.getCurrentEquippedItem();
         return new ContainerGun(player.inventory, new InventoryGun(e3, player), e3);
      } else if(ID == 2) {
         BlockArmorStandTileEntity e2 = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
         return new ContainerArmorStand(player.inventory, e2);
      } else {
         if(ID == 5) {
            e = world.getEntityByID(x);
            if(e instanceof EntityHumanNPC) {
               return new ContainerShop(player.inventory, new InventoryShop((EntityHumanNPC)e));
            }
         }

         if(ID == 7) {
            e = world.getEntityByID(x);
            if(e instanceof EntityHumanNPC) {
               return new ContainerAwakement(player.inventory, new InventoryAwakement((EntityHumanNPC)e), y, z);
            }
         }

         if(ID == 10) {
            e = world.getEntityByID(x);
            if(e != null) {
               ItemStack is = player.getCurrentEquippedItem();
               return new ContainerBDChest(player.inventory, new InventoryMedal(e));
            }
         }

         return null;
      }
   }
}
