package wtf.rich.client.features.impl.movement;

import net.minecraft.client.renderer.Vector3d;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.MoveEvent;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class SafeWalk extends Feature {
     private Vector3d vec = new Vector3d();

     public SafeWalk() {
          super("SafeWalk", "Не даёт вам упасть с края блока", 0, Category.MOVEMENT);
     }

     @EventTarget
     public void event(MoveEvent event) {
          double x = event.getX();
          double z = event.getZ();
          if (mc.player.onGround) {
               double increment = 0.05D;

               while(true) {
                    while(x != 0.0D && this.isOffsetBBEmpty(x, -1.0D, 0.0D)) {
                         if (x < increment && x >= -increment) {
                              x = 0.0D;
                         } else if (x > 0.0D) {
                              x -= increment;
                         } else {
                              x += increment;
                         }
                    }

                    while(true) {
                         while(z != 0.0D && this.isOffsetBBEmpty(0.0D, -1.0D, z)) {
                              if (z < increment && z >= -increment) {
                                   z = 0.0D;
                              } else if (z > 0.0D) {
                                   z -= increment;
                              } else {
                                   z += increment;
                              }
                         }

                         while(true) {
                              while(x != 0.0D && z != 0.0D && this.isOffsetBBEmpty(x, -1.0D, z)) {
                                   if (x < increment && x >= -increment) {
                                        x = 0.0D;
                                   } else if (x > 0.0D) {
                                        x -= increment;
                                   } else {
                                        x += increment;
                                   }

                                   if (z < increment && z >= -increment) {
                                        z = 0.0D;
                                   } else if (z > 0.0D) {
                                        z -= increment;
                                   } else {
                                        z += increment;
                                   }
                              }

                              return;
                         }
                    }
               }
          }
     }

     public boolean isOffsetBBEmpty(double offsetX, double offsetY, double offsetZ) {
          this.vec.x = offsetX;
          this.vec.y = offsetY;
          this.vec.z = offsetZ;
          return mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(this.vec.x, this.vec.y, this.vec.z)).isEmpty();
     }
}
