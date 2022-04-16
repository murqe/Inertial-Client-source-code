package wtf.rich;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import wtf.rich.api.changelogs.ChangeManager;
import wtf.rich.api.command.CommandManager;
import wtf.rich.api.command.macro.Macro;
import wtf.rich.api.command.macro.MacroManager;
import wtf.rich.api.event.EventManager;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventKey;
import wtf.rich.api.utils.friend.FriendManager;
import wtf.rich.api.utils.shader.ShaderShell;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.FeatureDirector;
import wtf.rich.client.features.impl.misc.DiscordRPC;
import wtf.rich.client.ui.clickgui.ClickGuiScreen;
import wtf.rich.client.ui.settings.FileManager;
import wtf.rich.client.ui.settings.config.ConfigManager;
import wtf.rich.client.ui.settings.impls.FriendConfig;
import wtf.rich.client.ui.settings.impls.MacroConfig;

public class Main {
     public static String holo = "Holo";
     public static Main instance = new Main();
     public String name = "INERTIAL FREE";
     public String version = "1.1";
     public EventManager eventManager;
     public FeatureDirector featureDirector;
     public ConfigManager configManager;
     public FriendManager friendManager;
     public FileManager fileManager;
     public ChangeManager changeManager;
     public MacroManager macroManager;
     public CommandManager commandManager;
     public ClickGuiScreen clickGui;

     public void startClient() {
          ShaderShell.init();
          DiscordRPC.init();
          this.eventManager = new EventManager();
          this.featureDirector = new FeatureDirector();
          this.configManager = new ConfigManager();
          this.commandManager = new CommandManager();
          this.macroManager = new MacroManager();
          this.changeManager = new ChangeManager();
          this.clickGui = new ClickGuiScreen();
          this.friendManager = new FriendManager();
          Display.setTitle(this.name);

          try {
               this.fileManager.getFile(FriendConfig.class).loadFile();
          } catch (Exception var3) {
          }

          try {
               this.fileManager.getFile(MacroConfig.class).loadFile();
          } catch (Exception var2) {
          }

          EventManager.register(this);
     }

     public void stopClient() {
          EventManager.unregister(instance);
          (this.fileManager = new FileManager()).saveFiles();
     }

     public static void msg(String s, boolean prefix) {
          s = (prefix ? TextFormatting.GRAY + "[" + TextFormatting.RED + "INERTIAL" + TextFormatting.GRAY + "]: " : "") + s;
          Minecraft.getMinecraft().player.addChatMessage(new TextComponentTranslation(s.replace("&", "??"), new Object[0]));
     }

     @EventTarget
     public void onKey(EventKey event) {
          Iterator var2 = this.featureDirector.getFeatureList().iterator();

          while(var2.hasNext()) {
               Feature feature = (Feature)var2.next();
               if (feature.getKey() == event.getKey()) {
                    feature.toggle();
               }

               Iterator var4 = this.macroManager.getMacros().iterator();

               while(var4.hasNext()) {
                    Macro macro = (Macro)var4.next();
                    if (macro.getKey() == Keyboard.getEventKey() && Minecraft.getMinecraft().player.getHealth() > 0.0F && Minecraft.getMinecraft().player != null) {
                         Minecraft.getMinecraft().player.sendChatMessage(macro.getValue());
                    }
               }
          }

     }
}
