package me.rich.module.render;

import de.Hero.settings.Setting;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ESP extends Feature {
      public final List collectedEntities = new ArrayList();
      private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
      private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
      private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
      private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
      private final int black;

      public void setup() {
            new ArrayList();
            Main.settingsManager.rSetting(new Setting("ESPBackground", this, true));
            Main.settingsManager.rSetting(new Setting("Model", this, true));
            Main.settingsManager.rSetting(new Setting("HealthLine", this, true));
            Main.settingsManager.rSetting(new Setting("BorderLine", this, true));
            Main.settingsManager.rSetting(new Setting("ItemsLine", this, true));
            Main.settingsManager.rSetting(new Setting("Pinky", this, true));
      }

      public ESP() {
            super("ESP", 0, Category.RENDER);
            this.black = Color.BLACK.getRGB();
      }

      @EventTarget
      public void onRender2D(EventRender2D render) {
            this.setModuleName("ESP");
            GL11.glPushMatrix();
            this.collectEntities();
            float partialTicks = render.getPartialTicks();
            ScaledResolution scaledResolution = render.getResolution();
            int scaleFactor = ScaledResolution.getScaleFactor();
            double scaling = (double)scaleFactor / Math.pow((double)scaleFactor, 2.0D);
            GL11.glScaled(scaling, scaling, scaling);
            int black = this.black;
            RenderManager renderMng = mc.getRenderManager();
            EntityRenderer entityRenderer = mc.entityRenderer;
            List collectedEntities = this.collectedEntities;
            Iterator var11 = collectedEntities.iterator();

            while(true) {
                  double endPosY;
                  int[] counter;
                  EntityLivingBase entityLivingBase;
                  float hp2;
                  double hpHeight2;
                  int healthColor2;
                  Minecraft var46;
                  double posX;
                  do {
                        while(true) {
                              Entity entity;
                              boolean living;
                              double posY;
                              do {
                                    double endPosX;
                                    do {
                                          Vector4d position;
                                          do {
                                                do {
                                                      do {
                                                            if (!var11.hasNext()) {
                                                                  GL11.glPopMatrix();
                                                                  GL11.glEnable(2929);
                                                                  GlStateManager.enableBlend();
                                                                  entityRenderer.setupOverlayRendering();
                                                                  return;
                                                            }

                                                            entity = (Entity)var11.next();
                                                      } while(!this.isValid(entity));
                                                } while(!RenderUtil.isInViewFrustrum(entity));

                                                double x;
                                                double y;
                                                double var10000;
                                                double z;
                                                double var10001;
                                                double width;
                                                label89: {
                                                      x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, (double)partialTicks);
                                                      y = RenderUtil.interpolate(entity.posY, entity.lastTickPosY, (double)partialTicks);
                                                      z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, (double)partialTicks);
                                                      width = (double)entity.width / 1.5D;
                                                      var10000 = (double)entity.height;
                                                      if (!entity.isSneaking()) {
                                                            Minecraft var10002 = mc;
                                                            if (entity != Minecraft.player) {
                                                                  var10001 = 0.2D;
                                                                  break label89;
                                                            }
                                                      }

                                                      var10001 = -0.3D;
                                                }

                                                double height = var10000 + var10001;
                                                AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                                                Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                                                entityRenderer.setupCameraTransform(partialTicks, 0);
                                                position = null;
                                                Vector3d[] var26 = vectors;
                                                int var27 = vectors.length;

                                                for(int var28 = 0; var28 < var27; ++var28) {
                                                      Vector3d vector = var26[var28];
                                                      vector = this.project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                                                      if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
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

                                          entityRenderer.setupOverlayRendering();
                                          posX = position.x;
                                          posY = position.y;
                                          endPosX = position.z;
                                          endPosY = position.w;
                                          counter = new int[]{1};
                                          Minecraft var47 = mc;
                                    } while(entity == Minecraft.player);

                                    if (Main.settingsManager.getSettingByName("BorderLine").getValBoolean() && Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "Pinky").getValBoolean()) {
                                          RenderUtil.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
                                          RenderUtil.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
                                          RenderUtil.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
                                          RenderUtil.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
                                          RenderUtil.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
                                          if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "ESPBackground").getValBoolean()) {
                                                RenderUtil.drawRect(1.0D + posX - 0.5D, posY + 1.0D, endPosX - 1.0D, endPosY - 1.0D, (new Color(40, 40, 40, 115)).getRGB());
                                          }
                                    } else if (Main.settingsManager.getSettingByName("BorderLine").getValBoolean() && !Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "Pinky").getValBoolean()) {
                                          RenderUtil.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
                                          RenderUtil.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
                                          RenderUtil.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, (new Color(255, 255, 255)).getRGB());
                                          RenderUtil.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, (new Color(255, 255, 255)).getRGB());
                                          RenderUtil.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, (new Color(255, 255, 255)).getRGB());
                                          RenderUtil.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, (new Color(255, 255, 255)).getRGB());
                                          if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "ESPBackground").getValBoolean()) {
                                                RenderUtil.drawRect(1.0D + posX - 0.5D, posY + 1.0D, endPosX - 1.0D, endPosY - 1.0D, (new Color(40, 40, 40, 115)).getRGB());
                                          }
                                    }

                                    living = entity instanceof EntityLivingBase;
                              } while(!living);

                              float maxHealth;
                              double hpPercentage;
                              float absorption;
                              if (Main.settingsManager.getSettingByName("HealthLine").getValBoolean() && Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "Pinky").getValBoolean()) {
                                    entityLivingBase = (EntityLivingBase)entity;
                                    hp2 = entityLivingBase.getHealth();
                                    maxHealth = entityLivingBase.getMaxHealth();
                                    hpPercentage = (double)(hp2 / maxHealth);
                                    absorption = entityLivingBase.getAbsorptionAmount();
                                    hpHeight2 = (endPosY - posY) * hpPercentage;
                                    new ScaledResolution(mc);
                                    RenderUtil.drawRect(posX - 3.8D, endPosY - hpHeight2 - 0.5D, posX - 1.5D, endPosY + 0.5D, black);
                                    break;
                              }

                              if (Main.settingsManager.getSettingByName("HealthLine").getValBoolean() && !Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ESP.class), "Pinky").getValBoolean()) {
                                    entityLivingBase = (EntityLivingBase)entity;
                                    hp2 = entityLivingBase.getHealth();
                                    maxHealth = entityLivingBase.getMaxHealth();
                                    hpPercentage = (double)(hp2 / maxHealth);
                                    absorption = entityLivingBase.getAbsorptionAmount();
                                    hpHeight2 = (endPosY - posY) * hpPercentage;
                                    new ScaledResolution(mc);
                                    RenderUtil.drawRect(posX - 3.8D, endPosY - hpHeight2 - 0.5D, posX - 1.5D, endPosY + 0.5D, black);
                                    if (hp2 > 0.0F) {
                                          healthColor2 = (int)hp2;
                                          if (hp2 > 12.0F) {
                                                healthColor2 = (new Color(52, 125, 52)).getRGB();
                                          } else if (hp2 < 12.0F && hp2 > 6.0F) {
                                                healthColor2 = (new Color(177, 108, 64)).getRGB();
                                          } else if (hp2 < 6.0F) {
                                                healthColor2 = (new Color(173, 57, 57)).getRGB();
                                          }

                                          var46 = mc;
                                          if (Minecraft.player.getDistanceToEntity(entityLivingBase) < 20.0F) {
                                                Fonts.roboto_13.drawBorderedString(MathHelper.floor(hp2) + "hp", posX - 22.0D, endPosY - hpHeight2 + 2.0D, healthColor2);
                                          }

                                          RenderUtil.drawRect(posX - 3.3D, endPosY, posX - 2.0D, endPosY - hpHeight2, healthColor2);
                                    }
                              }
                        }
                  } while(hp2 <= 0.0F);

                  healthColor2 = (int)hp2;
                  if (hp2 > 12.0F) {
                        healthColor2 = (new Color(52, 125, 52)).getRGB();
                  } else if (hp2 < 12.0F && hp2 > 6.0F) {
                        healthColor2 = (new Color(177, 108, 64)).getRGB();
                  } else if (hp2 < 6.0F) {
                        healthColor2 = (new Color(173, 57, 57)).getRGB();
                  }

                  var46 = mc;
                  if (Minecraft.player.getDistanceToEntity(entityLivingBase) < 20.0F) {
                        Fonts.roboto_13.drawBorderedString(MathHelper.floor(hp2) + "hp", posX - 22.0D, endPosY - hpHeight2 + 2.0D, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
                  }

                  RenderUtil.drawRect(posX - 3.3D, endPosY, posX - 2.0D, endPosY - hpHeight2, TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), (double)Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 6.0D * (double)counter[0] * 2.55D / 90.0D).getRGB());
            }
      }

      private boolean isValid(Entity entity) {
            Minecraft var10001;
            if (mc.gameSettings.thirdPersonView == 0) {
                  var10001 = mc;
                  if (entity == Minecraft.player) {
                        return false;
                  }
            }

            if (entity.isDead) {
                  return false;
            } else {
                  if (entity instanceof EntityItem) {
                        Minecraft var10000 = mc;
                        if (Minecraft.player.getDistanceToEntity(entity) < 30.0F && Main.settingsManager.getSettingByName("ItemsLine").getValBoolean()) {
                              return true;
                        }
                  }

                  if (entity instanceof EntityAnimal) {
                        return false;
                  } else if (entity instanceof EntityPlayer) {
                        return true;
                  } else if (entity instanceof EntityArmorStand) {
                        return false;
                  } else if (entity instanceof IAnimals) {
                        return false;
                  } else if (entity instanceof EntityItemFrame) {
                        return false;
                  } else if (!(entity instanceof EntityArrow) && !(entity instanceof EntitySpectralArrow)) {
                        if (entity instanceof EntityMinecart) {
                              return false;
                        } else if (entity instanceof EntityBoat) {
                              return false;
                        } else if (entity instanceof EntityDragonFireball) {
                              return false;
                        } else if (entity instanceof EntityXPOrb) {
                              return false;
                        } else if (entity instanceof EntityMinecartChest) {
                              return false;
                        } else if (entity instanceof EntityTNTPrimed) {
                              return false;
                        } else if (entity instanceof EntityMinecartTNT) {
                              return false;
                        } else if (entity instanceof EntityVillager) {
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
                              var10001 = mc;
                              return entity != Minecraft.player;
                        } else {
                              return false;
                        }
                  } else {
                        return false;
                  }
            }
      }

      private void collectEntities() {
            this.collectedEntities.clear();
            List playerEntities = mc.world.loadedEntityList;
            int i = 0;

            for(int playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; ++i) {
                  Entity entity = (Entity)playerEntities.get(i);
                  if (this.isValid(entity)) {
                        this.collectedEntities.add(entity);
                  }
            }

      }

      private Vector3d project2D(int scaleFactor, double x, double y, double z) {
            GL11.glGetFloat(2982, this.modelview);
            GL11.glGetFloat(2983, this.projection);
            GL11.glGetInteger(2978, this.viewport);
            return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d((double)(this.vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor), (double)this.vector.get(2)) : null;
      }

      public static Color TwoColoreffect(Color color, Color color2, double delay) {
            double n3;
            if (delay > 1.0D) {
                  n3 = delay % 1.0D;
                  delay = (int)delay % 2 == 0 ? n3 : 1.0D - n3;
            }

            n3 = 1.0D - delay;
            return new Color((int)((double)color.getRed() * n3 + (double)color2.getRed() * delay), (int)((double)color.getGreen() * n3 + (double)color2.getGreen() * delay), (int)((double)color.getBlue() * n3 + (double)color2.getBlue() * delay), (int)((double)color.getAlpha() * n3 + (double)color2.getAlpha() * delay));
      }

      public void onEnable() {
            super.onEnable();
            NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
      }

      public void onDisable() {
            NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
            super.onDisable();
      }
}
