package wtf.rich.api.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import wtf.rich.Main;
import wtf.rich.api.command.CommandAbstract;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.client.ui.settings.config.Config;
import wtf.rich.client.ui.settings.config.ConfigManager;

public class ConfigCommand extends CommandAbstract {
     public ConfigCommand() {
          super("config", "configurations", "§6.config" + ChatFormatting.RED + " save | load | delete §7<name>", "config");
     }

     public void execute(String... args) {
          try {
               if (args.length >= 2) {
                    String upperCase = args[1].toUpperCase();
                    if (args.length == 3) {
                         byte var4 = -1;
                         switch(upperCase.hashCode()) {
                         case 2342118:
                              if (upperCase.equals("LOAD")) {
                                   var4 = 0;
                              }
                              break;
                         case 2537853:
                              if (upperCase.equals("SAVE")) {
                                   var4 = 1;
                              }
                              break;
                         case 2012838315:
                              if (upperCase.equals("DELETE")) {
                                   var4 = 2;
                              }
                         }

                         switch(var4) {
                         case 0:
                              if (Main.instance.configManager.loadConfig(args[2])) {
                                   Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.SUCCESS);
                              } else {
                                   Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.ERROR);
                              }
                              break;
                         case 1:
                              if (Main.instance.configManager.saveConfig(args[2])) {
                                   Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.SUCCESS);
                                   ConfigManager.getLoadedConfigs().clear();
                                   Main.instance.configManager.load();
                              } else {
                                   Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.ERROR);
                              }
                              break;
                         case 2:
                              if (Main.instance.configManager.deleteConfig(args[2])) {
                                   Main.msg(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.SUCCESS);
                              } else {
                                   Main.msg(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + args[2] + "\"", true);
                                   NotificationPublisher.queue("Config", ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + args[2] + "\"", NotificationType.ERROR);
                              }
                         }
                    } else if (args.length == 2 && upperCase.equalsIgnoreCase("LIST")) {
                         Main.msg(ChatFormatting.GREEN + "Configs:", true);
                         Iterator var3 = Main.instance.configManager.getContents().iterator();

                         while(var3.hasNext()) {
                              Config config = (Config)var3.next();
                              Main.msg(ChatFormatting.RED + config.getName(), true);
                         }
                    }
               } else {
                    Main.msg(this.getUsage(), true);
               }
          } catch (Exception var5) {
               var5.printStackTrace();
          }

     }
}
