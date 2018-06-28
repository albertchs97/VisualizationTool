package finalwork;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class VisualizationTool extends JFrame{
	
	//添加菜单
	JMenuBar bar = new JMenuBar();	//菜单栏
	JMenu mFile = new JMenu("文件");	//菜单-文件
	JMenu mTools = new JMenu("工具");	//菜单-工具
	JMenuItem mFile_Import = new JMenuItem("导入数据");
	//JMenuItem mFile_Save = new JMenuItem("保存图形");
	JMenuItem mFile_Close = new JMenuItem("关闭");
	JMenuItem mTools_HA = new JMenuItem("谐波分析");	//Harmonic Analysis
	JMenuItem mTools_PC = new JMenuItem("功率计算");	//Power Calculation
	JMenuItem mTools_FT = new JMenuItem("傅里叶变换");	//Fourier Transform
	JMenuItem mTools_SA = new JMenuItem("频谱分析");	//Spectrum Analysis
	
	Container con;	// 容器
	JPanel pan;	//面板
	//JTextArea TestArea = new JTextArea("什么也没有");
	JPanel Panel1 = new JPanel();
	JPanel Panel2 = new JPanel();
	JToggleButton MousePointer = new JToggleButton("MousePointer");
	JToggleButton HorizontalZoom = new JToggleButton("HorizontalZoom");
	JToggleButton VerticalZoom  = new JToggleButton("VerticalZoom");
	JToggleButton Zoom = new JToggleButton("Zoom");
	JButton Reset = new JButton("Reset");
	
	double[] t;
	double[] data;
	int datalength = 0;
	FileDialog fd = new FileDialog(this);
	String eFilePath = null;
	String eFile = null;
	
	double[] t_UI;
	double[] data_U;
	double[] data_I;
	int datalength_UI = 0;
	FileDialog fd_PC = new FileDialog(this);
	String UIFilePath = null;
	String UIFile = null;
	
	JTextArea P_TextArea = new JTextArea();
	JTextField TextField_HA1 = new JTextField(5);
	JTextField TextField_HA2 = new JTextField(5);
	JButton Result_HA = new JButton("显示结果");
	double HA_THD = 0;
	
	
	public VisualizationTool(){
		//设置标题栏
		this.setTitle("暂态仿真计算结果可视化工具");
		//设置窗体大小
		this.setSize(500, 500);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//添加菜单栏
		mFile.add(mFile_Import);
		//mFile.add(mFile_Save);
		mFile.add(mFile_Close);
		mTools.add(mTools_HA);
		mTools.add(mTools_PC);
		mTools.add(mTools_FT);
		mTools.add(mTools_SA);
		bar.add(mFile);
		bar.add(mTools);
		this.setJMenuBar(bar);
		con = this.getContentPane();                         
		pan = new JPanel();
		
		//pan.add(Panel1); 
		//pan.add(Panel2);
		con.add(pan);	//在容器中添加面板
		this.setVisible(true);
		
		
		mFile_Import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//设置对话框的模式是打开LOAD模式
				fd.setMode(FileDialog.LOAD);
				//设置对话框标题
				fd.setTitle("选择数据文件");
				//设置只显示excel的文件
				fd.setFile("*.xlsx");
				//配置好对话框的所有参数（包括文件夹路径，文件类型，窗口标题等）之后，再让对话框显示
				fd.setVisible(true);
				eFilePath = fd.getDirectory();
				eFile = fd.getFile();
				
				//读取数据文件
				String FileName = eFilePath + eFile;
				File file = new File(FileName);
				//读取xlsx
				boolean readFileSuccess = true;
				try{
					InputStream is = new FileInputStream(file);
					XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
					XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
					datalength = xssfSheet.getLastRowNum()+1;
					t = new double[datalength];
					data = new double[datalength];
					for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
						XSSFRow xssfRow = xssfSheet.getRow(rowNum);
						if (xssfRow != null) {
				        //读取第一列数据-时间
				        t[rowNum] = xssfRow.getCell(0).getNumericCellValue();
				        //读取第二列数据-电压/电流
				        data[rowNum] = xssfRow.getCell(1).getNumericCellValue();
						}
					}		
				}catch(Exception e1){
					readFileSuccess = false;
					e1.printStackTrace();
					PopUpDialog errorDialog = new PopUpDialog(VisualizationTool.this, "Error Dialog", "Format of this file is unvalid");
					errorDialog.setVisible(true);
				}
				if(readFileSuccess){
					PlotGraph graph1 = new PlotGraph();
					graph1.setChartTitle("V-T graph");
					graph1.setLabel("Time (s)", "Voltage (V)");
					graph1.setDataset(t, data, "data1");
					//pan.remove(TestArea);
					pan.removeAll();
					pan.setLayout(new java.awt.BorderLayout());
					pan.add(graph1.createChart(),BorderLayout.CENTER);
					pan.validate();
				}
			}
		});
		
		/*mFile_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});*/
		
		mFile_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		mTools_HA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame_HA = new JFrame();
				JLabel Label_HA1 = new JLabel("基频(Hz): ");
				JLabel Label_HA2 = new JLabel("最高谐波次数: ");
				JPanel Panel_HA1 = new JPanel();
				JPanel Panel_HA2 = new JPanel();
				JPanel Panel_HA3 = new JPanel();
				TextField_HA1.setText("");
				TextField_HA2.setText("");
				Panel_HA1.add(Label_HA1);
				Panel_HA1.add(TextField_HA1);
				Panel_HA2.add(Label_HA2);
				Panel_HA2.add(TextField_HA2);
				Panel_HA3.add(Result_HA);
				
				frame_HA.setTitle("谐波分析");
				frame_HA.setSize(300, 200);
				frame_HA.add(Panel_HA1,"North");
				frame_HA.add(Panel_HA2,"Center");
				frame_HA.add(Panel_HA3,"South");
				//frame_HA.pack();
				frame_HA.setVisible(true);
				
				Result_HA.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(TextField_HA1.getText().isEmpty() || TextField_HA2.getText().isEmpty())
							return;
						
						double f_base = Double.parseDouble(TextField_HA1.getText()); //基频
						int max_harmonic_order = Integer.parseInt(TextField_HA2.getText()); //最高谐波次数
						int datalength_addzero;
						if((datalength & -datalength) != datalength){
							//如果datalength不是2的整数次幂，在后面补零
							int m = (int)Math.ceil(Math.log(datalength)/Math.log(2));
							datalength_addzero = (int)Math.pow(2, m);
						}
						else
							datalength_addzero = datalength;
						
						Complex[] data_Complex = new Complex[datalength_addzero];
						for(int i=0;i<datalength_addzero;i++){
							if(i<datalength)
								data_Complex[i] = new Complex(data[i]);
							else
								data_Complex[i] = new Complex(0);
						}
						Complex[] data_FFT = FFT.fft(data_Complex);
						
						double[] data_FFT_Abs =new double[datalength_addzero];
						for(int i=0;i<datalength_addzero;i++){
							data_FFT_Abs[i] = data_FFT[i].Abs();
						}
						
						double Ts = t[1]-t[0]; //采样周期
						double fs = 1.0/Ts;
						double[] f = new double[datalength_addzero/2-1];
						for(int i=0;i<datalength_addzero/2-1;i++){ 
							f[i] = i*fs/datalength_addzero;
						}
						//XYSeries series_HA = new XYSeries("xySeries"); //折线图
						DefaultCategoryDataset dataset_HA_bar = new DefaultCategoryDataset();//柱形图
						
						double E_base = 0;
						double E_sum_harmonic = 0;
						for(int i=0;i<=max_harmonic_order;i++){
							double[] absdelta_temp = new double[datalength_addzero];							
							for(int j=0; j<datalength_addzero/2-1; j++){
								absdelta_temp[j] = Math.abs(f[j]-i*f_base);
							}
							double min_temp = absdelta_temp[0];
							int min_pos = 0;
							for(int k=0; k<datalength_addzero/2-1; k++) {
								if(absdelta_temp[k]<min_temp) {
									min_temp = absdelta_temp[k];
									min_pos = k;
								}
							}
							if(i==1)
								E_base = data_FFT_Abs[min_pos]*data_FFT_Abs[min_pos];
							if(i>1)
								E_sum_harmonic += data_FFT_Abs[min_pos]*data_FFT_Abs[min_pos];
							//series_HA.add(i, data_FFT_Abs[min_pos]);
							dataset_HA_bar.addValue(data_FFT_Abs[min_pos],"Harmonic",String.valueOf(i));
						}
						HA_THD = Math.sqrt(E_sum_harmonic/E_base);
						/*
						XYSeriesCollection dataset_HA = new XYSeriesCollection();
						dataset_HA.addSeries(series_HA);
						JFreeChart chart_HA = ChartFactory.createXYLineChart(
								"Harmonic Analysis ,THD = " + HA_THD*100.0 + "%", // chart title
								"Harmonic order", // x axis label
								"Amplitude", // y axis label
								dataset_HA, // data
								PlotOrientation.VERTICAL,
								false, // include legend
								false, // tooltips
								false // urls
								);
						ChartFrame chartframe_HA = new ChartFrame("Harmonic Analysis", chart_HA);
						chartframe_HA.pack();
						chartframe_HA.setVisible(true);
						*/
						DecimalFormat df = new DecimalFormat("0.00"); 
						JFreeChart chart_HA = ChartFactory.createBarChart(
								"Harmonic Analysis" + System.getProperty("line.separator") +
									"Fundamental frequncy = " + df.format(f_base) + "Hz" + 
									" ,THD = " + df.format(HA_THD*100.0) + "%", // chart title
								"Harmonic order", // x axis label
								"Amplitude", // y axis label
								dataset_HA_bar, // data
								PlotOrientation.VERTICAL,
								false, // include legend
								false, // tooltips
								false // urls
								);
						ChartFrame chartframe_HA = new ChartFrame("Harmonic Analysis", chart_HA);
						chartframe_HA.pack();
						chartframe_HA.setVisible(true);
						TextField_HA1.setText("");
						TextField_HA2.setText("");
					}
				});
			}
		});
		
		mTools_PC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//考虑到输入的电压电流可能是非正弦变化的，故这里应用定义计算其有功功率
				P_TextArea.setText("");
				JFrame frame_PC = new JFrame();
				JLabel Label_PC = new JLabel("请导入电压、电流数据");
				JButton UI_Import = new JButton("导入数据");
				JPanel Panel_PC = new JPanel();
				JPanel Panel_PC2 = new JPanel();
				JLabel Label_PC2 = new JLabel("有功功率:");
				//JTextArea P_TextArea = new JTextArea();
				Panel_PC.add(Label_PC);
				Panel_PC.add(UI_Import);
				Panel_PC2.add(Label_PC2);
				Panel_PC2.add(P_TextArea);
				frame_PC.setTitle("功率计算");
				frame_PC.setSize(300, 200);
				frame_PC.add(Panel_PC,"North");
				frame_PC.add(Panel_PC2,"Center");
				//frame_PC.pack();
				frame_PC.setVisible(true);
				UI_Import.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						//设置对话框的模式是打开LOAD模式
						fd_PC.setMode(FileDialog.LOAD);
						//设置对话框标题
						fd_PC.setTitle("选择数据文件");
						//设置只显示excel的文件
						fd_PC.setFile("*.xlsx");
						//配置好对话框的所有参数（包括文件夹路径，文件类型，窗口标题等）之后，再让对话框显示
						fd_PC.setVisible(true);
						UIFilePath = fd_PC.getDirectory();
						UIFile = fd_PC.getFile();
						//读取数据文件
						String FileName_UI = UIFilePath + UIFile;
						File file_UI = new File(FileName_UI);
						//读取xlsx
						try{
							InputStream is = new FileInputStream(file_UI);
							XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
							XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
							datalength_UI = xssfSheet.getLastRowNum()+1;
							t_UI = new double[datalength_UI];
							data_U = new double[datalength_UI];
							data_I = new double[datalength_UI];
							double P_sum = 0;
							double P_av = 0;
							for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
								XSSFRow xssfRow = xssfSheet.getRow(rowNum);
								if (xssfRow != null) {
								        //读取第一列数据-时间
								        t_UI[rowNum] = xssfRow.getCell(0).getNumericCellValue();
								        //读取第二列数据-电压
								        data_U[rowNum] = xssfRow.getCell(1).getNumericCellValue();
								        //读取第三列数据-电流
								        data_I[rowNum] = xssfRow.getCell(2).getNumericCellValue();
								        P_sum += data_U[rowNum]*data_I[rowNum];
								    }
								}	
							P_av = P_sum/(t_UI[datalength_UI-1]-t_UI[0]);
							String P_avString = String.format("%.3f",P_av);
							P_TextArea.setText(P_avString);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						
					}
				});
				
			}
		});
		
		mTools_FT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int datalength_addzero;
				if((datalength & -datalength) != datalength){
					//如果datalength不是2的整数次幂，在后面补零
					int m = (int)Math.ceil(Math.log(datalength)/Math.log(2));
					datalength_addzero = (int)Math.pow(2, m);
				}
				else
					datalength_addzero = datalength;
				
				Complex[] data_Complex = new Complex[datalength_addzero];
				for(int i=0;i<datalength_addzero;i++){
					if(i<datalength)
						data_Complex[i] = new Complex(data[i]);
					else
						data_Complex[i] = new Complex(0);
				}
				Complex[] data_FFT = FFT.fft(data_Complex);
				
				double[] data_FFT_Abs =new double[datalength_addzero];
				for(int i=0;i<datalength_addzero;i++){
					data_FFT_Abs[i] = data_FFT[i].Abs();
				}
				
				XYSeries series_FT = new XYSeries("xySeries");
				for (int i=0;i<datalength_addzero;i++) {
					series_FT.add(i, data_FFT_Abs[i]);
				}
				
				XYSeriesCollection dataset_FT = new XYSeriesCollection();
				dataset_FT.addSeries(series_FT);
				JFreeChart chart_FT = ChartFactory.createXYLineChart(
						"FFT (N = "+datalength_addzero+")", // chart title
						"k", // x axis label
						"Amplitude", // y axis label
						dataset_FT, // data
						PlotOrientation.VERTICAL,
						false, // include legend
						false, // tooltips
						false // urls
						);

				ChartFrame frame_FT = new ChartFrame("FFT", chart_FT);
				frame_FT.pack();
				frame_FT.setVisible(true);
			}
			
		});
		
		mTools_SA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int datalength_addzero;
				if((datalength & -datalength) != datalength){
					//如果datalength不是2的整数次幂，在后面补零
					int m = (int)Math.ceil(Math.log(datalength)/Math.log(2));
					datalength_addzero = (int)Math.pow(2, m);
				}
				else
					datalength_addzero = datalength;
				
				Complex[] data_Complex = new Complex[datalength_addzero];
				for(int i=0;i<datalength_addzero;i++){
					if(i<datalength)
						data_Complex[i] = new Complex(data[i]);
					else
						data_Complex[i] = new Complex(0);
				}
				Complex[] data_FFT = FFT.fft(data_Complex);
				
				double[] data_FFT_Abs =new double[datalength_addzero];
				for(int i=0;i<datalength_addzero;i++){
					data_FFT_Abs[i] = data_FFT[i].Abs();
				}
				
				double Ts = t[1]-t[0]; //采样周期
				double fs = 1.0/Ts;
				double[] f = new double[datalength_addzero/2-1];
				for(int i=0;i<datalength_addzero/2-1;i++){
					f[i] = i*fs/datalength_addzero;
				}
				
				XYSeries series_SA = new XYSeries("xySeries");
				for (int i=0;i<datalength_addzero/2-1;i++) {
					series_SA.add(f[i], data_FFT_Abs[i]);
				}
				
				XYSeriesCollection dataset_SA = new XYSeriesCollection();
				dataset_SA.addSeries(series_SA);
				JFreeChart chart_SA = ChartFactory.createXYLineChart(
						"Spectrum Analysis", // chart title
						"f/Hz", // x axis label
						"Amplitude", // y axis label
						dataset_SA, // data
						PlotOrientation.VERTICAL,
						false, // include legend
						false, // tooltips
						false // urls
						);

				ChartFrame frame_SA = new ChartFrame("Spectrum Analysis", chart_SA);
				frame_SA.pack();
				frame_SA.setVisible(true);
			}
		});
	}
	
	
	public static void main(String[] args) {
		VisualizationTool VT1 = new VisualizationTool();
		
	}
}

