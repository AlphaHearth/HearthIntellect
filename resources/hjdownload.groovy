import static java.nio.file.StandardCopyOption.*
import java.nio.file.*
import java.util.concurrent.Executors

def downloadFile(base, url) {
  def realUrl = new URL(base + url)
  def conn = realUrl.openConnection()
  conn.setRequestProperty("User-Agent", "Mozilla")
  try {
  	def input = conn.getInputStream()
    def tarFile = new File("." + url)
    tarFile.parentFile.mkdirs()
    tarFile.createNewFile()
    Files.copy(input, tarFile.toPath(), REPLACE_EXISTING)
    println "Downloaded ${url}."
  } catch (FileNotFoundException ex) {
  	println "${url} not found."
  } catch (ex) {
  	println "${url}: ${ex}"
  } finally {
  	input.close()
  }
}

def patches = [ 3140, 3388, 3604, 3645, 3664, 3749, 3890, 3937, 4217, 4217, 4243, 4442,
        4458, 4482, 4944, 4973, 5170, 5314, 5435, 5506, 5834, 6024, 6141, 6284, 6485, 6898, 7234, 7628, 7785, 7835,
        8036, 8108, 8311, 8416, 8834, 9166, 9554, 9786, 10357, 10604, 10784, 10833, 10956, 11461, 11959, 12051,
        12105, 12266, 12574, 13030, 13619, 13714, 13740, 13807 ]
def locales = [ 'all', 'deDE', 'enUS', 'esES', 'esMX', 'frFR', 'itIT', 'jaJP', 'koKR', 'plPL', 'ptBR', 'ruRU', 'thTH', 'zhCN', 'zhTW' ]
def files = [ 'cardbacks.json', 'cards.collectible.json', 'cards.json' ]

def base = "https://api.hearthstonejson.com"
def url(patch, locale, file) { "/v1/${patch}/${locale}/${file}" }
def executor = Executors.newFixedThreadPool(50)

for (patch in patches) {
	for (locale in locales) {
		for (file in files) {
			def url = url(patch, locale, file)
			executor.submit({-> downloadFile(base, url)} as Runnable)
		}
	}
}

executor.shutdown()
