package ui.renderers;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PetIconsPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = -7300885155771024978L;
	private ImageIcon zebra;

	public PetIconsPanel() {
		super();
		try {
			loadIcons();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		initGUI();
	}

	public void setNumberOfPets(int pets) {
		for (int i = 0; i < pets; i++) {
			JLabel label = new JLabel("pet");
			label.setIcon(zebra);
			label.setText("");
			add(label);
		}
	}

	private void loadIcons() throws IOException {
		zebra = new ImageIcon(ImageIO.read(new File("img/zebra24.png")));
	}

	private void initGUI() {
			this.setPreferredSize(new java.awt.Dimension(144, 24));
			this.setRequestFocusEnabled(false);
	}
}
