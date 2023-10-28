package org.example;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

import org.example.exception.GradeNotFoundException;
import org.example.model.Grade;
import org.example.repository.GradeInMemoryRepositoryImpl;
import org.example.repository.GradeUsingFileRepositoryImpl;
import org.example.service.AcademicRecordService;
import org.example.service.AcademicRecordServiceImpl;


public class ApplicationRunner {
  public static void main(String[] args) {

    AcademicRecordService academicRecordService =

        new AcademicRecordServiceImpl(new GradeUsingFileRepositoryImpl ());

    printInitialGrades(academicRecordService);

    Grade grade = new Grade("PARTIAL", 4.5D, LocalDate.now());
    academicRecordService.addGrade(grade);

    printUpdatedGrades(academicRecordService);

    System.out.println("Sum of number of grades: " + academicRecordService.sumNumberOfGrades());
    System.out.println("Average: " + academicRecordService.calculateAverage());

    String projectName = "Unit 10";
    try {
      Grade foundGrade = academicRecordService.getGrade(projectName);
      System.out.println("Grade for the project " + projectName + ": " + foundGrade);
    } catch (GradeNotFoundException e) {
      System.out.println("A grade was not found for the project " + projectName);
    }
  }

  private static void printInitialGrades(AcademicRecordService service) {
    System.out.println("Initial notes");
    List<Grade> gradeList = service.listAllGrades();
    gradeList.forEach(System.out::println);
  }

  private static void printUpdatedGrades(AcademicRecordService service) {
    System.out.println("Update grades when a new one is added");
    List<Grade> gradeList = service.listAllGrades();
    gradeList.forEach(System.out::println);
  }
}
