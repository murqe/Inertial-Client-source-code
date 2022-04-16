package wtf.rich.api.utils.world;

public class TimerHelper {
     private long prevMS = 0L;
     private long lastMS;
     private long previousTime = -1L;
     public static float timerSpeed = 1.0F;
     private long tick = 0L;

     public long getCurrentTime() {
          return System.currentTimeMillis();
     }

     public boolean check(float milliseconds) {
          return (float)(this.getCurrentTime() - this.previousTime) >= milliseconds;
     }

     public long getCurrentMS() {
          return System.nanoTime() / 1000000L;
     }

     public boolean hasTimeElapsed(long time, boolean reset) {
          if (System.currentTimeMillis() - this.lastMS > time) {
               if (reset) {
                    this.reset();
               }

               return true;
          } else {
               return false;
          }
     }

     public long getDifference() {
          return this.getTime() - this.prevMS;
     }

     public void setDifference(long difference) {
          this.prevMS = this.getTime() - difference;
     }

     public boolean hasReached(double milliseconds) {
          return (double)(this.getCurrentMS() - this.lastMS) >= milliseconds;
     }

     public boolean hasTimePassed(float ticks) {
          return (float)this.tick >= ticks;
     }

     public void resetwatermark() {
          this.previousTime = this.getCurrentTime();
     }

     public void reset() {
          this.lastMS = this.getCurrentMS();
     }

     public boolean delay(float milliSec) {
          return (float)(this.getTime() - this.prevMS) >= milliSec;
     }

     public void setTime(long time) {
          this.lastMS = time;
     }

     public long getTime() {
          return this.getCurrentMS() - this.lastMS;
     }
}
