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

// NEED TO WRITE ALL LINKS TO WEB_PAGES TABLE SO WE DON'T REPROCESS LINKS
// (necessary because I have been bypassing links in Katalon so they get reprocessed and re-bypassed when popping from the stack

// Add a row to the web pages table of the from_url and to_url for all links found. After navigating to a page, set the done flag for that row.
// Before navigating to a page, ensure that that there is not a row with the url that has the done flag set.
// When popping a url from the stack, instead of navigating to that page, read the page links from the db table for that from page
// Never navigate to a page whose url contains a string in the bypass table

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//							Added primary key on mn_web_pages because of dupes in mn_web_pages_accessed
//							Modified to ignore links that include '#' (anchor tag)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ======== DEFINES ==========
// I have this in there as a comment because Katalon sometimes deletes it!!!
//ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
//	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

//sql = new Sql(ds)

sql = new Sql(ds)

tStart = new Date()

startTime = new Date().format('yyyy-MM-dd HH:mm:ss')

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Log Page Load Times - missionnext Errors ' + tStart.getTime() + '.txt')

outFile.write('Testing Log Page Load Times - missionnext Errors\'s\n')

badLinkFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Log Bad Links - missionnext.txt')

badLinkFile.write('Testing Log Page Load Times - missionnext Errors\'s\n')

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes', 'facebook', 'instagram'
    , 'twitter', 'linkedin', 'browsers', '#', 'group']

// Clear the data tables
clearTables()

home = 'https://missionnext.org/'
//home = 'https://journey.explorenext.org/'

// Open browser with extension to exclude analytics
//WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)
WebUI.openBrowser('')

WebUI.maximizeWindow()

startPage = home

// Define the base for the sql insert values clause
valueClause = 'values'

for (def bypass : bypassList) {
    valueClause = (((valueClause + '(\'') + bypass) + '\'),')
}

valueClause = valueClause.substring(0, valueClause.length() - 1)

sql.executeInsert('INSERT INTO mn_web_pages_bypass (string)' + valueClause)

// Call navigate function returning link list
linkList = navigateToUrl('', startPage)

fromPage = startPage

// ======== MAIN ==========
done = false

pageStack = []

pageCount = 1
// While not done
//while (!(done) && pageCount < 10) {
while (!(done)) {
    // If link list is not empty
    if (!(linkList.isEmpty())) {
        // Sort link list and get first element
        linkList.sort()

        toUrl = (linkList[0])
				
//		nextUrl = toUrl.toLowerCase()
		
//		if(!nextUrl.contains('missionnext.')) {


	        // Push element to stack
	        pageStack.push(fromPage)
			outFile.append('Pushing ' + fromPage + '\n')
	
	        // Set done flag for element in all pages table
	        //===========================================================	
	        // Call navigate function returning link list
	        lastPage = fromPage
	
			outFile.append('Navigating from ' + fromPage + ' to ' + toUrl + '\n')
	        linkList = navigateToUrl(fromPage, toUrl)
			pageCount ++
	
	        fromPage = toUrl 
//		} else {
//			badLinkFile.append("'" + toUrl + "' is a link to MISSIONNEXT from  " + fromPage + ".\n")
//		}

    } else {
		// Else: link list is empty
		// If stack is not empty
		//   Pop link from stack
		//   Retrive the link list from mn_web_page_links
		// Else: stack is empty
		//   Set done flag

        if (!(pageStack.isEmpty())) {
//		if (pageStack.size() > 0) {
				
			outFile.append('Marking page ' + toUrl + ' as done\n')
			
			sql.execute("update mn_web_page_links set processed = true where to_url = '" + toUrl + "'")
			
            fromPage = pageStack.pop()
			
			outFile.append('Popped ' + fromPage + '\n')
			
			println('POPPED LINK IS ' + fromPage)
/*
			if (!(pageStack.isEmpty())) {
				
	            fromPage = pageStack.pop()
			
				println('POPPED LINK IS ' + fromPage)
			
				outFile.append('Popped ' + fromPage + '\n')
			
			}
*/
            sql.eachRow("select to_url from mn_web_page_links where processed = false and from_url = '" + fromPage + "'", { def row ->
                    linkList.add(row[0])
                })
//            links = sql.execute("select to_url from mn_web_page_links where from_url = '" + fromPage + "'")
			
			println(linkList)
			
			outFile.append('Popped linkList is ' + linkList + '\n')
        } else {
            done = true
        }
    }
}

