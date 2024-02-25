package com.qa.viou.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openxml4j.exceptions.InvalidFormatException;

import com.qa.viou.keyword.base.Base;

public class KeyWordEngine {

	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	public ResourceBundle rb;
	public static WebDriver driver;
	public WebElement element;

	public final String SCENARIO_SHEET_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/qa/viou/keyword/scenarios/viou_scenarios.xlsx";

	public void startExecution(String sheetName) {
		String locatorName = null;
		String locatorValue = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = (Sheet) book.getSheet(sheetName);
		int k = 0;
		for (int i = 0; i < ((org.apache.poi.ss.usermodel.Sheet) sheet).getLastRowNum(); i++) {
			String locatorColValue = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(i + 1).getCell(k + 1).toString()
					.trim();
			if (!locatorColValue.equalsIgnoreCase("NA")) {
				locatorName = locatorColValue.split("=")[0].trim();
				locatorValue = locatorColValue.split("=")[1].trim();
			}
			String action = ((Cell) sheet).getRow().getCell(k + 2).toString().trim();
			String value = ((Cell) sheet).getRow().getCell(k + 3).toString().trim();

			switch (action) {
			case "open browser":
				base = new Base();
				String browserName = base.getURL().getString("browser");
				if (value.isEmpty() || value.equals("NA")) {
					driver = base.init_driver(browserName);
				} else {
					driver = base.init_driver(value);
				}
				break;
			case "enter url":
				if (value.isEmpty() || value.equals("NA")) {
					driver.get(base.getURL().getString("url"));
				} else {
					driver.get(value);
				}
				break;
			case "quit":
				driver.quit();
				break;
			default:
				break;
			}

			switch (locatorName) {
			case "id":
				element = driver.findElement(By.id(locatorName));
				if (action.equalsIgnoreCase("sendkeys")) {
					element.sendKeys(value);
				} else if (action.equalsIgnoreCase("click")) {
					element.click();
				}
				locatorName=null;
				break;
				
			case "linkText":
				element = driver.findElement(By.linkText(locatorName));
				element.click();
				locatorName=null;
				break;
				
			default:
				break;
			}
		}
	}

}
