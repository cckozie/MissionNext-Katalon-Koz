import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

/*

https://www.themissionapp.com/missionary-application/#startapp
https://www.themissionapp.com/missionary-test/
https://themissionapp.com/wp-content/uploads/2021/06/The-Ultimate-Mission-Checklist-for-Your-Church-2.pdf  (Paulette, where this is on the website??)
https://www.themissionapp.com/blog/ (Ultimate Preparation Checklist Download)
https://www.themissionapp.com/contact-us/
 
*/
WebUI.openBrowser('')

WebUI.maximizeWindow()

urls = ["https://www.themissionapp.com/5-mission-agencies-church-planting-among-the-unreached-of-central-asia/#content", 
"https://www.themissionapp.com/6-mission-agencies-church-planting-among-the-unreached-of-sub-saharan-africa/#content", 
"https://www.themissionapp.com/7-mission-agencies-church-planting-among-the-unreached-of-the-middle-east/#content", 
"https://www.themissionapp.com/10-mission-agencies-church-planting-among-the-unreached-of-north-africa/#content", 
"https://www.themissionapp.com/8-mission-agencies-church-planting-among-the-unreached-of-eastern-europe/#content", 
"https://www.themissionapp.com/5-steps-to-pray-for-the-unreached/#content", 
"https://www.themissionapp.com/heart-check-are-you-cut-out-for-the-mission-field/#content", 
"https://www.themissionapp.com/missionary-mom-life/#content", 
"https://www.themissionapp.com/fully-funding-your-ministry-5-keys-to-personal-support-raising/#content", 
"https://www.themissionapp.com/am-i-called-to-missions/#content", 
"https://www.themissionapp.com/arriving-well/#content", 
"https://www.themissionapp.com/5-things-i-wish-someone-had-told-me-about-missions/#content", 
"https://www.themissionapp.com/10-steps-to-become-a-missionary/#content", 
"https://www.themissionapp.com/webinar-teachers-world-missions/#content", 
"https://www.themissionapp.com/9-mission-agencies-church-planting-among-the-unreached-of-south-america/#content", 
"https://www.themissionapp.com/blog/page/2/#content", 
"https://www.themissionapp.com/blog/page/3/#content", 
"https://www.themissionapp.com/blog/page/4/#content", 
"https://www.themissionapp.com/7-mission-agencies-church-planting-among-the-unreached-of-west-africa/#content", 
"https://www.themissionapp.com/8-mission-agencies-church-planting-among-the-unreached-of-south-east-asia/#content", 
"https://www.themissionapp.com/8-mission-agencies-church-planting-among-the-unreached-of-central-america/#content", 
"https://www.themissionapp.com/9-mission-agencies-church-planting-among-the-unreached-of-north-america/#content"]

for(url in urls) {
	WebUI.navigateToUrl(url)

	WebUI.delay(5)
	
	found = WebUI.verifyTextPresent('Checklist for Your Church', false, FailureHandling.OPTIONAL)
	
	if(found) {
		break
	}
}
