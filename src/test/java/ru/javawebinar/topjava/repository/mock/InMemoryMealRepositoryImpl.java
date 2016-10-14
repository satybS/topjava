package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Comparator<UserMeal> MEAL_COMPARATOR = Comparator.comparing(UserMeal::getDateTime).reversed();

    // Map  userId -> (mealId-> meal)
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        } else if (get(mealId, userId) == null) {
            return null;
        }
        Map<Integer, UserMeal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        meals.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return getAllStream(userId).collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllStream(userId)
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    private Stream<UserMeal> getAllStream(int userId) {
        Map<Integer, UserMeal> meals = repository.get(userId);
        return meals == null ?
                Stream.empty() : meals.values().stream().sorted(MEAL_COMPARATOR);
    }
}

