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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Partner Positions Needed Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varAvailable_positions, varExperience_preferred]

//xpath of the Positions Available group (div?)
positions = '//input[@id=\'profile_group-1456436124.683_educational_positions\']'

//xpath of the Positions Available group (div?)
experiences = "//input[@id='profile_group-1456436124.683_teacher_experience_preferred']"

xpaths = [positions, experiences]

// Define path to tooltip text images
//tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/tabs/positions needed/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Education partner Profile/Tabs/Positions Needed/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy']

// Define the expected tooltip texts
tooltipText = []

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Available Positions') : 'The educational positions field is required.',
('Experience Preferred') : 'The teacher experience preferred field is required.']

//Go to the Admin Info tab
WebUI.click(findTestObject('Education Partner Profile/Tabs/a_Positions Needed'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script (THERE ARE NONE)
/*
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.OPTIONAL)
*/

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as education partner if not on dashboard page
if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Positions Needed'))

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({
		fieldList.add(it.key)
	})

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.STOP_ON_FAILURE)

if(!GlobalVariable.fastPath) {
	WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)
} else {
	object = 'Education Partner Profile/Tabs/Positions Needed/checkbox_Administrator'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

	object = 'Education Partner Profile/Tabs/Positions Needed/checkbox_English'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

if (varAvailable_other_positions != null) {
	object = 'Object Repository/Education Partner Profile/Tabs/Positions Needed/textarea_Other Available Positions'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varAvailable_other_positions], FailureHandling.STOP_ON_FAILURE)
}

if (varOther_experience_comment != null) {
	object = 'Object Repository/Education Partner Profile/Tabs/Positions Needed/textarea_Other Experience Comment'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varOther_experience_comment], FailureHandling.STOP_ON_FAILURE)
}



object = 'Object Repository/Education Partner Profile/Tabs/Positions Needed/button_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

