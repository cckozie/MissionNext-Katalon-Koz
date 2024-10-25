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

//positions = '//*[@id="group-1456436124.683"]/div[1]/div[3]'
positions = "//input[@id='profile_group-1456436124.683_educational_positions']"
//experience = '//*[@id="group-1456436124.683"]/div[3]/div[2]'
experience = "//input[@id='profile_group-1456436124.683_teacher_experience_preferred']"
Map availablePositions = [:]
availablePositions = [
//Administration
"Administrative Assistant" : null,
"Administrator" : null,
"Assistant Principal" : null,
"Athletic Director" : null,
"Childcare Director" : null,
"Day Care Center Director" : null,
"Director" : null,
"Outdoor Program Supervisor" : null,
"Preschool Director" : null,
"Principal" : null,
"Recruiting Teachers" : null,
"Superintendent of Child Education" : null,
"Superintendent of Schools" : null,
//Education Support
"Boarding Home Assistant" : null,
"Boarding Home Parent" : null,
"Childcare Attendant" : null,
"Curriculum Specialist" : null,
"Education Consultant" : null,
"Education Resource Coordinator" : null,
"IT Coordinator" : null,
"Manager, Business" : null,
"Manager, Facilities" : null,
"Manager, Finance" : null,
"Manager, Maintenance" : null,
"Non-Traditional Consultant" : null,
"School Counselor" : null,
"School Librarian" : null,
"School Librarian Assistant" : null,
"School Nurse" : null,
"School Psychologist" : null,
"School Psychometrist" : null,
"Teacher's Aide" : null,
"Youth Director/Worker" : null,
//General Teachers
"Distance Education Teacher" : null,
"Elementary School Teacher" : null,
"High School Teacher" : null,
"Home Schooling" : null,
"Kindergarten Teacher" : null,
"Middle School Teacher" : null,
"Non-Traditional School Teacher" : null,
"Preschool Teacher" : null,
"Substitute Teacher" : null,
"Tutor" : null,
//Language Teachers
"English Second Language (ESL)" : null,
"English Teacher" : null,
"Foreign Language - French" : null,
"Foreign Language - Portuguese" : null,
"Foreign Language - Spanish" : null,
"Foreign Language Teacher" : null,
//Specialty Teachers
"Art Teacher" : null,
"Arts - Performing" : null,
"Athletic Coach" : null,
"Bible Teacher" : null,
"Business Education Teacher" : null,
"Computer Teacher (School)" : null,
"Drama Teacher" : null,
"Geography Teacher" : null,
"Health Teacher" : null,
"History Teacher" : null,
"Home Economics Teacher" : null,
"Industrial Arts Teacher" : null,
"Mathematics Teacher" : null,
"Music Teacher" : null,
"Physical Education Teacher" : null,
"Reading Teacher/Specialist" : null,
"Resource Teacher" : null,
"Science, Biology" : null,
"Science, Chemistry" : null,
"Science, Physics" : null,
"Science Teacher" : null,
"Social Studies Teacher" : null,
"Special Education Teacher" : null]

experiencePreferred = [
//Administration
"Administrator",
"Counseling",
//General Teacher
"Elementary",
"High School",
"Home Schooling",
"Kindergarten",
"Middle School",
"Non-Traditional",
"Pre-School",
"Substitute Teacher",
"Tutoring",
//Support
"Housing/Boarding/Childcare",
"Maintenance",
"Recruiting",
"School Nurse",
"Support",
"Teacher's Aide",
//Specialty Teacher
"Arts - Performing",
"Arts - Visual",
"Bible",
"Business Education",
"Computer Science",
"Health",
"Home Economics",
"History",
"Industrial Arts",
"Mathematics",
"Music Teacher",
"Physical Education",
"Reading",
"Sciences",
"Sciences - Biology",
"Sciences - Chemistry",
"Sciences - Earth Science",
"Sciences - Physical Science/Physics",
"Social Subjects",
"Speech Pathologist",
"Special Needs",
"Technology",
//Languages
"English",
"ESL",
"Language Teacher"]

positionElements = [:]

experienceElements = [:]

//Log in as education partner
WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Positions Needed'))

WebDriver driver = DriverFactory.getWebDriver()

positionCheckboxes = driver.findElements(By.xpath(positions))

element_count = positionCheckboxes.size()

println('count is ' + element_count)

for(element in positionCheckboxes) {
	myValue = element.getAttribute("value")
	availablePositions[myValue] = element
	if(myValue == "Middle School Teacher") {
		println('======== ' + element)
		myElement = availablePositions.get("Middle School Teacher")
		println(myElement)
	}
//	myIndex = availablePositions.indexOf(myValue)
//	positionElements.put(myIndex,element)
}
//myElement = positionElements.get(70)
myElement = availablePositions.get("Middle School Teacher")
println(myElement)
myObj = WebUI.convertWebElementToTestObject(myElement)
WebUI.click(myObj)

System.exit(1)

element = driver.findElement(By.cssSelector('[value="Administrative Assistant"]'))

myObj = WebUI.convertWebElementToTestObject(element)

WebUI.click(myObj)

count = 0

for (def position : AvailablePositions) {
	
	count ++
	
    position = '"' + position + '"'

    println(position + ':' + count) 

    element = driver.findElement(By.cssSelector(('[value=' + position) + ']'))

//    element.click() 
	
	myObj = WebUI.convertWebElementToTestObject(element)

	WebUI.waitForElementClickable(myObj, 10)
	
	
    WebUI.click(myObj)
    //	WebUI.delay(1)
	
}

experienceCheckboxes = driver.findElements(By.xpath(experience))

element_count = experienceCheckboxes.size()

println('count is ' + element_count)

element = driver.findElement(By.cssSelector('[value="Administrator"]'))

myObj = WebUI.convertWebElementToTestObject(element)

WebUI.click(myObj)

count = 0

for (def experience : ExperiencePreferred) {
	
	count ++
	
	experience = '"' + experience + '"'

	println(experience + ':' + count)

	element = driver.findElement(By.cssSelector(('[value=' + experience) + ']'))

//    element.click()
	
	myObj = WebUI.convertWebElementToTestObject(element)

	WebUI.waitForElementClickable(myObj, 10)
	
	
	WebUI.click(myObj)
	WebUI.delay(1)
	
}


