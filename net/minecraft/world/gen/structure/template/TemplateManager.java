package net.minecraft.world.gen.structure.template;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import org.apache.commons.io.IOUtils;

public class TemplateManager {
      private final Map templates = Maps.newHashMap();
      private final String baseFolder;
      private final DataFixer field_191154_c;

      public TemplateManager(String p_i47239_1_, DataFixer p_i47239_2_) {
            this.baseFolder = p_i47239_1_;
            this.field_191154_c = p_i47239_2_;
      }

      public Template getTemplate(@Nullable MinecraftServer server, ResourceLocation id) {
            Template template = this.get(server, id);
            if (template == null) {
                  template = new Template();
                  this.templates.put(id.getResourcePath(), template);
            }

            return template;
      }

      @Nullable
      public Template get(@Nullable MinecraftServer p_189942_1_, ResourceLocation p_189942_2_) {
            String s = p_189942_2_.getResourcePath();
            if (this.templates.containsKey(s)) {
                  return (Template)this.templates.get(s);
            } else {
                  if (p_189942_1_ == null) {
                        this.readTemplateFromJar(p_189942_2_);
                  } else {
                        this.readTemplate(p_189942_2_);
                  }

                  return this.templates.containsKey(s) ? (Template)this.templates.get(s) : null;
            }
      }

      public boolean readTemplate(ResourceLocation server) {
            String s = server.getResourcePath();
            File file1 = new File(this.baseFolder, s + ".nbt");
            if (!file1.exists()) {
                  return this.readTemplateFromJar(server);
            } else {
                  FileInputStream inputstream = null;

                  boolean flag;
                  try {
                        inputstream = new FileInputStream(file1);
                        this.readTemplateFromStream(s, inputstream);
                        boolean var6 = true;
                        return var6;
                  } catch (Throwable var10) {
                        flag = false;
                  } finally {
                        IOUtils.closeQuietly(inputstream);
                  }

                  return flag;
            }
      }

      private boolean readTemplateFromJar(ResourceLocation id) {
            String s = id.getResourceDomain();
            String s1 = id.getResourcePath();
            InputStream inputstream = null;

            boolean flag;
            try {
                  inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/structures/" + s1 + ".nbt");
                  this.readTemplateFromStream(s1, inputstream);
                  boolean var6 = true;
                  return var6;
            } catch (Throwable var10) {
                  flag = false;
            } finally {
                  IOUtils.closeQuietly(inputstream);
            }

            return flag;
      }

      private void readTemplateFromStream(String id, InputStream stream) throws IOException {
            NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);
            if (!nbttagcompound.hasKey("DataVersion", 99)) {
                  nbttagcompound.setInteger("DataVersion", 500);
            }

            Template template = new Template();
            template.read(this.field_191154_c.process(FixTypes.STRUCTURE, nbttagcompound));
            this.templates.put(id, template);
      }

      public boolean writeTemplate(@Nullable MinecraftServer server, ResourceLocation id) {
            String s = id.getResourcePath();
            if (server != null && this.templates.containsKey(s)) {
                  File file1 = new File(this.baseFolder);
                  if (!file1.exists()) {
                        if (!file1.mkdirs()) {
                              return false;
                        }
                  } else if (!file1.isDirectory()) {
                        return false;
                  }

                  File file2 = new File(file1, s + ".nbt");
                  Template template = (Template)this.templates.get(s);
                  FileOutputStream outputstream = null;

                  boolean flag;
                  try {
                        NBTTagCompound nbttagcompound = template.writeToNBT(new NBTTagCompound());
                        outputstream = new FileOutputStream(file2);
                        CompressedStreamTools.writeCompressed(nbttagcompound, outputstream);
                        boolean var10 = true;
                        return var10;
                  } catch (Throwable var14) {
                        flag = false;
                  } finally {
                        IOUtils.closeQuietly(outputstream);
                  }

                  return flag;
            } else {
                  return false;
            }
      }

      public void remove(ResourceLocation p_189941_1_) {
            this.templates.remove(p_189941_1_.getResourcePath());
      }
}
