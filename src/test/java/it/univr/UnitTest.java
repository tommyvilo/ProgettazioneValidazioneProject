package it.univr;
import it.univr.Model.Project;
import it.univr.Model.User.Administrator;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Model.User.Utente;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void testUserResearcherUnits(){
        Researcher r = new Researcher("nicozerman","123toor","Nicolò","Zerman","ZRMNCL02S19L781E");

        assertNull(r.getId());

        assertEquals("nicozerman",r.getUsername());
        r.setUsername("nico");
        assertEquals("nico",r.getUsername());

        assertEquals("123toor",r.getPassword());
        r.setPassword("123");
        assertEquals("123",r.getPassword());

        assertEquals("Nicolò",r.getName());
        r.setName("Nico");
        assertEquals("Nico",r.getName());

        assertEquals("Zerman",r.getSurname());
        r.setSurname("Zerm");
        assertEquals("Zerm",r.getSurname());

        assertEquals("ZRMNCL02S19L781E",r.getCf());
        r.setCf("ZRMNCL02S19L781R");
        assertEquals("ZRMNCL02S19L781R",r.getCf());

        assertEquals("Researcher [id=null, username=nico, name=Nico, surname=Zerm, cf=ZRMNCL02S19L781R]",r.toString());
    }

    @Test
    public void testSupervisorUnits(){
        Supervisor s = new Supervisor("nicozerman","123toor","Nicolò","Zerman","ZRMNCL02S19L781E");
        assertEquals("Supervisor [id=null, username=nicozerman, name=Nicolò, surname=Zerman, cf=ZRMNCL02S19L781E]",s.toString());
    }

    @Test
    public void testAdministratorUnits(){
        Administrator a = new Administrator("nicozerman","123toor","Nicolò","Zerman","ZRMNCL02S19L781E");
        assertEquals("Administrator [id=null, username=nicozerman, name=Nicolò, surname=Zerman, cf=ZRMNCL02S19L781E]",a.toString());
    }

    @Test
    public void testProjectUnits(){
        Project p = new Project("SmartCity","123456789","2025F","Univr","UVR25");

        assertNull(p.getId());
        assertEquals("SmartCity",p.getTitle());
        p.setTitle("Smart");
        assertEquals("Smart",p.getTitle());

        assertEquals("123456789",p.getCup());
        p.setCup("12345");
        assertEquals("12345",p.getCup());

        assertEquals("2025F",p.getCode());
        p.setCode("2025");
        assertEquals("2025",p.getCode());

        assertEquals("Univr",p.getDenominazioneSoggetto());
        p.setDenominazioneSoggetto("univr");
        assertEquals("univr",p.getDenominazioneSoggetto());

        assertEquals("UVR25",p.getCfSoggetto());
        p.setCfSoggetto("UVR");
        assertEquals("UVR",p.getCfSoggetto());

        Supervisor s = new Supervisor("nicozerman","123toor","Nicolò","Zerman","ZRMNCL02S19L781E");
        Researcher r = new Researcher("tommyvilo","tom","Tommaso","Vilotto","VLTTMS02B18F861N");

        p.setSupervisor(s);
        assertEquals(p.getSupervisor(),s);

        ArrayList<Researcher> researchers = new ArrayList<Researcher>();
        p.setResearchers(new ArrayList<Researcher>());
        p.addResearcher(r);
        researchers.add(r);
        assertEquals(researchers,p.getResearchers());

        assertEquals("Project{id=null, title='Smart', cup='12345', code='2025', denominazioneSoggetto='univr', cfSoggetto='UVR', supervisor=Supervisor [id=null, username=nicozerman, name=Nicolò, surname=Zerman, cf=ZRMNCL02S19L781E], researchers=[Researcher [id=null, username=tommyvilo, name=Tommaso, surname=Vilotto, cf=VLTTMS02B18F861N]]}",p.toString());

    }

    @Test
    public void testWorkingTimeUnits() {
        Researcher r = new Researcher("nicozerman","123toor","Nicolò","Zerman","ZRMNCL02S19L781E");
        Project p = new Project("SmartCity","123456789","2025F","Univr","UVR25");
        LocalDate date = LocalDate.of(2024,12,25);
        WorkingTime wt = new WorkingTime(r,p, date,5,false,false);

        assertNull(wt.getId());

        assertEquals(r,wt.getResearcher());
        Researcher r2 = new Researcher("tom","123toor","Tommaso","Vilotto","VLTTMS02B18F861N");
        wt.setResearcher(r2);
        assertEquals(r2,wt.getResearcher());

        assertEquals(p,wt.getProject());
        Project p2 = new Project("QuantumLab", "5J9LMNO", "5D", "UniTO", "35872");
        wt.setProject(p2);
        assertEquals(p2,wt.getProject());

        assertEquals(date,wt.getDate());
        LocalDate newDate = LocalDate.of(2025,1,1);
        wt.setDate(newDate);
        assertEquals(newDate,wt.getDate());

        assertEquals(5,wt.getWorkedHours(),0);
        wt.setWorkedHours(3);
        assertEquals(3,wt.getWorkedHours(),0);

        assertFalse(wt.getValidated());
        wt.setValidated(true);
        assertTrue(wt.getValidated());

        assertFalse(wt.getLeave());
        wt.setLeave(true);
        assertTrue(wt.getLeave());

        assertEquals("01/2025",wt.getMonthYear());
        assertEquals("3:00",wt.getFormattedHours());
    }
}
