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
import java.io.File
import javax.lang.model.util.Elements
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
import org.sikuli.script.*
import org.openqa.selenium.JavascriptExecutor;
import javax.swing.*


calledBySikuli = false


//#############################################\\
//////// FOR TEST BEING CALLED BY SIKULIX \\\\\\\
// ENTER SITE HERE


/////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\
//#############################################\\

// first letter of tests; menus, logo, donate, footer, header links on child (linked to) pages
myTests = 'mldfh'
//myTests = 'm'

// first letter of menu tests; connecting, equipping, about us
myMenus = 'cea'
//myMenus = 'c'

mySites = [
	'MissionNext',
	'Journey',
	'Education',
	'QuickStart',
	'Journey Guide',
	'MissionLinked',
	'Coaching',
	'About Us',
	'MissionConnexion',
	'MissionGuide',
	'Statement of Faith',
	'Team and Board',
	'Careers',
	'Contact Us',
	'MissionExcellence',
	'homepage2'
	]

fontTest = false

screenshots = false

maxImageTries = 3

if(calledBySikuli) {
	imageTestOnly = true
	printMatchPercentage = true
	screenshots = true
	myTests = 'f'
} else {
	imageTestOnly = false
	printMatchPercentage = false
}

imageMatchMinimum = 50

screensize = 'full'

if(calledBySikuli){
	screensize = 'full'
}

printFolders = false

printImageSize = false

printTestObject = false

sendErrorsEmail = false
'######################################################################'
'######################################################################'
'######################################################################'

//devices = ['iPhoneSE':[375,667], 'iPadAir':[820,1180]]
devices = ['phone':[375,667,'iPhoneSE'], 'tablet':[820,1180,'iPadAir']]

if(screensize != 'full') {
	sSize = devices.get(screensize)
	sWidth = sSize[0]
	sHeight = sSize[1]
}

tests = ['menuTest':false, 'logoTest':false, 'donateTest':false, 'footerTest':false, 'headerLinksTest':false]

for(test in tests) {
	println(test.key)
	if(myTests.contains(test.key[0])) {
		tests.put(test.key, true)
	}
}

menus = []
if(myMenus.contains('c')) {
	menus.add('Connecting')
}

if(myMenus.contains('e')) {
	menus.add('Equipping')
}

if(myMenus.contains('a')) {
	menus.add('About Us')
}

screenObjectFolders = ['full' : 'MissionWorks Headers and Footers/', 'phone' : 'MissionWorks Headers and Footers - Hamburger/',
	 'tablet' : 'MissionWorks Headers and Footers - Hamburger/' ]

screenFolderBase = screenObjectFolders.get(screensize)

myObjectFolder = ''

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

sites = ['MissionNext' : "https://missionnext.org/", "Journey" : "https://journey.missionnext.org", 
	"Education" : "https://education.missionnext.org", "QuickStart" : "https://quickstart.missionnext.org",
	'Journey Guide' : 'https://jg.missionnext.org/', 'MissionLinked' : 'https://missionlinked.global/',
	'Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/', 'About Us' : 'https://missionworks.global/homepage/about-us/',
	'MissionConnexion' : 'https://missionconnexion.global/', 'MissionGuide' : 'https://missionguide.global/',
	'Statement of Faith' : 'https://missionworks.global/statement-of-faith/', 'Team and Board' : 'https://missionworks.global/team-and-board/',
	'Careers' : 'https://missionworks.global/careers/', 'Contact Us' : 'https://missionworks.global/contact-us/',
	'MissionExcellence' : 'https://missionexcellence.global/', 'homepage2' : 'https://missionworks.global/homepage2/']

// ********** TO TEST JUST ONE SITE REMOVE THE COMMENT INDICATION ON ONE OF THESE LINES *********
//sites = ['MissionNext' : "https://missionnext.org/"]
//sites = ["Journey" : "https://journey.missionnext.org/journey-home/login-here-2/"]
//sites = ["Education" : "https://education.missionnext.org/education-home/login-here"]
//sites = ["QuickStart" : "https://quickstart.missionnext.org/quickstart-home/login-here"]
//sites = ['Journey Guide' : 'https://jg.missionnext.org/']
//sites = ['MissionLinked' : 'https://missionlinked.global/']
//sites = ['Coaching' : 'https://missionnext.org/homepage/goer/resources-for-goers/journey-guides/']
//sites = ['About Us' : 'https://missionworks.global/homepage/about-us/']
//sites = ['MissionConnexion' : 'https://missionconnexion.global/']
//sites = ['MissionGuide' : 'https://missionguide.global/']
//sites = ['Statement of Faith' : 'https://missionworks.global/statement-of-faith/']
//sites = ['Team and Board' : 'https://missionworks.global/team-and-board/']
//sites = ['Careers' : 'https://missionworks.global/careers/']
//sites = ['Contact Us' : 'https://missionworks.global/contact-us/']
//sites = ['MissionExcellence' : 'https://missionexcellence.global/']
//sites = ['homepage2' : 'https://missionworks.global/homepage2/']

if(sites.size() > 1 && imageTestOnly) {
	sites = imageTestSite
}

if(sites.size() == 1) { 
	fileNameAdd = '-' + sites.keySet().first()
	myURL = sites.values().first()
	now = ''
} else {
	fileNameAdd = '-ALL'
}
println(sites)

if(screensize != 'full') {
	fileNameAdd += '-' + screensize
}

////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


footerElements = ["a_Careers" : ["link", "Careers – MissionWorks", true], "a_Sponsorship" : ["link", "Sponsorship – MissionWorks", true],
	 "a_Sign Up" : ["link", "Email Subscription", true],  "a_Privacy" : ["link", "Privacy Policy – MissionWorks", true],
	 "a_Donate" : ["link", "MissionWorks Donation", true], "a_Contact Us" : ["link", "Contact Us – MissionWorks", true],
	 "span_Phone" : ["text", "503.360.1865", false], "span_Email" : ["text", "Info@MissionWorks.global", false],
	 "span_Address" : ["text", "1400 NE 136th Ave, Vancouver, WA 98684", false],
	 "img_Gold Transparency" : ["image", "", false], "img_ECFA" : ["image", "", false], 
	 "p_Copyright  2024-2026 MissionWorks" : ["text", "Copyright © 2024-2026 MissionWorks - All rights reserved.", false],
	 "i_Facebook" : ["link", "MissionWorks | Facebook", false], 
	 "i_Instagram" : ["link", "MissionWorks (@missionworksglobal) • Instagram photos and videos",false]]

myImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/MW_Footer/'

outFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + now + '.txt')

errorFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + now + '-ERRORS.txt')

errorsOnlyFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + fileNameAdd + now + '-ERRORS ONLY.txt')


if(imageTestOnly) {
	outFileName = outFileName.replace('.txt', '-Match Percentages.txt')
	errorFileName = errorFileName.replace('.txt', '-Match Percentages.txt')
	imageMatchFileName = (GlobalVariable.reportPath + myTestCase + ' on ' + domain + ' Image Match ' + now + '.csv')
	matchFile = new File(imageMatchFileName)
}


outFile = new File(outFileName)

GlobalVariable.outFile = outFile

errorFile = new File(errorFileName)

errorsOutFile = new File(errorsOnlyFileName)

GlobalVariable.errorsOutFile = errorsOutFile

//GlobalVariable.errorsOutFile = errorsOutFile

objectsFile = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Headers and Footers/Used Test Objects.txt')

siteList = []
count = 0
outText = ''
for(site in sites) {
	if(site.key in mySites) {
		outText += site.key + ', '
		count++
		if(count > 10) {
			outText += '\n           '
			count = 0
		}
	}
}

outFile.write(myTestCase + ' on the ' + domain + ' domain.\n')

outFile.append('\nTesting sites: ' + outText + '\n')

errorsOutFile.write(myTestCase + ' on the ' + domain + ' domain.\n')

errorsOutFile.append('\nTesting sites: ' + outText + '\n')

errorFlag = false

arrow = ''

myObjectFolder = ''

site = ''

noHeaderFlag = false

usedFolders = []

objects = []

lastFolder = ''

lastObjectFound = false 

currentElement = ''

// [menu/footer option|site : font size, font weight, font family]
fontDetails = [:]

// [image|site : width, height]
imageSizes = [:]

// [image|site : match percentage]
imageMatches = [:]

firstSite = true

firstOpen = true

for(site in sites) {
	
	if(site.key in mySites) {
	
		outFile.append('\n\n     <<<<<<<<<<<<<<<<<<< Testing ' + site.key + ' >>>>>>>>>>>>>>>>>>>>>>')
		
		errorsOutFile.append('\n\n    <<<<<<<<<<<<<<<<<<< Testing ' + site.key + ' >>>>>>>>>>>>>>>>>>>>>>')
		
		println(tests.get('menuTest'))
		
		if(tests.get('menuTest')) {
		
			
			for(menu in menus) {
				retValue = 	testMenus(site.key, site.value, menu)
				
				if(retValue == 'no header') {
					noHeaderFlag = true
					break
				}
			}
		}
	
		if(tests.get('logoTest') && noHeaderFlag == false) {
			testMWLogo(site.key)
		}
		
		if(tests.get('donateTest') && noHeaderFlag == false) {
			testDonate(site.key)
		}
		
		if(tests.get('footerTest')) {
			testFooter(site.key)
		}
		
		if(tests.get('headerLinksTest')) {
			
			siteURL = site.value
			
			bypassSites = ['About Us','Statement of Faith','Team and Board','homepage2','MissionConnexion','MissionGuide','MissionLinked','MissionWorks']
			
			if(siteURL.contains('global') && (!bypassSites.contains(site.key) || screensize == 'full')) {
				
				WebUI.openBrowser('')
				
				if(screensize == 'full') {
					WebUI.maximizeWindow()
					if(firstOpen) {
						sHeight = WebUI.getViewportHeight()
						sWidth = WebUI.getViewportWidth()
						firstOpen = false
					}
				} else {
					WebUI.setViewPortSize(sWidth, sHeight)
				}
				
				navigateToUrl(site.value)
				
				errors = WebUI.callTestCase(findTestCase('MissionWorks Headers and Footers/Test MissionWorks Header Menu'), [('varSite'):site.key, ('varScreensize'):[screensize,sWidth,sHeight]], FailureHandling.STOP_ON_FAILURE)
				
				if(errors) {
					errorFlag = true
				}
			}
		}
		
		allFalse = tests.every { it.value == false }
		
		if(allFalse) { //Just load all of the sites (used to watch for header anomolies)
			if(firstSite) {
				WebUI.openBrowser('')
				if(screensize == 'full') {
					WebUI.maximizeWindow()
					if(firstOpen) {
						sHeight = WebUI.getViewportHeight()
						sWidth = WebUI.getViewportWidth()
						firstOpen = false
					}
				} else {
					WebUI.setViewPortSize(sWidth, sHeight)
				}
			}
			navigateToUrl(site.value)
			if(firstSite) {
				JFrame frame = new JFrame("User Input Frame")
				frame.setFocusableWindowState(true)
				frame.requestFocus()
				frame.setAlwaysOnTop (true)
				instructions = "Open developer tools,\nThrottle the network to Slow 4G\n and minimize the tools window width."
				String entry = JOptionPane.showInputDialog(frame, instructions)
				firstSite = false
			}
		}
	}
}

if(!imageTestOnly) {
	if(fontTest) {
		outFile.append('\n<<<<< Font Sizes and Weights >>>>>\n')
		sortedDetails = fontDetails.sort()
		sortedDetails.each {
			outFile.append(it.key + ', ' + it.value[0] + ', ' + it.value[1] + ', ' + it.value[2] + '\n')
		}
	}
	
	if(printImageSize) {
		outFile.append('\n<<<<< Image Sizes >>>>>\n')
		sortedImages = imageSizes.sort()
		sortedImages.each {
			outFile.append(it.key + ', ' + it.value[0] + ', ' + it.value[1] + '\n')
		}
	}
	
	if(printTestObject) {
		outFile.append('\n<<<<< Test Objects >>>>>\n')
		sortedObjects = objects.sort()
		sortedObjects.each {
			outFile.append(it +'\n')
		}
	}
}

