package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;

public class Log extends JFrame {

	private JPanel contentPane;
	JTextArea textPane;
	/**
	 * Create the frame.
	 */
	public Log() {
		setTitle("Log");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 400, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textPane = new JTextArea();
		textPane.setEditable(false);
		JScrollPane scroll=new JScrollPane(textPane,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);//設定為永久顯示Y軸捲動容器
		scroll.setSize(220,200);
		scroll.setLocation(0,0);
		contentPane.add(scroll, BorderLayout.CENTER);
		
	}

}
