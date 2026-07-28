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
import java.io.File

local = false

if(local) {
	url = 'https://missionconnexion.global/'
	varSite = 'MissionConnexion'
	outFileName ='/Users/cckozie/Documents/MissionNext/Test Reports/Test MissionWorks Headers and Footers on missionnext.org-local.txt'
	outFile = new File(outFileName)
	outFile.write('Local Test of ' + varSite + '\n')
} else {
	
	outFile = GlobalVariable.outFile
	
	url = WebUI.getUrl()
}

site = varSite

print('The site is ' + site)

headerLinks = ['MissionWorks' : ['Careers', 'Sponsorship', 'Membership', 'Contact Us', 'Sign Up', 'Donation'],
	'MissionConnexion' : ['Sponsorship', 'Contact Us', 'Sign Up', 'Donate Now'],
	'MissionExcellence' : ['7 Standards' , 'About' , 'Contact' , 'Join Today' , 'Member Login' , 'Membership' , 'Resources' , 'Sponsorship'],
	'MissionGuide' : ['Quick Search', 'US Trips', 'Trips For Your Group', 'Internships/Gap Year', 'List Trips Today']]


pageTitles = ["Careers" : "Careers – MissionWorks", "Sponsorship" : "Sponsorship – MissionWorks", "Membership" : "Membership – MissionWorks",
	 "Sign Up" : "Sign Up – MissionWorks", "Donation" : "MissionWorks Donation", "Contact Us" : "Contact Us – MissionWorks"]

altPageTitles = ["MissionConnexion" : ["Sign Up" : "Sign Up - MissionConnexion", "Donate Now" : "MissionConnexion Donation", "Contact Us" : "Contact Us - MissionConnexion"],
	"Careers" : ["Donation": "Donation – MissionWorks"],
	"MissionExcellence" : ["About" : "About - MissionExcellence", "7 Standards" : "7 Standards of Excellence - MissionExcellence",
		"Sponsorship" : "Sponsorship – MissionWorks", "Membership" : "Your Membership Journey - MissionExcellence", 
		"Resources" : "Resources - MissionExcellence",	"Contact" : "Contact - MissionExcellence", 
		"Join Today" : "MissionExcellence Membership Form", "Member Login" : "Login"],
	"MissionGuide" : ["Quick Search" : "Directory of Christian Mission Trips : MissionGuide.global", 
		"US Trips" : "United States Mission Trips : MissionGuide.global", "Trips For Your Group" : "Groups only Mission Trips : MissionGuide.global", 
		"Internships/Gap Year" : "Internship Mission Trips : MissionGuide.global", "List Trips Today" : "Signup - MissionGuide.global"]]

altSignUpSites = ['MissionWorks', 'Contact Us', 'About Us', 'Statement of Faith', 'Team and Board']

if(site == 'MissionConnexion' || site == 'MissionExcellence' || site == 'MissionGuide') {
	objectFolder = 'MissionWorks Headers and Footers/' + site + ' Header Links/span_'
} else {
	objectFolder = 'MissionWorks Headers and Footers/MissionWorks Header Links/span_'
}

if(local) {
	WebUI.openBrowser('')
	WebUI.maximizeWindow()
	WebUI.navigateToUrl(url)
	WebUI.waitForPageLoad(20)
}

println('site is ' + site)

if(headerLinks.containsKey(site)) {
	links = headerLinks.get(site)
} else {
	links = headerLinks.get('MissionWorks')
}

outFile.append(' \n')

errorFlag = false

for(link in links) {
	
	println('Testing ' + link + ' link.')
	
	outFile.append('Testing the header link ' + link + ' on ' + site + '.\n')
	
	println('objectFolder is ' + objectFolder)
	
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
	
	if(altPageTitles.containsKey(site)) {
		
		titles = altPageTitles.get(site)
		
		println('altPageTitles : ' + titles)
		
		if(titles.containsKey(link)) {
		
			myTitle = titles.get(link)
			
			println('myTitle : ' + myTitle)
			
		} else {
			
			myTitle = pageTitles.get(link)
			println('myTitle : ' + myTitle)
		}
		
	} else {
		
		myTitle = pageTitles.get(link)
		println('myTitle : ' + myTitle)
	}
	
	if(title != myTitle) {
		
		if(link == 'Sign Up' && altSignUpSites.contains(site)) {
			
			textPresent = WebUI.verifyTextPresent('Please choose your email subscription:', false, FailureHandling.OPTIONAL)
			
			if(!textPresent) {
				
				outText = ('#### ERROR: The text "Please choose your email subscription:" was not found on the page linked from ' + link + '.\n')
				
				println(outText)
				
				outFile.append(outText)
				
				errorFlag = true
				
			} else {
				
				println('Expected text was present on page.')
			}
		} else {
		
			outText = ('#### ERROR: Linked to page title is "' + title + '", but should be "' + myTitle + '"\n')
			
			println(outText)
			
			outFile.append(outText)
			
				errorFlag = true
		}
	
	} else {
		
		println('Title matches')
	}
	
	if(tabs > 1) {
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
	} else {
		
		WebUI.navigateToUrl(url)
		
		WebUI.waitForPageLoad(20)
	}
}

return errorFlag
