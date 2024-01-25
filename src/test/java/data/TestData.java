package data;

import com.github.javafaker.Faker;

public class TestData {
    private final Faker faker = new Faker();

    public String userName = faker.name().username() + System.currentTimeMillis();
    public String password = faker.internet().password(8, 15,
            true, true);
    public String isbn = faker.options().option("9781449325862", "9781449331818",
            "9781449337711", "9781449365035", "9781491904244", "9781491950296", "9781593275846", "9781593277574");
    public String authResultMessage = "User authorized successfully.";
    public String userNotFoundCode = "1207";
    public String userNotFoundMessage = "User not found!";
}
