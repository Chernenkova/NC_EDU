package Elena.Chernenkova;

/**
 * Created by 123 on 02.08.2017.
 */
public class Constants {
    public final static String CONTENT_TYPE_JSON = "Content-Type: json\r\n", CONTENT_TYP_XML = "Content-Type: xml\r\n";
    public final static String BALANCE = "balance", TYPE_M = "typeM", RESULT_CODE = "result-code",
            YOUR_BALANCE = "Your balance: ", CODE = "code";
    public final static String WELCOME = "What do you want to do?\n1. Create new account\n2. Set balance\n3. Get balance\n4. Finish\n5. XML or json?";
    public final static String TYPE_REGISTER_CUSTOMER = "registerCustomer", TYPE_SET_BALANCE = "setBalance",
            TYPE_GET_BALANCE = "getBalance";
    public final static String CHOOSE_TYPE_OF_MESSAGE = "1 - XML\n2 - json", ERROR = "Error!", RN = "\r\n", N = "\n";
    public final static String ASK_LOGIN = "Login: ", ASK_PASSWORD = "Password: ", ASK_NEW_BALANCE = "New balance: ";
    public final static String CASE1 = "1", CASE2 = "2", CASE3 = "3", CASE4 = "4", CASE5 = "5", TYPE = "type";
    public final static String LOCAL_HOST = "localhost", PATH ="POST  /MyServlet HTTP/1.0\r\n",
            CONTENT_LENGTH = "Content-Length: ";
    public final static String REGISTRATION_MESSAGE_PART1 = "<?xml version=”1.0” encoding=”utf-8”?>\n<request>\n<type>registerCustomer</type>\n<login>";
    public final static String REGISTRATION_MESSAGE_PART2 = "</login>\n<password>",
            REGISTRATION_MESSAGE_PART3 = "</password>\n</request>\n";
    public final static String SET_BALANCE_PART1 = "<?xml version=”1.0” encoding=”utf-8”?>\n<request>\n<type>setBalance</type>\n<login>";
    public final static String SET_BALANCE_PART2 = "</login>\n<balance>", SET_BALANCE_PART3 = "</balance>\n</request>\n";
    public final static String GET_BALANCE_PART1 = "<?xml version=”1.0” encoding=”utf-8”?>\n<request>\n<type>getBalance</type>\n<login>";
    public final static String GET_BALANCE_PART2 = "</login>\n<password>", GET_BALANCE_PART3 = "</password>\n</request>\n";
    public final static String[] REGISTRATION_ANSWER = {"Successful registration.", "This number has already registered.",
            "Invalid format of number.", "Insecure password.", "Another error. Please, try again later."};
    public final static String ACCOUNT_WAS_NOT_FOUND = "The account wasn't found.\n", INVALID_BALANCE = "Invalid format of balance.\n",
            WRONG_PASSWORD = "Wrong password\n", ANOTHER_ERROR = "Another error. Please, try again later.\n";
}
