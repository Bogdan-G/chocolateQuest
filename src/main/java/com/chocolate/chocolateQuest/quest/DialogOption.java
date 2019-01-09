package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.DialogManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class DialogOption {

   public int id;
   public String folder;
   public String name;
   public String prompt;
   public String[] text;
   public DialogOption[] options;
   public List actions;
   public List conditions;


   public DialogOption() {
      this.folder = "Quest/lang/default";
      this.name = "";
      this.prompt = "";
   }

   public DialogOption(DialogOption[] options, String name) {
      this();
      this.options = options;
      this.name = name;
   }

   public void setID(int id) {
      this.id = id;
      if(this.options != null) {
         for(int i = 0; i < this.options.length; ++i) {
            this.options[i].setID(i);
         }
      }

   }

   public void execute(EntityPlayer player, EntityHumanNPC npc) {
      if(this.actions != null) {
         Iterator i$ = this.actions.iterator();

         while(i$.hasNext()) {
            DialogAction action = (DialogAction)i$.next();
            boolean shouldExecuteAction = true;
            if(action.conditions != null) {
               Iterator i$1 = action.conditions.iterator();

               while(i$1.hasNext()) {
                  DialogCondition condition = (DialogCondition)i$1.next();
                  if(!condition.matches(player, npc)) {
                     shouldExecuteAction = false;
                     break;
                  }
               }
            }

            if(shouldExecuteAction) {
               action.execute(player, npc);
            }
         }
      }

   }

   public void writeToNBT(NBTTagCompound tag) {
      tag.setString("Name", this.name);
      tag.setString("Folder", this.folder);
      NBTTagList list;
      if(this.options != null) {
         list = new NBTTagList();
         DialogOption[] i$ = this.options;
         int condition = i$.length;

         for(int conditionTag = 0; conditionTag < condition; ++conditionTag) {
            DialogOption option = i$[conditionTag];
            NBTTagCompound optionTag = new NBTTagCompound();
            option.writeToNBT(optionTag);
            list.appendTag(optionTag);
         }

         tag.setTag("Options", list);
      }

      Iterator var8;
      NBTTagCompound var11;
      if(this.actions != null) {
         list = new NBTTagList();
         var8 = this.actions.iterator();

         while(var8.hasNext()) {
            DialogAction var9 = (DialogAction)var8.next();
            var11 = new NBTTagCompound();
            var9.writeToNBT(var11);
            list.appendTag(var11);
         }

         tag.setTag("Actions", list);
      }

      if(this.conditions != null) {
         list = new NBTTagList();
         var8 = this.conditions.iterator();

         while(var8.hasNext()) {
            DialogCondition var10 = (DialogCondition)var8.next();
            var11 = new NBTTagCompound();
            var10.writeToNBT(var11);
            list.appendTag(var11);
         }

         tag.setTag("Conditions", list);
      }

      tag.setInteger("ID", this.id);
   }

   public void readFromNBT(NBTTagCompound tag) {
      this.name = tag.getString("Name");
      this.folder = tag.getString("Folder");
      NBTTagList list;
      int optionCount;
      int i;
      if(tag.hasKey("Options")) {
         list = (NBTTagList)tag.getTag("Options");
         optionCount = list.tagCount();
         this.options = new DialogOption[optionCount];

         for(i = 0; i < optionCount; ++i) {
            this.options[i] = new DialogOption();
            this.options[i].readFromNBT(list.getCompoundTagAt(i));
         }
      }

      if(tag.hasKey("Actions")) {
         list = (NBTTagList)tag.getTag("Actions");
         optionCount = list.tagCount();

         for(i = 0; i < optionCount; ++i) {
            this.addAction(DialogAction.getFromNBT(list.getCompoundTagAt(i)));
         }
      }

      if(tag.hasKey("Conditions")) {
         list = (NBTTagList)tag.getTag("Conditions");
         optionCount = list.tagCount();

         for(i = 0; i < optionCount; ++i) {
            this.addCondition(DialogCondition.getFromNBT(list.getCompoundTagAt(i)));
         }
      }

      this.id = tag.getInteger("ID");
   }

   public String toString() {
      return this.name;
   }

   public void addDialog(DialogOption newDialog) {
      if(this.options == null) {
         this.options = new DialogOption[]{newDialog};
      } else {
         DialogOption[] newOptions = new DialogOption[this.options.length + 1];

         for(int i = 0; i < this.options.length; ++i) {
            newOptions[i] = this.options[i];
         }

         newOptions[this.options.length] = newDialog;
         this.options = newOptions;
      }

   }

   public void removeDialog(DialogOption newDialog) {
      if(this.options != null) {
         for(int newOptions = 0; newOptions < this.options.length; ++newOptions) {
            if(newDialog == this.options[newOptions]) {
               this.options[newOptions] = null;
            }
         }

         DialogOption[] var5 = new DialogOption[this.options.length - 1];
         int cont = 0;

         for(int i = 0; i < this.options.length; ++i) {
            if(null != this.options[i]) {
               var5[cont] = this.options[i];
               ++cont;
            }
         }

         this.options = var5;
      }

   }

   public void addAction(DialogAction newDialog) {
      if(this.actions == null) {
         this.actions = new ArrayList();
      }

      this.actions.add(newDialog);
   }

   public void removeAction(DialogAction newDialog) {
      if(this.actions != null) {
         this.actions.remove(newDialog);
         if(this.actions.isEmpty()) {
            this.actions = null;
         }
      }

   }

   public void addCondition(DialogCondition newDialog) {
      if(this.conditions == null) {
         this.conditions = new ArrayList();
      }

      this.conditions.add(newDialog);
   }

   public void removeCondition(DialogCondition condition) {
      if(this.conditions != null) {
         this.conditions.remove(condition);
         if(this.conditions.isEmpty()) {
            this.conditions = null;
         }
      }

   }

   public void replaceKeys(String playerName, EntityHumanNPC npc) {
      for(int i = 0; i < this.text.length; ++i) {
         this.text[i] = this.replaceKeys(this.text[i], playerName, npc);
      }

      this.prompt = this.replaceKeys(this.prompt, playerName, npc);
   }

   public String replaceKeys(String s, String playerName, EntityHumanNPC npc) {
      if(s.contains("@sp")) {
         s = s.replaceAll("@sp", playerName);
      }

      if(s.contains("@name")) {
         s = s.replaceAll("@name", npc.getCommandSenderName());
      }

      return s;
   }

   public void readText(String lang) {
      DialogManager.readText(lang, this);
   }

   public String getDefaultFileName() {
      return BDHelper.getInfoDir() + this.folder + "/default/" + this.name + ".dialog";
   }

   public void saveText() {
      DialogManager.saveText(this);
   }

   public DialogOption copy() {
      NBTTagCompound tag = new NBTTagCompound();
      this.writeToNBT(tag);
      DialogOption option = new DialogOption();
      option.readFromNBT(tag);
      return option;
   }
}
