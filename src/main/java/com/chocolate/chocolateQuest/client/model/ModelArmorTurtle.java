package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelArmor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelArmorTurtle extends ModelArmor {

   public ModelRenderer turtleShell;
   public ModelRenderer turtleShellPart;
   ModelRenderer protectorShoulderL;
   ModelRenderer protectorShoulderR;


   public ModelArmorTurtle(int type) {
      this(0.0F, type);
   }

   public ModelArmorTurtle(float f, int type) {
      super(f, type);
      f += 0.1F;
      super.type = type;
      this.protectorShoulderR = this.getProtector(f);
      this.protectorShoulderR.setRotationPoint(-2.1F, 0.0F, 0.0F);
      this.protectorShoulderR.rotateAngleZ = 1.1F;
      super.bipedRightArm.addChild(this.protectorShoulderR);
      this.protectorShoulderL = this.getProtector(f);
      this.protectorShoulderL.setRotationPoint(3.1F, 0.0F, 0.0F);
      this.protectorShoulderL.rotateAngleY = 3.1415927F;
      this.protectorShoulderL.rotateAngleZ = -1.1F;
      super.bipedLeftArm.addChild(this.protectorShoulderL);
      f = 1.5F;
      this.turtleShell = new ModelRenderer(this, 32, 0);
      this.turtleShell.addBox(0.0F, 6.0F, 0.0F, 10, 12, 3, f);
      this.turtleShell.setRotationPoint(-5.0F, -6.0F, 1.0F);
      this.turtleShellPart = new ModelRenderer(this, 42, 4);
      this.turtleShellPart.addBox(0.0F, 6.0F, 2.0F, 6, 8, 1, f);
      this.turtleShellPart.setRotationPoint(-3.0F, -4.0F, 3.0F);
   }

   public ModelRenderer getProtector(float f) {
      ModelRenderer protector = new ModelRenderer(this, 54, 12);
      protector.addBox(0.0F, -1.0F, -2.0F, 1, 4, 4, f);
      ModelRenderer protector1 = new ModelRenderer(this, 54, 12);
      protector1.addBox(-1.0F, -3.0F, -2.0F, 1, 4, 4, f);
      protector.addChild(protector1);
      return protector;
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      if(super.type == 0) {
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         this.turtleShell.rotateAngleX = this.turtleShellPart.rotateAngleX = super.bipedBody.rotateAngleX;
         this.turtleShell.render(par7);
         this.turtleShellPart.render(par7);
         if(par1Entity instanceof EntityLivingBase) {
            ItemStack cachedItem = ((EntityLivingBase)par1Entity).getEquipmentInSlot(3);
            this.renderFront(cachedItem);
         }
      } else {
         super.bipedHead.render(par7);
      }

   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
