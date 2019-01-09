package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.TextureExternal;
import com.chocolate.chocolateQuest.client.model.ModelGolemSmall;
import com.chocolate.chocolateQuest.client.model.ModelHuman;
import com.chocolate.chocolateQuest.client.model.ModelHumanSkeleton;
import com.chocolate.chocolateQuest.client.model.ModelMinotaur;
import com.chocolate.chocolateQuest.client.model.ModelMonkey;
import com.chocolate.chocolateQuest.client.model.ModelNaga;
import com.chocolate.chocolateQuest.client.model.ModelSpecter;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderNPC extends RenderHuman {

   static ModelHuman base = new ModelHuman();
   ModelHuman skeleton = new ModelHumanSkeleton();
   ModelHuman specter = new ModelSpecter();
   ModelHuman triton = new ModelNaga();
   ModelHuman minotaur = new ModelMinotaur();
   ModelHuman monkey = new ModelMonkey();
   ModelHuman golem = new ModelGolemSmall();


   public RenderNPC() {
      super(base, 0.5F);
   }

   protected void renderModel(EntityLivingBase entityliving, float f, float f1, float f2, float f3, float f4, float f5) {
      EntityHumanNPC npc = (EntityHumanNPC)entityliving;
      float red = BDHelper.getColorRed(npc.color);
      float green = BDHelper.getColorGreen(npc.color);
      float blue = BDHelper.getColorBlue(npc.color);
      GL11.glColor4f(red, green, blue, 1.0F);
      switch(npc.modelType) {
      case 3:
         super.mainModel = this.triton;
         break;
      case 4:
         super.mainModel = this.minotaur;
         break;
      case 5:
         super.mainModel = this.skeleton;
         break;
      case 6:
         super.mainModel = this.specter;
         break;
      case 7:
         super.mainModel = this.monkey;
         break;
      case 8:
         super.mainModel = this.golem;
         break;
      default:
         super.mainModel = base;
      }

      super.renderPassModel = super.modelBipedMain = (ModelBiped)super.mainModel;
      super.renderModel(entityliving, f, f1, f2, f3, f4, f5);
   }

   public void doRender(Entity entity, double x, double y, double z, float ry, float rp) {
      EntityHumanNPC npc = (EntityHumanNPC)entity;
      super.doRender(entity, x, y, z, ry, rp);
   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      super.preRenderCallback(entityliving, f);
      EntityHumanNPC e = (EntityHumanNPC)entityliving;
      if(e.modelType == 1) {
         GL11.glScalef(1.0F, 0.7F, 1.0F);
      }

      GL11.glScalef(e.size + 0.5F, e.size + 0.5F, e.size + 0.5F);
   }

   protected int shouldRenderPass(EntityLivingBase entityliving, int par2, float par3) {
      EntityHumanNPC e = (EntityHumanNPC)entityliving;
      return e.modelType != 3?super.shouldRenderPass(entityliving, par2, par3):(par2 != 2 && par2 != 3?super.shouldRenderPass(entityliving, par2, par3):0);
   }

   protected ResourceLocation getEntityTexture(Entity e) {
      EntityHumanNPC npc = (EntityHumanNPC)e;
      if(npc.hasPlayerTexture) {
         if(npc.textureLocationPlayer == null) {
            ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
            String name = npc.texture;
            resourcelocation = AbstractClientPlayer.getLocationSkin(name);
            AbstractClientPlayer.getDownloadImageSkin(resourcelocation, name);
            npc.textureLocationPlayer = resourcelocation;
         }

         return npc.textureLocationPlayer;
      } else {
         return npc.texture.startsWith("@")?new ResourceLocation("cql:" + npc.texture.substring(1)):new ResourceLocation("chocolateQuest:textures/entity/biped/" + npc.texture);
      }
   }

   protected void bindTexture(ResourceLocation rl) {
      if(rl.getResourceDomain().equals("cql")) {
         TextureExternal.bindTexture(rl.getResourcePath());
      } else {
         super.bindTexture(rl);
      }

   }

}
