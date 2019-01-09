package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.quest.DialogCondition;

public class DialogConditionList {

   private final Class conditionClass;
   public final String name;
   public final int conditionID;
   static int conditions = 0;


   public DialogConditionList(Class conditionClass, String name) {
      this.conditionClass = conditionClass;
      this.name = name;
      this.conditionID = conditions++;
   }

   public String toString() {
      return this.name;
   }

   public DialogCondition getNewInstance() {
      try {
         DialogCondition e = (DialogCondition)this.conditionClass.newInstance();
         e.setType(this.conditionID);
         return e;
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

}
