package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import static ru.javawebinar.topjava.model.BaseEntity.START_MEAL_SEQ;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final Meal BREAKFAST = new Meal(START_MEAL_SEQ, LocalDateTime.parse("2015-05-30T10:00:00"), "Завтрак", 500);
    public static final Meal LANCH = new Meal(START_MEAL_SEQ + 1, LocalDateTime.parse("2015-05-30T13:00:00"), "Обед", 1000);


    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
