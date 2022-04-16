package wtf.rich.api.utils.shader;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderShell {
     public static ShaderShell BLUR_SHADER;
     public static ShaderShell MENU_SHADER;
     public static ShaderShell CIRCLE_TEXTURE_SHADER;
     public static ShaderShell CIRCLE_SHADER;
     public static ShaderShell SCROLL_SHADER;
     public static ShaderShell FONTRENDERER_SUBSTRING;
     public static ShaderShell ROUNDED_RECT;
     public static ShaderShell GLOW_SHADER;
     private int shaderID;

     public ShaderShell(String shaderName, boolean post) {
          this.parseShaderFromFile(shaderName, post);
     }

     public static void init() {
          ROUNDED_RECT = new ShaderShell("roundedrect", false);
     }

     public void attach() {
          ARBShaderObjects.glUseProgramObjectARB(this.shaderID);
     }

     public void set1I(String name, int value0) {
          ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(this.shaderID, name), value0);
     }

     public void set1F(String name, float value0) {
          ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(this.shaderID, name), value0);
     }

     public void set2F(String name, float value0, float value1) {
          ARBShaderObjects.glUniform2fARB(ARBShaderObjects.glGetUniformLocationARB(this.shaderID, name), value0, value1);
     }

     public void set3F(String name, float value0, float value1, float value2) {
          ARBShaderObjects.glUniform3fARB(ARBShaderObjects.glGetUniformLocationARB(this.shaderID, name), value0, value1, value2);
     }

     public void set4F(String name, float value0, float value1, float value2, float value3) {
          ARBShaderObjects.glUniform4fARB(ARBShaderObjects.glGetUniformLocationARB(this.shaderID, name), value0, value1, value2, value3);
     }

     public void detach() {
          ARBShaderObjects.glUseProgramObjectARB(0);
     }

     private void parseShaderFromFile(String shaderName, boolean post) {
          if (shaderName.equalsIgnoreCase("roundedrect")) {
               this.parseShaderFromString("uniform vec4 color;\nuniform vec2 resolution;\nuniform vec2 center;\nuniform vec2 dst;\nuniform float radius;\nuniform float force;\n\nfloat rect(vec2 pos, vec2 center, vec2 size) {\n    return length(max(abs(center - pos) - (size / 2), 0)) - radius;\n}\n\nvoid main() {\n    vec2 pos = gl_FragCoord.xy;\n\tpos.y = resolution.y - pos.y;\n\tgl_FragColor = vec4(vec3(color), (-rect(pos, center, dst) / radius) * color.a / (radius / force));\n} \t", post);
          }

     }

     private void parseShaderFromString(String str, boolean post) {
          this.localInit(str);
     }

     void localInit(String str) {
          int shaderProgram = ARBShaderObjects.glCreateProgramObjectARB();
          if (shaderProgram == 0) {
               System.out.println("PC Issued");
               System.exit(0);
          } else {
               int shader = ARBShaderObjects.glCreateShaderObjectARB(35632);
               ARBShaderObjects.glShaderSourceARB(shader, str);
               ARBShaderObjects.glCompileShaderARB(shader);
               ARBShaderObjects.glAttachObjectARB(shaderProgram, shader);
               ARBShaderObjects.glLinkProgramARB(shaderProgram);
               this.shaderID = shaderProgram;
          }
     }
}
