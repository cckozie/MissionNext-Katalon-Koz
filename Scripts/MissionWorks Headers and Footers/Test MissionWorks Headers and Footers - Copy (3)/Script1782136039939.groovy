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
import groovy.io.FileType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

////////////////////////////////////
//Test for a main element in the header and footer to determine which test object set to use, and whether to bypass the test if none found

bypassMissionLinkedFooter = true

menuTest = true

logoTest = true

donateTest = true

footerTest = true

domain = GlobalVariable.domain

compare = false

varFormat = 'YY-MM-dd.HH-mm'

now = '.' + new Date().format(varFormat)

//Process tests control - Set value to 0 or 1 
tests = ['Connecting':0, 'Equipping':0, 'About Us':0, 'Logo':0, 'Donate':1, 'Footer':0]


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
	'Journey Guide' : 'https://jg.missionnext.org/', 'MissionLinked' : 'https://missionlinked.global/',
	'Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/', 'About Us' : 'https://missionworks.global/homepage/about-us/',
	'MissionConnexion' : 'https://missionconnexion.global/']

//sites = ['MissionNext' : "https://missionnext.org/"]
//sites = ['MissionLinked' : 'https://missionlinked.global/']
//sites = [ "QuickStart" : "https://quickstart.missionnext.org/quickstart-home/login-here"]
//sites = ["Education" : "https://education.missionnext.org/education-home/login-here"]
//sites = ["Journey" : "https://journey.missionnext.org/journey-home/login-here-2/"]
//sites = ['Journey Guide' : 'https://jg.missionnext.org/']
//sites = ['Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/']
//sites = ['About Us' : 'https://missionworks.global/homepage/about-us/']
//sites = ['MissionConnexion' : 'https://missionconnexion.global/']

if(sites.size() == 1) { 
	fileNameAdd = '-' + sites.keySet().first()
	myURL = sites.values().first()
	now = ''
} else {
	fileNameAdd = '-ALL'
//	myURL = 'https://missionnext.org'
}
//connexion, statement of faith, armour, about us, team and board, careers, contact us
menus = []
menus.add('Connecting')
menus.add('Equipping')
menus.add('About Us')
//menus = []
/*
if(menus.size() == 0) {
	WebUI.openBrowser(myURL)
	WebUI.maximizeWindow()
}
*/
footerElements = ["a_Careers" : ["link", "Careers – MissionWorks", true], "a_Contact Us" : ["link", "Contact Us – MissionWorks", true],
	 "a_Donate" : ["link", "MissionWorks Donation", true], "a_Privacy" : ["link", "Privacy Policy – MissionWorks", true], "a_Sign Up" : ["verify", "Email Subscription", true],
	  "a_Sponsorship" : ["link", "Sponsorship – MissionWorks", true], "i_Facebook" : ["link", "MissionWorks | Facebook", false], 
	  "i_Instagram" : ["link", "MissionWorks (@missionworksglobal) • Instagram photos and videos",false], "img_ECFA" : ["image", "", false], "img_Gold Transparency" : ["image", "", false],
	  "p_Copyright  2024-2026 MissionWorks" : ["text", "Copyright © 2024-2026 MissionWorks - All rights reserved.", false], 
	  "span_Address" : ["text", "1400 NE 136th Ave, Vancouver, WA 98684", false], "span_Email" : ["text", "Info@MissionWorks.global", false], "span_Phone" : ["text", "503.360.1865", false]]

//footerElements = ["a_Careers - Copy" : ["link", "Careers – MissionWorks", true]]

outFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + now + '.txt')

errorFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + now + '-ERRORS.txt')

outFile = new File(outFileName)

errorFile = new File(errorFileName)

GlobalVariable.outFile = outFile

outFile.write(myTestCase + ' on the ' + domain + ' domain.\n')

outFile.append('Testing sites: ' + sites.keySet() + '\n')

errorFlag = false

for(site in sites) {
	
	if(menuTest) {
	
		for(menu in menus) {
			retValue = 	testMenus(site.key, site.value, menu)
			
			if(retValue) {
				errorFlag = true
			}
		}
	}

	if(logoTest) {
		testMWLogo(site.key)
	}
	
	if(donateTest) {
		testDonate(site.key)
	}
	
	if(footerTest) {
		testFooter(site.key)
	}

}

if(errorFlag) {
	outFile.renameTo(errorFile)
	GlobalVariable.errorsFlag = true
}
	
WebUI.closeBrowser()

