package com.zaky.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaky.app.models.Player;
import com.zaky.app.models.Root;
import com.zaky.app.models.Stats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayersService {

    List<Player> localPlayers = new ArrayList<>();
    public static final String DATA_URL = "https://data.latelier.co/training/tennis_stats/headtohead.json";

    @PostConstruct
    public void initPlayers() {
        localPlayers = getPlayersFromJsonLink(DATA_URL);
    }

    public List<Player> getAllPlayers() {
        return localPlayers;
    }

    public List<Player> orderByRank(List<Player> players) {
        return players.stream()
                .sorted(Comparator.comparingInt(p -> p.getData().getRank()))
                .collect(Collectors.toList());
    }

    public Player getPlayerById(List<Player> players, String id) {
        return players.stream()
                .filter(p -> id != null && !id.isEmpty() && id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    public Stats getStats(List<Player> players) {
        return Stats.builder()
                .imcMoyen(moyenImc(players))
                .tailleMediane(tailleMediane(players))
                .build();
    }

    public double moyenImc(List<Player> players) {
        double imcTotal = 0;
        for (Player player : players) {
            imcTotal += ImcService.getIMC(player.getData().getWeight(), player.getData().getHeight());
        }
        return imcTotal / players.size();
    }

    // Moyen avec Stream
    public double moyenImcWithStream(List<Player> players) {
        return players.stream()
                .mapToDouble(p -> ImcService.getIMC(p.getData().getWeight(), p.getData().getHeight()))
                .average().orElse(Double.NaN);
    }

    public double tailleMediane(List<Player> players) {
        double mediane;
        int middle = players.size() / 2;
        if (players.size() % 2 == 1) {
            mediane = players.get(middle).getData().getHeight();
        } else {
            mediane = (players.get(middle - 1).getData().getHeight()
                    + players.get(middle).getData().getHeight()) / 2;
        }
        return mediane;
    }

    // autre méthode avec Stream, mais plus complexe :)
    public double tailleMedianeavecStream(List<Player> players) {
        return players.stream().mapToDouble(p -> p.getData().getHeight())
                .sorted()
                .skip((players.size() - 1) / 2).limit((2 - players.size() % 2))
                .average().orElse(Double.NaN);
    }


    public List<Player> getPlayersFromJsonLink(String url) {
        List<Player> players = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Root root = mapper.readValue(new URL(url), Root.class);
            players = root.getPlayers();
        } catch (IOException e) {
            log.error("Erreur lors de la récupération des données");
        }
        return players;
    }


    public List<Player> addPlayer(List<Player> players, Player player) {
        players.add(player);
        return players;
    }
}
