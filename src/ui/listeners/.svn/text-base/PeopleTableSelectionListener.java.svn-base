package ui.listeners;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ui.PetManagerFrame;
import ui.models.PeopleTableModel;

public class PeopleTableSelectionListener implements ListSelectionListener {
	private JTable table;
	private PetManagerFrame view;

	public PeopleTableSelectionListener(JTable listenedTable,
			PetManagerFrame editView) {
		this.table = listenedTable;
		this.view = editView;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (mouseHasBeenReleased(e)) {
			if(tableNeedsToBeCleared()){
				view.clearPets();
			}
			else{
				int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
				view.updatePets(((PeopleTableModel)table.getModel()).getPersonAt(selectedRow));
			}
		}
	}
	
	public boolean tableNeedsToBeCleared(){
		return table.getSelectedRowCount() == 0 || table.getSelectedRowCount() > 1;
	}

	private boolean mouseHasBeenReleased(ListSelectionEvent e) {
		return !e.getValueIsAdjusting();
	}
}
