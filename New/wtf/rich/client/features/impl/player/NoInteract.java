package wtf.rich.client.features.impl.player;

import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;

public class NoInteract extends Feature {
     public static BooleanSetting armorStands;
     public static BooleanSetting craftTable = new BooleanSetting("Craft Table", true, () -> {
          return true;
     });
     public static BooleanSetting standing = new BooleanSetting("Standing Sign", true, () -> {
          return true;
     });
     public static BooleanSetting door = new BooleanSetting("Door", true, () -> {
          return true;
     });
     public static BooleanSetting hopper = new BooleanSetting("Hopper", true, () -> {
          return true;
     });
     public static BooleanSetting furnace = new BooleanSetting("Furnace", true, () -> {
          return true;
     });
     public static BooleanSetting dispenser = new BooleanSetting("Dispenser", true, () -> {
          return true;
     });
     public static BooleanSetting anvil = new BooleanSetting("Furnace", true, () -> {
          return true;
     });
     public static BooleanSetting woodenslab = new BooleanSetting("Wooden Slab", true, () -> {
          return true;
     });
     public static BooleanSetting lever = new BooleanSetting("Lever", true, () -> {
          return true;
     });

     public NoInteract() {
          super("NoInteract", "Позволяет не нажимать ПКМ по верстакам, печкам и т.д", 0, Category.PLAYER);
          armorStands = new BooleanSetting("Armor Stand", true, () -> {
               return true;
          });
          this.addSettings(new Setting[]{armorStands, craftTable, standing, door, hopper, furnace, dispenser, anvil, woodenslab, lever});
     }
}
