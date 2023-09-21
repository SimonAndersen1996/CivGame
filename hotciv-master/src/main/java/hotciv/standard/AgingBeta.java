package hotciv.standard;

import hotciv.framework.AgingStrategy;

/**
 * The word "phase" is what we use to refer the way time flows,
 * since there are various years per round, depending
 * on the current age.
 */

public class AgingBeta implements AgingStrategy {
    int sequence = 1;

    @Override
    public int passTime(int currentAge) {

        if(-4000 <= currentAge && currentAge < -100){
            return currentAge + 100;
        }
        else if(-100 <= currentAge && currentAge < 50){
            switch (sequence){
                case 1: sequence++;
                        return -100;
                case 2: sequence++;
                        return -1;
                case 3: sequence++;
                        return 1;
                case 4: return 50;
            }
        }
        else if(50 <= currentAge && currentAge < 1750){
            return currentAge + 50;
        }
        else if(1750 <= currentAge && currentAge < 1900){
            return currentAge + 25;
        }
        else if(1900 <= currentAge && currentAge < 1970){
            return currentAge + 5;
        }
        else{
            return currentAge + 1;
        }
        return currentAge;
    }
}
