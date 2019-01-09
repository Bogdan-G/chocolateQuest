package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.packets.ChannelHandlerClient;
import com.chocolate.chocolateQuest.particles.EffectManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketSpawnParticlesAround implements IMessage, IMessageHandler<PacketSpawnParticlesAround, IMessage> {

   public static final byte HEARTS = 0;
   public static final byte CRIT = 1;
   public static final byte FLAME_LAUNCH = 2;
   public static final byte FLAMES = 3;
   public static final byte SPARK = 4;
   public static final byte SMOKE = 5;
   public static final byte WITCH_MAGIC = 6;
   public static final byte GUNSHOT = 7;
   public static final byte HATE_AURA = 8;
   public static final byte FIRE = 100;
   public static final byte BLAST = 101;
   public static final byte MAGIC = 102;
   public static final byte PHYSIC = 103;
   public static final byte LIGHT = 104;
   public static final byte DARK = 105;
   byte type;
   double x;
   double y;
   double z;


   public PacketSpawnParticlesAround() {}

   public PacketSpawnParticlesAround(byte animType, double x, double y, double z) {
      this.type = animType;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public IMessage onMessage(PacketSpawnParticlesAround message, MessageContext ctx) {
      World world = ChannelHandlerClient.getClientWorld();
      message.execute(world);
      return null;
   }

   public void execute(World world) {
      double var23;
      if(this.type == 2) {
         Entity var20 = world.getEntityByID((int)this.x);
         Random var19 = new Random();
         if(var20 != null) {
            float var21 = (float)(-Math.sin(Math.toRadians((double)var20.rotationYaw)));
            float var18 = (float)Math.cos(Math.toRadians((double)var20.rotationYaw));
            float var22 = (float)(-Math.sin(Math.toRadians((double)var20.rotationPitch)));
            var21 *= 1.0F - Math.abs(var22);
            var18 *= 1.0F - Math.abs(var22);

            for(int var24 = 0; var24 < 50; ++var24) {
               var23 = var19.nextDouble() + 0.2D;
               float var25 = var20.height * 0.7F;
               if(var20 instanceof EntityPlayer) {
                  var25 = 0.0F;
               }

               EffectManager.spawnParticle(3, var20.worldObj, var20.posX, var20.posY + (double)var25, var20.posZ, ((double)var21 + (var19.nextDouble() - 0.5D) / 3.0D) * var23, ((double)var22 + (var19.nextDouble() - 0.5D) / 3.0D) * var23, ((double)var18 + (var19.nextDouble() - 0.5D) / 3.0D) * var23);
            }
         }

      } else {
         Random part;
         if(this.type >= 100) {
            part = world.rand;
            double ammount = 0.05D;
            double posVar = 3.0D;
            Elements element = Elements.fire;
            if(this.type == 101) {
               element = Elements.blast;
            }

            if(this.type == 102) {
               element = Elements.magic;
            }

            if(this.type == 103) {
               element = Elements.physic;
            }

            if(this.type == 104) {
               element = Elements.light;
            }

            if(this.type == 105) {
               element = Elements.darkness;
            }

            for(int motionY = 0; motionY < 8; ++motionY) {
               EffectManager.spawnElementParticle(0, world, this.x + part.nextGaussian() / posVar, this.y + part.nextGaussian() / posVar, this.z + part.nextGaussian() / posVar, part.nextGaussian() * ammount, part.nextGaussian() * ammount, part.nextGaussian() * ammount, element);
            }
         } else {
            part = null;
            byte var17 = 8;
            double motionVariation = 0.25D;
            double motionX = 0.0D;
            var23 = 0.0D;
            double motionZ = 0.0D;
            double posVar1 = 3.0D;
            String var16;
            switch(this.type) {
            case 0:
               var16 = "heart";
               var17 = 4;
               break;
            case 1:
               var16 = "crit";
               var17 = 16;
               break;
            case 2:
            default:
               var16 = "crit";
               break;
            case 3:
               var16 = "flame";
               motionVariation = 0.08D;
               break;
            case 4:
               var16 = "fireworksSpark";
               break;
            case 5:
               var16 = "smoke";
               motionVariation = 0.02D;
               posVar1 = 6.0D;
               break;
            case 6:
               var16 = "witchMagic";
               break;
            case 7:
               var16 = "explode";
               var17 = 3;
               motionVariation = 0.0D;
               var23 = 0.1D;
            }

            Random rand = world.rand;

            for(int i = 0; i < var17; ++i) {
               world.spawnParticle(var16, this.x + rand.nextGaussian() / posVar1, this.y + rand.nextGaussian() / posVar1, this.z + rand.nextGaussian() / posVar1, motionX + rand.nextGaussian() * motionVariation, var23 + rand.nextGaussian() * motionVariation, motionZ + rand.nextGaussian() * motionVariation);
            }
         }

      }
   }

   public void fromBytes(ByteBuf bytes) {
      this.type = bytes.readByte();
      this.x = bytes.readDouble();
      this.y = bytes.readDouble();
      this.z = bytes.readDouble();
   }

   public void toBytes(ByteBuf bytes) {
      bytes.writeByte(this.type);
      bytes.writeDouble(this.x);
      bytes.writeDouble(this.y);
      bytes.writeDouble(this.z);
   }

   public static byte getParticleFromName(String name) {
      return (byte)(name.equals("smoke")?101:(name.equals("flame")?3:(name.equals("witchMagic")?102:(name.equals("fireworksSpark")?103:(name.equals("crit")?1:(name.equals("heart")?0:(name.equals("fire")?100:(name.equals("blast")?101:(name.equals("magic")?102:(name.equals("physic")?103:(name.equals("light")?104:(name.equals("dark")?105:(name.equals("fireE")?100:(name.equals("blastE")?101:(name.equals("magicE")?102:(name.equals("physicE")?103:0))))))))))))))));
   }
}
