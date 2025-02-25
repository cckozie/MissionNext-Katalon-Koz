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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
	println('The Execution Profile must be set to "Education Candidate"')

	System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Candidate Spouse Experience Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Tabs/Spouse Experience/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Spouse Degree Field') : 'img_Spouse Degree Field_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Spouse Degree Field') : 'Spouse Earned Degree Field']

// Define the required field missing error message test objects
requiredFieldMsgs = []

// Define the page's links and the text to search for on the linked page
pageLinks = [('Terms and Conditions') : 'Terms and Conditions']

//Go to the Spouse Experience tab
WebUI.click(findTestObject('Education Candidate Profile/Tabs/a_Spouse Experience'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder,
	('varTooltipTextMap') : tooltipTextMap], FailureHandling.STOP_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each {
	fieldList.add(it.key)
}

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Set the input fields provided
if (varSpouse_highest_degree_earned != null) {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Experience/select_Spouse Highest Degree Earned'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectByValue',
		('varObject') : object, ('varParm1') : varSpouse_highest_degree_earned], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_degree_field != null) {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Experience/input_Spouse Degree Field'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_degree_field], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_occupation != null) {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Experience/textarea_Spouse Occupation'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_occupation], FailureHandling.STOP_ON_FAILURE)
}

// Set the Spouse an Educator checkbox
if (varSpouse_an_educator == 'Yes') {
	WebDriver driver = DriverFactory.getWebDriver()
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Experience/input_Spouse an Educator'
	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
	myStatus = element.isSelected()
	if((varSpouse_an_educator == 'Yes' && !myStatus) || (varSpouse_an_educator == 'No' && myStatus)) {
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object, ('varParm1') : null],
			FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
	}
}

object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

