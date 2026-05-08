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

site = varSite

includeTestAccts = varTestAccts

mainPage = 'https://ad.missionnext.org/index.php'

subscriptionPage = 'https://ad.missionnext.org/subscriptions.php?'

myPage = WebUI.getUrl()

if (myPage.contains(subscriptionPage)) {
    if (!(myPage.contains(mainPage))) {
        WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))

        WebUI.delay(2)
    }
}

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.waitForPageLoad(30)

WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Website'), site, false)

WebUI.waitForPageLoad(30)

WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Role'), 'Organization', false)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/button_Go'))

WebUI.waitForPageLoad(30)

tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/p[3]/table/tbody'

WebDriver driver = DriverFactory.getWebDriver()

Table = driver.findElement(By.xpath(tableXpath))

Rows = Table.findElements(By.tagName('tr'))

row_count = (Rows.size() - 2)

println(row_count)

subscriptions = [:]

if (row_count > 0) {
    for (row = 1; row < row_count; row++) {
		values = []
		
        List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
		line = Columns.get(0).getText()
		
		if(line.contains('* Account') || line.contains('Expired Accounts')) {
			break
		}

        userID = Columns.get(1).getText()
		
		organization = Columns.get(4).getText()

		start = Columns.get(7).getText().trim()

		expires = Columns.get(8).getText()

		username = Columns.get(10).getText()

		values.addAll(organization, start, expires,username)
		
		subscriptions.put(userID,values)
    }
}

WebUI.back()

return subscriptions
