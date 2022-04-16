package wtf.rich.client.ui;

import wtf.rich.api.utils.Helper;

public class MouseHelper implements Helper {
     public static boolean isHovered(double x, double y, double mouseX, double mouseY, int width, int height) {
          return (double)width > x && (double)height > y && (double)width < mouseX && (double)height < mouseY;
     }
}
