package wtf.rich.client.features.impl.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.api.event.event.EventAttackSilent;
import wtf.rich.api.event.event.EventNameTags;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventSendPacket;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.combat.EntityHelper;
import wtf.rich.api.utils.combat.GCDFix;
import wtf.rich.api.utils.combat.KillAuraHelper;
import wtf.rich.api.utils.combat.RotationHelper;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.api.utils.other.Util;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.api.utils.world.InventoryHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.visuals.NameProtect;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class KillAura extends Feature {
     private boolean isBreaked;
     public static EntityLivingBase target;
     private double healthBarWidth;
     private double hudHeight;
     float yaw;
     float pitch;
     public static boolean canBlock;
     public static NumberSetting range;
     public static ListSetting clickMode;
     public static NumberSetting fov;
     public static ListSetting rotationMode;
     public static ListSetting targetHudMode;
     public static ListSetting sortMode;
     public static BooleanSetting targetHud;
     public static NumberSetting thudX;
     public static NumberSetting thudY;
     public static BooleanSetting shieldBreak;
     public static BooleanSetting shieldFix;
     public static BooleanSetting onlyCrits;
     public static BooleanSetting stopSprint;
     public static BooleanSetting ignoreNakedPlayer;
     public static BooleanSetting weaponOnly;
     public static BooleanSetting rayTrace;
     public static BooleanSetting walls;
     public static BooleanSetting teamCheck;
     public static BooleanSetting invisiblecheck;
     public static BooleanSetting auramobs;
     public static BooleanSetting auraplayers;
     public static BooleanSetting shieldDesync;
     public static NumberSetting rotPredict = new NumberSetting("Rotation Predict", 0.05F, 0.0F, 10.0F, 0.001F, () -> {
          return true;
     });
     public static NumberSetting minAps;
     public static NumberSetting maxAps;
     public static NumberSetting attackCoolDown;
     public static BooleanSetting autoBlock;
     public static NumberSetting breakerDelay;
     public static NumberSetting critFallDistance;
     public static BooleanSetting autoShieldUnPress;
     private double hurttimeBarWidth;
     List targets = new ArrayList();
     float pitch2 = 0.0F;
     float yaw2 = 0.0F;
     private boolean isBlocking;
     private int changeSlotCounter;

     public KillAura() {
          super("KillAura", "Автоматически аттакует энтити.", 0, Category.COMBAT);
          sortMode = new ListSetting("Sorting Mode", "Distance", () -> {
               return true;
          }, new String[]{"Distance", "Health"});
          targetHudMode = new ListSetting("TargetHud Mode", "Main", () -> {
               return targetHud.getBoolValue();
          }, new String[]{"Astolfo", "Main", "Celestial"});
          clickMode = new ListSetting("Click Mode", "1.9", () -> {
               return true;
          }, new String[]{"1.9", "1.8"});
          rotationMode = new ListSetting("Rotation Mode", "Matrix", () -> {
               return true;
          }, new String[]{"Matrix", "Sunrise", "Snap"});
          fov = new NumberSetting("FOV", "Позволяет редактировать радиус в котором вы можете ударить игрока", 180.0F, 0.0F, 180.0F, 1.0F, () -> {
               return true;
          });
          range = new NumberSetting("AttackRange", "Дистанция в которой вы можете ударить игрока", 3.6F, 3.0F, 6.0F, 0.1F, () -> {
               return true;
          });
          attackCoolDown = new NumberSetting("Cooldown", "Редактирует скорость удара", 0.85F, 0.1F, 1.0F, 0.01F, () -> {
               return clickMode.currentMode.equals("1.9");
          });
          minAps = new NumberSetting("Min CPS", "Минимальное количество кликов в секунду", 12.0F, 1.0F, 20.0F, 1.0F, () -> {
               return clickMode.currentMode.equals("1.8");
          }, NumberSetting.NumberType.APS);
          maxAps = new NumberSetting("Max CPS", "Максимальное количество кликов в секунду", 13.0F, 1.0F, 20.0F, 1.0F, () -> {
               return clickMode.currentMode.equals("1.8");
          }, NumberSetting.NumberType.APS);
          auraplayers = new BooleanSetting("Players", "Позволяет бить игроков", true, () -> {
               return true;
          });
          auramobs = new BooleanSetting("Mobs", "Позволяет бить мобов", true, () -> {
               return true;
          });
          invisiblecheck = new BooleanSetting("Invisible", "Позволяет бить невидемых существ", true, () -> {
               return true;
          });
          walls = new BooleanSetting("Walls", "Позволяет бить сквозь стены", true, () -> {
               return true;
          });
          rayTrace = new BooleanSetting("RayTrace", "Проверяет смотрит ли ротация на хитбокс", true, () -> {
               return true;
          });
          weaponOnly = new BooleanSetting("Weapon Only", "Позволяет бить только с оружием в руках", false, () -> {
               return true;
          });
          ignoreNakedPlayer = new BooleanSetting("Ignore Naked Players", "Не бьет голых игроков", false, () -> {
               return true;
          });
          stopSprint = new BooleanSetting("Stop Sprinting", "Автоматически выключает спринт", false, () -> {
               return true;
          });
          onlyCrits = new BooleanSetting("OnlyCrits", "Бьет в нужный момент для крита", false, () -> {
               return !clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          critFallDistance = new NumberSetting("Criticals Fall Distance", "Регулировка дистанции до земли для крита", 0.2F, 0.1F, 1.0F, 0.01F, () -> {
               return onlyCrits.getBoolValue();
          });
          teamCheck = new BooleanSetting("Team Check", false, () -> {
               return true;
          });
          shieldBreak = new BooleanSetting("Break Shield", "Автоматически ломает щит сопернику", false, () -> {
               return !clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          breakerDelay = new NumberSetting("Breaker Delay", "Регулировка ломания щита", 50.0F, 0.0F, 500.0F, 10.0F, () -> {
               return shieldBreak.getBoolValue();
          });
          shieldFix = new BooleanSetting("Shield Fix", "Автоматически зажимает щит(обход)", false, () -> {
               return !clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          autoShieldUnPress = new BooleanSetting("Auto Shield UnPress", "Автоматически отжимает щит если у соперника топор в руках", false, () -> {
               return !clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          shieldDesync = new BooleanSetting("Shield Desync", "Десинкает щит (работает только на санрайсе)", false, () -> {
               return !clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          autoBlock = new BooleanSetting("Auto Block", "Автоматически жмет пкм при ударе (нужно для 1.8 серверов)", false, () -> {
               return clickMode.currentMode.equalsIgnoreCase("1.8");
          });
          targetHud = new BooleanSetting("TargetHud", "Отображает хп, еду, броню соперника на экране", true, () -> {
               return true;
          });
          thudX = new NumberSetting("TargetHud X", 188.0F, -500.0F, 500.0F, 1.0F, () -> {
               return targetHud.getBoolValue();
          });
          thudY = new NumberSetting("TargetHud Y", 50.0F, -300.0F, 300.0F, 1.0F, () -> {
               return targetHud.getBoolValue();
          });
          this.addSettings(new Setting[]{sortMode, clickMode, rotationMode, fov, range, attackCoolDown, minAps, maxAps, rotPredict, auraplayers, auramobs, invisiblecheck, walls, rayTrace, weaponOnly, ignoreNakedPlayer, stopSprint, onlyCrits, critFallDistance, teamCheck, shieldBreak, breakerDelay, autoShieldUnPress, shieldFix, shieldDesync, autoBlock, targetHud, targetHudMode, thudX, thudY});
     }

     @EventTarget
     public void onSendPacket(EventSendPacket event) {
          if (event.getPacket() instanceof CPacketUseEntity) {
               CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
               if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT) {
                    event.setCancelled(true);
               }

               if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
                    event.setCancelled(true);
               }
          }

     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (autoShieldUnPress.getBoolValue()) {
               if (target.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                    if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                         mc.gameSettings.keyBindUseItem.pressed = false;
                    }
               } else {
                    mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
               }
          }

     }

     @EventTarget
     public void onEventPreMotion(EventPreMotionUpdate mamanooma) {
          if (this.isToggled()) {
               this.setModuleName("KillAura " + ChatFormatting.GRAY + rotationMode.getOptions());
               if (Minecraft.getMinecraft().player.isEntityAlive()) {
                    target = KillAuraHelper.getSortEntities();
                    if (target == null) {
                         return;
                    }

                    if (target.getHealth() > 0.0F) {
                         if (!MovementHelper.isBlockAboveHead()) {
                              if (Minecraft.getMinecraft().player.fallDistance < critFallDistance.getNumberValue() && !Util.checkcrit() && onlyCrits.getBoolValue()) {
                                   return;
                              }
                         } else if (Minecraft.getMinecraft().player.fallDistance != 0.0F && !Util.checkcrit() && onlyCrits.getBoolValue()) {
                              return;
                         }

                         if (!RotationHelper.isLookingAtEntity(this.yaw, this.pitch, 0.14F, 0.14F, 0.14F, target, (double)range.getNumberValue()) && rayTrace.getBoolValue()) {
                              return;
                         }

                         this.attackEntitySuccess(target);
                    }
               }
          }

     }

     private void attackEntitySuccess(EntityLivingBase target) {
          if (target.getHealth() > 0.0F) {
               String var2 = clickMode.getOptions();
               byte var3 = -1;
               switch(var2.hashCode()) {
               case 48571:
                    if (var2.equals("1.8")) {
                         var3 = 1;
                    }
                    break;
               case 48572:
                    if (var2.equals("1.9")) {
                         var3 = 0;
                    }
               }

               switch(var3) {
               case 0:
                    float attackDelay = 0.5F;
                    if (mc.player.getCooledAttackStrength(attackDelay) >= attackCoolDown.getNumberValue()) {
                         mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                         mc.playerController.attackEntity(mc.player, EntityHelper.rayCast(target, (double)range.getNumberValue()));
                         mc.player.swingArm(EnumHand.MAIN_HAND);
                         mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    }
                    break;
               case 1:
                    if (KillAuraHelper.canApsAttack()) {
                         if (this.isBlocking && autoBlock.getBoolValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && timerHelper.hasReached(100.0D)) {
                              mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                              this.isBlocking = false;
                              timerHelper.reset();
                         }

                         mc.playerController.attackEntity(mc.player, EntityHelper.rayCast(target, (double)range.getNumberValue()));
                         mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
               }
          }

     }

     @EventTarget
     public void onAttackSilent(EventAttackSilent eventAttackSilent) {
          if (mc.player.isBlocking() && mc.player.getHeldItemOffhand().getItem() instanceof ItemShield && shieldFix.getBoolValue()) {
               mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-0.8D, -0.8D, -0.8D), EnumFacing.DOWN));
               mc.playerController.processRightClick(mc.player, mc.world, EnumHand.OFF_HAND);
          }

     }

     @EventTarget
     public void onRotations(EventPreMotionUpdate event) {
          String mode = rotationMode.getOptions();
          if (target != null && target.getHealth() > 0.0F) {
               float[] rots = RotationHelper.getRotations(target, true, 1.6F, 1.6F);
               float[] sunriseRots = RotationHelper.getRotations(target, true, 1.5F, 1.5F);
               if (mode.equalsIgnoreCase("Matrix")) {
                    event.setYaw(rots[0]);
                    event.setPitch(rots[1]);
                    this.yaw = rots[0];
                    this.pitch = rots[1];
                    mc.player.renderYawOffset = rots[0];
                    mc.player.rotationYawHead = rots[0];
                    mc.player.rotationPitchHead = rots[1];
               } else if (mode.equalsIgnoreCase("Sunrise")) {
                    this.yaw2 = GCDFix.getFixedRotation(MathHelper.Rotate(this.yaw2, sunriseRots[0], 5.0F, 20.1F));
                    this.pitch2 = GCDFix.getFixedRotation(MathHelper.Rotate(this.pitch2, sunriseRots[1], 0.35F, 2.1F));
                    event.setYaw(this.yaw2);
                    event.setPitch(this.pitch2);
                    this.yaw = this.yaw2;
                    this.pitch = this.pitch2;
                    mc.player.renderYawOffset = sunriseRots[0];
                    mc.player.rotationYawHead = sunriseRots[0];
                    mc.player.rotationPitchHead = sunriseRots[1];
               } else if (mode.equalsIgnoreCase("Snap") && mc.player.getCooledAttackStrength(0.0F) >= attackCoolDown.getNumberValue() && target != null) {
                    mc.player.rotationYaw = rots[0];
                    this.yaw = rots[0];
                    this.pitch = rots[1];
                    mc.player.rotationPitch = rots[1];
               }
          }

     }

     @EventTarget
     public void onShieldBreaker(EventPreMotionUpdate eventPreMotionUpdate) {
          if (target != null && InventoryHelper.doesHotbarHaveAxe() && shieldBreak.getBoolValue() && (target.getHeldItemOffhand().getItem() instanceof ItemShield || target.getHeldItemMainhand().getItem() instanceof ItemShield)) {
               if (target.isBlocking() && target.isHandActive() && target.isActiveItemStackBlocking(2) && mc.player.getDistanceToEntity(target) < range.getNumberValue() && RotationHelper.isLookingAtEntity(this.yaw, this.pitch, 0.06F, 0.06F, 0.06F, target, (double)range.getNumberValue())) {
                    if (RotationHelper.isAimAtMe(target, 65.0F)) {
                         if (mc.player.inventory.currentItem != InventoryHelper.getAxe()) {
                              mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem = InventoryHelper.getAxe()));
                         }

                         if (mc.player.inventory.currentItem == InventoryHelper.getAxe()) {
                              if (timerHelper.hasReached(40.0D)) {
                                   this.isBreaked = true;
                                   mc.playerController.attackEntity(mc.player, target);
                                   mc.player.swingArm(EnumHand.MAIN_HAND);
                                   mc.player.resetCooldown();
                                   timerHelper.reset();
                              }

                              this.changeSlotCounter = -1;
                         } else {
                              this.changeSlotCounter = 0;
                         }
                    }
               } else if (mc.player.inventory.currentItem != InventoryHelper.getSwordAtHotbar() && this.changeSlotCounter == -1 && InventoryHelper.getSwordAtHotbar() != -1 && (!target.isBlocking() || !target.isHandActive() || !target.isActiveItemStackBlocking(2))) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem = InventoryHelper.getSwordAtHotbar()));
                    this.changeSlotCounter = 0;
                    this.isBreaked = false;
               }
          }

     }

     @EventTarget
     public void onNickRemove(EventNameTags event) {
          if (targetHudMode.currentMode.equalsIgnoreCase("Astolfo")) {
               event.setCancelled(true);
          }

     }

     @EventTarget
     public void e(Event2D e) {
          if (this.isToggled()) {
               ScaledResolution sr = new ScaledResolution(mc);
               String mode = targetHudMode.getOptions();
               float scaledWidth;
               float scaledHeight;
               if (target != null && mode.equalsIgnoreCase("Main")) {
                    if (targetHud.getBoolValue() && target instanceof EntityPlayer) {
                         int color = 15;
                         scaledWidth = (float)(new ScaledResolution(mc)).getScaledWidth() / 2.0F - thudX.getNumberValue();
                         scaledHeight = (float)(new ScaledResolution(mc)).getScaledHeight() / 2.0F + thudY.getNumberValue();
                         double hpWidth = (double)(target.getHealth() / target.getMaxHealth() * 78.0F);
                         this.healthBarWidth = (double)MathHelper.lerp((double)((float)this.healthBarWidth), (double)((float)hpWidth), 7.0D * Feature.deltaTime());
                         DrawHelper.drawNewRect((double)(scaledWidth + 122.0F), (double)(scaledHeight - 14.0F), (double)(scaledWidth + 250.0F), (double)(scaledHeight + 25.0F), (new Color(color, color, color, 0)).getRGB());
                         DrawHelper.drawGlowRoundedRect(scaledWidth + 122.0F, scaledHeight - 14.0F, scaledWidth + 250.0F, scaledHeight + 25.0F, (new Color(color, color, color, 150)).getRGB(), 8.0F, 10.0F);
                         Util.drawHead1(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)scaledWidth + 129, (int)(scaledHeight - 4.0F));
                         Gui.drawRect((double)(scaledWidth + 160.0F), (double)(scaledHeight + 13.0F), (double)(scaledWidth + 160.0F) + this.healthBarWidth, (double)(scaledHeight + 18.0F), ClientHelper.getClientColor().getRGB());
                         mc.neverlose500_13.drawStringWithShadow("Hp: " + (float)((int)target.getHealth()) / 2.0F + " | Ground: " + (target.onGround ? "true" : "false"), (double)(scaledWidth + 121.0F + 46.0F - (float)mc.neverlose500_16.getStringWidth(String.valueOf((float)((int)target.getHealth()) / 2.0F)) / 2.0F), (double)(scaledHeight + 6.0F), -1);
                         mc.neverlose500_18.drawStringWithShadow(target.getName(), (double)(scaledWidth + 158.0F), (double)(scaledHeight - 5.0F), -1);
                         mc.getRenderItem().renderItemOverlays(mc.neverlose500_18, target.getHeldItem(EnumHand.OFF_HAND), (int)scaledWidth + 228, (int)scaledHeight - 30);
                         mc.getRenderItem().renderItemIntoGUI(target.getHeldItem(EnumHand.OFF_HAND), (float)((int)scaledWidth + 232), (float)((int)scaledHeight - 13));
                    } else {
                         this.healthBarWidth = 92.0D;
                         this.hudHeight = 0.0D;
                         target = null;
                    }
               }

               double hpPercentage;
               Minecraft var10001;
               float x;
               RenderItem var23;
               if (mode.equalsIgnoreCase("Astolfo") && this.isToggled() && target != null && target instanceof EntityPlayer && targetHud.getBoolValue()) {
                    float scaledWidth = (float)sr.getScaledWidth();
                    scaledWidth = (float)sr.getScaledHeight();
                    scaledHeight = scaledWidth / 2.0F - thudX.getNumberValue();
                    x = scaledWidth / 2.0F + thudY.getNumberValue();
                    double healthWid = (double)(target.getHealth() / target.getMaxHealth() * 120.0F);
                    healthWid = MathHelper.clamp(healthWid, 0.0D, 120.0D);
                    hpPercentage = target != null && target.getHealth() < (float)(target instanceof EntityPlayer ? 18 : 10) && target.getHealth() > 1.0F ? 8.0D : 0.0D;
                    this.healthBarWidth = (double)MathHelper.lerp((double)((float)healthWid), (double)((float)this.healthBarWidth), 5.0D * Feature.deltaTime());
                    DrawHelper.drawGlowRoundedRect(scaledHeight, x, scaledHeight + 155.0F, x + 62.0F, (new Color(20, 20, 20, 255)).getRGB(), 6.0F, 5.0F);
                    Minecraft var10000;
                    if (!target.getName().isEmpty()) {
                         var10000 = mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(Main.instance.featureDirector.getFeatureByClass(NameProtect.class).isToggled() ? "Protected" : target.getName(), scaledHeight + 31.0F, x + 5.0F, -1);
                    }

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(scaledHeight, x, 1.0F);
                    GL11.glScalef(2.5F, 2.5F, 2.5F);
                    GlStateManager.translate(-scaledHeight - 3.0F, -x - 2.0F, 1.0F);
                    var10000 = mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(wtf.rich.api.utils.math.MathHelper.round(target.getHealth() / 2.0F, 1) + " ❤", scaledHeight + 16.0F, x + 10.0F, (new Color(ClientHelper.getClientColor().getRGB())).getRGB());
                    GlStateManager.popMatrix();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    var23 = mc.getRenderItem();
                    var10001 = mc;
                    var23.renderItemOverlays(Minecraft.fontRendererObj, target.getHeldItem(EnumHand.OFF_HAND), (int)scaledHeight + 137, (int)x + 7);
                    mc.getRenderItem().renderItemIntoGUI(target.getHeldItem(EnumHand.OFF_HAND), (float)((int)scaledHeight + 137), (float)((int)x + 1));
                    GuiInventory.drawEntityOnScreen((int)scaledHeight + 16, (int)x + 55, 25, target.rotationYaw, -target.rotationPitch, target);
                    DrawHelper.drawRect2((double)(scaledHeight + 30.0F), (double)(x + 48.0F), 120.0D, 8.0D, (new Color(ClientHelper.getClientColor().getRGB())).darker().darker().darker().getRGB());
                    DrawHelper.drawRect2((double)(scaledHeight + 30.0F), (double)(x + 48.0F), this.healthBarWidth + hpPercentage, 8.0D, (new Color(ClientHelper.getClientColor().getRGB())).darker().darker().getRGB());
                    DrawHelper.drawRect2((double)(scaledHeight + 30.0F), (double)(x + 48.0F), healthWid, 8.0D, (new Color(ClientHelper.getClientColor().getRGB())).getRGB());
               }

               if (mode.equalsIgnoreCase("Celestial") && target != null) {
                    if (targetHud.getBoolValue() && target instanceof EntityPlayer) {
                         ScaledResolution sr1 = new ScaledResolution(mc);
                         scaledWidth = (float)sr1.getScaledWidth();
                         scaledHeight = (float)sr1.getScaledHeight();
                         x = scaledWidth / 2.0F - thudX.getNumberValue();
                         float y = scaledHeight / 2.0F + thudY.getNumberValue();
                         float health = target.getHealth();
                         hpPercentage = (double)(health / target.getMaxHealth());
                         hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
                         double hpWidth = 110.0D * hpPercentage;
                         String healthStr = String.valueOf((float)((int)target.getHealth()) / 2.0F);
                         this.healthBarWidth = AnimationHelper.animate(hpWidth, this.healthBarWidth, 5.0D * Feature.deltaTime());
                         this.hudHeight = AnimationHelper.animate(40.0D, this.hudHeight, 5.0D * Feature.deltaTime());
                         DrawHelper.drawNewRect((double)(x + 125.0F), (double)(y - 19.0F), (double)(x + 275.0F), (double)(y + 29.0F), (new Color(32, 31, 32, 150)).getRGB());
                         DrawHelper.drawGlowRoundedRect(x + 120.0F, y - 26.0F, x + 280.0F, y + 35.0F, (new Color(32, 31, 32, 150)).getRGB(), 15.0F, 10.0F);
                         DrawHelper.drawGlowRoundedRect((float)((double)x + 127.5D), (float)((double)y - 11.5D), x + 273.0F, y - 11.0F, (new Color(140, 140, 140)).getRGB(), 6.0F, 10.0F);
                         DrawHelper.drawGlowRoundedRect(x + 162.0F, y + 18.0F, (float)((double)(x + 162.0F) + this.healthBarWidth), y + 20.0F, (new Color((float)ClientHelper.getClientColor().getRed() / 255.0F, (float)ClientHelper.getClientColor().getGreen() / 255.0F, (float)ClientHelper.getClientColor().getBlue() / 255.0F, 0.4509804F)).getRGB(), 6.0F, 25.0F);
                         DrawHelper.drawGlowRoundedRect(x + 162.0F, y + 18.0F, (float)((double)(x + 162.0F) + hpWidth), y + 20.0F, ClientHelper.getClientColor().getRGB(), 6.0F, 25.0F);
                         mc.sfui16.drawStringWithShadow("Ground: " + (target.onGround ? "true;" : "false;"), (double)(x + 162.0F), (double)(y - 3.0F), -1);
                         mc.sfui16.drawStringWithShadow("HurtTime", (double)(x + 162.5F), (double)(y + 7.0F), -1);
                         mc.neverlose500_13.drawCenteredString(target.getName(), (float)((double)x + 199.2753623188406D), (float)((double)y - 16.7D), -1);
                         double hurttimePercentage = MathHelper.clamp((double)target.hurtTime, 1.0D, 0.3D);
                         double hurttimeWidth = 71.0D * hurttimePercentage;
                         this.hurttimeBarWidth = AnimationHelper.animate(hurttimeWidth, this.hurttimeBarWidth, 0.0529999852180481D);
                         DrawHelper.drawGlowRoundedRect(x + 201.0F, y + 9.0F, x + 272.0F, y + 11.0F, (new Color(40, 40, 40)).getRGB(), 2.0F, 10.0F);
                         DrawHelper.drawGlowRoundedRect(x + 201.0F, y + 9.0F, (float)((double)(x + 201.0F) + this.hurttimeBarWidth), y + 11.0F, ClientHelper.getClientColor().getRGB(), 2.0F, 4.0F);
                         var23 = mc.getRenderItem();
                         var10001 = mc;
                         var23.renderItemOverlays(Minecraft.fontRendererObj, target.getHeldItem(EnumHand.OFF_HAND), (int)x + 255, (int)y - 5);
                         mc.getRenderItem().renderItemIntoGUI(target.getHeldItem(EnumHand.OFF_HAND), (float)((int)x + 259), (float)((int)y - 10));
                         DrawHelper.drawGlowRoundedRect((float)((double)x + 125.5D), y - 20.5F, x + 275.0F, y - 18.0F, ClientHelper.getClientColor().getRGB(), 4.0F, 6.0F);
                         Util.drawHead3(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(target.getUniqueID()).getLocationSkin(), (int)x + 127 + target.hurtTime / 2, (int)(y - 8.0F) + target.hurtTime / 2, 32 - target.hurtTime, 32 - target.hurtTime);
                    } else {
                         this.healthBarWidth = 92.0D;
                         this.hudHeight = 0.0D;
                    }
               }
          }

     }
}
