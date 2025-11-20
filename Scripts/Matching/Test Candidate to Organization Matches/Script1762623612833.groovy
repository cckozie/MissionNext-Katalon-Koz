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
import java.io.FileReader as FileReader
import java.io.IOException as IOException
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
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader as ExecutionProfilesLoader
import java.time.Instant as Instant
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions

maxMatches = 10 //overridden if running test suite

updateWildcards = false

debug = false

if (varSite != null) {
    site = varSite // Journey or Education
} else {
    site = 'Journey'
}

matchType = 'Org' // Org or Job

pages = GlobalVariable.matchPages //How many match table pages to test, actual number of pages to process or 'ALL'

if (site == 'Journey') {
    user = 'Journey Candidate 15'
}

if (site == 'Education') {
    user = 'Education Candidate 14'
}

new ExecutionProfilesLoader().loadProfile(user)

candidateUsername = GlobalVariable.username

candidatePassword = GlobalVariable.password

candidateEmail = GlobalVariable.email

firstName = ''

lastName = ''

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if (GlobalVariable.testSuiteRunning) {
    testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)

    myTestCase = ((((myTestCase.substring(0, myTestCase.length() - 3) + ' - ') + testCaseName) + '-') + site)
	
	maxMatches = 100
	
} else {
    myTestCase = ((myTestCase.substring(0, myTestCase.length() - 3) + '-') + site)
}

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

filePathDetails = '/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '.txt')

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
    candidateWildcards = evaluate(new File((filePath + site) + ' Candidate Wildcards.txt'))

    partnerWildcards = evaluate(new File((filePath + site) + ' Partner Wildcards.txt'))
} else {
    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site, ('varType') : matchType], 
        FailureHandling.STOP_ON_FAILURE)

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

if(site == 'Education') {
	myFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : candidateEmail, ('varSite') : site, ('varFile') : ''],
		FailureHandling.STOP_ON_FAILURE)
	
	println(myFieldValues.get('Your Country of Citizenship'))

}

WebUI.closeBrowser()

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : matchType], 
    FailureHandling.STOP_ON_FAILURE)

tab = '\t'

outText = 'Matching Rules'

outFile.append(('\n ' + outText) + '\n')

candidateMatchFields = []

organizationMatchFields = []

matchValues.each({ 
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[
        3]))

        //println(outText)
        outFile.append(outText + '\n')

        candidateMatchFields.add(it.key)

        organizationMatchFields.add(it.value[0])
    })

candidateMatchFields.each({ 
        println(it)
    })

organizationMatchFields.each({ 
        println(it)
    })

BufferedReader reader

orgFieldValues = [:]

if (site == 'Journey') {
    candidateSelections = [:]

    candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : candidateEmail
            , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

    candidateFieldValues.each({ 
            println(it)
        })

    for (def it : candidateFieldValues) {
        if (it.key in candidateMatchFields) {
            candidateSelections.put(it.key, it.value)

            println(it)
        }
    }
    
    apiTab = WebUI.getWindowIndex() //Education
    
} else {

    adTab = WebUI.getWindowIndex()

    WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : candidateUsername, ('varFile') : profileFile
            , ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

    returnValues = formatProfile(profileFile, categoriesCandidate, candidateMatchFields)

    candidateSelections = (returnValues[0])

    candidateSelections.put('Your Country of Citizenship', myFieldValues.get('Your Country of Citizenship'))

    candidateSelections.each({ 
            println(it)
        })

    WebUI.closeWindowIndex(1)

    WebUI.switchToWindowIndex(adTab)
}

if (site == 'Journey') {
    maritalStatus = (candidateFieldValues.get('Marital status')[0])

    spouseServing = (candidateFieldValues.get('Spouse Serving with You?')[0])

    firstName = (candidateFieldValues.get('First Name')[0])

    lastName = (candidateFieldValues.get('Last Name')[0])

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
} else {
    married = (returnValues[1])

    spouseServing = (returnValues[2])

    if (married) {
        maritalStatus = 'Married'
    } else {
        maritalStatus = 'Single'
    }
}

