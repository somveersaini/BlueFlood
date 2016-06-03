package org.bluechat.blueflood;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.bluechat.blueflood.model.GamePreferences;
import org.bluechat.blueflood.model.GridLinesEnum;

import java.util.Arrays;
import java.util.Collection;


/**
 * Created by samsaini on 10/26/2015.
 */

public class GameAdapter extends BaseAdapter {
    private final static  String TAG = "GameAdater";
    public int[] pre;
   private Context context;
    private String HIGH_SCORE = "highscore";
    public char input='x';
    public int posx = 0;
    public int posy = 0;
    int currentScore = 0;
    int currentScore2 = 0;
    int highScore = 0;
  //  public int COLUMNS;
  //  public int ROW;
    TextView pOne;
    TextView pTwo;
    TextView pstats1;
    TextView pstats2;
    TextView highScoreTv;

    //TextView terminal;
    int turn = 0; //0 for playerOne and 1 for playerTwo
    SharedPreferences settings;
    private int[] uiColors;
    private int columns = 5, rows = 5, startPos;
    private int[] cellColors = new int[0];
    private boolean[] cellHighlights = new boolean[0];
    private boolean[] cellColorNumbers = new boolean[0];
    private GridLinesEnum gridLines;
    public GamePreferences gamePreferences;
    int height, theight;

    public GameAdapter(Context context, final int columns, final int rows, int height, final int[] uiColors, final int startPos,final int[] cellcolor, final int cellSize) {
        this.context = context;
    //    COLUMNS = column;
     //   ROW = row;
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

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
      //  int height = size.y;
        theight = height/17;
        theight = (theight * 10)/rows;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.item2, null);
            TextView text = (TextView) gridView.findViewById(R.id.grid_item2);
                text.setText("");
            text.setHeight(theight);

            int color = this.cellColors[position];

            text.setBackgroundColor(uiColors[color]);
          /*  if(pre[position] != color) {
                YoYo.with(Techniques.FlipInX)
                        .duration(100)
                        .playOn(text);
            }
            */
        } else { gridView = (View) convertView; }
        return gridView;
    }



    /**
     * set the colors of all cells.
     */
    public void setCellColors(final int[] cellColors, final GridLinesEnum gle) {
//        pre = this.cellColors;
        this.cellColors = cellColors;
        this.cellHighlights = new boolean[this.cellColors.length];
        this.gridLines = gle;
        this.notifyDataSetChanged();
    }

    public void applyColorScheme(final int[] uiColors, final GridLinesEnum gle) {
        this.uiColors = uiColors;
        this.gridLines = gle;
        this.notifyDataSetChanged();
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
        this.notifyDataSetChanged();
    }
    //gameplay
    public void addCurrentScore(int points, int turn){
        if(turn == 0) {
            currentScore += points;
        }
        else {
            currentScore2 += points;
        }
    }
    public void setHighScoreTv(){

        recordHighScore();
    }
    public void setCurrentScoreTv(int turn){

    }

    private void recordHighScore() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(HIGH_SCORE, highScore);
        editor.commit();
    }

    public void setName(String name){
        setCurrentScoreTv(0);
    }

    private int getHighScore() {
        return settings.getInt(HIGH_SCORE, 0);
    }

    @Override
    public int getCount() {
        return columns*rows;
    }

    @Override
    public String getItem(int pos) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public int getColor(int pos){
        return this.cellColors[pos];
    }
}