package me.rich.module.misc;

import de.Hero.settings.Setting;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventChatMessage;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AutoAccept extends Feature {
      public AutoAccept() {
            super("AutoAccept", 0, Category.MISC);
            Main.settingsManager.rSetting(new Setting("Only Friends", this, true));
      }

      @EventTarget
      public void onReceiveChat(EventChatMessage event) {
            Iterator var2 = mc.world.playerEntities.iterator();

            while(true) {
                  EntityPlayer entity;
                  label35:
                  do {
                        while(var2.hasNext()) {
                              entity = (EntityPlayer)var2.next();
                              if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(AutoAccept.class), "Only Friends").getValBoolean()) {
                                    continue label35;
                              }

                              if ((event.getMessage().contains("/tpyes") || event.getMessage().contains("/tpaccept")) && timerHelper.check(500.0F)) {
                                    Minecraft.player.sendChatMessage("/tpaccept");
                                    timerHelper.resetwatermark();
                              }
                        }

                        return;
                  } while(!event.getMessage().contains("/tpyes") && !event.getMessage().contains("/tpaccept"));

                  if (FriendManager.isFriend(entity.getName()) && timerHelper.check(500.0F)) {
                        Minecraft.player.sendChatMessage("/tpaccept");
                        timerHelper.resetwatermark();
                  }
            }
      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
