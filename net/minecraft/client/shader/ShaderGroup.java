package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {
      private final Framebuffer mainFramebuffer;
      private final IResourceManager resourceManager;
      private final String shaderGroupName;
      private final List listShaders = Lists.newArrayList();
      private final Map mapFramebuffers = Maps.newHashMap();
      private final List listFramebuffers = Lists.newArrayList();
      private Matrix4f projectionMatrix;
      private int mainFramebufferWidth;
      private int mainFramebufferHeight;
      private float time;
      private float lastStamp;

      public ShaderGroup(TextureManager p_i1050_1_, IResourceManager resourceManagerIn, Framebuffer mainFramebufferIn, ResourceLocation p_i1050_4_) throws JsonException, IOException, JsonSyntaxException {
            this.resourceManager = resourceManagerIn;
            this.mainFramebuffer = mainFramebufferIn;
            this.time = 0.0F;
            this.lastStamp = 0.0F;
            this.mainFramebufferWidth = mainFramebufferIn.framebufferWidth;
            this.mainFramebufferHeight = mainFramebufferIn.framebufferHeight;
            this.shaderGroupName = p_i1050_4_.toString();
            this.resetProjectionMatrix();
            this.parseGroup(p_i1050_1_, p_i1050_4_);
      }

      public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws JsonException, IOException, JsonSyntaxException {
            JsonParser jsonparser = new JsonParser();
            IResource iresource = null;

            try {
                  iresource = this.resourceManager.getResource(p_152765_2_);
                  JsonObject jsonobject = jsonparser.parse(IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
                  int j;
                  Iterator var8;
                  JsonElement jsonelement1;
                  JsonException jsonexception2;
                  JsonArray jsonarray1;
                  if (JsonUtils.isJsonArray(jsonobject, "targets")) {
                        jsonarray1 = jsonobject.getAsJsonArray("targets");
                        j = 0;

                        for(var8 = jsonarray1.iterator(); var8.hasNext(); ++j) {
                              jsonelement1 = (JsonElement)var8.next();

                              try {
                                    this.initTarget(jsonelement1);
                              } catch (Exception var18) {
                                    jsonexception2 = JsonException.forException(var18);
                                    jsonexception2.prependJsonKey("targets[" + j + "]");
                                    throw jsonexception2;
                              }
                        }
                  }

                  if (JsonUtils.isJsonArray(jsonobject, "passes")) {
                        jsonarray1 = jsonobject.getAsJsonArray("passes");
                        j = 0;

                        for(var8 = jsonarray1.iterator(); var8.hasNext(); ++j) {
                              jsonelement1 = (JsonElement)var8.next();

                              try {
                                    this.parsePass(p_152765_1_, jsonelement1);
                              } catch (Exception var17) {
                                    jsonexception2 = JsonException.forException(var17);
                                    jsonexception2.prependJsonKey("passes[" + j + "]");
                                    throw jsonexception2;
                              }
                        }
                  }
            } catch (Exception var19) {
                  JsonException jsonexception = JsonException.forException(var19);
                  jsonexception.setFilenameAndFlush(p_152765_2_.getResourcePath());
                  throw jsonexception;
            } finally {
                  IOUtils.closeQuietly(iresource);
            }

      }

      private void initTarget(JsonElement p_148027_1_) throws JsonException {
            if (JsonUtils.isString(p_148027_1_)) {
                  this.addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
            } else {
                  JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
                  String s = JsonUtils.getString(jsonobject, "name");
                  int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
                  int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);
                  if (this.mapFramebuffers.containsKey(s)) {
                        throw new JsonException(s + " is already defined");
                  }

                  this.addFramebuffer(s, i, j);
            }

      }

      private void parsePass(TextureManager p_152764_1_, JsonElement p_152764_2_) throws JsonException, IOException {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_152764_2_, "pass");
            String s = JsonUtils.getString(jsonobject, "name");
            String s1 = JsonUtils.getString(jsonobject, "intarget");
            String s2 = JsonUtils.getString(jsonobject, "outtarget");
            Framebuffer framebuffer = this.getFramebuffer(s1);
            Framebuffer framebuffer1 = this.getFramebuffer(s2);
            if (framebuffer == null) {
                  throw new JsonException("Input target '" + s1 + "' does not exist");
            } else if (framebuffer1 == null) {
                  throw new JsonException("Output target '" + s2 + "' does not exist");
            } else {
                  Shader shader = this.addShader(s, framebuffer, framebuffer1);
                  JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", (JsonArray)null);
                  if (jsonarray != null) {
                        int i = 0;

                        for(Iterator var12 = jsonarray.iterator(); var12.hasNext(); ++i) {
                              JsonElement jsonelement = (JsonElement)var12.next();

                              try {
                                    JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
                                    String s4 = JsonUtils.getString(jsonobject1, "name");
                                    String s3 = JsonUtils.getString(jsonobject1, "id");
                                    Framebuffer framebuffer2 = this.getFramebuffer(s3);
                                    if (framebuffer2 == null) {
                                          ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
                                          IResource iresource = null;

                                          try {
                                                iresource = this.resourceManager.getResource(resourcelocation);
                                          } catch (FileNotFoundException var29) {
                                                throw new JsonException("Render target or texture '" + s3 + "' does not exist");
                                          } finally {
                                                IOUtils.closeQuietly(iresource);
                                          }

                                          p_152764_1_.bindTexture(resourcelocation);
                                          ITextureObject lvt_20_2_ = p_152764_1_.getTexture(resourcelocation);
                                          int lvt_21_1_ = JsonUtils.getInt(jsonobject1, "width");
                                          int lvt_22_1_ = JsonUtils.getInt(jsonobject1, "height");
                                          boolean var23 = JsonUtils.getBoolean(jsonobject1, "bilinear");
                                          if (var23) {
                                                GlStateManager.glTexParameteri(3553, 10241, 9729);
                                                GlStateManager.glTexParameteri(3553, 10240, 9729);
                                          } else {
                                                GlStateManager.glTexParameteri(3553, 10241, 9728);
                                                GlStateManager.glTexParameteri(3553, 10240, 9728);
                                          }

                                          shader.addAuxFramebuffer(s4, lvt_20_2_.getGlTextureId(), lvt_21_1_, lvt_22_1_);
                                    } else {
                                          shader.addAuxFramebuffer(s4, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
                                    }
                              } catch (Exception var31) {
                                    JsonException jsonexception = JsonException.forException(var31);
                                    jsonexception.prependJsonKey("auxtargets[" + i + "]");
                                    throw jsonexception;
                              }
                        }
                  }

                  JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray)null);
                  if (jsonarray1 != null) {
                        int l = 0;

                        for(Iterator var34 = jsonarray1.iterator(); var34.hasNext(); ++l) {
                              JsonElement jsonelement1 = (JsonElement)var34.next();

                              try {
                                    this.initUniform(jsonelement1);
                              } catch (Exception var28) {
                                    JsonException jsonexception1 = JsonException.forException(var28);
                                    jsonexception1.prependJsonKey("uniforms[" + l + "]");
                                    throw jsonexception1;
                              }
                        }
                  }

            }
      }

      private void initUniform(JsonElement p_148028_1_) throws JsonException {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_148028_1_, "uniform");
            String s = JsonUtils.getString(jsonobject, "name");
            ShaderUniform shaderuniform = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);
            if (shaderuniform == null) {
                  throw new JsonException("Uniform '" + s + "' does not exist");
            } else {
                  float[] afloat = new float[4];
                  int i = 0;

                  for(Iterator var7 = JsonUtils.getJsonArray(jsonobject, "values").iterator(); var7.hasNext(); ++i) {
                        JsonElement jsonelement = (JsonElement)var7.next();

                        try {
                              afloat[i] = JsonUtils.getFloat(jsonelement, "value");
                        } catch (Exception var11) {
                              JsonException jsonexception = JsonException.forException(var11);
                              jsonexception.prependJsonKey("values[" + i + "]");
                              throw jsonexception;
                        }
                  }

                  switch(i) {
                  case 0:
                  default:
                        break;
                  case 1:
                        shaderuniform.set(afloat[0]);
                        break;
                  case 2:
                        shaderuniform.set(afloat[0], afloat[1]);
                        break;
                  case 3:
                        shaderuniform.set(afloat[0], afloat[1], afloat[2]);
                        break;
                  case 4:
                        shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
                  }

            }
      }

      public Framebuffer getFramebufferRaw(String p_177066_1_) {
            return (Framebuffer)this.mapFramebuffers.get(p_177066_1_);
      }

      public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
            Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
            framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            this.mapFramebuffers.put(p_148020_1_, framebuffer);
            if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight) {
                  this.listFramebuffers.add(framebuffer);
            }

      }

      public void deleteShaderGroup() {
            Iterator var1 = this.mapFramebuffers.values().iterator();

            while(var1.hasNext()) {
                  Framebuffer framebuffer = (Framebuffer)var1.next();
                  framebuffer.deleteFramebuffer();
            }

            var1 = this.listShaders.iterator();

            while(var1.hasNext()) {
                  Shader shader = (Shader)var1.next();
                  shader.deleteShader();
            }

            this.listShaders.clear();
      }

      public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException, IOException {
            Shader shader = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
            this.listShaders.add(this.listShaders.size(), shader);
            return shader;
      }

      private void resetProjectionMatrix() {
            this.projectionMatrix = new Matrix4f();
            this.projectionMatrix.setIdentity();
            this.projectionMatrix.m00 = 2.0F / (float)this.mainFramebuffer.framebufferTextureWidth;
            this.projectionMatrix.m11 = 2.0F / (float)(-this.mainFramebuffer.framebufferTextureHeight);
            this.projectionMatrix.m22 = -0.0020001999F;
            this.projectionMatrix.m33 = 1.0F;
            this.projectionMatrix.m03 = -1.0F;
            this.projectionMatrix.m13 = 1.0F;
            this.projectionMatrix.m23 = -1.0001999F;
      }

      public void createBindFramebuffers(int width, int height) {
            this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
            this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
            this.resetProjectionMatrix();
            Iterator var3 = this.listShaders.iterator();

            while(var3.hasNext()) {
                  Shader shader = (Shader)var3.next();
                  shader.setProjectionMatrix(this.projectionMatrix);
            }

            var3 = this.listFramebuffers.iterator();

            while(var3.hasNext()) {
                  Framebuffer framebuffer = (Framebuffer)var3.next();
                  framebuffer.createBindFramebuffer(width, height);
            }

      }

      public void loadShaderGroup(float partialTicks) {
            if (partialTicks < this.lastStamp) {
                  this.time += 1.0F - this.lastStamp;
                  this.time += partialTicks;
            } else {
                  this.time += partialTicks - this.lastStamp;
            }

            for(this.lastStamp = partialTicks; this.time > 20.0F; this.time -= 20.0F) {
            }

            Iterator var2 = this.listShaders.iterator();

            while(var2.hasNext()) {
                  Shader shader = (Shader)var2.next();
                  shader.loadShader(this.time / 20.0F);
            }

      }

      public final String getShaderGroupName() {
            return this.shaderGroupName;
      }

      private Framebuffer getFramebuffer(String p_148017_1_) {
            if (p_148017_1_ == null) {
                  return null;
            } else {
                  return p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : (Framebuffer)this.mapFramebuffers.get(p_148017_1_);
            }
      }
}
