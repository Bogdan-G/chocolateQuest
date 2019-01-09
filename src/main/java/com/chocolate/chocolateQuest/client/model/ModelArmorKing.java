package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmorHeavyPlate;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmorKing extends ModelArmorHeavyPlate {

   public ModelRenderer smallJewell;
   public ModelRenderer smallJewellSupport;
   public ModelRenderer headSmallJewell;
   public ModelRenderer headSmallJewellSupport;
   public ModelRenderer leftJewell;
   public ModelRenderer leftJewellTop;
   public ModelRenderer rightJewell;
   public ModelRenderer rightJewellTop;
   boolean renderHead;


   public ModelArmorKing() {
      this(0.0F);
   }

   public ModelArmorKing(float f, boolean head) {
      this(f);
      this.renderHead = head;
   }

   public ModelArmorKing(float f) {
      super(f, 1);
      this.renderHead = false;
      float headScale = 1.1F;
      super.bipedHeadwear = new ModelRenderer(this, 32, 0);
      super.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, headScale);
      super.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.smallJewellSupport = new ModelRenderer(this, 0, 0);
      this.smallJewellSupport.addBox(1.5F, 2.0F, -3.5F, 2, 2, 1, 0.0F);
      this.smallJewell = new ModelRenderer(this, 0, 3);
      this.smallJewell.addBox(2.0F, 2.5F, -3.75F, 1, 1, 1, 0.0F);
      super.bipedBody.addChild(this.smallJewell);
      super.bipedBody.addChild(this.smallJewellSupport);
      this.headSmallJewellSupport = new ModelRenderer(this, 0, 0);
      this.headSmallJewellSupport.addBox(-1.0F, -7.5F, -5.5F, 2, 2, 1, 0.5F);
      this.headSmallJewell = new ModelRenderer(this, 0, 3);
      this.headSmallJewell.addBox(-0.5F, -7.0F, -5.75F, 1, 1, 1, 0.5F);
      super.bipedHeadwear.addChild(this.headSmallJewellSupport);
      super.bipedHeadwear.addChild(this.headSmallJewell);
      this.leftJewell = this.getJewell();
      this.leftJewell.setRotationPoint(6.5F, -1.0F, 0.5F);
      super.bipedLeftArm.addChild(this.leftJewell);
      this.leftJewellTop = this.getJewell();
      this.leftJewellTop.setRotationPoint(2.5F, -4.0F, 0.5F);
      this.leftJewellTop.rotateAngleZ = -1.5707964F;
      super.bipedLeftArm.addChild(this.leftJewellTop);
      this.rightJewell = this.getJewell();
      this.rightJewell.setRotationPoint(-5.5F, -1.0F, 0.5F);
      this.rightJewell.rotateAngleZ = 3.1415927F;
      super.bipedRightArm.addChild(this.rightJewell);
      this.rightJewellTop = this.getJewell();
      this.rightJewellTop.setRotationPoint(-2.5F, -4.0F, 0.5F);
      this.rightJewellTop.rotateAngleZ = -1.5707964F;
      super.bipedRightArm.addChild(this.rightJewellTop);
   }

   protected ModelRenderer getJewell() {
      ModelRenderer jewellSupport = new ModelRenderer(this, 24, 0);
      jewellSupport.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
      ModelRenderer jewell = new ModelRenderer(this, 56, 0);
      jewell.addBox(0.5F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
      jewellSupport.addChild(jewell);
      return jewellSupport;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      super.bipedHeadwear.setRotationPoint(0.0F, -1.5F, 0.0F);
      super.bipedHeadwear.render(par7);
      super.render(par1Entity, par2, par3, par4, par5, par6, par7);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