if(errorFlag) {
	outFile.renameTo(errorFile)
	if(sendErrorsEmail) {
		GlobalVariable.errorsFlag = true
	}
}
	
WebUI.closeBrowser()

println('################# FONT USAGES #################')
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

def navigateToUrl(url) {
	
	if(screensize == 'full') {
		WebUI.maximizeWindow()
	} else {
		WebUI.setViewPortSize(sWidth, sHeight)
	}
	
	WebUI.navigateToUrl(url)
	
	securityPageFalse = WebUI.verifyTextNotPresent('Performing security verification', false, FailureHandling.OPTIONAL)
	
	if(!securityPageFalse) {
		
		WebUI.closeBrowser()
		
		WebUI.openBrowser(url)
		
		if(screensize == 'full') {
			WebUI.maximizeWindow()
		} else {
			WebUI.setViewPortSize(sWidth, sHeight)
		}
	}
}

def testPageTitle(option) {
	
	println('<<< getting page title')
	
	linkedURL = WebUI.getUrl().toLowerCase()
	
	if(linkedURL.contains('wordpress')) {
		outFile.append('#### ERROR: Linked to page is in the sandbox.\n')
		errorsOutFile.append('#### ERROR: Linked to page is in the sandbox.\n')
		errorFlag = true
	}
	
	title = WebUI.getWindowTitle()
	
	println(title)
	
	if(title != pageTitles.get(option)) {
		outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
		errorsOutFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
		errorFlag = true
	}
}

def testForObjectExists(objectFolder, testObject) {
	
	driver = DriverFactory.getWebDriver()
	
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
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
//		exists = !driver.findElements(By.xpath(objectXpath)).isEmpty()
		
		elements = driver.findElements(By.xpath(objectXpath))
		
		elementCount = elements.size()
		
		if(elementCount == 1) {
			if(elements[0].isDisplayed() && elements[0].isEnabled()) {
				isClickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
				if(isClickable) {
					currentElement = elements[0]
//					js.executeScript("arguments[0].style.border='3px solid red';", currentElement);
//					WebUI.delay(1)
					println('Returning true')
					return true
				} else {
					println('the element is not clickable')
				}
			} else {
				println('the element is not visible')
			}
		} else {
			println('the element count is ' + elementCount)
		}
	}
	return false
}


def getObjectFolder(folderBase, testObject, mySite) {
	
	testObjectFolders = ['/', '/Custom1/', '/Custom2/', '/Custom3/', '/Custom4/', '/Custom5/', '/Custom6/', '/Custom7/']
	
	lastObjectFound = false
	
	if(lastFolder != '') {
		println(lastFolder)
		testObjectFolders.add(0, lastFolder)
//		outFile.append(testObjectFolders + '\n')
	}
	
	for(folder in testObjectFolders) {
/*		
		if(testObject.contains('p_Copyright')) {
			println(testObjectFolders)
			println(folder)
		}
*/		
		objectFolder = folderBase + folder
		
		exists = testForObjectExists(objectFolder, testObject)
		
		println(exists)
		
		if(exists) {
			
			if(!usedFolders.contains(folder)) {
				usedFolders.add(objectFolder + ' - ' + mySite)
			}
			
			lastFolder = folder
			
			lastObjectFound = true
			
			return objectFolder
			
		}
	}

	return null
}

def testMenus(site, url, menu) {
	
	println('site is ' + site + ', url is ' + url + ', menu is ' + menu)
	
	menusMap = ['Connecting' : ['MissionConnexion', 'MissionNext','MissionGuide'],
		'Equipping' : ['Coaching', 'MissionLinked','MissionArmor', 'MissionExcellence'],
//		'About Us' : ['About Us Header', 'About Us', 'Statement of Faith', 'Team and Board','Careers', 'Contact Us']]
		'About Us' : ['About Us', 'Statement of Faith', 'Team and Board','Careers', 'Contact Us']]

	menuOptions = menusMap.get(menu)
	
	pageTitles = ['MissionConnexion' : 'Homepage - MissionConnexion',
		'MissionNext' : 'Serve in Missions - MissionNext.org',
		'MissionGuide' : 'Short Term Mission Trips | International and Domestic',
		
		'Coaching' : 'Journey Guides - MissionNext.org',
		'MissionLinked' : 'MissionLinked – MissionWorks',
		'MissionArmor' : 'MissionArmor – MissionWorks',
		'MissionExcellence' : 'MissionExcellence',
	
//		'About Us Header' : 'About Us /Bakery Builder – MissionWorks',
		'About Us' : 'About Us /Bakery Builder – MissionWorks',
		'Statement of Faith' : 'Statement of Faith – MissionWorks',
		'Team and Board' : 'Team and Board – MissionWorks',
		'Careers' : 'Careers – MissionWorks',
		'Contact Us' : 'Contact Us – MissionWorks']
	
	WebUI.openBrowser('')
	
	navigateToUrl(url)
				
	// Test to see if there is a "Donate" link near the top of the page
	donateFound = false
	
	driver = DriverFactory.getWebDriver()
	
	// If not full screen, find the hamburger test object
	if(screensize != 'full') {
		
		WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.HOME))
		
		hamburgerBase = screenFolderBase + 'Header'
		
		hamburger = 'i_Hamburger'
		
		println(hamburgerBase + ';' + hamburger)
		
		folder = getObjectFolder(hamburgerBase, hamburger, site)
		
		if(folder == null) {
			outFile.append('\n_________________________________________________________________________________________________\n')
			outFile.append('#### ERROR: Unable to find the Hamburger in the header. Bypassing menu, logo, and donate tests on ' + site + '.\n')
			errorsOutFile.append('#### ERROR: Unable to find the Hamburger in the header. Bypassing menu, logo, and donate tests on ' + site + '.\n')
			errorFlag = true
	
			return('no header')
		}
		
		hamburger = folder + hamburger
		
		println('hamburger is ' + hamburger)
		
		WebUI.click(findTestObject(hamburger))
		
		WebUI.delay(1)
	}
					
	donateXpath = "//a[text()='Donate']"
	
	println(donateXpath)
	
	if(site == 'MissionLinked' && screensize == 'full') { //Hover on Donate element to allow full page load
		hoverElement = driver.findElement(By.xpath(donateXpath))
		Actions actions = new Actions(driver);
		actions.moveToElement(hoverElement).perform();
		WebUI.delay(1)
	}

	folderBase = screenFolderBase + 'Menus/' + menu
	
	println(folderBase)
	
	arrow = 'span_' + menu + ' sub-arrow'
	
	println(folderBase + ';' + arrow)
	
	myObjectFolder = getObjectFolder(folderBase, arrow, site)
	
	List<WebElement> donateElements = driver.findElements(By.xpath(donateXpath))
	
	println(donateElements.size() + ' donate elements found.')
	
	if(donateElements.size() > 0) {
		
		for(element in donateElements) {

			println(element)
			
			elementLink = element.getAttribute("href")
			
			println(elementLink)
			
			if(elementLink != null) {
			
				location = element.getLocation()
				
				println(location)
				
				yLoc = location.getY()
				
				if(screensize == 'full') {
					if(yLoc < 240) {
						donateFound = true
					}
				} else {
					if(yLoc < 290) {
						donateFound = true
					}
				}
			}
		}
	} 
	
	if(!donateFound) {
		outFile.append('\n_________________________________________________________________________________________________\n')
		
		outFile.append('#### ERROR: Unable to find a Donate link in the header. Bypassing menu, logo, and donate tests on ' + site + '.\n')
		errorsOutFile.append('#### ERROR: Unable to find a Donate link in the header. Bypassing menu, logo, and donate tests on ' + site + '.\n')
		errorFlag = true						
		
		return('no header')
	}
