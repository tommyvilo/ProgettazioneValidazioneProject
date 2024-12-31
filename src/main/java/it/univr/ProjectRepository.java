package it.univr;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>{
    Iterable<Project> findAllBySupervisor(Supervisor byUsername);

    @Query("select pj.researchers from Project pj where pj.id=?1")
    Iterable<Researcher> findResearchersByProjectId(long id);

    List<Project> findAllByResearchersContains(Researcher researcher);

    Project findById(long id);

    Project findByTitle(String username);
}
