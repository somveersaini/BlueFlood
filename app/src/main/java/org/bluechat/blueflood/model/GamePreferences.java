package org.bluechat.blueflood.model;


import android.graphics.Color;

import java.util.prefs.Preferences;

public class GamePreferences {

    // keys in the persistent preferences store
    private static final String PREFS_NODE_NAME = "smack42ColorFill";
    private static final String PREFS_WIDTH     = "width";
    private static final String PREFS_HEIGHT    = "height";
    private static final String PREFS_NUMCOLORS = "numColors";
    private static final String PREFS_STARTPOS  = "startPos";
    private static final String PREFS_GRIDLINES = "gridLines";
    private static final String PREFS_CELLSIZE  = "cellSize";
    private static final String PREFS_COLSCHEME = "colorScheme";
    private static final String PREFS_GAMESTATE_BOARD    = "gameStateBoard";
    private static final String PREFS_GAMESTATE_SOLUTION = "gameStateSolution";
    private static final char   PREFS_GAMESTATE_SEPARATOR = ';';

    private static final Preferences PREFS = Preferences.userRoot().node(PREFS_NODE_NAME);


    // hard-coded default values
    public static final int DEFAULT_BOARD_WIDTH  = 3;
    public static final int DEFAULT_BOARD_HEIGHT = 3;
    public static final int DEFAULT_BOARD_NUM_COLORS = 6;
    public static final StartPositionEnum DEFAULT_BOARD_STARTPOS = StartPositionEnum.TOP_LEFT;
    public static final GridLinesEnum DEFAULT_UI_GRIDLINES = GridLinesEnum.NONE;
    public static final int DEFAULT_UI_CELLSIZE = 32;
    public static final int DEFAULT_UI_COLSCHEME = 0;
    private static int[][] DEFAULT_UI_COLORS = {
        { // Flood-It scheme
            Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA
        },
        { // Color Flood (Android) scheme 1
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA
        },{ // Color Flood (Android) scheme 1
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA
    },
            { // Color Flood (Android) scheme 1
                    Color.RED,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA
            },
            { // Color Flood (Android) scheme 1
                    Color.RED,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA
            },
            { // Color Flood (Android) scheme 1
                    Color.RED,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA
            },
            { // Color Flood (Android) scheme 1
                    Color.RED,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA
            },

    };

    private int width;
    private int height;
    private int numColors;
    private int startPos;
    private int uiColors;
    private int gridLines;
    private int cellSize;

    public GamePreferences() {
        this.width = DEFAULT_BOARD_WIDTH;
        this.height = DEFAULT_BOARD_HEIGHT;
        this.numColors = DEFAULT_BOARD_NUM_COLORS;
        this.startPos = DEFAULT_BOARD_STARTPOS.intValue;
        this.uiColors = DEFAULT_UI_COLSCHEME;
        this.gridLines = DEFAULT_UI_GRIDLINES.intValue;
        this.cellSize = DEFAULT_UI_CELLSIZE;
        this.loadPrefs();
        this.savePrefs();
    }

    public int getWidth() {
        return this.width;
    }
    public boolean setWidth(final int width) {
        if ((this.width != width) && (width >= 2) && (width <= 100)) { // validation
            this.width = width;
            return true; // new value has been set
        }
        return false; // value not changed
    }

    public int getHeight() {
        return this.height;
    }
    public boolean setHeight(final int height) {
        if ((this.height != height) && (height >= 2) && (height <= 100)) { // validation
            this.height = height;
            return true; // new value has been set
        }
        return false; // value not changed
    }

    public int getNumColors() {
        return this.numColors;
    }
    public boolean setNumColors(final int numColors) {
        if ((this.numColors != numColors) && (numColors >= 2) && (numColors <= 6)) { // validation
            this.numColors = numColors;
            return true; // new value has been set
        }
        return false; // value not changed
    }

    public int getStartPos() {
        return this.startPos;
    }
    public int getStartPos(int width, int height) {
        return StartPositionEnum.calculatePosition(this.startPos, width, height);
    }
    public StartPositionEnum getStartPosEnum() {
        return StartPositionEnum.valueOf(this.startPos); // may be null
    }
    public boolean setStartPos(int startPos) {
        if (null != StartPositionEnum.valueOf(startPos)) {
            this.startPos = startPos;
            return true; // new value has been set
        }
        return false; // value not changed
    }
    public boolean setStartPos(StartPositionEnum spe) {
        if (this.startPos != spe.intValue) {
            this.startPos = spe.intValue;
            return true; // new value has been set
        }
        return false; // value not changed
    }

