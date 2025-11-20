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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils
import javax.swing.*;
import groovy.io.FileType

type = 'Error'  // All or Errors

varFormat = "YY-MM-dd"

now = new Date()

today = now.format(varFormat)

then = now - 1

yesterday = then.format(varFormat)

println(today)

println(yesterday)


list = []

filePath = "/Users/cckozie/Documents/MissionNext/Test Reports/"

dir = new File(filePath)

dir.eachFile (FileType.FILES) { file ->
  list << file
}

list.sort()

list.each{
	file = it.toString()

	if(file.contains(today) && file.contains('.csv') && file.contains(type)) {
		nowFile = it
	} else if(file.contains(yesterday) && file.contains('.csv') && file.contains(type)) {
		thenFile = it
	}
}

println('now file is ' + nowFile)

println('then file is ' + thenFile)


files = [nowFile, thenFile]

oldMap = [:]

newMap = [:]

maps = [oldMap, newMap]

for(myFile in files) {
	
	if(myFile == thenFile) {
		myMap = oldMap
	} else {
		myMap = newMap
	}
	
	lines = myFile.readLines()
	
	lines.each{
		fields = it.split(',')
		
		if(fields[0].isNumber()) {
			count = fields.size()
			values = []
			for(i = 1; i < count; i++) {
				values.add(fields[i].replace("'",""))
			}
			myMap.put(fields[0], values)
		}
	}
	
	myMap.each {
		println(it)
	}
	println('\n--------------------------\n--------------------------\n--------------------------\n--------------------------\n--------------------------')
}

println('oldFile has ' + oldMap.size() + ' candidates')

println('newFile has ' + newMap.size() + ' candidates')

oldKeys = oldMap.keySet()

newKeys = newMap.keySet()

commonKeys = oldKeys.intersect(newKeys)

commonKeys.sort()

runDate = now.format("MM/dd/YY")

thenDate = then.format("MM/dd/YY")

outFile = new File(filePath + 'Candidate Expiration Date Changes_' + today + '.csv')

outFile.write('Run Date,User ID,Username,Email,First Name,Last Name,Last Login,End Date,Calculated,Errors\n')

logFile = new File(filePath + 'Candidate Expiration Date Changes_Log.csv')

if(!logFile.exists()) {
	logFile.write('Run Date,User ID,Username,Email,First Name,Last Name,Last Login,End Date,Calculated,Errors\n')
} else {
	logFile.append('\n')
}

commonKeys.each {
	oldValues = oldMap.get(it)
	
	newValues = newMap.get(it)
	
	if(oldValues != newValues) {
		
		println(runDate + ',' + it + ',' + oldValues.join(','))
		outFile.append(thenDate + ',' + it + ',' + oldValues.join(',') + '\n')
		logFile.append(thenDate + ',' + it + ',' + oldValues.join(',') + '\n')
		
		println(runDate + ',' + it + ',' + newValues.join(','))
		outFile.append(runDate + ',' + it + ',' + newValues.join(',') + '\n')
		logFile.append(runDate + ',' + it + ',' + newValues.join(',') + '\n')
	}
}


