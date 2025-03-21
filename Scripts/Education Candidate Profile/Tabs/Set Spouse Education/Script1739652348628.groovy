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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if ((username[(-3..-1)]) != '4ec') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

// Set output file
testName = 'Education Candidate Spouse Education Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varSpouse_formal_degree, varSpouse_education_credentials, varSpouse_previous_experience]

//xpath of the Spouse Formal Degree group
spouse_formal_degree = '//input[@name=\'profile[group-1631402580.093][spouse_formal_education_degree]\']'

//xpath of the Spouse Education Credentials group
spouse_education_credentials = '//input[@name=\'profile[group-1631402580.093][spouse_education_credentials]\']'

//xpath of the Spouse Previous Experience group
spouse_previous_experience = '//input[@id=\'profile_group-1631402580.093_spouse_teaching_experience\']'

xpaths = [spouse_formal_degree, spouse_education_credentials, spouse_previous_experience]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Education Candidate Profile/Tabs/Spouse Education/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [('dummy') : 'dummy', ('Spouse Additional Language(s)') : 'img_Spouse Additional Language(s)_field-tooltip']

// Define the expected tooltip texts
tooltipText = [('Spouse Additional Language(s)') : 'Other language in which you have some level of fluency.']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Spouse Previous Experience') : 'The spouse teaching experience field is required.']

//Go to the Education tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Spouse Education'))

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

//WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

// Set the text boxes and dropdown lists
if (varSpouse_credential_authority != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Education/input_Spouse Credential Authority'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varSpouse_credential_authority], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_other_experience != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Education/textarea_Spouse Other Experience'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varSpouse_other_experience], 
        FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_english_proficiency != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Education/select_Spouse English Proficiency'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
            , ('varParm1') : varSpouse_english_proficiency], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_additional_languages != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Education/input_Spouse Additional Language(s)'

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : varSpouse_additional_languages], 
        FailureHandling.STOP_ON_FAILURE)
}

object = 'Education Candidate Profile/Tabs/btn_Submit'

WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
// The tab may not be found since this is typically the last tab. In that case, test for the Thank You page
WebUI.waitForPageLoad(10)

profile = WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Tabs/a_Contact Info'), FailureHandling.OPTIONAL)

if (profile) {
    if (!(myClass.contains('error'))) {
        outText = (testName + ' was successfully completed.\n')
    } else {
        outText = (('Unable to successfully complete ' + testName) + '.\n')

        KeywordUtil.markError(outText)
    }
} else {
	found = WebUI.verifyTextPresent('Thank You', false, FailureHandling.OPTIONAL)

    if (found) {
        outText = (testName + ' was successfully completed.\n')
    } else {
        outText = (('Unable to successfully complete ' + testName) + '.\n')

        KeywordUtil.markError(outText)
    }
}

println(outText)

outFile.append(outText)

