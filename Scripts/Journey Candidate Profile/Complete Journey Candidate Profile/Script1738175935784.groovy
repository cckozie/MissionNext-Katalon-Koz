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

if(GlobalVariable.testSuiteRunning) {
	pages = []
}

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest05jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

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
	outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Journey Candidate Profile on ' + 
	domain) + '.txt')
	
	GlobalVariable.outFile = outFile
	
	outFile.write(('Testing Complete Journey Candidate Profile on ' + domain) + '\n')
} else {
	outFile.append(('\nTesting Complete Journey Candidate Profile on ' + domain) + '\n')
	
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

dashboard = WebUI.verifyElementPresent(findTestObject('Journey Candidate Profile/Dashboard/a_My Profile'), 2, 
    FailureHandling.OPTIONAL)

if(dashboard) { 
        WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	gender = 'Male'
	
	state = 'Minnesota'
	
	country = 'United States'
	
	country_of_citizenship = 'United States'
	
	birth_year = '1949'
	
	marital_status = 'Married'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Contact Info'), [('varGender') : gender
	        , ('varState') : state, ('varCountry') : country, ('varCountry_of_Citizenship') : country_of_citizenship
			, ('varBirth_year') : birth_year, ('varMarital_status') : marital_status], FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Experience' in pages) {
	// Complete the Experience tab
	highest_degree = 'Bachelor of Science (BS)'
	
	degree_field = 'Electrical Engineering'
	
	occupation = 'Business Analyst'
	
	cross_cultural = 'Occasionally served in cultures other than my own'
	
	life_experience = "I've been a believer for 23 years. I went on a mission trip to train Vietnamese in Bible translation"
	
	it_professional = 'Yes'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Experience'), [('varHighest_degree') : highest_degree
			, ('varDegree_field') : degree_field, ('varOccupation') : occupation, ('varCross_cultural') : cross_cultural
			, ('varLife_experience') : life_experience, ('varIT_professional') : it_professional],
		FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Situation' in pages) {
	// Complete the Situation tab
	process_stage = 'I am actively investigating missions'
	
	bible_training = 'Some Bible school classes'
	
	describe_training = 'I audited some classes at Crown College'
	
	perspectives = 'I am planning to take the Perspectives Course'
	
	missions_experience = 'I have attended a missions event'
	
	interest_in_missionary_training = 'Maybe, need more information'
	
	church_affiliated = 'Yes'
	
	church_name = 'Parkside Church of the C&MA'
	
	church_involvement = 'I am serving in my local church'
	
	journey_guide = 'Maybe at a later time'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Situation'), [('varProcess_stage') : process_stage
			, ('varBible_training') : bible_training, ('varDescribe_training') : describe_training
			, ('varPerspectives') : perspectives, ('varMissions_experience') : missions_experience
			, ('varInterest_in_missionary_training') : interest_in_missionary_training, ('varChurch_affiliated') : church_affiliated
			, ('varChurch_name') : church_name, ('varChurch_involvement') : church_involvement
			, ('varJourney_guide') : journey_guide], FailureHandling.CONTINUE_ON_FAILURE)
}
	
/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Availability' in pages) {
// Complete the Availability tab
when_available = 'In one to two years'

time_commitments = ['One year to two years', 'Long Term']

time_available = ['Part Time ~ 20 Hours/Week', 'Part of Each Year', 'Summer Opportunities']

paid_and_volunteer_positions = ['Position requires raising some support', 'A salary provided; enough to live locally']

interested_in_short_term = 'Yes'

length_of_assignment = ['Two Weeks', '3-4 Weeks', 'A few months']

short_term_availability = ['Summer', 'Fall']

short_term_objective = 'Build vision for a career in missions'

short_term_comment = 'I would prefer an assignment in Southeast Asia'

travel_options = 'Travel funding negotiated'

relocation_options = '(!) Not sure'

WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Availability'), [('varWhen_available') : when_available
        , ('varTime_commitments') : time_commitments, ('varTime_available') : time_available
		, ('varPaid_and_volunteer_positions') : paid_and_volunteer_positions, ('varInterested_in_short_term') : interested_in_short_term
		, ('varLength_of_assignment') : length_of_assignment, ('varShort_term_availability') : short_term_availability
		, ('varShort_term_objective') : short_term_objective, ('varShort_term_comment') : short_term_comment
		, ('varTravel_options') : travel_options, ('varRelocation_options') : relocation_options], 
    FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Service/Comment' in pages) {
	// Complete the Service/Comment tab
	preferred_regions = ['Caribbean', 'Central America', 'Europe, West', 'North America']
	
	languages = ['English is a native language']
	
	additional_languages = 'Rudementary French'
	
	vision_trip = 'Yes'
	
	awareness_trip = 'No'
	
	comments = 'I did a vision trip to Viet Nam in 2015'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Service-Comment'), [('varPreferred_regions') : preferred_regions
	        , ('varLanguages') : languages,  ('varAdditional_languages') : additional_languages, ('varVision_trip') : vision_trip
			, ('varAwareness_trip') : awareness_trip, ('varComments') : comments], FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'IT Skills and Interest' in pages) {
	
	// Complete the IT Skills and Interests tab
	if(!GlobalVariable.fastPath) {
		
		WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_IT Skills and Interest'))
		
		// Get the job categories and preferences from /Users/cckozie/Documents/MissionNext/Journey Candidate Forms/Your Ministry Prefs.csv
		categories = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'Journey Candidate Forms/IT Skills and Interest.csv', ('varSelections') : 'IT Job Categories'], FailureHandling.STOP_ON_FAILURE)
		
		proficiencies = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'Journey Candidate Forms/IT Skills and Interest.csv', ('varSelections') : 'IT Proficiencies'], FailureHandling.STOP_ON_FAILURE)
		
		languages = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'Journey Candidate Forms/IT Skills and Interest.csv', ('varSelections') : 'Computer Languages'], FailureHandling.STOP_ON_FAILURE)
	} else {
		categories = ''
		proficiencies = ''
		languages = ''
	}
	
	it_comments = 'My strongest language skills are in Python'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set IT Skills and Interest'), [('varIT_job_categories') : categories
		, ('varIT_proficiencies') : proficiencies, ('varComputer_languages') : languages, 
		('varIT_comments') : it_comments], FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if (((pages.size() == 0) || ('All' in pages)) || ('Spouse Info' in pages)) {
	// Complete the Contact Info tab
	spouse_first_name = 'Jennifer'

	spouse_birth_year = '1951'

	spouse_citizenship_country = 'United States'

	spouse_ethnicity = 'WHITE/CAUCASIAN'

	spouse_serving_with_you = 'Yes'

	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Spouse Info'), [('varSpouse_first_name') : spouse_first_name
			, ('varSpouse_birth_year') : spouse_birth_year, ('varSpouse_citizenship_country') : spouse_citizenship_country
			, ('varSpouse_ethnicity') : spouse_ethnicity, ('varSpouse_serving_with_you') : spouse_serving_with_you], FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if (((pages.size() == 0) || ('All' in pages)) || ('Spouse Experience' in pages)) {
	// Complete the Contact Info tab
	spouse_highest_degree_earned = 'Bachelor of Arts (BA)'

	spouse_degree_field = 'Music'

	spouse_occupation = 'At home mom'
	
	spouse_bible_training = 'Some Bible school classes'
	
	spouse_describe_bible_training = 'Online classes from Hillsdale'
	
	spouse_cross_cultural_experience = 'Not served in a culture other than my own'
	
	spouse_missions_experience = 'I have attended a missions event'
	
	spouse_attended_perspectives = 'I am planning to take the Perspectives Course'
	
	spouse_experience = 'Sunday School teacher'

	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Spouse Experience'), [('varSpouse_highest_degree_earned') : spouse_highest_degree_earned
		, ('varSpouse_degree_field') : spouse_degree_field, ('varSpouse_occupation') : spouse_occupation
		, ('varSpouse_bible_training') : spouse_bible_training, ('varSpouse_describe_bible_training') : spouse_describe_bible_training
		, ('varSpouse_cross_cultural_experience') : spouse_cross_cultural_experience, ('varSpouse_missions_experience') : spouse_missions_experience
		, ('varSpouse_attended_perspectives') : spouse_attended_perspectives, ('varSpouse_experience') : spouse_experience]
		, FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if (((pages.size() == 0) || ('All' in pages)) || ('Spouse Ministry Prefs' in pages)) {
// Complete the Spouse Service Prefs tab
	if(!GlobalVariable.fastPath) {
		// Get the positions preferences from /Users/cckozie/Documents/MissionNext/Education Candidate Forms/spouse service prefs.csv
		WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Spouse Ministry Prefs'))
	
		positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'), [('varFileName') : 'Journey Candidate Forms/spouse ministry prefs.csv'
				, ('varSelections') : 'Spouse Preferred Position(s)'], FailureHandling.STOP_ON_FAILURE)
	} else {
		positions = ''
	}
	
	spouse_preferences_comment = 'Willing to consider other opportunities'

	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Spouse Ministry Prefs'), [('varSpouse_preferred_prefs') : positions
			, ('varSpouse_preferences_comment') : spouse_preferences_comment], FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Your Ministry Prefs' in pages) {
	if(!GlobalVariable.fastPath) {
		// Complete the Your Ministry Prefs tab
		WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Your Ministry Prefs'))
		
		// Get the job categories and preferences from /Users/cckozie/Documents/MissionNext/Journey Candidate Forms/Your Ministry Prefs.csv
		categories = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'Journey Candidate Forms/Your Ministry Prefs.csv', ('varSelections') : 'Job Categories'], FailureHandling.STOP_ON_FAILURE)
		
		positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
			[('varFileName') : 'Journey Candidate Forms/Your Ministry Prefs.csv', ('varSelections') : 'Preferred Position(s)'], FailureHandling.STOP_ON_FAILURE)
		
	} else {
		categories = ''
	}
	
	other_people_group = 'None'
	
	WebUI.callTestCase(findTestCase('Journey Candidate Profile/Tabs/Set Your Ministry Prefs'), [('varJob_categories') : categories
		, ('varPreferred_positions') : positions, ('varOther_people_group') : other_people_group], FailureHandling.CONTINUE_ON_FAILURE)
}

if(!called) { 	//Leave the browser open if this script was called from another script
//	WebUI.closeBrowser()
}

return pages