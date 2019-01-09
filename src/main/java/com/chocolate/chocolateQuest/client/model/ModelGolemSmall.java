package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGolemSmall extends ModelHuman {

   ModelRenderer mouth;
   public ModelRenderer bipedBody2;


   public ModelGolemSmall() {
      this(0.0F);
   }

   public ModelGolemSmall(float f) {
      super(f);
      super.bipedBody = new ModelRenderer(this, 16, 16);
      super.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 6, 4, f);
      super.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedBody2 = new ModelRenderer(this, 16, 22);
      this.bipedBody2.addBox(-3.0F, 6.0F, -2.0F, 6, 6, 4, f);
      this.bipedBody2.setRotationPoint(0.0F, 0.0F, 0.0F);
      super.bipedBody.addChild(this.bipedBody2);
      this.mouth = new ModelRenderer(this, 25, 0);
      this.mouth.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2);
      super.bipedHead.addChild(this.mouth);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      super.bipedRightLeg.setRotationPoint(-3.0F, 0.0F, 0.0F);
      super.bipedLeftLeg.setRotationPoint(3.0F, 0.0F, 0.0F);
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
   }

   public boolean useTinyArms() {
      return true;
   }
}
