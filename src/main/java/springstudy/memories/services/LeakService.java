package springstudy.memories.services;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeakService {

    private List<byte[]> leakyList = new ArrayList<>();

    public void leakClear() {
        leakyList.clear();
    }

    public void leakMemory() {
        // 1MB 크기의 배열을 생성하여 리스트에 추가
        leakyList.add(new byte[1024 * 1024]);
    }
}
