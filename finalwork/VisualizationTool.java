package finalwork;

import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class VisualizationTool extends JFrame{
	
	
	//添加菜单
	JMenuBar bar = new JMenuBar();	//菜单栏
	JMenu mFile = new JMenu("文件");	//菜单-文件
	JMenu mTools = new JMenu("工具");	//菜单-工具
	JMenuItem mFile_Import = new JMenuItem("导入数据");
	JMenuItem mFile_Save = new JMenuItem("保存图形");
	JMenuItem mFile_Close = new JMenuItem("关闭");
	JMenuItem mTools_HA = new JMenuItem("谐波分析");	//Harmonic Analysis
	JMenuItem mTools_PC = new JMenuItem("功率计算");	//Power Calculation
	JMenuItem mTools_FT = new JMenuItem("傅里叶变换");	//Fourier Transform
	JMenuItem mTools_SA = new JMenuItem("频谱分析");	//Spectrum Analysis
	
	Container con;	// 容器
	JPanel pan;	//面板
	JTextArea TestArea = new JTextArea("什么也没有");
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
	
	JFrame frame_HA = new JFrame("HA");
	JFrame frame_PC = new JFrame("PC");
	JFrame frame_FT = new JFrame("FT");
	JFrame frame_SA = new JFrame("SA");
	
	//frame_HA setting
	JPanel Panel_HA = new JPanel();
	//frame_PC setting
	JPanel Panel_PC = new JPanel();
	//frame_FT setting
	//JPanel_FT Panel_FT;
	//frame_SA setting
	JPanel Panel_SA = new JPanel();
	
	
	
	
	/*
	public void frame_HA_init(){
		frame_HA.setTitle("谐波分析");
		//设置窗体大小
		frame_HA.setSize(300, 300);
	}
	public void frame_PC_init(){
		frame_PC.setTitle("功率计算");
		//设置窗体大小
		frame_PC.setSize(300, 300);
	}
	public void frame_FT_init(){
		frame_FT.setTitle("傅里叶变换");
		//设置窗体大小
		frame_FT.setSize(300, 300);
		

	}
	public void frame_SA_init(){
		frame_SA.setTitle("频谱分析");
		//设置窗体大小
		frame_SA.setSize(300, 300);
	}
	*/
	
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
		mFile.add(mFile_Save);
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
		Panel1.add(MousePointer);
		Panel1.add(HorizontalZoom);
		Panel1.add(VerticalZoom);
		Panel1.add(Zoom);
		Panel1.add(Reset);
		Panel2.add(TestArea);
		
		pan.add(Panel1); 
		pan.add(Panel2);
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
					e1.printStackTrace();
				}
				
				TestArea.setText("文件名: " + FileName + 
						" 数据长度: " + String.valueOf(datalength));
			}
		});
		
		mFile_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("保存图形");
			}
		});
		
		mFile_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("关闭");
				System.exit(0);
			}
		});
		
		mTools_HA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("HA");
			}
		});
		
		mTools_PC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("PC");
			}
		});
		
		mTools_FT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("FT");
				frame_FT.setTitle("傅里叶变换");
				frame_FT.setSize(300, 300);
				JPanel_FT Panel_FT = new JPanel_FT(datalength,t,data);
				
				JPanel Panel_FT2 = new JPanel();//
				JLabel test1 = new JLabel("test1");//
				Panel_FT2.add(test1);
				frame_FT.add(Panel_FT);
				frame_FT.add(Panel_FT2);//
				Panel_FT.repaint();
				frame_FT.setVisible(true);
				
			}
			
		});
		
		mTools_SA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestArea.setText("SA");
			}
		});
	}
	
	
	public static void main(String[] args) {
		VisualizationTool VT1 = new VisualizationTool();
		
	}
	
	class JPanel_FT extends JPanel{
		ArrayList lines;
		Point2D.Double beginpoint;
		Point2D.Double endpoint;
		public JPanel_FT(int data_length,double[] t,double[] data){
			lines = new ArrayList();
			for(int i=0;i<data_length-1;i++){
				beginpoint = new Point2D.Double(t[i],data[i]);
				endpoint = new Point2D.Double(t[i+1],data[i+1]);
				Line2D line = new Line2D.Double(beginpoint,endpoint);
				lines.add(line);
			}
			System.out.println(lines.size());
		} 
		public void paint(Graphics g){  
			//super.paint(g);
			
			Graphics2D g2 = (Graphics2D)g;      // draw all lines
			g2.setColor(Color.red);
			for (int i = 0; i < lines.size(); i++)
		    	g2.draw((Line2D)lines.get(i));
			} 
	}
	
	
}


