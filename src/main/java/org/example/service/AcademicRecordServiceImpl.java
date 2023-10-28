package org.example.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.example.exception.GradeNotFoundException;
import org.example.model.Grade;
import org.example.repository.GradeRepository;
import org.example.repository.GradeUsingFileRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcademicRecordServiceImpl implements AcademicRecordService {

  private static final Logger logger = LoggerFactory.getLogger(AcademicRecordServiceImpl.class);
  private final GradeRepository gradeRepository;

  public AcademicRecordServiceImpl(GradeRepository gradeRepository) {
    this.gradeRepository = gradeRepository;
  }

  @Override
  public Double calculateAverage() {
    logger.info("Calculating the grade point average");
    List<Grade> gradeList = this.gradeRepository.findAllGrades();
    return gradeList.stream().mapToDouble( Grade::grade ).average().getAsDouble();
  }

  @Override
  public Integer sumNumberOfGrades() {
    logger.info("Adding the number of grades");
    List<Grade> gradeList = this.gradeRepository.findAllGrades();
    return gradeList.size();
  }

  @Override
  public Grade getGrade(String proyecto) throws GradeNotFoundException {
    Optional<Grade> gradeOptional = this.gradeRepository.getGrade( proyecto );

    if( gradeOptional.isEmpty() ){
      logger.error( "No grade found for the project {}", proyecto );
      throw new GradeNotFoundException(proyecto);
    }

    return gradeOptional.get();
  }

  @Override
  public List<Grade> listAllGrades() {
    logger.info("creating the grade list");
    this.gradeRepository.findAllGrades ();
    return this.gradeRepository.findAllGrades();

  }

  @Override
  public Grade addGrade(Grade newGrade) {
    logger.info("adding a new grade");
    return this.gradeRepository.addGrade(newGrade);


  }
}
