package use_case.compatibility;

import java.util.*;

public class FengshuiCalculator {

    private final Map<String, String> generationMap = new HashMap<>();
    private final Map<String, String> controlMap = new HashMap<>();
    private final Map<String, String> harmonyMap = new HashMap<>();
    private final Map<String, String> clashMap = new HashMap<>();
    private final List<String> user1stembranch = new ArrayList<>();
    private final List<String> user2stembranch = new ArrayList<>();

    public FengshuiCalculator(Date userDate1, Date userDate2) {
        // Initialize generation relationships (相生) for Heavenly Stems
        generationMap.put("JIA", "BING");   // 甲生丙
        generationMap.put("BING", "WU");    // 丙生戊
        generationMap.put("WU", "GENG");    // 戊生庚
        generationMap.put("GENG", "REN");   // 庚生壬
        generationMap.put("REN", "JIA");    // 壬生甲

        // Initialize control relationships (相剋) for Heavenly Stems
        controlMap.put("JIA", "GENG");      // 甲剋庚
        controlMap.put("YI", "XIN");        // 乙剋辛
        controlMap.put("BING", "REN");      // 丙剋壬
        controlMap.put("DING", "GUI");      // 丁剋癸
        controlMap.put("WU", "JIA");        // 戊剋甲
        controlMap.put("JI", "YI");         // 己剋乙
        controlMap.put("GENG", "BING");     // 庚剋丙
        controlMap.put("XIN", "DING");      // 辛剋丁
        controlMap.put("REN", "WU");        // 壬剋戊
        controlMap.put("GUI", "JI");        // 癸剋己

        // Initialize harmony relationships (六合) for Earthly Branches
        harmonyMap.put("ZI", "CHOU");       // 子丑合
        harmonyMap.put("YIN", "HAI");       // 寅亥合
        harmonyMap.put("MAO", "XU");        // 卯戌合
        harmonyMap.put("CHEN", "YOU");      // 辰酉合
        harmonyMap.put("SI", "SHEN");       // 巳申合
        harmonyMap.put("WU", "WEI");        // 午未合
        harmonyMap.put("CHOU", "ZI");       // 丑子合
        harmonyMap.put("HAI", "YIN");       // 亥寅合
        harmonyMap.put("XU", "MAO");        // 戌卯合
        harmonyMap.put("YOU", "CHEN");      // 酉辰合
        harmonyMap.put("SHEN", "SI");       // 申巳合
        harmonyMap.put("WEI", "WU");        // 未午合

        // Initialize clash relationships (相沖) for Earthly Branches
        clashMap.put("ZI", "WU");           // 子午沖
        clashMap.put("WU", "ZI");           // 午子沖
        clashMap.put("CHOU", "WEI");        // 丑未沖
        clashMap.put("WEI", "CHOU");        // 未丑沖
        clashMap.put("YIN", "SHEN");        // 寅申沖
        clashMap.put("SHEN", "YIN");        // 申寅沖
        clashMap.put("MAO", "YOU");         // 卯酉沖
        clashMap.put("YOU", "MAO");         // 酉卯沖
        clashMap.put("CHEN", "XU");         // 辰戌沖
        clashMap.put("XU", "CHEN");         // 戌辰沖
        clashMap.put("SI", "HAI");          // 巳亥沖
        clashMap.put("HAI", "SI");          // 亥巳沖


        user1stembranch.addAll(calculateYearStems(userDate1));
        System.out.println(calculateYearStems(userDate1));
        user1stembranch.addAll(calculateMonthStems(userDate1));
        System.out.println(calculateMonthStems(userDate1));
        user1stembranch.addAll(calculateDayStems(userDate1));
        System.out.println(calculateDayStems(userDate1));

        user2stembranch.addAll(calculateYearStems(userDate2));
        user2stembranch.addAll(calculateMonthStems(userDate2));
        user2stembranch.addAll(calculateDayStems(userDate2));
    }