outText = ((((('\n\n Candidate Selections for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)

resultsText = ((((('\n\n' + site + ' Organization Matches for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)



if (married) {
    if (spouseServing) {
        outText += ', spouse is serving.'
    } else {
        outText += ', spouse is not serving.'
    }
}

outFile.append(outText + '\n')

resultsFile.append(resultsText + '\n')

errorFile.append(resultsText + '\n')

candidateSelections.each({ 
        outText = ((it.key + ':') + it.value)

        outFile.append(outText + '\n')
    })

WebUI.delay(2)

//WebUI.openBrowser(null)
WebUI.executeJavaScript('window.open();', [])

WebUI.switchToWindowIndex(1)

orgTab = WebUI.getWindowIndex()

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : candidateUsername, ('varPassword') : candidatePassword
        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE //***
    )

if (site == 'Journey') {
    WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_View Agency Matches'))
} else {
    WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Dashboard/a_View School Matches'))
}

educationTab = WebUI.getWindowIndex()

WebUI.waitForPageLoad(60)

WebDriver driver = DriverFactory.getWebDriver()

tableXpath = '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody'

WebElement Table = driver.findElement(By.xpath(tableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

println(Rows.size())

int row_count = Rows.size() - 1

if (maxMatches < row_count) {
    row_count = maxMatches
}

found = false

noErrors = true

if (row_count > 0) {
    for (row = 1; row < row_count; row++) {
        println('row is ' + row)
		
		rowClass = Rows.get(row).getAttribute("class")
		
		if(rowClass.contains('item')) {
				
	        List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	        line = Columns.get(0).getText()
	
	        println('line is ' + line)
	
	        organization = Columns.get(1).getText()
	
	        println('organization is ' + organization)
	
	        pct = Columns.get(2).getText()
	
	        println('Table pct is ' + pct)
	
	        tablePct = pct.toInteger()
	
	        println('Table percent is ' + tablePct)
	
	        myRow = (row + 1)
	
	        elementXpath = (('//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody/tr[' + myRow) + ']/td[2]/a')
	
	        element = driver.findElement(By.xpath(elementXpath))
	
	        println(element)
	
	        Actions actions = new Actions(driver)
	
	        actions.moveToElement(element)
	
	        actions.perform()
	
	        element.click()
	
	        WebUI.switchToWindowIndex(educationTab + 1)
	
	        WebUI.waitForPageLoad(30)
	
	        element = driver.findElement(By.xpath('//*[text()=\'Key Contact Email:\']'))
	
	        email = element.findElement(By.xpath('following-sibling::*')).getText().trim()
	
	        println(email)
	
	        WebUI.closeWindowIndex(educationTab + 1)
	
	        WebUI.switchToWindowIndex(0)
	
	        orgFieldValues = [:]
	
	        if (site == 'Journey') {
	            orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email'
	                    , ('varEmail') : email, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
	        } else {
	            WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varEmail') : email, ('varFile') : profileFile
	                    , ('varSearchType') : 'email'], FailureHandling.STOP_ON_FAILURE)
	
	            returnValues = formatProfile(profileFile, categoriesOrganization, candidateMatchFields)
	
	            organizationSelections = (returnValues[0])
	
	            organizationSelections.each({ 
	                    println(it)
	                })
	
	            WebUI.closeWindowIndex(2)
	        }
	        
	        organization = (orgFieldValues.get('Organization')[0])
	
	        outText = (' \n\n>>>>> Organization Field Values for ' + organization)
	
	        outFile.append(outText + '\n')
	
	        println(outText)
	
	        orgFieldValues.each({ 
	                outText = ((it.key + ':') + it.value)
	
	                outFile.append(outText + '\n')
	
	                println(outText)
	            })
	
	        organizationSelections = [:]
	
	        if (orgFieldValues != null) {
	            orgFieldValues.each({ 
	                    println(it)
	                })
	
	            for (def v : orgFieldValues) {
	                myKey = v.key
	
	                if (organizationMatchFields.contains(myKey)) {
	                    organizationSelections.put(v.key, v.value)
	                }
	            }
	            
	            outText = ('\n\n Organization Selection Matches for ' + organization)
	
	            outFile.append(outText + '\n')
	
	            outText = ('\n Results for organization ' + organization)
	
	            resultsFile.append(outText + '\n')
	
				lineText = outText
				
	            doMatching(candidateSelections, organizationSelections)
	        }
	        
	        WebUI.switchToWindowIndex(1)
	
	        WebUI.waitForPageLoad(10)
	
	        Table = driver.findElement(By.xpath(tableXpath))
	
	        Rows = Table.findElements(By.tagName('tr'))
		}
    }
}

if(noErrors) {
	errorFile.delete()
}

WebUI.closeBrowser()

def doMatching(def candidateSelections, def organizationSelections) {
	
	notMatched = []
	
	error = false
	
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
		
		if(it.key == 'Spouse Preferred Position(s)' && !married) { // Bypass spouse preferred positions if candidate is not married or spouse is not serving
			continue
		}

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

            if (((matchValue != 5) && !((values[pointValue]).contains('-'))) && ((values[pointValue]).length() > 0)) {
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
			
			notMatched.add(it.key)
        }
    }
    
    addedPct = points.round(0).toInteger()

    println('addedPct=' + addedPct)

    println('tablePct=' + tablePct)

    error = false

    tablePct = tablePct.toInteger()

    code = ''

    if ((addedPct - tablePct) > 1) {
        error = true
		
		noErrors = false
		
    }
    
    outText = (('\nCalculated match percentage adding is ' + addedPct) + '%.')

    outFile.append(outText + '\n')

    tblPct = tablePct.toString()

    tblPct = tblPct.replace('\n', '')

    outText = (('Table match percentage is ' + tblPct) + '%.')

    outFile.append(outText + '\n')

    if (error) {
        outText = ('##### ERRORS: ')

        outFile.append(outText + '\n')
    }
    
    outFile.append('\n\n')

    outText = (((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%.\n')

	resultsText = outText

    if (error) {
		errorFile.append(lineText + '\n')
			
        outText = ('##### ERRORS: ' + outText)

        errorFile.append(outText + '\n')
    }
    
    resultsFile.append(outText)
	
	if(notMatched.size() > 0) {
		outText = 'No match on ' + notMatched
	} else {
		outText = 'No non-matches displayed.'
	}
	
	resultsFile.append(outText + '\n')
	
	if(error) {
		errorFile.append(outText + '\n')
		errorFile.append(resultsText + '\n')
	}
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

def formatProfile(def file, def categories, def matchFields) {
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
            if ((line.length() > 3) && line.substring(1, 4).contains('\t')) {
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
                        
                        if (site == 'Education') {
                            orgFieldValues.put(field, values)
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
                        
                        if (field == 'Organization') {
                            organizationName = value
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

