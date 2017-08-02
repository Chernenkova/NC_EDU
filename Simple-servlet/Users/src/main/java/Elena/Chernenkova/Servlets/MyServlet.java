package Elena.Chernenkova.Servlets;

import Elena.Chernenkova.Service.Constants;
import Elena.Chernenkova.Service.Service;
import Elena.Chernenkova.entity.User;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Created by 123 on 21.07.2017.
 */
@WebServlet(name = "Servlet")
public class MyServlet extends HttpServlet {
    public Gson gson = new Gson();
    private static Logger log = Logger.getLogger(MyServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Method doPost begins");
        String s = this.ReqToString(request);
        String typeOfText = request.getContentType();
        switch(typeOfText){
            case Constants.JSON:
                log.info("json");
                workInJson(response, s);
                break;
            case Constants.XML:
                log.info("xml");
                workInXML(response, s);
                break;
        }
    }

    private void answerRegistration(int typeOfMessage, HttpServletResponse response) throws IOException{
        StringBuilder result = new StringBuilder();
        response.getWriter().println(result.append(Constants.ANSWER_REGISTRATION_STRING_PART1).append(typeOfMessage)
                .append(Constants.ANSWER_REGISTRATION_STRING_PART2).toString());
    }

    private void answerRegistrationJson(int typeOfMessage, HttpServletResponse response) throws IOException{
        response.getWriter().println(gson.toJson(new Service.TypeAndBalanceData(typeOfMessage, Constants.ZERO)));
    }

    private void answerGetBalance(int typeOfMessage, double balance, HttpServletResponse response) throws IOException{
        StringBuilder result = new StringBuilder();
        if (typeOfMessage == 0) {
            response.getWriter().println(result.append(Constants.ANSWER_GET_BALANCE_STRING_PART1).append(typeOfMessage)
                    .append(Constants.ANSWER_GET_BALANCE_STRING_PART2).append(balance)
                    .append(Constants.ANSWER_GET_BALANCE_STRING_PART3).toString());
        } else {
            response.getWriter().println(result.append(Constants.ANSWER_GET_BALANCE_STRING_PART1).append(typeOfMessage)
                    .append(Constants.ANSWER_GET_BALANCE_STRING_PART4).toString());
        }
    }

    private void answerGetBalanceJson(int typeOfMessage, double balance, HttpServletResponse response) throws IOException{

        if (typeOfMessage == 0) {
            response.getWriter().println(gson.toJson(new Service.TypeAndBalanceData(typeOfMessage, Constants.ZERO)));
        } else {
            response.getWriter().println(gson.toJson(new Service.TypeAndBalanceData(typeOfMessage, Constants.ZERO)));
        }
    }

    private String ReqToString(HttpServletRequest request) throws IOException{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String temp;
        while((temp = reader.readLine()) != null) {
            builder.append(temp).append(Constants.N);
        }
        return builder.toString();
    }

    private void workInXML(HttpServletResponse response, String s) throws ServletException, IOException{
        String type = Service.find(Constants.TYPE, s);
        if (Constants.TYPE_REGISTER_CUSTOMER.equals(type)) {
            String login = Service.find(Constants.LOGIN, s);
            if(!Service.isLoginCorrect(login)){
                log.info("Login is not correct");
                this.answerRegistration(2, response);
                return;
            }
            String password = Service.find(Constants.PASSWORD, s);
            if(!Service.isNewPasswordCorrect(password)){
                log.info("New password is not correct");
                this.answerRegistration(3, response);
                return;
            }
            if(Service.addUser(new User(login, DigestUtils.md5Hex(password)))) {
                this.answerRegistration(0, response);
            } else {
                log.info("This login exists");
                this.answerRegistration(1, response);
            }
        }

        if (Constants.TYPE_GET_BALANCE.equals(type)) {
            String login = Service.find(Constants.LOGIN, s);
            String password = Service.find(Constants.PASSWORD, s);
            if(!Service.isExist(login)) {
                log.info("This login does not exist");
                this.answerGetBalance(1, 0, response);
                return;
            }
            if(!Service.isPasswordCorrect(login, password)) {
                log.info("Password is not correct");
                this.answerGetBalance(3, 0, response);
                return;
            }
            this.answerGetBalance(0, Service.getBalance(login), response);
            return;
        }

        if (type.equals(Constants.TYPE_SET_BALANCE)) {
            String login = Service.find(Constants.LOGIN, s);
            double balance;
            try {
                balance = Double.parseDouble(Service.find(Constants.BALANCE, s));
            } catch (Exception e) {
                log.info("Balance is not correct");
                this.answerGetBalance(4, 0, response);
                return;
            }
            if(!Service.isExist(login)) {
                log.info("This login does not exist");
                this.answerGetBalance(1, 0, response);
                return;
            }
            Service.getUser(login).setBalance(Service.setNewBalance(Service.getUser(login).getBalance(), balance));
            this.answerGetBalance(0, balance, response);
            return;
        }
    }

    private void workInJson(HttpServletResponse response, String s) throws ServletException, IOException{
        String type = Service.findJson(Constants.TYPE, s);
        if (type.equals(Constants.TYPE_REGISTER_CUSTOMER)) {
            String login = Service.findJson(Constants.LOGIN, s);
            if(!Service.isLoginCorrect(login)){
                log.info("Login is not correct");
                this.answerRegistrationJson(2, response);
                return;
            }
            String password = Service.findJson(Constants.PASSWORD, s);
            if(!Service.isNewPasswordCorrect(password)){
                log.info("New password is not correct");
                this.answerRegistrationJson(3, response);
                return;
            }
            if(Service.addUser(new User(login, DigestUtils.md5Hex(password)))) {
                this.answerRegistrationJson(0, response);
            } else {
                log.info("This login exists");
                this.answerRegistrationJson(1, response);
            }
        }

        if (type.equals(Constants.TYPE_GET_BALANCE)) {
            String login = Service.findJson(Constants.LOGIN, s);
            String password = Service.findJson(Constants.PASSWORD, s);
            if(!Service.isExist(login)) {
                log.info("This login does not exist");
                this.answerGetBalanceJson(1, 0, response);
                return;
            }
            if(!Service.isPasswordCorrect(login, password)) {
                log.info("Password is not correct");
                this.answerGetBalanceJson(3, 0, response);
                return;
            }
            this.answerGetBalanceJson(0, Service.getBalance(login), response);
            return;
        }

        if (type.equals(Constants.TYPE_SET_BALANCE)) {
            String login = Service.findJson(Constants.LOGIN, s);
            double balance;
            try {
                balance = Double.parseDouble(Service.findJson(Constants.BALANCE, s));
            } catch (Exception e) {
                log.info("Balance is not correct");
                this.answerGetBalanceJson(4, 0, response);
                return;
            }
            if(!Service.isExist(login)) {
                log.info("This login does not exist");
                this.answerGetBalanceJson(1, 0, response);
                return;
            }
            Service.getUser(login).setBalance(Service.setNewBalance(Service.getUser(login).getBalance(), balance));
            this.answerGetBalanceJson(0, balance, response);
            return;
        }
    }
}
