package handlers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class EcrNavigation {
	public String m_browser = "IE";

	public void doLogin(String uid, String pass) {
		WebDriver driver = null;
		if (this.m_browser.equals("FX")) {
			driver = new FirefoxDriver();
		} else if (this.m_browser.equals("IE")) {
			driver = new InternetExplorerDriver();
		} else if (this.m_browser.equals("CHROME")) {
			driver = new ChromeDriver();
		}
		doStuff(driver, uid, pass);
	}

	private void doStuff(WebDriver driver, String uid, String pass) {
		String baseUrl = "http://esewa.epfoservices.in/";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);
		driver.get(baseUrl + "/index.php");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(new CharSequence[] { uid });
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(new CharSequence[] { pass });
		driver.findElement(By.name("submit")).click();
	}

	public EcrNavigation() {
	}
}
