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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

domain = GlobalVariable.domain

// Specify file to contain test case results, update the global variable, and write the first line of text
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Education Partner Profile.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Complete Education Partner Profile on ' + domain) + '\n')

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [:], FailureHandling.STOP_ON_FAILURE)
/*
url = WebUI.getUrl(FailureHandling.OPTIONAL)

if(url.indexOf('dashboard') >= 0) {
	WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))
}
*/
//Make the screen tall if headless
headless = WebUI.callTestCase(findTestCase('_Functions/Test for Headless Browser'), [:], FailureHandling.STOP_ON_FAILURE)

dashboard = WebUI.verifyElementVisible(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'), FailureHandling.OPTIONAL)

if(dashboard) {
	WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))
}

WebUI.callTestCase(findTestCase('_Functions/Take Screenshot'), [:], FailureHandling.STOP_ON_FAILURE)

//Complete the Contact Info tab
WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Contact Info'), [:], FailureHandling.OPTIONAL)

//Complete the Positions Needed tab
positions = ['Assistant Principal', 'Principal', 'Childcare Director', 'English Teacher', 'Manager, Business']

experiences = ['Administrator', 'Non-Traditional', 'Teacher\'s Aide', 'Computer Science']

WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Positions Needed'), [('varPositions') : positions, ('varExperiences') : experiences], 
    FailureHandling.OPTIONAL)

//Complete the Service Options tab
time_commitments = ['One year', 'One year to two years']

start_options = ['Within 12 months']

school_terms = ['Open']

WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Service Options'), 
	[('varTime_commitments') : time_commitments, ('varStart_options') : start_options,
		('varSchool_terms') : school_terms], FailureHandling.OPTIONAL)

//Complete the Readiness tab
process_stage = ['I am actively investigating missions', 'I am ready to select a ministry']
	
cross_cultural = ['Not served in a culture other than my own', 'Occasionally served in cultures other than my own',
	 'Extensive experience serving in cultures other than my own']

bible_training = ['Some Bible school classes', 'Bible school degree or equivalent']

perspectives = ['I have not taken the Perspectives Course', 'I am planning to take the Perspectives Course',
	'I have taken or am taking the Perspectives Course']

missions_experience = ['I have attended a missions event', 'I have taken a short-term missions trip',
	'I have served in ministry or missions full-time', 'None of the above']

relocation = ['I am willing to relocate within North America', 'I am willing to relocate overseas']

WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Readiness'), [('varProcess_stage') : process_stage,
	('varCross_cultural') : cross_cultural, ('varBible_training') : bible_training, 
	('varPerspectives') : perspectives,	('varMissions_experience') : missions_experience, 
	('varRelocation') : relocation], FailureHandling.OPTIONAL)

//Complete the Match Filters tab
degree = ['No']

experience = ['Yes']

credentials = ['Yes', 'No', 'Pending']

english = ['Not Applicable']

travel = ['Some Travel funds available', 'Travel funding negotiated']

paid_volunteer = ['Volunteer/self-supported position', 'Self-supported, need travel and overhead',
 'Position requires raising your support', 'Position requires raising some support']

WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Match Filters'), [('varDegree') : degree,
	('varExperience') : experience, ('varCredentials') : credentials, ('varEnglish') : english,
	('varTravel') : travel, ('varPaid_volunteer') : paid_volunteer], FailureHandling.OPTIONAL)

