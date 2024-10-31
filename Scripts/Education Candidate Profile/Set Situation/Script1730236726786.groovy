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

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varProcess_stage, varBible_training, varChurch_affiliated, varJourney_guide]

// Define the xpath for the radio button groups
//xpath of the Process Stage group
process_stage = '//input[@name=\'profile[group-1449794128.145][process_stage]\']'

//xpath of the Bible Training group
bible_training = '//input[@name=\'profile[group-1449794128.145][bible_training]\']'

//xpath of the Church Affiliated group
church_affiliated = '//input[@name=\'profile[group-1449794128.145][affiliated_with_church]\']'

//xpath of the Church Affiliated group
church_affiliated = '//input[@name=\'profile[group-1449794128.145][affiliated_with_church]\']'

//xpath of the Journey Guide group
journey_guide = '//input[@name=\'profile[group-1449794128.145][journey_guide_option]\']'

//Log in as education candidate if not on dashboard page
url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

xpaths = [process_stage, bible_training, church_affiliated, journey_guide]

//Go to the Situation tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Situation'))

// Set the text boxes and dropdown lists
if (varPerspectives != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/select_Perspectives'), 
        varPerspectives, false)
}

if (varDescribe_training != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/textarea_Describe Bible Training'), 
        varDescribe_training)
}

if (varChurch_name != null) {
    WebUI.setText(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/input_Church Name'), varChurch_name)
}

if (varChurch_involvement != null) {
    WebUI.selectOptionByValue(findTestObject('Object Repository/Education Candidate Profile/Tab-Situation/select_Church Involvement'), 
        varChurch_involvement, false)
}

//WebDriver driver = DriverFactory.getWebDriver()

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

//xpaths = [process_stage, bible_training, church_affiliated, journey_guide]
/*
// For each radio button group, click the radio button that corresponds to the input parameter
// We have to do this by group in case of identicle labels in different groups
for (i = 0; i < xpaths.size(); i++) {
    elements = driver.findElements(By.xpath(xpaths[i]) //Find all of the radio buttons in the group
        )

    element_count = elements.size()

    println('count is ' + element_count)

    for (def element : elements) {
        myValue = element.getAttribute('value' //Get the radio button's label (value attribute)
            )

        //This processing is necessary because '(!) ' is getting prepended to the value attribute
        //	if it starts with 'No'
        if (myValue.length() >= 4) {
            if (myValue.substring(0, 4) == '(!) ') {
                myValue = myValue.substring(4)
            }
        }
        
        if (myValue in (parms[i])) {
            //Click on the radio button if it's label matches input parameter
            myObj = WebUI.convertWebElementToTestObject(element)

            WebUI.click(myObj, FailureHandling.OPTIONAL)
        }
    }
}
*/
WebUI.click(findTestObject('Education Candidate Profile/Tab-Situation/btn_Complete Submit'))

