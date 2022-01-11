package facts;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.facts.Fact;

public class NetflixPlans implements Fact {

    public static NetflixPlans toViewSeries(){
        return new NetflixPlans();
    }
    @Override
    public void setup(Actor actor) {

    }
}
