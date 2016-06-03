package org.bluechat.blueflood;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import org.bluechat.blueflood.model.GamePreferences;
import org.bluechat.blueflood.model.GridLinesEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.jar.Attributes;

/**
 * Created by Samsaini on 05/16/2016.
 */
public class GameGrid extends GridLayout {

    private final static  String TAG = "GameAdater";
    public int[] pre;
    private Context context;
    SharedPreferences settings;
    public int[] uiColors;
    public int columns = 5, rows = 5, startPos;
    public int[] cellColors = new int[0];
    public boolean[] cellHighlights = new boolean[0];
    public boolean[] cellColorNumbers = new boolean[0];
    public GridLinesEnum gridLines;
    public GamePreferences gamePreferences;
    int height, theight;

    public GameGrid(Context context, AttributeSet attrs){
        super(context);
    }
    public GameGrid(Context context, final int columns, final int rows, int height, final int[] uiColors, final int startPos,final int[] cellcolor, final int cellSize) {

        super(context);
        this.context = context;
        this.pre = new int[uiColors.length];
        this.uiColors = uiColors;
        this.columns = columns;
        this.rows = rows;
        this.height = height;
        this.startPos = startPos;
        this.cellColors = new int[columns * rows];
        this.cellColors = cellcolor;
        System.out.println(Arrays.toString(cellcolor));
        this.cellHighlights = new boolean[this.cellColors.length];
        settings = PreferenceManager.getDefaultSharedPreferences(this.context);
    }



    /**
     * set the colors of all cells.
     */
    public void setCellColors(final int[] cellColors, final GridLinesEnum gle) {
        pre = this.cellColors;
        this.cellColors = cellColors;
        this.cellHighlights = new boolean[this.cellColors.length];
        this.gridLines = gle;
        this.invalidate();
    }

    public void applyColorScheme(final int[] uiColors, final GridLinesEnum gle) {
        this.uiColors = uiColors;
        this.gridLines = gle;
        this.invalidate();
    }
    /**
     * set the highlight value of all cells - the ones contained in the specified
     * collection are set to true, all others to false.
     * @param highlightCells
     */
    public void highlightCells(final Collection<Integer> highlightCells) {
        Arrays.fill(this.cellHighlights, false);
        for (final Integer cell : highlightCells) {
            this.cellHighlights[cell.intValue()] = true;
        }
        this.invalidate();
    }

    public int getColor(int pos){
        return this.cellColors[pos];
    }
}
