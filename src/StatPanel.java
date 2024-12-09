import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatPanel extends JPanel {
	static JProgressBar hpBar;
	static JLabel lblUsername, lblDamage, lblCriticalChance,
	lblAgility, weaponImg, lblWeapon, armorImg, lblArmor, accesoriesImg, lblAccesories;
	
	public StatPanel() throws FileNotFoundException {
		setBounds(37, 38, 310, 603);
		setBackground(new Color(220, 220, 220));
		MainLayout.contentPane.add(this);
		setLayout(null);
		
		createComponents();
	}
	
	private void createComponents() {
		hpBar = new JProgressBar(0, MainLayout.HP);
		hpBar.setBounds(31, 82, 236, 32);
		hpBar.setStringPainted(true);
		hpBar.setString(MainLayout.HP+"");
		hpBar.setForeground(new Color(190, 15, 15));
		hpBar.setBackground(Color.WHITE);
		hpBar.setBorderPainted(false);
		hpBar.setValue(MainLayout.HP);
		add(hpBar);
		
		lblUsername = new JLabel("Player");
		lblUsername.setBounds(31, 31, 236, 40);
		lblUsername.setFont(MainLayout.unFont);
		lblUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	MainLayout.dmg = 10000;
            }
        });
		add(lblUsername);
		
		lblDamage = new JLabel("Damage");
		lblDamage.setBounds(31, 123, 236, 25);
		lblDamage.setFont(MainLayout.statFont);
		add(lblDamage);
		
		lblCriticalChance = new JLabel("Critical chance");
		lblCriticalChance.setBounds(31, 148, 236, 25);
		lblCriticalChance.setFont(MainLayout.statFont);
		add(lblCriticalChance);
		
		lblAgility = new JLabel("Agility");
		lblAgility.setBounds(31, 172, 236, 25);
		lblAgility.setFont(MainLayout.statFont);
		add(lblAgility);
		
		weaponImg = new JLabel("");
		weaponImg.setBounds(31, 218, 108, 108);
		add(weaponImg);
		
		lblWeapon = new JLabel("");
		lblWeapon.setBounds(168, 218, 108, 108);
		lblWeapon.setFont(MainLayout.statFont2);
		add(lblWeapon);
		
		armorImg = new JLabel("");
		armorImg.setBounds(31, 337, 108, 108);
		add(armorImg);
		
		lblArmor = new JLabel("");
		lblArmor.setBounds(168, 337, 108, 108);
		lblArmor.setFont(MainLayout.statFont2);
		add(lblArmor);
		
		accesoriesImg = new JLabel("");
		accesoriesImg.setBounds(31, 456, 108, 108);
		add(accesoriesImg);
		
		lblAccesories = new JLabel("");
		lblAccesories.setBounds(168, 456, 108, 108);
		lblAccesories.setFont(MainLayout.statFont2);
		add(lblAccesories);
	}
}
