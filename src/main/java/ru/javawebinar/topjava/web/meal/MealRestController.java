package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }


    @GetMapping(value = "/bt", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam("from") @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                           @RequestParam ("to") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return super.getBetween(from.toLocalDate(), from.toLocalTime(), to.toLocalDate(), to.toLocalTime());
    }
}
