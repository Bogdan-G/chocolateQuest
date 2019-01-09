package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelHumanZombie;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHumanZombie extends RenderHuman {

   public RenderHumanZombie(float f, ResourceLocation r) {
      this(new ModelHumanZombie(), f, r);
   }

   public RenderHumanZombie(ModelBiped model, float f, ResourceLocation r) {
      super(model, f, r);
   }

   protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
      EntityHumanZombie zombie = (EntityHumanZombie)entityliving;
      if(zombie.isDwarf()) {
         GL11.glScalef(0.65F, 0.65F, 0.65F);
         GL11.glTranslatef(0.0F, 1.0F, 0.0F);
      }

      super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return super.getEntityTexture(par1Entity);
   }
}
