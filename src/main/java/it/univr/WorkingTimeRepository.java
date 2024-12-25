package it.univr;

import it.univr.User.Researcher;
import it.univr.User.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface WorkingTimeRepository extends CrudRepository<WorkingTime, Long> {
    Iterable<WorkingTime> findByDateAndResearcher(LocalDate date, Researcher researcher);

    @Query("select wt.project from WorkingTime wt where wt.date=?1 and wt.researcher=?2 ")
    Iterable<Project> findProjectsByDateAndResearcher(LocalDate date, Researcher researcher);

    WorkingTime getWorkingTimeByProjectAndResearcherAndDate(Project project, Researcher researcher, LocalDate date);

    WorkingTime getTopByResearcherAndDate(Researcher researcher, LocalDate date);

    void deleteByResearcherAndDateAndProject(Researcher researcher, LocalDate date, Project project);
}
