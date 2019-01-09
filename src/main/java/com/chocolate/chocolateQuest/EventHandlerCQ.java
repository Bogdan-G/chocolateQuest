package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.gui.guiParty.PartyManager;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.items.ItemMedal;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.gun.ItemMusket;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.misc.PotionCQ;
import com.chocolate.chocolateQuest.packets.PacketShieldBlockFromServer;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.quest.worldManager.ReputationManager;
import com.chocolate.chocolateQuest.quest.worldManager.TerrainManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class EventHandlerCQ {

   @SubscribeEvent
   public void onLivingUpdate(LivingUpdateEvent event) {
      if(event.entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.entity;
         PlayerManager.addStamina(player, 0.075F);
         if(player.worldObj.isRemote) {
            BuilderHelper.builderHelper.resetStructureGenerationAmmount();
         }
      }

   }

   @SubscribeEvent
   public void onLivingJump(LivingJumpEvent event) {
      ItemStack is = event.entityLiving.getEquipmentInSlot(1);
      if(is != null) {
         EntityLivingBase entity;
         if(is.getItem() == ChocolateQuest.cloudBoots) {
            entity = event.entityLiving;
            if(!entity.isSneaking()) {
               entity.motionY += 0.4D;
            }
         }

         if(is.getItem() == ChocolateQuest.spiderBoots) {
            entity = event.entityLiving;
            if(!entity.isSneaking()) {
               entity.motionY += 0.2D;
            }
         }

         if(is.getItem() == ChocolateQuest.slimeBoots) {
            entity = event.entityLiving;
            if(!entity.isSneaking()) {
               entity.motionY += 0.1D;
            }
         }
      }

   }

   @SubscribeEvent
   public void onLivingHurt(LivingHurtEvent event) {
      Entity hitEntity = event.entity;
      Entity sourceEntity = event.source.getEntity();
      if(hitEntity instanceof EntityLivingBase) {
         if(hitEntity.ridingEntity != null && hitEntity.ridingEntity instanceof EntityGolemMechaHeavy) {
            hitEntity.ridingEntity.attackEntityFrom(event.source, event.ammount);
            event.setCanceled(true);
            return;
         }

         int armor;
         if(sourceEntity instanceof EntityLivingBase && !event.source.isProjectile()) {
            ItemStack el = ((EntityLivingBase)sourceEntity).getEquipmentInSlot(0);
            if(el != null && (el.getItem() instanceof ItemCQBlade || el.getItem() instanceof ItemMusket)) {
               ItemStaffHeal.applyPotionEffects(el, (EntityLivingBase)hitEntity);
               if(Elements.hasElements(el)) {
                  Elements[] i = Elements.values();
                  armor = i.length;

                  for(int ammount = 0; ammount < armor; ++ammount) {
                     Elements packet = i[ammount];
                     float damage = Elements.getElementDamage(el, packet);
                     if(damage > 0.0F) {
                        damage = packet.onHitEntity(sourceEntity, hitEntity, damage);
                        damage = (float)((double)damage * BDHelper.getDamageReductionForElement((EntityLivingBase)hitEntity, packet, true));
                        event.ammount += damage;
                        byte particle = PacketSpawnParticlesAround.getParticleFromName(packet.getParticle());
                        PacketSpawnParticlesAround packet1 = new PacketSpawnParticlesAround(particle, hitEntity.posX, hitEntity.posY + 1.0D, hitEntity.posZ);
                        ChocolateQuest.channel.sendToAllAround(hitEntity, packet1, 64);
                     }
                  }
               }
            }
         }

         if(hitEntity instanceof EntityPlayer) {
            EntityPlayer var12 = (EntityPlayer)hitEntity;
            if(var12.isBlocking() && !event.source.isUnblockable()) {
               ItemStack var14 = var12.getCurrentEquippedItem();
               if(var14 != null && var14.getItem() instanceof ItemSwordAndShieldBase) {
                  armor = Awakements.getEnchantLevel(var14, Awakements.blockStamina) + 1;
                  float var17 = event.ammount / (float)armor * 1.5F;
                  if(sourceEntity != null) {
                     if(sourceEntity instanceof EntityLivingBase) {
                        ((ItemSwordAndShieldBase)var14.getItem()).onBlock(var12, (EntityLivingBase)sourceEntity, event.source);
                     }

                     if(PlayerManager.useStamina(var12, var17)) {
                        PacketShieldBlockFromServer var18 = new PacketShieldBlockFromServer(var12.getEntityId(), sourceEntity.getEntityId(), var17);
                        ChocolateQuest.channel.sendToPlayer(var18, (EntityPlayerMP)var12);
                        event.ammount = 0.0F;
                        event.setCanceled(true);
                     } else {
                        event.ammount /= (float)(2 + armor);
                     }
                  }
               }
            }
         }

         EntityLivingBase var13 = (EntityLivingBase)hitEntity;
         event.ammount = (float)((double)event.ammount * BDHelper.getDamageReductionForElement(var13, BDHelper.getElementFromDamageSource(event.source), false));

         for(int var15 = 0; var15 < 4; ++var15) {
            ItemStack var16 = var13.getEquipmentInSlot(1 + var15);
            if(var16 != null && var16.getItem() instanceof ItemArmorBase) {
               ((ItemArmorBase)var16.getItem()).onHit(event, var16, var13);
            }
         }
      }

      if(hitEntity instanceof EntityCreeper && sourceEntity instanceof EntityHumanBase) {
         hitEntity.setDead();
      }

   }

   @SubscribeEvent
   public void onLivingDeath(LivingDeathEvent event) {
      if(!event.entity.worldObj.isRemote) {
         if(event.source.getEntity() instanceof EntityPlayer) {
            ReputationManager.instance.onEntityKilled(event.entityLiving, (EntityPlayer)event.source.getEntity());
         }

         if(event.source.getEntity() instanceof EntityHumanBase) {
            EntityHumanBase items = (EntityHumanBase)event.source.getEntity();
            if(items.getOwner() instanceof EntityPlayer) {
               ReputationManager.instance.onEntityKilled(event.entityLiving, (EntityPlayer)items.getOwner());
            }
         }

         if(event.entity.getEntityData().hasKey("items")) {
            ItemStack[] var4 = ItemMedal.getEntityInventory(event.entity);

            for(int i = 0; i < var4.length; ++i) {
               if(var4[i] != null) {
                  event.entity.entityDropItem(var4[i], 0.5F);
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void PlayerInteractEvent(PlayerInteractEvent event) {
      Action var10001 = event.action;
      if(event.action == Action.RIGHT_CLICK_BLOCK) {
         Block block = event.world.getBlock(event.x, event.y, event.z);
         if(!block.canProvidePower() && block != Blocks.wooden_door) {
            if(!event.entityPlayer.capabilities.isCreativeMode && event.entityPlayer.isPotionActive(PotionCQ.minePrevention)) {
               ItemStack is = event.entityPlayer.getCurrentEquippedItem();
               if(is != null && is.getItem() instanceof ItemBlock && ((ItemBlock)is.getItem()).field_150939_a != Blocks.torch) {
                  event.setCanceled(true);
               }
            }
         } else if(!event.entity.worldObj.isRemote) {
            this.alertNearbyEntities(event.world, event.x, event.y, event.z, 10, 200);
         }
      } else {
         var10001 = event.action;
         if(event.action == Action.LEFT_CLICK_BLOCK && !this.canBreakBlock(event.entityPlayer, event.x, event.y, event.z)) {
            event.setCanceled(true);
         }
      }

   }

   @SubscribeEvent
   public void onBreakBlock(BreakEvent event) {
      if(!this.canBreakBlock(event.getPlayer(), event.x, event.y, event.z)) {
         event.setCanceled(true);
      } else {
         if(!event.getPlayer().worldObj.isRemote) {
            this.alertNearbyEntities(event.world, event.x, event.y, event.z, 15, 100);
         }

      }
   }

   public boolean canBreakBlock(EntityPlayer player, int x, int y, int z) {
      if(!player.capabilities.isCreativeMode && player.isPotionActive(PotionCQ.minePrevention)) {
         Block block = player.worldObj.getBlock(x, y, z);
         if(block != Blocks.torch && block != Blocks.mob_spawner && !(block instanceof BlockFalling)) {
            return false;
         }
      }

      return true;
   }

   public void alertNearbyEntities(World world, int x, int y, int z, int expansion, int alarmTime) {
      List list = world.getEntitiesWithinAABB(EntityHumanBase.class, AxisAlignedBB.getBoundingBox((double)(x - expansion), (double)(y - expansion), (double)(z - expansion), (double)(x + expansion), (double)(y + expansion), (double)(z + expansion)));
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         EntityHumanBase e = (EntityHumanBase)i$.next();
         e.alert(alarmTime);
      }

   }

   @SubscribeEvent
   public void onAnvilUpdateEvent(AnvilUpdateEvent event) {
      if(event.left != null && event.right != null && (event.left.getItem() instanceof ItemCQBlade || event.left.getItem() instanceof ItemArmorBase || event.left.getItem() instanceof ItemMusket && ChocolateQuest.musket.getBayonet(event.left) > 0) && event.right.getItem() == ChocolateQuest.elementStone) {
         event.output = ItemStack.copyItemStack(event.left);
         Elements element = ChocolateQuest.elementStone.getElement(event.right);
         int actualValue = Elements.getElementValue(event.left, element);
         byte cost = 50;
         boolean costPerLevel = true;
         if(event.left.getItem() instanceof ItemArmorBase) {
            cost = 25;
            costPerLevel = true;
         }

         if(actualValue < ChocolateQuest.elementStone.getMaxLevel(event.right)) {
            Elements.setElementValue(event.output, element, actualValue + 1);
            event.cost = cost + actualValue * 10;
         }
      }

   }

   @SubscribeEvent
   public void worldLoad(Load event) {
      if(!event.world.isRemote) {
         ReputationManager.instance = new ReputationManager();
         ReputationManager.instance.read(event.world, "/data/chocolateQuest/chocolateQuest.dat");
         TerrainManager.instance = new TerrainManager(ChocolateQuest.config.dungeonSeparation);
         TerrainManager.instance.read(event.world, "/data/chocolateQuest/terrain.dat");
      } else {
         PartyManager.instance.restart();
      }

   }

   @SubscribeEvent
   public void worldSave(Save event) {
      if(!event.world.isRemote) {
         if(ReputationManager.instance != null) {
            ReputationManager.instance.save(event.world, "/data/chocolateQuest", "chocolateQuest.dat");
         }

         if(TerrainManager.instance != null) {
            TerrainManager.instance.save(event.world, "/data/chocolateQuest", "terrain.dat");
         }
      }

   }

   @SubscribeEvent
   public void worldUnload(Unload event) {
      PlayerManager.reset();
   }
}
