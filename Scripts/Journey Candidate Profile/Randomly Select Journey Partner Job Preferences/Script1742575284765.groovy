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
import javax.swing.*
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

debug = false

username = GlobalVariable.username

if ((username[(-3..-1)]) != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

domain = GlobalVariable.domain

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Randomly Select Job Preferrences for Journey Candidate ' + username + ' on ' + 
domain) + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Randomly selecting jobs on Your Ministry Prefs tab for journey jandidate ' + username + ' on ' + domain) + '\n')

WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)

dashboard = WebUI.verifyElementPresent(findTestObject('Journey Candidate Profile/Dashboard/a_My Profile'), 2, FailureHandling.OPTIONAL)

if (dashboard) {
    WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))
	
	WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Your Ministry Prefs'))
}

//xpath of the Preferred Positions group
preferred_positions = "//input[@id='profile_group-1623987608.435_ministry_preferences']"

job_count = 25

WebUI.callTestCase(findTestCase('_Functions/Randomly Select Group Checkboxes'), [varXpath:preferred_positions, varSize: job_count], 
	FailureHandling.STOP_ON_FAILURE)

object = ('Journey Candidate Profile/Tabs/btn_Complete Submit')
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)


