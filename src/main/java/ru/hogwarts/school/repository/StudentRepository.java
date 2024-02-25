package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int ageFrom, int ageTo);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getAmountStudents();

    @Query(value = "SELECT ROUND(AVG(age), 2) FROM student", nativeQuery = true)
    Long getAverageAgeStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
