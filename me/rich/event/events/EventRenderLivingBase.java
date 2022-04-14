package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderLivingBase extends Event {
      private EntityLivingBase ent;
      private final boolean isPre;
      private final boolean isPost;
      public ModelBase mainModel;

      public EventRenderLivingBase(EntityLivingBase ent, boolean pre, ModelBase mainModel) {
            this.mainModel = mainModel;
            this.ent = ent;
            this.isPre = pre;
            this.isPost = !pre;
      }

      public boolean isPre() {
            return this.isPre;
      }

      public boolean isPost() {
            return this.isPost;
      }

      public EntityLivingBase getEntity() {
            return this.ent;
      }
}