class FFT {

	public static Complex[] fft(Complex[] x) {
		int n = x.length;
		// 因为exp(-2i*n*PI)=1，n=1时递归原点
		if (n == 1){
			return x;
		}
		// 提取下标为偶数的原始信号值进行递归fft计算
		Complex[] even = new Complex[n / 2];
		for (int k = 0; k < n / 2; k++) {
			even[k] = x[2 * k];
		}
		Complex[] evenValue = fft(even);

		// 提取下标为奇数的原始信号值进行fft计算
		// 节约内存
		Complex[] odd = even;
		for (int k = 0; k < n / 2; k++) {
			odd[k] = x[2 * k + 1];
		}
		Complex[] oddValue = fft(odd);

		// 偶数+奇数
		Complex[] result = new Complex[n];
		for (int k = 0; k < n / 2; k++) {
			// 使用欧拉公式e^(-i*2pi*k/N) = cos(-2pi*k/N) + i*sin(-2pi*k/N)
			double p = -2 * k * Math.PI / n;
			Complex m = new Complex(Math.cos(p), Math.sin(p));
			result[k] = evenValue[k].plus(m.multiple(oddValue[k]));
			// exp(-2*(k+n/2)*PI/n) 相当于 -exp(-2*k*PI/n)，其中exp(-n*PI)=-1(欧拉公式);
			result[k + n / 2] = evenValue[k].minus(m.multiple(oddValue[k]));
		}
		return result;
	}
}

class Complex{
	double Re;
	double Im;
	public Complex(double re,double im){
		this.Re = re;
		this.Im = im;
	}
	public Complex(double re){
		this.Re = re;
		this.Im = 0;
	}
	Complex multiple(Complex c1){
		Complex c = new Complex(this.Re*c1.Re-this.Im*c1.Im,this.Re*c1.Im+c1.Re*this.Im);
		return c;
	}
	Complex conjugate(){
		Complex c = new Complex(this.Re,-this.Im);
		return c;
	}
	Complex plus(Complex c1){
		Complex c = new Complex(this.Re + c1.Re, this.Im + c1.Im);
		return c;
	}
	Complex minus(Complex c1){
		Complex c = new Complex(this.Re - c1.Re, this.Im - c1.Im);
		return c;
	}
	public double Abs(){
		return Math.sqrt(this.Re*this.Re+this.Im*this.Im);
	}
}
