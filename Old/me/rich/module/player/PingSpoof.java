package me.rich.module.player;

import de.Hero.settings.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventSendPacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketKeepAlive;

public class PingSpoof extends Feature {
      long id;
      short tsid;
      int twid;
      int ticks;

      public PingSpoof() {
            super("PingSpoof", 0, Category.PLAYER);
            Main.settingsManager.rSetting(new Setting("Delay", this, 15000.0D, 0.0D, 30000.0D, false));
      }

      @EventTarget
      public void onSendPacket(EventSendPacket event) {
            int ping = (int)Main.settingsManager.getSettingByName("Delay").getValDouble();
            if (!mc.isSingleplayer()) {
                  if (event.getPacket() instanceof CPacketConfirmTransaction) {
                        if (this.tsid == ((CPacketConfirmTransaction)event.getPacket()).getUid() && this.twid == ((CPacketConfirmTransaction)event.getPacket()).getWindowId()) {
                              return;
                        }

                        event.setCancelled(true);
                        (new Thread(() -> {
                              try {
                                    Thread.sleep((long)ping);
                              } catch (InterruptedException var4) {
                                    var4.printStackTrace();
                              }

                              this.tsid = ((CPacketConfirmTransaction)event.getPacket()).getUid();
                              this.twid = ((CPacketConfirmTransaction)event.getPacket()).getWindowId();
                              if (Minecraft.player != null) {
                                    if (Minecraft.player.connection != null) {
                                          Minecraft.player.connection.sendPacket(event.getPacket());
                                    }
                              }
                        })).start();
                  }

                  if (event.getPacket() instanceof CPacketKeepAlive) {
                        if (this.id == ((CPacketKeepAlive)event.getPacket()).getKey()) {
                              return;
                        }

                        event.setCancelled(true);
                        (new Thread(() -> {
                              try {
                                    Thread.sleep((long)ping);
                              } catch (InterruptedException var4) {
                                    var4.printStackTrace();
                              }

                              this.id = ((CPacketKeepAlive)event.getPacket()).getKey();
                              Minecraft.player.connection.sendPacket(event.getPacket());
                        })).start();
                  }
            }

      }
}
