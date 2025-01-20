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
import javax.swing.*;

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

// Set output file
testName = 'Education Candidate Preferences Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varPositions, varRegions]

//xpath of the Positions group
positions = '//input[@id=\'profile_group-1449972047.293_preferred_education_positions\']'

//xpath of the Regions group
regions = '//input[@id=\'profile_group-1449972047.293_world_region_preferences\']'

xpaths = [positions, regions]
///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Tabs/Preferences/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy']

// Define the expected tooltip texts
tooltipText = []

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Preferred School Positions') : 'The preferred education positions field is required.',
('Preferred Region(s)') : 'The world region preferences field is required.']

//Go to the Preferences tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Preferences'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Availability Tab'], FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

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

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

click('Education Candidate Profile/Tabs/Preferences/input_Submit')
      

def testFieldMessages(def fieldList) {
	for (def field : fieldList) {
		errorMsg = requiredFieldMsgs.get(field)

		msg = WebUI.verifyTextPresent(errorMsg, false, FailureHandling.OPTIONAL)

		println((field + ':') + msg)

		if (!(msg)) {
			outText = (((('The expected error message "' + errorMsg) + '" for field ') + field) + ' was not found.')

			println(outText)

			outFile.append(outText + '\n')
		}
	}
	
	WebUI.delay(GlobalVariable.fieldTestDelay)
}


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
