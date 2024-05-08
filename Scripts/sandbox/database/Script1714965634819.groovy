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

//def ds = new BasicDataSource(driverClassName: "org.hsqldb.jdbcDriver",        org.hsqldb.jdbc.JDBCDriver   org.postgresql.Driver
//def ds = new BasicDataSource(driverClassName: "org.postgresql.jdbc.jdbcDriver",
def ds = new BasicDataSource(driverClassName: "org.postgresql.Driver",
    url: 'jdbc:postgresql://localhost:5432/postgres', username: 'Katalon', password: 'katalon')
def sql = new Sql(ds)

String whereClause = "to_url = 'http://missionnext.org/homepage/blog-vlog/'"

sql.query("SELECT COUNT(*) from navigation_times where " + whereClause) { resultSet ->
//sql.query("SELECT COUNT(*) from navigation_times where to_url = 'http://missionnext.org/homepage/blog-vlogx/'") { resultSet ->
	//Retrieving the ResultSetMetaData object

//sql.query("SELECT * from navigation_times where to_url = 'http://missionnext.org/homepage/blog-vlogxxx/'") { resultSet ->
	while (resultSet.next()) {
	  def from = resultSet.getString(1)
//	  def to = resultSet.getString(2)
//	  def min = resultSet.getString(3)
//	  def max = resultSet.getString(4)
//	  def avg = resultSet.getString(5)
//	  def count = resultSet.getString(6)
	  println(from)
//	  println(to)
//	  println(resultSet.getString(6))
//	  println(resultSet.getString(6).getClass())
//	  println(x)
//	  println(resultSet.getString(x))
	}
  }
  
sql.close()
// use then close 'sql' instance ...