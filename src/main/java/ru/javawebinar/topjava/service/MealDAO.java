package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sanzhar on 9/14/16.
 */
public class MealDAO implements MealService {
    private final static AtomicInteger counter = new AtomicInteger();

    public static int getCounter() {
        return counter.incrementAndGet();
    }

    private static final int limitResultsPerPage = 5;
    List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, getCounter()),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, getCounter()),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, getCounter()),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, getCounter()),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, getCounter()),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, getCounter())
    );

    public void addItem(Meal meal) {
        meals.add(meal.getId(), meal);
    }

    public void updateItem(Meal meal) {
        meals.add(meal.getId(), meal);
    }

    public void removeItem(Meal meal) {
        meals.add(meal.getId(), null);
    }

    public List<MealWithExceed> getItems(int page) {
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.of(0, 0), LocalTime.of(23, 0),2000);
    }

}
