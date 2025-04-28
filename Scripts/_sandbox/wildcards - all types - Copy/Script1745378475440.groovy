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

varSite = 'Journey'
//varSite = 'Education'

varMap = ['Journey' : ['Journey Candidate Profile', 'Journey Candidate 05', 'Journey Partner 07'],
	'Education' : ['Education Candidate Profile', 'Education Candidate 04', 'Education Partner 06']]	

varValues = varMap.get(varSite)

for(username in [varValues[1],varValues[2]]) {
	
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : username], FailureHandling.STOP_ON_FAILURE)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebUI.click(findTestObject('Object Repository/'+ varValues[0] + '/Dashboard/a_My Profile'))
	
	WebUI.waitForPageLoad(10)
	
	WebUI.delay(5)
	
	checkboxes = [:]
	
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
			
			if(checkboxes.containsKey(fLabel)) {
				
				values = checkboxes.get(fLabel)
			} 
			values.add(label)
			
			checkboxes.put(fLabel, values)
			
			values = []
		}
	}

	if(username.contains('Candidate')) {
		wcCandidates = [:]
		wcCandidates = checkboxes
	} else {
		wcPartners = [:]
		wcPartners = checkboxes
	}
	
	WebUI.closeBrowser()
}

wcCandidates.each {
	println(it.key + ':' + it.value)
}

wcPartners.each {
	println(it.key + ':' + it.value)
}

/* Journey Candidate
Time Commitment(s):[Open - Will negotiate]
Time/Hours Available:[Open]
Paid & Volunteer Positions:[Volunteer/self-supported position, Open]
Length of Assignment:[Open]
Short-Term Availability:[Open]
Travel Options:[No travel funding provided]
Preferred Region(s):[No Preference]

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

*/