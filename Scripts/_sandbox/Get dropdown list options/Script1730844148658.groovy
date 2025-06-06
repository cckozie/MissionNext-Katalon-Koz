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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.*

// For use potentially in using Excel sheets as input pages for creating/updating profiles.

//Education
education = '//*[@id="profile_group-1443650427.743_highest_earned_degree"]'

//Classroom experience
experience = '//*[@id="profile_group-1443650427.743_classroom_experience"]'

//Cross Cultural Experience
cultural = '//*[@id="profile_group-1443650427.743_cross-cultural_experience"]'

lists = [education, experience, cultural]

WebDriver driver = DriverFactory.getWebDriver()

for(list in lists) {
	
	Select drop = new Select(driver.findElement(By.xpath(list)));
	
	options = drop.getOptions();
	
	println('Options for ' + list)
	
	options.each{
		println(it.getText()) 
	}
}
