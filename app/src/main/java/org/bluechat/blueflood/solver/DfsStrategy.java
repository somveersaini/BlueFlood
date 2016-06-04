package org.bluechat.blueflood.solver;

import org.bluechat.blueflood.model.ColorArea;

import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.objects.ReferenceSet;

/**
 * a strategy for the depth-first search (DFS) solver.
 */
public interface DfsStrategy extends Strategy {

    /**
     * select one or more colors (from neighbors) for the next step of the depth-first search.
     * 
     * @param depth current DFS depth
     * @param thisColor color used in this step
     * @param solution the solution so far
     * @param flooded the flooded area of the board
     * @param notFlooded the area of the board not flooded yet
     * @param neighbors the neighbor areas of the flooded area
     * @return the colors to be used for the next step
     */
    public ByteList selectColors(int depth,
                                 byte thisColor,
                                 byte[] solution,
                                 ReferenceSet<ColorArea> flooded,
                                 ColorAreaGroup notFlooded,
                                 ColorAreaGroup neighbors);
}
