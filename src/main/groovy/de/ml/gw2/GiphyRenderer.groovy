package de.ml.gw2

import at.mukprojects.giphy4j.Giphy
import at.mukprojects.giphy4j.entity.search.SearchFeed
import at.mukprojects.giphy4j.exception.GiphyException
import com.google.inject.Singleton
import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class GiphyRenderer {
    private final Giphy giphy = new Giphy("dc6zaTOxFJmzC")
    private final Random random = new Random(System.currentTimeMillis())

    String render(String searchString) {
        String giphyIframe = "https://media.giphy.com/media/WzCZU1PbrmfZu/giphy.gif"
        try {
            SearchFeed feed = giphy.search(searchString, 1, random.nextInt(100))
            giphyIframe = feed.dataList.get(0).images.original.url
        } catch (GiphyException ignore) {
        }
        return '''<html><head>
    <title>$searchString</title>
    <style>
      .center {
          display: block;
          margin-left: auto;
          margin-right: auto;
          width: 50%;
      }
    </style>
  </head><img src="$giphyIframe" title="$searchString" class="center" ></html>'''
    }
}
