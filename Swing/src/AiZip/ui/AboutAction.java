package AiZip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutAction extends AbstractAction{
	
private JFileChooser aboutPrograma;	
	
	public AboutAction(JFileChooser aboutPrograma){
		super ("Sobre!");
		this.putValue(AboutAction.SMALL_ICON, new ImageIcon("abouti.png"));
		this.putValue(AboutAction.SHORT_DESCRIPTION, "Sobre à Aplicação");		
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		JFrame tt = new JFrame();
		JButton b = new JButton("Fechar");
		ImageIcon icone  = new ImageIcon("icone.png");
		tt.setIconImage(icone.getImage());
		tt.setSize(821, 552);
		tt.setResizable(false);
		JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon("about.png"));
        Color oraRed = new Color(103,135, 226, 255);
		label2.setBorder(BorderFactory.createLineBorder(oraRed, 2));
        tt.add(b, BorderLayout.SOUTH);
        tt.add(label2);
        b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tt.dispose();
				
			}
		});
		tt.setLocationRelativeTo(null);
		tt.setVisible(true);
		tt.dispose();  
		tt.setUndecorated(true);  
		tt.setVisible(true);  
	}	
	
}