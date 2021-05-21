package com.game.service;

import com.game.entity.Player;
import com.game.repository.IPlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements IPlayerService {

    Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    private IPlayerRepository playerRepository;

    @Override
    @Transactional
    public Player getById(Long id) {
        logger.info("PlayerServiceImpl getById", id);
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Player player) {
        logger.info("PlayerServiceImpl save", player);
        playerRepository.save(player);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("PlayerServiceImpl delete", id);
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Player> getAll() {
        logger.info("PlayerServiceImpl getAll");
        return playerRepository.findAll();
    }
}