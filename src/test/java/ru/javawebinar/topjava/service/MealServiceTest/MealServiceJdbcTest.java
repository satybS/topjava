package ru.javawebinar.topjava.service.MealServiceTest;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

/**
 * Created by sanzhar on 10/20/16.
 */
@ActiveProfiles({Profiles.POSTGRES,Profiles.JDBC})
public class MealServiceJdbcTest extends AbstractMealServiceTest {
}
