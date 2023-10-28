package org.example.service;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.Grade;
import org.example.repository.GradeInMemoryRepositoryImpl;
import org.example.repository.GradeUsingFileRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcademicRecordServiceImplTest {

  private AcademicRecordService academicRecordService;

  @BeforeEach
  void setUp() {

    this.academicRecordService = new AcademicRecordServiceImpl(new GradeUsingFileRepositoryImpl ());
  }

  @Test
  void summation_of_number_of_grades_should_return_a_valid_number() {
    Integer numberOfGrades = this.academicRecordService.sumNumberOfGrades();

    assertNotNull(numberOfGrades);
    assertEquals(
        4,
        numberOfGrades);
  }

  @Test
  void average_of_grades_should_calculated_successful() {

    Double average = this.academicRecordService.calculateAverage();

    assertNotNull(average);
    assertEquals(4.175D, average);
  }


  @Test
  void a_new_grade_is_added_successful() {

    Grade newGrade = new Grade( "PROJECT FINAL", 5D, LocalDate.now() );
    Grade createdGrade = this.academicRecordService.addGrade(newGrade);

    assertNotNull(createdGrade);
    assertEquals(newGrade.grade(), createdGrade.grade());
  }

  @Test
  void listing_all_grades_should_be_return_successful() {

    List<Grade> grades = this.academicRecordService.listAllGrades();

    assertNotNull(grades);
    assertFalse(grades.isEmpty());
    assertEquals(4, grades.size());
  }


}
