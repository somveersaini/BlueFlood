package org.bluechat.blueflood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.bluechat.blueflood.model.GameProgress;
import org.bluechat.blueflood.model.GameState;
import org.bluechat.blueflood.model.GridLinesEnum;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    public static int HEIGHT;
    public static int WIDTH;
    public static Context context;
    GridView grid;
    GameAdapter adapter;
    ProgressDialog progressDialog;
    boolean isContinue = false;

//    GridLayout gridLayout;
//    int gridColumn;
//    int gridRow;
//    int[] colors;
//    int[] uicolors;

    EditText etwidth;
    EditText etheight;
    private GameState gameState;
    public int numprogress = 0;


    public String name = "somveer";
    private SharedPreferences settings;
    boolean initialize;
    TextView tv ;
    boolean once = true;
    Typeface typeface;
    Typeface typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;


       // this.preferencesController = new PreferencesController(this, this.gameState, this.mainView, progname, version, author);
        context=getApplicationContext();
        setContentView(R.layout.activity_main);
        grid = (GridView) findViewById(R.id.gridView);
       // gridLayout = (GridLayout) findViewById(R.id.gridlayout);
//        gameView = (GameView) findViewById(R.id.gameView);
        etheight = (EditText) findViewById(R.id.numrow);
        etwidth = (EditText) findViewById(R.id.numcol);
        tv = (TextView) findViewById(R.id.top);
        typeface = Typeface.createFromAsset(getAssets(),
                "Gasalt-Black.ttf");
        typeface1 = Typeface.createFromAsset(getAssets(),
                "future.otf");
        tv.setTypeface(typeface);
        ((Button) findViewById(R.id.previous)).setTypeface(typeface);
        ((Button) findViewById(R.id.next)).setTypeface(typeface);

        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Settings.load(settings);
        initialize = true;


    }


    void init(){

       // progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = ProgressDialog.show(this, "Blue Flood",
                "Loading...", true);

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // do the thing that takes a long time
                int time = (int) Math.abs(System.currentTimeMillis());
                if(initialize) {
                    gameState = new GameState();
                    gameState.addPropertyChangeListener(new GameStatePropertyChangeListener());
                    gameState.setAutoRunSolver(true);
                    initialize = false;
                }
                gameState.getPreferences().setWidth(Settings.column);
                gameState.getPreferences().setHeight(Settings.row);
                gameState.getPreferences().savePrefs();
                time = (int) Math.abs(System.currentTimeMillis()) - time;
                System.out.println("initialization in " + time);
                gameState.setNewRandomBoard();
                time = (int)Math.abs(System.currentTimeMillis()) - time;
                System.out.println("NewRandomBord in " + time/1000);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(progressDialog != null) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        grid.setNumColumns(gameState.getPreferences().getWidth());
                        adapter = new GameAdapter(MainActivity.this,gameState.getPreferences().getWidth(), gameState.getPreferences().getHeight(),HEIGHT,
                                gameState.getPreferences().getUiColors(),
                                gameState.getStartPos(),
                                gameState.getSelectedProgress().getColors(),
                                gameState.getPreferences().getCellSize());
                        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v,
                                                    int position, long id) {
                                Log.d("clicked", " "+ position);
                                // sp.play(btnbtn, 1,1,1, 0,1);
                                input(position);
                            }
                        });
                        grid.setAdapter(adapter);
                        grid.setEnabled(true);
                        numprogress = 0;

                        setControlButton();
                        once = true;
                        repaintBoardPanel();


                    }
                });
            }
        }).start();
        if(progressDialog != null){
           // progressDialog.dismiss();
        }



        //gridlayout
//        gridColumn = this.gameState.getPreferences().getWidth();
//        gridRow = this.gameState.getPreferences().getHeight();
//        colors = this.gameState.getSelectedProgress().getColors();
//        uicolors = this.gameState.getPreferences().getUiColors();
//
//        gridLayout.setColumnCount(gridColumn);
//        gridLayout.setRowCount(gridRow);
//
//        for (int i = 0; i < gridRow; i++){
//            for (int j = 0; j < gridColumn; j++) {
//                TileView tile = new TileView(this);
//                final int position = (i * gridRow) + j;
//                tile.setPosition(position);
//                tile.setBackgroundColor(uicolors[colors[position]]);
//                tile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        input(position);
//                    }
//                });
//                gridLayout.addView(tile);
//            }
//        }
//        gridLayout.invalidate();
        //game view

