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

date = new Date()
/*
datePart = date.format("dd/MM/yyyy")
println(datePart)

day = datePart.substring(0,2)
month = datePart.substring(3,5)
year = datePart.substring(6,10)

yearInt = year.toInteger()
nextYear = year.toInteger() + 1
println(nextYear)

*/
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