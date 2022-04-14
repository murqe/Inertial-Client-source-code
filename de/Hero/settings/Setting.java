package de.Hero.settings;

import java.util.ArrayList;
import java.util.function.Supplier;
import me.rich.module.Feature;

public class Setting {
      private String name;
      private Feature parent;
      private String mode;
      private String sval;
      private ArrayList options;
      private boolean bval;
      private double dval;
      private double min;
      private double max;
      private boolean onlyint = false;
      private Supplier visibility;

      public Setting(String name, Feature parent, String sval, ArrayList options) {
            this.name = name;
            this.parent = parent;
            this.sval = sval;
            this.options = options;
            this.mode = "Combo";
            this.visibility = () -> {
                  return true;
            };
      }

      public Setting(String name, Feature parent, boolean bval) {
            this.name = name;
            this.parent = parent;
            this.bval = bval;
            this.mode = "Check";
            this.visibility = () -> {
                  return true;
            };
      }

      public Setting(String name, Feature parent, double dval, double min, double max, boolean onlyint) {
            this.name = name;
            this.parent = parent;
            this.dval = dval;
            this.min = min;
            this.max = max;
            this.onlyint = onlyint;
            this.mode = "Slider";
            this.visibility = () -> {
                  return true;
            };
      }

      public Setting(String name, Feature parent, double dval, double min, double max) {
            this.name = name;
            this.parent = parent;
            this.dval = dval;
            this.min = min;
            this.max = max;
            this.mode = "HueSlider";
            this.visibility = () -> {
                  return true;
            };
      }

      public Setting(String name, Feature parent, double dval, double min, double max, int fix) {
            this.name = name;
            this.parent = parent;
            this.dval = dval;
            this.min = min;
            this.max = max;
            this.mode = "BrightNessSlider";
            this.visibility = () -> {
                  return true;
            };
      }

      public Setting(String name, Feature parent, double dval, double min, double max, String fix) {
            this.name = name;
            this.parent = parent;
            this.dval = dval;
            this.min = min;
            this.max = max;
            this.mode = "SaturationSlider";
            this.visibility = () -> {
                  return true;
            };
      }

      public String getName() {
            return this.name;
      }

      public Feature getParentMod() {
            return this.parent;
      }

      public String getValString() {
            return this.sval;
      }

      public void setValString(String in) {
            this.sval = in;
      }

      public int getValInt() {
            if (this.onlyint) {
                  this.dval = (double)((int)this.dval);
            }

            return (int)this.dval;
      }

      public void setValFloat(float in) {
            this.dval = (double)in;
      }

      public boolean isVisible() {
            return (Boolean)this.visibility.get();
      }

      public ArrayList getOptions() {
            return this.options;
      }

      public boolean getValBoolean() {
            return this.bval;
      }

      public void setValBoolean(boolean in) {
            this.bval = in;
      }

      public double getValDouble() {
            if (this.onlyint) {
                  this.dval = (double)((int)this.dval);
            }

            return this.dval;
      }

      public float getValFloat() {
            if (this.onlyint) {
                  this.dval = (double)((int)this.dval);
            }

            return (float)this.dval;
      }

      public void setValDouble(double in) {
            this.dval = in;
      }

      public boolean isHueSlider() {
            return this.mode.equalsIgnoreCase("HueSlider");
      }

      public boolean isBrightSlider() {
            return this.mode.equalsIgnoreCase("BrightNessSlider");
      }

      public boolean isSaturationSlider() {
            return this.mode.equalsIgnoreCase("SaturationSlider");
      }

      public boolean isCheck() {
            return this.mode.equalsIgnoreCase("Check");
      }

      public boolean isSlider() {
            return this.mode.equalsIgnoreCase("Slider");
      }

      public double getMin() {
            return this.min;
      }

      public double getMax() {
            return this.max;
      }

      public boolean isCombo() {
            return this.mode.equalsIgnoreCase("Combo");
      }

      public boolean onlyInt() {
            return this.onlyint;
      }
}
