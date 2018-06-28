package finalwork;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PopUpDialog extends JDialog{
//	private static String dialogTitle;
//	private static String dialogInformation;
//	
//	public void setTitle(String title){
//		dialogTitle = title;
//	}
//	
//	public void setInformation(String information){
//		dialogInformation = information;
//	}
	
	public PopUpDialog(Frame owner, String dialogTitle, String dialogInformation){
		// set title
		super(owner,dialogTitle,true);
		// add Label for information
		add(
				new JLabel(dialogInformation),
				BorderLayout.CENTER
				);
		// setup ok button
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				setVisible(false);
			}
		});
		
		// add OK button to southern border
		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel,BorderLayout.SOUTH);
		setSize(250,150);
	}

}
