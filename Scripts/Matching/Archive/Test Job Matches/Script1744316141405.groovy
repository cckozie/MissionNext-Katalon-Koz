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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.io.File as File

WebUI.callTestCase(findTestCase('Matching/_Functions/Archive/Get Match Data from Web'), [('varMatchType'):'Jobs'], FailureHandling.STOP_ON_FAILURE)

stopOnError = false

candidate = '0' // Set candidate number from csv file, or '0' for all candidate

debug = true

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

//####
//complete these (org's) for all match fields
//add another map for user's wildcards
/*
wildcards = [
	'Process Stage':'I am just beginning to look at missions',
	'Attended Perspectives?':'I have not taken the Perspectives Course',
	'Relocation Option(s)':'Not a Match criterion',
	'Affiliated with a Church?':'No',
	'Paid & Volunteer Positions':'No Preference',
	'Time Commitment(s)':'Open - Will negotiate']
*/
orgWildcards = [('Paid & Volunteer Positions') : 'No Preference', ('Affiliated with a Church?') : 'No', ('Attended Perspectives?') : 'I have not taken the Perspectives Course'
    , ('Relocation Option(s)') : 'Not a Match criterion', ('Time/Hours Needed') : 'Open', ('Time Commitments') : 'Open - Will negotiate'
    , ('Preferred Region(s)') : 'No Preference', ('Process Stage') : 'I am just beginning to look at missions']

userWildcards = [('Paid & Volunteer Positions') : 'Open', ('Relocation Option(s)') : 'Not sure', ('Time/Hours Available') : 'Open'
    , ('Time Commitment(s)') : 'Open - Will negotiate', ('Preferred Region(s)') : 'No Preference']

site = 'Journey'

type = 'Job'

orgEmail = 'headquarters@missionnext.org'

candidateMatches = WebUI.callTestCase(findTestCase('Matching/_Functions/Archive/Get Match Data from CSV File'), [('varMatchType'):'Organization'], FailureHandling.STOP_ON_FAILURE)

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varType') : type], 
    FailureHandling.STOP_ON_FAILURE)

outText = '\n matchValues\n'

println(outText)

if (debug) {
    outFile.append(outText + '\n')
}

matchValues.each({ 
        outText = ((it.key + ':') + it.value)

        println(outText)

        if (debug) {
            outFile.append(outText + '\n')
        }
    })
System.exit(0)
WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varSearchKey') : null], FailureHandling.STOP_ON_FAILURE)

orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email', ('varEmail') : orgEmail], 
    FailureHandling.STOP_ON_FAILURE)

outText = '\n orgFieldValues\n'

println(outText)

if (debug) {
    outFile.append(outText + '\n')
}

orgFieldValues.each({ 
        outText = ((it.key + ':') + it.value)

        println(outText)

        if (debug) {
            outFile.append(outText + '\n')
        }
    })

