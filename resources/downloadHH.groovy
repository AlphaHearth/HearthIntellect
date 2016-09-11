import static java.nio.file.StandardCopyOption.*
import java.nio.file.*
import java.util.concurrent.Executors

def hhUrl(cardId) { "http://www.hearthhead.com/card=$cardId" }

def openConnWithRetry(url, retryTime, sleepTime) {
  def conn = null
  while (retryTime >= 0) {
    try {
      conn = url.openConnection()
      break
    } catch (ex) {
      retryTime--
      Thread.sleep(sleepTime)
    }
  }
  conn
}

def downloadFile(tarFileName, url) {
  def realUrl = new URL(url)
  def conn = openConnWithRetry(realUrl, 5, 500)
  conn.setRequestProperty("User-Agent", "Mozilla")
  try {
    def tarFile = new File("./" + tarFileName)
    tarFile.parentFile.mkdirs()
    tarFile.createNewFile()
  	def input = conn.getInputStream()
    Files.copy(input, tarFile.toPath(), REPLACE_EXISTING)
    println "Downloaded ${url}."
  } catch (FileNotFoundException ex) {
  	println "${url} not found."
  } catch (ex) {
    ex.printStackTrace()
  } finally {
  	input.close()
  }
}

def executor = Executors.newFixedThreadPool(20)

def content = new File("effectiveHHID.txt").text
def effectiveHHIDs = content.split(",")

for (hhid in effectiveHHIDs) {
	final def i = hhid.trim()
	executor.submit({ -> downloadFile("hh/${i}.html", hhUrl(i))} as Runnable)
}

executor.shutdown()
