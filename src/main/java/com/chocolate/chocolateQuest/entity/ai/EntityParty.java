package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityParty {

   public static final int FRONT = 0;
   public static final int RIGHT = 90;
   public static final int BACK = 180;
   public static final int LEFT = -90;
   public static final int FRONT_RIGHT = 45;
   public static final int FRONT_LEFT = -45;
   public static final int BACK_RIGHT = 135;
   public static final int BACK_LEFT = -135;
   final int distanceToCaptain = 2;
   EntityHumanBase[] members = new EntityHumanBase[8];
   EntityHumanBase leader;
   EntityLivingBase[] potentialTargets = new EntityLivingBase[4];
   int[] targetsAggro = new int[4];
   NBTTagCompound partyTag = null;


   public void update() {
      if(this.partyTag != null) {
         this.loadFromNBT(this.partyTag);
         this.partyTag = null;
      }

      int i;
      for(i = 0; i < this.getMembersLength(); ++i) {
         EntityHumanBase e = this.getMember(i);
         if(e != null && e.isDead) {
            this.removeMember(e);
         }
      }

      for(i = 0; i < this.targetsAggro.length; ++i) {
         if(this.potentialTargets[i] != null) {
            if(this.targetsAggro[i] > 1) {
               --this.targetsAggro[i];
            }

            if(!this.potentialTargets[i].isEntityAlive()) {
               this.setAggro((EntityLivingBase)null, 0, i);
               this.sortTargets();
            }
         }
      }

   }

   public void addAggroToTarget(EntityLivingBase e, int aggro) {
      int i;
      for(i = 0; i < this.potentialTargets.length; ++i) {
         if(this.potentialTargets[i] == e) {
            this.targetsAggro[i] += aggro;
            this.sortTargets();
            return;
         }
      }

      for(i = 0; i < this.potentialTargets.length; ++i) {
         if(this.potentialTargets[i] == null) {
            this.setAggro(e, aggro, i);
            this.sortTargets();
            return;
         }
      }

      for(i = this.potentialTargets.length - 1; i >= 0; --i) {
         if(this.potentialTargets[i] != null) {
            if(aggro < this.targetsAggro[i]) {
               this.setAggro(e, aggro, i);
               this.sortTargets();
            }

            return;
         }
      }

      this.sortTargets();
   }

   public void setAggro(EntityLivingBase e, int aggro, int i) {
      this.potentialTargets[i] = e;
      this.targetsAggro[i] = aggro;
   }

   public void sortTargets() {
      double[] targetsAggro = new double[this.potentialTargets.length];

      int i;
      for(i = 0; i < this.potentialTargets.length; ++i) {
         if(this.potentialTargets[i] != null) {
            targetsAggro[i] = this.getLeader().getDistanceSqToEntity(this.potentialTargets[i]);
         }
      }

      for(i = 0; i < this.potentialTargets.length; ++i) {
         double max = 0.0D;
         int index = i;

         for(int currentEntity = i; currentEntity < this.potentialTargets.length; ++currentEntity) {
            if(targetsAggro[currentEntity] > max && this.potentialTargets[currentEntity] != null) {
               max = targetsAggro[currentEntity];
               index = currentEntity;
            }
         }

         EntityLivingBase var9 = this.potentialTargets[index];
         double currentAggro = targetsAggro[index];
         this.potentialTargets[index] = this.potentialTargets[i];
         targetsAggro[index] = targetsAggro[i];
         this.potentialTargets[i] = var9;
         targetsAggro[i] = currentAggro;
      }

   }

   public EntityLivingBase getTarget() {
      if(this.potentialTargets[0] == null || !this.potentialTargets[0].isEntityAlive()) {
         this.sortTargets();
      }

      return this.potentialTargets[0] == null?this.leader.getAttackTarget():this.potentialTargets[0];
   }

   public void removeMember(EntityHumanBase entity) {
      entity.party = null;
      int leader;
      if(entity == this.getLeader()) {
         this.leader = null;
         entity.setCaptain(false);
         leader = this.getNewLeaderFromParty();
         EntityHumanBase e = this.getMember(leader);
         if(e != null) {
            this.members[leader] = null;
            this.setLeader(e);
         }
      } else {
         for(leader = 0; leader < this.getMembersLength(); ++leader) {
            if(entity.isEntityEqual(this.getMember(leader))) {
               this.removeMember(leader);
               break;
            }
         }
      }

      if(!this.hasMembers()) {
         EntityHumanBase var4 = this.getLeader();
         if(var4 != null) {
            var4.setOutOfParty();
            var4.setCaptain(false);
         }
      }

   }

   public void cleanParty() {
      if(!this.leader.isEntityAlive()) {
         this.leader = null;
         EntityHumanBase newLeader = this.getMember(this.getNewLeaderFromParty());
         if(newLeader != null) {
            this.setLeader(newLeader);
         }
      }

   }

   protected int getNewLeaderFromParty() {
      int bestEntityIndex = 0;
      int maxLevel = -9999;

      for(int i = 0; i < this.getMembersLength(); ++i) {
         if(this.getMember(i) != null) {
            int currentLevel = this.getEntityLevel(this.getMember(i));
            if(currentLevel > maxLevel) {
               maxLevel = currentLevel;
               bestEntityIndex = i;
            }
         }
      }

      return bestEntityIndex;
   }

   public boolean tryToAddNewMember(EntityHumanBase newMember) {
      return this.tryToAddNewMember(newMember, false);
   }

   public boolean tryToAddNewMember(EntityHumanBase newMember, boolean replaceLeader) {
      if(!replaceLeader && this.isFull()) {
         return false;
      } else if(this.getLeader() == null) {
         this.setLeader(newMember);
         return true;
      } else if(newMember == this.getLeader()) {
         return false;
      } else {
         int newLevel;
         for(newLevel = 0; newLevel < this.getMembersLength(); ++newLevel) {
            if(newMember.isEntityEqual(this.getMember(newLevel))) {
               return false;
            }
         }

         if(newMember.party != null) {
            newMember.party.removeMember(newMember);
         }

         newLevel = this.getEntityLevel(newMember);
         int leaderLevel = this.getEntityLevel(this.leader);
         if(replaceLeader && newLevel > leaderLevel) {
            this.setLeader(newMember);
            return true;
         } else {
            int skipThisOne = this.getSuggestedPosition(newMember);
            if(this.tryAddToSlot(skipThisOne, newMember, newLevel)) {
               return true;
            } else {
               for(int i = 0; i < this.getMembersLength(); ++i) {
                  if(i != skipThisOne && this.tryAddToSlot(i, newMember, newLevel)) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   private boolean isFull() {
      for(int i = 0; i < this.getMembersLength(); ++i) {
         if(this.getMember(i) == null) {
            return false;
         }
      }

      return true;
   }

   private boolean hasMembers() {
      for(int i = 0; i < this.getMembersLength(); ++i) {
         if(this.getMember(i) != null) {
            return true;
         }
      }

      return false;
   }

   protected int getSuggestedPosition(EntityHumanBase e) {
      return !e.isHealer() && !e.isRanged()?(this.getMember(1) == null?1:(this.getMember(3) == null?3:(this.getMember(4) == null?4:(this.getMember(0) == null?0:(this.getMember(2) == null?2:(this.getMember(6) == null?6:(this.getMember(5) == null?5:(this.getMember(7) == null?7:7)))))))):(this.getMember(6) == null?6:(this.getMember(5) == null?5:(this.getMember(7) == null?7:(this.getMember(3) == null?3:(this.getMember(4) == null?4:6)))));
   }

   public int getAngleForSlot(int i) {
      switch(i) {
      case 0:
         return -45;
      case 1:
         return 0;
      case 2:
         return 45;
      case 3:
         return -90;
      case 4:
         return 90;
      case 5:
         return 135;
      case 6:
         return 180;
      case 7:
         return -135;
      default:
         return 0;
      }
   }

   public int getMembersLength() {
      return this.members.length;
   }

   protected void setMember(int index, EntityHumanBase newMember) {
      newMember.setInParty(this, this.getAngleForSlot(index), 2);
      this.members[index] = newMember;
   }

   public EntityHumanBase getMember(int i) {
      return this.members[i];
   }

   protected void removeMember(int i) {
      this.members[i].setOutOfParty();
      this.members[i] = null;
   }

   protected void setLeader(EntityHumanBase newLeader) {
      EntityHumanBase oldLeader = this.leader;
      this.leader = newLeader;
      this.leader.setInParty(this, 0, 2);
      newLeader.setCaptain(true);
      if(oldLeader != null) {
         oldLeader.setCaptain(false);
         oldLeader.setOutOfParty();
         this.tryToAddNewMember(oldLeader);
      }

   }

   public EntityHumanBase getLeader() {
      if(this.leader == null) {
         this.leader = this.getMember(this.getNewLeaderFromParty());
      }

      return this.leader;
   }

   protected boolean tryAddToSlot(int index, EntityHumanBase newMember, int newLevel) {
      EntityHumanBase current = this.getMember(index);
      if(current != null) {
         int memberLevel = this.getEntityLevel(current);
         if(newLevel > memberLevel) {
            current.setOutOfParty();
            this.setMember(index, newMember);
            this.tryToAddNewMember(current);
            return true;
         } else {
            return false;
         }
      } else {
         this.setMember(index, newMember);
         return true;
      }
   }

   public int getEntityLevel(EntityHumanBase entity) {
      int value = getItemValue(entity.leftHandItem);

      for(int i = 0; i < 5; ++i) {
         value += getItemValue(entity.getEquipmentInSlot(i));
      }

      value += entity.getLeadershipValue();
      return value;
   }

   public static int getItemValue(ItemStack itemstack) {
      if(itemstack == null) {
         return 0;
      } else {
         Item item = itemstack.getItem();
         return item == ChocolateQuest.staffHeal?-20:(item instanceof ItemArmor?((ItemArmor)item).getArmorMaterial().getDamageReductionAmount(2):(item instanceof ItemCQBlade?4:(item instanceof ItemSword?3:(item == ChocolateQuest.banner?10:0))));
      }
   }

   public String toString() {
      String name = "Leader: ";
      if(this.leader != null) {
         name = name + this.leader.getEntityId();
      } else {
         name = name + "null";
      }

      for(int i = 0; i < this.getMembersLength(); ++i) {
         name = name + ", " + i + ": ";
         if(this.getMember(i) != null) {
            name = name + this.getMember(i).getEntityId();
         } else {
            name = name + "null";
         }
      }

      return name;
   }

   public void saveToNBT(NBTTagCompound par1nbtTagCompound) {
      int x = MathHelper.floor_double(this.getLeader().posX);
      int y = MathHelper.floor_double(this.getLeader().posY);
      int z = MathHelper.floor_double(this.getLeader().posZ);
      NBTTagList list = new NBTTagList();

      for(int i = 0; i < this.getMembersLength(); ++i) {
         EntityHumanBase e = this.getMember(i);
         if(e != null && !e.isDead) {
            NBTTagCompound eTag = new NBTTagCompound();
            eTag.setString("id", EntityList.getEntityString(e));
            e.writeEntityToSpawnerNBT(eTag, x, y, z);
            list.appendTag(eTag);
         }
      }

      par1nbtTagCompound.setTag("Party", list);
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.partyTag = nbttagcompound;
   }

   private void loadFromNBT(NBTTagCompound nbttagcompound) {
      int x = MathHelper.floor_double(this.getLeader().posX);
      int y = MathHelper.floor_double(this.getLeader().posY);
      int z = MathHelper.floor_double(this.getLeader().posZ);
      NBTTagList list = nbttagcompound.getTagList("Party", nbttagcompound.getId());
      World world = this.getLeader().worldObj;

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound eTag = list.getCompoundTagAt(i);
         Entity e = EntityList.createEntityFromNBT(eTag, world);
         if(e instanceof EntityHumanBase) {
            EntityHumanBase human = (EntityHumanBase)EntityList.createEntityFromNBT(eTag, world);
            human.readEntityFromSpawnerNBT(eTag, x, y, z);
            human.setPosition(human.posX, human.posY, human.posZ);
            this.getLeader().tryPutIntoPArty(human);
            world.spawnEntityInWorld(human);
            if(eTag.getTag("Riding") != null) {
               NBTTagCompound ridingNBT = (NBTTagCompound)eTag.getTag("Riding");
               Entity riding = EntityList.createEntityFromNBT(ridingNBT, world);
               if(riding != null) {
                  if(riding instanceof EntityHumanBase) {
                     ((EntityHumanBase)riding).entityTeam = human.entityTeam;
                  }

                  riding.setPosition(human.posX, human.posY, human.posZ);
                  world.spawnEntityInWorld(riding);
                  human.mountEntity(riding);
               }
            }
         }
      }

   }
}
