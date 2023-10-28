package org.example.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.model.Grade;

public class GradeUsingFileRepositoryImpl implements GradeRepository {
  private static final Logger logger = LoggerFactory.getLogger( GradeUsingFileRepositoryImpl.class);
  private final List<Grade> gradeList;

  public GradeUsingFileRepositoryImpl() {
    this.gradeList = new ArrayList<>(loadGradesFromFile());//Al momento de construir el Repository se cargan los datos desde el archivo
  }

  private List<Grade> loadGradesFromFile(){
    logger.info( "Loading data from file" );
    List<String> plainTextGradeList =  readFileWithGrades();
         return plainTextGradeList.stream().map(this::createGradeFromPlainText).toList();
  }

  private List<String> readFileWithGrades(){


    Path path = Paths.get( "./src/main/resources/notas.txt");
    try (Stream<String> stream = Files.lines( path)) {
      return stream.toList();
    } catch (IOException x) {
      logger.error("Error while reading grade file", x);
    }
    return Collections.emptyList();
  }

  public Grade createGradeFromPlainText (String plainTextGrade){

    String[] gradeArray = plainTextGrade.split(",");

      return new Grade( gradeArray[0], Double.valueOf(gradeArray[1]), LocalDate.parse( gradeArray[2], DateTimeFormatter.ISO_DATE));
  }


  @Override
  public List<Grade> findAllGrades() {
    return gradeList;
  }

  @Override
  public Optional<Grade> getGrade(String proyecto) {
    return this.gradeList.stream().filter( grade -> grade.project().equals( proyecto ) ).findAny();
  }

  @Override
  public Grade addGrade(Grade newGrade) {
    this.gradeList.add( newGrade );

    return this.gradeList.stream()
            .filter( isTheGradeOfTheProject( newGrade ) )
            .findAny()
            .orElse( null );
  }

  private Predicate<Grade> isTheGradeOfTheProject(Grade newGrade) {
    return grade -> grade.project().equals( newGrade.project() );
  }



}
