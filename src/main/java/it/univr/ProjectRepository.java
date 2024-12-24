package it.univr;
import it.univr.User.Supervisor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>{
    Iterable<Project> findAllBySupervisor(Supervisor byUsername);
}
