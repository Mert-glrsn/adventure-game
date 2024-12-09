import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main {
	static File user, username;
	static Scanner scan, scan_un;
	static PrintWriter pw, pw_un;
	static MainLayout frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user = new File("stats.txt");
					username = new File("username.txt");
					pw = new PrintWriter(new FileWriter(user, true));
					pw_un = new PrintWriter(new FileWriter(username, true));
					scan = new Scanner(user);
					scan_un = new Scanner(username);
					
					frame = new MainLayout();
					frame.setVisible(true);
					
					generateUsername();
					
					generateDefaultSettings();
					printItems();
					printStats();
					
					Main.updateHP(MainLayout.maxHP);
					StatPanel.lblUsername.setText(scan_un.nextLine());
					
					generateNames();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public static void printStats() throws FileNotFoundException {
		resetScanner();
		
		int lvl = Integer.parseInt(scan.nextLine().charAt(0) + "");
		MainLayout.dmg = (int) (lvl*300 + (Math.random()*40-20));
		StatPanel.lblDamage.setText("Damage: " + MainLayout.dmg);
		
		lvl = Integer.parseInt(scan.nextLine().charAt(0) + "");
		MainLayout.maxHP = (int) lvl*1250;
		StatPanel.hpBar.setString(MainLayout.HP+"");
		StatPanel.hpBar.setMaximum(MainLayout.maxHP);
		StatPanel.hpBar.setValue(MainLayout.HP);
		
		lvl = Integer.parseInt(scan.nextLine().charAt(0) + "");
		MainLayout.cc = (int) (lvl*15 + (Math.random()*6-3));
		StatPanel.lblCriticalChance.setText("Critical chance: %" + MainLayout.cc);
		MainLayout.agl = (int) (lvl*15 + (Math.random()*10-5));
		StatPanel.lblAgility.setText("Agility: %" + MainLayout.agl);
	}
	
	public static void printItems() throws FileNotFoundException {
		resetScanner();
		
		char lvl = scan.nextLine().charAt(0);
		StatPanel.weaponImg.setIcon(new ImageIcon(Main.class.getResource("/w" + lvl + ".png")));
		StatPanel.lblWeapon.setText("Level " + lvl);
		
		lvl = scan.nextLine().charAt(0);
		StatPanel.armorImg.setIcon(new ImageIcon(Main.class.getResource("/a" + lvl + ".png")));
		StatPanel.lblArmor.setText("Level " + lvl);
		
		lvl = scan.nextLine().charAt(0);
		StatPanel.accesoriesImg.setIcon(new ImageIcon(Main.class.getResource("/ac" + lvl + ".png")));
		StatPanel.lblAccesories.setText("Level " + lvl);
	}
	
	public static void generateDefaultSettings() {
		if(user.length() == 0) {
			pw.println("1 Weapon");
			pw.println("1 Armor");
			pw.println("1 Acc");
		}
		
		pw.flush();
	}
	
	public static void generateUsername() {
		if(username.length() == 0) {
			String unString = "Adventurer";
			unString = JOptionPane.showInputDialog(frame, "Enter a username:");
			if(unString == null) unString = "Adventurer";
			if(unString.equalsIgnoreCase("")) unString = "Adventurer";
			
			pw_un.println(unString);
		}
		
		pw_un.close();
	}
	
	public static void resetScanner() throws FileNotFoundException {
		scan.close();
		scan = new Scanner(user);
	}
	
	public static void updateStats() {
		try {
    		printItems();
    		printStats();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void updateHP(int newHP) {
		MainLayout.HP = Math.min(MainLayout.maxHP, newHP);
		StatPanel.hpBar.setString(MainLayout.HP + "");
		StatPanel.hpBar.setValue(MainLayout.HP);
	}
	
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		          Main.class.getResource("/"+url+".wav"));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
	
	private static void generateNames() {
		try {
			File f = new File("enemyNames.txt");
			f.createNewFile();
			
			if(f.exists() && !f.isDirectory()) { 
			    PrintWriter pw = new PrintWriter(f);
			    String[] names = {"Ucube", "Falmer", "Warmonger",
			    		"Hagraven", "Sabre", "Chaurus", "Serpentine",
			    		"Revered", "Lurker", "Vindicator", "Briarheart",
			    		"Draugr", "Ebony", "Miraak", "Karstaag",
			    		"Primeval", "Foulmirage", "Emberface",
			    		"Venomflayer", "Fogling", "Grieveclaw",
			    		"Cinderhag", "Vamppest", "Nightsnare", "Rusthag"};
			    for(String s : names) {
			    	pw.println(s);
			    }
			    pw.close();
			}
		} catch (IOException e) {}
	}
}
