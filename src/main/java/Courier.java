import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public final String login;
    public final String password;
    public final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }


    @Override
    public String toString() {
        return "Courier{" +
               "login='" + login + '\'' +
               ", password='" + password + '\'' +
               ", firstName='" + firstName + '\'' +
               '}';
    }

}
