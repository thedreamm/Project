package com.overload.net.packet.impl.commands.developer;

import com.overload.game.content.combat.WeaponInterfaces;
import com.overload.game.content.skill.SkillManager;
import com.overload.game.entity.impl.player.Player;
import com.overload.game.model.Skill;
import com.overload.net.packet.impl.commands.Command;

public class Reset extends Command {

	@Override
	public void execute(Player player, String input) {
		for(Skill skill : Skill.values()) {
			int level = skill == Skill.HITPOINTS ? 10 : 1;
			player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill, SkillManager.getExperienceForLevel(level));
		}
		WeaponInterfaces.assign(player);
	}

}
