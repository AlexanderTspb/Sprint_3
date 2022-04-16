public class LoginRequestBody {

    public final String login;
    public final String password;

    public LoginRequestBody(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static LoginRequestBody from(Courier courier) {
        return new LoginRequestBody(courier.login, courier.password);
    }

    @Override
    public String toString() {
        return "loginRequestBody{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
