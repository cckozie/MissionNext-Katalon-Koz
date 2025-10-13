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
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.apache.commons.io.FileUtils as FileUtils
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader
import java.time.Instant
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
//import org.openqa.selenium.Actions
import org.openqa.selenium.interactions.Actions

// NEED TO ADD TEST FOR CHECKING 'ALL' FOR RECRUITING COUNTRIES

maxMatches = 100

updateWildcards = false	

debug = false

if(varSite != null) {
	site = varSite
} else {
	site = 'Education' // Journey or Education
}

matchType = 'Org'	// Org or Job

pages = GlobalVariable.matchPages //How many match table pages to test, actual number of pages to process or 'ALL'

if(site == 'Journey') {
	user = 'Journey Partner 17'
}

if(site == 'Education') {
	user = 'Education Partner 16'
}

new ExecutionProfilesLoader().loadProfile(user)

orgUsername = GlobalVariable.username

orgPassword = GlobalVariable.password

orgEmail = GlobalVariable.email

firstName = ''

lastName = ''

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName + '-' + site
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3) + '-' + site
}

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

filePathDetails = '/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/'

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase + '.txt')

outFile.write(('Running ' + myTestCase) + '\n\n')

profileFile = (filePath + 'userprofile.txt')

// Entries in AD organization profile page that need to be ignored
categoriesOrganization = ['Contact Info', 'School Info', 'Vision Trip Description', 'Security', 'Positions Needed', 'Service Options'
    , 'Readiness', 'Match Filters', 'Admin Info', 'Recruiting Countries', 'Ministry Prefs', 'IT Positions']

// Entries in AD candidate profile page that need to be ignored
categoriesCandidate = ['Name & Preferences', 'Contact Info', 'Ministry Positions', 'Enter other Ethnicity', 'Enter Family Status'
    , 'Experience', 'Education', 'Additional Language ProficiencyGroup', 'Situation', 'Church', 'Availability', 'Preferences'
    , 'Options/Comment']

if (!(updateWildcards)) {
	
	candidateWildcards = evaluate(new File(filePath + site + ' Candidate Wildcards.txt'))
	
	partnerWildcards = evaluate(new File(filePath + site + ' Partner Wildcards.txt'))
	
} else {
//    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)
	wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site,
		('varType') : matchType], FailureHandling.STOP_ON_FAILURE)

    candidateWildcards = (wildcards[0])

    partnerWildcards = (wildcards[1])
}

//Write the wildcard maps to the output file
outFile.append('Candidate Wildcards\n')

outFile.append(candidateWildcards.toString() + '\n')

outFile.append('Partner Wildcards\n')

outFile.append(partnerWildcards.toString() + '\n\n')

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

lastEmailAddress = ''

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : matchType], 
    FailureHandling.STOP_ON_FAILURE)

tab = '\t'

outText = 'Matching Rules'

outFile.append(('\n ' + outText) + '\n')

candidateMatchFields = []

organizationMatchFields = []

matchValues.each({ 
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[3]))

        //println(outText)
        outFile.append(outText + '\n')

        candidateMatchFields.add(it.key)

        organizationMatchFields.add(it.value[0])
    })

BufferedReader reader

if (site == 'Journey') {	
    organizationSelections = [:]

	orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : orgEmail, ('varSite') : site], 
	    FailureHandling.STOP_ON_FAILURE)
	orgFieldValues.each {
		println(it)
	}

	for(it in orgFieldValues){ 
        if (it.key in organizationMatchFields) {
            organizationSelections.put(it.key, it.value)
        }
    }
	
	apiTab = WebUI.getWindowIndex()
	
} else { //Education
	
	adTab = WebUI.getWindowIndex()
	
    WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : orgUsername, ('varFile') : profileFile
            , ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

    returnValues = formatProfile(profileFile, categoriesOrganization, organizationMatchFields)

    organizationSelections = (returnValues[0])
	
	organizationSelections.each {
		println(it)
	}
	
	WebUI.closeWindowIndex(1)
	
	WebUI.switchToWindowIndex(adTab)
}

outText = '\n\n Organization Selections'

