package org.bluechat.blueflood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.bluechat.blueflood.model.GamePreferences;
import org.bluechat.blueflood.model.GridLinesEnum;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Samsaini on 05/18/2016.
 */
public class GameView extends View {

    // Set of vertex only x and y coordinates
    public static int intersections;     //no.of intersections
    public static int GameOver = 0;            // 1 means game is over i.e player has succeeded
    public int time = 0;
    public static boolean focus = false;
    private Handler h;                          // Array of nodes in the gameplay

    private Paint testPaint;
    private Paint textPaint;                // for painting the text
    private Paint finalPaint;
    private Paint blurPaint;
    private int HEIGHT = 470;
    private int WIDTH = 280;

    private static final long serialVersionUID = 8760536779314645208L;
    private int[] uiColors;
    private int columns = 5, rows = 5, startPos;
    private int[] cellColors = new int[0];
    private boolean[] cellHighlights = new boolean[0];
    private boolean[] cellColorNumbers = new boolean[0];
    private GridLinesEnum gridLines;
    public GamePreferences gamePreferences;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);


        gamePreferences = new GamePreferences();
        HEIGHT = MainActivity.HEIGHT  - 20;
       WIDTH = MainActivity.WIDTH  - 40;

    //    System.out.println("height " + HEIGHT + " " + WIDTH);

     //   Shader shader = new RadialGradient(WIDTH, HEIGHT, 350, Color.BLUE, Color.parseColor("#2979ff"), Shader.TileMode.CLAMP);

        testPaint = new Paint();
        testPaint.setColor(Color.MAGENTA);
        testPaint.setAntiAlias(true);
        testPaint.setStrokeWidth(5);
        testPaint.setStrokeJoin(Paint.Join.ROUND);
        //  testPaint.setStyle(Paint.Style.STROKE);

        finalPaint = new Paint();
        finalPaint.setAntiAlias(true);
        finalPaint.setStrokeWidth(5);
        finalPaint.setStyle(Paint.Style.STROKE);
      //  finalPaint.setShader(shader);

        blurPaint = new Paint();
        blurPaint.setAntiAlias(true);
        blurPaint.setAlpha(140);
        blurPaint.setStyle(Paint.Style.STROKE);
        blurPaint.setColor(Color.parseColor("#2196fe"));
        blurPaint.setStrokeWidth(6);


        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#76ff03"));
        textPaint.setTextSize(32);
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setAntiAlias(true);
        textPaint.setShadowLayer(9f, 3, 3, Color.parseColor("#2090ff"));

    /*        switch (Settings.theme) {
        case Constants.MAGNETA:
            testPaint.setColor(Color.MAGENTA);
            textPaint.setColor(Color.MAGENTA);
            this.setBackgroundColor(Color.WHITE);
            break;

        case Constants.WHITE:
            testPaint.setColor(Color.GRAY);
            textPaint.setColor(Color.MAGENTA);
            this.setBackgroundColor(Color.WHITE);
            break;

        case Constants.BLACK:
            testPaint.setColor(Color.WHITE);
            textPaint.setColor(Color.WHITE);
            this.setBackgroundColor(Color.BLACK);
            break;

        case Constants.BLUE:
            testPaint.setColor(Color.WHITE);
            blurPaint.setColor(Color.BLUE);
            textPaint.setColor(Color.WHITE);
            this.setBackgroundColor(Color.parseColor("#2196fe"));
            break;
    }

    */
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setDrawingInfo();
        return;
    }

    private void setDrawingInfo(){
        int dimension;
        if (getWidth() > getHeight()) {
            dimension = getHeight();
        } else {
            dimension = getWidth();
        }

//        dimension -= (dimension % boardSize);
//        if (boardSize != 0) {
//            cellSize = dimension / boardSize;
//            xOffset = (getWidth() - dimension) / 2 ;
//            yOffset = (getHeight() - dimension) / 2 ;
//            textPaint.setTextSize(cellSize);
//        }
        return;
    }
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }

    private int calculateCellIndex(final Point point) {
        final int cellWidth = WIDTH / this.columns;
        final int cellHeight = HEIGHT / this.rows;
        final int column = point.x / cellWidth;
        final int row = point.y / cellHeight;
        final int result = row * this.columns + column;
        return result;
    }
