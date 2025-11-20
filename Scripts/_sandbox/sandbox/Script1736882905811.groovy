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
import org.sikuli.script.*
import java.awt.Desktop as Desktop
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.io.File as File
import groovy.json.JsonSlurper as JsonSlurper


candidateSelections = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile as Map from AD'), [('varUsername') : 'cktest14ec',
	('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

candidateSelections.each {
	println(it)
}
System.exit(0)

varFormat = "YY-MM-dd.HH-mm"
//varTime = "17:33"
//varDelay = 20

now = new Date().format(varFormat)
println(now)

System.exit(0)
retArray = WebUI.callTestCase(findTestCase('_Functions/Test for App Assigned and Correct Expiration Date'), [('varUsername') : 'cktest05jc'], FailureHandling.STOP_ON_FAILURE)
println(retArray)
if(retArray[0] == 'Journey' && retArray[1] == 'Candidate' && retArray[3] == true) {
	prefix = '+++++ '
	suffix = ' as expected.'
} else {
	prefix = '##### ERROR:'
	suffix = '.'
}
println(prefix + 'Site is ' + retArray[0] + ', role is ' + retArray[1] + ', end date is ' + retArray[2] + suffix)


System.exit(0)
WebUI.openBrowser('missionnext.org')

//myWindow = WebUI.getWindowIndex()

myText = WebUI.callTestCase(findTestCase('_Functions/Get Random Text'), [('varParagraphs') : 2], FailureHandling.STOP_ON_FAILURE)

println(myText)

WebUI.delay(1)

//WebUI.switchToWindowIndex(myWindow)

WebUI.navigateToUrl('journey.missionnext.org')

System.exit(0)

def today = new Date()

println("Today: $today.format(yyyy-MM-dd)")

// Add 5 days using plus()
def futureDate1 = today.plus(180)

println("180 days from now (plus()): $futureDate1.format(yyyy-MM-dd)")

println(futureDate1.format('yyyy-MM-dd'))

System.exit(0)

newJobsFilePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/' // Path to new jobs data files

//myDir = "/Users/cckozie/git/MissionNext-Katalon-Koz/Scripts"
files = new File(newJobsFilePath)

capturedJobs = []

files.eachFileRecurse({ 
        path = it.absolutePath

        if (path.substring(path.length() - 4) == '.txt') {
            capturedJobs.add(path)
        }
    })

newJobs = []

i = 0

capturedJobs.each({ 
        values = []

        println(it)

        file = it.substring(newJobsFilePath.length())

        uBar = file.indexOf('_')

        category = file.substring(0, uBar)

        println(category)

        title = file.substring(uBar + 1, file.length() - 4)

        println(title)

        values.add(category)

        values.add(title)

        values.add(it)

        newJobs.add(values)
    })

newJobs.each({ 
        println(it)
    })

System.exit(0)

tabs = [('Job Category') : [['select_Category'], ['checkboxes- BUSINESS AS MISSION', 'checkboxes- CHURCH DEVELOPMENT', 'checkboxes- COMMUNICATIONS'
            , 'checkboxes- COMMUNITY DEVELOPMENT', 'checkboxes- CONSTRUCT/MAINTAIN', 'checkboxes- DISCIPLESHIP', 'checkboxes- DISCIPLE YOUTH'
            , 'checkboxes- EDUCATION', 'checkboxes- ESLTESOL', 'checkboxes- ENGINEERING', 'checkboxes- EVANGELISM', 'checkboxes- EVANGELISM SUPPORT'
            , 'checkboxes- HEALTH CARE', 'checkboxes- INFORMATION TECHNOLOGY', 'checkboxes- JUSTICE/ADVOCACY', 'checkboxes- RELIEF AND DEVELOPMENT'
            , 'checkboxes- RESOURCE MANAGEMENT', 'checkboxes- SUPPORT HELPS', 'checkboxes- SUPPORT PROFESSIONAL']], ('Job Classification') : [
        'input_Alternate Job Title', 'select_World Region'], ('Assignment Detail') : ['select_Start_Requested', 'checkboxes_Time Commitment'
        , 'checkboxes_TimeHours Available', 'checkboxes_Cross-Cultural Experience', 'checkboxes_Languages'], ('Logistics') : [
        'checkboxes_Paid and Volunteer Positions', 'checkboxes_Travel Support', 'checkboxes_Relocation Question'], ('Contact Details') : [
        'input_Contact Name', 'input_Contact Email'], ('Other Criteria') : ['checkboxes_Ministry Preferences']]

values = tabs.get('Job Category')

println(values[0])

println((values[1])[3])

