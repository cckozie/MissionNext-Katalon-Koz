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
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp' && username != 'cktest02jp')  {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

//######################################################################################################
registerOnly = false //Set this flag to true if you do not want to complete the tabs

//######################################################################################################
//================================== Initialize ===============================================
// Get the domain and set the url

domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://journey.' + domain) + '/signup/organization')

// Write results to text file
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Journey Partner on ' + domain) + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Journey Partner on ' + GlobalVariable.domain) + '.\n')

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/journey partner application/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Journey Partner Profile/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('Organization') : 'img_Organization_field-tooltip',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('Key Contact Email') : 'img_Key Contact Email_field-tooltip',
('Tier Level') : 'img_Tier Level_field-tooltip',
('Mailing Address') : 'img_Mailing Address_field-tooltip',
('Organization City') : 'img_Organization City_field-tooltip',
('PostZip Code') : 'img_PostZip Code_field-tooltip',
('Description') : 'img_Description_field-tooltip',
('Website Address') : 'img_Website Address_field-tooltip',
('Mission Statement') : 'img_Mission Statement_field-tooltip',
('Board of Directors') : 'img_Board of Directors_field-tooltip',
('Statement of Faith') : 'img_Statement of Faith_field-tooltip',
('How did you hear about MissionNext') : 'img_How did you hear about MissionNext_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Organization') : 'Name of your Organization.(Shorten, if too long to fit in field)',
('Abbreviation') : 'Generally, the first initials of your organization name; this is used in some displays.',
('Key Contact Email') : 'Email address must be unique. Use another for a different MissionNext account.',
('Tier Level') : "Select Tier level based on size of your organization's Annual Revenue.",
('Mailing Address') : 'Organization mailing address is required',
('Organization City ') : 'Organization city field is required',
('Post/Zip Code') : 'Enter 00000 if not from the U.S. and post code is unknown.',
('Description') : 'Hint: Take a paragraph or two from your website. If copying & pasting, you must manually type in any special characters, i.e, quotation marks, apostrophe, ampersand, etc.',
('Website Address') : 'Must start with https://',
('Mission Statement') : 'Add web address from website where the mission statement is displayed.',
('Board of Directors') : 'Add web address from website where the Board of Directors is displayed.',
('Statement of Faith') : 'Add web address from website where the Statement of Faith is displayed.',
('How did you hear about MissionNext') : 'Please complete this field.  It helps us to know where we should focus our efforts.']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Key Contact Email') : 'Please enter a valid email address.',
('Organization') : 'The organization name field is required.',
('Key Contact First Name') : 'The First Name field is required.',
('Key Contact Last Name') : 'The last name field is required.',
('Key Contact Phone') : 'The key contact phone field is required.',
('Mailing Address') : 'The mailing address field is required.',
('Organization City') : 'The city field is required.',
('State') : 'The state field is required.',
('Post/Zip Code') : 'The post code field is required.',
('Description') : 'The description field is required.',
('Website Address') : 'The website address field is required.',
('Affiliated Organizations') : 'The memberships field is required.',
('Mission Statement') : 'The mission statement field is required.',
('Board of Directors') : 'The board of directors field is required.',
('Statement of Faith') : 'The statement of faith field is required.',
('References') : 'The references field is required.',
('Partnership Agreement') : 'The partner agreement field is required.',
('Terms and Conditions') : 'The terms and conditions field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Affiliated Christian Organizations') : 'Affiliated Organizations', ('Partnership Agreement') : 'Partnership Agreement'
    , ('Privacy Policy') : 'Privacy Policy', ('Terms and Conditions') : 'Terms and Conditions']


//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Register as a journey partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'],
	FailureHandling.STOP_ON_FAILURE)

/*
tooltipTextMap.each({
		println(it)
	})
*/
WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.OPTIONAL)
//System.exit(1)
// Click the other hyperlinks and verify pages opened
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : pageLinks, ('varObjectPath') : 'Object Repository/Journey Partner Profile/Register/'],
	FailureHandling.STOP_ON_FAILURE)

// Submit the form with all of the fields empty
object = 'Journey Partner Profile/Register/button_Sign up'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Test for username, email, and password required messages
fieldList = ['Username', 'Password', 'Key Contact Email']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.STOP_ON_FAILURE)

// Set username, password, and email and then test for the other missing data error messages
object = 'Journey Partner Profile/Register/input_Username'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.username],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Password'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.email],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for other field messages
WebUI.waitForPageLoad(10)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)


