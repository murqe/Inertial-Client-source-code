package wtf.rich.api.utils.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FontUtil {
     public static Font getFontFromTTF(ResourceLocation loc, float fontSize, int fontType) {
          try {
               Font output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream());
               output = output.deriveFont(fontSize);
               return output;
          } catch (Exception var4) {
               return null;
          }
     }
}
