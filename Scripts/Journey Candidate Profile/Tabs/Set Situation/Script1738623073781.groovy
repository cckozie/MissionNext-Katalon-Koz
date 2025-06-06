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

if(username[-3..-1] != '5jc') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

// Set output file
testName = 'Journey Candidate Situation Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varProcess_stage, varBible_training, varMissions_experience, varInterest_in_missionary_training, varChurch_affiliated, 
	 varJourney_guide]

// Define the xpath for the radio button groups
//xpath of the Process Stage group
process_stage = "//input[@name='profile[group-1445373964.535][process_stage]']"

//xpath of the Bible Training group
bible_training = "//input[@name='profile[group-1445373964.535][bible_training]']"
				 
//xpath of the Mission Experience group
mission_experience = "//input[@id='profile_group-1445373964.535_missions_exposure']"

//xpath of the interest_in_mission_training
interest_in_mission_training = "//input[@name='profile[group-1445373964.535][interest_in_missionary_training]']"

//xpath of the Church Affiliated group
church_affiliated = "//input[@name='profile[group-1445373964.535][affiliated_with_church]']"

//xpath of the Journey Guide group
journey_guide_option = "//input[@name='profile[group-1445373964.535][journey_guide_option]']"

//Log in as Journey candidate if not on dashboard page
url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (!(url) == (('https://Journey.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

xpaths = [process_stage, bible_training, mission_experience, interest_in_mission_training, church_affiliated, journey_guide_option]
///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Candidate Profile/Tabs/Situation/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Bible Training') : 'img_Bible Training_field-tooltip',
('Affiliated with a church') : 'img_Affiliated with a church_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Bible Training') : 'If you have attended Sunday School classes for years, select Informal Training. If you have attended or taught Bible classes, select Some Bible School Classes.If your preferred assignments do not require Bible training, you can select Not Applicable.',
('Affiliated with a church') : 'This would be a church with a staff member that can provide a reference, if requested. ']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Mission Experience') : 'The missions exposure field is required.']
//('Affiliated with a Church') : 'The affiliated with church field is required.'] //Message disappears after Submit button clicked

//Go to the Situation tab
WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Situation'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], FailureHandling.STOP_ON_FAILURE)

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

// Set the text boxes and dropdown lists
if (varDescribe_training != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Situation/textarea_Describe Bible Training'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varDescribe_training], FailureHandling.STOP_ON_FAILURE)
}

if (varPerspectives != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Situation/select_Perspectives'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varPerspectives], FailureHandling.STOP_ON_FAILURE)
}

if (varChurch_name != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Situation/input_Church Name'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varChurch_name], FailureHandling.STOP_ON_FAILURE)
}

if (varChurch_involvement != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Situation/select_Church Involvement'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varChurch_involvement], FailureHandling.STOP_ON_FAILURE)
}

//WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Candidate Profile/Tabs/a_Situation'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)

