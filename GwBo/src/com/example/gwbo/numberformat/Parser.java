package com.example.gwbo.numberformat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;

public class Parser {
	
	 private static final int STATUS_INFINITE = 0;
	 private static final int STATUS_POSITIVE = 1;
	 private static final int STATUS_LENGTH   = 2;
	
	 private transient boolean isCurrencyFormat = false;
	 
	 private boolean parseIntegerOnly = false;
	 private boolean groupingUsed = true;
	 
	 private DecimalFormatSymbols symbols;
	 
	 private String  positivePrefix = "";
	 private String  positiveSuffix = "";
	 private String  negativePrefix = "-";
	 private String  negativeSuffix = "";
	 
	 private int     multiplier = 1;
	 private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
	 private boolean parseBigDecimal = false;
	 
	 private transient DigitList digitList = new DigitList();
	 
	 public Parser(DecimalFormatSymbols symbols) {
		 this.symbols = symbols;
	 }
	
	 
	 public Number parse(String text, ParsePosition pos) {
	        // special case NaN
	        if (text.regionMatches(pos.index, symbols.getNaN(), 0, symbols.getNaN().length())) {
	            pos.index = pos.index + symbols.getNaN().length();
	            return new Double(Double.NaN);
	        }

	        boolean[] status = new boolean[STATUS_LENGTH];
	        if (!subparse(text, pos, positivePrefix, negativePrefix, digitList, false, status)) {
	        	System.out.println("subparse false");
	            return null;
	        }
	        System.out.println("subparse true");
	        System.out.println("index " + pos.getIndex());

	        // special case INFINITY
	        if (status[STATUS_INFINITE]) {
	            if (status[STATUS_POSITIVE] == (multiplier >= 0)) {
	                return new Double(Double.POSITIVE_INFINITY);
	            } else {
	                return new Double(Double.NEGATIVE_INFINITY);
	            }
	        }

	        if (multiplier == 0) {
	            if (digitList.isZero()) {
	                return new Double(Double.NaN);
	            } else if (status[STATUS_POSITIVE]) {
	                return new Double(Double.POSITIVE_INFINITY);
	            } else {
	                return new Double(Double.NEGATIVE_INFINITY);
	            }
	        }

	        if (isParseBigDecimal()) {
	            BigDecimal bigDecimalResult = digitList.getBigDecimal();

	            if (multiplier != 1) {
	                try {
	                    bigDecimalResult = bigDecimalResult.divide(getBigDecimalMultiplier());
	                }
	                catch (ArithmeticException e) {  // non-terminating decimal expansion
	                    bigDecimalResult = bigDecimalResult.divide(getBigDecimalMultiplier(), roundingMode);
	                }
	            }

	            if (!status[STATUS_POSITIVE]) {
	                bigDecimalResult = bigDecimalResult.negate();
	            }
	            return bigDecimalResult;
	        } else {
	            boolean gotDouble = true;
	            boolean gotLongMinimum = false;
	            double  doubleResult = 0.0;
	            long    longResult = 0;

	            // Finally, have DigitList parse the digits into a value.
	            if (digitList.fitsIntoLong(status[STATUS_POSITIVE], isParseIntegerOnly())) {
	                gotDouble = false;
	                longResult = digitList.getLong();
	                if (longResult < 0) {  // got Long.MIN_VALUE
	                    gotLongMinimum = true;
	                }
	            } else {
	                doubleResult = digitList.getDouble();
	            }

	            // Divide by multiplier. We have to be careful here not to do
	            // unneeded conversions between double and long.
	            if (multiplier != 1) {
	                if (gotDouble) {
	                    doubleResult /= multiplier;
	                } else {
	                    // Avoid converting to double if we can
	                    if (longResult % multiplier == 0) {
	                        longResult /= multiplier;
	                    } else {
	                        doubleResult = ((double)longResult) / multiplier;
	                        gotDouble = true;
	                    }
	                }
	            }

	            if (!status[STATUS_POSITIVE] && !gotLongMinimum) {
	                doubleResult = -doubleResult;
	                longResult = -longResult;
	            }

	            // At this point, if we divided the result by the multiplier, the
	            // result may fit into a long.  We check for this case and return
	            // a long if possible.
	            // We must do this AFTER applying the negative (if appropriate)
	            // in order to handle the case of LONG_MIN; otherwise, if we do
	            // this with a positive value -LONG_MIN, the double is > 0, but
	            // the long is < 0. We also must retain a double in the case of
	            // -0.0, which will compare as == to a long 0 cast to a double
	            // (bug 4162852).
	            if (multiplier != 1 && gotDouble) {
	                longResult = (long)doubleResult;
	                gotDouble = ((doubleResult != (double)longResult) ||
	                            (doubleResult == 0.0 && 1/doubleResult < 0.0)) &&
	                            !isParseIntegerOnly();
	            }

	            return gotDouble ?
	                (Number)new Double(doubleResult) : (Number)new Long(longResult);
	        }
	    }
	 