////////////////
	println('myObjectFolder is ' + myObjectFolder)
	
	if(myObjectFolder != null) {
		
		println('myObjectFolder is ' + myObjectFolder)
	
	} else {
		println("###NOT ABLE TO FIND OBJECT FOLDER")
		
		outFile.append('#### ERROR: Unable to find object folder.\n')
		errorsOutFile.append('#### ERROR: Unable to find object folder.\n')
		
		errorFlag = true
		
		return
	}
	
	println('my arrow is ' + myObjectFolder + arrow)
	
	arrow = myObjectFolder + arrow
	
	objectsFile.append(arrow + '\n')
	
	if(!objects.contains(arrow)) {
		objects.add(arrow + ' - ' + site)
	}
	
//////////////////
	Screen s = new Screen()
	
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
		
		if(screensize != 'full' && !first) {
			WebUI.click(findTestObject(hamburger))
			WebUI.delay(1)
		}
			
		first = false
		
		// This curson action is needed for the MissionLinked site where the menu drop down arrows do not appear until after a cursor movement

		
		if(arrow != null) {
			
			if(printFolders) {
				outFile.append('----- Using ' + myObjectFolder + '.\n')
			}
		
			println(myObjectFolder)
						
			object = myObjectFolder + 'a_' + option
			
			println(object)
			
			objectsFile.append(object + '\n')
			
			if(!objects.contains(object)) {
				objects.add(object + ' - ' + site)
			}
			
			println(object)
			
			outFile.append('\nTesting ' + option + ' option on the ' + menu + ' menu on ' + site + '.\n')
			
			println(arrow) 
	
			arrowClickable = WebUI.verifyElementClickable(findTestObject(arrow), FailureHandling.OPTIONAL)	
					
			if(arrowClickable) {
			
				println('>>> clicking menu down arrow')
				
				WebUI.click(findTestObject(arrow))
								
				WebUI.delay(1)
				
				hasLink = WebUI.verifyElementHasAttribute(findTestObject(object), "href", 1, FailureHandling.OPTIONAL)
				
				if(!hasLink) {
					outFile.append('#### ERROR: There is no link on the ' + option + ' menu option.\n')
					errorsOutFile.append('#### ERROR: There is no link on the ' + option + ' menu option.\n')
					errorFlag = true

				} else {
				
					WebUI.waitForElementClickable(findTestObject(object), 2, FailureHandling.OPTIONAL)
							
					optionclickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)	
							
					if(optionclickable) {
						
						if(fontTest) {
						
							fontSize = WebUI.getCSSValue(findTestObject(object), 'font-size')
		
							fontWeight = WebUI.getCSSValue(findTestObject(object), 'font-weight')
							
							fontFamily = WebUI.getCSSValue(findTestObject(object), 'font-family')
							
							outFile.append('Font size = ' + fontSize + '. Font weight = ' + fontWeight + '. Font family = ' + fontFamily + '.\n')
							
							values = [fontSize,fontWeight,fontFamily]
							
							// [menu/footer option|site : font size, font weight]
							fontDetails.put('Menu option ' + option + ' on ' + site, values)
							
						}
						
						if(screensize == 'full' || option != 'About Us Header') {
							println('Testing the option background color')
		
							bgColor = WebUI.getCSSValue(findTestObject(object), 'background-color')
							
							if(bgColor != 'rgba(0, 0, 0, 0)') {
					
								outFile.append('#### ERROR: The background color for the ' + option + ' link is ' + bgColor + ', instead of white.\n')
								errorsOutFile.append('#### ERROR: The background color for the ' + option + ' link is ' + bgColor + ', instead of white.\n')
								errorFlag = true						
							}
						}
						
						WebUI.mouseOver(findTestObject(object), FailureHandling.OPTIONAL)
					
						println('Testing the option text')
						
						linkText = WebUI.getText(findTestObject(object))
						
						if(option != 'About Us Header') {
							if(linkText != option) {			
								outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
								errorsOutFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
								errorFlag = true
							}
						} else {
							if(linkText != 'About Us') {
								outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "About Us"\n')
								errorsOutFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "About Us"\n')
								errorFlag = true
							}
						}
											
						println('>>> clicking menu option ' + option)
				
						WebUI.click(findTestObject(object))
						
						testPageTitle(option)
						
					} else {
						outFile.append('#### ERROR: Unable to click on ' + option + ' option.\n')
						errorsOutFile.append('#### ERROR: Unable to click on ' + option + ' option.\n')
						errorFlag = true
					}
				}
			
			} else {
				outFile.append('#### ERROR: Unable to click on down arrow.\n')
				errorsOutFile.append('#### ERROR: Unable to click on down arrow.\n')
				errorFlag = true
			}
		
			if(site == 'MissionGuide') {
				WebUI.closeBrowser()
				
				WebUI.openBrowser('')
				
				if(screensize != 'full') {
					WebUI.setViewPortSize(sWidth, sHeight)
				} else {
					WebUI.maximizeWindow()
				}
				s = new Screen()
			}
		}
		navigateToUrl(url)
		
		if(site == 'MissionLinked' && screensize == 'full') { //Hover on Donate element to allow full page load
			hoverElement = driver.findElement(By.xpath(donateXpath))
			Actions actions = new Actions(driver);
			actions.moveToElement(hoverElement).perform();
			WebUI.delay(1)
		}
	}
	
	return errorFlag	
}

