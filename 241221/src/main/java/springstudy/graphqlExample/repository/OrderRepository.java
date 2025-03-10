package springstudy.graphqlExample.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springstudy.graphqlExample.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
