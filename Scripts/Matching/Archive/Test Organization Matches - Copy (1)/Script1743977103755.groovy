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
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File as File

stopOnError = false

candidate = '4' // Set candidate number from csv file, or 0 for all candidate

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf('/')+1)
myTestCase = myTestCase.substring(0,myTestCase.length()-3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write('Running ' + myTestCase + '\n')

GlobalVariable.outFile = outFile

wildcards = [
	'Process Stage':'I am just beginning to look at missions',
	'Attended Perspectives?':'I have not taken the Perspectives Course',
	'Relocation Options':'Not a Match criterion',
	'Affiliated with a Church?':'No',
	'Paid & Volunteer Positions':'No Preference']

site = 'Journey'

type = 'Org'

orgEmail = 'headquarters@missionnext.org'

candidateMatches = WebUI.callTestCase(findTestCase('Matching/_Functions/Archive/Get Match Data from CSV File'), [:], FailureHandling.STOP_ON_FAILURE)

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varType') : type],
	FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varSearchKey') : null], FailureHandling.STOP_ON_FAILURE)

orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email', 
	('varEmail') : orgEmail], FailureHandling.STOP_ON_FAILURE)

orgFieldValues.each {
	println(it)
}


//candidateMatches.each {
for(itc in candidateMatches) {
	
	println(itc.key + ':' + itc.value)
	
	if(itc.value[0] != 'First Name' && (itc.key == candidate || candidate == 0)) {
	
		firstName = itc.value[0]
		
		lastName = itc.value[1]
	
		userFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'name',
			('varFirstName') : firstName, ('varLastName') : lastName], FailureHandling.STOP_ON_FAILURE)
		
		if(userFieldValues != null) {
/*		
			println('\n matchValues\n')
			matchValues.each {
				println(it.key + ':' + it.value)
			}
			
			println('\n userFieldValues\n')
			userFieldValues.each {
				println(it.key + ':' + it.value)
			}
			
			println('\n orgFieldValues\n')
			orgFieldValues.each {
				println(it.key + ':' + it.value)
			}
	*/		
			score = 0
			excluded = false
	//		matchValues.each {
			for (it in matchValues) {
				if(!excluded) {
					matched = false
					println('\nTesting user ' + it.key + ', org ' + it.value[0])
					userLabel = it.key
					orgLabel = it.value[0]
					weight = it.value[1]
					pointsStr = it.value[2]
					if(pointsStr == '-') {
						points = 0.0
					} else {
						points = pointsStr.toFloat()
					}
					orgValues = orgFieldValues.get(orgLabel)
					userValues = userFieldValues.get(userLabel)
					println('org = ' + orgValues + ':' + ' user = ' + userValues)
					for(userValue in userValues) {
						if(!matched) {
							if(userValue in orgValues || 'Open' in orgValues || 'Open' in userValues || 'Open - Will negotiate' in userValues || 'Not sure' in userValues  || 'No Preference' in orgValues || 'No Preference' in userValues) {
								println('found match on ' + userValue)
								println('Adding ' + points)
								score = score + points
								matched = true
							} else {
								wValue = wildcards.get(orgLabel)
								println('orgLabel is ' + orgLabel + ', wValue is ' + wValue + ', orgValues is ' + orgValues)
								if(wValue in orgValues) {
									println('found wildcard match on ' + orgValues)
									println('Adding ' + points)
									score = score + points
									matched = true
								}
							}
						}
					}
					if(!matched && weight == 5) {
						outText = ('Candidate ' + firstName + ' ' + lastName + ' is excluded based on ' + orgLabel)
						println(outText)
						outFile.append(outText + '\n')
						score = 0
						excluded = true
					}
				}
			}
			calcScore = score.toInteger().toString()
			tableScore = itc.value[2].toString()
	//		println('\n User score is ' + score)
			outText = ('For ' + firstName + ' ' + lastName + ', the Table score is ' + tableScore  + ' and calculated score is ' + calcScore + '.')
			if(tableScore != calcScore) {
				outText = ('#ERROR: ' + outText)
			}
			println(outText)
			outFile.append(outText + '\n')
			if(tableScore != calcScore && stopOnError) {
				System.exit(9)
			}
		} else {
			outText = 'The profile for ' + firstName + ' ' + lastName + ' could not be found. '
		}
	}
}
