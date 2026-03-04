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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.File as File
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

runCompare = true

testAccts = true 	//Include test accounts in results

today = new Date().format('yyyy-MM-dd')

todayDate = Date.parse('yyyy-MM-dd', today)

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

varFormat = 'YY-MM-dd.HH-mm'

now = new Date().format(varFormat)

txt = '_All'

outFile = new File((((('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + txt) + '_') + now) + '.csv')

outFile.write('User ID,Username,Email,Organization,Start,Expiration Date,Status,Days Remaining,In Grace Period\n')

domain = GlobalVariable.domain

url = ('https://ad.' + domain)

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

sites = ['Journey','Education']

for (def site : sites) {
	
	firstTest = true
	
    subscriptions = WebUI.callTestCase(findTestCase('_Functions/Get Partner Subscription Data'), [('varSite') : site, ('varTestAccts') : testAccts], FailureHandling.STOP_ON_FAILURE)

    subscriptions.each({ 
            println(it)
        })

    WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))

    WebUI.delay(2)

    WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

    WebUI.waitForPageLoad(30)

    outFile.append(('\n>>>>> ' + site) + ' partners.\n')

    WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad User Viewer Utility/select_Website'), site, false)

    WebUI.selectOptionByValue(findTestObject('Object Repository/Admin/Ad User Viewer Utility/select_Role'), 'Organization', 
        false)

    WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/button_Go'))

    //    WebUI.delay(1)
    //    WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/button_Search'))
    WebUI.waitForPageLoad(60)

    WebDriver driver = DriverFactory.getWebDriver()

    tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table/tbody'

    WebElement Table = driver.findElement(By.xpath(tableXpath))

    List<WebElement> Rows = Table.findElements(By.tagName('tr'))

    int row_count = Rows.size()

    partners = [:]

    for (row = 1; row < row_count; row++) {
        List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

        userID = Columns.get(1).getText()

        //        username = Columns.get(10).getText()
        email = Columns.get(11).getText()

        status = Columns.get(15).getText()

        //        values = [username, email, status]
        //	println(values)
        values = [email, status]

        partners.put(userID, values)
    }
    
    partners.each({ 
            println(it)
        })

    myList = []

    myTestList = []

    for (def subscription : subscriptions) {
        subKey = subscription.key

        subValues = subscription.value //values.addAll(organization, start, expires, username, acctType)
		
		if(subValues[4] == 'Test' && firstTest) {
			outFile.append('----- Test Accounts:')
			firstTest = false
		}

        organization = (subValues[0]).replace(',', ' ')

        start = (subValues[1])

        expires = (subValues[2]).substring(0, 10)

        expireDate = Date.parse('yyyy-MM-dd', expires)

        dateDiff = (expireDate - todayDate)

        if (dateDiff < -10) {
            inGrace = 'Expired'
        } else if (dateDiff >= 0) {
            inGrace = ''
        } else {
            inGrace = 'Grace'
        }
        
        username = (subValues[3])

        partnerValues = partners.get(subKey, null)

        println('###########' + partnerValues)

        if (partnerValues != null) {
            //			username = partnerValues[0]
            //			println('--- username is ' + username)
            email = (partnerValues[0])

            println('--- email is' + email)
			
//*********** Need to put test accounts into their own map

            status = (partnerValues[1])

			if(subValues[4] != 'test') {
				myList.add([subKey, username, email, organization, start, expires, status, dateDiff, inGrace])
			} else { 
				myTestList.add([subKey, username, email, organization, start, expires, status, dateDiff, inGrace])
			}
			
			
        } else {
            outFile.append(((('***ERROR: Failed to obtain status for user ' + subKey) + '-') + username) + '***\n')
        }
    }
    
    myList.sort({ def a, def b ->
            (a[7]) <=> (b[7])
        })

    myList.each({ 
            println(it)

            for (def field : it) {
                outFile.append(field + ',')
            }
            
            outFile.append('\n')
        })

	if(testAccts) {
		outFile.append('\n')
		outFile.append(" ----- " + site + " partner test accounts")
		outFile.append('\n')
		
		myTestList.sort({ def a, def b ->
			(a[7]) <=> (b[7])
		})
	
		myTestList.each({
			println(it)
	
			for (def field : it) {
				outFile.append(field + ',')
			}
			
			outFile.append('\n')
		})
	}

    WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))
}

WebUI.closeBrowser()

if (runCompare) {
	WebUI.callTestCase(findTestCase('Expiration Date Tests/Compare Partner Expiration Dates in CSV Files'), [:], FailureHandling.STOP_ON_FAILURE)
}


