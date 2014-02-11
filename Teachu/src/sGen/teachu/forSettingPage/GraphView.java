package sGen.teachu.forSettingPage;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;

import sGen.teachu.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class GraphView extends View {
	private GraphicalView mChartView;

	XYMultipleSeriesRenderer renderer;
	LinearLayout layout_background;

	public GraphView(Context context) {
		super(context);
		layout_background = (LinearLayout) findViewById(R.id.layout_background);
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		// TODO Auto-generated method stub

		renderer.setApplyBackgroundColor(true);
		// 표 색깔
		renderer.setBackgroundColor(Color.rgb(249, 178, 51));
		// renderer.setBarWidth(40.0f);
		renderer.setMarginsColor(Color.rgb(249, 178, 51));
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		// 좌표 개수
		renderer.setXLabels(10);
		renderer.setYLabels(5);
		renderer.setShowLegend(false);
		renderer.setShowGrid(false);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.CENTER);
		// 그래프 이동
		renderer.setPanLimits(new double[] { 0, 20, 0, 40 });
		// renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
		renderer.setInScroll(false);

	}

	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		// TODO Auto-generated method stub
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		// TODO Auto-generated method stub
		// renderer.setBarWidth(100);
		renderer.setAxisTitleTextSize(30);
		renderer.setChartTitleTextSize(40);
		renderer.setLabelsTextSize(20);
		renderer.setPointSize(5f);

		// top left bottom right
		renderer.setMargins(new int[] { 20, 55, 20, 55 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	private XYMultipleSeriesDataset mDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		// TODO Auto-generated method stub
		XYMultipleSeriesDataset dataset1 = new XYMultipleSeriesDataset();
		addXYSeries(dataset1, titles, xValues, yValues, 0);
		return dataset1;
	}

	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		// TODO Auto-generated method stub

		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}

	}

	void makeGraph(Context context, LinearLayout layout) {

		String[] titles = new String[] { "ㅇㅇㅇㅇ" };
		List<double[]> x = new ArrayList<double[]>();
		x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
		int[] colors = new int[] { Color.WHITE };
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };

		renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		setChartSettings(renderer, "", "날짜", "정답률", 0, 15, 0, 30, Color.WHITE,
				Color.BLACK);

		for (int i = 0; i < length; i++) {
			XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			if (i == length - 1) {
				FillOutsideLine fill = new FillOutsideLine(
						FillOutsideLine.Type.BOUNDS_ALL);
				fill.setColor(Color.rgb(0, 255, 151));
				seriesRenderer.addFillOutsideLine(fill);
			}
			seriesRenderer.setLineWidth(3.5f);
		//y값 표시
			//	seriesRenderer.setDisplayChartValues(true);
		//	seriesRenderer.setChartValuesTextSize(40);
		}

		if (mChartView == null) {
			mChartView = ChartFactory.getLineChartView(context,
					mDataset(titles, x, values), renderer);
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		} else {
			mChartView.repaint();
		}
	}
}
