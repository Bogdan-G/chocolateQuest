package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.RenderBubbleShield;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import java.util.Random;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHumanMecha extends RenderHuman {

   int divisions = 15;
   Random rnd = new Random();


   public RenderHumanMecha(ModelBiped model, float f, ResourceLocation r) {
      super(model, f, r);
   }

   public void doLeftHandRotationForGolemWeapon() {
      GL11.glTranslatef(0.6F, -0.2F, -1.05F);
   }

   public void doRender(EntityLivingBase entityliving, double x, double y, double z, float par8, float par9) {
      super.doRender(entityliving, x, y, z, par8, par9);
   }

   public void doRender(Entity par1Entity, double x, double y, double z, float par8, float par9) {
      super.doRender(par1Entity, x, y, z, par8, par9);
      this.renderElectricField(x, y, z, (EntityGolemMecha)par1Entity);
   }

   protected void renderElectricField(double x, double y, double z, EntityGolemMecha entityliving) {
      if(entityliving.hasElectricField() || entityliving.shieldON()) {
         GL11.glDisable(3553);
      }

      Tessellator tessellator = Tessellator.instance;
      int color = 5592575;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      this.rnd.setSeed((long)(entityliving.getEntityId() + entityliving.ticksExisted / 2));
      boolean steps = true;
      GL11.glLineWidth(1.0F);
      if(entityliving.hasElectricField()) {
         for(int a = 0; a < 5; ++a) {
            GL11.glDisable(2896);
            int var27 = this.rnd.nextInt(26) + 5;
            tessellator.startDrawing(3);
            tessellator.setColorRGBA_F(0.5F, 0.64F, 1.0F, 0.6F);
            int startX = this.rnd.nextInt();
            int startY = this.rnd.nextInt();
            int startZ = this.rnd.nextInt();

            for(int i = 0; i <= var27; ++i) {
               float stepVariation = (float)i / (float)var27;
               double dist = (double)entityliving.width * 1.6D;
               double boltSize = 80.0D;
               double varX = Math.sin((double)(i + startX) / boltSize * 3.141592653589793D * 2.0D) * dist + (this.rnd.nextDouble() - 0.5D) * 0.5D;
               double varZ = Math.cos((double)(i + startZ) / boltSize * 3.141592653589793D * 2.0D) * dist + (this.rnd.nextDouble() - 0.5D) * 0.5D;
               double varY = Math.sin((double)(entityliving.ticksExisted + i + startY) / boltSize * 3.141592653589793D) + this.rnd.nextDouble() + 1.6D;
               tessellator.addVertex(x + varX, y + varY, z + varZ);
            }

            tessellator.draw();
            GL11.glEnable(2896);
         }
      }

      if(entityliving.hasElectricShield() && entityliving.shieldON()) {
         GL11.glPushMatrix();
         GL11.glTranslated(x, y + (double)(entityliving.height / 2.0F) + 0.2D, z);
         GL11.glRotated((double)entityliving.rotationYaw, x, y, z);
         GL11.glColor4f(0.2F, 0.2F, 1.0F, 0.6F);
         RenderBubbleShield.renderBubble((entityliving.width + entityliving.height) * 0.52F, this.divisions);
         GL11.glPopMatrix();
      }

      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }
}
