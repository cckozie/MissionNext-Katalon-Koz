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
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

//varSite = 'Education'
 
//varUsername = 'cktest16ep'

siteDesignations = ['Journey' : 2,'Education' : 3,'QuickStart' : 6,'Journey Guide' : 7]

index = WebUI.getWindowIndex(FailureHandling.OPTIONAL)

if(index < 0) {
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
}

WebUI.navigateToUrl('https://missionnext.org/managerlogin/')

WebUI.waitForPageLoad(30)

WebUI.delay(2)

WebUI.setText(findTestObject('Object Repository/Manager/input_Username or Email Address'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Object Repository/Manager/input_Password'), 'xPc6erizqpZS5RHkbVxVdKyWkQxV4SRKassnbXTAWhQ=')

WebUI.click(findTestObject('Object Repository/Manager/input_Log In'))

WebUI.waitForPageLoad(30)

WebDriver driver = DriverFactory.getWebDriver()

WebElement mySites = driver.findElement(By.xpath('//*[@id="wp-admin-bar-my-sites"]/a'))

Actions actions = new Actions(driver);

actions.moveToElement(mySites).perform();

Thread.sleep(1000); // Wait for 1 second (use explicit waits in real projects)

if(varSite == null) { // Stop if a site was not specified
	System.exit(0)
}

site = siteDesignations.get(varSite)

siteXpath = '//*[@id="wp-admin-bar-blog-' + site + '"]/a'
 
//WebElement site = driver.findElement(By.xpath('//*[@id="wp-admin-bar-blog-2"]/a'))
WebElement mySite = driver.findElement(By.xpath(siteXpath))
 
actions.moveToElement(mySite).perform();

Thread.sleep(1000); // Wait for 1 second (use explicit waits in real projects)

siteDashboardXpath = '//*[@id="wp-admin-bar-blog-' + site + '-d"]/a'

WebElement dashboard = driver.findElement(By.xpath(siteDashboardXpath))

dashboard.click()

WebUI.waitForPageLoad(60)

WebElement users = driver.findElement(By.xpath('//*[@id="menu-users"]/a/div[2]'))

actions.moveToElement(users).perform();

Thread.sleep(1000); // Wait for 1 second (use explicit waits in real projects)

WebElement allUsers = driver.findElement(By.xpath('//*[@id="menu-users"]/ul/li[2]/a'))

allUsers.click()

if(varUsername != null && varUsername != '') {
	
	WebUI.delay(1)

	WebUI.setText(findTestObject('Object Repository/Manager/input_Search Users'), varUsername)
	
	WebUI.click(findTestObject('Object Repository/Manager/input_Search Users Submit'))
	
	WebUI.waitForPageLoad(60)
	
	WebElement Table = driver.findElement(By.xpath('//*[@id="the-list"]'))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	row_count = Rows.size()
	
	found = false
	
	clicked = false
		
	if(row_count > 0) {
	
		for (row = 0; row < row_count && !clicked; row++) {
			
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
			user = Columns.get(0).getText()
			
			println(user)
			
			if(user == varUsername) {
				
				myRow = Rows.get(row)
				
				actions.moveToElement(myRow).perform();
				
				WebUI.delay(1)
				
				myName = Columns.get(1)
				
				List<WebElement> Spans = myName.findElements(By.tagName('span'))
				
				for(span = 0; span < Spans.size(); span++) {
					
					html = Spans.get(span).getAttribute("innerHTML")
					
					if(html.contains('action=switch_to_user')) {
						
						Spans.get(span).click()
						
						clicked = true
						
						break
					}
				}
			}
		}
	}
}
	
	
