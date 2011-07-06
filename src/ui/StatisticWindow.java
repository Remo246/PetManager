package ui;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import applications.PetManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class StatisticWindow extends JWindow implements Observer {

	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
	private JTextArea averageLabel;
	private JLabel average;
	private GradientPanel gradientPanel;
	private JPanel backgroundPanel = null;
	private PetManagerFrame mainFrame;
	
	private PetManager application;

	/**
	 * This is the default constructor
	 */
	public StatisticWindow(PetManagerFrame mainFrame, PetManager application) {
		super();
		this.application = application;
		this.mainFrame = mainFrame;
		application.addObserver(this);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(130, 150);
		this.setContentPane(getBackgroundPanel());
		setPosition();
		update(null, null);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				dispose();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				toFront();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				toBack();
			}
		});
		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				setPosition();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				setPosition();
			}
		});
	}


	protected void setPosition() {
		Rectangle mainRec = mainFrame.getBounds();
		Rectangle newPos = this.getBounds();
		newPos.setLocation((int) (mainRec.getX() + mainRec.getWidth()),
				(int) mainRec.getY());
		this.setBounds(newPos);
	}

	private JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel();
			titleLabel.setText("Statistics :");
			titleLabel.setBackground(new java.awt.Color(0, 0, 0));
			titleLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
		}
		return titleLabel;
	}

	private JLabel getAverage() {
		if (average == null) {
			average = new JLabel();
			average.setText("0.0");
			average.setFont(new java.awt.Font("Tahoma", 0, 36));
			average.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return average;
	}

	private JTextArea getAverageLabel() {
		if (averageLabel == null) {
			averageLabel = new JTextArea();
			averageLabel.setText("Average pets\nper Person :");
			averageLabel.setEditable(false);
			averageLabel.setOpaque(false);
		}
		return averageLabel;
	}

	private JPanel getGradientPanel() {
		if (gradientPanel == null) {
			gradientPanel = new GradientPanel();
			GroupLayout jPanel1Layout = new GroupLayout(
					(JComponent) gradientPanel);
			gradientPanel.setLayout(jPanel1Layout);
			gradientPanel.setBackground(new java.awt.Color(255, 255, 255));
			jPanel1Layout.setHorizontalGroup(jPanel1Layout
					.createSequentialGroup().addContainerGap().add(
							jPanel1Layout.createParallelGroup().add(
									GroupLayout.LEADING, getAverage(), 0, 82,
									Short.MAX_VALUE).add(GroupLayout.LEADING,
									getAverageLabel(), 0, 82, Short.MAX_VALUE))
					.addContainerGap());
			jPanel1Layout.setVerticalGroup(jPanel1Layout
					.createSequentialGroup().addContainerGap().add(
							getAverageLabel(), GroupLayout.PREFERRED_SIZE, 36,
							GroupLayout.PREFERRED_SIZE).addPreferredGap(
							LayoutStyle.RELATED).add(getAverage(),
							GroupLayout.PREFERRED_SIZE, 46,
							GroupLayout.PREFERRED_SIZE).add(6));
		}
		return gradientPanel;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBackgroundPanel() {
		if (backgroundPanel == null) {
			backgroundPanel = new GradientPanel();
			GroupLayout jContentPaneLayout = new GroupLayout(
					(JComponent) backgroundPanel);
			backgroundPanel.setLayout(jContentPaneLayout);
			backgroundPanel.setPreferredSize(new java.awt.Dimension(130, 156));
			jContentPaneLayout.setHorizontalGroup(jContentPaneLayout
					.createSequentialGroup().addContainerGap().add(
							jContentPaneLayout.createParallelGroup().add(
									GroupLayout.LEADING, getTitleLabel(), 0,
									106, Short.MAX_VALUE).add(
									GroupLayout.LEADING, getGradientPanel(), 0, 106,
									Short.MAX_VALUE)).addContainerGap());
			jContentPaneLayout.setVerticalGroup(jContentPaneLayout
					.createSequentialGroup().addContainerGap().add(
							getTitleLabel(), GroupLayout.PREFERRED_SIZE,
							GroupLayout.PREFERRED_SIZE,
							GroupLayout.PREFERRED_SIZE).addPreferredGap(
							LayoutStyle.RELATED).add(getGradientPanel(), 0, 106,
							Short.MAX_VALUE).addContainerGap());
		}
		return backgroundPanel;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		DecimalFormat f = new DecimalFormat("0.0#");
		getAverage().setText(f.format(application.getAveragePetPerPerson()));
		
	}

}
