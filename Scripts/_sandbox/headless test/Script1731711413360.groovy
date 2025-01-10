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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities

//System.setProperty('webdriver.chrome.driver', '/Applications/Katalon Studio Free.app/Contents/Eclipse/configuration/resources/drivers/chromedriver_mac/chromedriver')
WebUI.openBrowser(null)

WebUI.maximizeWindow()

headless = WebUI.callTestCase(findTestCase('_Functions/Test for Headless Browser'), [:], FailureHandling.STOP_ON_FAILURE)

println(headless)

System.exit(4)
WebDriver driver = DriverFactory.getWebDriver()

user_agent = driver.executeScript('return navigator.userAgent;')

println(user_agent)

pos = user_agent.indexOf('Headless')

if (pos >= 0) {
    println('yes')
} else {
    println('no')
}

//WebDriver driver = new ChromeDriver(options);
//DriverFactory.changeWebDriver(driver)
System.exit(1)

//WebUI.openBrowser('google.com')
//WebUI.maximizeWindow()
//WebDriver driver = DriverFactory.getWebDriver()
/*
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
*/
options.add_argument('--headless')

//service = Service(ChromeDriverManager().install())
//driver = webdriver.Chrome(service=service, options=chrome_options)
println(options)

position = driver.manage().window().getPosition()

println(position.getX())

println(position.getY())

System.exit(9)

WebUI.click(findTestObject('Object Repository/temp/a_Read More'))

WebUI.delay(10)

System.exit(2)

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

domain = GlobalVariable.domain

WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))

//WebUI.click(findTestObject('Object Repository/temp/a_Educator Matches'))
//WebUI.verifyElementVisible(findTestObject('Object Repository/temp/td_1'), FailureHandling.OPTIONAL)
//WebUI.verifyElementVisible(findTestObject('Object Repository/temp/td_1031'), FailureHandling.OPTIONAL)
/*
WebUI.delay(5)

WebUI.switchToWindowIndex(1)
WebUI.verifyElementText(findTestObject('Object Repository/temp/td_1'), '1')

WebUI.verifyElementText(findTestObject('Object Repository/temp/td_1031'), '1031')

WebUI.scrollToElement(findTestObject('Object Repository/temp/td_1'), 2)

WebUI.scrollToElement(findTestObject('Object Repository/temp/td_1031'), 2)

WebUI.scrollToElement(findTestObject('Object Repository/temp/td_Folder assignment by you. To change, select a new folder from the dropdown list, then click the Set Folders button'), 2)

v = 1
while(v<=1030) {
	val = v.toString() + ' '
	println(val)
	WebUI.scrollToElement(findTestObject('Object Repository/temp/td_Number Parm', [('val') : val]), 2)
	v = v + 20
	WebUI.delay(1)
}
//WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/checkbox_Affiliation-Parm', [('org') : org]))
System.exit(0)


url = WebUI.getUrl(FailureHandling.OPTIONAL)

if(url.indexOf('dashboard') >= 0) {
	WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))
}

*/
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Positions Needed'))

//WebDriver driver = DriverFactory.getWebDriver()
//elements = driver.findElements(By.xpath(varXpaths[i]))
//elements1 = driver.findElements(By.cssSelector("*"))
elements = driver.findElements(By.xpath('//*[@class = \'mn-group-choices\']'))

println(elements.size())

//println(elements1.size())
for (def element : elements) {
    myObj = WebUI.convertWebElementToTestObject(element //Convert the element to a test object
        )

    WebUI.scrollToElement(myObj, 2)

    WebUI.delay(1)
}