//        gameView.init(8,8,this.gameState.getPreferences().getUiColors(),this.gameState.getStartPos(),
//                this.gameState.getPreferences().getCellSize());
//        gameView.invalidate();


    }
    protected void actionNewBoard() {
      //  progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = ProgressDialog.show(this, "Blue Flood",
                "Generating new Board", true);


        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // do the thing that takes a long time

                gameState.setNewRandomBoard();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        internalUpdateBoardColors();
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();


        //gridLayout.invalidate();
    }
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            YoYo.with(Techniques.FlipInX)
                    .duration(700)
                    .playOn(findViewById(R.id.gridView));
        }
    }
    public void onResume(){
        super.onResume();
        if(initialize || Settings.column != gameState.getPreferences().getWidth() || Settings.row != gameState.getPreferences().getHeight()){
           init();
        }
        YoYo.with(Techniques.FlipInX)
                .duration(700)
                .playOn(findViewById(R.id.gridView));
    }
    public void repaintBoardPanel() {
        Log.d("board", "  repaint");
        adapter.setCellColors(this.gameState.getSelectedProgress().getColors(), this.gameState.getPreferences().getGridLinesEnum());
        System.out.println(Arrays.toString(this.gameState.getSelectedProgress().getColors()));

        adapter.notifyDataSetChanged();
        grid.setAdapter(adapter);

        //gridlayout
//        colors = this.gameState.getSelectedProgress().getColors();
//        for (int i = 0; i < gridRow; i++){
//            for (int j = 0; j < gridColumn; j++) {
//                final int position = (i * gridRow) + j;
//                TileView tile = (TileView) gridLayout.getChildAt(position);
//                tile.setBackgroundColor(uicolors[colors[position]]);
//            }
//        }

//        gameView.setCellColors(this.gameState.getSelectedProgress().getColors(), this.gameState.getPreferences().getGridLinesEnum());
//        gameView.invalidate();
        if(once) {
            tv.setText("Flood the board from TOP Left");
            once = false;
        }else {
            if (numprogress == 0) {
                tv.setText("MOVES   " + this.gameState.getSelectedProgress().getCurrentStep() + "/" + (this.gameState.getMinsteps() + 1));
                int extraMoves = this.gameState.getSelectedProgress().getCurrentStep() - (this.gameState.getMinsteps()) ;
                if (extraMoves > 0 || this.gameState.getSelectedProgress().isFinished()){
                    dfsMovesLimit(extraMoves);
                }
            } else {
                tv.setText("MOVES   " + this.gameState.getSelectedProgress().getCurrentStep() + "/" + this.gameState.getSelectedProgress().getTotalSteps());
            }
        }
    }

    private void dfsMovesLimit(int extraMoves) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (this.gameState.getSelectedProgress().isFinished()){
            //prompt for new game
            View view = inflater.inflate(R.layout.finished, null);
            TextView tv1 = (TextView) view.findViewById(R.id.gamename);
            tv1.setTypeface(typeface1);
            TextView line = (TextView) view.findViewById(R.id.finishline);
            line.setTypeface(typeface);
            TextView desc = (TextView) view.findViewById(R.id.finishdescription);
            desc.setTypeface(typeface);
            isContinue = false;
            String game = gameState.getBoard().getWidth()+","+gameState.getBoard().getHeight()+","+gameState.getMinsteps()+","+extraMoves;
            Settings.addGame(settings,game);
            Settings.winSound();
            if(extraMoves < 0){
                //beated dfs
                line.setText(R.string.extramovelessthan0line);
                desc.setText("Superb!!\nKonsi chakki ka atta khate ho yrr.\nYou nailed DFS by " + (-extraMoves) + " Moves.");
            }else if(extraMoves == 0){
                //dfs equvivalent
                line.setText(R.string.extramove0line);
                desc.setText(R.string.extramove0desc);
            }else if(extraMoves == 1){
                //beated current game
                line.setText(R.string.extramove1line);
                desc.setText(R.string.extramove1desc);
            }else{
                //used more moves
                line.setText(R.string.extramovegreaterthan1line);
                desc.setText("Yeah!!\n Finally you have taken your revenge against DFS starategy.\nCan\'t fail t try although it took " +  extraMoves + " Moves.");
            }
            alertDialogBuilder.setView(view);
            final AlertDialog alertDialog = alertDialogBuilder.create();
          //  alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Button finishnewgame = (Button) view.findViewById(R.id.finishnewgame);
            finishnewgame.setTypeface(typeface);

            finishnewgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //save the achiewments
                    Settings.buttonSound();
                    numprogress = 0;
                    actionNewBoard();
                    alertDialog.cancel();
                }
            });
            //  alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
            alertDialog.show();
        }else {
            if (!isContinue) {
                Settings.contiSound();
                View view = inflater.inflate(R.layout.iscontinue, null);
                //prompt for continue or new game
                TextView tv1 = (TextView) view.findViewById(R.id.gamename);
                tv1.setTypeface(typeface1);
                TextView line = (TextView) view.findViewById(R.id.finishline);
                line.setTypeface(typeface);
                TextView desc = (TextView) view.findViewById(R.id.finishdescription);
                desc.setTypeface(typeface);


                line.setText(R.string.xtraline);
                desc.setText(R.string.xtradesc);
                Button newgame = (Button) view.findViewById(R.id.contnewgame);
                Button cont = (Button) view.findViewById(R.id.cont);
                newgame.setTypeface(typeface);
                cont.setTypeface(typeface);

                alertDialogBuilder.setView(view);
                final AlertDialog alertDialog = alertDialogBuilder.create();
            //    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                newgame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Settings.buttonSound();
                        numprogress = 0;
                        actionNewBoard();
                        alertDialog.cancel();
                    }
                });
                cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Settings.buttonSound();
                        isContinue = true;
                        alertDialog.cancel();
                    }
                });
                //  alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
                alertDialog.show();
            }
        }

    }

    protected int[] getUiColors() {
        return this.gameState.getPreferences().getUiColors();
    }
    public void input( int position){

        if (this.gameState.isUserProgress() && this.gameState.getSelectedProgress().isFloodNeighborCell(position)) {
            this.actionAddStep(adapter.getColor(position));
            adapter.notifyDataSetChanged();
            //gridlayout
           // this.actionAddStep(colors[position]);
        }
    }
    public void actionUpdateBoardColors() {
        this.repaintBoardPanel();
    }
    /**
     * repaint board using this UI color scheme.
     */
    public void actionRepaintBoardUiColors(final int[] uiColors, final GridLinesEnum gle) {
        //adapter.applyColorScheme(uiColors, gle);
        //gameView.applyColorScheme(uiColors, gle);
    }
    public void actionHightlightFloodNeighborCells(final int color) {
        final Collection<Integer> neighborCells = this.gameState.getSelectedProgress().getFloodNeighborCells(color);
       // adapter.highlightCells(neighborCells);
       // gameView.highlightCells(neighborCells);
    }
    private void internalUpdateBoardColors() {
        this.actionUpdateBoardColors();
       // actionRepaintBoardUiColors();
        if (!this.gameState.isUserProgress()) {
            final Integer nextColor = this.gameState.getSelectedProgress().getNextColor();
            if (null != nextColor) {
                this.actionHightlightFloodNeighborCells(nextColor.intValue());
            }
        }
    }

    /**
     * add a color step to gamestate.
     * @param color
     */
    protected void actionAddStep(final int color) {
        final boolean isAdded = this.gameState.getSelectedProgress().addStep(color);
       // Log.d("color", "  step");
        Settings.gameSound();
        if (isAdded) {
           // Log.d("color", "  added to");
            this.internalUpdateBoardColors();
        }
    }

    /**
     * undo a color step in gamestate.
     */
    protected void actionUndoStep() {
        final boolean isDone = this.gameState.getSelectedProgress().undoStep();
        if (isDone) {
            this.internalUpdateBoardColors();
        }
    }

    /**
     * redo a color step in gamestate.
     */
    protected void actionRedoStep() {
        final boolean isDone = this.gameState.getSelectedProgress().redoStep();
        if (isDone) {
            this.internalUpdateBoardColors();
        }
    }

    /**
     * make a new board with random cell colors.
     */


    /**
     * show preferences dialog.
     */
    protected void actionPreferences() {
        //this.preferencesController.showDialog();
    }

    /**
     * apply updated preferences.
     */
    protected void actionUpdatedPrefs(final boolean isNewBoard, final boolean isNewSize) {
        if (isNewBoard) {
            this.gameState.setNewRandomBoard();
        }
        this.init();
        if (isNewSize) {
          //  this.mainView.update();
        }
        this.internalUpdateBoardColors();
    }


    public void setControlButton(){
        int color[] = this.gameState.getPreferences().getUiColors();
//        Button button = (Button) findViewById(R.id.c1);
//        button.setBackgroundColor(color[0]);
//        button = (Button) findViewById(R.id.c2);
//        button.setBackgroundColor(color[1]);
//        button = (Button) findViewById(R.id.c3);
//        button.setBackgroundColor(color[2]);
//        button = (Button) findViewById(R.id.c4);
//        button.setBackgroundColor(color[3]);
//        button = (Button) findViewById(R.id.c5);
//        button.setBackgroundColor(color[4]);
//        button = (Button) findViewById(R.id.c6);
//        button.setBackgroundColor(color[5]);

    }

    /**
     * select a game progress in gamestate.
     * @param numProgress number of the selected solution (0 == user solution, other = solver solutions)
     */
    protected void actionSelectGameProgress(final int numProgress) {
        final boolean isDone = this.gameState.selectGameProgress(numProgress);
        if (isDone) {
            this.internalUpdateBoardColors();
        }
    }

    public void buttonhandler(View view) {
        int id = view.getId();
        Settings.buttonSound();
        switch(id){
            case R.id.newgame:
                numprogress = 0;
                this.actionNewBoard();
                break;
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.undo:
                this.actionUndoStep();
                break;
            case R.id.redo:
                this.actionRedoStep();
                break;
            case R.id.previous:
                YoYo.with(Techniques.SlideOutRight)
                        .duration(300)
                        .playOn(findViewById(R.id.gridView));
                    numprogress = 0;
                    actionSelectGameProgress(numprogress);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(500)
                        .playOn(findViewById(R.id.gridView));
                break;
            case R.id.next:
                YoYo.with(Techniques.SlideOutLeft)
                        .duration(300)
                        .playOn(findViewById(R.id.gridView));
                numprogress++;
                actionSelectGameProgress(numprogress);
                YoYo.with(Techniques.SlideInRight)
                        .duration(500)
                        .playOn(findViewById(R.id.gridView));
                break;
            default:
                break;
        }
    }

    public void buttoninput(View view) {
        int id = view.getId();
        switch(id){
            case R.id.c1:
                userButtonColor(0);
                break;
            case R.id.c2:
                userButtonColor(1);
                break;
            case R.id.c3:
                userButtonColor(2);
                break;
            case R.id.c4:
                userButtonColor(3);
                break;
            case R.id.c5:
                userButtonColor(4);
                break;
            case R.id.c6:
                userButtonColor(5);
                break;
            default:
                break;
        }
    }

    /**
     * this class handles the Property Change Events coming from GameState
     * when the solver(s) running in a worker thread present their solutions.
     */
    private class GameStatePropertyChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (GameState.PROPERTY_PROGRESS_SOLUTIONS.equals(evt.getPropertyName())) {
                final GameProgress[] oldValue = (GameProgress[]) evt.getOldValue();
                final GameProgress[] newValue = (GameProgress[]) evt.getNewValue();
                if (0 == newValue.length) {
                   // MainActivity.this.controlController.actionClearSolverResults();
                } else {
                    System.out.println("propertyChange add " + (newValue.length - oldValue.length));
                   //this.actionAddSolverResult(newValue[newValue.length - 1]);
                }
            }
        }
    }

    /**
     * called by ControlPanel when user clicks on button "New"
     */
    protected void userButtonNew() {
        this.actionNewBoard();
    }

    /**
     * called by ControlPanel when user clicks on button "Settings"
     */
    protected void userButtonPrefs() {
        this.actionPreferences();
    }

    /**
     * called by ControlPanel when user clicks on button "Undo"
     */
    protected void userButtonUndo() {
        this.actionUndoStep();
    }

    /**
     * called by ControlPanel when user clicks on button "Redo"
     */
    protected void userButtonRedo() {
        this.actionRedoStep();
    }

    /**
     * called by ControlPanel when user clicks on one of the solution RadioButtons.
     * @param numSolution number of the selected solution (0 == user solution, other = solver solutions)
     */
    protected void userButtonSolution(final int numSolution) {
        this.actionSelectGameProgress(numSolution);
    }

    /**
     * called by ControlPanel when user clicks on a color button.
     * @param color number
     */
    protected void userButtonColor(final int color) {
        if (this.gameState.isUserProgress()) {
            this.actionAddStep(color);
        }
    }

    /**
     * remove all solver results from control panel.
     */
    public void actionClearSolverResults() {
//        System.out.println("ControlController.actionClearSolverResults");
       // this.controlPanel.clearSolverResults();
    }

    /**
     * add this solver result to control panel.
     * @param gameProgress solver result
     */
    public void actionAddSolverResult(final GameProgress gameProgress) {
//        System.out.println("ControlController.actionAddSolverResult " + gameProgress);
        //this.controlPanel.addSolverResult(gameProgress.getTotalSteps() + " " + gameProgress.getName());
    }



