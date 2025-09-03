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
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Matching/'

myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

filePath = '/Users/cckozie/Documents/MissionNext/Test Reports/'

userFile = new File(filePath + 'Users with No Avatar.txt')

userFile.write('Usernames of Candidates without Avatars\n')

csvFile = new File(filePath + 'Users with No Avatar.csv')

csvFile.write('Candidates without Avatars\n\n')

sites = ['Journey', 'Education']

first = true

for (def site : sites) {
    if (first) {
        csvFile.append('Site, WP Page, WP Line, Username\n')
    }
    
    badCount = 0

    WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : null, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

    /*
	WebUI.click(findTestObject('Object Repository/Admin/WordPress Administrator User List/button_Screen Options'))
	
	WebUI.setText(findTestObject('Object Repository/Admin/WordPress Administrator User List/input_Number of items per page'), '999')
	
	WebUI.click(findTestObject('Object Repository/Admin/WordPress Administrator User List/button_Apply Screen Options'))
	*/
    scroll = WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/WordPress Administrator User List/text_Last Page Number'), 
        FailureHandling.OPTIONAL)

    if (scroll) {
        myPages = WebUI.getText(findTestObject('Object Repository/Admin/WordPress Administrator User List/text_Last Page Number'))

        pages = myPages.toInteger()
    } else {
        pages = 1
    }
    
    driver = DriverFactory.getWebDriver()

    //	JavascriptExecutor executor = (JavascriptExecutor) driver;
    userFile.append(('\n' + site) + ' Candidates\n')

    if (!(first)) {
        csvFile.append('\n')
    }
    
    first = false

    for (int page = 1; page <= pages; page++) {
        WebElement Table = driver.findElement(By.xpath('//*[@id="wpbody-content"]/div[3]/form/table'))

        List<WebElement> Rows = Table.findElements(By.tagName('tr'))

        int row_count = Rows.size()

        //		println('there are ' + row_count + ' rows')
        found = false

        for (row = 1; row < (row_count - 1); row++) {
            List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

            user = Columns.get(0).getText()

            //	println(user)
            List<WebElement> Images = Columns.get(0).findElements(By.tagName('img'))

            image = Images.get(0)

            srcset = image.getAttribute('srcset')

            alt = image.getAttribute('alt')

            src = image.getAttribute('src')

            if ((srcset == '') || (srcset == null)) {
                srcset = 'NONE'
            }
            
            //			elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	image)
            //			attrs = elementAttributes.toString()
            //	println(attrs)
            if (src.contains('explorenext') || (src == '')) {
                userFile.append(((((('Page ' + page) + ', Row ') + row) + ', User ') + user) + '\n')

                csvFile.append(((((((site + ',') + page) + ',') + row) + ',') + user) + '\n')

                badCount++
            }
        }
        
        if ((page < pages) && (page == 1)) {
            WebUI.click(findTestObject('Object Repository/Admin/WordPress Administrator User List/button_Next page - first page'))
        }
        
        if ((page < pages) && (page != 1)) {
            WebUI.click(findTestObject('Object Repository/Admin/WordPress Administrator User List/button_Next page - other pages'))
        }
    }
    
    if (badCount == 0) {
        userFile.append('NONE FOUND\n')

        csvFile.append(site + ' : NONE FOUND')
    }
    
    WebUI.closeBrowser()

}

WebUI.callTestCase(findTestCase('Utilities/Test If Users Are Active'), [:], FailureHandling.STOP_ON_FAILURE)


