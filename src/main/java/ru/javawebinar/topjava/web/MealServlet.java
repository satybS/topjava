package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.service.MealDAO;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sanzhar on 9/14/16.
 */
public class MealServlet extends HttpServlet {
    MealService mealService=new MealDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals",mealService.getItems(1));
        request.getRequestDispatcher("/mealList.jsp").forward(request,response);
    }
}
