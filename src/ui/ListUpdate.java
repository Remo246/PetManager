package ui;

import java.util.Arrays;

public class ListUpdate {
	
	private int[] insertedElements, deletedElements, updatedElements;
	
	public ListUpdate() {
		insertedElements = new int[0];
		deletedElements = new int[0];
		updatedElements = new int[0];
	}
	
	public boolean hasChanges() {
		return insertedElements.length != 0 || deletedElements.length != 0 || updatedElements.length != 0;
	}
	
	public void addInserts(int[] inserts) {
		insertedElements = addChanges(inserts,insertedElements);
	}
	
	public void addDeletes(int[] deletes) {
		deletedElements = addChanges(deletes,deletedElements);
	}
	
	public void addUpdates(int[] updates) {
		updatedElements = addChanges(updates,updatedElements);
	}

	public int[] getInsertedElements() {
		Arrays.sort(insertedElements);
		return insertedElements;
	}

	public int[] getDeletedElements() {
		Arrays.sort(deletedElements);
		return deletedElements;
	}

	public int[] getUpdatedElements() {
		return updatedElements;
	}

	private int[] addChanges(int[] inserts, int[] container) {
		if(container == null) {
			container = inserts;
		} else {
			int[] tmp = container.clone();
			container = new int[container.length + inserts.length];
			System.arraycopy(tmp, 0, container, 0, tmp.length);
			System.arraycopy(inserts, 0, container, tmp.length, inserts.length);
		}
		return container;
	}

}
