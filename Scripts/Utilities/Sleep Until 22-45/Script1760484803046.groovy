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
import java.time.*
import java.io.File as File

outFile = new File(GlobalVariable.reportPath + 'Sleeping Now.txt')

LocalDateTime now = LocalDateTime.now()

myTime = now.toLocalTime()

outText = 'The time now is ' + myTime

println(outText)

outFile.write(outText + '\n\n')

int seconds = myTime.toSecondOfDay()

int targetTime = 81900 //Approximately 22:45 local time

delay = targetTime - seconds

outText = delay/60 + ' minutes until targetTime'

println(outText)

outFile.append(outText + '\n')

outText = 'Going to sleep now for ' + delay + ' seconds.'

println(outText)

outFile.append(outText + '\n')

outFile = new File(GlobalVariable.reportPath + 'Waking Now.txt')

Thread.sleep(delay * 1000)

now = LocalDateTime.now()

myTime = now.toLocalTime()

outText = 'Waking now at ' + myTime

println(outText)

outFile.write(outText)

