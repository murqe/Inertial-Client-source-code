package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {
      public String getCommandName() {
            return "save-off";
      }

      public String getCommandUsage(ICommandSender sender) {
            return "commands.save-off.usage";
      }

      public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            boolean flag = false;

            for(int i = 0; i < server.worldServers.length; ++i) {
                  if (server.worldServers[i] != null) {
                        WorldServer worldserver = server.worldServers[i];
                        if (!worldserver.disableLevelSaving) {
                              worldserver.disableLevelSaving = true;
                              flag = true;
                        }
                  }
            }

            if (flag) {
                  notifyCommandListener(sender, this, "commands.save.disabled", new Object[0]);
            } else {
                  throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
            }
      }
}