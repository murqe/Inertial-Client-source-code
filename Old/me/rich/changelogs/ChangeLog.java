package me.rich.changelogs;

public class ChangeLog {
      private String changeName;
      private final ChangelogType type;

      public ChangeLog(String name, ChangelogType type) {
            this.changeName = name;
            this.type = type;
            switch(type) {
            case NONE:
                  this.changeName = ": " + this.changeName;
                  break;
            case ADD:
                  this.changeName = "§7[§a+§7] Added §f" + this.changeName;
                  break;
            case DELETE:
                  this.changeName = "§7[§c-§7] Delete §f" + this.changeName;
                  break;
            case IMPROVED:
                  this.changeName = "§7[§9/§7] Improved §f" + this.changeName;
                  break;
            case FIXED:
                  this.changeName = "§7[§b/§7] Fixed §f" + this.changeName;
                  break;
            case PROTOTYPE:
                  this.changeName = "§7[§e?§7] Prototype §f" + this.changeName;
                  break;
            case NEW:
                  this.changeName = "§7[§6!§7] New §f" + this.changeName;
            }

      }

      public String getChangeName() {
            return this.changeName;
      }

      public ChangelogType getType() {
            return this.type;
      }
}
