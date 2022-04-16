package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.utils.combat.RotationHelper;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class EntityESP extends Feature {
     public ListSetting espMode;
     public ListSetting rectMode;
     private final int black;
     private final ColorSetting colorEsp;
     private final ColorSetting triangleColor;
     private final BooleanSetting fullBox;
     private final BooleanSetting heathPercentage;
     private final BooleanSetting border;
     private final BooleanSetting healRect;
     private BooleanSetting triangleESP;
     private final BooleanSetting ignoreInvisible;
     private final ListSetting healcolorMode;
     private final ColorSetting healColor;
     private final ListSetting csgoMode;
     private final ListSetting colorMode;
     private final ListSetting triangleMode;
     private final NumberSetting xOffset;
     private final NumberSetting triangleFov;
     private final NumberSetting size;

     public EntityESP() {
          super("EntityESP", "Показывает игроков, ник и их здоровье сквозь стены", 0, Category.VISUALS);
          this.black = Color.BLACK.getRGB();
          this.healRect = new BooleanSetting("Health Rect", true, () -> {
               return true;
          });
          this.ignoreInvisible = new BooleanSetting("Ignore Invisible", true, () -> {
               return true;
          });
          this.healcolorMode = new ListSetting("Color Health Mode", "Custom", () -> {
               return this.healRect.getBoolValue();
          }, new String[]{"Astolfo", "Health", "Rainbow", "Client", "Custom"});
          this.healColor = new ColorSetting("Health Color", -1, () -> {
               return this.healcolorMode.currentMode.equals("Custom");
          });
          this.colorMode = new ListSetting("Color Box Mode", "Custom", () -> {
               return this.espMode.currentMode.equals("Box") || this.espMode.currentMode.equals("2D");
          }, new String[]{"Astolfo", "Rainbow", "Client", "Custom"});
          this.triangleMode = new ListSetting("Triangle Mode", "Custom", () -> {
               return this.triangleESP.getBoolValue();
          }, new String[]{"Astolfo", "Rainbow", "Client", "Custom"});
          this.espMode = new ListSetting("ESP Mode", "2D", () -> {
               return true;
          }, new String[]{"2D", "Box"});
          this.rectMode = new ListSetting("Rect Mode", "Default", () -> {
               return this.espMode.currentMode.equalsIgnoreCase("2D");
          }, new String[]{"Default", "Smooth"});
          this.border = new BooleanSetting("Border Rect", true, () -> {
               return this.espMode.currentMode.equals("2D");
          });
          this.csgoMode = new ListSetting("Border Mode", "Box", () -> {
               return this.espMode.currentMode.equals("2D") && this.border.getBoolValue();
          }, new String[]{"Box", "Corner"});
          this.colorEsp = new ColorSetting("ESP Color", (new Color(16777215)).getRGB(), () -> {
               return !this.colorMode.currentMode.equals("Client") && (this.espMode.currentMode.equals("2D") || this.espMode.currentMode.equals("Box") || this.espMode.currentMode.equals("Glow") || this.colorMode.currentMode.equalsIgnoreCase("Custom"));
          });
          this.fullBox = new BooleanSetting("Full Box", false, () -> {
               return this.espMode.currentMode.equals("Box");
          });
          this.heathPercentage = new BooleanSetting("Health Percentage", false, () -> {
               return this.espMode.currentMode.equals("2D");
          });
          this.triangleESP = new BooleanSetting("Triangle ESP", true, () -> {
               return true;
          });
          this.triangleColor = new ColorSetting("Triangle Color", Color.PINK.getRGB(), () -> {
               return this.triangleESP.getBoolValue() && this.triangleMode.currentMode.equals("Custom");
          });
          this.xOffset = new NumberSetting("Triangle XOffset", 10.0F, 0.0F, 50.0F, 5.0F, () -> {
               return this.triangleESP.getBoolValue();
          });
          this.triangleFov = new NumberSetting("Triangle FOV", 100.0F, 0.0F, 180.0F, 1.0F, () -> {
               return this.triangleESP.getBoolValue();
          });
          this.size = new NumberSetting("Triangle Size", 5.0F, 0.0F, 20.0F, 1.0F, () -> {
               return this.triangleESP.getBoolValue();
          });
          this.addSettings(new Setting[]{this.espMode, this.csgoMode, this.rectMode, this.colorMode, this.healcolorMode, this.healColor, this.colorEsp, this.border, this.fullBox, this.healRect, this.heathPercentage, this.ignoreInvisible, this.triangleESP, this.triangleMode, this.triangleColor, this.triangleFov, this.xOffset, this.size});
     }

     @EventTarget
     public void onRender3D(Event3D event3D) {
          if (this.isToggled()) {
               int color = 0;
               String var3 = this.colorMode.currentMode;
               byte var4 = -1;
               switch(var3.hashCode()) {
               case -1656737386:
                    if (var3.equals("Rainbow")) {
                         var4 = 3;
                    }
                    break;
               case 961091784:
                    if (var3.equals("Astolfo")) {
                         var4 = 2;
                    }
                    break;
               case 2021122027:
                    if (var3.equals("Client")) {
                         var4 = 0;
                    }
                    break;
               case 2029746065:
                    if (var3.equals("Custom")) {
                         var4 = 1;
                    }
               }

               switch(var4) {
               case 0:
                    color = ClientHelper.getClientColor().getRGB();
                    break;
               case 1:
                    color = this.colorEsp.getColorValue();
                    break;
               case 2:
                    color = DrawHelper.astolfo(false, 10).getRGB();
                    break;
               case 3:
                    color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
               }

               if (this.espMode.currentMode.equals("Box")) {
                    GlStateManager.pushMatrix();
                    Iterator var5 = mc.world.loadedEntityList.iterator();

                    while(var5.hasNext()) {
                         Entity entity = (Entity)var5.next();
                         if (entity instanceof EntityPlayer && entity != mc.player) {
                              DrawHelper.drawEntityBox(entity, new Color(color), this.fullBox.getBoolValue(), this.fullBox.getBoolValue() ? 0.15F : 0.9F);
                         }
                    }

                    GlStateManager.popMatrix();
               }
          }

     }

     @EventTarget
     public void onRenderTriangle(EventRender2D eventRender2D) {
          if (this.triangleESP.getBoolValue()) {
               int color = 0;
               String var3 = this.triangleMode.currentMode;
               byte var4 = -1;
               switch(var3.hashCode()) {
               case -1656737386:
                    if (var3.equals("Rainbow")) {
                         var4 = 3;
                    }
                    break;
               case 961091784:
                    if (var3.equals("Astolfo")) {
                         var4 = 2;
                    }
                    break;
               case 2021122027:
                    if (var3.equals("Client")) {
                         var4 = 0;
                    }
                    break;
               case 2029746065:
                    if (var3.equals("Custom")) {
                         var4 = 1;
                    }
               }

               switch(var4) {
               case 0:
                    color = ClientHelper.getClientColor().getRGB();
                    break;
               case 1:
                    color = this.triangleColor.getColorValue();
                    break;
               case 2:
                    color = DrawHelper.astolfo(false, 1).getRGB();
                    break;
               case 3:
                    color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
               }

               ScaledResolution sr = new ScaledResolution(mc);
               float size = 50.0F;
               float xOffset = (float)sr.getScaledWidth() / 2.0F - 24.5F;
               float yOffset = (float)sr.getScaledHeight() / 2.0F - 25.2F;
               Iterator var7 = mc.world.playerEntities.iterator();

               while(true) {
                    EntityPlayer entity;
                    do {
                         if (!var7.hasNext()) {
                              return;
                         }

                         entity = (EntityPlayer)var7.next();
                    } while(this.ignoreInvisible.getBoolValue() && entity.isInvisible());

                    if (entity != null && entity != mc.player) {
                         GlStateManager.pushMatrix();
                         GlStateManager.disableBlend();
                         double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.timer.renderPartialTicks;
                         mc.getRenderManager();
                         double x = var10000 - RenderManager.renderPosX;
                         var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
                         mc.getRenderManager();
                         double z = var10000 - RenderManager.renderPosZ;
                         double cos = Math.cos((double)mc.player.rotationYaw * 0.017453292519943295D);
                         double sin = Math.sin((double)mc.player.rotationYaw * 0.017453292519943295D);
                         double rotY = -(z * cos - x * sin);
                         double rotX = -(x * cos + z * sin);
                         if (MathHelper.sqrt(rotX * rotX + rotY * rotY) < size) {
                              float angle = (float)(Math.atan2(rotY, rotX) * 180.0D / 3.141592653589793D);
                              double xPos = (double)(size / 2.0F) * Math.cos(Math.toRadians((double)angle)) + (double)xOffset + (double)(size / 2.0F);
                              double y = (double)(size / 2.0F) * Math.sin(Math.toRadians((double)angle)) + (double)yOffset + (double)(size / 2.0F);
                              GlStateManager.translate(xPos, y, 0.0D);
                              GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
                              GlStateManager.scale(1.5D, 1.0D, 1.0D);
                              if (RotationHelper.canSeeEntityAtFov(entity, this.triangleFov.getNumberValue())) {
                                   if (Main.instance.friendManager.isFriend(entity.getName())) {
                                        DrawHelper.drawTriangle(this.xOffset.getNumberValue(), 0.0F, this.size.getNumberValue(), 3.0F, (new Color(0, 255, 0)).getRGB());
                                   } else {
                                        DrawHelper.drawTriangle(this.xOffset.getNumberValue(), 0.0F, this.size.getNumberValue(), 3.0F, (new Color(color)).getRGB());
                                   }
                              }
                         }

                         GlStateManager.resetColor();
                         GlStateManager.popMatrix();
                    }
               }
          }
     }

     @EventTarget
     public void onRender2D(EventRender2D event) {
          String mode = this.espMode.getOptions();
          this.setSuffix(mode);
          float partialTicks = mc.timer.renderPartialTicks;
          event.getResolution();
          int scaleFactor = ScaledResolution.getScaleFactor();
          double scaling = (double)scaleFactor / Math.pow((double)scaleFactor, 2.0D);
          GL11.glPushMatrix();
          GlStateManager.scale(scaling, scaling, scaling);
          int color = 0;
          String var8 = this.colorMode.currentMode;
          byte var9 = -1;
          switch(var8.hashCode()) {
          case -1656737386:
               if (var8.equals("Rainbow")) {
                    var9 = 3;
               }
               break;
          case 961091784:
               if (var8.equals("Astolfo")) {
                    var9 = 2;
               }
               break;
          case 2021122027:
               if (var8.equals("Client")) {
                    var9 = 0;
               }
               break;
          case 2029746065:
               if (var8.equals("Custom")) {
                    var9 = 1;
               }
          }

          switch(var9) {
          case 0:
               color = ClientHelper.getClientColor().getRGB();
               break;
          case 1:
               color = this.colorEsp.getColorValue();
               break;
          case 2:
               color = DrawHelper.astolfo(false, 1).getRGB();
               break;
          case 3:
               color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
          }

          Iterator var42 = mc.world.loadedEntityList.iterator();

          while(true) {
               Vector4d position;
               Entity entity;
               double posX;
               double posY;
               double endPosX;
               double endPosY;
               do {
                    do {
                         do {
                              do {
                                   do {
                                        do {
                                             if (!var42.hasNext()) {
                                                  GL11.glEnable(2929);
                                                  GlStateManager.enableBlend();
                                                  GL11.glPopMatrix();
                                                  mc.entityRenderer.setupOverlayRendering();
                                                  return;
                                             }

                                             entity = (Entity)var42.next();
                                        } while(!(entity instanceof EntityPlayer));
                                   } while(entity == mc.player);
                              } while(this.ignoreInvisible.getBoolValue() && entity.isInvisible());
                         } while(!this.isValid(entity));
                    } while(!DrawHelper.isInViewFrustum(entity));

                    posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks();
                    posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks();
                    endPosX = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks();
                    endPosY = (double)entity.width / 1.5D;
                    double height = (double)entity.height + (entity.isSneaking() || entity == mc.player && mc.player.isSneaking() ? -0.3D : 0.2D);
                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(posX - endPosY, posY, endPosX - endPosY, posX + endPosY, posY + height, endPosX + endPosY);
                    List vectors = Arrays.asList(new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
                    mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                    position = null;
                    Iterator var23 = vectors.iterator();

                    while(var23.hasNext()) {
                         Vector3d vector = (Vector3d)var23.next();
                         double var10002 = vector.x;
                         mc.getRenderManager();
                         var10002 -= RenderManager.renderPosX;
                         double var10003 = vector.y;
                         mc.getRenderManager();
                         var10003 -= RenderManager.renderPosY;
                         double var10004 = vector.z;
                         mc.getRenderManager();
                         vector = this.vectorRender2D(scaleFactor, var10002, var10003, var10004 - RenderManager.renderPosZ);
                         if (vector != null && vector.z > 0.0D && vector.z < 1.0D) {
                              if (position == null) {
                                   position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
                              }

                              position.x = Math.min(vector.x, position.x);
                              position.y = Math.min(vector.y, position.y);
                              position.z = Math.max(vector.x, position.z);
                              position.w = Math.max(vector.y, position.w);
                         }
                    }
               } while(position == null);

               mc.entityRenderer.setupOverlayRendering();
               posX = position.x;
               posY = position.y;
               endPosX = position.z;
               endPosY = position.w;
               if (this.border.getBoolValue()) {
                    if (mode.equalsIgnoreCase("2D") && this.csgoMode.currentMode.equalsIgnoreCase("Box") && this.rectMode.currentMode.equalsIgnoreCase("Smooth")) {
                         DrawHelper.drawSmoothRect(posX - 0.5D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 1.0D, this.black);
                         DrawHelper.drawSmoothRect(posX - 0.5D, endPosY - 0.5D - 1.0D, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX - 1.5D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(endPosX - 0.5D - 1.0D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX - 1.0D, posY, posX + 0.5D - 0.5D, endPosY, color);
                         DrawHelper.drawSmoothRect(posX, endPosY - 1.0D, endPosX, endPosY, color);
                         DrawHelper.drawSmoothRect(posX - 1.0D, posY, endPosX, posY + 1.0D, color);
                         DrawHelper.drawSmoothRect(endPosX - 1.0D, posY, endPosX, endPosY, color);
                    } else if (mode.equalsIgnoreCase("2D") && this.csgoMode.currentMode.equalsIgnoreCase("Corner") && this.rectMode.currentMode.equalsIgnoreCase("Smooth")) {
                         DrawHelper.drawSmoothRect(posX + 1.0D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX - 1.0D, endPosY, posX + 1.0D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D, posY + 1.0D, this.black);
                         DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D - 0.0D, posY - 0.5D, endPosX, posY + 1.5D, this.black);
                         DrawHelper.drawSmoothRect(endPosX - 1.5D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(endPosX - 1.5D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX - 1.0D, endPosY - 1.5D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawSmoothRect(posX + 0.5D, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawSmoothRect(posX + 0.5D, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawSmoothRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 1.0D, color);
                         DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D + 0.5D, posY, endPosX, posY + 1.0D, color);
                         DrawHelper.drawSmoothRect(endPosX - 1.0D, posY, endPosX, posY + (endPosY - posY) / 4.0D + 0.5D, color);
                         DrawHelper.drawSmoothRect(endPosX - 1.0D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawSmoothRect(posX, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D, endPosY, color);
                         DrawHelper.drawSmoothRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 1.0D, endPosX - 0.5D, endPosY, color);
                    } else if (mode.equalsIgnoreCase("2D") && this.csgoMode.currentMode.equalsIgnoreCase("Box") && this.rectMode.currentMode.equalsIgnoreCase("Default")) {
                         DrawHelper.drawNewRect(posX - 0.5D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 1.0D, this.black);
                         DrawHelper.drawNewRect(posX - 0.5D, endPosY - 0.5D - 1.0D, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawNewRect(posX - 1.5D, posY, posX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawNewRect(endPosX - 0.5D - 1.0D, posY, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawNewRect(posX - 1.0D, posY, posX + 0.5D - 0.5D, endPosY, color);
                         DrawHelper.drawNewRect(posX, endPosY - 1.0D, endPosX, endPosY, color);
                         DrawHelper.drawNewRect(posX - 1.0D, posY, endPosX, posY + 1.0D, color);
                         DrawHelper.drawNewRect(endPosX - 1.0D, posY, endPosX, endPosY, color);
                    } else if (mode.equalsIgnoreCase("2D") && this.csgoMode.currentMode.equalsIgnoreCase("Corner") && this.rectMode.currentMode.equalsIgnoreCase("Default")) {
                         DrawHelper.drawNewRect(posX + 1.0D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
                         DrawHelper.drawNewRect(posX - 1.0D, endPosY, posX + 1.0D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
                         DrawHelper.drawNewRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D, posY + 1.0D, this.black);
                         DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D - 0.0D, posY - 0.5D, endPosX, posY + 1.5D, this.black);
                         DrawHelper.drawNewRect(endPosX - 1.5D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, this.black);
                         DrawHelper.drawNewRect(endPosX - 1.5D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, this.black);
                         DrawHelper.drawNewRect(posX - 1.0D, endPosY - 1.5D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.5D, endPosX + 0.5D, endPosY + 0.5D, this.black);
                         DrawHelper.drawNewRect(posX + 0.5D, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawNewRect(posX + 0.5D, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawNewRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 1.0D, color);
                         DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D + 0.5D, posY, endPosX, posY + 1.0D, color);
                         DrawHelper.drawNewRect(endPosX - 1.0D, posY, endPosX, posY + (endPosY - posY) / 4.0D + 0.5D, color);
                         DrawHelper.drawNewRect(endPosX - 1.0D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
                         DrawHelper.drawNewRect(posX, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D, endPosY, color);
                         DrawHelper.drawNewRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 1.0D, endPosX - 0.5D, endPosY, color);
                    }
               }

               boolean living = entity instanceof EntityLivingBase;
               EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
               float targetHP = entityLivingBase.getHealth();
               targetHP = MathHelper.clamp(targetHP, 0.0F, 24.0F);
               float maxHealth = entityLivingBase.getMaxHealth();
               double hpPercentage = (double)(targetHP / maxHealth);
               double hpHeight2 = (endPosY - posY) * hpPercentage;
               if (living && this.healRect.getBoolValue() && !this.espMode.currentMode.equals("Box")) {
                    int colorHeal = 0;
                    String string2 = this.healcolorMode.currentMode;
                    byte var41 = -1;
                    switch(string2.hashCode()) {
                    case -2137395588:
                         if (string2.equals("Health")) {
                              var41 = 4;
                         }
                         break;
                    case -1656737386:
                         if (string2.equals("Rainbow")) {
                              var41 = 3;
                         }
                         break;
                    case 961091784:
                         if (string2.equals("Astolfo")) {
                              var41 = 2;
                         }
                         break;
                    case 2021122027:
                         if (string2.equals("Client")) {
                              var41 = 0;
                         }
                         break;
                    case 2029746065:
                         if (string2.equals("Custom")) {
                              var41 = 1;
                         }
                    }

                    switch(var41) {
                    case 0:
                         colorHeal = ClientHelper.getClientColor().getRGB();
                         break;
                    case 1:
                         colorHeal = this.healColor.getColorValue();
                         break;
                    case 2:
                         colorHeal = DrawHelper.astolfo(false, (int)entity.height).getRGB();
                         break;
                    case 3:
                         colorHeal = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
                         break;
                    case 4:
                         colorHeal = DrawHelper.getHealthColor(((EntityLivingBase)entity).getHealth(), ((EntityLivingBase)entity).getMaxHealth());
                    }

                    if (targetHP > 0.0F) {
                         string2 = "" + MathematicHelper.round(entityLivingBase.getHealth() / entityLivingBase.getMaxHealth() * 20.0F, 1);
                         if (living && this.heathPercentage.getBoolValue() && !this.espMode.currentMode.equals("Box") && this.heathPercentage.getBoolValue()) {
                              GlStateManager.pushMatrix();
                              mc.sfui18.drawStringWithOutline(string2, (double)((float)posX - 30.0F), (double)((float)((double)((float)endPosY) - hpHeight2)), -1);
                              GlStateManager.popMatrix();
                         }

                         DrawHelper.drawRect(posX - 5.0D, posY - 0.5D, posX - 2.5D, endPosY + 0.5D, (new Color(0, 0, 0, 125)).getRGB());
                         DrawHelper.drawRect(posX - 4.5D, endPosY, posX - 3.0D, endPosY - hpHeight2, colorHeal);
                    }
               }
          }
     }

     private boolean isValid(Entity entity) {
          if (mc.gameSettings.thirdPersonView == 0 && entity == mc.player) {
               return false;
          } else if (entity.isDead) {
               return false;
          } else if (entity instanceof EntityAnimal) {
               return false;
          } else if (entity instanceof EntityPlayer) {
               return true;
          } else if (entity instanceof EntityArmorStand) {
               return false;
          } else if (entity instanceof IAnimals) {
               return false;
          } else if (entity instanceof EntityItemFrame) {
               return false;
          } else if (entity instanceof EntityArrow) {
               return false;
          } else if (entity instanceof EntityMinecart) {
               return false;
          } else if (entity instanceof EntityBoat) {
               return false;
          } else if (entity instanceof EntityDragonFireball) {
               return false;
          } else if (entity instanceof EntityXPOrb) {
               return false;
          } else if (entity instanceof EntityTNTPrimed) {
               return false;
          } else if (entity instanceof EntityExpBottle) {
               return false;
          } else if (entity instanceof EntityLightningBolt) {
               return false;
          } else if (entity instanceof EntityPotion) {
               return false;
          } else if (entity instanceof Entity) {
               return false;
          } else if (!(entity instanceof EntityMob) && !(entity instanceof EntitySlime) && !(entity instanceof EntityDragon) && !(entity instanceof EntityGolem)) {
               return entity != mc.player;
          } else {
               return false;
          }
     }

     private Vector3d vectorRender2D(int scaleFactor, double x, double y, double z) {
          float xPos = (float)x;
          float yPos = (float)y;
          float zPos = (float)z;
          IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
          FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
          FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
          FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
          GL11.glGetFloat(2982, modelview);
          GL11.glGetFloat(2983, projection);
          GL11.glGetInteger(2978, viewport);
          return GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector) ? new Vector3d((double)(vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - vector.get(1)) / (float)scaleFactor), (double)vector.get(2)) : null;
     }
}
