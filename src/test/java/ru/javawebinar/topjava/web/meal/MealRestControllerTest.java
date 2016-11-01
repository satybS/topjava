package ru.javawebinar.topjava.web.meal;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by sanzhar on 10/31/16.
 */
public class MealRestControllerTest extends AbstractControllerTest {
    public final static String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER.contentMatcher(MEAL1));

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2),
                mealService.getAll(USER_ID));

    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay()))));


    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = MealTestData.getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        Meal createdMeal = MealTestData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdMeal)))
                .andExpect(status().isOk());

        Meal returned = MATCHER.fromJsonAction(action);
        createdMeal.setId(returned.getId());

        MATCHER.assertEquals(createdMeal, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(createdMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime from = LocalDateTime.of(2015, Month.MAY, 30, 01, 0);
        LocalDateTime to = LocalDateTime.of(2015, Month.MAY, 31, 23, 0);
        TestUtil.print(mockMvc.perform(get(REST_URL +
                "bt?from=" + ISO_LOCAL_DATE_TIME.format(from) +
                "&to=" + ISO_LOCAL_DATE_TIME.format(to)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER_EXCEEDED.contentListMatcher(
                        MealsUtil.getFilteredWithExceeded(MEALS, from.toLocalTime(), to.toLocalTime(),
                                AuthorizedUser.getCaloriesPerDay())))
        );

    }

}