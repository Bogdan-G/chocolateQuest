package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelElemental extends ModelHuman {

   public ModelElemental() {
      this(0.0F);
   }

   public ModelElemental(float f) {
      super(f);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      GL11.glDisable(2896);
      GL11.glMatrixMode(5890);
      GL11.glPushMatrix();
      float desp = (float)System.nanoTime() * 1.0E-10F;
      GL11.glTranslatef(desp, desp, 0.0F);
      GL11.glMatrixMode(5888);
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
      GL11.glMatrixMode(5890);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glEnable(2896);
   }
}
