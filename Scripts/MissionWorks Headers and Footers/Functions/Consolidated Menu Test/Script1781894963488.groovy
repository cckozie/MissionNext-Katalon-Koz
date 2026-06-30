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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.File as File

site = varSite

url = varUrl

menu = varMenu

menus = ['Connecting' : ['MissionConnexion', 'MissionNext','MissionGuide'], 
	'Equipping' : ['Coaching', 'MissionLinked','MissionArmor', 'MissionExcellence'],
	'About Us' : ['About Us', 'About Us', 'Statement of Faith', 'Team and Board','Careers', 'Contact Us']]

menuOptions = menus.get(menu)

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

extensions = ['MissionNext' : '', 'Journey' : '', 'Education' : '', 'QuickStart' : '', 'Journey Guide' : '', 'MissionLinked' : '',
	'Coaching' : '', 'About Us' : '']

folderBase = 'MissionWorks Headers and Footers/Functions/' + menu + '/'

extension = extensions.get(site)

objectFolder = folderBase + extension

outFile = GlobalVariable.outFile

if(outFile == '') {
	outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Testing ' + menu + ' Menu Options.txt')
	outFile.write('     Testing ' + menu + ' Menu Options\n')
}

WebUI.openBrowser('')

WebUI.maximizeWindow()

outFile.append('\n_________________________________________________________________________________________________\n')

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
		
			optionClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'a_' + option), 1)
			
			if(optionClickable) {
				
				linkText = WebUI.getText(findTestObject(objectFolder + 'a_' + option))
				
				if(linkText != option) {
					
					outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
					errorFlag = true
				}
	
				WebUI.click(findTestObject(objectFolder + 'a_' + option))
				
				println('<<< getting page title')
				
				for(attemps = 1; attemps <= 2; attemps++) {
					
					title = WebUI.getWindowTitle()
					
					println(title)
					
					if(title != pageTitles.get(option)) {
						if(attemps == 1) {
							outFile.append('----- RETRYING option "' + option+ '"\n')
							WebUI.delay(2)
							WebUI.click(findTestObject(objectFolder + 'a_' + option))
							WebUI.delay(2)
						} else {
							outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles.get(option) + '"\n')
							errorFlag = true
						}
					}
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

