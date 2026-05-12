package com.ao.service.npc;

import com.ao.model.character.NpcCharacter;
import com.ao.model.character.npc.NpcCharacterImpl;
import com.ao.model.character.npc.properties.Npc;
import com.ao.model.map.Position;
import com.ao.service.CharacterIndexManager;
import com.ao.service.NpcService;
import com.google.inject.Inject;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio extendido para gestionar NPCs en el mundo.
 * <p>
 * Este servicio mantiene un mapeo entre CharIndex y NpcCharacter, similar al array CharList(CharIndex) de VB6.
 */

public class NpcManager {

    private final NpcService npcService;
    private final CharacterIndexManager charIndexManager;

    /**
     * Mapeo CharIndex -> NpcCharacter
     * <p>
     * Equivalente al CharList(CharIndex) = NpcIndex de VB6
     */
    private final Map<Short, NpcCharacter> npcsByCharIndex;

    /**
     * Contador de NPCs activos en el mundo
     */
    private int activeNpcsCount;

    @Inject
    public NpcManager(NpcService npcService, CharacterIndexManager charIndexManager) {
        this.npcService = npcService;
        this.charIndexManager = charIndexManager;
        this.npcsByCharIndex = new HashMap<>();
        this.activeNpcsCount = 0;
    }

    /**
     * Crea (spawn) un NPC en una posicion especifica del mapa.
     * <p>
     * Equivalente a OpenNPC + MakeNPCChar de VB6.
     *
     * @param npcId    ID del tipo de NPC (del archivo npcs.dat)
     * @param position Posicion donde crear el NPC
     * @return La instancia del NPC creado, o null si no se pudo crear
     */
    public NpcCharacter spawnNpc(int npcId, Position position) {
        try {
            // 1. Obtener las propiedades del NPC desde npcs.dat
            Npc npc = npcService.getNpc(npcId);
            if (npc == null) {
                Logger.error("Cannot spawn NPC: NPC properties not found for ID {}", npcId);
                return null;
            }

            // 2. Asignar un CharIndex (equivalente a NextOpenCharIndex de VB6)
            short charIndex = (short) charIndexManager.assignCharIndex();

            // 3. Crear la instancia del NPC
            NpcCharacter npcCharacter = new NpcCharacterImpl(npc, position, charIndex);

            // 4. Registrar en el mapeo CharIndex -> NpcCharacter
            // (equivalente a CharList(CharIndex) = NpcIndex)
            npcsByCharIndex.put(charIndex, npcCharacter);

            // 5. Incrementar contador
            activeNpcsCount++;

            Logger.debug("Spawned NPC '{}' (ID: {}) at position ({}, {}, {}) with CharIndex {}",
                npc.getName(), npcId, position.getX(), position.getY(),
                position.getMap(), charIndex);

            return npcCharacter;

        } catch (Exception e) {
            Logger.error("Error spawning NPC with ID {} at position {}", npcId, position, e);
            return null;
        }
    }

    /**
     * Remueve un NPC del mundo.
     * <p>
     * Equivalente a EraseNPCChar de VB6.
     *
     * @param npc El NPC a remover
     */
    public void removeNpc(NpcCharacter npc) {
        if (npc == null) return;

        short charIndex = npc.getCharIndex();

        // Remover del mapeo
        npcsByCharIndex.remove(charIndex);

        // Liberar el CharIndex para que pueda ser reutilizado
        charIndexManager.freeCharIndex(charIndex);

        // Decrementar contador
        activeNpcsCount--;

        Logger.debug("Removed NPC '{}' with CharIndex {}", npc.getName(), charIndex);
    }

    /**
     * Obtiene un NPC por su CharIndex.
     * <p>
     * Equivalente a acceder CharList(CharIndex) en VB6.
     *
     * @param charIndex El CharIndex del NPC
     * @return El NPC, o null si no existe
     */
    public NpcCharacter getNpcByCharIndex(short charIndex) {
        return npcsByCharIndex.get(charIndex);
    }

    /**
     * Verifica si existe un NPC con el CharIndex dado.
     *
     * @param charIndex El CharIndex a verificar
     * @return true si existe un NPC con ese CharIndex
     */
    public boolean hasNpcWithCharIndex(short charIndex) {
        return npcsByCharIndex.containsKey(charIndex);
    }

    /**
     * Obtiene la cantidad de NPCs activos en el mundo.
     *
     * @return Cantidad de NPCs activos
     */
    public int getActiveNpcsCount() {
        return activeNpcsCount;
    }

    /**
     * Limpia todos los NPCs del mundo. Util para reinicios o limpieza de mapas.
     */
    public void clearAllNpcs() {
        for (NpcCharacter npc : npcsByCharIndex.values()) {
            charIndexManager.freeCharIndex(npc.getCharIndex());
        }
        npcsByCharIndex.clear();
        activeNpcsCount = 0;
        Logger.info("Cleared all NPCs from the world");
    }

}
