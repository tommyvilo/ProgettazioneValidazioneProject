package it.univr;

import Pages.*;
import it.univr.Controller.TimeTrackingController;
import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Time;
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
        Il ricercatore inserisce le ore giornaliere, per poi salvarle
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInsertWorkingHourByResearcher() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("nicozerman","nicksss",userRepository);
        researcherPage = researcherPage.setDate("2024-12-12");
        researcherPage.signHours(1);

        LocalDate date = LocalDate.of(2024,12,12);

        Project p = projectRepository.findByTitle(researcherPage.getProjectTitle(1));
        Researcher r = (Researcher) userRepository.findByUsername("nicozerman");

        WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(p,r,date);
        assertEquals(8,wt.getWorkedHours(),0);

        researcherPage.logout();
    }

    /*
        Il responsabile scientifico inserisce le ore giornaliere, per poi salvarle
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInsertWorkingHourBySupervisor() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        supervisorPage = supervisorPage.setDate("2024-12-12");
        supervisorPage.signHours(0);

        LocalDate date = LocalDate.of(2024,12,12);

        Project p = projectRepository.findByTitle(supervisorPage.getProjectTitle(0));
        Supervisor r = (Supervisor) userRepository.findByUsername("tom");

        WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(p,r,date);
        assertEquals(8,wt.getWorkedHours(),0);

        supervisorPage.logout();
    }

    /*
        Il ricercatore accede ai vari timesheet del progetto e ne scarica uno acessibile
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDownloadTimesheetByResearcher() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        DownloadTimesheetPage downloadTimesheetPage = researcherPage.downloadTimesheet(1);
        downloadTimesheetPage.downloadTimesheet();
        downloadTimesheetPage.logout();
    }

    /*
        Il responsabile scientifico accede ai vari timesheet del progetto e ne scarica uno acessibile
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDownloadTimesheetBySupervisor() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        DownloadTimesheetPage downloadTimesheetPage = supervisorPage.downloadTimesheet(0);
        downloadTimesheetPage.downloadTimesheet();
        downloadTimesheetPage.logout();
    }

    /*
        Il ricercatore si mette in malattia per la giornata, ed è impossibilitato ad aggiungere le ore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testMalattiaWorkingTime(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        researcherPage = researcherPage.setDate("2024-12-12");
        assertTrue(researcherPage.sliderStatus());
        researcherPage.signVacation();
        assertFalse(researcherPage.sliderStatus());
        researcherPage.logout();
    }

    /*
        Il responsabile scientifico seleziona un progetto ed aggiunge un ricercatore a quel progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testAddingResearcherToProject() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        SuperviseProjectPage superviseProjectPage = supervisorActionPage.manageResearcher(0);
        List<Researcher> listResearcher = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();
        assertEquals(6,superviseProjectPage.getResearcherSelected());
        superviseProjectPage.selectAndSaveResearcher(7);
        List<Researcher> listResearcherUpdated = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertNotEquals(listResearcher,listResearcherUpdated);
        assertEquals(7,superviseProjectPage.getResearcherSelected());


        superviseProjectPage.logout();
    }

    /*
        Il responsabile scientifico seleziona un progetto e rimuove un ricercatore da quel progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testRemovingResearcherFromProject() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        SuperviseProjectPage superviseProjectPage = supervisorActionPage.manageResearcher(0);
        List<Researcher> listResearcher = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();
        assertEquals(6,superviseProjectPage.getResearcherSelected());

        superviseProjectPage.selectAndSaveResearcher(0);
        List<Researcher> listResearcherUpdated = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertNotEquals(listResearcher,listResearcherUpdated);
        assertEquals(5,superviseProjectPage.getResearcherSelected());


        superviseProjectPage.logout();
    }

    /*
        Il responsabile scientifico controfirma e fa il download di un timesheet di un ricercatore del progetto che supervisiona
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testValidateAndDownloadTimesheet() {
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        ValidationTimesheetPage validationTimesheetPage = supervisorActionPage.manageValidation();

        assertEquals("false",validationTimesheetPage.getStatusTimesheet(1));
        validationTimesheetPage.validateTimesheet(1);
        assertEquals("true",validationTimesheetPage.getStatusTimesheet(1));
        validationTimesheetPage.downloadTimesheet(1);

        validationTimesheetPage.logout();
    }

    /*
        L'amministratore aggiunge un utente ricercatore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateResearcher(){
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
        L'amministratore aggiunge un progetto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateProject(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageProjectPage manageProjectPage = administratorPage.manageProjects();

        assertEquals(6,manageProjectPage.projectsNumber());

        NewProjectPage newProjectPage = manageProjectPage.newProject();
        manageProjectPage = newProjectPage.createProject("NewProject","CUP","Code","Univr","UNI","Tommaso Vilotto");

        assertEquals(7,manageProjectPage.projectsNumber());
        manageProjectPage.logout();
    }

    /*
        L'amministratore elimina un utente ricercatore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteResearcher(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());
        manageUsersPage.deleteUser(3);
        assertEquals(19,manageUsersPage.usersNumber());

        manageUsersPage.logout();
    }

    /*
      L'amministratore accede ed elimina un supervisore
   */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteSupervisor(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());
        manageUsersPage.deleteUser("tom");
        assertEquals(19,manageUsersPage.usersNumber());

        loginPage = manageUsersPage.logout();

        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        assertFalse(researcherPage.isThereProject("NeuroPlus")); //progetto il cui supervisore era il 15esimo
        manageUsersPage.logout();
    }

    /*
        Il ricercatore nota che non c'è un timesheet scaricabile. Successivamente il supervisore di uno dei suoi progetti valida il timesheet. Infine il ricercatore scarica il timesheet validato.
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testValidationAndDownload(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        DownloadTimesheetPage downloadTimesheetPage = researcherPage.downloadTimesheet(0);
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

    /*
        L'amministratore crea un utente ricercatore, successivamente quest'ultimo accede alla piattaforma
     */
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

    /*

        L'amministratore aggiunge un progetto con rispettivo supervisor, successivamente il supervisor aggiunge un ricercatore al progetto,
        infine il ricercatore designato accede e visualizza il nuovo progetto che gli è stato aggiunto
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateAndVerifyProject(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageProjectPage manageProjectPage = administratorPage.manageProjects();

        assertEquals(6,manageProjectPage.projectsNumber());

        NewProjectPage newProjectPage = manageProjectPage.newProject();
        manageProjectPage = newProjectPage.createProject("TestProject","1F","2025P","Univr","UNI","Mattia Vino");

        assertEquals(7,manageProjectPage.projectsNumber());
        loginPage = manageProjectPage.logout();

        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("mattia","mattevino",userRepository);
        SupervisorActionPage supervisorActionPage = supervisorPage.goToSupervisorAction();
        SuperviseProjectPage superviseProjectPage = supervisorActionPage.manageResearcher(1);

        List<Researcher> listResearcher = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertEquals(0,superviseProjectPage.getResearcherSelected());
        superviseProjectPage.selectAndSaveResearcher(0); //Sappiamo essere Nicolò Zerman
        List<Researcher> listResearcherUpdated = projectRepository.findById(Long.parseLong(superviseProjectPage.getProjectId())).getResearchers();

        assertNotEquals(listResearcher,listResearcherUpdated);
        assertEquals(1,superviseProjectPage.getResearcherSelected());

        loginPage = superviseProjectPage.logout();
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("nicozerman","nicksss",userRepository);
        researcherPage = researcherPage.setDate("2024-12-12");
        researcherPage.signHours(2);

        LocalDate date = LocalDate.of(2024,12,12);
        Project p = projectRepository.findByTitle(researcherPage.getProjectTitle(2));
        Researcher r = (Researcher) userRepository.findByUsername("nicozerman");

        WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(p,r,date);
        assertEquals(8,wt.getWorkedHours(),0);
        researcherPage.logout();
    }

    /*
       Test password invalida
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testFailedLogin(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        loginPage = (LoginPage) loginPage.login("mot","tom",userRepository);
        assertEquals("Wrong password",loginPage.getErrorMessage());
    }

    /*
       Test redirect per url invalida, lato ricercatore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInvalidUrlResearcher(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        ResearcherPage researcherPage = (ResearcherPage) loginPage.login("mot","mot",userRepository);
        researcherPage = researcherPage.goTo("http://localhost:8080/supervisor");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/supervisorAction");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/superviseProject?id=1");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/saveProjectResearcher?idResearchers=1&projectId=1");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/validationTimesheet?id=1");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/administrator");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/administrator");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/manageUsers");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/manageProjects");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage = researcherPage.goTo("http://localhost:8080/deleteUser?id=15");
        assertEquals("Welcome RESEARCHER mot",researcherPage.getWelcomeString());
        researcherPage.logout();
    }

    /*
       Test redirect per url invalida, lato supervisore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInvalidUrlSupervisor(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        SupervisorPage supervisorPage = (SupervisorPage) loginPage.login("tom","tom",userRepository);
        supervisorPage = supervisorPage.goTo("http://localhost:8080/researcher");
        assertEquals("Welcome SUPERVISOR tom",supervisorPage.getWelcomeString());
        supervisorPage = supervisorPage.goTo("http://localhost:8080/administrator");
        assertEquals("Welcome SUPERVISOR tom",supervisorPage.getWelcomeString());
        supervisorPage = supervisorPage.goTo("http://localhost:8080/manageUsers");
        assertEquals("Welcome SUPERVISOR tom",supervisorPage.getWelcomeString());
        supervisorPage = supervisorPage.goTo("http://localhost:8080/manageProjects");
        assertEquals("Welcome SUPERVISOR tom",supervisorPage.getWelcomeString());
        supervisorPage = supervisorPage.goTo("http://localhost:8080/deleteUser?id=15");
        assertEquals("Welcome SUPERVISOR tom",supervisorPage.getWelcomeString());
        supervisorPage.logout();
    }

    /*
       Test redirect per url invalida, lato utente non loggato
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInvalidUrlNotLoggedIn(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        loginPage = loginPage.goTo("http://localhost:8080/index");
        assertEquals("Login",loginPage.getLoginTitle());
    }

    /*
       Test redirect per url invalida, lato amministratore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testInvalidUrlAdministrator(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        administratorPage.goTo("http://localhost:8080/researcher");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/saveWorkingTime?selectedDate=2025-01-10&hours=0&hours=5.25&hours=0");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/downloadTimesheet?id=1");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/supervisor");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/supervisorAction");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/superviseProject?id=1");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.goTo("http://localhost:8080/saveProjectResearcher?idResearchers=1&projectId=1");
        assertEquals("Welcome ADMINISTRATOR",administratorPage.getWelcomeString());
        administratorPage.logout();
    }

    /*
        L'amministratore accede e aggiunge un utente supervisore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateSupervisor(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        ManageUsersPage manageUsersPage = administratorPage.manageUsers();

        assertEquals(20,manageUsersPage.usersNumber());

        NewUserPage newUserPage = manageUsersPage.newUser();
        manageUsersPage = newUserPage.createUser("newUtente","123","Nuovo","Utente","UUU","Supervisor");

        assertEquals(21,manageUsersPage.usersNumber());
        manageUsersPage.logout();
    }

    /*
        Viene testata la pagina di errore
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testErrorPage(){
        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        AdministratorPage administratorPage = (AdministratorPage) loginPage.login("admin","admin",userRepository);
        
        driver.get("http://localhost:8080/paginaInesistente");
        ErrorPage errorPage = new ErrorPage(driver);
        assertEquals("Oops! The page you're looking for doesn't exist.",errorPage.getErrorMessage());
    }

}