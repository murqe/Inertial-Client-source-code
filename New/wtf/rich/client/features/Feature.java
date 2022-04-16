package wtf.rich.client.features;

import com.google.gson.JsonObject;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import wtf.rich.Main;
import wtf.rich.api.event.EventManager;
import wtf.rich.api.utils.notifications.NotificationPublisher;
import wtf.rich.api.utils.notifications.NotificationType;
import wtf.rich.api.utils.render.AnimationHelper;
import wtf.rich.api.utils.render.Translate;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.impl.display.Notifications;
import wtf.rich.client.features.impl.misc.ModuleSoundAlert;
import wtf.rich.client.ui.clickgui.ScreenHelper;
import wtf.rich.client.ui.settings.Configurable;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Feature extends Configurable {
     protected static Minecraft mc = Minecraft.getMinecraft();
     public static TimerHelper timerHelper = new TimerHelper();
     private final Translate translate = new Translate(0.0F, 0.0F);
     protected String label;
     protected String desc;
     private String moduleName;
     private String suffix;
     private int key;
     private Category category;
     private boolean toggled;
     private final AnimationHelper animation;
     public double slidex = 0.0D;
     public double slidey = 0.0D;
     public boolean visible = true;
     public ScreenHelper screenHelper = new ScreenHelper(0.0F, 0.0F);

     public Feature(String name, String desc, int key, Category category) {
          this.label = name;
          this.desc = desc;
          this.key = key;
          this.category = category;
          this.toggled = false;
          this.animation = new AnimationHelper(150, this.isToggled());
          this.setup();
     }

     public JsonObject save() {
          JsonObject object = new JsonObject();
          object.addProperty("state", this.isToggled());
          object.addProperty("keyIndex", this.getKey());
          object.addProperty("visible", this.isVisible());
          JsonObject propertiesObject = new JsonObject();

          for(Iterator var3 = this.getSettings().iterator(); var3.hasNext(); object.add("Settings", propertiesObject)) {
               Setting set = (Setting)var3.next();
               if (this.getSettings() != null) {
                    if (set instanceof BooleanSetting) {
                         propertiesObject.addProperty(set.getName(), ((BooleanSetting)set).getBoolValue());
                    } else if (set instanceof ListSetting) {
                         propertiesObject.addProperty(set.getName(), ((ListSetting)set).getCurrentMode());
                    } else if (set instanceof NumberSetting) {
                         propertiesObject.addProperty(set.getName(), ((NumberSetting)set).getNumberValue());
                    } else if (set instanceof ColorSetting) {
                         propertiesObject.addProperty(set.getName(), ((ColorSetting)set).getColorValue());
                    }
               }
          }

          return object;
     }

     public void load(JsonObject object) {
          if (object != null) {
               if (object.has("state")) {
                    this.setEnabled(object.get("state").getAsBoolean());
               }

               if (object.has("visible")) {
                    this.setVisible(object.get("visible").getAsBoolean());
               }

               if (object.has("keyIndex")) {
                    this.setKey(object.get("keyIndex").getAsInt());
               }

               Iterator var2 = this.getSettings().iterator();

               while(var2.hasNext()) {
                    Setting set = (Setting)var2.next();
                    JsonObject propertiesObject = object.getAsJsonObject("Settings");
                    if (set != null && propertiesObject != null && propertiesObject.has(set.getName())) {
                         if (set instanceof BooleanSetting) {
                              ((BooleanSetting)set).setBoolValue(propertiesObject.get(set.getName()).getAsBoolean());
                         } else if (set instanceof ListSetting) {
                              ((ListSetting)set).setListMode(propertiesObject.get(set.getName()).getAsString());
                         } else if (set instanceof NumberSetting) {
                              ((NumberSetting)set).setValueNumber(propertiesObject.get(set.getName()).getAsFloat());
                         } else if (set instanceof ColorSetting) {
                              ((ColorSetting)set).setColorValue(propertiesObject.get(set.getName()).getAsInt());
                         }
                    }
               }
          }

     }

     public void onEnable() {
          if (Main.instance.featureDirector.getFeatureByClass(ModuleSoundAlert.class).isToggled()) {
               mc.player.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 1.8F, 0.71999997F);
          }

          EventManager.register(this);
          if (!this.getLabel().contains("ClickGui") && !this.getLabel().contains("Client Font") && !this.getLabel().contains("Notifications") && Notifications.notifMode.currentMode.equalsIgnoreCase("Rect") && Notifications.state.getBoolValue()) {
               NotificationPublisher.queue(this.getLabel(), "was enabled!", NotificationType.INFO);
          } else if (!this.getLabel().contains("ClickGui") && !this.getLabel().contains("Client Font") && !this.getLabel().contains("Notifications") && Notifications.notifMode.currentMode.equalsIgnoreCase("Chat") && Notifications.state.getBoolValue()) {
               Main.msg(TextFormatting.GRAY + "[Notifications] " + TextFormatting.WHITE + this.getLabel() + " was" + TextFormatting.GREEN + " enabled!", false);
          }

     }

     public void onDisable() {
          if (Main.instance.featureDirector.getFeatureByClass(ModuleSoundAlert.class).isToggled()) {
               mc.player.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 1.8F, 0.71999997F);
          }

          EventManager.unregister(this);
          if (!this.getLabel().contains("ClickGui") && !this.getLabel().contains("Client Font") && !this.getLabel().contains("Notifications") && Notifications.notifMode.currentMode.equalsIgnoreCase("Rect") && Notifications.state.getBoolValue()) {
               NotificationPublisher.queue(this.getLabel(), "was disabled!", NotificationType.INFO);
          } else if (!this.getLabel().contains("ClickGui") && !this.getLabel().contains("Client Font") && !this.getLabel().contains("Notifications") && Notifications.notifMode.currentMode.equalsIgnoreCase("Chat") && Notifications.state.getBoolValue()) {
               Main.msg(TextFormatting.GRAY + "[Notifications] " + TextFormatting.WHITE + this.getLabel() + " was" + TextFormatting.RED + " disabled!", false);
          }

     }

     public ScreenHelper getScreenHelper() {
          return this.screenHelper;
     }

     public boolean isVisible() {
          return this.visible;
     }

     public void setVisible(boolean visible) {
          this.visible = visible;
     }

     public boolean isHidden() {
          return !this.visible;
     }

     public void setHidden(boolean visible) {
          this.visible = !visible;
     }

     public void setEnabled(boolean enabled) {
          if (enabled) {
               EventManager.register(this);
          } else {
               EventManager.unregister(this);
          }

          this.toggled = enabled;
     }

     public void onToggle() {
     }

     public void toggle() {
          this.toggled = !this.toggled;
          this.onToggle();
          if (this.toggled) {
               this.onEnable();
          } else {
               this.onDisable();
          }

     }

     public String getLabel() {
          return this.label;
     }

     public void setLabel(String name) {
          this.label = name;
     }

     public String getDesc() {
          return this.desc;
     }

     public void setDesc(String desc) {
          this.desc = desc;
     }

     public int getKey() {
          return this.key;
     }

     public void setKey(int key) {
          this.key = key;
     }

     public Category getCategory() {
          return this.category;
     }

     public void setCategory(Category category) {
          this.category = category;
     }

     public boolean isToggled() {
          return this.toggled;
     }

     public String getSuffix() {
          return this.suffix == null ? this.label : this.suffix;
     }

     public void setSuffix(String suffix) {
          this.suffix = suffix;
          this.suffix = this.getLabel() + " " + suffix;
     }

     public String getModuleName() {
          return this.moduleName == null ? this.label : this.moduleName;
     }

     public void setModuleName(String moduleName) {
          this.moduleName = moduleName;
     }

     public void setup() {
     }

     public static double deltaTime() {
          return Minecraft.getDebugFPS() > 0 ? 1.0D / (double)Minecraft.getDebugFPS() : 1.0D;
     }

     public AnimationHelper getAnimation() {
          return this.animation;
     }

     public Translate getTranslate() {
          return this.translate;
     }
}
