package AiZip.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.util.zip.*;
import AiZip.exceptions.*;
/**
 * 
 * @author Ronaldo Rolim, Cassia Regina, Vinicius Lemos e Caio Cezar
 * @version 0.15
 * @date 08/06/2015
 * 
 */
public class FramePrincipal extends JFrame{		
	
	/**
	 * Criação de variaveis
	 */
	private String selectFile;
	private String compactLocale;
	private String compactFile;
	private String selectLocale = "...";
	private String inf;
	private String[] colunas = {"ID", "Nome Arquivo", "Caminho"};	
	private int id = 1;
	private String nome;
	private String caminhoA;
	private String[][] dados;
	private int sq = 0;
	private File[] list;
	
	/**
	 * Componentes de criação Swing
	 */
	private JToolBar Jstatusbar = new JToolBar(); //cria objeto JToolbar
	private JToolBar Jtoolbar = new JToolBar(); //cria objeto JToolbar
	private JMenuBar Jmenubar = new JMenuBar(); //cria objeto JMenuBar
	private JMenu Jarquivo = new JMenu("Arquivo"); //cria o objeto Menu
	private JMenu Jhelp = new JMenu("Ajuda"); //cria o objeto Menu
	private JPanel Jpanelprincipal = new JPanel(); //cria o painel principal	
	private JFileChooser jarquivoProcurar = new JFileChooser();	
	private JFileChooser jcompactarPastas = new JFileChooser();
	private JFileChooser jsairPrograma = new JFileChooser();
	private JFileChooser jaboutPrograma = new JFileChooser();
	private JLabel jtext = new JLabel("Ação:  ");
	private JLabel jcaminho = new JLabel(selectLocale);
	private JLabel Jlinf = new JLabel(inf);
	DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
	private JTable tabela = new JTable(modelo); //carrega as variáveis no grid
	private JScrollPane barraRolagem = new JScrollPane(tabela);
	private ImageIcon icone;	
	private JButton cancelar = new JButton("Cancelar");
	private JButton compactar = new JButton("Compactar");
	private File dir = new File("zipfiles\\");
	
	
	/**
	 * Componentes de Ações
	 */
	private SelectAction selecionararquivo = new SelectAction (this.jarquivoProcurar);	
	private CompactFoldersAction compactarpastas = new CompactFoldersAction (this.jcompactarPastas);
	private ExitAction sairprograma = new ExitAction (this.jsairPrograma);
	private AboutAction aboutprograma = new AboutAction (this.jaboutPrograma);
	
	/**
	 * Início de criações dos métodos
	 *  
	 */
	
