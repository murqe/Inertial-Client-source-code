package wtf.rich.api.utils.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {
     private int program;
     private Map uniformsMap;

     public Shader(String fragmentShader) {
          int vertexShaderID;
          int fragmentShaderID;
          try {
               InputStream vertexStream = this.getClass().getResourceAsStream("/assets/minecraft/richclient/shaders/vertex.vert");
               vertexShaderID = this.createShader(IOUtils.toString(vertexStream), 35633);
               IOUtils.closeQuietly(vertexStream);
               InputStream fragmentStream = this.getClass().getResourceAsStream("/assets/minecraft/richclient/shaders/fragment/" + fragmentShader);
               fragmentShaderID = this.createShader(IOUtils.toString(fragmentStream), 35632);
               IOUtils.closeQuietly(fragmentStream);
          } catch (Exception var6) {
               return;
          }

          if (vertexShaderID != 0 && fragmentShaderID != 0) {
               this.program = ARBShaderObjects.glCreateProgramObjectARB();
               if (this.program != 0) {
                    ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
                    ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
                    ARBShaderObjects.glLinkProgramARB(this.program);
                    ARBShaderObjects.glValidateProgramARB(this.program);
               }
          }
     }

     public void startShader() {
          GL11.glPushMatrix();
          GL20.glUseProgram(this.program);
          if (this.uniformsMap == null) {
               this.uniformsMap = new HashMap();
               this.setupUniforms();
          }

          this.updateUniforms();
     }

     public void stopShader() {
          GL20.glUseProgram(0);
          GL11.glPopMatrix();
     }

     public abstract void setupUniforms();

     public abstract void updateUniforms();

     private int createShader(String shaderSource, int shaderType) {
          byte shader = 0;

          try {
               int shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
               if (shader == 0) {
                    return 0;
               } else {
                    ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
                    ARBShaderObjects.glCompileShaderARB(shader);
                    return shader;
               }
          } catch (Exception var5) {
               ARBShaderObjects.glDeleteObjectARB(shader);
               throw var5;
          }
     }

     public void setUniform(String uniformName, int location) {
          this.uniformsMap.put(uniformName, location);
     }

     public void setupUniform(String uniformName) {
          this.setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
     }

     public int getUniform(String uniformName) {
          return (Integer)this.uniformsMap.get(uniformName);
     }
}
