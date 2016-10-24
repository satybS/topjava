package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by sanzhar on 10/23/16.
 */
@Controller
public class MealController {

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");
        int userId = AuthorizedUser.id();
        if (action == null) {
            model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
            return "meals";

        } else if ("delete".equals(action)) {
            int id = getId(request);
            service.delete(id, userId);
            return "redirect:meals";

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = "create".equals(action) ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000) :
                    service.get(getId(request), userId);
            model.addAttribute("meal", meal);
            return "meal";
        }
        return "redirect:meals";
    }


    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String setMeal(HttpServletRequest request) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = AuthorizedUser.id();
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getAttribute("dateTime").toString()),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            service.save(meal, userId);
        } else {
            service.update(meal, userId);
        }
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}

