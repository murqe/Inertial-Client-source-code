package wtf.rich.api.utils.friend;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {
     private final List friends = new ArrayList();

     public void addFriend(Friend friend) {
          this.friends.add(friend);
     }

     public void addFriend(String name) {
          this.friends.add(new Friend(name));
     }

     public boolean isFriend(String friend) {
          return this.friends.stream().anyMatch((isFriend) -> {
               return isFriend.getName().equals(friend);
          });
     }

     public void removeFriend(String name) {
          this.friends.removeIf((friend) -> {
               return friend.getName().equalsIgnoreCase(name);
          });
     }

     public List getFriends() {
          return this.friends;
     }

     public Friend getFriend(String friend) {
          return (Friend)this.friends.stream().filter((isFriend) -> {
               return isFriend.getName().equals(friend);
          }).findFirst().get();
     }
}
