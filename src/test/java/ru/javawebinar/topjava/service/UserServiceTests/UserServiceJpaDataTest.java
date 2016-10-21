package ru.javawebinar.topjava.service.UserServiceTests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

/**
 * Created by sanzhar on 10/18/16.
 */

@ActiveProfiles({Profiles.POSTGRES,Profiles.DATA_JPA})
public class UserServiceJpaDataTest extends AbstractUserServiceTest {

}