outFile.append(outText + '\n')

organizationSelections.each({ 
        outText = ((it.key + ':') + it.value)

        outFile.append(outText + '\n')
    })

WebUI.delay(2)

WebUI.openBrowser(null)

orgTab = WebUI.getWindowIndex()

orgWindowTitle = WebUI.getWindowTitle()

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : orgUsername, ('varPassword') : orgPassword
        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE //***
    )

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))

tableWindowTitle = WebUI.getWindowTitle()

tableTab = (orgTab + 1)

matchTab = tableTab + 1

userTab = tableTab + 1

WebUI.switchToWindowIndex(tableTab)

WebUI.waitForPageLoad(60)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size() - 5

if(maxMatches < row_count) {
	row_count = maxMatches
}

found = false
	
if(row_count > 0) {

	for (row = 3; row < row_count + 3; row++) {
		
		println('row is ' + row)
		
		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
		line = Columns.get(0).getText()
		
		println('line is ' + line)
		
		firstName = Columns.get(1).getText()
		
		println('firstName is ' + firstName)
		
		lastName = Columns.get(2).getText()
		
		println('lastName is ' + lastName)
		
		pct = Columns.get(8).getText()
		
		println('Table pct is ' + pct)
		
		tablePct = pct.toInteger()
		
		println('Table percent is ' + tablePct)
		
		spyGlass = Columns.get(9)
		
		myRow = row + 1
		
		element = '/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[3]/span[@class="body"]/form[1]/p[1]/table[1]/tbody[1]/tr[' + myRow + ']/td[10]/a[1]/img[1]'
		
		println(element)
		
		driver.findElement(By.xpath(element)).click()
		
		WebUI.switchToWindowIndex(matchTab)
		
		poputWindowTitle = WebUI.getWindowTitle()
		
		popupPercent = WebUI.getText(findTestObject('Object Repository/Journey Partner Profile/Matching/text_Match Percent'))
		
		popupPercent = popupPercent.replace('%', '')
		
		popupPct = popupPercent.replace(' ', '').toInteger()
		
		println('PopupPct percent is ' + popupPct)
		
		WebUI.closeWindowTitle(poputWindowTitle)
		
		WebUI.switchToWindowIndex(tableTab)
		
		WebUI.delay(1)

		Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))

		Rows = Table.findElements(By.tagName('tr'))
		
		Columns = Rows.get(row).findElements(By.tagName('td'))
		
		myRow = row + 1
		
		element = '//tr[' + myRow + ']/td[3]/a'
		
		println(element)
		
		driver.findElement(By.xpath(element)).click()
			
		WebUI.switchToWindowIndex(userTab)
		
		WebUI
		
		WebUI.waitForPageLoad(30)

		candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Journey Profile from Web'),
			[('varMatchFields') : candidateMatchFields], FailureHandling.STOP_ON_FAILURE)
		
		println(candidateMatchFields)

		println(candidateFieldValues)
		
		maritalStatus = (candidateFieldValues.get('Marital status')[0])

		spouseServing = (candidateFieldValues.get('Spouse Serving with You?')[0])

		outText = (((((' \n\nCandidate profile for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)

		outFile.append(outText + '\n')

		println(outText)

		candidateFieldValues.each({
				outText = ((it.key + ':') + it.value)

				outFile.append(outText + '\n')

				println(outText)
			})

		candidateSelections = [:]
		
		if (candidateFieldValues != null) {
			candidateFieldValues.each({
					println(it)
				})

			for (def v : candidateFieldValues) {
				myKey = v.key

				if (candidateMatchFields.contains(myKey)) {
					candidateSelections.put(v.key, v.value)
				}
			}
			
			if ('Married' in candidateFieldValues.get('Marital status')) {
				married = true
			} else {
				married = false
			}
			
			if ('Yes' in candidateFieldValues.get('Spouse Serving with You?')) {
				spouseServing = true
			} else {
				spouseServing = false
			}
			
			outText = ((('\n\n Candidate Selection Matches for ' + firstName) + ' ') + lastName)

			outFile.append(outText + '\n')

			outText = ((('\n Results for candidate ' + firstName) + ' ') + lastName)

			resultsFile.append(outText + '\n')
			
			candidateText = outText

			if (!(married)) {
				outText = 'Candidate is not married.'
			} else {
				outText = 'Candidate is married and spouse is '

				if (!(spouseServing)) {
					outText += 'not '
				}
				
				outText += 'serving.'
			}
			
			outFile.append(outText + '\n')
			
			maritalStatusText = outText

			doMatching(candidateSelections, organizationSelections) 
				
		}
		
		WebUI.closeWindowIndex(userTab)
		
		WebUI.switchToWindowIndex(tableTab)
		
		WebUI.waitForPageLoad(10)
		
		Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))

		Rows = Table.findElements(By.tagName('tr'))
		
		Columns = Rows.get(row).findElements(By.tagName('td'))
	
		line = Columns.get(0).getText()
		
		firstName = Columns.get(1).getText()
		
		lastName = Columns.get(2).getText()
		
	}
}

WebUI.closeBrowser()

WebUI.delay(5)

def doMatching(def candidateSelections, def organizationSelections) {
    if (married && !(spouseServing)) {
        married = false
    }
    
    if (married) {
        pointValue = 3
    } else {
        pointValue = 2
    }
    
    excluded = false

    points = 0.0

    lostPoints = 0.0

    for (def it : matchValues) {
        match = false

        if (!(excluded)) {
			match = false
            candidateValues = candidateSelections.get(it.key)

            values = matchValues.get(it.key)

            orgSelectionKey = (values[0])

            matchValue = (values[1])

            println(matchValue)

            matchValue = (values[1]).toInteger()

            pointsSingle = (values[2])

            pointsMarried = (values[3])

            organizationValues = organizationSelections.get(orgSelectionKey)

            outText = ('\nFor ' + it.key)

            outFile.append(outText + '\n')

            outText = ('Organization selections = ' + organizationValues)

            outFile.append(outText + '\n')

            outText = ('Candidate selections = ' + candidateValues)

            outFile.append(outText + '\n')

            println(matchValue)

            if (matchValue != 5 && !values[pointValue].contains('-') && values[pointValue].length() > 0) {
                newPoints = (values[pointValue]).toFloat().round(1)
            } else {
                newPoints = 0
            }
            
            cWC = candidateWildcards.get(it.key)

            println(cWC)

            println(candidateValues)

            if ((cWC != null) && (candidateValues != null)) {
                if (cWC.intersect(candidateValues).size() > 0) {
                    outText = (((('Candidate wildcard match on ' + cWC.intersect(candidateValues)) + '. Adding ') + newPoints) + 
                    ' points.')

                    outFile.append(outText + '\n')

                    points = (points + newPoints)

                    match = true
                }
            
            }
			if (!(match)) {
                oWC = partnerWildcards.get(orgSelectionKey)

                println('partner wildcards = ' + oWC)

                println('partner selections =' + organizationValues)

                if ((oWC != null) && (organizationValues != null)) {
					
                    if (oWC.intersect(organizationValues).size() > 0) {
						
                        outText = (((('Organization wildcard match on ' + oWC.intersect(organizationValues)) + '. Adding ') + 
                        newPoints) + ' points.')

                        outFile.append(outText + '\n')

                        points = (points + newPoints)

                        match = true
                    }
                }
				if ((!(match) && (candidateValues != null)) && (organizationValues != null)) {
                    matches = candidateValues.intersect(organizationValues)
                    if (matches.size() > 0) {
                        outText = (((('Found match on ' + candidateValues.intersect(organizationValues)) + '. Adding ') + 
                        newPoints) + ' points.')

                        outFile.append(outText + '\n')

                        points = (points + newPoints)

                        match = true
                    }
                }
            }
            
        }
        
        if (match) {
            outText = (('Add match is now ' + points.round(1)) + '%.')

            outFile.append(outText + '\n')
        } else {
            if (matchValue == 5) {
                excluded = true

                outText = 'This is a must match field. Candidate is excluded.'

                outFile.append(outText + '\n')

                resultsFile.append(outText)

                break
            }
            
            outText = 'No match found.'

            outFile.append(outText + '\n')
        }
    }
    
    addedPct = points.round(0).toInteger()

    println('addedPct=' + addedPct)

    println('popupPct=' + popupPct)

    println('tablePct=' + tablePct)

    error = false

	popupPct = popupPct.toString().replace('\n', '')
	
	popupPct = popupPct.replace('%', '')

	popupPct = popupPct.toInteger()

	tablePct = tablePct.toInteger()

	code = ''
	
    if (((addedPct - tablePct) > 1) || ((tablePct - popupPct) > 1)) {
        error = true
		
		if(Math.abs(addedPct - tablePct) > 1) {	
			code = '12 '
		}
		if(Math.abs(addedPct - popupPct) > 1) {
			code = code + '13 '
		}
		if(Math.abs(tablePct - popupPct) > 1) {
			code = code + '23'
		}
    }
    
    outText = (('\nCalculated match percentage adding is ' + addedPct) + '%.')

    outFile.append(outText + '\n')

    tblPct = tablePct.toString()

    tblPct = tblPct.replace('\n', '')

    outText = (('Table match percentage is ' + tblPct) + '%.')

    outFile.append(outText + '\n')

	outText = (('Popup match percentage is ' + popupPct) + '%.')
	
    outFile.append(outText + '\n')

    if (error) {
		outText = '##### ERRORS ' + code + ' FOUND #####'
		
        outFile.append(outText + '\n')
		
    }
    
    outFile.append('\n\n')

	outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popupPct) +
		'%.\n\n')

    if (error) {
        outText = ('##### ERRORS ' + code + ': ' + outText)
    }
    
    resultsFile.append(outText)

	errorFile.append(outText)
	
}

