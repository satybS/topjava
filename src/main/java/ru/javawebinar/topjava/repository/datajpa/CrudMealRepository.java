package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    Meal save(Meal meal, int userId);

    @Transactional
    @Modifying
    @Query ("DELETE FROM Meal u WHERE u.id=:id AND u.user.id=:userId")
    int delete(@Param("id")int id, @Param("userId")int userId);

    @Transactional
    Meal get(int id, int userId);


    @Transactional
    @Query("SELECT u FROM Meal u ORDER BY u.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

}
