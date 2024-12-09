import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class FightPanel extends JPanel {
	static int enemyHP, enemyMaxHP, enemyDmg;
	static String enemyName;
	
	static JProgressBar enemyHpBar;
	static JLabel lblEnemyName, enemyImg, log;
	static JButton attackBtn, heavyBtn, evadeBtn;

	static File enemyNames = new File("enemyNames.txt");
	static Scanner nameScan;
	static Random rand = new Random();
	
	public FightPanel() {
		setBounds(374, 38, 570, 602);
		setBackground(new Color(220, 220, 220));
		MainLayout.contentPane.add(this);
		setVisible(false);
		setLayout(null);
		
		createComponents();
	}
	
	private void createComponents() {
		lblEnemyName = new JLabel("Enemy Name");
		lblEnemyName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemyName.setBounds(10, 11, 510, 41);
		lblEnemyName.setFont(MainLayout.unFont);
		add(lblEnemyName);
		
		enemyImg = new JLabel(new ImageIcon(getClass().getResource("/e1.png")), JLabel.CENTER);
		enemyImg.setHorizontalAlignment(SwingConstants.CENTER);
		enemyImg.setBounds(52, 55, 452, 250);
		add(enemyImg);
		
		enemyHpBar = new JProgressBar();
		enemyHpBar.setBounds(33, 315, 501, 27);
		enemyHpBar.setStringPainted(true);
		enemyHpBar.setString(MainLayout.HP+"");
		enemyHpBar.setForeground(new Color(190, 15, 15));
		enemyHpBar.setBackground(Color.WHITE);
		enemyHpBar.setBorderPainted(false);
		enemyHpBar.setValue(MainLayout.HP);
		add(enemyHpBar);
		
		attackBtn = new JButton("  Attack", new ImageIcon(getClass().getResource("/attack.png")));
		attackBtn.setBounds(106, 368, 332, 41);
		attackBtn.setBorderPainted(false);
		attackBtn.setFocusPainted(false);
		attackBtn.setBackground(new Color(255, 255, 255));
		attackBtn.setForeground(new Color(25, 25, 25));
		attackBtn.setFont(new Font("statFont", 1, 17));
		attackBtn.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons();
				
				int curDmg = MainLayout.dmg + rand.nextInt(300)-150; 
				updateEnemyHP(enemyHP-curDmg);
				
				Main.playSound("hit");
				
				if(enemyHP <= 0) {
					try {
						win();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					return;
				}
				
				
				int enmDmg = enemyAttack();
				
				if(MainLayout.HP <= 0) {
					endFight();
					JOptionPane.showMessageDialog(null, "You have lost the fight!",
							"Oops!", JOptionPane.ERROR_MESSAGE);
					if (enemyName.equalsIgnoreCase("Ender Beast"))
						JOptionPane.showMessageDialog(null, "Come back later when you get stronger!",
								"You fool!", JOptionPane.PLAIN_MESSAGE);
					return;
				}

				log.setText("<html>You have dealt <b>-" + curDmg + " DMG</b> on <u>" + enemyName + "</u>.<br><u>"
				+ enemyName + "</u> has dealt <b>-" + enmDmg + " DMG</b> on you!</html>");
			}
		});
		add(attackBtn);
		
		heavyBtn = new JButton("  Heavy Attack", new ImageIcon(getClass().getResource("/hvattack.png")));
		heavyBtn.setBounds(106, 420, 332, 41);
		heavyBtn.setBorderPainted(false);
		heavyBtn.setFocusPainted(false);
		heavyBtn.setBackground(new Color(255, 255, 255));
		heavyBtn.setForeground(new Color(25, 25, 25));
		heavyBtn.setFont(new Font("statFont", 1, 17));
		heavyBtn.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons();
				
				String msg = "";
				
				int curDmg = (MainLayout.dmg + rand.nextInt(50)-25)*3; 
				if (rand.nextInt(100)+1 < (MainLayout.cc)) {
					updateEnemyHP(enemyHP-curDmg);
					
					msg = "You have dealt <u><b>CRIT -" + curDmg + " DMG</b></u> on <u>" + enemyName + "</u>.";
					
					Main.playSound("crit");
				}
				else {
					Main.playSound("dmg");
					msg = "<u><b>You missed!</b></u>";
				}
				
				if(enemyHP <= 0) {
					try {
						win();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					return;
				}
				
				int enmDmg = enemyAttack();
				
				if(MainLayout.HP <= 0) {
					endFight();
					JOptionPane.showMessageDialog(null, "You have lost the fight!",
							"Oops!", JOptionPane.ERROR_MESSAGE);
					if (enemyName.equalsIgnoreCase("Ender Beast"))
						JOptionPane.showMessageDialog(null, "Come back later when you get stronger!",
								"You fool!", JOptionPane.PLAIN_MESSAGE);
					return;
				}

				log.setText("<html>" + msg + "<br><u>"
				+ enemyName + "</u> has dealt <b>-" + enmDmg + " DMG</b> on you!</html>");
			}
		});
		add(heavyBtn);
		
		evadeBtn = new JButton("  Evade", new ImageIcon(getClass().getResource("/run.png")));
		evadeBtn.setBounds(106, 472, 332, 41);
		evadeBtn.setBorderPainted(false);
		evadeBtn.setFocusPainted(false);
		evadeBtn.setBackground(new Color(255, 255, 255));
		evadeBtn.setForeground(new Color(25, 25, 25));
		evadeBtn.setFont(new Font("statFont", 1, 17));
		evadeBtn.addActionListener(new ActionListener( ) {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons();
				
				if (rand.nextInt(100)+1 < (MainLayout.agl)) {
					Main.updateHP(MainLayout.HP + MainLayout.maxHP*4/10);
					log.setText("<html>You evaded the next attack<br>and healed <b>" + MainLayout.maxHP*3/10 + "</b> HP!</html>");
					Main.playSound("evade");
				}
				
				else {
					int enmDmg = enemyAttack();
					log.setText("<html><u><b>You failed evading!</b></u><br> " + enemyName + "</u> has dealt <b>-" + enmDmg + " DMG</b> on you!</html>");
					Main.playSound("dmg");
					if(MainLayout.HP <= 0) {
						endFight();
						JOptionPane.showMessageDialog(null, "You have lost the fight!",
								"Oops!", JOptionPane.ERROR_MESSAGE);
						if (enemyName.equalsIgnoreCase("Ender Beast"))
							JOptionPane.showMessageDialog(null, "Come back later when you get stronger!",
									"You fool!", JOptionPane.PLAIN_MESSAGE);
						return;
					}
				}
			}
		});
		add(evadeBtn);
		
		log = new JLabel("", JLabel.CENTER);
		log.setBounds(40, 525, 480, 52);
		log.setFont(MainLayout.statFont);
		add(log);
	}
	
	public static void startFight(boolean isBoss) {
		MainLayout.inFight = true;
		
		if(!isBoss) {
			Main.playSound("enemy");
			generateEnemy();
		}
		else {
			Main.playSound("boss");
			MainLayout.bgLabel.setIcon(new ImageIcon(FightPanel.class.getResource("/boss.jpg")));
			
			enemyMaxHP = 10000;
			enemyHP = enemyMaxHP;
			enemyDmg = 1500;
			updateEnemyHP(enemyHP);
			
			enemyName = "Ender Beast";
			lblEnemyName.setText("Ender Beast");
			enemyImg.setIcon(new ImageIcon(Main.class.getResource("/boss.png")));
		}
		
		Main.updateHP(MainLayout.maxHP);
		updateEnemyHP(enemyMaxHP);
		
		log.setText("<html>You stumbled upon a <u>" + enemyName + "</u></html>");
		
		MainLayout.mp.setVisible(false);
		MainLayout.fp.setVisible(true);
	}
	
	private static void endFight() {
		MainLayout.bgLabel.setIcon(new ImageIcon(FightPanel.class.getResource("/bg.jpg")));
		
		Main.updateHP(MainLayout.maxHP);
		
		MainLayout.mp.setVisible(true);
		MapPanel.lblMap.setVisible(false);
		MainLayout.fp.setVisible(false);
		
		MainLayout.inFight = false;
	}
	
	private static void win() throws FileNotFoundException {
		if (enemyName.equalsIgnoreCase("Ender Beast")) {
			Main.playSound("win");
			
			Main.scan.close();
			Main.scan_un.close();
			Main.pw.close();
			Main.pw_un.close();
			MapPanel.scan_map.close();
			MapPanel.pw_map.close();
			
			File file = new File("stats.txt");
			try {
				Files.delete(file.toPath());
				file = new File("location.txt");
				Files.delete(file.toPath());
				file = new File("username.txt");
				Files.delete(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "You beat the game!",
					"Congratz!", JOptionPane.PLAIN_MESSAGE);
			
			System.exit(0);
		}
		
		Main.playSound("success");
		
		JOptionPane.showMessageDialog(null, "You have won the fight!",
				"Congratz!", JOptionPane.INFORMATION_MESSAGE);
		
		if(rand.nextInt(100)+1 < 75) {
			Main.resetScanner();
			
			int lvl;
			int toUpgrade = rand.nextInt(3)+1;
			switch (toUpgrade) {
				case 1:
					lvl = Integer.parseInt(Main.scan.nextLine().charAt(0) + "");
					if(lvl == 3) break;
					
					try {
						updateLine(lvl + " Weapon", ++lvl + " Weapon");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Main.playSound("equip");
					
					JOptionPane.showMessageDialog(null, "The enemy has dropped level " + lvl + " sword!",
							"New drop!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/w" + lvl + ".png")));
					break;
					
				case 2:
					Main.scan.nextLine();
					lvl = Integer.parseInt(Main.scan.nextLine().charAt(0) + "");
					if(lvl == 3) break;
					
					try {
						updateLine(lvl + " Armor", ++lvl + " Armor");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Main.playSound("equip");
					
					JOptionPane.showMessageDialog(null, "The enemy has dropped level " + lvl + " shield!",
							"New drop!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/a" + lvl + ".png")));
					break;
					
				case 3:
					Main.scan.nextLine();
					Main.scan.nextLine();
					lvl = Integer.parseInt(Main.scan.nextLine().charAt(0) + "");
					if(lvl == 3) break;
					
					try {
						updateLine(lvl + " Acc", ++lvl + " Acc");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Main.playSound("equip");
					
					JOptionPane.showMessageDialog(null, "The enemy has dropped level " + lvl + " accessories!",
							"New drop!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/ac" + lvl + ".png")));
			}
			
			Main.printItems();
			Main.printStats();
		}
		
		endFight();
	}
	
	private static void generateEnemy() {
		try {
			nameScan = new Scanner(enemyNames);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> names = new ArrayList<String>();
		
		enemyMaxHP = (int) (MainLayout.maxHP + (Math.random()*400+100));
		enemyHP = enemyMaxHP;
		enemyDmg = (int) (MainLayout.dmg + (Math.random()*200+50));
		updateEnemyHP(enemyHP);
		
		while(nameScan.hasNextLine()) {
			names.add(nameScan.nextLine());
		}
		
		enemyName = names.get((int)(Math.random()*names.size()));
		lblEnemyName.setText(enemyName);
		
		String path = "/e" + (new Random().nextInt(19)+1) + ".png";
		enemyImg.setIcon(new ImageIcon(Main.class.getResource(path)));
	}
	
	public static void updateEnemyHP(int newHP) {
		enemyHP = newHP;
		enemyHpBar.setString(newHP + "");
		enemyHpBar.setValue(newHP);
		enemyHpBar.setMaximum(enemyMaxHP);
	}
	
	private static int enemyAttack() {
		int curDmg = (enemyDmg + rand.nextInt(100)-50)/2; 
		Main.updateHP(MainLayout.HP-curDmg);
		return curDmg;
	}
	
	private static void updateLine(String oldLine, String newLine) throws IOException {
		Scanner scan = new Scanner(new File("stats.txt"));
		StringBuffer buffer = new StringBuffer();
		
		while (scan.hasNextLine()) {
			buffer.append(scan.nextLine() + System.lineSeparator());
		}
		String fileContents = buffer.toString();
		scan.close();
		
		fileContents = fileContents.replaceAll(oldLine, newLine);
		FileWriter writer = new FileWriter("stats.txt");

	    writer.append(fileContents);
	    writer.close();
	}
	
	private static void disableButtons() {
		attackBtn.setEnabled(false);
		evadeBtn.setEnabled(false);
		heavyBtn.setEnabled(false);
		
		Timer t = new Timer(1000, null);
		t.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attackBtn.setEnabled(true);
				evadeBtn.setEnabled(true);
				heavyBtn.setEnabled(true);
			}
		});
		t.setRepeats(false);
		t.start();
	}
}
