package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import persistence.ActiveRecordManager;
import persistence.ActiveRecord;

public class Person extends Observable implements ActiveRecord {

	public String name, job;
	private int id = NOTINDB;
	private List<Pet> pets;

	public Person(String name, String job) {
		this.name = name;
		this.job = job;
		pets = new ArrayList<Pet>();
	}

	public Person(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getString("job"));
		this.id = row.getInt("id");
	}

	public void addPet(Pet p) {
		pets.add(p);
		p.setOwner(this);
		setChanged();
		notifyObservers(p);
	}

	public boolean removePet(Pet p) {
		if (pets.remove(p)) {
			p.delete();
			setChanged();
			notifyObservers(p);
			return true;
		}
		return false;
	}

	public List<Pet> getPets() {
		return pets;
	}
	

	@Override
	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link Person} was not successful.
	 */
	@Override
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into people (name,job) values (?,?)",
								name, job);
			else {
				ActiveRecordManager.execute(
						"UPDATE people SET name = ?, job = ? WHERE id = ?",
						name, job, Integer.toString(id));
			}
			for (Pet p : pets) {
				p.save();
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		setChanged();
		notifyObservers(this);
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Person} was not successful.
	 */
	@Override
	public boolean delete() {
		try {
			if (isInDB())
				ActiveRecordManager.execute("DELETE FROM pet WHERE owner=?;", Integer
						.toString(id));
			ActiveRecordManager.execute("DELETE FROM people WHERE id=?;", Integer
					.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		setChanged();
		notifyObservers(this);
		return true;
	}

	@Override
	public boolean isInDB() {
		return id > NOTINDB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person pers = (Person) obj;
			
			if(isInDB()){
				return id == pers.getID();
			}
			else{
				return name.equals(pers.name) && pets.equals(pers.pets) && job.equals(pers.job);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Name: " + name + " Job: " + job;
	}

	public static List<Person> findAll() {
		String sql = "select * from people;";
		List<Person> lp = ActiveRecordManager.getObjectList(sql, Person.class);
		loadPets(lp);
		return lp;
	}

	public static Person findByID(int id) {
		String sql = "select * from people WHERE id = " + id + ";";
		List<Person> res = ActiveRecordManager.getObjectList(sql, Person.class);
		if (res.isEmpty())
			return null;
		else {
			loadPets(res);
			return res.get(0);
		}
	}

	private static void loadPets(List<Person> people) {
		for (Person person : people) {
			person.pets = Pet.findByOwner(person);
			for (Pet pet : person.pets) {
				pet.setOwner(person);
			}
		}
	}

}
