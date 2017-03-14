'''
    Dependencies to rn this program:
        * Python3
        * Scipy
        * Numpy
        * Matplotlib
        * Image
        * wordcloud

        To install dependcies, type following commands:  pip3 install <dependency_name>

'''

from os import path
from scipy.misc import imread
import matplotlib.pyplot as plt
import random
import operator

from wordcloud import WordCloud

word_freq = {}

def createDict():
    file = open('freq3.csv') # PATH TO CSV WHICH CAN BE CHANGED DEPENDING UPON THE LOCATION OF CSV FILE
    for line in file:
        word_freq[line.strip().split(',')[0]] = int(line.split(',')[1])

# Word cloud code - uncomment
#############################
def word_cloud():
    frequencies = sorted(word_freq.items(), key=operator.itemgetter(1), reverse=True)
    frequencies = frequencies[:500]

    wordcloud = WordCloud(font_path='/Library/Fonts/Verdana.ttf', max_words=500).fit_words(frequencies)
    plt.imshow(wordcloud)
    plt.axis("off")
    plt.show()

# Bar Chart for top 5
######################
def bar_chart(word_freq):
    word_freq = dict(sorted(word_freq.items(), key=operator.itemgetter(1), reverse=True)[:20])
    colors = ['gold', 'yellowgreen', 'lightcoral', 'lightskyblue', 'red']
    plt.bar(range(len(word_freq)), word_freq.values(), align='center')
    plt.xticks(range(len(word_freq)), word_freq.keys(), rotation=25)
    plt.show()

#  For Pie Chart
#################
def pie_chart():
    wordFreq = dict(sorted(word_freq.items(), key=operator.itemgetter(1), reverse=True)[:5])

    labels = list(wordFreq.keys())
    sizes = list(wordFreq.values())
    colors = ['gold', 'yellowgreen', 'lightcoral', 'lightskyblue', 'red']
    explode = (0.1, 0, 0, 0, 0)

    plt.pie(sizes, explode=explode, labels=labels, colors=colors, autopct='%1.1f%%', shadow=True, startangle=140)
    plt.axis('equal')
    plt.show()

if __name__ == '__main__':
    createDict()
    word_cloud()
    bar_chart(word_freq)
    pie_chart()
