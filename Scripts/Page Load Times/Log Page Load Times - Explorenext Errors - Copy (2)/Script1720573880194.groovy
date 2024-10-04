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
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//							Added primary key on en_web_pages because of dupes in en_web_pages_accessed
//							Modified to ignore links that include '#' (anchor tag)
//							Modified to use db to move missionnext.org links into en_web_page_errors instead of finding them in Katalon
//							Also us db to remove links not containing 'explorenext.org'
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ======== DEFINES ==========
// I have this in there as a comment because Katalon sometimes deletes it!!!
//ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
//	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

sql = new Sql(ds)

sql = new Sql(ds)

outFile = new File('/Users/cckozie/Documents/MissionNext/stackActivity.csv')

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'facebook', 'instagram'
    , 'twitter', 'linkedin', 'browsers']

// Clear the data tables
clearTables()

home = 'https://explorenext.org/'

// Open browser with extension to exclude analytics
WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)

startPage = home

// Define the base for the sql insert values clause
valueClause = 'values'

for (def bypass : bypassList) {
    valueClause = (((valueClause + '(\'') + bypass) + '\'),')
}

valueClause = valueClause.substring(0, valueClause.length() - 1)

sql.executeInsert('INSERT INTO en_web_pages_bypass (string)' + valueClause)

// Call navigate function returning link list
linkList = navigateToUrl('', startPage)

fromPage = startPage

// ======== MAIN ==========
done = false

pageStack = []

// While not done
while (!(done)) {
    // If link list is not empty
    if (!(linkList.isEmpty())) {
        // Sort link list and get first element
        linkList.sort()

        toUrl = (linkList[0])

        // Push element to stack
        pageStack.push(fromPage)

        // Set done flag for element in all pages table
        //===========================================================	
        // Call navigate function returning link list
        lastPage = fromPage

        linkList = navigateToUrl(fromPage, toUrl)

        fromPage = toUrl // Else: link list is empty
        // If stack is not empty
        // Pop link from stack
        // Call navigate function to return to last page before the page that was finished. Return link list
        // Else: stack is empty
        // Set done flag
    } else {
        if (!(pageStack.isEmpty())) {
            toUrl = pageStack.pop()

            linkList = navigateToUrl(lastPage, toUrl)

            fromPage = toUrl
        } else {
            done = true
        }
    }
}

sql.close() // ======== Navigate Function ==========
// Navigate to page collecting load time
// Call Insert/Update Load Times
// Get all links for page
// Delete all current rows and then insert all links into temp table
// If link contians 'missionnext.org' write to error table, if link doesn't contain 'explorenext.org' skip it
// If we found some valid links
//insert into en_web_pages_temp (url)
//		WebUI.delay(1)
// Delete all links for pages to disregard; include any links to self and any pages already accessed
//		WebUI.delay(1)
//		WebUI.delay(1)
//		WebUI.delay(1)
// Copy remaining temp rows to all pages table
// Load link list from links table and return to caller
// ======== Insert/Update Load Times ==========

