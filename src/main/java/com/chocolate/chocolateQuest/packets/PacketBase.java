package com.chocolate.chocolateQuest.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PacketBase implements IMessage {

   public void setData(byte[] bs) {}

   public abstract void fromBytes(ByteBuf var1);

   public abstract void toBytes(ByteBuf var1);

   public static void writeString(ByteBuf bytes, String string) {
      byte size = (byte)(string.length() > 255?255:string.length());
      bytes.writeByte(string.length());

      for(byte i = 0; i < size; ++i) {
         bytes.writeChar(string.charAt(i));
      }

   }

   public static String readString(ByteBuf bytes) {
      String string = "";
      byte size = bytes.readByte();

      for(byte i = 0; i < size; ++i) {
         string = string + bytes.readChar();
      }

      return string;
   }

   public static void writeTag(ByteBuf bytes, NBTTagCompound tag) {
      try {
         bytes.writeBytes(CompressedStreamTools.compress(tag));
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static NBTTagCompound readTag(ByteBuf bytes) {
      ByteBufInputStream byteBuff = new ByteBufInputStream(bytes);
      NBTTagCompound tag = null;

      try {
         tag = CompressedStreamTools.readCompressed(byteBuff);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return tag;
   }
}
