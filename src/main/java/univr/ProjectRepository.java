package univr;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long>{
    Iterable<Project> findProjectsByResearchersContains(Long researcher);
}
