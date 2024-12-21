package springstudy.graphqlExample.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springstudy.graphqlExample.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