def testMenus(site, url, menu) {
	
	println('site is ' + site + ', url is ' + url + ', menu is ' + menu)
	
	menusMap = ['Connecting' : ['MissionConnexion', 'MissionNext','MissionGuide'],
		'Equipping' : ['Coaching', 'MissionLinked','MissionArmor', 'MissionExcellence'],
		'About Us' : ['About Us', 'About Us', 'Statement of Faith', 'Team and Board','Careers', 'Contact Us']]
	
	menuOptions = menusMap.get(menu)
	
	pageTitles = ['MissionConnexion' : 'Homepage - MissionConnexion',
		'MissionNext' : 'Serve in Missions - MissionNext.org',
		'MissionGuide' : 'Short Term Mission Trips | International and Domestic',
		
		'Coaching' : 'Journey Guides - MissionNext.org',
		'MissionLinked' : 'MissionLinked – MissionWorks',
		'MissionArmor' : 'MissionArmor – MissionWorks',
		'MissionExcellence' : 'MissionExcellence',
	
		'About Us' : 'About Us /Bakery Builder – MissionWorks',
		'About Us' : 'About Us /Bakery Builder – MissionWorks',
		'Statement of Faith' : 'Statement of Faith – MissionWorks',
		'Team and Board' : 'Team and Board – MissionWorks',
		'Careers' : 'Careers – MissionWorks',
		'Contact Us' : 'Contact Us – MissionWorks']
	
	extensions = ['MissionConnexion' : 'MissionConnexion/']
	
	folderBase = 'MissionWorks Headers and Footers/Menus/' + menu + '/'
	
	extension = extensions.get(site)
	
	if(extension == null) {
		extension = ''
	}
	
	objectFolder = folderBase + extension
	
	outFile = GlobalVariable.outFile
	
	if(outFile == '') {
		outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Testing ' + menu + ' Menu Options.txt')
		outFile.write('     Testing ' + menu + ' Menu Options\n')
	}
	
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
	
	outFile.append('\n_________________________________________________________________________________________________\n')
	
	println('My menu is ' + menu)
	
	outFile.append('\n<<< TESTING THE ' + menu.toUpperCase() + ' MENU ON ' + site + ' >>>\n')
	
	errorFlag = false
	
	optionCount = 0
	
	for(option in menuOptions) {
		
		WebUI.navigateToUrl(url)
		
		if(site == 'MissionLinked') {
			WebUI.mouseOver(findTestObject('Object Repository/MissionWorks Headers and Footers/Header/img_MissionWorks logo'))
			WebUI.delay(2)
		}
		
		if(menu == 'About Us' && optionCount == 0) {
			
			outFile.append('\nTesting the link on the About Us menu header on ' + site + '.\n')
	
		} else {
				
			outFile.append('\nTesting ' + option + ' option on the ' + menu + ' menu on ' + site + '.\n')
			
		}
		
		println('>>> clicking sub-arrow')
	
		arrowClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'span_' + menu + ' sub-arrow'), 1)
		
		if(arrowClickable) {
			
			WebUI.click(findTestObject(objectFolder + 'span_' + menu + ' sub-arrow'))
			
			if(menu == 'About Us' && option == 'About Us' && optionCount == 0) { //This tests clicking on the About Us menu text, not the About Us menu option
				
				WebUI.click(findTestObject(objectFolder + 'span_' + menu + ' sub-arrow'))
				
				optionCount++
				
			} else {
			
				println('>>> clicking menu option ' + option)
			
				WebUI.delay(1)
			
				optionclickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'a_' + option), 1)
				
				if(optionclickable) {
					
					linkText = WebUI.getText(findTestObject(objectFolder + 'a_' + option))
					
					if(linkText != option) {
						
						outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
						errorFlag = true
					}
		
					WebUI.click(findTestObject(objectFolder + 'a_' + option))
					
					println('<<< getting page title')
					
					title = WebUI.getWindowTitle()
					
					println(title)
					
					if(title != pageTitles.get(option)) {
						outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
						errorFlag = true
					}
					
				} else {
					outFile.append('#### ERROR: Unable to click on ' + option + ' option.\n')
					errorFlag = true
				}
			}
		} else {
			outFile.append('#### ERROR: Unable to click on down arrow.\n')
			errorFlag = true
		}
		
		WebUI.delay(1)
	}
	
	WebUI.navigateToUrl(url)
		
	return errorFlag
	
}

def testMWLogo(site) {
	
	extensions = ['MissionConnexion' : 'MissionConnexion/']
	
	objectBase = 'Object Repository/MissionWorks Headers and Footers/Header/'
	
	if(extensions.get(site) == null) {
		objectPath = objectBase
	} else {
		objectPath = objectBase + site + '/'
	}
	
	myUrl = WebUI.getUrl()
	
	outFile.append('\n< Testing the MissionWorks logo option on ' + site + '.>\n')
	
	exists = WebUI.verifyElementPresent(findTestObject(objectPath + 'img_MissionWorks logo'), 1, FailureHandling.OPTIONAL)
	
	if(exists) {
	
		clickable = WebUI.verifyElementClickable(findTestObject(objectPath + 'img_MissionWorks logo'), FailureHandling.OPTIONAL)
		
		if(clickable) {
		
			WebUI.click(findTestObject(objectPath + 'img_MissionWorks logo'))
			
			WebUI.waitForPageLoad(20)
			
			driver = DriverFactory.getWebDriver()
			
			handles = driver.getWindowHandles()
			
			tabs = handles.size() 
			
			if(tabs > 1) {
				WebUI.switchToWindowIndex(1)
				WebUI.waitForPageLoad(10)
				WebUI.delay(2)
				outFile.append('#### ERROR: Clicking the MissionWorks Logo on ' + site + ' opens a second tab.\n')
				errorFlag = true
			}
			
			
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
			
			if(tabs > 1) {
				WebUI.closeWindowIndex(1)
				WebUI.switchToWindowIndex(0)
			}
		} else {
			outFile.append('#### ERROR: Unable to click on MissionWorks logo.\n')
			errorFlag = true
		}

	} else {
		outFile.append('#### ERROR: Unable to find the MissionWorks logo.\n')
		errorFlag = true
	}

	WebUI.navigateToUrl(myUrl)
}


