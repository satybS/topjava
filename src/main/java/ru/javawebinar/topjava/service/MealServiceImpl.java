package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    public Meal save(Meal meal, int userId) {

        return repository.save(meal, userId);
    }

    public void delete(int mealId, int userId) {
        ExceptionUtil.checkNotFoundWithId(repository.delete(mealId, userId), userId);
    }

    public Meal get(int mealId, int userId) {
        return ExceptionUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public Collection<Meal> getAll(int userId) {

        return repository.getAll(userId);
    }
}
