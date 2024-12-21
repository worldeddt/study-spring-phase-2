package springstudy.memoriArea.services;


import org.springframework.stereotype.Service;

@Service
public class MemoryAreaService {


    // 정적 변수 , method ared 에 저장
    static String staticVar = "staticVar";

    // 인스턴스 변수 (힙 영역에 저장)
    int instanceVar = 20;
    String test1 = "222";

    public void heap(int param) {

        // 지역 변수 (스택 영역에 저장)
        String localVar = "localVar";

        // 객체 생성 (힙 영역에 저장)
        Object obj = new Object();

        // 배열 생성 (힙 영역에 저장)
        int[] numbers = new int[5];

        // 내부 클래스 (힙 영역에 저장)
        InnerClass innerClass = new InnerClass();
    }

    class InnerClass {
        // 내부 클래스의 인스턴스 변수 (힙 영역에 저장)
        int innerVar = 40;
    }
}
