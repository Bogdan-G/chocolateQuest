package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBaiter extends RenderBiped {

   ResourceLocation texture = new ResourceLocation("chocolatequest:textures/entity/biped/pirateBoss.png");


   public RenderBaiter(ModelBiped par1ModelBiped, float par2) {
      super(par1ModelBiped, par2);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      EntityBaiter e = (EntityBaiter)entity;
      AbstractClientPlayer player;
      if(e.getThrower() instanceof AbstractClientPlayer) {
         player = (AbstractClientPlayer)e.getThrower();
         return player.getLocationSkin();
      } else if(e.getThrower() instanceof EntityHumanNPC) {
         player = (AbstractClientPlayer)e.getThrower();
         return player.getLocationSkin();
      } else {
         return this.texture;
      }
   }
}
