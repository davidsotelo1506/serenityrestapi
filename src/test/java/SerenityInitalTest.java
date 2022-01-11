import Model.DataItem;
import Model.RequestRegisterUser;
import Model.RequestUpdateUser;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import task.GetUser;
import task.RegisterUser;
import task.UpdateUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class SerenityInitalTest {

    private static final String restApiUrl= "https://reqres.in/api/";

    @Test
    public void getUser(){

        Actor david = Actor.named("David")
                .whoCan(CallAnApi.at(restApiUrl));
        david.attemptsTo(
            GetUser.fromPage(1)
        );
        david.should(
                seeThat("el codigo de respuesta ", ResponseCode.was(), equalTo(200))
        );

        DataItem user = new GetUsersQuestion().answeredBy(david)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);
        david.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
        );
        david.should(
                seeThat("el correo es", act -> user.getEmail(), equalTo("george.bluth@reqres.in"))
        );
    }

    @Test
    public void registerUserTest(){

        Actor david = Actor.named("David")
                .whoCan(CallAnApi.at(restApiUrl));
        RequestRegisterUser user = new RequestRegisterUser();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        david.attemptsTo(
                RegisterUser.withInfo(user)
        );
        david.should(
                seeThat("el codigo de respuesta ", ResponseCode.was(), equalTo(201))
        );
    }

    @Test
    public void updateUserTest(){

        Actor david = Actor.named("David")
                .whoCan(CallAnApi.at(restApiUrl));
        RequestUpdateUser userUpdate = new RequestUpdateUser();
        userUpdate.setName("morpheus");
        userUpdate.setJob("zion resident");
        david.attemptsTo(
                UpdateUser.withInfo(userUpdate)
        );
        david.should(
                seeThatResponse("retonar campo 'updatedAt' ",
                        response -> response.statusCode(200)
                                .body("updatedAt", notNullValue()))
        );
    }
}
