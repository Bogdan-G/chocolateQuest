package com.chocolate.chocolateQuest.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCompanionCube extends ModelBase {

   ModelRenderer Body = new ModelRenderer(this, 0, 0);


   public ModelCompanionCube() {
      this.Body.addBox(0.0F, -8.0F, 0.0F, 16, 16, 16);
      this.Body.setRotationPoint(-8.0F, -8.0F, -8.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.Body.render(f5);
   }
}
