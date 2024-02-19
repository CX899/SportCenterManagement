package ca.mcgill.ecse321.sportsregistrationw24;

import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InstructorAccountRepositoryTests {
    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @AfterEach
    public void clearDatabase() {
        instructorAccountRepository.deleteAll();
    }
}
