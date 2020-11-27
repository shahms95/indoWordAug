from nltk.corpus import wordnet as wn
from random import randint

# inputFile = "../hi-en/parallel/IITB.en-hi.100k.en"
# outputFile = "../hi-en/parallel/IITB-SR-aug.en-hi.100k.en"

inputFile = "../hi-en/dev_test/dev.en"
outputFileName = "../hi-en/dev_test/dev-SR-aug.en"
outputFile = open(outputFileName, 'w')

with open(inputFile,'r') as f:
	lines = f.readlines()

counter = 5
linecount = 0
for inputLine in lines:
	if counter<0:
		break
	counter = counter - 1
	outputFile.write(str(linecount) + "::" + inputLine)
	words = inputLine.split()
	numWords = len(words)
	numReplacedWords = max(1,0.15*numWords)
	replaceIndexes = []
	for x in xrange(numReplacedWords):
		replaceIndexes.append(randint(0,numReplacedWords-1))

	for replaceIndex in replaceIndexes:
		originalWord = words[replaceIndex]

		synset = set()
		for s in wn.synsets(originalWord):
			for l in s.lemmas():
				synset.add(l.name())

		synsetLimit = 3
		for synsetWord in synset:
			if(synsetLimit<0):
				break
			synsetLimit = synsetLimit - 1
			words[replaceIndex] = synsetWord
			changedLine = str(linecount) + "::" + " ".join(words)
			outputFile.write(changedLine)

		words[replaceIndex] = originalWord
	linecount = linecount+1
outputFile.close()