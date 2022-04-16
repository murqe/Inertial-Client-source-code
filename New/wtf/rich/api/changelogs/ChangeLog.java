package wtf.rich.api.changelogs;

public class ChangeLog {
     protected String changeName;
     protected ChangeType type;

     public ChangeLog(String name, ChangeType type) {
          this.changeName = name;
          this.type = type;
          switch(type) {
          case NEW:
               this.changeName = "    §7[§6!§7] New §f" + this.changeName;
               break;
          case ADD:
               this.changeName = "    §7[§a+§7] Added §f" + this.changeName;
               break;
          case RECODE:
               this.changeName = "    §7[§9*§7] Recoded §f" + this.changeName;
               break;
          case IMPROVED:
               this.changeName = "    §7[§d/§7] Improved §f" + this.changeName;
               break;
          case DELETE:
               this.changeName = "    §7[§c-§7] Deleted §f" + this.changeName;
               break;
          case FIXED:
               this.changeName = "    §7[§b/§7] Fixed §f" + this.changeName;
               break;
          case MOVED:
               this.changeName = "    §7[§9->§7] Moved §f" + this.changeName;
               break;
          case RENAMED:
               this.changeName = "    §7[§9!§7] Renamed §f" + this.changeName;
               break;
          case NONE:
               this.changeName = " " + this.changeName;
          }

     }

     public String getLogName() {
          return this.changeName;
     }

     public ChangeType getType() {
          return this.type;
     }
}
