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
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varProcess_stage, varBible_training, varChurch_affiliated, varJourney_guide]

// Define the xpath for the radio button groups
//xpath of the Process Stage group
process_stage = '//input[@name=\'profile[group-1449794128.145][process_stage]\']'

//xpath of the Bible Training group
bible_training = '//input[@name=\'profile[group-1449794128.145][bible_training]\']'

//xpath of the Church Affiliated group
church_affiliated = '//input[@name=\'profile[group-1449794128.145][affiliated_with_church]\']'

//xpath of the Journey Guide group
journey_guide = '//input[@name=\'profile[group-1449794128.145][journey_guide_option]\']'

//Log in as education candidate if not on dashboard page
url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

xpaths = [process_stage, bible_training, church_affiliated, journey_guide]

//Go to the Situation tab
click('Object Repository/Education Candidate Profile/Tabs/a_Situation')

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [('varExtension') : 'Situation Tab'], FailureHandling.STOP_ON_FAILURE)

// Set the text boxes and dropdown lists
if (varPerspectives != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Situation/select_Perspectives', varPerspectives, false)
}

if (varDescribe_training != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Situation/textarea_Describe Bible Training', varDescribe_training)
}

if (varChurch_name != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Situation/input_Church Name', varChurch_name)
}

if (varChurch_involvement != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Situation/select_Church Involvement', 
        varChurch_involvement, false)
}

//WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

click('Education Candidate Profile/Tabs/Situation/btn_Complete Submit')


def scrollToObject(def object) {
	println(('Converting ' + object) + ' to web element')

	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)

	loc = element.getLocation()

	y = loc.getY()

	println('Y location is ' + y)

	top = WebUI.getViewportTopPosition()

	println('Viewport top is ' + top)

	bottom = (top + 600)

	if (((y - top) < 150) || ((bottom - y) < 10)) {
		WebUI.scrollToPosition(0, y - 150)

		WebUI.delay(1)
	}
}

def click(def object) {
	scrollToObject(object)

	WebUI.click(findTestObject(object))
}

def getText(def object) {
	scrollToObject(object)

	value = WebUI.getText(findTestObject(object))

	return value
}

def setText(def object, def value) {
	scrollToObject(object)

	WebUI.setText(findTestObject(object), value)
}

def setEncryptedText(def object, def value) {
	scrollToObject(object)

	WebUI.setEncryptedText(findTestObject(object), value)
}

def selectOptionByValue(def object, def value, def flag) {
	scrollToObject(object)

	WebUI.selectOptionByValue(findTestObject(object), value, flag)
}

def selectOptionByLabel(def object, def label, def flag) {
	scrollToObject(object)

	WebUI.selectOptionByValue(findTestObject(object), label, flag)
}

def clearText(object) {
	scrollToObject(object)

	WebUI.clearText(findTestObject(object))
}





