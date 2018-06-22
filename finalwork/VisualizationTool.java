package finalwork;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;


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
				TestArea.setText("导入数据");
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

}
