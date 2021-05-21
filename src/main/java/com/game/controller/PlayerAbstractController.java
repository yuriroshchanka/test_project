package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class PlayerAbstractController {

    protected boolean hasWrongId(@PathVariable("id") Long playerId) {
        return playerId == null || playerId < 1;
    }

    protected void updateName(Player player, Player tablePlayer) {
        if (player.getName() != null) {
            tablePlayer.setName(player.getName());
        }
    }

    protected void updateTitle(Player player, Player tablePlayer) {
        if (player.getTitle() != null) {
            tablePlayer.setTitle(player.getTitle());
        }
    }

    protected void updateRace(Player player, Player tablePlayer) {
        if (player.getRace() != null) {
//            String race = String.valueOf(player.getRace());
//            tablePlayer.setRace(Race.valueOf(race));
            tablePlayer.setRace(player.getRace());
        }
    }

    protected void updateProfession(Player player, Player tablePlayer) {
        if (player.getProfession() != null) {
//            String profession = String.valueOf(player.getProfession());
//            tablePlayer.setProfession(Profession.valueOf(profession));
            tablePlayer.setProfession(player.getProfession());
        }
    }

    protected void updateBirthday(Player player, Player tablePlayer) {
        if (player.getBirthday() != null) {
            tablePlayer.setBirthday(player.getBirthday());
        }
    }
    protected void updateIsBanned(Player player, Player tablePlayer) {
        if (player.getBanned() != null) {
            tablePlayer.setBanned(player.getBanned());
        }
    }
    protected void updateExperience(Player player, Player tablePlayer) {
        if (player.getExperience() != null) {
            tablePlayer.setExperience(player.getExperience());
        }
    }
    protected void updateLevel(Player player) {
        Integer experience = player.getExperience();
        if (experience == null) {
            experience = 0;
        }
        int exp = (int) ((Math.sqrt(2500 + 200*experience)-50)/100);
        player.setLevel(exp);
    }
    protected void updateNextLevelExp(Player tablePlayer) {
        Integer untilNextLevel = tablePlayer.getUntilNextLevel();
        Integer level = tablePlayer.getLevel();
        if (level == null) {
            level = 0;
        }
        int nextLevelExp = 50*(level + 1) * (level + 2) - tablePlayer.getExperience();
        tablePlayer.setUntilNextLevel(nextLevelExp);
    }
}
