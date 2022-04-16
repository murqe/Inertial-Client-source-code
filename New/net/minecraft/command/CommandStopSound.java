package net.minecraft.command;

import io.netty.buffer.Unpooled;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class CommandStopSound extends CommandBase {
     public String getCommandName() {
          return "stopsound";
     }

     public int getRequiredPermissionLevel() {
          return 2;
     }

     public String getCommandUsage(ICommandSender sender) {
          return "commands.stopsound.usage";
     }

     public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
          if (args.length >= 1 && args.length <= 3) {
               int i = 0;
               int var10 = i + 1;
               EntityPlayerMP entityplayermp = getPlayer(server, sender, args[i]);
               String s = "";
               String s1 = "";
               if (args.length >= 2) {
                    String s2 = args[var10++];
                    SoundCategory soundcategory = SoundCategory.getByName(s2);
                    if (soundcategory == null) {
                         throw new CommandException("commands.stopsound.unknownSoundSource", new Object[]{s2});
                    }

                    s = soundcategory.getName();
               }

               if (args.length == 3) {
                    s1 = args[var10++];
               }

               PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
               packetbuffer.writeString(s);
               packetbuffer.writeString(s1);
               entityplayermp.connection.sendPacket(new SPacketCustomPayload("MC|StopSound", packetbuffer));
               if (s.isEmpty() && s1.isEmpty()) {
                    notifyCommandListener(sender, this, "commands.stopsound.success.all", new Object[]{entityplayermp.getName()});
               } else if (s1.isEmpty()) {
                    notifyCommandListener(sender, this, "commands.stopsound.success.soundSource", new Object[]{s, entityplayermp.getName()});
               } else {
                    notifyCommandListener(sender, this, "commands.stopsound.success.individualSound", new Object[]{s1, s, entityplayermp.getName()});
               }

          } else {
               throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
          }
     }

     public List getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
          if (args.length == 1) {
               return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
          } else if (args.length == 2) {
               return getListOfStringsMatchingLastWord(args, SoundCategory.getSoundCategoryNames());
          } else {
               return args.length == 3 ? getListOfStringsMatchingLastWord(args, SoundEvent.REGISTRY.getKeys()) : Collections.emptyList();
          }
     }

     public boolean isUsernameIndex(String[] args, int index) {
          return index == 0;
     }
}