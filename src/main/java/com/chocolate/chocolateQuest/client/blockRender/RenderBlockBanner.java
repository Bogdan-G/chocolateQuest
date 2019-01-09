package com.chocolate.chocolateQuest.client.blockRender;

import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.client.RenderBanner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBlockBanner extends TileEntitySpecialRenderer {

   RenderBanner render = new RenderBanner(0.0F);


   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
      BlockBannerStandTileEntity te = (BlockBannerStandTileEntity)tileentity;
      if(te.item != null && te.hasFlag) {
         this.render.renderBanner(x + 0.5D, y, z + 0.5D, (float)te.rotation, te.item.getItemDamage(), super.field_147501_a.field_147553_e);
      }

   }
}
