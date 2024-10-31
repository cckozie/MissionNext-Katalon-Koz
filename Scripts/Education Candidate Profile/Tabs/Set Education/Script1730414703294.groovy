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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

parms = [varPrevious_experience]

//xpath of the Previous Experience group
previous_experience = "//input[@id='profile_group-1449793011.346_teaching_experience']"

xpaths = [previous_experience]

//Go to the Education tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Education'))

// Set the text boxes and dropdown lists
if (varFormal_degree != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/select_Formal Degree'),
		varFormal_degree, false)
}

if (varTeaching_credentials != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/select_Teaching Credentials'),
		varTeaching_credentials, false)
}



if (varCredential_authority != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Credential Authority'), varCredential_authority)
}

if (varOther_experience != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Other Experience'), varOther_experience)
}

if (varEnglish_proficiency != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/select_English Proficiency'), 
        varEnglish_proficiency, false)
}

if (varAdditional_languages != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/input_Additional Language(s)'), 
		varAdditional_languages)
}

WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Education Candidate Profile/Tab-Situation/btn_Complete Submit'))

