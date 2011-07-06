package ui.models;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import persistence.ActiveRecordManager;

public class JobListModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 2203210028486754372L;
	private List<String> jobs;
	
	public JobListModel() {
		initialize();
	}
	
	private void initialize() {
		jobs = ActiveRecordManager.getStringList("SELECT DISTINCT job FROM people ORDER BY job");
	}

	@Override
	public Object getElementAt(int index) {
		return jobs.get(index);
	}
	
	@Override
	public int getSize() {
		return jobs.size();
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		super.setSelectedItem(anObject);
		initialize();
	}

}
