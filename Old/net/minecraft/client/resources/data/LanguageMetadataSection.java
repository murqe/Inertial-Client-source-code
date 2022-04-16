package net.minecraft.client.resources.data;

import java.util.Collection;

public class LanguageMetadataSection implements IMetadataSection {
      private final Collection languages;

      public LanguageMetadataSection(Collection languagesIn) {
            this.languages = languagesIn;
      }

      public Collection getLanguages() {
            return this.languages;
      }
}
