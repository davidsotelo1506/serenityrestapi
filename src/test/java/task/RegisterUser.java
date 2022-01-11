package task;

import Model.RequestRegisterUser;
import interactions.Post;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import javax.annotation.PostConstruct;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterUser implements Task {

    private final RequestRegisterUser userInfo;

    public RegisterUser(RequestRegisterUser userInfo) {
        this.userInfo = userInfo;
    }

    public static Performable withInfo(RequestRegisterUser userInfo){
        return instrumented(RegisterUser.class, userInfo);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("users").with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .body(userInfo)
                )
        );
    }
}