//candidateMatches.each {
for (def itc : candidateMatches) {
    println((itc.key + ':') + itc.value)

    if (((itc.value[0]) != 'First Name') && ((itc.key == candidate) || (candidate == '0'))) {
        firstName = (itc.value[0])

        lastName = (itc.value[1])

        outText = ((('\n\nProcessing candidate ' + firstName) + ' ') + lastName)

        println(outText)

        if (debug) {
            outFile.append(outText + '\n')
        }
        
        userFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'lastName'
                , ('varFirstName') : firstName, ('varLastName') : lastName], FailureHandling.STOP_ON_FAILURE)

        if (userFieldValues == null) {
            userFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'firstName'
                    , ('varFirstName') : firstName, ('varLastName') : lastName], FailureHandling.STOP_ON_FAILURE)
        }
        
        WebUI.delay(5)

        if ((userFieldValues != null) && (userFieldValues.size() > 40)) {
            outText = ('userFieldValues size is ' + userFieldValues.size())

            println(outText)

            if (debug) {
                outFile.append(outText + '\n')
            }
            
            if (candidate != '0') {
                outText = '\n matchValues\n'

                println(outText)

                if (debug) {
                    outFile.append(outText + '\n')
                }
                
                matchValues.each({ 
                        outText = ((it.key + ':') + it.value)

                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                    })

                outText = '\n orgFieldValues\n'

                println(outText)

                if (debug) {
                    outFile.append(outText + '\n')
                }
                
                orgFieldValues.each({ 
                        outText = ((it.key + ':') + it.value)

                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                    })

                outText = '\n userFieldValues\n'

                println(outText)

                if (debug) {
                    outFile.append(outText + '\n')
                }
                
                userFieldValues.each({ 
                        outText = ((it.key + ':') + it.value)

                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                    })
            }
            
            maritalStatus = userFieldValues.get('Marital status')

            if (maritalStatus.contains('Married')) {
                married = true
            } else {
                married = false
            }
            
            outText = ('Candidate is ' + maritalStatus)

            println(outText)

            if (debug) {
                outFile.append(outText + '\n')
            }
            
            /*
			outText = userFieldValues.get('Spouse Serving with You?')
			println(outText)
			if(debug) {
				outFile.append(outText + '\n')
			}
			*/
            if (userFieldValues.get('Spouse Serving with You?').contains('Yes')) {
                spouseServing = true
            } else {
                spouseServing = false
            }
            
            outText = ('Spouse serving is ' + spouseServing)

            println(outText)

            if (debug) {
                outFile.append(outText + '\n')
            }
            
            score = 0.0

            excluded = false

            if (married && spouseServing) {
                pointsColumn = 3
            } else {
                pointsColumn = 2
            }
            
            //		matchValues.each {
            for (def it : matchValues) {
                if (!(excluded)) {
                    matched = false

                    outText = ((('\nTesting candidate ' + it.key) + ', organization ') + (it.value[0]))

                    println(outText)

                    if (debug) {
                        outFile.append(outText + '\n')
                    }
                    
                    userLabel = it.key

                    orgLabel = (it.value[0])

                    weight = (it.value[1])

                    pointsStr = (it.value[pointsColumn])

                    if (pointsStr == '-') {
                        points = 0.0
                    } else {
                        points = pointsStr.toFloat()
                    }
                    
                    orgValues = orgFieldValues.get(orgLabel)

                    userValues = userFieldValues.get(userLabel)

                    outText = ('Organization = ' + orgValues)

                    println(outText)

                    if (debug) {
                        outFile.append(outText + '\n')
                    }
                    
                    if (it.key.contains('Spouse')) {
                        user = 'Spouse'
                    } else {
                        user = 'Candidate'
                    }
                    
                    outText = ((user + ' = ') + userValues)

                    println(outText)

                    if (debug) {
                        outFile.append(outText + '\n')
                    }
                    
                    orgW = orgWildcards.get(orgLabel)

                    if (orgW != null) {
                        if (orgValues.contains(orgW)) {
                            matched = true

                            outText = ('Found organization wildcard match on ' + orgW)
                        }
                    }
                    
                    if (!(matched)) {
                        userW = userWildcards.get(userLabel)

                        if (userW != null) {
                            if (userValues.contains(userW)) {
                                matched = true

                                outText = ('Found user wildcard match on ' + userW)
                            }
                        }
                    }
                    
                    if (!(matched)) {
                        if ((userValues != null) && (orgValues != null)) {
                            match = userValues.intersect(orgValues)

                            if (match.size() > 0) {
                                matched = true

                                outText = ('Found match on ' + match)
                            }
                        }
                    }
                    
                    if (matched) {
                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                        
                        score = (score + points).round(1)

                        outText = ((('Adding ' + points) + ', Score is now ') + score)

                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                    }
                    
                    if (!(matched) && (weight == 5)) {
                        outText = ((((('Candidate ' + firstName) + ' ') + lastName) + ' is excluded based on ') + orgLabel)

                        println(outText)

                        if (debug) {
                            outFile.append(outText + '\n')
                        }
                        
                        score = 0

                        excluded = true
                    }
                }
            }
            
            delta = Math.abs(score - (itc.value[2]).toInteger()).round(1)

            outFile.append(('score is ' + score) + '\n')

            outFile.append(('table is ' + (itc.value[2])) + '\n')

            outFile.append(('delta is ' + delta) + '\n')

            calcScore = score.round(0).toInteger().toString()

            tableScore = (itc.value[2]).toString()

            outText = (((((((('For ' + firstName) + ' ') + lastName) + ', the Table score is ') + tableScore) + ' and calculated score is ') + 
            calcScore) + '.')

            if (delta > 1) {
                outText = ('#ERROR: ' + outText)

                errorFile.append(outText + '\n')

                if (stopOnError) {
                    System.exit(9)
                }
            }
        } else {
            outText = (((('The profile for ' + firstName) + ' ') + lastName) + ' could not be found. ')
        }
        
        println(outText)

        outFile.append(outText + '\n')

        if (itc.key == candidate) {
            break
        }
    }
}

