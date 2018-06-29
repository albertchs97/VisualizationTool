package finalwork;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import test.ItemGenerator;




public class PlotGraph {
   
	private XYDataset xyDataSet ;
	private String chartTitle;
	private String chartXLabel;
	private String chartYLabel;
	
	public void setChartTitle(String title){
		chartTitle = title;
	}
	public void setLabel(String XLabel,String YLabel){
		chartXLabel = XLabel;
		chartYLabel = YLabel;
	}
	public void setDataset(double[] x, double[] y,String legend){
		xyDataSet = createDataset(x, y,legend);
	}
	
	public ChartPanel createChart(){
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				chartTitle,
				chartXLabel,
				chartYLabel,
				xyDataSet,
//				createDataset(),
				PlotOrientation.VERTICAL,
				true,true,false);
		ChartPanel chartPanel = new ChartPanel(xylineChart);
		final XYPlot plot = xylineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		renderer.setShapesVisible(false);
		renderer.setBaseToolTipGenerator((XYToolTipGenerator)new ItemGenerator());
		plot.setRenderer(renderer);
		return chartPanel;
	}
	
	public JFreeChart createFreeChart(){
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				chartTitle,
				chartXLabel,
				chartYLabel,
				xyDataSet,
//				createDataset(),
				PlotOrientation.VERTICAL,
				true,true,false);
		return xylineChart;
	}

	
	private XYDataset createDataset(double[] x, double[] y,String legend){
	   XYSeriesCollection dataset = new XYSeriesCollection();
	   XYSeries series = new XYSeries(legend);
	   for(int i=1;i<x.length;i++){
		   series.add(x[i],y[i]);
	   }
	   dataset.addSeries(series);
	   return dataset;
	}
	
	
	
	public static void main( String[ ] args ){
		
	}
}
