package com.zaky.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaky.app.models.Country;
import com.zaky.app.models.Player;
import com.zaky.app.models.PlayerData;
import com.zaky.app.models.Stats;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayersService {

    List<Player> localPlayers = new ArrayList<>();

    @PostConstruct
    public void initPlayers() {
        Player player1 = new Player();
        PlayerData player1Data = new PlayerData();
        Country player1Country = new Country();
        player1Country.setCode("GER");
        player1Data.setRank(8);
        player1Data.setWeight(90.5);
        player1Data.setHeight(190);
        player1.setData(player1Data);
        player1.setCountry(player1Country);
        player1.setFirstname("Mané");
        player1.setId("2");

        Player player2 = new Player();
        PlayerData player2Data = new PlayerData();
        Country player2Country = new Country();
        player2Country.setCode("GER");
        player2Data.setRank(5);
        player2Data.setWeight(83000);
        player2Data.setHeight(180);
        player2.setData(player2Data);
        player2.setCountry(player2Country);
        player2.setFirstname("Salah");
        player2.setId("5");
        localPlayers.add(player1);
        localPlayers.add(player2);
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

    public String json() {
        try (java.io.InputStream is = new java.net.URL("https://data.latelier.co/training/tennis_stats/headtohead.json").openStream()) {
            String contents = new String(is.readAllBytes());
            ObjectMapper mapper = new ObjectMapper();
            localPlayers = Arrays.asList(mapper.readValue(contents, Player[].class));

            return localPlayers.toString();
        } catch (IOException e) {
            return "Error";
        }
    }

    public List<Player> addPlayer(List<Player> players, Player player) {
        players.add(player);
        return players;
    }
}
