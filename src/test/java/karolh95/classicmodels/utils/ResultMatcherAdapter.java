package karolh95.classicmodels.utils;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ResultMatcherAdapter {

    public static <T> ResultMatcher equalTo(String name, T value) {
        return jsonPath("$." + name, Matchers.equalTo(value));
    }
}
