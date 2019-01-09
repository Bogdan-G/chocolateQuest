package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.gui.CachedEntity;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.items.ItemArmorHelmetScouter;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class GuiInGameStats extends Gui {

   public static final ResourceLocation GUIBars = new ResourceLocation("chocolatequest:textures/entity/bars.png");
   private Minecraft mc;
   private int xPos;
   private int yPos;
   CachedEntity trackingEntity;
   int lastTick = 0;
   float prevFov = 0.0F;
   boolean fovModified = false;
   private static final int ICON_SIZE = 16;
   private static final int ICON_SPACING = 18;
   private static final int ICONS_PER_ROW = 16;
   private static final int COLOR = 16777215;


   public GuiInGameStats(Minecraft mc) {
      this.mc = mc;
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL
   )
   public void onRenderExperienceBar(RenderGameOverlayEvent event) {
      if(!event.isCancelable() && event.type == ElementType.EXPERIENCE) {
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
         int staminaX;
         float builderPixelWidth;
         int barX;
         int max;
         if(player.getCurrentEquippedItem() != null) {
            ItemStack barPixelHeight = player.getCurrentEquippedItem();
            if(barPixelHeight.getItem() instanceof ILoadableGun) {
               ItemStack[] barHeight = InventoryBag.getCargo(barPixelHeight);
               int width = 0;

               for(staminaX = 0; staminaX < barHeight.length; ++staminaX) {
                  if(barHeight[staminaX] != null) {
                     ItemStack staminaY = barHeight[staminaX];
                     GL11.glPushMatrix();
                     builderPixelWidth = 16.0F;
                     GL11.glTranslatef(builderPixelWidth + (float)width, builderPixelWidth, 0.0F);
                     GL11.glScalef(-builderPixelWidth, -builderPixelWidth, 0.0F);
                     ResourceLocation builderPixelHeight = this.mc.renderEngine.getResourceLocation(staminaY.getItemSpriteNumber());
                     barX = staminaY.getItem().getRenderPasses(staminaY.getItemDamage());

                     for(int barY = 0; barY < barX; ++barY) {
                        max = staminaY.getItem().getColorFromItemStack(staminaY, barY);
                        GL11.glColor4f(BDHelper.getColorRed(max), BDHelper.getColorGreen(max), BDHelper.getColorBlue(max), 1.0F);
                        IIcon min = staminaY.getItem().getIconFromDamageForRenderPass(staminaY.getItemDamage(), barY);
                        this.mc.renderEngine.bindTexture(builderPixelHeight);
                        ItemRenderer.renderItemIn2D(Tessellator.instance, min.getMaxU(), min.getMinV(), min.getMinU(), min.getMaxV(), min.getIconWidth(), min.getIconHeight(), 0.0625F);
                     }

                     GL11.glPopMatrix();
                     if(staminaY.stackSize > 1) {
                        this.drawString(this.mc.fontRenderer, staminaY.stackSize + "", width + 8, 8, 16777215);
                     }

                     width = (int)((float)width + builderPixelWidth);
                  }
               }

               GL11.glTranslatef(0.0F, 16.0F, 0.0F);
            }
         }

         if(ItemArmorHelmetScouter.target != null) {
            this.drawScouterInfo();
         }

         GL11.glPopMatrix();
         byte var15 = 11;
         float var16 = 0.25F;
         this.mc.renderEngine.bindTexture(GUIBars);
         byte var17 = 80;
         staminaX = event.resolution.getScaledWidth() - var17 - 5;
         int var18 = event.resolution.getScaledHeight() - 20;
         if(PlayerManager.getStamina(player) < PlayerManager.getMaxStamina(player)) {
            this.drawTexturedRect(staminaX, var18, staminaX + var17, var18 + var15, 0.0F, 0.0F, 1.0F, 0.25F);
            builderPixelWidth = PlayerManager.getStamina(player);
            float var20 = PlayerManager.getMaxStamina(player);
            barX = (int)(builderPixelWidth / var20 * (float)var17);
            this.drawTexturedRect(staminaX, var18, staminaX + barX, var18 + var15, 0.0F, var16 * 2.0F, builderPixelWidth / var20, var16 * 3.0F);
         }

         if(player.getCurrentEquippedItem() != null && player.isUsingItem()) {
            ItemStack var19 = player.getCurrentEquippedItem();
            if(var19.getItem() instanceof ILoadBar) {
               this.drawTexturedRect(staminaX, var18, staminaX + var17, var18 + var15, 0.0F, 0.0F, 1.0F, 0.25F);
               ILoadBar var24 = (ILoadBar)var19.getItem();
               float var25 = (float)var24.getMaxCharge();
               float var27 = Math.min((float)player.getItemInUseDuration(), var25);
               max = (int)(var27 / var25 * (float)var17);
               if(var24.shouldBarShine(player, var19)) {
                  GL11.glColor3f(1.0F, 0.0F, 0.0F);
               } else {
                  GL11.glColor3f(0.5F, 0.0F, 0.0F);
               }

               this.drawTexturedRect(staminaX, var18, staminaX + max, var18 + var15, 0.0F, var16 * 3.0F, var27 / var25, var16 * 4.0F);
               GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }
         }

         if(BuilderHelper.builderHelper.getStructureGenerationAmmount() > 0) {
            short var22 = 128;
            byte var23 = 16;
            barX = event.resolution.getScaledWidth() / 2 - var22 / 2;
            byte var28 = 30;
            this.drawTexturedRect(barX, var28, barX + var22, var28 + var23, 0.0F, 0.0F, 1.0F, 0.25F);
            float var26 = 100.0F;
            float var29 = (float)BuilderHelper.builderHelper.getStructureGenerationAmmount();
            int var21 = (int)(var29 / var26 * (float)var22);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            float desp = (float)Minecraft.getSystemTime() * 1.5E-4F;
            GL11.glTranslatef(-desp, 0.0F, 0.0F);
            this.drawTexturedRect(barX + 1, var28, barX + var21, var28 + var23, 0.0F, var16, var29 / var26, var16 * 2.0F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            this.drawString(this.mc.fontRenderer, StatCollector.translateToLocal("strings.generate_structure"), barX, var28 - 15, 16777215);
         }

         if(this.lastTick != Minecraft.getMinecraft().thePlayer.ticksExisted) {
            this.lastTick = Minecraft.getMinecraft().thePlayer.ticksExisted;
            if(this.trackingEntity != null) {
               this.trackingEntity.onUpdate();
            }
         }

         if(ItemArmorHelmetScouter.target != null) {
            if(this.trackingEntity == null) {
               this.trackingEntity = new CachedEntity(ItemArmorHelmetScouter.target);
            } else if(this.trackingEntity.entity != ItemArmorHelmetScouter.target) {
               this.trackingEntity = new CachedEntity(ItemArmorHelmetScouter.target);
            }

            this.drawBossBar(event.resolution.getScaledWidth(), event.resolution.getScaledHeight(), this.trackingEntity, 10, true);
         }

      }
   }

   public void drawBossBar(int scaleWidth, int scaleHeight, CachedEntity trackingEntity, int posY, boolean drawName) {
      float health = trackingEntity.entity.getHealth();
      float maxHealth = trackingEntity.entity.getMaxHealth();
      float dif = 0.0F;
      if(trackingEntity.trackingHealth != health) {
         dif = health - trackingEntity.trackingHealth;
         trackingEntity.lastDamage = dif;
         trackingEntity.damageTimer = 15;
      }

      trackingEntity.trackingHealth = health;
      int time = Minecraft.getMinecraft().thePlayer.ticksExisted;
      this.mc.renderEngine.bindTexture(GUIBars);
      short barTotalPixelWidth = 182;
      byte barPixelHeight = 8;
      int barX = scaleWidth / 2 - barTotalPixelWidth / 2;
      float barTextureHeight = 0.25F;
      byte barType = 3;
      this.drawTexturedRect(barX, posY, barX + barTotalPixelWidth, posY + barPixelHeight, 0.0F, 0.0F, 1.0F, 0.25F);
      int barPixelWidth = (int)(health / maxHealth * (float)barTotalPixelWidth);
      this.drawTexturedRect(barX + 1, posY, barX + barPixelWidth, posY + barPixelHeight, 0.0F, barTextureHeight * (float)barType, health / maxHealth, barTextureHeight * (float)(barType + 1));
      int textX;
      int textY;
      if(trackingEntity.lastDamage != 0.0F) {
         int name = (int)(Math.abs(trackingEntity.lastDamage / maxHealth) * (float)barTotalPixelWidth);
         if(trackingEntity.lastDamage > 0.0F) {
            textY = barX + barPixelWidth - name;
            GL11.glColor3f(0.0F, 1.0F, 0.0F);
         } else {
            textY = barX + barPixelWidth;
            GL11.glColor3f(1.0F, 0.0F, 0.0F);
         }

         this.drawTexturedRect(textY, posY, textY + name, posY + barPixelHeight, 0.1F, barTextureHeight * (float)barType, 0.9F, barTextureHeight * (float)(barType + 1));
         textX = posY + barPixelHeight - this.mc.fontRenderer.FONT_HEIGHT;
         int textX1 = barX + barTotalPixelWidth;
         int color = trackingEntity.lastDamage < 0.0F?16711680:'\uff00';
         this.drawString(this.mc.fontRenderer, BDHelper.floatToString((double)trackingEntity.lastDamage, 3), textX1, textX, color);
      }

      String name1 = trackingEntity.entity.getCommandSenderName();
      textY = posY + 1 - this.mc.fontRenderer.FONT_HEIGHT;
      textX = barX + barTotalPixelWidth / 2 - this.mc.fontRenderer.getStringWidth(name1) / 2;
      this.drawString(this.mc.fontRenderer, name1, textX, textY, 16777215);
   }

   public int drawScouterInfo() {
      GL11.glPushMatrix();
      float scale = 0.5F;
      GL11.glScalef(scale, scale, scale);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2896);
      this.xPos = 2;
      this.yPos = 2;
      this.mc.renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      byte iconBar = 25;
      byte infoBars = 3;

      int TEXT_OFFSET;
      for(TEXT_OFFSET = 0; TEXT_OFFSET < infoBars; ++TEXT_OFFSET) {
         this.drawTexturedModalRect(this.xPos, this.yPos + 16 * TEXT_OFFSET, iconBar % 16 * 16, iconBar / 16 * 16, 64, 16);
      }

      for(TEXT_OFFSET = 0; TEXT_OFFSET < infoBars; ++TEXT_OFFSET) {
         this.drawIcon(iconBar + 4 + TEXT_OFFSET, this.xPos, this.yPos + 16 * TEXT_OFFSET);
      }

      byte var21 = 3;
      int xResist = this.xPos + 16;
      int yResist = this.yPos + 16 * infoBars;
      byte icon = 9;
      this.drawIcon(icon + 1, this.xPos, yResist);
      this.drawIcon(icon + 2, this.xPos + 32, yResist);
      this.drawIcon(icon + 3, this.xPos, yResist + 16);
      this.drawIcon(icon + 4, this.xPos + 32, yResist + 16);
      this.drawIcon(icon + 5, this.xPos, yResist + 32);
      this.drawIcon(icon + 6, this.xPos + 32, yResist + 32);
      this.drawIcon(icon, xResist, yResist);
      this.drawIcon(icon, xResist + 32, yResist);
      this.drawIcon(icon, xResist, yResist + 16);
      this.drawIcon(icon, xResist + 32, yResist + 16);
      this.drawIcon(icon, xResist, yResist + 32);
      this.drawIcon(icon, xResist + 32, yResist + 32);
      EntityLivingBase el = ItemArmorHelmetScouter.target;
      this.drawStat("   " + BDHelper.floatToString((double)el.getHealth(), 2) + "/" + BDHelper.floatToString((double)el.getMaxHealth(), 2));
      double damage = 0.0D;
      int baseDamage = 0;
      IAttributeInstance attribute = el.getEntityAttribute(SharedMonsterAttributes.attackDamage);
      if(attribute != null) {
         damage = attribute.getAttributeValue();
         baseDamage = (int)attribute.getBaseValue();
         ItemStack protection = el.getEquipmentInSlot(0);
         if(protection != null) {
            damage += BDHelper.getWeaponDamage(protection);
         }
      }

      this.drawStat("   " + damage + " (" + baseDamage + ")");
      this.drawStat("     " + el.getTotalArmorValue() * 4 + "%");
      this.yPos += 16;
      int var22 = (int)((1.0D - BDHelper.getEnchantmentDamageReduction(el, Enchantment.protection.effectId)) * 100.0D);
      int projectileProtection = (int)((1.0D - BDHelper.getEnchantmentDamageReduction(el, Enchantment.projectileProtection.effectId)) * 100.0D);
      int physicProtection = (int)(100.0D * (1.0D - BDHelper.getDamageReductionForElement(el, Elements.physic, true)));
      int magicProtection = (int)(100.0D * (1.0D - BDHelper.getDamageReductionForElement(el, Elements.magic, true)));
      int blastProtection = (int)(100.0D * (1.0D - BDHelper.getDamageReductionForElement(el, Elements.blast, true)));
      int fireProtection = (int)(100.0D * (1.0D - BDHelper.getDamageReductionForElement(el, Elements.fire, true)));
      String text = var22 + "";
      this.drawString(this.mc.fontRenderer, text, xResist + var21, yResist + var21, 16777215);
      text = projectileProtection + "";
      this.drawString(this.mc.fontRenderer, text, xResist + 32 + var21, yResist + var21, 16777215);
      text = physicProtection + "";
      this.drawString(this.mc.fontRenderer, text, xResist + var21, yResist + 16 + var21, 16777215);
      text = fireProtection + "";
      this.drawString(this.mc.fontRenderer, text, xResist + 32 + var21, yResist + 16 + var21, 16777215);
      text = blastProtection + "";
      this.drawString(this.mc.fontRenderer, text, xResist + var21, yResist + 32 + var21, 16777215);
      text = magicProtection + "";
      this.drawString(this.mc.fontRenderer, text, xResist + 32 + var21, yResist + 32 + var21, 16777215);
      --ItemArmorHelmetScouter.targetTimer;
      if(ItemArmorHelmetScouter.targetTimer == 0) {
         ItemArmorHelmetScouter.target = null;
      }

      GL11.glPopMatrix();
      return 0;
   }

   public void drawIcon(int icon, int xPos, int yPos) {
      this.drawTexturedModalRect(xPos, yPos, icon % 16 * 16, icon / 16 * 16, 16, 16);
   }

   public void drawStat(String text) {
      if(text.length() > 14) {
         text = text.substring(0, 12);
      }

      this.drawString(this.mc.fontRenderer, text, this.xPos + 3, this.yPos + 3, 16777215);
      this.yPos += 16;
   }

   public void drawTexturedRect(int xMin, int yMin, int xMax, int yMax, float tx, float ty, float tx1, float ty1) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)xMin, (double)yMax, (double)super.zLevel, (double)tx, (double)ty1);
      tessellator.addVertexWithUV((double)xMax, (double)yMax, (double)super.zLevel, (double)tx1, (double)ty1);
      tessellator.addVertexWithUV((double)xMax, (double)yMin, (double)super.zLevel, (double)tx1, (double)ty);
      tessellator.addVertexWithUV((double)xMin, (double)yMin, (double)super.zLevel, (double)tx, (double)ty);
      tessellator.draw();
   }

}
