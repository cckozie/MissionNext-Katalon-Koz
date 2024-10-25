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

fromEmails = ['do_not_reply@info.missionnext.org', 'Chris.Kosieracki@missionnext.org']

subjects = ['Candidate Profile for Taiwo Adejinmi from MissionNext','Candidate Profile for Taiwo Adejinmi from MissionNext']

searchKeys = ['NA', 'NA', 'NA']

WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : fromEmails,
	('varSubjectKey') : subjects, ('varSearchKey') : searchKeys], FailureHandling.STOP_ON_FAILURE)

//WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'do_not_reply@info.missionnext.org',
//	('varSubjectKey') : 'Candidate Profile for Taiwo Adejinmi from MissionNext'], FailureHandling.STOP_ON_FAILURE)

