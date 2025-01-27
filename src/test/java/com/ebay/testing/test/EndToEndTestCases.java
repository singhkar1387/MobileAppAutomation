package com.ebay.testing.test;


import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ebay.testing.core.BasePage;
import com.ebay.testing.page.CheckoutPage;
import com.ebay.testing.page.HomePage;
import com.ebay.testing.page.ProductDetailsPage;
import com.ebay.testing.page.SearchResultsPage;
import com.ebay.testing.testData.SearchData;
import com.ebay.testing.utils.TestInitializationUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;


public class EndToEndTestCases {
	@Test(groups= "checkoutProduct")
	public void checkoutProduct() throws Exception {
		String testCaseName=new Exception().getStackTrace()[0].getMethodName();

		TestInitializationUtils utils=new TestInitializationUtils();
		AndroidDriver<MobileElement> driver=utils.getDriver();

		SearchData data=SearchData.fetch(testCaseName);
		HomePage homePage=new HomePage();

		homePage
		.signIn(data.LoginDetails.emailid, data.LoginDetails.password)
		.searchItem(data.searchSku);

		SearchResultsPage srp=new SearchResultsPage();
		srp
		.clickOnRandomProduct();

		ProductDetailsPage pdp=new ProductDetailsPage(driver);
		pdp
		.verifyProductDetailsInProductPage()
		.addProductToCart()
		.addQuantity()
		.clickOnReviewButton();

		CheckoutPage checkoutPage=new CheckoutPage();

		if(checkoutPage.ifErrorPage()) {
			checkoutPage
			.clickOnAbutton();

		}else {
			checkoutPage
			.verifyProductDetailsCheckoutPage()
			.clickOnCommitButton();

		}
		homePage
		.signOut();
	}

	@Test(dataProvider="getDataFromExcel",groups="searchProduct")
	public void searchProduct(List <SearchData> data) throws Exception {
		String testCaseName=new Exception().getStackTrace()[0].getMethodName();
		TestInitializationUtils utils=new TestInitializationUtils();
		utils.getDriver();
		SearchData logindata=SearchData.fetch(testCaseName);
		for(int i=0;i<data.size();i++) {
			HomePage homePage=new HomePage();

			homePage
			.signIn(logindata.LoginDetails.emailid, logindata.LoginDetails.password)
			.searchItem(data.get(i).searchSku);
			homePage
			.signOut();
		}

	}
	@DataProvider 
	public static Object[][] getDataFromExcel() {
		List <SearchData> searchList=BasePage.getDataFromExcel();	

		return new Object[][]{
			{
				searchList
			},
		};
	}
}

