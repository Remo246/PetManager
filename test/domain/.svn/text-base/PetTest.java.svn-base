package domain;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import persistence.ActiveRecordManager;
import domain.Person;
import domain.Pet;


public class PetTest {

	
	private Person mike;
	private Pet cat;
	private Pet dog;
	private Person fred;
	private Pet frog;
	private Pet duck;

	@Before
	public void setUp() throws Exception {
		ActiveRecordManager.setDatabase("jdbc:sqlite:db/test.db");
		Connection conn = ActiveRecordManager.getConnection();
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("drop table if exists pet;");
		stat.executeUpdate("create table people (id INTEGER PRIMARY KEY AUTOINCREMENT, name, job);");
		stat.executeUpdate("create table pet (id INTEGER PRIMARY KEY AUTOINCREMENT,owner INTEGER REFERENCES people(id), name, breed);");
		
		
		mike = new Person("Mike", "Chief");
		cat = new Pet("Fritz","Katze");
		dog = new Pet("Fido","Hund");
		
		fred = new Person("Fred", "Staff");
		frog = new Pet("Heino","Reptil");
		duck = new Pet("Donald","Ente");
	}
	
	@Test
	public void implementsEquals() {
		try {
			Pet.class.getDeclaredMethod("equals", Object.class);
		} catch (NoSuchMethodException e) {
			fail("Class " + Pet.class.getName() + " does not implement the method equals(Object)");
		}
		
		assertFalse(duck.equals(new Float(2f)));
	}
	
	@Test
	public void petCreation() {
		mike.addPet(cat);
		mike.addPet(dog);
		
		assertEquals(false, mike.isInDB());
		assertEquals(false,cat.isInDB());
		assertEquals(false,dog.isInDB());
		String fehlerConstr = "Haustier wurde gespeichert obwohl die person noch nicht in DB exisiert. Kein DB Constraint vorhanden";
		assertFalse(fehlerConstr,cat.save());
		assertFalse(fehlerConstr,dog.save());
		assertEquals(fehlerConstr,false,cat.isInDB());
		assertEquals(fehlerConstr,false,dog.isInDB());
		
		mike.save();
		String fehlerSpeichern = "Haustier wurde nicht gespeichert.";
		assertTrue(fehlerSpeichern,cat.save());
		assertTrue(fehlerSpeichern,dog.save());
		assertEquals(fehlerSpeichern,true,cat.isInDB());
		assertEquals(fehlerSpeichern,true,dog.isInDB());

	}
	
	@Test
	public void findAll() {
		mike.addPet(cat);
		mike.addPet(dog);
		
		fred.addPet(frog);
		fred.addPet(duck);
		
		mike.save();
		fred.save();
		
		cat.save();
		dog.save();
		frog.save();
		duck.save();
		
		List<Pet> list = Pet.findAll();
		assertTrue(list.contains(dog));
		assertTrue(list.contains(frog));
		
	}
	
	@Test
	public void ownership(){
		mike.addPet(cat);
		mike.addPet(duck);
		
		mike.save();
		
		duck = Pet.findByID(duck.getID());
		assertEquals(mike.getID(), duck.getOwnerID());
		assertNull(duck.getOwner());
		
		duck.loadOwner();
		assertEquals(mike, duck.getOwner());
		
		frog.loadOwner();
		assertNull(frog.getOwner());
	}
	
	@Test
	public void findByID() {
		mike.addPet(cat);
		mike.addPet(dog);
		
		fred.addPet(frog);
		fred.addPet(duck);
		
		mike.save();
		fred.save();
		
		cat.save();
		dog.save();
		frog.save();
		duck.save();
		
		List<Pet> list = Pet.findAll();
		assertTrue(list.contains(dog));
		assertTrue(list.contains(frog));
		
		Pet found = Pet.findByID(cat.getID());
		assertEquals(cat, found);
		
		Pet found2 = Pet.findByID(duck.getID());
		assertNotSame(duck, found2);
		
	}

}
