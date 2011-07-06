package ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import ui.listeners.PeopleTableSelectionListener;
import ui.models.JobListModel;
import ui.models.PeopleTableModel;
import ui.models.PetsTableModel;
import ui.renderers.PetIconsTableCellRenderer;
import applications.PetManager;
import domain.Person;
import domain.Pet;

public class PetManagerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable peopleTable;
	private JScrollPane peopleTableScrollPane;
	private JLabel titleLabel;
	private JScrollPane petTableScrollPane;
	private JLabel filterLabel;
	private JTextField filterTextField;
	private JButton removePetButton;
	private JButton addPetButton;
	private JButton removePersonButton;
	private JTable petsTable;
	private JButton addPersonButton;
	private GradientPanel backgroundPanel = null;
	private TableRowSorter<PeopleTableModel> sorter;
	private java.awt.Font buttonFont;
	private ImageIcon removeIcon;
	private ImageIcon addIcon;
	
	private PetManager application;

	public PetManagerFrame(PetManager application) {
		super();
		this.application = application;
		buttonFont = new java.awt.Font("Tahoma", 0, 11);
		try {
			removeIcon = new ImageIcon(ImageIO.read(new File(
					"img/edit_remove.png")));
			addIcon = new ImageIcon(ImageIO.read(new File("img/edit_add.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * callback method for {@link PeopleTableSelectionListener}.<br>
	 * Will update the model of the pet table to include only animals of this
	 * person.<br>
	 */
	public void updatePets(Person person) {
		petsTable.setModel(new PetsTableModel(person));

		petsTable.getColumn("ID").setPreferredWidth(10);

		addPetButton.setEnabled(true);
		removePetButton.setEnabled(true);
	}

	public void clearPets() {
		removePetButton.setEnabled(false);
		addPetButton.setEnabled(false);
		petsTable.setModel(new PetsTableModel());
	}

	private void initialize() {
		this.setTitle("PetManager 0.1");
		this.setContentPane(getBackGroundPanel());
		this.setSize(514, 484);
		peopleTable.getColumn("Pets").setCellRenderer(
				new PetIconsTableCellRenderer());
		peopleTable.getSelectionModel().addListSelectionListener(
				new PeopleTableSelectionListener(peopleTable, this));
	}

	private JPanel getBackGroundPanel() {
		if (backgroundPanel == null) {
			backgroundPanel = new GradientPanel();
			GroupLayout jContentPaneLayout = new GroupLayout(
					(JComponent) backgroundPanel);
			backgroundPanel.setLayout(jContentPaneLayout);
			backgroundPanel.setPreferredSize(new java.awt.Dimension(477, 405));
			jContentPaneLayout.setHorizontalGroup(jContentPaneLayout.createSequentialGroup()
				.addContainerGap()
				.add(jContentPaneLayout.createParallelGroup()
				    .add(jContentPaneLayout.createSequentialGroup()
				        .add(jContentPaneLayout.createParallelGroup()
				            .add(GroupLayout.LEADING, jContentPaneLayout.createSequentialGroup()
				                .add(getTitleLabel(), GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
				                .add(0, 16, Short.MAX_VALUE)
				                .add(jContentPaneLayout.createParallelGroup()
				                    .add(GroupLayout.LEADING, getRemovePetButton(), GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
				                    .add(GroupLayout.LEADING, getRemovePersonButton(), GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)))
				            .add(GroupLayout.LEADING, jContentPaneLayout.createSequentialGroup()
				                .add(getFilterLabel(), GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
				                .add(getFilterTextField(), GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
				                .add(36)))
				        .addPreferredGap(LayoutStyle.RELATED, 5, GroupLayout.PREFERRED_SIZE)
				        .add(jContentPaneLayout.createParallelGroup()
				            .add(GroupLayout.LEADING, getAddPetButton(), GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
				            .add(GroupLayout.LEADING, getAddPersonButton(), GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
				    .add(GroupLayout.LEADING, getPeopleScrollPane(), 0, 482, Short.MAX_VALUE)
				    .add(GroupLayout.LEADING, getPetScrollPane(), 0, 482, Short.MAX_VALUE))
				.addContainerGap());
			jContentPaneLayout.setVerticalGroup(jContentPaneLayout.createSequentialGroup()
				.addContainerGap()
				.add(getTitleLabel(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.UNRELATED)
				.add(jContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
				    .add(GroupLayout.BASELINE, getFilterTextField(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .add(GroupLayout.BASELINE, getFilterLabel(), GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.RELATED)
				.add(getPeopleScrollPane(), 0, 199, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.RELATED)
				.add(jContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
				    .add(GroupLayout.BASELINE, getAddPersonButton(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .add(GroupLayout.BASELINE, getRemovePersonButton(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.UNRELATED)
				.add(getPetScrollPane(), GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.RELATED)
				.add(jContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
				    .add(GroupLayout.BASELINE, getAddPetButton(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .add(GroupLayout.BASELINE, getRemovePetButton(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
		}
		return backgroundPanel;
	}

	private JTable getPeopleTable() {
		if (peopleTable == null) {
			PeopleTableModel model = new PeopleTableModel(application);
			sorter = new TableRowSorter<PeopleTableModel>(model);
			peopleTable = new JTable(model);
			peopleTable.setAutoCreateRowSorter(true);
			peopleTable.setRowSorter(sorter);
			peopleTable.setRowHeight(26);
			TableColumn col = peopleTable.getColumnModel().getColumn(2);
			JComboBox comboBox = new JComboBox(new JobListModel());
			comboBox.setEditable(true);
			col.setCellEditor(new DefaultCellEditor(comboBox));
			peopleTable.getColumn("ID").setPreferredWidth(10);
		}
		return peopleTable;
	}

	private JScrollPane getPeopleScrollPane() {
		if (peopleTableScrollPane == null) {
			peopleTableScrollPane = new JScrollPane();
			peopleTableScrollPane.setViewportView(getPeopleTable());
		}
		return peopleTableScrollPane;
	}

	private JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel();
			titleLabel.setText("Pet Manager");
			titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
		}
		return titleLabel;
	}

	private JTable getPetsTable() {
		if (petsTable == null) {
			petsTable = new JTable();
			clearPets();
			GroupLayout jTable1Layout = new GroupLayout((JComponent) petsTable);
			petsTable.setLayout(null);
			jTable1Layout.setVerticalGroup(jTable1Layout.createParallelGroup());
			jTable1Layout.setHorizontalGroup(jTable1Layout
					.createParallelGroup());
			petsTable.getColumn("ID").setPreferredWidth(10);
		}
		return petsTable;
	}

	private JScrollPane getPetScrollPane() {
		if (petTableScrollPane == null) {
			petTableScrollPane = new JScrollPane();
			petTableScrollPane.setViewportView(getPetsTable());
		}
		return petTableScrollPane;
	}

	private JButton getAddPersonButton() {
		if (addPersonButton == null) {
			addPersonButton = new JButton();
			addPersonButton.setText("add Person");
			addPersonButton.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent evt) {
					application.addPerson(new Person("", ""));
					getPeopleTable().setColumnSelectionInterval(1, 1);
					getPeopleTable().setRowSelectionInterval(0, 0);
					getPeopleTable().editCellAt(0, 1);
					getPeopleTable().requestFocus();
				}
			});

			addPersonButton.setIcon(addIcon);
			addPersonButton.setFont(buttonFont);
		}
		return addPersonButton;
	}

	private JButton getRemovePersonButton() {
		if (removePersonButton == null) {
			removePersonButton = new JButton();
			removePersonButton.setText("remove Person");
			removePersonButton.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent evt) {
					if (peopleTable.getSelectedRowCount() > 0) {
						int[] selectionView = peopleTable.getSelectedRows();
						int[] selectionModel = new int[selectionView.length];

						for (int i = 0; i < selectionView.length; ++i) {
							selectionModel[i] = peopleTable
									.convertRowIndexToModel(selectionView[i]);
						}
						((PeopleTableModel) getPeopleTable().getModel())
								.removePersonAtRow(selectionView,
										selectionModel);
					}
				}
			});

			removePersonButton.setIcon(removeIcon);
			removePersonButton.setFont(buttonFont);
		}
		return removePersonButton;
	}

	private JButton getAddPetButton() {
		if (addPetButton == null) {
			addPetButton = new JButton();
			addPetButton.setText("add Pet");
			addPetButton.setFont(buttonFont);
			addPetButton.addActionListener(new AddPetButtonListener());
			addPetButton.setIcon(addIcon);
		}
		return addPetButton;
	}

	private JButton getRemovePetButton() {
		if (removePetButton == null) {
			removePetButton = new JButton();
			removePetButton.setText("remove Pet");
			removePetButton.setFont(buttonFont);
			removePetButton.setIcon(removeIcon);
			removePetButton.addActionListener(new RemovePetButtonListener());
		}
		return removePetButton;
	}

	private JTextField getFilterTextField() {
		if (filterTextField == null) {
			filterTextField = new JTextField();
			filterTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					newFilter();
				}

				public void insertUpdate(DocumentEvent e) {
					newFilter();
				}

				public void removeUpdate(DocumentEvent e) {
					newFilter();
				}
			});

		}
		return filterTextField;
	}

	private JLabel getFilterLabel() {
		if (filterLabel == null) {
			filterLabel = new JLabel();
			filterLabel.setText("Search Persons : ");
		}
		return filterLabel;
	}

	private void newFilter() {
		RowFilter<PeopleTableModel, Object> filter = null;
		try {
			filter = RowFilter.regexFilter("(?i)" + getFilterTextField().getText());
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(filter);
	}

	public PeopleTableModel getModelOfPeopleTable() {
		return (PeopleTableModel) getPeopleTable().getModel();
	}

	private final class RemovePetButtonListener extends AbstractAction {
		public void actionPerformed(ActionEvent evt) {
			if (petsTable.getSelectedRowCount() > 0) {
				int viewSelectedRow = peopleTable.getSelectedRow();
				int modelSelectedRow = peopleTable
						.convertRowIndexToModel(viewSelectedRow);
				PeopleTableModel peopleModel = (PeopleTableModel) peopleTable
						.getModel();
				Person person = application.getPersonAt(modelSelectedRow);
				
				int[] selectionView = petsTable.getSelectedRows();
				int[] selectionModel = new int[selectionView.length];

				for (int i = 0; i < selectionView.length; ++i) {
					selectionModel[i] = petsTable
							.convertRowIndexToModel(selectionView[i]);
				}
				((PetsTableModel) getPetsTable().getModel())
						.removePetAtRow(selectionView,
								selectionModel);

				peopleModel.reloadPerson(person);
				
				peopleTable.setRowSelectionInterval(viewSelectedRow,
						viewSelectedRow);
			}
		}
	}

	private final class AddPetButtonListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			PetsTableModel petsModel = (PetsTableModel) petsTable
					.getModel();
			Pet newPet = new Pet("", "");
	
			int viewSelectedRow = peopleTable.getSelectedRow();
			int modelSelectedRow = peopleTable
					.convertRowIndexToModel(viewSelectedRow);
			PeopleTableModel peopleModel = (PeopleTableModel) peopleTable
					.getModel();
			Person person = application.getPersonAt(modelSelectedRow);
	
			newPet.setOwner(person);
			newPet.save();
	
			peopleModel.reloadPerson(person);
	
			peopleTable.setRowSelectionInterval(viewSelectedRow,
					viewSelectedRow);
	
			petsModel.addPet(newPet);
	
			petsTable.setColumnSelectionInterval(1, 1);
			petsTable.setRowSelectionInterval(
					petsModel.getRowCount() - 1, petsModel
							.getRowCount() - 1);
			petsTable.editCellAt(0, 1);
			petsTable.requestFocus();
		}
	}
}
