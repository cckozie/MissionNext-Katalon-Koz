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
import javax.swing.*;


// Set to page(s) to run, or empty or 'All' to run all pages
pages = []

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Candidate"')

    System.exit(0)
}

domain = GlobalVariable.domain

called = false
if (binding.hasVariable('varCalled')) {
	called = varCalled
}

//Check to see if we're writing printed output to a file
writeFile = false

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

if(!writeFile) {
	outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Education Candidate Profile on ' + 
	domain) + '.txt')
	
	GlobalVariable.outFile = outFile
	
	outFile.write(('Testing Complete Education Candidate Profile on ' + domain) + '\n')
} else {
	outFile.append(('\nTesting Complete Education Candidate Profile on ' + domain) + '\n')
	
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

dashboard = WebUI.verifyElementPresent(findTestObject('Education Candidate Profile/Dashboard/a_My Profile'), 2, 
    FailureHandling.OPTIONAL)

if(dashboard) { 
        WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Dashboard/a_My Profile'))
}

if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	gender = 'Male'
	
	country = 'United States'
	
	country_of_citizenship = 'United States'
	
	birth_year = '1949'
	
	marital_status = 'Widowed'
	
	WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Contact Info'), [('varGender') : gender
	        , ('varCountry') : country, ('varCountry_of_Citizenship') : country_of_citizenship, ('varBirth_year') : birth_year
	        , ('varMarital_status') : marital_status], FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Experience' in pages) {
// Complete the Experience tab
highest_degree = 'Bachelor of Science (BS)'

degree_field = 'Physical Science'

classroom_experience = 'Yes'

occupation = 'Middle School Teacher'

cross_cultural = 'Occasionally served in cultures other than my own'

missions_experience = 'I have taken a short-term missions trip'

life_experience = "I've been a believer for 23 years. I went on a mission trip to train Vietnamese in Bible translation"

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Experience'), [('varHighest_degree') : highest_degree
		, ('varDegree_field') : degree_field, ('varClassroom_experience') : classroom_experience, ('varOccupation') : occupation
		, ('varCross_cultural') : cross_cultural, ('varMissions_experience') : missions_experience, ('varLife_experience') : life_experience],
	FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Education' in pages) {
// Complete the Education tab
formal_degree = 'Yes'

teaching_credentials = 'Pending'

credential_authority = 'TBD'

previous_experience = ['Business Education', ' Computer Science', ' ESL', 'Industrial Arts', 'Physical Education', 'Technology'
    , 'Sciences - Physical Science/Physics', 'Substitute Teacher']

other_experience = 'Middle school football coach'

english_proficiency = 'Advanced'

additional_languages = 'French'

proficiency = 'Rudimentary'

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Education'), [('varFormal_degree') : formal_degree
        , ('varTeaching_credentials') : teaching_credentials, ('varCredential_authority') : credential_authority, ('varPrevious_experience') : previous_experience
        , ('varOther_experience') : other_experience, ('varEnglish_proficiency') : english_proficiency, ('varAdditional_languages') : additional_languages 
		, ('varProficiency') : proficiency],
    FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Situation' in pages) {
// Complete the Situation tab
process_stage = 'I am actively investigating missions'

bible_training = 'Some Bible school classes'

church_affiliated = 'Yes'

journey_guide = 'Maybe at a later time'

perspectives = 'I am planning to take the Perspectives Course'

describe_training = 'I audited some classes at Crown College'

church_name = 'Parkside Church of the C&MA'

church_involvement = 'I am serving in my local church'

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Situation'), [('varProcess_stage') : process_stage
        , ('varBible_training') : bible_training, ('varChurch_affiliated') : church_affiliated, ('varJourney_guide') : journey_guide
        , ('varPerspectives') : perspectives, ('varDescribe_training') : describe_training, ('varChurch_name') : church_name
        , ('varChurch_involvement') : church_involvement], FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Availability' in pages) {
// Complete the Availability tab
when_available = 'In one to two years'

term_available = ['Spring Semester', 'Fall Semester']

time_commitments = ['One year to two years', 'Long Term']

relocation_options = '(!) Not sure'

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Availability'), [('varWhen_available') : when_available
        , ('varTerm_available') : term_available, ('varTime_commitments') : time_commitments, ('varRelocation_options') : relocation_options], 
    FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Preferences' in pages) {
	
frame = new JFrame("");
JPanel p = new JPanel();
JLabel l = new JLabel("LOADING ...", SwingConstants.CENTER);
frame.add(l);
frame.setSize(300, 100);
frame.setLocation(600, 0);
frame.setAlwaysOnTop (true)
frame.show();
	
// Complete the Preferences tab
// Get the positions and regions preferences from /Users/cckozie/Documents/MissionNext/Education Candidate Forms/preferred positions.csv
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Preferences'))

positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'), 
[('varFileName') : 'Education Candidate Forms/preferred positions.csv', ('varSelections') : 'positions'], FailureHandling.STOP_ON_FAILURE)

regions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
	[('varFileName') : 'Education Candidate Forms/preferred positions.csv', ('varSelections') : 'regions'], FailureHandling.STOP_ON_FAILURE)

frame.hide();

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Preferences'), [('varPositions') : positions, ('varRegions') : regions], 
    FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Options/Comment' in pages) {
// Complete the Options/Comment tab
paid_volunteer = ['Position requires raising some support', 'A salary provided; enough to live locally']

travel_options = ['Travel funding negotiated', 'Some travel funds available', 'Travel funds provided']

comments = 'I really hope I can get a partially paid position'

WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Options-Comment'), [('varPaid_volunteer') : paid_volunteer
        , ('varTravel_options') : travel_options, ('varComments') : comments], FailureHandling.STOP_ON_FAILURE)
}

if(!called) { 	//Leave the browser open if this script was called from another script
//	WebUI.closeBrowser()
}