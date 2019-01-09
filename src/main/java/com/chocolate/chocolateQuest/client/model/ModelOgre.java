package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOgre extends ModelHuman {

   public ModelRenderer bipedBody2;


   public ModelOgre() {
      this(0.0F);
   }

   public ModelOgre(float f, boolean useTinyArms) {
      this(f);
      super.renderTinyArms = useTinyArms;
   }

   public ModelOgre(float f) {
      super(f);
      this.bipedBody2 = new ModelRenderer(this, 16, 22);
      this.bipedBody2.addBox(-4.0F, 0.0F, -2.0F, 8, 6, 4, 0.0F);
      this.bipedBody2.setRotationPoint(0.0F, 6.0F, -3.0F);
      super.bipedBody.addChild(this.bipedBody2);
      super.bipedHead = new ModelRenderer(this, 0, 0);
      super.bipedHead.addBox(-4.0F, -5.0F, -7.0F, 8, 8, 8, f);
      super.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -5.0F, -7.0F, 8, 8, 8, f + 0.5F);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.bipedRightLeg.setRotationPoint(-2.5F, 0.0F, 0.0F);
      super.bipedLeftLeg.setRotationPoint(2.5F, 0.0F, 0.0F);
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
   }
}
