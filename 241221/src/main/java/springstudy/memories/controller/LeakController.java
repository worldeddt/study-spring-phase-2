package springstudy.memories.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springstudy.memories.services.LeakService;

@RestController
@RequiredArgsConstructor
public class LeakController {

    private final LeakService leakService;

    @GetMapping("/leak")
    public String leak() {
        leakService.leakMemory();
        return "Memory leaked!";
    }

    @GetMapping("/clear")
    public String clear() {
        leakService.leakClear();
        return "Memory cleared!";
    }
}
