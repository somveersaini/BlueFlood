package org.bluechat.blueflood.solver;

import org.bluechat.blueflood.model.ColorArea;

import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.objects.ReferenceSet;

/**
 * this strategy results in an incomplete search.
 * it chooses the colors in two steps:
 * <p>
 * 1) colors that can be completely flooded in the next step.
 * (these are always optimal moves!?)
 * <p>
 * 2) if 1) gives no result then the colors that have the maximum number
 * of new neighbor member cells, that means neighbors that are not yet flooded
 * and not yet known as neighbors of the flooded area.
 * (hence the name "greedy next")
 */
public class GreedyNextDfsStrategy implements DfsStrategy {

    @Override
    public ByteList selectColors(final int depth,
            final byte thisColor,
            final byte[] solution,
            final ReferenceSet<ColorArea> flooded,
            final ColorAreaGroup notFlooded,
            final ColorAreaGroup neighbors) {
        ByteList result = neighbors.getColorsCompleted(notFlooded);
        if (result.isEmpty()) {
            result = neighbors.getColorsMaxNextNeighbors(flooded);
        }
        return result;
    }
}
