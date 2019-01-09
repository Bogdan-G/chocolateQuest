package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.model.ModelBoneHound;
import com.chocolate.chocolateQuest.client.model.ModelGolemVanilla;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSummonedUndead extends RenderBiped {

   public static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   public static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
   public static final ResourceLocation elemental = new ResourceLocation("chocolatequest:textures/entity/biped/elemental.png");
   public static final ResourceLocation bones = new ResourceLocation("chocolatequest:textures/entity/bones.png");
   ModelGolemVanilla modelGolem = new ModelGolemVanilla();


   public RenderSummonedUndead(ModelBiped model, float f) {
      super(model, f);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      EntitySummonedUndead e = (EntitySummonedUndead)par1Entity;
      return e.getElement() != null?elemental:(e.getType() == 3?witherTextures:(e.getType() == 2?bones:skeletonTextures));
   }

   protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
      if(!entityliving.isInvisible()) {
         EntitySummonedUndead e = (EntitySummonedUndead)entityliving;
         this.bindEntityTexture(entityliving);
         if(e.getElement() != null) {
            GL11.glDisable(2896);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            Elements element = e.getElement();
            float r = element.getColorX();
            float g = element.getColorY();
            float b = element.getColorZ();
            GL11.glColor4f(r, g, b, 1.0F);
            float desp = (float)System.nanoTime() * 1.0E-10F;
            GL11.glTranslatef(desp, desp, 0.0F);
            float scale = 1.68F + (float)Math.sin((double)(desp * 3.0F)) * 0.5F;
            GL11.glScalef(scale, scale, 0.0F);
            GL11.glMatrixMode(5888);
            this.renderCustomModel(e, f, f1, f2, f3, f4, f5);
            GL11.glMatrixMode(5890);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glEnable(2896);
         } else {
            this.renderCustomModel(e, f, f1, f2, f3, f4, f5);
         }
      }

   }

   protected void renderCustomModel(EntitySummonedUndead e, float f, float f1, float f2, float f3, float f4, float f5) {
      if(e.getType() == 1) {
         this.modelGolem.render(e, f, f1, f2, f3, f4, f5);
      } else if(e.getType() == 2) {
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
         ModelBoneHound model = new ModelBoneHound();
         model.render(e, f, f1, f2, f3, f4, f5);
      } else {
         super.mainModel.render(e, f, f1, f2, f3, f4, f5);
      }

   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      super.preRenderCallback(entityliving, f);
   }

   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.3F, 0.6F);
      float scale = 2.0F;
      GL11.glScalef(scale, scale, scale);
      GL11.glRotatef(40.0F, 1.3F, -1.8F, 1.4F);
      super.renderEquippedItems(par1EntityLivingBase, par2);
      GL11.glPopMatrix();
   }

}
