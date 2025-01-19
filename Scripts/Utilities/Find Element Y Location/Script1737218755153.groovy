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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)

//Go to the Availability tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Availability'))

WebDriver driver = DriverFactory.getWebDriver()

println(WebUI.getViewportTopPosition())

element = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Education Candidate Profile/Tabs/Availability/btn_Submit'), 1)

loc = element.getLocation()

y = loc.getY()

println('Y location is ' + y)

v = WebUI.getViewportTopPosition()

println('Relative location is ' + Math.abs(y-v))

atBottom = WebUI.verifyElementInViewport(findTestObject('Object Repository/Education Candidate Profile/Tabs/Availability/section_Footer'), 2,
    FailureHandling.OPTIONAL)

println(atBottom)

b = 500

while (!(atBottom)) {
    WebUI.scrollToPosition(0, b)

    b = (b + 50)

    WebUI.delay(1)

	atBottom = WebUI.verifyElementInViewport(findTestObject('Object Repository/Education Candidate Profile/Tabs/Availability/section_Footer'), 2,
		FailureHandling.OPTIONAL)
}


v = WebUI.getViewportTopPosition()


println('Relative location is ' + (y-v))


