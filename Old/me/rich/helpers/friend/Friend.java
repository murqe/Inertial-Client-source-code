package me.rich.helpers.friend;

public class Friend {
      private String name;
      private String alias;

      public Friend(String name, String alias) {
            this.name = name;
            this.alias = alias;
      }

      public String getName() {
            return this.name;
      }

      public String getAlias() {
            return this.alias;
      }

      public void setName(String s) {
            this.name = s;
      }

      public void setAlias(String s) {
            this.alias = s;
      }
}
