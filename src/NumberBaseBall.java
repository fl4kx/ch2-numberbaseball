import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NumberBaseBall {

    /**
     * 플레이어가 맞춰야 하는 숫자
     */
    private final List<Integer> numberOfAnswer = new ArrayList<>();
    /**
     * 플레이어가 제출한 숫자
     */
    private final List<Integer> numberOfUser = new ArrayList<>();
    /**
     * 유저가 입력한 답의 점수판 입니다.
     */
    private final Map<String, Integer> scoreBoard = new HashMap<>();

    /**
     * {@code scoreBoard}를 초기화하는 메서드 입니다.
     */
    private void initScoreBoard() {
        scoreBoard.put("strike", 0);
        scoreBoard.put("ball", 0);
        scoreBoard.put("out", 3);
    }

    public void controller() {

    }

    /**
     * 정답으로 사용될 임의의 세 자리 수를 생성해 {@code numberOfAnswer}에 저장합니다.
     */
    private void numberGenerator() {
        Random random = new Random();
        Set<Integer> randomNumberSet = new LinkedHashSet<>();

        while (randomNumberSet.size() < 3) {
            randomNumberSet.add(random.nextInt(9) + 1);
        }

        numberOfAnswer.addAll(randomNumberSet);
    }

    /**
     * 사용자로부터 숫자를 입력을 받고 분석 가능한 형태로 가공해 {@code numberOfUser}에 저장합니다.
     */
    private void getUserInput() {
        Scanner sc = new Scanner(System.in);
        int userInput = sc.nextInt();

        while (userInput > 0) {
            numberOfUser.add(userInput % 10);
            userInput /= 10;
        }
        Collections.reverse(numberOfUser);
    }

    /**
     * {@code numberOfAnswer}와 {@code numberOfUser}의 요소를 비교해 {@code scoreBoard}에 결과를 기록합니다.
     *
     * @return {@code scoreBoard}를 반영한 문자열을 반환합니다.
     */
    private String answerComparator() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numberOfAnswer.get(i).equals(numberOfUser.get(j)) && i == j) {
                    scoreBoard.merge("strike", 1, Integer::sum);
                } else if (numberOfAnswer.get(i).equals(numberOfUser.get(j))) {
                    scoreBoard.merge("ball", 1, Integer::sum);
                }
            }
        }
        scoreBoard.merge("out", scoreBoard.get("strike") + scoreBoard.get("ball"), Integer::sum);
        return String.format("\u001B[34m" + "S : " + scoreBoard.get("strike") + "  " + "\u001B[33m" + " B : " + scoreBoard.get("ball") + "  " + "\u001B[31m " + "OUT : " + scoreBoard.get("out"));
    }

}
