package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry {
     private final Object value;

     public UserListEntry(Object valueIn) {
          this.value = valueIn;
     }

     protected UserListEntry(Object valueIn, JsonObject json) {
          this.value = valueIn;
     }

     Object getValue() {
          return this.value;
     }

     boolean hasBanExpired() {
          return false;
     }

     protected void onSerialization(JsonObject data) {
     }
}