def getNextLine(def reader, def categories) {
    line = reader.readLine()

    while (((line != null) && (line.length() <= 2)) || (line in categories)) {
        line = reader.readLine()
    }
    
    if (((line != null) && !(line.contains('Logout'))) && !(line.contains('Note: A candidate user'))) {
        return line
    } else {
        return 'end'
    }
}

def formatProfile(def file, def categories, def matchFields) { //education
    reader = new BufferedReader(new FileReader(file))

    selections = [:]

    fieldNumbers = []

    values = []

    married = false

    spouseServing = false

    line = getNextLine(reader, categories)

    while (line != 'end') {
        line = getNextLine(reader, categories)

        while (line != 'end') {
            if (line.length() > 3 && line.substring(1, 4).contains('\t')) {
                values = []

                tab = line.indexOf('\t')

                number = line.substring(0, tab)

                if (!(fieldNumbers.contains(number))) {
                    fieldNumbers.add(number)

                    line = line.substring(tab + 1)

                    tab = line.indexOf('\t')

                    if (tab >= 0) {
                        field = line.substring(0, tab).trim()

                        value = line.substring(tab + 1).trim()

                        if (value.length() >= 2) {
                            values.add(value)
                        }
                        
                        line = getNextLine(reader, categories)

                        while (!(line.contains('\t') && !(line.contains('end')))) {
                            line = line.trim()

                            values.add(line)

                            line = getNextLine(reader, categories)

                            if (line == 'end') {
                                break
                            }
                        }
                        
                        if (field in matchFields) {
                            selections.put(field, values)
                        }
                        
                        if (field == 'Marital status') {
                            if ('Married' in values) {
                                married = true
                            }
                        }
                        
                        if (field == 'First Name') {
                            firstName = value
                        }
                        
                        if (field == 'Last Name') {
                            lastName = value
                        }
                        
                        if (field == 'Spouse Serving with You?') {
                            if ('Yes' in values) {
                                spouseServing = true
                            }
                        }
                    }
                } else {
                    line = getNextLine(reader, categories)

                    if (line == 'end') {
                        break
                    }
                }
            } else {
                line = getNextLine(reader, categories)

                if (line == 'end') {
                    break
                }
            }
        }
        
        line = getNextLine(reader, categories)

        if (line == 'end') {
            break
        }
    }
    
    reader.close()
	
	println(selections)
	
    return [selections, married, spouseServing]
}

