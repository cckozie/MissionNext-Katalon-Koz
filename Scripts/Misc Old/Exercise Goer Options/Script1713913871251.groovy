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

options = ['a_QuickStart', 'a_Start your journey', 'a_Go to Education', 'a_Jobs', 'a_Resources']

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

for (def option : options) {
    WebElement goer = driver.findElement(By.xpath('//a[contains(text(),\'Goer\')]'))

    Actions actions = new Actions(driver)

    actions.moveToElement(goer).perform()

    WebUI.waitForElementVisible(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/Goer_arrow'), 5)

    WebUI.click(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/Goer_arrow'))

    WebUI.click(findTestObject('Page_Serve in Missions - MissionNext.org/' + option))

    if (option != options.last()) {
        WebUI.back()

        WebUI.refresh()
    }
}

WebUI.closeBrowser()

