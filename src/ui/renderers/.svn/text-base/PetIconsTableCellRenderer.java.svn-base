package ui.renderers;

import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ui.models.PeopleTableModel;
import domain.Pet;

public class PetIconsTableCellRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		PetIconsPanel petIcons = new PetIconsPanel();
		int pets = (Integer) value;
		petIcons.setNumberOfPets(pets);

		StringBuilder buff = new StringBuilder();
		List<Pet> petsList = ((PeopleTableModel) table.getModel()).getPersonAt(
				table.convertRowIndexToModel(row)).getPets();
		for (Pet p : petsList) {
			buff.append(p.name + ", ");
		}
		if (pets > 0) {
			petIcons.setToolTipText(buff.substring(0, buff.length() - 2));
		}

		if (isSelected)
			petIcons.setBackground(table.getSelectionBackground());
		else
			petIcons.setBackground(table.getBackground());

		return petIcons;
	}
}