def testMWLogo(site) {
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)

	if(windowIndex == null) {
		
		WebUI.openBrowser('')
		
		if(screensize != 'full') {
			WebUI.setViewPortSize(sWidth, sHeight)
		} else {
			WebUI.maximizeWindow()
			if(firstOpen) {
				sHeight = WebUI.getViewportHeight()
				sWidth = WebUI.getViewportWidth()
				firstOpen = false
			}
		}
	
		s = new Screen()
	}
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	println('<<< Navigating to ' + siteURL)
	
	outFile.append('\n< Testing the MissionWorks logo option on ' + site + '.>\n')
	
	navigateToUrl(siteURL)
	
	WebUI.delay(1)
	
	testObject = 'img_MissionWorks logo'
	
//	folderBase = 'MissionWorks Headers and Footers/Header'
	folderBase = screenFolderBase + 'Header'
	
	myObjectFolder = getObjectFolder(folderBase, testObject, site)
	
	println(myObjectFolder)
	
	if(myObjectFolder != null) {
		
		println('myObjectFolder is ' + myObjectFolder)

		if(printFolders) {
			outFile.append('----- Using ' + myObjectFolder + '.\n')
		}
					
	} else { 
	
		outFile.append('\n#### ERROR: Unable to find the MissionWorks logo in any test object folder.\n')
		errorsOutFile.append('\n#### ERROR: Unable to find the MissionWorks logo in any test object folder.\n')
		
		errorFlag = true
		
		return
	}
	
	exists = testForObjectExists(myObjectFolder, testObject)

	if(exists) {
		
		objectsFile.append(myObjectFolder + testObject + '\n')
	
		if(!objects.contains(myObjectFolder + testObject)) {
			objects.add(myObjectFolder + testObject + ' - ' + site)
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
			} else {
				outFile.append('#### ERROR: Clicking the MissionWorks Logo on ' + site + ' does not open a second tab.\n')
				errorsOutFile.append('#### ERROR: Clicking the MissionWorks Logo on ' + site + ' does not open a second tab.\n')
				errorFlag = true
			}
			
			
			mwUrl = WebUI.getUrl()
			
			title = WebUI.getWindowTitle()
			
			println(title)
			
			if(title != 'MissionWorks') {
				outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks' + '"\n')
				errorsOutFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks' + '"\n')
				errorFlag = true
			}
			
			if(mwUrl != 'https://missionworks.global/') {
				outFile.append('#### ERROR: The URL of linked to page is "' + mwUrl + '", but should be "' + 'https://missionworks.global/' + '"\n')
				errorsOutFile.append('#### ERROR: The URL of linked to page is "' + mwUrl + '", but should be "' + 'https://missionworks.global/' + '"\n')
				errorFlag = true
			}
			
			if(tabs > 1) {
				WebUI.closeWindowIndex(1)
				WebUI.switchToWindowIndex(0)
			}
		} else {
			outFile.append('#### ERROR: Unable to click on MissionWorks logo on ' + site + '.\n')
			errorsOutFile.append('#### ERROR: Unable to click on MissionWorks logo on ' + site + '.\n')
			errorFlag = true
		}

	} else {
		outFile.append('#### ERROR: Unable to find the MissionWorks logo on ' + site + '.\n')
		errorsOutFile.append('#### ERROR: Unable to find the MissionWorks logo on ' + site + '.\n')
		errorFlag = true
	}

	WebUI.closeBrowser()
}


def testDonate(site) {
	
	println(site)
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
	}
	
	if(screensize == 'full') {
		WebUI.maximizeWindow()
		if(firstOpen) {
			sHeight = WebUI.getViewportHeight()
			sWidth = WebUI.getViewportWidth()
			firstOpen = false
		}
	} else {
		WebUI.setViewPortSize(sWidth, sHeight)
	}
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	navigateToUrl(siteURL)

	if(screensize != 'full') {
//		WebUI.setViewPortSize(sHeight, sWidth)
		
		hamburgerBase = screenFolderBase + 'Header'
		
		hamburger = 'i_Hamburger'
		
		println(hamburgerBase + ';' + hamburger)
		
		folder = getObjectFolder(hamburgerBase, hamburger, site)
		
		hamburger = folder + hamburger
		
		println('hamburger is ' + hamburger)
		
		WebUI.click(findTestObject(hamburger))
		
		WebUI.delay(1)
				
	} else {
		WebUI.maximizeWindow()
	}

	s = new Screen()
	
	outFile.append('\n< Testing Donate option on ' + site + '.>\n')
	
	testObject = 'a_Donate'
	
	folderBase = screenFolderBase + 'Header' 
	
	myObjectFolder = getObjectFolder(folderBase, testObject, site)
	
	println(myObjectFolder)
	
	if(myObjectFolder != null) {
		
		println('myObjectFolder is ' + myObjectFolder)
		
		if(printFolders) {
			outFile.append('----- Using ' + myObjectFolder + '.\n')
		}
					
	} else {
	
		outFile.append('#### ERROR: Unable to find the MissionWorks logo in any test object folder.\n')
		errorsOutFile.append('#### ERROR: Unable to find the MissionWorks logo in any test object folder on ' + site + '.\n')
		
		errorFlag = true
		
		return
	}
	
	exists = testForObjectExists(myObjectFolder, testObject)
	
	if(exists) {
		
		objectsFile.append(myObjectFolder + testObject + '\n')
		
		if(!objects.contains(myObjectFolder + testObject)) {
			objects.add(myObjectFolder + testObject + ' - ' + site)
		}
		
		clickable = WebUI.verifyElementClickable(findTestObject(myObjectFolder + testObject), FailureHandling.OPTIONAL)
		
		if(clickable) {
			WebUI.mouseOver(findTestObject(myObjectFolder + testObject))
	
			WebUI.click(findTestObject(myObjectFolder + testObject))
			
			WebUI.waitForPageLoad(20)
			
			WebUI.delay(2)
			
//			testPageTitle()
			
			title = WebUI.getWindowTitle()
			
			println(title)
			
			if(title != 'MissionWorks Donation') {
				outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks Donation' + '"\n')
				errorsOutFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + 'MissionWorks Donation' + '"\n')
				errorFlag = true
			}

		} else {
			outFile.append('#### ERROR: Unable to click on the Donation link on ' + site + '.\n')
			errorsOutFile.append('#### ERROR: Unable to click on the Donation link on ' + site + '.\n')
			errorFlag = true
		}
	} else {
		outFile.append('#### ERROR: Unable to find the Donation link on ' + site + '.\n')
		errorsOutFile.append('#### ERROR: Unable to find the Donation link on ' + site + '.\n')
		errorFlag = true
	}
	
	WebUI.closeBrowser()
}

