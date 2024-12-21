package springstudy.graphqlExample.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 싱글 테이블 전략 설정
@DiscriminatorColumn(name = "type")  // 구분컬럼명 지정
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    // 비즈니스 로직 추가

    /**
     * stock 증가
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity
     */
    public void removeStock(int quantity) {

        log.debug("request quantity : {} / stock quantity : {}", quantity, this.stockQuantity);
        int restStock = this.stockQuantity - quantity;
        // 잔여수량이 0보다 작으면 예외발생
        if(restStock < 0) {
            throw new RuntimeException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}