import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ButtonPanel extends JPanel {
	static JLabel lblMap, lblDungeon, lblMapButton, lblDungeonButton;
	
	public ButtonPanel() {
		setBounds(969, 39, 254, 601);
		MainLayout.contentPane.add(this);
		setBackground(new Color(220, 220, 220));
		setLayout(null);
		
		createComponents();
	}
	
	private void createComponents() {
		lblMap = new JLabel("MAP");
		lblMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblMap.setBounds(10, 34, 234, 34);
		lblMap.setFont(MainLayout.unFont);
		add(lblMap);
		
		lblDungeon = new JLabel("DUNGEON");
		lblDungeon.setHorizontalAlignment(SwingConstants.CENTER);
		lblDungeon.setBounds(10, 303, 234, 34);
		lblDungeon.setFont(MainLayout.unFont);
		add(lblDungeon);
		
		lblMapButton = new JLabel("");
		lblMapButton.setBounds(33, 79, 191, 196);
		lblMapButton.setIcon(new ImageIcon(getClass().getResource("/map_btn.png")));
		lblMapButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	if(MainLayout.inFight || MapLabel.isPassenger) return;
            	boolean mapOn = MapPanel.lblMap.isVisible();
            	MapPanel.lblMap.setVisible(!mapOn);
            	Main.playSound("map");
            }
        });
		add(lblMapButton);
		
		lblDungeonButton = new JLabel("");
		lblDungeonButton.setBounds(33, 363, 191, 196);
		lblDungeonButton.setIcon(new ImageIcon(getClass().getResource("/dng_btn.png")));
		lblDungeonButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	if(!MapLabel.isPassenger && !MainLayout.fp.isVisible())FightPanel.startFight(true);
            }
        });
		add(lblDungeonButton);
	}
}
