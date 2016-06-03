
package org.bluechat.blueflood.solver;

import org.bluechat.blueflood.model.Board;
import org.bluechat.blueflood.model.ColorArea;

import java.util.Collection;
import java.util.Collections;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteLists;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;

/**
 * this class contains a collection for ColorAreas, grouped by their colors.
 * provides some helper functions for the search strategies.
 */
public class ColorAreaGroup {

    private final Board board;
    private final ReferenceOpenHashSet<ColorArea>[] theArray;

    /**
     * the standard constructor
     */
    @SuppressWarnings("unchecked")
    public ColorAreaGroup(final Board board) {
        this.board = board;
        this.theArray = new ReferenceOpenHashSet[this.board.getNumColors()];
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            this.theArray[color] = new ReferenceOpenHashSet<ColorArea>();
        }
    }

    /**
     * the copy constructor
     */
    @SuppressWarnings("unchecked")
    public ColorAreaGroup(final org.bluechat.blueflood.solver.ColorAreaGroup other) {
        this.board = other.board;
        this.theArray = new ReferenceOpenHashSet[this.board.getNumColors()];
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            this.theArray[color] = other.theArray[color].clone();
        }
    }

    /**
     * add all color areas that are not members of the "exclude" set.
     * @param addColorAreas the color areas to be added
     * @param excludeColorAreas color areas that are also members of this set will not be added
     */
    public void addAll(final Collection<ColorArea> addColorAreas, final ReferenceSet<ColorArea> excludeColorAreas) {
        for (final ColorArea ca : addColorAreas) {
            if (false == excludeColorAreas.contains(ca)) {
                this.theArray[ca.getColor()].add(ca);
            }
        }
    }

    /**
     * remove from this object all the specified color areas.
     * @param removeColorAreas
     */
    public void removeAll(final Collection<ColorArea> removeColorAreas) {
        for (final ColorArea ca : removeColorAreas) {
            this.theArray[ca.getColor()].remove(ca);
        }
    }

    /**
     * add all color areas into the specified color.
     * warning: does not check or update the color areas for consistency.
     * @param addColorAreas the color areas to be added
     * @param color the color
     */
    public void addAllColor(final Collection<ColorArea> addColorAreas, final byte color) {
        this.theArray[color].addAll(addColorAreas);
    }

    /**
     * remove all color areas from the specified color.
     * warning: does not check or update the color areas for consistency.
     * @param removeColorAreas the color areas to be removed
     * @param color the color
     */
    public void removeAllColor(final Collection<ColorArea> removeColorAreas, final byte color) {
        this.theArray[color].removeAll(removeColorAreas);
    }

    /**
     * count the number of colors that have at least one color area.
     * @return number of occupied colors
     */
    public int countColorsNotEmpty() {
        int result = 0;
        for (final ReferenceSet<ColorArea> setCa : this.theArray) {
            if (false == setCa.isEmpty()) {
                ++result;
            }
        }
        return result;
    }

    /**
     * get the colors that have at least one color area.
     * @return list of occupied colors, not expected to be empty
     */
    public ByteList getColorsNotEmpty() {
        final ByteList result = new ByteArrayList();
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            if (false == this.theArray[color].isEmpty()) {
                result.add(color);
            }
        }
        return result;
    }

    /**
     * get the colors that are situated at the specified depth.
     * @return list of colors at depth, may be empty
     */
    public ByteList getColorsDepth(final int depth) {
        ByteList result = ByteLists.EMPTY_LIST;
        for (final ReferenceSet<ColorArea> caSet : this.theArray) {
            for (final ColorArea ca : caSet) {
                if (ca.getDepth() == depth) {
                    if (false == result instanceof ByteArrayList) {
                        result = new ByteArrayList();
                    }
                    result.add(ca.getColor());
                    break; // for (ca)
                }
            }
        }
        return result;
    }

    /**
     * get the colors that are situated at the specified depth or lower,
     * but only the colors at the maximum depth level.
     * @return list of colors at depth or lower, not expected to be empty
     */
    public ByteList getColorsDepthOrLower(final int depth) {
        final ByteList result = new ByteArrayList();
        int depthMax = -1;
        for (final ReferenceSet<ColorArea> caSet : this.theArray) {
            byte color = Byte.MIN_VALUE;
            int depthColor = -2;
            for (final ColorArea ca : caSet) {
                final int d = ca.getDepth();
                if (d == depth) {
                    color = ca.getColor();
                    depthColor = d;
                    break; // for (ca)
                } else if ((d > depthColor) && (d < depth)) {
                    color = ca.getColor();
                    depthColor = d;
                }
            }
            if (depthMax < depthColor) {
                depthMax = depthColor;
                result.clear();
                result.add(color);
            } else if (depthMax == depthColor) {
                result.add(color);
            }
        }
        return result;
    }

    /**
     * get the colors that are contained completely in other.
     * @param other
     * @return list of completed colors, may be empty
     */
    public ByteList getColorsCompleted(final org.bluechat.blueflood.solver.ColorAreaGroup other) {
        ByteList result = ByteLists.EMPTY_LIST;
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            final ReferenceSet<ColorArea> thisSet = this.theArray[color];
            if (thisSet.size() > 0) {
                final ReferenceSet<ColorArea> otherSet = other.theArray[color];
                if ((thisSet.size() == otherSet.size()) && (thisSet.containsAll(otherSet))) {
                    if (false == result instanceof ByteArrayList) {
                        result = new ByteArrayList();
                    }
                    result.add(color);
                }
            }
        }
        return result;
    }

    /**
     * get the colors that have the maximum number of member cells.
     * @return list of colors, not expected to be empty
     */
    public ByteList getColorsMaxMembers() {
        final ByteList result = new ByteArrayList();
        int maxCount = 1; // return empty collection if all colors are empty. not expected!
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            int count = 0;
            for (final ColorArea ca : this.theArray[color]) {
                count += ca.getMembers().size();
            }
            if (maxCount < count) {
                maxCount = count;
                result.clear();
            }
            if (maxCount == count) {
                result.add(color);
            }
        }
        return result;
    }

    /**
     * get the colors that have the maximum number of member cells.
     * the color areas are counted only if their neighbors are not contained in excludeNeighbors.
     * @param excludeNeighbors exclude color areas if their neighbors are contained here
     * @return list of colors, not expected to be empty
     */
    public ByteList getColorsMaxMembers(final ReferenceSet<ColorArea> excludeNeighbors) {
        final ByteList result = new ByteArrayList();
        int maxCount = 1; // return empty collection if all colors are empty. not expected!
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            int count = 0;
            for (final ColorArea ca : this.theArray[color]) {
                if (false == excludeNeighbors.containsAll(ca.getNeighbors())) {
                    count += ca.getMembers().size();
                }
            }
            if (maxCount < count) {
                maxCount = count;
                result.clear();
            }
            if (maxCount == count) {
                result.add(color);
            }
        }
        return result;
    }

    /**
     * get the colors that have the maximum number of new neighbor cells,
     * with the neighbors and all of their neighbors are not contained in excludeNeighbors.
     * @param excludeNeighbors exclude color areas if their neighbors or their next neighbors are contained here
     * @return list of colors, not expected to be empty
     */
    public ByteList getColorsMaxNextNeighbors(final ReferenceSet<ColorArea> excludeNeighbors) {
        final ByteList result = new ByteArrayList();
        int maxCount = -1; // include colors that have zero or more next new neighbors
        for (byte color = 0;  color < this.theArray.length;  ++color) {
            int count = 0;
            for (final ColorArea ca : this.theArray[color]) {
                for (final ColorArea caNext : ca.getNeighbors()) {
                    if ((false == excludeNeighbors.contains(caNext))
                            && Collections.disjoint(excludeNeighbors, caNext.getNeighbors())) {
                        count += caNext.getMembers().size();
                    }
                }
            }
            if (maxCount < count) {
                maxCount = count;
                result.clear();
            }
            if (maxCount == count) {
                result.add(color);
            }
        }
        return result;
    }

    /**
     * return the areas of this color.
     * @param color
     * @return the areas
     */
    public Collection<ColorArea> getColor(final byte color) {
        return this.theArray[color];
    }

    /**
     * remove from this object the areas of this color.
     * @param color
     */
    public Collection<ColorArea> removeColor(final byte color) {
        return this.theArray[color] = new ReferenceOpenHashSet<ColorArea>();
    }
}
