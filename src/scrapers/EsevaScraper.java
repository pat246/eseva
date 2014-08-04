package scrapers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class EsevaScraper {
    private String sessionId;

    public EsevaScraper() {
    }

    public static void main(String[] args) throws IOException {
        EsevaScraper scraper = new EsevaScraper();
        try {
            boolean success = scraper.doLogin("sbpatil1973", "123456@b");
            Date lprd = scraper.getLprd();
            if (!success) {
                return;
            }
        } finally {
            scraper.doLogout();
        }
    }

    public Date getLprd() {
        try {
            Connection conn = Jsoup.connect("http://esewa.epfoservices.in/view_editing_history.php");
            Connection.Response res = conn.method(Connection.Method.GET).cookie("PHPSESSID", this.sessionId).execute();

            Element firstRow = Jsoup.parse(res.body()).getElementsByTag("tr").get(2);
            String index = firstRow.getElementsByTag("td").get(0).html();
            String dateStr = firstRow.getElementsByTag("td").get(1).html();
            if ("1".equals(index)) {
                return utils.ThreadSafeUtil.getDateTime12HrsWithoutSecondsDotFormat(false, false).parse(dateStr);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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
}