sql.close() 

stopTime = new Date().format('yyyy-MM-dd HH:mm:ss')
println('Started at : ' + startTime)
println('Ended at   : ' + stopTime)
tStop = new Date()
println('Elapsed time = ' + tStop.getTime() - tStart.getTime())
outFile.append('Started at ' + tStart.format('yyyy-MM-dd HH:mm:ss') +'\n')
outFile.append('Ended at   ' + tStop.format('yyyy-MM-dd HH:mm:ss') +'\n')
outFile.append('Navigated to ' + pageCount + ' pages.\n')


// ======== Navigate Function ==========
// Navigate to page collecting load time
// Call Insert/Update Load Times
// Get all links for page
// Delete all current rows and then insert all links into temp table
// If link contians 'missionnext.org' write to error table, if link doesn't contain 'explorenext.org' skip it
// If we found some valid links
//insert into mn_web_pages_temp (url)
//		WebUI.delay(1)
// Delete all links for pages to disregard; include any links to self and any pages already accessed
//		WebUI.delay(1)
//		WebUI.delay(1)
//		WebUI.delay(1)
// Copy remaining temp rows to web pages table
// Load link list from links table and return to caller
// ======== Insert/Update Load Times ==========

def navigateToUrl(def from, def link) {
	
    println((('>>>>>>>>>>>>>>>> Navigating from ' + from) + ' to  ') + link)
	
    timeStart = new Date()

//    WebUI.navigateToUrl(link)
	WebUI.navigateToUrl(link, FailureHandling.CONTINUE_ON_FAILURE)
	
	goodLink = WebUI.verifyTextNotPresent('Check if there is a typo', false, FailureHandling.CONTINUE_ON_FAILURE)
	if(!goodLink) {
		badLinkFile.append("'" + link + "' from " + from + " is a bad link.\n")
	}

	pageTitle = WebUI.getWindowTitle()
	
	if(pageTitle.contains('Page not found')) {
		badLinkFile.append("\n'" + link + "' from " + from + " loaded the 'Oops!' page.\n")
		sql.executeInsert("insert into mn_web_page_not_found values('" + from + "', '" + link + "')")
	}
	
	noCriticalError = WebUI.verifyTextNotPresent('There has been a critical error on this website.', false, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(!noCriticalError) {
		badLinkFile.append("\n'" + link + "' from " + from + " has a critical error.\n")
		sql.executeInsert("insert into mn_web_page_critical_error values('" + from + "', '" + link + "')")
	}
/*
		 
//		WebUI.back()
		System.exit(0)
		
		linkList = []
		
	} else {
*/
	    timeStop = new Date()
	
	    float duration = (timeStop.getTime() - timeStart.getTime()) / 1000
	
	    String whereClause = ((('WHERE max_time_from_url = \'' + from) + '\' AND to_url = \'') + link) + '\''
	
	    println(whereClause)
	
	    updateDB(from, link, whereClause, duration)
	
//	    WebUI.navigateToUrl(link)
		WebUI.navigateToUrl(link, FailureHandling.CONTINUE_ON_FAILURE)

	
	    WebUI.delay(1)
	
	    linkList = []
	
	    pageLinks = []
		
		notBlog = WebUI.verifyTextNotPresent('RECENT POSTS', false, FailureHandling.CONTINUE_ON_FAILURE)
//		notBlog = WebUI.verifyTextNotPresent('Share on:', false, FailureHandling.CONTINUE_ON_FAILURE)
		
//		if(notBlog) {
		if(notBlog && goodLink) {
				
		    WebDriver driver = DriverFactory.getWebDriver()
		
		    List<WebElement> linkList = driver.findElements(By.tagName('a'))
		
		    if (linkList == null) {
		        WebUI.delay(2)
		
		        WebUI.navigateToUrl(link)
		
		        WebUI.delay(2)
		
		        linkList = driver.findElements(By.tagName('a'))
				
		    }
		    
		    if (linkList != null) {
		        sql.execute('delete from mn_web_pages_temp')
		
		        valueClause = 'values'
				
				elementNo = 0
				
				listSize = linkList.size()
		
				println(listSize)
				
		        for (def lnk : linkList) {
					elementNo ++
					try {
						url = lnk.getAttribute('href')
		                valueClause = (((valueClause + '(\'') + url) + '\'),')
					}
					catch(e) {
						println('***************** STALE REFERENCE ERROR on link ' + elementNo + ' of ' + listSize + '*****************')
					}
	            }
	        }
		        
	        valueClause = valueClause.substring(0, valueClause.length() - 1)
	
	        pageLinks = []
	
	        if (valueClause != 'value') {
	            sql.executeInsert('insert into mn_web_pages_temp (url)' + valueClause)
	
	            sql.executeInsert("insert into mn_web_page_errors select '" + link + "', url from mn_web_pages_temp where url like '%missionnext.org%'")
	
	//			sql.execute("delete from mn_web_pages_temp where url like '%missionnext.org%' or url not like '%missionnext.org%' or url like '%#%'")
				sql.execute("delete from mn_web_pages_temp where url not like '%missionnext.org%'")
				
	            sql.execute('delete from mn_web_pages_temp a where exists (select 1 from mn_web_pages_bypass b where a.url like \'%\' || b.string || \'%\')')
	
	            sql.execute('delete from mn_web_pages_temp a where exists (select 1 from mn_web_page_links b where a.url = b.from_url or a.url = b.to_url)')
	
	            sql.execute(('delete from mn_web_pages_temp a where url = \'' + link) + '\'')
				
	            sql.executeInsert("insert into mn_web_page_links select '" + link + "', url from mn_web_pages_temp")
				
	            sql.eachRow('select url from mn_web_pages_temp', { def row ->
	                    pageLinks.add(row[0])
	                })
	        }
				
	    } else {
			println('------------ BLOG PAGE ------------- or BAD LINK')
	    }
//	}
    
//    println(pageLinks)

    return pageLinks
}

def updateDB(def fromUrl, def myUrl, def where, def duration) {
    now = new Date().format('yyyy-MM-dd HH:mm:ss')

    toWhere = (('WHERE to_url = \'' + myUrl) + '\'')

    sql.query('SELECT COUNT(*) from mn_page_load_times ' + toWhere, { def resultSet ->
            while (resultSet.next()) {
                rows = resultSet.getString(1).toInteger()
            }
        })

    maxTimeStamp = ''

    minTimeStamp = ''

    lastTimeStamp = now

    if (rows == 0) {
        sql.executeInsert('INSERT INTO mn_page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)', [fromUrl, myUrl, duration
                , duration, duration, 1, now, now, now])
    } else {
        sql.query('SELECT * from mn_page_load_times ' + toWhere, { def resultSet ->
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

        sql.executeUpdate("UPDATE mn_page_load_times SET max_time_from_url = $maxUrl, minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count,			minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp " + 
            toWhere)
    }
    
    sql.executeInsert('INSERT INTO mn_web_pages VALUES (?)', [myUrl])
}

def clearTables() {
    println('******************* Clearing tables ***********************')

    sql.execute('DELETE FROM mn_web_pages_bypass')

    sql.execute('DELETE FROM mn_web_pages')

    sql.execute('DELETE FROM mn_web_page_errors')

    sql.execute('DELETE FROM mn_web_page_not_found')

    sql.execute('DELETE FROM mn_web_page_critical_error')

    sql.execute('DELETE FROM mn_web_page_links')

    sql.execute('DELETE FROM mn_page_load_times')

//    WebUI.delay(5)
}

/*
Started at : 2024-07-11 18:24:13
2024-07-11 18:26:39.389 DEBUG Log Page Load Times - missionnext Errors - 25: println("Ended at   : " + stopTime)
Ended at   : 2024-07-11 18:26:39
*/