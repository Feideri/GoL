package classes.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;

import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Canvas;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JSlider;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.Font;


public class View {

	private JFrame frmGameOfLife;
	private JScrollPane scrollPane;
	private GridView currentGrid;
	private JTextField txtGenInd;
	private JTextField txtPosInd;
	private JTextField txtSectInd;
	private Timer mousePosTimer;
	private AdjustmentListener sd;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					
					window.frmGameOfLife.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGameOfLife = new JFrame();
		frmGameOfLife.setTitle("Game of Life");
		frmGameOfLife.setBounds(100, 100, 600, 600);
		frmGameOfLife.setExtendedState(frmGameOfLife.getExtendedState() | JFrame.MAXIMIZED_BOTH); 
		frmGameOfLife.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmGameOfLife.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open File...");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);
		    }
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 30, 100, 0, 400, 0, 100, 10, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 400, 25, 5, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmGameOfLife.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblGen = new JLabel("Generation:");
		lblGen.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblGen = new GridBagConstraints();
		gbc_lblGen.anchor = GridBagConstraints.EAST;
		gbc_lblGen.insets = new Insets(0, 0, 5, 5);
		gbc_lblGen.gridx = 5;
		gbc_lblGen.gridy = 1;
		frmGameOfLife.getContentPane().add(lblGen, gbc_lblGen);
		
		
		
		txtGenInd = new JTextField();
		txtGenInd.setHorizontalAlignment(SwingConstants.RIGHT);
		txtGenInd.setEditable(false);
		txtGenInd.setText("0");
		GridBagConstraints gbc_txtGenInd = new GridBagConstraints();
		gbc_txtGenInd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGenInd.insets = new Insets(0, 0, 5, 5);
		gbc_txtGenInd.gridx = 6;
		gbc_txtGenInd.gridy = 1;
		frmGameOfLife.getContentPane().add(txtGenInd, gbc_txtGenInd);
		txtGenInd.setColumns(6);
		
		//currentGrid = new GridView();
		currentGrid = new GridView();
		scrollPane = new JScrollPane(currentGrid);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		frmGameOfLife.getContentPane().add(scrollPane, gbc_scrollPane);
		
		//currentGrid.getView(new Rectangle);
		
		sd = new AdjustmentListener(){
			@Override
			public void adjustmentValueChanged(AdjustmentEvent event) {
				currentGrid.getView(scrollPane.getViewport().getViewRect());
				currentGrid.paintComponent(currentGrid.getGraphics());
			}
		};
		
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(sd);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(sd);
		
		JButton button = new JButton("Step");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 3;
		frmGameOfLife.getContentPane().add(button, gbc_button);
		
		JButton btnCenterView = new JButton("Center");
		btnCenterView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerView();
			}
		});
		GridBagConstraints gbc_btnCenterView = new GridBagConstraints();
		gbc_btnCenterView.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCenterView.insets = new Insets(0, 0, 5, 5);
		gbc_btnCenterView.gridx = 2;
		gbc_btnCenterView.gridy = 3;
		frmGameOfLife.getContentPane().add(btnCenterView, gbc_btnCenterView);
		
		JLabel lblPosition = new JLabel("Position: ");
		lblPosition.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition.anchor = GridBagConstraints.EAST;
		gbc_lblPosition.gridx = 5;
		gbc_lblPosition.gridy = 3;
		frmGameOfLife.getContentPane().add(lblPosition, gbc_lblPosition);
		
		txtPosInd = new JTextField();
		txtPosInd.setFont(new Font("Dialog", Font.PLAIN, 10));
		txtPosInd.setText("(0, 0)");
		txtPosInd.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPosInd.setEditable(false);
		txtPosInd.setColumns(6);
		GridBagConstraints gbc_txtPosInd = new GridBagConstraints();
		gbc_txtPosInd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPosInd.insets = new Insets(0, 0, 5, 5);
		gbc_txtPosInd.gridx = 6;
		gbc_txtPosInd.gridy = 3;
		frmGameOfLife.getContentPane().add(txtPosInd, gbc_txtPosInd);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rectangle r = currentGrid.view;
				JOptionPane.showMessageDialog(null, "x: " + r.x + " y: " + r.y);
				JOptionPane.showMessageDialog(null, "x: " + (r.x+r.width) + " y: " + (r.y+r.height));
				
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 4;
		frmGameOfLife.getContentPane().add(btnStart, gbc_btnStart);
		
		JSlider slider = new JSlider();
		slider.setValue(3);
		slider.setToolTipText("Generation speed");
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(1);
		slider.setMaximum(3);
		slider.setMajorTickSpacing(1);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.anchor = GridBagConstraints.NORTH;
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 4;
		frmGameOfLife.getContentPane().add(slider, gbc_slider);
		
		JLabel lblSector = new JLabel("Sector:");
		GridBagConstraints gbc_lblSector = new GridBagConstraints();
		gbc_lblSector.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSector.insets = new Insets(0, 0, 5, 5);
		gbc_lblSector.gridx = 5;
		gbc_lblSector.gridy = 4;
		frmGameOfLife.getContentPane().add(lblSector, gbc_lblSector);
		
		txtSectInd = new JTextField();
		txtSectInd.setFont(new Font("Dialog", Font.PLAIN, 10));
		txtSectInd.setText("(0, 0)");
		txtSectInd.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSectInd.setEditable(false);
		txtSectInd.setColumns(6);
		GridBagConstraints gbc_txtSectInd = new GridBagConstraints();
		gbc_txtSectInd.anchor = GridBagConstraints.NORTH;
		gbc_txtSectInd.insets = new Insets(0, 0, 5, 5);
		gbc_txtSectInd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSectInd.gridx = 6;
		gbc_txtSectInd.gridy = 4;
		frmGameOfLife.getContentPane().add(txtSectInd, gbc_txtSectInd);
		
		
		mousePosTimer = new Timer(100, new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        txtPosInd.setText("(" + currentGrid.coordX + " , " + currentGrid.coordY + ")");
		        txtSectInd.setText("(" + currentGrid.sectX + " , " + currentGrid.sectY + ")");
		    }
		});
		mousePosTimer.start();
		
		
		
	}
	
	private void centerView(){
		Rectangle bounds = scrollPane.getViewport().getViewRect();
		Dimension size = scrollPane.getViewport().getViewSize();
		
		int x = (size.width - bounds.width) / 2;
		int y = (size.height - bounds.height) / 2;
		
		scrollPane.getViewport().setViewPosition(new Point(x, y));
	}
	
	private void clearGrid(){
		//currentGrid = new GridView();
		scrollPane.setViewportView(currentGrid);
		centerView();
	}
}
