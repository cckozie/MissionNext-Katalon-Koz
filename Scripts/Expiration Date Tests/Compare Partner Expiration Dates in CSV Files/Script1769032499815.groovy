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
import com.kms.katalon.core.util.KeywordUtil

type = 'All'  // All or Errors

domain = GlobalVariable.domain

loggedIn = false

varFormat = "YY-MM-dd"

now = new Date()

today = now.format(varFormat)

todayHHMM = now.format('YY-MM-dd.HH-mm')

then = now - 1

yesterday = then.format(varFormat)

println(today)

println(yesterday)


list = []

filePart = 'Test Reports/Test Partner Expiration Dates_All'

filePath = "/Users/cckozie/Documents/MissionNext/Test Reports/"

dir = new File(filePath)

dir.eachFile (FileType.FILES) { file ->
  list << file
}

list.sort()

files = []

for(file in list) {
	myFile = file.toString()
	if(myFile.contains(filePart) && myFile.contains('.csv')) {
		files.add(myFile)
	}
}
/*
files.each {
	if(it.contains(type)) {
		println(it)
	}
}
*/
count = files.size()

println(files[count-2])

nowFile = '?'

thenFile = '?'


for(file in files) {
	println(file)
	if(file.contains(today)) {
		nowFile = new File(file)
	} else {
		thenFile = new File(files[count-2])
	}
}

if(nowFile == '?') {
	outText = '\n##################################################\n'
	outText += "YOU MUST RUN SCRIPT 'Test Partner Expiration Dates' with options for 'All' and 'errorsOnly = false' to get the current list.\n"
	outText += '##################################################'
	KeywordUtil.markFailedAndStop(outText)
}

println('now file is ' + nowFile)

if(thenFile == '?') {
	outText = '\n##################################################\n'
	outText += "COULD NOT FIND A 'Test Partner Expiration Dates' FILE FOR " + yesterday + "\n"
	outText += '##################################################'
	KeywordUtil.markFailedAndStop(outText)

}

println('then file is ' + thenFile)

thenFileName = thenFile.getName()

allIndex = thenFileName.indexOf('All')

thenYY = thenFileName.substring(allIndex + 4, allIndex + 6)

thenMM = thenFileName.substring(allIndex + 7, allIndex + 9)

thenDD = thenFileName.substring(allIndex + 10, allIndex + 12)

thenDate = thenMM + '/' + thenDD + '/' + thenYY

println(thenDate)

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
//				values.add(fields[i].replace("'",""))
//				values.add(fields[i].replace('"',''))
				values.add(fields[i])
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

println(then)

//thenDate = then.format("MM/dd/YY")
//thenDate = new Date().parse("MM/dd/YY", then)

outFile = new File(filePath + 'Partner Expiration Date Changes_' + todayHHMM + '.csv')

outText = 'Run Date,User ID,Username,Email,Organization,Start,Expires,Last Login,Status,Days Remaining,Grace?'

outFile.write(outText + '\n')

logFile = new File(filePath + 'Partner Expiration Date Changes_Log.csv')

if(!logFile.exists()) {
	logFile.write('Run Date,User ID,Username,Email,Organization,Start,Expires,Status,Days Remaining,Grace?\n')
} else {
	logFile.append('\n')
}

count = 0

changes = [:]

//commonKeys.each {
for(it in commonKeys) {
	
//	it = commonKey.key
	
	oldValues = oldMap.get(it)
	
	newValues = newMap.get(it)
	
	if(oldValues != newValues) {
//		println(oldValues)
//		println(newValues)
		
		if(oldValues[4] != newValues[4]) {
			
			println(oldValues)
			
			println(newValues)
						
			count++
			
			lastLogin = getKeyDates(oldValues[0])
			
			println('Last login was ' + lastLogin)
			
			oldValues.add(5, lastLogin)
			
			newValues.add(5, lastLogin)
			
			dates = []
			
			dates.addAll(thenDate, oldValues[4], newValues[4], oldValues[5])
			
			changes.put(oldValues[0], dates)
			
			println(runDate + ',' + it + ',' + oldValues.join(','))
			outFile.append(thenDate + ',' + it + ',' + oldValues.join(',') + '\n')
			
			if(varUpdateLogFile) {
				logFile.append(thenDate + ',' + it + ',' + oldValues.join(',') + '\n')
			}
			
			println(runDate + ',' + it + ',' + newValues.join(','))
			outFile.append(runDate + ',' + it + ',' + newValues.join(',') + '\n')

			if(varUpdateLogFile) {
				logFile.append(runDate + ',' + it + ',' + newValues.join(',') + '\n')
			}
		}
	}
}
if(count == 0) {
	outFile.append(">>>>> No Changes")
} else {

	WebUI.closeBrowser()
//if(loggedIn) {
	String subject = count + ' Partner Expiration Dates Have Changed'
	
	String text = ''
	
	varFormat = "MM/dd/YY"
	
	for(change in changes) {
		
		username = change.key
		
		thenDate = change.value[0]
		
		oldDate = new Date().parse("yyyy-MM-dd", change.value[1])
		
		oldDate = oldDate.format(varFormat)
		
//		oldDate = change.value[1]
		
//		newDate = change.value[2]
		
		newDate = new Date().parse("yyyy-MM-dd", change.value[2])
		
		newDate = newDate.format(varFormat)
		
//		loginDate = change.value[3]
	
		loginDate = new Date().parse("yyyy-MM-dd", change.value[3])
		
		loginDate = loginDate.format(varFormat)
		
		text += "On " + thenDate + " " + username + " had expiration date " + oldDate + ". It is now " + newDate + ". Last login was on " + loginDate + ".\n"
	}
	
	text += '\nOutput file is file:///' + outFile
	
	WebUI.callTestCase(findTestCase('_Functions/Java Send Email'), [('varSubject'):subject, ('varText'):text], FailureHandling.STOP_ON_FAILURE)

}

WebUI.closeBrowser()

def getKeyDates(username) {
	
	if(!loggedIn) {
		
		loggedIn = true
		
		url = ('https://ad.' + domain)
		
		WebUI.openBrowser('')
		
		WebUI.navigateToUrl(url)
		
		WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')
		
		WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')
		
		WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))
		
		WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))
	}
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))
	
	WebUI.delay(1)
	
	lastLogin = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_Last Login'))
	
	return lastLogin
}
