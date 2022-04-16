package wtf.rich.api.utils.shader.shaders;

import org.lwjgl.opengl.GL20;
import wtf.rich.api.utils.shader.FramebufferShader;

public class GlowShader extends FramebufferShader {
     public static final GlowShader GLOW_SHADER = new GlowShader();

     public GlowShader() {
          super("glow.frag");
     }

     public void setupUniforms() {
          this.setupUniform("texture");
          this.setupUniform("texelSize");
          this.setupUniform("color");
          this.setupUniform("radius");
          this.setupUniform("direction");
     }

     public void updateUniforms() {
          GL20.glUniform1i(this.getUniform("texture"), 0);
          GL20.glUniform2f(this.getUniform("texelSize"), 1.0F / (float)mc.displayWidth * this.radius * this.quality, 1.0F / (float)mc.displayHeight * this.radius * this.quality);
          GL20.glUniform3f(this.getUniform("color"), this.red, this.green, this.blue);
          GL20.glUniform1f(this.getUniform("radius"), this.radius);
          GL20.glUniform2f(this.getUniform("direction"), 1.0F, 1.0F);
     }
}
