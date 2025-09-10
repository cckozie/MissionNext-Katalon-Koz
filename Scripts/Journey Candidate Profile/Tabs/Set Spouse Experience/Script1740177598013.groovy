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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

domain = GlobalVariable.domain

if(username[-3..-1] != '5jc') {
	println('The Execution Profile must be set to "Journey Candidate"')

	System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')


// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varSpouse_bible_training, varSpouse_missions_experience]

// Define the xpath for the radio button groups
//xpath of the Bible Training group
bible_training = "//input[@name='profile[group-1623988190.243][spouse_bible_training]']"
				
//xpath of the Mission Experience group
mission_experience = "//input[@id='profile_group-1623988190.243_spouse_missions_exposure']"

xpaths = [bible_training, mission_experience]

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Journey candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Candidate Profile/Tabs/Spouse Experience/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Spouse Degree Field') : 'img_Spouse Degree Field_field-tooltip',
('Spouse Bible Training') : 'img_Spouse Bible Training_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Spouse Degree Field') : 'Spouse Earned Degree Field',
('Spouse Bible Training') : 'If you have attended Sunday School classes for years, select Informal Training. If you have attended or taught Bible classes, select Some Bible School Classes.If your preferred assignments do not require Bible training, you can select Not Applicable.']

// Define the required field missing error message test objects
requiredFieldMsgs = []

// Define the page's links and the text to search for on the linked page
pageLinks = []

//Go to the Spouse Experience tab
WebUI.click(findTestObject('Journey Candidate Profile/Tabs/a_Spouse Experience'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder,
	('varTooltipTextMap') : tooltipTextMap], FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each {
	fieldList.add(it.key)
}

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

// Set the input fields provided
if (varSpouse_highest_degree_earned != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/select_Spouse Highest Degree Earned'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varSpouse_highest_degree_earned], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_degree_field != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/input_Spouse Degree Field'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_degree_field], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_occupation != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/textarea_Spouse Occupation'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_occupation], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_describe_bible_training != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/textarea_Spouse Describe Bible Training'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_describe_bible_training], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_cross_cultural_experience != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/select_Spouse Cross-Cultural Experience'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varSpouse_cross_cultural_experience], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_attended_perspectives != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/select_Spouse Attended Perspectives'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varSpouse_attended_perspectives], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_experience != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Spouse Experience/textarea_Spouse Experience'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_experience], FailureHandling.STOP_ON_FAILURE)
}

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Candidate Profile/Tabs/a_Spouse Experience'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)
