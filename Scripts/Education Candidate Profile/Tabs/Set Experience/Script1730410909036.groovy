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
parms = [varMissions_experience]

//xpath of the Missions Experience group
missions_experience = '//input[@id=\'profile_group-1443650427.743_missions_exposure\']'

xpaths = [missions_experience]

//Go to the Experience tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Experience'))

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [('varExtension') : 'Experience Tab'], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_sandbox/Get dropdown list options'), [:], FailureHandling.STOP_ON_FAILURE)

System.exit(0)

// Set the text boxes and dropdown lists
if (varHighest_degree != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/select_High Earned Degree'), 
        varHighest_degree, false)
}

if (varDegree_field != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/input_Degree Field'), varDegree_field)
}

if (varClassroom_experience != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/select_Classroom Experience'), 
        varClassroom_experience, false)
}

if (varOccupation != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/textarea_Occupation'), varOccupation)
}

if (varCross_cultural != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/select_Cross-Cultural Experience'), 
        varCross_cultural, false)
}

if (varLife_experience != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tabs/Experience/textarea_Life Experience'), 
        varLife_experience)
}

WebUI.callTestCase(findTestCase('_Functions/Click on All Group Elements'), [('varXpaths') : xpaths], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Education Candidate Profile/Tabs/Experience/btn_Submit'))

