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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.io.File as File
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import groovy.io.FileType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.interactions.Actions;

////////////////////////////////////////////////////
// Test for mobile
// Test size on social media images
// Test for link on the email address


// first letter of tests; menus, logo, donate, footer
myTests = 'mldf'
//myTests = 'f'

tests = ['menuTest':false, 'logoTest':false, 'donateTest':false, 'footerTest':false]

for(test in tests) {
	println(test.key)
	if(myTests.contains(test.key[0])) {
		tests.put(test.key, true)
	}
}
fontWeights = []

domain = GlobalVariable.domain

compare = false

varFormat = 'YY-MM-dd.HH-mm'

now = '.' + new Date().format(varFormat)

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
	'MissionConnexion' : 'https://missionconnexion.global/', 'MissionGuide' : 'https://missionguide.global/']

//sites = ['MissionNext' : "https://missionnext.org/"]
//sites = ['MissionLinked' : 'https://missionlinked.global/']
//sites = ["QuickStart" : "https://quickstart.missionnext.org/quickstart-home/login-here"]
//sites = ["Education" : "https://education.missionnext.org/education-home/login-here"]
//sites = ["Journey" : "https://journey.missionnext.org/journey-home/login-here-2/"]
//sites = ['Journey Guide' : 'https://jg.missionnext.org/']
//sites = ['Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/']
//sites = ['About Us' : 'https://missionworks.global/homepage/about-us/']
//sites = ['MissionConnexion' : 'https://missionconnexion.global/']
//sites = ['MissionGuide' : 'https://missionguide.global/']

if(sites.size() == 1) { 
	fileNameAdd = '-' + sites.keySet().first()
	myURL = sites.values().first()
	now = ''
} else {
	fileNameAdd = '-ALL'
}

menus = []
menus.add('Connecting')
menus.add('Equipping')
menus.add('About Us')
//menus = []

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

objectsFile = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Headers and Footers/Used Test Objects.txt')

objectsFile.write('\n')

GlobalVariable.outFile = outFile

outFile.write(myTestCase + ' on the ' + domain + ' domain.\n')

outFile.append('Testing sites: ' + sites.keySet() + '\n')

errorFlag = false

arrow = ''

myObjectFolder = ''

printFolders = false

usedFolders = []

objects = []

for(site in sites) {
	
	if(tests.get('menuTest')) {
		
		for(menu in menus) {
			retValue = 	testMenus(site.key, site.value, menu)
			
			if(retValue) {
				errorFlag = true
				
			}
		}
	}

	if(tests.get('logoTest')) {
		testMWLogo(site.key)
	}
	
	if(tests.get('donateTest')) {
		testDonate(site.key)
	}
	
	if(tests.get('footerTest')) {
		testFooter(site.key)
	}

}

if(errorFlag) {
	outFile.renameTo(errorFile)
	GlobalVariable.errorsFlag = true
}
	
WebUI.closeBrowser()

fontWeights.each {
	println(it)
}

println('################# USED FOLDERS #################')
usedFolders.each {
	println(it)
}

println('################# USED OBJECTS #################')
objects.each {
	println(it)
}

def testPageTitle(option) {
	
	println('<<< getting page title')
	
	linkedURL = WebUI.getUrl().toLowerCase()
	
	if(linkedURL.contains('wordpress')) {
		outFile.append('#### ERROR: Linked to page is in the sandbox.\n')
		errorFlag = true
	}
	
	title = WebUI.getWindowTitle()
	
	println(title)
	
	if(title != pageTitles.get(option)) {
		outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
		errorFlag = true
	}
}

def testForObjectExists(objectFolder, testObject) {
	
	driver = DriverFactory.getWebDriver()
	
	object = objectFolder + testObject
	
	println(object)
	
	// 1. Fetch the object from your Object Repository
	TestObject myObject = findTestObject(object)
	
	if(myObject != null) {
	
		// 2. Retrieve its compiled XPath locator
		String objectXpath = myObject.getSelectorCollection().get(SelectorMethod.XPATH)
		
		// 3. Print the output to the Console log
		println("The element XPath is: " + objectXpath)
		
		// By.xpath or By.cssSelector bypassing Katalon's repository tracking
		exists = !driver.findElements(By.xpath(objectXpath)).isEmpty()
		
		if(exists) {
			println('Returning true')
			return true
		}
	}
	return false
}


