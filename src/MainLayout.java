import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainLayout extends JFrame {

	static int HP, maxHP, dmg, cc, agl;
	static JPanel contentPane;
	static Font statFont = new Font("statFont", 17, 19);
	static Font statFont2 = new Font("statFont2", 1, 23);
	static Font unFont = new Font("unFont", 1, 25);
	
	static StatPanel sp;
	static FightPanel fp;
	static ButtonPanel bp;
	static MapPanel mp;
	
	static JLabel bgLabel;
	
	static boolean inFight;

	public MainLayout() throws IOException {
		setResizable(false);
		setTitle("Adventure Game");
		setIconImage(new ImageIcon(getClass().getResource("/hvattack.png")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 150, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sp = new StatPanel();
		fp = new FightPanel();
		bp = new ButtonPanel();
		mp = new MapPanel();
		
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, 1264, 681);
		MainLayout.contentPane.add(bgPanel);
		bgPanel.setLayout(null);
		
		bgLabel = new JLabel("");
		bgLabel.setBounds(0, 0, 1264, 681);
		bgLabel.setIcon(new ImageIcon(getClass().getResource("/bg.jpg")));
		bgPanel.add(bgLabel);
	}
}
