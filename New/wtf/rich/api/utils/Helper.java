package wtf.rich.api.utils;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import wtf.rich.api.utils.world.TimerHelper;

public interface Helper {
     Minecraft mc = Minecraft.getMinecraft();
     Gui gui = new Gui();
     Random random = new Random();
     TimerHelper timerHelper = new TimerHelper();
     ScaledResolution sr = new ScaledResolution(mc);

     default void sendPacket(Packet packet) {
          mc.player.connection.sendPacket(packet);
     }
}