def testDonate(site) {
	
	println(site)
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		siteURL = sites.get(site)
		println(siteURL)
		WebUI.navigateToUrl(siteURL)
	}
	
	extensions = ['MissionConnexion' : 'MissionConnexion/']
	
	objectBase = 'Object Repository/MissionWorks Headers and Footers/Header/'
	
	if(extensions.get(site) == null) {
		objectPath = objectBase
	} else {
		objectPath = objectBase + site + '/'
	}
	
	myUrl = WebUI.getUrl()
	
	outFile.append('\n< Testing Donate option on ' + site + '.>\n')
	
	exists = WebUI.verifyElementPresent(findTestObject(objectPath + 'a_Donate'), 1, FailureHandling.OPTIONAL)
	
	if(exists) {
	
		clickable = WebUI.verifyElementClickable(findTestObject(objectPath + 'a_Donate'), FailureHandling.OPTIONAL)
		
		if(clickable) {
	
			WebUI.click(findTestObject(objectPath + 'a_Donate'))
			
			WebUI.waitForPageLoad(20)
			
			WebUI.delay(5)
			
			title = WebUI.getWindowTitle()
			
			println(title)
			
			if(title != 'MissionWorks Donation') {
				outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks Donation' + '"\n')
				errorFlag = true
			}
		} else {
			outFile.append('#### ERROR: Unable to click on the Donation link.\n')
			errorFlag = true
		}
	} else {
		outFile.append('#### ERROR: Unable to find the Donation link.\n')
		errorFlag = true
	}
	
	if(!windowIndex == null) {
		WebUI.navigateToUrl(myUrl)
	} else {
		WebUI.closeBrowser()
	}
}

def testFooter(site) {
	
	if(site == 'MissionLinked' && bypassMissionLinkedFooter) {
		return
	}
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		siteURL = sites.get(site)
		println(siteURL)
		WebUI.navigateToUrl(siteURL)
	}
	
	//These sites use the default (MissionNext) footer element definitions
	extensions = ['Journey' : 'Custom1/', 'Education' : 'Custom1/', 'QuickStart' : 'Custom1/', 'Journey Guide' : 'Custom1/', 'About Us' : 'Custom1/']
	
	outFile.append('\n_________________________________________________________________________________________________\n')

	outFile.append('\n<<< TESTING THE PAGE FOOTER ON ' + site + ' >>>\n')
	
	extension = extensions.get(site, '')
	
	objectPath = 'Object Repository/MissionWorks Headers and Footers/Footer/' + extension
	
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
				
		object = objectPath + myElement
		
		println(object)
		
		exists = false
		
//		element = WebUiCommonHelper.findWebElement(findTestObject(testObject), 1)
		
		try {
			//exists = WebUI.verifyElementPresent(findTestObject(object), 1, FailureHandling.OPTIONAL)
			element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
			exists = true
		} catch (error) {
			println('oops')
		}
		
		if(exists) {
		
			clickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
			
			println(clickable)
			
			WebUI.callTestCase(findTestCase('_Functions/Page Element into View'), [('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			
			linkText = WebUI.getText(findTestObject(object))
							
			if(myType == "link" || myType == "verify") {
				
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
				
				// This code will replace the utf-8 dash with an ascii dash
				newText = ''
				
				txt = ''

				for(i = 0; i < text.length(); i++) {
					chr = text[i]
					int myAsc = chr
					println('chr:' + chr + ', myAsc:' + myAsc)
					println(myAsc)
					txt = txt + myAsc + ' '
					if(myAsc == 8211) {
						chr = '-'
						println('replaced utf with - at position ' + i)
					}
					newText += chr
				}
				
				text = newText

				if(text != myTest) {
					outFile.append('#### ERROR: The text in element ' + myElement + ' is "' + text + '", but should be "' + myTest + '"\n')
					errorFlag = true
				}
/*
					txt = ''
					for(chr in text) {
						int myAsc = chr.charAt(0);
						if(myAsc == 8211) {
							println('found it!')
							System.exit(0)
						}
						txt = txt + myAsc + ' '
					}
					println(txt)
					txt1 = ''
					for(chr in myTest) {
						int myAsc = chr.charAt(0);
						txt1 = txt1 + myAsc + ' '
					}
					println(txt)
					println(txt1)
					if(txt == txt1) {
						println('yes') 
					} else {
						println('no')
					}
				}
*/
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
	
	if(!windowIndex == null) {
		WebUI.navigateToUrl(myUrl)
	} else {
		WebUI.closeBrowser()
	}
}

