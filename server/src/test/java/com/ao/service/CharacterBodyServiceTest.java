package com.ao.service;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.service.character.CharacterBodyServiceImpl;
import com.ao.utils.RangeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CharacterBodyServiceTest {

    private static final int INVALID_HEAD = 0;
    private static final int VALID_DARKELF_MALE_HEAD = 201;
    private static final int VALID_DARKELF_FEMALE_HEAD = 270;
    private static final int VALID_DWARF_MALE_HEAD = 301;
    private static final int VALID_DWARF_FEMALE_HEAD = 370;
    private static final int VALID_ELF_MALE_HEAD = 101;
    private static final int VALID_ELF_FEMALE_HEAD = 170;
    private static final int VALID_GNOME_MALE_HEAD = 401;
    private static final int VALID_GNOME_FEMALE_HEAD = 470;
    private static final int VALID_HUMAN_MALE_HEAD = 1;
    private static final int VALID_HUMAN_FEMALE_HEAD = 70;
    private final List<Integer> headsDarkelfMale = RangeParser.parseIntegers("201-221");
    private final List<Integer> headsDarkelfFemale = RangeParser.parseIntegers("270-288");
    private final List<Integer> headsDwarfMale = RangeParser.parseIntegers("301-319");
    private final List<Integer> headsDwarfFemale = RangeParser.parseIntegers("370-384");
    private final List<Integer> headsElfMale = RangeParser.parseIntegers("101-122");
    private final List<Integer> headsElfFemale = RangeParser.parseIntegers("170-188");
    private final List<Integer> headsGnomeMale = RangeParser.parseIntegers("401-416");
    private final List<Integer> headsGnomeFemale = RangeParser.parseIntegers("470-484");
    private final List<Integer> headsHumanMale = RangeParser.parseIntegers("1-40");
    private final List<Integer> headsHumanFemale = RangeParser.parseIntegers("70-89");
    private final int darkelfMaleBody = 3;
    private final int darkelfFemaleBody = 4;
    private final int dwarfMaleBody = 300;
    private final int dwarfFemaleBody = 301;
    private final int elfMaleBody = 2;
    private final int elfFemaleBody = 5;
    private final int gnomeMaleBody = 302;
    private final int gnomeFemaleBody = 303;
    private final int humanMaleBody = 1;
    private final int humanFemaleBody = 6;
    protected CharacterBodyService characterBodyService;

    @BeforeEach
    public void setUp() throws Exception {
        characterBodyService = new CharacterBodyServiceImpl(headsDarkelfMale, headsDarkelfFemale,
                headsDwarfMale, headsDwarfFemale, headsElfMale, headsElfFemale, headsGnomeMale,
                headsGnomeFemale, headsHumanMale, headsHumanFemale, darkelfMaleBody, darkelfFemaleBody,
                dwarfMaleBody, dwarfFemaleBody, elfMaleBody, elfFemaleBody, gnomeMaleBody,
                gnomeFemaleBody, humanMaleBody, humanFemaleBody);
    }

    @Test
    public void testValidHead() {
        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.DARK_ELF, Gender.MALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_DARKELF_MALE_HEAD, Race.DARK_ELF, Gender.MALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.DARK_ELF, Gender.FEMALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_DARKELF_FEMALE_HEAD, Race.DARK_ELF, Gender.FEMALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.DWARF, Gender.MALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_DWARF_MALE_HEAD, Race.DWARF, Gender.MALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.DWARF, Gender.FEMALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_DWARF_FEMALE_HEAD, Race.DWARF, Gender.FEMALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.ELF, Gender.MALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_ELF_MALE_HEAD, Race.ELF, Gender.MALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.ELF, Gender.FEMALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_ELF_FEMALE_HEAD, Race.ELF, Gender.FEMALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.GNOME, Gender.MALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_GNOME_MALE_HEAD, Race.GNOME, Gender.MALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.GNOME, Gender.FEMALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_GNOME_FEMALE_HEAD, Race.GNOME, Gender.FEMALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.HUMAN, Gender.MALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_HUMAN_MALE_HEAD, Race.HUMAN, Gender.MALE)).isTrue();

        assertThat(characterBodyService.isValidHead(INVALID_HEAD, Race.HUMAN, Gender.FEMALE)).isFalse();
        assertThat(characterBodyService.isValidHead(VALID_HUMAN_FEMALE_HEAD, Race.HUMAN, Gender.FEMALE)).isTrue();
    }

    @Test
    public void GetBody() {
        assertThat(characterBodyService.getBody(Race.DARK_ELF, Gender.MALE)).isEqualTo(darkelfMaleBody);
        assertThat(characterBodyService.getBody(Race.DARK_ELF, Gender.FEMALE)).isEqualTo(darkelfFemaleBody);
        assertThat(characterBodyService.getBody(Race.DWARF, Gender.MALE)).isEqualTo(dwarfMaleBody);
        assertThat(characterBodyService.getBody(Race.DWARF, Gender.FEMALE)).isEqualTo(dwarfFemaleBody);
        assertThat(characterBodyService.getBody(Race.ELF, Gender.MALE)).isEqualTo(elfMaleBody);
        assertThat(characterBodyService.getBody(Race.ELF, Gender.FEMALE)).isEqualTo(elfFemaleBody);
        assertThat(characterBodyService.getBody(Race.GNOME, Gender.MALE)).isEqualTo(gnomeMaleBody);
        assertThat(characterBodyService.getBody(Race.GNOME, Gender.FEMALE)).isEqualTo(gnomeFemaleBody);
        assertThat(characterBodyService.getBody(Race.HUMAN, Gender.MALE)).isEqualTo(humanMaleBody);
        assertThat(characterBodyService.getBody(Race.HUMAN, Gender.FEMALE)).isEqualTo(humanFemaleBody);
    }

}