//    this.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mousePressed(MouseEvent e) {
//            final int index = calculateCellIndex(e.getPoint());
//            if ((index >= 0) && (index < BoardPanel.this.cellColors.length)) {
//                final int color = BoardPanel.this.cellColors[index];
//                BoardPanel.this.controller.userClickedOnCell(e, index, color);
//            }
//        }
//    });
//    this.addMouseMotionListener(new MouseMotionAdapter() {
//        int currentIndex = -1;
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            final int index = calculateCellIndex(e.getPoint());
//            if ((index >= 0) && (index < BoardPanel.this.cellColors.length)) {
//                if (this.currentIndex != index) {
//                    this.currentIndex = index;
//                    final int color = BoardPanel.this.cellColors[index];
//                    BoardPanel.this.controller.userMovedMouseToCell(e, index, color);
//                }
//            }
//        }
//    });
    protected void onDraw(Canvas c) {
        // final Graphics2D g2d = (Graphics2D) g.create();
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final int cellWidth = WIDTH / this.columns -6;
        final int cellHeight = (int)(HEIGHT / this.rows);
        final int cw1 = cellWidth - 1;
        final int ch1 = cellHeight - 1;
        final int cw4 = cellWidth / 4;
        final int ch4 = cellHeight / 4;
        final int cwHighlight = cellWidth - cw4 - cw4;
        final int chHighlight = cellHeight - ch4 - ch4;
        for (int index = 0, y = 0, row = 0;  row < this.rows;  y += cellHeight, ++row) {
            for (int x = 0, column = 0;  column < this.columns;  x += cellWidth, ++column, ++index) {
               // final boolean highlight = this.cellHighlights[index];
              //  final int color = this.cellColors[index];
                testPaint.setColor(Color.BLUE);
                // testPaint.setStrokeWidth(5f);
                c.drawRect(x , y, cellWidth, cellHeight, testPaint);
                Log.d("x, y cell", x + " " +y +" "+ cellHeight + " "+ cellWidth);
                x+=10;

                // finalPaint.setColor(Color.WHITE);
            /*if (highlight) {
               // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               // g2d.setColor(Color.WHITE);
                c.drawRect(x + cw4, y + ch4, cwHighlight, chHighlight, finalPaint);

            }
            if (index == this.startPos) {
                c.drawRect(x + cellWidth * 3/8, y + cellHeight * 3/8, cw4, ch4, finalPaint);
            }
            if (GridLinesEnum.NONE != this.gridLines) {
               // g2d.setColor(Color.WHITE);
                if ((column < this.columns - 1) && ((GridLinesEnum.ALL == this.gridLines) || (color != this.cellColors[index + 1]))) {
                    c.drawLine(x + cw1, y, x + cw1, y + ch1, finalPaint);
                }
                if ((row < this.rows - 1) && ((GridLinesEnum.ALL == this.gridLines) || (color != this.cellColors[index + this.columns]))) {
                    c.drawLine(x, y + ch1, x + cw1, y + ch1, finalPaint);
                }
            }
            */
            }
        }
        // int twidth = mynodes[0].getBitmap().getWidth() / 2;
        //int theight = mynodes[0].getBitmap().getHeight() / 2;

    }
    public void init(final int columns, final int rows, final int[] uiColors, final int startPos, final int cellSize) {
        // if (SwingUtilities.isEventDispatchThread()) {
        initInternal(columns, rows, uiColors, startPos, cellSize);
        // }

        // else { Activity.runOnUiThread(new Runnable() { public void run() { initInternal(columns, rows, uiColors, startPos, cellSize); } }); }
    }
    private void initInternal(final int columns, final int rows, final int[] uiColors, final int startPos, final int cellSize) {
        this.uiColors = uiColors;
        this.columns = columns;
        this.rows = rows;
        this.startPos = startPos;
        this.cellColors = new int[columns * rows];
        this.cellHighlights = new boolean[this.cellColors.length];
        this.cellColorNumbers = new boolean[this.cellColors.length];
        // this.setPreferredSize(new Dimension(columns * cellSize, rows * cellSize));
    }

    /**
     * set the colors of all cells.
     */
    public void setCellColors(final int[] cellColors, final GridLinesEnum gle) {
        this.cellColors = cellColors;
        this.cellHighlights = new boolean[this.cellColors.length];
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

    public void applyColorScheme(final int[] uiColors, final GridLinesEnum gle) {
        this.uiColors = uiColors;
        this.gridLines = gle;
        this.invalidate();
    }
    public int getColor(int pos){
        return this.cellColors[pos];
    }
}
