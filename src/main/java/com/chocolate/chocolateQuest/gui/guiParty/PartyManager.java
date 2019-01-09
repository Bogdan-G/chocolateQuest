package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import java.util.ArrayList;
import java.util.List;

public class PartyManager {

   public static PartyManager instance = new PartyManager();
   public List partyMembers = new ArrayList();
   public List buttons = new ArrayList();


   public boolean addMember(EntityHumanBase e) {
      if(this.countEntities() >= 10) {
         return false;
      } else {
         for(int i = 0; i < this.partyMembers.size(); ++i) {
            if(this.partyMembers.get(i) == e) {
               return true;
            }
         }

         this.partyMembers.add(e);
         return true;
      }
   }

   public boolean removeMember(EntityHumanBase e) {
      for(int i = 0; i < this.partyMembers.size(); ++i) {
         if(this.partyMembers.get(i) == e) {
            this.partyMembers.remove(i);
            if(this.buttons.size() > i) {
               this.buttons.remove(i);
            }
         }
      }

      return true;
   }

   public List getMembers() {
      for(int i = 0; i < this.partyMembers.size(); ++i) {
         EntityHumanBase human = (EntityHumanBase)this.partyMembers.get(i);
         if(human != null && human.isDead) {
            this.removeMember(human);
         }
      }

      return this.partyMembers;
   }

   public EntityHumanBase[] getEntitiesArray() {
      int count = this.countEntities();
      int current = 0;
      EntityHumanBase[] entities = new EntityHumanBase[count];

      for(int i = 0; i < this.partyMembers.size(); ++i) {
         EntityHumanBase e = (EntityHumanBase)this.partyMembers.get(i);
         if(e != null) {
            entities[current] = e;
            ++current;
         }
      }

      return entities;
   }

   public int countEntities() {
      int count = 0;

      for(int i = 0; i < this.partyMembers.size(); ++i) {
         if(this.partyMembers.get(i) != null) {
            ++count;
         }
      }

      return count;
   }

   public void setButton(int i, Object button) {
      this.buttons.add(i, button);
   }

   public Object getButton(int i) {
      return i >= this.buttons.size()?null:this.buttons.get(i);
   }

   public void restart() {
      this.partyMembers.clear();
      this.buttons.clear();
   }

}
