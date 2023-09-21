package hotciv.Common;

public class OperationNames {
    public static final char SEPARATOR = '_';

    // Type prefixes
    public static final String UNIT_PREFIX = "unit";
    public static final String TILE_PREFIX = "tile";
    public static final String CITY_PREFIX = "city";
    public static final String GAME_PREFIX = "game";

    //Game methods
    public static final String GAME_GET_WINNER = GAME_PREFIX + SEPARATOR + "get-winner-method";
    public static final String GAME_GET_PLAYER_IN_TURN = GAME_PREFIX + SEPARATOR + "get-player-in-turn-method";
    public static final String GAME_MOVE = GAME_PREFIX + SEPARATOR + "move-method";
    public static final String GAME_ACTION = GAME_PREFIX + SEPARATOR + "perform-action-method";
    public static final String GAME_GET_AGE = GAME_PREFIX + SEPARATOR + "get-age-method";
    public static final String GAME_TILE_AT = GAME_PREFIX + SEPARATOR + "get-tileat-method";
    public static final String GAME_CITY_AT = GAME_PREFIX + SEPARATOR + "get-cityat-method";
    public static final String GAME_CURRENT_ROUND = GAME_PREFIX + SEPARATOR + "get-currentround-method";
    public static final String GAME_UNIT_AT = GAME_PREFIX + SEPARATOR + "get-unitat-method";
    public static final String GAME_CITY_POPULATION = GAME_PREFIX + SEPARATOR + "get-citypop-method";
    public static final String GAME_ADD_UNIT = GAME_PREFIX + SEPARATOR + "addunit-method";
    public static final String GAME_ADD_CITY = GAME_PREFIX + SEPARATOR + "addcity-method";
    public static final String GAME_ADD_OBSERVER = GAME_PREFIX + SEPARATOR + "addobserver-method";
    public static final String GAME_SET_TILE_FOCUS = GAME_PREFIX + SEPARATOR + "settilefocus-method";
    public static final String GAME_END_OF_TURN = GAME_PREFIX + SEPARATOR + "endofturn-method";
    public static final String GAME_END_OF_ROUND = GAME_PREFIX + SEPARATOR + "endofround-method";
    public static final String GAME_CHANGE_WORKFORCE_FOCUS = GAME_PREFIX + SEPARATOR + "changeworkforcefocus-method";
    public static final String GAME_CHANGE_PRODUCTION = GAME_PREFIX + SEPARATOR + "changeproduction-method";
    public static final String GAME_GET_FREE_POSITION = GAME_PREFIX + SEPARATOR + "get-free-position-method";
    public static final String GAME_END = GAME_PREFIX + SEPARATOR + "end-method";
    public static final String GAME_REMOVE_UNIT = GAME_PREFIX + SEPARATOR + "remove-unit-method";

    //City methods
    public static final String CITY_GET_OWNER = CITY_PREFIX + SEPARATOR + "get-owner-method";
    public static final String CITY_GET_SIZE = CITY_PREFIX + SEPARATOR + "get-size-method";
    public static final String CITY_GET_TREASURY = CITY_PREFIX + SEPARATOR + "get-treasury-method";
    public static final String CITY_GET_PRODUCTION_TYPE = CITY_PREFIX + SEPARATOR + "get-production-type-method";
    public static final String CITY_GET_WORKFORCE_TYPE = CITY_PREFIX + SEPARATOR + "get-workforce-type-method";
    public static final String CITY_ADD_TREASURY = CITY_PREFIX + SEPARATOR + "add-treasury-method";
    public static final String CITY_SET_WORKFORCE_FOCUS = CITY_PREFIX + SEPARATOR + "set-workforce-focus-method";
    public static final String CITY_SET_PRODUCTION_FOCUS = CITY_PREFIX + SEPARATOR + "set-production-focus-method";
    public static final String CITY_SET_OWNER = CITY_PREFIX + SEPARATOR + "set-owner-method";

    //Unit methods
    public static final String UNIT_GET_TYPESTRING = UNIT_PREFIX + SEPARATOR + "get-typestring-method";
    public static final String UNIT_GET_OWNER = UNIT_PREFIX + SEPARATOR + "get-unit-owner-method";
    public static final String UNIT_GET_MOVECOUNT = UNIT_PREFIX + SEPARATOR + "get-movecount-method";
    public static final String UNIT_GET_DEFENSIVE_STRENGTH = UNIT_PREFIX + SEPARATOR + "get-defensive-strength-method";
    public static final String UNIT_SET_DEFENSIVE_STRENGTH = UNIT_PREFIX + SEPARATOR + "set-defensive-strength-method";
    public static final String UNIT_GET_ATTACKING_STRENGTH = UNIT_PREFIX + SEPARATOR + "get-attacking-strength-method";
    public static final String UNIT_SET_MOVED = UNIT_PREFIX + SEPARATOR + "set-moved-method";
    public static final String UNIT_GET_MOVED = UNIT_PREFIX + SEPARATOR + "get-moved-method";
    public static final String UNIT_IS_FORTIFIED = UNIT_PREFIX + SEPARATOR + "is-fortified-method";
    public static final String UNIT_SET_MOVEMENT_COUNT = UNIT_PREFIX + SEPARATOR + "set-movement-count-method";
    public static final String UNIT_SET_FORTIFIED = UNIT_PREFIX + SEPARATOR + "set-fortified-method";
    public static final String UNIT_GET_MAX_MOVE = UNIT_PREFIX + SEPARATOR + "get-max-move-method";

    //Tile methods
    public static final String TILE_GET_TYPESTRING = TILE_PREFIX + SEPARATOR + "get-tile-typestring-method";
}
