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

if(username[-3..-1] != '9ea') {
    println('The Execution Profile must be set to "Education Affiliate"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Affiliate Account Information Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education Affiliate/tabs/Account Information/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Education Affiliate/Tabs/Account Information/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('Description') : 'img_Description_field-tooltip',
('Board of Directors') : 'img_Board of Directors_field-tooltip',
('Statement of Faith') : 'img_Statement of Faith_field-tooltip',
('Web Address') : 'img_Web Address_field-tooltip',
('Settings') : 'img_Settings_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Abbreviation') : 'Used in some displays.',
('Description') : 'Hint: Take a paragraph or two from your website.',
('Statement of Faith') : 'Give web address where this can be found.',
('Web Address') : 'Must be a full web address and  begin with https://',
('Settings') : 'Open up Settings tab']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Mailing Address') : 'The srvc agency mailing address field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = []

//Go to the Account Information tab
WebUI.click(findTestObject('Education Affiliate/Tabs/a_Account Information'))

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

//Set the settings checkbox
if (varSettings != null) {
	checked = WebUI.verifyElementChecked(findTestObject('Object Repository/Education Affiliate/Tabs/Account Information/checkbox_Settings'), 1, FailureHandling.OPTIONAL)
	
	if((varSettings == 'Yes' && !checked) || (varSettings == 'No' && checked)) {
		
	    object = 'Object Repository/Education Affiliate/Tabs/Account Information/checkbox_Settings'
	    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object, ('varParm1') : null], 
	        FailureHandling.STOP_ON_FAILURE)
	}
}

WebUI.click(findTestObject('Education Affiliate/Tabs/input_Submit'))

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Education Affiliate/a_Account Information'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)

