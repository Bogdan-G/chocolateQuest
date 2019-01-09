package com.chocolate.chocolateQuest.config;

import com.chocolate.chocolateQuest.API.RegisterChestItem;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ConfigHelper {

   public static void readChests() {
      BDHelper.println("## Chest items register ##");
      getItems("Chocolate/ChestConfig/chests.prop", RegisterChestItem.chestList, "default");
      getItems("Chocolate/ChestConfig/treasures.prop", RegisterChestItem.treasureList, "treasure");
      getItems("Chocolate/ChestConfig/weapons.prop", RegisterChestItem.weaponList, "weapon");
      getItems("Chocolate/ChestConfig/ores.prop", RegisterChestItem.mineralList, "ores");
      getItems("Chocolate/ChestConfig/food.prop", RegisterChestItem.foodList, "food");
   }

   public static void getItems(String st, ArrayList list, String listName) {
      File file = new File(BDHelper.getAppDir(), st);

      try {
         Properties e = new Properties();
         FileReader fr = new FileReader(file);
         e.load(fr);
         Enumeration e1 = e.elements();
         int cont = 0;

         while(e1.hasMoreElements()) {
            String s = (String)e1.nextElement();
            StringTokenizer stkn = new StringTokenizer(s, ",");
            int tokens = stkn.countTokens();
            String name = ((String)stkn.nextElement()).trim();
            int numberOfItems = 1;
            int damageOfItems = 0;
            int itemsWeight = 100;
            if(tokens == 4) {
               numberOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
               damageOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
               itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
            }

            if(tokens == 3) {
               numberOfItems = Integer.parseInt(((String)stkn.nextElement()).trim());
               itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
            }

            if(tokens == 2) {
               itemsWeight = Integer.parseInt(((String)stkn.nextElement()).trim());
            }

            ++cont;
            FMLLog.getLogger().info("Added " + name + " to " + listName + " chests list");
            Object item = Item.itemRegistry.getObject(name);
            if(item instanceof Item) {
               ItemStack is = new ItemStack((Item)item, numberOfItems, damageOfItems);
               RegisterChestItem.addChestItem(is, itemsWeight, list);
            } else {
               BDHelper.println("Error loading item: " + s + " into " + listName + " chests list");
            }
         }
      } catch (FileNotFoundException var17) {
         FMLLog.getLogger().error("Not found config file at Chocolate Quest mod: " + file.getAbsolutePath());
         var17.printStackTrace();
      } catch (IOException var18) {
         FMLLog.getLogger().error("Error reading config file at Chocolate Quest mod: " + file.getAbsolutePath());
         var18.printStackTrace();
      }

   }
}
