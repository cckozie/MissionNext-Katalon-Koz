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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//							Reads page urls from mn_web_pages_accessed and updates page_load_times
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ======== DEFINES ==========
//ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
//	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
	url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')

sql = new Sql(ds)

// Open browser with extension to exclude analytics
WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)

from = ''

loops = 1

loop = 1

scriptStart = new Date()

while (loop <= loops) {
    // Delete the pages accessed log
    sql.execute('DELETE FROM mn_web_pages')

    pageCount = 0

    pages = []

    sql.query('select distinct url from mn_web_pages_accessed', { def resultSet ->
            while (resultSet.next()) {
                page = resultSet.getString(1)

                pages.add(page)
            }
        })

    for (def link : pages) {
        println((('>>>>>>>>>>>>>>>> Navigating from ' + from) + ' to  ') + link)

        timeStart = new Date()
		
		try {

			WebUI.navigateToUrl(link)

	        pageCount++
	
	        timeStop = new Date()
	
	        float duration = (timeStop.getTime() - timeStart.getTime()) / 1000
	
	        //	String whereClause = ((('WHERE max_time_from_url = \'' + from) + '\' AND to_url = \'') + link) + '\''
	        String whereClause = ('WHERE to_url = \'' + link) + '\''
	
	        println('Where clause is ' + whereClause)
	
	        // Call Insert/Update Load Times
	        updateDB(from, link, whereClause, duration)
	
	        println(((('++++++++++++ Page count is ' + pageCount) + ' on loop ') + loop) + ' +++++++++++++++')
	
	        //		WebUI.delay(1)
	        from = link
		}
		
		catch (Exception e) {

			now = new Date().format('yyyy-MM-dd HH:mm:ss')

			sql.executeInsert('INSERT INTO mn_web_page_load_errors VALUES (?,?)', [link, now])
			
		}
			
    }
    
    loop++
}

sql.close()

WebUI.closeBrowser()

scriptStop = new Date()

duration = ((scriptStop.getTime() - scriptStart.getTime()) / 1000)

println('Run time was ')

println((duration / 60).round(2))

println(((('Run time was ' + (duration / 60).round(2)) + ' minutes for ') + loops) + ' loops.') // ======== Insert/Update Load Times ==========

def updateDB(def fromUrl, def myUrl, def where, def duration) {
    now = new Date().format('yyyy-MM-dd HH:mm:ss')

    sql.query('SELECT COUNT(*) from page_load_times ' + where, { def resultSet ->
            while (resultSet.next()) {
                rows = resultSet.getString(1).toInteger()
            }
        })

    if (rows == 0) {
        println('>>>>>>>>>>>> Inserting row for page at ' + myUrl)

        sql.executeInsert('INSERT INTO page_load_times VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)', [fromUrl, myUrl, duration, duration
                , duration, 1, now, now, now])
    } else {
        println('^^^^^^^^^^^ Updating row for page at ' + myUrl)

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
    
    sql.executeInsert('INSERT INTO mn_web_pages VALUES (?)', [myUrl])

}

