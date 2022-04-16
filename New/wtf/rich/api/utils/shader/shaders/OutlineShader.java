package wtf.rich.api.utils.shader.shaders;

import org.lwjgl.opengl.GL20;
import wtf.rich.api.utils.shader.FramebufferShader;

public class OutlineShader extends FramebufferShader {
     public float rainbowSpeed = 0.5F;
     public float rainbowStrength = 0.25F;
     public float customSaturation = 0.5F;
     public static final OutlineShader INSTANCE = new OutlineShader("outline.frag");

     public OutlineShader(String fragmentShader) {
          super(fragmentShader);
     }

     public void setCustomValues(float rainbowSpeed, float rainbowStrength, float saturation) {
          this.rainbowSpeed = rainbowSpeed;
          this.rainbowStrength = rainbowStrength;
          this.customSaturation = saturation;
     }

     public void setupUniforms() {
          this.setupUniform("texture");
          this.setupUniform("texelSize");
          this.setupUniform("color");
          this.setupUniform("radius");
          this.setupUniform("rainbowStrength");
          this.setupUniform("rainbowSpeed");
          this.setupUniform("saturation");
     }

     public void updateUniforms() {
          GL20.glUniform1i(this.getUniform("texture"), 0);
          GL20.glUniform2f(this.getUniform("texelSize"), 1.0F / (float)mc.displayWidth * this.radius * this.quality, 1.0F / (float)mc.displayHeight * this.radius * this.quality);
          GL20.glUniform4f(this.getUniform("color"), this.red, this.green, this.blue, this.alpha);
          GL20.glUniform1f(this.getUniform("radius"), this.radius);
          float strength = 1.0F / -(1000.0F * this.rainbowStrength);
          GL20.glUniform2f(this.getUniform("rainbowStrength"), strength, strength);
          GL20.glUniform1f(this.getUniform("rainbowSpeed"), this.rainbowSpeed);
          GL20.glUniform1f(this.getUniform("saturation"), this.customSaturation);
     }
}
