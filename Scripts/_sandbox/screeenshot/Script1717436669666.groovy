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
import ru.yandex.qatools.ashot.AShot as AShot
import ru.yandex.qatools.ashot.Screenshot as Screenshot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies as ShootingStrategies
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider as WebDriverCoordsProvider
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import javax.imageio.ImageIO as ImageIO
import java.io.File as File
import org.openqa.selenium.By as By

WebUI.openBrowser('missionnext.org')

WebUI.maximizeWindow()

WebUI.delay(1)

//public class screenshotHelper {
// THIS DOES TAKE A SCREENSHOT BUT IT IS NOT OF THE AREA EXPECTED.
//	@Keyword
//def getWebElementScreenshot(TestObject object) {
//	println(1)
//	println(object)
WebDriver driver = DriverFactory.getWebDriver()

WebUI.scrollToElement(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/i_Facebook-f_fab fa-facebook-f'), 0)

WebUI.delay(2)

println(2)

WebElement element = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Misc Saved Objects/Page_Serve in Missions - MissionNext.org/i_Facebook-f_fab fa-facebook-f'), 
    10)

println(3)

println(element)

rect = element.getRect()

println(rect)

println('x is ' + rect.getX())
println('y is ' + rect.getY())
println('w is ' + rect.getWidth())
println('h is ' + rect.getHeight())

/*
 WebElement myWebElement = webDriver.findElement(By.cssSelector("#my_element"));
new AShot()
  .takeScreenshot(webDriver, myWebElement);
 */
//Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element)
//Screenshot logoImageScreenShot = new AShot().coordsProvider(new	WebDriverCoordsProvider()).takeScreenshot(driver, element);
//Screenshot screenshot = new AShot().coordsProvider(new	WebDriverCoordsProvider()).takeScreenshot(driver, element);
//new AShot().coordsProvider(new WebDriverCoordsProvider()) //find coordinates with WebDriver API
//.takeScreenshot(webDriver, myWebElement);
//new AShot().takeScreenshot(driver, element);
//Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,driver.findElement(By.xpath(webElementXpath)));
Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, driver.findElement(
        By.xpath('//a[contains(@href, \'https://www.facebook.com/MissionNextOrg/\')]')))

println(4)

ImageIO.write(screenshot.getImage(), 'PNG', new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Screenshots/MyScreenshot.png'))

WebUI.acceptAlert()

