package wtf.rich.client.ui.clickgui.component.impl;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.PropertyComponent;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class BooleanSettingComponent extends Component implements PropertyComponent {
     public BooleanSetting booleanSetting;
     public float textHoverAnimate = 0.0F;
     public float leftRectAnimation = 0.0F;
     public float rightRectAnimation = 0.0F;
     Minecraft mc = Minecraft.getMinecraft();

     public BooleanSettingComponent(Component parent, BooleanSetting booleanSetting, int x, int y, int width, int height) {
          super(parent, booleanSetting.getName(), x, y, width, height);
          this.booleanSetting = booleanSetting;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          if (this.booleanSetting.isVisible()) {
               int x = this.getX();
               int y = this.getY();
               int width = this.getWidth();
               int height = this.getHeight();
               int middleHeight = this.getHeight() / 2;
               boolean hovered = this.isHovered(mouseX, mouseY);
               int color = 0;
               Color onecolor = new Color(ClickGUI.color.getColorValue());
               Color twocolor = new Color(ClickGUI.colorTwo.getColorValue());
               double speed = (double)ClickGUI.speed.getNumberValue();
               String var15 = ClickGUI.clickGuiColor.currentMode;
               byte var16 = -1;
               switch(var15.hashCode()) {
               case -1808614770:
                    if (var15.equals("Static")) {
                         var16 = 4;
                    }
                    break;
               case -1656737386:
                    if (var15.equals("Rainbow")) {
                         var16 = 5;
                    }
                    break;
               case -311641137:
                    if (var15.equals("Color Two")) {
                         var16 = 2;
                    }
                    break;
               case 2181788:
                    if (var15.equals("Fade")) {
                         var16 = 1;
                    }
                    break;
               case 961091784:
                    if (var15.equals("Astolfo")) {
                         var16 = 3;
                    }
                    break;
               case 2021122027:
                    if (var15.equals("Client")) {
                         var16 = 0;
                    }
               }

               switch(var16) {
               case 0:
                    color = DrawHelper.fadeColor(ClientHelper.getClientColor().getRGB(), ClientHelper.getClientColor().darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)y * 6L / 60L * 2L)) % 2.0D - 1.0D));
                    break;
               case 1:
                    color = DrawHelper.fadeColor(onecolor.getRGB(), onecolor.darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)y + (double)((float)((long)height * 6L) / 60.0F * 2.0F)) % 2.0D - 1.0D));
                    break;
               case 2:
                    color = DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)y + (double)((float)((long)height * 6L) / 60.0F * 2.0F)) % 2.0D - 1.0D));
                    break;
               case 3:
                    color = DrawHelper.astolfo(true, y).getRGB();
                    break;
               case 4:
                    color = onecolor.getRGB();
                    break;
               case 5:
                    color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
               }

               DrawHelper.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), (new Color(20, 20, 20, 160)).getRGB());
               this.mc.neverlose500_13.drawStringWithShadow(this.getName(), (double)(x + 3), (double)(y + middleHeight - 2), Color.GRAY.getRGB());
               this.textHoverAnimate = AnimationHelper.animation(this.textHoverAnimate, hovered ? 2.3F : 2.0F, 0.0F);
               this.leftRectAnimation = AnimationHelper.animation(this.leftRectAnimation, this.booleanSetting.getBoolValue() ? 10.0F : 17.0F, 0.0F);
               this.rightRectAnimation = AnimationHelper.animation(this.rightRectAnimation, (float)(this.booleanSetting.getBoolValue() ? 3 : 10), 0.0F);
               DrawHelper.drawSmoothRect((float)(x + width - 18), (float)(y + 5), (float)(x + width) - 3.2F, (float)(y + height) - 6.2F, (new Color(4, 4, 4, 100)).getRGB());
               DrawHelper.drawNewRect((double)((float)(x + width) - this.leftRectAnimation), (double)(y + 6), (double)((float)(x + width) - this.rightRectAnimation - 2.0F), (double)(y + height - 7), this.booleanSetting.getBoolValue() ? color : (new Color(111, 111, 111, 50)).getRGB());
               if (hovered && this.booleanSetting.getDesc() != null) {
                    double var10000 = (double)(x + 120);
                    double var10001 = (double)((float)y + (float)height / 1.5F + 3.5F);
                    int var10002 = x + 138;
                    Minecraft var10003 = this.mc;
                    DrawHelper.drawNewRect(var10000, var10001, (double)(var10002 + Minecraft.fontRendererObj.getStringWidth(this.booleanSetting.getDesc()) - 10), (double)((float)y + 3.5F), (new Color(30, 30, 30, 255)).getRGB());
                    Minecraft var17 = this.mc;
                    Minecraft.fontRendererObj.drawStringWithShadow(this.booleanSetting.getDesc(), (float)(x + 124), (float)y + (float)height / 1.5F - 6.0F, -1);
               }
          }

     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          if (button == 0 && this.isHovered(mouseX, mouseY) && this.booleanSetting.isVisible()) {
               this.booleanSetting.setBoolValue(!this.booleanSetting.getBoolValue());
          }

     }

     public Setting getSetting() {
          return this.booleanSetting;
     }
}
