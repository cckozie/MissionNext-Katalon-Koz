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
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import groovy.time.*
import java.io.File as File
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import java.util.concurrent.TimeUnit

////////////////////// ADDED CHROME DRIVER OPTION THAT DISABLES JAVASCRIPT \\\\\\\\\\\\\\\\\\\\\\\
//\\\\\\\\\\\\\\ THEN REMOVED IT BECAUSE IT DIDN'T HELP WITH STALE ELEMENT ERRORS ////////////////
System.setProperty('webdriver.chrome.driver', '/Applications/Katalon Studio Free.app/Contents/Eclipse/configuration/resources/drivers/chromedriver_mac/chromedriver')

ChromeOptions options = new ChromeOptions()

options.addExtensions(new File('/Users/cckozie/Downloads/fllaojicojecljbmefodhfapmkghcbnh-1.1-Crx4Chrome.com.crx'))

// new stuff
//options.setCapability("javascript.enabled", false)
//

DesiredCapabilities caps = new DesiredCapabilities()

caps.setCapability(ChromeOptions.CAPABILITY, options)

WebDriver driver = new ChromeDriver(caps)

DriverFactory.changeWebDriver(driver)

driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES)


WebUI.maximizeWindow()

