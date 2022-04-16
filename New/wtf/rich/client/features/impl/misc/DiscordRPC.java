package wtf.rich.client.features.impl.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import wtf.rich.Main;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class DiscordRPC extends Feature {
     private static Minecraft mc = Minecraft.getMinecraft();

     public DiscordRPC() {
          super("DiscordRPC", "Status on discord.", 0, Category.MISC);
     }

     public static void init() {
          DiscordEventHandlers handlers = new DiscordEventHandlers();
          handlers.ready = (user) -> {
          };
          club.minnced.discord.rpc.DiscordRPC discordRPC = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
          discordRPC.Discord_Initialize("963847999937724485", handlers, false, (String)null);
          DiscordRichPresence discordRichPresence = new DiscordRichPresence();
          discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
          discordRichPresence.details = "vk.com/inertialclient";
          discordRichPresence.largeImageKey = "large";
          discordRichPresence.largeImageText = "Inertial Free " + Main.instance.version;
          (new Thread(() -> {
               while(true) {
                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                    if (mc.world != null) {
                         if (mc.isSingleplayer()) {
                              discordRichPresence.state = "Playing Singleplayer";
                         } else {
                              discordRichPresence.state = "Playing on " + mc.getCurrentServerData().serverIP;
                         }
                    }

                    discordRPC.Discord_RunCallbacks();

                    try {
                         Thread.sleep(5000L);
                    } catch (InterruptedException var3) {
                         var3.printStackTrace();
                    }
               }
          })).start();
          System.out.println("[Inertial] DiscordRPC successfully initialized!");
     }
}
