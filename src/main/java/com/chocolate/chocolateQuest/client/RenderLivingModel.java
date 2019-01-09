package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.model.ModelMage;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderLivingModel extends RenderLiving {

   protected ModelBase model;
   private ResourceLocation texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");


   public RenderLivingModel(ResourceLocation r) {
      super(new ModelMage(0.0F), 0.5F);
      this.texture = r;
   }

   public RenderLivingModel(ModelBase model, float f, ResourceLocation r) {
      super(model, f);
      this.texture = r;
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.texture;
   }
}
