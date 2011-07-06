package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import persistence.ActiveRecordManager;
import persistence.ActiveRecord;


public class Pet implements ActiveRecord {
	
	private int id = NOTINDB;
	private Person owner;
	private int ownerID;
	public String name, breed;
	
	public Pet(String name, String breed) {
		this.name = name;
		this.breed = breed;
	}
	
	public Pet(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getString("breed"));
		this.id = row.getInt("id");
		this.ownerID = row.getInt("owner");
	}
	
	public void setOwner(Person owner) {
		this.owner = owner;
		ownerID = owner.getID();
	}
	
	public Person getOwner(){
		return owner;
	}
	
	public int getOwnerID() {
		return ownerID;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean isInDB() {
		return id > NOTINDB;
	}

	@Override
	public boolean save() {
		if(owner == null || !owner.isInDB())
			return false;
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert("insert into pet (name,breed,owner) values (?,?,?)",name,breed,Integer.toString(owner.getID()));
			else
				ActiveRecordManager.execute("UPDATE pet SET owner = ?, name = ?, breed = ? WHERE id = ?",Integer.toString(owner.getID()),name,breed,Integer.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean delete() {
		try {
			if (isInDB())
				ActiveRecordManager.execute("DELETE FROM pet WHERE id = ?", Integer.toString(id));
		} catch (SQLException e) {
			return false;
		}
		return true;	
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pet) {
			Pet otherPet = (Pet) obj;
			
			if(isInDB()){
				return id == otherPet.getID();
			}
			else{
				return name.equals(otherPet.name) && breed.equals(otherPet.breed);
			}
		}
		return false;
	}
	
	
	public static List<Pet> findAll() {
		String sql = "SELECT * FROM pet;";
		return ActiveRecordManager.getObjectList(sql, Pet.class);
	}
	
	public static Pet findByID(int id) {
		String sql = "select * from pet WHERE id = " + id + ";";
		return ActiveRecordManager.getObjectList(sql,Pet.class).get(0);
	}
	
	public static List<Pet> findByOwner(Person owner) {
		String sql = "select * from pet WHERE owner = " + owner.getID() + ";";
		return ActiveRecordManager.getObjectList(sql,Pet.class);
		
	}
	@Override
	public String toString() {
		return "Tier ID: " + id + " Name: " + name + " Rasse: " + breed;
	}

	public void loadOwner() {
		owner = Person.findByID(ownerID);
	}
}
