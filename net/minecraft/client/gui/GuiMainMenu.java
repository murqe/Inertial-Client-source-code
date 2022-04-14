package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import me.rich.altmgr.GuiAlt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import optifine.CustomPanorama;
import optifine.CustomPanoramaProperties;
import optifine.Reflector;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen {
      private static final Logger LOGGER = LogManager.getLogger();
      private static final Random RANDOM = new Random();
      private final float updateCounter;
      private String splashText;
      private GuiButton buttonResetDemo;
      private float panoramaTimer;
      private DynamicTexture viewportTexture;
      private final Object threadLock = new Object();
      public static final String MORE_INFO_TEXT;
      private int openGLWarning2Width;
      private int openGLWarning1Width;
      private int openGLWarningX1;
      private int openGLWarningY1;
      private int openGLWarningX2;
      private int openGLWarningY2;
      private String openGLWarning1;
      private String openGLWarning2;
      private String openGLWarningLink;
      private static final ResourceLocation SPLASH_TEXTS;
      private static final ResourceLocation MINECRAFT_TITLE_TEXTURES;
      private static final ResourceLocation field_194400_H;
      private static final ResourceLocation[] TITLE_PANORAMA_PATHS;
      private ResourceLocation backgroundTexture;
      private GuiButton realmsButton;
      private boolean hasCheckedForRealmsNotification;
      private GuiScreen realmsNotification;
      private int field_193978_M;
      private int field_193979_N;
      private GuiButton modButton;
      private GuiScreen modUpdateNotification;
      private final long initTime = System.currentTimeMillis();

      public GuiMainMenu() {
            this.openGLWarning2 = MORE_INFO_TEXT;
            this.splashText = "missingno";
            IResource iresource = null;

            try {
                  List list = Lists.newArrayList();
                  iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
                  BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));

                  String s;
                  while((s = bufferedreader.readLine()) != null) {
                        s = s.trim();
                        if (!s.isEmpty()) {
                              list.add(s);
                        }
                  }

                  if (!list.isEmpty()) {
                        do {
                              this.splashText = (String)list.get(RANDOM.nextInt(list.size()));
                        } while(this.splashText.hashCode() == 125780783);
                  }
            } catch (IOException var8) {
            } finally {
                  IOUtils.closeQuietly(iresource);
            }

            this.updateCounter = RANDOM.nextFloat();
            this.openGLWarning1 = "";
      }

      private boolean areRealmsNotificationsEnabled() {
            return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.realmsNotification != null;
      }

      public void updateScreen() {
            if (this.areRealmsNotificationsEnabled()) {
                  this.realmsNotification.updateScreen();
            }

      }

      public boolean doesGuiPauseGame() {
            return false;
      }

      protected void keyTyped(char typedChar, int keyCode) throws IOException {
      }

      public void initGui() {
            this.viewportTexture = new DynamicTexture(256, 256);
            this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("1", this.viewportTexture);
            this.field_193978_M = this.fontRendererObj.getStringWidth("1");
            this.field_193979_N = this.width - this.field_193978_M - 49;
            int i = true;
            int j = this.height / 4 + 48;
            if (this.mc.isDemo()) {
                  this.addDemoButtons(j, 24);
            } else {
                  this.addSingleplayerMultiplayerButtons(j, 24);
            }

            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 80 + 12, 99, 20, I18n.format("menu.options")));
            this.buttonList.add(new GuiButton(4, this.width / 2 + 1, j + 80 + 12, 99, 20, I18n.format("menu.quit")));
            synchronized(this.threadLock) {
                  this.openGLWarning1Width = this.fontRendererObj.getStringWidth(this.openGLWarning1);
                  this.openGLWarning2Width = this.fontRendererObj.getStringWidth(this.openGLWarning2);
                  int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
                  this.openGLWarningX1 = (this.width - k) / 2;
                  this.openGLWarningY1 = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
                  this.openGLWarningX2 = this.openGLWarningX1 + k;
                  this.openGLWarningY2 = this.openGLWarningY1 + 24;
            }

            this.mc.setConnectedToRealms(false);
            if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.hasCheckedForRealmsNotification) {
                  RealmsBridge realmsbridge = new RealmsBridge();
                  this.realmsNotification = realmsbridge.getNotificationScreen(this);
                  this.hasCheckedForRealmsNotification = true;
            }

            if (this.areRealmsNotificationsEnabled()) {
                  this.realmsNotification.setGuiSize(this.width, this.height);
                  this.realmsNotification.initGui();
            }

            if (Reflector.NotificationModUpdateScreen_init.exists()) {
                  this.modUpdateNotification = (GuiScreen)Reflector.call(Reflector.NotificationModUpdateScreen_init, this, this.modButton);
            }

      }

      private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
            String name = "��������� �����";
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_ + 20, I18n.format("menu.singleplayer")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1 + 20, I18n.format("menu.multiplayer")));
            if (Reflector.GuiModList_Constructor.exists()) {
                  this.realmsButton = this.addButton(new GuiButton(14, this.width / 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("menu.online").replace("Minecraft", "").trim()));
                  this.buttonList.add(this.modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));
            } else {
                  this.realmsButton = this.addButton(new GuiButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2 + 20, I18n.format("Alt Manager")));
            }

      }

      private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
            this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
            this.buttonResetDemo = this.addButton(new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
            if (worldinfo == null) {
                  this.buttonResetDemo.enabled = false;
            }

      }

      protected void actionPerformed(GuiButton button) throws IOException {
            if (button.id == 0) {
                  this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            }

            if (button.id == 1) {
                  this.mc.displayGuiScreen(new GuiWorldSelection(this));
            }

            if (button.id == 2) {
                  this.mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (button.id == 14) {
                  this.mc.displayGuiScreen(new GuiAlt(this));
            }

            if (button.id == 4) {
                  this.mc.shutdown();
            }

            if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
                  this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, this));
            }

            if (button.id == 11) {
                  this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
            }

            if (button.id == 12) {
                  ISaveFormat isaveformat = this.mc.getSaveLoader();
                  WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
                  if (worldinfo != null) {
                        this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
                  }
            }

      }

      private void switchToRealms() {
            RealmsBridge realmsbridge = new RealmsBridge();
            realmsbridge.switchToRealms(this);
      }

      public void confirmClicked(boolean result, int id) {
            if (result && id == 12) {
                  ISaveFormat isaveformat = this.mc.getSaveLoader();
                  isaveformat.flushCache();
                  isaveformat.deleteWorldDirectory("Demo_World");
                  this.mc.displayGuiScreen(this);
            } else if (id == 12) {
                  this.mc.displayGuiScreen(this);
            } else if (id == 13) {
                  if (result) {
                        try {
                              Class oclass = Class.forName("java.awt.Desktop");
                              Object object = oclass.getMethod("getDesktop").invoke((Object)null);
                              oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                        } catch (Throwable var5) {
                              LOGGER.error("Couldn't open link", var5);
                        }
                  }

                  this.mc.displayGuiScreen(this);
            }

      }

      private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableCull();
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            int i = true;
            int j = 64;
            CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
            if (custompanoramaproperties != null) {
                  j = custompanoramaproperties.getBlur1();
            }

            for(int k = 0; k < j; ++k) {
                  GlStateManager.pushMatrix();
                  float f = ((float)(k % 8) / 8.0F - 0.5F) / 64.0F;
                  float f1 = ((float)(k / 8) / 8.0F - 0.5F) / 64.0F;
                  float f2 = 0.0F;
                  GlStateManager.translate(f, f1, 0.0F);
                  GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(-this.panoramaTimer * 0.1F, 0.0F, 1.0F, 0.0F);

                  for(int l = 0; l < 6; ++l) {
                        GlStateManager.pushMatrix();
                        if (l == 1) {
                              GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                        }

                        if (l == 2) {
                              GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                        }

                        if (l == 3) {
                              GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                        }

                        if (l == 4) {
                              GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                        }

                        if (l == 5) {
                              GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                        }

                        ResourceLocation[] aresourcelocation = TITLE_PANORAMA_PATHS;
                        if (custompanoramaproperties != null) {
                              aresourcelocation = custompanoramaproperties.getPanoramaLocations();
                        }

                        this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
                        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                        int i1 = 255 / (k + 1);
                        float f3 = 0.0F;
                        bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, i1).endVertex();
                        bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, i1).endVertex();
                        bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, i1).endVertex();
                        bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, i1).endVertex();
                        tessellator.draw();
                        GlStateManager.popMatrix();
                  }

                  GlStateManager.popMatrix();
                  GlStateManager.colorMask(true, true, true, false);
            }

            bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.enableDepth();
      }

      private void rotateAndBlurSkybox() {
            this.mc.getTextureManager().bindTexture(this.backgroundTexture);
            GlStateManager.glTexParameteri(3553, 10241, 9729);
            GlStateManager.glTexParameteri(3553, 10240, 9729);
            GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.colorMask(true, true, true, false);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            GlStateManager.disableAlpha();
            int i = true;
            int j = 3;
            CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
            if (custompanoramaproperties != null) {
                  j = custompanoramaproperties.getBlur2();
            }

            for(int k = 0; k < j; ++k) {
                  float f = 1.0F / (float)(k + 1);
                  int l = this.width;
                  int i1 = this.height;
                  float f1 = (float)(k - 1) / 256.0F;
                  bufferbuilder.pos((double)l, (double)i1, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
                  bufferbuilder.pos((double)l, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
                  bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
                  bufferbuilder.pos(0.0D, (double)i1, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            }

            tessellator.draw();
            GlStateManager.enableAlpha();
            GlStateManager.colorMask(true, true, true, true);
      }

      private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
            this.mc.getFramebuffer().unbindFramebuffer();
            GlStateManager.viewport(0, 0, 256, 256);
            this.drawPanorama(mouseX, mouseY, partialTicks);
            this.rotateAndBlurSkybox();
            int i = 3;
            CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
            if (custompanoramaproperties != null) {
                  i = custompanoramaproperties.getBlur3();
            }

            for(int j = 0; j < i; ++j) {
                  this.rotateAndBlurSkybox();
                  this.rotateAndBlurSkybox();
            }

            this.mc.getFramebuffer().bindFramebuffer(true);
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            float f2 = 120.0F / (float)(this.width > this.height ? this.width : this.height);
            float f = (float)this.height * f2 / 256.0F;
            float f1 = (float)this.width * f2 / 256.0F;
            int k = this.width;
            int l = this.height;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            bufferbuilder.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            bufferbuilder.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            bufferbuilder.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            tessellator.draw();
      }

      public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            int posX = true;
            int posY = true;
            int j = this.height / 4 + 48;
            GlStateManager.disableAlpha();
            GlStateManager.enableAlpha();
            this.mc.getTextureManager().bindTexture(new ResourceLocation("toperclient/1613224280_151-p-fon-sinii-kosmos-226.png"));
            Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height, (float)this.width, (float)this.height);
            new ScaledResolution(this.mc);
            super.drawScreen(mouseX, mouseY, partialTicks);
      }

      protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            synchronized(this.threadLock) {
                  if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2) {
                        GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                        guiconfirmopenlink.disableSecurityWarning();
                        this.mc.displayGuiScreen(guiconfirmopenlink);
                  }
            }

            if (this.areRealmsNotificationsEnabled()) {
                  this.realmsNotification.mouseClicked(mouseX, mouseY, mouseButton);
            }

            if (mouseX > this.field_193979_N && mouseX < this.field_193979_N + this.field_193978_M && mouseY > this.height - 10 && mouseY < this.height) {
                  this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
            }

      }

      public void onGuiClosed() {
            if (this.realmsNotification != null) {
                  this.realmsNotification.onGuiClosed();
            }

      }

      static {
            MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
            SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
            MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
            field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
            TITLE_PANORAMA_PATHS = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
      }
}
