#!/usr/bin/env python3

import argparse
import pandas as pd

def main():
    """ Consolidate the rows in a csv file and 
        output them in a new csv file.
    """
    parser = argparse.ArgumentParser(
        description='Consolidate the rows in a csv file and output \
            them in a new csv file.')

    parser.add_argument('filepaths', metavar='F', type=str, nargs='+',
                        help='A filepath to a csv file')

    args = parser.parse_args()

    for filepath in args.filepaths:
        spreadsheet = pd.read_csv(filepath)

        # Consolidate the rows
        spreadsheet = spreadsheet.groupby(['Timestamp']).aggregate(sum)
        spreadsheet = pd.DataFrame(spreadsheet)

        # Remove .csv from filename and output
        spreadsheet.to_csv(filepath[:-4:] + 'consolidated.csv')


if __name__ == '__main__':
    main()
