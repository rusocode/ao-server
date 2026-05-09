package com.ao.config;

public interface IntervalsConfig {
    RegenerationConfig getRegeneration();

    SurvivalConfig getSurvival();

    StatesConfig getStates();

    CombatConfig getCombat();

    MovementConfig getMovement();

    NpcConfig getNpc();

    WorldConfig getWorld();

    NetworkConfig getNetwork();

    SystemConfig getSystem();

    interface RegenerationConfig {
        int getHp();

        int getHpResting();

        int getStamina();

        int getStaminaResting();

        int getManaRecoveryPercent();
    }

    interface SurvivalConfig {
        int getThirst();

        int getHunger();
    }

    interface StatesConfig {
        int getPoison();

        int getParalyzed();

        int getInvisible();

        int getHidden();

        int getCold();
    }

    interface CombatConfig {
        int getUserAttack();

        int getNpcAttack();

        int getSpellCast();

        int getMagicHitDelay();

        int getHitMagicDelay();

        int getBowAttack();

        int getUseItem();

        int getWork();
    }

    interface MovementConfig {
        int getUser();
    }

    interface NpcConfig {
        int getAiTick();
    }

    interface WorldConfig {
        int getSaveInterval();

        int getSummonDuration();

        int getRainStaminaLoss();

        int getFxWavDelay();
    }

    interface NetworkConfig {
        int getConnectionRetry();

        int getCloseConnection();
    }

    interface SystemConfig {
        int getMainEngineTick();

        int getAutoRestart();
    }
}
