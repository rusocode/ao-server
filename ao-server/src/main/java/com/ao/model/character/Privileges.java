package com.ao.model.character;

// FIXME : This should probably be just an enum with smart methods...
public class Privileges {
	private final int USER = 0x01;
	private final int COUNSELOR = 0x02;
	private final int DEMIGOD = 0x04;
	private final int GOD = 0x08;
	private final int ADMIN = 0x10;
	private final int ROLE_MASTER = 0x20;
	private final int CHAOS_COUNSELOR = 0x40;
	private final int ROYAL_COUNSELOR = 0x80;

	private int privilegesFlags;

	public Privileges(int privilegesFlags) {
		this.privilegesFlags = privilegesFlags;
	}

	public boolean isUser() {
		return ((privilegesFlags & USER) != 0);
	}

	public boolean isCouncelor() {
		return ((privilegesFlags & COUNSELOR) != 0);
	}

	public boolean isDemigod() {
		return ((privilegesFlags & DEMIGOD) != 0);
	}

	public boolean isGod() {
		return ((privilegesFlags & GOD) != 0);
	}

	public boolean isAdmin() {
		return ((privilegesFlags & ADMIN) != 0);
	}

	public boolean isRoleMaster() {
		return ((privilegesFlags & ROLE_MASTER) != 0);
	}

	public boolean isChaosCounserlor() {
		return ((privilegesFlags & CHAOS_COUNSELOR) != 0);
	}

	public boolean isRoyalCounselor() {
		return ((privilegesFlags & ROYAL_COUNSELOR) != 0);
	}

	public boolean isGameMaster() {
		return ((privilegesFlags & (ADMIN | GOD | DEMIGOD | COUNSELOR)) != 0);
	}

	public int getPrivilegesFlags() {
		return privilegesFlags;
	}

}
