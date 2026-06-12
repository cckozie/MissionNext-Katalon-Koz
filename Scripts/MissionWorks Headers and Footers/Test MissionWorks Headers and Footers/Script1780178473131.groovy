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
import java.io.File as File
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

domain = GlobalVariable.domain

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get('current_testcase').toString().substring(RunConfiguration.getExecutionProperties().get(
        'current_testcase').toString().lastIndexOf('/') + 1)

if (GlobalVariable.testSuiteRunning) {
    myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
    myTestCase = testName
}

sites = ['MissionNext' : "https://missionnext.org/", "Journey" : "https://journey.missionnext.org/journey-home/login-here-2/", 
	"Education" : "https://education.missionnext.org/education-home/login-here", "QuickStart" : "https://quickstart.missionnext.org/quickstart-home/login-here",
	'Journey Guide' : 'https://jg.missionnext.org/', 'MissionLinked' : 'https://missionlinked.global/', 'Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/']

//sites = ['MissionNext' : "https://missionnext.org/"]
//sites = ['MissionLinked' : 'https://missionlinked.global/']
//sites = [ "QuickStart" : "https://quickstart.missionnext.org/quickstart-home/login-here"]
//sites = ["Education" : "https://education.missionnext.org/education-home/login-here"]
//sites = ["Journey" : "https://journey.missionnext.org/journey-home/login-here-2/"]
//sites = ['Journey Guide' : 'https://jg.missionnext.org/']
//sites = ['Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/']

if(sites.size() == 1) {
	fileNameAdd = '-' + sites.keySet().first()
} else {
	fileNameAdd = ''
}

footerElements = ["a_Careers" : ["link", "Careers – MissionWorks", true], "a_Contact Us" : ["link", "Contact Us – MissionWorks", true],
	 "a_Donate" : ["link", "MissionWorks Donation", true], "a_Privacy" : ["link", "Privacy Policy – MissionWorks", true], "a_Sign Up" : ["verify", "Email Subscription", true],
	  "a_Sponsorship" : ["link", "Sponsorship – MissionWorks", true], "i_Facebook" : ["link", "MissionWorks | Facebook", false], 
	  "i_Instagram" : ["link", "MissionWorks (@missionworksglobal) • Instagram photos and videos",false], "img_ECFA" : ["image", "", false], "img_Gold Transparency" : ["image", "", false],
	  "p_Copyright  2024-2026 MissionWorks" : ["text", "Copyright © 2024-2026 MissionWorks – All rights reserved.", false], 
	  "span_Address" : ["text", "1400 NE 136th Ave, Vancouver, WA 98684", false], "span_Email" : ["text", "Info@MissionWorks.global", false], "span_Phone" : ["text", "503.360.1865", false]]

//footerElements = ["a_Careers - Copy" : ["link", "Careers – MissionWorks", true]]

outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + '.txt')

errorFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + '-ERRORS.txt')

GlobalVariable.outFile = outFile

outFile.write(myTestCase + ' on ' + domain + '.\n')

errorFlag = false

for(site in sites) {

	retValue = WebUI.callTestCase(findTestCase('MissionWorks Headers and Footers/Functions/Connecting Menu'), [('varSite') : site.key, ('varUrl') : site.value], FailureHandling.OPTIONAL)
	
	if(retValue) {
		errorFlag = true
	}

	retValue = WebUI.callTestCase(findTestCase('MissionWorks Headers and Footers/Functions/Equipping Menu'), [('varSite') : site.key, ('varUrl') : site.value], FailureHandling.OPTIONAL)
	
	if(retValue) {
		errorFlag = true
	}

	retValue = WebUI.callTestCase(findTestCase('MissionWorks Headers and Footers/Functions/About Us Menu'), [('varSite') : site.key, ('varUrl') : site.value], FailureHandling.OPTIONAL)

	if(retValue) {
		errorFlag = true
	}

	testMWLogo(site.key)
	
	testDonate(site.key)

	testFooter(site.key)

}

if(errorFlag) {
	outFile.renameTo(errorFile)
	GlobalVariable.errorsFlag = true
}
	
WebUI.closeBrowser()

def testMWLogo(site) {
	
	return
	
	myUrl = WebUI.getUrl()
	
	outFile.append('\n< Testing the MissionWorks logo option on ' + site + '.>\n')
	
	WebUI.click(findTestObject('Object Repository/MissionWorks Headers and Footers/img_MissionWorks logo'))
	
	WebUI.waitForPageLoad(20)
	
	mwUrl = WebUI.getUrl()
	
	title = WebUI.getWindowTitle()
	
	println(title)
	
	if(title != 'MissionWorks') {
		outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks' + '"\n')
		errorFlag = true
	}
	
	if(mwUrl != 'https://missionworks.global/') {
		outFile.append('#### ERROR: The URL of linked to page is "' + mwUrl + '", but should be "' + 'https://missionworks.global/' + '"\n')
		errorFlag = true
	}
	
	WebUI.navigateToUrl(myUrl)
}


