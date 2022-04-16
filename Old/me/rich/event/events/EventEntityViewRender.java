package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

public abstract class EventEntityViewRender extends Event {
      private final EntityRenderer renderer;
      private final Entity entity;
      private final IBlockState state;
      private final double renderPartialTicks;

      public EventEntityViewRender(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks) {
            this.renderer = renderer;
            this.entity = entity;
            this.state = state;
            this.renderPartialTicks = renderPartialTicks;
      }

      public EntityRenderer getRenderer() {
            return this.renderer;
      }

      public Entity getEntity() {
            return this.entity;
      }

      public IBlockState getState() {
            return this.state;
      }

      public double getRenderPartialTicks() {
            return this.renderPartialTicks;
      }

      public static class FOVModifier extends EventEntityViewRender {
            private float fov;

            public FOVModifier(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float fov) {
                  super(renderer, entity, state, renderPartialTicks);
                  this.setFOV(fov);
            }

            public float getFOV() {
                  return this.fov;
            }

            public void setFOV(float fov) {
                  this.fov = fov;
            }
      }

      public static class CameraSetup extends EventEntityViewRender {
            private float yaw;
            private float pitch;
            private float roll;

            public CameraSetup(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float yaw, float pitch, float roll) {
                  super(renderer, entity, state, renderPartialTicks);
                  this.setYaw(yaw);
                  this.setPitch(pitch);
                  this.setRoll(roll);
            }

            public float getYaw() {
                  return this.yaw;
            }

            public void setYaw(float yaw) {
                  this.yaw = yaw;
            }

            public float getPitch() {
                  return this.pitch;
            }

            public void setPitch(float pitch) {
                  this.pitch = pitch;
            }

            public float getRoll() {
                  return this.roll;
            }

            public void setRoll(float roll) {
                  this.roll = roll;
            }
      }

      public static class FogColors extends EventEntityViewRender {
            private float red;
            private float green;
            private float blue;

            public FogColors(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float red, float green, float blue) {
                  super(renderer, entity, state, renderPartialTicks);
                  this.setRed(red);
                  this.setGreen(green);
                  this.setBlue(blue);
            }

            public float getRed() {
                  return this.red;
            }

            public void setRed(float red) {
                  this.red = red;
            }

            public float getGreen() {
                  return this.green;
            }

            public void setGreen(float green) {
                  this.green = green;
            }

            public float getBlue() {
                  return this.blue;
            }

            public void setBlue(float blue) {
                  this.blue = blue;
            }
      }

      public static class RenderFogEvent extends EventEntityViewRender {
            private final int fogMode;
            private final float farPlaneDistance;

            public RenderFogEvent(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, int fogMode, float farPlaneDistance) {
                  super(renderer, entity, state, renderPartialTicks);
                  this.fogMode = fogMode;
                  this.farPlaneDistance = farPlaneDistance;
            }

            public int getFogMode() {
                  return this.fogMode;
            }

            public float getFarPlaneDistance() {
                  return this.farPlaneDistance;
            }
      }

      public static class FogDensity extends EventEntityViewRender {
            private float density;

            public FogDensity(EntityRenderer renderer, Entity entity, IBlockState state, double renderPartialTicks, float density) {
                  super(renderer, entity, state, renderPartialTicks);
                  this.setDensity(density);
            }

            public float getDensity() {
                  return this.density;
            }

            public void setDensity(float density) {
                  this.density = density;
            }
      }
}
