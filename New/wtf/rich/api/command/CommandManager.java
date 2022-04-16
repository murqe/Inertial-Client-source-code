package wtf.rich.api.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import wtf.rich.api.command.impl.BindCommand;
import wtf.rich.api.command.impl.ClipCommand;
import wtf.rich.api.command.impl.ConfigCommand;
import wtf.rich.api.command.impl.FriendCommand;
import wtf.rich.api.command.impl.HelpCommand;
import wtf.rich.api.command.impl.MacroCommand;
import wtf.rich.api.event.EventManager;

public class CommandManager {
     private final ArrayList commands = new ArrayList();

     public CommandManager() {
          EventManager.register(new CommandHandler(this));
          this.commands.add(new BindCommand());
          this.commands.add(new ClipCommand());
          this.commands.add(new FriendCommand());
          this.commands.add(new MacroCommand());
          this.commands.add(new ConfigCommand());
          this.commands.add(new HelpCommand());
     }

     public List getCommands() {
          return this.commands;
     }

     public boolean execute(String args) {
          String noPrefix = args.substring(1);
          String[] split = noPrefix.split(" ");
          if (split.length > 0) {
               Iterator var4 = this.commands.iterator();

               while(var4.hasNext()) {
                    Command command = (Command)var4.next();
                    CommandAbstract abstractCommand = (CommandAbstract)command;
                    String[] commandAliases = abstractCommand.getAliases();
                    String[] var8 = commandAliases;
                    int var9 = commandAliases.length;

                    for(int var10 = 0; var10 < var9; ++var10) {
                         String alias = var8[var10];
                         if (split[0].equalsIgnoreCase(alias)) {
                              abstractCommand.execute(split);
                              return true;
                         }
                    }
               }
          }

          return false;
     }
}
