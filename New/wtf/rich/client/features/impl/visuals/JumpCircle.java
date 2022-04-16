package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class JumpCircle extends Feature {
     static final int TYPE = 0;
     static final byte MAX_JC_TIME = 20;
     static List circles = new ArrayList();
     private ListSetting jumpcircleMode = new ListSetting("JumpCircle Mode", "Default", () -> {
          return true;
     }, new String[]{"Default", "Disc"});
     public static ColorSetting jumpCircleColor = new ColorSetting("JumpCircle Color", (new Color(16777215)).getRGB(), () -> {
          return true;
     });
     static float pt;

     public JumpCircle() {
          super("JumpCircles", "Показывает круги после прыжка", 0, Category.VISUALS);
          this.addSettings(new Setting[]{this.jumpcircleMode, jumpCircleColor});
     }

     @EventTarget
     public void onJump(EventUpdate event) {
          if (mc.player.motionY == 0.33319999363422365D && !mc.player.killaurachecks()) {
               handleEntityJump(mc.player);
          }

          onLocalPlayerUpdate(mc.player);
     }

     @EventTarget
     public void onRender(Event3D event) {
          String mode = this.jumpcircleMode.getOptions();
          EntityPlayerSP client = Minecraft.getMinecraft().player;
          Minecraft mc = Minecraft.getMinecraft();
          double ix = -(client.lastTickPosX + (client.posX - client.lastTickPosX) * (double)mc.getRenderPartialTicks());
          double iy = -(client.lastTickPosY + (client.posY - client.lastTickPosY) * (double)mc.getRenderPartialTicks());
          double iz = -(client.lastTickPosZ + (client.posZ - client.lastTickPosZ) * (double)mc.getRenderPartialTicks());
          Iterator var11;
          JumpCircle.Circle c;
          float start;
          float end;
          int i;
          if (mode.equalsIgnoreCase("Disc")) {
               GL11.glPushMatrix();
               GL11.glTranslated(ix, iy, iz);
               GL11.glDisable(2884);
               GL11.glEnable(3042);
               GL11.glDisable(3553);
               GL11.glDisable(3008);
               GL11.glDisable(2929);
               GL11.glBlendFunc(770, 771);
               GL11.glShadeModel(7425);
               Collections.reverse(circles);

               try {
                    var11 = circles.iterator();

                    while(var11.hasNext()) {
                         c = (JumpCircle.Circle)var11.next();
                         float k = (float)c.existed / 20.0F;
                         double x = c.position().xCoord;
                         double y = c.position().yCoord - (double)k * 0.5D;
                         double z = c.position().zCoord;
                         start = k;
                         end = k + 1.0F - k;
                         GL11.glBegin(8);

                         for(i = 0; i <= 360; i += 5) {
                              GL11.glColor4f((float)c.color().xCoord, (float)c.color().yCoord, (float)c.color().zCoord, 0.2F * (1.0F - (float)c.existed / 20.0F));
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)(i * 4))) * (double)start, y, z + Math.sin(Math.toRadians((double)(i * 4))) * (double)start);
                              GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.01F * (1.0F - (float)c.existed / 20.0F));
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)i)) * (double)end, y + Math.sin((double)(k * 8.0F)) * 0.5D, z + Math.sin(Math.toRadians((double)i) * (double)end));
                         }

                         GL11.glEnd();
                    }
               } catch (Exception var23) {
               }

               Collections.reverse(circles);
               GL11.glEnable(3553);
               GL11.glDisable(3042);
               GL11.glShadeModel(7424);
               GL11.glEnable(2884);
               GL11.glEnable(2929);
               GL11.glEnable(3008);
               GL11.glPopMatrix();
          } else if (mode.equalsIgnoreCase("Default")) {
               GL11.glPushMatrix();
               GL11.glTranslated(ix, iy, iz);
               GL11.glDisable(2884);
               GL11.glEnable(3042);
               GL11.glDisable(3553);
               GL11.glDisable(3008);
               GL11.glBlendFunc(770, 771);
               GL11.glShadeModel(7425);
               Collections.reverse(circles);
               var11 = circles.iterator();

               while(var11.hasNext()) {
                    c = (JumpCircle.Circle)var11.next();
                    double x = c.position().xCoord;
                    double y = c.position().yCoord;
                    double z = c.position().zCoord;
                    float k = (float)c.existed / 20.0F;
                    start = k * 2.5F;
                    end = start + 1.0F - k;
                    GL11.glBegin(8);

                    for(i = 0; i <= 360; i += 5) {
                         GL11.glColor4f((float)c.color().xCoord, (float)c.color().yCoord, (float)c.color().zCoord, 0.7F * (1.0F - (float)c.existed / 20.0F));
                         switch(0) {
                         case 0:
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)i)) * (double)start, y, z + Math.sin(Math.toRadians((double)i)) * (double)start);
                              break;
                         case 1:
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)(i * 2))) * (double)start, y, z + Math.sin(Math.toRadians((double)(i * 2))) * (double)start);
                         }

                         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.01F * (1.0F - (float)c.existed / 20.0F));
                         switch(0) {
                         case 0:
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)i)) * (double)end, y, z + Math.sin(Math.toRadians((double)i)) * (double)end);
                              break;
                         case 1:
                              GL11.glVertex3d(x + Math.cos(Math.toRadians((double)(-i))) * (double)end, y, z + Math.sin(Math.toRadians((double)(-i))) * (double)end);
                         }
                    }

                    GL11.glEnd();
               }

               Collections.reverse(circles);
               GL11.glEnable(3553);
               GL11.glDisable(3042);
               GL11.glShadeModel(7424);
               GL11.glEnable(2884);
               GL11.glEnable(3008);
               GL11.glPopMatrix();
          }

     }

     public static void onLocalPlayerUpdate(EntityPlayerSP instance) {
          circles.removeIf(JumpCircle.Circle::update);
     }

     public static void handleEntityJump(Entity entity) {
          int red = (int)((float)(jumpCircleColor.getColorValue() >> 16 & 255) / 100.0F);
          int green = (int)((float)(jumpCircleColor.getColorValue() >> 8 & 255) / 100.0F);
          int blue = (int)((float)(jumpCircleColor.getColorValue() & 255) / 100.0F);
          Vec3d color = new Vec3d((double)red, (double)green, (double)blue);
          circles.add(new JumpCircle.Circle(entity.getPositionVector(), color));
     }

     static class Circle {
          private final Vec3d vec;
          private final Vec3d color;
          byte existed;

          Circle(Vec3d vec, Vec3d color) {
               this.vec = vec;
               this.color = color;
          }

          Vec3d position() {
               return this.vec;
          }

          Vec3d color() {
               return this.color;
          }

          boolean update() {
               return ++this.existed > 20;
          }
     }
}
