<!-- Generated: 2026-05-08 | Files scanned: 258 classes / 47 packages | Token estimate: ~700 -->

# Data

## Storage: File-Based (No Database)

All persistence is via legacy flat files under `data/`. No migrations — data files are loaded read-only at startup.

## Data Files

| File | Format | DAO | Model |
|------|--------|-----|-------|
| `data/maps/*.map` (290 files) | Binary | MapDAOImpl | Map, Tile, Trigger |
| `data/objects.dat` | INI | ObjectDAOIni | WorldObject hierarchy (43 types) |
| `data/npcs.dat` | INI | NpcDAOIni | NpcCharacter |
| `data/cities.dat` | INI | CityDAOIni | City |
| `charfiles/*.chr` | INI | UserDAOIni | Account, UserCharacter |

## Domain Model

### Character Hierarchy
```
Character (interface)
├── UserCharacter
│   └── attrs: Strength, Dexterity, Intelligence, Charisma, Constitution
│   └── Race (HUMAN, ELF, DARK_ELF, GNOME, DWARF)
│   └── Gender, Archetype (17 types), Guild, Skills (enum)
└── NpcCharacter (NpcCharacterImpl)
    └── NpcType, AIType, MovementStrategy, Drop
```

### WorldObject Hierarchy (43 ObjectType values)
```
Object (interface)
└── AbstractObject
    ├── Item → AbstractItem
    │   ├── EquipableItem: Weapon, RangedWeapon, Staff, Armor, Shield, Helmet, Accessory
    │   ├── ConsumableItem: HPPotion, ManaPotion, PoisonPotion, StrengthPotion,
    │   │                   DexterityPotion, DeathPotion, Food, Drink, FilledBottle, EmptyBottle
    │   └── Other: Backpack, Key, Parchment, Ammunition, Ingot, Mineral, Wood, Gold,
    │              MusicalInstrument
    ├── ResourceSource: Mine, Tree (gatherable)
    └── Prop: GrabableProp, Sign, Door, Boat, Teleport, Forum
```

### Spell Model
```
Spell
├── id, name, magicWords, description
├── requiredMana, requiredSkill, requiredStaffPower
├── negative (bool), fx (visual ID), sound
└── effects: Effect[]
    └── HitPointsEffect, PoisonEffect, ParalysisEffect, ImmobilizationEffect,
        InvisibilityEffect, MimetismEffect, RecoverMobilityEffect, DumbEffect
```

### Map Model
```
Map
├── id, width, height
├── tiles: Tile[][]
│   └── Tile: blocked (bool), trigger (Trigger enum), objects, npc
├── City references
└── Area zones
```

### User/Account Model
```
Account (AccountImpl)
├── username, passwordHash
└── Guild reference

UserCharacter (wraps Character)
├── All Character fields
├── Skills map (Skill enum → level int)
├── Inventory (InventoryImpl — slot-based)
├── LoggedUser / ConnectedUser (session state)
└── Reputation (ReputationImpl)
```

## Configuration Files (Not Game Data)

| File | Format | Loaded By |
|------|--------|-----------|
| `data/config/server.ini` | INI | ServerConfigIni |
| `server/src/main/resources/project.properties` | Java Properties | ApplicationProperties |

`project.properties` keys: `config.path.maps`, `config.path.server`, `config.heads.<race>.<gender>` (ranges), `config.bodies.<race>.<gender>` (ranges), `config.inventory.itemperrow`, `config.security.manager`
