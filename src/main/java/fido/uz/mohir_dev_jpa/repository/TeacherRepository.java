package fido.uz.mohir_dev_jpa.repository;

import fido.uz.mohir_dev_jpa.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query(value = "SELECT * FROM teacher WHERE email = :email", nativeQuery = true)
    Optional<Teacher> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM teacher WHERE phone_number = :phoneNumber", nativeQuery = true)
    Optional<Teacher> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(value = "SELECT * FROM teacher", nativeQuery = true)
    List<Teacher> findAllTeachers();

    @Query(value = "SELECT * FROM teacher WHERE id = :id", nativeQuery = true)
    Optional<Teacher> findById(@Param("id") Long id);


    @Query(value = "DELETE FROM teacher WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Query(value = "INSERT INTO teacher (first_name, last_name, email, phone_number, address, age) VALUES (:firstName, :lastName, :email, :phoneNumber, :address, :age)", nativeQuery = true)
    void saveTeacher(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("phoneNumber") String phoneNumber, @Param("address") String address, @Param("age") Integer age);

    @Query(value = "UPDATE teacher SET first_name = :firstName, last_name = :lastName, email = :email, phone_number = :phoneNumber, address = :address, age = :age WHERE id = :id", nativeQuery = true)
    void updateTeacher(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("phoneNumber") String phoneNumber, @Param("address") String address, @Param("age") Integer age);
}
