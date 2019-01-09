package com.chocolate.chocolateQuest.quest;


class TextEntry {

   public String name;
   public String prompt;
   public String[] text;


   public TextEntry() {}

   public TextEntry(String name, String prompt, String[] text) {
      this.name = name;
      this.prompt = prompt;
      this.text = text;
   }
}
