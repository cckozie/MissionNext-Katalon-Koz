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
import javax.swing.JOptionPane as JOptionPane
import javax.swing.JFrame as JFrame
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

// ======== DEFINES ==========
ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
sql = new Sql(ds)

sql = new Sql(ds)

outFile = new File('/Users/cckozie/Documents/MissionNext/stackActivity.csv')

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes']

home = 'https://missionnext.org/'

// Open browser with extension to exclude analytics

WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)

// Prompt for start page 

JFrame frame = new JFrame('My First JFrame')

frame.requestFocus()

home = 'https://missionnext.org/'

startPage = JOptionPane.showInputDialog(frame, 'Enter the website where you would like to begin!')

if (startPage == '') {
	startPage = home
}

// Clear the data tables
clearTables()

// Call navigate function returning link list

linkList = navigateToUrl('', startPage)

fromPage = startPage

// ======== MAIN ==========

done = false

pageStack = []

// While not done
while(!done) {

	// If link list is not empty
	if(!linkList.isEmpty()) {
	
		// Sort link list and get first element
		linkList.sort()
		
		toUrl = linkList[0]
	
		// Push element to stack
		pageStack.push(fromPage)
	
		// Set done flag for element in all pages table
//===========================================================	
		// Call navigate function returning link list
		lastPage = fromPage
		
		linkList = navigateToUrl(fromPage, toUrl)
		
		fromPage = toUrl
		
	// Else: link list is empty
	} else {
	
		// If stack is not empty
		if(!pageStack.isEmpty()) {
	
			// Pop link from stack
			toUrl = pageStack.pop()
	
			// Call navigate function to return to last page before the page that was finished. Return link list
			linkList = navigateToUrl(lastPage, toUrl)
			
			fromPage = toUrl
			
		} else {
		// Else: stack is empty
	
			// Set done flag
			done = true
		}
	}
}

sql.close()
// ======== Navigate Function ==========
// Navigate to page collecting load time

def navigateToUrl(def from, def link) {
	println('Navigating to url ' + link)

	timeStart = new Date()

	WebUI.navigateToUrl(link)

	timeStop = new Date()

	float duration = (timeStop.getTime() - timeStart.getTime()) / 1000

	String whereClause = ((('WHERE max_time_from_url = \'' + from) + '\' AND to_url = \'') + link) + '\''

	println(whereClause)
	
	// Call Insert/Update Load Times
	updateDB(from, link, whereClause, duration)

	// Get all links for page
    WebUI.navigateToUrl(link)

    WebUI.delay(1)

    linkList = []

    linkList = WebUI.getAllLinksOnCurrentPage(false, [], FailureHandling.CONTINUE_ON_FAILURE)

    if (linkList == null) {
        WebUI.delay(2)

        WebUI.navigateToUrl(link)

        WebUI.delay(2)

        linkList = WebUI.getAllLinksOnCurrentPage(false, [])
    }

	// Delete all current rows and then insert all links into temp table
	sql.execute('delete from mn_web_pages_temp')
	
	valueClause = 'values'

	for(url in linkList) {
		valueClause = valueClause + "('" + url + "'),"
	}
	
	valueClause = valueClause.substring(0,valueClause.length()-1)
	
	println(valueClause)
	
	//insert into mn_web_pages_temp (url)
	sql.executeInsert('insert into mn_web_pages_temp (url)' + valueClause)
	
	// Delete all links for pages to disregard; include any links to self and any pages already accessed
	sql.execute("delete from mn_web_pages_temp a where exists (select 1 from mn_web_pages_bypass b where a.url like '%' || b.string || '%')")
	
	sql.execute("delete from mn_web_pages_temp a where exists (select 1 from mn_web_pages_accessed b where a.url = b.url)")
	
	sql.execute("delete from mn_web_pages_temp a where url = '" + link + "'")
	
	// Copy remaining temp rows to all pages table

// Load link list from links table and return to caller
	 
	pageLinks = []
	sql.eachRow('select url from mn_web_pages_temp') { row ->
		pageLinks.add(row[0])
	}
	
	println(pageLinks)
	
	return pageLinks
	



}

// ======== Insert/Update Load Times ==========
def updateDB(def fromUrl, def myUrl, def where, def duration) {

	now = new Date().format('yyyy-MM-dd HH:mm:ss')
	
	sql.query('SELECT COUNT(*) from page_load_times ' + where, { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})
	
	if (rows == 0) {
		sql.executeInsert('INSERT INTO page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)', [fromUrl, myUrl, duration, duration
				, duration, 1, now, now, now])
	} else {
		sql.query('SELECT * from page_load_times ' + where, { def resultSet ->
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
	
		sql.executeUpdate("UPDATE page_load_times SET max_time_from_url = $maxUrl, minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count,			minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp " +
			where)
	}
//************************************** WHY SO MANY ROWS?	
	sql.executeInsert('INSERT INTO mn_web_pages_accessed VALUES (?)', [myUrl])
	
}

def clearTables() {
	println('******************* Clearing tables ***********************')
	sql.execute('DELETE FROM all_web_page_links')

	sql.execute('DELETE FROM mn_web_page_links')

	sql.execute('DELETE FROM mn_web_pages_accessed')

	sql.execute('DELETE FROM page_load_times')
	
	WebUI.delay(10)
}

