package me.rich.module.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.other.ChatUtils;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

public class TotemDetector extends Feature {
      public static HashMap popList = new HashMap();

      public TotemDetector() {
            super("TotemDetector", 0, Category.COMBAT);
      }

      @EventTarget
      public void onPacket(EventPacket event) {
            if (mc.world != null) {
                  Minecraft var10000 = mc;
                  if (Minecraft.player != null) {
                        if (event.getPacket() instanceof SPacketEntityStatus) {
                              SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
                              if (packet.getOpCode() == 35) {
                                    Entity entity = packet.getEntity(mc.world);
                                    if (popList == null) {
                                          popList = new HashMap();
                                    }

                                    if (popList.get(entity.getName()) == null) {
                                          popList.put(entity.getName(), 1);
                                          ChatUtils.addChatMessage(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + entity.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.GREEN + "1 totem!");
                                    } else if (popList.get(entity.getName()) != null) {
                                          int popCounter = (Integer)popList.get(entity.getName());
                                          ++popCounter;
                                          popList.put(entity.getName(), popCounter);
                                          ChatUtils.addChatMessage(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + entity.getName() + ChatFormatting.WHITE + " has popped " + ChatFormatting.GREEN + popCounter + " totems!");
                                    }
                              }
                        }

                        return;
                  }
            }

      }

      @EventTarget
      public void onPre(EventPreMotionUpdate event) {
            if (mc.world != null) {
                  Minecraft var10000 = mc;
                  if (Minecraft.player != null) {
                        Iterator var2 = mc.world.playerEntities.iterator();

                        while(var2.hasNext()) {
                              EntityPlayer player = (EntityPlayer)var2.next();
                              if (player.getHealth() <= 0.0F && popList.containsKey(player.getName())) {
                                    ChatUtils.addChatMessage(ChatFormatting.WHITE + "Player " + ChatFormatting.RED + player.getName() + ChatFormatting.WHITE + " died after popped " + ChatFormatting.GREEN + popList.get(player.getName()) + " totems!");
                                    popList.remove(player.getName(), popList.get(player.getName()));
                              }
                        }

                        return;
                  }
            }

      }
}
