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
import java.text.SimpleDateFormat
//import java.time.LocalDateTime
import java.time.*

date = new Date()
start = date.format("yyyy-MM-dd")
year = start.substring(0,4)
month = start.substring(5,7)
day = start.substring(8,)
if(1==2) {
	endYear = year.toInteger() + 1
	end = endYear + '-' + month + '-' + day
} else {
	endYear = year.toInteger() + 5
	end = endYear + '-12-31'
}
println(start)
println(end)

LocalDateTime now = LocalDateTime.now()
println(now)
myTime = now.toLocalTime()
println(myTime)
int seconds = myTime.toSecondOfDay()
println(seconds)
int midnightPlus = 90000
delay = midnightPlus - seconds
println(delay)
delay = 15
Thread.sleep(delay * 1000)
println('i waited')
//LocalDateTime release = LocalDateTime.of(2019, 10, 30, 13, 30);