package wtf.rich.api.command.impl;

import net.minecraft.client.Minecraft;
import wtf.rich.Main;
import wtf.rich.api.command.CommandAbstract;

public class ClipCommand extends CommandAbstract {
     Minecraft mc = Minecraft.getMinecraft();

     public ClipCommand() {
          super("clip", "clip | hclip", "§6.clip | (hclip) + | - §3<value> | down", "clip", "hclip");
     }

     public void execute(String... args) {
          if (args.length > 1) {
               if (args[0].equalsIgnoreCase("clip")) {
                    try {
                         if (args[1].equals("down")) {
                              this.mc.player.setPositionAndUpdate(this.mc.player.posX, -2.0D, this.mc.player.posZ);
                         }

                         if (args[1].equals("+")) {
                              this.mc.player.setPositionAndUpdate(this.mc.player.posX, this.mc.player.posY + Double.parseDouble(args[2]), this.mc.player.posZ);
                         }

                         if (args[1].equals("-")) {
                              this.mc.player.setPositionAndUpdate(this.mc.player.posX, this.mc.player.posY - Double.parseDouble(args[2]), this.mc.player.posZ);
                         }
                    } catch (Exception var12) {
                    }
               }

               if (args[0].equalsIgnoreCase("hclip")) {
                    double x = this.mc.player.posX;
                    double y = this.mc.player.posY;
                    double z = this.mc.player.posZ;
                    double yaw = (double)this.mc.player.rotationYaw * 0.017453292D;

                    try {
                         if (args[1].equals("+")) {
                              this.mc.player.setPositionAndUpdate(x - Math.sin(yaw) * Double.parseDouble(args[2]), y, z + Math.cos(yaw) * Double.parseDouble(args[2]));
                         }

                         if (args[1].equals("-")) {
                              this.mc.player.setPositionAndUpdate(x + Math.sin(yaw) * Double.parseDouble(args[2]), y, z - Math.cos(yaw) * Double.parseDouble(args[2]));
                         }
                    } catch (Exception var11) {
                    }
               }
          } else {
               Main.msg(this.getUsage(), true);
          }

     }
}
