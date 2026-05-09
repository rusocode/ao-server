package com.ao.config.yaml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IntervalsConfigYamlTest {

    private static final String TEST_CONFIG = "data/config/game_intervals_test.yaml";
    private static final String MISSING_CONFIG = "data/config/nonexistent.yaml";

    @Test
    void load_validYaml_parsesRegenerationIntervals() {
        IntervalsConfigYaml config = new IntervalsConfigYaml(TEST_CONFIG);

        assertThat(config.getRegeneration().getHp()).isEqualTo(100);
        assertThat(config.getRegeneration().getStamina()).isEqualTo(20);
        assertThat(config.getRegeneration().getManaRecoveryPercent()).isEqualTo(5);
    }

    @Test
    void load_validYaml_parsesSurvivalIntervals() {
        IntervalsConfigYaml config = new IntervalsConfigYaml(TEST_CONFIG);

        assertThat(config.getSurvival().getHunger()).isEqualTo(4000);
        assertThat(config.getSurvival().getThirst()).isEqualTo(3000);
    }

    @Test
    void load_validYaml_parsesStatesIntervals() {
        IntervalsConfigYaml config = new IntervalsConfigYaml(TEST_CONFIG);

        assertThat(config.getStates().getPoison()).isEqualTo(250);
    }

    @Test
    void load_validYaml_parsesNpcInterval() {
        IntervalsConfigYaml config = new IntervalsConfigYaml(TEST_CONFIG);

        assertThat(config.getNpc().getAiTick()).isEqualTo(200);
    }

    @Test
    void load_validYaml_parsesWorldSaveInterval() {
        IntervalsConfigYaml config = new IntervalsConfigYaml(TEST_CONFIG);

        assertThat(config.getWorld().getSaveInterval()).isEqualTo(60);
    }

    @Test
    void load_missingFile_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> new IntervalsConfigYaml(MISSING_CONFIG))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MISSING_CONFIG);
    }

}