package org.bluechat.blueflood;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Samsaini on 12/05/2016.
 */
public class acheivements extends Activity{
    private static final String TAG = "AchievementsActivity" ;
    public static int theme;
    public static boolean next = false;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ArrayList<String> games;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.achivements);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "Gasalt-Black.ttf");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),
                "future.otf");
        ((TextView) findViewById(R.id.achi1)).setTypeface(typeface1);
        ((TextView) findViewById(R.id.achi2)).setTypeface(typeface1);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        Settings.load(PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()));
        games = Settings.games;
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, typeface);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        List<String> ultimate = new ArrayList<String>();
        List<String> prof = new ArrayList<String>();
        List<String> dfs = new ArrayList<String>();
        List<String> revenge = new ArrayList<String>();

        String game, item;
        int boardwidth, boardheight,DFSsteps, extramoves;
        int ud,pd,dd,rd;
        ud=pd=dd=rd=0;
        String[] part;
        for (int i = 0; i < Settings.gamePlayed; i++) {
            game = games.get(i);
            if(game != null) {
                part = game.split(",");
                boardwidth = Integer.parseInt(part[0]);
                boardheight = Integer.parseInt(part[1]);
                DFSsteps = Integer.parseInt(part[2]);
                extramoves = Integer.parseInt(part[3]);
                item = " "+boardwidth + "X" + boardheight + " Board in ";
                if(extramoves < 0){
                   // if(ud == 0) ultimate.add("Board " + " DFS steps" + " Moves");
                    ud++;
                    ultimate.add(item + (DFSsteps + extramoves) + " Moves " + "( DFS " + DFSsteps +")");
                }else if(extramoves == 0){
                   // if(pd == 0) prof.add("Board " + " DFS steps");
                    pd++;
                    prof.add(item + DFSsteps + " Moves " + "( DFS " + DFSsteps +" )");
                }else if (extramoves == 1){
                    //if(dd == 0) dfs.add("Board " + " DFS steps");
                    dd++;
                    dfs.add(item + (DFSsteps + 1) + " Moves " + "( DFS " + DFSsteps +" )");
                }else {
                    //if(rd == 0) revenge.add("Board " + " DFS steps" + " Moves");
                    rd++;
                    revenge.add(item + (DFSsteps + extramoves) + " Moves " + "( DFS " + DFSsteps +" )");
                }

            }
        }

        String start = "Get in the game to see some here!!";
        String more = "Woo!! play to get more";

        if(ud==0) ultimate.add(start);
        else ultimate.add(more);
        if(pd==0) prof.add(start);
        else prof.add(more);
        if(dd==0) dfs.add(start);
        else dfs.add(more);
        if(rd==0) revenge.add(start);
        else revenge.add(more);

        // Adding child data
        listDataHeader.add("Ultimate DFS Beater       " + ud);
        listDataHeader.add("Professional DFS Beater  " + pd);
        listDataHeader.add("DFS Beater                    " + dd);
        listDataHeader.add("Revenge on DFS            " + rd);

        listDataChild.put(listDataHeader.get(0), ultimate); // Header, Child data
        listDataChild.put(listDataHeader.get(1), prof);
        listDataChild.put(listDataHeader.get(2), dfs);
        listDataChild.put(listDataHeader.get(3), revenge);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
