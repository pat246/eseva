package handlers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class EcrNavigation {

	public String m_browser = "IE";

	public void doLogin(String uid, String pass) {

		WebDriver driver = null;
		if (m_browser.equals("FX")) {
			driver = new FirefoxDriver();
		} else if (m_browser.equals("IE")) {
			driver = new InternetExplorerDriver();
		}
		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(driver,
				"http://esewa.epfoservices.in");
		// selenium.start();
		selenium1.open("");
		selenium1.type("username", uid);
		selenium1.type("password", pass);
		selenium1.click("submit");
	}

	public EcrNavigation() {
	}
}
