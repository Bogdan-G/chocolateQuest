package com.chocolate.chocolateQuest.packets;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.packets.PacketUpdateHumanData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class PacketUpdateHumanDummyData extends PacketUpdateHumanData {

   double maxHealth = 1.0D;
   float dropRight;
   float dropHelmet;
   float dropBody;
   float dropLegs;
   float dropFeet;
   float dropLeft;
   boolean lockedInventory;


   public PacketUpdateHumanDummyData() {}

   public PacketUpdateHumanDummyData(EntityHumanBase e) {
      super(e);
      this.maxHealth = e.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
      this.dropLeft = e.leftHandDropChances;
      this.dropRight = e.getEquipmentDropChances(0);
      this.dropHelmet = e.getEquipmentDropChances(4);
      this.dropBody = e.getEquipmentDropChances(3);
      this.dropLegs = e.getEquipmentDropChances(2);
      this.dropFeet = e.getEquipmentDropChances(1);
      this.lockedInventory = e.inventoryLocked;
   }

   public void fromBytes(ByteBuf inputStream) {
      super.fromBytes(inputStream);
      this.maxHealth = inputStream.readDouble();
      this.dropLeft = inputStream.readFloat();
      this.dropRight = inputStream.readFloat();
      this.dropHelmet = inputStream.readFloat();
      this.dropBody = inputStream.readFloat();
      this.dropLegs = inputStream.readFloat();
      this.dropFeet = inputStream.readFloat();
      this.lockedInventory = inputStream.readBoolean();
   }

   public void toBytes(ByteBuf outputStream) {
      super.toBytes(outputStream);
      outputStream.writeDouble(this.maxHealth);
      outputStream.writeFloat(this.dropLeft);
      outputStream.writeFloat(this.dropRight);
      outputStream.writeFloat(this.dropHelmet);
      outputStream.writeFloat(this.dropBody);
      outputStream.writeFloat(this.dropLegs);
      outputStream.writeFloat(this.dropFeet);
      outputStream.writeBoolean(this.lockedInventory);
   }

   public void execute(World world) {
      super.execute(world);
      if(super.human != null) {
         super.human.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.maxHealth);
         EntityHumanBase dummy = super.human;
         dummy.leftHandDropChances = this.dropLeft;
         dummy.setEquipmentDropChances(0, this.dropRight);
         dummy.setEquipmentDropChances(4, this.dropHelmet);
         dummy.setEquipmentDropChances(3, this.dropBody);
         dummy.setEquipmentDropChances(2, this.dropLegs);
         dummy.setEquipmentDropChances(1, this.dropFeet);
         super.human.inventoryLocked = this.lockedInventory;
      }

   }
}
