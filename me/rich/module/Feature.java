package me.rich.module;

import me.rich.Main;
import me.rich.event.EventManager;
import me.rich.helpers.render.Translate;
import me.rich.helpers.world.TimerHelper;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;

public class Feature {
      protected static Minecraft mc = Minecraft.getMinecraft();
      public static TimerHelper timerHelper = new TimerHelper();
      private final Translate translate = new Translate(0.0F, 0.0F);
      protected String name;
      private String moduleName;
      private String suffix;
      private int key;
      private Category category;
      private boolean toggled;

      public Feature(String name, int key, Category category) {
            this.name = name;
            this.key = key;
            this.category = category;
            this.toggled = false;
            this.setup();
      }

      public static double deltaTime() {
            Minecraft.getMinecraft();
            double var10000;
            if (Minecraft.getDebugFPS() > 0) {
                  Minecraft.getMinecraft();
                  var10000 = 1.0D / (double)Minecraft.getDebugFPS();
            } else {
                  var10000 = 1.0D;
            }

            return var10000;
      }

      public void onEnable() {
            NotificationPublisher.queue("Feature was enable", this.name, NotificationType.INFO);
            Main var10000 = Main.instance;
            EventManager var1 = Main.eventManager;
            EventManager.register(this);
      }

      public void onDisable() {
            NotificationPublisher.queue("Feature was disable", this.name, NotificationType.INFO);
            Main var10000 = Main.instance;
            EventManager var1 = Main.eventManager;
            EventManager.unregister(this);
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

      public String getName() {
            return this.name;
      }

      public void setName(String name) {
            this.name = name;
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
            return this.suffix;
      }

      public void setSuffix(String suffix) {
            this.suffix = suffix;
      }

      public String getModuleName() {
            return this.moduleName == null ? this.name : this.moduleName;
      }

      public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
      }

      public void setup() {
      }

      public Translate getTranslate() {
            return this.translate;
      }
}