//    public void settings() {
//        LayoutInflater inflater = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.settings, null);
//
//        etwidth = (EditText) view.findViewById(R.id.numcol);
//        etheight = (EditText) view.findViewById(R.id.numrow);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setView(view);
//        final AlertDialog alertDialog1 = alertDialogBuilder.create();
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog1.cancel();
//
//            }
//        });
//
//        //alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
//        alertDialog1.show();
//
//    }

//    public void buttonsettings(View view) {
//
//
//        Log.d(etwidth.getText().toString(), etwidth.getText().toString());
//        int col = Integer.parseInt(etwidth.getText().toString());
//        int row = Integer.parseInt(etheight.getText().toString());
//        userPrefsOK(col, row);
//    }

    /**
     * called by PreferencesDialog when user pressed the "OK" button.
     */
    protected void userPrefsOK(
            final int width,
            final int height
           /* final int numColors,
            final StartPositionEnum spe,
            final GridLinesEnum gle,
            final int uiColorsNumber,
            final int cellSize */) {
        boolean isNewBoard = this.gameState.getPreferences().setWidth(width);
        isNewBoard |= this.gameState.getPreferences().setHeight(height);
        boolean isNewSize = isNewBoard;
       // isNewBoard |= this.gameState.getPreferences().setNumColors(numColors);
       // isNewBoard |= this.gameState.getPreferences().setStartPos(spe);
       // isNewSize |= this.gameState.getPreferences().setCellSize(cellSize);
      //  this.gameState.getPreferences().setGridLines(gle);
      //  this.gameState.getPreferences().setUiColorsNumber(uiColorsNumber);
        this.gameState.getPreferences().savePrefs();
        this.actionUpdatedPrefs(isNewBoard, isNewSize);
        init();
       // new LoadGame().execute();
    }

    private class LoadGame extends AsyncTask<Void, Void, Long>{
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);
        String typeStatus;


        @Override
        protected Long doInBackground(Void... params) {
            init();
            return null;
        }

        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("Loading..");
            //show dialog
            asyncDialog.show();
            super.onPreExecute();
        }
        protected void onPostExecute(Long result) {
            asyncDialog.dismiss();
        }


    }
}
