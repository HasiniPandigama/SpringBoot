package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
 //@Autowired(remove 19 & 20)
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Student studentOptional = studentRepository.findByEmail(student.getEmail());
        if (studentOptional == null){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists =studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "student with id" + studentId + "does not exists");
        }
        studentRepository.deleteById(studentId);
        System.out.println(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalStateException("student with id" + studentId + "does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
            System.out.println(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){
            Student studentOptional = studentRepository.findByEmail(email);
            if (studentOptional == null){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
            System.out.println(email);
        }
        studentRepository.save(student);

    }
}
