# HearthIntellect Crawler

Execute the following Java main classes in order:

- `HearthJsonDownloader`: Download JSON data files from [hearthstonejson.com](http://www.hearthstonejson.com).
- `HearthJsonCardParser`: Parse the downloaded files and save the data to local database.
- `HearthHeadCardCrawler`: Populate the card data in database with data from [hearthhead.com](http://www.hearthhead.com).
- `HearthHeadAssetDownloader`: Download card assets like images and audios from [hearthhead.com](http://www.hearthhead.com).