package clickgui.panel.component.impl;

import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import de.Hero.settings.Setting;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;

public final class NumberOptionComponent extends Component {
      private boolean dragging = false;
      private int opacity = 120;
      private float animation = 0.0F;
      private float textHoverAnimate = 0.0F;
      private float currentValueAnimate = 0.0F;

      public NumberOptionComponent(Setting option, Panel panel, int x, int y, int width, int height) {
            super(panel, x, y, width, height);
            this.option = option;
      }

      public void onDraw(int mouseX, int mouseY) {
            Panel parent = this.getPanel();
            int x = parent.getX() + this.getX();
            int y = parent.getY() + this.getY() + -1;
            boolean hovered = this.isMouseOver(mouseX, mouseY);
            int height = this.getHeight();
            int width = this.getWidth();
            Setting option = this.option;
            double min = option.getMin();
            double max = option.getMax();
            if (this.dragging) {
                  option.setValDouble(this.round((double)(mouseX - x) * (max - min) / (double)width + min, 0.01D));
                  if (Double.valueOf(option.getValDouble()) > max) {
                        option.setValDouble(max);
                  } else if (Double.valueOf(option.getValDouble()) < min) {
                        option.setValDouble(min);
                  }
            }

            double optionValue = this.round(option.getValDouble(), 0.01D);
            String optionValueStr = String.valueOf(optionValue);
            int color = Color.WHITE.getRGB();
            double kak = (option.getValDouble() - option.getMin()) / (option.getMax() - option.getMin());
            this.currentValueAnimate = AnimationHelper.animation(this.currentValueAnimate, (float)kak, 1.0E-9F);
            double renderPerc = (double)(width - 2) / (max - min);
            double barWidth = renderPerc * optionValue - renderPerc * min;
            if (hovered) {
                  if (this.opacity < 200) {
                        this.opacity += 5;
                  }
            } else if (this.opacity > 120) {
                  this.opacity -= 5;
            }

            this.textHoverAnimate = AnimationHelper.animation(this.textHoverAnimate, hovered ? 2.3F : 2.0F, 1.0E-12F);
            this.animation = AnimationHelper.animation(this.animation, this.dragging ? (float)(y + height - 6) : (float)(y + height - 5), 1.0E-6F);
            RenderUtil.drawNewRect((double)x, (double)y, (double)(x + width), (double)(y + height), parent.dragging ? 150994944 : ColorUtils.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
            RenderUtil.drawNewRect((double)(x + 3), (double)this.animation, (double)(x + (width - 3)), (double)(y + height - 2), (new Color(45, 44, 44)).getRGB());
            RenderUtil.drawGradientSideways((double)(x + 3), (double)(y + height - 5), (double)((float)x + (float)width * this.currentValueAnimate), (double)(y + height - 2), ColorUtils.getColor(0, 255, 255), (new Color(0, 0, 0)).getRGB());
            Fonts.neverlose502.drawStringWithShadow(option.getName(), (double)((float)x + 2.0F), (double)((float)y + (float)height / this.textHoverAnimate - 4.0F), color);
            Fonts.neverlose502.drawStringWithShadow(optionValueStr, (double)(x + width - Fonts.neverlose502.getStringWidth(optionValueStr) - 3), (double)((float)y + (float)height / this.textHoverAnimate - 4.0F), color);
      }

      public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
            if (this.isMouseOver(mouseX, mouseY)) {
                  this.dragging = true;
            }

      }

      public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
            this.dragging = false;
      }

      private double round(double num, double increment) {
            double v = (double)Math.round(num / increment) * increment;
            BigDecimal bd = new BigDecimal(v);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
      }
}
