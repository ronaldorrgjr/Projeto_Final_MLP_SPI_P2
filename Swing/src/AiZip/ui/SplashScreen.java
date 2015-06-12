package AiZip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.JPanel;

public class SplashScreen extends JWindow{
	
	 private int duration;
	    
	    public SplashScreen(int d) {
	        duration = d;
	    }
	    
	// Este é um método simples para mostrar uma tela de apresentção

	// no centro da tela durante a quantidade de tempo passada no construtor

	    public void showSplash() {        
	        JPanel content = (JPanel)getContentPane();
	        content.setBackground(Color.WHITE);        
	        
	        // Configura a posição e o tamanho da janela
	        int width = 386;
	        int height = 462;
	        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	        int x = (screen.width-width)/2;
	        int y = (screen.height-height)/2;
	        setBounds(x,y,width,height);
	        
	        // Constrói o splash screen
	        //JLabel label = new JLabel("Ronaldo Rolim, Cassia, Caio e Vinicios", JLabel.CENTER);
	        JLabel label2 = new JLabel();
	        label2.setIcon(new ImageIcon("splash.png"));
	        //JLabel copyrt = new JLabel
	        //        ("Copyright 2015, SPI P2 - UNIPE", JLabel.CENTER);
	        //copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	        //content.add(label, BorderLayout.NORTH);
	        content.add(label2, BorderLayout.CENTER);
	        //content.add(copyrt, BorderLayout.SOUTH);
	        Color oraRed = new Color(103,135, 226, 255);
	        content.setBorder(BorderFactory.createLineBorder(oraRed, 2));        
	        // Torna visível
	        setVisible(true);
	        
	        // Espera ate que os recursos estejam carregados
	        try { Thread.sleep(duration); } catch (Exception e) {}        
	        setVisible(false);        
	    }   
	    
}
