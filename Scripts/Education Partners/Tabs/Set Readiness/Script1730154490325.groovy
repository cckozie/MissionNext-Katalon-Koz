import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.security.spec.MGF1ParameterSpec

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
import org.openqa.selenium.JavascriptExecutor;

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
	println('The Execution Profile must be set to "Education Partner"')

	System.exit(0)
}

//xpath of the Process Stage group 
process_stage = "//input[@id='profile_group-1456436491.551_candidate_process_stages']"

//xpath of the Cross-cultural group
cross_cultural = "//input[@id='profile_group-1456436491.551_cross-cultural_experience']"

//xpath of the Bible_training group
bible_training = "//*[@id='profile_group-1456436491.551_bible_school_training']"

//xpath of the Perspectives group
perspectives = "//*[@id='profile_group-1456436491.551_attended_perspectives?']"

//xpath of the Missions Experience group
missions_experience = "//input[@id='profile_group-1456436491.551_missions_exposure']"

//xpath of the Relocation group
relocation = "//input[@id='profile_group-1456436491.551_relocation_question']"

xpaths = [process_stage, cross_cultural, bible_training, perspectives, missions_experience, relocation]

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Readiness'))

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [('varExtension') : 'Readiness Tab'], FailureHandling.STOP_ON_FAILURE)

xpaths = [process_stage, cross_cultural, bible_training, perspectives, missions_experience, relocation]

parms = [varProcess_stage, varCross_cultural, varBible_training, varPerspectives, 
	varMissions_experience, varRelocation]

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms],
	FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/Readiness/btn_Complete Submit'))
