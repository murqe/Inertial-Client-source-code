package me.rich.font;

import java.awt.Font;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
      public static CFontRenderer sfui14 = new CFontRenderer(getFontTTF("sf-ui", 14), true, true);
      public static CFontRenderer sfui15 = new CFontRenderer(getFontTTF("sf-ui", 15), true, true);
      public static CFontRenderer sfui16 = new CFontRenderer(getFontTTF("sf-ui", 16), true, true);
      public static CFontRenderer sfui17 = new CFontRenderer(getFontTTF("sf-ui", 17), true, true);
      public static CFontRenderer sfui18 = new CFontRenderer(getFontTTF("sf-ui", 18), true, true);
      public static CFontRenderer sfui19 = new CFontRenderer(getFontTTF("sf-ui", 19), true, true);
      public static CFontRenderer sfui20 = new CFontRenderer(getFontTTF("sf-ui", 20), true, true);
      public static CFontRenderer roboto_18 = new CFontRenderer(getFontTTF("roboto", 18), true, true);
      public static CFontRenderer roboto_20 = new CFontRenderer(getFontTTF("roboto", 20), true, true);
      public static CFontRenderer roboto_16 = new CFontRenderer(getFontTTF("roboto", 16), true, true);
      public static CFontRenderer roboto_15 = new CFontRenderer(getFontTTF("roboto", 15), true, true);
      public static CFontRenderer roboto_14 = new CFontRenderer(getFontTTF("roboto", 14), true, true);
      public static CFontRenderer roboto_13 = new CFontRenderer(getFontTTF("roboto", 13), true, true);
      public static CFontRenderer neverlose500 = new CFontRenderer(getFontTTF("neverlose500", 13), true, true);
      public static CFontRenderer futura_14 = new CFontRenderer(getFontTTF("futura-normal", 14), true, true);
      public static CFontRenderer neverlose502 = new CFontRenderer(getFontTTF("neverlose500", 15), true, true);
      public static CFontRenderer neverlose500_16 = new CFontRenderer(getFontTTF("neverlose500", 16), true, true);
      public static CFontRenderer neverlose500_17 = new CFontRenderer(getFontTTF("neverlose500", 17), true, true);
      public static CFontRenderer neverlose14 = new CFontRenderer(getFontTTF("neverlose500", 14), true, true);
      public static CFontRenderer neverlose500_18 = new CFontRenderer(getFontTTF("neverlose500", 18), true, true);
      public static CFontRenderer neverlose501 = new CFontRenderer(getFontTTF("neverlose500", 20), true, true);
      public static CFontRenderer museo = new CFontRenderer(getFontTTF("museo", 20), true, true);
      public static CFontRenderer neverlosewater15 = new CFontRenderer(getFontTTF("neverlosewater1", 17), true, true);
      public static CFontRenderer neverlosewater2 = new CFontRenderer(getFontTTF("neverlosewater2", 20), true, true);

      private static Font getFontTTF(String name, int size) {
            Font font;
            try {
                  InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/" + name + ".ttf")).getInputStream();
                  font = Font.createFont(0, is);
                  font = font.deriveFont(0, (float)size);
            } catch (Exception var4) {
                  var4.printStackTrace();
                  System.out.println("Error loading font");
                  font = new Font("default", 0, size);
            }

            return font;
      }

      private static Font getFontOTF(String name, int size) {
            Font font;
            try {
                  InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/" + name + ".otf")).getInputStream();
                  font = Font.createFont(0, is);
            } catch (Exception var4) {
            }

            font = new Font("default", 0, size);
            return font;
      }

      private static Font getDefault(int size) {
            Font font = new Font("default", 0, size);
            return font;
      }
}
