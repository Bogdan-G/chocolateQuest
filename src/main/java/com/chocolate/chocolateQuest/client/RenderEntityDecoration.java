package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.RenderBanner;
import com.chocolate.chocolateQuest.client.model.ModelCompanionCube;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEntityDecoration extends Render {

   RenderBanner render = new RenderBanner(0.0F);
   ModelCompanionCube cube = new ModelCompanionCube();
   public static final ResourceLocation cubeTexture = new ResourceLocation("chocolateQuest:textures/entity/companionCube.png");


   public RenderEntityDecoration() {
      super.shadowSize = 0.5F;
   }

   public void doRender(EntityDecoration entity, double x, double y, double z, float f, float f1) {
      if(entity.type != 1000 && entity.type != 1001) {
         super.shadowSize = 0.2F * entity.size;
         this.render.renderBanner(x, y + (double)entity.field_70129_M, z, f, entity.type, super.renderManager.renderEngine, entity.size);
      } else {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x, (float)y, (float)z);
         GL11.glEnable('\u803a');
         GL11.glScalef(-entity.size, -entity.size, entity.size);
         GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
         super.renderManager.renderEngine.bindTexture(entity.type == 1000?cubeTexture:new ResourceLocation("chocolateQuest:textures/entity/companionCube2.png"));
         this.cube.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GL11.glPopMatrix();
      }

   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityDecoration)p_110775_1_);
   }

   public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
      this.doRender((EntityDecoration)entity, d0, d1, d2, f, f1);
   }

}
