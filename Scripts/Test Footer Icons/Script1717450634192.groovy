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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.sikuli.script.*
import java.io.File

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Footer Icons.txt')

outFile.write("Testing Footer Icons\n")

iconsHome = ['facebook', 'x', 'instagram', 'linkedin', 'wordpress']

iconsBackend = ['facebookBackend', 'x', 'instagramBackend', 'linkedinBackend', 'wordpress']

//icons = ['facebook']
path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/'

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

WebElement goer = driver.findElement(By.xpath('//a[contains(text(),\'Goer\')]'))

Actions actions = new Actions(driver)

actions.moveToElement(goer).perform()

WebUI.waitForElementVisible(findTestObject('Page_Serve in Missions - MissionNext.org/Goer_arrow'), 5)

WebUI.scrollToElement(findTestObject('Page_Serve in Missions - MissionNext.org/i_Facebook-f_fab fa-facebook-f'), 0)

WebUI.delay(1)

outText = '=========== Checking for icons on home page ==========='

println(outText)

outFile.append(outText + '\n')

checkIcons(iconsHome)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('_Functions/Candidate Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.scrollToElement(findTestObject('Page_Serve in Missions - MissionNext.org/i_Facebook-f_fab fa-facebook-f'), 0)

WebUI.delay(1)

outText = '=========== Checking for icons on backend ==========='

println(outText)

outFile.append('\n' + outText + '\n')

checkIcons(iconsBackend)

WebUI.closeBrowser()

def checkIcons(iconList) {
    Screen s = new Screen()

    for (def icon : iconList) {
        myImage = ((path + icon) + '.png')

        println('Looking for ' + myImage)

        myImage = ((path + icon) + '.png')

        Pattern icn = new Pattern(myImage).similar(0.7)

        found = s.exists(icn)

        println(found)

        if (found != null) {
			
			foundStr = found.toString()
			
			matchP = foundStr.indexOf('S:')
			
			println(matchP)
			
			pct = foundStr.substring(matchP + 2, matchP + 5)
			
			outText = '+++++++++++++++ Found icon for ' + icon
			
			println(outText)
			
			pctVal = new BigDecimal(pct)
			
			pctVal = (pctVal *100).intValue()
//			pctVal = pct.replace('.','').toInteger() *10
			
			outFile.append(outText + ' : ' + pctVal + '%\n')
			
        } else {
			
            outText = '----------- Unable to find icon for ' + icon 
			
			println(outText)
			
			outFile.append(outText + '\n')
			
        }
    }
}

