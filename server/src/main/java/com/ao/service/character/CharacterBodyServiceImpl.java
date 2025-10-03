package com.ao.service.character;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;
import com.ao.service.CharacterBodyService;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;

public record CharacterBodyServiceImpl(List<Integer> headsDarkelfMale, List<Integer> headsDarkelfFemale,
                                       List<Integer> headsDwarfMale, List<Integer> headsDwarfFemale, List<Integer> headsElfMale,
                                       List<Integer> headsElfFemale, List<Integer> headsGnomeMale, List<Integer> headsGnomeFemale,
                                       List<Integer> headsHumanMale, List<Integer> headsHumanFemale, int darkElfMaleBody,
                                       int darkElfFemaleBody, int dwarfMaleBody, int dwarfFemaleBody, int elfMaleBody,
                                       int elfFemaleBody, int gnomeMaleBody, int gnomeFemaleBody, int humanMaleBody,
                                       int humanFemaleBody) implements CharacterBodyService {

    @Inject
    public CharacterBodyServiceImpl(@Named("headsDarkelfMale") List<Integer> headsDarkelfMale,
                                    @Named("headsDarkelfFemale") List<Integer> headsDarkelfFemale,
                                    @Named("headsDwarfMale") List<Integer> headsDwarfMale,
                                    @Named("headsDwarfFemale") List<Integer> headsDwarfFemale,
                                    @Named("headsElfMale") List<Integer> headsElfMale,
                                    @Named("headsElfFemale") List<Integer> headsElfFemale,
                                    @Named("headsGnomeMale") List<Integer> headsGnomeMale,
                                    @Named("headsGnomeFemale") List<Integer> headsGnomeFemale,
                                    @Named("headsHumanMale") List<Integer> headsHumanMale,
                                    @Named("headsHumanFemale") List<Integer> headsHumanFemale,
                                    @Named("darkElfMaleBody") int darkElfMaleBody,
                                    @Named("darkElfFemaleBody") int darkElfFemaleBody,
                                    @Named("dwarfMaleBody") int dwarfMaleBody,
                                    @Named("dwarfFemaleBody") int dwarfFemaleBody,
                                    @Named("elfMaleBody") int elfMaleBody,
                                    @Named("elfFemaleBody") int elfFemaleBody,
                                    @Named("gnomeMaleBody") int gnomeMaleBody,
                                    @Named("gnomeFemaleBody") int gnomeFemaleBody,
                                    @Named("humanMaleBody") int humanMaleBody,
                                    @Named("humanFemaleBody") int humanFemaleBody) {
        this.headsDarkelfMale = headsDarkelfMale;
        this.headsDarkelfFemale = headsDarkelfFemale;
        this.headsDwarfMale = headsDwarfMale;
        this.headsDwarfFemale = headsDwarfFemale;
        this.headsElfMale = headsElfMale;
        this.headsElfFemale = headsElfFemale;
        this.headsGnomeMale = headsGnomeMale;
        this.headsGnomeFemale = headsGnomeFemale;
        this.headsHumanMale = headsHumanMale;
        this.headsHumanFemale = headsHumanFemale;

        this.darkElfMaleBody = darkElfMaleBody;
        this.darkElfFemaleBody = darkElfFemaleBody;
        this.dwarfMaleBody = dwarfMaleBody;
        this.dwarfFemaleBody = dwarfFemaleBody;
        this.elfMaleBody = elfMaleBody;
        this.elfFemaleBody = elfFemaleBody;
        this.gnomeMaleBody = gnomeMaleBody;
        this.gnomeFemaleBody = gnomeFemaleBody;
        this.humanMaleBody = humanMaleBody;
        this.humanFemaleBody = humanFemaleBody;
    }

    @Override
    public boolean isValidHead(int head, Race race, Gender gender) {
        return switch (race) {
            case DARK_ELF -> gender == Gender.MALE ? headsDarkelfMale.contains(head) : headsDarkelfFemale.contains(head);
            case DWARF -> gender == Gender.MALE ? headsDwarfMale.contains(head) : headsDwarfFemale.contains(head);
            case ELF -> gender == Gender.MALE ? headsElfMale.contains(head) : headsElfFemale.contains(head);
            case GNOME -> gender == Gender.MALE ? headsGnomeMale.contains(head) : headsGnomeFemale.contains(head);
            case HUMAN -> gender == Gender.MALE ? headsHumanMale.contains(head) : headsHumanFemale.contains(head);
        };
    }

    @Override
    public int getBody(Race race, Gender gender) {
        return switch (race) {
            case DARK_ELF -> gender == Gender.MALE ? darkElfMaleBody : darkElfFemaleBody;
            case DWARF -> gender == Gender.MALE ? dwarfMaleBody : dwarfFemaleBody;
            case ELF -> gender == Gender.MALE ? elfMaleBody : elfFemaleBody;
            case GNOME -> gender == Gender.MALE ? gnomeMaleBody : gnomeFemaleBody;
            case HUMAN -> gender == Gender.MALE ? humanMaleBody : humanFemaleBody;
        };
    }

}
