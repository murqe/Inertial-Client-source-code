package me.rich.module.player;

import java.util.Iterator;
import me.rich.event.EventTarget;
import me.rich.event.events.EventReceivePacket;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

public class NoLag extends Feature {
      public NoLag() {
            super("AntiLag", 0, Category.PLAYER);
      }

      @EventTarget
      public void receivePacket(EventReceivePacket event) {
            if (event.getPacket() instanceof SPacketParticles) {
                  SPacketParticles packet = (SPacketParticles)event.getPacket();
                  event.setCancelled(true);
            }

            SPacketEffect packet;
            if (event.getPacket() instanceof SPacketEffect) {
                  packet = (SPacketEffect)event.getPacket();
                  event.setCancelled(true);
            }

            SPacketSoundEffect packet;
            if (event.getPacket() instanceof SPacketSoundEffect) {
                  packet = (SPacketSoundEffect)event.getPacket();
                  if (packet.getCategory() == SoundCategory.PLAYERS && packet.getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC) {
                        event.setCancelled(true);
                  }
            }

            if (event.getPacket() instanceof SPacketSpawnMob) {
                  SPacketSpawnMob packet = (SPacketSpawnMob)event.getPacket();
                  if (packet.getEntityType() == 55) {
                        event.setCancelled(true);
                  }
            }

            if (event.getPacket() instanceof SPacketSoundEffect) {
                  packet = (SPacketSoundEffect)event.getPacket();
                  if (packet.getCategory() == SoundCategory.WEATHER && packet.getSound() == SoundEvents.ENTITY_LIGHTNING_THUNDER) {
                        event.setCancelled(true);
                  }
            }

            if (event.getPacket() instanceof SPacketEffect) {
                  packet = (SPacketEffect)event.getPacket();
                  if (packet.getSoundType() == 1038 || packet.getSoundType() == 1023 || packet.getSoundType() == 1028) {
                        event.setCancelled(true);
                  }
            }

            Iterator var10 = mc.world.loadedTileEntityList.iterator();

            while(true) {
                  TileEntity e;
                  do {
                        if (!var10.hasNext()) {
                              for(int i = 0; i <= 45; ++i) {
                                    Minecraft var10000 = mc;
                                    ItemStack item = Minecraft.player.inventory.getStackInSlot(i);
                                    if (item.getItem() instanceof ItemBook) {
                                          var10000 = mc;
                                          Minecraft.player.dropItem(item, false);
                                    }
                              }

                              return;
                        }

                        e = (TileEntity)var10.next();
                  } while(!(e instanceof TileEntitySign));

                  TileEntitySign sign = (TileEntitySign)e;

                  for(int i = 0; i <= 3; ++i) {
                        sign.signText[i] = new TextComponentString("");
                  }
            }
      }

      public void onDisable() {
            mc.renderGlobal.loadRenderers();
            super.onDisable();
      }
}
