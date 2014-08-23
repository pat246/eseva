package scrapers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import utils.DialogUtils;

import database.Company;

public class EsevaResetPasswordScraper extends Thread {

    private String  sessionId;
    private String  userName;
    private String  password;
    private String  newPassword;
    private boolean success = false;
    public String   error   = "";

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public static void main(String[] args) throws IOException {
        EsevaResetPasswordScraper scraper = new EsevaResetPasswordScraper();
        try {
            boolean success = scraper.doLogin("sbpatil1973", "1234567@b");
            if (!success) {
                return;
            }
            success = scraper.changePassword("123456@b", "1234567@b");
        } finally {
            scraper.doLogout();
        }
        scraper.doLogout();
    }

    public boolean doLogin(String username, String password) {
        boolean success = false;
        try {
            Map<String, String> allFields = new HashMap();

            allFields.put("username", username);
            allFields.put("password", password);
            allFields.put("submit", "Sign+In");

            Connection conn = Jsoup.connect("http://esewa.epfoservices.in/index.php");

            Connection.Response res = conn.data(allFields).method(Connection.Method.POST).execute();
            this.sessionId = res.cookie("PHPSESSID");

            success = Jsoup.parse(res.body()).getElementsByClass("menubartext").get(0).html().contains("Welcome:");
        } catch (Exception e) {
            return false;
        }

        return success;
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        boolean success = false;
        try {
            Map<String, String> allFields = new HashMap();

            allFields.put("old_password", oldPassword);
            allFields.put("n_password", newPassword);
            allFields.put("c_password", newPassword);
            allFields.put("office", "Sign+In");
            allFields.put("submit", "Change+Password");

            Connection conn = Jsoup.connect("http://esewa.epfoservices.in/change_employer_pass.php")
                    .referrer("http://esewa.epfoservices.in/change_employer_pass.php")
                    .cookie("PHPSESSID", this.sessionId);

            Connection.Response res = conn.data(allFields).method(Connection.Method.POST).execute();

            success = res.body().contains("Password Changed Successfully");
        } catch (Exception e) {
            return false;
        }

        return success;
    }

    public boolean doLogout() {
        boolean success = false;
        try {
            Connection conn = Jsoup.connect("http://esewa.epfoservices.in/change_employer_pass.php?doLogout=true")
                    .referrer("http://esewa.epfoservices.in/change_employer_pass.php")
                    .cookie("PHPSESSID", this.sessionId);

            Connection.Response res = conn.method(Connection.Method.GET).execute();

            success = true;
        } catch (Exception e) {
            return false;
        }

        return success;
    }

    public EsevaResetPasswordScraper(String userName, String password, String newPassword) {
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
    }

    public EsevaResetPasswordScraper() {
    }

    public void run() {
        try {
            if (!doLogin(this.userName, this.password)) {
                throw new Exception("Login failed");
            }
            this.success = changePassword(this.newPassword, this.password);
            Company comp = Company.getCompanyByUsername(this.userName);
            Date currentDate = Calendar.getInstance().getTime();
            comp.updateLprd(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
        } finally {
            DialogUtils.WAIT_DIALOG.setVisible(false);
            doLogout();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
