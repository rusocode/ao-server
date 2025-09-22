package com.ao.data.dao.map;

import com.ao.data.dao.WorldMapDAO;
import com.ao.model.map.Position;
import com.ao.model.map.Tile;
import com.ao.model.map.Trigger;
import com.ao.model.map.WorldMap;
import com.ao.utils.RangeParser;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class WorldMapDAOImpl implements WorldMapDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(WorldMapDAOImpl.class);

    private static final String MAP_FILE_NAME_FORMAT = "Mapa%d.map";
    private static final String INF_FILE_NAME_FORMAT = "Mapa%d.inf";

    private static final byte BITFLAG_BLOCKED = 1;
    private static final byte BITFLAG_LAYER2 = 2;
    private static final byte BITFLAG_LAYER3 = 4;
    private static final byte BITFLAG_LAYER4 = 8;
    private static final byte BITFLAG_TRIGGER = 16;

    private static final byte BITFLAG_TILEEXIT = 1;
    private static final byte BITFLAG_NPC = 2;
    private static final byte BITFLAG_OBJECT = 4;

    private final int mapsAmount;
    private final String mapsPath;
    private final Set<Short> waterGrhs = new HashSet<>();
    private final Set<Short> lavaGrhs = new HashSet<>();

    @Inject
    public WorldMapDAOImpl(@Named("mapsPath") String mapsPath, @Named("mapsAmount") int mapsAmount, @Named("mapsConfigFile") String mapsConfigFile) {
        this.mapsPath = mapsPath;
        this.mapsAmount = mapsAmount;
        loadMapsConfig(mapsConfigFile);
    }

    @Override
    public WorldMap[] load() {
        WorldMap[] maps = new WorldMap[mapsAmount];
        // Maps enumeration starts at 1
        for (int i = 1; i <= mapsAmount; i++)
            maps[i - 1] = loadMap(i);
        return maps;
    }

    /**
     * Loads the map with the given id in the given map list.
     *
     * @param id map's id
     */
    private WorldMap loadMap(int id) {
        byte[] bufInf, bufMap;

        // TODO Load .dat file too
        String infFileName = mapsPath + String.format(INF_FILE_NAME_FORMAT, id);
        String mapFileName = mapsPath + String.format(MAP_FILE_NAME_FORMAT, id);

        try {
            // Load .inf file from the classpath
            try (InputStream infStream = getClass().getClassLoader().getResourceAsStream(infFileName)) {
                if (infStream == null)
                    throw new FileNotFoundException("The file " + infFileName + " was not found in the classpath");
                bufInf = infStream.readAllBytes();
            }

            // Load .map file from the classpath
            try (InputStream mapStream = getClass().getClassLoader().getResourceAsStream(mapFileName)) {
                if (mapStream == null)
                    throw new FileNotFoundException("The file " + mapFileName + " was not found in the classpath");
                bufMap = mapStream.readAllBytes();
            }

        } catch (IOException e) {
            LOGGER.error("Map " + id + " loading failed!", e);
            throw new RuntimeException(e);
        }

        ByteBuffer infBuffer = ByteBuffer.wrap(bufInf);
        ByteBuffer mapBuffer = ByteBuffer.wrap(bufMap);

        // The map files are written with Little-Endian, and the default byte order in Java is Big Endian
        infBuffer.order(ByteOrder.LITTLE_ENDIAN);
        mapBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // Load the map header
        short mapVersion = mapBuffer.getShort();

        byte[] description = new byte[255];
        mapBuffer.get(description);

        // Never actually implemented
        @SuppressWarnings("unused") int crc = mapBuffer.getInt();
        @SuppressWarnings("unused") int magicWord = mapBuffer.getInt();

        // Unused header value
        mapBuffer.getLong();

        // Unused header values
        infBuffer.getLong();
        infBuffer.getShort();

        Tile[] tiles = new Tile[WorldMap.MAP_HEIGHT * WorldMap.MAP_WIDTH];

        byte flag;
        boolean blocked;
        boolean isWater;
        boolean isLava;

        short triggerIndex;
        Trigger trigger;
        Position tileExit;
        short floor;

        // Tiles enumeration starts at 1
        for (int y = WorldMap.MIN_Y; y <= WorldMap.MAX_Y; y++) {
            for (int x = WorldMap.MIN_X; x <= WorldMap.MAX_X; x++) {
                blocked = isWater = isLava = false;
                trigger = Trigger.NONE;
                tileExit = null;

                flag = mapBuffer.get();

                // If the first bitflag is set, the tile is blocked.
                if ((flag & BITFLAG_BLOCKED) == BITFLAG_BLOCKED) blocked = true;


                // Every tile must have the first layer
                floor = mapBuffer.getShort();

                // Are we on water?
                if (waterGrhs.contains(floor)) isWater = true;
                else if (lavaGrhs.contains(floor)) isLava = true;// Are we on lava?

                // In this layer goes stuff that should appear over the floor
                if ((flag & BITFLAG_LAYER2) == BITFLAG_LAYER2)
                    mapBuffer.getShort(); // Remove the short from the buffer so we can fetch the next value.


                // In this layer goes stuff over the chars but is not a roof.
                if ((flag & BITFLAG_LAYER3) == BITFLAG_LAYER3)
                    mapBuffer.getShort(); // Remove the short from the buffer so we can fetch the next value.

                // This layer determines the roof, if any
                if ((flag & BITFLAG_LAYER4) == BITFLAG_LAYER4) {
                    /*
                     * Don't really care whether the tile has a roof or not is determined by the trigger.
                     * Remove the short from the buffer so we can fetch the next value.
                     */
                    mapBuffer.getShort();
                }

                if ((flag & BITFLAG_TRIGGER) == BITFLAG_TRIGGER) {

                    triggerIndex = mapBuffer.getShort();

                    try {
                        trigger = Trigger.get(triggerIndex);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        LOGGER.warn(String.format("The position (%d, %d, %d) has an invalid trigger: %d.", id, x, y, triggerIndex));
                        trigger = Trigger.NONE;
                    }
                }

                flag = infBuffer.get();

                // The tile takes you to another position
                if ((flag & BITFLAG_TILEEXIT) == BITFLAG_TILEEXIT) {
                    short toMap = infBuffer.getShort();

                    byte toX = (byte) infBuffer.getShort();
                    byte toY = (byte) infBuffer.getShort();

                    if (toMap < 1 || toMap > mapsAmount)
                        LOGGER.error(String.format("The position (%d, %d, %d) has an invalid tile exit to a non-existant map (%d). Omitting.", id, x, y, toMap));
                    else tileExit = new Position(toX, toY, toMap);

                }

                if ((flag & BITFLAG_NPC) == BITFLAG_NPC) {
                    // The Npc number
                    infBuffer.getShort();
                    // TODO instantiate the NpcCharacter object.
                }

                if ((flag & BITFLAG_OBJECT) == BITFLAG_OBJECT) {
                    // The object index
                    infBuffer.getShort();

                    // The object's amount
                    infBuffer.getShort();

                    // TODO instantiate the WorldObject object
                }

                // TODO Replace the nulls with the NpcCharacter and WorldObject objects
                tiles[WorldMap.getTileKey(x, y)] = new Tile(blocked, isWater, isLava, trigger, tileExit, null, null);
            }
        }

        // Fill the map with the loaded data
        return new WorldMap(null, id, mapVersion, tiles);
    }

    /**
     * Loads the map configuration from the given file.
     *
     * @param configFile file path to the config file
     */
    private void loadMapsConfig(String configFile) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
            if (inputStream == null)
                throw new FileNotFoundException("The file " + configFile + " was not found in the classpath");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error("Error loading maps properties file({})", configFile);
            throw new RuntimeException(e);
        }
        waterGrhs.addAll(RangeParser.parseShorts(properties.getProperty("maps.water")));
        lavaGrhs.addAll(RangeParser.parseShorts(properties.getProperty("maps.lava")));
    }

}
