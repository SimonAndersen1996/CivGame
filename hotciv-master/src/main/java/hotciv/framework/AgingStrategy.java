package hotciv.framework;

/**
 * AgingStrat represents the flow of time for each passing round.
 * Depending on the current age, the flow of time may vary.
 */
public interface AgingStrategy {

    /**
     * Move forward in time.
     * @param currentAge is the age to move from.
     * @return the new age we reach to.
     */
    int passTime(int currentAge);

}
