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
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

outFile = GlobalVariable.outFile

pageLinks = varPageLinks
println(pageLinks)

objectPath = varObjectPath
println(objectPath)

callingTab = varCallingTab
println(callingTab)

WebDriver driver = DriverFactory.getWebDriver()

// Click the other hyperlinks and verify pages opened
//pageLinks.each {
for(it in pageLinks) {
	myElement = ('a_' + it.key)

	myText = it.value

//		object = 'Object Repository/Education Partner Profile/Register/' + myElement
	object = objectPath + myElement
	println(object)
	WebUI.click(findTestObject(object))
//	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	windowHandles = driver.getWindowHandles().toArray()
	
	windowCount = windowHandles.size()
	
	if(windowCount == 2) {
		
		WebUI.switchToWindowIndex(1)
	
		WebUI.delay(1)
	}
	
	textFound = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)

	if (textFound) {
		outText = (((('+++++ The text "' + myText) + '" was found after clicking on the ') + it.key) + ' link')

		println(outText)

		outFile.append(outText + '\n')
	} else {
		outText = (((('----- The text "' + myText) + '" was NOT found after clicking on the ') + it.key) + ' link')

		println(outText)

		outFile.append(outText + '\n')

		KeywordUtil.markError('\n' + outText)
	}
	
	if(windowCount == 2) {

		WebUI.closeWindowIndex(1)
	
		WebUI.delay(1)
	
		WebUI.switchToWindowIndex(0)
	
		WebUI.delay(1)
		
	} else {
		
		WebUI.back()
		
		WebUI.waitForPageLoad(10)
		
		driver.navigate().refresh();
		
		WebUI.waitForPageLoad(10)
		
		if(callingTab != null) {
			
			object = callingTab
			
			WebUI.click(findTestObject(object))
//			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			WebUI.waitForPageLoad(10)
		
		}
	}
}