def navigateToUrl(def from, def link) {
    println((('>>>>>>>>>>>>>>>> Navigating from ' + from) + ' to  ') + link)

    timeStart = new Date()

    WebUI.navigateToUrl(link)

    timeStop = new Date()

    float duration = (timeStop.getTime() - timeStart.getTime()) / 1000

    String whereClause = ((('WHERE max_time_from_url = \'' + from) + '\' AND to_url = \'') + link) + '\''

    println(whereClause)

    updateDB(from, link, whereClause, duration)

    WebUI.navigateToUrl(link)

    WebUI.delay(1)

    linkList = []

    pageLinks = []
	
	notBlog = WebUI.verifyTextNotPresent('RECENT POSTS', false, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(notBlog) {

	    WebDriver driver = DriverFactory.getWebDriver()
	
	    List<WebElement> linkList = driver.findElements(By.tagName('a'))
	
	    if (linkList == null) {
	        WebUI.delay(2)
	
	        WebUI.navigateToUrl(link)
	
	        WebUI.delay(2)
	
	        linkList = driver.findElements(By.tagName('a'))
	    }
	    
	    if (linkList != null) {
	        sql.execute('delete from en_web_pages_temp')
	
	        valueClause = 'values'
			
			elementNo = 0
			
			listSize = linkList.size()
	
	        for (def lnk : linkList) {
				elementNo ++
				try {
					url = lnk.getAttribute('href')
				}
				catch(e) {
					println('***************** ERROR *****************')
					println('on link ' + elementNo + ' of ' + listSize)
					println(e)
					WebUI.delay(1)
					WebUI.navigateToUrl(link)
					WebUI.delay(2)
					url = lnk.getAttribute('href')
				}
				
	            if (url != null && url.indexOf('#') < 0) { 
                    if (url.indexOf('missionnext.org') >= 0) {
                        reference = lnk.getText()

                        sql.executeInsert('INSERT INTO en_web_page_errors VALUES(?, ?, ?)', [link, reference, url])
                    } else if (url.indexOf('explorenext.org') >= 0) {
                        valueClause = (((valueClause + '(\'') + url) + '\'),')
                    }
                } 
            }
        }
	        
        valueClause = valueClause.substring(0, valueClause.length() - 1)

        println('########## valueClause: ' + valueClause)

        println(valueClause)

        pageLinks = []

        if (valueClause != 'value') {
            sql.executeInsert('insert into en_web_pages_temp (url)' + valueClause)

            sql.execute('delete from en_web_pages_temp a where exists (select 1 from en_web_pages_bypass b where a.url like \'%\' || b.string || \'%\')')

            sql.execute('delete from en_web_pages_temp a where exists (select 1 from en_web_pages b where a.url = b.url)')

            sql.execute(('delete from en_web_pages_temp a where url = \'' + link) + '\'')

            sql.eachRow('select url from en_web_pages_temp', { def row ->
                    pageLinks.add(row[0])
                })
        }
			
    } else {
		println('------------ BLOG PAGE -------------')
    }
	
    
//    println(pageLinks)

    return pageLinks
}

def updateDB(def fromUrl, def myUrl, def where, def duration) {
    now = new Date().format('yyyy-MM-dd HH:mm:ss')

    toWhere = (('WHERE to_url = \'' + myUrl) + '\'')

    sql.query('SELECT COUNT(*) from en_page_load_times ' + toWhere, { def resultSet ->
            while (resultSet.next()) {
                rows = resultSet.getString(1).toInteger()
            }
        })

    if (rows == 0) {
        sql.executeInsert('INSERT INTO en_page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)', [fromUrl, myUrl, duration
                , duration, duration, 1, now, now, now])
    } else {
        sql.query('SELECT * from en_page_load_times ' + toWhere, { def resultSet ->
                while (resultSet.next()) {
                    lastFrom = resultSet.getString(1)

                    min = resultSet.getFloat(3)

                    max = resultSet.getFloat(4)

                    avg = resultSet.getFloat(5)

                    count = resultSet.getInt(6)

                    minTimeStamp = resultSet.getString(7)

                    maxTimeStamp = resultSet.getString(8)

                    lastTimeStamp = resultSet.getString(8)
                }
            })

        maxTimeStamp = ''

        minTimeStamp = ''

        lastTimeStamp = now

        if (duration > max) {
            max = duration

            maxTimeStamp = now

            maxUrl = fromUrl
        } else {
            maxUrl = lastFrom

            if (duration < min) {
                min = duration

                minTimeStamp = now
            }
        }
        
        float newAvg = (((avg * count) + duration) / (count + 1)).round(3)

        count++

        sql.executeUpdate("UPDATE en_page_load_times SET max_time_from_url = $maxUrl, minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count,			minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp " + 
            toWhere)
    }
    
    sql.executeInsert('INSERT INTO en_web_pages VALUES (?)', [myUrl])
}

def clearTables() {
    println('******************* Clearing tables ***********************')

    sql.execute('DELETE FROM en_web_pages_bypass')

    sql.execute('DELETE FROM en_web_pages')

    sql.execute('DELETE FROM en_web_page_errors')

    sql.execute('DELETE FROM en_page_load_times')

    WebUI.delay(5)
}

