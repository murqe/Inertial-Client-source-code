package wtf.rich.api.command;

import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventMessage;

public class CommandHandler {
     public CommandManager commandManager;

     public CommandHandler(CommandManager commandManager) {
          this.commandManager = commandManager;
     }

     @EventTarget
     public void onMessage(EventMessage event) {
          String msg = event.getMessage();
          if (msg.length() > 0 && msg.startsWith(".")) {
               event.setCancelled(this.commandManager.execute(msg));
          }

     }
}
