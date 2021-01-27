package calculator.string.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delimiter {
    private final Pattern pattern = Pattern.compile("//(.)\n(.*)");
    public List<String> delimiters;
    public Matcher m;

    public Delimiter() {
        this.delimiters = new ArrayList<>(Arrays.asList(":", ","));
    }

    public boolean isMatch(String expression){
        m = pattern.matcher(expression);
        return m.find();
    }

    public void updateDelimiter(){
        String customDelimiter = m.group(1);
        delimiters.add(customDelimiter);
    }

    public String getNewExpression(){
        String newExpression = m.group(2);
        return newExpression;
    }

    public String getDelimiters() {
        StringBuilder result = new StringBuilder();
        for (String symbol : this.delimiters) {
            result.append(symbol).append("|");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
