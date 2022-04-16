package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
     public static void checkThreadAndEnqueue(final Packet packetIn, final INetHandler processor, IThreadListener scheduler) throws ThreadQuickExitException {
          if (!scheduler.isCallingFromMinecraftThread()) {
               scheduler.addScheduledTask(new Runnable() {
                    public void run() {
                         packetIn.processPacket(processor);
                    }
               });
               throw ThreadQuickExitException.INSTANCE;
          }
     }
}
