package hello.springtx.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders") // 테이블이름을 지정하지 않으면 order가 됨. order는 DB의 예약어 (order by)여서 사용 불가
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String username;    // 정상, 예외, 잔고부족
    private String payStatus;   // 대기, 완료
}
