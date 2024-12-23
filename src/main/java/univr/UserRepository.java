package univr;
import univr.User.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Utente, Long>{
   Utente findByUsername(String username);
   boolean existsByUsername(String username);
}
