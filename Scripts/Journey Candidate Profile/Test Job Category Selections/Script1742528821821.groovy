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
import javax.swing.*
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

debug = false

username = GlobalVariable.username

if ((username[(-3..-1)]) != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

domain = GlobalVariable.domain

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

if(GlobalVariable.testSuiteRunning) {
	myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
	myTestCase = testName
}

outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing ' + myTestCase + ' on ' + domain) + '.\n')

WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)

dashboard = WebUI.verifyElementPresent(findTestObject('Journey Candidate Profile/Dashboard/a_My Profile'), 2, FailureHandling.OPTIONAL)

if (dashboard) {
    WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))
	
	WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Your Ministry Prefs'))
}

//xpath of the Job Categories group
job_categories = "//input[@id='profile_group-1623987608.435_job_categories']"

//xpath of the Preferred Positions group
preferred_positions = "//input[@id='profile_group-1623987608.435_ministry_preferences']"

element_list = [] //All elements

checked_only = true

topCategories = WebUI.callTestCase(findTestCase('_Functions/Get Status of Group Elements'), [varXpath:job_categories, varList: element_list,
	varCheckedOnly: checked_only], FailureHandling.STOP_ON_FAILURE)

categories = []

topCategories.each{
	if(debug) {
		println(it.value)
	}
	
	categories.add(it.value[0])
}

if(debug) {
	println('The categories are ' + categories)
}

positions = WebUI.callTestCase(findTestCase('_Functions/Get Status of Group Elements'), [varXpath:preferred_positions, varList: element_list,
	varCheckedOnly: checked_only], FailureHandling.STOP_ON_FAILURE)

positionLocations = [:]

for(it in positions) {
	
	println(it.value)
	
	yLocation = it.key.getLocation().getY()
	
	positionLocations.put(it.value[0], yLocation)
}

if(debug) {
	positionLocations.each {
		println(it.key + ':' + it.value)
	}
}

//Get the y location of all of the job categories
WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> elements = driver.findElements(By.tagName('p'))

//println(elements.size())

jobCategories = [:]

categoryLocations = []

for (def element : elements) {
    if (element.isDisplayed()) {
        yLocation = element.getLocation().getY().toInteger()

        categoryText = element.getAttribute('textContent')

        jobCategories.put(yLocation,categoryText)

        categoryLocations.add(yLocation)
    }
}

if(debug) {
	jobCategories.each({ 
	        println((it.key + ':') + it.value)
	    })
}

categoryLocations = categoryLocations.sort()

catCount = categoryLocations.size()

selectedCategories = [:]

//Build a map of jobs and their categories
for(i = 0; i < catCount; i++) {
	catLocation = categoryLocations[i].toInteger()
	
	if(i < catCount - 1) {
		
		nextCatLocation = categoryLocations[i+1].toInteger()
		
		if(debug) {
			println('This category is at ' + catLocation + ', and the next is at ' + nextCatLocation)
		}
		
		for(it in positionLocations) {
			found = false
			job = it.key
			yLoc = it.value
			if(yLoc != 0) {
				if(debug) {
					println('Processing ' + job + ':' + it.value)
				}
				if(it.value > catLocation && it.value < nextCatLocation) {
					selectedCategories.put(job, jobCategories.get(catLocation))
					if(debug) {
						println('The category location is ' + catLocation)
						println('The category is ' + jobCategories.get(catLocation))
						println('Added ' + job + ':' + jobCategories.get(catLocation))
					}
					found = true
				}
			}
		
			if(found) {
				positionLocations.put(job,0)
			}
		}
	} 
}

if(debug) {
	selectedCategories.each {
		println(it.key + ':' + it.value)
	}
	println(categories)
}

//Check if all of the job categories have their category at the top of the page checked
errorCount = 0
for(it in selectedCategories) {
	job = it.key
	jobCategory = it.value
	if(debug) {
		println('Job is ' + job + ' and category is ' + jobCategory)
	}
	if(!categories.contains(jobCategory)) {
		outText = '##### ERROR: The job "' + job + '" is in the "' + jobCategory + '" category, but that category is not checked.'
		println(outText)
		outFile.append(outText + '\n')
		errorCount++
	}
}
if(errorCount == 0) {
	outText = '+++++ All job related categories were checked. No errors were found.'
	println(outText)
	outFile.append(outText + '\n')
}

WebUI.closeBrowser()
