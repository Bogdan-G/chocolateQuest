package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEntityDungeonCrystal extends Render {

   public static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
   private ModelBase modelEnderCrystal;


   public RenderEntityDungeonCrystal() {
      super.shadowSize = 0.5F;
      this.modelEnderCrystal = new ModelEnderCrystal(0.0F, true);
   }

   public void doRender(EntityDungeonCrystal entity, double d0, double d1, double d2, float f, float f1) {
      float f2 = (float)System.nanoTime() * 5.0E-8F;
      float s = 1.0F;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d0, (float)d1, (float)d2);
      GL11.glScalef(s, s, s);
      this.bindTexture(enderCrystalTextures);
      float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
      f3 += f3 * f3;
      this.modelEnderCrystal.render(entity, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   protected ResourceLocation getEntityTexture(EntityDungeonCrystal entity) {
      return enderCrystalTextures;
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityDungeonCrystal)p_110775_1_);
   }

   public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
      this.doRender((EntityDungeonCrystal)entity, d0, d1, d2, f, f1);
   }

}
