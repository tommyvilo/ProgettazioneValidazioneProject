package it.univr.Repository;
import it.univr.Model.User.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Utente, Long>{
    Utente findByUsername(String username);
    boolean existsByUsername(String user);
}
