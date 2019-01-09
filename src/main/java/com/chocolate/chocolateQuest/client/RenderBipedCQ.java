package com.chocolate.chocolateQuest.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBipedCQ extends RenderBiped {

   protected ModelBiped model;
   private ResourceLocation texture = new ResourceLocation("chocolatequest:textures/entity/necromancer.png");


   public RenderBipedCQ(ResourceLocation r) {
      super(new ModelBiped(0.0F), 0.5F);
      this.texture = r;
   }

   public RenderBipedCQ(ModelBiped model, float f, ResourceLocation r) {
      super(model, f);
      this.texture = r;
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.texture;
   }
}
