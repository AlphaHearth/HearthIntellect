import java.util.concurrent.Executors

def hhUrl(cardId) { "http://www.hearthhead.com/card=$cardId" }

def executor = Executors.newFixedThreadPool(50)

for (i in 0..50000) {
  final def j = i
  executor.submit ({ ->
    def url = hhUrl(j)
    def conn = new URL(url).openConnection()
    conn.setRequestProperty("User-Agent", "Mozilla")
    conn.requestMethod = "HEAD"
    conn.connect();

    if (conn.responseCode == 200)
      print "$j, "
  } as Runnable)
}

executor.shutdown()
