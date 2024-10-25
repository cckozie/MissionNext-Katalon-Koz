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
import org.openqa.selenium.JavascriptExecutor;

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
	println('The Execution Profile must be set to "Education Partner"')

	System.exit(0)
}

positionList = []

if (binding.hasVariable('varPositions')) {
	positionList = varPositions
} 

experienceList = []

if (binding.hasVariable('varExperiences')) {
	experienceList = varExperiences
}

if(positionList.size() + experienceList.size() == 0) {
	println('NO POSITIONS OR EXPERIENCE CHECKBOXES ARE TO BE SET')
	System.exit(9)
}

//xpath of the Positions Available group (div?)
positions = "//input[@id='profile_group-1456436124.683_educational_positions']"

//xpath of the Positions Available group (div?)
experiences = "//input[@id='profile_group-1456436124.683_teacher_experience_preferred']"

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
"Administrator" : null,
"Counseling" : null,
//General Teacher
"Elementary" : null,
"High School" : null,
"Home Schooling" : null,
"Kindergarten" : null,
"Middle School" : null,
"Non-Traditional" : null,
"Pre-School" : null,
"Substitute Teacher" : null,
"Tutoring" : null,
//Support
"Housing/Boarding/Childcare" : null,
"Maintenance" : null,
"Recruiting" : null,
"School Nurse" : null,
"Support" : null,
"Teacher's Aide" : null,
//Specialty Teacher
"Arts - Performing" : null,
"Arts - Visual" : null,
"Bible" : null,
"Business Education" : null,
"Computer Science" : null,
"Health" : null,
"Home Economics" : null,
"History" : null,
"Industrial Arts" : null,
"Mathematics" : null,
"Music Teacher" : null,
"Physical Education" : null,
"Reading" : null,
"Sciences" : null,
"Sciences - Biology" : null,
"Sciences - Chemistry" : null,
"Sciences - Earth Science" : null,
"Sciences - Physical Science/Physics" : null,
"Social Subjects" : null,
"Speech Pathologist" : null,
"Special Needs" : null,
"Technology" : null,
//Languages
"English" : null,
"ESL" : null,
"Language Teacher" : null]

//Log in as education partner
WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Positions Needed'))

WebDriver driver = DriverFactory.getWebDriver()

//Find all of the position checkboxes
positionCheckboxes = driver.findElements(By.xpath(positions))

element_count = positionCheckboxes.size()

println('count is ' + element_count)

 
//Add the position checkbox elements to the positions map
//and uncheck any checked elements
for(element in positionCheckboxes) {
	
	myValue = element.getAttribute("value")
	
	availablePositions[myValue] = element
	
	if(element.isSelected()) {
		
		myObj = WebUI.convertWebElementToTestObject(myElement)
		
		WebUI.click(myObj)
	
	}
}

//Click the checkbox for each Position Available from the input paramenter
for(position in positionList) {
	
	myElement = availablePositions.get(position)
	
	myObj = WebUI.convertWebElementToTestObject(myElement)
	
	WebUI.click(myObj)
}

for(element in positionCheckboxes) {
	
	if(element.isSelected()) {
		
		println(element.getAttribute("value") + ' is selected')
		
	}

}
 
//Find all of the experience checkboxes
experienceCheckboxes = driver.findElements(By.xpath(experiences))

element_count = experienceCheckboxes.size()

println('count is ' + element_count)

//Add the experience checkbox elements to the experiences map
//and uncheck any checked elements
for(element in experienceCheckboxes) {
	
	myValue = element.getAttribute("value")
	
	experiencePreferred[myValue] = element
	
	if(element.isSelected()) {
		
		myObj = WebUI.convertWebElementToTestObject(myElement)
		
		WebUI.click(myObj)
	
	}
}

//Click the checkbox for each experience in the input paramenter
for(experience in experienceList) {
	
	myElement = experiencePreferred.get(experience)
	
	myObj = WebUI.convertWebElementToTestObject(myElement)
	
	WebUI.click(myObj)
}	

//Report the results
for(element in experienceCheckboxes) {
	
	if(element.isSelected()) {
		
		println(element.getAttribute("value") + ' is selected')
		
	}
}

//Functions to click all position or experience checkboxes
def clickAllPositions() {
	count = 0
	
	availablePositions.each({
	
		position = it.key
		
		element = it.value
	
		count ++
		
		println(position + ':' + count)
	
		myObj = WebUI.convertWebElementToTestObject(element)
	
		WebUI.waitForElementClickable(myObj, 10)
		
		WebUI.click(myObj)
		//	WebUI.delay(1)
		
	})
	
}

def clickAllExperiences() {
	
	count = 0
	
	experiencePreferred.each({
		
		position = it.key
		
		element = it.value
	
		count ++
		
		println(position + ':' + count)
	
		myObj = WebUI.convertWebElementToTestObject(element)
	
		WebUI.waitForElementClickable(myObj, 10)
		
		WebUI.click(myObj)
		//	WebUI.delay(1)
		
	})

}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/Positions Needed/button_Complete Submit'))