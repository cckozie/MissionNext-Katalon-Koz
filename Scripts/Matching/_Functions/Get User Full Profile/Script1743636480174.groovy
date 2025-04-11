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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.interactions.Actions as Actions


if(varType == 'email') {
	fName = ''
	lName = ''
	varFirstName = ''
	varLastName = ''
	user = varEmail
	searchKey = user
} else {
	user = varFirstName + ' ' + varLastName
	searchKey = varLastName
}

//Check to see if we're writing printed output also to a file
writeFile = false

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

writeFile = false

WebDriver driver = DriverFactory.getWebDriver()

Actions act = new Actions(driver);

JavascriptExecutor js =(JavascriptExecutor)driver;

WebUI.setText(findTestObject('Object Repository/Admin/API Dashboard/input_User_search'), searchKey)

WebUI.delay(5)

WebElement Table = driver.findElement(By.xpath('//*[@id="default-rezult"]/table'))
 
List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count + ' rows found')

if(row_count > 1) {
	
	for(r = 1; r < row_count; r++) {
	
		Table = driver.findElement(By.xpath('//*[@id="default-rezult"]/table'))
		 
		Rows = Table.findElements(By.tagName('tr'))
		 
		println('here')
		row = Rows.get(r)
		fieldValues = [:]
		
		//List<WebElement> Columns = Rows.get(1).findElements(By.tagName('td'))
		List<WebElement> Columns = row.findElements(By.tagName('td'))
		
		col = Columns.get(1)
	/*
		println(Columns.get(1).getText())
		
		js.executeScript("arguments[0].style.border='5px dotted yellow'", Columns.get(1));
		
		act.moveToElement(Columns.get(1)).moveByOffset(-10, 0).click().perform();
	*/
		println(col.getText())
		
		int center = col.getSize().getWidth()
		
		println(center)
		
		int myOffset = center / 4
		
		println(myOffset)
		
		js.executeScript("arguments[0].style.border='5px dotted blue'",col);
		
		act.moveToElement(col).moveByOffset(-(myOffset) , 0).click().perform();
	
		WebUI.delay(2)
		
		outText = ('Looking for full profile of ' + user)
		
		println('=====> ' + outText)
		
		if (writeFile) {
			outFile.append(outText + '\n')
		}
		
		found = false
		
		userFound = false
		
		firstNameExists = false
			
		if(varType != 'email') {
			
			// ############# Check first and last names here before clicking the Full Profile button
			// The possible options here are:
			// 1. Hopefull case - The first and last names do appear here so we know we have the correct profile
			//		so click the Full Profile button and proceed
			// 2. Wrong candidate case - The first and last name fields appear here, but it is the wrong candidate
			//		so click the back button to get the next user
			// 3. Not a candidate profile - The first and last name fields are not on the screen so this is not a
			//		candidate. It may be a partner or guide, or may be an error screen of some sort
			//		so click the back button to get the next user
			
			//	First check for the First Name field to be on the page
			firstNameExists = WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/API Dashboard/td_First Name'), 3, FailureHandling.OPTIONAL)
			
			if(firstNameExists) {
				fName = WebUI.getText(findTestObject('Object Repository/Admin/API Dashboard/td_First Name')).toLowerCase()
				
				lName = WebUI.getText(findTestObject('Object Repository/Admin/API Dashboard/td_Last Name')).toLowerCase()
				
				if(fName == varFirstName.toLowerCase() && lName == varLastName.toLowerCase()) {
		
					user = varFirstName + ' ' + varLastName
		
					userFound = true
				}
		
			}
		
		}
		
		if(userFound || varType == 'email') {
		
			WebUI.click(findTestObject('Object Repository/Admin/API User Profile/btn_Full Profile'))
			
			WebUI.delay(5)
							
			outText = ('Getting full profile of ' + user)
			
			println('=====> ' + outText)
			
			if (writeFile) {
				outFile.append(outText + '\n')
			}
		
			WebElement pTable = driver.findElement(By.xpath('/html/body/div/table'))
				
			List<WebElement> pRows = pTable.findElements(By.tagName('tr'))
				
			int pRow_count = pRows.size()
				
			println(pRow_count + ' profile rows found')
				
			first = true
			
			values = []
			
			for (r = 0; r < pRow_count; r++) {
			
				List<WebElement> header = pRows.get(r).findElements(By.tagName('th'))
				
				field = header.get(0).getText()
				
				if(field != '') {
					if(!first) {
						fieldValues.put(myField,values)
					}
					myField = field
					values = []
				}
				
				first = false
				
				List<WebElement> detail = pRows.get(r).findElements(By.tagName('td'))
				
				value = detail.get(0).getText()
				
			//	print('row is ' + r + ', header is ' + field + ', value is ' + value)
				pos = value.indexOf("\12")
				if(pos >= 0) {
					myValues = value.split("\12")
					for(val in myValues) {
						values.add(val)
					}
				} else {
					values.add(value)
				}
			}
			
			fieldValues.put(myField,values)
		
			found = true
		
			WebUI.delay(2)
		
			WebUI.back()
		
		} else { 		// Wrong candidate or not a candidate
			
			if(firstNameExists) {
				outText = ('Nope! Profile is for ' + fName + ', ' + lName + '.')
			} else {
				outText = ('Nope! Not a candidate profile.')
			}
				
			println('=====> ' + outText)
			
			if (writeFile) {
				outFile.append(outText + '\n')
			}
			
			fName = ''
			
			lName = ''
		}
		
		WebUI.delay(1)
		
		WebUI.back()
		
		WebUI.delay(1)

		if(r < row_count -1 && !found) {
		
			WebUI.setText(findTestObject('Object Repository/Admin/API Dashboard/input_User_search'), searchKey)
			
			WebUI.delay(5)
			
		} else {
		
			return fieldValues
			
			break
		}
	}
}
	
return null
/*
fieldValues.each {
	println(it.key + ' = ' + it.value)
}
*/


