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
import java.io.File as File
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

//			 sql.query("INSERT INTO navigation_times VALUES(from_url, to_url, duration, duration, duration, 1")
//			 sql.executeInsert("INSERT INTO navigation_times VALUES (?, ?, ?, ?, ?, ?)", ['https://missionnext.org/', 'https://missionnext.org/xx', 1, 2, 1.5, 1])
//				  min = Float.parseFloat(minS);
//				  max = Float.parseFloat(maxS);
//				  avg = Float.parseFloat(avgS);
//			avg = newAvg.round(3)
//			sql.execute "UPDATE Author SET firstname='Erik' where lastname='Thorvaldsson'"
WebUI.openBrowser('')

WebUI.maximizeWindow()

home = 'https://missionnext.org/'

startPage = home

WebUI.navigateToUrl(startPage)

linkList = WebUI.getAllLinksOnCurrentPage(false, [])

links = linkList.sort()

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml']

first = true

outFile = new File('/Users/cckozie/Documents/MissionNext/homepagetimingsA.csv')

linkFile = new File('/Users/cckozie/Documents/MissionNext/pagelinks.csv')

linkFile.write("Level 1\n")

linksList = links

linksList.each {it ->
	linkFile.append(it + '\n')
}

int rows

float min

float max

float avg

int count

int depth = 2 //How deep to go

int level = 0 //current level

String from = startPage

for (def link in links) {
    skip = false

    for (def txt : bypassList) {
        if (link.indexOf(txt) > 0) {
            skip = true

            break
        }
    }
    
    if ((link != startPage) && !(skip)) {
        newLinkList = navigate(from,link, true)
		
		linkFile.append("Level 2\n")
		
		newList = newLinkList
		
		newList.each { it ->
			
			linkFile.append(it+ '\n')
			
		}
		
        if (depth > 1) {
			from = link
            for (def newLink in newLinkList) {
                skip = false

                for (def atxt : bypassList) {
                    if (newLink.indexOf(atxt) > 0) {
                        skip = true

                        break
                    }
                }
                
                if (!(skip) && newLink != home) {
                    newLinkList = navigate(from, newLink, false)

                    WebUI.delay(1)

                    WebUI.navigateToUrl(from)
                }
            }
        }
        
        WebUI.delay(1)

        WebUI.navigateToUrl(startPage)
    }
}

def navigate(def from, def link, def getLinks) {
	
		def ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
			url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
		def sql = new Sql(ds)


		timeStart = new Date()
	    WebUI.navigateToUrl(link)
		timeStop = new Date()
		float duration = (timeStop.getTime()- timeStart.getTime())/1000
		if(first) {
			outFile.write('FROM , TO , ELAPSED TIME\n')
			first = false			
		}
		outFile.append(from + ',' + link + ',' + duration + '\n')
		String whereClause = "WHERE from_url = '" + from + "' AND to_url = '" + link + "'"
		println(whereClause)
		sql.query("SELECT COUNT(*) from navigation_times " + whereClause) { resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		}
		if(rows == 0) { 
//			 sql.query("INSERT INTO navigation_times VALUES(from_url, to_url, duration, duration, duration, 1")
			 sql.executeInsert("INSERT INTO navigation_times VALUES (?, ?, ?, ?, ?, ?)", [from, link, duration, duration, duration, 1])
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
	
	if(getLinks) {
    
		linkList = []
		
	    linkList = WebUI.getAllLinksOnCurrentPage(false, [],FailureHandling.CONTINUE_ON_FAILURE)
		
	//    linkList = WebUI.getAllLinksOnCurrentPage(false, [])
		
		if(linkList == null) {
			WebUI.refresh()
			WebUI.delay(5)
			linkList = WebUI.getAllLinksOnCurrentPage(false, [])
		}
	
	    links = linkList.sort()
	}

    return links
}

