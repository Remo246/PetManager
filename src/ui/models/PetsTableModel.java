package ui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import domain.Person;
import domain.Pet;

public class PetsTableModel extends AbstractTableModel implements Observer{
	private static final long serialVersionUID = 3449532699477970153L;
	private Person person;
	
	private String[] columns = { "ID", "Name", "Breed" };

	public PetsTableModel(Person person) {
		this.person = person;
		person.addObserver(this);
	}

	public PetsTableModel() {
		person = new Person("","");
	}

	public Pet getPetAt(int row) {
		return person.getPets().get(row);
	}

	public void addPet(Pet pet) {
		person.getPets().add(0, pet);
	}

	public void removePetAtRow(int[] rowsView, int[] rowsModel) {
		List<Pet> toRemove = new ArrayList<Pet>();
		for(int i = 0; i < rowsModel.length ; ++i) {
			toRemove.add(person.getPets().get(rowsModel[i]));
		}
		for(Pet p : toRemove) {
			person.removePet(p);
		}
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return person.getPets().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pet p = person.getPets().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getID();
		case 1:
			return p.name;
		case 2:
			return p.breed;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1 || columnIndex == 2;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Pet changedPet = person.getPets().get(rowIndex);
		switch (columnIndex) {
		case 1:
			changedPet.name = value.toString();
			break;
		case 2:
			changedPet.breed = value.toString();
			break;
		default:
			break;
		}
		changedPet.save();
	}

	@Override
	public void update(Observable o, Object arg) {
		fireTableDataChanged();
	}

}
