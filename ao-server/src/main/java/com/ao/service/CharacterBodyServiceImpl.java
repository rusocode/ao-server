

package com.ao.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ao.model.character.Gender;
import com.ao.model.character.Race;

public class CharacterBodyServiceImpl implements CharacterBodyService {

	private int darkElfMaleBody;
	private int darkElfFemaleBody;
	private int dwarfMaleBody;
	private int dwarfFemaleBody;
	private int elfMaleBody;
	private int elfFemaleBody;
	private int gnomeMaleBody;
	private int gnomeFemaleBody;
	private int humanMaleBody;
	private int humanFemaleBody;


	private List<Integer> headsDarkelfMale;
	private List<Integer> headsDarkelfFemale;
	private List<Integer> headsDwarfMale;
	private List<Integer> headsDwarfFemale;
	private List<Integer> headsElfMale;
	private List<Integer> headsElfFemale;
	private List<Integer> headsGnomeMale;
	private List<Integer> headsGnomeFemale;
	private List<Integer> headsHumanMale;
	private List<Integer> headsHumanFemale;

	@Inject
	public CharacterBodyServiceImpl(@Named("headsDarkelfMale") List<Integer> headsDarkelfMale,
			@Named("headsDarkelfFemale")  List<Integer> headsDarkelfFemale,
			@Named("headsDwarfMale") List<Integer> headsDwarfMale,
			@Named("headsDwarfFemale")  List<Integer> headsDwarfFemale,
			@Named("headsElfMale") List<Integer> headsElfMale,
			@Named("headsElfFemale") List<Integer> headsElfFemale,
			@Named("headsGnomeMale")  List<Integer> headsGnomeMale,
			@Named("headsGnomeFemale") List<Integer> headsGnomeFemale,
			@Named("headsHumanMale") List<Integer> headsHumanMale,
			@Named("headsHumanFemale")  List<Integer> headsHumanFemale,
			@Named("darkElfMaleBody")  int darkElfMaleBody,
			@Named("darkElfFemaleBody")  int darkElfFemaleBody,
			@Named("dwarfMaleBody")  int dwarfMaleBody,
			@Named("dwarfFemaleBody")  int dwarfFemaleBody,
			@Named("elfMaleBody")  int elfMaleBody,
			@Named("elfFemaleBody")  int elfFemaleBody,
			@Named("gnomeMaleBody")  int gnomeMaleBody,
			@Named("gnomeFemaleBody")  int gnomeFemaleBody,
			@Named("humanMaleBody")  int humanMaleBody,
			@Named("humanFemaleBody")  int humanFemaleBody) {
		super();
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
		this.darkElfFemaleBody = darkElfFemaleBody ;
		this.dwarfMaleBody = dwarfMaleBody;
		this.dwarfFemaleBody = dwarfFemaleBody;
		this.elfMaleBody = elfMaleBody;
		this.elfFemaleBody = elfFemaleBody;
		this.gnomeMaleBody = gnomeMaleBody;
		this.gnomeFemaleBody = gnomeFemaleBody;
		this.humanMaleBody = humanMaleBody;
		this.humanFemaleBody = humanFemaleBody;

	}

	/* (non-Javadoc)
	 * @see com.ao.service.CharacterBodyService#isValidHead(int, com.ao.model.character.Race, com.ao.model.character.Gender)
	 */
	@Override
	public boolean isValidHead(int head, Race race, Gender gender){

		switch (race) {
			case DARK_ELF:
				if (gender == Gender.MALE) {
					return headsDarkelfMale.contains(head);
				} else {
					return headsDarkelfFemale.contains(head);
				}

			case DWARF:
				if (gender == Gender.MALE) {
					return headsDwarfMale.contains(head);
				} else {
					return headsDwarfFemale.contains(head);
				}

			case ELF:
				if (gender == Gender.MALE) {
					return headsElfMale.contains(head);
				} else {
					return headsElfFemale.contains(head);
				}

			case GNOME:
				if (gender == Gender.MALE) {
					return headsGnomeMale.contains(head);
				} else {
					return headsGnomeFemale.contains(head);
				}

			case HUMAN:
				if (gender == Gender.MALE){
					return headsHumanMale.contains(head);
				} else {
					return headsHumanFemale.contains(head);
				}

			default:
				break;
		}

		return false;
	}

	@Override
	public int getBody(Race race, Gender gender) {

		int body = 0;

		switch (race) {
			case DARK_ELF:
				if (gender == Gender.MALE) {
					body = darkElfMaleBody;
				} else {
					body = darkElfFemaleBody;
				}
				break;

			case DWARF:
				if (gender == Gender.MALE) {
					body = dwarfMaleBody;
				} else {
					body = dwarfFemaleBody;
				}
				break;

			case ELF:
				if (gender == Gender.MALE) {
					body = elfMaleBody;
				} else {
					body = elfFemaleBody;
				}
				break;

			case GNOME:
				if (gender == Gender.MALE) {
					body = gnomeMaleBody;
				} else {
					body = gnomeFemaleBody;
				}
				break;

			case HUMAN:
				if (gender == Gender.MALE){
					body = humanMaleBody;
				} else {
					body = humanFemaleBody;
				}
				break;

			default:
				break;
		}

		return body;
	}
}
