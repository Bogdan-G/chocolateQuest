package com.chocolate.chocolateQuest.quest;

import com.chocolate.chocolateQuest.quest.DialogOption;
import com.chocolate.chocolateQuest.quest.TextEntry;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DialogManager {

   static Map currentEntries;
   static String currentFile;
   static final String nameTag = "@name:";
   static final String promptTag = "@prompt:";
   static final String endTag = "#####";


   public static void readText(String lang, DialogOption option) {
      loadOptions(lang, option.folder);
      TextEntry entry = (TextEntry)currentEntries.get(option.name);
      if(entry == null) {
         entry = new TextEntry();
      }

      if(entry.text == null) {
         entry.text = new String[]{"(@name stares at you)"};
      }

      if(entry.name == null || entry.name.isEmpty()) {
         entry.name = option.name;
      }

      if(entry.prompt == null || entry.prompt.isEmpty()) {
         entry.prompt = option.name;
      }

      option.name = entry.name;
      option.prompt = entry.prompt;
      option.text = entry.text;
   }

   public static void loadOptions(String lang, String fileName) {
      if(currentFile != fileName) {
         currentFile = fileName;
         currentEntries = new HashMap();
         File file = new File(BDHelper.getAppDir(), getDefaultFileName(fileName));
         loadTagsFromFile(file);
         file = new File(BDHelper.getAppDir(), getDefaultFileNameWithLang(fileName, lang));
         loadTagsFromFile(file);
      }

   }

   public static String[] getOptionNames(String fileName) {
      loadOptions("en_UK", fileName);
      if(currentEntries != null && !currentEntries.isEmpty()) {
         String[] names = new String[currentEntries.size()];
         Object[] entries = currentEntries.values().toArray();

         for(int i = 0; i < names.length; ++i) {
            names[i] = ((TextEntry)entries[i]).name;
         }

         return names;
      } else {
         return null;
      }
   }

   protected static boolean loadTagsFromFile(File file) {
      try {
         if(!file.exists()) {
            return false;
         }

         BufferedReader e = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
         ArrayList list = new ArrayList();
         TextEntry currentEntry = null;

         String line;
         String[] text;
         while((line = e.readLine()) != null) {
            if(!line.startsWith("#")) {
               if(line.startsWith("@name:")) {
                  if(currentEntry != null) {
                     text = new String[list.size()];
                     list.toArray(text);
                     currentEntry.text = text;
                     currentEntries.put(currentEntry.name, currentEntry);
                     list.clear();
                  }

                  currentEntry = new TextEntry();
                  currentEntry.name = line.replace("@name:", "");
               } else if(line.startsWith("@prompt:") && currentEntry != null) {
                  currentEntry.prompt = line.replace("@prompt:", "");
               } else if(currentEntry != null) {
                  list.add(line);
               }
            }
         }

         if(currentEntry != null) {
            text = new String[list.size()];
            list.toArray(text);
            currentEntry.text = text;
            currentEntries.put(currentEntry.name, currentEntry);
         }

         e.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return true;
   }

   protected static String getDefaultFileName(String fileName) {
      return BDHelper.getInfoDir() + fileName + ".dialog";
   }

   protected static String getDefaultFileNameWithLang(String fileName, String lang) {
      return BDHelper.getInfoDir() + fileName + "." + lang + ".dialog";
   }

   public static void saveText(DialogOption option) {
      new ArrayList();

      try {
         File e = new File(BDHelper.getAppDir(), getDefaultFileName(option.folder));
         if(!e.exists()) {
            e.getParentFile().mkdirs();
            e.createNewFile();
         }

         TextEntry current = (TextEntry)currentEntries.get(option.name);
         if(current == null) {
            current = new TextEntry(option.name, option.prompt, option.text);
            currentEntries.put(option.name, current);
         } else {
            current.prompt = option.prompt;
            current.text = option.text;
         }

         FileWriter writer = new FileWriter(e);
         BufferedWriter stream = new BufferedWriter(writer);
         Iterator i$ = currentEntries.values().iterator();

         while(i$.hasNext()) {
            TextEntry entry = (TextEntry)i$.next();
            stream.write("@name:" + entry.name);
            stream.newLine();
            stream.write("@prompt:" + entry.prompt);
            stream.newLine();
            String[] arr$ = entry.text;
            int len$ = arr$.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
               String line = arr$[i$1];
               stream.write(line);
               stream.newLine();
            }

            stream.write("#####");
            stream.newLine();
         }

         stream.close();
      } catch (Exception var12) {
         var12.printStackTrace();
      }

   }
}
