package dao;

import model.Vacancy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VacancyDao {

    @Select("SELECT ID, NAME, SALARY, EXPERIENCE, CITY "
            + "FROM VACANCY ")
    List<Vacancy> findAll();

    @Insert("INSERT INTO VACANCY (ID, NAME, SALARY, EXPERIENCE, CITY) "
            + "VALUES(#{id}, #{name}, #{salary}, #{experience}, #{city})")
    void save(Vacancy resource);

    @Select("SELECT ID, NAME, SALARY, EXPERIENCE, CITY "
            + "FROM VACANCY WHERE ID = #{id}")
    Vacancy findById(Long id);

    @Update("UPDATE VACANCY SET NAME = #{name}, SALARY = #{salary}, EXPERIENCE = #{experience}, CITY = #{city} "
            + " WHERE ID = #{id}")
    void update(Vacancy vacancy);

    @Delete("DELETE FROM VACANCY WHERE ID = #{id}")
    void deleteById(Long id);
}
