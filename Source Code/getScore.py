from sentimentAnalysis import get_score

file = open("freq4.csv")

count=0

for line in file:
    
    score = get_score(line.split(',')[0])
    if score == 1:
        count=count+1
        print('Word: '+ line.split(',')[0] + str(score))

print('No. of positive sentiments: ', count)
        
