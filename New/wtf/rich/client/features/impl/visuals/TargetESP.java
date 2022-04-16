package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class TargetESP extends Feature {
     private double circleAnim;
     double height;
     boolean animat;
     public ListSetting bebraPonyxana = new ListSetting("TargetESP Mode", "Jello", () -> {
          return true;
     }, new String[]{"Jello", "Sims", "Astolfo"});
     public NumberSetting circlesize = new NumberSetting("Circle Size", "Размер круга", 0.4F, 0.1F, 3.0F, 0.1F, () -> {
          return this.bebraPonyxana.currentMode.equalsIgnoreCase("Jello") || this.bebraPonyxana.currentMode.equalsIgnoreCase("Astolfo");
     });
     public NumberSetting points = new NumberSetting("Points", 30.0F, 3.0F, 30.0F, 1.0F, () -> {
          return this.bebraPonyxana.currentMode.equalsIgnoreCase("Astolfo");
     });
     public BooleanSetting depthTest = new BooleanSetting("DepthTest", "Глубина(test)", false, () -> {
          return this.bebraPonyxana.currentMode.equalsIgnoreCase("Jello");
     });
     public ColorSetting targetEspColor;

     public TargetESP() {
          super("TargetESP", "Рисует красивый круг на энтити", 0, Category.VISUALS);
          this.targetEspColor = new ColorSetting("TargetESP Color", Color.RED.getRGB(), () -> {
               return true;
          });
          this.addSettings(new Setting[]{this.bebraPonyxana, this.circlesize, this.points, this.targetEspColor, this.depthTest});
     }

     @EventTarget
     public void onRender(Event3D event3D) {
          String mode = this.bebraPonyxana.getOptions();
          this.setSuffix(mode);
          if (KillAura.target != null && (double)KillAura.target.getHealth() > 0.0D && mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getNumberValue() && Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled()) {
               if (mode.equalsIgnoreCase("Sims")) {
                    float radius = 0.2F;
                    int side = 4;
                    if (this.animat) {
                         this.height = (double)MathHelper.lerp(this.height, 0.4D, 2.0D * Feature.deltaTime());
                         if (this.height > 0.39D) {
                              this.animat = false;
                         }
                    } else {
                         this.height = (double)MathHelper.lerp(this.height, 0.1D, 4.0D * Feature.deltaTime());
                         if (this.height < 0.11D) {
                              this.animat = true;
                         }
                    }

                    GL11.glPushMatrix();
                    GL11.glTranslated(KillAura.target.lastTickPosX + (KillAura.target.posX - KillAura.target.lastTickPosX) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosX, KillAura.target.lastTickPosY + (KillAura.target.posY - KillAura.target.lastTickPosY) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosY + (double)KillAura.target.height + this.height, KillAura.target.lastTickPosZ + (KillAura.target.posZ - KillAura.target.lastTickPosZ) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosZ);
                    GL11.glRotatef(((float)mc.player.ticksExisted + mc.timer.renderPartialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
                    DrawHelper.setColor(KillAura.target.hurtTime > 0 ? DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), (double)Math.abs(System.currentTimeMillis() / 15L) / 100.0D + 0.255D).getRGB() : DrawHelper.TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), (double)Math.abs(System.currentTimeMillis() / 15L) / 100.0D + 0.16999999999999998D).getRGB());
                    DrawHelper.enableSmoothLine(0.5F);
                    Cylinder c = new Cylinder();
                    c.setDrawStyle(100011);
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                    c.draw(0.0F, radius, 0.3F, side, 100);
                    GL11.glTranslated(0.0D, 0.0D, 0.3D);
                    c.draw(radius, 0.0F, 0.3F, side, 100);
                    DrawHelper.disableSmoothLine();
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
               } else if (mode.equalsIgnoreCase("Jello")) {
                    double everyTime = 1500.0D;
                    double drawTime = (double)System.currentTimeMillis() % everyTime;
                    boolean drawMode = drawTime > everyTime / 2.0D;
                    double drawPercent = drawTime / (everyTime / 2.0D);
                    if (!drawMode) {
                         drawPercent = 1.0D - drawPercent;
                    } else {
                         --drawPercent;
                    }

                    drawPercent = MathHelper.easeInOutQuad(drawPercent, 2);
                    mc.entityRenderer.disableLightmap();
                    GL11.glPushMatrix();
                    GL11.glDisable(3553);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(2848);
                    GL11.glEnable(3042);
                    if (this.depthTest.getBoolValue()) {
                         GL11.glDisable(2929);
                    }

                    GL11.glDisable(2884);
                    GL11.glShadeModel(7425);
                    mc.entityRenderer.disableLightmap();
                    double radius = (double)this.circlesize.getNumberValue();
                    double height = (double)KillAura.target.height + 0.1D;
                    double x = KillAura.target.lastTickPosX + (KillAura.target.posX - KillAura.target.lastTickPosX) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosX;
                    double y = KillAura.target.lastTickPosY + (KillAura.target.posY - KillAura.target.lastTickPosY) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosY + height * drawPercent;
                    double z = KillAura.target.lastTickPosZ + (KillAura.target.posZ - KillAura.target.lastTickPosZ) * (double)event3D.getPartialTicks() - mc.renderManager.viewerPosZ;
                    double eased = height / 3.0D * (drawPercent > 0.5D ? 1.0D - drawPercent : drawPercent) * (double)(drawMode ? -1 : 1);

                    for(int lox = 0; lox < 360; lox += 5) {
                         double x1 = x - Math.sin((double)lox * 3.141592653589793D / 180.0D) * radius;
                         double z1 = z + Math.cos((double)lox * 3.141592653589793D / 180.0D) * radius;
                         double x2 = x - Math.sin((double)(lox - 5) * 3.141592653589793D / 180.0D) * radius;
                         double z2 = z + Math.cos((double)(lox - 5) * 3.141592653589793D / 180.0D) * radius;
                         GL11.glBegin(7);
                         DrawHelper.glColor(this.targetEspColor.getColorValue(), 0.0F);
                         GL11.glVertex3d(x1, y + eased, z1);
                         GL11.glVertex3d(x2, y + eased, z2);
                         DrawHelper.glColor(this.targetEspColor.getColorValue(), 255);
                         GL11.glVertex3d(x2, y, z2);
                         GL11.glVertex3d(x1, y, z1);
                         GL11.glEnd();
                         GL11.glBegin(2);
                         GL11.glVertex3d(x2, y, z2);
                         GL11.glVertex3d(x1, y, z1);
                         GL11.glEnd();
                    }

                    GL11.glEnable(2884);
                    GL11.glShadeModel(7424);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    if (this.depthTest.getBoolValue()) {
                         GL11.glEnable(2929);
                    }

                    GL11.glDisable(2848);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glPopMatrix();
               } else if (mode.equalsIgnoreCase("Astolfo") && KillAura.target != null) {
                    if (KillAura.target.getHealth() > 0.0F) {
                         this.circleAnim += 0.014999999664723873D * Minecraft.frameTime / 10.0D;
                         DrawHelper.drawCircle3D(KillAura.target, this.circleAnim + 0.001D, event3D.getPartialTicks(), (int)this.points.getNumberValue(), 4.0F, Color.black.getRGB());
                         DrawHelper.drawCircle3D(KillAura.target, this.circleAnim - 0.001D, event3D.getPartialTicks(), (int)this.points.getNumberValue(), 4.0F, Color.black.getRGB());
                         DrawHelper.drawCircle3D(KillAura.target, this.circleAnim, event3D.getPartialTicks(), (int)this.points.getNumberValue(), 2.0F, this.targetEspColor.getColorValue());
                         this.circleAnim = MathHelper.clamp(this.circleAnim, 0.0D, (double)(this.circlesize.getNumberValue() * 0.5F));
                    } else {
                         this.circleAnim = 0.0D;
                    }
               }
          }

     }
}
