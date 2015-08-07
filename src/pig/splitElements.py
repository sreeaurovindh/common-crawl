@outputSchema("leafpaths:bag{t:tuple(xpaths:chararray)}")
def splitBag(input):
    result = [' ']
    if input is not None:
        result = input.split("|")
    return result
