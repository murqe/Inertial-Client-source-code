package wtf.rich.api.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import wtf.rich.Main;
import wtf.rich.api.command.CommandAbstract;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.client.features.Feature;

public class BindCommand extends CommandAbstract {
     public BindCommand() {
          super("bind", "bind", "§6.bind" + ChatFormatting.RED + " add §7<name> §7<key> " + TextFormatting.RED + "/ §6.bind " + ChatFormatting.RED + "remove §7<name> §7<key>", "§6.bind" + ChatFormatting.RED + " add §7<name> §7<key> | §6.bind" + ChatFormatting.RED + "remove §7<name> <key> | §6.bind" + ChatFormatting.RED + "clear", "bind");
     }

     public void execute(String... arguments) {
          try {
               if (arguments.length == 4) {
                    String moduleName = arguments[2];
                    String bind = arguments[3].toUpperCase();
                    Feature feature = Main.instance.featureDirector.getFeatureByLabel(moduleName);
                    if (arguments[0].equals("bind")) {
                         String var5 = arguments[1];
                         byte var6 = -1;
                         switch(var5.hashCode()) {
                         case -934610812:
                              if (var5.equals("remove")) {
                                   var6 = 1;
                              }
                              break;
                         case 96417:
                              if (var5.equals("add")) {
                                   var6 = 0;
                              }
                              break;
                         case 3322014:
                              if (var5.equals("list")) {
                                   var6 = 3;
                              }
                              break;
                         case 94746189:
                              if (var5.equals("clear")) {
                                   var6 = 2;
                              }
                         }

                         switch(var6) {
                         case 0:
                              feature.setKey(Keyboard.getKeyIndex(bind));
                              Main.msg(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " was set on key " + ChatFormatting.RED + "\"" + bind + "\"", true);
                              NotificationPublisher.queue("Bind Manager", ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " was set on key " + ChatFormatting.RED + "\"" + bind + "\"", NotificationType.SUCCESS);
                              break;
                         case 1:
                              feature.setKey(0);
                              Main.msg(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " bind was deleted from key " + ChatFormatting.RED + "\"" + bind + "\"", true);
                              NotificationPublisher.queue("Bind Manager", ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " bind was deleted from key " + ChatFormatting.RED + "\"" + bind + "\"", NotificationType.SUCCESS);
                              break;
                         case 2:
                              if (!feature.getLabel().equals("ClickGui")) {
                                   feature.setKey(0);
                              }
                              break;
                         case 3:
                              if (feature.getKey() == 0) {
                                   Main.msg(ChatFormatting.RED + "Your macros list is empty!", true);
                                   NotificationPublisher.queue("Macro Manager", "Your macros list is empty!", NotificationType.ERROR);
                                   return;
                              }

                              Main.instance.featureDirector.getFeatureList().forEach((feature1) -> {
                                   Main.msg(ChatFormatting.GREEN + "Binds list: " + ChatFormatting.WHITE + "Binds Name: " + ChatFormatting.RED + feature1.getKey() + ChatFormatting.WHITE + ", Macro Bind: " + ChatFormatting.RED + Keyboard.getKeyName(feature1.getKey()), true);
                              });
                         }
                    }
               } else {
                    Main.msg(this.getUsage(), true);
               }
          } catch (Exception var7) {
          }

     }
}
