#!/usr/bin/python

@outputSchema('concated: chararray')
def concat_bag(BAG):
        return '|'.join([ str(i) for i in BAG ])

