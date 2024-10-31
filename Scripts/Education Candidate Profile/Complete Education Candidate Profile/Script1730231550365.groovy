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

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

domain = GlobalVariable.domain

// Specify file to contain test case results, update the global variable, and write the first line of text
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Education Partner Profile on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Complete Education Partner Profile on ' + domain) + '\n')

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}
/*
// Complete the required entries on the Contact Info tab
WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Contact Info'), [:], FailureHandling.STOP_ON_FAILURE)

// Complete the Experience tab
highest_degree = 'Bachelor of Science (BS)'

degree_field = 'Physical Science'

classroom_experience = 'Yes'

occupation = 'Middle School Teacher'

cross_cultural = 'Occasionally served in cultures other than my own'

missions_experience = 'I have taken a short-term missions trip'

life_experience = "I've been a believer for 23 years. I went on a mission trip to train Vietnamese in Bible translation"

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Experience'), [('varHighest_degree') : highest_degree,
	('varDegree_field') : degree_field, ('varClassroom_experience') : classroom_experience, ('varOccupation') : occupation,
	('varCross_cultural') : cross_cultural, ('varMissions_experience') : missions_experience, 
	('varLife_experience') : life_experience], FailureHandling.STOP_ON_FAILURE)
*/

// Complete the Education tab
formal_degree = 'Yes'

teaching_credentials = 'Pending'

credential_authority = 'TBD'

previous_experience = ['Business Education', ' Computer Science', ' ESL', 'Industrial Arts', 'Physical Education', 'Technology',
	'Sciences - Physical Science/Physics', 'Substitute Teacher']

other_experience = 'Middle school football coach'

english_proficiency = 'Advanced'

additional_languages = "Rudimentary French"

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Education'), [('varFormal_degree') : formal_degree,
	('varTeaching_credentials') : teaching_credentials, ('varCredential_authority') : credential_authority, 
	('varPrevious_experience') : previous_experience, ('varOther_experience') : other_experience, 
	('varEnglish_proficiency') : english_proficiency, ('varAdditional_languages') : additional_languages], FailureHandling.STOP_ON_FAILURE)

/*
// Complete the Situation tab
process_stage = 'I am actively investigating missions'

bible_training = 'Some Bible school classes'

church_affiliated = 'Yes'

journey_guide = 'Maybe at a later time'

perspectives = 'I am planning to take the Perspectives Course'

describe_training = 'I audited some classes at Crown College'

church_name = 'Parkside Church of the C&MA'

church_involvement = 'I am serving in my local church'

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Situation'), [('varProcess_stage') : process_stage,
	('varBible_training') : bible_training, ('varChurch_affiliated') : church_affiliated, ('varJourney_guide') : journey_guide,
	('varPerspectives') : perspectives, ('varDescribe_training') : describe_training, 
	('varChurch_name') : church_name,('varChurch_involvement') : church_involvement], FailureHandling.STOP_ON_FAILURE)


//Complete the Positions Needed tab
positions = ['Assistant Principal', 'Principal', 'Childcare Director', 'English Teacher', 'Manager, Business']

experiences = ['Administrator', 'Non-Traditional', 'Teacher\'s Aide', 'Computer Science']

WebUI.callTestCase(findTestCase('Education Partners/Tabs/Set Positions Needed'), [('varPositions') : positions, ('varExperiences') : experiences], 
    FailureHandling.OPTIONAL)

//Complete the Contact Info tab
WebUI.callTestCase(findTestCase('Education Partners/Tabs/Set Contact Info'), [:], FailureHandling.OPTIONAL)

//Complete the Service Options tab
WebUI.callTestCase(findTestCase('Education Partners/Tabs/Set Service Options'), [:], FailureHandling.OPTIONAL)

//Complete the Match Filters tab
degree = ['No']

experience = ['Yes']

credentials = ['Yes', 'No', 'Pending']

english = ['Not Applicable']

travel = ['Some Travel funds available', 'Travel funding negotiated']

paid_volunteer = ['Volunteer/self-supported position', 'Self-supported, need travel and overhead',
 'Position requires raising your support', 'Position requires raising some support']

WebUI.callTestCase(findTestCase('Education Partners/Tabs/Set Match Filters'), [('varDegree') : degree,
	('varExperience') : experience, ('varCredentials') : credentials, ('varEnglish') : english,
	('varTravel') : travel, ('varPaid_volunteer') : paid_volunteer], FailureHandling.OPTIONAL)

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

//println(cross_cultural)
WebUI.callTestCase(findTestCase('Education Partners/Tabs/Set Readiness'), [('varProcess_stage') : process_stage,
	('varCross_cultural') : cross_cultural, ('varBible_training') : bible_training, 
	('varPerspectives') : perspectives,	('varMissions_experience') : missions_experience, 
	('varRelocation') : relocation], FailureHandling.OPTIONAL)
*/
