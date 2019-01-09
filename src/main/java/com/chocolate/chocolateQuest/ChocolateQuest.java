package com.chocolate.chocolateQuest;

import com.chocolate.chocolateQuest.CommonProxy;
import com.chocolate.chocolateQuest.EventHandlerCQ;
import com.chocolate.chocolateQuest.WorldGeneratorNew;
import com.chocolate.chocolateQuest.API.DungeonBase;
import com.chocolate.chocolateQuest.API.DungeonRegister;
import com.chocolate.chocolateQuest.API.RegisterDungeonBuilder;
import com.chocolate.chocolateQuest.block.BlockAltar;
import com.chocolate.chocolateQuest.block.BlockArmorStand;
import com.chocolate.chocolateQuest.block.BlockBannerStand;
import com.chocolate.chocolateQuest.block.BlockDecoration;
import com.chocolate.chocolateQuest.block.BlockDungeonBrick;
import com.chocolate.chocolateQuest.block.BlockEditor;
import com.chocolate.chocolateQuest.block.BlockEditorVoid;
import com.chocolate.chocolateQuest.block.BlockMobSpawner;
import com.chocolate.chocolateQuest.block.ItemBlockDungeonBrick;
import com.chocolate.chocolateQuest.builder.BuilderCastle;
import com.chocolate.chocolateQuest.builder.BuilderCavern;
import com.chocolate.chocolateQuest.builder.BuilderEmptyCave;
import com.chocolate.chocolateQuest.builder.BuilderNether;
import com.chocolate.chocolateQuest.builder.BuilderStronghold;
import com.chocolate.chocolateQuest.builder.BuilderTemplate;
import com.chocolate.chocolateQuest.builder.BuilderTemplateSurface;
import com.chocolate.chocolateQuest.command.CommandAwakeEquipement;
import com.chocolate.chocolateQuest.command.CommandItemElement;
import com.chocolate.chocolateQuest.command.CommandKillCounter;
import com.chocolate.chocolateQuest.command.CommandKillCounterDelete;
import com.chocolate.chocolateQuest.command.CommandReputation;
import com.chocolate.chocolateQuest.command.CommandSpawnBoss;
import com.chocolate.chocolateQuest.config.ConfigHelper;
import com.chocolate.chocolateQuest.config.Configurations;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import com.chocolate.chocolateQuest.entity.EntityPortal;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.entity.EntitySchematicBuilder;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.entity.EntityTracker;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntityPartRidable;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimePart;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtlePart;
import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanGremlin;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMinotaur;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMummy;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.EntitySpaceWarrior;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.MobDefault;
import com.chocolate.chocolateQuest.entity.mob.registry.MobGremlin;
import com.chocolate.chocolateQuest.entity.mob.registry.MobMinotaur;
import com.chocolate.chocolateQuest.entity.mob.registry.MobMummy;
import com.chocolate.chocolateQuest.entity.mob.registry.MobPirate;
import com.chocolate.chocolateQuest.entity.mob.registry.MobSkeleton;
import com.chocolate.chocolateQuest.entity.mob.registry.MobSpecter;
import com.chocolate.chocolateQuest.entity.mob.registry.MobWalker;
import com.chocolate.chocolateQuest.entity.mob.registry.MobZombie;
import com.chocolate.chocolateQuest.entity.mob.registry.MobZombiePig;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanDummy;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.items.ItemAlchemistBag;
import com.chocolate.chocolateQuest.items.ItemArmorBackpack;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.items.ItemArmorBootsCloud;
import com.chocolate.chocolateQuest.items.ItemArmorBull;
import com.chocolate.chocolateQuest.items.ItemArmorColored;
import com.chocolate.chocolateQuest.items.ItemArmorHeavy;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetDragon;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetScouter;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetWitch;
import com.chocolate.chocolateQuest.items.ItemArmorKing;
import com.chocolate.chocolateQuest.items.ItemArmorMage;
import com.chocolate.chocolateQuest.items.ItemArmorRobe;
import com.chocolate.chocolateQuest.items.ItemArmorSlime;
import com.chocolate.chocolateQuest.items.ItemArmorSpider;
import com.chocolate.chocolateQuest.items.ItemArmorTurtle;
import com.chocolate.chocolateQuest.items.ItemBanner;
import com.chocolate.chocolateQuest.items.ItemCursedBone;
import com.chocolate.chocolateQuest.items.ItemDungeonCrystal;
import com.chocolate.chocolateQuest.items.ItemEggBD;
import com.chocolate.chocolateQuest.items.ItemElementStone;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.items.ItemHookShootSpider;
import com.chocolate.chocolateQuest.items.ItemMedal;
import com.chocolate.chocolateQuest.items.ItemMulti;
import com.chocolate.chocolateQuest.items.ItemPickaxeMagic;
import com.chocolate.chocolateQuest.items.ItemPotionHeal;
import com.chocolate.chocolateQuest.items.ItemShied;
import com.chocolate.chocolateQuest.items.ItemSoulBottle;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.items.ItemTeleportStone;
import com.chocolate.chocolateQuest.items.gun.ItemAmmoLoader;
import com.chocolate.chocolateQuest.items.gun.ItemGolem;
import com.chocolate.chocolateQuest.items.gun.ItemGolemCannon;
import com.chocolate.chocolateQuest.items.gun.ItemGolemFramethrower;
import com.chocolate.chocolateQuest.items.gun.ItemGolemHeavy;
import com.chocolate.chocolateQuest.items.gun.ItemGolemMachineGun;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.items.gun.ItemMusket;
import com.chocolate.chocolateQuest.items.gun.ItemPistol;
import com.chocolate.chocolateQuest.items.gun.ItemShotGun;
import com.chocolate.chocolateQuest.items.gun.ItemWaterPump;
import com.chocolate.chocolateQuest.items.mobControl.ItemController;
import com.chocolate.chocolateQuest.items.mobControl.ItemMobToSpawner;
import com.chocolate.chocolateQuest.items.mobControl.ItemPathMarker;
import com.chocolate.chocolateQuest.items.mobControl.ItemPositionMarker;
import com.chocolate.chocolateQuest.items.swords.ItemBaseAxe;
import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.items.swords.ItemBigSwordArea;
import com.chocolate.chocolateQuest.items.swords.ItemDaggerEnd;
import com.chocolate.chocolateQuest.items.swords.ItemDaggerNinja;
import com.chocolate.chocolateQuest.items.swords.ItemHookSword;
import com.chocolate.chocolateQuest.items.swords.ItemSpearFire;
import com.chocolate.chocolateQuest.items.swords.ItemSpearGun;
import com.chocolate.chocolateQuest.items.swords.ItemSpearSpider;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShield;
import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import com.chocolate.chocolateQuest.items.swords.ItemSwordEffect;
import com.chocolate.chocolateQuest.items.swords.ItemSwordTurtle;
import com.chocolate.chocolateQuest.items.swords.ItemSwordWalker;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.EnchantmentMagicDefense;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.misc.DungeonsItemsTab;
import com.chocolate.chocolateQuest.misc.PotionCQ;
import com.chocolate.chocolateQuest.packets.ChannelHandler;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(
   modid = "chocolateQuest",
   version = "1.0",
   name = "Chocolate Quest"
)
public class ChocolateQuest {

