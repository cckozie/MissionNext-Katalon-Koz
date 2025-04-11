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
import org.openqa.selenium.ActionChains
//import org.sikuli.script.*
//import org.sikuli.script.SikulixForJython as SikulixForJython
//import java.io.File
import javax.imageio.ImageIO;
import java.awt.image.*
//import net.sourceforge.tess4j.Tesseract
System.setProperty("jna.library.path", "/opt/homebrew/lib")

//username = varUsername

username = 'misty'

//Check to see if we're writing printed output also to a file
writeFile = false

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

//Screen s = new Screen()

//imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Misc/'

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//*[@id="default-rezult"]/table'))

List<WebElement> cells = Table.findElements(By.tagName('td'))

int cell_count = cells.size()

println(cell_count + ' cells found')

fieldValues = [:]
/*
userReg = s.find(imagePath + 'Username.png')

userReg.setW(100)

userReg.setH(400)

userReg.highlight(1)

checkReg = s.find(imagePath + 'Status.png')

//checkReg.setW(40)

checkReg.setH(400)

//checkReg.highlight(1)

checks = checkReg.findAll(imagePath + 'checkmark.png')

for (check in checks) {
	check.setX(userReg.getX())
	check.setW(120)
	check.highlight(1)
	check.hover()
//	check.click()
	WebUI.delay(1)
	myX = userReg.getX()
	println(myX)
	myY = check.getY()
	println(myY)
	mouse.move("95", "450")
	WebUI.delay(1)
	
	mouse.click(button='left')
	
//	s.mouseMove(-310,0)
	WebUI.delay(1)
//	s.click()
//	WebUI.delay(1)
//	check.click().targetOffset(-150,0)
	
//	filename = capture(check, "/Users/cckozie/Desktop", "aaa.png")
	
	BufferedImage imgFile = s.capture(check).getImage()
//	BufferedImage bf = s2.capture().getImage();
	println(imgFile)
	File outputfile = new File(imagePath + "saved.png");
	ImageIO.write(imgFile, "png", outputfile);
	
	File file1 = new File(imagePath+"/destFile.png");
	ImageIO.write(imgFile, "png", file1);
	
	//user = check.text()
	
//	Tesseract tesseract = new Tesseract();
//	tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0_1/share/tessdata/"); // 'Configure your own path'
//	tesseract.setDatapath('/Users/cckozie/Downloads/Tess4J/tessdata/osd.traineddata')

	//user = tesseract.doOCR(new File(imagePath + "saved.png"))   //.trim().replaceAll(" ","")

	//println(user)
}
System.exit(0)
*/

// 120, 456

for (cell in cells) {
	println(cell.getText())
	name = cell.getText()
	if(name.contains(username)) {
		location = cell.location
		println(location)
		println(location.x)
		println(location.y)
		cell.click()
		
		WebUI.delay(5)

//		WebUI.waitForPageLoad(10)

		outText = ('Opening full profile of ' + username)

		println('=====> ' + outText)

		if (writeFile) {
			outFile.append(outText + '\n')
		}
		
		WebUI.click(findTestObject('Object Repository/Admin/API User Profile/btn_Full Profile'))
		
		WebUI.delay(5)
		
		WebElement pTable = driver.findElement(By.xpath('/html/body/div/table'))
		 
		List<WebElement> pRows = pTable.findElements(By.tagName('tr'))
		 
		int pRow_count = pRows.size()
		 
		println(pRow_count + ' profile rows found')
		 
		for (r = 0; r < pRow_count; r++) {

			List<WebElement> header = pRows.get(r).findElements(By.tagName('th'))
			
			field = header.get(0).getText()
			
			List<WebElement> detail = pRows.get(r).findElements(By.tagName('td'))
			
			value = detail.get(0).getText()
			
//			println(field + ' = ' + value)
			
			fieldValues.put(field,value)
		}

		break
	}
}

fieldValues.each {
	println(it.key + ' = ' + it.value)
}

return fieldValues

WebUI.closeBrowser()

