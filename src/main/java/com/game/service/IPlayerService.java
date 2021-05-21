package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface IPlayerService {
    Player getById(Long id);
    void save(Player player);
    void delete(Long id);
    List<Player> getAll();
}
