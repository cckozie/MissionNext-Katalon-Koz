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
import javax.swing.JOptionPane as JOptionPane
import javax.swing.JFrame as JFrame
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

JFrame frame = new JFrame('My First JFrame')

frame.setAlwaysOnTop(true);
frame.requestFocus()
home = JOptionPane.showInputDialog(frame, 'Enter the website where you would like to begin!')

WebUI.openBrowser(home)

WebUI.maximizeWindow()

frame.requestFocus()

done = false

while (!(done)) {
    depth = JOptionPane.showInputDialog(frame, 'Navigate to 1 level or 2?')

    if ((depth.toInteger() == 1) || (depth.toInteger() == 2)) {
        done = true
    }
}

startPage = home

linkList = WebUI.getAllLinksOnCurrentPage(false, [])

links = linkList.sort()

doneList = []

String combo = ''

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml']

first = true

outFile = new File('/Users/cckozie/Documents/MissionNext/homepagetimingsA.csv')

linkFile = new File('/Users/cckozie/Documents/MissionNext/pagelinks.csv')

linkFile.write(('Level 1 ' + startPage) + '\n')

linksList = links

linksList.each({ def it ->
        linkFile.append(it + '\n')
    })

int rows

float min

float max

float avg

int count

String minTimeStamp

String maxTimeStamp

//int depth = 2 //How deep to go
int level = 0 //current level

String from = startPage

for (def link : links) {
    skip = false

    for (def txt : bypassList) {
        if (link.indexOf(txt) > 0) {
            skip = true

            break
        }
    }
    
    if ((link != startPage) && !(skip)) {
        combo = ((from + ':') + link)

        doneList.add(combo)

        newLinkList = navigate(from, link, true)

        linkFile.append(('Level 2 ' + from) + '\n')

        newList = newLinkList

        newList.each({ def it ->
                linkFile.append(it + '\n')
            })

        if (depth > 1) {
            from = link

            for (def newLink : newLinkList) {
                skip = false

                for (def atxt : bypassList) {
                    if (newLink.indexOf(atxt) > 0) {
                        skip = true

                        break
                    }
                }
                
                combo = ((from + ':') + newLink)

                if (!(skip)) {
                    if (doneList.contains(combo)) {
                        skip = true
                    } else {
                        doneList.add(combo)
                    }
                }
                
                if (!(skip) && (newLink != home)) {
                    newLinkList = navigate(from, newLink, false)

                    WebUI.delay(1)

                    println('Navigating back to url ' + from)

                    WebUI.navigateToUrl(from)
                }
            }
        }
        
        WebUI.delay(1)

        println('Navigating back to url ' + startPage)

        WebUI.navigateToUrl(startPage)
    }
}


WebUI.closeBrowser()

println(doneList)

def navigate(def from, def link, def getLinks) {
	
	def ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
		url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
	def sql = new Sql(ds)

    println('Navigating to url ' + link)

    timeStart = new Date()

    WebUI.navigateToUrl(link)

    timeStop = new Date()

    float duration = (timeStop.getTime() - timeStart.getTime()) / 1000

    if (first) {
        outFile.write('FROM , TO , ELAPSED TIME\n')

        first = false
    }
    
    outFile.append(((((from + ',') + link) + ',') + duration) + '\n')

    String whereClause = "WHERE from_url = '" + from + "' AND to_url = '" + link + "'"

    println(whereClause)

    sql.query('SELECT COUNT(*) from navigation_times ' + whereClause, { def resultSet ->
            while (resultSet.next()) {
                rows = resultSet.getString(1).toInteger()
            }
        })
	now = new Date().format("yyyy-MM-dd HH:mm:ss")

    if (rows == 0) {
        sql.executeInsert('INSERT INTO navigation_times VALUES (?, ?, ?, ?, ?, ?, ?, ?)', [from, link, duration, duration, duration
                , 1, now, now])
    } else {
        sql.query('SELECT * from navigation_times ' + whereClause, { def resultSet ->
                while (resultSet.next()) {
                    min = resultSet.getFloat(3)

                    max = resultSet.getFloat(4)

                    avg = resultSet.getFloat(5)

                    count = resultSet.getInt(6)
					
					minTimeStamp = resultSet.getString(7)

					maxTimeStamp = resultSet.getString(8)
                }
            })
		
		maxTimeStamp = ''
		minTimeStamp = ''

        if (duration > max) {
            max = duration
			maxTimeStamp = now
        } else {
            if (duration < min) {
                min = duration
				minTimeStamp = now
            }
        }
        
        float newAvg = (((avg * count) + duration) / (count + 1)).round(3)

        count++

        sql.executeUpdate("UPDATE navigation_times SET minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count, \
            minimum_time_stamp = $minTimeStamp, maximum_time_stamp = $maxTimeStamp " + whereClause)
    }
    
    if (getLinks) {
        linkList = []

        linkList = WebUI.getAllLinksOnCurrentPage(false, [], FailureHandling.CONTINUE_ON_FAILURE)

        if (linkList == null) {
            WebUI.refresh()

            WebUI.delay(5)

            linkList = WebUI.getAllLinksOnCurrentPage(false, [])
        }
        
        links = linkList.sort()
    }
    
    return links
    
}