   public static final String MODID = "chocolateQuest";
   public static final String VERSION = "1.0";
   @SidedProxy(
      clientSide = "com.chocolate.chocolateQuest.client.ClientProxy",
      serverSide = "com.chocolate.chocolateQuest.CommonProxy"
   )
   public static CommonProxy proxy;
   @Instance("chocolateQuest")
   public static ChocolateQuest instance;
   public static ChannelHandler channel;
   public static Configurations config;
   public static DungeonsItemsTab tabItems = new DungeonsItemsTab("Items");
   public static DungeonMonstersBase defaultMob;
   public static DungeonMonstersBase skeleton;
   public static DungeonMonstersBase zombie;
   public static DungeonMonstersBase specter;
   public static DungeonMonstersBase gremlin;
   public static DungeonMonstersBase walker;
   public static DungeonMonstersBase pirate;
   public static DungeonMonstersBase pigZombie;
   public static DungeonMonstersBase minotaur;
   public static DungeonMonstersBase spaceWarrior;
   public static DungeonMonstersBase mummy;
   public static Block table;
   public static Block armorStand;
   public static Block bannerStand;
   public static Block dungeonBrick;
   public static Block spawner;
   public static Block exporter;
   public static Block emptyBlock;
   public static Block exporterChest;
   public static final Enchantment enchantmentMagicDefense = new EnchantmentMagicDefense(52, 1);
   public static Item egg = (new ItemEggBD()).setUnlocalizedName("egg").setCreativeTab(tabItems);
   public static Item mobToSpawner = (new ItemMobToSpawner()).setUnlocalizedName("mobToSpawner").setCreativeTab(tabItems);
   public static Item magicPickaxe = (new ItemPickaxeMagic()).setUnlocalizedName("pickaxeMagic").setCreativeTab(tabItems);
   public static Item swordMoonLight = (new ItemSwordEffect(ToolMaterial.EMERALD, "swordMoonLight")).setShieldId(14).setUnlocalizedName("moonSword").setCreativeTab(tabItems);
   public static Item swordSunLight = (new ItemSwordEffect(ToolMaterial.EMERALD, "swordDefensive")).setShieldId(13).setUnlocalizedName("defenseSword").setCreativeTab(tabItems);
   public static Item swordTurtle = (new ItemSwordTurtle(ToolMaterial.EMERALD, "swordTurtle")).setShieldId(15).setUnlocalizedName("swordTurtle").setCreativeTab(tabItems);
   public static Item swordSpider = (new ItemSwordEffect(ToolMaterial.IRON, "swordSpider", Potion.poison.id)).setShieldId(18).setUnlocalizedName("swordSpider").setCreativeTab(tabItems);
   public static Item endSword = (new ItemSwordWalker()).setUnlocalizedName("walkerSword").setCreativeTab(tabItems);
   public static Item tricksterDagger = (new ItemDaggerNinja()).setUnlocalizedName("pirateDagger").setCreativeTab(tabItems);
   public static Item ninjaDagger = (new ItemDaggerEnd()).setUnlocalizedName("ninjaDagger").setCreativeTab(tabItems);
   public static Item fireSpear = (new ItemSpearFire()).setUnlocalizedName("dwarfSpear").setCreativeTab(tabItems);
   public static Item bigSwordBull = (new ItemBigSwordArea(ToolMaterial.EMERALD, "bigSwordBull", 6.0F)).setUnlocalizedName("bigSwordBull").setCreativeTab(tabItems);
   public static Item monkingSword = (new ItemBaseBroadSword(ToolMaterial.EMERALD, "swordMonking", 7.0F)).setUnlocalizedName("swordMonking").setCreativeTab(tabItems);
   public static Item monkingDagger = (new ItemBaseDagger(ToolMaterial.EMERALD, "daggerMonking", 4, 1.2F)).setCreativeTab(tabItems).setUnlocalizedName("daggerMonking");
   public static Item monkingSwordAndShield = (new ItemSwordAndShieldBase(ToolMaterial.EMERALD, "swordShiedMonking", 5, 1.2F)).setShieldId(17).setCreativeTab(tabItems).setUnlocalizedName("swordShiedMonking").setMaxDamage(1988);
   public static Item hookSword = (new ItemHookSword(ToolMaterial.EMERALD)).setUnlocalizedName("hookSword").setCreativeTab(tabItems);
   public static Item banner = (new ItemBanner()).setUnlocalizedName("banner").setCreativeTab(tabItems);
   public static Item shield = (new ItemShied()).setUnlocalizedName("shield").setCreativeTab(tabItems);
   public static Item bullAxe = (new ItemBaseAxe(ToolMaterial.EMERALD, "battle_axe_bull", 4, 1.2F)).setCreativeTab(tabItems).setUnlocalizedName("battle_axe_bull");
   public static Item rustedDagger = (new ItemBaseDagger(ToolMaterial.GOLD, "rustedDagger", 1, 3.0F)).setCreativeTab(tabItems).setUnlocalizedName("rustedDagger").setMaxDamage(1988);
   public static Item rustedSpear = (new ItemBaseSpear(ToolMaterial.GOLD, "rustedSpear", 1, 2.5F)).setCreativeTab(tabItems).setUnlocalizedName("rustedSpear").setMaxDamage(1988);
   public static Item rustedBigSword = (new ItemBaseBroadSword(ToolMaterial.GOLD, "rustedBigSword", 2.0F, 4.3F)).setCreativeTab(tabItems).setUnlocalizedName("rustedBigSword").setMaxDamage(1988);
   public static Item rustedSwordAndShied = (new ItemSwordAndShieldBase(ToolMaterial.GOLD, "rustedSword", 1, 3.0F)).setShieldId(16).setCreativeTab(tabItems).setUnlocalizedName("rustedSwordAndShied").setMaxDamage(1988);
   public static Item dragonHelmet = (new ItemArmorHelmetDragon()).setEpic().setUnlocalizedName("dragonHelmet").setCreativeTab(tabItems);
   public static Item scouter = (new ItemArmorHelmetScouter()).setEpic().setUnlocalizedName("scouter").setCreativeTab(tabItems);
   public static Item witchHat = (new ItemArmorHelmetWitch()).setEpic().setUnlocalizedName("witchHat").setCreativeTab(tabItems);
   public static Item cloudBoots = (new ItemArmorBootsCloud()).setEpic().setUnlocalizedName("cloudBoots").setCreativeTab(tabItems);
   public static Item kingArmor = (new ItemArmorKing()).setEpic().setUnlocalizedName("kingArmor").setCreativeTab(tabItems);
   public static Item slimeHelmet = (new ItemArmorSlime(0, "slimeHelmet")).setUnlocalizedName("slimeHelmet").setCreativeTab(tabItems);
   public static Item slimePlate = (new ItemArmorSlime(1, "slimePlate")).setUnlocalizedName("slimePlate").setCreativeTab(tabItems);
   public static Item slimePants = (new ItemArmorSlime(2, "slimePants")).setUnlocalizedName("slimePants").setCreativeTab(tabItems);
   public static Item slimeBoots = (new ItemArmorSlime(3, "slimeBoots")).setUnlocalizedName("slimeBoots").setCreativeTab(tabItems);
   public static Item turtleHelmet = (new ItemArmorTurtle(0, "turtleHelmet")).setUnlocalizedName("turtleHelmet").setCreativeTab(tabItems);
   public static Item turtlePlate = (new ItemArmorTurtle(1, "turtlePlate")).setUnlocalizedName("turtlePlate").setCreativeTab(tabItems);
   public static Item turtlePants = (new ItemArmorTurtle(2, "turtlePants")).setUnlocalizedName("turtlePants").setCreativeTab(tabItems);
   public static Item turtleBoots = (new ItemArmorTurtle(3, "turtleBoots")).setUnlocalizedName("turtleBoots").setCreativeTab(tabItems);
   public static Item bullHelmet = (new ItemArmorBull(0, "bullHelmet")).setUnlocalizedName("bullHelmet").setCreativeTab(tabItems);
   public static Item bullPlate = (new ItemArmorBull(1, "bullPlate")).setUnlocalizedName("bullPlate").setCreativeTab(tabItems);
   public static Item bullPants = (new ItemArmorBull(2, "bullPants")).setUnlocalizedName("bullPants").setCreativeTab(tabItems);
   public static Item bullBoots = (new ItemArmorBull(3, "bullBoots")).setUnlocalizedName("bullBoots").setCreativeTab(tabItems);
   public static Item spiderHelmet = (new ItemArmorSpider(0, "spiderHelmet")).setUnlocalizedName("spiderHelmet").setCreativeTab(tabItems);
   public static Item spiderPlate = (new ItemArmorSpider(1, "spiderPlate")).setUnlocalizedName("spiderPlate").setCreativeTab(tabItems);
   public static Item spiderPants = (new ItemArmorSpider(2, "spiderPants")).setUnlocalizedName("spiderPants").setCreativeTab(tabItems);
   public static Item spiderBoots = (new ItemArmorSpider(3, "spiderBoots")).setUnlocalizedName("spiderBoots").setCreativeTab(tabItems);
   public static Item diamondHeavyHelmet = (new ItemArmorHeavy(0, "armorHeavyDiamond", Items.diamond)).setUnlocalizedName("diamondHeavyHelmet").setCreativeTab(tabItems);
   public static Item diamondHeavyPlate = (new ItemArmorHeavy(1, "armorHeavyDiamond", Items.diamond)).setUnlocalizedName("diamondHeavyPlate").setCreativeTab(tabItems);
   public static Item diamondHeavyPants = (new ItemArmorHeavy(2, "armorHeavyDiamond", Items.diamond)).setUnlocalizedName("diamondHeavyPants").setCreativeTab(tabItems);
   public static Item diamondHeavyBoots = (new ItemArmorHeavy(3, "armorHeavyDiamond", Items.diamond)).setUnlocalizedName("diamondHeavyBoots").setCreativeTab(tabItems);
   public static Item ironHeavyHelmet = (new ItemArmorHeavy(0, "armorHeavyIron", Items.iron_ingot, ArmorMaterial.DIAMOND)).setUnlocalizedName("ironHeavyHelmet").setCreativeTab(tabItems);
   public static Item ironHeavyPlate = (new ItemArmorHeavy(1, "armorHeavyIron", Items.iron_ingot, ArmorMaterial.DIAMOND)).setUnlocalizedName("ironHeavyPlate").setCreativeTab(tabItems);
   public static Item ironHeavyPants = (new ItemArmorHeavy(2, "armorHeavyIron", Items.iron_ingot, ArmorMaterial.DIAMOND)).setUnlocalizedName("ironHeavyPants").setCreativeTab(tabItems);
   public static Item ironHeavyBoots = (new ItemArmorHeavy(3, "armorHeavyIron", Items.iron_ingot, ArmorMaterial.DIAMOND)).setUnlocalizedName("ironHeavyBoots").setCreativeTab(tabItems);
   public static Item inquisitionHelmet = (new ItemArmorBase(ArmorMaterial.DIAMOND, 0, "armorInquisition")).setUnlocalizedName("inquisitionHelmet").setCreativeTab(tabItems);
   public static Item inquisitionPlate = (new ItemArmorBase(ArmorMaterial.DIAMOND, 1, "armorInquisition")).setUnlocalizedName("inquisitionPlate").setCreativeTab(tabItems);
   public static Item inquisitionPants = (new ItemArmorBase(ArmorMaterial.DIAMOND, 2, "armorInquisition")).setUnlocalizedName("inquisitionPants").setCreativeTab(tabItems);
   public static Item inquisitionBoots = (new ItemArmorBase(ArmorMaterial.DIAMOND, 3, "armorInquisition")).setUnlocalizedName("inquisitionBoots").setCreativeTab(tabItems);
   public static Item diamondHelmet = (new ItemArmorColored(ArmorMaterial.DIAMOND, 0, "diamond", '\uffff')).setUnlocalizedName("diamondColoredHelmet").setCreativeTab(tabItems);
   public static Item diamondPlate = (new ItemArmorColored(ArmorMaterial.DIAMOND, 1, "diamond", '\uffff')).setUnlocalizedName("diamondColoredPlate").setCreativeTab(tabItems);
   public static Item diamondPants = (new ItemArmorColored(ArmorMaterial.DIAMOND, 2, "diamond", '\uffff')).setUnlocalizedName("diamondColoredPants").setCreativeTab(tabItems);
   public static Item diamondBoots = (new ItemArmorColored(ArmorMaterial.DIAMOND, 3, "diamond", '\uffff')).setUnlocalizedName("diamondColoredBoots").setCreativeTab(tabItems);
   public static Item ironHelmet = (new ItemArmorColored(ArmorMaterial.IRON, 0, "iron", 13421772)).setUnlocalizedName("ironColoredHelmet").setCreativeTab(tabItems);
   public static Item ironPlate = (new ItemArmorColored(ArmorMaterial.IRON, 1, "iron", 13421772)).setUnlocalizedName("ironColoredPlate").setCreativeTab(tabItems);
   public static Item ironPants = (new ItemArmorColored(ArmorMaterial.IRON, 2, "iron", 13421772)).setUnlocalizedName("ironColoredPants").setCreativeTab(tabItems);
   public static Item ironBoots = (new ItemArmorColored(ArmorMaterial.IRON, 3, "iron", 13421772)).setUnlocalizedName("ironColoredBoots").setCreativeTab(tabItems);
   public static Item cursedBone = (new ItemCursedBone()).setUnlocalizedName("cursedBone").setCreativeTab(tabItems);
   public static Item armorMage = (new ItemArmorRobe()).setUnlocalizedName("mageRobe").setCreativeTab(tabItems);
   public static Item staffHeal = (new ItemStaffHeal()).setUnlocalizedName("staffLife").setCreativeTab(tabItems);
   public static Item staffPhysic = (new ItemStaffBase(Elements.physic)).setUnlocalizedName("staffPhysic").setCreativeTab(tabItems);
   public static Item staffMagic = (new ItemStaffBase(Elements.magic)).setUnlocalizedName("staffMagic").setCreativeTab(tabItems);
   public static Item staffBlast = (new ItemStaffBase(Elements.blast)).setUnlocalizedName("staffBlast").setCreativeTab(tabItems);
   public static Item staffFire = (new ItemStaffBase(Elements.fire)).setUnlocalizedName("staffFire").setCreativeTab(tabItems);
   public static Item staffLight = (new ItemStaffBase(Elements.light)).setUnlocalizedName("staffLight").setCreativeTab(tabItems);
   public static Item spell = (new ItemMulti(SpellBase.getNames(), "spell")).setUnlocalizedName("spell").setCreativeTab(tabItems);
   public static Item ironSwordAndShield = (new ItemSwordAndShield(ToolMaterial.IRON)).setCreativeTab(tabItems).setUnlocalizedName("ironSwordAndShield");
   public static Item diamondSwordAndShield = (new ItemSwordAndShield(ToolMaterial.EMERALD)).setCreativeTab(tabItems).setUnlocalizedName("diamondSwordAndShield");
   public static Item ironDagger = (new ItemBaseDagger(ToolMaterial.IRON, "daggerIron")).setCreativeTab(tabItems).setUnlocalizedName("daggerIron");
   public static Item diamondDagger = (new ItemBaseDagger(ToolMaterial.EMERALD, "daggerDiamond")).setCreativeTab(tabItems).setUnlocalizedName("daggerDiamond");
   public static Item ironSpear = (new ItemBaseSpear(ToolMaterial.IRON, "spearIron")).setCreativeTab(tabItems).setUnlocalizedName("spearIron");
   public static Item diamondSpear = (new ItemBaseSpear(ToolMaterial.EMERALD, "spearDiamond")).setCreativeTab(tabItems).setUnlocalizedName("spearDiamond");
   public static Item ironBigsword = (new ItemBaseBroadSword(ToolMaterial.IRON, "bigSwordIron")).setCreativeTab(tabItems).setUnlocalizedName("bigswordIron");
   public static Item diamondBigsword = (new ItemBaseBroadSword(ToolMaterial.EMERALD, "bigSwordDiamond")).setCreativeTab(tabItems).setUnlocalizedName("bigswordDiamond");
   public static Item revolver = (new ItemPistol()).setUnlocalizedName("revolver").setCreativeTab(tabItems);
   public static Item spearGun = (new ItemSpearGun()).setUnlocalizedName("spearGun").setCreativeTab(tabItems);
   public static Item golem = (new ItemGolem("mechaGolem")).setUnlocalizedName("mecha").setCreativeTab(tabItems);
   public static Item golemHeavy = (new ItemGolemHeavy()).setUnlocalizedName("mechaHeavy").setCreativeTab(tabItems);
   public static Item golemUpgrade = (new ItemMulti(new String[]{"golemArmor", "golemField", "golemShield", "golemRockets", "golemAutoRepair"}, "golemUpgrade")).setUnlocalizedName("golemUpgrade").setCreativeTab(tabItems);
   public static Item ammoLoader = (new ItemAmmoLoader()).setUnlocalizedName("ammoLoader").setCreativeTab(tabItems);
   public static Item golemGun = (new ItemGolemWeapon(30, 16.0F, 50.0F, 1.0F, 8)).setUnlocalizedName("golemGun").setCreativeTab(tabItems);
   public static Item golemFlameThrower = (new ItemGolemFramethrower()).setUnlocalizedName("golemFlameThrower").setCreativeTab(tabItems);
   public static Item golemRifleGun = (new ItemGolemWeapon(60, 64.0F, 10.0F, 1.0F, 2)).setUnlocalizedName("golemRailGun").setCreativeTab(tabItems);
   public static Item golemMachineGun = (new ItemGolemMachineGun()).setUnlocalizedName("golemMachineGun").setCreativeTab(tabItems);
   public static Item golemCannon = (new ItemGolemCannon(80, 16.0F, 30.0F)).setUnlocalizedName("golemCannon").setCreativeTab(tabItems);
   public static Item golemBubbleCannon = (new ItemWaterPump(30, 16.0F, 3.0F)).setUnlocalizedName("golemBubbleCannon").setCreativeTab(tabItems);
   public static Item hookShoot = (new ItemHookShoot(0, "chocolatequest:hookShoot")).setUnlocalizedName("hookshoot").setCreativeTab(tabItems);
   public static Item longShoot = (new ItemHookShoot(1, "chocolatequest:hookLong")).setUnlocalizedName("longshoot").setCreativeTab(tabItems);
   public static Item manualShoot = (new ItemHookShoot(2, "chocolatequest:hookManual")).setUnlocalizedName("manualshoot").setCreativeTab(tabItems);
   public static Item spiderHook = (new ItemHookShootSpider(3)).setUnlocalizedName("spiderhook").setCreativeTab(tabItems);
   public static Item bullet = (new ItemMulti(new String[]{"ironBullet", "goldBullet", "magicBullet", "fireBullet", "cannonBullet"}, "bullets")).setUnlocalizedName("bullet").setCreativeTab(tabItems);
   public static Item material = (new ItemMulti(new String[]{"hammer", "turtleScale", "bullLeather", "slimeBall", "spiderLeather", "monkingBone", "spiderPoison", "bullHorn"}, "mat")).setUnlocalizedName("material").setCreativeTab(tabItems);
   public static Item alchemistBag = (new ItemAlchemistBag()).setUnlocalizedName("potionsBag").setCreativeTab(tabItems);
   public static Item potion = (new ItemPotionHeal()).setUnlocalizedName("healingPotion").setCreativeTab(tabItems);
   public static Item pathMarker = (new ItemPathMarker()).setUnlocalizedName("pathMarker").setTextureName("chocolatequest:pathMap").setCreativeTab(tabItems);
   public static Item controller = (new ItemController()).setUnlocalizedName("controller").setTextureName("chocolateQuest:i0").setCreativeTab(tabItems);
   public static ItemPositionMarker pointMarker = (ItemPositionMarker)(new ItemPositionMarker()).setUnlocalizedName("controllerTeam").setTextureName("chocolateQuest:partyEditor").setCreativeTab(tabItems);
   public static Item soulBottle = (new ItemSoulBottle()).setUnlocalizedName("soulBottle").setCreativeTab(tabItems).setTextureName("chocolateQuest:soulBottle");
   public static Item medal = (new ItemMedal()).setUnlocalizedName("badge").setCreativeTab(tabItems).setTextureName("chocolateQuest:badge");
   public static ItemMusket musket = (ItemMusket)(new ItemMusket()).setUnlocalizedName("musket").setCreativeTab(tabItems);
   public static Item golemShotgun = (new ItemShotGun(20, 6.0F, 250.0F, 0.25F, 4, 6)).setUnlocalizedName("shotgun").setCreativeTab(tabItems);
   public static Item teleportStone = (new ItemTeleportStone()).setUnlocalizedName("teleportStone").setCreativeTab(tabItems);
   public static Item spawnDisruptor = (new ItemDungeonCrystal()).setUnlocalizedName("miningDisruptor").setTextureName("chocolateQuest:miningDisruptor").setCreativeTab(tabItems);
   public static Item spiderSpear = (new ItemSpearSpider()).setUnlocalizedName("spearSpider").setCreativeTab(tabItems);
   public static ItemElementStone elementStone = (ItemElementStone)(new ItemElementStone()).setUnlocalizedName("elementStone").setCreativeTab(tabItems);
   public static ItemArmorBackpack backpack = (ItemArmorBackpack)(new ItemArmorBackpack()).setUnlocalizedName("backpack").setCreativeTab(tabItems);
   public static Item mageArmorHelmet = (new ItemArmorMage(ItemArmorBase.MAGE_ROBE, 0, 20, 25)).setUnlocalizedName("mageHood").setCreativeTab(tabItems);
   public static Item mageArmorPlate = (new ItemArmorMage(ItemArmorBase.MAGE_ROBE, 1, 35, 30)).setUnlocalizedName("mageJacket").setCreativeTab(tabItems);
   public static Item mageArmorPants = (new ItemArmorMage(ItemArmorBase.MAGE_ROBE, 2, 10, 10)).setUnlocalizedName("mageSkirt").setCreativeTab(tabItems);
   public static Item mageArmorBoots = (new ItemArmorMage(ItemArmorBase.MAGE_ROBE, 3, 10, 10)).setUnlocalizedName("mageBoots").setCreativeTab(tabItems);
   public static final int GUI_MOB = 0;
   public static final int GUI_EDITOR = 1;
   public static final int GUI_ARMORSTAND = 2;
   public static final int GUI_GUN = 3;
   public static final int GUI_CHEST = 4;
   public static final int GUI_MERCHANT = 5;
   public static final int GUI_NPC = 6;
   public static final int GUI_AWAKEMENT = 7;
   public static final int GUI_CONTROLLER = 8;
   public static final int GUI_PARTY = 9;
   public static final int GUI_MEDAL = 10;


   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
      config = new Configurations();
      config.load(configFile);
      GameRegistry.registerWorldGenerator(new WorldGeneratorNew(), 100);
      MinecraftForge.EVENT_BUS.register(new EventHandlerCQ());
      this.registerBlocks();
      this.registerItems();
      this.registerEntities();
      PotionCQ.registerPotions(event);
      proxy.register();
      channel = new ChannelHandler();
      ChannelHandler.init();
      if(config.updateData) {
         this.unpackMod();
      }

   }

   public void unpackMod() {
      String path = "assets/chocolatequest/Chocolate";
      Class<ChocolateQuest> clazz = ChocolateQuest.class;
      URL dirURL = clazz.getResource(path);
      String e;
      if(dirURL == null) {
         e = clazz.getName().replace(".", "/") + ".class";
         dirURL = clazz.getClassLoader().getResource(e);
      }

      if(dirURL.getProtocol().equals("jar")) {
         try {
            e = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            JarFile jar = new JarFile(URLDecoder.decode(e, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries();

            while(entries.hasMoreElements()) {
               JarEntry entry = entries.nextElement();
               String name = entry.getName();
               if(name.startsWith(path)) {
                  String entryName = name.substring(path.length());
                  File f = new File(BDHelper.getChocolateDir() + entryName);
                  if(f.exists()) {
                     BDHelper.println(f + " " + new Date(entry.getTime()) + " " + new Date(f.lastModified()));
                  } else {
                     if(entry.isDirectory()) {
                        f.mkdirs();
                     } else {
                        FileOutputStream fileoutputstream = new FileOutputStream(f);
                        InputStream zipinputstream = jar.getInputStream(entry);
                        byte[] buf = new byte[1024];

                        int n;
                        while((n = zipinputstream.read(buf, 0, 1024)) > -1) {
                           fileoutputstream.write(buf, 0, n);
                        }

                        fileoutputstream.close();
                        zipinputstream.close();
                     }

                     BDHelper.println("Extracted: " + f);
                  }
               }
            }
         } catch (UnsupportedEncodingException var15) {
            var15.printStackTrace();
         } catch (IOException var16) {
            var16.printStackTrace();
         }
      }

   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit();
      ConfigHelper.readChests();
   }

   @EventHandler
   public void load(FMLInitializationEvent evt) {
      NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
      this.registerDungeons();
      ((ItemEggBD)egg).init();
   }

   @EventHandler
   public void serverStart(FMLServerStartingEvent event) {
      MinecraftServer server = MinecraftServer.getServer();
      ICommandManager command = server.getCommandManager();
      ServerCommandManager manager = (ServerCommandManager)command;
      manager.registerCommand(new CommandSpawnBoss());
      manager.registerCommand(new CommandAwakeEquipement());
      manager.registerCommand(new CommandItemElement());
      manager.registerCommand(new CommandReputation());
      manager.registerCommand(new CommandKillCounter());
      manager.registerCommand(new CommandKillCounterDelete());
   }

   public void registerEntities() {
      byte id = 0;
      int var2 = id + 1;
      EntityRegistry.registerModEntity(EntityHumanDummy.class, "dummy", id, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityGolemMecha.class, "mecha", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityGolemMechaHeavy.class, "mechaHeavy", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanSkeleton.class, "armoredSkeleton", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityNecromancer.class, "necromancer", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanZombie.class, "armoredZombie", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityLich.class, "Lich", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanSpecter.class, "specter", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanMummy.class, "mummy", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanPigZombie.class, "pigzombie", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanMinotaur.class, "minotaur", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntitySpecterBoss.class, "specterBoss", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanWalker.class, "abyssWalker", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityWalkerBoss.class, "abyssWalkerBoss", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanGremlin.class, "gremlin", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityHumanPirate.class, "pirate", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityPirateBoss.class, "pirateBoss", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntitySpaceWarrior.class, "spaceWarrior", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityGiantBoxer.class, "monking", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityBull.class, "bull", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntitySlimeBoss.class, "slimeBoss", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntitySpiderBoss.class, "spiderBoss", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityWyvern.class, "greenDragon", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityGiantZombie.class, "giantZombie", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityTurtle.class, "turtleBoss", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityTurtlePart.class, "TurtleBossPart", var2++, this, 60, 3, true);
      EntityRegistry.registerModEntity(EntityHumanNPC.class, "CQ_npc", var2++, this, 80, 3, true);
      EntityRegistry.registerModEntity(EntityPart.class, "EntityPart", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntityPartRidable.class, "EntityPartRidable", var2++, this, 100, 3, true);
      EntityRegistry.registerModEntity(EntitySlimePart.class, "EntityPartSlime", var2++, this, 64, 5, true);
      EntityRegistry.registerModEntity(EntityBaiter.class, "SummonedBait", var2++, this, 50, 3, true);
      EntityRegistry.registerModEntity(EntitySummonedUndead.class, "SummonedUndead", var2++, this, 50, 3, true);
      EntityRegistry.registerModEntity(EntityReferee.class, "Referee", var2++, this, 50, 3, true);
      EntityRegistry.registerModEntity(EntityBaseBall.class, "ChocoProjectile", var2++, this, 64, 3, true);
      EntityRegistry.registerModEntity(EntityHookShoot.class, "Hookshoot", var2++, this, 64, 3, true);
      EntityRegistry.registerModEntity(EntityProjectileBeam.class, "Beam", var2++, this, 64, 3, true);
      EntityRegistry.registerModEntity(EntityTracker.class, "Shield", var2++, this, 64, 5, true);
      EntityRegistry.registerModEntity(EntitySchematicBuilder.class, "Builder", var2++, this, 128, 5, true);
      EntityRegistry.registerModEntity(EntityDungeonCrystal.class, "DungeonCrystal", var2++, this, 64, 5, true);
      EntityRegistry.registerModEntity(EntityDecoration.class, "Decoration", var2++, this, 128, 5, true);
      EntityRegistry.registerModEntity(EntityPortal.class, "Portal", var2++, this, 64, 20, false);
   }

   public void registerBlocks() {
      spawner = (new BlockMobSpawner()).setBlockName("CQSpawner");
      GameRegistry.registerBlock(spawner, "CQSpawner");
      exporter = (new BlockEditor()).setBlockName("exporter").setCreativeTab(tabItems);
      GameRegistry.registerBlock(exporter, "exporter");
      armorStand = (new BlockArmorStand()).setHardness(0.5F).setResistance(0.1F).setBlockName("armorStand").setBlockTextureName("planks").setCreativeTab(tabItems);
      GameRegistry.registerBlock(armorStand, "armorStand");
      bannerStand = (new BlockBannerStand()).setHardness(0.5F).setResistance(0.1F).setBlockName("bannerStand").setBlockTextureName("planks").setCreativeTab(tabItems);
      GameRegistry.registerBlock(bannerStand, "bannerStand");
      table = (new BlockAltar()).setHardness(0.5F).setResistance(0.1F).setBlockName("table").setBlockTextureName("planks").setCreativeTab(tabItems);
      GameRegistry.registerBlock(table, "table");
      dungeonBrick = (new BlockDungeonBrick()).setHardness(120.0F).setResistance(80.0F).setBlockName("dungeonBrick").setCreativeTab(tabItems);
      GameRegistry.registerBlock(dungeonBrick, ItemBlockDungeonBrick.class, "dungeonBrick");
      String[] names = new String[]{"Treasure Chest", "Food Chest", "Tools Chest", "Ores Chest", "Boss Spawner"};
      exporterChest = (new BlockDecoration(Material.wood, "c", names)).setBlockName("ExporterChest").setCreativeTab(tabItems);
      GameRegistry.registerBlock(exporterChest, ItemBlockDungeonBrick.class, "exporterChest");
      emptyBlock = (new BlockEditorVoid()).setBlockName("none").setCreativeTab(tabItems);
      GameRegistry.registerBlock(emptyBlock, "none");
   }

   public void registerItems() {
      this.registerItem(egg);
      this.registerItem(magicPickaxe);
      this.registerItem(mobToSpawner);
      this.registerItem(rustedDagger);
      this.registerItem(rustedBigSword);
      this.registerItem(rustedSpear);
      this.registerItem(rustedSwordAndShied);
      this.registerItem(hookSword);
      this.registerItem(ironSwordAndShield);
      this.registerItem(diamondSwordAndShield);
      this.registerItem(swordMoonLight);
      this.registerItem(swordSunLight);
      this.registerItem(endSword);
      this.registerItem(swordTurtle);
      this.registerItem(swordSpider);
      this.registerItem(monkingSwordAndShield);
      this.registerItem(ironDagger);
      this.registerItem(diamondDagger);
      this.registerItem(tricksterDagger);
      this.registerItem(ninjaDagger);
      this.registerItem(monkingDagger);
      this.registerItem(ironSpear);
      this.registerItem(diamondSpear);
      this.registerItem(spearGun);
      this.registerItem(fireSpear);
      this.registerItem(ironBigsword);
      this.registerItem(diamondBigsword);
      this.registerItem(bigSwordBull);
      this.registerItem(monkingSword);
      this.registerItem(banner);
      this.registerItem(shield);
      this.registerItem(dragonHelmet);
      this.registerItem(scouter);
      this.registerItem(witchHat);
      this.registerItem(cloudBoots);
      this.registerItem(kingArmor);
      this.registerItem(slimeHelmet);
      this.registerItem(slimePlate);
      this.registerItem(slimePants);
      this.registerItem(slimeBoots);
      this.registerItem(turtleHelmet);
      this.registerItem(turtlePlate);
      this.registerItem(turtlePants);
      this.registerItem(turtleBoots);
      this.registerItem(bullHelmet);
      this.registerItem(bullPlate);
      this.registerItem(bullPants);
      this.registerItem(bullBoots);
      this.registerItem(spiderHelmet);
      this.registerItem(spiderPlate);
      this.registerItem(spiderPants);
      this.registerItem(spiderBoots);
      this.registerItem(diamondHeavyHelmet);
      this.registerItem(diamondHeavyPlate);
      this.registerItem(diamondHeavyPants);
      this.registerItem(diamondHeavyBoots);
      this.registerItem(ironHeavyHelmet);
      this.registerItem(ironHeavyPlate);
      this.registerItem(ironHeavyPants);
      this.registerItem(ironHeavyBoots);
      this.registerItem(inquisitionHelmet);
      this.registerItem(inquisitionPlate);
      this.registerItem(inquisitionPants);
      this.registerItem(inquisitionBoots);
      this.registerItem(diamondHelmet);
      this.registerItem(diamondPlate);
      this.registerItem(diamondPants);
      this.registerItem(diamondBoots);
      this.registerItem(ironHelmet);
      this.registerItem(ironPlate);
      this.registerItem(ironPants);
      this.registerItem(ironBoots);
      this.registerItem(armorMage);
      this.registerItem(staffHeal);
      this.registerItem(staffPhysic);
      this.registerItem(staffMagic);
      this.registerItem(staffFire);
      this.registerItem(staffBlast);
      this.registerItem(staffLight);
      this.registerItem(spell);
      this.registerItem(potion);
      this.registerItem(alchemistBag);
      this.registerItem(revolver);
      this.registerItem(golem);
      this.registerItem(golemHeavy);
      this.registerItem(golemUpgrade);
      this.registerItem(golemGun);
      this.registerItem(golemFlameThrower);
      this.registerItem(golemRifleGun);
      this.registerItem(golemCannon);
      this.registerItem(golemBubbleCannon);
      this.registerItem(golemMachineGun);
      this.registerItem(ammoLoader);
      this.registerItem(bullet);
      this.registerItem(material);
      this.registerItem(cursedBone);
      this.registerItem(hookShoot);
      this.registerItem(longShoot);
      this.registerItem(manualShoot);
      this.registerItem(spiderHook);
      this.registerItem(pathMarker);
      this.registerItem(controller);
      this.registerItem(pointMarker);
      this.registerItem(soulBottle);
      this.registerItem(medal);
      this.registerItem(musket);
      this.registerItem(golemShotgun);
      this.registerItem(bullAxe);
      this.registerItem(teleportStone);
      this.registerItem(spawnDisruptor);
      this.registerItem(elementStone);
      this.registerItem(spiderSpear);
      this.registerItem(backpack);
      this.registerItem(mageArmorHelmet);
      this.registerItem(mageArmorPlate);
      this.registerItem(mageArmorPants);
      this.registerItem(mageArmorBoots);
      tabItems.setItemIcon(endSword);
   }

   public Item registerItem(Item item) {
      GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
      return item;
   }

   public void registerDungeons() {
      defaultMob = new MobDefault();
      this.addMobToList(defaultMob);
      skeleton = new MobSkeleton();
      this.addMobToList(skeleton);
      zombie = new MobZombie();
      this.addMobToList(zombie);
      specter = new MobSpecter();
      this.addMobToList(specter);
      pigZombie = new MobZombiePig();
      this.addMobToList(pigZombie);
      minotaur = new MobMinotaur();
      this.addMobToList(minotaur);
      walker = new MobWalker();
      this.addMobToList(walker);
      pirate = new MobPirate();
      this.addMobToList(pirate);
      gremlin = new MobGremlin();
      this.addMobToList(gremlin);
      mummy = new MobMummy();
      this.addMobToList(mummy);
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderTemplate());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderTemplateSurface());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderCastle());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderEmptyCave());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderNether());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderStronghold());
      RegisterDungeonBuilder.addDungeonBuilder(new BuilderCavern());
      File file = new File(BDHelper.getAppDir(), "Chocolate/DungeonConfig/");
      BDHelper.println("## Dungeon register ##");
      this.registerDungeonsFromFolder(file);
      File[] files = file.listFiles();
      if(files != null) {
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File current = arr$[i$];
            if(current.isDirectory()) {
               this.registerDungeonsFromFolder(current);
            }
         }
      }

   }

   public void registerDungeonsFromFolder(File folder) {
      File[] files = folder.listFiles();
      if(files != null) {
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File current = arr$[i$];
            if(!current.isDirectory()) {
               DungeonBase dungeon = new DungeonBase();
               dungeon = dungeon.readData(current);
               if(dungeon != null) {
                  DungeonRegister.addDungeon(dungeon);
                  BDHelper.println("Registered dungeon: " + current.getName());
               }
            }
         }
      }

   }

   private void addMobToList(DungeonMonstersBase mob) {
      RegisterDungeonMobs.addMob(mob);
   }

}