	/**
	 * Método de ler os arquivos contidos na pasta
	 */
	private void lerArquivos(){
		
		list = dir.listFiles();
		for (int i = 0; i < list.length; i++) {
			
			nome = list[i].getName();
			caminhoA = list[i].getAbsolutePath();
			modelo.addRow(new String[]{id+"",nome, caminhoA});
			id++;		
		}
		
	}
	/**
	 * Método de construção do Painel com os componentes da Tabela
	 */
	private void criaPanel(){
		
		this.Jlinf.setBounds(10, 48, 400, 20); //definição do tamanho e localização do Label
		this.add(Jlinf);	//iniciando o lbel no frame	
		this.cancelar.setBounds(410, 48, 100, 20);//definição tamanho e localização do botão
		cancelar.setVisible(false);//botao inicia com visibilidade false
		this.add(cancelar);//inicia o botão		
		this.compactar.setBounds(520, 48, 100, 20);
		compactar.setVisible(false);
		this.add(compactar);
		Jpanelprincipal.setBounds(0, 75, 640, 320);// define a posição do panel e seu tamanho
		Jpanelprincipal.setLayout(new GridLayout(1, 1));// inicia o grid no panel				
		TableColumn tc = null;//reseta as configurações padrão de dimensões das colunas
		tc = tabela.getColumnModel().getColumn(0);//iniciando chamada para coluna 1
		tc.setPreferredWidth(55);//espaçamento inicial da coluna
		tc.setMaxWidth(55); // maximo que a coluna pode ser dimensionada
		tc.setMinWidth(55); // minimo que a coluna pode ser dimensionada
		tc = tabela.getColumnModel().getColumn(1);
		tc.setPreferredWidth(150);
		tc.setMaxWidth(150);
		tc.setMinWidth(150);		 
		Jpanelprincipal.add(barraRolagem);	
		
		this.add(Jpanelprincipal, BorderLayout.CENTER);
		
	}
	/**
	 * Método de construção do menu
	 */
	private void criaMenu(){
		JMenuItem itemSelect = new JMenuItem(this.selecionararquivo);
		JMenuItem compPastas = new JMenuItem(this.compactarpastas);
		JMenuItem sairPrograma = new JMenuItem(this.sairprograma);
		JMenuItem aboutPrograma = new JMenuItem(this.aboutprograma);
		this.Jarquivo.add(itemSelect);		
		this.Jarquivo.add(compPastas);
		this.Jarquivo.addSeparator();
		this.Jarquivo.add(sairPrograma);
		this.Jmenubar.add(this.Jarquivo);
		this.Jmenubar.add(this.Jhelp);
		this.Jhelp.add(aboutPrograma);
		this.Jmenubar.setBackground(new Color(184, 207, 229));
		this.setJMenuBar(this.Jmenubar);
	}
	/**
	 * Método de construção das barras de menu e status
	 */
	private void criaToolBar(){
		this.Jstatusbar.add(this.jtext);
		this.Jstatusbar.add(this.jcaminho);
		this.Jtoolbar.add(this.selecionararquivo);
		this.Jtoolbar.addSeparator();
		this.Jtoolbar.add(this.compactarpastas);
		this.Jtoolbar.addSeparator();
		this.Jtoolbar.add(this.sairprograma);
		
	}
	/**
	 * Metodo de construção do layout
	 * @param interno
	 */
	private void criaGUI (Container interno){
		interno.setLayout(new BorderLayout());
		interno.add(this.Jstatusbar, BorderLayout.SOUTH);
		jtext.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		jcaminho.setFont(new Font("Sans-Serif", Font.ITALIC, 12));
		interno.add(this.Jtoolbar, BorderLayout.NORTH);
		
	}	
	/**
	 * Metodo de contrução e carregamento dos demais mtodos de inicialização
	 */
	public FramePrincipal(){		
		super ("AiZIP  SPI P2 2015.1  V1.0"); //define o titulo da janela
		
		  addWindowListener(new WindowAdapter()   
		  {  
		  public void windowClosing(WindowEvent evt)  
		  {  
			  if (sq == 1){
			  int op = JOptionPane.showOptionDialog(null, "Compactação não realizada!.. Deseja Fechar Programa?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			  if (op == 0){
				  System.exit(0);					 
			  }else{
				  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			  }
			  }else{
				  int op = JOptionPane.showOptionDialog(null, "Fechar Programa?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				  if (op == 0){
					  System.exit(0);					 
				  }else{
					  setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				  }
			  }
		  }
		  });		  
		icone  = new ImageIcon("icone.png");
		this.setIconImage(icone.getImage());
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.criaMenu();
		this.criaToolBar();
		this.criaPanel();
		Container interno = this.getContentPane();
		this.criaGUI(interno);
		this.lerArquivos();	
	}
	/**
	 * Ação do comando Selecionar Arquivo para compacatar
	 * @author Ronaldo Rolim, Cassia Regina, Caio Cezar e Vinicius Lemos
	 *
	 */
	public class SelectAction extends AbstractAction{
		
		private JFileChooser jarquivoProcurar;	
		