	private boolean isParseBigDecimal() {
		return parseBigDecimal;
	}

	protected final boolean subparse(String text, ParsePosition parsePosition,
			String positivePrefix, String negativePrefix, DigitList digits,
			boolean isExponent, boolean status[]) {
		int position = parsePosition.index;
		int oldStart = parsePosition.index;
		int backup;
		boolean gotPositive, gotNegative;

		// check for positivePrefix; take longest
		gotPositive = text.regionMatches(position, positivePrefix, 0,
				positivePrefix.length());
		gotNegative = text.regionMatches(position, negativePrefix, 0,
				negativePrefix.length());

		if (gotPositive && gotNegative) {
			if (positivePrefix.length() > negativePrefix.length()) {
				gotNegative = false;
			} else if (positivePrefix.length() < negativePrefix.length()) {
				gotPositive = false;
			}
		}

		if (gotPositive) {
			position += positivePrefix.length();
		} else if (gotNegative) {
			position += negativePrefix.length();
		} else {
			parsePosition.errorIndex = position;
			return false;
		}

		// process digits or Inf, find decimal position
		status[STATUS_INFINITE] = false;
		if (!isExponent
				&& text.regionMatches(position, symbols.getInfinity(), 0,
						symbols.getInfinity().length())) {
			position += symbols.getInfinity().length();
			status[STATUS_INFINITE] = true;
		} else {
			// We now have a string of digits, possibly with grouping symbols,
			// and decimal points. We want to process these into a DigitList.
			// We don't want to put a bunch of leading zeros into the DigitList
			// though, so we keep track of the location of the decimal point,
			// put only significant digits into the DigitList, and adjust the
			// exponent as needed.

			digits.decimalAt = digits.count = 0;
			char zero = symbols.getZeroDigit();
			char decimal = isCurrencyFormat ? symbols
					.getMonetaryDecimalSeparator() : symbols
					.getDecimalSeparator();
			char grouping = symbols.getGroupingSeparator();
			String exponentString = symbols.getExponentSeparator();
			boolean sawDecimal = false;
			boolean sawExponent = false;
			boolean sawDigit = false;
			int exponent = 0; // Set to the exponent value, if any

			// We have to track digitCount ourselves, because digits.count will
			// pin when the maximum allowable digits is reached.
			int digitCount = 0;

			backup = -1;
			for (; position < text.length(); ++position) {
				char ch = text.charAt(position);

				/*
				 * We recognize all digit ranges, not only the Latin digit range
				 * '0'..'9'. We do so by using the Character.digit() method,
				 * which converts a valid Unicode digit to the range 0..9.
				 * 
				 * The character 'ch' may be a digit. If so, place its value
				 * from 0 to 9 in 'digit'. First try using the locale digit,
				 * which may or MAY NOT be a standard Unicode digit range. If
				 * this fails, try using the standard Unicode digit ranges by
				 * calling Character.digit(). If this also fails, digit will
				 * have a value outside the range 0..9.
				 */
				int digit = ch - zero;
				if (digit < 0 || digit > 9) {
					digit = Character.digit(ch, 10);
				}

				if (digit == 0) {
					// Cancel out backup setting (see grouping handler below)
					backup = -1; // Do this BEFORE continue statement below!!!
					sawDigit = true;

					// Handle leading zeros
					if (digits.count == 0) {
						// Ignore leading zeros in integer part of number.
						if (!sawDecimal) {
							continue;
						}

						// If we have seen the decimal, but no significant
						// digits yet, then we account for leading zeros by
						// decrementing the digits.decimalAt into negative
						// values.
						--digits.decimalAt;
					} else {
						++digitCount;
						digits.append((char) (digit + '0'));
					}
				} else if (digit > 0 && digit <= 9) { // [sic] digit==0 handled
														// above
					sawDigit = true;
					++digitCount;
					digits.append((char) (digit + '0'));

					// Cancel out backup setting (see grouping handler below)
					backup = -1;
				} else if (!isExponent && ch == decimal) {
					// If we're only parsing integers, or if we ALREADY saw the
					// decimal, then don't parse this one.
					if (isParseIntegerOnly() || sawDecimal) {
						break;
					}
					digits.decimalAt = digitCount; // Not digits.count!
					sawDecimal = true;
				} else if (!isExponent && ch == grouping && isGroupingUsed()) {
					if (sawDecimal) {
						break;
					}
					// Ignore grouping characters, if we are using them, but
					// require that they be followed by a digit. Otherwise
					// we backup and reprocess them.
					backup = position;
				} else if (!isExponent
						&& text.regionMatches(position, exponentString, 0,
								exponentString.length()) && !sawExponent) {
					// Process the exponent by recursively calling this method.
					ParsePosition pos = new ParsePosition(position
							+ exponentString.length());
					boolean[] stat = new boolean[STATUS_LENGTH];
					DigitList exponentDigits = new DigitList();

					if (subparse(text, pos, "",
							Character.toString(symbols.getMinusSign()),
							exponentDigits, true, stat)
							&& exponentDigits.fitsIntoLong(
									stat[STATUS_POSITIVE], true)) {
						position = pos.index; // Advance past the exponent
						exponent = (int) exponentDigits.getLong();
						if (!stat[STATUS_POSITIVE]) {
							exponent = -exponent;
						}
						sawExponent = true;
					}
					break; // Whether we fail or succeed, we exit this loop
				} else {
					break;
				}
			}

			if (backup != -1) {
				position = backup;
			}

			// If there was no decimal point we have an integer
			if (!sawDecimal) {
				digits.decimalAt = digitCount; // Not digits.count!
			}

			// Adjust for exponent, if any
			digits.decimalAt += exponent;

			// If none of the text string was recognized. For example, parse
			// "x" with pattern "#0.00" (return index and error index both 0)
			// parse "$" with pattern "$#0.00". (return index 0 and error
			// index 1).
			if (!sawDigit && digitCount == 0) {
				parsePosition.index = oldStart;
				parsePosition.errorIndex = oldStart;
				return false;
			}
		}

		// check for suffix
		if (!isExponent) {
			if (gotPositive) {
				gotPositive = text.regionMatches(position, positiveSuffix, 0,
						positiveSuffix.length());
			}
			if (gotNegative) {
				gotNegative = text.regionMatches(position, negativeSuffix, 0,
						negativeSuffix.length());
			}

			// if both match, take longest
			if (gotPositive && gotNegative) {
				if (positiveSuffix.length() > negativeSuffix.length()) {
					gotNegative = false;
				} else if (positiveSuffix.length() < negativeSuffix.length()) {
					gotPositive = false;
				}
			}

			// fail if neither or both
			if (gotPositive == gotNegative) {
				parsePosition.errorIndex = position;
				return false;
			}

			parsePosition.index = position
					+ (gotPositive ? positiveSuffix.length() : negativeSuffix
							.length()); // mark success!
		} else {
			parsePosition.index = position;
		}

		status[STATUS_POSITIVE] = gotPositive;
		if (parsePosition.index == oldStart) {
			parsePosition.errorIndex = position;
			return false;
		}
		return true;
	}
	
	private boolean isGroupingUsed() {
		return groupingUsed;
	}

	public boolean isParseIntegerOnly() {
	       return parseIntegerOnly;
	}
	
	private BigDecimal getBigDecimalMultiplier() {
       if (bigDecimalMultiplier == null) {
            bigDecimalMultiplier = new BigDecimal(multiplier);
     }
        return bigDecimalMultiplier;
        }
	private transient BigDecimal bigDecimalMultiplier;
}