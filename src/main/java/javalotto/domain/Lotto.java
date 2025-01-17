package javalotto.domain;

import javalotto.constants.LottoConstants;
import javalotto.exception.lotto.LottoInvalidSizeException;
import javalotto.exception.lotto.LottoNumberDuplicateException;
import javalotto.exception.lotto.LottoNumberOutOfRangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javalotto.constants.LottoConstants.*;

public class Lotto {

    private final List<Integer> numbers;

    private Lotto(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
    }

    public static Lotto from(List<Integer> numbers) {
        validateLotto(numbers);

        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .collect(Collectors.toList());

        return new Lotto(sortedNumbers);
    }

    private static void validateLotto(List<Integer> numbers) {
        if (isInvalidRange(numbers)) {
            throw new LottoNumberOutOfRangeException(numbers);
        }
        if (hasDuplicate(numbers)) {
            throw new LottoNumberDuplicateException(numbers);
        }
        if (isInvalidSize(numbers)) {
            throw new LottoInvalidSizeException(numbers);
        }
    }

    private static boolean isInvalidRange(List<Integer> numbers) {
        return numbers.stream()
                .anyMatch(Lotto::isInvalidRange);
    }

    public static boolean isInvalidRange(int number) {
        return LOTTO_NUMBER_MIN_VALUE > number || number > LOTTO_NUMBER_MAX_VALUE;
    }

    private static boolean hasDuplicate(List<Integer> numbers) {
        return getDistinctCount(numbers) != numbers.size();
    }

    private static long getDistinctCount(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .count();
    }

    private static boolean isInvalidSize(List<Integer> numbers) {
        return numbers.size() != LOTTO_NUMBERS_COUNT;
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public int getMatchCount(Lotto other) {
        return (int) this.numbers.stream()
                .filter(other.numbers::contains)
                .count();
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
