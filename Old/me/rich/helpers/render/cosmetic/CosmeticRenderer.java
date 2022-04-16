package me.rich.helpers.render.cosmetic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import me.rich.Main;
import me.rich.module.render.Cosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class CosmeticRenderer implements LayerRenderer {
      private final RenderPlayer playerRenderer;

      public CosmeticRenderer(RenderPlayer playerRenderer) {
            this.playerRenderer = playerRenderer;
      }

      public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            if (Main.moduleManager.getModule(Cosmetics.class).isToggled() && player.hasPlayerInfo() && !player.isInvisible()) {
                  Minecraft.getMinecraft();
                  if (player == Minecraft.player) {
                        if (Cosmetics.wing) {
                              Cosmetic.renderAccessory(new String[]{"Dragon_wing"}, player, partialTicks);
                        }

                        if (Cosmetics.cape) {
                              if (!player.isWearing(EnumPlayerModelParts.CAPE)) {
                                    return;
                              }

                              ResourceLocation rl = Cosmetic.getCape(Cosmetics.capepng);
                              GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                              this.playerRenderer.bindTexture(rl);
                              GlStateManager.pushMatrix();
                              GlStateManager.translate(0.0F, 0.0F, 0.125F);
                              double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
                              double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double)partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks);
                              double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);
                              float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
                              double d3 = (double)MathHelper.sin(f * 0.017453292F);
                              double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
                              float f1 = (float)d1 * 10.0F;
                              f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
                              float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                              float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                              if (f2 < 0.0F) {
                                    f2 = 0.0F;
                              }

                              if (f2 > 165.0F) {
                                    f2 = 165.0F;
                              }

                              float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
                              f1 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
                              if (player.isSneaking()) {
                                    f1 += 25.0F;
                                    GlStateManager.translate(0.0F, 0.142F, -0.0178F);
                              }

                              GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
                              GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
                              GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
                              GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                              this.playerRenderer.getMainModel().renderCape(0.0625F);
                              GlStateManager.popMatrix();
                        }
                  }
            }

      }

      public boolean shouldCombineTextures() {
            return false;
      }

      public static void bindTexture(String url, String resource) {
            IImageBuffer iib = new IImageBuffer() {
                  ImageBufferDownload ibd = new ImageBufferDownload();

                  public BufferedImage parseUserSkin(BufferedImage var1) {
                        return CosmeticRenderer.parseCape(var1);
                  }

                  public void skinAvailable() {
                  }
            };
            ResourceLocation rl = new ResourceLocation(resource);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            textureManager.getTexture(rl);
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData((File)null, url, (ResourceLocation)null, iib);
            textureManager.loadTexture(rl, textureCape);
      }

      private static BufferedImage parseCape(BufferedImage img) {
            int imageWidth = 64;
            int imageHeight = 32;
            int srcWidth = img.getWidth();

            for(int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
                  imageWidth *= 2;
            }

            BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
            Graphics g = imgNew.getGraphics();
            g.drawImage(img, 0, 0, (ImageObserver)null);
            g.dispose();
            return imgNew;
      }
}
