package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.font.CFontRenderer;
import me.rich.font.Fonts;
import me.rich.helpers.Util;
import me.rich.helpers.combat.RotationHelper;
import me.rich.helpers.combat.RotationSpoofer;
import me.rich.helpers.friend.Friend;
import me.rich.helpers.friend.FriendManager;
import me.rich.helpers.math.MathHelper;
import me.rich.helpers.movement.MovementHelper;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShield;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class KillAura extends Feature {
      public static EntityLivingBase target;
      private double healthBarWidth;
      private double hudHeight;
      int easingHealth = 0;
      float yaw;
      float pitch;
      List targets = new ArrayList();
      private double hurttimeBarWidth;

      public KillAura() {
            super("KillAura", 0, Category.COMBAT);
            ArrayList rotation = new ArrayList();
            rotation.add("Matrix");
            rotation.add("ReallyWorld");
            rotation.add("Sunrise");
            ArrayList thud = new ArrayList();
            thud.add("Remake");
            thud.add("Inertial");
            thud.add("TableHealth");
            thud.add("InSelC");
            Main.settingsManager.rSetting(new Setting("Rotation Mode", this, "Matrix", rotation));
            Main.settingsManager.rSetting(new Setting("TargetHud Mode", this, "Akrien", thud));
            Main.settingsManager.rSetting(new Setting("TargetHudX", this, 360.0D, 0.0D, 500.0D, true));
            Main.settingsManager.rSetting(new Setting("TargetHudY", this, 150.0D, 0.0D, 170.0D, true));
            Main.settingsManager.rSetting(new Setting("FOV", this, 360.0D, 0.0D, 360.0D, true));
            Main.settingsManager.rSetting(new Setting("Range", this, 4.0D, 3.0D, 7.0D, false));
            Main.settingsManager.rSetting(new Setting("Players", this, true));
            Main.settingsManager.rSetting(new Setting("Mobs", this, false));
            Main.settingsManager.rSetting(new Setting("Invisible", this, false));
            Main.settingsManager.rSetting(new Setting("Walls", this, false));
            Main.settingsManager.rSetting(new Setting("ClientLook", this, false));
            Main.settingsManager.rSetting(new Setting("TargetHud", this, true));
            Main.settingsManager.rSetting(new Setting("NoSwing", this, false));
            Main.settingsManager.rSetting(new Setting("ShieldBreak", this, false));
            Main.settingsManager.rSetting(new Setting("ShieldBlock", this, false));
            Main.settingsManager.rSetting(new Setting("OnlyCrits", this, false));
            Main.settingsManager.rSetting(new Setting("Crits Fall Distance", this, 0.1D, 0.08D, 0.2D, false));
            Main.settingsManager.rSetting(new Setting("RayCast", this, false));
            Main.settingsManager.rSetting(new Setting("RayCast Box", this, 0.6D, 0.4D, 1.5D, false));
      }

      @EventTarget
      public void onEventPreMotion(EventPreMotionUpdate mamanooma) {
            if (this.isToggled()) {
                  String mode = Main.settingsManager.getSettingByName("Rotation Mode").getValString();
                  this.setModuleName("KillAura §7Rotation: " + Main.settingsManager.getSettingByName("Rotation Mode").getValString() + " " + Main.settingsManager.getSettingByName("Range").getValFloat() + "");
                  if (Minecraft.player.isEntityAlive()) {
                        target = this.getClosest(Main.settingsManager.getSettingByName("Range").getValDouble());
                        if (target == null) {
                              return;
                        }

                        if (target.getHealth() > 0.0F) {
                              float cdValue = Main.settingsManager.getSettingByName("OnlyCrits").getValBoolean() ? 0.95F : 1.0F;
                              if (Minecraft.player.getCooledAttackStrength(0.0F) >= cdValue) {
                                    if (!MovementHelper.isBlockAboveHead()) {
                                          if (Minecraft.player.fallDistance <= Main.settingsManager.getSettingByName("Crits Fall Distance").getValFloat() && !Minecraft.player.killaurachecks() && Main.settingsManager.getSettingByName("OnlyCrits").getValBoolean()) {
                                                return;
                                          }
                                    } else if (Minecraft.player.fallDistance != 0.0F) {
                                          if (!Minecraft.player.killaurachecks() && Main.settingsManager.getSettingByName("OnlyCrits").getValBoolean()) {
                                                return;
                                          }

                                          if (!RotationSpoofer.isLookingAtEntity(target) && Main.settingsManager.getSettingByName("RayCast").getValBoolean()) {
                                                return;
                                          }
                                    }

                                    mc.playerController.attackEntity(Minecraft.player, target);
                                    if (Main.settingsManager.getSettingByName("NoSwing").getValBoolean()) {
                                          Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                                    } else {
                                          Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                                    }

                                    timerHelper.reset();
                              }
                        }
                  }
            }

      }

      @EventTarget
      public void onRotations(EventPreMotionUpdate event) {
            if (this.isToggled()) {
                  if (target == null) {
                        return;
                  }

                  if (target.getHealth() > 0.0F) {
                        String mode = Main.settingsManager.getSettingByName("Rotation Mode").getValString();
                        float[] rots = RotationHelper.getRatations(target);
                        if (mode.equalsIgnoreCase("Matrix")) {
                              event.setYaw(rots[0]);
                              Minecraft.player.renderYawOffset = rots[0];
                              Minecraft.player.rotationYawHead = rots[0];
                              event.setPitch(rots[1]);
                              if (Main.settingsManager.getSettingByName(this, "ClientLook").getValBoolean()) {
                                    Minecraft.player.rotationYaw = rots[0];
                              }
                        }

                        if (mode.equalsIgnoreCase("Reallyworld")) {
                              rots = RotationHelper.getRotations3(target);
                              event.setYaw(rots[2]);
                              Minecraft.player.renderYawOffset = rots[1];
                              Minecraft.player.rotationYawHead = rots[1];
                              event.setPitch(rots[1]);
                        }

                        if (mode.equalsIgnoreCase("Sunrise")) {
                              rots = RotationHelper.getRatationsG1(target);
                              this.pitch = rots[3];
                        }

                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        this.yaw = rots[2];
                        Minecraft.player.renderYawOffset = rots[2];
                        Minecraft.player.rotationYawHead = rots[0];
                        Minecraft.player.rotationPitchHead = rots[1];
                  }

                  if (Main.settingsManager.getSettingByName(this, "ClientLook").getValBoolean()) {
                        float[] rots = RotationHelper.getRotations(target);
                        Minecraft.player.rotationYaw = rots[0];
                  }

                  if (Main.settingsManager.getSettingByName("ShieldBreak").getValBoolean()) {
                        if (target == null) {
                              return;
                        }

                        if (target.isBlocking()) {
                              Minecraft.player.inventory.currentItem = Util.getAxeAtHotbar();
                              mc.playerController.attackEntity(Minecraft.player, target);
                        } else if (target.getHeldItemOffhand().getItem() instanceof ItemShield) {
                              Minecraft.player.inventory.currentItem = Util.getSwordAtHotbar();
                        }
                  }
            }

      }

      @EventTarget
      public void e(Event2D e) {
            new ScaledResolution(mc);
            String fuckyou = Main.settingsManager.getSettingByName("TargetHud Mode").getValString();
            ScaledResolution sr1;
            float scaledWidth;
            float scaledWidth;
            float scaledHeight;
            float y;
            float health;
            double hurttimeWidth;
            double hpWidth;
            String healthStr;
            Minecraft var10000;
            if (fuckyou.equalsIgnoreCase("Remake")) {
                  if (target instanceof EntityPlayer && Main.settingsManager.getSettingByName("TargetHud").getValBoolean()) {
                        sr1 = new ScaledResolution(mc);
                        scaledWidth = (float)sr1.getScaledWidth();
                        scaledWidth = (float)sr1.getScaledHeight();
                        scaledHeight = scaledWidth / 2.0F - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudX").getValFloat();
                        y = scaledWidth / 2.0F + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudY").getValFloat();
                        health = target.getHealth();
                        hurttimeWidth = (double)(health / target.getMaxHealth());
                        hurttimeWidth = (double)MathHelper.clamp(hurttimeWidth, 0.0D, 1.0D);
                        hpWidth = 76.0D * hurttimeWidth;
                        healthStr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
                        if (timerHelper.hasReached(15.0D)) {
                              this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481D);
                              this.hudHeight = AnimationHelper.animate(40.0D, this.hudHeight, 0.10000000149011612D);
                              timerHelper.reset();
                        }

                        Gui.drawRect((double)scaledHeight + 125.5D, (double)y - 9.5D, (double)(scaledHeight + 215.0F), (double)(y + 17.0F), (new Color(32, 31, 32, 255)).getRGB());
                        Gui.drawRect((double)(scaledHeight + 137.0F), (double)(y + 11.0F), (double)(scaledHeight + 213.0F), (double)(y + 13.0F), (new Color(40, 40, 40, 255)).getRGB());
                        Gui.drawRect((double)(scaledHeight + 137.0F), (double)(y + 11.0F), (double)(scaledHeight + 137.0F) + this.healthBarWidth, (double)(y + 13.0F), (new Color(243, 141, 45)).getRGB());
                        Gui.drawRect((double)(scaledHeight + 137.0F), (double)(y + 11.0F), (double)(scaledHeight + 137.0F) + hpWidth, (double)(y + 13.0F), (new Color(90, 228, 147)).getRGB());
                        Fonts.roboto_15.drawStringWithShadow(healthStr + " HP", (double)(scaledHeight + 106.5F + 46.0F - (float)Fonts.roboto_15.getStringWidth(healthStr) / 2.0F), (double)(y + 3.5F), -1);
                        Fonts.roboto_15.drawStringWithShadow(target.getName(), (double)(scaledHeight + 145.0F), (double)(y - 5.0F), -1);
                        Gui.drawRect((double)scaledHeight + 126.5D, (double)(y - 9.0F), (double)scaledHeight + 142.5D, (double)(y + 7.5F), (new Color(25, 170, 255, 255)).getRGB());
                        var10000 = mc;
                        Minecraft.fontRendererObj.drawStringWithShadow("❤", scaledHeight + 127.5F, y + 8.0F, -1);
                        this.drawHead1(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)scaledHeight + 127, (int)y - 8);
                  } else {
                        this.healthBarWidth = 92.0D;
                        this.hudHeight = 0.0D;
                        target = null;
                  }
            }

            if (fuckyou.equalsIgnoreCase("Inertial")) {
                  if (target instanceof EntityPlayer && Main.settingsManager.getSettingByName("TargetHud").getValBoolean()) {
                        sr1 = new ScaledResolution(mc);
                        scaledWidth = (float)sr1.getScaledWidth();
                        scaledWidth = (float)sr1.getScaledHeight();
                        scaledHeight = scaledWidth / 2.0F - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudX").getValFloat();
                        y = scaledWidth / 2.0F + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudY").getValFloat();
                        health = target.getHealth();
                        hurttimeWidth = (double)(health / target.getMaxHealth());
                        hurttimeWidth = (double)MathHelper.clamp(hurttimeWidth, 0.0D, 1.0D);
                        hpWidth = 76.0D * hurttimeWidth;
                        healthStr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
                        if (timerHelper.hasReached(15.0D)) {
                              this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481D);
                              this.hudHeight = AnimationHelper.animate(40.0D, this.hudHeight, 0.10000000149011612D);
                              timerHelper.reset();
                        }

                        Gui.drawRect((double)(scaledHeight + 100.0F), (double)(y - 15.0F), (double)(scaledHeight + 215.0F), (double)(y + 17.0F), (new Color(30, 31, 32, 255)).getRGB());
                        Gui.drawRect((double)(scaledHeight + 137.0F), (double)(y - 7.5F), (double)(scaledHeight + 137.0F) + this.healthBarWidth, (double)(y + 0.0F), (new Color(0, 152, 224)).getRGB());
                        Fonts.neverlose502.drawStringWithShadow(target.getName(), (double)(scaledHeight + 140.0F), (double)(y - -8.0F), -1);
                        Fonts.neverlose500.drawStringWithShadow(healthStr + " HP", (double)(scaledHeight + 108.0F - (float)Fonts.neverlose500.getStringWidth(healthStr) / 2.0F), (double)(y + 10.0F), -1);
                        var10000 = mc;
                        Minecraft.fontRendererObj.drawStringWithShadow("❤", scaledHeight + 127.0F, y - 8.0F, -1);
                        this.drawHead1(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)scaledHeight + 105, (int)y - 10);
                  } else {
                        this.healthBarWidth = 92.0D;
                        this.hudHeight = 0.0D;
                        target = null;
                  }
            }

            if (fuckyou.equalsIgnoreCase("TableHealth") && target instanceof EntityPlayer && Main.settingsManager.getSettingByName("TargetHud").getValBoolean()) {
                  sr1 = new ScaledResolution(mc);
                  scaledWidth = (float)sr1.getScaledWidth();
                  scaledWidth = (float)sr1.getScaledHeight();
                  scaledHeight = scaledWidth / 2.0F - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudX").getValFloat();
                  y = scaledWidth / 2.0F + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudY").getValFloat();
                  health = target.getHealth();
                  hurttimeWidth = (double)(health / target.getMaxHealth());
                  hurttimeWidth = (double)MathHelper.clamp(hurttimeWidth, 0.0D, 1.0D);
                  hpWidth = 76.0D * hurttimeWidth;
                  healthStr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
                  if (timerHelper.hasReached(15.0D)) {
                        this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 0.1029999852180481D);
                        this.hudHeight = AnimationHelper.animate(80.0D, this.hudHeight, 0.10000000149011612D);
                        timerHelper.reset();
                  }

                  Gui.drawRect((double)(scaledHeight + 100.0F), (double)(y - 16.0F), (double)(scaledHeight + 215.0F), (double)(y + 17.0F), (new Color(31, 31, 31)).getRGB());
                  Gui.drawRect((double)(scaledHeight + 133.0F), (double)(y - -15.0F), (double)(scaledHeight + 215.0F), (double)(y + 8.0F), (new Color(117, 117, 117)).getRGB());
                  Gui.drawRect((double)(scaledHeight + 133.0F), (double)(y - -15.0F), (double)(scaledHeight + 139.0F) + this.healthBarWidth, (double)(y + 8.0F), (new Color(210, 47, 45)).getRGB());
                  Gui.drawRect((double)(scaledHeight + 133.0F), (double)(y - -15.0F), (double)(scaledHeight + 139.0F) + hpWidth, (double)(y + 8.0F), (new Color(193, 0, 255)).getRGB());
                  CFontRenderer var25 = Fonts.neverlose502;
                  StringBuilder var10001 = (new StringBuilder()).append("Health: ").append((int)target.getHealth()).append(" | Range: ");
                  Minecraft var10002 = mc;
                  var25.drawStringWithShadow(var10001.append((int)Minecraft.player.getDistanceToEntity(target)).toString(), (double)(scaledHeight + 143.0F - (float)Fonts.neverlose500.getStringWidth(healthStr) / 2.0F), (double)(y + -13.0F), -1);
                  Fonts.neverlose502.drawString(target.getName(), (double)(scaledHeight + 135.0F), (double)(y - 4.0F), -1);
                  this.drawHead(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)scaledHeight + 100, (int)y - 16);
            }

            if (fuckyou.equalsIgnoreCase("InSelC") && target instanceof EntityPlayer && Main.settingsManager.getSettingByName("TargetHud").getValBoolean()) {
                  EntityPlayer entity = (EntityPlayer)target;
                  ScaledResolution sr1 = new ScaledResolution(mc);
                  scaledWidth = (float)sr1.getScaledWidth();
                  scaledHeight = (float)sr1.getScaledHeight();
                  double hurttimePercentage = net.minecraft.util.math.MathHelper.clamp((double)Minecraft.player.hurtTime, 0.0D, 0.2D);
                  hurttimeWidth = 51.0D * hurttimePercentage;
                  this.hurttimeBarWidth = AnimationHelper.animate(hurttimeWidth, this.hurttimeBarWidth, 0.0429999852180481D);
                  float x = scaledWidth / 2.0F - Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudX").getValFloat();
                  float y = scaledHeight / 2.0F + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KillAura.class), "TargetHudY").getValFloat();
                  float health = target.getHealth();
                  double hpPercentage = (double)(health / target.getMaxHealth());
                  hpPercentage = (double)MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
                  double hpWidth = 120.0D * hpPercentage;
                  String healthStr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
                  Gui.drawRect((double)(x + 50.0F), (double)(y - 30.0F), (double)(x + 200.0F), (double)(y + 17.0F), (new Color(31, 31, 31)).getRGB());
                  RenderHelper.drawSmoothRect(x + 50.0F, y - 31.0F, x + 200.0F, y - 30.0F, (new Color(0, 255, 255)).getRGB());
                  Fonts.neverlose500_18.drawString(target.getName(), (double)(x + 100.0F), (double)(y - 25.0F), -1);
                  RenderHelper.drawNewRect((double)(x + 52.0F), (double)(y - 17.0F), (double)(x + 198.0F), (double)(y - 16.0F), (new Color(88, 88, 88, 255)).getRGB());
                  this.drawHead2(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)x + 53, (int)y - 14);
                  RenderHelper.drawSmoothRect(x + 125.0F, y - 1.0F, (float)((double)(x + 150.0F) + this.hurttimeBarWidth), y + 1.0F, (new Color(0, 255, 255)).getRGB());
                  Fonts.neverlose500_16.drawString("HurtTime", (double)(x + 83.0F), (double)(y - 2.0F), -1);
                  Fonts.neverlose500_16.drawString("Ground: " + entity.onGround, (double)(x + 83.0F), (double)(y - 12.0F), -1);
                  RenderHelper.drawSmoothRect(x + 83.0F, y + 10.0F, (float)((double)(x + 78.0F) + hpWidth), y + 13.0F, (new Color(0, 255, 255)).getRGB());
                  GlStateManager.color(1.0F, 1.0F, 1.0F);
                  RenderHelper.renderItem(target.getHeldItem(EnumHand.OFF_HAND), (int)x + 182, (int)y - 16);
            }

      }

      public void drawHead(ResourceLocation skin, int width, int height) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(skin);
            Gui.drawScaledCustomSizeModalRect(width, height, 8.0F, 8.0F, 8, 8, 33, 33, 64.0F, 64.0F);
      }

      public void drawHead1(ResourceLocation skin, int width, int height) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(skin);
            Gui.drawScaledCustomSizeModalRect(width, height, 8.0F, 8.0F, 8, 8, 15, 15, 64.0F, 64.0F);
      }

      public void drawHead2(ResourceLocation skin, int width, int height) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(skin);
            Gui.drawScaledCustomSizeModalRect(width, height, 8.0F, 8.0F, 8, 8, 28, 28, 64.0F, 64.0F);
      }

      private EntityLivingBase getClosest(double range) {
            this.targets.clear();
            double dist = range;
            EntityLivingBase target = null;
            Iterator var6 = mc.world.loadedEntityList.iterator();

            while(var6.hasNext()) {
                  Object object = var6.next();
                  Entity entity = (Entity)object;
                  EntityLivingBase player;
                  if (entity instanceof EntityLivingBase && canAttack(player = (EntityLivingBase)entity)) {
                        double currentDist = (double)Minecraft.player.getDistanceToEntity(player);
                        if (currentDist <= dist) {
                              dist = currentDist;
                              target = player;
                              this.targets.add(player);
                        }
                  }
            }

            return target;
      }

      public static boolean canAttack(EntityLivingBase player) {
            String mode = Main.settingsManager.getSettingByName("AntiBot Mode").getValString();
            if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
                  if (player instanceof EntityPlayer && !Main.settingsManager.getSettingByName("Players").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityAnimal && !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityMob && !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
                        return false;
                  }

                  if (player instanceof EntityVillager && !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
                        return false;
                  }
            }

            if (player.isInvisible() && !Main.settingsManager.getSettingByName("Invisible").getValBoolean()) {
                  return false;
            } else {
                  Iterator var2 = FriendManager.friendsList.iterator();

                  Friend friend;
                  do {
                        if (!var2.hasNext()) {
                              if (Main.moduleManager.getModule(AntiBot.class).isToggled() && mode.equalsIgnoreCase("HitBefore") && !AntiBot.entete.contains(player)) {
                                    return false;
                              }

                              if (!RotationHelper.canSeeEntityAtFov(player, (float)Main.settingsManager.getSettingByName("FOV").getValDouble()) && !canSeeEntityAtFov(player, (float)Main.settingsManager.getSettingByName("FOV").getValDouble())) {
                                    return false;
                              }

                              if (!range(player, Main.settingsManager.getSettingByName("Range").getValDouble())) {
                                    return false;
                              }

                              if (!player.canEntityBeSeen(Minecraft.player)) {
                                    return Main.settingsManager.getSettingByName("Walls").getValBoolean();
                              }

                              return player != Minecraft.player;
                        }

                        friend = (Friend)var2.next();
                  } while(!player.getName().equals(friend.getName()));

                  return false;
            }
      }

      private static boolean range(EntityLivingBase entity, double range) {
            Minecraft.player.getDistanceToEntity(entity);
            return (double)Minecraft.player.getDistanceToEntity(entity) <= range;
      }

      public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
            double diffX = entityLiving.posX - Minecraft.player.posX;
            double diffZ = entityLiving.posZ - Minecraft.player.posZ;
            float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
            double d = (double)newYaw;
            double difference = angleDifference(d, (double)Minecraft.player.rotationYaw);
            return difference <= (double)scope;
      }

      public static double angleDifference(double a, double b) {
            float yaw360 = (float)(Math.abs(a - b) % 360.0D);
            if (yaw360 > 180.0F) {
                  yaw360 = 360.0F - yaw360;
            }

            return (double)yaw360;
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