def getObjectFolder(folderBase, testObject) {
	
	testObjectFolders = ['/', '/Custom1/', '/Custom2/', '/Custom3/']
	
	for(folder in testObjectFolders) {
		
		objectFolder = folderBase + folder
		
		exists = testForObjectExists(objectFolder, testObject)
		
		println(exists)
		
		if(exists) {
			
			if(!usedFolders.contains(folder)) {
				usedFolders.add(objectFolder)
			}
			
			return objectFolder
			
		}
	}

	return null
}

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
	
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
	
	outFile.append('\n_________________________________________________________________________________________________\n')
	
	println('My menu is ' + menu)
	
	outFile.append('\n<<< TESTING THE ' + menu.toUpperCase() + ' MENU ON ' + site + ' >>>\n')
	
	outFile = GlobalVariable.outFile
	
	if(outFile == '') {
		outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Testing ' + menu + ' Menu Options.txt')
		outFile.write('     Testing ' + menu + ' Menu Options\n')
	}
	
	optionCount = 0
	
	first = true
	
	for(option in menuOptions) {
		
		WebUI.navigateToUrl(url)
		
		// This curson action is needed for the MissionLinked site where the menu drop down arrows do not appear until after a cursor movement
		if(site == 'MissionLinked') {
			
			driver = DriverFactory.getWebDriver()
			
			List<WebElement> donateElements = driver.findElements(By.xpath("//a[contains(text(), 'Donate')]"))
			
			if(donateElements.size() > 0) {
				
				Actions actions = new Actions(driver);
				
				actions.moveToElement(donateElements[0]).perform();
			}
		}
		WebUI.waitForPageLoad(30)
		
		WebDriver driver = DriverFactory.getWebDriver()
		
		folderBase = 'MissionWorks Headers and Footers/Menus/' + menu
		
		if(first) {
			
			first = false
		
			arrow = 'span_' + menu + ' sub-arrow'
			
			println(folderBase + ';' + arrow)
			
			myObjectFolder = getObjectFolder(folderBase, arrow)
			
			println(myObjectFolder)
			
			if(myObjectFolder != null) {
				
				println('myObjectFolder is ' + myObjectFolder)
			
			} else {
				println("###NOT ABLE TO FIND OBJECT FOLDER")
				
				outFile.append('#### ERROR: Unable to find object folder.\n')
	
				errorFlag = true
				
				return
			}
			
			println('my arrow is ' + myObjectFolder + arrow)
			
			arrow = myObjectFolder + arrow
			
			objectsFile.append(arrow + '\n')
			
			if(!objects.contains(arrow)) {
				objects.add(arrow)
			}
			
		}
		
		if(arrow != null) {
		
			println(myObjectFolder)
						
			object = myObjectFolder + 'a_' + option
			
			objectsFile.append(object + '\n')
			
			if(!objects.contains(object)) {
				objects.add(object)
			}
			
			println(object)
			
			if(menu == 'About Us' && optionCount == 0) {
				
				outFile.append('\nTesting the link on the About Us menu header on ' + site + '.\n')
		
			} else {
					
				outFile.append('\nTesting ' + option + ' option on the ' + menu + ' menu on ' + site + '.\n')
				
			}
			
			println(arrow) 
			
//			arrowClickable = WebUI.waitForElementClickable(findTestObject(arrow), 1)
			arrowClickable = WebUI.verifyElementClickable(findTestObject(arrow), FailureHandling.OPTIONAL)			
			if(arrowClickable) {
				
				println('>>> clicking menu down arrow')
				
				WebUI.click(findTestObject(arrow))
				
				if(menu == 'About Us' && option == 'About Us' && optionCount == 0) { //This tests clicking on the About Us menu text, not the About Us menu option
					
					WebUI.click(findTestObject(arrow))
					
					optionCount++
					
					testPageTitle(option)
/*					
					println('<<< getting page title')
					
					title = WebUI.getWindowTitle()
					
					println(title)
					
					if(title != pageTitles.get(option)) {
						outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
						errorFlag = true
					}
*/					
				} else {
				
					WebUI.delay(1)
				
//					optionclickable = WebUI.waitForElementClickable(findTestObject(object), 1)		
					optionclickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)	
							
					if(optionclickable) {
						
						println('Testing the option background color')
						
						bgColor = WebUI.getCSSValue(findTestObject(objectFolder + 'a_' + option), 'background-color')
						
						if(bgColor != 'rgba(0, 0, 0, 0)') {
				
							outFile.append('#### ERROR: The background color for the ' + option + ' link is ' + bgColor + ', instead of white.\n')
							errorFlag = true						
						}
						
//						WebUI.mouseOver(findTestObject(object), FailureHandling.OPTIONAL)
					
						println('Testing the option text')
						
						linkText = WebUI.getText(findTestObject(object))
						
						if(linkText != option) {
							
							outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
							errorFlag = true
						}
											
						println('>>> clicking menu option ' + option)
				
						WebUI.click(findTestObject(object))
						
						testPageTitle(option)
/*						
						println('<<< getting page title')
						
						title = WebUI.getWindowTitle()
						
						println(title)
						
						if(title != pageTitles.get(option)) {
							outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
							errorFlag = true
						}
*/						
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
			
		} else {
			outFile.append('#### ERROR: Unable to find down arrow.\n')
			errorFlag = true
		}
		
		if(site == 'MissionGuide') {
			WebUI.closeBrowser()
			
			WebUI.openBrowser('')
			
			WebUI.maximizeWindow()
		}
					
	}
	
//	WebUI.navigateToUrl(url)

	return errorFlag	
}

def testMWLogo(site) {
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)

	if(windowIndex == null) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	println('<<< Navigating to ' + siteURL)
	
	outFile.append('\n< Testing the MissionWorks logo option on ' + site + '.>\n')
	
	WebUI.navigateToUrl(siteURL)
	
	WebUI.delay(1)
	
	testObject = 'img_MissionWorks logo'
	
	folderBase = 'MissionWorks Headers and Footers/Header'
	
	myObjectFolder = getObjectFolder(folderBase, testObject)
	
	println(myObjectFolder)
	
	if(myObjectFolder != null) {
		
		println('myObjectFolder is ' + myObjectFolder)
		
	} else { 
	
		outFile.append('\n#### ERROR: Unable to find the MissionWorks logo in any test object folder.\n')
		
		errorFlag = true
		
		return
	}
	
	exists = testForObjectExists(myObjectFolder, testObject)

	if(exists) {
		
		objectsFile.append(myObjectFolder + testObject + '\n')
	
		if(!objects.contains(myObjectFolder + testObject)) {
			objects.add(myObjectFolder + testObject)
		}
			
		clickable = WebUI.verifyElementClickable(findTestObject(myObjectFolder + 'img_MissionWorks logo'), FailureHandling.OPTIONAL)
		
		if(clickable) {
		
			WebUI.click(findTestObject(myObjectFolder + 'img_MissionWorks logo'))
			
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

	WebUI.closeBrowser()
}


def testDonate(site) {
	
	println(site)
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	WebUI.navigateToUrl(siteURL)
	
	outFile.append('\n< Testing Donate option on ' + site + '.>\n')
	
	testObject = 'a_Donate'
	
	folderBase = 'MissionWorks Headers and Footers/Header'
	
	myObjectFolder = getObjectFolder(folderBase, testObject)
	
	println(myObjectFolder)
	
	if(myObjectFolder != null) {
		
		println('myObjectFolder is ' + myObjectFolder)
		
	} else {
	
		outFile.append('#### ERROR: Unable to find the MissionWorks logo in any test object folder.\n')
		
		errorFlag = true
		
		return
	}
	
	exists = testForObjectExists(myObjectFolder, testObject)
	
	if(exists) {
		
		objectsFile.append(myObjectFolder + testObject + '\n')
		
		if(!objects.contains(myObjectFolder + testObject)) {
			objects.add(myObjectFolder + testObject)
		}
		
		clickable = WebUI.verifyElementClickable(findTestObject(myObjectFolder + testObject), FailureHandling.OPTIONAL)
		
		if(clickable) {
	
			WebUI.click(findTestObject(myObjectFolder + testObject))
			
			WebUI.waitForPageLoad(20)
			
			WebUI.delay(2)
			
//			testPageTitle()
			
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
	
	WebUI.closeBrowser()
}

def testFooter(site) {
	
	fontTests = ['Careers': 0, 'Copyright':0, 'Email':0]
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	WebUI.navigateToUrl(siteURL)
	
	if(site == 'MissionLinked') {
	
		driver = DriverFactory.getWebDriver()
		
		List<WebElement> donateElements = driver.findElements(By.xpath("//a[contains(text(), 'Donate')]"))
		
		if(donateElements.size() > 0) {		

			actions = new Actions(driver);
			
			actions.moveToElement(donateElements[0]).perform();
	
		}
	}
	
	WebUI.waitForPageLoad(30)
	
	outFile.append('\n_________________________________________________________________________________________________\n')

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
		
		folderBase = 'MissionWorks Headers and Footers/Footer'
		
		myObjectFolder = getObjectFolder(folderBase, myElement)
		
		println(myObjectFolder)
		
		if(myObjectFolder != null) {
			
			println('myObjectFolder is ' + myObjectFolder)
			
		} else {
		
			outFile.append('#### ERROR: Unable to find ' + myElement + ' in any test object folder.\n')
			
			errorFlag = true
		}
	
				
		object = myObjectFolder + myElement
		
		println(object)
		
		exists = testForObjectExists(myObjectFolder, myElement)
		
		if(exists) {
		
			clickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
			
			println(clickable)
			
			WebUI.callTestCase(findTestCase('_Functions/Page Element into View'), [('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			
//			WebUI.delay(5)
			
			WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
			
			linkText = WebUI.getText(findTestObject(object))
			
			for(test in fontTests) {
				
				if(linkText.contains(test.key)) {
					
					fontSize = WebUI.getCSSValue(findTestObject(object), 'font-size')
					
					println(fontSize)
					
					sizeNum = fontSize.replace('px', '')
					
					size = Float.parseFloat(sizeNum)
					
					if(size <= 14) {
						fontTests.put(test.key, size)
						errorFlag = true
					}
					
					fontWeight = WebUI.getCSSValue(findTestObject(object), 'font-weight')
					
					fontWeights.add(site + ':' + linkText + ':' + fontWeight)
					
					println(fontWeight)
					

				}
			}
							
			if(myType == "link" || myType == "verify") {
				
				WebUI.click(findTestObject(object))
				
				WebUI.waitForPageLoad(20)
				
				driver = DriverFactory.getWebDriver()
				
				handles = driver.getWindowHandles()
				
				tabs = handles.size() 
				
				if(tabs > 1) {
					WebUI.switchToWindowIndex(1)
						WebUI.waitForPageLoad(10, FailureHandling.OPTIONAL)
						
						WebUI.delay(2)
				}
				
				if(myType == "link") {
					
					outFile.append('\nTesting the link from "' + ele + '".\n')
					
					objectsFile.append(myObjectFolder + ele + '\n')
					
					if(!objects.contains(myObjectFolder + ele)) {
						objects.add(myObjectFolder + ele)
					}
					
					if(printFolders) {
						outFile.append('----- Using ' + myObjectFolder + '.\n')
					}
					
					if(mySwitch && linkText != ele) {
					
						outFile.append('#### ERROR: The text for the ' + ele + ' link is "' + linkText + '", but should be "' + ele + '"\n')
						errorFlag = true
					}
					
					println(myTest)
					
					title = WebUI.getWindowTitle()
					
					if(title != myTest) {
						outFile.append('#### ERROR: The title of the page linked from ' + ele + ' is "' + title + '", but should be "' + myTest + '"\n')
						errorFlag = true
					}
					
					fSize = fontTests.get(ele, null)
					
					if(fSize != null && fSize > 0) {
						if(ele == 'Careers') {
							
							outFile.append('#### ERROR: The font size for the footer menu is too small (' + fontSize + ')\n')
						}
					}
				} else {
					
					outFile.append('\nTesting the text on the page linked from "' + ele + '".\n')
					
					objectsFile.append(myObjectFolder + ele + '\n')
					
					if(!objects.contains(myObjectFolder + ele)) {
						objects.add(myObjectFolder + ele)
					}
					
					if(printFolders) {
						outFile.append('----- Using ' + myObjectFolder + '.\n')
					}
				
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
					if(site == 'MissionGuide') {
						WebUI.closeBrowser()
						
						WebUI.openBrowser('')
						
						WebUI.maximizeWindow()
					}
			
					WebUI.navigateToUrl(myUrl)
				}
				
			} else if(myType == 'text') {
				
				outFile.append('\nTesting the text in the "' + ele + '".\n')
					
				objectsFile.append(myObjectFolder + ele + '\n')
				
				if(!objects.contains(myObjectFolder + ele)) {
					objects.add(myObjectFolder + ele)
				}
					
				if(printFolders) {
					outFile.append('----- Using ' + myObjectFolder + '.\n')
				}
				
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
				
				fSize = fontTests.get(ele, null)
				
				println('>>>>>>>>>>>> font size is ' + fSize + ' for ' + ele)
					
				if(fSize != null && fSize > 0) {
					if(ele == 'Email') {
						
						outFile.append('#### ERROR: The font size for the contact info is too small (' + fontSize + ')\n')
						
					} else if(ele == 'Copyright') {
						
						outFile.append('#### ERROR: The font size for the copyright is too small (' + fontSize + ')\n')
			
					}
				}

				
			} else if(myType == 'image') {
				outFile.append('\nTesting for ' + ele + ' image.\n')
				
				objectsFile.append(myObjectFolder + ele + '\n')
					
				if(!objects.contains(myObjectFolder + ele)) {
					objects.add(myObjectFolder + ele)
				}
					
				if(printFolders) {
					outFile.append('----- Using ' + myObjectFolder + '.\n')
				}
				
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
		if(site == 'MissionGuide') {
			WebUI.closeBrowser()
			
			WebUI.openBrowser('')
			
			WebUI.maximizeWindow()
		}

		WebUI.navigateToUrl(myUrl)
		
	} else {
		WebUI.closeBrowser()
	}
	
}

