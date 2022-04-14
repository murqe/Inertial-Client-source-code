package me.rich;

import clickgui.ClickGuiScreen;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.SettingsManager;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import me.rich.changelogs.ChangeLogMngr;
import me.rich.config.Config;
import me.rich.event.EventManager;
import me.rich.event.EventTarget;
import me.rich.event.events.EventKey;
import me.rich.module.Feature;
import me.rich.module.FeatureDirector;
import me.rich.module.misc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.opengl.Display;

public class Main {
      public static Main instance = new Main();
      public static String name = "InertialClient";
      public static String version = "#0001";
      public static EventManager eventManager;
      public static FeatureDirector moduleManager;
      public static SettingsManager settingsManager;
      public static ChangeLogMngr changeLogMngr;
      public static ClickGuiScreen clickGui1;
      public static Config config;

      public static void startClient() {
            try {
                  Desktop.getDesktop().browse(new URI("https://vk.com/inertialclient"));
            } catch (IOException | URISyntaxException var1) {
                  System.out.println("Error loading site.");
                  var1.printStackTrace();
            }

            (changeLogMngr = new ChangeLogMngr()).setChangeLogs();
            eventManager = new EventManager();
            settingsManager = new SettingsManager();
            moduleManager = new FeatureDirector();
            clickGui1 = new ClickGuiScreen();
            config = new Config();
            Display.setTitle(name);
            DiscordRPC.init();
            EventManager.register(instance);
      }

      public static void stopClient() {
            EventManager.unregister(instance);
      }

      public static ChangeLogMngr getChangeLogMngr() {
            return changeLogMngr;
      }

      public static void msg(String text) {
            Minecraft.player.addChatMessage(new TextComponentString(ChatFormatting.RED + "InertialClient: " + ChatFormatting.WHITE + text));
      }

      @EventTarget
      public void onKey(EventKey event) {
            FeatureDirector.getModules().stream().filter((module) -> {
                  return module.getKey() == event.getKey();
            }).forEach(Feature::toggle);
      }
}
