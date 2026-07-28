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

// add MissionGuide, MissionLinked, MissionExcellence, 
//MissionArmor, About Us, Statement of Faith, Team and Board, Careers, Contact Us (same as MissionWorks),
site = 'MissionWorks'

headerLinks = ['MissionWorks' : ['Careers', 'Sponsorship', 'Membership', 'Contact Us', 'Sign Up', 'Donation'],
	'MissionConnexion' : ['Sponsorship', 'Contact Us', 'Sign Up', 'Donate Now']]


pageTitles = ["MissionWorks" : ["Careers" : "Careers – MissionWorks", "Sponsorship" : "Sponsorship – MissionWorks", "Membership" : "Membership – MissionWorks",
	 "Sign Up" : "Email Subscription", "Donation" : "MissionWorks Donation", "Contact Us" : "Contact Us – MissionWorks"],
 	 "MissionConnexion" :["Sponsorship" : "Sponsorship – MissionWorks", "Sign Up" : "Sign Up - MissionConnexion", "Donate Now" : "MissionConnexion Donation", "Contact Us" : "Contact Us - MissionConnexion"]]

objectFolder = site + ' Header Links/span_'

WebUI.openBrowser('')

WebUI.maximizeWindow()

url = 'https://missionworks.global/careers/'
//WebUI.navigateToUrl(site + '.global/')
WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(20)

links = headerLinks.get(site)

titles = pageTitles.get(site)

for(link in links) {
	
	println('Testing ' + link + ' link.')
	
	WebUI.click(findTestObject(objectFolder + link.toUpperCase()))
	
	driver = DriverFactory.getWebDriver()
	
	handles = driver.getWindowHandles()
	
	tabs = handles.size()
	
	if(tabs > 1) {
		
		WebUI.switchToWindowIndex(1)
		
		WebUI.waitForPageLoad(10)
		
		WebUI.delay(2)
	}
	
	title = WebUI.getWindowTitle()
	
	println(title)
	
	if(link == 'Sign Up' && site == 'MissionWorks') {
		
		textPresent = WebUI.verifyTextPresent('Please choose your email subscription:', false, FailureHandling.OPTIONAL)
		
		if(!textPresent) {
			
			println('#### ERROR: The text "Please choose your email subscription:" was not found on the page linked from ' + link + '.\n')
			System.exit(0)
			
		} else {
			
			println('Expected text was present on page.')
		}
		
	} else if(title != titles.get(link)) {
		
		println('#### ERROR: Linked to page title is "' + title + '", but should be "' + titles.get(link) + '"\n')
		System.exit(1)
		
	} else {
		
		println('Title matches')
	}
	
	if(tabs > 1) {
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
	} else {
		
		WebUI.navigateToUrl(url)
	}
}

