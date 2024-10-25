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

ministries = [
	//CHURCH DEVELOPMENT
	'Adult Men', 'Adult Women', 'Bible Teaching', 'Church Planter', 'Leadership Development', 'Marriage & Family'
    , 'Missions Pastor', 'Pastor/Associate Pastor', 'Youth Pastor', 'Worship Leader'
	//COMMUNICATIONS
	, 'Art Director', 'Church Relations', 'Community Relations', 'Drama', 'Editors', 'Graphic Arts Design', 'Media Relations', 'Music'
	, 'Online Store', 'Photographer', 'Public Relations', 'Radio Broadcasting', 'Social Media', 'TV Production', 'Video Dubbing/Audio'
	, 'Videographer', 'Visual Arts', 'Web Content', 'Website Help/Chat', 'Writers' 
	//COMMUNITY DEVELOPMENT
	, 'Ag Training & Business', 'Anthropology', 'Appropriate Technology', 'Aquaculture', 'Community Organizer', 'Construction/Management'
	, 'Construction/Trade', 'Environmental', 'Physically Challenged', 'Public Health', 'Relief (Logistics)', 'Urban Coordinator'
	, 'Urban Gardening' 
	//CONSTRUCT/MAINTAIN
	, 'Carpenter', 'Dry Wall', 'Electrician', 'Handyman/Maintenance', 'HVAC Technician', 'Janitor', 'Job Site Foreman', 'Machine Shop Operator'
	, 'Masonry', 'Plumber/Pipe Fitter', 'Project Manager', 'Skilled Maintenance Tech', 'Superintendent', 'Vessel Maintenance'
	//DISCIPLESHIP
	, 'College Students', 'Discipleship', 'Discipleship, Chat Room', 'Intro to Christianity', 'Mentoring', 'Online Bible Study'
	//DISCIPLE YOUTH
	, 'Pre-School/Toddlers', 'Elementary', 'Junior High', 'Senior High', 'Student (College)', 'Military Kids', 'Third-Culture Kids'
	//EDUCATION
	, 'Elementary Education', 'Guidance Counselor', 'Higher Education', 'Informal Education', 'Languages', 'Librarian', 'Literacy'
	, 'Middle School/Jr. High', 'M.K. Education', 'Pre-School', 'Principal/Administrator', 'Secondary Education', 'Sign Languages'
	, 'Special Education', 'Teacher Training', 'TESL (English)', 'Theological Education'
	//ESL/TESOL
	, 'Manage ESL Program', 'Training ESL Teachers', 'ESL - Teach, Trained', 'ESL - Teach, Will Learn How'
	//ENGINEERING
    , 'Architecture', 'Chemical', 'Civil', 'Contractor', 'Electrical', 'Environment', 'Marine', 'Mechanical', 'Radio', 'Structural', 'Water/Wells' 
	//EVANGELISM
	, 'Camping', 'Chaplaincy', 'Christian Growth', 'Intl Students in USA', 'International, Outside US', 'JESUS Film Showings/Dist'
    , 'Online/Chat Room', 'Personal/Group Evangelism', 'Prison Ministry', 'Sports'
	//EVANGELISM SUPPORT
	, 'Bible Translation', 'Church Planting', 'City Coordinator', 'Literature Preparation', 'Lit Printing/Binding', 'Lit Wholesale Dist'
	, 'People Group Research', 'Inner City Ministries', 'Unreached People Groups'
	//HEALTH CARE
	, 'Career Counseling', 'Counseling, Family', 'Crisis Counseling', 'Dentist', 'Doctor', 'Health Education', 'Massage Therapist'
	, 'Medical Technologist', 'Midwife', 'Nurse', 'Occ.- Phys. Therapy', 'Ophthalmologist', 'Pediatric', 'Pharmacist', 'Physician'
	, 'Physician Assistant', 'Preventative Medicine', 'Primary Care', 'Psychologist', 'Surgeon', 'Work with Disabled'
	//INFORMATION TECHNOLOGY
	, 'Database Administrator', 'IT Application Developer', 'IT Architect', 'IT Business Analyst', 'IT Network Administrator'
	, 'IT Project Manager', 'IT Security Engineer', 'IT Support Specialist', 'IT Systems Administrator', 'IT Systems Analyst'
	, 'IT Systems Integrator', 'IT Technical Writer', 'IT Training Coordinator', 'Quality Assurance Engineer', 'Software Developer'
	, 'Software Systems Integrator', 'Software Type Designer', 'Web Developer', 'Web Layout/Design', 'Web Programming', 'Webmaster'
	//JUSTICE/ADVOCACY
	, 'Children in Crisis', 'Corporate Attorney', 'Criminal Investigator', 'Human Trafficking/Prostitution', 'Legal/Executive Assistant'
	, 'Managing Attorney', 'Paralegal', 'Social Worker'
	//PEOPLE GROUP
	, 'Animist', 'Buddhist', 'Hindu', 'Jewish', 'Muslim', 'Other'
	//RESOURCE MANAGEMENT
	, 'Distribution', 'Logistics', 'Purchasing', 'Shipping/Freight Mgmt', 'Warehouse Manager'
	//SUPPORT HELPS
	, 'Administrative Assistant', 'Auto Mechanic/Body', 'Data Entry', 'Dorm Parent/Hospitality', 'Event Coordinator', 'Family Ministry'
	, 'Food Service', 'Hair Stylist', 'Hospitality', 'Housekeeping', 'Housing Coordinator', 'Mothers Helper/Nanny', 'Outdoor Adventure Staff'
	, 'Prayer Partner', 'Receptionist', 'Refugee Work - Europe', 'Refugee Work - US', 'Tour/Museum Guide'
	//SUPPORT PROFESSIONAL
    , 'Accountant', 'Admin/Management', 'A&P Mechanic', 'Avionics Technician', 'Controller', 'COO', 'Finance Director', 'Fund Development'
    , 'Grant Writing', 'Marketing', 'Maritime', 'Maritime Captain', 'Mission Representative', 'Missionary Member Care', 'Personnel/HR'
    , 'Pilot - Helicopter', 'Pilot - Single Engine', 'Pilot - Other', 'President/CEO', 'Project Management', 'Recruiter/Mobilizer'
    , 'Security/Safety', 'Shipwright', 'Social Media Marketing', 'Training Coordinator', 'Volunteer Coordinator'
	//BUSINESS AS MISSION
	, 'Accounting', 'Agriculture', 'Business', 'Develop Executive Leadership', 'Education', 'Engineering', 'Health', 'Information Technology'
    , 'Lead Peer Roundtables', 'Management Consulting', 'Media', 'Coach Business Owners/CEOs', 'People Research'
	//RELIEF AND DEVELOPMENT
	, 'Agriculture/Horticulture', 'Construction Training', 'Economic Development', 'Food Distribution', 'General Contractor', 'Healthcare'
	, 'HIV/AIDS', 'IT Training', 'Micro-Enterprise', 'Micro-Finance', 'Orphanage', 'Project Managers', 'Shelter/Home Building', 'Veterinary'
    , 'Water Development']

WebUI.callTestCase(findTestCase('_Functions/Profile Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Ministry Prefs Tab/a_Ministry Prefs'))

WebDriver driver = DriverFactory.getWebDriver()

allCheckboxes = driver.findElements(By.xpath('//input[@id=\'profile_group-1446522088.64_ministry_preferences\']'))

element_count = allCheckboxes.size()

println(element_count)

element = driver.findElement(By.cssSelector('[value="Adult Women"]'))

myObj = WebUI.convertWebElementToTestObject(element)

WebUI.click(myObj)

count = 0

for (def ministry : ministries) {
	
	if(ministry != 'Other') {
		count ++
		
	    ministry = '"' + ministry + '"'
	
	    println(ministry + ':' + count) 
	
	    element = driver.findElement(By.cssSelector(('[value=' + ministry) + ']'))
	
	//    element.click() 
		
		myObj = WebUI.convertWebElementToTestObject(element)
	
		WebUI.waitForElementClickable(myObj, 10)
		
		
	    WebUI.click(myObj)
	    //	WebUI.delay(1)
		
	}

}

