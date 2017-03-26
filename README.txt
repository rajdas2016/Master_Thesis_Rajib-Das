Master thesis on 'Mining Opinionated Product Features from Amazon Reviews'

For extracting features code is written in Java and it requires stanford-postagger-3.6.0, Jsoup, Slf4j-api, Htmlparser Java and Jackson-all. First we need to call HugeJSONRead3 class in order to get noun features (.csv file) and we have to insert into database counting noun frequency and review numbers.

If we wan to visualize noun features as wourdcloud, we have to call python class wcloud. For sentiment analysis we use a simple dictionary-based tool for sentiment analysis a sentence based on SentiWordNet 3.0 Sentiment scores are between -1 and 1, greater than 0 for positive and less than 0 for negative.

Dictionary-based sentiment analysis does not perform as well as a trained classifier, but it is domain-independent, based on a priori knowledge of words' sentiment values.

The class handles negations and multiword expressions.

Dependencies nltk including tokenizers

First download SentiWordNet 3.0 here, and delete any header and footer lines so that the file contains only data.

Initialize SentimentAnalysis with your SentiWordNet filesname and choice of weighting across word senses.

For mining opinion words we used Stanford CoreNLP parser, code is written in Java and it requires Stanford CoreNLP 3.4, Stanford Parser, JUnit and Mongo Java driver (if one plan to run it over many reviews stored in Mongo). If we don't use Mongo, just call the run method in Extract class giving it the text to extract opinion words from it.
