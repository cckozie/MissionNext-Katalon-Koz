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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '7jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Journey Partner Ministry Prefs Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varMinistry_preferences]
println(parms)

//xpath of the Ministry Preferences group
ministry_preferences = "//input[@id='profile_group-1446522088.64_ministry_preferences']"

xpaths = [ministry_preferences]

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Partner Profile/Tabs/ Ministry Prefs/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = []

// Define the expected tooltip texts
tooltipText = []

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Ministry Preferences') : 'The ministry preferences field is required.']

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Ministry Prefs'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.CONTINUE_ON_FAILURE)

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as Journey Partner if not on dashboard page
if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({
		fieldList.add(it.key)
	})

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.CONTINUE_ON_FAILURE)

// Set the Need Specific IT Positions checkbox
if (varNeed_specific_it_positions == 'Yes') {
	WebDriver driver = DriverFactory.getWebDriver()
	object = 'Object Repository/Journey Partner Profile/Tabs/Ministry Prefs/input_Need Specific IT Positions'
	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
	myStatus = element.isSelected()
	if((varNeed_specific_it_positions == 'Yes' && !myStatus) || (varNeed_specific_it_positions == 'No' && myStatus)) {
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object, ('varParm1') : null],
			FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
	}
}

if(!GlobalVariable.fastPath) {
	WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)
} else {
	object = 'Journey Partner Profile/Tabs/Ministry Prefs/input_Bible Teaching'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

	object = 'Journey Partner Profile/Tabs/Ministry Prefs/input_Bible Translation'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

if (varOther_people_group != null) {
	object = 'Object Repository/Journey Partner Profile/Tabs/Ministry Prefs/textarea_Other People Group'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varOther_people_group], FailureHandling.STOP_ON_FAILURE)
}


WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs//btn_Complete Submit'))

