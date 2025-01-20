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

process_stage = ['I am just beginning to look at missions','I am actively investigating missions',
	'I am ready to select a ministry']
	
cross_cultural = ['Not served in a culture other than my own', 'Occasionally served in cultures other than my own',
	 'Extensive experience serving in cultures other than my own']

bible_training = ['Minimal Bible training', 'Informal Bible training', 'Some Bible school classes',
	'Bible school degree or equivalent', 'Not Applicable']

perspectives = ['I have not taken the Perspectives Course', 'I am planning to take the Perspectives Course',
	'I have taken or am taking the Perspectives Course']

missions_experience = ['I have attended a missions event', 'I have taken a short-term missions trip',
	'I have served in ministry or missions full-time', 'None of the above']

relocation = ['I am not able to relocate for the foreseeable future', 'I am willing to relocate within North America',
	'I am willing to relocate overseas', 'Not a match criteria']
