package com.zaky.app.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ImcService.class, PlayersService.class})
class ImcServiceTest {
    @Autowired
    private ImcService imcService;
    @Autowired
    private PlayersService playersService;

    @Test
    void should_calcul_imc() {
        Assertions.assertThat(ImcService.getIMC(93.5, 1)).isEqualTo(94);
        Assertions.assertThat(ImcService.getIMC(93.5, 180)).isEqualTo(29);
        Assertions.assertThat(ImcService.getIMC(87000, 1.85)).isEqualTo(25);
    }

    @Test
    void should_get_IMC_Interpretation() {
        Assertions.assertThat(ImcService.getIMCInterpretation(0)).isEqualTo("Normal");
        Assertions.assertThat(ImcService.getIMCInterpretation(19)).isEqualTo("Normal");
        Assertions.assertThat(ImcService.getIMCInterpretation(25)).isEqualTo("Overweight");
    }
}