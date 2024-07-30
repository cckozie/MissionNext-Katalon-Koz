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
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

ministryPreferences = ['Adult Men', 'Adult Women', 'Bible Teaching', 'Worship Leader', 'Church Planter', 'Leadership Development'
    , 'Marriage & Family', 'Missions Pastor', 'Pastor/Associate Pastor', 'Youth Pastor']

Runtime.getRuntime().exec('open /Applications/GoogleAnalyticsOptOut.app', '/Applications/GoogleAnalyticsOptOut/')

WebUI.openBrowser('')

//WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)
WebUI.navigateToUrl('https://journey.missionnext.org/journey-home/login-here/')

WebUI.click(findTestObject('Object Repository/temp/i_Wordpress_eicon-close'))

WebUI.setText(findTestObject('Object Repository/temp/input_Username_log'), 'cktest01@missionnext.org')

WebUI.setEncryptedText(findTestObject('Object Repository/temp/input_Password_pwd'), '5lwQhML0qdMWXTXRrgjfVw==')

WebUI.click(findTestObject('Object Repository/temp/button_Log In'))

WebUI.click(findTestObject('Object Repository/temp/a_My Profile'))

WebUI.click(findTestObject('Object Repository/temp/input_Email_profilegroup-1444754265.711email'))

WebUI.click(findTestObject('Object Repository/temp/a_Your Ministry Prefs'))

WebUI.setViewPortSize(600, 1200)

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> categories = driver.findElements(By.xpath('//input[@id=\'profile_group-1623987608.435_job_categories\']'))

for (def category : categories) {
    cat = WebUI.convertWebElementToTestObject(category)

    text = category.getAttribute('value')

    try {
        WebUI.click(cat)
    }
    catch (Exception e) {
        println('Unable to click on checkbox for Job Category ' + text)

        break
    } 
}

List<WebElement> positions = driver.findElements(By.xpath("//input[@id='profile_group-1446522088.64_ministry_preferences']"))
	
for (def position : positions) {
    pos = WebUI.convertWebElementToTestObject(position)

    text = position.getAttribute('value')

    try {
        WebUI.click(pos)
    }
    catch (Exception e) {
        println('Unable to click on checkbox for Preferred Position ' + text)

        KeywordUtil.markFailedAndStop('Unable to click on checkbox for Preferred Position " + text, test failed.')

        break
    } 
}

WebUI.closeBrowser()

WebUI.verifyImagePresent(findTestObject(null))

