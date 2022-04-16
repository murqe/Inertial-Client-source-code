package clickgui.panel.component.impl;

import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import de.Hero.settings.Setting;
import java.awt.Color;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorUtils;
import me.rich.helpers.render.RenderUtil;

public final class BoolOptionComponent extends Component {
      private int opacity = 120;
      private int animation = 20;
      float textHoverAnimate = 0.0F;
      float leftRectAnimation = 0.0F;
      double rightRectAnimation = 0.0D;

      public BoolOptionComponent(Setting option, Panel panel, int x, int y, int width, int height) {
            super(panel, x, y, width, height);
            this.option = option;
      }

      public void onDraw(int mouseX, int mouseY) {
            Panel parent = this.getPanel();
            int x = parent.getX() + this.getX();
            int y = parent.getY() + this.getY();
            boolean hovered = this.isMouseOver(mouseX, mouseY);
            if (hovered) {
                  if (this.opacity < 200) {
                        this.opacity += 5;
                  }
            } else if (this.opacity > 120) {
                  this.opacity -= 5;
            }

            if (this.option.getValBoolean()) {
                  if (this.animation < 30) {
                        ++this.animation;
                  }
            } else if (this.animation > 20) {
                  --this.animation;
            }

            RenderUtil.drawNewRect((double)x, (double)y, (double)(x + this.getWidth()), (double)(y + this.getHeight()), parent.dragging ? 150994944 : ColorUtils.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
            int color = this.option.getValBoolean() ? -1 : (new Color(this.opacity, this.opacity, this.opacity)).getRGB();
            this.textHoverAnimate = AnimationHelper.animation(this.textHoverAnimate, hovered ? 2.3F : 2.0F, 0.01F);
            this.leftRectAnimation = AnimationHelper.animation(this.leftRectAnimation, this.option.getValBoolean() ? 10.0F : 17.0F, 1.0E-13F);
            this.rightRectAnimation = (double)AnimationHelper.animation((float)this.rightRectAnimation, this.option.getValBoolean() ? 3.0F : 10.0F, 1.0E-13F);
            RenderUtil.drawSmoothRect((float)((double)x + this.width - 18.0D), (float)(y + 4), (float)((double)x + this.width - 2.0D), (float)((double)y + this.height - 5.0D), (new Color(14, 14, 14)).getRGB());
            RenderUtil.drawSmoothRect((float)((double)x + this.width - (double)this.leftRectAnimation), (float)(y + 5), (float)((double)x + this.width - this.rightRectAnimation), (float)(y + this.getHeight()) - 6.5F, this.option.getValBoolean() ? ColorUtils.getColor(0, 255, 255) : (new Color(50, 50, 50)).getRGB());
            Fonts.neverlose502.drawStringWithShadow(this.option.getName(), (double)((float)x + 5.0F), (double)((float)y + (float)this.getHeight() / this.textHoverAnimate - 3.0F), color);
      }

      public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
            if (this.isMouseOver(mouseX, mouseY)) {
                  this.option.setValBoolean(!this.option.getValBoolean());
            }

      }
}
