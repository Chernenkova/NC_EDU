package Elena.Chernenkova;


import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 123 on 25.07.2017.
 */
public class Client extends Socket {
    public static boolean format = true;       // false - XML, true - json
    public static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println(Constants.WELCOME);
            String code = reader.readLine();
            String login, password;
            switch(code) {
                case Constants.CASE1:
                    System.out.print(Constants.ASK_LOGIN);
                    login = reader.readLine();
                    System.out.print(Constants.ASK_PASSWORD);
                    password = reader.readLine();
                    receiveRegistrationMessage(sendMessage(registrationMessage(login, password)));
                    break;
                case Constants.CASE2:
                    System.out.print(Constants.ASK_LOGIN);
                    login = reader.readLine();
                    System.out.print(Constants.ASK_NEW_BALANCE);
                    String balance = reader.readLine();
                    receiveSetBalanceMessage(sendMessage(setBalance(login, balance)));
                    break;
                case Constants.CASE3:
                    System.out.print(Constants.ASK_LOGIN);
                    login = reader.readLine();
                    System.out.print(Constants.ASK_PASSWORD);
                    password = reader.readLine();
                    receiveGetBalanceMessage(sendMessage(getBalance(login, password)));
                    break;
                case Constants.CASE4:
                    return;
                case Constants.CASE5:
                    System.out.println(Constants.CHOOSE_TYPE_OF_MESSAGE);
                    String s = reader.readLine();
                    if(Constants.CASE1.equals(s)) {
                        format = false;
                    } else {
                        if (Constants.CASE2.equals(s)) {
                            format = true;
                        } else {
                            System.out.println(Constants.ERROR);
                        }
                    }
                    break;
                default:
                    System.out.println(Constants.ERROR);
            }
        }
    }

    private static String sendMessage(String message){
        Socket s = null;
        String result = Constants.ERROR;
        StringBuilder builder = new StringBuilder();
        try{
            s = new Socket(Constants.LOCAL_HOST, 8080);
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            w.write(Constants.PATH);
            w.write(builder.append(Constants.CONTENT_LENGTH).append(message.length()).append(Constants.RN).toString());
            if(format) {
                w.write(Constants.CONTENT_TYPE_JSON);
            } else {
                w.write(Constants.CONTENT_TYP_XML);
            }
            w.write(Constants.RN);

            w.write(message);
            w.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String temp;
            StringBuilder tempResult = new StringBuilder();
            while ((temp = reader.readLine())!= null){
                tempResult.append(temp).append(Constants.N);
            }
            result = tempResult.toString();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(s != null){
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String registrationMessage(String login, String password){
        StringBuilder result = new StringBuilder();
        if(format) {
            return gson.toJson(new Service.LoginAndPasswordData(Constants.TYPE_REGISTER_CUSTOMER, login, password));
        } else {
            return result.append(Constants.REGISTRATION_MESSAGE_PART1).append(login).append(Constants.REGISTRATION_MESSAGE_PART2)
                    .append(password).append(Constants.REGISTRATION_MESSAGE_PART3).toString();
        }
    }

    private static String setBalance(String login, String balance){
        StringBuilder result = new StringBuilder();
        if(format) {
            return gson.toJson(new Service.LoginAndBalanceData(Constants.TYPE_SET_BALANCE, login, balance));
        } else {
            return result.append(Constants.SET_BALANCE_PART1).append(login).append(Constants.SET_BALANCE_PART2)
                    .append(balance).append(Constants.SET_BALANCE_PART3).toString();
        }
    }

    private static String getBalance(String login, String password){
        StringBuilder result = new StringBuilder();
        if(format) {
            return gson.toJson(new Service.LoginAndPasswordData(Constants.TYPE_GET_BALANCE, login, password));
        } else {
            return result.append(Constants.GET_BALANCE_PART1).append(login).append(Constants.GET_BALANCE_PART2)
                    .append(password).append(Constants.GET_BALANCE_PART3).toString();
        }
    }

    private static void receiveRegistrationMessage(String message) throws IOException{
        int type = format ? Integer.parseInt(Service.findJson(Constants.TYPE_M, message))
                :Integer.parseInt(Service.find(Constants.RESULT_CODE, message));

        System.out.println(Constants.REGISTRATION_ANSWER[type]);
        System.out.println();
    }

    private static void receiveSetBalanceMessage(String message) throws IOException{
        int type = format ? Integer.parseInt(Service.findJson(Constants.TYPE_M, message))
                :Integer.parseInt(Service.find(Constants.RESULT_CODE, message));

        if (type == 1) {
            System.out.println(Constants.ACCOUNT_WAS_NOT_FOUND);
            return;
        }
        if (type == 4){
            System.out.println(Constants.INVALID_BALANCE);
            return;
        }
        Double balance = format ? Double.parseDouble(Service.findJson(Constants.BALANCE, message))
                :Double.parseDouble(Service.find(Constants.BALANCE, message));

        System.out.println(Constants.YOUR_BALANCE + balance);
        System.out.println();
    }

    private static void receiveGetBalanceMessage(String message) throws IOException{
        int type;
        if(format) {
            type = Integer.parseInt(Service.findJson(Constants.TYPE_M, message));
        } else {
            type = Integer.parseInt(Service.find(Constants.CODE, message));
        }
        switch(type){
            case 0:
                Double balance = format ? Double.parseDouble(Service.findJson(Constants.BALANCE, message))
                        : Double.parseDouble(Service.find(Constants.BALANCE, message));

                System.out.println(Constants.YOUR_BALANCE + balance);
                System.out.println();
                break;
            case 1:
                System.out.println(Constants.ACCOUNT_WAS_NOT_FOUND);
                break;
            case 3:
                System.out.println(Constants.WRONG_PASSWORD);
                break;
            case 4:
                System.out.println(Constants.ANOTHER_ERROR);
                break;
        }
    }
}