fieldList = ['Organization', 'Key Contact First Name', 'Key Contact Last Name', 'Key Contact Phone' ,'Mailing Address' , 
	'Organization City' , 'State' , 'Post/Zip Code' , 'Description' ,'Website Address' , 'Affiliated Organizations' ,
	'Board of Directors' ,'Statement of Faith' , 'References' , 'Partnership Agreement' ,'Terms and Conditions']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.STOP_ON_FAILURE)


object = 'Journey Partner Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object, ('varParm1') : GlobalVariable.password],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Partner Profile/Register/input_Organization'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.organization],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Abbreviation'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.abbreviation],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Title'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.title],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.first_name],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.last_name],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.email],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Phone'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.phone_number],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_Tier Level'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.tier], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/textarea_Mailing Address'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.address],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_City'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.city],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_State'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.state], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_PostZip Code'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.zip],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/textarea_Description'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.description],
	FailureHandling.STOP_ON_FAILURE)

website = ('https://' + GlobalVariable.domain)

object = 'Journey Partner Profile/Register/input_Website Address'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : website],
	FailureHandling.STOP_ON_FAILURE)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
	myObject = ('Journey Partner Profile/Register/checkbox_' + org)
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : myObject], FailureHandling.STOP_ON_FAILURE)
}

object = 'Journey Partner Profile/Register/input_Mission Statement'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.mission_statement],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Board of Directors'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.bod],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Statement of Faith'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.sof],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/textarea_References'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.references],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_Referral'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.hear_about], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/textarea_Referral or Other Comment'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.referral],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/checkbox_Partnership Agreement'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

// Delay, then test for the Approval Pending page
WebUI.waitForPageLoad(10)

pending = WebUI.verifyTextPresent('Approval Pending', false, FailureHandling.OPTIONAL)

if (pending) {
	outText = 'Approval Pending page was found.'

	println(outText)

	outFile.append(outText + '\n')
} else {
	outText = 'Failed to find the Approval Pending page.'

	println(outText)

	outFile.append(outText + '\n')

	KeywordUtil.markError('\n' + outText)
}

//================================== Wait for the approval pending email for the new journey partner =========
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
	, ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
	println(('Approval request email for ' + GlobalVariable.username) + ' was found')

	WebUI.closeBrowser()

	//================================== Grant access for the new journey partner ==================================
	WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

	//================================== Create a subscriptioon for the new journey partner ========================
	WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : GlobalVariable.username, ('varType') : 'Journey'
			, ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)
	WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Education'
		, ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)

}

