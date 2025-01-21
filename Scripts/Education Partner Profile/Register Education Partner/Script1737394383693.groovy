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
import java.awt.Desktop as Desktop
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.*
import org.apache.commons.lang.WordUtils as WordUtils
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

//######################################################################################################
registerOnly = false //Set this flag to true if you do not want to complete the tabs
//######################################################################################################

//================================== Initialize ===============================================
// Get the domain and set the url
domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://education.' + domain) + '/signup/organization')

// Specify file to contain test case results, update the global variable, and write the first line of text
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Partner on ' + domain) + 
'.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Education Partner on ' + domain) + '.\n')

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Education Partner Profile/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('Key Contact Email') : 'img_Key Contact Email_field-tooltip',
('Organization') : 'img_Organization_field-tooltip',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('Description') : 'img_Description_field-tooltip',
('Website Address') : 'img_Website Address_field-tooltip',
('How did you hear about MissionNext') : 'img_How did you hear about MissionNext_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Key Contact Email') : 'Email address must be unique. Use another for a different MissionNext account.',
('Organization') : 'Name of your Organization.(Shorten, if too long to fit in field)',
('Abbreviation') : 'Generally, the first initials of your organization name; this is used in some displays.',
('Description') : 'Hint: Take a paragraph or two from your website. If copying & pasting, you must manually type in any special characters, i.e, quotation marks, apostrophe, ampersand, etc.',
('Website Address') : 'Must start with https://',
('How did you hear about MissionNext') : 'Please complete this field.  It helps us to know where we should focus our efforts.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Affiliated Organizations') : 'Affiliated Organizations', ('countries by region') : 'Countries by Region', ('Partnership Agreement') : 'Partnership Agreement'
    , ('Privacy Policy') : 'Privacy Policy', ('School Qualifications') : 'SCHOOL QUALIFICATIONS', ('Terms and Conditions') : 'Terms and Conditions']

// Define the field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Key Contact Email') : 'Please enter a valid email address.',
('Key Contact First Name') : 'The First Name field is required.',
('Key Contact Last Name') : 'The last name field is required.',
('Organization') : 'The organization name field is required.',
('Description') : 'The description field is required.',
('Website Address') : 'The website address field is required.',
('Meet Christian School Qualifications') : 'The meet qualifications for christian field is required.',
('Partnership Agreement') : 'The partner agreement field is required.',
('Terms and Conditions') : 'The terms and conditions field is required.']

//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Create the education partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'],
	FailureHandling.STOP_ON_FAILURE)

tooltipTextMap.each {
	println(it)
}

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)
/*
// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.STOP_ON_FAILURE)
*/
// Click on the Customer Support link and use Sikulix to verify the opening of an email addressed to us
Screen s = new Screen()

object = 'Object Repository/Education Partner Profile/Register/a_Customer Support'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)
//Start robot
Robot robot = new Robot()

robot.keyPress(KeyEvent.VK_META) //META is the Mac Command key

robot.keyPress(KeyEvent.VK_W)

robot.keyRelease(KeyEvent.VK_W)

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(2)

myImage = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/Email Customer Support.png'

f = new File(myImage)

println(f)

if (f.exists()) {
    println('Looking for ' + myImage)

    Pattern icn = new Pattern(myImage).similar(0.10)

    found = s.exists(icn)

    println(found)

    if (found != null) {
		
        foundStr = found.toString()

        matchP = foundStr.indexOf('%')

        println(matchP)

        pct = foundStr.substring(matchP + 1, matchP + 6)

		outText = ('+++ Image match for email to customer support is ' + pct + '%')
		
		pctF = pct.toFloat()
		
		min = 80
		
		if(pct.toFloat() < min.toFloat()) {
			outText = outText + ' <<<<'
		}	
		
    } else {
        outText = '--- Unable to find image for email to customer support'
    }
	
	println(outText)
	
	outFile.append(outText + '\n')
	
} else {
	
    outText = ('Unable to find the image file ' + myImage)

    outFile.append(outText + '\n')

    println(outText)

    KeywordUtil.markError('\n' + outText)
}

// Close the email window and app

WebUI.delay(1)

//Take the Don't Save option
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_ESCAPE)

robot.keyRelease(KeyEvent.VK_ESCAPE)

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(1)

//Quit the app
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_Q)

robot.keyRelease(KeyEvent.VK_Q)

robot.keyRelease(KeyEvent.VK_META)

// Click the other hyperlinks and verify pages opened
pageLinks.each({ 
        myElement = ('a_' + it.key)

        myText = it.value

		object = 'Object Repository/Education Partner Profile/Register/' + myElement
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
		
        WebUI.switchToWindowIndex(1)

        WebUI.delay(1)

        textFound = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)

        if (textFound) {
            outText = (((('+++++ The text "' + myText) + '" was found after clicking on the ') + it.key) + ' link')

            println(outText)

            outFile.append(outText + '\n')
        } else {
            outText = (((('----- The text "' + myText) + '" was NOT found after clicking on the ') + it.key) + ' link')

            println(outText)

            outFile.append(outText + '\n')

            KeywordUtil.markError('\n' + outText)
        }
        
        WebUI.closeWindowIndex(1)

        WebUI.delay(1)

        WebUI.switchToWindowIndex(0)

        WebUI.delay(1)
    })


// Submit the form with all of the fields empty
object = 'Education Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Test for username, email, and password required messages
fieldList = ['Username', 'Password', 'Key Contact Email']

outText = 'Verifying the field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

   // Set username, password, and email and then test for the other missing data error messages
object = 'Education Partner Profile/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncrytedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Key Contact Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.email], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for other field messages
WebUI.waitForPageLoad(10)
fieldList = [
'Key Contact First Name',
'Key Contact Last Name',
'Organization',
'Description',
'Website Address',
'Meet Christian School Qualifications',
'Partnership Agreement',
'Terms and Conditions']

outText = 'Verifying the field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)


// Fill in the other fields and submit
object = 'Education Partner Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncrytedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Key Contact First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.first_name], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Key Contact Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Organization'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.organization], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/input_Abbreviation'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.abbreviation], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/select_World Region'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.world_region], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/textarea_Description'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.description], FailureHandling.STOP_ON_FAILURE)

website = ('https://' + GlobalVariable.domain)

object = 'Education Partner Profile/Register/input_Website Address'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : website], FailureHandling.STOP_ON_FAILURE)



orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = (testObjectFolder + 'checkbox_' + org)

	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : myObject], FailureHandling.STOP_ON_FAILURE)
}

object = 'Education Partner Profile/Register/select_School Qualifications'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.meet_qualifications], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/select_How did you hear about MissionNext'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.learn_about], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Partner Profile/Register/textarea_Referral or Other Comment'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.other_comment], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/checkbox_Partnership Agreement'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Education Partner Profile/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(2)

object = 'Object Repository/Education Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

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

//================================== Wait for the approval pending email for the new education partner =========
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
        , ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
    println(('Approval request email for ' + username) + ' was found')

    WebUI.closeBrowser()

    //================================== Grant access for the new education partner ==================================
    WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

    //================================== Create a subscriptioon for the new education partner ========================
    WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Education'
            , ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)
	
    //================================== Complete the Education Partner tabs ========================
	WebUI.callTestCase(findTestCase('Education Partner Profile/Complete Education Partner Profile'), [:], FailureHandling.STOP_ON_FAILURE)
	
	
}
