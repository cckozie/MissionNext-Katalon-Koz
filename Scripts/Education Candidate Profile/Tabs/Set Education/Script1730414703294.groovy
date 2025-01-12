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

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [('varExtension') : 'Education Tab'], FailureHandling.STOP_ON_FAILURE)

// Set the text boxes and dropdown lists
if (varFormal_degree != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Education/select_Formal Degree',
		varFormal_degree, false)
}

if (varTeaching_credentials != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Education/select_Teaching Credentials',
		varTeaching_credentials, false)
}

//WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

if (varCredential_authority != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Credential Authority', varCredential_authority)
}

if (varOther_experience != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Other Experience', varOther_experience)
}

if (varEnglish_proficiency != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Education/select_English Proficiency', 
        varEnglish_proficiency, false)
}

if (varAdditional_languages != null) {
    setText('Object Repository/Education Candidate Profile/Tabs/Education/input_Additional Language(s)', 
		varAdditional_languages)
	WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Other Experience'))
}

if (varAdditional_languages != null && varProficiency != null) {
    selectOptionByValue('Object Repository/Education Candidate Profile/Tabs/Education/select_Additional Language Proficiency', 
		varProficiency, false )
}

click('Object Repository/Education Candidate Profile/Tabs/Education/btn_Submit')

def scrollToObject(def object) {
	println(('Converting ' + object) + ' to web element')

	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)

	loc = element.getLocation()

	y = loc.getY()

	println('Y location is ' + y)

	top = WebUI.getViewportTopPosition()

	println('Viewport top is ' + top)

	bottom = (top + 600)

	if (((y - top) < 150) || ((bottom - y) < 10)) {
		WebUI.scrollToPosition(0, y - 150)

		WebUI.delay(1)
	}
}

def click(def object) {
	scrollToObject(object)

	WebUI.click(findTestObject(object))
}

def getText(def object) {
	scrollToObject(object)

	value = WebUI.getText(findTestObject(object))

	return value
}

def setText(def object, def value) {
	scrollToObject(object)

	WebUI.setText(findTestObject(object), value)
}

def setEncryptedText(def object, def value) {
	scrollToObject(object)

	WebUI.setEncryptedText(findTestObject(object), value)
}

def selectOptionByValue(def object, def value, def flag) {
	scrollToObject(object)

	WebUI.selectOptionByValue(findTestObject(object), value, flag)
}

def selectOptionByLabel(def object, def label, def flag) {
	scrollToObject(object)

	WebUI.selectOptionByValue(findTestObject(object), label, flag)
}

def clearText(object) {
	scrollToObject(object)

	WebUI.clearText(findTestObject(object))
}

def scrollToOnly(object) {
	scrollToObject(object)
}


