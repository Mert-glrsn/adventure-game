import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MapPanel extends JPanel {
	static JLabel lblCamp;
	static MapLabel lblMap;
	static File map;
	static Scanner scan_map;
	static PrintWriter pw_map;
	
	public MapPanel() throws IOException {
		setBounds(374, 38, 570, 602);
		setBackground(new Color(220, 220, 220));
		MainLayout.contentPane.add(this);
		setLayout(null);
		
		map = new File("location.txt");
		pw_map = new PrintWriter(new FileWriter(map, true));
		scan_map = new Scanner(map);
		generateLocation();
		
		createComponents();
	}
	
	private void createComponents() {
		lblMap = new MapLabel();
		add(lblMap);
		
		lblCamp = new JLabel("");
		lblCamp.setBounds(36, 53, 497, 496);
		lblCamp.setIcon(new ImageIcon(getClass().getResource("/camp.jpg")));
		add(lblCamp);
	}
	
	public static void generateLocation() throws FileNotFoundException {
		if(map.length() == 0) {
			pw_map.println(250);
			pw_map.println(250);
		}
		
		pw_map.flush();
	}
	
	public static void clearLocation() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(map);
		writer.print("");
		writer.close();
	}
}
