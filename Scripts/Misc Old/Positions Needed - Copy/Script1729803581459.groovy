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
AvailablePositions = [
//Administration
"Administrative Assistant",
"Administrator",
"Assistant Principal",
"Athletic Director",
"Childcare Director",
"Day Care Center Director",
"Director",
"Outdoor Program Supervisor",
"Preschool Director",
"Principal",
"Recruiting Teachers",
"Superintendent of Child Education",
"Superintendent of Schools",
//Education Support
"Boarding Home Assistant",
"Boarding Home Parent",
"Childcare Attendant",
"Curriculum Specialist",
"Education Consultant",
"Education Resource Coordinator",
"IT Coordinator",
"Manager, Business",
"Manager, Facilities",
"Manager, Finance",
"Manager, Maintenance",
"Non-Traditional Consultant",
"School Counselor",
"School Librarian",
"School Librarian Assistant",
"School Nurse",
"School Psychologist",
"School Psychometrist",
"Teacher's Aide",
"Youth Director/Worker",
//General Teachers
"Distance Education Teacher",
"Elementary School Teacher",
"High School Teacher",
"Home Schooling",
"Kindergarten Teacher",
"Middle School Teacher",
"Non-Traditional School Teacher",
"Preschool Teacher",
"Substitute Teacher",
"Tutor",
//Language Teachers
"English Second Language (ESL)",
"English Teacher",
"Foreign Language - French",
"Foreign Language - Portuguese",
"Foreign Language - Spanish",
"Foreign Language Teacher",
//Specialty Teachers
"Art Teacher",
"Arts - Performing",
"Athletic Coach",
"Bible Teacher",
"Business Education Teacher",
"Computer Teacher (School)",
"Drama Teacher",
"Geography Teacher",
"Health Teacher",
"History Teacher",
"Home Economics Teacher",
"Industrial Arts Teacher",
"Mathematics Teacher",
"Music Teacher",
"Physical Education Teacher",
"Reading Teacher/Specialist",
"Resource Teacher",
"Science, Biology",
"Science, Chemistry",
"Science, Physics",
"Science Teacher",
"Social Studies Teacher",
"Special Education Teacher"]

ExperiencePreferred = [
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

//Log in as education partner
WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Positions Needed'))

WebDriver driver = DriverFactory.getWebDriver()

positionCheckboxes = driver.findElements(By.xpath(positions))

element_count = positionCheckboxes.size()

println('count is ' + element_count)

for(element in positionCheckboxes) {
	value = element.getAttribute("value")
	println(value)
}
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


