package it.univr;
import it.univr.User.Researcher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>{
}
