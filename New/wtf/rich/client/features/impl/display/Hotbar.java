package wtf.rich.client.features.impl.display;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event2D;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class Hotbar extends Feature {
     public Hotbar() {
          super("Hotbar", "Заменяет обычный хотбар майнкрафта , на более красивый", 0, Category.DISPLAY);
     }

     @EventTarget
     public void hotbar(Event2D event2D) {
          ScaledResolution scaledResolution = new ScaledResolution(mc);
          if (!(mc.currentScreen instanceof GuiChat)) {
               StringBuilder var10000 = (new StringBuilder()).append("FPS: ");
               Minecraft var10001 = mc;
               String fpsandping = var10000.append(Minecraft.getDebugFPS()).append(" Ping: ").append(((NetHandlerPlayClient)Objects.requireNonNull(mc.getConnection())).getPlayerInfo(mc.player.getUniqueID()).getResponseTime()).toString();
               String cords = "X:" + (int)mc.player.posX + " Y:" + (int)mc.player.posY + " Z:" + (int)mc.player.posZ;
               String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
               String date = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
               Minecraft.getMinecraft().sfui16.drawStringWithShadow(fpsandping, 3.0D, (double)(scaledResolution.getScaledHeight() - 17), -1);
               Minecraft.getMinecraft().sfui16.drawStringWithShadow(cords, 3.0D, (double)(scaledResolution.getScaledHeight() - 8), -1);
               Minecraft.getMinecraft().sfui16.drawStringWithShadow(time, (double)(scaledResolution.getScaledWidth() - Minecraft.getMinecraft().sfui16.getStringWidth(time) - 18), (double)scaledResolution.getScaledHeight() - 17.5D, -1);
               Minecraft.getMinecraft().sfui16.drawStringWithShadow(date, (double)(scaledResolution.getScaledWidth() - Minecraft.getMinecraft().sfui16.getStringWidth(date) - 7), (double)scaledResolution.getScaledHeight() - 8.5D, -1);
          }

     }
}
