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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

username = GlobalVariable.username

domain = GlobalVariable.domain

// Ensure that we are using the correct execution profile
if(username[-3..-1] != '7jp') {
	println('The Profile must be set to "Journey Partner"')

	System.exit(0)
}

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varRecruit_from_countries]

//xpath of the Recruiting Countries group
recruit_from_countries = "//input[@id='profile_group-1448248787.895_recruit_countries']"

xpaths = [recruit_from_countries]

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Partner Profile/Tabs/Recruiting Countries/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Recruit From Countries') : 'img_Recruit From Countries_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Recruit From Countries') : "Select countries from which your organization can recruit. The matching scheme will require a 'match' with the candidate's country of citizenship unless All is checked."]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Recruit From Countries') : 'The recruit countries field is required.']

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as Journey Partner if not on dashboard page
if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Recruiting Countries'))
}

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Recruiting Countries'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

if(tooltipTextMap.size() != tooltipText.size()) {
	outText = '----- There were ' + tooltipText.size() + ' tooltips expected, but ' + tooltipTextMap.size() + ' were found.'
	GlobalVariable.testCaseErrorFlag = true
} else {
	outText = 'There were ' + tooltipTextMap.size() + ' tooltips found as expected.'
}

println(outText)

outFile.append(outText + '\n')

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
fieldList = []

requiredFieldMsgs.each({
		fieldList.add(it.key)
	})

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.CONTINUE_ON_FAILURE)

if(!GlobalVariable.fastPath) {
	WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)
} else {
	object = 'Journey Partner Profile/Tabs/Recruiting Countries/input_United States'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

	object = 'Journey Partner Profile/Tabs/Recruiting Countries/input_Brazil'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

object = 'Object Repository/Journey Partner Profile/Tabs//btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
// The tab may not be found since this is typically the last tab. In that case, test for the Thank You page
WebUI.waitForPageLoad(10)

profile = WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Tabs/a_Contact Info'), FailureHandling.OPTIONAL)

if (profile) {
	myClass = WebUI.getAttribute(findTestObject('Journey Partner Profile/Tabs/a_Recruiting Countries'), 'class', FailureHandling.OPTIONAL)
	if(!myClass.contains('error')) {
		outText = testName + ' was successfully completed.\n'
	} else {
		outText = 'Unable to successfully complete ' + testName + '.\n'
		KeywordUtil.markError(outText)
	}
} else {
	myURL = WebUI.getUrl()
	
	if(myURL.contains('https://journey.missionnext.org/dashboard')) {
		outText = (testName + ' was successfully completed.\n')
	} else {
		outText = (('Unable to successfully complete ' + testName) + '.\n')

		KeywordUtil.markError(outText)
		
		GlobalVariable.testCaseErrorFlag = true
	}
}

println(outText)
outFile.append(outText)
