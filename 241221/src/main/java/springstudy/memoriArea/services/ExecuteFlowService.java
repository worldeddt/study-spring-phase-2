package springstudy.memoriArea.services;


import org.springframework.stereotype.Service;

@Service
public class ExecuteFlowService {

    int y = 20;

    public static void main(String[] args) {
        // 스택: main 메서드의 스택 프레임 생성
        int x = 10; // 스택에 저장

        MyClass obj = new MyClass(); // 힙에 객체 생성, 'obj'는 스택에 참조 저장

        obj.methodA(); // 스택에 methodA의 스택 프레임 생성
    }
}

class MyClass {
    public void methodA() {
        System.out.println("methodA");
    }
}