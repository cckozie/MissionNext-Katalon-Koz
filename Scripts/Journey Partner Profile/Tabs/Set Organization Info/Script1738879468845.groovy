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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

username = GlobalVariable.username

domain = GlobalVariable.domain

// Ensure that we are using the correct execution profile
if(username[-3..-1] != '7jp') {
	println('The Profile must be set to "Journey Partner"')

	System.exit(0)
}

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')


// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey partner Profile/Tabs/Organization Info/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Tier Level') : 'img_Tier Level_field-tooltip',
('Mission Statement') : 'img_Mission Statement_field-tooltip',
('Statement of Faith') : 'img_Statement of Faith_field-tooltip',
('Board of Directors') : 'img_Board of Directors_field-tooltip',
('Year Founded') : 'img_Year Founded_field-tooltip',
('Upload Brochure') : 'img_Upload Brochure_field-tooltip',
('Link back URL') : 'img_Link back URL_field-tooltip',
('Hide Listing') : 'img_Hide Listing_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Tier Level') : "Select Tier level based on size of your organization's Annual Revenue.",
('Mission Statement') : 'Add web address from website where the mission statement is displayed.',
('Statement of Faith') : 'Add web address from website where the Statement of Faith is displayed.',
('Board of Directors') : 'Add web address from website where the Board of Directors is displayed.',
('Year Founded') : 'Format: YYYY',
('Upload Brochure') : 'Can upload a preliminary application or a brochure. Must be a PDF file.',
('Link back URL') : 'Where on your website is there a link back to MissionNext? (Include the http:// or https://)',
('Hide Listing') : 'Hide listing from public view for security purposes. (This choice can always be changed later.)']

// Define the required field missing error message test objects
requiredFieldMsgs = [:]


// Define the page's links and the text to search for on the linked page
pageLinks = [('Partnership Agreement') : 'Partnership Agreement', ('Terms and Conditions') : 'Terms and Conditions']


//Go to the Organization Info tab
WebUI.click(findTestObject('Journey Partner Profile/Tabs/a_Organization Info'))

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
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

//Enter the Organization info fields
if (varYear_founded != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Organization Info/input_Year Founded'
    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varYear_founded], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varLink_back_url != null) {
    object = 'Object Repository/Journey Partner Profile/Tabs/Organization Info/input_Link back URL'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varLink_back_url], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varHide_listing != null) {
	WebDriver driver = DriverFactory.getWebDriver()
    object = 'Object Repository/Journey Partner Profile/Tabs/Organization Info/input_Hide Listing'
	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
	myStatus = element.isSelected()
	if((varHide_listing == 'Yes' && !myStatus) || (varHide_list == 'No' && myStatus)) {
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object, ('varParm1') : null],
			FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
	}
	if(varHide_listing == 'Yes' && varSecurity_explanation != null) {
		object = 'Object Repository/Journey Partner Profile/Tabs/Organization Info/textarea_Security Explanation'
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varSecurity_explanation],
			FailureHandling.STOP_ON_FAILURE)
	}
}

// Test the external page links
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks'):pageLinks,
	('varObjectPath') : 'Object Repository/Journey Partner Profile/Tabs/Organization Info/'], FailureHandling.CONTINUE_ON_FAILURE)

object = 'Journey Partner Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object
	, ('varParm1') : null], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Partner Profile/Tabs/a_Organization Info'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
	GlobalVariable.testCaseErrorFlag = true
}
println(outText)
outFile.append(outText)
