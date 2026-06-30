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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper


object = varObject //'Object Repository/MissionLinked/Page_MissionLinked/a_' + link

element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)

loc = element.getLocation()

y = loc.getY()

//println('Y location is ' + y)

top = WebUI.getViewportTopPosition()

//println('Viewport top is ' + top)

height = WebUI.getViewportHeight()

bottom = top + height

if(y > bottom) {
	keys = Keys.PAGE_DOWN
} else {
	keys = Keys.PAGE_UP
}

//println('Viewport bottom is ' + bottom)

while(y > bottom || y < top) {
	WebUI.sendKeys(findTestObject(null), Keys.chord(keys))
	
	top = WebUI.getViewportTopPosition()
	
//	println('Viewport top is ' + top)
	
	height = WebUI.getViewportHeight()
	
	bottom = top + height
	
//	println('Viewport bottom is ' + bottom)

}
