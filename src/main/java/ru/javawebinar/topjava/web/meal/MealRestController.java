package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal, int userId) {
        LOG.info("save " + meal.getId());
        return service.save(meal, userId);
    }

    public void delete(int mealId, int userId) {
        LOG.info("delete " + mealId);
        service.delete(mealId, userId);
    }

    public Meal get(int mealId, int userId) {
        LOG.info("get " + userId);
        return service.get(mealId, userId);
    }

    public Collection<Meal> getAll(int userId) {
        LOG.info("getall " + userId);
        return service.getAll(userId);
    }

}
