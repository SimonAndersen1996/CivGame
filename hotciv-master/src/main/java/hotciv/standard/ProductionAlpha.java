package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.impls.UnitImpl;

public class ProductionAlpha implements ProductionStrategy {

    @Override
    public void produceUnit(Position cityPosition, Game game) {
        City city = game.getCityAt(cityPosition);
        String productionType = city.getProductionType();
        switch (productionType) {
            case GameConstants.ARCHER:
                if (city.getTreasury() < 10) {
                    break;
                }
                game.addUnit(game.getFreePosition(cityPosition), new UnitImpl(city.getProductionType(), city.getOwner()));
                game.increaseProduction(cityPosition, -10);
                break;
            case GameConstants.SETTLER:
                if (city.getTreasury() < 30) {
                    break;
                }
                game.addUnit(game.getFreePosition(cityPosition), new UnitImpl(city.getProductionType(), city.getOwner()));
                game.increaseProduction(cityPosition, -30);
                break;
            case GameConstants.LEGION:
                if (city.getTreasury() < 15) {
                    break;
                }
                game.addUnit(game.getFreePosition(cityPosition), new UnitImpl(city.getProductionType(), city.getOwner()));
                game.increaseProduction(cityPosition, -15);
                break;
        }
    }

}
