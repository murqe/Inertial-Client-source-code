package wtf.rich.client.ui.settings.impls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import wtf.rich.Main;
import wtf.rich.api.utils.friend.Friend;
import wtf.rich.client.ui.settings.FileManager;

public class FriendConfig extends FileManager.CustomFile {
     public FriendConfig(String name, boolean loadOnStart) {
          super(name, loadOnStart);
     }

     public void loadFile() {
          try {
               BufferedReader br = new BufferedReader(new FileReader(this.getFile()));

               String line;
               while((line = br.readLine()) != null) {
                    String curLine = line.trim();
                    String name = curLine.split(":")[0];
                    Main.instance.friendManager.addFriend(name);
               }

               br.close();
          } catch (Exception var5) {
          }

     }

     public void saveFile() {
          try {
               BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
               Iterator var2 = Main.instance.friendManager.getFriends().iterator();

               while(var2.hasNext()) {
                    Friend friend = (Friend)var2.next();
                    out.write(friend.getName().replace(" ", ""));
                    out.write("\r\n");
               }

               out.close();
          } catch (Exception var4) {
          }

     }
}
