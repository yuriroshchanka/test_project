package com.game.controller;

import com.game.entity.Player;
import com.game.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RestController
@RequestMapping("/rest/players")
public class PlayersController extends PlayerAbstractController {

    @Autowired
    private IPlayerService playerService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId) {
        if (hasWrongId(playerId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = this.playerService.getById(playerId);
        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
         if (PlayerValidator.hasNullValues(player) || PlayerValidator.hasErrors(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        updateLevel(player);
        updateNextLevelExp(player);
        this.playerService.save(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        if (hasWrongId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = this.playerService.getById(id);
        if (player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getAllPlayers(HttpServletRequest request) {
        List<Player> players = this.playerService.getAll();
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PlayerFilter.filterParams(players, request);
        PlayerFilter.filterPage(players, request);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getCountAllPlayers(HttpServletRequest request) {
        List<Player> players = this.playerService.getAll();
        PlayerFilter.filterParams(players, request);
        return new ResponseEntity<>(players.size(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable Long id) {
        if (hasWrongId(id) || PlayerValidator.hasErrors(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player tablePlayer = this.playerService.getById(id);
        if (tablePlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updateName(player, tablePlayer);
        updateTitle(player, tablePlayer);
        updateExperience(player, tablePlayer);
        updateBirthday(player, tablePlayer);
        updateIsBanned(player, tablePlayer);
        updateProfession(player, tablePlayer);
        updateRace(player, tablePlayer);
        updateLevel(tablePlayer);
        updateNextLevelExp(tablePlayer);
        this.playerService.save(tablePlayer);
        return new ResponseEntity<>(tablePlayer, HttpStatus.OK);
    }
}