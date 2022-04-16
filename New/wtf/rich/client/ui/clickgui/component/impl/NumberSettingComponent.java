package wtf.rich.client.ui.clickgui.component.impl;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.ui.clickgui.component.Component;
import wtf.rich.client.ui.clickgui.component.PropertyComponent;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class NumberSettingComponent extends Component implements PropertyComponent {
     public NumberSetting numberSetting;
     public float currentValueAnimate = 0.0F;
     private boolean sliding;
     Minecraft mc = Minecraft.getMinecraft();

     public NumberSettingComponent(Component parent, NumberSetting numberSetting, int x, int y, int width, int height) {
          super(parent, numberSetting.getName(), x, y, width, height);
          this.numberSetting = numberSetting;
     }

     public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
          super.drawComponent(scaledResolution, mouseX, mouseY);
          int x = this.getX();
          int y = this.getY();
          int width = this.getWidth();
          int height = this.getHeight();
          double min = (double)this.numberSetting.getMinValue();
          double max = (double)this.numberSetting.getMaxValue();
          boolean hovered = this.isHovered(mouseX, mouseY);
          if (this.sliding) {
               this.numberSetting.setValueNumber((float)MathematicHelper.round((double)(mouseX - x) * (max - min) / (double)width + min, (double)this.numberSetting.getIncrement()));
               if ((double)this.numberSetting.getNumberValue() > max) {
                    this.numberSetting.setValueNumber((float)max);
               } else if ((double)this.numberSetting.getNumberValue() < min) {
                    this.numberSetting.setValueNumber((float)min);
               }
          }

          float amountWidth = (float)(((double)this.numberSetting.getNumberValue() - min) / (max - min));
          int color = 0;
          Color onecolor = new Color(ClickGUI.color.getColorValue());
          Color twocolor = new Color(ClickGUI.colorTwo.getColorValue());
          double speed = (double)ClickGUI.speed.getNumberValue();
          String var19 = ClickGUI.clickGuiColor.currentMode;
          byte var20 = -1;
          switch(var19.hashCode()) {
          case -1808614770:
               if (var19.equals("Static")) {
                    var20 = 4;
               }
               break;
          case -1656737386:
               if (var19.equals("Rainbow")) {
                    var20 = 5;
               }
               break;
          case -311641137:
               if (var19.equals("Color Two")) {
                    var20 = 2;
               }
               break;
          case 2181788:
               if (var19.equals("Fade")) {
                    var20 = 1;
               }
               break;
          case 961091784:
               if (var19.equals("Astolfo")) {
                    var20 = 3;
               }
               break;
          case 2021122027:
               if (var19.equals("Client")) {
                    var20 = 0;
               }
          }

          switch(var20) {
          case 0:
               color = DrawHelper.fadeColor(ClientHelper.getClientColor().getRGB(), ClientHelper.getClientColor().darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((long)y * 6L / 60L * 2L)) % 2.0D - 1.0D));
               break;
          case 1:
               color = DrawHelper.fadeColor(onecolor.getRGB(), onecolor.darker().getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((float)((long)y * 6L) / 60.0F * 2.0F)) % 2.0D - 1.0D));
               break;
          case 2:
               color = DrawHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float)Math.abs(((double)System.currentTimeMillis() / speed / speed + (double)((float)((long)y * 6L) / 60.0F * 2.0F)) % 2.0D - 1.0D));
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

          this.currentValueAnimate = AnimationHelper.animation(this.currentValueAnimate, amountWidth, 0.0F);
          float optionValue = (float)MathematicHelper.round((double)this.numberSetting.getNumberValue(), (double)this.numberSetting.getIncrement());
          DrawHelper.drawRect((double)x, (double)y, (double)(x + width), (double)(y + height), (new Color(20, 20, 20, 160)).getRGB());
          DrawHelper.drawRect((double)(x + 3), (double)(y + height - 5), (double)(x + (width - 3)), (double)(y + 13), (new Color(40, 39, 39)).getRGB());
          DrawHelper.drawRect((double)(x + 3), (double)y + 13.5D, (double)((float)(x + 5) + this.currentValueAnimate * (float)(width - 8)), (double)((float)y + 15.0F), color);
          DrawHelper.drawFilledCircle((int)((float)(x + 5) + this.currentValueAnimate * (float)(width - 8)), (int)((float)y + 14.0F), 1.3F, new Color(color));
          String valueString = "";
          NumberSetting.NumberType numberType = this.numberSetting.getType();
          switch(numberType) {
          case PERCENTAGE:
               valueString = valueString + '%';
               break;
          case MS:
               valueString = valueString + "ms";
               break;
          case DISTANCE:
               valueString = valueString + 'm';
          case SIZE:
               valueString = valueString + "SIZE";
          case APS:
               valueString = valueString + "APS";
               break;
          default:
               valueString = "";
          }

          this.mc.neverlose500_13.drawStringWithShadow(this.numberSetting.getName(), (double)((float)x + 2.0F), (double)((float)y + (float)height / 2.5F - 4.0F), Color.lightGray.getRGB());
          this.mc.neverlose500_14.drawStringWithShadow(optionValue + " " + valueString, (double)(x + width - this.mc.neverlose500_16.getStringWidth(optionValue + " " + valueString) - 5), (double)((float)y + (float)height / 2.5F - 4.0F), Color.GRAY.getRGB());
          if (hovered && this.numberSetting.getDesc() != null) {
               double var10000 = (double)(x + 120);
               double var10001 = (double)((float)y + (float)height / 1.5F + 3.5F);
               int var10002 = x + 138;
               Minecraft var10003 = this.mc;
               DrawHelper.drawNewRect(var10000, var10001, (double)(var10002 + Minecraft.fontRendererObj.getStringWidth(this.numberSetting.getDesc()) - 10), (double)((float)y + 3.5F), (new Color(30, 30, 30, 255)).getRGB());
               Minecraft var22 = this.mc;
               Minecraft.fontRendererObj.drawStringWithShadow(this.numberSetting.getDesc(), (float)(x + 124), (float)y + (float)height / 1.5F - 6.0F, -1);
          }

     }

     public void onMouseClick(int mouseX, int mouseY, int button) {
          if (!this.sliding && button == 0 && this.isHovered(mouseX, mouseY)) {
               this.sliding = true;
          }

     }

     public void onMouseRelease(int button) {
          this.sliding = false;
     }

     public Setting getSetting() {
          return this.numberSetting;
     }
}
