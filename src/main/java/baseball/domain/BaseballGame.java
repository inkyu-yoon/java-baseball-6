package baseball.domain;

import camp.nextstep.edu.missionutils.Console;

import static baseball.constants.ErrorMessage.WRONG_RESPONSE_AFTER_FINISH;
import static baseball.constants.GameMessage.*;
import static baseball.constants.GameOption.ANSWER_NUMBER_LENGTH;
import static baseball.constants.SystemOption.EXIT_SYSTEM_VALUE;
import static baseball.constants.SystemOption.RESTART_SYSTEM_VALUE;

public class BaseballGame {
    private Answer answer;
    private Rule rule;

    public BaseballGame() {
        rule = new Rule();
        answer = new Answer();
    }

    public void run() {
        System.out.println(START_MESSAGE);
        start();
    }

    private void start() {
        answer.pickNumbers();

        int[] score;
        do {
            String userInput = getUserInput();
            int[] inputNums = rule.evaluateNumbers(userInput);
            score = answer.calculateScore(inputNums);
            printScore(score);
        } while (!canFinish(score));

        if (checkRestart()) {
            start();
        }
    }

    private static String getUserInput() {
        System.out.print(GET_USER_INPUT);
        return Console.readLine();
    }

    private boolean canFinish(int[] score) {
        int countOfStrikes = score[0];

        if (countOfStrikes == ANSWER_NUMBER_LENGTH.getLength()) {
            System.out.println(FINISH_MESSAGE);
            return true;
        }

        return false;
    }

    private void printScore(int[] score) {
        StringBuilder result = new StringBuilder();
        int countOfStrikes = score[0], countOfBalls = score[1];

        if (countOfBalls > 0) {
            result.append(countOfBalls).append(BALL).append(" ");
        }

        if (countOfStrikes > 0) {
            result.append(countOfStrikes).append(STRIKE).append(" ");
        }

        if (countOfBalls == 0 && countOfStrikes == 0) {
            result.append(NOTHING);
        }

        System.out.println(result);
    }

    private boolean checkRestart() {
        String userInput = Console.readLine();
        
        if (userInput.equals(RESTART_SYSTEM_VALUE.getValue())) {
            return true;
        }

        if (userInput.equals(EXIT_SYSTEM_VALUE.getValue())) {
            return false;
        }

        throw new IllegalStateException(WRONG_RESPONSE_AFTER_FINISH.toString());

    }
}
