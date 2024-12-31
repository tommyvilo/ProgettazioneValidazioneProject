package it.univr;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.univr.User.Administrator;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import it.univr.User.Utente;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.List;

@Controller
public class TimeTrackingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;
    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @PostConstruct
    public void init() {
        Researcher r1 = new Researcher("nicozerman","zermaculo","Nicolò","Zerman","ZRMNCL02S19L781E");
        Researcher r2 = new Researcher("lucasrin", "lucarino", "Luca", "Rinaldi", "RNLCLS01D13L781X");
        Researcher r3 = new Researcher("alessandrab1", "alessandrabb", "Alessandra", "Bianchi", "BNCHLD01E44F861Z");
        Researcher r4 = new Researcher("giovanniferrari", "gioferr", "Giovanni", "Ferrari", "FRRGNN03C19A481S");
        Researcher r5 = new Researcher("serenacapu", "serenacaputo", "Serena", "Caputo", "CPTSRN01B25L761Q");
        Researcher r6 = new Researcher("marcofiore", "marcofiore", "Marco", "Fiore", "FIRMRC01D22N484S");
        Researcher r7 = new Researcher("martina", "martabianchi", "Martina", "Bianchi", "BNCHTN02M55A922L");
        Researcher r8 = new Researcher("lorenzomassimo", "lorenzomassimo", "Lorenzo", "Massimo", "MSSLNZ03L19D919M");
        Researcher r9 = new Researcher("giuliasorriso", "giuliasorriso", "Giulia", "Sorriso", "SRRGIA01P63D876R");
        Researcher r10 = new Researcher("marcoantonio", "marcoantonio", "Marco", "Antonio", "MTNTMR02R15F381A");
        Researcher r11 = new Researcher("giuliafranceschi", "giuliafranceschi", "Giulia", "Franceschi", "FRNCGL02E45N001J");
        Researcher r12 = new Researcher("andreaedvige", "andreaedvige", "Andrea", "Edvige", "EDVADR01C13L989M");
        Researcher r13 = new Researcher("mariateresagrazi", "mariateresagrazi", "Maria Teresa", "Graziani", "GRZMTT01S77B124C");
        Researcher r14 = new Researcher("francescotommaso", "francescotommaso", "Francesco", "Tommaso", "TMMSFC02D23R123A");
        Researcher r15 = new Researcher("mot", "mot", "Sergio", "Ruolo", "RLSRGX01A01B456K");

        userRepository.save(r1);
        userRepository.save(r2);
        userRepository.save(r3);
        userRepository.save(r4);
        userRepository.save(r5);
        userRepository.save(r6);
        userRepository.save(r7);
        userRepository.save(r8);
        userRepository.save(r9);
        userRepository.save(r10);
        userRepository.save(r11);
        userRepository.save(r12);
        userRepository.save(r13);
        userRepository.save(r14);
        userRepository.save(r15);

        Supervisor s1 = new Supervisor("tom","tom","Tommaso","Vilotto","VLTTMS02B18F861N");
        Supervisor s2 = new Supervisor("mattia", "mattevino", "Mattia", "Vino", "VNTMTT01C25L591A");
        Supervisor s3 = new Supervisor("alessandra", "alesforza", "Alessandra", "Forza", "FRZLSN01S57B849P");
        Supervisor s4 = new Supervisor("davide", "davidevitali", "Davide", "Vitali", "VTLDVD01S43F031S");
        Supervisor s5 = new Supervisor("francescospano", "francescospano", "Francesco", "Spano", "SPNFRS01M15E493D");

        userRepository.save(s1);
        userRepository.save(s2);
        userRepository.save(s3);
        userRepository.save(s4);
        userRepository.save(s5);

        Administrator a1 = new Administrator("admin","admin","Gianfranco","Sulla Riva","GFTATS02B18F861N");
        Administrator a2 = new Administrator("superadmin", "superadmin", "Giovanni", "Rossi", "RSSGVN02H18A820L");
        Administrator a3 = new Administrator("support", "support123", "Alessandra", "Bruni", "BRNLSR01K29F481X");

        userRepository.save(a1);
        userRepository.save(a2);
        userRepository.save(a3);

        Project p1 = new Project("NeuroPlus", "1S4DFFG", "1F", "Univr", "97823");
        Project p2 = new Project("BioMedX", "2F5GHHK", "2A", "Polimi", "31425");
        Project p3 = new Project("GreenTech", "3G7HJKL", "3B", "Sapienza", "14276");
        Project p4 = new Project("SmartCity", "4H8KLMN", "4C", "Unipd", "25094");
        Project p5 = new Project("QuantumLab", "5J9LMNO", "5D", "UniTO", "35872");

        p1.setSupervisor(s1);
        p2.setSupervisor(s2);
        p3.setSupervisor(s3);
        p4.setSupervisor(s4);
        p5.setSupervisor(s5);

        p1.addResearcher(r1);
        p1.addResearcher(r2);
        p1.addResearcher(r3);
        p1.addResearcher(r4);
        p1.addResearcher(r5);

        p2.addResearcher(r6);
        p2.addResearcher(r7);
        p2.addResearcher(r8);
        p2.addResearcher(r9);
        p2.addResearcher(r10);

        p3.addResearcher(r11);
        p3.addResearcher(r12);
        p3.addResearcher(r13);
        p3.addResearcher(r14);
        p3.addResearcher(r15);

        p4.addResearcher(r1);
        p4.addResearcher(r2);
        p4.addResearcher(r6);
        p4.addResearcher(r8);
        p4.addResearcher(r15);

        p5.addResearcher(r3);
        p5.addResearcher(r4);
        p5.addResearcher(r9);
        p5.addResearcher(r10);
        p5.addResearcher(r13);

        p1.addResearcher(r15);

        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);
        projectRepository.save(p4);
        projectRepository.save(p5);

        WorkingTime w1 = new WorkingTime(r15,p1, LocalDate.now(),2,false,false);
        WorkingTime w6 = new WorkingTime(r15,p1, LocalDate.of(2024,11,10),2,false,false);
        WorkingTime w7 = new WorkingTime(r15,p1, LocalDate.of(2024,12,10),2,false,false);
        WorkingTime w2 = new WorkingTime(r15,p3, LocalDate.now(),2.5,false,false);
        WorkingTime w3 = new WorkingTime(r15,p4, LocalDate.now(),3,false,false);
        WorkingTime w5 = new WorkingTime(r15,p4, LocalDate.of(2024,12,10),4,false,false);
        WorkingTime w4 = new WorkingTime(r15,p4, LocalDate.of(2024,11,19),8,true,false);

        wtRepository.save(w1);
        wtRepository.save(w2);
        wtRepository.save(w3);
        wtRepository.save(w4);
        wtRepository.save(w5);
        wtRepository.save(w6);
        wtRepository.save(w7);
    }

    @PostMapping("/print")
    public String print(){
        for (Utente utente: userRepository.findAll()){
            System.out.println(utente);

        }

        for (Project project: projectRepository.findAll()){
            System.out.println(project);
        }
        return "login";
    }

    @RequestMapping("/")
    public String principale(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return "redirect:/index";
        }
        return "login";}

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    cookie.setValue(null);  // Imposta il valore del cookie a null
                    cookie.setMaxAge(0);    // Imposta l'età del cookie a 0 per eliminarlo
                    cookie.setPath("/");    // Assicurati che il cookie venga rimosso per tutto il dominio
                    response.addCookie(cookie);  // Aggiungi il cookie modificato nella risposta
                }
            }
        }
        return "login";}

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    cookie.setValue(null);  // Imposta il valore del cookie a null
                    cookie.setMaxAge(0);    // Imposta l'età del cookie a 0 per eliminarlo
                    cookie.setPath("/");    // Assicurati che il cookie venga rimosso per tutto il dominio
                    response.addCookie(cookie);  // Aggiungi il cookie modificato nella risposta
                }
            }
        }
        return "redirect:/login";}

    @RequestMapping("/supervisor")
    public String supervisor(HttpServletRequest request, Model model) {

        if(!isValidUrl("supervisor",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("projects", projectRepository.findAllBySupervisor((Supervisor)userRepository.findByUsername(cookie.getValue())));
        model.addAttribute("username", cookie.getValue());
        return "supervisor";
    }

    @RequestMapping("/researcher")
    public String researcher(HttpServletRequest request, Model model) {
        if(!isValidUrl("researcher",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());
        LocalDate date = LocalDate.now();

        for(Project project : projectRepository.findAllByResearchersContains(researcher)){
            if (wtRepository.getWorkingTimeByProjectAndResearcherAndDate(project,researcher,date)==null){
                wtRepository.save(new WorkingTime(researcher,project,date,0,false,false));
            }
        }

        model.addAttribute("hours", wtRepository.findByDateAndResearcher(date,researcher));
        model.addAttribute("username", cookie.getValue());
        if(wtRepository.getTopByResearcherAndDate(researcher, date)==null){
            model.addAttribute("status",false);
        }else{
            model.addAttribute("status", wtRepository.getTopByResearcherAndDate(researcher,date).getLeave());
        }
        model.addAttribute("isHoliday",isHoliday(date));
        return "researcher";
    }

    @RequestMapping("/saveWorkingTime")
    public String saveWorkingTime(HttpServletRequest request, Model model, @RequestParam(name="hours", required=true) List<Double> hours, @RequestParam(name="checkbox", required=false) String checkbox) {
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());
        Iterable<Project> listaProject = wtRepository.findProjectsByDateAndResearcher(LocalDate.now(),researcher);

        if(checkbox==null){
            int counter = 0;
            for(Project pj : listaProject){
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndResearcherAndDate(pj,researcher,LocalDate.now());
                wt.setWorkedHours(hours.get(counter));
                wt.setLeave(false);
                wtRepository.save(wt);
                counter++;
            }
        } else {
            for(Project pj : listaProject){ //settings all working time as 0 hours
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndResearcherAndDate(pj,researcher,LocalDate.now());
                //wtRepository.deleteByResearcherAndDateAndProject(researcher,LocalDate.now(),pj);
                wtRepository.deleteById(wt.getId());
                wtRepository.save(new WorkingTime(researcher,pj,LocalDate.now(),0,false,true));
            }
        }
        return "redirect:/researcher";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request, @RequestParam(name="id") Long id) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }

        Researcher researcher = (Researcher)userRepository.findById(id).orElse(null);

        for(WorkingTime wt: wtRepository.findByResearcher(researcher)){
            wtRepository.deleteById(wt.getId());
        }

        for(Project pj: projectRepository.findAllByResearchersContains(researcher)){
            List<Researcher> researchers = pj.getResearchers();
            researchers.remove(researcher);
            pj.setResearchers(researchers);
            projectRepository.save(pj);
        }

        userRepository.deleteById(id);
        return "redirect:/administrator";
    }

    @RequestMapping("/saveProjectResearcher")
    public String saveProjectResearcher(HttpServletRequest request, Model model, @RequestParam(name="idResearchers", required=true) List<Long> ids, @RequestParam(name="projectId", required=true) Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        List<Long> currentResearchersProjectIds = project.getResearchers().stream().map(Researcher::getId).toList();
        List<Researcher> newResearchers = new ArrayList<>();

        for(Long id : ids){
            Researcher current = (Researcher) userRepository.findById(id).orElse(null);
            if (currentResearchersProjectIds.contains(id) && !ids.contains(id)) {
                wtRepository.deleteByProjectAndResearcher(project,current);
            }
            else{
                newResearchers.add(current);
            }
        }

        project.setResearchers(newResearchers);
        projectRepository.save(project);

        return "redirect:/projectmanagement?id="+projectId;
    }

    @RequestMapping("/downloadtimesheet")
    public String downloadtimesheet(HttpServletRequest request, Model model, @RequestParam(name="id", required = true) long id) {
        if(!isValidUrl("researcher",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        Project project = projectRepository.findById(id);
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());

        model.addAttribute("project", project);
        model.addAttribute("validatedMonths",getWorkedMonth(project, researcher));
        model.addAttribute("username", cookie.getValue());
        return "downloadtimesheet";
    }

    @RequestMapping("/downloadMonthlyTimesheet")
    public String downloadMonthlyTimesheet(HttpServletRequest request, Model model, @RequestParam(name = "idProject", required = true) long idProject,
                                           @RequestParam(name = "date", required = true) String monthYear,
                                           @RequestParam(name = "researcherUser", required = true) String username) {
        Cookie cookie = getCookieByName(request, "userLoggedIn");

        int month = Integer.parseInt(monthYear.split("/")[0]);
        int year = Integer.parseInt(monthYear.split("/")[1]);
        YearMonth yM = YearMonth.of(year, month);
        int totalDays = yM.lengthOfMonth();
        Researcher researcher = (Researcher)userRepository.findByUsername(username);
        Project project = projectRepository.findById(idProject);
        Supervisor supervisor = project.getSupervisor();
        Map<Integer,Double> totalHoursPerDay = new HashMap<>(); //giorno : totale ore lavorate



        String outputFilePath = "TimeTrackingReport.pdf";

        try {
            // Creazione del documento
            Document document = new Document(PageSize.A4.rotate()); // Orientamento orizzontale
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));

            document.open();

            // Titolo principale e mese/anno
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("TIMESHEET PER RENDICONTAZIONE PERSONALE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font dateFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Paragraph monthYearParagraph = new Paragraph("Mese: " + month + " / Anno: " + year, dateFont);
            monthYearParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(monthYearParagraph);

            // Tabella dati progetto
            PdfPTable projectTable = new PdfPTable(2);
            projectTable.setWidthPercentage(100);
            projectTable.setSpacingBefore(10f);
            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 10);

            projectTable.addCell(new PdfPCell(new Phrase("Titolo del progetto", headerFont)));
            projectTable.addCell(new PdfPCell(new Phrase("PRIN 2022: Smartitude - Automated Testing and Security Assessment of Smart Contracts", normalFont)));
            projectTable.addCell(new PdfPCell(new Phrase("CUP del progetto", headerFont)));
            projectTable.addCell(new PdfPCell(new Phrase("B53D23012750006", normalFont)));
            projectTable.addCell(new PdfPCell(new Phrase("Codice del progetto", headerFont)));
            projectTable.addCell(new PdfPCell(new Phrase("202233YFCJ", normalFont)));
            projectTable.addCell(new PdfPCell(new Phrase("Denominazione soggetto", headerFont)));
            projectTable.addCell(new PdfPCell(new Phrase("Università degli Studi di Verona - Dipartimento di Informatica", normalFont)));



            // Riga "Figura professionale" in una tabella
            PdfPTable professionalRoleTable = new PdfPTable(1);
            professionalRoleTable.setWidthPercentage(100);
            professionalRoleTable.setSpacingBefore(0f);
            PdfPCell professionalRoleCell = new PdfPCell(new Phrase("Figura professionale", headerFont));
            professionalRoleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            professionalRoleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            professionalRoleTable.addCell(professionalRoleCell);

            // Tabella informazioni ricercatore
            PdfPTable personalInfoTable = new PdfPTable(4); // 4 colonne per nome, cognome, codice fiscale, ore totali
            personalInfoTable.setWidthPercentage(100);
            personalInfoTable.setSpacingBefore(0f);

            personalInfoTable.addCell(new PdfPCell(new Phrase("Nome", headerFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getName(), normalFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase("Cognome", headerFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getSurname(), normalFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase("Codice fiscale", headerFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getCf(), normalFont)));
            personalInfoTable.addCell(new PdfPCell(new Phrase("Ore totali rendicontate", headerFont)));
            //ORE TOTALI AGGIUNTE ALLA FINE

            // Tabella ore giornaliere
            float[] columnWidths = new float[totalDays+2];
            columnWidths[0] = 2f; // Colonna "Day"
            for (int i = 1; i < totalDays + 1; i++) {
                columnWidths[i] = 1f;
            }
            columnWidths[totalDays+1] = 1.5f; // Totale

            PdfPTable thisProjectTable = new PdfPTable(columnWidths);
            thisProjectTable.setWidthPercentage(100);
            thisProjectTable.setSpacingBefore(20f);

            // Intestazione della tabella
            thisProjectTable.addCell(new PdfPCell(new Phrase("Day", headerFont)));
            for (int i = 1; i <= totalDays; i++) {
                thisProjectTable.addCell(new PdfPCell(new Phrase(getNameDay(year,month,i)+" "+String.valueOf(i), headerFont)));
            }
            thisProjectTable.addCell(new PdfPCell(new Phrase("Totale", headerFont)));

            // Riga "Attività svolta sul progetto"
            thisProjectTable.addCell(new PdfPCell(new Phrase("Attività svolta sul progetto", normalFont)));
            double counterWorkedHoursP = 0;
            for (int i = 1; i <= totalDays; i++) {
                double hoursWorkedThisDay = Optional.ofNullable(workingTimeRepository.findByDateAndResearcherAndProject(LocalDate.of(year,month,i),researcher,project)).orElse(0.0);
                thisProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(hoursWorkedThisDay), normalFont)));
                counterWorkedHoursP += hoursWorkedThisDay;
                totalHoursPerDay.put(i, hoursWorkedThisDay);
            }
            thisProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(counterWorkedHoursP), normalFont)));

            PdfPTable otherProject = new PdfPTable(1);
            otherProject.setWidthPercentage(100);
            otherProject.setSpacingBefore(0f);

            // Riga "Other MUR Projects"
            otherProject.addCell(new PdfPCell(new Phrase("Attività svolta su altri progetti MUR", normalFont)));

            PdfPTable otherProjectTable = new PdfPTable(columnWidths);
            otherProjectTable.setWidthPercentage(100);
            otherProjectTable.setSpacingBefore(0f);

            List<Project> otherProjects = projectRepository.findAllByResearchersContains(researcher);
            otherProjects.remove(project);
            for (Project pjt : otherProjects){
                otherProjectTable.addCell(new PdfPCell(new Phrase(pjt.getTitle(), normalFont)));
                counterWorkedHoursP = 0;
                for (int i = 1; i <= totalDays; i++) {
                    double hoursWorkedThisDay = Optional.ofNullable(workingTimeRepository.findByDateAndResearcherAndProject(LocalDate.of(year,month,i),researcher,pjt)).orElse(0.0);
                    otherProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(hoursWorkedThisDay), normalFont)));
                    counterWorkedHoursP += hoursWorkedThisDay;
                    totalHoursPerDay.put(i, totalHoursPerDay.get(i)+hoursWorkedThisDay);
                }
                otherProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(counterWorkedHoursP), normalFont)));
            }

            PdfPTable endProjectTable = new PdfPTable(columnWidths);
            endProjectTable.setWidthPercentage(100);
            endProjectTable.setSpacingBefore(0f);

            // Riga totale ore
            endProjectTable.addCell(new PdfPCell(new Phrase("TOTALE ORE", headerFont)));
            double sumHours = 0;
            for (int i = 1; i <= 31; i++) {
                endProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(totalHoursPerDay.get(i)), normalFont)));
                sumHours += totalHoursPerDay.get(i);
            }
            endProjectTable.addCell(new PdfPCell(new Phrase(String.valueOf(sumHours), headerFont)));

            personalInfoTable.addCell(new PdfPCell(new Phrase(String.valueOf(sumHours), normalFont))); //AGGIUNTE ORE TOTALI

            // Sezione firme
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setSpacingBefore(30f);

            signatureTable.addCell(new PdfPCell(new Phrase("Firmato dal dipendente:\n"+researcher.getName() + " " + researcher.getSurname() + "\nData:"+LocalDate.now(), normalFont)));
            signatureTable.addCell(new PdfPCell(new Phrase("Firmato dal responsabile del Dipartimento:\nProf. "+supervisor.getName() + " " + supervisor.getSurname() + "\nData:"+LocalDate.now(), normalFont)));

            document.add(projectTable);
            document.add(professionalRoleTable);
            document.add(personalInfoTable);
            document.add(thisProjectTable);
            document.add(otherProject);
            document.add(otherProjectTable);
            document.add(endProjectTable);

            document.add(signatureTable);

            // Chiusura del documento
            document.close();

            System.out.println("PDF generato con successo: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("username", cookie.getValue());

        if (isValidUrl("researcher", request)) {
            return "redirect:/downloadtimesheet";
        }
        return "redirect:/validationtimesheet?id="+idProject;
    }



    @RequestMapping("/validationtimesheet")
    public String validationtimesheet(HttpServletRequest request, Model model, @RequestParam(name="id", required = true) long id) {
        if(!isValidUrl("supervisor",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn"); //SERVE???
        Project project = projectRepository.findById(id);
        Iterable<Researcher> researchers = projectRepository.findResearchersByProjectId(id);

        ArrayList<WorkingTime> wts = new ArrayList<>();
        for(Researcher researcher : researchers){
            wts.addAll(getWorkedMonthWt(project,researcher));
        }

        model.addAttribute("workedMonthYear",wts);
        model.addAttribute("researchers",researchers);
        model.addAttribute("username", cookie.getValue());
        return "validationtimesheet";
    }

    @RequestMapping("/projectmanagement")
    public String projectmanagement(HttpServletRequest request, Model model, @RequestParam(name="id", required = true) long id) {
        if(!isValidUrl("supervisor",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");

        ArrayList<Researcher> listaRicercatori = new ArrayList<>();
        for (Utente utente : userRepository.findAll()){
            if(utente instanceof  Researcher){
                listaRicercatori.add((Researcher) utente);
            }
        }

        ArrayList<Long> listaIdRicercatori = new ArrayList<>();
        for (Utente utente : projectRepository.findById(id).getResearchers()){
            listaIdRicercatori.add(utente.getId());
        }

        model.addAttribute("selectedResearcherIds",listaIdRicercatori);
        model.addAttribute("allResearcher",listaRicercatori);
        model.addAttribute("project",projectRepository.findById(id));
        model.addAttribute("username", cookie.getValue());
        return "superviseproject";
    }

    @RequestMapping("/approveTimesheet")
    public String approveTimesheet(@RequestParam(name="id", required = true) long id) {
        WorkingTime wt = wtRepository.findById(id);
        Researcher researcher = wt.getResearcher();
        Project project = wt.getProject();
        LocalDate date = wt.getDate();

        for (WorkingTime everyWt: wtRepository.findAllByDateBetweenAndProjectAndResearcher(LocalDate.of(date.getYear(),date.getMonth(),1),LocalDate.of(date.getYear(),date.getMonth(),YearMonth.of(date.getYear(),date.getMonth()).lengthOfMonth()),project,researcher)){
            everyWt.setValidated(true);
            wtRepository.save(everyWt);
        }
        return "redirect:/validationtimesheet?id="+wt.getProject().getId();
    }

    @RequestMapping("/administrator")
    public String administrator(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("username", cookie.getValue());
        return "administrator";
    }

    @RequestMapping("/manageusers")
    public String manageuser(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }

        Cookie cookie = getCookieByName(request, "userLoggedIn");
        ArrayList<Utente> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if( user instanceof Researcher || user instanceof Supervisor) {
                users.add(user);
            }
        }
        model.addAttribute("users",users);
        model.addAttribute("username", cookie.getValue());
        return "manageusers";
    }

    @RequestMapping("/newuser")
    public String newuser(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("username", cookie.getValue());
        return "newuser";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password,
                             @RequestParam(name="name") String name, @RequestParam(name="surname") String surname,
                             @RequestParam(name="cf") String cf, @RequestParam(name="userType") String userType) {

        if(userType.equals("Supervisor")){
            userRepository.save(new Supervisor(username,password,name,surname,cf));
        } else{
            userRepository.save(new Researcher(username,password,name,surname,cf));
        }
        return "redirect:/manageusers";
    }

    @RequestMapping("/manageprojects")
    public String manageproject(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("username", cookie.getValue());
        return "manageprojects";
    }

    @RequestMapping("/newproject")
    public String newproject(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        ArrayList<Supervisor> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if(user instanceof Supervisor) {
                users.add((Supervisor) user);
            }
        }
        model.addAttribute("supervisors", users);
        model.addAttribute("username", cookie.getValue());
        return "newproject";
    }

    @PostMapping("/createProject")
    public String createProject(@RequestParam(name="title") String title, @RequestParam(name="cup") String cup,
                                @RequestParam(name="code") String code, @RequestParam(name="denominazioneSoggetto") String denominazioneSoggetto,
                                @RequestParam(name="cfSoggetto") String cfSoggetto, @RequestParam(name="supervisor") Long supervisor // ID del supervisore scelto
    ) {
        Project newProject = new Project(title,cup,code,denominazioneSoggetto,cfSoggetto);
        newProject.setSupervisor((Supervisor) userRepository.findById(supervisor).orElse(null));
        projectRepository.save(newProject);
        return "redirect:/manageprojects";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        if (isUserLoggedIn(request)) {
            return getIndexByUser(request);
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/loginUser")
    public String create(@RequestParam(name="user", required = true) String user, @RequestParam(name="psw", required = true) String psw, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (userRepository.existsByUsername(user)) {
            Utente foundUser = userRepository.findByUsername(user);
            if (foundUser.getPassword().equals(psw)) {
                Cookie cookie = new Cookie("userLoggedIn", foundUser.getUsername());
                cookie.setMaxAge(3600); // 1 ora di durata
                cookie.setHttpOnly(true); // Proteggi il cookie
                cookie.setPath("/"); // Il cookie è accessibile in tutto il dominio
                response.addCookie(cookie);

                return "redirect:/index";
            }
            redirectAttributes.addFlashAttribute("error", "Wrong password");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "User does not exist.");
        }
        return "redirect:/login";
    }

    public Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private String getIndexByUser(HttpServletRequest request){
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        if(userRepository.findByUsername(cookie.getValue()) instanceof Supervisor){
            return "redirect:/supervisor";
        }
        if(userRepository.findByUsername(cookie.getValue()) instanceof Researcher){
            return "redirect:/researcher";
        }
        return "redirect:/administrator";
    }

    private boolean isValidUrl(String url,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    return ("redirect:/"+url).equals(getIndexByUser(request));
                }
            }
        }
        return false;
    }

    public boolean isUserLoggedIn(HttpServletRequest request) {
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        if (cookie != null) {
            return userRepository.existsByUsername(cookie.getValue());

        }
        return false;
    }

    public boolean isHoliday(LocalDate date) {
        int year = date.getYear();

        // Festività fisse
        List<LocalDate> fixedHolidays = List.of(
                LocalDate.of(year, Month.JANUARY, 1),     // Capodanno
                LocalDate.of(year, Month.JANUARY, 6),     // Epifania
                LocalDate.of(year, Month.APRIL, 25),      // Festa della Liberazione
                LocalDate.of(year, Month.MAY, 1),         // Festa dei lavoratori
                LocalDate.of(year, Month.JUNE, 2),        // Festa della Repubblica
                LocalDate.of(year, Month.AUGUST, 15),     // Ferragosto
                LocalDate.of(year, Month.NOVEMBER, 1),    // Tutti i Santi
                LocalDate.of(year, Month.DECEMBER, 8),    // Immacolata Concezione
                LocalDate.of(year, Month.DECEMBER, 25),  // Natale
                LocalDate.of(year, Month.DECEMBER, 26)    // Santo Stefano
        );

        // Controllo festività fisse
        if (fixedHolidays.contains(date)) {
            return true;
        }

        // Controllo Pasqua e Pasquetta (variabili)
        LocalDate easterSunday = getEasterSunday(year);
        LocalDate easterMonday = easterSunday.plusDays(1);
        if (date.equals(easterSunday) || date.equals(easterMonday)) {
            return true;
        }

        // Controllo fine settimana
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }

        return false;
    }

    private LocalDate getEasterSunday(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
    }

    public ArrayList<String> getWorkedMonth(Project project, Researcher researcher){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByValidatedTrueAndResearcherAndProject(researcher, project);
        ArrayList<String> monthYearList = new ArrayList<>();
        for(WorkingTime wt : wts){
            String monthYear = wt.getMonthYear();
            if (!monthYearList.contains(monthYear)){
                monthYearList.add(monthYear);
            }
        }
        return monthYearList;
    }

    public ArrayList<WorkingTime> getWorkedMonthWt(Project project, Researcher researcher){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByResearcherAndProject(researcher, project);
        ArrayList<WorkingTime> listWts = new ArrayList<>();

        ArrayList<String> monthYearList = new ArrayList<>();

        for(WorkingTime wt : wts){
            String monthYear = wt.getMonthYear();
            if (!monthYearList.contains(monthYear)){
                monthYearList.add(monthYear);
                listWts.add(wt);
            }
        }
        return listWts;
    }

    private static String getNameDay(int anno, int mese, int giorno) {
        LocalDate data = LocalDate.of(anno, mese, giorno);

        DayOfWeek giornoSettimana = data.getDayOfWeek();

        switch (giornoSettimana) {
            case MONDAY:
                return "Lun";
            case TUESDAY:
                return "Mar";
            case WEDNESDAY:
                return "Mer";
            case THURSDAY:
                return "Gio";
            case FRIDAY:
                return "Ven";
            case SATURDAY:
                return "Sab";
            case SUNDAY:
                return "Dom";
            default:
                return "";
        }
    }


    /*@PostMapping("/createSupervisor")
    public Supervisor createSupervisor(@RequestBody Supervisor user) {
        System.out.println(user);
        return userRepository.save(user);
    }

    @PostMapping("/createResearcher")
    public Researcher createResearcher(@RequestBody Researcher user) {
        return userRepository.save(user);
    }

    @PostMapping("/createAdministrator")
    public Administrator createAdministrator(@RequestBody Administrator user) {
        return userRepository.save(user);
    }

    @PostMapping("/createProject")
    public Project createResearcher(@RequestBody Project progetto) {
        projectRepository.save(progetto);
        return progetto;
    }

    */

}
