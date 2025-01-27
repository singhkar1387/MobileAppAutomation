package com.ebay.testing.core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.ebay.testing.page.HomePage;


public class TestListener implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub
		captureScreenShot(arg0);
		if(TestProperties.APPLICATION_TYPE.toString().equalsIgnoreCase("mobile")) {
			HomePage page=new HomePage();
			try {
				page.signOut();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DriverBase.getDriverInstanceForAndroid().closeApp();;
		}
		else {
			DriverBase.getDriverInstance().close();
		}
	}
	public void captureScreenShot(ITestResult arg0) {
		String testcase=arg0.getMethod().getMethodName();
		File source = ((TakesScreenshot) DriverBase.getDriverInstanceForAndroid()).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"\\test-output\\screenshots\\"+testcase+".png";
		try {
			File copied=new File(path);
			FileUtils.copyFile(source,copied );
			String hrefPath = "./screenshots/" + testcase + ".png";
			Reporter.log("<a href='" + hrefPath + "'>Screenshot</a>");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		if(TestProperties.APPLICATION_TYPE.toString().equalsIgnoreCase("mobile")) {
			DriverBase.getDriverInstanceForAndroid().closeApp();;
		}
		else {
			DriverBase.getDriverInstance().close();
		}
	}

}


