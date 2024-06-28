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
import java.io.File

// Write results to text file
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test IT Skills & Interest Pag.txt')

outFile.write("Testing IT Skills & Interest Page\n\n")

// Map of checkboxes when test case was first written (Key is merely sequence of skill)
checkboxes = [1:'TECHNICAL', 2:'IT ENGINEERING ANALYST', 3:'ADMINISTRATOR', 4:'IT COMMUNICATIONS', 5:'CONTENT', 6:'DESIGNERS', 7:'DATABASE',
	8:'MANAGEMENT', 9:'SOCIAL MEDIA MARKETING', 10:'WEB', 11:'Developer, Applications', 12:'Developer, Architect', 13:'Developer, Front End',
	14:'Developer, Full-Stack', 15:'Developer, Games', 16:'Developer, Java', 17:'Developer, Mobile', 18:'Developer, .NET', 19:'Developer, PERL',
	20:'Developer, PHP', 21:'Developer, Python', 22:'Developer, Ruby on Rails', 23:'Developer, Software', 24:'Engineer, Applications',
	25:'Engineer, Network', 26:'Engineer, Software', 27:'Engineer, Solutions', 28:'Engineer, Support', 29:'Engineer, Systems',
	30:'Engineer, Quality Assurance', 31:'IT Analyst, Business', 32:'IT Analyst, Infrastructure', 33:'IT Analyst, Security', 34:'Systems Administrators',
	35:'Network Administrators', 36:'LAN Administrators', 37:'WAN Administrators', 38:'Storage - SAN Administrators', 39:'Wireless Communications',
	40:'Telecommunications', 41:'Datacommunications', 42:'IT Training Coordinator', 43:'IT Technical Writer', 44:'Content Editor', 45:'Content Manager',
	46:'Content Strategist', 47:'Content Translation', 48:'Content Writer', 49:'Information Architect', 50:'Animator', 51:'Graphic Designer',
	52:'User Experience Designer', 53:'User Interface Designer', 54:'Accessibility Specialist', 55:'Interaction Designer',
	56:'Software Development Manager', 57:'Software Systems Integrator', 58:'Software Type Designer', 59:'Database Administrator',
	60:'Database Analyst', 61:'Data Architect', 62:'Dababase Manager', 63:'Data Modeler', 64:'Data Qaulity Manager', 65:'Data Scientist',
	66:'Datawarehouse Architect', 67:'Chief Information Officer', 68:'Chief Technology Officer', 69:'Manager, Project', 70:'Manager, Sales',
	71:'Manager, Quality Assurance', 72:'Manager, Customer Support', 73:'Manager, Network Operations', 74:'Manager, Software Development',
	75:'Manager, Systems', 76:'Manager, LAN', 77:'Network Director', 78:'Digital Marketing Manager', 79:'Growth Hacker', 80:'Marketing Technologist',
	81:'Social Media Manager', 82:'Social Media Practitioner', 83:'Web, Administrator', 84:'Web, Analytics Specialist', 85:'Web, Developer',
	86:'Web Layout Design', 87:'Web Programming', 88:'Webmaster', 89:'Developer, WordPress', 90:'Developer, Other CMS', 91:'SEO Consultant', 92:'Assembly',
	93:'C', 94:'C++', 95:'C#', 96:'CSS', 97:'Delphi', 98:'Go', 99:'HTML', 100:'Java', 101:'JavaScript', 102:'JQuery', 103:'MATLAB', 104:'Objective-C', 105:'Perl',
	106:'PHP', 107:'PL SQL', 108:'Python', 109:'R', 110:'Ruby', 111:'Scratch', 112:'Swift', 113:'VB.Net', 114:'Visual Basic', 115:'None of the above',
	116:'Not a programmer']

// Map of skills checked when profile was creatd
checkedSkills = [1:'TECHNICAL', 2:'IT ENGINEERING/ANALYST', 7:'DATABASE', 11:'Developer, Applications', 12:'Developer, Architect', 
	13:'Developer, Front End', 21:'Developer, Python', 23:'Developer, Software', 26:'Engineer, Software', 30:'Engineer, Quality Assurance', 
	31:'IT Analyst, Business', 56:'Software Development Manager', 57:'Software Systems Integrator', 69:'Manager, Project', 
	71:'Manager, Quality Assurance', 74:'Manager, Software Development']

// Log in to Journey profile and build list of checkbox elements
WebUI.callTestCase(findTestCase('_Functions/Candidate Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('IT Skills and Interest Tab/a_IT Skills and Interest TAB'))

WebDriver driver = DriverFactory.getWebDriver()

allCheckboxes = driver.findElements(By.xpath("//div[@id='group-1694660169.099']/div/div[2]/div/div/input"))

int element_count = allCheckboxes.size()

int checked_count = checkedSkills.size()

outText = "There should be " + checked_count + ' skills checked out of a total of ' + element_count + ' skills listed.'

println(outText)

outFile.append(outText + '\n\n')

// Convert the map of checked skills to a list
List skills = new ArrayList(checkedSkills.values());

// Test all skills to verify those originally checked are the ones, and only ones, currently checked
checked = 0
for(box in allCheckboxes) {
	skill = box.getAttribute("value")
	status = box.getAttribute("checked")
	if(skill in skills) {
		if(!status) {
			outText = '##### ' + skill + ' should be checked but is not.'
		} else {
			outText = skill + ' is properly checked.'
			checked ++
		}
	} else {
		if(status) {
			outText = '##### ' + skill + ' is not checked but should be.'
		} else {
			outText = skill + ' is properly not checked'
		}
	}
	println(outText)
	outFile.append(outText + '\n')
}

outFile.append('\n ' + checked + ' skills were found to be checked.\n')

WebUI.closeBrowser()

// This function builds the map of checkboxes and checkboxes checked and then prints them
def buildCheckboxesMaps() {
	checkboxMap = [:]
	checkedMap = [:]
	count = 1
	for(box in checkboxes) {
		skill = box.getAttribute("value")
		checkboxMap.put(count,"'" + skill + "'")
		if(box.getAttribute("checked")) {
			checkedMap.put(count,"'" + skill + "'")
		}
		
		count ++
	}
	
	println(checkboxMap)
	println('\n\n\n')
	println(checkedMap)
}

