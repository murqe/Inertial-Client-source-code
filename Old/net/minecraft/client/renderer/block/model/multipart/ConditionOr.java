package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockStateContainer;

public class ConditionOr implements ICondition {
      final Iterable conditions;

      public ConditionOr(Iterable conditionsIn) {
            this.conditions = conditionsIn;
      }

      public Predicate getPredicate(final BlockStateContainer blockState) {
            return Predicates.or(Iterables.transform(this.conditions, new Function() {
                  @Nullable
                  public Predicate apply(@Nullable ICondition p_apply_1_) {
                        return p_apply_1_ == null ? null : p_apply_1_.getPredicate(blockState);
                  }
            }));
      }
}
