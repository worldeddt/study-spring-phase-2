package article.functional.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FunctionalService {

    private int total;
    // 암묵적 코딩
    public int addToTotal(int amount) { // 인자는 명시적 출력.
        // 전역변수를 읽는 것은 암묵적 입력.
        log.debug("old total : {}", total); // 로그에 찍는 것은 암묵적 출력.
        total += amount;  // 전역번수를 바꾸는 것은 암묵적 출력.
        return total; // 리턴값은 명시적 출력.
    }

    private int shoppingTotal;

    public void updateTaxDom() {
        setTaxDom(calculateTax(shoppingTotal));
    }

    private double calculateTax(int total) {
        return total * 0.10;
    }

    private void setTaxDom(double v) {

    }
}
