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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp' && username != 'cktest02jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Journey Partner Contact Info Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey partner Profile/Tabs/Contact Info/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Organization') : 'img_Organization_field-tooltip',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('PostZip Code') : 'img_PostZip Code_field-tooltip',
('Key Contact Email') : 'img_Key Contact Email_field-tooltip',
('Website Address') : 'img_Website Address_field-tooltip',
('Description') : 'img_Description_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Organization') : 'Name of your Organization.(Shorten, if too long to fit in field)',
('Abbreviation') : 'Generally, the first initials of your organization name; this is used in some displays.',
('Post/Zip Code') : 'Enter 00000 if not from the U.S. and post code is unknown.',
('Key Contact Email') : 'Email address must be unique. Use another for a different MissionNext account.',
('Website Address') : 'Must start with https://',
('Description') : 'Hint: Take a paragraph or two from your website. If copying & pasting, you must manually type in any special characters, i.e, quotation marks, apostrophe, ampersand, etc.']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Organization Phone') : 'The agency phone field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [:]

//Go to the Contact Info tab
WebUI.click(findTestObject('Journey Partner Profile/Tabs/a_Contact Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

//Enter the contact info fields
if (varKey_contact_phone != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/input_Key Contact Phone'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varKey_contact_phone], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varOrganization_phone != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/input_Organization Phone'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varOrganization_phone], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varOrganization_city != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/input_Organization City'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varOrganization_city], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varProvince_state_region != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/select_State'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
            , ('varParm1') : varProvince_state_region], FailureHandling.STOP_ON_FAILURE)
}


if (varProvince_state_region != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/input_ProvinceStateRegion'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varProvince_state_region], FailureHandling.STOP_ON_FAILURE)
}

if (varCountry != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Contact Info/select_Country'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
            , ('varParm1') : varCountry], FailureHandling.STOP_ON_FAILURE)
}

// Test the external page links
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks'):pageLinks,
	('varObjectPath') : 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/'], FailureHandling.CONTINUE_ON_FAILURE)

object = 'Journey Partner Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object
	, ('varParm1') : null], FailureHandling.STOP_ON_FAILURE)

