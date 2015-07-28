@outputSchema("leafpaths:bag{t:tuple(xpaths:chararray)}")
def splitBag(input):
    outBag = input.split("|")
    return outBag
