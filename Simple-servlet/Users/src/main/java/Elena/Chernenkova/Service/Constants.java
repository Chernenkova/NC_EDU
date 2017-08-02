package Elena.Chernenkova.Service;

/**
 * Created by 123 on 02.08.2017.
 */
public class Constants {
    public final static String JSON = "json", XML = "xml";
    public final static String LOGIN = "login", BALANCE = "balance", PASSWORD = "password", USER_IS_EXIST = "User.isExist", TYPE_M = "typeM";
    public final static String TYPE_REGISTER_CUSTOMER = "registerCustomer", TYPE_SET_BALANCE = "setBalance", TYPE_GET_BALANCE = "getBalance";
    public final static String TYPE = "type", N = "\n", ZERO = "0.0";
    public final static String ANSWER_REGISTRATION_STRING_PART1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<response><result-code>",
            ANSWER_REGISTRATION_STRING_PART2 = "</result-code></response>";
    public final static String ANSWER_GET_BALANCE_STRING_PART1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<response><code>",
            ANSWER_GET_BALANCE_STRING_PART2 = "</code><balance>", ANSWER_GET_BALANCE_STRING_PART3 = "</balance></response>",
            ANSWER_GET_BALANCE_STRING_PART4 = "</code></response>";
}
