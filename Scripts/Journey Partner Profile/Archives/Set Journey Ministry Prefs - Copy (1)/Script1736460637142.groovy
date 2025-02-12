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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

ministriesList = []

if (binding.hasVariable('varMinistries')) {
    ministriesList = varMinistries
}

if (ministriesList.size() == 0) {
    println('EXITING BECAUSE NO MINISTRY PREFERENCES WERE SPECIFIED')

    System.exit(9)
}

ministries = [ //CHURCH DEVELOPMENT
    ('Adult Men') : null, ('Adult Women') : null, ('Bible Teaching') : null, ('Church Planter') : null, ('Leadership Development') : null
    , ('Marriage & Family') : null, ('Missions Pastor') : null, ('Pastor/Associate Pastor') : null, ('Youth Pastor') : null
    , ('Worship Leader') : null, //COMMUNICATIONS
    ('Art Director') : null, ('Church Relations') : null, ('Community Relations') : null, ('Drama') : null, ('Editors') : null
    , ('Graphic Arts Design') : null, ('Media Relations') : null, ('Music') : null, ('Online Store') : null, ('Photographer') : null
    , ('Public Relations') : null, ('Radio Broadcasting') : null, ('Social Media') : null, ('TV Production') : null, ('Video Dubbing/Audio') : null
    , ('Videographer') : null, ('Visual Arts') : null, ('Web Content') : null, ('Website Help/Chat') : null, ('Writers') : null
    , //COMMUNITY DEVELOPMENT
    ('Ag Training & Business') : null, ('Anthropology') : null, ('Appropriate Technology') : null, ('Aquaculture') : null
    , ('Community Organizer') : null, ('Construction/Management') : null, ('Construction/Trade') : null, ('Environmental') : null
    , ('Physically Challenged') : null, ('Public Health') : null, ('Relief (Logistics)') : null, ('Urban Coordinator') : null
    , ('Urban Gardening') : null, //CONSTRUCT/MAINTAIN
    ('Carpenter') : null, ('Dry Wall') : null, ('Electrician') : null, ('Handyman/Maintenance') : null, ('HVAC Technician') : null
    , ('Janitor') : null, ('Job Site Foreman') : null, ('Machine Shop Operator') : null, ('Masonry') : null, ('Plumber/Pipe Fitter') : null
    , ('Project Manager') : null, ('Skilled Maintenance Tech') : null, ('Superintendent') : null, ('Vessel Maintenance') : null
    , //DISCIPLESHIP
    ('College Students') : null, ('Discipleship') : null, ('Discipleship, Chat Room') : null, ('Intro to Christianity') : null
    , ('Mentoring') : null, ('Online Bible Study') : null, //DISCIPLE YOUTH
    ('Pre-School/Toddlers') : null, ('Elementary') : null, ('Junior High') : null, ('Senior High') : null, ('Student (College)') : null
    , ('Military Kids') : null, ('Third-Culture Kids') : null, //EDUCATION
    ('Elementary Education') : null, ('Guidance Counselor') : null, ('Higher Education') : null, ('Informal Education') : null
    , ('Languages') : null, ('Librarian') : null, ('Literacy') : null, ('Middle School/Jr. High') : null, ('M.K. Education') : null
    , ('Pre-School') : null, ('Principal/Administrator') : null, ('Secondary Education') : null, ('Sign Languages') : null
    , ('Special Education') : null, ('Teacher Training') : null, ('TESL (English)') : null, ('Theological Education') : null
    , //ESL/TESOL
    ('Manage ESL Program') : null, ('Training ESL Teachers') : null, ('ESL - Teach, Trained') : null, ('ESL - Teach, Will Learn How') : null
    , //ENGINEERING
    ('Architecture') : null, ('Chemical') : null, ('Civil') : null, ('Contractor') : null, ('Electrical') : null, ('Environment') : null
    , ('Marine') : null, ('Mechanical') : null, ('Radio') : null, ('Structural') : null, ('Water/Wells') : null, //EVANGELISM
    ('Camping') : null, ('Chaplaincy') : null, ('Christian Growth') : null, ('Intl Students in USA') : null, ('International, Outside US') : null
    , ('JESUS Film Showings/Dist') : null, ('Online/Chat Room') : null, ('Personal/Group Evangelism') : null, ('Prison Ministry') : null
    , ('Sports') : null, //EVANGELISM SUPPORT
    ('Bible Translation') : null, ('Church Planting') : null, ('City Coordinator') : null, ('Literature Preparation') : null
    , ('Lit Printing/Binding') : null, ('Lit Wholesale Dist') : null, ('People Group Research') : null, ('Inner City Ministries') : null
    , ('Unreached People Groups') : null, //HEALTH CARE
    ('Career Counseling') : null, ('Counseling, Family') : null, ('Crisis Counseling') : null, ('Dentist') : null, ('Doctor') : null
    , ('Health Education') : null, ('Massage Therapist') : null, ('Medical Technologist') : null, ('Midwife') : null, ('Nurse') : null
    , ('Occ.- Phys. Therapy') : null, ('Ophthalmologist') : null, ('Pediatric') : null, ('Pharmacist') : null, ('Physician') : null
    , ('Physician Assistant') : null, ('Preventative Medicine') : null, ('Primary Care') : null, ('Psychologist') : null
    , ('Surgeon') : null, ('Work with Disabled') : null, //INFORMATION TECHNOLOGY
    ('Database Administrator') : null, ('IT Application Developer') : null, ('IT Architect') : null, ('IT Business Analyst') : null
    , ('IT Network Administrator') : null, ('IT Project Manager') : null, ('IT Security Engineer') : null, ('IT Support Specialist') : null
    , ('IT Systems Administrator') : null, ('IT Systems Analyst') : null, ('IT Systems Integrator') : null, ('IT Technical Writer') : null
    , ('IT Training Coordinator') : null, ('Quality Assurance Engineer') : null, ('Software Developer') : null, ('Software Systems Integrator') : null
    , ('Software Type Designer') : null, ('Web Developer') : null, ('Web Layout/Design') : null, ('Web Programming') : null
    , ('Webmaster') : null, //JUSTICE/ADVOCACY
    ('Children in Crisis') : null, ('Corporate Attorney') : null, ('Criminal Investigator') : null, ('Human Trafficking/Prostitution') : null
    , ('Legal/Executive Assistant') : null, ('Managing Attorney') : null, ('Paralegal') : null, ('Social Worker') : null
    , //PEOPLE GROUP
    ('Animist') : null, ('Buddhist') : null, ('Hindu') : null, ('Jewish') : null, ('Muslim') : null, ('Other') : null, //RESOURCE MANAGEMENT
    ('Distribution') : null, ('Logistics') : null, ('Purchasing') : null, ('Shipping/Freight Mgmt') : null, ('Warehouse Manager') : null
    , //SUPPORT HELPS
    ('Administrative Assistant') : null, ('Auto Mechanic/Body') : null, ('Data Entry') : null, ('Dorm Parent/Hospitality') : null
    , ('Event Coordinator') : null, ('Family Ministry') : null, ('Food Service') : null, ('Hair Stylist') : null, ('Hospitality') : null
    , ('Housekeeping') : null, ('Housing Coordinator') : null, ('Mothers Helper/Nanny') : null, ('Outdoor Adventure Staff') : null
    , ('Prayer Partner') : null, ('Receptionist') : null, ('Refugee Work - Europe') : null, ('Refugee Work - US') : null
    , ('Tour/Museum Guide') : null, //SUPPORT PROFESSIONAL
    ('Accountant') : null, ('Admin/Management') : null, ('A&P Mechanic') : null, ('Avionics Technician') : null, ('Controller') : null
    , ('COO') : null, ('Finance Director') : null, ('Fund Development') : null, ('Grant Writing') : null, ('Marketing') : null
    , ('Maritime') : null, ('Maritime Captain') : null, ('Mission Representative') : null, ('Missionary Member Care') : null
    , ('Personnel/HR') : null, ('Pilot - Helicopter') : null, ('Pilot - Single Engine') : null, ('Pilot - Other') : null
    , ('President/CEO') : null, ('Project Management') : null, ('Recruiter/Mobilizer') : null, ('Security/Safety') : null
    , ('Shipwright') : null, ('Social Media Marketing') : null, ('Training Coordinator') : null, ('Volunteer Coordinator') : null
    , //BUSINESS AS MISSION
    ('Accounting') : null, ('Agriculture') : null, ('Business') : null, ('Develop Executive Leadership') : null, ('Education') : null
    , ('Engineering') : null, ('Health') : null, ('Information Technology') : null, ('Lead Peer Roundtables') : null, ('Management Consulting') : null
    , ('Media') : null, ('Coach Business Owners/CEOs') : null, ('People Research') : null, //RELIEF AND DEVELOPMENT
    ('Agriculture/Horticulture') : null, ('Construction Training') : null, ('Economic Development') : null, ('Food Distribution') : null
    , ('General Contractor') : null, ('Healthcare') : null, ('HIV/AIDS') : null, ('IT Training') : null, ('Micro-Enterprise') : null
    , ('Micro-Finance') : null, ('Orphanage') : null, ('Project Managers') : null, ('Shelter/Home Building') : null, ('Veterinary') : null
    , ('Water Development') : null]

