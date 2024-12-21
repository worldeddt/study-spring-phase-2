package springstudy.memoriArea.services;


import org.springframework.stereotype.Service;

@Service
public class MethodAreaService {

    // 정적 변수 (Method Area에 저장)
    static int count = 0;

    // 정적 초기화 블록 (Method Area에 저장)
    static {
        count = 10;
    }

    // 정적 메서드 (메서드의 바이트코드가 Method Area에 저장)
    public static void staticMethod() {
        System.out.println("Static Method");
    }

    // 인스턴스 메서드 (메서드의 바이트코드가 Method Area에 저장)
    public void instanceMethod() {
        System.out.println("Instance Method");
    }

    // 네이티브 메서드 선언 (Native Method Stack에서 사용)
    public native void nativeMethod();

    static {
        // 네이티브 라이브러리 로드
        System.loadLibrary("NativeLib");
    }

    public class MathUtils {
        // 정적 변수 (메소드 영역에 저장)
        public static final double PI = 3.141592;

        // 정적 메서드 (메서드 바이트코드가 메소드 영역에 저장)
        public static int add(int x, int y) {
            return x + y;
        }
    }
}
