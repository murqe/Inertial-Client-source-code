package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Breadcrumbs extends Feature {
     ArrayList points = new ArrayList();
     ListSetting colorMode = new ListSetting("Trails Color", "Astolfo", () -> {
          return true;
     }, new String[]{"Astolfo", "Pulse", "Custom", "Client", "Static"});
     ColorSetting onecolor = new ColorSetting("One Color", (new Color(255, 255, 255)).getRGB(), () -> {
          return this.colorMode.currentMode.equalsIgnoreCase("Static") || this.colorMode.currentMode.equalsIgnoreCase("Custom");
     });
     ColorSetting twocolor = new ColorSetting("Two Color", (new Color(255, 255, 255)).getRGB(), () -> {
          return this.colorMode.currentMode.equalsIgnoreCase("Custom");
     });
     NumberSetting removeticks = new NumberSetting("Remove Ticks", "Задержка после которой будут пропадать трейлы", 10.0F, 10.0F, 500.0F, 1.0F, () -> {
          return true;
     });
     NumberSetting alpha = new NumberSetting("Alpha Trails", "Прозрачность", 255.0F, 1.0F, 255.0F, 1.0F, () -> {
          return true;
     });
     BooleanSetting smoothending = new BooleanSetting("Smooth Ending", true, () -> {
          return true;
     });

     public Breadcrumbs() {
          super("Trails", "Показывает линию взади вас", 0, Category.VISUALS);
          this.addSettings(new Setting[]{this.colorMode, this.onecolor, this.twocolor, this.removeticks, this.alpha, this.smoothending});
     }

     @EventTarget
     public void onUpdate(EventUpdate eventUpdate) {
          this.setModuleName("Trails " + TextFormatting.GRAY + this.colorMode.currentMode);
     }

     @EventTarget
     public void onRender(Event3D event) {
          this.points.removeIf((p) -> {
               return p.age >= this.removeticks.getNumberValue();
          });
          float x = (float)(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)event.getPartialTicks());
          float y = (float)(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)event.getPartialTicks());
          float z = (float)(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)event.getPartialTicks());
          this.points.add(new Breadcrumbs.Point(x, y, z));
          GL11.glPushMatrix();
          GL11.glDisable(3008);
          GL11.glEnable(3042);
          GL11.glEnable(2848);
          GL11.glDisable(3553);
          GL11.glBlendFunc(770, 771);
          GL11.glEnable(3042);
          GL11.glDisable(2884);
          Iterator var5 = this.points.iterator();

          while(var5.hasNext()) {
               Breadcrumbs.Point t = (Breadcrumbs.Point)var5.next();
               if (this.points.indexOf(t) < this.points.size() - 1) {
                    Breadcrumbs.Point temp = (Breadcrumbs.Point)this.points.get(this.points.indexOf(t) + 1);
                    float a = this.alpha.getNumberValue();
                    if (this.smoothending.getBoolValue()) {
                         a = this.alpha.getNumberValue() * ((float)this.points.indexOf(t) / (float)this.points.size());
                    }

                    Color color = Color.WHITE;
                    Color firstcolor = new Color(this.onecolor.getColorValue());
                    String var11 = this.colorMode.currentMode;
                    byte var12 = -1;
                    switch(var11.hashCode()) {
                    case -1808614770:
                         if (var11.equals("Static")) {
                              var12 = 4;
                         }
                         break;
                    case 77474681:
                         if (var11.equals("Pulse")) {
                              var12 = 2;
                         }
                         break;
                    case 961091784:
                         if (var11.equals("Astolfo")) {
                              var12 = 1;
                         }
                         break;
                    case 2021122027:
                         if (var11.equals("Client")) {
                              var12 = 0;
                         }
                         break;
                    case 2029746065:
                         if (var11.equals("Custom")) {
                              var12 = 3;
                         }
                    }

                    switch(var12) {
                    case 0:
                         color = ClientHelper.getClientColor(t.age / 16.0F, 5.0F, t.age, 5);
                         break;
                    case 1:
                         color = DrawHelper.astolfoColors45(t.age - t.age + 1.0F, t.age, 0.5F, 10.0F);
                         break;
                    case 2:
                         color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (double)(6.0F * (t.age / 16.0F) / 60.0F));
                         break;
                    case 3:
                         color = DrawHelper.TwoColoreffect(new Color(this.onecolor.getColorValue()), new Color(this.twocolor.getColorValue()), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (double)(3.0F * (t.age / 16.0F) / 60.0F));
                         break;
                    case 4:
                         color = firstcolor;
                    }

                    Color c = DrawHelper.setAlpha(color, (int)a);
                    GL11.glBegin(8);
                    double x2 = (double)t.x - RenderManager.renderPosX;
                    double y2 = (double)t.y - RenderManager.renderPosY;
                    double z2 = (double)t.z - RenderManager.renderPosZ;
                    double x1 = (double)temp.x - RenderManager.renderPosX;
                    double y1 = (double)temp.y - RenderManager.renderPosY;
                    double z1 = (double)temp.z - RenderManager.renderPosZ;
                    DrawHelper.glColor((new Color(c.getRed(), c.getGreen(), c.getBlue(), 0)).getRGB());
                    GL11.glVertex3d(x2, y2 + (double)mc.player.height - 0.1D, z2);
                    DrawHelper.glColor(c.getRGB());
                    GL11.glVertex3d(x2, y2 + 0.2D, z2);
                    GL11.glVertex3d(x1, y1 + (double)mc.player.height - 0.1D, z1);
                    GL11.glVertex3d(x1, y1 + 0.2D, z1);
                    GL11.glEnd();
                    ++t.age;
               }
          }

          GlStateManager.resetColor();
          GL11.glDisable(3042);
          GL11.glEnable(3008);
          GL11.glEnable(3553);
          GL11.glDisable(2848);
          GL11.glDisable(3042);
          GL11.glPopMatrix();
     }

     public void onDisable() {
          this.points.clear();
          super.onDisable();
     }

     public static Color getGradientOffset(Color color1, Color color2, double offset, int alpha) {
          double inverse_percent;
          int redPart;
          if (offset > 1.0D) {
               inverse_percent = offset % 1.0D;
               redPart = (int)offset;
               offset = redPart % 2 == 0 ? inverse_percent : 1.0D - inverse_percent;
          }

          inverse_percent = 1.0D - offset;
          redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
          int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
          int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
          return new Color(redPart, greenPart, bluePart, alpha);
     }

     class Point {
          public final float x;
          public final float y;
          public final float z;
          public float age = 0.0F;

          public Point(float x, float y, float z) {
               this.x = x;
               this.y = y;
               this.z = z;
          }
     }
}