WebUI.callTestCase(findTestCase('_Functions/Profile Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

click('Object Repository/Journey Partner Profile/Ministry Prefs Tab/a_Ministry Prefs')

WebDriver driver = DriverFactory.getWebDriver()

allCheckboxes = driver.findElements(By.xpath('//input[@id=\'profile_group-1446522088.64_ministry_preferences\']'))

element_count = allCheckboxes.size()

println(element_count)

count = 0

element_count = allCheckboxes.size()

println('count is ' + element_count)

//Add the checkbox elements to the ministries map
//and uncheck any checked elements
for (def element : allCheckboxes) {
    myValue = element.getAttribute('value')
	println(myValue)

    (ministries[myValue]) = element

    if (element.isSelected()) {
        myObj = WebUI.convertWebElementToTestObject(element)

        click(myObj)
    }
}

ministries.each {
	println(it.key + ':' + it.value)
}

//Click the checkbox for each Ministry from the input paramenter
for (def ministry : ministriesList) {
	
	println(ministry)
	
	println(ministries.get(ministry))
	
    myElement = ministries.get(ministry)
	println(myElement)
    myObj = WebUI.convertWebElementToTestObject(myElement)
	println(myObj)
    WebUI.click(findTestObject(myObj))
}
System.exit(0)
checked = []

for (def element : allCheckboxes) {
    if (element.isSelected()) {
        checked.add(element.getAttribute('value'))
    }
}

errorCount = 0

ministriesList.each({ 
        if (!(it in checked)) {
            println(it + ' was in the ministries list but was not a selected.')

            errorCount++
        }
    })

checked.each({ 
        if (!(it in ministriesList)) {
            println(it + ' was selected but not in the ministries list')

            errorCount++
        }
    })

println(('There were ' + errorCount) + ' selection error(s) found.')

click('Journey Partner Profile/Ministry Prefs Tab/input_Complete Submit')

def scrollToObject(def object) {
	println(('Converting ' + object) + ' to web element')

	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)

	loc = element.getLocation()

	y = loc.getY()

	println('Y location is ' + y)

	top = WebUI.getViewportTopPosition()

	println('Viewport top is ' + top)

	bottom = (top + 600)

	if (((y - top) < 150) || ((bottom - y) < 10)) {
		WebUI.scrollToPosition(0, y - 150)

		WebUI.delay(1)
	}
}

def click(def object) {
	scrollToObject(object)

	WebUI.click(findTestObject(object))
}