		public SelectAction(JFileChooser jarquivoprocurar){
			super ("Selecionar Arquivo ...");
			this.putValue(SelectAction.SMALL_ICON, new ImageIcon("file.png"));
			this.putValue(SelectAction.SHORT_DESCRIPTION, "Selecionar Arquivo para compactar");		
		}	
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			
			JFileChooser selectarquivo = new JFileChooser("zipfiles\\");
			selectarquivo.setDialogTitle("Selecionar Arquivo para compactar");
			int arquivo = selectarquivo.showOpenDialog(this.jarquivoProcurar);
			
						
				if (arquivo == JFileChooser.APPROVE_OPTION){
					selectFile = selectarquivo.getSelectedFile().getName();
					selectLocale = selectarquivo.getSelectedFile().getAbsolutePath();
					
					
					jcaminho.setText("Arquivo Selecionado");
					jcaminho.repaint();
					sq = 1;
					inf = selectLocale;
					Jlinf.setText(inf);
					cancelar.setVisible(true);
					cancelar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							jcaminho.setText("Seleção cancelada");
							jcaminho.repaint();
							sq = 0;
							inf = "";							
							Jlinf.setText(inf);
							cancelar.setVisible(false);
							compactar.setVisible(false);
							Jlinf.validate();
							Jlinf.repaint();
							
						}					
					});
					
					/**
					 * compactação na ação seleção
					 */
					compactar.setVisible(true);
					compactar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							JFileChooser jcompactarArquivo = new JFileChooser();
							
							JFileChooser arquivo2 = new JFileChooser("zipfiles\\");
							arquivo2.setDialogTitle("Salvar... - Nome e parâmetros do arquivo");
							FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP Files", "zip");
							arquivo2.setFileFilter(filter);
							int t = arquivo2.showSaveDialog(jcompactarArquivo);    
				        
							if(t == JFileChooser.APPROVE_OPTION){  
								compactLocale = arquivo2.getSelectedFile().getPath()+".zip";
								compactFile = arquivo2.getSelectedFile().getName()+".zip";
								
								/**
								 * Sequencia para compactação
								 */
								
								byte[] buffer = new byte[1024];
								 
								try {
									// Stream de saida
									FileOutputStream fos = new FileOutputStream(compactLocale);
									// Zip de saida
									
									ZipOutputStream zos = new ZipOutputStream(fos);
									
									// Arquivo a ser zipdo
									ZipEntry ze = new ZipEntry(selectFile);
									
									
								
									// Adciona arquivo no Zip de saida
									zos.putNextEntry(ze);
								
									// Ler o Arquivo que sera Zipado
									FileInputStream in = new FileInputStream(selectLocale);
								
									int len;
								    while ((len = in.read(buffer)) > 0) {
								    	zos.write(buffer, 0, len);
								    }
								
								    // Fecha Arquivos
								    in.close();
								
								    // Fecha Zip e entrada
								    zos.closeEntry();
								    zos.close();
								    
								    sq = 0;
								    jcaminho.setText("Arquivo compactado com sucesso");					    
									jcaminho.repaint();							
									inf = "";							
									Jlinf.setText(inf);
									cancelar.setVisible(false);
									compactar.setVisible(false);
									Jlinf.validate();
									Jlinf.repaint();
									nome = compactFile;
									caminhoA = compactLocale;
									modelo.addRow(new String[]{id+"",nome, caminhoA});
								
								} catch (IOException ex) {
									ex.printStackTrace();
								}							
							}						
						}
					});
					Jlinf.validate();
					Jlinf.repaint();
				}										
		}
		
	}
	/**
	 * 	 
	 * Ação para compactação na barra ferramentas
	 */	
	public class CompactFoldersAction extends AbstractAction{

		private JFileChooser jcompactarPastas;	
		
		public CompactFoldersAction(JFileChooser jcompactarpastas){
			super ("Compactar ...");
			this.putValue(CompactFoldersAction.SMALL_ICON, new ImageIcon("zip.png"));
			this.putValue(CompactFoldersAction.SHORT_DESCRIPTION, "Iniciar Compactação da Seleção");		
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
		if (sq == 1){
			JFileChooser arquivo2 = new JFileChooser("zipfiles\\");
			arquivo2.setDialogTitle("Salvar... - Nome e parâmetros do arquivo");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP Files", "zip");
			arquivo2.setFileFilter(filter);
			int t = arquivo2.showSaveDialog(jcompactarPastas);    
        
			if(t == JFileChooser.APPROVE_OPTION){  
				compactLocale = arquivo2.getSelectedFile().getPath()+".zip";
				compactFile = arquivo2.getSelectedFile().getName()+".zip";
				
				/**
				 * Sequencia para compactação
				 */
				
				byte[] buffer = new byte[1024];
				 
				try {
					// Stream de saida
					FileOutputStream fos = new FileOutputStream(compactLocale);
					// Zip de saida
					
					ZipOutputStream zos = new ZipOutputStream(fos);
					
					// Arquivo a ser zipdo
					ZipEntry ze = new ZipEntry(selectFile);					
				
					// Adciona arquivo no Zip de saida
					zos.putNextEntry(ze);
				
					// Ler o Arquivo que sera Zipado
					FileInputStream in = new FileInputStream(selectLocale);
				
					int len;
				    while ((len = in.read(buffer)) > 0) {
				    	zos.write(buffer, 0, len);
				    }
				
				    // Fecha Arquivos
				    in.close();
				
				    // Fecha Zip e entrada
				    zos.closeEntry();
				    zos.close();
				    
				    sq = 0;
				    jcaminho.setText("Arquivo compactado com sucesso");					    
					jcaminho.repaint();							
					inf = "";							
					Jlinf.setText(inf);
					cancelar.setVisible(false);
					compactar.setVisible(false);
					Jlinf.validate();
					Jlinf.repaint();
					nome = compactFile;
					caminhoA = compactLocale;
					modelo.addRow(new String[]{id+"",nome, caminhoA});
				
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			}else{
				jcaminho.setText("Arquivo não selecionado!.. Compactação não realizada!...");					    
				jcaminho.repaint();					
			}						
		}	
	}
	
	
	/**
	 * 
	  * Ação para sair
	 */
	public class ExitAction extends AbstractAction{			
			
			public ExitAction(JFileChooser sairPrograma){
				super ("Sair ...");
				this.putValue(ExitAction.SMALL_ICON, new ImageIcon("sair.png"));
				this.putValue(ExitAction.SHORT_DESCRIPTION, "Sair da Aplicação");		
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (sq == 1){
				  int op = JOptionPane.showOptionDialog(null, "Compactação não realizada!.. Deseja Fechar Programa?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				  if (op == 0){
					  System.exit(0);					 
				  }
				}else{
					int op = JOptionPane.showOptionDialog(null, "Fechar Programa?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					  if (op == 0){
						  System.exit(0);					 
					  }
				}
			}		
		}
	/**
	 * Main Principal
	 * @param args
	 */
	 public static void main(String[] args){        
	        // Mostra uma imagem com o título da aplicação
	        SplashScreen splash = new SplashScreen(4000);
	        splash.showSplash();        
	        
	        //cria objeto referente a classe FramePrincipal
	         FramePrincipal janela = new FramePrincipal();	        
	    }
}
