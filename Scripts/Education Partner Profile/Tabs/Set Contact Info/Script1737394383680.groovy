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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

username = GlobalVariable.username

domain = GlobalVariable.domain

// Ensure that we are using the correct execution profile
if(username[-3..-1] != '6ep') {
	println('The Execution Profile must be set to "Education Partner"')

	System.exit(0)
}

testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')


// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '6ep') {
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
requiredFieldMsgs = [('Key Contact Phone') : 'The key contact phone field is required.', ('Organization') : 'The phone field is required.'
    , ('Organization City') : 'The city field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('countries by region') : 'Countries by Region', 'Find your International Post Code' : 'World Zip/Postal Code']

//Go to the Contact Info tab
WebUI.click(findTestObject('Education Partner Profile/Tabs/a_Contact Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

if(tooltipTextMap.size() != tooltipText.size()) {
	outText = '----- There were ' + tooltipText.size() + ' tooltips expected, but ' + tooltipTextMap.size() + ' were found.'
} else {
	outText = 'There were ' + tooltipTextMap.size() + ' tooltips found as expected.'
}

println(outText)

outFile.append(outText + '\n')

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)
// Call the tooltip testing script

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

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

// Test the external page links
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks'):pageLinks,
	('varObjectPath') : 'Object Repository/Education Partner Profile/Tabs/Contact Info/'], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Education Partner Profile/Tabs/Contact Info/btn_Complete Submit'))

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Education Partner Profile/Tabs/a_Contact Info'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)

