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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.io.File as File

debug = false

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

//varSite = 'Journey'
//varSite = 'Education'
//varType = 'Job'
//varType = 'Org'

varMap = ['Journey_Job' : ['Journey Partner Profile', 'Journey Candidate 05', 'Journey Partner 07', 'a_My Agency Jobs'],
	'Journey_Org' : ['Journey Candidate Profile', 'Journey Candidate 05', 'Journey Partner 07'],
	'Education_Job' : ['Education Partner Profile', 'Education Candidate 04', 'Education Partner 06', 'a_My School Jobs'],
	'Education_Org' : ['Education Candidate Profile', 'Education Candidate 04', 'Education Partner 06']]

mapKey = varSite + '_' + varType

varValues = varMap.get(mapKey)

for(username in [varValues[1],varValues[2]]) {
	
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : username], FailureHandling.STOP_ON_FAILURE)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebUI.click(findTestObject('Object Repository/'+ varValues[0] + '/Dashboard/a_My Profile'))
		
	if(username == varValues[2]) {
		
		if(varType == 'Org') {
		
			WebUI.click(findTestObject('Object Repository/'+ varValues[0] + '/Dashboard/a_My Profile'))
			
		} else {
		
			WebUI.click(findTestObject('Object Repository/'+ varValues[0] + '/Dashboard/' + varValues[3]))
			
			WebUI.waitForPageLoad(10)
			
			WebUI.click(findTestObject('Object Repository/'+ varValues[0] + '/Matching/btn_New Job'))
		}
	}
			
	WebUI.waitForPageLoad(10)
	
	WebUI.delay(5)
	
	wildcards = [:]
	
	values = []
	
	List<WebElement> inputs = driver.findElements(By.tagName('input'))
	
	println(inputs.size())
	
	i = 0
	
	for(option in inputs) {
		
		name = option.getAttribute('name')
		
		type = option.getAttribute('type')
		
		value = option.getAttribute('value')
		
		if(type == 'checkbox' && value.contains('!')) {
			
			WebElement optionLabel = option.findElement(By.xpath("following-sibling::*"))
			
			label = optionLabel.getAttribute('innerHTML').trim()
	
			values.add(label)
			
			WebElement parent = option.findElement(By.xpath("./.."));
			
			WebElement gParent = parent.findElement(By.xpath("./.."));
			
			WebElement ggParent = gParent.findElement(By.xpath("./.."));
			
			WebElement gggParent = ggParent.findElement(By.xpath("./.."));
			
			WebElement fieldLabel = gggParent.findElement(By.tagName('label'))
			
			fLabel = fieldLabel.getAttribute('innerHTML').replace('*','')
			
			if(fLabel.contains('&')) {
				
				segments = fLabel.split(' ')
				
				fLabel = segments[0] + ' & ' + segments[2] + ' ' + segments[3]
			}
			
			values = []
			
			if(wildcards.containsKey(fLabel)) {
				
				values = wildcards.get(fLabel)
			} 
			values.add(label)
			
			wildcards.put(fLabel, values)
			
			values = []
		}
	}

	inputs = driver.findElements(By.tagName('option'))
	
	for(option in inputs) {
		value = option.getAttribute('value')
		
		if(value.contains('!')) {
				
			label = option.getAttribute('innerHTML').trim()
	
			values.add(label)
			
			WebElement parent = option.findElement(By.xpath("./.."));
			
			WebElement gParent = parent.findElement(By.xpath("./.."));
			
			WebElement ggParent = gParent.findElement(By.xpath("./.."));
			
			List<WebElement> children = ggParent.findElements(By.xpath("./child::*"));
			
			for(child in children) {
				if(child.getAttribute('class') == 'col-sm-3') {
					WebElement myLabel = child.findElement(By.tagName('label'));
					fLabel = myLabel.getAttribute('innerHTML').replace('*','')
				}
			}
					
			values = []
			
			if(wildcards.containsKey(fLabel)) {
				
				values = wildcards.get(fLabel)
			}
			
			values.add(label)
			
			wildcards.put(fLabel, values)
			
			values = []
		}
	}
	
	if(username.contains('Candidate')) {
		wcCandidates = [:]
		wcCandidates = wildcards
	} else {
		wcPartners = [:]
		wcPartners = wildcards
	}
	
	WebUI.closeBrowser()
}

if(debug) {
	wcCandidates.each {
		println(it.key + ':' + it.value)
	}
	
	wcPartners.each {
		println(it.key + ':' + it.value)
	}
}

// Write the maps as string to files to copy into match testing programs
wcPS = convertMap(wcPartners)
if(debug) {
	println('WCPartners = ' + wcPS)
}

if(varType != 'Job') {
	outFile = new File(filePath + varSite + ' Partner Wildcards.txt')
} else {
	outFile = new File(filePath + varSite + ' Job Wildcards.txt')
}
outFile.write(wcPS)

wcCS = convertMap(wcCandidates)
if(debug) {
	println('WCCandidates = ' + wcCS)
}
outFile = new File(filePath + varSite + ' Candidate Wildcards.txt')
outFile.write(wcCS)

return [wcCandidates, wcPartners]


def convertMap(myMap) {
	s = myMap.toString()
	s = s.replace("[", "['")
	s = s.replace("]", "']")
	s = s.replace(":", "':")
	s = s.replace(", ", ", '")
	s = s.replace("']']", "']]")
	s = s.replace("'],", "'],\n")
	s = s.replace(", '", "', '")
	return s
}


/* Journey Candidate
Time Commitment(s):[Open - Will negotiate]
Time/Hours Available:[Open]
Paid & Volunteer Positions:[Volunteer/self-supported position, Open]
Length of Assignment:[Open]
Short-Term Availability:[Open]
Travel Options:[No travel funding provided]
Preferred Region(s):[No Preference]
I/We can be Available:[Not sure]
Relocation Option(s):[Not sure]

	Journey Partner
Time Commitments:[Open - Will negotiate]
Available Start Options:[No Preference]
Time/Hours Needed:[Open]
Short-Term Trip Lengths:[Open]
Short-Term Availability:[Open]
Short-Term Objective:[Any or all candidate objectives]
Preferred Region(s):[No Preference]
Process Stage:[I am just beginning to look at missions]
Cross-cultural Experience:[Not served in a culture other than my own]
Bible Training:[Not Applicable]
Attended Perspectives?:[I have not taken the Perspectives Course]
Relocation Option(s):[Not a Match criterion]
Paid & Volunteer Positions:[No Preference]
Profile Years:[All]
Affiliated with a Church?:[No]

*/