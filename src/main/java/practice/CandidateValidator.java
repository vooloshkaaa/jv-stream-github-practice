package practice;

import java.util.function.Predicate;
import java.util.stream.Stream;
import model.Candidate;

public class CandidateValidator implements Predicate<Candidate> {
    public static final int YEARS_OF_RESIDENCE = 10;
    public static final int MINIMUM_AGE = 35;
    public static final String REQUIRED_NATIONALITY = "Ukrainian";

    @Override
    public boolean test(Candidate c) {
        if (c.getAge() < MINIMUM_AGE) {
            return false;
        }
        if (!c.isAllowedToVote()) {
            return false;
        }
        if (!REQUIRED_NATIONALITY.equals(c.getNationality())) {
            return false;
        }
        return hasMinimumResidence(c, YEARS_OF_RESIDENCE);
    }

    private boolean hasMinimumResidence(Candidate c, int yearsOfResidence) {
        return Stream.of(c.getPeriodsInUkr().split(","))
                .map(String::trim)
                .mapToInt(range -> {
                    String[] years = range.split("-");
                    return Integer.parseInt(years[1].trim())
                            - Integer.parseInt(years[0].trim());
                })
                .sum() >= yearsOfResidence;
    }
}
