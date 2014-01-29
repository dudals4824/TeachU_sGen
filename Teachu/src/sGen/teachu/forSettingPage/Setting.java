package sGen.teachu.forSettingPage;

import sGen.teachu.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Setting extends Activity {
	LinearLayout layout_graphview, progress, test;
	ScrollView layout_background;
	GridLayout layout_correctionrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		layout_graphview = (LinearLayout) findViewById(R.id.layout_graphview);
		layout_background = (ScrollView) findViewById(R.id.layout_background);
		layout_correctionrate = (GridLayout) findViewById(R.id.layout_correctionrate);

		GraphView graphview = new GraphView(this);
		graphview.makeGraph(this, layout_graphview);
	}
}
