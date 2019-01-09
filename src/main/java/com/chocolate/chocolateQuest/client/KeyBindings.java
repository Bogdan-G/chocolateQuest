package com.chocolate.chocolateQuest.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {

   public static KeyBinding partyKey;
   public static KeyBinding nextSpell;
   public static KeyBinding prevSpell;


   public static void init() {
      partyKey = new KeyBinding("key.party", 33, "key.categories.chocolatequest");
      nextSpell = new KeyBinding("key.next", 45, "key.categories.chocolatequest");
      prevSpell = new KeyBinding("key.prev", 46, "key.categories.chocolatequest");
      ClientRegistry.registerKeyBinding(partyKey);
      ClientRegistry.registerKeyBinding(nextSpell);
      ClientRegistry.registerKeyBinding(prevSpell);
   }
}
