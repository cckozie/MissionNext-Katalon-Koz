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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions;
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

WebUI.openBrowser('')

/*
WebUI.navigateToUrl('https://missionlinked.global/')

WebUI.scrollToPosition(9999999, 9999999)

//WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

href = WebUI.getAttribute(findTestObject(object), 'href')

println(href)

//System.exit(0)
clickAble = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.STOP_ON_FAILURE)

println(clickAble)

WebUI.navigateToUrl(href)

WebUI.click(findTestObject(object))

//WebUI.waitForPageLoad(20)
for (int i = 0; i < 30; i++) {
	String readyState = WebUI.executeJavaScript("return document.readyState;", null)
	if (readyState.equals("complete")) {
		break;
	}
	WebUI.delay(1)
}
*/
WebUI.navigateToUrl('https://missionlinked.global/')

links = ['Careers', 'Sponsorship', 'Sign Up']

for(link in links) {

	object = 'Object Repository/MissionLinked/Page_MissionLinked/a_' + link
	
	WebUI.callTestCase(findTestCase('_Functions/Page Element into View'), [('varObject') : object], FailureHandling.STOP_ON_FAILURE)
/*	
	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
	
	loc = element.getLocation()
	
	y = loc.getY()
	
	println('Y location is ' + y)
	
	top = WebUI.getViewportTopPosition()
	
	println('Viewport top is ' + top)
	
	height = WebUI.getViewportHeight()
	
	bottom = top + height
	
	println('Viewport bottom is ' + bottom)
	
	while(y > bottom) {
		WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
		
		top = WebUI.getViewportTopPosition()
		
		println('Viewport top is ' + top)
		
		height = WebUI.getViewportHeight()
		
		bottom = top + height
		
		println('Viewport bottom is ' + bottom)
	
	}

	System.exit(0)
	href = WebUI.getAttribute(findTestObject(object), 'href')
	
	println(href)
	
	clickAble = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.STOP_ON_FAILURE)
	
	println(clickAble)
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	
	clickAble = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.STOP_ON_FAILURE)
	
	println(clickAble)
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
*/
	clickAble = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.STOP_ON_FAILURE)
	
	println(clickAble)
	
	inViewport = WebUI.verifyElementInViewport(findTestObject(object), 0, FailureHandling.OPTIONAL)
	
	println(inViewport)
	
	WebUI.click(findTestObject(object))
	
	WebUI.delay(1)
	
	//WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	//WebUI.navigateToUrl(href)
	
	//WebUI.click(findTestObject('Object Repository/MissionLinked/Page_MissionLinked/a_Sponsorship'))
	
	WebUI.waitForPageLoad(20)
	
	println('Title is ' + WebUI.getWindowTitle())
	
	WebUI.navigateToUrl('https://missionlinked.global/')
}	
System.exit(0)
WebUI.click(findTestObject('Object Repository/MissionLinked/Page_MissionLinked/a_Sign Up'))

WebUI.waitForPageLoad(20)

WebUI.navigateToUrl('https://missionlinked.global/')

WebUI.click(findTestObject('Object Repository/MissionLinked/Page_MissionLinked/a_Privacy'))

WebUI.waitForPageLoad(20)

WebUI.navigateToUrl('https://missionlinked.global/')

WebUI.click(findTestObject('Object Repository/MissionLinked/Page_MissionLinked/a_Donate'))

WebUI.waitForPageLoad(20)

WebUI.navigateToUrl('https://missionlinked.global/')

WebUI.click(findTestObject('Object Repository/MissionLinked/Page_MissionLinked/a_Contact-Us'))

WebUI.waitForPageLoad(20)

WebUI.closeBrowser()

