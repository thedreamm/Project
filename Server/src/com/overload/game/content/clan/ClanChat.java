package com.overload.game.content.clan;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.overload.game.World;
import com.overload.game.entity.impl.player.Player;
import com.overload.util.Stopwatch;

/**
 * An instance of a clanchat channel, holding all fields.
 * @author Adam Maxwell (Jowcey)
 */
public class ClanChat {

	public ClanChat(Player owner, String name, int index) {
		this.owner = owner;
		this.name = name;
		this.index = index;
		this.ownerName = owner.getUsername();
	}

	public ClanChat(String ownerName, String name, int index) {
		Optional<Player> o = World.getPlayerByName(ownerName);
		this.owner = o.isPresent() ? o.get() : null;
		this.ownerName = ownerName;
		this.name = name;
		this.index = index;
	}

	private String name;
	private Player owner;
	private String ownerName;
	private final int index;
	private boolean lootShare;
	private boolean coinShare;
	private Stopwatch lastAction = new Stopwatch();

	private ClanChatRank[] rankRequirement = new ClanChatRank[4];
	private CopyOnWriteArrayList<Player> members = new CopyOnWriteArrayList<Player>();
	private CopyOnWriteArrayList<BannedMember> bannedMembers = new CopyOnWriteArrayList<BannedMember>();
	private Map<String, ClanChatRank> rankedNames = new HashMap<String, ClanChatRank>();
	
	public Player getOwner() {
		return owner;
	}

	public ClanChat setOwner(Player owner) {
		this.owner = owner;
		return this;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public ClanChat setName(String name) {
		this.name = name;
		return this;
	}

	public boolean getLootShare() {
		return lootShare;
	}

	public void setLootShare(boolean lootShare) {
		this.lootShare = lootShare;
	}

	public boolean getCoinShare() {
		return coinShare;
	}

	public void setCoinShare(boolean coinShare) {
		this.coinShare = coinShare;
	}

	public Stopwatch getLastAction() {
		return lastAction;
	}

	public ClanChat addMember(Player member) {
		members.add(member);
		return this;
	}

	public ClanChat removeMember(String name) {
		for(int i = 0; i < members.size(); i++) {
			Player member = members.get(i);
			if(member == null)
				continue;
			if(member.getUsername().equals(name)) {
				members.remove(i);
				break;
			}
		}
		return this;
	}

	public ClanChatRank getRank(Player player) {
		return rankedNames.get(player.getUsername());
	}

	public ClanChat giveRank(Player player, ClanChatRank rank) {
		rankedNames.put(player.getUsername(), rank);
		return this;
	}

	public CopyOnWriteArrayList<Player> getMembers() {
		return members;
	}

	public Map<String, ClanChatRank> getRankedNames() {
		return rankedNames;
	}

	public CopyOnWriteArrayList<BannedMember> getBannedNames() {
		return bannedMembers;
	}

	public void addBannedName(String name) {
		bannedMembers.add(new BannedMember(name, 1800)); //30 mins
	}

	public boolean isBanned(String name) {
		for(BannedMember b : bannedMembers) {
			if(b == null) {
				continue;
			}
			if(b.getName().equals(name) && 
					!b.getTimer().finished()) {
				return true;
			}
		}
		return false;
	}

	public ClanChatRank[] getRankRequirement() {
		return rankRequirement;
	}

	public ClanChat setRankRequirements(int index, ClanChatRank rankRequirement) {
		this.rankRequirement[index] = rankRequirement;
		return this;
	}

	public static final int RANK_REQUIRED_TO_ENTER = 0, RANK_REQUIRED_TO_KICK = 1, RANK_REQUIRED_TO_TALK = 2, RANK_REQUIRED_TO_LOOTSHARE = 3;
}