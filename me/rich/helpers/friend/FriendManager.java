package me.rich.helpers.friend;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.StringUtils;

public class FriendManager {
      public static FriendManager friendManager;
      public static ArrayList friendsList = new ArrayList();

      public void addFriend(String name, String alias) {
            friendsList.add(new Friend(name, alias));
      }

      public static void removeFriend(String name) {
            Iterator var1 = friendsList.iterator();

            while(var1.hasNext()) {
                  Friend friend = (Friend)var1.next();
                  if (friend.getName().equalsIgnoreCase(name)) {
                        friendsList.remove(friend);
                        break;
                  }
            }

      }

      public static boolean isFriend(String name) {
            boolean isFriend = false;
            Iterator var2 = friendsList.iterator();

            while(var2.hasNext()) {
                  Friend friend = (Friend)var2.next();
                  if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                        isFriend = true;
                        break;
                  }
            }

            return isFriend;
      }

      public static FriendManager getFriends() {
            if (friendManager == null) {
                  friendManager = new FriendManager();
            }

            return friendManager;
      }
}
