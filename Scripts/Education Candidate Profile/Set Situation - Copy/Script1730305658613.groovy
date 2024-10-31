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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
	println('The Execution Profile must be set to "Education Partner"')
	System.exit(0)
}

//xpath of the Process Stage group
process_stage = "//input[@name='profile[group-1449794128.145][process_stage]']"

process_stageList = []

if (binding.hasVariable('varProcess_stage')) {
	process_stageList = varProcess_stage
}
println('process_stageList' + process_stageList)

//xpath of the Bible Training group
bible_training = "//input[@name='profile[group-1449794128.145][bible_training]']"

bible_trainingList = []

if (binding.hasVariable('varBible_training')) {
	bible_trainingList = varBible_training
}
println('bible_trainingList' + bible_trainingList)

//xpath of the Church Affiliated group
church_affiliated = "//input[@name='profile[group-1449794128.145][affiliated_with_church]']"

church_affiliatedList = []

if (binding.hasVariable('varChurch_affiliated')) {
	church_affiliatedList = varChurch_affiliated
}
println('church_affiliatedList' + church_affiliatedList)

//xpath of the Journey Guide group
journey_guide = "//input[@name='profile[group-1449794128.145][journey_guide_option]']"

journey_guideList = []

if (binding.hasVariable('varJourney_guide')) {
	journey_guideList = varJourney_guide
}
println('journey_guideList' + journey_guideList)

if (binding.hasVariable('varPerspectives')) {
	perspectives = varPerspectives
} else {
	perspectives = null
}
println('perspectives' + perspectives)

if (binding.hasVariable('varDescribe_training')) {
	describe_training = varDescribe_training
} else {
	describe_training = null
}
println('describe_training' + describe_training)

if (binding.hasVariable('varChurch_name')) {
	church_name = varChurch_name
} else {
	church_name = null
}
println('church_name' + church_name)

if (binding.hasVariable('varChurch_involvement')) {
	church_involvement = varChurch_involvement
} else {
	church_involvement = null
}
println('church_involvement' + church_involvement)

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as education candidate if not on dashboard page
if(!url == 'https://education.' + GlobalVariable.domain + '/profile?requestUri=/dashboard') {
	WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Situation'))

if(perspectives != null) {
	WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/select_Perspectives'), perspectives, false)
}

if(describe_training != null) {
	WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/textarea_Describe Bible Training'), describe_training)
}

if(church_name != null) {
	WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/input_Church Name'), church_name)
}

if(church_involvement != null) {
	WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/select_Church Involvement'), church_involvement, false)
}

WebDriver driver = DriverFactory.getWebDriver()

xpaths = [process_stage, bible_training, church_affiliated, journey_guide]

lists = [process_stageList, bible_trainingList, church_affiliatedList, journey_guideList]

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

WebUI.click(findTestObject('Education Candidate Profile/Tab-Situation/btn_Complete Submit'))

