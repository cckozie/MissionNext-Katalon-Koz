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
import java.sql.ResultSetMetaData;

//import groovy.sql.Sql
//import org.hsqldb.jdbc.JDBCDataSource


@Grab('org.apache.commons:commons-dbcp2:2.7.0')
import groovy.sql.Sql
import org.apache.commons.dbcp2.BasicDataSource

def ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
    url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
def sql = new Sql(ds)

fnc = 2

if(fnc == 1) {
msg = sql.execute("insert into mn_web_pages (url) values('abc')")
println(msg)
}

if(fnc == 2) {
	pageLinks = []
	pages = []
	
	sql.query('select * from mn_web_pages_bypass') { resultSet ->
		while (resultSet.next()) {
		  page = resultSet.getString(1)
		  pages.add(page)
		}
	}
	println(pages)
}


if(1==2) {
	
//pageLinks = sql.rows('select string from mn_web_pages_bypass')
//println(pageLinks)

//for(page in pageLinks) {
//	println('page is ' + page)
//	myPage = page.join(',')
//	pages.append(myPage)
//}

//sql.eachRow('SELECT firstname, lastname FROM Athlete') { row -> println row[0] + ' ' + row.lastname }


sql.eachRow('select string from mn_web_pages_bypass') { row -> 
   pages.add(row[0] )
}

println(pages)


//sql.execute('delete from mn_web_pages_temp')

urlList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes', 'wp-includes']

valueClause = 'values'

for(url in urlList) {
	valueClause = valueClause + "('" + url + "'),"
}

valueClause = valueClause.substring(0,valueClause.length()-1)

println(valueClause)

//insert into mn_web_pages_temp (url)
sql.executeInsert('insert into mn_web_pages_bypass (string)' + valueClause)
}

sql.close()
