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

menuOptions = ['About Us', 'About Us', 'Statement of Faith', 'Team and Board','Careers', 'Contact Us']

pageTitles = ['About Us /Bakery Builder – MissionWorks', 'About Us /Bakery Builder – MissionWorks', 'Statement of Faith – MissionWorks', 'Team and Board – MissionWorks', 'Careers – MissionWorks', 'Contact Us – MissionWorks']

extensions = ['MissionNext' : '', 'Journey' : '', 'Education' : '', 'QuickStart' : '', 'Journey Guide' : '', 'MissionLinked' : '',
	'Coaching' : '', 'About Us' : '']

folderBase = 'MissionWorks Headers and Footers/Functions/About Us/'

extension = extensions.get(site)

objectFolder = folderBase + extension

outFile = GlobalVariable.outFile

if(outFile == '') {
	outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Testing About Us Menu Options.txt')
	outFile.write('     Testing About Us Menu Options\n')
}

WebUI.openBrowser('')

WebUI.maximizeWindow()

outFile.append('\n_________________________________________________________________________________________________\n')

outFile.append('\n<<< TESTING THE ABOUT US MENU ON ' + site + ' >>>\n')

errorFlag = false

aboutUsCount = 1

index = 0

for(option in menuOptions) {
	
	WebUI.navigateToUrl(url)
		
	if(site == 'MissionLinked') {
		WebUI.mouseOver(findTestObject('Object Repository/MissionWorks Headers and Footers/Header/img_MissionWorks logo'))
		WebUI.delay(2)
	}
	
	if(option == 'About Us' && aboutUsCount == 1) {

		outFile.append('\nTesting the link on the About Us menu header on ' + site + '.\n')

	} else {
	
		outFile.append('\nTesting ' + option + ' link on the About Us menu on ' + site + '.\n')
	}
	
//	println('\nTesting ' + option + ' option on the About Us menu.\n')

	arrowClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'span_About Us sub-arrow'), 1)
	
	if(arrowClickable) {
		
		WebUI.click(findTestObject(objectFolder + 'span_About Us sub-arrow'))
	
		if(option == 'About Us' && aboutUsCount == 1) {
				
			WebUI.click(findTestObject(objectFolder + 'span_About Us sub-arrow'))
			
			aboutUsCount++
			
		} else {
			
			WebUI.delay(1)
			
			optionClickable = WebUI.waitForElementClickable(findTestObject(objectFolder + 'a_' + option), 1)
			
			if(optionClickable) {
				
				linkText = WebUI.getText(findTestObject(objectFolder + 'a_' + option))
				
				if(linkText != option) {
				
				outFile.append('#### ERROR: The text for the ' + option + ' link is "' + linkText + '", but should be "' + option + '"\n')
				errorFlag = true
				}
			
				WebUI.click(findTestObject(objectFolder + 'a_' + option))
			
			} else {			
				outFile.append('#### ERROR: Unable to click on ' + option + ' option.\n')
				errorFlag = true
			}
		}
		
		for(attemps = 1; attemps <= 2; attemps++) {
		
			title = WebUI.getWindowTitle()
			
			println(title)
			
			if(title != pageTitles[index]) {
				if(attemps == 1) {
					outFile.append('----- RETRYING option "' + option+ '"\n')
					WebUI.delay(2)
					WebUI.click(findTestObject(objectFolder + 'a_' + option))
					WebUI.delay(2)
				} else {
					outFile.append('#### ERROR: Linked to page title is "' + title + '", but should be "' + pageTitles[index] + '"\n')
					errorFlag = true
				}
			}
		}
	} else {
		outFile.append('#### ERROR: Unable to click on menu down arrow.\n')
		errorFlag = true
	}
			
	WebUI.delay(2)
	
	index++
}

WebUI.navigateToUrl(url)
	
return errorFlag

