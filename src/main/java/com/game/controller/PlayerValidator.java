package com.game.controller;

import com.game.entity.Player;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PlayerValidator {
    private static Date dateMin;
    private static Date dateMax;
    static {
        Calendar cal = Calendar.getInstance();
        cal.set(2000,01, 01);
        dateMin = new Date(cal.getTimeInMillis());
        cal.set(3000,01,01);
        dateMax = new Date(cal.getTimeInMillis());
    }

    public static boolean hasNullValues(Player player) {
        return player == null || player.getName() == null || player.getTitle() == null || player.getRace() == null
                || player.getProfession() == null || player.getExperience() == null || player.getBirthday() == null;
    }

    public static boolean hasErrors(Player player) {
        try {
            if (hasNameError(player) || player.getTitle().length() > 30) {
                return true;
            }
            if (hasWrongRegistration(player)) {
                return true;
            }
            if (player.getExperience() < 0 || player.getExperience() > 10_000_000) {
                return true;
            }
        } catch (Exception e){
            LoggerFactory.getLogger(PlayerValidator.class).info(e.getMessage());
        }
        return false;
    }

    public static boolean hasWrongRegistration(Player player) {
        Date birthday = player.getBirthday();
        return birthday.before(dateMin) || birthday.after(dateMax);
    }

    public static boolean hasNameError(Player player) {
        String pName = player.getName();
        return pName.isEmpty() || pName.length() > 12;
    }
}
