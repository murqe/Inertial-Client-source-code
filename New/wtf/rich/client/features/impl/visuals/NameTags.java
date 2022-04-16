package wtf.rich.client.features.impl.visuals;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import optifine.CustomColors;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.event.event.EventNameTags;
import wtf.rich.api.utils.math.MathHelper;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.display.ClientFont;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class NameTags extends Feature {
     public static Map entityPositions = new HashMap();
     public NumberSetting ntsize = new NumberSetting("NameTags Size", "Размер неймтагов", 1.0F, 0.5F, 2.0F, 0.1F, () -> {
          return true;
     });
     public NumberSetting borderOpacity = new NumberSetting("Border Opacity", "Прозрачность бэк-граунда", 10.0F, 0.0F, 255.0F, 1.0F, () -> {
          return this.ntbg.getBoolValue();
     });
     public BooleanSetting ntbg = new BooleanSetting("NameTags Background", "Включает бэк-граунд неймтагов", false, () -> {
          return true;
     });
     public BooleanSetting showArmor = new BooleanSetting("Show Armor", true, () -> {
          return true;
     });
     public BooleanSetting showPotions = new BooleanSetting("Show Potion", true, () -> {
          return true;
     });

     public NameTags() {
          super("NameTags", "Показывает игроков, ник, броню и их здоровье сквозь стены", 0, Category.VISUALS);
          this.addSettings(new Setting[]{this.ntsize, this.ntbg, this.borderOpacity, this.showArmor, this.showPotions});
     }

     @EventTarget
     public void onNickRemove(EventNameTags event) {
          if (this.isToggled()) {
               event.setCancelled(true);
          }

     }

     public static TextFormatting getHealthColor(float health) {
          if (health <= 4.0F) {
               return TextFormatting.RED;
          } else if (health <= 8.0F) {
               return TextFormatting.GOLD;
          } else if (health <= 12.0F) {
               return TextFormatting.YELLOW;
          } else {
               return health <= 16.0F ? TextFormatting.DARK_GREEN : TextFormatting.GREEN;
          }
     }

     @EventTarget
     public void on3D(Event3D event) {
          try {
               this.updatePositions();
          } catch (Exception var3) {
          }

     }

     @EventTarget
     public void on2D(Event2D event) {
          GlStateManager.pushMatrix();
          Iterator var2 = entityPositions.keySet().iterator();

          while(true) {
               while(true) {
                    EntityLivingBase entity;
                    double y;
                    do {
                         do {
                              if (!var2.hasNext()) {
                                   GL11.glPopMatrix();
                                   GlStateManager.enableBlend();
                                   return;
                              }

                              entity = (EntityLivingBase)var2.next();
                              double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks();
                              mc.getRenderManager();
                              double x = var10000 - RenderManager.renderPosX;
                              var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks();
                              mc.getRenderManager();
                              y = var10000 - RenderManager.renderPosY;
                              var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks();
                              mc.getRenderManager();
                              double z = var10000 - RenderManager.renderPosZ;
                              GlStateManager.pushMatrix();
                         } while(!(entity instanceof EntityPlayer));
                    } while(entity == mc.player);

                    double[] array = (double[])entityPositions.get(entity);
                    if (array[3] >= 0.0D && array[3] < 1.0D) {
                         ScaledResolution sr = new ScaledResolution(mc);
                         GlStateManager.translate(array[0] / (double)ScaledResolution.getScaleFactor(), array[1] / (double)ScaledResolution.getScaleFactor(), 0.0D);
                         this.scale();
                         String string = "";
                         if (Main.instance.featureDirector.getFeatureByClass(NameProtect.class).isToggled()) {
                              string = "Protected";
                         } else if (Main.instance.friendManager.isFriend(entity.getName())) {
                              string = ChatFormatting.GREEN + "[F] " + ChatFormatting.RESET + entity.getDisplayName().getUnformattedText();
                         } else {
                              string = entity.getDisplayName().getUnformattedText();
                         }

                         String string2 = "" + MathHelper.round(entity.getHealth(), 1);
                         Minecraft var29 = mc;
                         float width = (float)(Minecraft.fontRendererObj.getStringWidth(string2 + " " + string) + 2);
                         GlStateManager.translate(0.0D, -10.0D, 0.0D);
                         if (this.ntbg.getBoolValue()) {
                              DrawHelper.drawBorderedRect((double)(-width / 2.0F), -10.0D, (double)(width / 2.0F), 3.0D, 1.0D, DrawHelper.getColor(0, (int)this.borderOpacity.getNumberValue()), DrawHelper.getColor(25, (int)this.borderOpacity.getNumberValue()), true);
                         }

                         var29 = mc;
                         Minecraft.fontRendererObj.drawStringWithShadow(string + " " + getHealthColor(entity.getHealth()) + string2, -width / 2.0F + 2.0F, -7.5F, -1);
                         if (this.showPotions.getBoolValue()) {
                              float yPotion = (float)(y - 60.0D);
                              Iterator var16 = entity.getActivePotionEffects().iterator();

                              while(var16.hasNext()) {
                                   PotionEffect effectPotion = (PotionEffect)var16.next();
                                   GL11.glDisable(2929);
                                   Potion effect = Potion.getPotionById(CustomColors.getPotionId(effectPotion.getEffectName()));
                                   if (effect != null) {
                                        ChatFormatting getPotionColor = null;
                                        if (effectPotion.getDuration() < 200) {
                                             getPotionColor = ChatFormatting.RED;
                                        } else if (effectPotion.getDuration() < 400) {
                                             getPotionColor = ChatFormatting.GOLD;
                                        } else if (effectPotion.getDuration() > 400) {
                                             getPotionColor = ChatFormatting.GRAY;
                                        }

                                        String durationString = Potion.getDurationString(effectPotion);
                                        String level = I18n.format(effect.getName());
                                        if (effectPotion.getAmplifier() == 1) {
                                             level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.2") + " (" + getPotionColor + durationString + ")";
                                        } else if (effectPotion.getAmplifier() == 2) {
                                             level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.3") + " (" + getPotionColor + durationString + ")";
                                        } else if (effectPotion.getAmplifier() == 3) {
                                             level = level + " " + ChatFormatting.GRAY + I18n.format("enchantment.level.4") + " (" + getPotionColor + durationString + ")";
                                        }

                                        var29 = mc;
                                        Minecraft.fontRendererObj.drawStringWithShadow(level, (float)MathematicHelper.getMiddle((int)(-width) + 100, (int)width + 100) - width, yPotion, -1);
                                   }

                                   yPotion -= 10.0F;
                                   GL11.glEnable(2929);
                              }
                         }

                         ItemStack heldItemStack = entity.getHeldItem(EnumHand.OFF_HAND);
                         if (this.showArmor.getBoolValue()) {
                              ArrayList list = new ArrayList();

                              int n10;
                              for(n10 = 0; n10 < 5; ++n10) {
                                   ItemStack getEquipmentInSlot = ((EntityPlayer)entity).getEquipmentInSlot(n10);
                                   list.add(getEquipmentInSlot);
                              }

                              n10 = -(list.size() * 9);
                              RenderItem var31 = mc.getRenderItem();
                              int var10002 = n10 + 105;
                              Minecraft var10003 = mc;
                              var31.renderItemIntoGUI(heldItemStack, (float)(var10002 - Minecraft.fontRendererObj.getStringWidth("" + n10)), -29.0F);
                              var31 = mc.getRenderItem();
                              Minecraft var10001 = mc;
                              int var34 = n10 + 105;
                              Minecraft var10004 = mc;
                              var31.renderItemOverlays((FontRenderer)Minecraft.fontRendererObj, heldItemStack, var34 - Minecraft.fontRendererObj.getStringWidth("" + n10), -28);

                              for(Iterator var33 = list.iterator(); var33.hasNext(); n10 = (int)((double)n10 + 13.5D)) {
                                   ItemStack itemStack = (ItemStack)var33.next();
                                   RenderHelper.enableGUIStandardItemLighting();
                                   mc.getRenderItem().renderItemIntoGUI(itemStack, (float)(n10 + 6), -28.0F);
                                   var31 = mc.getRenderItem();
                                   var10001 = mc;
                                   var31.renderItemOverlays((FontRenderer)Minecraft.fontRendererObj, itemStack, n10 + 5, -26);
                                   n10 += 3;
                                   RenderHelper.disableStandardItemLighting();
                                   int n11 = 7;
                                   int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(16)), itemStack);
                                   int getEnchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(20)), itemStack);
                                   int getEnchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(19)), itemStack);
                                   if (getEnchantmentLevel > 0) {
                                        this.drawEnchantTag("S" + this.getColor(getEnchantmentLevel) + getEnchantmentLevel, n10, n11);
                                        n11 += 8;
                                   }

                                   if (getEnchantmentLevel2 > 0) {
                                        this.drawEnchantTag("F" + this.getColor(getEnchantmentLevel2) + getEnchantmentLevel2, n10, n11);
                                        n11 += 8;
                                   }

                                   int getEnchantmentLevel7;
                                   if (getEnchantmentLevel3 > 0) {
                                        this.drawEnchantTag("Kb" + this.getColor(getEnchantmentLevel3) + getEnchantmentLevel3, n10, n11);
                                   } else {
                                        int getEnchantmentLevel8;
                                        int getEnchantmentLevel9;
                                        if (itemStack.getItem() instanceof ItemArmor) {
                                             getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(0)), itemStack);
                                             getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(7)), itemStack);
                                             getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(34)), itemStack);
                                             if (getEnchantmentLevel7 > 0) {
                                                  this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
                                                  n11 += 8;
                                             }

                                             if (getEnchantmentLevel8 > 0) {
                                                  this.drawEnchantTag("Th" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                                  n11 += 8;
                                             }

                                             if (getEnchantmentLevel9 > 0) {
                                                  this.drawEnchantTag("U" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
                                             }
                                        } else if (itemStack.getItem() instanceof ItemBow) {
                                             getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(48)), itemStack);
                                             getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(49)), itemStack);
                                             getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(50)), itemStack);
                                             if (getEnchantmentLevel7 > 0) {
                                                  this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
                                                  n11 += 8;
                                             }

                                             if (getEnchantmentLevel8 > 0) {
                                                  this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                                  n11 += 8;
                                             }

                                             if (getEnchantmentLevel9 > 0) {
                                                  this.drawEnchantTag("F" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
                                             }
                                        }
                                   }

                                   getEnchantmentLevel7 = (int)Math.round(255.0D - (double)itemStack.getItemDamage() * 255.0D / (double)itemStack.getMaxDamage());
                                   (new Color(255 - getEnchantmentLevel7 << 16 | getEnchantmentLevel7 << 8)).brighter();
                                   if (itemStack.getMaxDamage() - itemStack.getItemDamage() > 0) {
                                        GlStateManager.pushMatrix();
                                        GlStateManager.disableDepth();
                                        GlStateManager.enableDepth();
                                        GlStateManager.popMatrix();
                                   }
                              }
                         }

                         GlStateManager.popMatrix();
                    } else {
                         GlStateManager.popMatrix();
                    }
               }
          }
     }

     private void drawEnchantTag(String text, int n, int n2) {
          GlStateManager.pushMatrix();
          GlStateManager.disableDepth();
          n2 -= 7;
          if (!ClientFont.minecraftfont.getBoolValue()) {
               ClientHelper.getFontRender().drawStringWithShadow(text, (double)(n + 6), (double)(-35 - n2), -1);
          } else {
               Minecraft var10000 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(text, (float)(n + 6), (float)(-35 - n2), -1);
          }

          GlStateManager.enableDepth();
          GlStateManager.popMatrix();
     }

     private String getColor(int n) {
          if (n != 1) {
               if (n == 2) {
                    return "";
               }

               if (n == 3) {
                    return "";
               }

               if (n == 4) {
                    return "";
               }

               if (n >= 5) {
                    return "";
               }
          }

          return "";
     }

     private void updatePositions() {
          entityPositions.clear();
          float pTicks = mc.timer.renderPartialTicks;
          Iterator var2 = mc.world.loadedEntityList.iterator();

          while(var2.hasNext()) {
               Entity entity = (Entity)var2.next();
               if (entity instanceof EntityPlayer && entity != mc.player) {
                    double var10000 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)pTicks;
                    mc.getRenderManager();
                    double x = var10000 - RenderManager.renderPosX;
                    var10000 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)pTicks;
                    mc.getRenderManager();
                    double y = var10000 - RenderManager.renderPosY;
                    var10000 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)pTicks;
                    mc.getRenderManager();
                    double z = var10000 - RenderManager.renderPosZ;
                    y += (double)entity.height + 0.2D;
                    if (((double[])Objects.requireNonNull(this.convertTo2D(x, y, z)))[2] >= 0.0D && ((double[])Objects.requireNonNull(this.convertTo2D(x, y, z)))[2] < 1.0D) {
                         entityPositions.put((EntityPlayer)entity, new double[]{((double[])Objects.requireNonNull(this.convertTo2D(x, y, z)))[0], ((double[])Objects.requireNonNull(this.convertTo2D(x, y, z)))[1], Math.abs(this.convertTo2D(x, y + 1.0D, z, entity)[1] - this.convertTo2D(x, y, z, entity)[1]), this.convertTo2D(x, y, z)[2]});
                    }
               }
          }

     }

     private double[] convertTo2D(double x, double y, double z, Entity ent) {
          float pTicks = mc.timer.renderPartialTicks;
          mc.entityRenderer.setupCameraTransform(pTicks, 0);
          double[] convertedPoints = this.convertTo2D(x, y, z);
          mc.entityRenderer.setupCameraTransform(pTicks, 0);
          return convertedPoints;
     }

     private double[] convertTo2D(double x, double y, double z) {
          FloatBuffer screenCords = BufferUtils.createFloatBuffer(3);
          IntBuffer viewport = BufferUtils.createIntBuffer(16);
          FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
          FloatBuffer projection = BufferUtils.createFloatBuffer(16);
          GL11.glGetFloat(2982, modelView);
          GL11.glGetFloat(2983, projection);
          GL11.glGetInteger(2978, viewport);
          boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCords);
          return result ? new double[]{(double)screenCords.get(0), (double)((float)Display.getHeight() - screenCords.get(1)), (double)screenCords.get(2)} : null;
     }

     private void scale() {
          float n = mc.gameSettings.smoothCamera ? 2.0F : this.ntsize.getNumberValue();
          GlStateManager.scale(n, n, n);
     }
}
