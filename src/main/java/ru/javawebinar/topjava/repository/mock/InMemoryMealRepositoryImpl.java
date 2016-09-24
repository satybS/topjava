package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Integer userId = AuthorizedUser.id();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, AuthorizedUser.id()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        if (repository.get(userId) == null) {
            Map<Integer, Meal> userRepository = new HashMap<>();
            userRepository.put(meal.getId(), meal);
            repository.put(userId, userRepository);
        }
        return repository.get(userId).put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        if (repository.get(userId).containsKey(mealId)) {
            repository.get(userId).remove(mealId);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {

        return repository.get(userId).get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return repository.get(userId).values();
    }
}

