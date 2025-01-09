package it.univr;

import Pages.*;
import it.univr.Controller.TimeTrackingController;
import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WorkingTimeRepository wtRepository;

    @Autowired
    private TimeTrackingController ttController;

    /*
        Il ricercatore effettua il login ed inserisce le ore giornaliere, salvandole e poi effettuando il logout
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario1() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        researcherPage = researcherPage.setDate("2024-12-12");
        researcherPage.signHours(1);

        LocalDate date = LocalDate.of(2024,12,12);

        Project p = projectRepository.findByTitle(researcherPage.getProjectTitle(1));
        Researcher r = (Researcher) userRepository.findByUsername("mot");

        WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(p,r,date);
        assertEquals(8,wt.getWorkedHours(),0);

        researcherPage.logout();
    }

    /*
        Il ricercatore effettua il login, accede ai vari timesheet del progetto e scarica il primo accessibile
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario2() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        DownloadTimesheetPage downloadTimesheetPage = researcherPage.downloadTimesheet(2);
        downloadTimesheetPage.downloadTimesheet();
        downloadTimesheetPage.logout();
    }

    /*
        Il ricercatore effettua il login e si mette in malattia per la giornata, ed è impossibilitato ad aggiungere le ore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario3(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        assertTrue(researcherPage.sliderStatus());
        researcherPage.signVacation();
        assertFalse(researcherPage.sliderStatus());
        researcherPage.logout();
    }

    /*
        Il responsabile scientifico effettua il login, seleziona un progetto ed aggiunge ricercatori a quel progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario4() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        SuperviseProjectPage superviseProjectPage = supervisorActionPage.manageResearcher(0);
        List<Researcher> listResearcher = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();
        assertEquals(6,superviseProjectPage.getResearcherSelected());
        superviseProjectPage.addResearcher(7);
        List<Researcher> listResearcherUpdated = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertNotEquals(listResearcher,listResearcherUpdated);
        assertEquals(7,superviseProjectPage.getResearcherSelected());

        superviseProjectPage.logout();
    }

    /*
        Il responsabile scientifico effettua il login, e controfirma il timesheet di un ricercatore di un progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario5() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        ValidationTimesheetPage validationTimesheetPage = supervisorActionPage.manageValidation();

        assertEquals("false",validationTimesheetPage.getStatusTimesheet(1));
        validationTimesheetPage.validateTimesheet(1);
        assertEquals("true",validationTimesheetPage.getStatusTimesheet(1));

        validationTimesheetPage.logout();
    }

    /*
        L'amministratore accede e aggiunge un utente
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario6(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());

        NewUserPage newUserPage = manageUsersPage.newUser();
        manageUsersPage = newUserPage.createUser("newUtente","123","Nuovo","Utente","UUU","Researcher");

        assertEquals(21,manageUsersPage.usersNumber());
        manageUsersPage.logout();
    }

    /*
        L'amministratore accede e aggiunge un progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void scenario7(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageProjectPage manageProjectPage = administratorPage.manageProjects();

        assertEquals(5,manageProjectPage.projectsNumber());

        NewProjectPage newProjectPage = manageProjectPage.newProject();
        manageProjectPage = newProjectPage.createProject("NewProject","CUP","Code","Univr","UNI","Tommaso Vilotto");

        assertEquals(6,manageProjectPage.projectsNumber());
        manageProjectPage.logout();
    }

    /*
        L'amministratore accede ed elimina un utente
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteUser(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());
        manageUsersPage.deleteUser(3);
        assertEquals(19,manageUsersPage.usersNumber());

        manageUsersPage.logout();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testValidationAndDownload(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        DownloadTimesheetPage downloadTimesheetPage = researcherPage.downloadTimesheet(1);
        assertFalse(downloadTimesheetPage.downloadTimesheet("12/2024"));
        researcherPage.logout();

        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        ValidationTimesheetPage validationTimesheetPage =  supervisorActionPage.manageValidation();
        assertEquals("false",validationTimesheetPage.getStatusTimesheet("12/2024"));
        validationTimesheetPage.validateTimesheet("12/2024");
        assertEquals("true",validationTimesheetPage.getStatusTimesheet("12/2024"));
        validationTimesheetPage.logout();

        researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        downloadTimesheetPage = researcherPage.downloadTimesheet(0);
        assertTrue(downloadTimesheetPage.downloadTimesheet("12/2024"));
        researcherPage.logout();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateResearcherAndLogin(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());

        NewUserPage newUserPage = manageUsersPage.newUser();
        manageUsersPage = newUserPage.createUser("jack","123","Nuovo","Utente","UUU","Researcher");

        assertEquals(21,manageUsersPage.usersNumber());
        loginPage = manageUsersPage.logout();

        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("jack","123",userRepository);
        assertEquals("Welcome RESEARCHER jack",researcherPage.getWelcomeString());
        researcherPage.logout();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateAndVerifyProject(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageProjectPage manageProjectPage = administratorPage.manageProjects();

        assertEquals(5,manageProjectPage.projectsNumber());

        NewProjectPage newProjectPage = manageProjectPage.newProject();
        manageProjectPage = newProjectPage.createProject("TestProject","1F","2025P","Univr","UNI","Mattia Vino");

        assertEquals(6,manageProjectPage.projectsNumber());
        loginPage = manageProjectPage.logout();

        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("mattia","mattevino",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        SuperviseProjectPage superviseProjectPage = supervisorActionPage.manageResearcher(1);

        List<Researcher> listResearcher = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertEquals(0,superviseProjectPage.getResearcherSelected());
        superviseProjectPage.addResearcher(0); //Sappiamo essere Nicolò Zerman
        List<Researcher> listResearcherUpdated = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertNotEquals(listResearcher,listResearcherUpdated);
        assertEquals(1,superviseProjectPage.getResearcherSelected());

        loginPage = superviseProjectPage.logout();
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("nicozerman","nicksss",userRepository);
        researcherPage.signHours(2);

        LocalDate date = LocalDate.now();
        Project p = projectRepository.findByTitle(researcherPage.getProjectTitle(2));
        Researcher r = (Researcher) userRepository.findByUsername("nicozerman");

        WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(p,r,date);
        assertEquals(8,wt.getWorkedHours(),0);
        researcherPage.logout();
    }



    /*
    delete supervisor
     */
}