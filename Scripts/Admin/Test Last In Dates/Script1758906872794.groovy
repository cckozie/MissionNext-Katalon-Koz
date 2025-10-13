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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
@Grab('com.xlson.groovycsv:groovycsv:1.3')
import static com.xlson.groovycsv.CsvParser.parseCsv

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

dataFile = new File('/Users/cckozie/Documents/MissionNext/Test Data/' + '2019 Journey Candidate Last In Dates.csv')

inFile = new File('/Users/cckozie/Documents/MissionNext/Test data/last in 2019 journey candidates.csv')

dataFile.write('Username,U ID,Email,Last Login User List,Last In Subscription List, Years Match\n')

users = [:]

mismatches= [:]

for(line in parseCsv(new FileReader(inFile), separator: ',')) {
//    println (line.'Username' + ',' +  line.'Last in')
	key = line.'Username'
	values = [:]
	values.put('u id',line.'u id')
	values.put('Last in', line.'Last in')
	values.put('First Name', line.'First Name')
	values.put('Last Name', line.'Last Name')
	users.put(line.'Username', values)
}
//users.each {
//	println(it)
//}

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

for(it in users) {
	
//	username = 'abhallman'
	
	username = it.key
	
	values = it.value
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))
	
	WebUI.delay(1)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath('//table[2]'))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size()
	
	println(row_count + ' rows found')
	
	found = false
		
	if(row_count > 0) {
		
		row = 0
	
		match = false
		
		while(!match && row < row_count) {
			
			row++

		    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
			uid = Columns.get(1).getText()
		
		    user = Columns.get(2).getText()
			
			println(user)
			
			email = Columns.get(3).getText()
			
			lastLogin = Columns.get(6).getText()
		
		    if (uid == values.get('u id')) {
				
				println('testing user ' + uid + ', ' + username + 'with email ' + email + ' last login ' + lastLogin)
				
				year1 = lastLogin.substring(0,4).toInteger()
								
				year2 = values.get('Last in').substring(1,5).toInteger()
				
				match = true
				
				link = Columns.get(2).findElement(By.tagName('a'))
				
				if(year1 == year2) {
					yearMatch = true 
				} else {
					yearMatch = false
				}
				
				dataFile.append(username + ',' + uid + ',' + email + ', ' + year1 + ', ' +  year2 + ', ' + yearMatch + '\n')
				
		    }
		}
		
		
		if(year1 != year2) {
			
			values = [:]
			
			values.put('subscription last in', year2)
			
			values.put('user viewer last login', year1)
			
			mismatches.put(username, values)
		}
	}
}

mismatches.each {
	println(it)
}
//WebUI.closeBrowser()

