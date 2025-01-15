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
import groovy.console.ui.SystemOutputInterceptor as SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Candidate"')

    System.exit(0)
}

//######################################################################################################
registerOnly = true //Set this flag to true if you do not want to complete the tabs
fieldTestDelay = 2
//######################################################################################################
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	Need to add tests for the tooltips on all of the tabs. 
//  Consider using a called script to test all tooltips.
//  Write all failures to the output file
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://education.' + domain) + '/signup/candidate')

// Write results to text file
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Candidate on ' + domain) + 
'.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Education Candidate on ' + domain) + '.\n')

//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder with the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Education Register/')

// Define the names of the tooltip fields and the unique part of the related test object
// (header is a dummy because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [('header') : 'h1_Header', ('username') : 'img_Username_field-tooltip', ('email') : 'img_Email_field-tooltip'
    , ('password') : 'img_Password_field-tooltip', ('first_name') : 'img_First Name_field-tooltip', ('last_name') : 'img_Last Name_field-tooltip'
    , ('learn_about') : 'img_Learn About Us_field-tooltip', ('terms_conditions') : 'img_Terms and Conditions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Email') : 'Your primary email address and must be unique in our database.', ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('First Name') : 'May include your middle initial; enter last name below.', ('Last Name') : 'Family Name', ('How did you learn about us?') : 'It is helpful to know how people are learning about us.'
    , ('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('Email') : 'Please enter a valid email address.', ('First Name') : 'The First Name field is required.', ('Last Name') : 'The last name field is required.'
    , ('Country') : 'The country field is required.', ('Phone Number') : 'The phone number field is required.', ('Terms and Conditions') : 'The terms and conditions field is required.']

//================================== Create the education partner ==================================
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'], 
    FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Call the tooltip testing script
outText = 'Verifying the tooltips can be displayed.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder], FailureHandling.STOP_ON_FAILURE)
System.exit(0)
// Verify the tooltip text found in the call to Get Screenshot and Tooltip Text against what we expected in tooltipText[]
outText = 'Verifying the tooltip text.\n'

outFile.append(outText)

for (def it : tooltipText) {
	myKey = it.key

	myText = it.value

	actualText = tooltipTextMap.get(myKey)

	println((myKey + ':') + actualText)

	if (actualText != myText) {
		outText = (((((('####### ERROR: The tooltip text for ' + myKey) + ' should be ') + myText) + ' but instead is ') +
		actualText) + '.')

		println(outText)

		outFile.append(outText + '\n')
	}
}

outText = 'Verifying the required field message.\n'

outFile.append(outText)

// Submit the empty page
click('Education Candidate Profile/Education Register/button_Sign up')

// Test for username, email, and password required messages
fieldList = ['Username', 'Email', 'Password']

testFieldMessages(fieldList)

setText('Education Candidate Profile/Education Register/input_Username', GlobalVariable.username)

setText('Education Candidate Profile/Education Register/input_Email', GlobalVariable.email)

setEncryptedText('Education Candidate Profile/Education Register/input_Password', GlobalVariable.password)

click('Education Candidate Profile/Education Register/button_Sign up')

// Test for first name, last name, and phone number required messages
fieldList = ['First Name', 'Last Name', 'Country', 'Phone Number', 'Terms and Conditions']

testFieldMessages(fieldList)

//Enter the password, first and last names, country, and phone number, and optional fields except Terms and Conditions
setEncryptedText('Education Candidate Profile/Education Register/input_Password', GlobalVariable.password)

setText('Education Candidate Profile/Education Register/input_First Name', GlobalVariable.username)

setText('Education Candidate Profile/Education Register/input_Last Name', GlobalVariable.last_name)

selectOptionByLabel('Education Candidate Profile/Education Register/select_Country', GlobalVariable.country, false)

setText('Education Candidate Profile/Education Register/input_Best Phone Number', GlobalVariable.phone_number)

if (GlobalVariable.prefer_text) {
    click('Education Candidate Profile/Education Register/checkbox_Prefer Text Message')
}

selectOptionByLabel('Education Candidate Profile/Education Register/select_Learn About Us', GlobalVariable.learn_about, 
    false)

setText('Education Candidate Profile/Education Register/textarea_Other Comment', GlobalVariable.other_comment)

click('Education Candidate Profile/Education Register/button_Sign up')

// Test for terms and conditions required messages
fieldList = ['Terms and Conditions']

testFieldMessages(fieldList)

outText = 'Verifying the links to other pages.\n'

outFile.append(outText)


//Test links to Privacy Policy and Terms and Conditions pages
click('Object Repository/Education Candidate Profile/Education Register/a_Privacy Policy')

WebUI.switchToWindowIndex(1)

WebUI.delay(1)

newUrl = WebUI.getUrl()

if (newUrl.indexOf('org/privacy') < 0) {
    println('######## Failed to find Privacy page')
}

WebUI.delay(1)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

click('Object Repository/Education Candidate Profile/Education Register/a_Terms and Conditions')

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

outText = 'Submitting the finished page.\n'

outFile.append(outText)


//Complete and submit the registration
setEncryptedText('Education Candidate Profile/Education Register/input_Password', GlobalVariable.password)

click('Object Repository/Education Candidate Profile/Education Register/checkbox_Terms and Conditions')

click('Object Repository/Education Candidate Profile/Education Register/button_Sign up')

if (!(registerOnly)) {
    WebUI.callTestCase(findTestCase('Education Candidate Profile/Complete Education Candidate Profile'), [('varCalled') : true], 
        FailureHandling.STOP_ON_FAILURE)

    WebUI.verifyTextPresent('Thank you for submitting your profile on MissionNext Education!', false)
}

WebUI.closeBrowser()

WebUI.acceptAlert()

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
	
	WebUI.delay(fieldTestDelay)
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

