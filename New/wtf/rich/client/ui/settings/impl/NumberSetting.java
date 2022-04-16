package wtf.rich.client.ui.settings.impl;

import java.util.function.Supplier;
import wtf.rich.client.ui.settings.Setting;

public class NumberSetting extends Setting {
     private final NumberSetting.NumberType type;
     private float current;
     private float minimum;
     private float maximum;
     private float increment;
     private String desc;

     public NumberSetting(String name, float current, float minimum, float maximum, float increment, Supplier visible) {
          this.name = name;
          this.minimum = minimum;
          this.current = current;
          this.maximum = maximum;
          this.increment = increment;
          this.type = NumberSetting.NumberType.DEFAULT;
          this.setVisible(visible);
     }

     public NumberSetting(String name, float current, float minimum, float maximum, float increment, Supplier visible, NumberSetting.NumberType type) {
          this.name = name;
          this.minimum = minimum;
          this.current = current;
          this.maximum = maximum;
          this.increment = increment;
          this.type = type;
          this.setVisible(visible);
     }

     public NumberSetting(String name, String desc, float current, float minimum, float maximum, float increment, Supplier visible) {
          this.name = name;
          this.desc = desc;
          this.minimum = minimum;
          this.current = current;
          this.maximum = maximum;
          this.increment = increment;
          this.type = NumberSetting.NumberType.DEFAULT;
          this.setVisible(visible);
     }

     public NumberSetting(String name, String desc, float current, float minimum, float maximum, float increment, Supplier visible, NumberSetting.NumberType type) {
          this.name = name;
          this.desc = desc;
          this.minimum = minimum;
          this.current = current;
          this.maximum = maximum;
          this.increment = increment;
          this.type = type;
          this.setVisible(visible);
     }

     public String getDesc() {
          return this.desc;
     }

     public void setDesc(String desc) {
          this.desc = desc;
     }

     public float getMinValue() {
          return this.minimum;
     }

     public void setMinValue(float minimum) {
          this.minimum = minimum;
     }

     public float getMaxValue() {
          return this.maximum;
     }

     public void setMaxValue(float maximum) {
          this.maximum = maximum;
     }

     public float getNumberValue() {
          return this.current;
     }

     public void setValueNumber(float current) {
          this.current = current;
     }

     public float getIncrement() {
          return this.increment;
     }

     public void setIncrement(float increment) {
          this.increment = increment;
     }

     public NumberSetting.NumberType getType() {
          return this.type;
     }

     public static enum NumberType {
          MS("Ms"),
          APS("Aps"),
          SIZE("Size"),
          PERCENTAGE("Percentage"),
          DISTANCE("Distance"),
          DEFAULT("");

          String name;

          private NumberType(String name) {
               this.name = name;
          }

          public String getName() {
               return this.name;
          }
     }
}
