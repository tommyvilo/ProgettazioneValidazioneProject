package it.univr;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long>{
    Iterable<Project> findAllBySupervisor(Supervisor byUsername);
    Iterable<Project> findAllByResearchersContains(Researcher byUsername);
    Project findById(long id);
}
