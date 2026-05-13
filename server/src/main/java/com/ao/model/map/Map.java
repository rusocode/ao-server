package com.ao.model.map;

import com.ao.model.character.Character;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A game's map.
 */
public class Map {

    public static final int MAP_WIDTH = 100;
    public static final int MAP_HEIGHT = 100;
    public static final int VISIBLE_AREA_WIDTH = 8;
    public static final int VISIBLE_AREA_HEIGHT = 6;
    public static final int MAX_DISTANCE = 12;

    public static final int MAX_X = 99;
    public static final int MAX_Y = 99;
    public static final int MIN_X = 0;
    public static final int MIN_Y = 0;

    private final int id;
    private final short version;

    // .dat properties
    private final String name;
    private int musicNum;
    private boolean noMagic;
    private boolean noEncryptMP;
    private String terrain;
    private String zone;
    private boolean restrict;
    private int backup;
    private boolean pk;

    // We don't use jagged arrays, they are inefficient in Java!
    private final Tile[] tiles;

    /**
     * Creates a new Map.
     *
     * @param name          name of the map
     * @param id            unique id of the map
     * @param version       map's version
     * @param tiles         array of tiles composing the map
     * @param musicNum      music number
     * @param noMagic       if magic is disabled
     * @param noEncryptMP   if MP encryption is disabled
     * @param terrain       terrain type
     * @param zone          zone type
     * @param restrict      if map is restricted
     * @param backup        backup value
     * @param pk            if PK is enabled
     */
    public Map(String name, int id, short version, Tile[] tiles, int musicNum, boolean noMagic, boolean noEncryptMP,
               String terrain, String zone, boolean restrict, int backup, boolean pk) {
        super();
        this.name = name;
        this.id = id;
        this.version = version;
        this.tiles = tiles;
        this.musicNum = musicNum;
        this.noMagic = noMagic;
        this.noEncryptMP = noEncryptMP;
        this.terrain = terrain;
        this.zone = zone;
        this.restrict = restrict;
        this.backup = backup;
        this.pk = pk;
    }

    /**
     * Retrieves the tile index at the given coordinates.
     *
     * @param x coordinate along the x vertex (zero is at the left)
     * @param y coordinate along the y vertex (zero is at the top)
     * @return the tile index at the given coordinates
     */
    public static int getTileKey(int x, int y) {
        return y * MAP_WIDTH + x;
    }

    public short getVersion() {
        return version;
    }

