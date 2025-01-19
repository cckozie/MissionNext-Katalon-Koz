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

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Candidate Contact Info Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/Contact Info Tab/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Tabs/Contact Info/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('First Name') : 'img_First Name_field-tooltip',
('Last Name') : 'img_Last Name_field-tooltip',
('Your Email') : 'img_Your Email_field-tooltip',
('State') : 'img_State_field-tooltip',
('Your Country of Citizenship') : 'img_Your Country of Citizenship_field-tooltip',
('Birth Year') : 'img_Birth Year_field-tooltip',
('Terms and Conditions') : 'img_Terms and Conditions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('First Name') : 'May include your middle initial; enter last name below.',
('Last Name') : 'Family Name',
('Your Email ') : 'Your primary email address',
('State    ') : 'List is country-specific. Selection is required.',
('Your Country of Citizenship') : 'If you hold dual citizenship, select the country where you normally reside. ',
('Birth Year') : 'Four Digit Year',
('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']

/*
// Define the required field error messages
requiredFieldMsgs = [
	('Gender') : 'The gender field is required.',
	('Country of Citizenship') : 'The citizenship country field is required.',
	('Birth Year') : 'The birth year field is required.']
*/
// Define the required field missing error message test objects
requiredFieldMsgs = [
('Gender') : 'The gender field is required.',
('Country of Citizenship') : 'The citizenship country field is required.',
('Birth Year') : 'The birth year field is required.']

//Go to the Contact Info tab
WebUI.click(findTestObject('Education Candidate Profile/Tabs/a_Contact Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Contact Info Tab'],
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
if (varGender == 'Male') {
    click('Object Repository/Education Candidate Profile/Tabs/Contact Info/radio_Male')
} else if (varGender == 'Female') {
    click('Object Repository/Education Candidate Profile/Tabs/Contact Info/radio_Female')
}

if (varCountry != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country', varCountry, false)
}

if (varCountry_of_Citizenship != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country_of_Citizenship', 
        varCountry_of_Citizenship, false)
}

if (varBirth_year != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Contact Info/input_Birth Year', varBirth_year)
}

if (varMarital_status != null) {
//    setText('Object Repository/Education Candidate Profile/Tabs/Contact Info/input_Birth Year', varBirth_year)
	WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Marital Status'), varMarital_status, false)
}


//Test terms and conditions linke
click('Object Repository/Education Candidate Profile/Tabs/Contact Info/a_Terms and Conditions')

WebUI.switchToWindowIndex(1)

WebUI.delay(1)

newUrl = WebUI.getUrl()

if (newUrl.indexOf('org/terms') < 0) {
	println('######## Failed to find Terms and Conditions page')
}

WebUI.delay(1)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(1)

click('Education Candidate Profile/Tabs/Contact Info/btn_Submit')

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

def clearText(def object) {
    scrollToObject(object)

    WebUI.clearText(findTestObject(object))
}

