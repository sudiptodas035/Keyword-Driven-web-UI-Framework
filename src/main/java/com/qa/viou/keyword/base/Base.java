package com.qa.viou.keyword.base;

import java.util.Properties;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;

public class Base {

	protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public Properties prop;

	public WebDriver init_driver(String browserName) {
		if (browserName.equals("chrome")) {

			if (prop.getProperty("headless").equals("yes")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver.set(ThreadGuard.protect(new ChromeDriver(options)));
			} else {
				ChromeOptions options = new ChromeOptions();
				driver.set(ThreadGuard.protect(new ChromeDriver(options)));
			}
		} else if (browserName.equals("firefox")) {

		}
		return driver.get();
	}

	// method created for getting URL's from properties file
	public ResourceBundle getURL() {
		ResourceBundle routes = ResourceBundle.getBundle("config"); // load the properties file
		return routes;
	}

}
