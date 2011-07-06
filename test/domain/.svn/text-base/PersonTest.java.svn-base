package domain;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import persistence.ActiveRecordManager;
import persistence.ActiveRecord;
import domain.Person;
import domain.Pet;

public class PersonTest {

	private Person mike;
	private Person fred;

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
		fred = new Person("Fred", "Staff");
	}
	
	@Test
	public void implementsEquals() {
		try {
			Person.class.getDeclaredMethod("equals", Object.class);
		} catch (NoSuchMethodException e) {
			fail("Class " + Person.class.getName() + " does not implement the method equals(Object)");
		}
		
		assertTrue("Mike should be Mike before saving...", mike.equals(mike));
		
		mike.save();
		fred.save();
		
		assertFalse("Fred and Mike should not be equal.", mike.equals(fred));
		
		assertFalse(mike.equals(new Integer(10)));
	}

	@Test
	public void personCreation() {
		assertEquals(false,mike.isInDB());
		mike.save();
		assertEquals(true, mike.isInDB());
		mike.job = "arbeitslos";
		assertEquals(true,mike.save());
		
	}
	
	@Test
	public void findAll() {
		mike.save();
		fred.save();
		
		List<Person> list = Person.findAll();
		assertTrue(list.contains(mike));
		assertTrue(list.contains(fred));
	}
	
	@Test
	public void findByID() {
		mike.save();
		fred.save();
		
		Person found = Person.findByID(mike.getID());
		assertEquals(mike, found);
		
		ActiveRecord found2 = Person.findByID(fred.getID());
		assertNotSame(mike, found2);
	}
	
	@Test
	public void deletePerson() {
		mike.save();
		fred.save();
		
		mike.delete();
		
		Person found = Person.findByID(mike.getID());
		assertEquals(null, found);
		
		Person found2 = Person.findByID(fred.getID());
		assertEquals(fred, found2);
	}
	
	@Test
	public void removePet(){
		Pet fido = new Pet("Fido", "Hund");
		mike.addPet(fido);
		mike.save();
		
		mike = Person.findByID(mike.getID());
		assertNotNull(mike);
		mike.removePet(fido);
		
		assertEquals(0, mike.getPets().size());
		mike.save();
		mike = Person.findByID(mike.getID());
		assertEquals(0, mike.getPets().size());
		
		assertFalse(mike.removePet(fido));
	}
	
	@Test
	public void getPets() {
		Pet katze = new Pet("Fritz","Katze");
		Pet hund = new Pet("Fido","Hund");
				
		hund.save();
		
		mike.addPet(katze);
		mike.addPet(hund);
		mike.addPet(new Pet("Alma","Vogel"));
		
		List<Pet> plist1 = mike.getPets();
		
		assertTrue(plist1.contains(katze));
		assertTrue(plist1.contains(hund));

		mike.save();
		
		Person foundPerson = Person.findByID(mike.getID());
		
		List<Pet> foundPets = foundPerson.getPets();
		
		assertTrue(foundPets.contains(hund));
		assertEquals(3, foundPets.size());
	}

}
