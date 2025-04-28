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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
//import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


//#### Add wildcard maps
/*
 * Log into AD (function - exists)
 * Get match rules (function - exists)
 * Get organization profile (function - new)
 * Switch to new tab
 * Log in as organization
 * Show match table
 * For candidates in table:
 * 	Get table match percent
 * 	Get popup match percent
 * 	Switch to AD tab 
 * 	Get candidate profile (function - reuse new)
 * 	Calculate match percent logging matches
 * 	Report match values
 * 	Switch back to table tab
 * 
 */



// Entries in AD candidate profile page that need to be ignored
categoriesOrganization = [
	'Contact Info',
	'School Info',
	'Vision Trip Description',
	'Security',
	'Positions Needed',
	'Service Options',
	'Readiness',
	'Match Filters',
	'Admin Info',
	'Recruiting Countries',
	'Ministry Prefs',
	'IT Positions']

// Entries in AD organization profile page that need to be ignored
categoriesCandidate = [
	'Name & Preferences',
	'Contact Info',
	'Ministry Positions',
	'Enter other Ethnicity',
	'Enter Family Status',
	'Experience',
	'Education',
	'Additional Language ProficiencyGroup',
	'Situation',
	'Church',
	'Availability',
	'Preferences',
	'Options/Comment',
	]
	
//Education Organization Wildcards
orgWildcardsEO = [
	'Affiliated with a Church?' : 'No',
	'Available Start Options' : 'No Preference',
	'Time Commitments' : 'Open - Will negotiate',
	'Process Stage' : 'I am just beginning to look at missions',
	'Relocation Option(s)' : 'Not a Match criterion',
	'Experience Preferred' : 'No',
	'Formal Education Degree' : 'No',
	'Classroom Experience' : 'No',
	'Paid & Volunteer Positions' : 'No Preference',
	'Bible Training' : 'Not Applicable',
	'Cross-cultural Experience' : 'Not served in a culture other than my own']

//Education Candidate Wildcards
userWildcardsEO = [
	'I/We can be Available' : 'Not sure',
	'Time Commitment(s)': 'Open - Will negotiate',
	'Relocation Option(s)' : 'Not sure',
	'Paid & Volunteer Positions' : 'Open']


debug = true

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

site = 'Education'

radioType = 'Org'

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : radioType],
	FailureHandling.STOP_ON_FAILURE)

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

BufferedReader reader

profileTypes = ['Candidate', 'Organization']

for(profileType in profileTypes) {
	
	if(profileType == 'Candidate') {

		tempFile = filePath + 'education candidate profile-api.txt'
		
		categories = categoriesCandidate
		
		candidateSelections = [:]
	
	} else {
		
		tempFile = filePath + 'education partner profile-api.txt'
		
		categories = categoriesOrganization
		
		organizationSelections = [:]
		
		schoolMatchFields = []
		
		matchValues.each {
			schoolMatchFields.add(it.value[0])
		}

	}
	
	categories = []
	
	if(profileType == 'Organization') {
		tempFile = filePath + 'education partner profile-api.txt'
		categories = categoriesOrganization
	} else {
		tempFile = filePath + 'education candidate profile-api.txt'
		categories = categoriesCandidate
	}
	
	reader = new BufferedReader(new FileReader(tempFile))
	
	fieldNumbers = []
	values = []
	line = getNextLine(reader)
	while(line != 'end') { 
		line = getNextLine(reader)
		println(line)
		while(line != 'end') {
			if(line.substring(1,4).contains('\t')) { // It's potentially a field
				values = []
				tab = line.indexOf('\t')
				number = line.substring(0,tab)
				if(!fieldNumbers.contains(number)) { // It's a field (and not a dup of one already found. Some dupes in table)
					fieldNumbers.add(number)
					println(number)
					line = line.substring(tab + 1)
					println(line)
					tab = line.indexOf('\t')
					if(tab >= 0) {
						field = line.substring(0, tab).trim()
						println('Field is ' + field)
						value = line.substring(tab + 1).trim()
						println('Value is ' + value)
						if(value.length() >= 2) {
							values.add(value)
						}
						line = getNextLine(reader)
						while (!line.contains('\t')) { // Get all of the field selections
							line = line.trim()
							values.add(line)
							line = getNextLine(reader)
							if(line == 'end') {
								break
							}
						}
//						println('Values is ' + values)
						if(profileType == 'Organization' && field in schoolMatchFields) { //field in schoolMatchFields || 
							organizationSelections.put(field,values)
						} else {
							if(matchValues.get(field) != null) {
								candidateSelections.put(field,values)
							}
						}
					}
				} else {
					line = getNextLine(reader)
					if(line == 'end') {
						break
					}
				}
			} else {
				line = getNextLine(reader)
				if(line == 'end') {
					break
				}
			}
		}
		line = getNextLine(reader)
		if(line == 'end') {
			break
		}
	}
	
	reader.close()
}

println('\n\n Candidate Selections')
candidateSelections.each {
	println(it.key + ':' + it.value)
}

println('\n\n Organization Selections')
organizationSelections.each {
	println(it.key + ':' + it.value)
}

matchValues.each {
	candidateValues = candidateSelections.get(it.key)
	values = matchValues.get(it.key)
	orgSelectionKey = values[0]
	pointsSingle = values[1]
	pointsMarried = values[2]
	organizationValues = organizationSelections.get(orgSelectionKey)
	println('\n\nFor ' + it.key)
	println('org selections = ' + organizationValues)
	println('cand selections = ' + candidateValues)
	println('Matches are : ' + candidateValues.intersect(organizationValues))
	//#### add wildcard tests
	//	add point calcs
	// 	add test for missed must matches
}

def getNextLine(reader) {
	line = reader.readLine()
//	println(line + ':' + line.length())
	while(line != null && line.length() <= 2 || line in categories) {
//		println(line + ':' + line.length())
		line = reader.readLine()
//		println(line)
	}
//	println(line + ':' + line.length())
	if(line != null && !line.contains('Logout')) {
		return line
	} else {
		return 'end'
	}
}
