import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.security.spec.MGF1ParameterSpec

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

degreeList = []

if (binding.hasVariable('varDegree')) {
	degreeList = varDegree
}
println('degreeList:' + degreeList)

//xpath of the Formal Education Degreee group 
degree = "//input[@id='profile_group-1456436956.575_school_formal_education_degree']"

experienceList = []

if (binding.hasVariable('varExperience')) {
	experienceList = varExperience
}
println('experienceList' + experienceList)

//xpath of the Experiencee group
experience = "//input[@id='profile_group-1456436956.575_school_classroom_experience']"

credentialsList = []

if (binding.hasVariable('varCredentials')) {
	credentialsList = varCredentials
}
println('credentialsList' + credentialsList)

//xpath of the Credentials group
credentials = "//input[@id='profile_group-1456436956.575_has_teaching_credentials']"

englishList = []

if (binding.hasVariable('varEnglish')) {
	englishList = varEnglish
}
println('englishList:' + englishList)

//xpath of the English group
english = "//input[@id='profile_group-1456436956.575_english_skills']"

travelList = []

if (binding.hasVariable('varTravel')) {
	travelList = varTravel
}
println('travelList:' + travelList)

//xpath of the Travel group
travel = "//input[@id='profile_group-1456436956.575_travel_support']"

if (binding.hasVariable('varPaid_volunteer')) {
	paid_volunteerList = varPaid_volunteer
}
println('paid_volunteerList:' + paid_volunteerList)

//xpath of the Paid_volunteer group
paid_volunteer = "//input[@id='profile_group-1456436956.575_financial_support']"

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as education partner if not on dashboard page
if(!url == 'https://education.' + GlobalVariable.domain + '/profile?requestUri=/dashboard') {
	WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Match Filters'))

WebDriver driver = DriverFactory.getWebDriver()

xpaths = [degree, experience, credentials, english, travel, paid_volunteer]

lists = [degreeList, experienceList, credentialsList, englishList, travelList, paid_volunteerList]

for(i = 0; i < xpaths.size(); i++) {
	
	elements = driver.findElements(By.xpath(xpaths[i]))
	
	element_count = elements.size()
	
	println('count is ' + element_count)
	
	for(element in elements) {
		
		myValue = element.getAttribute("value")
		
		//This processing is necessary because '(!) ' is getting prepended to the value attribute 
		//	if it starts with 'No'
		if(myValue.length() >= 4) { 
			
			if(myValue.substring(0,4) == '(!) ') {
				
				myValue = myValue.substring(4)
				
			}
		}
		
		myObj = WebUI.convertWebElementToTestObject(element) //Convert the element to a test object
		
		myType = element.getAttribute("type")	//We need to know if it's checkbox or radio button
		
		if(myValue in lists[i]) {	//Is the element's label in the list to be selected?
			inList = true
		} else {
			inList = false
		}
		
		//If it's a checkbox, then either clear it or check it depending on the input parm
		if(myType == 'checkbox') {
			myStatus = element.isSelected()
			if(!myStatus && inList || myStatus && !inList) {
					WebUI.click(myObj)
			}
			
		//If it's a radio button, then just click it if it's the one
		} else { 		//Must be radio button
			if(inList) {
				WebUI.click(myObj)
			}
		}
	}
}