    public Tile getTile(int x, int y) {
        return tiles[Map.getTileKey(x, y)];
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public int getMusicNum() {
        return musicNum;
    }

    public void setMusicNum(int musicNum) {
        this.musicNum = musicNum;
    }

    public boolean isNoMagic() {
        return noMagic;
    }

    public void setNoMagic(boolean noMagic) {
        this.noMagic = noMagic;
    }

    public boolean isNoEncryptMP() {
        return noEncryptMP;
    }

    public void setNoEncryptMP(boolean noEncryptMP) {
        this.noEncryptMP = noEncryptMP;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean isRestrict() {
        return restrict;
    }

    public void setRestrict(boolean restrict) {
        this.restrict = restrict;
    }

    public int getBackup() {
        return backup;
    }

    public void setBackup(int backup) {
        this.backup = backup;
    }

    public List<Character> getCharactersNearby(int x, int y) {
        List<Character> charList = new LinkedList<>();
        Character character;

        int toX = Math.min(x + Map.VISIBLE_AREA_WIDTH * 2, MAX_X);
        int toY = Math.min(y + Map.VISIBLE_AREA_HEIGHT * 2, MAX_Y);
        int fromY = Math.max(y - Map.VISIBLE_AREA_HEIGHT, MIN_Y);
        int fromX = Math.max(x - Map.VISIBLE_AREA_WIDTH, MIN_X);

        for (int xx = fromX; xx < toX; xx++) {
            for (int yy = fromY; yy < toY; yy++) {
                character = getTile(xx, yy).getCharacter();
                if (character != null && (xx != x && yy != y)) charList.add(character);
            }
        }

        return charList;
    }

    public void putCharacterAtPos(Character character, byte x, byte y) {
        synchronized (tiles) {
            Tile tile = getTile(x, y);
            if (tile.getCharacter() != null)
                tile = getEmptyTileAround(x, y, character.canWalkInWater(), !character.canWalkInWater());
            tile.setCharacter(character);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(tiles);
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Map other = (Map) obj;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (!Arrays.equals(tiles, other.tiles)) return false;
        if (version != other.version) return false;
        return true;
    }

    public Tile getNearestAvailableTile(byte x, byte y, byte maxDistance, boolean includeGroundTiles, boolean includeWaterTiles, boolean includeLavaTiles, boolean includeExitTiles) {

        if (isTileAvailable(x, y, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
            return getTile(x, y);

        for (int currentDistance = 1; currentDistance <= maxDistance; currentDistance += 1) {
            byte currentX;
            byte currentY;

            for (byte dx = -1; dx <= currentDistance - 1; dx += 1) {
                byte dy = (byte) (currentDistance - Math.abs(dx));
                if ((dx == 0) || (dy == 0)) continue;
                currentX = (byte) (x + dx);
                currentY = (byte) (y + dy);
                if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                    return getTile(currentX, currentY);
            }

            for (byte dx = (byte) (currentDistance - 1); dx >= -(currentDistance - 1); dx -= 1) {
                byte dy = (byte) (currentDistance - Math.abs(dx));
                if ((dx == 0) || (dy == 0)) continue;
                currentX = (byte) (x + dx);
                currentY = (byte) (y - dy);
                if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                    return getTile(currentX, currentY);
            }

            for (byte dx = (byte) -(currentDistance - 1); dx < -1; dx += 1) {
                byte dy = (byte) (currentDistance - Math.abs(dx));
                if ((dx == 0) || (dy == 0)) continue;
                currentX = (byte) (x + dx);
                currentY = (byte) (y + dy);
                if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                    return getTile(currentX, currentY);
            }

            // North
            currentX = x;
            currentY = (byte) (y - currentDistance);
            if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                return getTile(currentX, currentY);

            // East
            currentX = (byte) (x + currentDistance);
            currentY = y;
            if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                return getTile(currentX, currentY);

            // South
            currentX = x;
            currentY = (byte) (y + currentDistance);
            if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                return getTile(currentX, currentY);

            // West
            currentX = (byte) (x - currentDistance);
            currentY = y;
            if (isTileAvailable(currentX, currentY, includeGroundTiles, includeWaterTiles, includeLavaTiles, includeExitTiles))
                return getTile(currentX, currentY);
        }

        return null;
    }

    public boolean isTileAvailable(byte x, byte y, boolean canBeGround, boolean canBeWater, boolean canBeLava, boolean canBeExitTile) {
        if ((x < MIN_X) || (x > MAX_X)) return false;
        if ((y < MIN_Y) || (y > MAX_Y)) return false;

        Tile tile = getTile(x, y);

        if (tile.isBlocked() || (tile.getCharacter() != null)) return false;
        if ((!canBeWater) && tile.isWater()) return false;
        if ((!canBeLava) && tile.isLava()) return false;
        if ((!canBeGround) && (!tile.isWater()) && (!tile.isLava())) return false;
        if ((!canBeExitTile) && (tile.getTileExit() != null)) return false;

        return true;
    }

    public void moveCharacterTo(Character character, byte x, byte y) {
        Position position = character.getPosition();
        getTile(position.getX(), position.getY()).setCharacter(null);
        getTile(x, y).setCharacter(character);
    }

    private Tile getEmptyTileAround(byte lookAroundX, byte lookAroundY, boolean canBeWater, boolean canBeLand) {
        byte distance = 1;
        Tile t;
        while (distance < MAX_DISTANCE) {
            for (int x = Math.max(lookAroundX - distance, 1); x < lookAroundX + distance && x <= MAP_HEIGHT; x++) {
                for (int y = Math.max(lookAroundY - distance, 1); y < lookAroundY + distance && y <= MAP_WIDTH; y++) {
                    if (Math.abs(x - lookAroundX) != distance && Math.abs(y - lookAroundY) != distance) continue;
                    t = getTile(x, y);
                    if (t.getCharacter() == null && !t.isBlocked() && t.getTileExit() == null && ((t.isWater() && canBeWater) || (!t.isWater() && canBeLand)))
                        return t;
                }
            }
            distance++;
        }
        return null;
    }

}
