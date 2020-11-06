package com.ebay.testing.utils;

import org.openqa.selenium.WebDriver;

import com.ebay.testing.core.DriverBase;
import com.ebay.testing.core.TestProperties;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestInitializationUtils {

	
	public AndroidDriver<MobileElement> getDriver() {
		String url=TestProperties.TEST_URL.toString();
		AndroidDriver<MobileElement> driver=DriverBase.getDriverInstanceForAndroid();
		if(TestProperties.APPLICATION_TYPE.toString().equals("desktop")) {
		driver.get(url);
		}
		return driver;
	}
	public WebDriver getDriverForWeb() {
		String url=TestProperties.TEST_URL.toString();
		WebDriver driver=DriverBase.getDriverInstance();
		if(TestProperties.APPLICATION_TYPE.toString().equals("desktop")) {
			driver.get(url);
		}
		return driver;
	}
}