def testDonate(site) {
	
	myUrl = WebUI.getUrl()
	
	outFile.append('\n< Testing Donate option on ' + site + '.>\n')
	
	WebUI.click(findTestObject('Object Repository/MissionWorks Headers and Footers/a_Donate'))
	
	WebUI.waitForPageLoad(20)
	
	WebUI.delay(5)
	
	title = WebUI.getWindowTitle()
	
	println(title)
	
	if(title != 'MissionWorks Donation') {
		outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks Donation' + '"\n')
		errorFlag = true
	}
	
	WebUI.navigateToUrl(myUrl)
}

def testFooter(site) {
	
	//These sites use the default (MissionNext) footer element definitions
	defaultFooterSites = ['Journey', 'Education', 'QuickStart', 'Journey Guide', 'Coaching']
	
	outFile.append('\n<<< TESTING THE PAGE FOOTER ON ' + site + ' >>>\n')
	
	for(element in footerElements) {
		myUrl = WebUI.getUrl()
		
		myElement = element.key
		
		println(myElement)
		
		uBar = myElement.indexOf('_')
		
		ele = myElement.substring(uBar + 1)
		
		values = element.value
		
		myType = values[0]
		
		myTest = values[1]
		
		mySwitch = values[2]
		
		if(site in defaultFooterSites) {
			site = 'MissionNext'
		}
		
		object = 'Object Repository/MissionWorks Headers and Footers/Footer/' + site + '/' + myElement
		
		println(object)
		
		exists = WebUI.verifyElementPresent(findTestObject(object), 1, FailureHandling.OPTIONAL)
		
		if(exists) {
		
			clickAble = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
			
			println(clickAble)
			
	//		if(!clickAble) {	
	//			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	//		} else {
				WebUI.callTestCase(findTestCase('_Functions/Page Element into View'), [('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	//		}
			
			linkText = WebUI.getText(findTestObject(object))
							
			if(myType == "link" || myType == "verify") {
				
	//			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	//			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
				
				WebUI.click(findTestObject(object))
				
				WebUI.waitForPageLoad(20)
				
				driver = DriverFactory.getWebDriver()
				
				handles = driver.getWindowHandles()
				
				tabs = handles.size() 
				
				if(tabs > 1) {
					WebUI.switchToWindowIndex(1)
					WebUI.waitForPageLoad(10)
					
					WebUI.delay(2)
				}
				
				if(myType == "link") {
					
					outFile.append('\nTesting the link from "' + ele + '".\n')
					
					if(mySwitch && linkText != ele) {
					
						outFile.append('#### ERROR: The text for the ' + ele + ' link is "' + linkText + '", but should be "' + ele + '"\n')
						errorFlag = true
					}
					
					title = WebUI.getWindowTitle()
					
					if(title != myTest) {
						outFile.append('#### ERROR: The title of the page linked from ' + ele + ' is "' + title + '", but should be "' + myTest + '"\n')
						errorFlag = true
					}
					
				} else {
					
					outFile.append('\nTesting the text on the page linked from "' + ele + '".\n')
					
					found = WebUI.verifyTextPresent(myTest, false, FailureHandling.OPTIONAL)
					
					if(!found) {
						outFile.append('#### ERROR: Unable to find the text "' + myTest + '" on the page linked from ' + ele + '.\n')
						errorFlag = true
					}
				}
				
				if(tabs > 1) {
					
					WebUI.closeWindowIndex(1)
					
					WebUI.switchToWindowIndex(0)
					
				} else {
				
					WebUI.navigateToUrl(myUrl)
				}
				
			} else if(myType == 'text') {
				
				outFile.append('\nTesting the text in the "' + ele + '".\n')
					
				text = WebUI.getText(findTestObject(object))
				
				if(text != myTest) {
					outFile.append('#### ERROR: The text in element ' + myElement + ' is "' + text + '", but should be "' + myTest + '"\n')
					errorFlag = true
					txt = ''
					for(chr in text) {
						int myAsc = chr.charAt(0);
						txt = txt + myAsc + ' '
					}
					println(txt)
					txt = ''
					for(chr in myTest) {
						int myAsc = chr.charAt(0);
						txt = txt + myAsc + ' '
					}
					println(txt)
//					System.exit(0)
				}
							
			} else if(myType == 'image') {
				outFile.append('\nTesting for ' + ele + ' image.\n')
				
				visible = WebUI.verifyElementVisible(findTestObject(object), FailureHandling.OPTIONAL)
				
				if(!visible) {
					outFile.append('#### ERROR: The image ' + myElement + ' was not found on the page.\n')
					errorFlag = true
				}
			}
			
		} else {
			outFile.append('#### ERROR: The element ' + myElement + ' was not found in the footer.\n')
			errorFlag = true
		}
	}	
}
