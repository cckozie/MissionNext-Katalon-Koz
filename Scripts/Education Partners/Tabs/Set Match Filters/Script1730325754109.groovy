import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.security.spec.MGF1ParameterSpec as MGF1ParameterSpec
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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

parms = [varDegree, varExperience, varCredentials, varEnglish, varTravel, varPaid_volunteer]

println(parms)

//xpath of the Formal Education Degreee group 
degree = '//input[@id=\'profile_group-1456436956.575_school_formal_education_degree\']'

//xpath of the Experiencee group
experience = '//input[@id=\'profile_group-1456436956.575_school_classroom_experience\']'

//xpath of the Credentials group
credentials = '//input[@id=\'profile_group-1456436956.575_has_teaching_credentials\']'

//xpath of the English group
english = '//input[@id=\'profile_group-1456436956.575_english_skills\']'

//xpath of the Travel group
travel = '//input[@id=\'profile_group-1456436956.575_travel_support\']'

//xpath of the Paid_volunteer group
paid_volunteer = '//input[@id=\'profile_group-1456436956.575_financial_support\']'

xpaths = [degree, experience, credentials, english, travel, paid_volunteer]

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Match Filters'))

WebUI.callTestCase(findTestCase('_Functions/Click on ALL Group Elements'), [('varXpaths') : xpaths],
	FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

