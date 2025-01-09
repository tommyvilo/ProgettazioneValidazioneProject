package it.univr.Repository;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Utente;
import it.univr.Model.WorkingTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Long> {
    Iterable<WorkingTime> findByDateAndUtente(LocalDate date, Utente utente);

    WorkingTime findByDateAndUtenteAndProject(LocalDate date, Utente utente, Project project);

    Iterable<WorkingTime> findByUtente(Utente utente);

    void deleteByProjectAndUtente(Project project, Utente utente);

    @Query("select wt.project from WorkingTime wt where wt.date=?1 and wt.utente=?2")
    Iterable<Project> findProjectsByDateAndResearcher(LocalDate date, Utente utente);

    Iterable<WorkingTime> findWorkingTimesByUtenteAndProject(Utente utente, Project project);

    Iterable<WorkingTime> findWorkingTimesByValidatedTrueAndUtenteAndProject(Utente utente, Project project);

    WorkingTime getWorkingTimeByProjectAndUtenteAndDate(Project project, Utente researcher, LocalDate date);

    WorkingTime getTopByUtenteAndDate(Utente researcher, LocalDate date);
    
    WorkingTime findById(long id);

    Iterable<WorkingTime> findAllByDateBetweenAndProjectAndUtente(LocalDate dateAfter, LocalDate dateBefore, Project project, Utente utente);
}
