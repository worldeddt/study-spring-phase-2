package springstudy.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.session.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
