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

//xpath of the Process Stage group 
process_stage = "//input[@id='profile_group-1456436491.551_candidate_process_stages']"

process_stageList = []

if (binding.hasVariable('varProcess_stage')) {
	process_stageList = varProcess_stage
}
println('process_stageList' + process_stageList)

//xpath of the Cross-cultural group
cross_cultural = "//input[@id='profile_group-1456436491.551_cross-cultural_experience']"

cross_culturalList = []

if (binding.hasVariable('varCross_cultural')) {
	cross_culturalList = varCross_cultural
}
println('cross_culturalList' + cross_culturalList)

//xpath of the Bible_training group
bible_training = "//*[@id='profile_group-1456436491.551_bible_school_training']"

bible_trainingList = []

if (binding.hasVariable('varBible_training')) {
	bible_trainingList = varBible_training
}
println('bible_trainingList:' + bible_trainingList)

//xpath of the Perspectives group
perspectives = "//*[@id='profile_group-1456436491.551_attended_perspectives?']"

perspectivesList = []

if (binding.hasVariable('varPerspectives')) {
	perspectivesList = varPerspectives
}
println('perspectivesList:' + perspectivesList)

//xpath of the Missions Experience group
missions_experience = "//input[@id='profile_group-1456436491.551_missions_exposure']"

missions_experienceList = []

if (binding.hasVariable('varMissions_experience')) {
	missions_experienceList = varMissions_experience
}
println('missions_experienceList:' + missions_experienceList)

//xpath of the Relocation group
relocation = "//input[@id='profile_group-1456436491.551_relocation_question']"

relocationList = []

if (binding.hasVariable('varRelocation')) {
	relocationList = varRelocation
}
println('relocationList:' + relocationList)

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as education partner if not on dashboard page
if(!url == 'https://education.' + GlobalVariable.domain + '/profile?requestUri=/dashboard') {
	WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Readiness'))

WebDriver driver = DriverFactory.getWebDriver()

xpaths = [process_stage, cross_cultural, bible_training, perspectives, missions_experience, relocation]

lists = [process_stageList, cross_culturalList, bible_trainingList, perspectivesList, 
	missions_experienceList, relocationList]

for(i = 0; i < xpaths.size(); i++) {
	
	elements = driver.findElements(By.xpath(xpaths[i]))
	
	element_count = elements.size()
	
	println('count is ' + element_count)
	
	for(element in elements) {
		
		myValue = element.getAttribute("value")
		
		println(myValue)
		//This processing is necessary because '(!) ' is getting prepended to the value attribute 
		//	if it starts with 'No'
		if(myValue.length() >= 4) { 
			
			if(myValue.substring(0,4) == '(!) ') {
				
				myValue = myValue.substring(4)
				
			}
		}
		if(myValue in lists[i]) {
			
			myObj = WebUI.convertWebElementToTestObject(element)
			
			WebUI.click(myObj, FailureHandling.OPTIONAL)
		
		}
	}
}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/Readiness/btn_Complete Submit'))
