package com.zaky.app.controllers;

import com.zaky.app.models.Player;
import com.zaky.app.models.Stats;
import com.zaky.app.services.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    PlayersService playersService;

    @GetMapping("/")
    public String home() {
        return "Welcome to Tennis Players App.";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playersService.orderByRank(playersService.getAllPlayers());
    }

    @GetMapping("/players/{idPlayer}")
    public Player getPlayer(@PathVariable String idPlayer) {
        return playersService.getPlayerById(playersService.getAllPlayers(), idPlayer);
    }

    @GetMapping("/stats")
    public Stats getStats() {
        return playersService.getStats(playersService.getAllPlayers());
    }
}