    public int[][] getAllUiColors() {
        return DEFAULT_UI_COLORS;
    }
    public int[] getUiColors() {
        return DEFAULT_UI_COLORS[this.uiColors];
    }
    public int getUiColorsNumber() {
        return this.uiColors;
    }
    public void setUiColorsNumber(final int num) {
        if ((num >= 0) && (num < DEFAULT_UI_COLORS.length)) {
            this.uiColors = num;
        }
    }

    public int getGridLines() {
        return this.gridLines;
    }
    public GridLinesEnum getGridLinesEnum() {
        return GridLinesEnum.valueOf(this.gridLines);
    }
    public void setGridLines(final int gridLines) {
        if (null != GridLinesEnum.valueOf(gridLines)) {
            this.gridLines = gridLines;
        }
    }
    public void setGridLines(final GridLinesEnum gle) {
        this.gridLines = gle.intValue;
    }

    public int getCellSize() {
        return this.cellSize;
    }
    public boolean setCellSize(final int cellSize) {
        if ((this.cellSize != cellSize) && (cellSize >= 3) && (cellSize <= 300)) { // validation
            this.cellSize = cellSize;
            return true; // new value has been set
        }
        return false; // value not changed
    }

    private void loadPrefs() {
        this.setWidth(PREFS.getInt(PREFS_WIDTH, DEFAULT_BOARD_WIDTH));
        this.setHeight(PREFS.getInt(PREFS_HEIGHT, DEFAULT_BOARD_HEIGHT));
        this.setNumColors(PREFS.getInt(PREFS_NUMCOLORS, DEFAULT_BOARD_NUM_COLORS));
        this.setStartPos(PREFS.getInt(PREFS_STARTPOS, DEFAULT_BOARD_STARTPOS.intValue));
        this.setUiColorsNumber(PREFS.getInt(PREFS_COLSCHEME, DEFAULT_UI_COLSCHEME));
        this.setGridLines(PREFS.getInt(PREFS_GRIDLINES, DEFAULT_UI_GRIDLINES.intValue));
        this.setCellSize(PREFS.getInt(PREFS_CELLSIZE, DEFAULT_UI_CELLSIZE));
    }

    public void savePrefs() {
        PREFS.putInt(PREFS_WIDTH, this.getWidth());
        PREFS.putInt(PREFS_HEIGHT, this.getHeight());
        PREFS.putInt(PREFS_NUMCOLORS, this.getNumColors());
        PREFS.putInt(PREFS_STARTPOS, this.getStartPos());
        PREFS.putInt(PREFS_COLSCHEME, this.getUiColorsNumber());
        PREFS.putInt(PREFS_GRIDLINES, this.getGridLines());
        PREFS.putInt(PREFS_CELLSIZE, this.getCellSize());
    }

    public static void saveBoard(final Board board) {
        final StringBuilder sb = new StringBuilder()
            .append(board.getWidth())       .append(PREFS_GAMESTATE_SEPARATOR)
            .append(board.getHeight())      .append(PREFS_GAMESTATE_SEPARATOR)
            .append(board.getNumColors())   .append(PREFS_GAMESTATE_SEPARATOR)
            .append(board.getStartPos())    .append(PREFS_GAMESTATE_SEPARATOR)
            .append(board.toStringCells());
        PREFS.put(PREFS_GAMESTATE_BOARD, sb.toString());
    }

    public static Board loadBoard() {
        Board result = null;
        try {
            final String str = PREFS.get(PREFS_GAMESTATE_BOARD, "");
            final String[] strSplit = str.split(String.valueOf(PREFS_GAMESTATE_SEPARATOR));
            final int width = Integer.parseInt(strSplit[0]);
            final int height = Integer.parseInt(strSplit[1]);
            final int numColors = Integer.parseInt(strSplit[2]);
            final int startPos = Integer.parseInt(strSplit[3]);
            result = new Board(width, height, numColors, strSplit[4], startPos);
        } catch (Exception e) {
            System.out.println("loadBoard() failed: " + e.toString());
        }
        return result;
    }

    public static void saveSolution(final GameProgress progress) {
        final StringBuilder sb = new StringBuilder()
            .append(progress.getCurrentStep())  .append(PREFS_GAMESTATE_SEPARATOR)
            .append(progress.toStringSteps());
        PREFS.put(PREFS_GAMESTATE_SOLUTION, sb.toString());
    }

    public static GameProgress loadSolution(final Board board, final int startPos) {
        GameProgress result = null;
        try {
            final String str = PREFS.get(PREFS_GAMESTATE_SOLUTION, "");
            final String[] strSplit = str.split(String.valueOf(PREFS_GAMESTATE_SEPARATOR));
            final int step = Integer.parseInt(strSplit[0]);
            result = new GameProgress(board, startPos, step, strSplit[1]);
        } catch (Exception e) {
            System.out.println("loadSolution() failed: " + e.toString());
        }
        return result;
    }
}
