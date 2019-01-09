package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.utils.BDHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class TextureExternal {

   int glTextureId = -1;
   static Map map = new HashMap();


   public static void bindTexture(String name) {
      TextureExternal texture = (TextureExternal)map.get(name);
      if(texture == null) {
         texture = new TextureExternal();

         try {
            texture.loadTexture(name);
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      bindTexture(texture);
   }

   static void bindTexture(TextureExternal texture) {
      GL11.glBindTexture(3553, texture.getGlTextureId());
   }

   public void loadTexture(String name) throws IOException {
      FileInputStream inputstream = null;

      try {
         File file = new File(BDHelper.getChocolateDir() + name);
         if(file.exists()) {
            inputstream = new FileInputStream(file);
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            boolean blur = false;
            boolean clamp = false;
            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, blur, clamp);
         } else {
            this.glTextureId = TextureUtil.missingTexture.getGlTextureId();
         }
      } finally {
         if(inputstream != null) {
            inputstream.close();
         }

      }

   }

   public int getGlTextureId() {
      if(this.glTextureId == -1) {
         this.glTextureId = TextureUtil.glGenTextures();
      }

      return this.glTextureId;
   }

}
