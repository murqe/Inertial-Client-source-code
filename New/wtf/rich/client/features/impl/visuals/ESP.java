package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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
import net.minecraft.entity.item.EntityEnderCrystal;
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
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.api.event.event.EventNameTags;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.api.utils.shader.FramebufferShader;
import wtf.rich.api.utils.shader.shaders.EntityGlowShader;
import wtf.rich.api.utils.shader.shaders.FlowShader;
import wtf.rich.api.utils.shader.shaders.OutlineShader;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class ESP extends Feature {
     private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
     private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
     private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
     private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
     private final int backgroundColor = (new Color(0, 0, 0, 120)).getRGB();
     private final int black;
     public FramebufferShader framebuffer;
     public BooleanSetting showArmor;
     public BooleanSetting name;
     public BooleanSetting health;
     public static ListSetting espMode;
     boolean nameTags;
     public static ColorSetting color = new ColorSetting("Color", (new Color(16777215)).getRGB(), () -> {
          return true;
     });
     public static ColorSetting colorfriend = new ColorSetting("FriendColor", (new Color(65535)).getRGB(), () -> {
          return true;
     });

     public ESP() {
          super("ShaderESP", "best feature btw", 0, Category.VISUALS);
          this.black = Color.BLACK.getRGB();
          this.framebuffer = null;
          new ArrayList();
          espMode = new ListSetting("Mode", "Chroma", () -> {
               return true;
          }, new String[]{"Chroma", "Shader", "Image"});
          this.addSettings(new Setting[]{espMode});
     }

     @EventTarget
     public void onRender2D(Event2D event) {
          String mode = espMode.getOptions();
          this.setSuffix(mode);
          GL11.glPushMatrix();
          float partialTicks = mc.timer.renderPartialTicks;
          int scaleFactor = ScaledResolution.getScaleFactor();
          double scaling = (double)scaleFactor / Math.pow((double)scaleFactor, 2.0D);
          GL11.glScaled(scaling, scaling, scaling);
          int black = this.black;
          float scale = 1.0F;
          float upscale = 1.0F / scale;
          RenderManager renderMng = mc.getRenderManager();
          EntityRenderer entityRenderer = mc.entityRenderer;
          Iterator var12 = mc.world.loadedEntityList.iterator();

          while(true) {
               Entity entity;
               double width;
               double height;
               Vector4d position;
               do {
                    do {
                         do {
                              if (!var12.hasNext()) {
                                   GL11.glPopMatrix();
                                   GL11.glEnable(2929);
                                   GlStateManager.enableBlend();
                                   entityRenderer.setupOverlayRendering();
                                   return;
                              }

                              entity = (Entity)var12.next();
                         } while(!this.isValid(entity));
                    } while(!DrawHelper.isInViewFrustrum(entity));

                    double x = DrawHelper.interpolate(entity.posX, entity.lastTickPosX, (double)partialTicks);
                    double y = DrawHelper.interpolate(entity.posY, entity.lastTickPosY, (double)partialTicks);
                    double z = DrawHelper.interpolate(entity.posZ, entity.lastTickPosZ, (double)partialTicks);
                    width = (double)entity.width / 1.5D;
                    height = (double)entity.height + (!entity.isSneaking() && (entity != mc.player || !mc.player.isSneaking()) ? 0.2D : -0.3D);
                    AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                    Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                    entityRenderer.setupCameraTransform(partialTicks, 0);
                    position = null;
                    Vector3d[] var27 = vectors;
                    int var28 = vectors.length;

                    for(int var29 = 0; var29 < var28; ++var29) {
                         Vector3d vector = var27[var29];
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
               double posX = position.x;
               double posY = position.y;
               double endPosX = position.z;
               double endPosY = position.w;
               if (mode.equalsIgnoreCase("Chroma")) {
                    GlStateManager.pushMatrix();
                    this.framebuffer = OutlineShader.INSTANCE;
                    OutlineShader.INSTANCE.startDraw(partialTicks);
                    this.nameTags = false;
                    mc.world.loadedEntityList.forEach((e) -> {
                         if (e != mc.player && (e instanceof EntityPlayer || e instanceof EntityEnderCrystal)) {
                              mc.getRenderManager().renderEntityStatic(e, partialTicks, true);
                         }

                    });
                    this.nameTags = false;
                    OutlineShader.INSTANCE.stopDraw(new Color(209, 209, 209, 120), 1.2F, 1.0F, 0.8F, 2.0F, 0.5F, 0.5F);
                    GlStateManager.popMatrix();
               }

               EntityGlowShader framebufferShader = EntityGlowShader.GLOW_SHADER;
               if (mode.equalsIgnoreCase("Glow")) {
                    mc.gameSettings.entityShadows = false;
                    framebufferShader.startDraw(partialTicks);
                    Iterator var36 = mc.world.loadedEntityList.iterator();

                    while(var36.hasNext()) {
                         Entity entity1 = (Entity)var36.next();
                         if (this.isValid(entity1) && !(entity1 instanceof EntityItem)) {
                              mc.getRenderManager().renderEntityStatic(entity1, partialTicks, true);
                         }
                    }

                    framebufferShader.stopDraw(new Color(120, 120, 120, 255), 2.0F, 1.0F, 1.0F, 1.0F, 0.5F, 0.5F);
               }

               if (mode.equalsIgnoreCase("Shader")) {
                    FlowShader.INSTANCE.startDraw(partialTicks);
                    mc.world.loadedEntityList.stream().filter((e) -> {
                         return e instanceof EntityPlayer && e != mc.player;
                    }).forEach((e) -> {
                         mc.getRenderManager().renderEntityStatic(e, partialTicks, true);
                    });
                    FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, 1.0F, 1.0F, 0.5F, 0.5F);
               }

               if (mode.equalsIgnoreCase("Image")) {
                    mc.renderEngine.bindTexture(new ResourceLocation("anime.png"));
                    GlStateManager.color(255.0F, 255.0F, 255.0F);
                    Gui.drawScaledCustomSizeModalRect((int)posX, (int)posY, 0.0F, 0.0F, (int)width + 60, (int)height + 60, (int)width + 60, (int)height + 60, (float)width + 60.0F, (float)height + 60.0F);
               }

               boolean living = entity instanceof EntityLivingBase;
               EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
               float hp2 = entityLivingBase.getHealth();
               float maxHealth = entityLivingBase.getMaxHealth();
               double hpPercentage = (double)(hp2 / maxHealth);
               double hpHeight2 = (endPosY - posY) * hpPercentage;
               int healthColor2;
               if (hp2 <= 4.0F) {
                    healthColor2 = (new Color(200, 0, 0)).getRGB();
               } else if (hp2 <= 8.0F) {
                    healthColor2 = (new Color(231, 143, 85)).getRGB();
               } else if (hp2 <= 12.0F) {
                    healthColor2 = (new Color(219, 201, 106)).getRGB();
               } else if (hp2 <= 16.0F) {
                    healthColor2 = (new Color(117, 231, 85)).getRGB();
               } else {
                    healthColor2 = (new Color(44, 186, 19)).getRGB();
               }

               if (entityLivingBase != null && hp2 > 0.0F) {
                    if (living && this.health.getBoolValue()) {
                         MathHelper.clamp(hp2, 0.0F, 20.0F);
                         DrawHelper.drawRect(posX - 4.5D, posY - 0.5D, posX - 2.5D, endPosY + 0.5D, (new Color(0, 0, 0, 125)).getRGB());
                         DrawHelper.drawRect(posX - 4.0D, endPosY, posX - 3.0D, endPosY - hpHeight2, healthColor2);
                    }

                    double diff1;
                    if (living && this.name.getBoolValue() && !Main.instance.featureDirector.getFeatureByClass(NameTags.class).isToggled()) {
                         float scaledHeight = 20.0F;
                         String name = entity.getName();
                         if (Main.instance.featureDirector.getFeatureByClass(NameProtect.class).isToggled()) {
                              name = "Protected";
                         }

                         double dif = (endPosX - posX) / 2.0D;
                         Minecraft var10000 = mc;
                         diff1 = (double)((float)Minecraft.fontRendererObj.getStringWidth(name + " §7" + (int)mc.player.getDistanceToEntity(entity) + "m") * scale);
                         float tagX = (float)((posX + dif - diff1 / 2.0D) * (double)upscale);
                         float tagY = (float)(posY * (double)upscale) - scaledHeight;
                         GL11.glPushMatrix();
                         GlStateManager.scale(scale, scale, scale);
                         var10000 = mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(name, tagX, tagY, Color.WHITE.getRGB());
                         GL11.glPopMatrix();
                    }

                    if (living && this.showArmor.getBoolValue() && entity instanceof EntityPlayer) {
                         EntityPlayer player = (EntityPlayer)entity;
                         double ydiff = (endPosY - posY) / 4.0D;
                         ItemStack stack = player.getEquipmentInSlot(4);
                         if (mc.player.getDistanceToEntity(player) <= 15.0F) {
                              if (stack != null) {
                                   diff1 = posY + ydiff - 1.0D - (posY + 2.0D);
                                   double var54 = 1.0D - (double)stack.getItemDamage() / (double)stack.getMaxDamage();
                                   DrawHelper.renderItem(stack, (int)endPosX + 4, (int)posY + (int)ydiff - 1 - (int)(diff1 / 2.0D) - 18);
                              }

                              ItemStack stack2 = player.getEquipmentInSlot(3);
                              if (stack2 != null) {
                                   double diff1 = posY + ydiff * 2.0D - (posY + ydiff + 2.0D);
                                   if (stack.getDisplayName().equalsIgnoreCase("Air")) {
                                        String var55 = "0";
                                   } else if (!(stack2.getItem() instanceof ItemArmor)) {
                                        stack2.getDisplayName();
                                   } else {
                                        (new StringBuilder()).append(stack2.getMaxDamage() - stack2.getItemDamage()).append("").toString();
                                   }

                                   if (mc.player.getDistanceToEntity(player) < 10.0F) {
                                        DrawHelper.renderItem(stack2, (int)endPosX + 4, (int)(posY + ydiff * 2.0D) - (int)(diff1 / 2.0D) - 18);
                                   }
                              }

                              ItemStack stack3 = player.getEquipmentInSlot(2);
                              if (stack3 != null) {
                                   double diff1 = posY + ydiff * 3.0D - (posY + ydiff * 2.0D + 2.0D);
                                   if (mc.player.getDistanceToEntity(player) < 10.0F) {
                                        DrawHelper.renderItem(stack3, (int)endPosX + 4, (int)(posY + ydiff * 3.0D) - (int)(diff1 / 2.0D) - 18);
                                   }
                              }

                              ItemStack stack4 = player.getEquipmentInSlot(1);
                              double diff1 = posY + ydiff * 4.0D - (posY + ydiff * 3.0D + 2.0D);
                              if (mc.player.getDistanceToEntity(player) < 10.0F) {
                                   DrawHelper.renderItem(stack4, (int)endPosX + 4, (int)(posY + ydiff * 4.0D) - (int)(diff1 / 2.0D) - 18);
                              }
                         }
                    }
               }
          }
     }

     @EventTarget
     public void onRenderName(EventNameTags eventRenderName) {
          if (this.isToggled()) {
               eventRenderName.setCancelled(this.name.getBoolValue());
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
                    return entity != mc.player;
               } else {
                    return false;
               }
          } else {
               return false;
          }
     }

     private Vector3d project2D(int scaleFactor, double x, double y, double z) {
          GL11.glGetFloat(2982, this.modelview);
          GL11.glGetFloat(2983, this.projection);
          GL11.glGetInteger(2978, this.viewport);
          return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d((double)(this.vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor), (double)this.vector.get(2)) : null;
     }
}
