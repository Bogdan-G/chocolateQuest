package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.CommonProxy;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.client.EventHandlerCQClient;
import com.chocolate.chocolateQuest.client.KeyBindings;
import com.chocolate.chocolateQuest.client.KeyInputHandler;
import com.chocolate.chocolateQuest.client.RenderBaiter;
import com.chocolate.chocolateQuest.client.RenderBallProjectile;
import com.chocolate.chocolateQuest.client.RenderBanner;
import com.chocolate.chocolateQuest.client.RenderBeam;
import com.chocolate.chocolateQuest.client.RenderBipedCQ;
import com.chocolate.chocolateQuest.client.RenderBossBiped;
import com.chocolate.chocolateQuest.client.RenderBubbleShield;
import com.chocolate.chocolateQuest.client.RenderEntityDecoration;
import com.chocolate.chocolateQuest.client.RenderEntityDungeonCrystal;
import com.chocolate.chocolateQuest.client.RenderEntityPortal;
import com.chocolate.chocolateQuest.client.RenderHookShoot;
import com.chocolate.chocolateQuest.client.RenderInvisiblePart;
import com.chocolate.chocolateQuest.client.RenderLivingBossModel;
import com.chocolate.chocolateQuest.client.RenderLivingModelFly;
import com.chocolate.chocolateQuest.client.RenderSummonedUndead;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockArmorStand;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockBanner;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockEditor;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockTable;
import com.chocolate.chocolateQuest.client.blockRender.RenderBlockTableItem;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemAxe;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBanner;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemDagger;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemGolemWeaponWithCooldown;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemHookSword;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemModel;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemMusket;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemPistol;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemShield;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemSpear;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemStaff;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemSwordDefensive;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemTwoHandedSword;
import com.chocolate.chocolateQuest.client.model.ModelArmor;
import com.chocolate.chocolateQuest.client.model.ModelArmorBag;
import com.chocolate.chocolateQuest.client.model.ModelArmorColored;
import com.chocolate.chocolateQuest.client.model.ModelArmorDragon;
import com.chocolate.chocolateQuest.client.model.ModelArmorHeavyPlate;
import com.chocolate.chocolateQuest.client.model.ModelArmorKing;
import com.chocolate.chocolateQuest.client.model.ModelArmorMageRobe;
import com.chocolate.chocolateQuest.client.model.ModelArmorMageTunic;
import com.chocolate.chocolateQuest.client.model.ModelArmorMinotaur;
import com.chocolate.chocolateQuest.client.model.ModelArmorSlime;
import com.chocolate.chocolateQuest.client.model.ModelArmorSpider;
import com.chocolate.chocolateQuest.client.model.ModelArmorTurtle;
import com.chocolate.chocolateQuest.client.model.ModelArmorWitchHat;
import com.chocolate.chocolateQuest.client.model.ModelBull;
import com.chocolate.chocolateQuest.client.model.ModelDragonQuadruped;
import com.chocolate.chocolateQuest.client.model.ModelGiantBoxer;
import com.chocolate.chocolateQuest.client.model.ModelGiantZombie;
import com.chocolate.chocolateQuest.client.model.ModelGolemMecha;
import com.chocolate.chocolateQuest.client.model.ModelGolemMechaHeavy;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.client.model.ModelHumanSkeleton;
import com.chocolate.chocolateQuest.client.model.ModelHumanZombie;
import com.chocolate.chocolateQuest.client.model.ModelMinotaur;
import com.chocolate.chocolateQuest.client.model.ModelSkeletonSummoned;
import com.chocolate.chocolateQuest.client.model.ModelSlimeBoss;
import com.chocolate.chocolateQuest.client.model.ModelSpecter;
import com.chocolate.chocolateQuest.client.model.ModelSpecterBoss;
import com.chocolate.chocolateQuest.client.model.ModelSpiderBoss;
import com.chocolate.chocolateQuest.client.model.ModelTurtle;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelBubbleCannon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelCannon;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelFlameThrower;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelGun;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelMachineGun;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelRifle;
import com.chocolate.chocolateQuest.client.model.golemWeapon.ModelShotgun;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanGremlin;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanMecha;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanSkeleton;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHumanZombie;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderNPC;
import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.EntityPortal;
import com.chocolate.chocolateQuest.entity.EntityReferee;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.entity.EntityTracker;
import com.chocolate.chocolateQuest.entity.boss.EntityBull;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityGiantZombie;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySpiderBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtle;
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
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.gui.GuiInGameStats;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

   public static ModelArmor defaultArmor = new ModelArmor(1.0F, 1);
   public static ModelArmor kingArmor = new ModelArmorKing(1.0F, true);
   public static ModelArmor heavyArmorPlate = new ModelArmorHeavyPlate(1.0F, 1);
   public static ModelArmor heavyArmorLegs = new ModelArmorHeavyPlate(0.5F, 2);
   public static ModelArmor mageArmor = new ModelArmorMageRobe(0.0F);
   public static ModelArmor turtleArmorModel = new ModelArmorTurtle(1.0F, 0);
   public static ModelArmor turtleHelmetModel = new ModelArmorTurtle(1.0F, 1);
   public static ModelBiped dragonHead = new ModelArmorDragon();
   public static ModelArmor bullHead = new ModelArmorMinotaur();
   public static ModelArmor bullPlate = new ModelArmorMinotaur(0.0F, 1);
   public static ModelArmor[] slimeArmor = new ModelArmor[]{new ModelArmorSlime(0), new ModelArmorSlime(1), new ModelArmorSlime(0.5F, 2), new ModelArmorSlime(3)};
   public static ModelArmor[] coloredArmor = new ModelArmor[]{new ModelArmorColored(0), new ModelArmorColored(1), new ModelArmorColored(0.5F, 2), new ModelArmorColored(3)};
   public static ModelArmor witchHat = new ModelArmorWitchHat(1.0F);
   public static ModelArmor armorSpider = new ModelArmorSpider(1.0F, 1);
   public static ModelArmor armorSpiderLegs = new ModelArmorSpider(0.5F, 2);
   public static ModelArmor armorBag = new ModelArmorBag();
   public static ModelArmor[] armorMagePlate = new ModelArmor[]{new ModelArmorMageTunic(0, 0.65F), new ModelArmorMageTunic(1, 0.6F), new ModelArmorMageTunic(2, 0.6F), new ModelArmorMageTunic(3, 0.6F)};
   public static int tableRenderID = 0;


   public void register() {
      super.register();
      this.registerRenderInformation();
      KeyBindings.init();
      FMLCommonHandler.instance().bus().register(new KeyInputHandler());
      MinecraftForge.EVENT_BUS.register(new EventHandlerCQClient());
   }

   public void postInit() {
      super.postInit();
      MinecraftForge.EVENT_BUS.register(new GuiInGameStats(Minecraft.getMinecraft()));
   }

   public void registerRenderInformation() {
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.backpack, new RenderItemModel(ChocolateQuest.backpack));
      RenderItemSpear ris = new RenderItemSpear();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedSpear, ris);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironSpear, ris);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondSpear, ris);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.fireSpear, ris);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.spearGun, ris);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.spiderSpear, ris);
      RenderItemTwoHandedSword riths = new RenderItemTwoHandedSword();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironBigsword, riths);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondBigsword, riths);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.monkingSword, riths);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.bigSwordBull, riths);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedBigSword, riths);
      RenderItemSwordDefensive risd = new RenderItemSwordDefensive();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedSwordAndShied, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.endSword, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordTurtle, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordSpider, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordSunLight, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.swordMoonLight, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.monkingSwordAndShield, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironSwordAndShield, risd);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondSwordAndShield, risd);
      RenderItemStaff risf = new RenderItemStaff();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffHeal, risf);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffPhysic, risf);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffMagic, risf);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffBlast, risf);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffFire, risf);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.staffLight, risf);
      RenderItemDagger rid = new RenderItemDagger();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ironDagger, rid);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.diamondDagger, rid);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.tricksterDagger, rid);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.ninjaDagger, rid);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.monkingDagger, rid);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.rustedDagger, rid);
      RenderItemAxe ria = new RenderItemAxe();
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.bullAxe, ria);
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.hookSword, new RenderItemHookSword());
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.banner, new RenderItemBanner());
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.shield, new RenderItemShield());
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.revolver, new RenderItemPistol());
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.musket, new RenderItemMusket());
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemGun, new RenderItemGolemWeaponWithCooldown(new ModelGun()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemShotgun, new RenderItemGolemWeaponWithCooldown(new ModelShotgun()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemFlameThrower, new RenderItemGolemWeaponWithCooldown(new ModelFlameThrower()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemRifleGun, new RenderItemGolemWeaponWithCooldown(new ModelRifle()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemMachineGun, new RenderItemGolemWeaponWithCooldown(new ModelMachineGun()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemCannon, new RenderItemGolemWeaponWithCooldown(new ModelCannon()));
      MinecraftForgeClient.registerItemRenderer(ChocolateQuest.golemBubbleCannon, new RenderItemGolemWeaponWithCooldown(new ModelBubbleCannon()));
      tableRenderID = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(tableRenderID, new RenderBlockTableItem());
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanBase.class, new RenderHuman(new ModelHumanSkeleton(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/woodMan.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityGolemMecha.class, new RenderHumanMecha(new ModelGolemMecha(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/golemMecha.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityGolemMechaHeavy.class, new RenderHumanMecha(new ModelGolemMechaHeavy(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/golemMechaElite.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanNPC.class, new RenderNPC());
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanSkeleton.class, new RenderHumanSkeleton(0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanZombie.class, new RenderHumanZombie(0.5F, new ResourceLocation("textures/entity/zombie/zombie.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanSpecter.class, new RenderHuman(new ModelSpecter(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/specter.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanMummy.class, new RenderHumanZombie(new ModelHuman(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/mummy.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanWalker.class, new RenderHuman(new ModelHuman(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/shadow.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanGremlin.class, new RenderHumanGremlin(0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/gremlin.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanPirate.class, new RenderHuman(new ModelHuman(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanMinotaur.class, new RenderHuman(new ModelMinotaur(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/minotaurzombie.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityHumanPigZombie.class, new RenderHumanZombie(new ModelHumanZombie(), 0.5F, new ResourceLocation("textures/entity/zombie_pigman.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityLich.class, new RenderHumanZombie(new ModelHuman(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/liche.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityWalkerBoss.class, new RenderHuman(new ModelHuman(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/shadow.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityPirateBoss.class, new RenderHuman(new ModelHuman(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/pirateBoss.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntitySpecterBoss.class, new RenderHuman(new ModelSpecterBoss(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/specterBoss.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityGiantBoxer.class, new RenderBossBiped(new ModelGiantBoxer(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/biped/monkey.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntitySlimeBoss.class, new RenderLivingBossModel(new ModelSlimeBoss(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/slimeBoss.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityBull.class, new RenderLivingBossModel(new ModelBull(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/icebull.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntitySpiderBoss.class, new RenderLivingBossModel(new ModelSpiderBoss(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/spiderBoss.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityWyvern.class, new RenderLivingModelFly(new ModelDragonQuadruped(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/dragonbd.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityGiantZombie.class, new RenderBossBiped(new ModelGiantZombie(), 1.0F, new ResourceLocation("textures/entity/zombie/zombie.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityPart.class, new RenderInvisiblePart());
      RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, new RenderLivingBossModel(new ModelTurtle(), 1.0F, new ResourceLocation("chocolatequest:textures/entity/turtle.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntityReferee.class, new RenderBipedCQ(new ModelBiped(), 0.5F, new ResourceLocation("chocolatequest:textures/entity/biped/referee.png")));
      RenderingRegistry.registerEntityRenderingHandler(EntitySummonedUndead.class, new RenderSummonedUndead(new ModelSkeletonSummoned(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityBaiter.class, new RenderBaiter(new ModelBiped(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityBaseBall.class, new RenderBallProjectile(0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityHookShoot.class, new RenderHookShoot(0.0F));
      RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBeam.class, new RenderBeam(0.0F));
      RenderingRegistry.registerEntityRenderingHandler(EntityTracker.class, new RenderBubbleShield(0.0F));
      RenderingRegistry.registerEntityRenderingHandler(EntityDungeonCrystal.class, new RenderEntityDungeonCrystal());
      RenderingRegistry.registerEntityRenderingHandler(EntityDecoration.class, new RenderEntityDecoration());
      RenderingRegistry.registerEntityRenderingHandler(EntityPortal.class, new RenderEntityPortal());
      RenderingRegistry.registerEntityRenderingHandler(EntityCursor.class, new RenderBanner(0.5F));
   }

   public void registerTileEntities() {
      RenderBlockTable rbaltar = new RenderBlockTable();
      ClientRegistry.registerTileEntity(BlockAltarTileEntity.class, "table", rbaltar);
      RenderBlockArmorStand rbastand = new RenderBlockArmorStand();
      ClientRegistry.registerTileEntity(BlockArmorStandTileEntity.class, "armorStand", rbastand);
      RenderBlockBanner rbbstand = new RenderBlockBanner();
      ClientRegistry.registerTileEntity(BlockBannerStandTileEntity.class, "bannerStand", rbbstand);
      RenderBlockEditor rbEditor = new RenderBlockEditor();
      ClientRegistry.registerTileEntity(BlockEditorTileEntity.class, "Exporter", rbEditor);
   }

}
