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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import groovy.time.*
import java.io.File
import groovy.sql.Sql
@Grab('org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource

def ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
def sql = new Sql(ds)

WebUI.openBrowser('')

WebUI.maximizeWindow()

startPage = 'https://missionnext.org/'

WebUI.navigateToUrl(startPage)

linkList = WebUI.getAllLinksOnCurrentPage(false, [])

links = linkList.sort()

bypassList = ['plugin','wp-content','wp-json','feed']
first = true

outFile = new File('/Users/cckozie/Documents/MissionNext/homepagetimingsA.csv')

int rows
float min
float max
float avg
int count

for (def link : links) {
	if(link != startPage && link.indexOf('plugin') < 0 && link.indexOf('wp-') < 0 && link.indexOf('wp-') < 0 && link.indexOf('feed') < 0 && link.indexOf('xml') < 0) {
		timeStart = new Date()
	    WebUI.navigateToUrl(link)
		timeStop = new Date()
		float duration = (timeStop.getTime()- timeStart.getTime())/1000
		if(first) {
			outFile.write('FROM , TO , ELAPSED TIME\n')
			first = false			
		}
		outFile.append(startPage + ',' + link + ',' + duration + '\n')
		String whereClause = "WHERE from_url = '" + startPage + "' AND to_url = '" + link + "'"
		sql.query("SELECT COUNT(*) from navigation_times " + whereClause) { resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		}
		if(rows == 0) { 
//			 sql.query("INSERT INTO navigation_times VALUES(from_url, to_url, duration, duration, duration, 1")
			 sql.executeInsert("INSERT INTO navigation_times VALUES (?, ?, ?, ?, ?, ?)", [startPage, link, duration, duration, duration, 1])
//			 sql.executeInsert("INSERT INTO navigation_times VALUES (?, ?, ?, ?, ?, ?)", ['https://missionnext.org/', 'https://missionnext.org/xx', 1, 2, 1.5, 1])
		} else {
			sql.query("SELECT * from navigation_times " + whereClause) { resultSet ->
				while (resultSet.next()) {
				  min = resultSet.getFloat(3)
//				  min = Float.parseFloat(minS);
				  max = resultSet.getFloat(4)
//				  max = Float.parseFloat(maxS);
				  avg = resultSet.getFloat(5)
//				  avg = Float.parseFloat(avgS);
				  count = resultSet.getInt(6)
				}
			}
			if(duration > max) {
				max = duration
			} else {
				if(duration < min) {
					min = duration
				}
			}
			float newAvg = ((avg * count + duration) / (count + 1)).round(3)
//			avg = newAvg.round(3)
			count ++
			sql.executeUpdate("UPDATE navigation_times SET minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count " + whereClause)
//			sql.execute "UPDATE Author SET firstname='Erik' where lastname='Thorvaldsson'"
		}		
		WebUI.delay(1)
	    WebUI.back()
		
	}
	
}
