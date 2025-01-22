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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Partner Contact Info Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
//tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/tabs/contact info/'
// Define the folder where the tooltip test objects live
testObjectFolder = 'Education partner Profile/Tabs/Contact Info/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [('dummy') : 'dummy', ('Organization') : 'img_Organization_field-tooltip', ('Abbreviation') : 'img_Abbreviation_field-tooltip'
    , ('Key Contact Email') : 'img_Key Contact Email_field-tooltip', ('Mailing Address') : 'img_Mailing Address_field-tooltip'
    , ('Organization City') : 'img_Organization City_field-tooltip', ('Post_Zip Code') : 'img_PostZip Code_field-tooltip']

// Define the expected tooltip texts
tooltipText = [('Organization') : 'Name of your Organization.(Shorten, if too long to fit in field)', ('Abbreviation') : 'Generally, the first initials of your organization name; this is used in some displays.'
    , ('Key Contact Email') : 'Email address must be unique. Use another for a different MissionNext account.', ('Mailing Address') : 'Organization mailing address is required'
    , ('Organization City ') : 'Organization city field is required', ('Post_Zip Code') : 'Enter 00000 if not from the U.S. and post code is unknown.']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Key Contact Phone') : 'The key contact phone field is required.', ('Organization') : 'The school phone field is required.'
    , ('Organization City') : 'The school city field is required.', ('Province/State/Region') : 'The province state region field is required.']

//Go to the Contact Info tab
WebUI.click(findTestObject('Education Partner Profile/Tabs/a_Contact Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Contact Info Tab'], 
    FailureHandling.STOP_ON_FAILURE)

//object = 'Education Partner Profile/Tabs/Contact Info/btn_Complete Submit'
//WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)
// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.OPTIONAL)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.STOP_ON_FAILURE)

//Enter the contact info fields
if (varKey_contact_phone != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/input_Key Contact Phone'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varKey_contact_phone], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varOrganization_phone != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/input_Organization Phone'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varOrganization_phone], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varOrganization_city != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/input_Organization City'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varOrganization_city], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varProvince_state_region != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/select_State'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
            , ('varParm1') : varProvince_state_region], FailureHandling.STOP_ON_FAILURE)
}


if (varProvince_state_region != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/input_ProvinceStateRegion'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varProvince_state_region], FailureHandling.STOP_ON_FAILURE)
}

if (varWorld_region != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/Contact Info/select_World Region'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
            , ('varParm1') : varWorld_region], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Education Partner Profile/Tabs/Contact Info/btn_Complete Submit'))

