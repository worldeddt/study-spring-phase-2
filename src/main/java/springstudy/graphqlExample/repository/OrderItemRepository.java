package springstudy.graphqlExample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springstudy.graphqlExample.entities.OrderItem;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
