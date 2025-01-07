package it.univr.Controller;
import it.univr.Model.Project;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.univr.Model.User.Administrator;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Model.User.Utente;

import java.io.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.util.List;

@Controller
public class TimeTrackingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;

    @PostConstruct
    public void init() {
        Researcher r1 = new Researcher("nicozerman","nicksss","Nicolò","Zerman","ZRMNCL02S19L781E");
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

        WorkingTime w1 = new WorkingTime(r15,p1, LocalDate.of(2025,1,1),2,false,false);
        WorkingTime w6 = new WorkingTime(r15,p1, LocalDate.of(2024,11,10),2,false,false);
        WorkingTime w7 = new WorkingTime(r15,p1, LocalDate.of(2024,12,10),2,false,false);
        WorkingTime w2 = new WorkingTime(r15,p3, LocalDate.of(2025,1,1),2.5,false,false);
        WorkingTime w3 = new WorkingTime(r15,p4, LocalDate.of(2025,1,1),3,false,false);
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

    @RequestMapping("/")
    public String rootPath(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return "redirect:/index";
        }
        return "login";
    }

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
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/login";
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
    public String loginUser(@RequestParam(name="user") String user, @RequestParam(name="psw") String psw,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {
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

    @RequestMapping("/downloadMonthlyTimesheet")
    public void downloadMonthlyTimesheet(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(name = "idProject") long idProject,
                                         @RequestParam(name = "date") String monthYear,
                                         @RequestParam(name = "researcherUser") String username){
        boolean isSupervisor;
        if(isValidUrl("researcher",request)){
            isSupervisor = false;
        }
        else if(isValidUrl("supervisor",request)){
            isSupervisor = true;
        }
        else {
            return;
        }

        int month = Integer.parseInt(monthYear.split("/")[0]);
        int year = Integer.parseInt(monthYear.split("/")[1]);
        YearMonth yM = YearMonth.of(year, month);
        int totalDays = yM.lengthOfMonth();

        Researcher researcher = (Researcher)userRepository.findByUsername(username);
        Project project = projectRepository.findById(idProject);
        Supervisor supervisor = project.getSupervisor();

        String outputFilePath = researcher.getName()+researcher.getSurname()+"_"+month+"-"+year+".pdf";

        try {
            Document document = new Document(PageSize.A4.rotate()); //Creazione documento + rotazione pagina
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));

            document.open();
            createDocument(document, totalDays, year, month, project, researcher, supervisor, isSupervisor);
            document.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFilePath + "\"");

            FileInputStream fileInputStream = new FileInputStream(outputFilePath);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            fileInputStream.close();
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            System.out.println("Error generating timesheet: "+e.getMessage());
        }
    }

    private void addTitlePDF(Document document){
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("TIMESHEET PER RENDICONTAZIONE PERSONALE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
    }

    private void addDatePDF(Document document, int year, int month){
        Month meseEnum = LocalDate.of(year,month,1).getMonth();
        Font dateFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
        Paragraph monthYearParagraph = new Paragraph(meseEnum.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ITALIAN) + " " + year, dateFont);
        monthYearParagraph.setAlignment(Element.ALIGN_CENTER);

        document.add(monthYearParagraph);
    }

    private PdfPTable projectTable(Project project){
        float[] projectTableWidths = new float[2];
        projectTableWidths[0] = 1f; //Titoli
        projectTableWidths[1] = 3f; //Descrizione del progetto

        PdfPTable projectTable = new PdfPTable(projectTableWidths);
        projectTable.setWidthPercentage(100);
        projectTable.setSpacingBefore(10f);
        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 10);

        PdfPCell cell = new PdfPCell(new Phrase("Titolo del progetto", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        projectTable.addCell(cell);
        projectTable.addCell(new PdfPCell(new Phrase(project.getTitle(), normalFont)));

        cell = new PdfPCell(new Phrase("CUP del progetto", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        projectTable.addCell(cell);
        projectTable.addCell(new PdfPCell(new Phrase(project.getCup(), normalFont)));

        cell = new PdfPCell(new Phrase("Codice del progetto", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        projectTable.addCell(cell);
        projectTable.addCell(new PdfPCell(new Phrase(project.getCode(), normalFont)));

        cell = new PdfPCell(new Phrase("Denominazione Soggetto", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        projectTable.addCell(cell);
        projectTable.addCell(new PdfPCell(new Phrase(project.getDenominazioneSoggetto(), normalFont)));

        return projectTable;
    }

    private PdfPTable professionalRoleTable(){
        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);

        PdfPTable professionalRoleTable = new PdfPTable(1);
        professionalRoleTable.setWidthPercentage(100);
        professionalRoleTable.setSpacingBefore(0f);

        PdfPCell professionalRoleCell = new PdfPCell(new Phrase("Figura professionale", headerFont));
        professionalRoleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        professionalRoleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        professionalRoleCell.setBackgroundColor(Color.GRAY);
        professionalRoleTable.addCell(professionalRoleCell);

        return professionalRoleTable;
    }

    private PdfPTable personalInfoTable(Researcher researcher, double sumHours){
        PdfPTable personalInfoTable = new PdfPTable(4); // 4 colonne per nome, cognome, codice fiscale, ore totali
        personalInfoTable.setWidthPercentage(100);
        personalInfoTable.setSpacingBefore(0f);

        Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 10);

        PdfPCell cell = new PdfPCell(new Phrase("Nome", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        personalInfoTable.addCell(cell);
        personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getName(), normalFont)));

        cell = new PdfPCell(new Phrase("Cognome", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        personalInfoTable.addCell(cell);
        personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getSurname(), normalFont)));

        cell = new PdfPCell(new Phrase("Codice Fiscale", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        personalInfoTable.addCell(cell);
        personalInfoTable.addCell(new PdfPCell(new Phrase(researcher.getCf(), normalFont)));

        cell = new PdfPCell(new Phrase("Ore Totali Rendicontate", headerFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        personalInfoTable.addCell(cell);

        personalInfoTable.addCell(new PdfPCell(new Phrase(String.valueOf(sumHours), normalFont))); //AGGIUNTE ORE TOTALI

        return personalInfoTable;
    }

    private PdfPTable daysRow(int totalDays, int year, int month){
        float[] columnWidths = getColumnWidths(totalDays);

        PdfPTable daysRow = new PdfPTable(columnWidths);
        daysRow.setWidthPercentage(100);
        daysRow.setSpacingBefore(20f);

        Font dayFont = new Font(Font.HELVETICA, 8);
        Font totaleFont = new Font(Font.HELVETICA, 8, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase("GIORNO", dayFont));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        daysRow.addCell(cell);

        for (int i = 1; i <= totalDays; i++) {
            cell = new PdfPCell(new Phrase(getNameDay(year,month,i)+" "+i, dayFont));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            daysRow.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("TOTAL", totaleFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        daysRow.addCell(cell);

        return daysRow;
    }

    private List<Object> thisProjectTable(int totalDays, int year, int month, Researcher researcher, Project project, Map<Integer,Double> totalHoursPerDay){
        float[] columnWidths = getColumnWidths(totalDays);

        PdfPTable thisProjectTable = new PdfPTable(columnWidths);
        thisProjectTable.setWidthPercentage(100);
        thisProjectTable.setSpacingBefore(0f);

        Font totaleFont = new Font(Font.HELVETICA, 8, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 8);

        PdfPCell cell = new PdfPCell(new Phrase("Attività svolta sul progetto", normalFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        thisProjectTable.addCell(cell);
        double counterWorkedHoursP = 0;

        for (int i = 1; i <= totalDays; i++) {
            LocalDate currentDate = LocalDate.of(year,month,i);
            double hoursWorkedThisDay = 0.0;
            if(isHoliday(currentDate)) {
                cell = new PdfPCell(new Phrase());
                cell.setBackgroundColor(Color.GRAY);
            }
            else {
                WorkingTime wt = wtRepository.findByDateAndResearcherAndProject(currentDate, researcher, project);
                if (wt!=null){
                    hoursWorkedThisDay = wt.getWorkedHours();
                }
                if (hoursWorkedThisDay == 0.0) {
                    cell = new PdfPCell(new Phrase());
                }
                else {
                    cell = checkCounter(normalFont,hoursWorkedThisDay);
                }
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            }
            thisProjectTable.addCell(cell);
            counterWorkedHoursP += hoursWorkedThisDay;
            totalHoursPerDay.put(i, hoursWorkedThisDay);
        }

        cell = checkCounter(totaleFont,counterWorkedHoursP);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        thisProjectTable.addCell(cell);

        return Arrays.asList(thisProjectTable,totalHoursPerDay);
    }

    private PdfPTable otherProject(){
        Font normalFont = new Font(Font.HELVETICA, 8);

        PdfPTable otherProject = new PdfPTable(1);
        otherProject.setWidthPercentage(100);
        otherProject.setSpacingBefore(0f);

        PdfPCell cell = new PdfPCell(new Phrase("Attività svolta su altri progetti MUR", normalFont));
        cell.setBackgroundColor(Color.GRAY);
        otherProject.addCell(cell);

        return otherProject;
    }

    private List<Object> otherProjectTable(boolean isSupervisor, int totalDays, int year, int month, Researcher researcher, Project project, Map<Integer,Double> totalHoursPerDay){
        Font totaleFont = new Font(Font.HELVETICA, 8, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 8);

        float[] columnWidths = getColumnWidths(totalDays);

        PdfPTable otherProjectTable = new PdfPTable(columnWidths);
        otherProjectTable.setWidthPercentage(100);
        otherProjectTable.setSpacingBefore(0f);

        Font projectFont = new Font(Font.HELVETICA, 8, Font.ITALIC);

        List<Project> otherProjects = projectRepository.findAllByResearchersContains(researcher);
        otherProjects.remove(project);

        for (Project pjt : otherProjects) {
            PdfPCell cell = new PdfPCell(new Phrase(pjt.getTitle(), projectFont));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            otherProjectTable.addCell(cell);
            double counterWorkedHoursP = 0;

            for (int i = 1; i <= totalDays; i++) {
                LocalDate currentDate = LocalDate.of(year, month, i);
                double hoursWorkedThisDay = 0.0;
                boolean validated = false;

                if (isHoliday(currentDate)) {
                    cell = new PdfPCell(new Phrase());
                    cell.setBackgroundColor(Color.GRAY);
                }
                else {
                    WorkingTime wt = wtRepository.findByDateAndResearcherAndProject(currentDate, researcher, pjt);
                    if (wt!=null){
                        hoursWorkedThisDay = wt.getWorkedHours();
                        validated = wt.getValidated();
                    }
                    if(isSupervisor){
                        if (hoursWorkedThisDay == 0.0) {
                            cell = new PdfPCell(new Phrase());
                        }
                        else {
                            cell = checkCounter(normalFont,hoursWorkedThisDay);
                            if(validated){
                                cell.setBackgroundColor(Color.green);
                            }
                            else {
                                cell.setBackgroundColor(Color.RED);
                            }
                        }
                    }
                    else {
                        if (hoursWorkedThisDay == 0.0 || !validated) {
                            cell = new PdfPCell(new Phrase());
                        }
                        else {
                            cell = checkCounter(normalFont,hoursWorkedThisDay);
                        }
                    }
                    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }
                otherProjectTable.addCell(cell);
                counterWorkedHoursP += hoursWorkedThisDay;
                totalHoursPerDay.put(i, totalHoursPerDay.get(i) + hoursWorkedThisDay);
            }

            cell = checkCounter(totaleFont, counterWorkedHoursP);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            otherProjectTable.addCell(cell);
        }

        return Arrays.asList(otherProjectTable, totalHoursPerDay);
    }

    private PdfPTable leavesRow(int totalDays, int year, int month, Researcher researcher, Project project) {
        float[] columnWidths = getColumnWidths(totalDays);

        PdfPTable leavesRow = new PdfPTable(columnWidths);
        leavesRow.setWidthPercentage(100);
        leavesRow.setSpacingBefore(0f);

        Font totaleFont = new Font(Font.HELVETICA, 8, Font.BOLD);
        Font projectFont = new Font(Font.HELVETICA, 8, Font.ITALIC);

        int leaveDays = 0;
        PdfPCell cell = new PdfPCell(new Phrase("Malattia ...", projectFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        leavesRow.addCell(cell);

        for (int i = 1; i <= totalDays; i++) {
            LocalDate currentDate = LocalDate.of(year,month,i);
            boolean isLeave = false;

            if(isHoliday(currentDate)) {
                cell = new PdfPCell(new Phrase());
                cell.setBackgroundColor(Color.GRAY);
            }
            else {
                WorkingTime wt = wtRepository.findByDateAndResearcherAndProject(currentDate, researcher, project);
                if (wt!=null){
                    isLeave = wt.getLeave();
                }
                if (isLeave) {
                    leaveDays++;
                }
                cell = new PdfPCell(new Phrase());
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            }
            leavesRow.addCell(cell);
        }

        int leaveHours = 0*leaveDays; //Teniamo 0
        cell = new PdfPCell(new Phrase(String.valueOf(leaveHours), totaleFont));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        leavesRow.addCell(cell);

        return leavesRow;
    }

    private List<Object> endProjectTable(int totalDays, Map<Integer,Double> totalHoursPerDay) {
        float[] columnWidths = getColumnWidths(totalDays);

        PdfPTable endProjectTable = new PdfPTable(columnWidths);
        endProjectTable.setWidthPercentage(100);
        endProjectTable.setSpacingBefore(0f);

        Font totaleFont = new Font(Font.HELVETICA, 8, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase("TOTAL", totaleFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        endProjectTable.addCell(cell);
        double sumHours = 0;

        for (int i = 1; i <= totalDays; i++) {
            double totalHoursDay = totalHoursPerDay.get(i);
            if(isInteger(totalHoursDay)) {
                cell = new PdfPCell(new Phrase(String.valueOf((int) totalHoursDay), totaleFont));
            }
            else {
                cell = new PdfPCell(new Phrase(String.valueOf(totalHoursDay), totaleFont));
            }
            cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            endProjectTable.addCell(cell);
            sumHours += totalHoursPerDay.get(i);
        }

        if(isInteger(sumHours)) {
            cell = new PdfPCell(new Phrase(String.valueOf((int) sumHours), totaleFont));
        } else{
            cell = new PdfPCell(new Phrase(String.valueOf(sumHours), totaleFont));
        }
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        endProjectTable.addCell(cell);

        return Arrays.asList(endProjectTable, sumHours);
    }

    private float[] getColumnWidths(int totalDays) {
        float[] columnWidths = new float[totalDays+2];
        columnWidths[0] = 3.8f; // Colonna "Day"
        for (int i = 1; i < totalDays + 1; i++) {
            columnWidths[i] = 1f;
        }
        columnWidths[totalDays+1] = 1.5f; // Totale
        return columnWidths;
    }

    private void createDocument(Document document, int totalDays, int year, int month, Project project, Researcher researcher, Supervisor supervisor, boolean isSupervisor) {
        Map<Integer,Double> totalHoursPerDay = new HashMap<>();

        addTitlePDF(document); //Aggiunta titolo
        addDatePDF(document,year,month); //Aggiunta data

        PdfPTable projectTable = projectTable(project);
        PdfPTable professionalRoleTable = professionalRoleTable();
        PdfPTable daysRow = daysRow(totalDays,year,month);

        List<Object> listaReturn = thisProjectTable(totalDays,year,month,researcher,project,totalHoursPerDay);
        PdfPTable thisProjectTable = (PdfPTable) listaReturn.get(0);
        totalHoursPerDay = (Map<Integer,Double>) listaReturn.get(1);

        PdfPTable otherProject = otherProject();

        listaReturn = otherProjectTable(isSupervisor, totalDays, year, month, researcher, project, totalHoursPerDay);
        PdfPTable otherProjectTable = (PdfPTable) listaReturn.get(0);
        totalHoursPerDay = (Map<Integer,Double>) listaReturn.get(1);

        PdfPTable leavesRow = leavesRow(totalDays,year,month,researcher,project);

        listaReturn = endProjectTable(totalDays,totalHoursPerDay);
        PdfPTable endProjectTable = (PdfPTable) listaReturn.get(0);
        double sumHours = (double) listaReturn.get(1);

        PdfPTable personalInfoTable = personalInfoTable(researcher, sumHours);

        PdfPTable signatureTable = signatureTable(researcher, supervisor);

        document.add(projectTable);
        document.add(professionalRoleTable);
        document.add(personalInfoTable);
        document.add(daysRow);
        document.add(thisProjectTable);
        document.add(otherProject);
        document.add(otherProjectTable);
        document.add(leavesRow);
        document.add(endProjectTable);
        document.add(signatureTable);
    }

    private PdfPCell checkCounter(Font font, double counter){
        if(isInteger(counter)) {
            return new PdfPCell(new Phrase(String.valueOf((int) counter), font));
        } else {
            return new PdfPCell(new Phrase(String.valueOf(counter), font));
        }
    }

    private PdfPTable signatureTable(Researcher researcher, Supervisor supervisor){
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(30f);

        Font normalFont = new Font(Font.HELVETICA, 10);

        signatureTable.addCell(new PdfPCell(new Phrase("Firmato dal dipendente:\n"+researcher.getName() + " " + researcher.getSurname() + "\nData: "+LocalDate.now(), normalFont)));
        signatureTable.addCell(new PdfPCell(new Phrase("Firmato dal responsabile del Dipartimento:\nProf. "+supervisor.getName() + " " + supervisor.getSurname() + "\nData: "+LocalDate.now(), normalFont)));

        return signatureTable;
    }

    private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
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
        assert cookie != null;
        if(userRepository.findByUsername(cookie.getValue()) instanceof Supervisor){
            return "redirect:/supervisor";
        }
        if(userRepository.findByUsername(cookie.getValue()) instanceof Researcher){
            return "redirect:/researcher";
        }
        return "redirect:/administrator";
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        if (cookie != null) {
            return userRepository.existsByUsername(cookie.getValue());

        }
        return false;
    }

    private boolean isInteger(double value){
        return value == (int) value;
    }

    public boolean isHoliday(LocalDate date) {
        int year = date.getYear();

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

        if (fixedHolidays.contains(date)) {
            return true;
        }

        LocalDate easterSunday = getEasterSunday(year);
        LocalDate easterMonday = easterSunday.plusDays(1);
        if (date.equals(easterSunday) || date.equals(easterMonday)) {
            return true;
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
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
        int v = (h + l - 7 * m + 114);
        int month = v / 31;
        int day = (v % 31) + 1;
        return LocalDate.of(year, month, day);
    }

    private static String getNameDay(int anno, int mese, int giorno) {
        LocalDate data = LocalDate.of(anno, mese, giorno);

        DayOfWeek giornoSettimana = data.getDayOfWeek();

        return switch (giornoSettimana) {
            case MONDAY -> "Lun";
            case TUESDAY -> "Mar";
            case WEDNESDAY -> "Mer";
            case THURSDAY -> "Gio";
            case FRIDAY -> "Ven";
            case SATURDAY -> "Sab";
            case SUNDAY -> "Dom";
        };
    }

    public boolean isValidUrl(String url, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    return !("redirect:/"+url).equals(getIndexByUser(request));
                }
            }
        }
        return true;
    }
}