def testFooter(site) {
	
	println('Testing site ' + site)
	
	outFile.append('\n_________________________________________________________________________________________________\n')
	
	outFile.append('\n<<< TESTING THE PAGE FOOTER ON ' + site + ' >>>\n')
	
	footerHamburger = false
	
	images = ['phone', 'envelope', 'goldTransparency', 'ecfa', 'facebook', 'instagram' ]
	
	windowIndex = WebUI.callTestCase(findTestCase('_Functions/Test If Browser Open'), [:], FailureHandling.STOP_ON_FAILURE)
	
	if(windowIndex == null) {
		WebUI.openBrowser('')
		viewPortHeight = WebUI.getViewportHeight()
		viewPortWidth = WebUI.getViewportWidth()
		println(viewPortHeight + 'x' + viewPortWidth)
	
		if(screensize != 'full') {
			println('height x width : ' + sHeight + ' x ' + sWidth)
			WebUI.setViewPortSize(sWidth, sHeight)
//			WebUI.delay(3)
			viewPortHeight = WebUI.getViewportHeight()
			viewPortWidth = WebUI.getViewportWidth()
			println(viewPortWidth + 'x' + viewPortHeight)
		} else {
			WebUI.maximizeWindow()
			if(firstOpen) {
				sHeight = WebUI.getViewportHeight()
				sWidth = WebUI.getViewportWidth()
				firstOpen = false
			}
		}
	}
	
	s = new Screen()
	
	siteURL = sites.get(site)
	
	println(siteURL)
	
	navigateToUrl(siteURL)
	
	driver = DriverFactory.getWebDriver()
	
	if(site == 'MissionLinked') { // && screensize == 'full') {
		
		if(screensize == 'full') {		
			hoverXpath = "//a[contains(text(), 'Donate')]"
		} else {
			hoverXpath = "//*[contains(@class, 'menu-toggle')]"
		}
		
		List<WebElement> hoverElements = driver.findElements(By.xpath(hoverXpath))
		
		if(hoverElements.size() > 0) {
			
			Actions actions = new Actions(driver);
			
			actions.moveToElement(hoverElements[0]).perform();
		}
	}

	WebUI.delay(1)
	
	WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
		
	WebUI.delay(1)
	
	if(screenshots) {
		
		fileImage = '/Users/cckozie/Documents/MissionNext/Test Reports/' + site + '-' + screensize + '.png'
		
		WebUI.takeScreenshot(fileImage)
		
		if(!imageTestOnly) {
			return
		}
	}
////////////////////
	if(printMatchPercentage && imageTestOnly) { //Test images
		
		first = false
		
		imageError = false
		
		if(screensize == 'full') {
			footerReg = new Region(0, 680, 1500, 240)
		} else if(screensize == 'iPhoneSE'){			// iphone [375,667]
			footerReg = new Region(0, 380, 520, 350)
		} else {										//iPadAir':[820,1180]]
			footerReg = new Region(0, 680, 900, 250)
		}
		
		footerReg.highlight(1)
		
		outText = 'Match Percentages, '
		
		for(image in images) {
			
			myImage = myImagePath + image + '.png'
			
			println(myImage)
			
			targetImage = new Pattern(myImage).similar(0.1f);
	
			testCount = 1
			
			found = footerReg.exists(targetImage).toString()
			
			while(found == 'null' && testCount <= maxImageTries) {
				
				WebUI.delay(1)
							
				found = footerReg.exists(targetImage).toString()
				
				testCount++
			}
			
			if(found == 'null') {
				found = '%0.00 '
			}
			
			println(found)
			
			pct = found.indexOf('%')
			
			space = found.indexOf('.', pct)
			
			match = found.substring(pct + 1, space)
			
			if((match as Integer) < imageMatchMinimum) {
				imageError = true
				match = '#!' + match
			}
			
			println(match)
			
			imageMatches.put(image + ' ' + site, match + '%')
			
			outText += image + ' - ' + match + '%, '
		}
			
		if(!matchFile.exists()) {
			matchFile.write('<<<<< Image Match Percentages >>>>>\n')
		}
		
		matchFile.append(site + ' ' + outText + '\n')
		
		if(imageError) {
			outText = '#### ERROR ' + outText + '\n'
			errorsOutText = '#### ERROR ' + outText + '\n'
		}
		
		outFile.append(outText + '\n')
		
		return
	}

//////////////////
	if(screensize != 'full') {
	
		folderBase = screenFolderBase + 'Footer'
		
		myObjectFolder = getObjectFolder(folderBase, 'i_Hamburger', site)
		
		println(myObjectFolder)
		
		if(myObjectFolder != null) {
			
			bypassFooterMenu = false
			
			footerHamburger = true
			
			println('myObjectFolder is ' + myObjectFolder)
			
			hamburger = myObjectFolder + 'i_Hamburger'
			
			inViewport = WebUI.verifyElementInViewport(findTestObject(hamburger), 1, FailureHandling.OPTIONAL)
			
			while(!inViewport) {
				WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
				WebUI.delay(1)
				inViewport = WebUI.verifyElementInViewport(findTestObject(hamburger), 1, FailureHandling.OPTIONAL)
			}
			
			WebUI.click(findTestObject(hamburger))
			
			WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
			
			WebUI.delay(1)

		} else {
			if(screensize.contains('iPhone')) {
				
				outFile.append('\n#### ERROR: Unable to find the phone footer hamburger on ' + site + '. Bypassing footer menu tests.\n')
				errorsOutFile.append('\n#### ERROR: Unable to find the phone footer hamburger on ' + site + '. Bypassing footer menu tests.\n')
				bypassFooterMenu = true
				errorFlag = true
			}
		}
	}
		
	first = true
	
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
		
		WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
		
		if(footerHamburger && !first && mySwitch && lastObjectFound && !bypassFooterMenu) { //If last link object was not found, don't try to click hamburger
				
			inViewport = WebUI.verifyElementInViewport(findTestObject(hamburger), 1, FailureHandling.OPTIONAL)
			
			while(!inViewport) {
				WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
				WebUI.delay(1)
				inViewport = WebUI.verifyElementInViewport(findTestObject(hamburger), 1, FailureHandling.OPTIONAL)
			}
			
			WebUI.click(findTestObject(hamburger))
			
			WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
			
			WebUI.delay(1)
			
		}
		
		first = false
		

		folderBase = screenFolderBase + 'Footer'
			
		myObjectFolder = getObjectFolder(folderBase, myElement, site)
		
		println(myObjectFolder)
		
		if(myObjectFolder != null) {
			
			println('myObjectFolder is ' + myObjectFolder)
			
		} else {
		
			outFile.append('#### ERROR: Unable to find ' + myElement + ' in any test object folder.\n')
			errorsOutFile.append('#### ERROR: Unable to find ' + myElement + ' in any test object folder on ' + site + '.\n')
			
			errorFlag = true
			lastObjectNotFound = true
			
		}
	
		object = myObjectFolder + myElement
		
		println(object)
		
		exists = testForObjectExists(myObjectFolder, myElement)
		
		if(exists) {
		
			clickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
			
			println(clickable)

			linkText = WebUI.getText(findTestObject(object))
			
			fontSize = WebUI.getCSSValue(findTestObject(object), 'font-size')
			
			fontWeight = WebUI.getCSSValue(findTestObject(object), 'font-weight')
							
			fontFamily = WebUI.getCSSValue(findTestObject(object), 'font-family')
							
			values = [fontSize,fontWeight,fontFamily]
							
			if(myType == "link" || myType == "verify") {
				
				if(printImageSize && ele == 'Instagram' || ele == 'Facebook') {
					element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
					width = element.getSize().getWidth();
					height = element.getSize().getHeight();
					imageSizes.put('Image ' + ele + ' on ' + site, [width, height])
				}
				
				inViewport = WebUI.verifyElementInViewport(findTestObject(object), 1, FailureHandling.OPTIONAL)
				
				while(!inViewport) {
					WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
					WebUI.delay(1)
					inViewport = WebUI.verifyElementInViewport(findTestObject(object), 1, FailureHandling.OPTIONAL)
				}
			
				clickable = WebUI.verifyElementClickable(findTestObject(object), FailureHandling.OPTIONAL)
				
				if(clickable) {
					try {
						WebUI.click(findTestObject(object))
					} catch (err) {
						outFile.append('#### ERROR: Unable to click on element ' + myElement + ' in the footer on ' + site + '.\n')
						errorsOutFile.append('#### ERROR: Unable to click on element ' + myElement + ' in the footer on ' + site + '.\n')
						errorFlag = true
						continue
					}
				}

				WebUI.waitForPageLoad(20)
				
				driver = DriverFactory.getWebDriver()
				
				handles = driver.getWindowHandles()
				
				tabs = handles.size() 
				
				if(tabs > 1) {
					WebUI.switchToWindowIndex(1)
						WebUI.waitForPageLoad(10, FailureHandling.OPTIONAL)
						
						WebUI.delay(1)
				}
				
				if(myType == "link") {
					
					outFile.append('\nTesting the link from "' + ele + '" on ' + site + '.\n')
					
					if(printImageSize && (ele == 'Instagram' || ele == 'Facebook')) {
						outFile.append('Image size is ' + width + ' x ' + height + '\n')
					}
					
					if(fontTest && ele != 'Instagram' && ele != 'Facebook') {
						
						outFile.append('Font size = ' + fontSize + '. Font weight = ' + fontWeight + '. Font family = ' + fontFamily + '.\n')
							
						values = [fontSize,fontWeight,fontFamily]
						
						// [menu/footer option|site : font size, font weight]
						fontDetails.put('Footer link for ' + ele + ' on ' + site, values)
					}
					
					objectsFile.append(myObjectFolder + ele + '\n')
					
					if(!objects.contains(myObjectFolder + ele)) {
						objects.add(myObjectFolder + ele + ' - ' + site)
					}
					
					if(printFolders) {
						outFile.append('----- Using ' + myObjectFolder + '.\n')
					}
					
					if(mySwitch && linkText != ele) {
					
						outFile.append('#### ERROR: The text for the ' + ele + ' link on ' + site + ' is "' + linkText + '", but should be "' + ele + '"\n')
						errorsOutFile.append('#### ERROR: The text for the ' + ele + ' link on ' + site + ' is "' + linkText + '", but should be "' + ele + '"\n')
						errorFlag = true
					}
					
					println(myTest)
					
					title = WebUI.getWindowTitle()
					
					println(title)
					
					if(ele == 'Sign Up') {
						textPresent = WebUI.verifyTextPresent('Please choose your email subscription:', false, FailureHandling.OPTIONAL)
						
						if(!textPresent) {
							outFile.append('#### ERROR: The text "Please choose your email subscription:" was not found on the page linked from ' + ele + ' on ' + site + '.\n')
							errorsOutFile.append('#### ERROR: The text "Please choose your email subscription:" was not found on the page linked from ' + ele + ' on ' + site + '.\n')
							errorFlag = true
						}
					} else {
						if(title != myTest) {
							outFile.append('#### ERROR: The title of the page linked from ' + ele + ' on ' + site + ' is "' + title + '", but should be "' + myTest + '"\n')
							errorsOutFile.append('#### ERROR: The title of the page linked from ' + ele + ' on ' + site + ' is "' + title + '", but should be "' + myTest + '"\n')
							errorFlag = true
						}
					}
				} else {
					
					outFile.append('\nTesting the text on the page linked from "' + ele + '" on ' + site + '.\n')
					
					if(fontTest) {
						
						outFile.append('Font size = ' + fontSize + '. Font weight = ' + fontWeight + '. Font family = ' + fontFamily + '.\n')
						
						values = [fontSize,fontWeight,fontFamily]
						
						// [menu/footer option|site : font size, font weight]
						fontDetails.put('Footer link for ' + ele + ' on ' + site, values)
					}
				
					objectsFile.append(myObjectFolder + ele + '\n')
					
					if(!objects.contains(myObjectFolder + ele)) {
						objects.add(myObjectFolder + ele + ' - ' + site)
					}
					
					if(printFolders) {
						outFile.append('----- Using ' + myObjectFolder + '.\n')
					}
				
					found = WebUI.verifyTextPresent(myTest, false, FailureHandling.OPTIONAL)
					
					if(!found) {
						outFile.append('#### ERROR: Unable to find the text "' + myTest + '" on the page linked from ' + ele + ' on ' + site + '.\n')
						errorsOutFile.append('#### ERROR: Unable to find the text "' + myTest + '" on the page linked from ' + ele + ' on ' + site + '.\n')
						errorFlag = true
					}
				}
				
				if(tabs > 1) {
					
					WebUI.closeWindowIndex(1)
					
					WebUI.switchToWindowIndex(0)
					
				} else {
					
					navigateToUrl(myUrl)
					
					if(site == 'MissionLinked') { // && screensize == 'full') {
						
						driver = DriverFactory.getWebDriver()
						
						if(screensize == 'full') {
							hoverXpath = "//a[contains(text(), 'Donate')]"
						} else {
							hoverXpath = "//*[contains(@class, 'menu-toggle')]"
						}
						
						List<WebElement> hoverElements = driver.findElements(By.xpath(hoverXpath))
						
						if(hoverElements.size() > 0) {
							
							Actions actions = new Actions(driver);
							
							actions.moveToElement(hoverElements[0]).perform();
						}
						
						WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.END))
						
						WebUI.delay(1)
					}
				}
				
			} else if(myType == 'text') {
				
				outFile.append('\nTesting the text in the "' + ele + '".\n')
				
				if(fontTest) {
					
					outFile.append('Font size = ' + fontSize + '. Font weight = ' + fontWeight + '. Font family = ' + fontFamily + '.\n')
					
						values = [fontSize,fontWeight,fontFamily]
						
						// [menu/footer option|site : font size, font weight]
						fontDetails.put('Footer text for ' + ele + ' on ' + site, values)

				}
				
				objectsFile.append(myObjectFolder + ele + '\n')
				
				if(!objects.contains(myObjectFolder + ele)) {
					objects.add(myObjectFolder + ele + ' - ' + site)
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
					outFile.append('#### ERROR: The text in element ' + myElement + ' on ' + site + ' is "' + text + '", but should be "' + myTest + '"\n')
					errorsOutFile.append('#### ERROR: The text in element ' + myElement + ' on ' + site + ' is "' + text + '", but should be "' + myTest + '"\n')
					errorFlag = true
				}
				
				if(myElement.contains('Email')) { //Test for a link on the email address
					emailLink = WebUI.getAttribute(findTestObject(object), "href", FailureHandling.OPTIONAL)
					if(emailLink == null) {
						parent = currentElement.findElement(By.xpath("./.."));
						parentTag = parent.getTagName();
						println("Email parent's tag is " + parentTag)
						if(parentTag != 'a') {
							outFile.append('#### ERROR: The email element on ' + site + ' should have a link but it does not.\n')
							errorsOutFile.append('#### ERROR: The email element on ' + site + ' should have a link but it does not.\n')
							errorFlag = true
						}
					}
				}
				
			} else if(myType == 'image') {
				outFile.append('\nTesting for ' + ele + ' image.\n')
				
				objectsFile.append(myObjectFolder + ele + '\n')
					
				if(!objects.contains(myObjectFolder + ele)) {
					objects.add(myObjectFolder + ele + ' - ' + site)
				}
					
				if(printFolders) {
					outFile.append('----- Using ' + myObjectFolder + '.\n')
				}
				
				visible = WebUI.verifyElementVisible(findTestObject(object), FailureHandling.OPTIONAL)
				
				if(!visible) {
					outFile.append('#### ERROR: The image ' + myElement + ' on ' + site + ' was not found on the page.\n')
					errorsOutFile.append('#### ERROR: The image ' + myElement + ' on ' + site + ' was not found on the page.\n')
					errorFlag = true
				} else if(printImageSize) {
					element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
					width = element.getSize().getWidth();
					height = element.getSize().getHeight();
					outFile.append('Image size is ' + width + ' x ' + height + '\n')
					imageSizes.put('Image ' + myElement + ' on ' + site, [width, height])
					
				}
			}
			
		} else {
			outFile.append('#### ERROR: The element ' + myElement + ' was not found in the footer on ' + site + '.\n')
			errorsOutFile.append('#### ERROR: The element ' + myElement + ' was not found in the footer on ' + site + '.\n')
			errorFlag = true
		}
	}
	
	if(!windowIndex == null) {
		navigateToUrl(myUrl)		
	} else {
		WebUI.closeBrowser()
	}
	
}

