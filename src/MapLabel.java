import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MapLabel extends JLabel {
	double old_x, old_y;
	static boolean isPassenger;
	
	public MapLabel() {
		old_x = MapPanel.scan_map.nextInt();
		old_y = MapPanel.scan_map.nextInt();
		
		try {
			MapPanel.scan_map = new Scanner(MapPanel.map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		setBounds(36, 92, 497, 417);
		setIcon(new ImageIcon(getClass().getResource("/map.png")));
		setVisible(false);
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {	
            	int x = e.getX();
            	int y = e.getY();
            	
            	if(isPassenger || (x == old_x && y == old_y)) return;
            	isPassenger = true;
            	
            	try {
					MapPanel.clearLocation();
					MapPanel.pw_map = new PrintWriter(MapPanel.map);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
            	
            	MapPanel.pw_map.println(x);
            	MapPanel.pw_map.println(y);
            	MapPanel.pw_map.flush();
            	
            	double y_length = y-old_y;
            	double x_length = x-old_x;
            	double length = Math.sqrt((x_length*x_length) + (y_length*y_length));
            	double x_stepl = x_length/length;
            	double y_stepl = y_length/length;
            	
            	Timer sound = new Timer(450, new ActionListener() {
            		public void actionPerformed(ActionEvent evt) {
                		Main.playSound("walk");
                		
                    }
            	});
            	sound.start();
            	
            	Timer t = new Timer(10, null);
            	ActionListener listener = new ActionListener() {
            		public void actionPerformed(ActionEvent evt) {
                    	if(Math.round(old_x) == x && Math.round(old_y) == y) {
                    		t.stop();
                    		isPassenger = false;
                    		FightPanel.startFight(false);
                    		
                    		sound.stop();
                    	}
                    	
                		old_x += x_stepl;
                		old_y += y_stepl;
                		
                		repaint();
                    }
            	};
            	t.addActionListener(listener);
            	t.start();
            }
        });
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.RED);
		g.fillOval((int) old_x-13, (int) old_y-13, 26, 26);
	}
}
