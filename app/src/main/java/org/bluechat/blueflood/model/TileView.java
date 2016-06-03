package org.bluechat.blueflood.model;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Samsaini on 05/17/2016.
 */
public class TileView extends TextView {
    private int position;
    public TileView(Context context) {
        super(context);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
