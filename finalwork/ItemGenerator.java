package finalwork;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.data.xy.XYDataset;

public class ItemGenerator implements XYToolTipGenerator {
	public ItemGenerator(){
	}

	@Override
	public String generateToolTip(XYDataset dataset, int series, int category) {

		
		//Number value = dataset.getValue(series, category);
		double x = (double)dataset.getXValue(series, category);
		double y = (double)dataset.getYValue(series, category);
		
		String message = String.format("(%.3f , %.3f)", x,y);
		return message;
	}
}