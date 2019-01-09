package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.quest.DialogAction;

public class DialogActionList {

   private final Class dialogClass;
   public final String name;
   public final int actionID;
   static int actions = 0;


   public DialogActionList(Class dialogClass, String name) {
      this.dialogClass = dialogClass;
      this.name = name;
      this.actionID = actions++;
   }

   public String toString() {
      return this.name;
   }

   public DialogAction getNewInstance() {
      try {
         DialogAction e = (DialogAction)this.dialogClass.newInstance();
         e.setType(this.actionID);
         return e;
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

}