/*
// Fill in the other fields and submit
object = 'Journey Partner Profile/Register/input_Password'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact First Name'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.first_name],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Key Contact Last Name'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.last_name],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Organization'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.organization],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/input_Abbreviation'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.abbreviation],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_World Region'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
		, ('varParm1') : GlobalVariable.world_region], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_Country'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
		, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/textarea_Description'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.description],
	FailureHandling.STOP_ON_FAILURE)

website = ('https://' + GlobalVariable.domain)

object = 'Journey Partner Profile/Register/input_Website Address'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : website],
	FailureHandling.STOP_ON_FAILURE)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
	myObject = ((testObjectFolder + 'checkbox_') + org)

	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : myObject], FailureHandling.STOP_ON_FAILURE)
}

object = 'Journey Partner Profile/Register/select_School Qualifications'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
		, ('varParm1') : GlobalVariable.meet_qualifications], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/select_How did you hear about MissionNext'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
		, ('varParm1') : GlobalVariable.learn_about], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Partner Profile/Register/textarea_Referral or Other Comment'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.other_comment],
	FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/checkbox_Partnership Agreement'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Partner Profile/Register/checkbox_Terms and Conditions'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(2)

object = 'Object Repository/Journey Partner Profile/Register/button_Sign up'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

// Delay, then test for the Approval Pending page
WebUI.waitForPageLoad(10)

pending = WebUI.verifyTextPresent('Approval Pending', false, FailureHandling.OPTIONAL)

if (pending) {
	outText = '\n+++ Approval Pending page was found.'

	println(outText)

	outFile.append(outText + '\n')
} else {
	outText = '\n--- Failed to find the Approval Pending page.'

	println(outText)

	outFile.append(outText + '\n')

	KeywordUtil.markError('\n' + outText)
}

//================================== Wait for the approval pending email for the new Journey partner =========
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
		, ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
	outText = (('Approval request email for ' + username) + ' was found')

	println(outText)

	outFile.append(outText)

	WebUI.closeBrowser()

	//================================== Grant access for the new Journey partner ==================================
	WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

	//================================== Create a subscriptioon for the new Journey partner ========================
	WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Journey'
			, ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)

	if (!(registerOnly)) {
		//================================== Complete the Journey Partner tabs ========================
		WebUI.callTestCase(findTestCase('Journey Partner Profile/Complete Journey Partner Profile'), [:], FailureHandling.STOP_ON_FAILURE)

		outText = '\nJourney Partner registration was successful.'

		println(outText)

		outFile.append(outText)
		
		WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}




// Submit the form with all of the fields empty
WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

// Test for username, password, and email required messages
WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Username message'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Password message'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Email message'))

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Journey Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

WebUI.waitForPageLoad(10)

// Test for first name, last name, and phone number required messages
WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_First name message'))

firstNameMsg = WebUI.getText(findTestObject('Journey Partner Profile/Register/div_First name message'))

println(firstNameMsg)

pos = firstNameMsg.indexOf('Fiirst')

if (pos >= 0) {
    outText = 'ERROR: "First" is misspelled as "Fiirst" in the first name required error message'

    outFile.append(outText + '\n')

    println(outText)

    KeywordUtil.markError('\n' + outText)
}

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Organization name message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_First name message'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Last name message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Phone message'))

//Define the fields that incorrectly referred to 'school' instead of 'organization'
fields = ['Address', 'City', 'Postal code']

for (def field : fields) {
    print('the field is ' + field)

    WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_' + field + ' message'))

    addressMessage = WebUI.getText(findTestObject('Object Repository/Journey Partner Profile/Register/div_' + field + ' message'))

    if (addressMessage.indexOf('school') >= 0) {
        outText = (('ERROR: The ' + field) + ' required error message references a school instead of an organization.')

        outFile.append(outText + '\n')

        println(outText)

        KeywordUtil.markError('\n' + outText)
    }
}

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_State message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Description message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Url message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Memberships message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_BOD message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_SoF message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_References message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Partner agreement message'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_Terms message'))

WebUI.setEncryptedText(findTestObject('Journey Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Object Repository/Journey Partner Profile/Register/input_Organization'), GlobalVariable.organization)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Abbreviation'), GlobalVariable.abbreviation)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Title'), GlobalVariable.title)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact First Name'), GlobalVariable.first_name)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Last Name'), GlobalVariable.last_name)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Phone'), GlobalVariable.phone_number)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Tier Level'), GlobalVariable.tier, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Mailing Address'), GlobalVariable.address)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_City'), GlobalVariable.city)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_State'), GlobalVariable.state, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_PostZip Code'), GlobalVariable.zip)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Country'), GlobalVariable.country, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Description'), GlobalVariable.description)

website = ('https://' + GlobalVariable.domain)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Website Address'), website)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = ('checkbox_' + org)

    WebUI.click(findTestObject('Journey Partner Profile/Register/' + myObject))
}

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Mission Statement'), GlobalVariable.mission_statement)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Board of Directors'), GlobalVariable.bod)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Statement of Faith'), GlobalVariable.sof)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_References'), GlobalVariable.references)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Referral'), GlobalVariable.hear_about, 
    false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Referral or Other Comment'), GlobalVariable.referral)

WebUI.click(findTestObject('Journey Partner Profile/Register/checkbox_Partnership Agreement'))

WebUI.click(findTestObject('Journey Partner Profile/Register/checkbox_Terms and Conditions'))

WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

WebUI.delay(1)

// Delay, then test for the Approval Pending page
WebUI.waitForPageLoad(10)

pending = WebUI.verifyTextPresent('Approval Pending', false, FailureHandling.OPTIONAL)

if (pending) {
    outText = 'Approval Pending page was found.'

    println(outText)

    outFile.append(outText + '\n')
} else {
    outText = 'Failed to find the Approval Pending page.'

    println(outText)

    outFile.append(outText + '\n')

    KeywordUtil.markError('\n' + outText)
}

//================================== Wait for the approval pending email for the new journey partner =========
//WebUI.callTestCase(findTestCase('_Functions/Wait for Email'), [('varSubjectKey') : 'Approval request', ('varUsername') : GlobalVariable.username],
//	FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'Chris.Kosieracki@missionnext.org'
        , ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
    println(('Approval request email for ' + GlobalVariable.username) + ' was found')

    WebUI.closeBrowser()

    //================================== Grant access for the new journey partner ==================================
    WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

    //================================== Create a subscriptioon for the new journey partner ========================
    WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : GlobalVariable.username, ('varType') : 'Journey'
            , ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)
}
*/
