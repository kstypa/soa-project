package project.soa.rest;


import project.soa.model.Dish;

import java.util.List;
import java.util.Locale;

public class Translator {
    public static List<Dish> translateCourses(List<Dish> dishes, Locale targetLocale) {
        Locale localeGb = new Locale("en", "GB");
        Locale localeDe = new Locale("de", "DE");
        if (localeGb.equals(targetLocale))
            dishes.forEach(e -> e.setName(e.getName() + " ang"));
        if (localeDe.equals(targetLocale))
            dishes.forEach(e -> e.setName(e.getName() + " niem"));
        return dishes;
    }
}
