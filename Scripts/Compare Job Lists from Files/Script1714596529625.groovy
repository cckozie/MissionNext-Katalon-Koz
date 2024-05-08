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
import com.sun.jna.platform.FileUtils as FileUtils
import groovy.json.JsonSlurper

jsonString = new File('/Users/cckozie/Documents/MissionNext/List_Category_Map.txt').text.readLines()

Map List_Category_Map = new JsonSlurper().parseText(jsonString)

List_Category_Map.each({key, val ->
	println((key + ' : ') + val)
})

jsonString = new File('/Users/cckozie/Documents/MissionNext/Detail_Category_Description.txt').text.readLines()

Map Detail_Category_Description = new JsonSlurper().parseText(jsonString)

Detail_Category_Description.each({key, val ->
	println((key + ' : ') + val)
})

jsonString = new File('/Users/cckozie/Documents/MissionNext/Summary_Category_Map.txt').text.readLines()

Map Summary_Category_Map = new JsonSlurper().parseText(jsonString)

Summary_Category_Map.each({key, val ->
	println((key + ' : ') + val)
})



Detail_Category_Description.each({key, val ->
	
	println('Detail Map -- ' + key + ':' + val)
	
	joblist = List_Category_Map.get(key)
	
	println('List Map -- ' + key + ':' + joblist)
	
	val.each({ job ->
			
		int found = joblist.indexOf(job)
		
		if(found >= 0) {
			
			println("----- Job " + job + " was NOT found in list for category " + key)
			
		} else {

			println("+++++ Job " + job + " was found for category " + key)

		}
	})

	WebUI.delay(60)
	
})
