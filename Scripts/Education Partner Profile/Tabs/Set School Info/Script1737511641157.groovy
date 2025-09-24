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
testName = 'Education Partner School Info Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
//tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/tabs/school info/'
// Define the folder where the tooltip test objects live
testObjectFolder = 'Education partner Profile/Tabs/School Info/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Description') : 'img_Description_field-tooltip',
('Year Founded') : 'img_Year Founded_field-tooltip',
('Vision Trip') : 'img_Vision Trip_field-tooltip',
('School Year Starts') : 'img_School Year Starts_field-tooltip',
('Website Address') : 'img_Website Address_field-tooltip',
('Hide Listing') : 'img_Hide Listing_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Description') : 'Hint: Take a paragraph or two from your website. If copying & pasting, you must manually type in any special characters, i.e, quotation marks, apostrophe, ampersand, etc.',
('Year Founded') : 'Format: YYYY',
('Vision Trip') : 'Does your organization offer Vision Trips?',
('School Year Starts') : 'For Candidate information only',
('Website Address') : 'Must start with https://',
('Hide Listing') : 'Hide listing from public view for security purposes.(This choice can always be changed later.)']

// Define the required field missing error message test objects
requiredFieldMsgs = []

//Go to the Contact Info tab
WebUI.click(findTestObject('Education Partner Profile/Tabs/a_School Info'))

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

//Enter the school info fields
if (varDescription != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/textarea_Description'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varDescription], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varYear_founded != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/input_Year Founded'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varYear_founded], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varVision_trip != null) {
	if(varVision_trip == 'Yes') {
		checkbox = 'checkbox_Vision Trip Yes'
	} else {
		checkbox = 'checkbox_Vision Trip No'
	}
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/' + checkbox

	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

if(varVision_trip == 'Yes') {
	WebUI.delay(1)
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/textarea_Vision Trip Description'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varVision_trip_description], FailureHandling.STOP_ON_FAILURE)
}

if (varSchool_year_starts != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/select_School Year Starts'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object, ('varParm1') : varSchool_year_starts], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varSchool_accredited != null) {
	if(varSchool_accredited == 'Yes') {
		checkbox = 'checkbox_School Accredited Yes'
	} else {
		checkbox = 'checkbox_School Accredited No'
	}
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/' + checkbox

	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}


if (varStudent_capacity != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/input_Student Capacity'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varStudent_capacity], FailureHandling.STOP_ON_FAILURE)
}

if (varWebsite_address != null) {
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/input_Website Address'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
            , ('varParm1') : varWebsite_address], FailureHandling.STOP_ON_FAILURE)
}

if (varHide_listing == 'Yes') {
	object = 'Object Repository/Education Partner Profile/Tabs/School Info/checkbox_Hide Listing'

	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	WebUI.delay(1)
	
    object = 'Object Repository/Education Partner Profile/Tabs/School Info/textarea_Security Explanation'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varReason_for_hiding], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Education Partner Profile/Tabs/School Info/btn_Complete Submit'))

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Education Partner Profile/Tabs/a_School Info'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)


