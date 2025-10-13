import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
import java.io.File as File
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	The input for this page is postgreSQL table en_web_page_errors which is created by Test Case 'Log Page Load Times - Explorenext Errors
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

WebUI.openBrowser('')

//inFile = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/input temp/en_web_page_errors.csv')

//pages = inFile.readLines()

outFile = new File('/Users/cckozie/Documents/MissionNext/Test data/ExploreNext Links to MissionNext.csv')

outText = 'FROM PAGE,LINK,LINK URL,VISIBLE\n'

outFile.write(outText)

pages = []
ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

sql = new Sql(ds)

//select distinct from_url from en_web_page_errors where to_url like '%missionnext.org%'
sql.eachRow("select distinct from_url from en_web_page_errors where to_url like '%missionnext.org%'", { def row ->
	pages.add(row[0])
})

ignore = [
	'Partnership Agreement',
	'Terms and Conditions',
	'Privacy Policy',
	'Site Map',
	'Suggested Browsers',
	'Sitemap',
	'image']

WebDriver driver = DriverFactory.getWebDriver()

first = true

//println(pages.size() + ' pages')
//println(links.size() + ' links')
//System.exit(0)
pages.each {
//for(page in pages) {
	
	page = it
	fromPage = page.replace('"', '')
	
	WebUI.navigateToUrl(fromPage)
	
	WebUI.waitForPageLoad(30)
	
	title = WebUI.getWindowTitle()
	
	blog = false
	
	if(title.contains('Blog')) {
		blog = true
	}
	
	goodPage = WebUI.verifyTextNotPresent('Oops!', false, FailureHandling.OPTIONAL)
	
	if(goodPage && !blog) {
	
		List<WebElement> linkList = driver.findElements(By.tagName('a'))
		
		//linkList.each {
		for(link in linkList) {
			if(link.isDisplayed()) {
				visible = true
			} else {
				visible = false
			}
			url = link.getAttribute('href')
			if(url != null) {
				qMark = url.indexOf('?')
				if(qMark > 0) {
					url = url.substring(0,qMark)
				}
				if(url.contains('missionnext.org')) {
					myClass = link.getAttribute('class')
					myLink = link.getAttribute('innerHTML')
					myStyle = link.getAttribute('style')
					
					bypass = false
					for(word in ignore) {
						if(myLink.contains(word)) {
							bypass = true
						}
					}
					if(!bypass) {
						println('myLink:' + myLink)
						myLink = myLink.replace('\t',' ')
						myLink = myLink.replace('\n','')
						myLink = myLink.replace('   ',' ')
						if(myClass != '') {
							outFile.append(fromPage + ',' + myClass + ' - ' + myLink + ',' + url + ',' + visible + '\n')
						} else {
							outFile.append(fromPage + ',' + myLink + ',' + url + ',' + visible + '\n')
						}
					}
				}
			}
		}
	} 
		
}	
