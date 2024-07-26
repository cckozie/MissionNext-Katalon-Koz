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

username = varUsername

WebUI.openBrowser('')

WebUI.navigateToUrl('https://api.explorenext.org')

WebUI.setText(findTestObject('Admin/API Login/input_username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/API Login/input_password'), '4Q/2PF7UjxevAl0v0kCS+w==')

WebUI.click(findTestObject('Admin/API Login/btn_Sign in'))

WebUI.click(findTestObject('Admin/API Dashboard/a_Users'))

WebUI.setText(findTestObject('Object Repository/Admin/API Dashboard/input_User_search'), username)

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//*[@id="default-rezult"]/table'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

for (row = 1; row < row_count; row++) {
    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

    user = Columns.get(1).getText()

    if (user.indexOf(username) >= 0) {
		
        Columns.get(1).click()

        println('=====> Granting access to ' + username)

        WebUI.click(findTestObject('Object Repository/Admin/API Dashboard/button_Grant Access'))
		
		WebUI.delay(3)
		
		WebUI.verifyTextPresent('ACCESS GRANTED', false)		

        println('=====> Access granted to ' + username)

        WebUI.click(findTestObject('Object Repository/Admin/API Dashboard/a_Logout'))

        break
    }
}

WebUI.closeBrowser()