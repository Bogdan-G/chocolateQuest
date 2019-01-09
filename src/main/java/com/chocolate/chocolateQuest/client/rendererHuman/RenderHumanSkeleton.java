package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.RenderSummonedUndead;
import com.chocolate.chocolateQuest.client.model.ModelHumanSkeleton;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHumanSkeleton extends RenderHuman {

   public RenderHumanSkeleton(float f) {
      super(new ModelHumanSkeleton(), f);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return par1Entity instanceof EntityHumanSkeleton && ((EntityHumanSkeleton)par1Entity).isWither()?RenderSummonedUndead.witherTextures:RenderSummonedUndead.skeletonTextures;
   }
}
