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

domain = GlobalVariable.domain

if(username[-3..-1] != '9ea') {
    println('The Execution Profile must be set to "Education Affiliate"')

    System.exit(0)
}

testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education Affiliate/tabs/contact info/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Education Affiliate/Tabs/Contact Info/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Contact Last Name') : 'img_Contact Last Name_field-tooltip',
('Contact Email') : 'img_Contact Email_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Contact Last Name') : 'Person working the website to recruit candidates.',
('Contact Email') : 'Email address must be unique.']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Mailing Address') : 'The srvc agency mailing address field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = []

//Go to the Contact Info tab
myTab = 'Education Affiliate/Tabs/a_Contact Info'
WebUI.click(findTestObject(myTab))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

if(tooltipTextMap.size() != tooltipText.size()) {
	outText = '----- There were ' + tooltipText.size() + ' tooltips expected, but ' + tooltipTextMap.size() + ' were found.'
	GlobalVariable.testCaseErrorFlag = true
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
if (varRecruiting_fax != null) {
    object = 'Object Repository/Education Affiliate/Tabs/Contact Info/input_Recruiting Fax'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varRecruiting_fax], 
        FailureHandling.STOP_ON_FAILURE)
}
println(varMailing_address)
if (varMailing_address != null) {
    object = 'Object Repository/Education Affiliate/Tabs/Contact Info/textarea_Mailing Address'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varMailing_address], 
        FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Education Affiliate/Tabs/input_Submit'))

// Test to see if the tab is complete (not colored red)
WebUI.waitForPageLoad(10)

testObject = myTab

WebUI.callTestCase(findTestCase('_Functions/Test for Tab Complete'), [('varTestName') : testName, ('varTestObject') : testObject ], FailureHandling.STOP_ON_FAILURE)

