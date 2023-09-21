package hotciv.framework;

public interface AttackingStrategy {

    /**
     * Perform an attack.
     * @param from is the attacker.
     * @param to is the attacked/defender.
     * @param game where attack takes place in.
     * @return boolean expression if the attack was successful or not.
     */
    boolean isSuccessfulAttack(Position from, Position to, Game game);

    /**
     * Compute stat bonus based on adjacent allies.
     * @param unitPosition is the unit receiving the bonuses.
     * @param game
     * @return the bonus.
     */
    int getAllies(Position unitPosition, Game game);

    /**
     * Compute stat bonus based on current tile
     * @param p is the position of the unit receiving bonuses and the current tile.
     * @param game
     * @return the bonus.
     */
    int getTerrainBonus(Position p, Game game);

    /**
     * Computation of total stat value of a unit.
     * @param unitPosition is the position of the unit.
     * @param isAttacking is true if said unit is the attacker, otherwise false.
     * @param game
     * @return the total stat value.
     */
    int computeStatValue(Position unitPosition, boolean isAttacking, Game game);


}
