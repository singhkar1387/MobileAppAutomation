package com.ebay.testing.core;


import java.util.HashMap;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ebay.testing.utils.Locator;
import com.ebay.testing.utils.TestNGUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class Action {
	WebDriver driver;
	AndroidDriver<MobileElement> appdriver;
	HashMap<String, Object> hashMap=new HashMap<String, Object>();
	public Action(AndroidDriver<MobileElement> driver) {
		this.appdriver = driver;
	}

	/**
	 * @author Karampal
	 * Description: Click on a Locator
	 * @return
	 * @throws Exception
	 */
	public Action click(Locator loc) throws Exception {
		try {
			appdriver.findElement(Locator.getByObject(loc)).click();
		}catch(Exception e) {
			TestNGUtils.reportLog("The element:"+loc.getKey()+" could not be clicked");
			throw e;
		}

		return this;
	}
	
	/**
	 * @author Karampal
	 * Description: Store a value with key
	 * @return
	 * @throws Exception
	 */
	public  synchronized void storeKeyValue(String key,Object value) {
		hashMap.put(key, value);
	}
	
	/**
	 * @author Karampal
	 * Description: Retrieve value with Key
	 * @return
	 * @throws Exception
	 */
	public  synchronized Object retrieveKeyValue(String key) {
		return hashMap.get(key);
	}

	public Action scrollTo(Locator loc) {
		int x=0;
		try {
			while(!isElementVisible(loc)) {
				x++;
				int pressX = appdriver.manage().window().getSize().width / 2;
				int bottomY = appdriver.manage().window().getSize().height * 2/5;
				int topY = appdriver.manage().window().getSize().height / 16;
				new TouchAction(appdriver).longPress(pressX, bottomY).moveTo(pressX, topY).release().perform();
				if(isElementVisible(loc)||x>5) {
					break;
				}
			}
		}catch(Exception e) {
			TestNGUtils.reportLog("The element:"+loc.getKey()+" could not be scrolled to");

		}

		return this;
	}
	
	/**
	 * @author Karampal
	 * Description: Scroll for 'N' number of times
	 * @return
	 * @throws Exception
	 */
	public Action scrollForN(int num) {
		try {
			while(num>0) {
				num--;
				int pressX = appdriver.manage().window().getSize().width / 2;
				int bottomY = appdriver.manage().window().getSize().height * 2/5;
				int topY = appdriver.manage().window().getSize().height / 16;
				new TouchAction(appdriver).longPress(pressX, bottomY).moveTo(pressX, topY).release().perform();
				if(num>7) {
					break;
				}
			}
		}catch(Exception e) {				
		}

		return this;
	}
	
	/**
	 * @author Karampal
	 * Description: Find element in the page by locator
	 * @return
	 * @throws Exception
	 */
	public WebElement findElement(Locator loc)  {

		waitTillElementVisible(3000,loc);
		WebElement ele=appdriver.findElement(Locator.getByObject(loc));
		return ele;
	}
	
	/**
	 * @author Karampal
	 * Description: Get text from locator
	 * @return
	 * @throws Exception
	 */
	public String getText(Locator loc) throws Exception {

		String text=	appdriver.findElement(Locator.getByObject(loc)).getText();
		return text;
	}
	
	/**
	 * @author Karampal
	 * Description: Returns true if element visible in page 
	 * @return
	 * @throws Exception
	 */
	public boolean isElementVisible(Locator loc){
		boolean isvisible=false;
		try {
			appdriver.findElement(Locator.getByObject(loc));
			isvisible=true;
		}catch(Exception e) {

		}		
		return isvisible;
	}
	
	/**
	 * @author Karampal
	 * Description: Send text to locator
	 * @return
	 * @throws Exception
	 */
	public Action sendText(Locator loc,String input) {
		waitTillElementVisible(3000,loc);
		appdriver.findElement(Locator.getByObject(loc)).clear();;
		appdriver.findElement(Locator.getByObject(loc)).sendKeys(input);

		return this;
	}
	
	/**
	 * @author Karampal
	 * Description: Press enter in android
	 * @return
	 * @throws Exception
	 */
	public Action pressEnter() {

		appdriver.pressKeyCode(AndroidKeyCode.ENTER);

		return this;
	}
	
	/**
	 * @author Karampal
	 * Description: Wait till element is visible
	 * @return
	 * @throws Exception
	 */
	public void waitTillElementVisible(long sec, Locator loc) {
		try {
			WebDriverWait wait=new WebDriverWait(appdriver, sec);
			wait.until(ExpectedConditions.visibilityOf(appdriver.findElement(Locator.getByObject(loc))));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Karampal
	 * Description: Rotate to 'mode'. Mode can be landscape or portrait
	 * @return
	 * @throws Exception
	 */
	public void rotateTo(String mode) {
		try {

			switch(mode) {
			case "landscape":
				appdriver.rotate(ScreenOrientation.LANDSCAPE);
				break;

			case "portrait":
				appdriver.rotate(ScreenOrientation.PORTRAIT);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Karampal
	 * Description: wait for milliseconds
	 * @return
	 * @throws Exception
	 */
	public void waitFor(long sec) {
		try {
			Thread.sleep(sec);
			//appdriver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);

		}catch(Exception e) {
			e.printStackTrace();
		}


	}
}