package com.zaky.app.services;

import com.zaky.app.models.Country;
import com.zaky.app.models.Player;
import com.zaky.app.models.PlayerData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PlayersService.class})
class PlayersServiceTest {
    @Autowired
    private PlayersService playersService;

    @Test
    void shoud_read_json_data() {
        Assertions.assertThat(playersService.getAllPlayers()).isNotEmpty();
    }

    @Test
    void should_order_players_by_rank() {
        List<Player> players = initPlayers();
        Assertions.assertThat(playersService.orderByRank(players)).isNotEmpty();
        Assertions.assertThat(playersService.orderByRank(players).get(0).getFirstname()).isEqualTo("Salah");
    }

    @Test
    void should_find_with_id() {
        List<Player> players = initPlayers();
        Assertions.assertThat(playersService.getPlayerById(players, "")).isNull();
        Assertions.assertThat(playersService.getPlayerById(players, "1")).isNull();
        Assertions.assertThat(playersService.getPlayerById(players, null)).isNull();
        Assertions.assertThat(playersService.getPlayerById(players, "5").getFirstname()).isEqualTo("Salah");
        Assertions.assertThat(playersService.getPlayerById(players, "2").getFirstname()).isEqualTo("Mané");
    }

    @Test
    void should_calcul_imc_moyen() {
        List<Player> players = initPlayers();
        Assertions.assertThat(playersService.moyenImc(players)).isNotZero();
        Assertions.assertThat(playersService.moyenImc(players)).isEqualTo(25.5);

        // Test Stream méthode
        Assertions.assertThat(playersService.moyenImcWithStream(players)).isNotZero();
        Assertions.assertThat(playersService.moyenImcWithStream(players)).isEqualTo(25.5);
    }

    @Test
    void should_calcul_taille_mediane() {
        List<Player> players = initPlayers();
        Assertions.assertThat(playersService.tailleMediane(players)).isNotZero();
        Assertions.assertThat(playersService.tailleMediane(players)).isEqualTo(185);
        // Test Stream méthode
        Assertions.assertThat(playersService.tailleMedianeavecStream(players)).isNotZero();
        Assertions.assertThat(playersService.tailleMedianeavecStream(players)).isEqualTo(185);
    }

    private List<Player> initPlayers() {
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
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

    @Test
    void test_json() {
        Assertions.assertThat(playersService.json()).isEqualTo("test");
    }

    @Test
    void should_add_new_player() {
        List<Player> players = initPlayers();
        Assertions.assertThat(players).hasSize(2);
        Player player = new Player();
        player.setFirstname("Fermino");
        playersService.addPlayer(players, player);
        Assertions.assertThat(players).hasSize(3);

    }
}