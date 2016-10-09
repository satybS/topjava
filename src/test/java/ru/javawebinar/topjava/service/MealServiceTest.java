package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static ru.javawebinar.topjava.MealTestData.BREAKFAST;
import static ru.javawebinar.topjava.MealTestData.LANCH;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_MEAL_SEQ;

/**
 * Created by sanzhar on 10/9/16.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(START_MEAL_SEQ, USER_ID);
        MATCHER.assertEquals(BREAKFAST, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(START_MEAL_SEQ, USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(LANCH), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(LANCH), service.getBetweenDateTimes(LocalDateTime.parse("2015-05-30T11:00:00"),LocalDateTime.parse("2015-05-30T14:00:00"),USER_ID));
    }


    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(LANCH, BREAKFAST), all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(START_MEAL_SEQ, LocalDateTime.MAX, "Updated", 300);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(START_MEAL_SEQ, USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.MAX, "Updated", 300);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, LANCH, BREAKFAST), service.getAll(USER_ID));
    }

}