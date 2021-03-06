package com.overload.game.content.combat.method.impl.specials;

import com.overload.game.content.combat.CombatSpecial;
import com.overload.game.content.combat.CombatType;
import com.overload.game.content.combat.hit.PendingHit;
import com.overload.game.content.combat.method.CombatMethod;
import com.overload.game.entity.impl.Character;
import com.overload.game.entity.impl.player.Player;
import com.overload.game.model.Animation;
import com.overload.game.model.Graphic;
import com.overload.game.model.Priority;
import com.overload.game.model.Skill;

public class DragonWarhammerCombatMethod implements CombatMethod {

    private static final Animation ANIMATION = new Animation(1378, Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(1292, Priority.HIGH);

    @Override
    public CombatType getCombatType() {
        return CombatType.MELEE;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public PendingHit[] getHits(Character character, Character target) {
        return new PendingHit[]{new PendingHit(character, target, this, true, 0)};
    }

    @Override
    public boolean canAttack(Character character, Character target) {
        return true;
    }

    @Override
    public void prepareAttack(Character character, Character target) {
        CombatSpecial.drain(character, CombatSpecial.DRAGON_WARHAMMER.getDrainAmount());
    }

    @Override
    public int getAttackSpeed(Character character) {
        return character.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Character character) {
        return 1;
    }

    @Override
    public void startAnimation(Character character) {
        character.performAnimation(ANIMATION);
        character.performGraphic(GRAPHIC);
    }

    @Override
    public void finished(Character character, Character target) {

    }

    @Override
    public void handleAfterHitEffects(PendingHit hit) {
        if (hit.isAccurate() && hit.getTarget().isPlayer() && hit.getTarget().isNpc()) {
            int damageDrain = (int) (hit.getTotalDamage() * 0.3);
            if (damageDrain < 0)
                return;
            Player player = hit.getAttacker().getAsPlayer();
            Player target = hit.getTarget().getAsPlayer();
            target.getSkillManager().decreaseCurrentLevel(Skill.DEFENCE, damageDrain, 1);
            player.getPacketSender().sendMessage("You've drained " + target.getUsername() + "'s Defence level by " + damageDrain + ".");
            target.getPacketSender().sendMessage("Your Defence level has been drained.");
        }
    }
}