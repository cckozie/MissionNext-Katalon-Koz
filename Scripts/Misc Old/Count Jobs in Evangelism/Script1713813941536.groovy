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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://missionnext.org/journey-jobs/')

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/Misc Saved Objects/Page_Journey Missionary Job Summary - Missi_d1fec4/span_View Job Details by Category'))

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table/tbody'))

List rows_table = Table.findElements(By.tagName('tr'))

List elements_table = Table.findElements(By.tagName('td'))

int element_count = elements_table.size()

int evangelism_jobs = 0

for (int count = 0; count < element_count; count++) {
    String celltext = elements_table.get(count).getText()

    int pos = celltext.indexOf('EVANGELISM')

    if (pos >= 0) {
        pos = celltext.indexOf('(')

        String jobs_text = celltext.substring(pos + 1, celltext.length() - 1)

        println(((('>>>>>>>>>> ' + celltext) + ' has ') + jobs_text) + ' jobs.')

        int jobs = jobs_text.toInteger()

        evangelism_jobs += jobs
    }
}

println((('>>>>>>>>>> ' + 'The total number of jobs related to EVANGELISM is ') + evangelism_jobs.toString()) + '.')

WebUI.closeBrowser()

