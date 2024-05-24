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

ds = new BasicDataSource(driverClassName: "org.postgresql.Driver", // **** BECAUSE KATALON KEEPS BREAKING THIS COMMAND
		url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
sql = new Sql(ds)

keyStack = []

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes']

JFrame frame = new JFrame('My First JFrame')

frame.requestFocus()

home = "http://missionnext.org/"
home = "https://www.parksidemn.org"

startPage = JOptionPane.showInputDialog(frame, 'Enter the website where you would like to begin!')

WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)

if(startPage == "") {
	startPage = home
}

linkList = []

lastFrom = ''

lastTo = ''

clearTables()

fromPage = ''

linkList = getLinkList(fromPage, startPage)

myKey = startPage

myList = linkList

println(myList)

done = false

while(!done) {
	
//	if(myList != null && !myList.isEmpty()) {
		
		println("The key is " + myKey + " and list is " + myList)
		
		end = writeLinks(myKey, myList)
		
//		if(end =='pop') {
		
		fromPage = myKey
		
		myKey = iterateLinkList(fromPage)
		
		if(myKey != null) {
		
			println("Key value is " + myKey)
			
			sql.executeInsert('INSERT INTO mn_web_pages_accessed VALUES (?)', [myKey])
			
			myList = getLinkList(fromPage, myKey)
			
			println("myList is " + myList)
			
		} else {
			if(!keyStack.isEmpty()) {
				myKey = keyStack.pop()
				myList = dbGetPageLinks(myKey)

				println("======================= Popped myKey is " + myList)
				
			} else {
				done = true
			}
			
		}
		
//	} else {
//		WebUI.delay(60)
//	}
	
}



def getLinkList(fromPage, url) {
	
//	bypass = checkForBypass(url)
	
	println(">>>>>>>>>>>>>>>>>>> Navigating to " + url)

	navigate(fromPage, url)
	
	WebUI.delay(1)
	
    linkList = []

    linkList = WebUI.getAllLinksOnCurrentPage(false, [], FailureHandling.CONTINUE_ON_FAILURE)

    if (linkList == null) {
		WebUI.delay(2)
		
		WebUI.navigateToUrl(url)
			
        WebUI.delay(2)

        linkList = WebUI.getAllLinksOnCurrentPage(false, [])
		
    }
	
	if(linkList.contains(url)) {
		println("Removing url " + url)
	
		linkList.remove(url)
	}
	
	bypassedPages = []
	
	for(lnk in linkList) {
		lnk.replace("http:","https:")
		sql.executeInsert('INSERT INTO all_web_page_links VALUES (?, ?)', [fromPage, lnk])
		
		skip = checkForBypass(lnk)
		if(skip) {
			bypassedPages.add(lnk)
		}
	}
	
	for(lnk in bypassedPages) {
		linkList.remove(lnk)
	}
	
	return linkList
	
}

def checkForBypass(link) {
	println("++++++++++++++++++ Checking url " + link)
	skip = false
	for (def txt : bypassList) {
		if (link.indexOf(txt) > 0) {
			skip = true
			break
		}
	}
	if(!skip) {
		sql.query("SELECT COUNT(*) from mn_web_pages_accessed where url = '" + link + "'", { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})
		if(rows > 0) {
			skip = true
		}
	}
	return skip
}
	
def iterateLinkList(key) {
	println("Iterating overt links for " + key)
	
	list = dbGetPageLinks(key)
	println(list)
		
	for(link in list) {
		sql.query("SELECT COUNT(*) from page_load_times WHERE from_url = '" + link + "'", { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})
		
		if(rows == 0) {
			println("================== Pushing to stack and returning " + link)
			keyStack.push(key)
			return link
		}
	}
}

def dbGetPageLinks(key) {
	println("+++++++++++++++ Finding links")
	list = sql.rows("select to_url from mn_web_page_links where from_url = '" + key + "'")*.to_url
	return list
}


def writeLinks(from, List toList) {
	for(toUrl in toList) {
		println('>>>>>>>>>>>> From ' + from + ' to ' + toUrl + ' <<<<<<<<<<<<<<<<<<<<<')
		sql.executeInsert('INSERT INTO mn_web_page_links VALUES (?, ?)', [from, toUrl])
		if(from == lastFrom && lastTo == toUrl) {
			println('^^^^^^^^^^^ FROM AND TO LINKS ARE THE SAME AS LASTS ^^^^^^^^^^^^^^^')
			return 'pop'
		} else {
			lastFrom = from
			lastTo = toUrl
			return ''
		}
	}
}

def navigate(from, link) {
	
	println('Navigating to url ' + link)
	
//	WebUI.delay(10)

	timeStart = new Date()

	WebUI.navigateToUrl(link)

	timeStop = new Date()

	float duration = (timeStop.getTime() - timeStart.getTime()) / 1000

	String whereClause = ((('WHERE from_url = \'' + from) + '\' AND to_url = \'') + link) + '\''

	println(whereClause)

	sql.query('SELECT COUNT(*) from page_load_times ' + whereClause, { def resultSet ->
			while (resultSet.next()) {
				rows = resultSet.getString(1).toInteger()
			}
		})

	now = new Date().format('yyyy-MM-dd HH:mm:ss')

	if (rows == 0) {
		sql.executeInsert('INSERT INTO page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)', [from, link, duration, duration
				, duration, 1, now, now, now])
	} else {
		sql.query('SELECT * from page_load_times ' + whereClause, { def resultSet ->
				while (resultSet.next()) {
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
		} else {
			if (duration < min) {
				min = duration

				minTimeStamp = now
			}
		}
		
		float newAvg = (((avg * count) + duration) / (count + 1)).round(3)

		count++

		sql.executeUpdate("UPDATE page_load_times SET minimum_time = $min, maximum_time = $max, average_time = $newAvg, test_count = $count,\
			minimum_timestamp = $minTimeStamp, maximum_timestamp = $maxTimeStamp, last_timestamp = $lastTimeStamp " + whereClause)
	}
}
		
def clearTables() {
	sql.executeUpdate("DELETE FROM all_web_page_links")
	sql.executeUpdate("DELETE FROM mn_web_page_links")
	sql.executeUpdate("DELETE FROM mn_web_pages_accessed")
	sql.executeUpdate("DELETE FROM page_load_times")	
}
