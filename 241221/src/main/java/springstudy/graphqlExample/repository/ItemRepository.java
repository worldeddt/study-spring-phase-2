package springstudy.graphqlExample.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springstudy.graphqlExample.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
