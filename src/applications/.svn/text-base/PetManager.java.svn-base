package applications;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import persistence.ActiveRecordManager;
import ui.ListUpdate;
import ui.PetManagerFrame;
import ui.StatisticWindow;
import domain.Person;

public class PetManager extends Observable {
	
	private List<Person> peopleList;
	
	public PetManager() {
		peopleList = Person.findAll();
	}
	
	
	public List<Person> getPeopleList() {
		return peopleList;
	}
	
	public Person getPersonAt(int position) {
		return peopleList.get(position);
	}
	
	public void addPerson(Person person) {
		peopleList.add(0,person);
		person.save();
		setChanged();
		ListUpdate up = new ListUpdate();
		up.addInserts(new int[] {0});
		notifyObservers(up);
	}
	
	public void removePeople(List<Person> pepoleToRemove) {
		int[] removeIndexes = new int[pepoleToRemove.size()];
		for(int i = 0; i < pepoleToRemove.size(); i++) {
			Person p = pepoleToRemove.get(i);
			removeIndexes[i] = peopleList.indexOf(p);
			p.delete();
		}
		peopleList.removeAll(pepoleToRemove);
		setChanged();
		ListUpdate up = new ListUpdate();
		up.addDeletes(removeIndexes);
		notifyObservers(up);
	}
	public void setPerson(int position, Person personToReplace) {
		peopleList.set(position, personToReplace);
		setChanged();
		ListUpdate up = new ListUpdate();
		up.addUpdates(new int[] {position});
		notifyObservers(up);
	}
	
	public double getAveragePetPerPerson() {
		double sum = 0;
		for(Person p : peopleList) {
			sum += p.getPets().size();
		}
		return sum / peopleList.size();
	}


	public static void main(String[] args) {
		createTablesIfMissing();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PetManager application = new PetManager();
				PetManagerFrame editView = new PetManagerFrame(application);
				editView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				editView.setVisible(true);
				StatisticWindow toolbar = new StatisticWindow(editView, application);
				toolbar.setVisible(true);

				editView.setLocation(100, 100);
				
				
//				// Second view to test observer pattern
//				PetManagerFrame editView2 = new PetManagerFrame(application);
//				editView2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				editView2.setVisible(true);
//				StatisticWindow toolbar2 = new StatisticWindow(editView2, application);
//				toolbar2.setVisible(true);
//				editView2.setLocation(editView.getX() + editView.getWidth() + toolbar.getWidth() + 20, editView.getY());
			}
		});
	}

	private static void createTablesIfMissing() {
		String sql = "select * from people";
		try {
			ActiveRecordManager.execute(sql);
		} catch (SQLException e) {
			try {
				createTables();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static void createTables() throws SQLException{
		Connection connection = ActiveRecordManager.getConnection();
		Statement stat = connection.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("drop table if exists pet;");
		stat.executeUpdate("create table people (id INTEGER PRIMARY KEY AUTOINCREMENT, name, job);");
		stat.executeUpdate("create table pet (id INTEGER PRIMARY KEY AUTOINCREMENT,owner INTEGER REFERENCES people(id), name, breed);");
	}
}
