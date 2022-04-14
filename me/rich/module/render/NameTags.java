package me.rich.module.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.event.events.Event3D;
import me.rich.font.Fonts;
import me.rich.helpers.combat.RotationHelper;
import me.rich.helpers.friend.FriendManager;
import me.rich.helpers.render.RenderUtil;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class NameTags extends Feature {
      public static Map entityPositions = new HashMap();

      public NameTags() {
            super("NameTags", 0, Category.RENDER);
            Main.settingsManager.rSetting(new Setting("NameBorder", this, true));
      }

      @EventTarget
      public void on3D(Event3D event) {
            try {
                  this.updatePositions();
            } catch (Exception var3) {
            }

      }

      @EventTarget
      public void onRender2D(Event2D event) {
            ScaledResolution sr = new ScaledResolution(mc);
            GlStateManager.pushMatrix();
            Iterator var3 = entityPositions.keySet().iterator();

            while(true) {
                  while(true) {
                        Entity entity;
                        do {
                              if (!var3.hasNext()) {
                                    GL11.glPopMatrix();
                                    GlStateManager.enableBlend();
                                    return;
                              }

                              entity = (Entity)var3.next();
                        } while(entity == Minecraft.player);

                        GlStateManager.pushMatrix();
                        if (entity instanceof EntityPlayer) {
                              double[] array = (double[])entityPositions.get(entity);
                              if (array[3] < 0.0D || array[3] >= 1.0D) {
                                    GlStateManager.popMatrix();
                                    continue;
                              }

                              GlStateManager.translate(array[0] / (double)ScaledResolution.getScaleFactor(), array[1] / (double)ScaledResolution.getScaleFactor(), 0.0D);
                              this.scale();
                              GlStateManager.translate(0.0D, -1.5D, 0.0D);
                              String prefix = "";
                              if (FriendManager.isFriend(entity.getName())) {
                                    prefix = TextFormatting.GRAY + "[" + TextFormatting.GREEN + "F" + TextFormatting.GRAY + "]";
                              }

                              String name = entity.getName();
                              String string = (double)Math.round((double)((EntityLivingBase)entity).getHealth() * 20.0D) / 20.0D + "";
                              float n2 = (float)Minecraft.fontRendererObj.getStringWidth(string);
                              Minecraft.fontRendererObj.drawStringWithShadow(prefix + " " + ChatFormatting.GRAY + name + ChatFormatting.GRAY + " [" + ChatFormatting.GREEN + string + ChatFormatting.GRAY + "]", -46.0F, -22.0F, (new Color(10, 239, 63)).getRGB());
                              EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                              ArrayList list = new ArrayList();

                              int n10;
                              for(n10 = 0; n10 < 5; ++n10) {
                                    ItemStack getEquipmentInSlot = ((EntityPlayer)entity).getEquipmentInSlot(n10);
                                    if (getEquipmentInSlot != null) {
                                          GlStateManager.pushMatrix();
                                          list.add(getEquipmentInSlot);
                                    }
                              }

                              n10 = -(list.size() * 9);
                              Iterator var26 = list.iterator();

                              while(var26.hasNext()) {
                                    ItemStack itemStack = (ItemStack)var26.next();
                                    RenderHelper.enableGUIStandardItemLighting();
                                    ItemStack stack1 = ((EntityPlayer)entityLivingBase).getEquipmentInSlot(4);
                                    ItemStack stack2 = ((EntityPlayer)entityLivingBase).getEquipmentInSlot(3);
                                    ItemStack stack3 = ((EntityPlayer)entityLivingBase).getEquipmentInSlot(2);
                                    ItemStack stack4 = ((EntityPlayer)entityLivingBase).getEquipmentInSlot(1);
                                    RenderUtil.renderItem(stack1, 18, -40);
                                    RenderUtil.renderItem(stack2, 3, -40);
                                    RenderUtil.renderItem(stack3, -12, -40);
                                    RenderUtil.renderItem(stack4, -27, -40);
                                    RenderUtil.renderItem(((EntityPlayer)entity).getHeldItemMainhand(), -44, -40);
                                    RenderUtil.renderItem(((EntityPlayer)entity).getHeldItemOffhand(), 31, -40);
                                    GlStateManager.popMatrix();
                                    n10 += 3;
                                    RenderHelper.disableStandardItemLighting();
                                    if (itemStack != null) {
                                          int n11 = 21;
                                          int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), itemStack);
                                          int getEnchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), itemStack);
                                          int getEnchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(19), itemStack);
                                          if (getEnchantmentLevel > 0) {
                                                this.drawEnchantTag("Sh" + this.getColor(getEnchantmentLevel) + getEnchantmentLevel, n10 - 11, n11 + 4);
                                                n11 += 6;
                                                GlStateManager.pushMatrix();
                                                GlStateManager.scale(0.9D, 0.9D, 1.1D);
                                                GlStateManager.popMatrix();
                                          }

                                          if (getEnchantmentLevel2 > 0) {
                                                this.drawEnchantTag("Fir" + this.getColor(getEnchantmentLevel2) + getEnchantmentLevel2, n10 - 11, n11 + 5);
                                                GlStateManager.pushMatrix();
                                                n11 += 6;
                                                GlStateManager.scale(0.9D, 0.9D, 1.1D);
                                                GlStateManager.popMatrix();
                                          }

                                          int getEnchantmentLevel7;
                                          if (getEnchantmentLevel3 > 0) {
                                                this.drawEnchantTag("Kb" + this.getColor(getEnchantmentLevel3) + getEnchantmentLevel3, n10 - 11, n11 + 6);
                                          } else {
                                                int getEnchantmentLevel8;
                                                int getEnchantmentLevel9;
                                                if (itemStack.getItem() instanceof ItemArmor) {
                                                      getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), itemStack);
                                                      getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(7), itemStack);
                                                      getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), itemStack);
                                                      if (getEnchantmentLevel7 > 0) {
                                                            GlStateManager.pushMatrix();
                                                            this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10 - 6, n11 + 4);
                                                            n11 += 6;
                                                            GlStateManager.scale(0.9D, 0.9D, 1.1D);
                                                            GlStateManager.popMatrix();
                                                      }

                                                      if (getEnchantmentLevel8 > 0) {
                                                            this.drawEnchantTag("Th" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                                            n11 += 6;
                                                      }

                                                      if (getEnchantmentLevel9 > 0) {
                                                            GlStateManager.pushMatrix();
                                                            GlStateManager.scale(0.9D, 0.9D, 1.1D);
                                                            this.drawEnchantTag("U" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10 - 8, n11 + 11);
                                                            GlStateManager.popMatrix();
                                                      }
                                                } else if (itemStack.getItem() instanceof ItemBow) {
                                                      getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), itemStack);
                                                      getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), itemStack);
                                                      getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), itemStack);
                                                      if (getEnchantmentLevel7 > 0) {
                                                            this.drawEnchantTag("Pw" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
                                                            n11 += 6;
                                                      }

                                                      if (getEnchantmentLevel8 > 0) {
                                                            this.drawEnchantTag("Pn" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                                            n11 += 6;
                                                      }

                                                      if (getEnchantmentLevel9 > 0) {
                                                            this.drawEnchantTag("Fa" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
                                                      }
                                                } else if (itemStack.getRarity() == EnumRarity.EPIC) {
                                                      this.drawEnchantTag("В§6В§lGod", n10 - 2, n11);
                                                }
                                          }

                                          getEnchantmentLevel7 = (int)Math.round(255.0D - (double)itemStack.getItemDamage() * 255.0D / (double)itemStack.getMaxDamage());
                                          (new Color(255 - getEnchantmentLevel7 << 16 | getEnchantmentLevel7 << 8)).brighter();
                                          float n13 = (float)((double)n10 * 1.05D) - 2.0F;
                                          if (itemStack.getMaxDamage() - itemStack.getItemDamage() > 0) {
                                                GlStateManager.pushMatrix();
                                                GlStateManager.disableDepth();
                                                GlStateManager.enableDepth();
                                                GlStateManager.popMatrix();
                                          }

                                          n10 += 12;
                                    }
                              }
                        }

                        GlStateManager.popMatrix();
                  }
            }
      }

      private void drawEnchantTag(String text, int n, int n2) {
            GlStateManager.pushMatrix();
            GlStateManager.disableDepth();
            double var10002 = (double)((n *= 1) + 9);
            n2 -= 6;
            Fonts.neverlose500_16.drawStringWithShadow(text, var10002, (double)(-30 - n2), -1);
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
                  Object o = var2.next();
                  Entity ent = (Entity)o;
                  if (ent != Minecraft.player && ent instanceof EntityPlayer) {
                        double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX;
                        double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY;
                        double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ;
                        if (this.convertTo2D(x, y += (double)ent.height + 0.2D, z)[2] >= 0.0D && this.convertTo2D(x, y, z)[2] < 1.0D) {
                              entityPositions.put((EntityPlayer)ent, new double[]{this.convertTo2D(x, y, z)[0], this.convertTo2D(x, y, z)[1], Math.abs(this.mods2d(x, y + 1.0D, z, ent)[1] - this.mods2d(x, y, z, ent)[1]), this.convertTo2D(x, y, z)[2]});
                        }
                  }
            }

      }

      private double[] mods2d(double x, double y, double z, Entity ent) {
            float pTicks = mc.timer.renderPartialTicks;
            float prevYaw = Minecraft.player.rotationYaw;
            float prevPrevYaw = Minecraft.player.prevRotationYaw;
            float[] rotations = RotationHelper.getRotationFromPosition(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks, ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks, ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - 1.6D);
            mc.getRenderViewEntity().rotationYaw = mc.getRenderViewEntity().prevRotationYaw = rotations[0];
            mc.entityRenderer.setupCameraTransform(pTicks, 0);
            double[] convertedPoints = this.convertTo2D(x, y, z);
            mc.getRenderViewEntity().rotationYaw = prevYaw;
            mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
            mc.entityRenderer.setupCameraTransform(pTicks, 0);
            return convertedPoints;
      }

      private double[] convertTo2D(double x, double y, double z) {
            FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
            IntBuffer viewport = BufferUtils.createIntBuffer(16);
            FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
            FloatBuffer projection = BufferUtils.createFloatBuffer(16);
            GL11.glGetFloat(2982, modelView);
            GL11.glGetFloat(2983, projection);
            GL11.glGetInteger(2978, viewport);
            boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
            return result ? new double[]{(double)screenCoords.get(0), (double)((float)Display.getHeight() - screenCoords.get(1)), (double)screenCoords.get(2)} : null;
      }

      private void scale() {
            float scale = 1.0F * (mc.gameSettings.smoothCamera ? 2.0F : 1.0F);
            GlStateManager.scale(scale, scale, scale);
      }
}
