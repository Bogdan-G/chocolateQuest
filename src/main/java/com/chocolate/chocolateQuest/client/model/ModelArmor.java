package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelArmor extends ModelBiped {

   public static final int HELMET = 0;
   public static final int PLATE = 1;
   public static final int LEGS = 2;
   public static final int BOOTS = 3;
   static ResourceLocation layer2 = new ResourceLocation("textures/models/armor/leather_layer_2.png");
   static ResourceLocation layer1 = new ResourceLocation("textures/models/armor/leather_layer_1.png");
   public int type;
   public static int renderPass0 = 0;
   public static int renderPass1 = 0;
   public static int renderPass2 = 0;
   public static int renderPass3 = 0;
   boolean isSitting;
   float movement;


   public ModelArmor(int type) {
      this(1.0F, type);
   }

   public ModelArmor(float f, int type) {
      super(f);
      this.type = 0;
      this.isSitting = false;
      this.movement = 0.0F;
      this.type = type;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      if(this.type == 0) {
         super.bipedHead.render(par7);
         super.bipedHeadwear.render(par7);
      } else if(this.type == 1) {
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         if(par1Entity instanceof EntityLivingBase) {
            this.renderCape(((EntityLivingBase)par1Entity).getEquipmentInSlot(3));
            this.renderFront(((EntityLivingBase)par1Entity).getEquipmentInSlot(3));
         }
      } else if(this.type == 2) {
         super.bipedBody.render(par7);
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
      } else if(this.type == 3) {
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
      }

   }

   public void renderArmor(ItemStack is, float par7) {
      if(this.type == 0) {
         super.bipedHead.render(par7);
         super.bipedHeadwear.render(par7);
      } else if(this.type == 1) {
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         this.renderCape(is);
         this.renderFront(is);
      } else if(this.type == 2) {
         super.bipedBody.render(par7);
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
      } else if(this.type == 3) {
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
      }

   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setArmorColor(ItemStack is) {
      int color = is.getItem().getColorFromItemStack(is, 1);
      GL11.glColor4f(BDHelper.getColorRed(color), BDHelper.getColorGreen(color), BDHelper.getColorBlue(color), 1.0F);
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
      super.isSneak = e.isSneaking();
      super.aimedBow = false;
      super.heldItemRight = 0;
      this.isSitting = false;
      this.movement = f * f1 * 0.0F;
      super.heldItemRight = ((EntityLivingBase)e).getEquipmentInSlot(0) == null?0:1;
      if(e instanceof EntityPlayer && ((EntityPlayer)e).getItemInUseCount() > 0) {
         EnumAction enumaction = ((EntityPlayer)e).getItemInUse().getItemUseAction();
         if(enumaction == EnumAction.block) {
            super.heldItemRight = 3;
         } else if(enumaction == EnumAction.bow) {
            super.aimedBow = true;
         }
      }

      super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
      if(e instanceof EntityHumanBase) {
         this.setHumanRotationAngles(f, f1, f2, f3, f4, f5, (EntityHumanBase)e);
      }

   }

   public void setHumanRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityHumanBase e) {
      if(e.isSitting()) {
         this.isSitting = true;
         super.bipedRightArm.rotateAngleX += -0.62831855F;
         super.bipedLeftArm.rotateAngleX += -0.62831855F;
         super.bipedRightLeg.rotateAngleX = -1.5707964F;
         super.bipedLeftLeg.rotateAngleX = -1.5707964F;
         super.bipedRightLeg.rotateAngleY = 0.31415927F;
         super.bipedLeftLeg.rotateAngleY = -0.31415927F;
      } else {
         if(e.isSneaking()) {
            super.bipedBody.rotateAngleX = 0.5F;
            super.bipedRightLeg.rotateAngleX -= 0.0F;
            super.bipedLeftLeg.rotateAngleX -= 0.0F;
            super.bipedRightArm.rotateAngleX += 0.4F;
            super.bipedLeftArm.rotateAngleX += 0.4F;
            super.bipedRightLeg.rotationPointZ = 4.0F;
            super.bipedLeftLeg.rotationPointZ = 4.0F;
            super.bipedRightLeg.rotationPointY = 9.0F;
            super.bipedLeftLeg.rotationPointY = 9.0F;
            super.bipedHead.rotationPointY = 1.0F;
         }

         if(e.isTwoHanded()) {
            this.setTwoHandedAngles(f2);
         }

         if(e.isAiming()) {
            float attackAnim = super.bipedRightArm.rotateAngleX;
            float maxAttackAnimTime = super.bipedRightArm.rotateAngleY;
            if(e.rightHand.isTwoHanded()) {
               if(e.rightHand.isAiming()) {
                  this.setAimingAngles(f2);
               }
            } else if(e.rightHand.isAiming()) {
               this.setAimingAnglesRight(f2);
            }

            if(e.leftHand.isAiming()) {
               this.setAimingAnglesLeft(f2);
            }

            if(e.rightHand.isAiming() && super.onGround > 0.0F) {
               super.bipedRightArm.rotateAngleY += maxAttackAnimTime;
               super.bipedRightArm.rotateAngleX += attackAnim + 0.3F;
            }
         }

         if(e.leftHandSwing > 0) {
            int attackAnim1 = e.leftHandSwing;
            byte maxAttackAnimTime1 = 10;
            float animProgress;
            if(e.haveShied()) {
               this.setShiedRotation(f2);
               animProgress = ((float)attackAnim1 + (float)(attackAnim1 - 1) * f5) / (float)maxAttackAnimTime1 * 4.82F;
               super.bipedLeftArm.rotateAngleY += MathHelper.cos(animProgress) * 1.8F - 0.8F;
               super.bipedLeftArm.rotateAngleX -= MathHelper.cos(animProgress) * 0.6F;
            } else {
               animProgress = (float)((double)(((float)attackAnim1 + (float)(attackAnim1 - 1) * f5) / (float)maxAttackAnimTime1) * 3.141592653589793D);
               super.bipedLeftArm.rotateAngleY += MathHelper.cos(animProgress) * 0.5F;
               super.bipedLeftArm.rotateAngleX -= MathHelper.sin(animProgress) * 1.2F;
            }
         } else if(e.isDefending()) {
            this.setShiedRotation(f2);
         }
      }

   }

   public void setTwoHandedAngles(float time) {
      float f7 = 0.0F;
      float rotationYaw = 0.0F;
      float swing = MathHelper.sin(super.onGround * 3.1415927F);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.3F;
      super.bipedRightArm.rotateAngleY = rotationYaw - 0.6F;
      super.bipedLeftArm.rotateAngleY = rotationYaw + 0.6F;
      super.bipedRightArm.rotateAngleX = -0.8F + swing;
      super.bipedLeftArm.rotateAngleX = -0.8F + swing;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      float f6 = 1.0F - super.onGround;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(super.onGround * 3.1415927F) * -(super.bipedHead.rotateAngleX - 0.7F) * 0.75F;
      super.bipedRightArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedRightArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedRightArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
      super.bipedLeftArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedLeftArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedLeftArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
   }

   public void setAimingAngles(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = 0.0F;
      super.bipedRightArm.rotateAngleY = -(0.1F - f7 * 0.6F) + super.bipedHead.rotateAngleY;
      super.bipedLeftArm.rotateAngleY = 0.1F - f7 * 0.6F + super.bipedHead.rotateAngleY + 0.4F;
      super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedRightArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }

   public void setAimingAnglesRight(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedRightArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedRightArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedRightArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedRightArm.rotateAngleY = -0.060000002F + super.bipedHead.rotateAngleY;
      super.bipedRightArm.rotateAngleZ = MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
   }

   public void setAimingAnglesLeft(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleY = 0.1F - f7 * 0.6F + super.bipedHead.rotateAngleY;
      super.bipedLeftArm.rotateAngleZ = -MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
   }

   public void setShiedRotation(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.7F;
      super.bipedLeftArm.rotateAngleY = 1.2F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }

   protected void renderCape(ItemStack is) {
      if(is.getItem() instanceof ItemArmorBase && ((ItemArmorBase)is.getItem()).hasCape(is)) {
         int spriteIndex = ((ItemArmorBase)is.getItem()).getCape(is);
         GL11.glPushMatrix();
         GL11.glTranslatef(-0.5F, 0.0F, 0.22F);
         GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
         if(this.getRenderPass(this.type) <= 1) {
            Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
         }

         float tx0 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
         float tx1 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
         float ty0 = (float)(32 + spriteIndex / 16 * 32) / 256.0F;
         float tyc = (float)(48 + spriteIndex / 16 * 32) / 256.0F;
         float ty1 = (float)(64 + spriteIndex / 16 * 32) / 256.0F;
         float txc = (float)(spriteIndex % 16 * 16 + 8) / 256.0F;
         double capeSize = 0.75D;
         double x0 = 0.0D;
         double xc = 0.5D;
         double x1 = 1.0D;
         double zDesp = -0.12D;
         double sneakDesp = 0.0D;
         double sneakDespHands = 0.0D;
         double yBase = 0.0D;
         if(super.isSneak) {
            sneakDesp = 0.3D;
            sneakDespHands = 0.14D;
            yBase = -0.1D;
         }

         double shoulderZr = Math.sin((double)super.bipedRightArm.rotateAngleX) * 0.05D;
         double shoulderYr = -Math.sin((double)super.bipedRightArm.rotateAngleX) * 0.2D;
         double shoulderZl = Math.sin((double)super.bipedLeftArm.rotateAngleX) * 0.05D;
         double shoulderYl = -Math.sin((double)super.bipedLeftArm.rotateAngleX) * 0.2D;
         double handDesp = 0.5D;
         double rightHandZ = Math.max(0.0D, Math.sin((double)super.bipedRightArm.rotateAngleX)) * 0.5D;
         double leftHandZ = Math.max(0.0D, Math.sin((double)super.bipedLeftArm.rotateAngleX)) * 0.5D;
         double handZmid = Math.max(rightHandZ, leftHandZ);
         double handYl = (-1.0D + Math.cos((double)super.bipedLeftLeg.rotateAngleX)) * 0.5D + 0.75D;
         double handYr = (-1.0D + Math.cos((double)super.bipedRightLeg.rotateAngleX)) * 0.5D + 0.75D;
         double handYmid = Math.max(handYl, handYr);
         double footZl = -0.24D + sneakDesp + Math.max(0.0D, Math.sin((double)super.bipedLeftLeg.rotateAngleX));
         double footZr = -0.24D + sneakDesp + Math.max(0.0D, Math.sin((double)super.bipedRightLeg.rotateAngleX));
         double footZMid = Math.max(footZr, footZl);
         double footYl = -1.0D + Math.cos((double)super.bipedLeftLeg.rotateAngleX) + 0.75D;
         double footYr = -1.0D + Math.cos((double)super.bipedRightLeg.rotateAngleX) + 0.75D;
         double footYMid = 0.75D + Math.max(footYr, footYl);
         double handZr = rightHandZ + -0.12D + sneakDespHands;
         double handZl = leftHandZ + -0.12D + sneakDespHands;
         handZmid += -0.12D + sneakDespHands;
         footYl += 0.75D;
         footYr += 0.75D;
         shoulderYl += yBase;
         shoulderYr += yBase;
         handYr += yBase;
         handYl += yBase;
         handYmid += yBase;
         footYl += yBase;
         footYr += yBase;
         footYMid += yBase;
         if(this.isSitting || super.isRiding) {
            double tessellator = 0.63D;
            handYr += tessellator;
            handYl += tessellator;
            handYmid += tessellator;
            double sittingYOffset = 0.55D;
            footZl = 0.75D;
            footZr = 0.75D;
            footZMid = 0.75D;
            footYl += sittingYOffset;
            footYr += sittingYOffset;
            footYMid += sittingYOffset;
         }

         Tessellator tessellator1 = Tessellator.instance;
         tessellator1.startDrawing(4);
         tessellator1.addVertexWithUV(1.0D, shoulderYl, shoulderZl, (double)tx1, (double)ty0);
         tessellator1.addVertexWithUV(0.0D, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(1.0D, shoulderYl, shoulderZl, (double)tx1, (double)ty0);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(1.0D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.0D, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
         tessellator1.addVertexWithUV(0.0D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(1.0D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(1.0D, footYl, footZl, (double)tx1, (double)ty1);
         tessellator1.addVertexWithUV(1.0D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(0.0D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.0D, footYr, footZr, (double)tx0, (double)ty1);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.0D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.draw();
         GL11.glPopMatrix();
      }

   }

   protected void renderFront(ItemStack is) {
      if(is.getItem() instanceof ItemArmorBase && ((ItemArmorBase)is.getItem()).hasApron(is)) {
         int spriteIndex = ((ItemArmorBase)is.getItem()).getApron(is);
         GL11.glPushMatrix();
         GL11.glTranslatef(-0.5F, 0.0F, -0.21F);
         GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
         if(this.getRenderPass(this.type) <= 1) {
            Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
         }

         float tx0 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
         float tx1 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
         float ty0 = (float)(32 + spriteIndex / 16 * 32) / 256.0F;
         float tyc = (float)(48 + spriteIndex / 16 * 32) / 256.0F;
         float ty1 = (float)(64 + spriteIndex / 16 * 32) / 256.0F;
         float txc = (float)(spriteIndex % 16 * 16 + 8) / 256.0F;
         double capeSize = 0.55D;
         double x0 = 0.25D;
         double xc = 0.5D;
         double x1 = 0.75D;
         double zDesp = -0.12D;
         double sneakDesp = 0.0D;
         double sneakDespHands = 0.0D;
         double yBase = 0.1D;
         if(super.isSneak) {
            yBase = 0.2D;
         }

         double shoulderZr = 0.0D;
         double shoulderYr = 0.0D;
         double shoulderZl = 0.0D;
         double shoulderYl = 0.0D;
         double rightHandZ = 0.0D;
         double leftHandZ = 0.0D;
         double handZr = -0.12D;
         double handZl = -0.12D;
         double handZmid = -0.12D;
         double handYl = 0.55D;
         double handYr = 0.55D;
         double handYmid = 0.55D;
         double footZl = -0.24D + Math.min(0.0D, Math.sin((double)super.bipedLeftLeg.rotateAngleX)) * 0.5D;
         double footZr = -0.24D + Math.min(0.0D, Math.sin((double)super.bipedRightLeg.rotateAngleX)) * 0.5D;
         double footZMid = Math.min(footZr, footZl);
         double footYl = Math.min(0.0D, Math.sin((double)super.bipedLeftLeg.rotateAngleX)) * 0.5D + 1.1D;
         double footYr = Math.min(0.0D, Math.sin((double)super.bipedRightLeg.rotateAngleX)) * 0.5D + 1.1D;
         double footYMid = Math.min(footYr, footYl);
         shoulderYl += yBase;
         shoulderYr += yBase;
         handYr += yBase;
         handYl += yBase;
         handYmid += yBase;
         footYl += yBase;
         footYr += yBase;
         footYMid += yBase;
         if(this.isSitting || super.isRiding) {
            double tessellator = -0.22D;
            handZr += tessellator;
            handZl += tessellator;
            handYmid += tessellator;
            double sittingYOffset = 0.5D;
            footYl = sittingYOffset;
            footYr = sittingYOffset;
            footYMid = sittingYOffset;
         }

         Tessellator tessellator1 = Tessellator.instance;
         tessellator1.startDrawing(4);
         tessellator1.addVertexWithUV(0.75D, shoulderYl, shoulderZl, (double)tx1, (double)ty0);
         tessellator1.addVertexWithUV(0.25D, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.75D, shoulderYl, shoulderZl, (double)tx1, (double)ty0);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.75D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.25D, shoulderYr, shoulderZr, (double)tx0, (double)ty0);
         tessellator1.addVertexWithUV(0.25D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.75D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(0.75D, footYl, footZl, (double)tx1, (double)ty1);
         tessellator1.addVertexWithUV(0.75D, handYl, handZl, (double)tx1, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(0.25D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.25D, footYr, footZr, (double)tx0, (double)ty1);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.addVertexWithUV(0.5D, handYmid, handZmid, (double)txc, (double)tyc);
         tessellator1.addVertexWithUV(0.25D, handYr, handZr, (double)tx0, (double)tyc);
         tessellator1.addVertexWithUV(0.5D, footYMid, footZMid, (double)txc, (double)ty1);
         tessellator1.draw();
         GL11.glPopMatrix();
      }

   }

   public void renderSkirt() {
      GL11.glPushMatrix();
      if(super.isSneak) {
         GL11.glTranslatef(0.0F, -0.1F, 0.25F);
      }

      boolean renderSides = true;
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.getItemTexture());
      byte spriteIndex = 31;
      float tx0 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float tx1 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float ty0 = (float)(32 + spriteIndex / 16 * 32) / 256.0F;
      float ty1 = (float)(64 + spriteIndex / 16 * 32) / 256.0F;
      double yPos = 0.6D;
      double width = 0.32D;
      double widthTop = 0.29D;
      double yBot = yPos + 0.9D;
      double xL = -width;
      double xLTop = -widthTop;
      double zF = -0.14D;
      double zB = 0.14D;
      double zLBot = Math.min(0.0D, Math.sin((double)super.bipedLeftLeg.rotateAngleX));
      double zRBot = Math.min(0.0D, Math.sin((double)super.bipedRightLeg.rotateAngleX));
      double yDesp = Math.min(zLBot, zRBot) * 0.6D;
      double zFBot = -0.1D + yDesp * 1.5D;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(xL, yBot + yDesp, zF + zFBot, (double)tx1, (double)ty1);
      tessellator.addVertexWithUV(width, yBot + yDesp, zF + zFBot, (double)tx0, (double)ty1);
      tessellator.addVertexWithUV(widthTop, yPos, zF, (double)tx0, (double)ty0);
      tessellator.addVertexWithUV(xLTop, yPos, zF, (double)tx1, (double)ty0);
      tessellator.addVertexWithUV(xL, yBot + yDesp, zB - zFBot, (double)tx1, (double)ty1);
      tessellator.addVertexWithUV(width, yBot + yDesp, zB - zFBot, (double)tx0, (double)ty1);
      tessellator.addVertexWithUV(widthTop, yPos, zB, (double)tx0, (double)ty0);
      tessellator.addVertexWithUV(xLTop, yPos, zB, (double)tx1, (double)ty0);
      if(renderSides) {
         tessellator.addVertexWithUV(xL, yBot + yDesp, zF + zFBot, (double)tx1, (double)ty1);
         tessellator.addVertexWithUV(xL, yBot + yDesp, zB - zFBot, (double)tx0, (double)ty1);
         tessellator.addVertexWithUV(xLTop, yPos, zB, (double)tx0, (double)ty0);
         tessellator.addVertexWithUV(xLTop, yPos, zF, (double)tx1, (double)ty0);
         tessellator.addVertexWithUV(width, yBot + yDesp, zF + zFBot, (double)tx1, (double)ty1);
         tessellator.addVertexWithUV(width, yBot + yDesp, zB - zFBot, (double)tx0, (double)ty1);
         tessellator.addVertexWithUV(widthTop, yPos, zB, (double)tx0, (double)ty0);
         tessellator.addVertexWithUV(widthTop, yPos, zF, (double)tx1, (double)ty0);
      }

      tessellator.draw();
      GL11.glPopMatrix();
   }

   public ItemStack getCachedItem(EntityLivingBase e, int type) {
      this.onRenderPass(type);
      return e.getEquipmentInSlot(4 - type);
   }

   public void onRenderPass(int type) {
      switch(type) {
      case 0:
         ++renderPass0;
         break;
      case 1:
         ++renderPass1;
         break;
      case 2:
         ++renderPass2;
         break;
      case 3:
         ++renderPass3;
      }

   }

   public int getRenderPass(int type) {
      switch(type) {
      case 0:
         return renderPass0 - 1;
      case 1:
         return renderPass1 - 1;
      case 2:
         return renderPass2 - 1;
      case 3:
         return renderPass3 - 1;
      default:
         return 0;
      }
   }

}