    public List<String> calculateYearStems(Date date) {
        List<String> heavenlyStems = Arrays.asList("JIA", "YI", "BING", "DING", "WU", "JI", "GENG", "XIN", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("ZI", "CHOU", "YIN", "MAO", "CHEN", "SI", "WU", "WEI", "SHEN",
                "YOU", "XU", "HAI");

        // Use Calendar to extract the year from the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        // Adjust the year relative to 1984, the starting point of the 60-year cycle (甲子年)
        int yearOffset = (year - 1984) % 60;
        if (yearOffset < 0) {
            yearOffset += 60; // Ensure it's within the 0-59 range
        }

        // Calculate Heavenly Stem (天干) and Earthly Branch (地支) based on the year offset
        int heavenlyStemIndex = yearOffset % 10; // Heavenly Stem index (0 to 9)
        int earthlyBranchIndex = yearOffset % 12; // Earthly Branch index (0 to 11)

        // Output the result
        List<String> yearStemBranch = new ArrayList<>();
        yearStemBranch.add(heavenlyStems.get(heavenlyStemIndex));
        yearStemBranch.add(earthlyBranches.get(earthlyBranchIndex));

        return yearStemBranch;
    }

    public List<String> calculateMonthStems(Date date) {
        // Lists for Heavenly Stems (天干) and Earthly Branches (地支)
        List<String> heavenlyStems = Arrays.asList("JIA", "YI", "BING", "DING", "WU", "JI", "GENG", "XIN", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("YIN", "MAO", "CHEN", "SI", "WU", "WEI", "SHEN", "YOU", "XU", "HAI", "ZI", "CHOU");

        // Use Calendar to extract year and month from the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // Calendar months are 0-based, so add 1

        // Calculate the Heavenly Stem and Earthly Branch for the year
        int yearOffset = (year - 1984) % 60;
        if (yearOffset < 0) {
            yearOffset += 60; // Ensure it's within the 0-59 range
        }
        int yearHeavenlyStemIndex = yearOffset % 10; // Index for the year's Heavenly Stem

        // Adjust for Chinese lunar calendar: If date is before February 4, it's still the previous lunar year
        if (month < 2 || (month == 2 && calendar.get(Calendar.DAY_OF_MONTH) < 4)) {
            yearHeavenlyStemIndex = (yearHeavenlyStemIndex + 9) % 10; // Move to the previous year's Heavenly Stem
        }

        // Calculate the month Heavenly Stem based on the adjusted year's Heavenly Stem
        int monthHeavenlyStemIndex = (yearHeavenlyStemIndex * 2 + month - 2) % 10; // Start from 寅月, adjust by -2

        // Determine the Earthly Branch for the month (0-based index for months starting with 寅 in Lunar Calendar)
        int monthEarthlyBranchIndex = (month - 2 + 12) % 12; // 寅月是第一个月, month-2对齐

        // Output the result as month stem and branch
        List<String> monthStemBranch = new ArrayList<>();
        monthStemBranch.add(heavenlyStems.get(monthHeavenlyStemIndex));
        monthStemBranch.add(earthlyBranches.get(monthEarthlyBranchIndex));

        return monthStemBranch;
    }

    public List<String> calculateDayStems(Date birthday) {
        List<String> heavenlyStems = Arrays.asList("JIA", "YI", "BING", "DING", "WU", "JI", "GENG",
                "XIN", "REN", "GUI");
        List<String> earthlyBranches = Arrays.asList("ZI", "CHOU", "YIN", "MAO", "CHEN", "SI", "WU", "WEI", "SHEN",
                "YOU", "XU", "HAI");

        List<String> dayStems = new ArrayList<>();
        // Use Calendar to get year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are 0-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Determine if it's a leap year and set February days
        int febDays;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            febDays = 29;
        } else {
            febDays = 28;
        }

        // Calculate Day based on the F_day formula from the Python code
        int Y_2 = year % 100;
        int F_day = ((Y_2 + 7) * 5 + (Y_2 + 19) / 4 + 15) % 60;

        // Determine N_day based on month and day, adjusting for month lengths
        int N_day = 0;
        switch (month) {
            case 1:
                N_day = (F_day + day) % 60;
                break;
            case 2:
                N_day = (F_day + 31 + day) % 60;
                break;
            case 3:
                N_day = (F_day + 31 + febDays + day) % 60;
                break;
            case 4:
                N_day = (F_day + 31 + febDays + 31 + day) % 60;
                break;
            case 5:
                N_day = (F_day + 31 + febDays + 31 + 30 + day) % 60;
                break;
            case 6:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + day) % 60;
                break;
            case 7:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + day) % 60;
                break;
            case 8:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + day) % 60;
                break;
            case 9:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + day) % 60;
                break;
            case 10:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + day) % 60;
                break;
            case 11:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + day) % 60;
                break;
            case 12:
                N_day = (F_day + 31 + febDays + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + day) % 60;
                break;
        }

        // Determine Heavenly Stem and Earthly Branch based on N_day
        int heavenlyStemIndex = (N_day % 10) - 1;
        int earthlyBranchIndex = (N_day % 12) - 1;

        // Ensure indices are within bounds
        if (heavenlyStemIndex < 0) heavenlyStemIndex += 10;
        if (earthlyBranchIndex < 0) earthlyBranchIndex += 12;

        dayStems.add(heavenlyStems.get(heavenlyStemIndex));
        dayStems.add(earthlyBranches.get(earthlyBranchIndex));

        return dayStems;
    }

    private int calculateStemCompatibility(String stem1, String stem2) {
        if (Objects.equals(generationMap.get(stem1), stem2)) {
            return 2; // 相生
        } else if (Objects.equals(controlMap.get(stem1), stem2)) {
            return -1; // 相剋
        }
        return 0; // 無相生或相剋
    }

    // 計算地支相合或相沖的分數
    private int calculateBranchCompatibility(String branch1, String branch2) {
        if (Objects.equals(harmonyMap.get(branch1), branch2) || Objects.equals(harmonyMap.get(branch2), branch1)) {
            return 2; // 相合
        } else if ((Objects.equals(clashMap.get(branch1), branch2) || Objects.equals(clashMap.get(branch2), branch1))) {
            return -1; // 相沖
        }
        return 0; // 無相合或相沖
    }

    public int calculateCompatibilityScore(List<String> user1, List<String> user2) {
        int score = 0;

        // Year Pillar Compatibility (user1[0], user1[1] with user2[0], user2[1])
        score += calculateStemCompatibility(user1.get(0), user2.get(0)); // Year Heavenly Stem
        score += calculateBranchCompatibility(user1.get(1), user2.get(1)); // Year Earthly Branch

        // Month Pillar Compatibility (user1[2], user1[3] with user2[2], user2[3])
        score += calculateStemCompatibility(user1.get(2), user2.get(2)); // Month Heavenly Stem
        score += calculateBranchCompatibility(user1.get(3), user2.get(3)); // Month Earthly Branch

        // Day Pillar Compatibility (user1[4], user1[5] with user2[4], user2[5])
        score += calculateStemCompatibility(user1.get(4), user2.get(4)); // Day Heavenly Stem
        score += calculateBranchCompatibility(user1.get(5), user2.get(5)); // Day Earthly Branch

        // Determine compatibility based on the score
        return score;
    }

    public String getCompatibilityResult(int score) {
        if (score >= 6) {
            return "High Compatibility: A very good match!";
        } else if (score >= 3) {
            return "Moderate Compatibility: Some harmony, but also some differences.";
        } else if (score >= 0) {
            return "Low Compatibility: There are some conflicts, more effort needed.";
        } else {
            return "Very Low Compatibility: Likely a challenging relationship.";
        }
    }

    public String calculateScore() {

        int compatibilityScore = calculateCompatibilityScore(user1stembranch, user2stembranch);
        return getCompatibilityResult(compatibilityScore);
    }

    public static void main(String[] args) {
        Calendar myCalendar = new GregorianCalendar(1981, 3, 15);
        Date myDate = myCalendar.getTime();

        Calendar myCalendar2 = new GregorianCalendar(1999, 7, 25);
        Date myDate2 = myCalendar2.getTime();

        FengshuiCalculator calculator = new FengshuiCalculator(myDate, myDate2);
        calculator.calculateScore();
    }

}
