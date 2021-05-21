package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerFilter {

    public static void filterParams(List<Player> players, HttpServletRequest request) {
        List<Player> toRemove = new ArrayList<>();
        for (Player player : players) {
            if (!hasMatchedRequest(request, player)) {
                toRemove.add(player);
            }
        }
        players.removeAll(toRemove);
    }

    public static void filterPage(List<Player> players, HttpServletRequest request) {
        sortPlayers(players, request.getParameter("order"));
        removeNotMatchedPage(players, request);
    }

    private static void sortPlayers(List<Player> players, String orderString) {
        PlayerOrder order = orderString != null ? PlayerOrder.valueOf(orderString) : null;
        if (PlayerOrder.BIRTHDAY == order) {
            players.sort(Comparator.comparing(Player::getBirthday));
        } else if (PlayerOrder.EXPERIENCE == order) {
            players.sort(Comparator.comparing(Player::getExperience));
        } else if (PlayerOrder.LEVEL == order) {
            players.sort(Comparator.comparing(Player::getLevel));
        } else if (PlayerOrder.NAME == order) {
            players.sort(Comparator.comparing(Player::getName));
        } else {
            players.sort(Comparator.comparing(Player::getId));
        }
    }

    private static boolean hasMatchedRequest(HttpServletRequest request, Player player) {
        return hasNameMatched(request, player) && hasTitleMatched(request, player)
                && hasRaceMatched(request, player) && hasProfessionMatched(request, player)
                && hasBirthdayMatched(request, player) && hasMatchedBanned(request, player)
                && hasExperienceMatched(request, player) && hasLevelMatched(request, player);
    }

    private static void removeNotMatchedPage(List<Player> players, HttpServletRequest request) {
        List<Player> toRemove = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!hasMatchedPage(request, i)){
                toRemove.add(players.get(i));
            }
        }
        players.removeAll(toRemove);
    }

    private static boolean hasMatchedPage(HttpServletRequest request, int index) {
        String pageN = request.getParameter("pageNumber");
        Integer pageNumber = pageN != null ? Integer.valueOf(pageN) : 0;
        String pageS = request.getParameter("pageSize");
        Integer pageSize = pageS != null ? Integer.valueOf(pageS) : 3;
        return pageNumber == index/ pageSize;
    }

    private static boolean hasNameMatched(HttpServletRequest request, Player player) {
        String name = request.getParameter("name");
        return name == null || player.getName().contains(name);
    }

    private static boolean hasTitleMatched(HttpServletRequest request, Player player) {
        String title = request.getParameter("title");
        return title == null || player.getTitle().contains(title);
    }

    private static boolean hasRaceMatched(HttpServletRequest request, Player player) {
        String race = request.getParameter("race");
        return race == null || player.getRace().equals(Race.valueOf(race));
    }

    private static boolean hasProfessionMatched(HttpServletRequest request, Player player) {
        String profession = request.getParameter("profession");
        return profession == null || player.getProfession().equals(Profession.valueOf(profession));
    }

    private static boolean hasMatchedBanned(HttpServletRequest request, Player player) {
        String banned = request.getParameter("banned");
        return banned == null || player.getBanned().toString().equalsIgnoreCase(banned);
    }

    private static boolean hasExperienceMatched(HttpServletRequest request, Player player) {
        Integer playerExp = player.getExperience();
        String experienceMin = request.getParameter("minExperience");
        Integer expMin = experienceMin != null ? Integer.valueOf(experienceMin) : null;
        String experienceMax = request.getParameter("maxExperience");
        Integer expMax = experienceMax != null ? Integer.valueOf(experienceMax) : null;
        return expMin == null && expMax == null
                || expMin != null && expMax != null && playerExp >= expMin && playerExp <= expMax
                || expMax == null && playerExp >= expMin
                || expMin == null && playerExp <= expMax;
    }

    private static boolean hasLevelMatched(HttpServletRequest request, Player player) {
        String levelM = request.getParameter("minLevel");
        Integer levelMin = levelM != null ? Integer.valueOf(levelM) : null;
        String levelMa = request.getParameter("maxLevel");
        Integer levelMax = levelMa != null ? Integer.valueOf(levelMa) : null;
        return levelMax == null && levelMin == null
        || levelMax != null && levelMin != null && player.getLevel() >= levelMin && player.getLevel() <= levelMax
                || levelMax == null && player.getLevel() >= levelMin
                || levelMin == null && player.getLevel() <= levelMax;
    }

    private static boolean hasBirthdayMatched(HttpServletRequest request, Player player) {
        Date birthday = player.getBirthday();
        String afterReq = request.getParameter("after");
        Long afterL = afterReq != null ? Long.valueOf(afterReq) : null;
        Date after = afterL != null ? new Date(afterL) : null;

        String beforeReq = request.getParameter("before");
        Long beforeL = beforeReq != null ? Long.valueOf(beforeReq) : null;
        Date before = beforeL != null ? new Date(beforeL) : null;
        return before == null && after == null
                || after != null && before != null && birthday.after(after) && birthday.before(before)
                || after != null && before == null && birthday.after(after)
            || after == null && before != null && birthday.before(before);
    }
}