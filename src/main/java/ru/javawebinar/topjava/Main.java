package ru.javawebinar.topjava;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm ");
        System.out.println(formatter.format(LocalDateTime.of(2015, Month.MAY, 30, 13, 0)));
    }
}
