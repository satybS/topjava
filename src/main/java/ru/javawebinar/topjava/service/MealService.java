package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

/**
 * Created by sanzhar on 9/14/16.
 */
public interface MealService {
    void addItem (Meal meal);
    void updateItem(Meal meal);
    void removeItem(Meal meal);
    List<MealWithExceed>getItems(int page);

}
