package com.ao.config.yaml;

import com.ao.config.IntervalsConfig;
import com.ao.utils.ResourceUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implementacion de la configuracion de intervalos que carga los datos desde un archivo YAML usando Jackson.
 */

public class IntervalsConfigYaml implements IntervalsConfig {

    @JsonProperty("intervals")
    private IntervalsData intervals;

    /**
     * El constructor usa Guice (@Inject) para recibir la ruta del archivo. Jackson se encarga de "parsear" el archivo y rellenar
     * esta misma clase.
     */
    @Inject
    public IntervalsConfigYaml(@Named("IntervalsConfigYaml") String configPath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try (InputStream is = ResourceUtils.getStream(configPath)) {
            if (is == null) throw new IllegalArgumentException("¡No se encontro el archivo de intervalos: " + configPath);

            // Aqui ocurre la magia: Jackson lee el InputStream y "mapea" el contenido a 'this'
            mapper.readerForUpdating(this).readValue(is);
            Logger.info("Intervalos de juego cargados correctamente desde {}", configPath);

        } catch (IOException e) {
            Logger.error("Error al cargar los intervalos desde YAML", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegenerationConfig getRegeneration() {
        return intervals.regeneration;
    }

    @Override
    public SurvivalConfig getSurvival() {
        return intervals.survival;
    }

    @Override
    public StatesConfig getStates() {
        return intervals.states;
    }

    @Override
    public CombatConfig getCombat() {
        return intervals.combat;
    }

    @Override
    public MovementConfig getMovement() {
        return intervals.movement;
    }

    @Override
    public NpcConfig getNpc() {
        return intervals.npc;
    }

    @Override
    public WorldConfig getWorld() {
        return intervals.world;
    }

    @Override
    public NetworkConfig getNetwork() {
        return intervals.network;
    }

    @Override
    public SystemConfig getSystem() {
        return intervals.system;
    }

    /**
     * Clases de Datos Internas (POJOs).
     * <p>
     * Estas clases sirven para que Jackson sepa donde meter cada valor del YAML.
     */

    public static class IntervalsData {
        public RegenerationData regeneration;
        public SurvivalData survival;
        public StatesData states;
        public CombatData combat;
        public MovementData movement;
        public NpcData npc;
        public WorldData world;
        public NetworkData network;
        public SystemData system;
    }

    public static class RegenerationData implements RegenerationConfig {
        @JsonProperty("hp")
        private int hp;
        @JsonProperty("hp_resting")
        private int hpResting;
        @JsonProperty("stamina")
        private int stamina;
        @JsonProperty("stamina_resting")
        private int staminaResting;
        @JsonProperty("mana_recovery_percent")
        private int manaRecoveryPercent;

        @Override
        public int getHp() {
            return hp;
        }

        @Override
        public int getHpResting() {
            return hpResting;
        }

        @Override
        public int getStamina() {
            return stamina;
        }

        @Override
        public int getStaminaResting() {
            return staminaResting;
        }

        @Override
        public int getManaRecoveryPercent() {
            return manaRecoveryPercent;
        }
    }

    public static class SurvivalData implements SurvivalConfig {
        private int thirst, hunger;

        @Override
        public int getThirst() {
            return thirst;
        }

        @Override
        public int getHunger() {
            return hunger;
        }
    }

    public static class StatesData implements StatesConfig {
        private int poison, paralyzed, invisible, hidden, cold;

        @Override
        public int getPoison() {
            return poison;
        }

        @Override
        public int getParalyzed() {
            return paralyzed;
        }

        @Override
        public int getInvisible() {
            return invisible;
        }

        @Override
        public int getHidden() {
            return hidden;
        }

        @Override
        public int getCold() {
            return cold;
        }
    }

    public static class CombatData implements CombatConfig {
        @JsonProperty("user_attack")
        private int userAttack;
        @JsonProperty("npc_attack")
        private int npcAttack;
        @JsonProperty("spell_cast")
        private int spellCast;
        @JsonProperty("magic_hit_delay")
        private int magicHitDelay;
        @JsonProperty("hit_magic_delay")
        private int hitMagicDelay;
        @JsonProperty("bow_attack")
        private int bowAttack;
        @JsonProperty("use_item")
        private int useItem;
        private int work;

        @Override
        public int getUserAttack() {
            return userAttack;
        }

        @Override
        public int getNpcAttack() {
            return npcAttack;
        }

        @Override
        public int getSpellCast() {
            return spellCast;
        }

        @Override
        public int getMagicHitDelay() {
            return magicHitDelay;
        }

        @Override
        public int getHitMagicDelay() {
            return hitMagicDelay;
        }

        @Override
        public int getBowAttack() {
            return bowAttack;
        }

        @Override
        public int getUseItem() {
            return useItem;
        }

        @Override
        public int getWork() {
            return work;
        }
    }

    public static class MovementData implements MovementConfig {
        private int user;

        @Override
        public int getUser() {
            return user;
        }
    }

    public static class NpcData implements NpcConfig {
        @JsonProperty("ai_tick")
        private int aiTick;

        @Override
        public int getAiTick() {
            return aiTick;
        }
    }

    public static class WorldData implements WorldConfig {
        @JsonProperty("save_interval")
        private int saveInterval;
        @JsonProperty("summon_duration")
        private int summonDuration;
        @JsonProperty("rain_stamina_loss")
        private int rainStaminaLoss;
        @JsonProperty("fx_wav_delay")
        private int fxWavDelay;

        @Override
        public int getSaveInterval() {
            return saveInterval;
        }

        @Override
        public int getSummonDuration() {
            return summonDuration;
        }

        @Override
        public int getRainStaminaLoss() {
            return rainStaminaLoss;
        }

        @Override
        public int getFxWavDelay() {
            return fxWavDelay;
        }
    }

    public static class NetworkData implements NetworkConfig {
        @JsonProperty("connection_retry")
        private int connectionRetry;
        @JsonProperty("close_connection")
        private int closeConnection;

        @Override
        public int getConnectionRetry() {
            return connectionRetry;
        }

        @Override
        public int getCloseConnection() {
            return closeConnection;
        }
    }

    public static class SystemData implements SystemConfig {
        @JsonProperty("main_engine_tick")
        private int mainEngineTick;
        @JsonProperty("auto_restart")
        private int autoRestart;

        @Override
        public int getMainEngineTick() {
            return mainEngineTick;
        }

        @Override
        public int getAutoRestart() {
            return autoRestart;
        }
    }

}
