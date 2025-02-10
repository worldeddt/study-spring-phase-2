//package springstudy.session.components;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import springstudy.session.entities.User;
//import springstudy.session.repository.UserRepository;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 테스트 사용자 생성
//        User findUser = userRepository.findByUsername("user");
//
//        if (findUser == null) {
//            User user = new User();
//            user.setUsername("user");
//            user.setPassword(passwordEncoder.encode("password"));
//            user.setRole("ROLE_USER");
//            userRepository.save(user);
//        }
//    }
//}