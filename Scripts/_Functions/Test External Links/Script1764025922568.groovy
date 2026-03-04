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
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Modified to close the tab if the 'Oops' page is displayed

KeywordLogger log = new KeywordLogger()

if(GlobalVariable.fastPath) {
	return
}

outFile = GlobalVariable.outFile

pageLinks = varPageLinks
println(pageLinks)

objectPath = varObjectPath
println(objectPath)

callingTab = varCallingTab
println(callingTab)

testType = varTestType
println(testType)

myWindowIndex = WebUI.getWindowIndex()

WebDriver driver = DriverFactory.getWebDriver()

windowHandles = driver.getWindowHandles().toArray()

startWindowCount = windowHandles.size()


// Click the other hyperlinks and verify pages opened
//pageLinks.each {
for(it in pageLinks) {
	myElement = ('a_' + it.key)

	myText = it.value
	println(myText)

	object = objectPath + myElement
	println(object)

	fromURL = WebUI.getUrl()
	
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)
	
	toURL = WebUI.getUrl()
	
	windowHandles = driver.getWindowHandles().toArray()
	
	windowCount = windowHandles.size()
	
	if(windowCount > startWindowCount) {
		
		WebUI.switchToWindowIndex(windowCount - 1)
		
	}
	
	textFound = false
	
	if(windowCount == startWindowCount && toURL == fromURL) {
		
		outText = '----- Clicking on the ' + it.key + ' link did not open a new page or tab. The link does not work.'
		
		println(outText)

		outFile.append(outText + '\n')
		log.logFailed(outText)
		
		GlobalVariable.testCaseErrorFlag = true
		
//		KeywordUtil.markError('\n' + outText)
		
//		textFound = false
		
	} else {
		
		WebUI.waitForPageLoad(30)
	
		WebUI.delay(2)
		
		if(testType != 'URL') {
		
			textFound = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)
			
		} else {
			url = WebUI.getUrl()
			[println(url)]
			
			if(url.contains(myText)) {
				
				textFound = true
			}
		}
	}
	
	if (textFound) {
		outText = (((('+++++ The text "' + myText) + '" was found after clicking on the ') + it.key) + ' link')

		println(outText)

		outFile.append(outText + '\n')
	} else {
		outText = (((('----- The text "' + myText) + '" was NOT found after clicking on the ') + it.key) + ' link')

		println(outText)

		outFile.append(outText + '\n')
		log.logFailed(outText)
		
		GlobalVariable.testCaseErrorFlag = true
		
//		KeywordUtil.markError('\n' + outText)
	}
	
	if(windowCount > startWindowCount) {

		WebUI.closeWindowIndex(windowCount - 1)
	
		WebUI.delay(1)
	
		WebUI.switchToWindowIndex(startWindowCount - 1)
	
		WebUI.delay(1)
		
	} else {
		
		outText = '----- Clicking on the ' + it.key + ' link did not open a separate tab.'

		println(outText)

		outFile.append(outText + '\n')

		GlobalVariable.testCaseErrorFlag = true
		
//		KeywordUtil.markError('\n' + outText)
		WebUI.back()
		
		WebUI.waitForPageLoad(10)
		WebUI.delay(1)
		
		textFound = WebUI.verifyTextPresent('Resubmission', false, FailureHandling.OPTIONAL)
		
		if (textFound) {
		
			driver.navigate().refresh();
		
			WebUI.waitForPageLoad(10)
			WebUI.delay(1)
		}
		
		if(callingTab != null) {
			object = callingTab
			
			println(object)
			
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)
			WebUI.waitForPageLoad(10)
			WebUI.delay(1)
			
		}
	}
}

